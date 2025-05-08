package moduloGestionUsuarios.ScheduleManagement.DTO;

public class AddConfigurationDTO {
    private String serviceName;
    private String dayOfWeek;
    private String ConfigurationId;

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
    public String getConfigurationId() {
        return ConfigurationId;
    }
    public void setConfigurationId(String configurationId) {
        ConfigurationId = configurationId;
    }
}
