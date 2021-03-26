package dhbw.vs.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasse stellt eine Vorlesung dar.
 */
@Entity
@Table(name = "lecture")
public class Lecture implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lectureName")
    private String lectureName;

    @Column(name = "modulName")
    private String modulName;

    @Column(name = "duration")
    private Long duration;

    @ManyToMany
    @JoinTable(name = "lecture_lectureDates",
            joinColumns = @JoinColumn(name = "lecture_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "lectureDates_id", referencedColumnName = "id"))
    private Set<LectureDate> lectureDates = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "lecture_lecturer",
            joinColumns = @JoinColumn(name = "lecture_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "lecturer_id", referencedColumnName = "id"))
    private Set<Lecturer> lecturers = new HashSet<>();

    @ManyToOne
    private Course course;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLectureName() {
        return lectureName;
    }

    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

    public String getModulName() {
        return modulName;
    }

    public void setModulName(String modulName) {
        this.modulName = modulName;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Set<LectureDate> getLectureDates() {
        return lectureDates;
    }

    public Lecture addLectureDate(LectureDate lectureDate) {
        this.lectureDates.add(lectureDate);
        lectureDate.setLecture(this);
        return this;
    }

    public Lecture removeLectureDate(LectureDate lectureDate) {
        this.lectureDates.remove(lectureDate);
        lectureDate.setLecture(null);
        return this;
    }

    public void setLectureDates(Set<LectureDate> lectureDates) {
        this.lectureDates = lectureDates;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public Lecture addLecturer(Lecturer lecturer) {
        this.lecturers.add(lecturer);
        lecturer.getLectures().add(this);
        return this;
    }

    public Lecture removeLecturer(Lecturer lecturer) {
        this.lecturers.remove(lecturer);
        lecturer.getLectures().remove(this);
        return this;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Lecture)) {
            return false;
        }
        return id != null && id.equals(((Lecture) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "Lecture{" +
            "id=" + getId() +
            ", lectureName='" + getLectureName() + "'" +
            ", modulName='" + getModulName() + "'" +
            ", duration=" + getDuration() +
            "}";
    }
}
