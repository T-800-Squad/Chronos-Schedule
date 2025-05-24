package moduloGestionUsuarios.ScheduleManagement.service;


import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.repository.ConfigurationRepository;
import moduloGestionUsuarios.ScheduleManagement.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class ScheduleService implements ScheduleServiceInterface {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ConfigurationRepository configurationRepository;

    public void addServiceSchedule(AddServiceDTO addServiceDTO) throws ScheduleManagementException{
        if(scheduleRepository.existsByServiceSpaceType(addServiceDTO.getServiceName())){
            throw new ScheduleManagementException(ScheduleManagementException.SERVICE_EXIST);
        }
        String[] days ={
                "lunes",
                "martes",
                "miercoles",
                "jueves",
                "viernes",
                "sabado",
        };
        for(String day:days) {
            Schedule schedule = new Schedule();
            schedule.setServiceSpaceType(addServiceDTO.getServiceName());
            schedule.setResponsibleUser(addServiceDTO.getResponsibleUser());
            schedule.setDayOfWeek(day);
            scheduleRepository.save(schedule);
        }
    }
    public void deleteServiceSchedule(String serviceName) throws ScheduleManagementException {
        if (!scheduleRepository.existsByServiceSpaceType(serviceName)) {
            throw new ScheduleManagementException(ScheduleManagementException.SERVICE_NOT_FOUND);
        }
        scheduleRepository.deleteAllByServiceSpaceType(serviceName);
    }

    public void updateServiceSchedule(UpdateServiceDTO updateServiceDTO) throws ScheduleManagementException{
        if(!scheduleRepository.existsByServiceSpaceType(updateServiceDTO.getServiceName())){
            throw new ScheduleManagementException(ScheduleManagementException.SERVICE_NOT_FOUND);
        }
        if(!configurationRepository.existsByName(updateServiceDTO.getConfigurationName())){
            throw new ScheduleManagementException(ScheduleManagementException.CONFIG_NOT_FOUND);
        }
        Schedule schedule = scheduleRepository.findByDayOfWeekAndServiceSpaceType(updateServiceDTO.getDayOfWeek(), updateServiceDTO.getServiceName());
        Optional<Configuration> configuration = configurationRepository.findByName(updateServiceDTO.getConfigurationName());
        schedule.setResponsibleUser(updateServiceDTO.getResponsibleUser());
        schedule.setIdConfiguration(configuration.get().getId());
        scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedule(GetScheduleDTO getScheduleDTO) throws ScheduleManagementException {
        List<Schedule> schedules = scheduleRepository.findAllByDayOfWeekAndServiceSpaceType(getScheduleDTO.getDayOfWeek(), getScheduleDTO.getServiceName());
        if (schedules.isEmpty()) {
            throw new ScheduleManagementException(ScheduleManagementException.SCHEDULE_NOT_FOUND);
        }
        return schedules;
    }

    public List<String> getServicesNames() throws ScheduleManagementException {
        List<Schedule> schedules = scheduleRepository.findAll();
        Set<String> services = new HashSet<>();
        for (Schedule schedule : schedules) {
            services.add(schedule.getServiceSpaceType());
        }
        return new ArrayList<>(services);
    }
}
