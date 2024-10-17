package mister3551.msr.msrserver.security.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    private String fullName;
    private String username;
    private String emailAddress;
    private String image;
    private String country;
    private boolean accountConfirmed;
    private boolean accountLocked;

    public CustomUser(String fullName, String username, String password, String emailAddress, Collection<? extends GrantedAuthority> authorities, String image, String country, boolean accountConfirmed, boolean accountLocked) {
        super(username, password, authorities);
        this.fullName = fullName;
        this.username = username;
        this.emailAddress = emailAddress;
        this.image = image;
        this.country = country;
        this.accountConfirmed = accountConfirmed;
        this.accountLocked = accountLocked;
    }

    @Override
    public String toString() {
        return "User full name = " + fullName
                + ", email Address = " + emailAddress
                + ", username = " + username
                + ", image = " + image
                + ", country =" + country
                + ", country =" + accountConfirmed
                + ", country =" + accountLocked
                + "] " + super.toString();
    }
}