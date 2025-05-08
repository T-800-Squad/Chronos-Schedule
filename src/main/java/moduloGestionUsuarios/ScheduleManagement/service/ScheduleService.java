package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.AddConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
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

    public void addServiceSchedule(@RequestBody AddServiceDTO addServiceDTO) {

    }

    public void deleteServiceSchedule(@RequestParam String serviceName) {

    }

    public void updateServiceSchedule(@RequestBody UpdateServiceDTO updateServiceDTO) {

    }

    public void addConfigurationToSchedule(@RequestBody AddConfigurationDTO addConfigurationDTO) {

    }

    public List<Schedule> getSchedule(GetScheduleDTO getScheduleDTO) {
        return null;
    }
}
