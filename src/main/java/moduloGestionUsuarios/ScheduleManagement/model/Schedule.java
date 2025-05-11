package moduloGestionUsuarios.ScheduleManagement.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Schedule")
public class Schedule {
    @Id
    private String id;
    private String serviceSpaceType;
    private String responsibleUser;
    private String dayOfWeek;
    private String idConfiguration;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getServiceSpaceType() {
        return serviceSpaceType;
    }
    public void setServiceSpaceType(String serviceSpaceType) {
        this.serviceSpaceType = serviceSpaceType;
    }
    public String getResponsibleUser() {
        return responsibleUser;
    }
    public void setResponsibleUser(String responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
    public String getDayOfWeek() {
        return dayOfWeek;
    }
    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }
    public String getIdConfiguration() {
        return idConfiguration;
    }
    public void setIdConfiguration(String idConfiguration) {
        this.idConfiguration = idConfiguration;
    }
}
