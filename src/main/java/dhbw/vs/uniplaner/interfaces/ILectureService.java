package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.Lecture;

import dhbw.vs.uniplaner.domain.LectureDate;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.repository.LectureRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface ILectureService {

    public Lecture save(Lecture lecture);

    public void delete(Long id);

    public List<Lecture> findAll();

    public Optional<Lecture> findOne(Long id);

    public Lecture update(Lecture lecture);

    public ArrayList<Event> getEventsFromLectureDates(ArrayList<LectureDate> lectureDates);
}
