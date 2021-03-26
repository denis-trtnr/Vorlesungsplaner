package dhbw.vs.uniplaner.service;


import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.repository.DegreeProgramRepository;
import dhbw.vs.uniplaner.domain.DegreeProgram;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DegreeProgramService implements IDegreeProgramService {

    Logger logger = LoggerFactory.getLogger(DegreeProgramService.class);

    private final DegreeProgramRepository degreeprogramRepository;

    public DegreeProgramService(DegreeProgramRepository degreeprogramRepository) {
        this.degreeprogramRepository = degreeprogramRepository;
    }

    @Override
    public DegreeProgram save(DegreeProgram degreeprogram) {
        logger.debug("Request to save DegreeProgram {}", degreeprogram);
        return degreeprogramRepository.save(degreeprogram);
    }


    @Override
    public void delete(Long id) {
        logger.debug("Request to delete DegreeProgram {}", id);
        degreeprogramRepository.deleteById(id);
    }

    @Override
    public List<DegreeProgram> findAll() {
        logger.debug("Request to get all DegreeProgram");
        return degreeprogramRepository.findAll();
    }

    @Override
    public Optional<DegreeProgram> findOne(Long id) {
        logger.debug("Request to find DegreeProgram {}", id);
        return degreeprogramRepository.findById(id);
    }
}
