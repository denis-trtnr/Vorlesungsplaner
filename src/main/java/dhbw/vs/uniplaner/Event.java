package dhbw.vs.uniplaner;

import java.time.LocalDateTime;

public class Event {
    private Long id;
    private String title;
    private LocalDateTime start;
    private LocalDateTime end;

    public Event() {

    }

    public Event(Long id, String title, LocalDateTime start, LocalDateTime end) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id;}

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
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
}
