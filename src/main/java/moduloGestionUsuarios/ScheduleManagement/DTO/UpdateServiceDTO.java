package moduloGestionUsuarios.ScheduleManagement.DTO;

public class UpdateServiceDTO {
    private String serviceName;
    private String dayOfWeek;
    private String responsibleUser;
    private String configurationName;

    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getResponsibleUser() {
        return responsibleUser;
    }
    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
    public String getConfigurationName() {
        return configurationName;
    }
    public void setConfigurationName(String configurationId) {
        this.configurationName = configurationId;
    }
}

