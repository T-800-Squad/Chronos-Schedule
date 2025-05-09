package moduloGestionUsuarios.ScheduleManagement.exception;

import moduloGestionUsuarios.ScheduleManagement.response.ApiResponseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(ScheduleManagementException.class)
    public ResponseEntity<ApiResponseException> handleUserManagementException(ScheduleManagementException ex) {
        ApiResponseException response = new ApiResponseException(
                HttpStatus.BAD_REQUEST.value(),
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
