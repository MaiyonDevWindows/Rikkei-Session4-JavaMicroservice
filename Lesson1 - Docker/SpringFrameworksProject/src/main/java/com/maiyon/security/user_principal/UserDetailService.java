package com.maiyon.security.user_principal;

import com.maiyon.repository.UserRepository;
import com.maiyon.model.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return UserPrincipal.builder().
                   user(user).authorities(user.getRoles().stream().map(
                           item->new SimpleGrantedAuthority(item.getRoleName().toString())
                            ).toList()).build();
        }
        return null;
    }
}
