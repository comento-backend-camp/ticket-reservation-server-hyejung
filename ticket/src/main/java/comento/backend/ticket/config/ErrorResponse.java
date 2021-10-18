package comento.backend.ticket.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ErrorResponse<T>{
    private Integer status;
    private String message;
    private String reason;

    public static ErrorResponse res(final Integer status, final String message, final String reason){
        return ErrorResponse.builder()
                .status(status)
                .message(message)
                .reason(reason)
                .build();
    }
}

