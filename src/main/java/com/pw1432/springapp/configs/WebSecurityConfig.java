
package com.pw1432.springapp.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
// import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.pw1432.springapp.services.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan("com.djamware.springsecuritymongo.configs")
public class WebSecurityConfig {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomizeAuthenticationSuccessHandler customizeAuthenticationSuccessHandler;

    @Bean
    public UserDetailsService mongoUserDetails() {
        return new CustomUserDetailsService();
    }

    // @Override
    // protected void configure(AuthenticationManagerBuilder auth) throws Exception
    // {
    // UserDetailsService userDetailsService = mongoUserDetails();
    // auth
    // .userDetailsService(userDetailsService)
    // .passwordEncoder(bCryptPasswordEncoder);

    // }

    @Bean
    public AuthenticationManager authenticationManagerBean(HttpSecurity http) throws Exception {
        UserDetailsService userDetailsService = mongoUserDetails();
        AuthenticationManagerBuilder authenticationManagerBuilder = http
                .getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        return authenticationManagerBuilder.build();
    }
    // @Override
    // protected void configure(HttpSecurity http) throws Exception {
    // http
    // .authorizeRequests()
    // .antMatchers("/").permitAll()
    // .antMatchers("/login").permitAll()
    // .antMatchers("/signup").permitAll()
    // .antMatchers("/dashboard/**").hasAuthority("ADMIN").anyRequest()
    // .authenticated().and().csrf().disable().formLogin()
    // .successHandler(customizeAuthenticationSuccessHandler)
    // .loginPage("/login").failureUrl("/login?error=true")
    // .usernameParameter("email")
    // .passwordParameter("password")
    // .and().logout()
    // .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
    // .logoutSuccessUrl("/").and().exceptionHandling();
    // }

    // @Override
    // public void configure(WebSecurity web) throws Exception {
    // web
    // .ignoring()
    // .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**",
    // "/images/**");
    // }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login").permitAll()
                .antMatchers("/signup").permitAll()
                .antMatchers("/home/**").hasAuthority("ADMIN")
                .antMatchers("/food/**").hasAuthority("ADMIN")
                .antMatchers("/day/**").hasAuthority("ADMIN").anyRequest()
                .authenticated().and().csrf().disable().formLogin()
                .successHandler(customizeAuthenticationSuccessHandler)
                .loginPage("/login").failureUrl("/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .and().logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login").and().exceptionHandling();
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web
                .ignoring()
                .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
    }

}
