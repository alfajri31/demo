package com.example.demo.config;

import com.example.demo.service.ILoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment environment;

    private final ILoginService iLoginService;

    @Autowired
    private OnceRequestPerFilter onceRequestPerFilter;

    WebSecurity(ILoginService iLoginService) {
        this.iLoginService = iLoginService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.headers().addHeaderWriter(new XFrameOptionsHeaderWriter(XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN));
        http.authorizeRequests().antMatchers(environment.getProperty("h2.path")).permitAll();
//        http.authorizeRequests().antMatchers(environment.getProperty("product.path")).authenticated().and().addFilterBefore(authenticationFilter(), AuthenticationFilter.class);
    }

    public AuthenticationFilter authenticationFilter() {
        return new AuthenticationFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(iLoginService);
    }
}
