package by.beltelecom.todolist.integration.web.rest.users;

import by.beltelecom.todolist.configuration.properties.SecurityProperties;
import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.data.repositories.AccountsRepository;
import by.beltelecom.todolist.integration.web.rest.sign.SignRestControllerTestsCase;
import by.beltelecom.todolist.security.authentication.SignService;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@EnableConfigurationProperties(SecurityProperties.class)
public class UsersRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestControllerTestsCase.class);

    private static long incr = 6000;
    @Autowired
    private MockMvc mvc;

    @Autowired
    private SignService signService;

    @Autowired
    private AccountsRepository accountsRepository;

    @Autowired
    private ObjectMapper mapper;

    private User registeredUser;

    @BeforeEach
    void beforeEach() {
        Account account = new Account();
        account.setEmail(String.format("test%d@mail.com", UsersRestControllerTestsCase.incr));
        account.setPassword("!1AaBbCcDd");
        User user = new User();
        user.setName("test60");
        account = this.signService.registerAccount(account, user);
        this.registeredUser = account.getUserOwner();
        UsersRestControllerTestsCase.incr++;
    }

    @AfterEach
    void afterEach() {
        this.accountsRepository.deleteAll();
    }

    @Test
    @Rollback
    @WithMockUser("test6000@mail.com")
    void userById_userNotFound_shouldReturnDtoWithExceptionStatusCode669() throws Exception {
        long unexpectedId = 5678945;

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/users/" +unexpectedId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +resultJson);

        UserRestDto userRestDto = this.mapper.readValue(resultJson, UserRestDto.class);

        Assertions.assertNotNull(userRestDto);
        Assertions.assertTrue(userRestDto.isException());
        Assertions.assertEquals(ExceptionStatusCodes.NOT_FOUND_EXCEPTION.getStatusCode(), userRestDto.getExceptionCode());
    }

    @Test
    @Rollback
    @WithMockUser("test6000@mail.com")
    void userById_userIsFound_shouldReturnDtoWithUserIdAndName() throws Exception {

        MvcResult result = this.mvc.perform(MockMvcRequestBuilders.get("/users/" +this.registeredUser.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String resultJson = result.getResponse().getContentAsString();
        LOGGER.debug("Result JSON: " +resultJson);

        UserRestDto userRestDto = this.mapper.readValue(resultJson, UserRestDto.class);

        Assertions.assertNotNull(userRestDto);
        Assertions.assertFalse(userRestDto.isException());
        Assertions.assertNotEquals(0L, userRestDto.getUserId());
        Assertions.assertNotNull(userRestDto.getUserName());
    }

}
