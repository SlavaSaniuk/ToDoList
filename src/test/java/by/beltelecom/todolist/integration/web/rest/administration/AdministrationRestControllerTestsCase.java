package by.beltelecom.todolist.integration.web.rest.administration;

import by.beltelecom.todolist.configuration.models.TestingUser;
import by.beltelecom.todolist.configuration.services.TestsUserService;
import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.exceptions.NotFoundException;
import by.beltelecom.todolist.services.security.role.RoleService;
import com.fasterxml.jackson.core.type.TypeReference;
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

import java.util.List;

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
        // Create admin user:
        TestingUser user = this.testsUserService.testingRootAdmin("helloAdmin2");

        // Set admin role to user:
        //this.roleService.addRoleToUser(UserRole.ROLE_ROOT_ADMIN, user.getUser());

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

    @Test
    void userRoles_userHas4Roles_shouldReturnListWith4UserRoles() throws Exception {
        // Create root admin (role +1):
        TestingUser testingUser = this.testsUserService.testingRootAdmin("userRoles1");

        // Add roles to user (role: 1 +3):
        try {
            this.roleService.addRoleToUser(UserRole.ROLE_USER, testingUser.getUser());
            this.roleService.addRoleToUser(UserRole.ROLE_AUTHENTICATED_USER, testingUser.getUser());
            this.roleService.addRoleToUser(UserRole.ROLE_ADMIN, testingUser.getUser());
        } catch (NotFoundException e) {
            Assertions.fail();
        }

        // Create http request:
        MvcResult result = this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/rest/administration/user-roles")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept("application/json")
                                .header("Authorization", testingUser.authentication().getAuthorizationHeaderValue()))
                .andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print()).andReturn();

        // Get result JSON:
        String responseJson = result.getResponse().getContentAsString();
        LOGGER.debug(String.format("Response JSON: %s;", responseJson));

        // Map JSON to Object:
        List<UserRole> listOfUserRoles = this.objectMapper.readValue(responseJson, new TypeReference<>() {
        });
        Assertions.assertNotNull(listOfUserRoles);
        Assertions.assertFalse(listOfUserRoles.isEmpty());
        Assertions.assertEquals(4, listOfUserRoles.size());
    }
}
