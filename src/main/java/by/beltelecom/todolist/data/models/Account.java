package by.beltelecom.todolist.data.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = "accounts")
@Getter @Setter
@NoArgsConstructor
@ToString
public class Account {

    @Id
    private long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(targetEntity = User.class)
    @MapsId
    @JoinColumn(name = "fk_owner")
    private User userAccount;

}
