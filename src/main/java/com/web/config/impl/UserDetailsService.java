package com.web.config.impl;


import com.feign.dto.StudentDTO;
import com.feign.service.StudentService;
import com.web.config.CustomUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService
{


    private StudentService studentService;

    @Autowired
    public void setStudentService(StudentService studentService) {
        this.studentService = studentService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        final StudentDTO studentDTO =studentService.getStudentByUsername(username);
        if(studentDTO==null){
            throw new UsernameNotFoundException("Email is not correct");
        }
        else {

            return new CustomUserDetails(studentDTO);

        }
    }

}
