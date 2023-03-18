package by.novik.securitybank.service;


import by.novik.securitybank.entity.User;
import by.novik.securitybank.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final CardService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = service.findByLogin(username).orElseThrow(() -> new UsernameNotFoundException(username));
        return CustomUserDetails.getUserDetailsFromUser(user);
    }
}
