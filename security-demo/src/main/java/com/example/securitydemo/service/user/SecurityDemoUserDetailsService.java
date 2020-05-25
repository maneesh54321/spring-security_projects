package com.example.securitydemo.service.user;

import com.example.securitydemo.vo.AppUserDetails;
import com.example.securitydemo.vo.SignupUserCredential;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component("securityDemoUserDetailsService")
public class SecurityDemoUserDetailsService implements UserDetailsService {

    private final SecurityDemoUserRepository repository;

    @Autowired
    public SecurityDemoUserDetailsService(SecurityDemoUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<SecurityDemoUser> maybeUser = repository.findByUsername(username);
        if (maybeUser.isPresent()) {
            SecurityDemoUser user = maybeUser.get();
            return new SecurityDemoUserDetails(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>(user.getAllRoles()).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            );
        }
        throw new UsernameNotFoundException(String.format("User with username: '%s' NOT FOUND", username));
    }

    public List<AppUserDetails> getAllUserDetails(){
        List<SecurityDemoUser> users = repository.findAll();
        return users.stream().map(user -> new AppUserDetails(user.getUsername(), user.getAllRoles())).collect(Collectors.toList());
    }

    public void signupUser(SignupUserCredential signupUserCredential){
        SecurityDemoUser securityDemoUser = new SecurityDemoUser(signupUserCredential.getUsername(), signupUserCredential.getPassword());
        repository.save(securityDemoUser);
    }

    @Transactional
    public void deleteUser(String username){
        repository.deleteByUsername(username);
    }
}
