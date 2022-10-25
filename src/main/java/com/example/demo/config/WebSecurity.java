package com.example.demo.config;

import com.example.demo.repository.LoginRepository;
import com.example.demo.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    private final ILoginService iLoginService;

    @Autowired
    private OnceRequestPerFilter onceRequestPerFilter;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private JwtAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    WebSecurity(ILoginService iLoginService) {
        this.iLoginService = iLoginService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
//        http.authorizeRequests().antMatchers(environment.getProperty("h2.path")).permitAll()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/product/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint).
//                 and().addFilter(authenticationFilter());

        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/login").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/login/re").permitAll()
                .antMatchers(environment.getProperty("bootstrap.path")).permitAll()
                .antMatchers(environment.getProperty("jquery.path")).permitAll()
                .and()
                .addFilterBefore(authenticationFilter(), AuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().authenticated().and().sessionManagement()
                .and().logout().
                addLogoutHandler(customLogoutSuccessHandler).invalidateHttpSession(true);
    }

    public AuthenticationFilter authenticationFilter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(iLoginService,loginRepository);
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iLoginService);
    }

}
