package by.beltelecom.todolist.integration.web.rest.administration;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.services.security.role.RoleService;
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
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class AdministrationRestControllerTestsCase {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(AdministrationRestControllerTestsCase.class);

    // Spring beans:
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestsUserService testsUserService;
    @Autowired
    private RoleService roleService;

    @Test
    void helloAdmin_userDoesNotHasAdminRoles_shouldReturnResponseWithErrorCode403() throws Exception {
       // Create user:
        TestingUser user = this.testsUserService.testingUser("helloAdmin1");

        // Create http request:
        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/administration/hello")
                                .header("Authorization", user.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isForbidden()).andDo(MockMvcResultHandlers.print());

    }

    @Test
    void helloAdmin_userHasAdminRole_shouldProcessRequestAndReturnHelloString() throws Exception {
        // Create user:
        TestingUser user = this.testsUserService.testingUser("helloAdmin2");

        // Set admin role to user:
        this.roleService.addRoleToUser(UserRole.ROLE_ROOT_ADMIN, user.getUser());

        // Create http request:
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/administration/hello")
                                .accept("text/html")
                                .header("Authorization", user.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

        String responseText = result.getResponse().getContentAsString();
        Assertions.assertNotNull(responseText);
        Assertions.assertFalse(responseText.isEmpty());
        LOGGER.debug(String.format("Response TEXT: %s;", responseText));


    }
}
