package dhbw.vs.uniplaner.service;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.LectureDate;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.repository.LectureRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LectureService implements ILectureService {

    Logger logger = LoggerFactory.getLogger(LectureService.class);
    private final LectureRepository lectureRepository;

    public LectureService(LectureRepository lectureRepository) {
        this.lectureRepository = lectureRepository;
    }

    @Override
    public Lecture save(Lecture lecture) {
        logger.debug("Request to save Lecture {}", lecture);
        return lectureRepository.save(lecture);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Lecture {}", id);
        lectureRepository.deleteById(id);
    }

    @Override
    public List<Lecture> findAll() {
        logger.debug("Request to get all Lecture");
        return lectureRepository.findAll();
    }

    @Override
    public Optional<Lecture> findOne(Long id) {
        logger.debug("Request to find Lecture {}", id);
        return lectureRepository.findById(id);
    }

    @Override
    public Lecture update(Lecture lecture) {
        logger.debug("Request to update Lecture {}",lecture.getId());
        Lecture savedLecture = lectureRepository.findById(lecture.getId()).orElseThrow(() -> new ResourceNotFoundException());
        return lectureRepository.save(savedLecture);
    }

    public List<Event> createEventsfromLectureDates(List<LectureDate> lectureDates) {
        List<Event> calendarEvents = new ArrayList<>();
        for (LectureDate lectureDate : lectureDates) {
            Event event = new Event();
            event.setTitle(findOne(lectureDate.getTitle().getId()).get().getLectureName());
            event.setStart(lectureDate.getStart().toString());
            event.setEnd(lectureDate.getEnd().toString());
            calendarEvents.add(event);
        }
        return calendarEvents;
    }
}
