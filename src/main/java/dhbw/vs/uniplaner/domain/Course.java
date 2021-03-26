package dhbw.vs.uniplaner.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Klasse stellt Kurs (z.B. WWI2020D) in der Hochschule dar.
 */
@Entity
@Table(name = "course")
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "courseName")
    private String courseName;

    @Column(name = "startDate")
    private LocalDate startDate;

    @Column(name = "endDate")
    private LocalDate endDate;

    @OneToMany
    @JoinTable(name = "course_lecture",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id", referencedColumnName = "id"))
    private Set<Lecture> lectures = new HashSet<>();

    @OneToMany
    @JoinTable(name = "course_semester",
            joinColumns = @JoinColumn(name = "course_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "semester_id", referencedColumnName = "id"))
    private Set<Semester> semesters = new HashSet<>();

    @ManyToOne
    private DegreeProgram degreeProgram;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Set<Lecture> getLectures() {
        return lectures;
    }

    public Course addLecture(Lecture lecture) {
        this.lectures.add(lecture);
        lecture.setCourse(this);
        return this;
    }

    public Course removeLecture(Lecture lecture) {
        this.lectures.remove(lecture);
        lecture.setCourse(null);
        return this;
    }

    public void setLectures(Set<Lecture> lectures) {
        this.lectures = lectures;
    }

    public Set<Semester> getSemesters() {
        return semesters;
    }

    public Course addSemester(Semester semester) {
        this.semesters.add(semester);
        semester.setCourse(this);
        return this;
    }

    public Course removeSemester(Semester semester) {
        this.semesters.remove(semester);
        semester.setCourse(null);
        return this;
    }

    public void setSemesters(Set<Semester> semesters) {
        this.semesters = semesters;
    }

    public DegreeProgram getDegreeProgram() {
        return degreeProgram;
    }

    public void setDegreeProgram(DegreeProgram degreeProgram) {
        this.degreeProgram = degreeProgram;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        return id != null && id.equals(((Course) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "Course{" +
            "id=" + getId() +
            ", courseName='" + getCourseName() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            "}";
    }

    public void setStartingYear(long l) {
    }
}