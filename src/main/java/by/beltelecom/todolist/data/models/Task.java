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
@Entity
@Table(name = "tasks")
@ToString
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
    private User userOwner;

    public Task(long a_id) {
        this.id = a_id;
    }

    public static Task newTask() {
        Task task = new Task();
        task.setDateCreation(LocalDate.now());
        return task;
    }
}
