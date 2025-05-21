package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.ConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ConfigurationServiceInterface {

    void createConfiguration(@RequestBody Configuration configuration) throws ScheduleManagementException;

    void deleteConfiguration(@RequestParam String id) throws ScheduleManagementException;

    List<ConfigurationDTO> getConfiguration() throws ScheduleManagementException;



    Configuration getConfigurationByName(@RequestParam String name) throws ScheduleManagementException;

    Configuration getConfigurationById(@RequestParam String id) throws ScheduleManagementException;
}
