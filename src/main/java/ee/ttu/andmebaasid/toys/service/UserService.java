package ee.ttu.andmebaasid.toys.service;

import ee.ttu.andmebaasid.toys.domain.User;
import ee.ttu.andmebaasid.toys.repository.UserRepository;
import ee.ttu.andmebaasid.toys.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.dao.SaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    private BCryptPasswordEncoder passwordEncoder;

    @Inject
    private UserRepository userRepository;

    public void deleteUser(String login) {
        userRepository.findOneByEmail(login).ifPresent(u -> {
            userRepository.delete(u);
            log.debug("Deleted User: {}", u);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin()).ifPresent(u -> {
            String encryptedPassword = passwordEncoder.encode(password);
            u.setPassword(encryptedPassword);
            userRepository.save(u);
            log.debug("Changed password for User: {}", u);
        });
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneByEmail(login).map(u -> u);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        User user = userRepository.findOne(id);
        return user;
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        Optional<User> optionalUser = userRepository.findOneByEmail(SecurityUtils.getCurrentUserLogin());
        User user = null;
        if (optionalUser.isPresent()) {
          user = optionalUser.get();
         }
         return user;
    }
}
