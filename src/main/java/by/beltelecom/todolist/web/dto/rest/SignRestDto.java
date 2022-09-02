package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.web.ExceptionStatusCodes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * {@link SignRestDto} data transfer object used
 * in {@link by.beltelecom.todolist.web.rest.sign.SignRestController} REST controller.
 */
@Getter @Setter
@NoArgsConstructor
public class SignRestDto extends ExceptionRestDto {

    private long userId; // User identification;
    private String jwt; // JSON Web token;

    private SignRestDto(long aUserId) {
        super();
        this.userId = aUserId;
    }

    /**
     * Construct new {@link SignRestDto} DTO with userId and json web token.
     * @param aUserId - {@link Long} user identification.
     * @param aJwt - {@link String} json web token.
     */
    public SignRestDto(long aUserId, String aJwt) {
        this(aUserId);
        this.jwt = aJwt;
    }

    /**
     * Construct new {@link SignRestDto} DTO with exception status code.
     * @param anExceptionStatusCodes - {@link ExceptionStatusCodes} exception status code.
     */
    public SignRestDto(ExceptionStatusCodes anExceptionStatusCodes) {
        super(anExceptionStatusCodes);
    }
}
