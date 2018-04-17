package gr.mgourlis.draftnationallity.configuration;

import gr.mgourlis.draftnationallity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	UserService userService;

	public SecurityConfiguration() {
		super();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
		auth.setUserDetailsService(userService);
		auth.setPasswordEncoder(passwordEncoder());
		return auth;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth)
			throws Exception {
		auth.authenticationProvider(authenticationProvider());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.
			authorizeRequests()
				.antMatchers("/login").permitAll()
				.antMatchers("/").fullyAuthenticated()
				.antMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest()
				.fullyAuthenticated()
				.and()
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?error=true")
				.defaultSuccessUrl("/")
				.usernameParameter("email")
				.passwordParameter("password")
				//.and()
				//.logout()
				//.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
				//.logoutSuccessUrl("/")
				//.and()
				//.exceptionHandling()
				//.accessDeniedPage("/access-denied")
				;
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
	    web
	       .ignoring()
	       .antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**");
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
	}

}