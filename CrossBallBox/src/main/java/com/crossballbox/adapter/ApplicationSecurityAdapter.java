package com.crossballbox.adapter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.crossballbox.service.UserService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityAdapter extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Value("${app.secret}")
	private String appSecret;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/bootstrap/**", "/resources/**").permitAll()
//				.antMatchers("/", "/index", "/signUp", "/user/activate").permitAll().antMatchers("/admin/**").hasRole("ADMIN").anyRequest()
//				.fullyAuthenticated().and().formLogin().loginPage("/login").successForwardUrl("/").failureUrl("/login?error").permitAll().and()
//				.logout().logoutUrl("/logout").logoutSuccessUrl("/");
		
		http.csrf();
		
		http.authorizeRequests().antMatchers("/css/**", "/js/**", "/img/**", "/bootstrap/**", "/resources/**").permitAll()
		.antMatchers("/", "/createAdminUser", "/index", "/signUp", "/user/activate", "/news", "/taebo", "/crossfit", "/aerotonus", "/total50").permitAll()
        .antMatchers("/user/**").hasRole("USER")
        .antMatchers("/admin/**", "/user/**").hasRole("ADMIN")
        .anyRequest().authenticated()
        .and().formLogin().loginPage("/login").successForwardUrl("/").failureUrl("/login?error").permitAll().and()
		.logout().logoutUrl("/logout").logoutSuccessUrl("/");
	}

	//in memory database
//	@Autowired
//	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
//		authenticationMgr.inMemoryAuthentication().withUser("steva1988").password("steva1988").authorities("USER");
//		authenticationMgr.inMemoryAuthentication().withUser("admin").password("admin").authorities("ADMIN");
//		
//	}
	
	//from jdbc 
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authenticationMgr) throws Exception {
		authenticationMgr.userDetailsService(userService);
	}

}
