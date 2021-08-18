package com.web.config;

//
//import com.feign.config.JwtAuthenticationEntryPoint;
//import com.feign.config.JwtAuthenticationFilter;
import com.web.config.impl.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity
public class Config extends WebSecurityConfigurerAdapter {

//
//    private JwtAuthenticationFilter jwtFilter;
//
//    @Autowired
//    public void setJwtFilter(JwtAuthenticationFilter jwtFilter) {
//        this.jwtFilter = jwtFilter;
//    }


//    private JwtAuthenticationEntryPoint entryPoint;
//
//    @Autowired
//    public void setEntryPoint(JwtAuthenticationEntryPoint entryPoint) {
//        this.entryPoint = entryPoint;
//    }


    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    @Bean
    public DaoAuthenticationProvider authProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;

    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }




    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authProvider());
    }



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/login","/","/register-user","/login","/get-form","/add-mark").permitAll()
                .antMatchers("/get-marks/**").hasRole("USER")
//                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .anyRequest().authenticated()
                .and()
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                //.and()
                //.exceptionHandling().authenticationEntryPoint(entryPoint)
                //.and()
                .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/student/data",true)
                .and()
                .logout()
                .logoutSuccessUrl("/login");

//
//                http.addFilterBefore((Filter) jwtFilter, UsernamePasswordAuthenticationFilter.class);

    }


}
