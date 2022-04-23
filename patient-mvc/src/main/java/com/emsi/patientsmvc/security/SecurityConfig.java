package com.emsi.patientsmvc.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder=passwordEncoder();
        String encodedPWD =passwordEncoder.encode("1234");
        auth.inMemoryAuthentication().withUser("user1").password(encodedPWD).roles("USER");
        auth.inMemoryAuthentication().withUser("user2").password(passwordEncoder.encode("1134")).roles("USER");
        auth.inMemoryAuthentication().withUser("Admin").password(passwordEncoder.encode("1224")).roles("USER","ADMIN");


    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web
                .ignoring()
                .antMatchers("/resources/**", "/static/**","/webjars/**");
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.formLogin();
       http.authorizeRequests().antMatchers("/").permitAll();
       http.authorizeRequests().antMatchers("/user/**").hasRole("USER");
       http.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
       http.authorizeRequests().anyRequest().authenticated();
       http.exceptionHandling().accessDeniedPage("/403");
    }
@Bean
   PasswordEncoder passwordEncoder(){
return new BCryptPasswordEncoder();
   }
}
