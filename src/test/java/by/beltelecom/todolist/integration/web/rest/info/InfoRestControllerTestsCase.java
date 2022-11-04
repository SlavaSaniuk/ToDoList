package by.beltelecom.todolist.integration.web.rest.info;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.web.dto.rest.DateTimeDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class InfoRestControllerTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoRestControllerTestsCase.class);
    // Spring beans:
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestsUserService testsUserService;

    @Test
    void serverDate_getServerDate_shouldReturnServerDateStr() throws Exception {
        // Create user:
        TestingUser testingUser = this.testsUserService.testingUser("serverDate");

        // Create web request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/info/server-date")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get created task:
        Assertions.assertNotNull(mvcResult);
        String responseJson = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotNull(responseJson);
        Assertions.assertFalse(responseJson.isEmpty());
        LOGGER.debug("Response JSON: " +responseJson);

        // Get response DTO:
        DateTimeDto dto = this.objectMapper.readValue(responseJson, DateTimeDto.class);
        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getServerDateStr());
        Assertions.assertFalse(dto.getServerDateStr().isEmpty());

        LOGGER.debug(String.format("Server date: %s;", dto.getServerDateStr()));
    }
}
