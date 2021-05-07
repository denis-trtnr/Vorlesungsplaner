package dhbw.vs.uniplaner.service;

import dhbw.vs.uniplaner.Event;
import dhbw.vs.uniplaner.domain.Lecture;
import dhbw.vs.uniplaner.domain.LectureDate;
import dhbw.vs.uniplaner.domain.Lecturer;
import dhbw.vs.uniplaner.interfaces.ILectureService;
import dhbw.vs.uniplaner.repository.LectureDateRepository;
import dhbw.vs.uniplaner.repository.LectureRepository;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class LectureService implements ILectureService {
    @Autowired
    private LectureDateService lectureDateService;
    @Autowired
    private LecturerService lecturerService;

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
        Optional<Lecture> lectureToDelete = lectureRepository.findById(id);
        Lecture lecture = lectureToDelete.orElseThrow(RuntimeException::new);
        Set<LectureDate> lectureDates = lecture.getLectureDates();
        List<LectureDate> lectureDatesList = new ArrayList<>(lectureDates);
        Set<Lecturer> lecturers = lecture.getLecturers();
        List<Lecturer> lecturersList = new ArrayList<>(lecturers);
        for (int i = 0; i < lectureDatesList.size(); i++) {
            lecture.removeLectureDate(lectureDatesList.get(i));
            lectureDateService.update(lectureDatesList.get(i));
        }
        for (int i = 0; i < lecturersList.size(); i++) {
            lecture.removeLecturer(lecturersList.get(i));
            lecturerService.update(lecturersList.get(i));
        }
        update(lecture);
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

    public ArrayList<Event> convertLectureDatesToEvents(ArrayList<LectureDate> lectureDates) {
        ArrayList<Event> calendarEvents = new ArrayList<>();
        for (LectureDate lectureDate : lectureDates) {
            Event event = new Event();
            event.setId(lectureDate.getId());
            event.setTitle(findOne(lectureDate.getLecture().getId()).get().getLectureName());
            event.setStart(lectureDate.getStart());
            event.setEnd(lectureDate.getEnd());
            calendarEvents.add(event);
        }
        return calendarEvents;
    }
}
