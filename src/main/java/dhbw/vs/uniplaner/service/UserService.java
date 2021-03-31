package dhbw.vs.uniplaner.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.repository.*;
import dhbw.vs.uniplaner.web.UserRegistrationDto;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import dhbw.vs.uniplaner.interfaces.IUserService;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UniUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UniUser save(UserRegistrationDto registration) {
        UniUser user = new UniUser();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        Role role = roleRepository.getOne(1L);
        user.addRole(role);
        userRepository.save(user);
        roleRepository.save(role);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UniUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),mapRolesToAuthorities(user.getRoles()));

    }

    private Collection< ? extends GrantedAuthority > mapRolesToAuthorities(Set < Role > roles) {
        Collection collection = new ArrayList();
        for(Role r : roles) {
            SimpleGrantedAuthority test = new SimpleGrantedAuthority(r.getRoleName());
            collection.add(test);
        }
        return collection;
    }
}
