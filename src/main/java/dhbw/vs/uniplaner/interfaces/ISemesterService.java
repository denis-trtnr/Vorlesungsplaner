package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.Semester;

import dhbw.vs.uniplaner.interfaces.ISemesterService;
import dhbw.vs.uniplaner.repository.SemesterRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ISemesterService {

    public Semester save(Semester semester);

    public void delete(Long id);

    public List<Semester> findAll();

    public Optional<Semester> findOne(Long id);
}
