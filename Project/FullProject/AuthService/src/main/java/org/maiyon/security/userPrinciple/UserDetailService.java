package org.maiyon.security.userPrinciple;

import org.maiyon.model.entity.User;
import org.maiyon.repository.UserRepository;
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
        Optional<User> userOptional = userRepository.findByUserName(username);
        if(userOptional.isPresent()){
            User user=userOptional.get();
            return UserPrinciple.builder()
                .user(user).authorities(user.getRoles().stream().map(
                        role -> new SimpleGrantedAuthority(role.getRoleName().toString())).toList()
                    )
                .build();
        }
        return null;
    }
}
