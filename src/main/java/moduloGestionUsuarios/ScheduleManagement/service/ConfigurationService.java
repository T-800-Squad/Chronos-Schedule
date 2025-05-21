package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.ConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Interval;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.repository.ConfigurationRepository;
import moduloGestionUsuarios.ScheduleManagement.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConfigurationService implements ConfigurationServiceInterface {

    @Autowired
    private ConfigurationRepository configurationRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void createConfiguration(Configuration configuration) throws ScheduleManagementException {
        if(configurationRepository.findByName(configuration.getName()).isPresent()){
            throw new ScheduleManagementException(ScheduleManagementException.NAME_EXIST);
        }
        List<Interval> attentionIntervals = configuration.getAttentionIntervals();
        List<Interval> breaksIntervals = configuration.getBreakIntervals();
        validateTimes(configuration.getStartTime(),configuration.getEndTime());
        validateIntervals(attentionIntervals);
        validateIntervals(breaksIntervals);
        validateAttentionAndBreakIntervals(attentionIntervals, breaksIntervals);
        configurationRepository.save(configuration);
    }


    private void validateIntervals(List<Interval> attentionIntervals) throws ScheduleManagementException {
        int attentionSize = attentionIntervals.size();
        for(int i = 0; i<attentionSize; i++){
            validateIntervalsNotCross(attentionIntervals,i);
            validateTimes(attentionIntervals.get(i).getStartTime(),attentionIntervals.get(i).getEndTime());
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
    private void validateTimes(LocalTime startTime,LocalTime endTime) throws ScheduleManagementException {
        if(startTime.isAfter(endTime)){
            throw new ScheduleManagementException(ScheduleManagementException.TIMES_CROSS);
        }
    }

    public void deleteConfiguration(String name) throws ScheduleManagementException {
        Optional<Configuration> configuration = configurationRepository.findByName(name);
        if (configuration.isEmpty()) {
            throw new ScheduleManagementException(ScheduleManagementException.CONFIG_NOT_FOUND);
        }
        Configuration config = configuration.get();
        String id = config.getId();
        if(!scheduleRepository.findAllByIdConfiguration(id).isEmpty()){
            throw new ScheduleManagementException(ScheduleManagementException.CONFIG_IN_SCHEDULE);
        }
        configurationRepository.deleteById(id);
    }

    public List<ConfigurationDTO> getConfiguration() throws ScheduleManagementException {
        List<Configuration> configs = configurationRepository.findAll();

        if (configs.isEmpty()) {
            throw new ScheduleManagementException(ScheduleManagementException.NO_CONFIGURATIONS_FOUND);
        }
        return convertConfigurationToDTO(configs);
    }



    private List<ConfigurationDTO> convertConfigurationToDTO(List<Configuration> configs) {
        List<ConfigurationDTO> dtos = new ArrayList<>();
        for (Configuration config : configs) {
            ConfigurationDTO dto = new ConfigurationDTO();
            dto.setName(config.getName());
            dto.setStartTime(config.getStartTime().toString());
            dto.setEndTime(config.getEndTime().toString());
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public Configuration getConfigurationByName(String name) throws ScheduleManagementException {
        Optional<Configuration> configuration = configurationRepository.findByName(name);
        if(configuration.isEmpty()){
            throw new ScheduleManagementException(ScheduleManagementException.CONFIG_NOT_FOUND);
        }
        return configuration.get();
    }

    @Override
    public Configuration getConfigurationById(String id) throws ScheduleManagementException {
        Optional<Configuration> configuration = configurationRepository.findById(id);
        if(configuration.isEmpty()){
            throw new ScheduleManagementException(ScheduleManagementException.CONFIG_NOT_FOUND);
        }
        return configuration.get();
    }
}
