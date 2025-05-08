package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.repository.ConfigurationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigurationService implements ConfigurationServiceInterface {

    @Autowired
    private ConfigurationRepository configurationRepository;

    public void createConfiguration(Configuration configuration) {

    }

    public void deleteConfiguration(String id) {

    }

    public List<Configuration> getConfiguration() {
        return List.of();
    }

    public List<Configuration> getConfigurationInInterval(IntervalDTO interval) {
        return List.of();
    }
}
