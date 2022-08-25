package by.beltelecom.todolist.web.dto.rest;

import by.beltelecom.todolist.web.ExceptionStatusCodes;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class SignRestDto extends ExceptionRestDto {

    private long userId;

    public SignRestDto(long aUserId) {
        super();
        this.userId = aUserId;
    }

   public SignRestDto(ExceptionStatusCodes anExceptionStatusCodes) {
        super(anExceptionStatusCodes);
   }
}
