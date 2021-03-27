package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.Course;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.LectureDate;

import dhbw.vs.uniplaner.interfaces.ILectureDateService;
import dhbw.vs.uniplaner.repository.LectureDateRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ILectureDateService {

    public LectureDate save(LectureDate lecturedate);

    public void delete(Long id);

    public List<LectureDate> findAll();

    public Optional<LectureDate> findOne(Long id);

    public LectureDate update(LectureDate lectureDate);
}
