package moduloGestionUsuarios.ScheduleManagement.contrtoller;

import moduloGestionUsuarios.ScheduleManagement.DTO.ConfigurationDTO;
import moduloGestionUsuarios.ScheduleManagement.DTO.IntervalDTO;
import moduloGestionUsuarios.ScheduleManagement.controller.ConfigurationController;
import moduloGestionUsuarios.ScheduleManagement.exception.ScheduleManagementException;
import moduloGestionUsuarios.ScheduleManagement.model.Configuration;
import moduloGestionUsuarios.ScheduleManagement.service.ConfigurationServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ConfigurationController.class)
@AutoConfigureMockMvc(addFilters = false)
public class ConfigurationControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ConfigurationServiceInterface configurationService;

    @Test
    public void testCreateConfiguration() throws Exception {
        try {
            String token = "123";
            Mockito.doNothing().when(configurationService).createConfiguration(Mockito.any(Configuration.class));
            String jsonBody =
                    """
                            {
                            "name": "test",
                            "startTime": "07:00",
                            "endTime": ""
                            }
                            """;
            mockMvc.perform(post("/configuration").content(jsonBody)
                            .contentType(MediaType.APPLICATION_JSON)
                            .characterEncoding("UTF-8"))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.status").value("201"))
                    .andExpect(jsonPath("$.message").value("La configuracion fue creada"))
                    .andExpect(jsonPath("$.data").doesNotExist());
        } catch (ScheduleManagementException e) {
            fail();
        }
    }


    @Test
    public void testGetConfigurationByName() throws Exception {
        Configuration configuration = new Configuration();
        configuration.setName("test");
        Mockito.when(configurationService.getConfigurationByName(Mockito.anyString())).thenReturn(configuration);

        mockMvc.perform(get("/configuration/name?name=test").content(""))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("Configuracion encontrada"))
                .andExpect(jsonPath("$.data.name").value("test"));
    }

}



