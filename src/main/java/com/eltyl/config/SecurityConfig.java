package com.eltyl.config;

import com.eltyl.util.HttpUtil;
import com.eltyl.util.validate.code.ValidateCodeFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsService userDetailsService;
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;
    @Autowired
    private MyAuthenticationSuccessHandler authenticationSuccessHandler;  // 登录成功返回的 JSON 格式数据给前端（否则为 html）

    @Autowired
    private MyAuthenticationFailureHandler authenticationFailureHandler;  //  登录失败返回的 JSON 格式数据给前端（否则为 html
    @Autowired
    private ValidateCodeFilter validateCodeFilter;
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl rememberMeTokenRepository = new JdbcTokenRepositoryImpl();
        rememberMeTokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return rememberMeTokenRepository;
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.rememberMe().key("eltyl").tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60*60*24*7).userDetailsService(userDetailsService);
        http
                .formLogin().loginPage("/login").permitAll().loginProcessingUrl("/doLogin").successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler) // 登录失败
                .and().logout().logoutUrl("/logout").permitAll().invalidateHttpSession(true).deleteCookies("JSESSIONID").logoutSuccessHandler(logoutSuccessHandler())
                // 所有请求都需要认证
                .and().authorizeRequests().anyRequest().authenticated()
                // 防止iframe 造成跨域
                .and().headers().frameOptions().disable()
                .and().sessionManagement().maximumSessions(1).maxSessionsPreventsLogin(false);
        http.exceptionHandling().authenticationEntryPoint(new MyAuthenticationEntryPoint()).accessDeniedHandler(accessDeniedHandler); // 无权访问 JSON 格式的数据
        http.addFilterBefore(validateCodeFilter,UsernamePasswordAuthenticationFilter.class);
        http.csrf().disable();
        //安全模块单独配置
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/code/*","/**/*.js", "/**/*.js.map", "/**/*.ts", "/**/*.css", "/**/*.css.map", "/**/*.png", "/**/*.gif", "/**/*.jpg", "/**/*.fco", "/**/*.woff", "/**/*.woff2", "/**/*.font", "/**/*.svg", "/**/*.ttf", "/**/*.pdf","/*.ico", "/admin/api/**", "/404", "/401","/403", "/error");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }
    //退出的处理
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() { //登出处理
        return new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                /*try {
                    SecurityUser user = (SecurityUser) authentication.getPrincipal();
                    if(user!=null)logger.info("USER : {} LOGOUT SUCCESS ! ", user.getUsername());
                } catch (Exception e) {
                    logger.error("printStackTrace", e);
                }*/
                response.sendRedirect("/login");
            }
        };
    }
    private class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
        @Override
        public void commence(HttpServletRequest request,
                             HttpServletResponse response,
                             AuthenticationException authException) throws IOException {
            response.setCharacterEncoding("utf-8");
            if (HttpUtil.isAjaxRequest(request)) {
                response.getWriter().println("请登录");
            } else {
                response.sendRedirect("/login");
            }

        }
    }

}
