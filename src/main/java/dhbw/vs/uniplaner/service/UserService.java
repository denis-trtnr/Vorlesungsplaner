package dhbw.vs.uniplaner.service;

import ch.qos.logback.core.net.SyslogOutputStream;
import dhbw.vs.uniplaner.domain.*;
import dhbw.vs.uniplaner.interfaces.IRoleService;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LecturerRepository lecturerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private IRoleService roleService;


    public UniUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public UniUser save(UserRegistrationDto registration) {
        UniUser user = new UniUser();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        user.addRole(roleService.getStudent());
        userRepository.save(user);



        return user;
    }

    /*
        Lecturer lecturer = new Lecturer();
        lecturer.setFirstName(registration.getFirstName());
        lecturer.setLastName(registration.getLastName());
        lecturer.setEmail(registration.getEmail());
        Lecture test = new Lecture();
        test.setDuration(10L);
        test.setLectureName("Test");
        test.addLecturer(lecturer);
        */

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
