package moduloGestionUsuarios.ScheduleManagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.response.Response;
import moduloGestionUsuarios.ScheduleManagement.service.ScheduleServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import java.util.List;

@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private ScheduleServiceInterface scheduleService;

    @Operation(summary = "Creacion de horarios", description = "Crea horarios para cada dia, siempre que se registra un servicio nuevo.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Se crea un horario para cada dia del servicio."),
            @ApiResponse(responseCode = "401", description = "No tiene permisos"),
            @ApiResponse(responseCode = "400", description = "Los horarios no se crearon")
    })
    @PostMapping()
    public ResponseEntity<Response<String>> addServiceSchedule(@RequestBody AddServiceDTO addServiceDTO) throws ScheduleManagementException {
        scheduleService.addServiceSchedule(addServiceDTO);
        Response<String> response = new Response<>(
            HttpStatus.CREATED.value(),
            "Horarios para el servicio creados",
            null
        );
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @Operation(summary = "Elimina los horarios de un servicio", description = "Elimina horarios para cada dia de un servicio")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se elimino los horarios del servicio"),
            @ApiResponse(responseCode = "401", description = "No tiene permisos"),
            @ApiResponse(responseCode = "400", description = "Los horarios no se eliminaron")
    })
    @DeleteMapping()
    public ResponseEntity<Response<String>> deleteServiceSchedule(@RequestParam String serviceName) throws ScheduleManagementException {
        scheduleService.deleteServiceSchedule(serviceName);

        Response<String> response = new Response<>(
                HttpStatus.OK.value(),
                "Horario del servicio eliminado correctamente",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @Operation(summary = "Edita un horario", description = "Edita un horario, tanto la configuracion como su usuario responsable")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se edito el horario"),
            @ApiResponse(responseCode = "401", description = "No tiene permisos"),
            @ApiResponse(responseCode = "400", description = "No se edito el horario")
    })
    @PutMapping()
    public ResponseEntity<Response<String>> updateServiceSchedule(@RequestBody UpdateServiceDTO updateServiceDTO) throws ScheduleManagementException {
        scheduleService.updateServiceSchedule(updateServiceDTO);
        Response<String> response = new Response<>(
                HttpStatus.OK.value(),
                "El horario fue actualiado corretamente",
                null
        );
        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @Operation(summary = "Obtiene un horario", description = "Obtiene un horario de un servicio y un dia exacto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "El horario se consulto correctamente"),
            @ApiResponse(responseCode = "401", description = "No tiene permisos"),
            @ApiResponse(responseCode = "400", description = "El horario del servicio no se encontro")
    })
    @GetMapping()
    public ResponseEntity<Response<List<Schedule>>> getSchedule(@RequestParam String serviceName, @RequestParam String dayOfWeek) throws ScheduleManagementException {
        GetScheduleDTO getScheduleDTO = new GetScheduleDTO();
        getScheduleDTO.setServiceName(serviceName);
        getScheduleDTO.setDayOfWeek(dayOfWeek);
        List<Schedule> horarios = scheduleService.getSchedule(getScheduleDTO);

        Response<List<Schedule>> response = new Response<>(
                HttpStatus.OK.value(),
                "Lista de horarios obtenida correctamente",
                horarios
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @Operation(summary = "Obtiene todos los servicios", description = "Obtiene todos los servicios de los que se tienen horarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Se devolvieron los servicios correctamente"),
            @ApiResponse(responseCode = "401", description = "No tiene permisos"),
            @ApiResponse(responseCode = "400", description = "No se devolvieron los servicios")
    })
    @GetMapping("/service")
    public ResponseEntity<Response<List<String>>> getService()throws ScheduleManagementException {
        List<String> services = scheduleService.getServicesNames();
        Response<List<String>> response = new Response<>(
                HttpStatus.OK.value(),
                "Lista de nombres de servicios registrados en el sistemas",
                services
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
