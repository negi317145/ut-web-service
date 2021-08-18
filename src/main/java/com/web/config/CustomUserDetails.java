package com.web.config;


import com.feign.dto.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private StudentDTO studentDTO;


    @Autowired
    public void setStudentDTO(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public CustomUserDetails(StudentDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority simpleGrantedAuthority=new SimpleGrantedAuthority(studentDTO.getRole());

        return List.of(simpleGrantedAuthority);
    }

    @Override
    public String getPassword() {
        return studentDTO.getPassword();
    }

    @Override
    public String getUsername() {

        return studentDTO.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
