package moduloGestionUsuarios.ScheduleManagement.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

@Document("Configuration")
public class Configuration {
    private String id;
    private LocalTime startTime;
    private LocalTime endTime;
    private List<BreakInterval> breakIntervals;
    private List<AttentionInterval> attentionIntervals;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LocalTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }
    public LocalTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }
    public List<BreakInterval> getBreakIntervals() {
        return breakIntervals;
    }
    public void setBreakIntervals(List<BreakInterval> breakIntervals) {
        this.breakIntervals = breakIntervals;
    }
    public List<AttentionInterval> getAttentionIntervals() {
        return attentionIntervals;
    }
    public void setAttentionIntervals(List<AttentionInterval> attentionIntervals) {
        this.attentionIntervals = attentionIntervals;
    }

}
