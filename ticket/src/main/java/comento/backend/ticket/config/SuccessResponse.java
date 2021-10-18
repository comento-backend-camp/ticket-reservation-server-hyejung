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

    public static SuccessResponse res(final Integer status, final String message, final Object data){
        return SuccessResponse.builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    public static SuccessResponse res(final Integer status, final String message){
        return SuccessResponse.builder()
                .status(status)
                .message(message)
                .build();
    }

}
