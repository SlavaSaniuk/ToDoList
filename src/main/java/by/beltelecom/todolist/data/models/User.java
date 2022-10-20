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
public class User implements Identification {

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

    @Override
    public String toString() {
        return String.format("User[id: %d, name: %s]", this.id, this.name);
    }

    @Override
    public boolean equals(Object obj) {
        // Check at null
        if (obj == null) return false;

        // Check classes:
        if (obj.getClass() != this.getClass()) return false;
        User otherUser = (User) obj;

        // Check id:
        return otherUser.getId() == this.id;
    }

    @Override
    public Number getIdentifier() {
        return this.id;
    }
}
