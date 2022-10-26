package by.beltelecom.todolist.integration.web.rest.tasks;

import by.beltelecom.todolist.configuration.AuthenticationTestsConfiguration;
import by.beltelecom.todolist.configuration.bean.TestsUsersService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.services.tasks.TasksService;
import by.beltelecom.todolist.web.dto.rest.task.TasksListRestDto;
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
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@Import(AuthenticationTestsConfiguration.class)
public class TasksRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TasksRestControllerTestsCase.class); // Logger;

    // Spring beans:
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestsUsersService testsUsersService;
    @Autowired
    private TasksService tasksService;

    @BeforeEach
    void beforeEach() {
        this.testsUsersService.registerUser();
    }

    @AfterEach
    void afterEach() {
        this.testsUsersService.deleteUser();
    }

    @Test
    @Rollback
    void listUserTasks_userHasTasks_shouldReturnListOfUserTasks() throws Exception {
        // Create users tasks:
        Task task1 = TaskWrapper.Creator.createTask();
        task1.setName("Test task 1.");
        Task task2 = TaskWrapper.Creator.createTask();
        task2.setName("Test task 2.");
        this.tasksService.createTask(task1, this.testsUsersService.getTestUser().getUser());
        this.tasksService.createTask(task2, this.testsUsersService.getTestUser().getUser());

        // Create http request:
        long userId = this.testsUsersService.getTestUser().getUser().getId();
        String authorizationHeaderValue = this.testsUsersService.getTestUser().authorizationHeaderJwtValue();
        MvcResult httpResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get("/rest/tasks/" +userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", authorizationHeaderValue)
        ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Get response JSON:
        String responseJson = httpResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        // Cast JSON to DTO:
        TasksListRestDto dto = this.objectMapper.readValue(responseJson, TasksListRestDto.class);

        Assertions.assertNotNull(dto);
        Assertions.assertNotNull(dto.getTasksList());

        Assertions.assertNotEquals(0L, dto.getUserOwnerId());
        Assertions.assertFalse(dto.getTasksList().isEmpty());
        Assertions.assertEquals(2, dto.getTasksList().size());

        LOGGER.debug("User tasks:");
        dto.getTasksList().forEach((taskDto) -> LOGGER.debug(taskDto.toString()));


    }
}
