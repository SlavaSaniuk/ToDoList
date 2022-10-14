package by.beltelecom.todolist.integration.web.rest.tasks;

import by.beltelecom.todolist.configuration.bean.TestUser;
import by.beltelecom.todolist.configuration.bean.TestsUsersService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.security.rest.jwt.JsonWebTokenService;
import by.beltelecom.todolist.web.dto.rest.task.TaskRestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestsUsersService.class)
public class TaskRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestControllerTestsCase.class);

    // Spring beans:
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestsUsersService testsUsersService;
    @Autowired
    private JsonWebTokenService jsonWebTokenService;

    private TestUser testUser;

    @BeforeEach
    void beforeEach() {
        this.testUser = testsUsersService.registerUser();
    }

    @AfterEach
    void afterEach() {
        this.testsUsersService.deleteUser(testUser);
        this.testUser = null;
    }

    @Test
    void createTask_newTask_shouldCreateAndReturnNewTask() throws Exception {
        // Generate JWT token:
        String JWT = this.jsonWebTokenService.generateToken(this.testUser.getAccount().getEmail());
        LOGGER.debug("Generated JWT: " +JWT);

        // Create task dto:
        Task task = new Task();
        task.setName("Test task name!");
        TaskRestDto taskRestDto = new TaskRestDto(task);
        String requestBody = this.objectMapper.writeValueAsString(taskRestDto);
        LOGGER.debug(String.format("Request body[%s];", requestBody));

        // Create web request:
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post("/rest/task/create-task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Beaver ".concat(JWT))
                        .content(requestBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get created task:
        Assertions.assertNotNull(mvcResult);
        String responseJson = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotNull(responseJson);
        Assertions.assertFalse(responseJson.isEmpty());
        LOGGER.debug("Response JSON: " +responseJson);

        // Get response DTO:
        TaskRestDto responseDto = this.objectMapper.readValue(responseJson, TaskRestDto.class);
        Assertions.assertNotNull(responseDto);

        // Get created task:
        Task createdTask = responseDto.toEntity();
        Assertions.assertNotNull(createdTask);
        Assertions.assertNotEquals(0L, createdTask.getId());
        Assertions.assertEquals(task.getName(), createdTask.getName());
        LOGGER.debug("Created task: " +createdTask);
    }
}
