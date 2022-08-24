package by.beltelecom.todolist.web.rest.sign;

import by.beltelecom.todolist.data.models.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/rest/sign", produces = "application/json")
@CrossOrigin(origins = "*")
public class SignRestController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignRestController.class);

    @PostMapping(value = "/login", consumes = "application/json")
    public long logInAccount(@RequestBody Account account) {

        LOGGER.debug(account.toString());

        return 1L;
    }


}
