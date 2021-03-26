package dhbw.vs.uniplaner.service;


import dhbw.vs.uniplaner.interfaces.ISemesterService;
import dhbw.vs.uniplaner.repository.SemesterRepository;
import dhbw.vs.uniplaner.domain.Semester;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SemesterService implements ISemesterService {

    Logger logger = LoggerFactory.getLogger(SemesterService.class);

    private final SemesterRepository semesterRepository;

    public SemesterService(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public Semester save(Semester semester) {
        logger.debug("Request to save Semester {}", semester);
        return semesterRepository.save(semester);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Semester {}", id);
        semesterRepository.deleteById(id);
    }

    @Override
    public List<Semester> findAll() {
        logger.debug("Request to get all Semester");
        return semesterRepository.findAll();
    }

    @Override
    public Optional<Semester> findOne(Long id) {
        logger.debug("Request to find Semester {}", id);
        return semesterRepository.findById(id);
    }
}
