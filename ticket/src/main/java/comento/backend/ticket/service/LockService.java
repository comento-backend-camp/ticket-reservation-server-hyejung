package comento.backend.ticket.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Supplier;

@Slf4j
@Service
public class LockService {
	private static final String GET_LOCK = "SELECT GET_LOCK(?, ?)";
	private static final String RELEASE_LOCK = "SELECT RELEASE_LOCK(?)";

	private final DataSource dataSource;

	public LockService(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public <T> T execute(String lockName, int timeoutSeconds, Supplier<T> supplier) {
		//@brief : Lock을 획득한 connection으로 Lock 획득, 반납 작업 필요
		try (Connection connection = dataSource.getConnection()) {
			try {
				log.info("lock 획득");
				getLock(connection, lockName, timeoutSeconds);
				return supplier.get();
			} finally {
				log.info("lock 반납");
				releaseLock(connection, lockName);
			}
		} catch (SQLException e) {
			log.info("lock 획득 실패");
			throw new IllegalStateException(e);
		}
	}

	//@see : 스레드 당 1개의 lock을 획득하도록
	public void execute(String lockName, int timeoutSeconds, Runnable runnable) {
		this.execute(lockName, timeoutSeconds, () -> {
			runnable.run();
			return null;
		});
	}

	private void getLock(Connection connection, String lockName, int timeoutSeconds) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(GET_LOCK)) {
			preparedStatement.setString(1, lockName);
			preparedStatement.setInt(2, timeoutSeconds);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					throw new IllegalStateException("[LockFail] RESULT EMPTY");
				}

				int result = resultSet.getInt(1);
				if (result != 1) {
					throw new IllegalStateException(
						"[LockFail] result : " + result + ", lockName : " + lockName + ", timeoutSeconds : "
							+ timeoutSeconds);
				}
			}
		}
	}

	private void releaseLock(Connection connection, String lockName) throws SQLException {
		try (PreparedStatement preparedStatement = connection.prepareStatement(RELEASE_LOCK)) {
			preparedStatement.setString(1, lockName);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (!resultSet.next()) {
					throw new IllegalStateException("[UnlockFail] RESULT EMPTY");
				}

				int result = resultSet.getInt(1);
				if (result != 1) {
					throw new IllegalStateException("[UnlockFail] result : " + result + ", lockName : " + lockName);
				}
			}
		}
	}
}
