package by.beltelecom.todolist.data.converter;

import by.beltelecom.todolist.data.models.TaskStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Optional;

@Converter
public class TaskStatusConverter implements AttributeConverter<TaskStatus, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TaskStatus attribute) {
        if (attribute==null) return 0;

        return attribute.getStatusCode();
    }

    @Override
    public TaskStatus convertToEntityAttribute(Integer dbData) {
        Optional<TaskStatus> taskStatusOpt = Arrays.stream(TaskStatus.values()).filter((status -> status.getStatusCode() == dbData)).findFirst();
        return taskStatusOpt.orElse(TaskStatus.WORKING);
    }

}
