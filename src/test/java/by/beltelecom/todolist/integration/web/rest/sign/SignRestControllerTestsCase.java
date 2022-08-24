package by.beltelecom.todolist.integration.web.rest.sign;

import by.beltelecom.todolist.data.models.Account;
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
public class SignRestControllerTestsCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestControllerTestsCase.class);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void logInAccount_accountEntity_shouldReturnAccountUserId() throws Exception {
        Account account = new Account();
        account.setEmail("5");
        account.setPassword("111");

        String accountJson = this.mapper.writeValueAsString(account);
        LOGGER.info("Account JSON String: " +accountJson);

        MvcResult result = mvc.perform(MockMvcRequestBuilders.post("/rest/sign/login")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(accountJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        long userId = this.mapper.readValue(responseJson, Long.class);
        Assertions.assertEquals(1L, userId);

    }
}
