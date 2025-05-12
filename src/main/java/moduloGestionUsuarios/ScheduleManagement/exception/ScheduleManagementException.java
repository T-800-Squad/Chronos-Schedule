package moduloGestionUsuarios.ScheduleManagement.exception;

public class ScheduleManagementException extends Exception {

    public static final String NAME_EXIST = "Una configuracion ya fue registrada con ese nombre";
    public static final String INTERVALS_CROSS = "Hay intervalos que se cruzan";
    public static final String CONFIG_NOT_FOUND = "No se encontró la configuración.";
    public static final String NO_CONFIGURATIONS_FOUND = "No hay configuraciones registradas.";
    public static final String SERVICE_NOT_FOUND = "No se encontró el servicio con el nombre proporcionado.";
    public static final String SCHEDULE_NOT_FOUND = "No se encontró el horario con los parámetros proporcionados.";
    public static final String CONFIG_IN_SCHEDULE = "Esa configuracion no se puede usar debido a que se encuentra en uso";
    public static final String NOT_CONFIG_INTERVALS ="No se encontraton configuraciones con ese intervalo de tiempo";
    public static final String SERVICE_EXIST = "Un servicio con ese nombre ya existe";

    public ScheduleManagementException(String message) {
        super(message);
    }
}
