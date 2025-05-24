package moduloGestionUsuarios.ScheduleManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import moduloGestionUsuarios.ScheduleManagement.DTO.ConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.response.Response;
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

    @Operation(summary = "Creacion de una configuracion", description = "Crea una configuracion.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se creo la configuracion."),
            @ApiResponse(responseCode = "401", description = "No tiene permisos"),
            @ApiResponse(responseCode = "400", description = "La configuracion no se creo")
    })
    @PostMapping()
    public ResponseEntity<Response<String>> createConfiguration(@RequestBody Configuration configuration) throws ScheduleManagementException {
        configurationService.createConfiguration(configuration);

        Response<String> response = new Response<>(
                HttpStatus.CREATED.value(),
                "La configuracion fue creada",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<Response<String>> deleteConfiguration(@RequestParam String name) throws ScheduleManagementException {
        configurationService.deleteConfiguration(name);

        Response<String> response = new Response<>(
                HttpStatus.OK.value(),
                "La configuracion fue eliminada",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<Response<List<ConfigurationDTO>>> getConfiguration() throws ScheduleManagementException {
        List<ConfigurationDTO> configuraciones = configurationService.getConfiguration();

        Response<List<ConfigurationDTO>> response = new Response<>(
                HttpStatus.OK.value(),
                "Lista de configuraciones obtenida correctamente",
                configuraciones
        );

        return new ResponseEntity<>(response, HttpStatus.OK);
    }




    @GetMapping("/name")
    public ResponseEntity<Response<Configuration>> getConfigurationByName(@RequestParam String name) throws ScheduleManagementException {
        Configuration configuration = configurationService.getConfigurationByName(name);
        Response<Configuration> response = new Response<>(
                HttpStatus.OK.value(),
                "Configuracion encontrada",
                configuration
        );
        return ResponseEntity.ok(response);
    }

    @GetMapping("/id")
    public ResponseEntity<Response<Configuration>> getConfigurationById(@RequestParam String id) throws ScheduleManagementException {
        Configuration configuration = configurationService.getConfigurationById(id);
        Response<Configuration> response = new Response<>(
                HttpStatus.OK.value(),
                "Configuracion encontrada",
                configuration
        );
        return ResponseEntity.ok(response);
    }
}
