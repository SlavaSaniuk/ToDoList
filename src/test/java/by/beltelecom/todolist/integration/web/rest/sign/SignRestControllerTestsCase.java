package by.beltelecom.todolist.integration.web.rest.sign;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.security.authentication.SignService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class SignRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestControllerTestsCase.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private SignService signService;

    @Autowired
    private ObjectMapper mapper;

    @Test
    @Rollback
    void logInAccount_accountEntityIsNotRegister_shouldReturnZero() throws Exception {
        Account accountToLogin = new Account();
        accountToLogin.setEmail("notfound@mail.com");
        accountToLogin.setPassword("1234QWERty");

        String accountJson = this.mapper.writeValueAsString(accountToLogin);
        LOGGER.info("Account JSON String: " +accountJson);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rest/sign/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(accountJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        long userId = this.mapper.readValue(responseJson, Long.class);
        Assertions.assertEquals(0L, userId);
    }

    @Test
    @Rollback
    void logInAccount_accountEntityIsRegister_shouldReturnAccountUserId() throws Exception {
        Account toRegister = new Account();
        toRegister.setEmail("test1@mail.com");
        toRegister.setPassword("1234QWERty");
        User userToRegister = new User();
        userToRegister.setName("testName");
        Account registered = this.signService.registerAccount(toRegister, userToRegister);
        Assertions.assertNotNull(registered);
        long expectedId = registered.getUserOwner().getId();
        Assertions.assertNotEquals(0L, expectedId);

        Account accountToLogin = new Account();
        accountToLogin.setEmail("test1@mail.com");
        accountToLogin.setPassword("1234QWERty");

        String accountJson = this.mapper.writeValueAsString(accountToLogin);
        LOGGER.info("Account JSON String: " +accountJson);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rest/sign/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(accountJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        long userId = this.mapper.readValue(responseJson, Long.class);
        Assertions.assertEquals(expectedId, userId);
    }
}
