package moduloGestionUsuarios.ScheduleManagement.contrtoller;


import moduloGestionUsuarios.ScheduleManagement.controller.ScheduleController;

import moduloGestionUsuarios.ScheduleManagement.service.ScheduleServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
}
