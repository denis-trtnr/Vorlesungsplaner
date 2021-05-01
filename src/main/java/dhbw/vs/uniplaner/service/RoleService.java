package dhbw.vs.uniplaner.service;


import dhbw.vs.uniplaner.domain.Role;
import dhbw.vs.uniplaner.interfaces.IRoleService;
import dhbw.vs.uniplaner.repository.RoleRepository;
import dhbw.vs.uniplaner.domain.Role;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService implements IRoleService {

    Role student = new Role("ROLE_USER","1");
    Role dozent = new Role("ROLE_LECTURER","2");
    Role admin = new Role("ROLE_ADMIN","3");

    Logger logger = LoggerFactory.getLogger(RoleService.class);

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role save(Role role) {
        logger.debug("Request to save Role {}", role);
        return roleRepository.save(role);
    }

    @Override
    public void delete(Long id) {
        logger.debug("Request to delete Role {}", id);
        roleRepository.deleteById(id);
    }

    @Override
    public List<Role> findAll() {
        logger.debug("Request to get all Role");
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findOne(Long id) {
        logger.debug("Request to find Role {}", id);
        return roleRepository.findById(id);
    }

    @Override
    public Role update(Role role) {
        logger.debug("Request to update Role {}",role.getId());
        Role savedRole = roleRepository.findById(role.getId()).orElseThrow(() -> new ResourceNotFoundException());
        return roleRepository.save(savedRole);
    }


    public Role getStudent() {
        return student;
    }

    public void createRoles() {
        roleRepository.save(student);
        roleRepository.save(dozent);
        roleRepository.save(admin);
    }

    public void setStudent(Role student) {
        this.student = student;
    }

    public Role getDozent() {
        return dozent;
    }

    public void setDozent(Role dozent) {
        this.dozent = dozent;
    }

    public Role getAdmin() {
        return admin;
    }

    public void setAdmin(Role admin) {
        this.admin = admin;
    }

}
