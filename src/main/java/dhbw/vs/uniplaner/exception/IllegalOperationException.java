package dhbw.vs.uniplaner.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;




        import org.springframework.http.HttpStatus;
        import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class IllegalOperationException extends Exception{

    private static final long serialVersionUID = 1L;

    public IllegalOperationException(String message){
        super(message);
    }
}
