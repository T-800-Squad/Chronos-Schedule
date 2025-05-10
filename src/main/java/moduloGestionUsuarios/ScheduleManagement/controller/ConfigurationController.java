package moduloGestionUsuarios.ScheduleManagement.controller;

import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.response.ApiResponse;
import moduloGestionUsuarios.ScheduleManagement.service.ConfigurationServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {

    @Autowired
    private ConfigurationServiceInterface configurationService;

    @PostMapping()
    public ResponseEntity<ApiResponse<String>> createConfiguration(@RequestBody Configuration configuration) throws ScheduleManagementException {
        configurationService.createConfiguration(configuration);
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "La configuracion fue creada",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
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
