package sam.example.ExportExcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceAlreadyExistedException extends RuntimeException {
    public ResourceAlreadyExistedException(String message){
        super(message);
    }
}
