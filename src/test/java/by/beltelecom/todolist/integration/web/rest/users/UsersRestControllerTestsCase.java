package by.beltelecom.todolist.integration.web.rest.users;

import by.beltelecom.todolist.configuration.bean.TestUser;
import by.beltelecom.todolist.configuration.bean.TestsUsersService;
import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.integration.web.rest.sign.SignRestControllerTestsCase;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import by.beltelecom.todolist.web.dto.rest.UserRestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties(SecurityProperties.class)
@Import(TestsUsersService.class)
public class UsersRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestControllerTestsCase.class);

    @Autowired
    private TestsUsersService testsUsersService;

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    @Rollback
    void userById_userNotFound_shouldReturnDtoWithExceptionStatusCode669() throws Exception {
        TestUser testUser = this.testsUsersService.registerUser();
        long unexpectedId = 5678945;

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/rest/users/" +unexpectedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Beaver " +testUser.getJwtToken())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +resultJson);

        UserRestDto userRestDto = this.mapper.readValue(resultJson, UserRestDto.class);

        Assertions.assertNotNull(userRestDto);
        Assertions.assertTrue(userRestDto.isException());
        Assertions.assertEquals(ExceptionStatusCodes.NOT_FOUND_EXCEPTION.getStatusCode(), userRestDto.getExceptionCode());

        this.testsUsersService.deleteUser(testUser);
    }

    @Test
    @Rollback
    void userById_userIsFound_shouldReturnDtoWithUserIdAndName() throws Exception {

        TestUser testUser = this.testsUsersService.registerUser();

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/rest/users/" +testUser.getUser().getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Beaver " +testUser.getJwtToken())
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +resultJson);

        UserRestDto userRestDto = this.mapper.readValue(resultJson, UserRestDto.class);

        Assertions.assertNotNull(userRestDto);
        Assertions.assertFalse(userRestDto.isException());
        Assertions.assertNotEquals(0L, userRestDto.getUserId());
        Assertions.assertNotNull(userRestDto.getUserName());

        this.testsUsersService.deleteUser(testUser);
    }

}
