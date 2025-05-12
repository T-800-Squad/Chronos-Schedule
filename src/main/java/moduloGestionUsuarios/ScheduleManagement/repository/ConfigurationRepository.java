package moduloGestionUsuarios.ScheduleManagement.repository;

import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface ConfigurationRepository extends MongoRepository<Configuration, String> {
    Optional<Configuration> findByName(String name);
    List<Configuration> findAllByName(String name);
    List<Configuration> findAllByStartTimeAndEndTime(LocalTime startTime,LocalTime endTime);
}
