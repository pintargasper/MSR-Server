package mister3551.msr.msrserver.security.security;

import lombok.extern.slf4j.Slf4j;
import mister3551.msr.msrserver.security.entity.User;
import mister3551.msr.msrserver.security.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmailAddress) throws UsernameNotFoundException {

        User user = usersRepository.findByUsernameOrEmailAddress(usernameOrEmailAddress, usernameOrEmailAddress);

        if (!(user.getUsername().equals(usernameOrEmailAddress) || user.getEmailAddress().equals(usernameOrEmailAddress))) {
            throw new UsernameNotFoundException("Invalid credentials!");
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (String role : user.getAuthorities().split(",")) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role));
        }

        return new CustomUser(
                user.getFullName(),
                user.getUsername(),
                user.getPassword(),
                user.getEmailAddress(),
                grantedAuthorities,
                user.getCountry(),
                user.isAccountConfirmed(),
                user.isAccountLocked()
        );
    }
}