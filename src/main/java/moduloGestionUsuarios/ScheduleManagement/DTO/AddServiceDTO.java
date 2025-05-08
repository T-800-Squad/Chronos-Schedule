package moduloGestionUsuarios.ScheduleManagement.DTO;

public class AddServiceDTO {
    private String serviceName;
    private String serviceDescription;

    public String getServiceDescription() {
        return serviceDescription;
    }
    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
}
