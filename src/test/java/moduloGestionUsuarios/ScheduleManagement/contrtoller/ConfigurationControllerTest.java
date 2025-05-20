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
    public void testGetConfigurationInInterval() throws Exception {
        ConfigurationDTO configuration = new ConfigurationDTO();
        configuration.setName("test");
        configuration.setStartTime("07:00");
        configuration.setEndTime("08:00");
        List<ConfigurationDTO> configurations = new ArrayList<>();
        configurations.add(configuration);
        Mockito.when(configurationService.getConfigurationInInterval(Mockito.any(IntervalDTO.class))).thenReturn(configurations);
        String jsonBody = """
                {
                "startTime": "07:00",
                "endTime": "08:00"
                }
                """;

        mockMvc.perform(get("/configuration/interval").content(jsonBody).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("200"))
                .andExpect(jsonPath("$.message").value("Lista de configuraciones para el intervalo de 07:00 y 08:00"))
                .andExpect(jsonPath("$.data[0].id").doesNotExist()) // Verifica que id es null
                .andExpect(jsonPath("$.data[0].name").value("test")) // Verifica que el nombre sea "test"
                .andExpect(jsonPath("$.data[0].startTime").value("07:00")) // Verifica que startTime es "07:00"
                .andExpect(jsonPath("$.data[0].endTime").value("08:00")) // Verifica que endTime es "08:00"
                .andExpect(jsonPath("$.data[0].breakIntervals").doesNotExist()) // Verifica que breakIntervals es null
                .andExpect(jsonPath("$.data[0].attentionIntervals").doesNotExist());

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



