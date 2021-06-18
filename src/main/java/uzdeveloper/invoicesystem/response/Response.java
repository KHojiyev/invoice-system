package uzdeveloper.invoicesystem.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response {
    private String message;
    private String status;
    private Integer invoiceNumber;
    private Object object;

    public Response(String status) {
        this.status = status;
    }

    public Response(String status, Integer invoiceNumber) {
        this.status = status;
        this.invoiceNumber = invoiceNumber;
    }

    public Response(String status, Object object) {
        this.status = status;
        this.object = object;
    }

    public Response(String status, String message) {
        this.message = message;
        this.status = status;
    }

}
