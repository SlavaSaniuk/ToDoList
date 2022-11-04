package by.beltelecom.todolist.web.rest.info;

import by.beltelecom.todolist.services.system.datetime.ServerDateTimeService;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import by.beltelecom.todolist.utilities.logging.SpringLogging;
import by.beltelecom.todolist.web.dto.rest.DateTimeDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Info rest controller handle http requests on "/rest/info/**" urls to retrieve different info data.
 */
@RestController
@RequestMapping(value = "/rest/info/", produces = "application/json")
public class InfoRestController {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(InfoRestController.class);
    // Spring beans:
    private final ServerDateTimeService dateTimeService;

    /**
     * Construct new {@link InfoRestController} controller bean.
     * @param aServerDateTimeService - server date/time service.
     */
    public InfoRestController(ServerDateTimeService aServerDateTimeService) {
        // Check arguments:
        ArgumentChecker.nonNull(aServerDateTimeService, "aServerDateTimeService");

        LOGGER.debug(SpringLogging.Creation.createBean(InfoRestController.class));

        // Map arguments:
        this.dateTimeService = aServerDateTimeService;
    }

    /**
     * Get server date.
     * @return - {@link DateTimeDto} dto.
     */
    @GetMapping(value = "/server-date")
    public ResponseEntity<DateTimeDto> serverDate() {
        LOGGER.debug("Get server date;");
        return new ResponseEntity<>(DateTimeDto.ofDate(this.dateTimeService.getServerDate()), HttpStatus.OK);
    }
}
