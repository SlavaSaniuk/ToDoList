package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.data.models.User;
import by.beltelecom.todolist.web.ExceptionStatusCodes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter @Setter
@NoArgsConstructor
public class UserRestDto extends ExceptionRestDto {

    private long userId;
    private String userName;

    public UserRestDto(User aUser) {
       // Map properties:
        this.userId = aUser.getId();
        this.userName = aUser.getName();
    }

    public UserRestDto(ExceptionStatusCodes anExceptionStatusCodes) {
        super(anExceptionStatusCodes);
    }

}
