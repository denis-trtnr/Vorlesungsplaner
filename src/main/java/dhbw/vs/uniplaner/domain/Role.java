package dhbw.vs.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse beschreibt mögliche Rollen von Benutzern im System (z.B. Dozent, Admin, Student, etc.)
 */
@Entity
@Table(name = "role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "roleName")
    private String roleName;

    @Column(name = "roleUid")
    private String roleUid;

    /*
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "uni_user_id", referencedColumnName = "id")
    private UniUser uniUser;
*/
    @ManyToMany(mappedBy = "roles")
    private Set<UniUser> uniUsers = new HashSet<>();


    public Role(String roleName, String roleUid) {
        this.roleName = roleName;
        this.roleUid = roleUid;
    }

    public Role() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleUid() {
        return roleUid;
    }

    public void setRoleUid(String roleUid) {
        this.roleUid = roleUid;
    }

    public Set<UniUser> getUniUsers() {
        return uniUsers;
    }

    public void setUniUsers(Set<UniUser> uniUsers) {
        this.uniUsers = uniUsers;
    }

    public Role addUser(UniUser user) {
        this.uniUsers.add(user);
        user.getRoles().add(this);
        return this;
    }

    public Role removeUser(UniUser user) {
        this.uniUsers.remove(user);
        user.getRoles().remove(this);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Role)) {
            return false;
        }
        return id != null && id.equals(((Role) o).id);
    }


    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "Role{" +
            "id=" + getId() +
            ", roleName='" + getRoleName() + "'" +
            ", roleUid='" + getRoleUid() + "'" +
            "}";
    }
}
