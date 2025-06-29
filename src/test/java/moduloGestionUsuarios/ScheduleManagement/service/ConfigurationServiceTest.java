package moduloGestionUsuarios.ScheduleManagement.service;

import moduloGestionUsuarios.ScheduleManagement.DTO.ConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ConfigurationServiceTest {
    @Autowired
    private ConfigurationServiceInterface configurationService;
    @MockitoBean
    private ConfigurationRepository configurationRepository;
    @MockitoBean
    private ScheduleRepository scheduleRepository;

    @Test
    public void testCreateConfigurationNameExistException() {
        try{
            Configuration configuration = new Configuration();
            configuration.setName("test");
            Mockito.when(configurationRepository.existsByName("test")).thenReturn(true);
            configurationService.createConfiguration(configuration);
        }catch (ScheduleManagementException e){
            assertEquals(e.getMessage(),ScheduleManagementException.NAME_EXIST);
        }
    }

    @Test
    public void testCreateConfigurationTimesCrossException() {
        try{
            Configuration configuration = new Configuration();
            configuration.setName("test");
            configuration.setStartTime(LocalTime.parse("10:00"));
            configuration.setEndTime(LocalTime.parse("07:00"));
            Mockito.when(configurationRepository.findByName("test")).thenReturn(Optional.empty());
            configurationService.createConfiguration(configuration);
        }catch (ScheduleManagementException e){
            assertEquals(e.getMessage(),ScheduleManagementException.TIMES_CROSS);
        }
    }

    @Test
    public void testCreateConfigurationIntervalsCross() {
        try{
            Configuration configuration = new Configuration();
            configuration.setName("test");
            configuration.setStartTime(LocalTime.parse("07:00"));
            configuration.setEndTime(LocalTime.parse("19:00"));
            //attention intervals configuration
            Interval attentionInterval = new Interval();
            attentionInterval.setEndTime(LocalTime.parse("19:00"));
            attentionInterval.setStartTime(LocalTime.parse("12:00"));
            Interval attentionInterval2 = new Interval();
            attentionInterval2.setEndTime(LocalTime.parse("13:00"));
            attentionInterval2.setStartTime(LocalTime.parse("07:00"));
            List<Interval> attentionIntervals = Arrays.asList(attentionInterval, attentionInterval2);
            configuration.setAttentionIntervals(attentionIntervals);
            configuration.setBreakIntervals(attentionIntervals);
            Mockito.when(configurationRepository.findByName("test")).thenReturn(Optional.empty());
            configurationService.createConfiguration(configuration);
        }catch (ScheduleManagementException e){
            assertEquals(e.getMessage(),ScheduleManagementException.INTERVALS_CROSS);
        }
    }


    @Test
    public void testCreateConfigurationAttentionIntervalsCrossWithBreaks() {
        try {
            Configuration configuration = new Configuration();
            configuration.setName("test");
            configuration.setStartTime(LocalTime.parse("07:00"));
            configuration.setEndTime(LocalTime.parse("19:00"));
            //attention intervals configuration
            Interval attentionInterval = new Interval();
            attentionInterval.setEndTime(LocalTime.parse("19:00"));
            attentionInterval.setStartTime(LocalTime.parse("11:00"));
            List<Interval> attentionIntervals = Arrays.asList(attentionInterval);
            //breaks intervals
            Interval interval = new Interval();
            interval.setEndTime(LocalTime.parse("12:00"));
            interval.setStartTime(LocalTime.parse("10:00"));
            List<Interval> intervals = Arrays.asList(interval);
            configuration.setAttentionIntervals(attentionIntervals);
            configuration.setBreakIntervals(intervals);
            Optional<Configuration> option = Optional.of(configuration);
            Mockito.when(configurationRepository.findByName("test")).thenReturn(Optional.empty());
            configurationService.createConfiguration(configuration);
        } catch (ScheduleManagementException e) {
            assertEquals(e.getMessage(),ScheduleManagementException.INTERVALS_CROSS);
        }
    }

    @Test
    public void testCreateConfigurationWithoutProblems() {
        try{
            Configuration configuration = new Configuration();
            configuration.setName("test");
            //attention intervals configuration
            Interval attentionInterval = new Interval();
            attentionInterval.setEndTime(LocalTime.parse("19:00"));
            attentionInterval.setStartTime(LocalTime.parse("12:00"));
            Interval attentionInterval2 = new Interval();
            attentionInterval2.setEndTime(LocalTime.parse("10:00"));
            attentionInterval2.setStartTime(LocalTime.parse("07:00"));
            List<Interval> attentionIntervals = Arrays.asList(attentionInterval, attentionInterval2);

            //breaks intervals
            Interval interval = new Interval();
            interval.setEndTime(LocalTime.parse("12:00"));
            interval.setStartTime(LocalTime.parse("10:00"));
            List<Interval> intervals = Arrays.asList(interval);

            //configuration sets
            configuration.setStartTime(LocalTime.parse("07:00"));
            configuration.setEndTime(LocalTime.parse("19:00"));
            configuration.setBreakIntervals(intervals);
            configuration.setAttentionIntervals(attentionIntervals);

            Mockito.when(configurationRepository.save(configuration)).thenReturn(configuration);
            Mockito.when(configurationRepository.findByName("test")).thenReturn(Optional.empty());

            configurationService.createConfiguration(configuration);

        }catch (ScheduleManagementException e){
            fail();
        }
    }
    @Test
    public void testDeleteConfigurationNotFound() {
        try {
            String id = "12345";
            Mockito.when(configurationRepository.existsById(id)).thenReturn(false);
            configurationService.deleteConfiguration(id);
            fail("Expected ScheduleManagementException was not thrown.");
        } catch (ScheduleManagementException e) {
            assertEquals(ScheduleManagementException.CONFIG_NOT_FOUND, e.getMessage());
        }
    }

    @Test
    public void testDeleteConfigurationSuccess() {
        try {
            String name = "12345";
            Configuration configuration = new Configuration();
            configuration.setName(name);
            configuration.setId(name);
            Mockito.when(configurationRepository.findByName(name)).thenReturn(Optional.of(configuration));
            Mockito.doNothing().when(configurationRepository).deleteById(name);
            List<Schedule> schedules = new ArrayList<>();
            Mockito.when(scheduleRepository.findAllByIdConfiguration("12345")).thenReturn(schedules);
            configurationService.deleteConfiguration(name);
            Mockito.verify(configurationRepository).deleteById(name);
        } catch (ScheduleManagementException e) {
            fail("Unexpected ScheduleManagementException: " + e.getMessage());
        }
    }

    @Test
    public void testGetConfigurationNoConfigurationsFound() {
        try {
            Mockito.when(configurationRepository.findAll()).thenReturn(Arrays.asList());
            configurationService.getConfiguration();
            fail("Expected ScheduleManagementException was not thrown.");
        } catch (ScheduleManagementException e) {
            assertEquals(ScheduleManagementException.NO_CONFIGURATIONS_FOUND, e.getMessage());
        }
    }

    @Test
    public void testGetConfigurationSuccess() {
        try {
            Configuration configuration = new Configuration();
            configuration.setStartTime(LocalTime.parse("10:00"));
            configuration.setEndTime(LocalTime.parse("12:00"));
            List<Configuration> configurations = Arrays.asList(configuration);
            Mockito.when(configurationRepository.findAll()).thenReturn(configurations);
            List<ConfigurationDTO> result = configurationService.getConfiguration();
            assertNotNull(result);
            assertEquals(1, result.size());
            assertEquals("10:00", result.get(0).getStartTime());
            assertEquals("12:00", result.get(0).getEndTime());
        } catch (ScheduleManagementException e) {
            fail("Unexpected ScheduleManagementException: " + e.getMessage());
        }
    }

    @Test
    public void testGetConfigurationByNameNotFound() {
        try{
            Mockito.when(configurationRepository.findByName("test")).thenReturn(Optional.empty());
            configurationService.getConfigurationByName("test");
        }catch (ScheduleManagementException e){
            assertEquals(ScheduleManagementException.CONFIG_NOT_FOUND, e.getMessage());
        }
    }
    @Test
    public void testGetConfigurationByNameSuccess() {
        try{
            Configuration configuration = new Configuration();
            Mockito.when(configurationRepository.findByName("test")).thenReturn(Optional.of(configuration));
            configurationService.getConfigurationByName("test");
        }catch (ScheduleManagementException e){
            fail();
        }
    }


}
