package by.beltelecom.todolist.web.controllers.security;

import by.beltelecom.todolist.data.models.Account;
import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.exceptions.AccountAlreadyRegisteredException;
import by.beltelecom.todolist.security.authentication.SignService;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.dto.AccountUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * HTTP controller endpoint used to register and authenticate http request's.
 * For get sign page(sign.html) use {@link SignController#getSignPage()} method.
 */
@Controller
public class SignController {

    private final SignService signService; // Security service bean (mapped in constructor);
    private static final Logger LOGGER = LoggerFactory.getLogger(SignController.class); // Logger;

    /**
     * Construct new {@link SignController} controller bean.
     * @param aSignService - {@link SignService} service bean.
     */
    @Autowired
    public SignController(SignService aSignService) {
        LOGGER.debug(SpringLogging.Creation.createBean(SignController.class));
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

    /**
     * Method user to register new account and create new user entity. If account successfully registered,
     * method redirect request to user's page.
     * @param dto - {@link AccountUserDto} dto;
     * @param redirectAttributes - Spring redirect attributes;
     * @return - String that redirect request to users page.
     */
    @PostMapping("/sign/register-account")
    public String registerAccount(@ModelAttribute(name="dto") AccountUserDto dto, RedirectAttributes redirectAttributes) {
        // Get user and account objects:
        User user = dto.toUser();
        Account account = dto.toEntity();

        // Try to register account:
        try {
            this.signService.registerAccount(account, user);
        }catch (AccountAlreadyRegisteredException exc) {
            LOGGER.warn(exc.getMessage());
        }

        // Try to log in account:
        redirectAttributes.addFlashAttribute("dto", dto);

        return "redirect:/sign/login-account";
    }

    /**
     * Method used to authenticate account in application. If account successfully authenticated,
     * method redirect request to user's page.
     * @param dto - {@link AccountUserDto} DTO;
     * @return - String that redirect to users page.
     */
    @RequestMapping(value = "/sign/login-account", method = {RequestMethod.GET, RequestMethod.POST})
    public String loginAccount(@ModelAttribute(name = "dto") AccountUserDto dto, HttpSession httpSession) {
        LOGGER.debug("Try to login account.");

        // Get account object from dto:
        Account account = dto.toEntity();

        // Try to log in account:
        try {
            account = this.signService.loginAccount(account);
        }catch (BadCredentialsException exc) {
            LOGGER.warn(exc.getMessage());
        }

        // Put user object in session:
        httpSession.setAttribute("userObj", account.getUserOwner());

        return "redirect:/user/" +account.getUserOwner().getId();
    }

}
