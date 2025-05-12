package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ConfigurationServiceInterface {

    public void createConfiguration(@RequestBody Configuration configuration) throws ScheduleManagementException;

    public void deleteConfiguration(@RequestParam String id) throws ScheduleManagementException;

    public List<Configuration> getConfiguration() throws ScheduleManagementException;

    List<Configuration> getConfigurationInInterval(@RequestBody IntervalDTO interval) throws ScheduleManagementException;

    Configuration getConfigurationByName(@RequestParam String name) throws ScheduleManagementException;
}
