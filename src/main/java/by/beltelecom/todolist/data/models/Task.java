package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "tasks")
@Table(name = "tasks")
public class Task {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String name;
    @Column(length = 65535, columnDefinition = "TEXT")
    @Type(type = "text")
    private String description;
    @Column(name = "created")
    private LocalDate dateCreation;
    @Column(name = "completion")
    private LocalDate dateCompletion;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_owner", nullable = false)
    private User owner;

    public Task(long a_id) {
        this.id = a_id;
    }

    public static Task newTask() {
        Task task = new Task();
        task.setDateCreation(LocalDate.now());
        return task;
    }

    @Override
    public String toString() {
        return String.format("Task[name: %s, description: %s, created: %s, completion: %s];",
                this.name, this.description, this.dateCreation, this.dateCompletion);
    }
}
