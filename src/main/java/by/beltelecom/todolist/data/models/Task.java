package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {


    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "created")
    private Date dateCreation;
    @Column(name = "completion")
    private Date dateCompletion;

    public Task(long a_id) {
        this.id = a_id;
    }

    public static Task newTask() {
        Task task = new Task();
        task.setDateCreation(new Date());
        return task;
    }
}
