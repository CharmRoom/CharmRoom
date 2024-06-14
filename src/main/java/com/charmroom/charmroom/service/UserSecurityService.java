package com.charmroom.charmroom.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.charmroom.charmroom.entity.User;
import com.charmroom.charmroom.entity.enums.UserLevel;
import com.charmroom.charmroom.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserSecurityService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> _user = this.userRepository.findById(id);

        if(_user.isEmpty()) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        }

        User user = _user.get();
        List<GrantedAuthority> authorities = new ArrayList<>();

        if("admin".equals(id)) {
            authorities.add(new SimpleGrantedAuthority(UserLevel.ADMIN.getValue()));
        } else {
            authorities.add(new SimpleGrantedAuthority(UserLevel.BASIC.getValue()));
        }
        return new org.springframework.security.core.userdetails.User(
                user.getId(), user.getPassword(), authorities
        );
    }
}