package moduloGestionUsuarios.ScheduleManagement.controller;

import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.service.ConfigurationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationServiceInterface configurationService;

    @PostMapping()
    public void createConfiguration(@RequestBody Configuration configuration) throws ScheduleManagementException {
        configurationService.createConfiguration(configuration);
    }

    @DeleteMapping()
    public void deleteConfiguration(@RequestParam String id) {

    }
    @GetMapping()
    public List<Configuration> getConfiguration() {
        return null;
    }

    @GetMapping("/interval")
    public List<Configuration> getConfigurationInInterval(@RequestBody IntervalDTO interval) {
        return null;
    }
}
