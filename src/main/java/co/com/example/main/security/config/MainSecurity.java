package co.com.example.main.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import co.com.example.main.security.serviceImpl.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class MainSecurity  extends WebSecurityConfigurerAdapter{
	
	String[] resources = new String[] { "/include/**", "/css/**", "/icons/**", "/images/**", "/js/**", "/layer/**" };

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(resources).permitAll().antMatchers("/","/pag/**","/detalleProducto/**","/busquedaPorPalabras","/home","/signup","/login","/registroClienteVendedor", "/registroVendedor", "/registroCliente","/soporte","/QuedateEnCasa", "/guardarUsuario").permitAll()
			.anyRequest().authenticated()
			.and().formLogin()
			.loginPage("/login").permitAll()
			.defaultSuccessUrl("/inicio").failureUrl("/signup?error=true")
			.usernameParameter("correo").passwordParameter("contrasena").and().logout().permitAll()
			.logoutSuccessUrl("/logout?logout=true")
			.and()
			.exceptionHandling().accessDeniedPage("/accesoDenegado");
	}

	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		bCryptPasswordEncoder = new BCryptPasswordEncoder(4);
		return bCryptPasswordEncoder;
	}

	@Autowired
	UserDetailsServiceImpl userDetailsService;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
}
