package com.yournet.yournet.config


import com.yournet.yournet.common.security.handler.UserAccessDeniedHandler
import com.yournet.yournet.common.security.handler.UserAuthenticationEntryPoint
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain

@EnableWebSecurity
@Configuration
class SecurityConfig(
    private var userAuthenticationEntryPoint: UserAuthenticationEntryPoint,
    private var userAccessDeniedHandler: UserAccessDeniedHandler
) {


    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain? {
        http.csrf().disable()
            .exceptionHandling()
            .authenticationEntryPoint(userAuthenticationEntryPoint)
            .accessDeniedHandler(userAccessDeniedHandler)
            .and()
            .headers()
            .frameOptions()
            .sameOrigin()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
//              .authorizeHttpRequests { authorize ->
//                  authorize //비로그인도 접근 가능 (누구나)
//                    .antMatchers(HttpMethod.GET, "/*").permitAll()
//                    .antMatchers(HttpMethod.POST, "/*/**").permitAll()
//                     //이외에는 로그인한 사람만 접근 가능
//                    .anyRequest().hasAnyRole("ADMIN", "USER")
//            }

            .logout()
            .disable()
        return http.build()
    }
}