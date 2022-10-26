package by.beltelecom.todolist.data.converter;

import by.beltelecom.todolist.data.enums.TaskStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Convert {@link TaskStatus} enum value to int and otherwise.
 */
@Converter
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

    // Logger:
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskStatusConverter.class);

    @Override
    public Integer convertToDatabaseColumn(TaskStatus attribute) {
        if (attribute==null) return 0;

        return attribute.getStatusCode();
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer dbData) {
        try {
            return TaskStatus.of(dbData);
        }catch (IllegalArgumentException e) {
            LOGGER.warn(e.getMessage());
            return TaskStatus.WORKING;
        }
    }

}
