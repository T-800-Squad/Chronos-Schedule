package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.AddConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.GetScheduleDTO;
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
    public void testAddConfigurationToScheduleNotFound() {
        AddConfigurationDTO dto = new AddConfigurationDTO();
        dto.setDayOfWeek("Monday");
        dto.setServiceName("Cleaning");

        Mockito.when(scheduleRepository.findByDayOfWeekAndServiceSpaceType("Monday", "Cleaning")).thenReturn(null);

        try {
            scheduleService.addConfigurationToSchedule(dto);
            fail("Expected ScheduleManagementException was not thrown.");
        } catch (ScheduleManagementException e) {
            assertEquals(ScheduleManagementException.SCHEDULE_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void testAddConfigurationToScheduleSuccess() {
        AddConfigurationDTO dto = new AddConfigurationDTO();
        dto.setDayOfWeek("Monday");
        dto.setServiceName("Cleaning");
        dto.setConfigurationId("config123");

        Schedule schedule = new Schedule();
        schedule.setDayOfWeek("Monday");
        schedule.setServiceSpaceType("Cleaning");

        Mockito.when(scheduleRepository.findByDayOfWeekAndServiceSpaceType("Monday", "Cleaning")).thenReturn(schedule);
        Mockito.when(scheduleRepository.save(schedule)).thenReturn(schedule);

        try {
            scheduleService.addConfigurationToSchedule(dto);
            assertEquals("config123", schedule.getIdConfiguration());
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
}

