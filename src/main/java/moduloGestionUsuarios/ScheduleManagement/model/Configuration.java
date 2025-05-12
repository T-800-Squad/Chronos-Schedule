package moduloGestionUsuarios.ScheduleManagement.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalTime;
import java.util.List;

@Document("Configuration")
public class Configuration {
    private String id;
    private String name;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @JsonFormat(pattern = "HH:mm")
    private LocalTime endTime;
    private List<Interval> breakIntervals;
    private List<Interval> attentionIntervals;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
    public List<Interval> getBreakIntervals() {
        return breakIntervals;
    }
    public void setBreakIntervals(List<Interval> intervals) {
        this.breakIntervals = intervals;
    }
    public List<Interval> getAttentionIntervals() {
        return attentionIntervals;
    }
    public void setAttentionIntervals(List<Interval> attentionIntervals) {
        this.attentionIntervals = attentionIntervals;
    }

}
