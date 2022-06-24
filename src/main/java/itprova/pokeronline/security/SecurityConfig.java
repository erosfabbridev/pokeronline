package itprova.pokeronline.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity 
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JWTFilter jwtFilter;
	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	private JWTAuthEntryPoint unauthorizedHandler;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception { 
		http.csrf().disable()
				.httpBasic().disable() 
				.cors() 
				.and()
				
				.authorizeHttpRequests() 
				.antMatchers("/api/auth/login").permitAll()
				.antMatchers("/api/utente/userInfo").authenticated()
				.antMatchers("/api/gestioneAmministrazione/**").hasRole("ADMIN")
				.antMatchers("/api/gestioneTavolo/**").hasAnyRole("ADMIN", "SPECIAL_PLAYER")
				.antMatchers("/api/playManagment/**").hasAnyRole("ADMIN", "SPECIAL_PLAYER", "PLAYER")
				.antMatchers("/**").hasAnyRole("ADMIN", "PLAYER", "SPECIAL_PLAYER")
				.anyRequest().authenticated()
				.and()
				
				.userDetailsService(customUserDetailsService) 
				.exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
				.and()
				
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); 

		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}

}
