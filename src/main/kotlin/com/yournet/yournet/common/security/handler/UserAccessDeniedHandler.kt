package com.yournet.yournet.common.security.handler

import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component

@Component
class UserAccessDeniedHandler: AccessDeniedHandler {
    @Override
    override fun handle(request: javax.servlet.http.HttpServletRequest?, response: javax.servlet.http.HttpServletResponse?, accessDeniedException: org.springframework.security.access.AccessDeniedException?) {
        response?.sendError(403, "Access Denied")
    }
}