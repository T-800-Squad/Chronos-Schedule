package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.AddConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
public class ScheduleService implements ScheduleServiceInterface {

    @Autowired
    private ScheduleRepository scheduleRepository;

    public void addServiceSchedule(AddServiceDTO addServiceDTO) {

    }
    public void deleteServiceSchedule(String serviceName) throws ScheduleManagementException {
        if (!scheduleRepository.existsByServiceSpaceType(serviceName)) {
            throw new ScheduleManagementException(ScheduleManagementException.SERVICE_NOT_FOUND);
        }
        scheduleRepository.deleteAllByServiceSpaceType(serviceName);
    }

    public void updateServiceSchedule(UpdateServiceDTO updateServiceDTO){

    }

    public void addConfigurationToSchedule(AddConfigurationDTO addConfigurationDTO) throws ScheduleManagementException {
        Schedule schedule = scheduleRepository.findByDayOfWeekAndServiceSpaceType(addConfigurationDTO.getDayOfWeek(), addConfigurationDTO.getServiceName());
        if (schedule == null) {
            throw new ScheduleManagementException(ScheduleManagementException.SCHEDULE_NOT_FOUND);
        }
        schedule.setIdConfiguration(addConfigurationDTO.getConfigurationId());
        scheduleRepository.save(schedule);
    }

    public List<Schedule> getSchedule(GetScheduleDTO getScheduleDTO) throws ScheduleManagementException {
        List<Schedule> schedules = scheduleRepository.findAllByDayOfWeekAndServiceSpaceType(getScheduleDTO.getDayOfWeek(), getScheduleDTO.getServiceName());
        if (schedules.isEmpty()) {
            throw new ScheduleManagementException(ScheduleManagementException.SCHEDULE_NOT_FOUND);
        }
        return schedules;
    }
}
