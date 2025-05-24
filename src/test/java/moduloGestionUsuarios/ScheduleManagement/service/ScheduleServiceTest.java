package moduloGestionUsuarios.ScheduleManagement.service;


import moduloGestionUsuarios.ScheduleManagement.DTO.AddServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.UpdateServiceDTO;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Interval;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.repository.ConfigurationRepository;
import moduloGestionUsuarios.ScheduleManagement.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ScheduleServiceTest {

    @Autowired
    private ScheduleServiceInterface scheduleService;

    @MockitoBean
    private ScheduleRepository scheduleRepository;
    @MockitoBean
    private ConfigurationRepository configurationRepository;

    @Test
    public void testDeleteServiceScheduleNotFound() {
        String serviceName = "Cleaning";
        Mockito.when(scheduleRepository.existsByServiceSpaceType(serviceName)).thenReturn(false);

        try {
            scheduleService.deleteServiceSchedule(serviceName);
            fail("Expected ScheduleManagementException was not thrown.");
        } catch (ScheduleManagementException e) {
            assertEquals(ScheduleManagementException.SERVICE_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void testDeleteServiceScheduleSuccess() {
        String serviceName = "Cleaning";
        Mockito.when(scheduleRepository.existsByServiceSpaceType(serviceName)).thenReturn(true);
        Mockito.doNothing().when(scheduleRepository).deleteAllByServiceSpaceType(serviceName);

        try {
            scheduleService.deleteServiceSchedule(serviceName);
            Mockito.verify(scheduleRepository).deleteAllByServiceSpaceType(serviceName);
        } catch (ScheduleManagementException e) {
            fail("Unexpected ScheduleManagementException: " + e.getMessage());
        }
    }


    @Test
    public void testGetScheduleNotFound() {
        GetScheduleDTO dto = new GetScheduleDTO();
        dto.setDayOfWeek("Tuesday");
        dto.setServiceName("Consulting");

        Mockito.when(scheduleRepository.findAllByDayOfWeekAndServiceSpaceType("Tuesday", "Consulting"))
                .thenReturn(Collections.emptyList());

        try {
            scheduleService.getSchedule(dto);
            fail("Expected ScheduleManagementException was not thrown.");
        } catch (ScheduleManagementException e) {
            assertEquals(ScheduleManagementException.SCHEDULE_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void testGetScheduleSuccess() {
        GetScheduleDTO dto = new GetScheduleDTO();
        dto.setDayOfWeek("Tuesday");
        dto.setServiceName("Consulting");

        Schedule schedule = new Schedule();
        schedule.setDayOfWeek("Tuesday");
        schedule.setServiceSpaceType("Consulting");

        Mockito.when(scheduleRepository.findAllByDayOfWeekAndServiceSpaceType("Tuesday", "Consulting"))
                .thenReturn(Arrays.asList(schedule));

        try {
            List<Schedule> result = scheduleService.getSchedule(dto);
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("Tuesday", result.get(0).getDayOfWeek());
        } catch (ScheduleManagementException e) {
            fail("Unexpected ScheduleManagementException: " + e.getMessage());
        }
    }
    @Test
    public void testExceptionAddServiceScheduleThatAlreadyExists() {
        try {
            AddServiceDTO dto = new AddServiceDTO();
            dto.setServiceName("Cleaning");
            dto.setResponsibleUser("admin");
            Mockito.when(scheduleRepository.existsByServiceSpaceType("Cleaning")).thenReturn(true);
            scheduleService.addServiceSchedule(dto);
        } catch (ScheduleManagementException e) {
            assertEquals("Un servicio con ese nombre ya existe", e.getMessage());
        }
    }

    @Test
    public void testAddServiceScheduleWithoutProblems() {
        try {
            AddServiceDTO dto = new AddServiceDTO();
            dto.setServiceName("Cleaning");
            dto.setResponsibleUser("admin");
            Mockito.when(scheduleRepository.existsByServiceSpaceType("Cleaning")).thenReturn(false);
            scheduleService.addServiceSchedule(dto);
        } catch (ScheduleManagementException e) {
            fail();
        }
    }
    @Test
    public void testExceptionUpdateServiceScheduleThatNotExists() {
        try{
            UpdateServiceDTO dto = new UpdateServiceDTO();
            dto.setServiceName("Cleaning");
            dto.setResponsibleUser("admin");
            Mockito.when(scheduleRepository.existsByServiceSpaceType("Cleaning")).thenReturn(false);
            scheduleService.updateServiceSchedule(dto);
        }catch(ScheduleManagementException e){
            assertEquals("No se encontró el servicio con el nombre proporcionado.", e.getMessage());
        }
    }

    @Test
    public void testExceptionUpdateServiceScheduleConfigurationNotExists() {
        try{
            UpdateServiceDTO dto = new UpdateServiceDTO();
            dto.setServiceName("Cleaning");
            dto.setResponsibleUser("admin");
            Mockito.when(scheduleRepository.existsByServiceSpaceType("Cleaning")).thenReturn(true);
            Mockito.when(configurationRepository.existsByName(dto.getConfigurationName())).thenReturn(false);
            scheduleService.updateServiceSchedule(dto);
        }catch(ScheduleManagementException e){
            assertEquals("No se encontró la configuración.", e.getMessage());
        }
    }
    @Test
    public void testUpdateServiceWithoutProblems() {
        try{
            UpdateServiceDTO dto = new UpdateServiceDTO();
            dto.setServiceName("Cleaning");
            dto.setResponsibleUser("admin");
            dto.setConfigurationName("config123");
            dto.setDayOfWeek("Monday");
            Schedule schedule = new Schedule();
            schedule.setDayOfWeek("Monday");
            schedule.setServiceSpaceType("Cleaning");
            schedule.setIdConfiguration("config12");
            schedule.setResponsibleUser("x");
            Configuration configuration = new Configuration();
            configuration.setId("config123");
            Mockito.when(configurationRepository.findByName(dto.getConfigurationName())).thenReturn(Optional.of(configuration));
            Mockito.when(configurationRepository.existsByName(dto.getConfigurationName())).thenReturn(true);
            Mockito.when(scheduleRepository.existsByServiceSpaceType("Cleaning")).thenReturn(true);
            Mockito.when(scheduleRepository.findByDayOfWeekAndServiceSpaceType("Monday", "Cleaning")).thenReturn(schedule);
            Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);
            scheduleService.updateServiceSchedule(dto);
        }catch(ScheduleManagementException e){
            fail();
        }
    }
}

