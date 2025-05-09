package moduloGestionUsuarios.ScheduleManagement.model;


import java.time.LocalTime;

public class Interval {
    private LocalTime startTime;
    private LocalTime endTime;
    private String reason;

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
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
}
