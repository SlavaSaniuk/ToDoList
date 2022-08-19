package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Getter @Setter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "userOwner")
    @JoinColumn
    private Account userAccount;

    @OneToMany(mappedBy = "userOwner", targetEntity = Task.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Task> tasks;

}
