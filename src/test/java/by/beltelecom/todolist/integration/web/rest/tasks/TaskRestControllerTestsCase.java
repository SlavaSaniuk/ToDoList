package by.beltelecom.todolist.integration.web.rest.tasks;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
import by.beltelecom.todolist.web.dto.rest.task.TaskRestDto;
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
public class TaskRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskRestControllerTestsCase.class);

    // Spring beans:
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestsUserService testsUserService;
    @Autowired
    private TestsTaskService testsTaskService;


    @Test
    void createTask_newTask_shouldCreateAndReturnNewTask() throws Exception {
        // Create user:
        TestingUser testingUser = this.testsUserService.testingUser("createTask1");
        // Generate JWT token:
        String JWT = testingUser.authentication().getJwt();
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

    @Test
    void deleteUserTask_taskIsExist_shouldReturnEmptyDto() throws Exception {
        // Create user and task:
        TestingUser testingUser = this.testsUserService.testingUser("deleteUserTask1");
        Task createdTask = this.testsTaskService.testTask(testingUser.getUser());

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/task/delete-task")
                                .param("id", String.valueOf(createdTask.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        ExceptionRestDto restDto = this.objectMapper.readValue(responseJson, ExceptionRestDto.class);
        Assertions.assertNotNull(restDto);
        Assertions.assertFalse(restDto.isException());
    }

    @Test
    void deleteUserTask_taskIsNotExist_shouldReturnExceptionDtoWithStatusCode669() throws Exception {
        // Create user and task:
        TestingUser testingUser = this.testsUserService.testingUser("deleteUserTask2");

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/task/delete-task")
                                .param("id", "333")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        ExceptionRestDto restDto = this.objectMapper.readValue(responseJson, ExceptionRestDto.class);
        Assertions.assertNotNull(restDto);
        Assertions.assertTrue(restDto.isException());
        Assertions.assertEquals(669, restDto.getExceptionCode());
    }

    @Test
    void deleteUserTask_userTryToDeleteNotOwnTask_shouldReturnExceptionDtoWithStatusCode604() throws Exception {
        // Create user and task:
        TestingUser testingUser = this.testsUserService.testingUser("deleteUserTask3");
        TestingUser testingUser2 = this.testsUserService.testingUser("deleteUserTask4");
        Task createdTask = this.testsTaskService.testTask(testingUser.getUser());


        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/task/delete-task")
                                .param("id", String.valueOf(createdTask.getId()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser2.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        ExceptionRestDto restDto = this.objectMapper.readValue(responseJson, ExceptionRestDto.class);
        Assertions.assertNotNull(restDto);
        Assertions.assertTrue(restDto.isException());
        Assertions.assertEquals(604, restDto.getExceptionCode());
        LOGGER.debug(restDto.getExceptionMessage());
    }
}
