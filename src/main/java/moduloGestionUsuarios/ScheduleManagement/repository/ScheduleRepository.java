package moduloGestionUsuarios.ScheduleManagement.repository;

import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ScheduleRepository extends MongoRepository<Schedule, String> {

}
