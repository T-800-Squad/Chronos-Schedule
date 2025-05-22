package moduloGestionUsuarios.ScheduleManagement.service;


import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;

import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ScheduleServiceInterface {



    void addServiceSchedule(@RequestBody AddServiceDTO addServiceDTO) throws ScheduleManagementException;

    void deleteServiceSchedule(@RequestParam String serviceName) throws ScheduleManagementException;

    void updateServiceSchedule(@RequestBody UpdateServiceDTO updateServiceDTO) throws ScheduleManagementException;


    List<Schedule> getSchedule(GetScheduleDTO getScheduleDTO)throws ScheduleManagementException;

    List<String> getServicesNames()throws ScheduleManagementException;
}
