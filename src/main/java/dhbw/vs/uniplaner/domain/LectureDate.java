package dhbw.vs.uniplaner.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Die Klasse stellt einen Vorlesungstermin dar.
 */
@Entity
@Table(name = "lecture_date")
public class LectureDate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start")
    private LocalDateTime start;

    @Column(name = "end")
    private LocalDateTime end;

    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="lecturers_id")
    private Lecturer lecturers;
    */
    @ManyToMany(mappedBy = "lectureDates")
    private Set<Lecturer> lecturers = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="title")
    private Lecture title;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public void setStart(LocalDateTime start) {
        this.start = start;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public Set<Lecturer> getLecturers() {
        return lecturers;
    }

    public void setLecturers(Set<Lecturer> lecturers) {
        this.lecturers = lecturers;
    }

    public Lecture getLecture() {
        return title;
    }

    public void setLecture(Lecture title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LectureDate)) {
            return false;
        }
        return id != null && id.equals(((LectureDate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }


    @Override
    public String toString() {
        return "LectureDate{" +
            "id=" + getId() +
            ", startDate='" + getStart() + "'" +
            ", endDate='" + getEnd() + "'" +
            "}";
    }

}
