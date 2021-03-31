package dhbw.vs.uniplaner.interfaces;

import dhbw.vs.uniplaner.domain.UniUser;
import dhbw.vs.uniplaner.web.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService extends UserDetailsService {
    UniUser findByEmail(String email);

    UniUser save(UserRegistrationDto registration);
}
