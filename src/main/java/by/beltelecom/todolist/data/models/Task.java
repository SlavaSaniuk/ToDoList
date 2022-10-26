package by.beltelecom.todolist.data.models;

import by.beltelecom.todolist.data.enums.TaskStatus;
import by.beltelecom.todolist.data.converter.TaskStatusConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Task entity object represent a user task.
 */
@Getter @Setter
@NoArgsConstructor
@Entity(name = "tasks")
@Table(name = "tasks")
public class Task {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id; // Task ID;
    @Column
    private String name; // Task name;
    @Column(length = 65535, columnDefinition = "TEXT")
    @Type(type = "text")
    private String description; // Task description;
    @Column(name = "created")
    private LocalDate dateCreation; // Date of task creation;
    @Column(name = "completion")
    private LocalDate dateCompletion; // Date of task completion;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "fk_owner", nullable = false)
    private User owner; // User owner;

    @Column(name = "status", nullable = false)
    @Convert(converter = TaskStatusConverter.class)
    private TaskStatus taskStatus; // Task status;

    @Override
    public String toString() {
        return String.format("Task[id: %d, name: %s, description: %s, created: %s, completion: %s, status: %s]",
                this.id, this.name, this.description, this.dateCreation, this.dateCompletion, this.taskStatus);
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

}
