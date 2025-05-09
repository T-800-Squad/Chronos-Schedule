package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Interval;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class ConfigurationService implements ConfigurationServiceInterface {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public void createConfiguration(Configuration configuration) throws ScheduleManagementException {
        if(configurationRepository.findByName(configuration.getName()).isPresent()){
            throw new ScheduleManagementException(ScheduleManagementException.NAME_EXIST);
        }
        List<Interval> attentionIntervals = configuration.getAttentionIntervals();
        List<Interval> breaksIntervals = configuration.getBreakIntervals();
        validateIntervals(attentionIntervals);
        validateIntervals(breaksIntervals);
        validateAttentionAndBreakIntervals(attentionIntervals, breaksIntervals);
        configurationRepository.save(configuration);
    }
    private void validateIntervals(List<Interval> attentionIntervals) throws ScheduleManagementException {
        int attentionSize = attentionIntervals.size();
        for(int i = 0; i<attentionSize; i++){
            validateIntervalsNotCross(attentionIntervals,i);
        }
    }

    private void validateIntervalsNotCross(List<Interval> attentionIntervals, int i) throws ScheduleManagementException {
        int attentionSize = attentionIntervals.size();
        Interval attentionInterval = attentionIntervals.get(i);
        LocalTime startTime = attentionInterval.getStartTime();
        LocalTime endTime = attentionInterval.getEndTime();
        for(int j = 0; j<attentionSize; j++){
            Interval attentionInterval2 = attentionIntervals.get(j);
            if(i!=j){
                validateIntervalsNotCross(startTime,attentionInterval2.getStartTime(),endTime,attentionInterval2.getEndTime());
            }
        }

    }
    private void validateAttentionAndBreakIntervals(List<Interval> attentionIntervals, List<Interval> breakIntervals) throws ScheduleManagementException {
        int attentionSize = attentionIntervals.size();
        for(int i = 0; i<attentionSize; i++){
            validateAttentionAndBreakIntervalsNotCross(attentionIntervals,breakIntervals,i);
        }
    }

    private void validateAttentionAndBreakIntervalsNotCross(List<Interval> attentionIntervals,List<Interval> breakIntervals, int i) throws ScheduleManagementException {
        int breakSize = breakIntervals.size();
        Interval attentionInterval = attentionIntervals.get(i);
        LocalTime startTime = attentionInterval.getStartTime();
        LocalTime endTime = attentionInterval.getEndTime();
        for(int j = 0; j<breakSize; j++){
            Interval breakInterval = breakIntervals.get(j);
            validateIntervalsNotCross(startTime,breakInterval.getStartTime(),endTime,breakInterval.getEndTime());
        }
    }

    private void validateIntervalsNotCross(LocalTime firstStartTime, LocalTime secondStartTime, LocalTime firstEndTime, LocalTime secondEndTime) throws ScheduleManagementException {
        if((firstStartTime.isBefore(secondStartTime) && firstEndTime.isAfter(secondStartTime))||
                (secondStartTime.isBefore(firstStartTime) && secondEndTime.isAfter(firstStartTime))){
            throw new ScheduleManagementException(ScheduleManagementException.INTERVALS_CROSS);
        }
    }



    public void deleteConfiguration(String id) {

    }

    public List<Configuration> getConfiguration() {
        return List.of();
    }

    public List<Configuration> getConfigurationInInterval(IntervalDTO interval) {
        return List.of();
    }
}
