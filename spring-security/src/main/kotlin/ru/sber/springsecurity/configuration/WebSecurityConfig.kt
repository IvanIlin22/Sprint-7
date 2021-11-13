package ru.sber.springsecurity.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import javax.sql.DataSource

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private val dataSource: DataSource? = null

    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/registration").permitAll()
            .antMatchers("/api/**").hasRole("API")
            .antMatchers("/login").permitAll()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/login")
            .defaultSuccessUrl("/app/add")
            .permitAll()
            .and()
            .logout()
            .permitAll()

    }

    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.jdbcAuthentication()
            .dataSource(dataSource)
            .passwordEncoder(BCryptPasswordEncoder())
            .usersByUsernameQuery("select username, password, is_active from t_user where username = ?")
            .authoritiesByUsernameQuery("select u.username, ur.roles from t_user u inner join user_role ur on u.id = ur.user_id where u.username = ?")
    }
}
