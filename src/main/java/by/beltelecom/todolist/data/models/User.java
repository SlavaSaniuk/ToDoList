package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity(name = "users")
@Getter @Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String name;

    @OneToOne(targetEntity = Account.class, cascade = CascadeType.ALL, mappedBy = "userAccount")
    @JoinColumn
    private Account userAccount;

    @OneToMany(mappedBy = "userOwner", cascade = CascadeType.ALL)
    private List<Task> tasks;

}
