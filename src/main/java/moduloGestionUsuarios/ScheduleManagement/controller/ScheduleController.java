package moduloGestionUsuarios.ScheduleManagement.controller;

import moduloGestionUsuarios.ScheduleManagement.DTO.AddConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.service.ScheduleService;
import moduloGestionUsuarios.ScheduleManagement.service.ScheduleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleServiceInterface scheduleService;

    @PostMapping()
    public void addServiceSchedule(@RequestBody AddServiceDTO addServiceDTO) {

    }
    @DeleteMapping()
    public void deleteServiceSchedule(@RequestParam String serviceName) {

    }
    @PutMapping()
    public void updateServiceSchedule(@RequestBody UpdateServiceDTO updateServiceDTO) {

    }
    @PostMapping("/configuration")
    public void addConfigurationToSchedule(@RequestBody AddConfigurationDTO addConfigurationDTO) {

    }
    @GetMapping()
    public List<Schedule> getSchedule(GetScheduleDTO getScheduleDTO) {
        return null;
    }
}
