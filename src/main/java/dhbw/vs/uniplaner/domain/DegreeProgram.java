package dhbw.vs.uniplaner.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse stellt einen Studiengang (z.B. Wirtschaftsinformatik) in der Hochschule dar.
 */
@Entity
@Table(name = "degree_program")
public class DegreeProgram implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "shortName")
    private String shortName;

    @OneToMany(mappedBy = "degreeProgram",cascade = CascadeType.ALL)
    private Set<Course> courses = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public DegreeProgram addCourse(Course course) {
        this.courses.add(course);
        course.setDegreeProgram(this);
        return this;
    }

    public DegreeProgram removeCourse(Course course) {
        this.courses.remove(course);
        course.setDegreeProgram(null);
        return this;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DegreeProgram)) {
            return false;
        }
        return id != null && id.equals(((DegreeProgram) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "DegreeProgram{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", shortName='" + getShortName() + "'" +
            "}";
    }
}
