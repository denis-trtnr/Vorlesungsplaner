package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.DegreeProgram;

import dhbw.vs.uniplaner.interfaces.IDegreeProgramService;
import dhbw.vs.uniplaner.repository.DegreeProgramRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface IDegreeProgramService {

    public DegreeProgram save(DegreeProgram degreeprogram);

    public void delete(Long id);

    public List<DegreeProgram> findAll();

    public Optional<DegreeProgram> findOne(Long id);
}
