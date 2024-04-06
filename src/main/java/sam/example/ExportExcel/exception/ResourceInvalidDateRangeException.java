package sam.example.ExportExcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceInvalidDateRangeException extends RuntimeException {
    public ResourceInvalidDateRangeException(String message){
        super(message);
    }
}
