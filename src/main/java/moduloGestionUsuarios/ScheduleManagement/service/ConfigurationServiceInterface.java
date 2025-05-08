package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ConfigurationServiceInterface {

    public void createConfiguration(@RequestBody Configuration configuration);

    public void deleteConfiguration(@RequestParam String id);

    public List<Configuration> getConfiguration();

    List<Configuration> getConfigurationInInterval(@RequestBody IntervalDTO interval);
}
