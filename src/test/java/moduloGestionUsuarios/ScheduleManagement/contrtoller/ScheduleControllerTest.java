package moduloGestionUsuarios.ScheduleManagement.contrtoller;


import moduloGestionUsuarios.ScheduleManagement.controller.ScheduleController;

import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Schedule;
import moduloGestionUsuarios.ScheduleManagement.service.ScheduleServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ScheduleControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ScheduleServiceInterface scheduleService;

    @Test
    public void testAddServiceSchedule() throws Exception {
        try {
            Mockito.doNothing().when(scheduleService).addServiceSchedule(Mockito.any());
            mockMvc.perform(post("/schedule").content("{}").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status").value("201"))
                    .andExpect(jsonPath("$.message").value("Horarios para el servicio creados"))
                    .andExpect(jsonPath("$.data").doesNotExist());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testDeleteServiceScheduke() throws Exception {
        try {
            Mockito.doNothing().when(scheduleService).deleteServiceSchedule(Mockito.any());
            mockMvc.perform(delete("/schedule").param("serviceName","1"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200"))
                    .andExpect(jsonPath("$.message").value("Horario del servicio eliminado correctamente"))
                    .andExpect(jsonPath("$.data").doesNotExist());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testUpdateServiceSchedule() throws Exception {
        try {
            Mockito.doNothing().when(scheduleService).updateServiceSchedule(Mockito.any());
            mockMvc.perform(put("/schedule").content("{}").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200"))
                    .andExpect(jsonPath("$.message").value("El horario fue actualiado corretamente"))
                    .andExpect(jsonPath("$.data").doesNotExist());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void testServiceSchedule() throws Exception {
        try {
            when(scheduleService.getSchedule(Mockito.any())).thenReturn(List.of(new Schedule()));
            mockMvc.perform(get("/schedule").param("serviceName","1").param("dayOfWeek","monday"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200"))
                    .andExpect(jsonPath("$.message").value("Lista de horarios obtenida correctamente"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    public void testGetSchedule() throws Exception {
        try{
            when(scheduleService.getServicesNames()).thenReturn(List.of("1", "2", "3"));
            mockMvc.perform(get("/schedule/service"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.status").value("200"))
                    .andExpect(jsonPath("$.message").value("Lista de nombres de servicios registrados en el sistemas"));
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
}
