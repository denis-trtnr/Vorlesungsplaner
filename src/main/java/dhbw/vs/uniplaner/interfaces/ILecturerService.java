package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.Lecturer;

import dhbw.vs.uniplaner.interfaces.ILecturerService;
import dhbw.vs.uniplaner.repository.LecturerRepository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;



import java.util.List;
import java.util.Optional;

public interface ILecturerService {

    public Lecturer save(Lecturer lecturer);

    public void delete(Long id);

    public List<Lecturer> findAll();

    public Optional<Lecturer> findOne(Long id);
}
