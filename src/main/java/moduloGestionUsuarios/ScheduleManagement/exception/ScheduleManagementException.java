package moduloGestionUsuarios.ScheduleManagement.exception;

public class ScheduleManagementException extends Exception {

    public static String NAME_EXIST = "Una configuracion ya fue registrada con ese nombre";
    public static String INTERVALS_CROSS = "Hay intervalos que se cruzan";


    public ScheduleManagementException(String message) {
        super(message);
    }
}
