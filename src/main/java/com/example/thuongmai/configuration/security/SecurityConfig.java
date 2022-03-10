package com.example.thuongmai.configuration.security;

import com.example.thuongmai.Service.role.IRoleService;
import com.example.thuongmai.Service.user.IUserService;
import com.example.thuongmai.configuration.custom.CustomAccessDeniedHandler;
import com.example.thuongmai.configuration.custom.RestAuthenticationEntryPoint;
import com.example.thuongmai.configuration.filter.JwtAuthenticationFilter;
import com.example.thuongmai.enums.EnumRoles;
import com.example.thuongmai.model.role.Role;
import com.example.thuongmai.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private IUserService userService;
    @Autowired
    private IRoleService roleService;

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public RestAuthenticationEntryPoint restServicesEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @PostConstruct
    public void init(){
        List<User> users = (List<User>) userService.findAll();
        List<Role> roles = (List<Role>) roleService.findAll();
        if (roles.isEmpty()){
            Role roleAdmin = new Role();
            roleAdmin.setId(1L);
            roleAdmin.setName(EnumRoles.ROLE_ADMIN);
            roleService.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setId(2L);
            roleUser.setName(EnumRoles.ROLE_USER);
            roleService.save(roleUser);
            Role roleProvider = new Role();
            roleProvider.setId(3L);
            roleProvider.setName(EnumRoles.ROLE_PROVIDER);
            roleService.save(roleProvider);
            Role roleVip = new Role();
            roleVip.setId(4L);
            roleProvider.setName(EnumRoles.ROLE_VIP);
        }
        if (users.isEmpty()){
            User userAdmin = new User();
            Set<Role> rolesSet = new HashSet<>();
            rolesSet.add(new Role(1L, EnumRoles.ROLE_ADMIN));
            userAdmin.setName("Admin");
            userAdmin.setUsername("admin");
            userAdmin.setWallet(0D);
            userAdmin.setDate(LocalDate.now());
            userAdmin.setAvatar("https://firebasestorage.googleapis.com/v0/b/upload-file-1-5c40f.appspot.com/o/20220115_231405.jpg?alt=media&token=424e36e7-a80e-4c61-b241-d03e5f6a52aa");
            userAdmin.setPassword(new BCryptPasswordEncoder().encode("12345"));
            userAdmin.setRoles(rolesSet);
            userService.save(userAdmin);
        }
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/**");
        http.httpBasic().authenticationEntryPoint(restServicesEntryPoint());
        http.authorizeRequests()
                .antMatchers("/**").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.cors();
    }
}
