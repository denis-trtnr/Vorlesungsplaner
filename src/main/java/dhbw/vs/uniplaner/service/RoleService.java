package dhbw.vs.uniplaner.service;


import dhbw.vs.uniplaner.interfaces.IRoleService;
import dhbw.vs.uniplaner.repository.RoleRepository;
import dhbw.vs.uniplaner.domain.Role;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoleService implements IRoleService {

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
}
