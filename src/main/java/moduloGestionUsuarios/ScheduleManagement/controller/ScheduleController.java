package moduloGestionUsuarios.ScheduleManagement.controller;

import moduloGestionUsuarios.ScheduleManagement.DTO.AddConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.service.ScheduleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import moduloGestionUsuarios.ScheduleManagement.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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
    public ResponseEntity<ApiResponse<String>> deleteServiceSchedule(@RequestParam String serviceName) throws ScheduleManagementException {
        scheduleService.deleteServiceSchedule(serviceName);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Horario del servicio eliminado correctamente",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping()
    public void updateServiceSchedule(@RequestBody UpdateServiceDTO updateServiceDTO){

    }

    @PostMapping("/configuration")
    public ResponseEntity<ApiResponse<String>> addConfigurationToSchedule(@RequestBody AddConfigurationDTO addConfigurationDTO) throws ScheduleManagementException {
        scheduleService.addConfigurationToSchedule(addConfigurationDTO);

        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.CREATED.value(),
                "Configuración agregada al horario correctamente",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse<List<Schedule>>> getSchedule(GetScheduleDTO getScheduleDTO) throws ScheduleManagementException {
        List<Schedule> horarios = scheduleService.getSchedule(getScheduleDTO);

        ApiResponse<List<Schedule>> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Lista de horarios obtenida correctamente",
                horarios
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
