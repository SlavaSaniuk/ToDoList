package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
public class Task {

    private long id;
    private String name;
    private String description;
    private Date dateCreation;
    private Date dateCompletion;

    public Task(long a_id) {
        this.id = a_id;
    }

}
