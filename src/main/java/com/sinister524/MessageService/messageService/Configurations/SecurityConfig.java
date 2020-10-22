package com.sinister524.MessageService.messageService.Configurations;

import com.sinister524.MessageService.messageService.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http.authorizeRequests()
                .antMatchers("/", "/info", "/registration").permitAll()
                .antMatchers("/rest/messages/send", "/rest/messages/draft", "/rest/messages/**/send", "/rest/messages/**/edit").hasRole("USER;i")
                .antMatchers("/rest/messages/**/accept", "/rest/messages/**/reject").hasRole("OPERATOR")
                .antMatchers("/messages", "/rest/messages").hasAnyRole("OPERATOR", "USER")
                .antMatchers("/admin", "/rest/admin").hasRole("ADMIN")
                .anyRequest().authenticated()
            .and()
                .formLogin().defaultSuccessUrl("/").permitAll()
            .and()
                .logout().permitAll().logoutSuccessUrl("/");

        http.httpBasic().authenticationEntryPoint(authEntryPoint);
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(bCryptPasswordEncoder());
    }
}
