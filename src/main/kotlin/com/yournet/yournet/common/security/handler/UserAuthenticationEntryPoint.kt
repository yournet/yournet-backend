package com.yournet.yournet.common.security.handler

import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component

@Component
class UserAuthenticationEntryPoint: AuthenticationEntryPoint {
    @Override
    override fun commence(request: javax.servlet.http.HttpServletRequest?, response: javax.servlet.http.HttpServletResponse?, authException: org.springframework.security.core.AuthenticationException?) {
        response?.sendError(401, "Unauthorized")
    }

}