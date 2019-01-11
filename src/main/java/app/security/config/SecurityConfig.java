package app.security.config;

import app.model.Role;
import app.model.User;
import app.service.RoleServiceImp;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan("app")
@EnableWebSecurity
@EnableOAuth2Sso
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private UserService userService;
	private RoleServiceImp roleService;

	@Autowired
	public SecurityConfig(UserService userService, RoleServiceImp roleService) {
		this.userService = userService;
		this.roleService = roleService;
	}


	/* настраивает обработку http запросов**/
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.authorizeRequests()
				//.mvcMatchers("/").permitAll()
				.anyRequest().authenticated()
				.and().logout().logoutSuccessUrl("/admin/").permitAll()
				.and().csrf().disable();
	}

	@Bean
	public PrincipalExtractor principalExtractor() {
		return map -> {
			String id = (String) map.get("sub");

			User user = userService.getUser(id);

			if (user != null) {
				return user;
			} else {
				User newUser = new User();
				newUser.setId(id);
				newUser.setName((String) map.get("name"));
				newUser.setLogin((String) map.get("email"));
				long pass = (long) (Math.random() * 100000000);
				newUser.setPassword(Long.valueOf(pass).toString());
				/*HashSet<Role> list = new HashSet<>();
				list.add(roleService.getRoleByName("user"));
				newUser.setRoles(list);*/
				userService.addUser(newUser);
				return newUser;
			}
		};

	}

	/* говорит что за авторизацией следит наша реализация UserDetailsService**//*
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		//The name of the configureGlobal method is not important. However,
		// it is important to only configure AuthenticationManagerBuilder in a class annotated with either @EnableWebSecurity
		auth.userDetailsService(authenticationService);
	}*/
}
