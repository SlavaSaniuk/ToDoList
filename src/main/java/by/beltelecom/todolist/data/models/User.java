package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This model class represent a user of application.
 */
@Entity(name = "users")
@Table(name = "users")
@Getter @Setter
@NoArgsConstructor
@NamedEntityGraph(name = "User.tasks", attributeNodes = @NamedAttributeNode("tasks"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "userOwner")
    @JoinColumn
    private Account userAccount;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

}
