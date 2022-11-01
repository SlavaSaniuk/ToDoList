package by.beltelecom.todolist.data.models;

import by.beltelecom.todolist.data.converter.UserRoleConverter;
import by.beltelecom.todolist.data.enums.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "roles")
@Table(name = "roles")
@Getter @Setter
@NoArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "role_name", nullable = false)
    @Convert(converter = UserRoleConverter.class)
    private UserRole roleName; // Role name;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "ref_role_owner", nullable = false)
    private User roleOwner; // Role owner;

}
