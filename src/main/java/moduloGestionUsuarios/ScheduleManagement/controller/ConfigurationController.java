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
    public ResponseEntity<ApiResponse<String>> deleteConfiguration(@RequestParam String id) throws ScheduleManagementException {
        configurationService.deleteConfiguration(id);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "La configuracion fue eliminada",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Configuration>>> getConfiguration() throws ScheduleManagementException {
        List<Configuration> configuraciones = configurationService.getConfiguration();

        ApiResponse<List<Configuration>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lista de configuraciones obtenida correctamente",
                configuraciones
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/interval")
    public List<Configuration> getConfigurationInInterval(@RequestBody IntervalDTO interval) {
        return null;
    }
}
