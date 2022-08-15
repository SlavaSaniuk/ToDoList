package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@Entity
@Table(name = "tasks")
@ToString
public class Task {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column(name = "created")
    private LocalDate dateCreation;
    @Column(name = "completion")
    private LocalDate dateCompletion;

    public Task(long a_id) {
        this.id = a_id;
    }

    public static Task newTask() {
        Task task = new Task();
        task.setDateCreation(LocalDate.now());
        return task;
    }
}
