package dhbw.vs.uniplaner.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A UniUser.
 */
@Entity
@Table(name = "uni_user")
public class UniUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "shibboleth_id")
    private String shibbolethId;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "uni_user_role",
               joinColumns = @JoinColumn(name = "uni_user_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    /*
    @OneToMany
    private Set<Role> roles = new HashSet<>();
*/
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public UniUser firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public UniUser lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getShibbolethId() {
        return shibbolethId;
    }

    public UniUser shibbolethId(String shibbolethId) {
        this.shibbolethId = shibbolethId;
        return this;
    }

    public void setShibbolethId(String shibbolethId) {
        this.shibbolethId = shibbolethId;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UniUser roles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public UniUser addRole(Role role) {
        this.roles.add(role);
        role.getUniUsers().add(this);
        return this;
    }

    public UniUser removeRole(Role role) {
        this.roles.remove(role);
        role.getUniUsers().remove(this);
        return this;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UniUser)) {
            return false;
        }
        return id != null && id.equals(((UniUser) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "UniUser{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", password='"+ getPassword() + "'" +
            ", mail='"+ getEmail() + "'" +
            ", shibbolethId='" + getShibbolethId() + "'" +
            "}";
    }
}
