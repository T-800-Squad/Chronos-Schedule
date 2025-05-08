package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.AddConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ScheduleServiceInterface {



    void addServiceSchedule(@RequestBody AddServiceDTO addServiceDTO);

    void deleteServiceSchedule(@RequestParam String serviceName);

    void updateServiceSchedule(@RequestBody UpdateServiceDTO updateServiceDTO);

    void addConfigurationToSchedule(@RequestBody AddConfigurationDTO addConfigurationDTO);

    List<Schedule> getSchedule(GetScheduleDTO getScheduleDTO);
}
