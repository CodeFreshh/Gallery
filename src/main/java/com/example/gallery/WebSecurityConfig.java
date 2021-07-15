package com.example.gallery;


import com.example.gallery.Model.AppUser;
import com.example.gallery.Repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;




@Configuration
class WebSeciurtiyConfig extends WebSecurityConfigurerAdapter {

    private UserSevice userDetailsService;
    private AppUserRepository appUserRepository;

    @Autowired
    public WebSeciurtiyConfig(UserSevice userDetailsService, AppUserRepository appUserRepository) {
        this.userDetailsService = userDetailsService;
        this.appUserRepository = appUserRepository;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/test1").hasRole("USER")
                .antMatchers("/test2").hasRole("ADMIN")
                .antMatchers("/upload").hasRole("ADMIN")
                .antMatchers("/gallery").hasRole("USER1")
                .and()
                .formLogin().permitAll()
                .and()
                .csrf().disable();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @EventListener(ApplicationReadyEvent.class)
    public void get() {
        AppUser appUserUser = new AppUser("user123", passwordEncoder().encode("Password123"), "ROLE_USER1");
        AppUser appUserAdmin = new AppUser("Admin12", passwordEncoder().encode("Adminpass1234"), "ROLE_ADMIN");
        appUserRepository.save(appUserUser);
        appUserRepository.save(appUserAdmin);
    }
}
