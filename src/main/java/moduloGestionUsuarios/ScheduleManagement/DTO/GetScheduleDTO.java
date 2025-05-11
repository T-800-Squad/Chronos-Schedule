package moduloGestionUsuarios.ScheduleManagement.DTO;

public class GetScheduleDTO {
    private String serviceName;
    private String dayOfWeek;

    public GetScheduleDTO(String serviceName, String dayOfWeek) {
        this.serviceName = serviceName;
    }

    public GetScheduleDTO() {

    }

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {this.dayOfWeek = dayOfWeek;}
}
