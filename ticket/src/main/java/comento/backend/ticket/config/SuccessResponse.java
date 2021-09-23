package comento.backend.ticket.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SuccessResponse<T> {
    private Integer status;
    private String message;
    private Object data;

    //공통된 Success Message를 전송할 때 사용
    public static SuccessResponse res(final Integer status, final String message, final Object data){
        return SuccessResponse.builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

}
