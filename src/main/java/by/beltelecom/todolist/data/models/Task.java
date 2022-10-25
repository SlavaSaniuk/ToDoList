package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

@Getter @Setter
@NoArgsConstructor
@Entity(name = "tasks")
@Table(name = "tasks")
public class Task implements Identification {

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

    @Column(name = "status")
    @Convert
    private TaskStatus taskStatus;

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
        return String.format("Task[id: %d, name: %s, description: %s, created: %s, completion: %s]",
                this.id, this.name, this.description, this.dateCreation, this.dateCompletion);
    }

    @Override
    public boolean equals(Object obj) {
        // Check at null
        if (obj == null) return false;

        // Check classes:
        if (obj.getClass() != this.getClass()) return false;
        Task otherTask = (Task) obj;

        // Check id:
        return otherTask.getId() == this.id;
    }

    @Override
    public Number getIdentifier() {
        return this.id;
    }
}
