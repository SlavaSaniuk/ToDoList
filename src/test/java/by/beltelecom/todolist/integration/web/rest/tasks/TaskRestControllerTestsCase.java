package by.beltelecom.todolist.integration.web.rest.tasks;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.configuration.services.TestsTaskService;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.models.Task;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.wrappers.TaskWrapper;
import by.beltelecom.todolist.utilities.datetime.DateTimeUtilities;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.rest.ExceptionRestDto;
import by.beltelecom.todolist.web.dto.rest.task.TaskRestDto;
import by.beltelecom.todolist.web.dto.rest.task.TasksListRestDto;
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

import java.time.LocalDate;
import java.util.List;

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

    @Test
    void loadUserTasks_userHasNotTasks_shouldReturnDtoWithEmptyList() throws Exception {
        // Create user and task:
        TestingUser testingUser = this.testsUserService.testingUser("loadUserTask1");

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/task/" +testingUser.getUser().getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        TasksListRestDto restDto = this.objectMapper.readValue(responseJson, TasksListRestDto.class);
        Assertions.assertNotNull(restDto);
        Assertions.assertFalse(restDto.isException());
        Assertions.assertTrue(restDto.getTasksList().isEmpty());
    }

    @Test
    void loadUserTasks_userHasFiveTasks_shouldReturnDtoWithListOfFiveTasks() throws Exception {
        // Create user and task:
        TestingUser testingUser = this.testsUserService.testingUser("loadUserTask2");
        this.testsTaskService.testTasks(testingUser.getUser(), 5);

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/task/" +testingUser.getUser().getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        TasksListRestDto restDto = this.objectMapper.readValue(responseJson, TasksListRestDto.class);
        Assertions.assertNotNull(restDto);
        Assertions.assertFalse(restDto.isException());
        Assertions.assertFalse(restDto.getTasksList().isEmpty());
        Assertions.assertEquals(5, restDto.getTasksList().size());

    }

    @Test
    void loadUserTasks_userHasTenTaskWithDateOfCompletion_shouldReturnListOfUserTasksWithDateOfCompletion() throws Exception {
        // Create user and tasks:
        TestingUser testingUser = this.testsUserService.testingUser("loadUSerTask3");

        LocalDate dateOfCompletion = LocalDate.now().plusDays(3L);
        this.testsTaskService.builder().withWordsCountInName(6)
                .withDateOfCompletion(dateOfCompletion)
                .buildList(testingUser.getUser(), 10);

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/task/" +testingUser.getUser().getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug("Response JSON: " +responseJson);

        TasksListRestDto restDto = this.objectMapper.readValue(responseJson, TasksListRestDto.class);
        Assertions.assertNotNull(restDto);
        Assertions.assertFalse(restDto.isException());
        Assertions.assertFalse(restDto.getTasksList().isEmpty());
        Assertions.assertEquals(10, restDto.getTasksList().size());

       // Test date of creation and completion:
       restDto.getTasksList().forEach((taskRestDto -> {
           Assertions.assertNotNull(taskRestDto);
           Assertions.assertNotNull(taskRestDto.getDateOfCompletion());
           Assertions.assertFalse(taskRestDto.getDateOfCompletion().isEmpty());
           Assertions.assertEquals(DateTimeUtilities.dateToJsString(dateOfCompletion), taskRestDto.getDateOfCompletion());
           Assertions.assertNotNull(taskRestDto.getDateOfCreation());
           Assertions.assertFalse(taskRestDto.getDateOfCreation().isEmpty());
           Assertions.assertEquals(DateTimeUtilities.dateToJsString(LocalDate.now()), taskRestDto.getDateOfCreation());

           LOGGER.debug(String.format("TaskRestDto[name: %s, creation date: [%s], completion date: [%s]];",
                   taskRestDto.getTaskName(), taskRestDto.getDateOfCreation(), taskRestDto.getDateOfCompletion()));
       }));

    }

    @Test
    void updateUserTask_userTryToUpdateOwnExistedTask_shouldReturnTaskRestDto() throws Exception {
        // Generate user and task:
        TestingUser user = this.testsUserService.testingUser("updateUserTask1");
        Task task = this.testsTaskService.testTask(user.getUser());

        // Create modified task:
        String modifiedName = "modified name";
        String modifiedDesc = "modified desc";
        task.setName(modifiedName);
        task.setDescription(modifiedDesc);
        String reqJson = this.objectMapper.writeValueAsString(TaskRestDto.of(task));
        LOGGER.debug(String.format("Request JSON: %s;", reqJson));

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/task/update-task")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", user.authentication().getAuthorizationHeaderValue())
                .content(reqJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // Handle response:
        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug(String.format("Response JSON: %s;", responseJson));

        TaskRestDto taskRestDto = this.objectMapper.readValue(responseJson, TaskRestDto.class);
        Assertions.assertNotNull(taskRestDto);
        Task modifiedTask = taskRestDto.toEntity();
        Assertions.assertEquals(task, modifiedTask);
        Assertions.assertEquals(modifiedName, modifiedTask.getName());
        Assertions.assertEquals(modifiedDesc, modifiedTask.getDescription());

        LOGGER.debug(String.format("Modified task: %s;", modifiedTask));
    }

    @Test
    void updateUserTask_userTryToUpdateNotOwnExistedTask_shouldReturnTaskRestDtoWithExceptionCode604() throws Exception {
        // Generate user and task:
        TestingUser user = this.testsUserService.testingUser("updateUserTask2");
        User userOwner = this.testsUserService.testingUser("updateUserTask3").getUser();
        Task task = this.testsTaskService.testTask(userOwner);

        // Create modified task:
        String modifiedName = "modified name";
        String modifiedDesc = "modified desc";
        task.setName(modifiedName);
        task.setDescription(modifiedDesc);
        String reqJson = this.objectMapper.writeValueAsString(TaskRestDto.of(task));
        LOGGER.debug(String.format("Request JSON: %s;", reqJson));

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/task/update-task")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", user.authentication().getAuthorizationHeaderValue())
                .content(reqJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // Handle response:
        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug(String.format("Response JSON: %s;", responseJson));

        TaskRestDto taskRestDto = this.objectMapper.readValue(responseJson, TaskRestDto.class);
        Assertions.assertNotNull(taskRestDto);
        Assertions.assertTrue(taskRestDto.isException());
        Assertions.assertEquals(ExceptionStatusCodes.NOT_OWNER_EXCEPTION.getStatusCode(), taskRestDto.getExceptionCode());
    }

    @Test
    void updateUserTask_userTryToUpdateNotExistedTask_shouldReturnTaskRestDtoWithExceptionStatusCode669() throws Exception {
        // Generate user and task:
        TestingUser user = this.testsUserService.testingUser("updateUserTask4");
        Task task = TaskWrapper.Creator.createTask();

        // Create modified task:
        String modifiedName = "modified name";
        String modifiedDesc = "modified desc";
        task.setName(modifiedName);
        task.setDescription(modifiedDesc);
        String reqJson = this.objectMapper.writeValueAsString(TaskRestDto.of(task));
        LOGGER.debug(String.format("Request JSON: %s;", reqJson));

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/task/update-task")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", user.authentication().getAuthorizationHeaderValue())
                .content(reqJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // Handle response:
        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug(String.format("Response JSON: %s;", responseJson));

        TaskRestDto taskRestDto = this.objectMapper.readValue(responseJson, TaskRestDto.class);
        Assertions.assertNotNull(taskRestDto);
        Assertions.assertTrue(taskRestDto.isException());
        Assertions.assertEquals(ExceptionStatusCodes.NOT_FOUND_EXCEPTION.getStatusCode(), taskRestDto.getExceptionCode());

        LOGGER.debug(String.format("Exception message: %s;", taskRestDto.getExceptionMessage()));
    }

    @Test
    void completeUserTask_userTryToCompleteYourTask_shouldCompleteTask() throws Exception {
        // Generate user and task:
        TestingUser user = this.testsUserService.testingUser("completeUserTask1");
        Task task = this.testsTaskService.testTask(user.getUser());

        // Create request:
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/rest/task/complete-task?id=" +task.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", user.authentication().getAuthorizationHeaderValue())
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // Handle response:
        String responseJson = mvcResult.getResponse().getContentAsString();
        LOGGER.debug(String.format("Response JSON: %s;", responseJson));

        ExceptionRestDto exceptionRestDto = this.objectMapper.readValue(responseJson, ExceptionRestDto.class);
        Assertions.assertNotNull(exceptionRestDto);
        Assertions.assertFalse(exceptionRestDto.isException());
    }

    @Test
    void completeUserTasks_userTryToCompleteOwnTasks_shouldCompleteTasks() throws Exception {
        // Generate user and tasks:
        TestingUser user = this.testsUserService.testingUser("completeUserTasks1");
        List<Task> userTasks = this.testsTaskService.testTasks(user.getUser(), 6);

        // Create request DTO:
        TasksListRestDto tasksListRestDto = new TasksListRestDto(userTasks);
        String reqJson = this.objectMapper.writeValueAsString(tasksListRestDto);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/rest/task/complete-tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", user.authentication().getAuthorizationHeaderValue())
                .content(reqJson)
        ).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

        // Get response:
        String respJson = mvcResult.getResponse().getContentAsString();
        Assertions.assertNotNull(respJson);
        LOGGER.debug(String.format("Response JSOM: %s;", respJson));

        TasksListRestDto respDto = this.objectMapper.readValue(respJson, TasksListRestDto.class);
        Assertions.assertNotNull(respDto);
        Assertions.assertFalse(respDto.isException());

        // Print response:
        LOGGER.debug("Completed tasks: ");
        respDto.getTasksList().forEach((taskDto) -> LOGGER.debug(taskDto.toEntity().toString()));
    }
}
