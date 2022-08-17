package by.beltelecom.todolist.web.controllers.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import by.beltelecom.todolist.services.security.SignService;
import by.beltelecom.todolist.web.dto.AccountUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SignController {

    private SignService signService; // Security service bean (mapped in constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class);

    @Autowired
    public SignController(SignService aSignService) {
        this.signService = aSignService;
    }

    /**
     * Controller method used to get sign html page.
     * @return - {@link ModelAndView} Sign.html page
     */
    @GetMapping("/sign")
    public ModelAndView getSignPage() {
        ModelAndView mav = new ModelAndView("sign");
        mav.addObject("dto", new AccountUserDto(new Account(), new User()));

        return mav;
    }

    @PostMapping("/sign/register-account")
    public String registerAccount(@ModelAttribute(name="dto") AccountUserDto dto) {
        // Get user and account objects:
        User user = dto.toUser();
        Account account = dto.toEntity();

        // Try to register account
        Account registeredAccount = null;
        try {
            registeredAccount = this.signService.registerAccount(account, user);
        }catch (AccountAlreadyRegisteredException exc) {
            LOGGER.warn(exc.getMessage());
        }

        return "redirect:/";
    }
}
