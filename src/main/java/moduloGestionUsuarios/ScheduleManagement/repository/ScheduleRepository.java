package moduloGestionUsuarios.ScheduleManagement.repository;

import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

    Schedule findByDayOfWeekAndServiceSpaceType(String dayOfWeek, String serviceSpaceType);

    List<Schedule> findAllByDayOfWeekAndServiceSpaceType(String dayOfWeek, String serviceSpaceType);

    void deleteAllByServiceSpaceType(String serviceSpaceType);

    boolean existsByServiceSpaceType(String serviceName);
}
