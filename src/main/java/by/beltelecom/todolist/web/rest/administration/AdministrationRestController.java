package by.beltelecom.todolist.web.rest.administration;

import by.beltelecom.todolist.data.models.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rest/administration/")
public class AdministrationRestController {

    @GetMapping(value = "/hello", produces = "text/html")
    public ResponseEntity<String> helloAdmin(@RequestAttribute("userObj") User userObj) {
        String helloString = String.format("Hello, administrator %s!", userObj.getName());
        return new ResponseEntity<>(helloString, HttpStatus.OK);
    }

}
