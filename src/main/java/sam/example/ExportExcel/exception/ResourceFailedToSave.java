package sam.example.ExportExcel.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ResourceFailedToSave extends RuntimeException{

    public ResourceFailedToSave(String message){
        super(message);
    }
}
