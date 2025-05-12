package moduloGestionUsuarios.ScheduleManagement.DTO;

public class AddServiceDTO {
    private String serviceName;
    private String responsibleUser;

    public String getResponsibleUser() {
        return responsibleUser;
    }
    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
