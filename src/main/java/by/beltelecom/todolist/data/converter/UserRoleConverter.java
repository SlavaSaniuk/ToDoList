package by.beltelecom.todolist.data.converter;

import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.data.enums.UserRole;
import by.beltelecom.todolist.utilities.ArgumentChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Converter
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(UserRoleConverter.class);

    @Override
    public String convertToDatabaseColumn(UserRole attribute) {
        ArgumentChecker.nonNull(attribute, "attribute");
        return attribute.getRoleName();
    }

    @Override
    public UserRole convertToEntityAttribute(String dbData) {
        Optional<UserRole> userRoleOptional = Arrays.stream(UserRole.values()).filter((role -> role.getRoleName().equals(dbData))).findFirst();
        try {
            return userRoleOptional.orElseThrow();
        }catch (NoSuchElementException e) {
            throw new IllegalArgumentException(String.format("User role of name[%s] is not exist.", dbData));
        }
    }
}
