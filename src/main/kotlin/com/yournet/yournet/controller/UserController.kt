package com.yournet.yournet.controller


import com.yournet.yournet.model.payload.auth.request.LoginRequestDto
import com.yournet.yournet.model.payload.auth.request.RegisterRequestDto
import com.yournet.yournet.model.payload.auth.response.UserResponseDto
import com.yournet.yournet.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("user/register")
    fun register(
        @RequestBody body: RegisterRequestDto,
        request: HttpServletRequest
    ): ResponseEntity<UserResponseDto> {
        val clientIp = getClientIp(request)
        println("Client IP: $clientIp")
        //Service에서 User 저장 로직 수행
        return ResponseEntity.ok(userService.saveUser(body, clientIp))
    }
    //TODO: Service로 코드 분리
    private fun getClientIp(request: HttpServletRequest): String {
        val xForwardedForHeader = request.getHeader("X-Forwarded-For")
        if (xForwardedForHeader != null) {
            return xForwardedForHeader.split(",")[0].trim()
        }
        return request.remoteAddr
    }

    @PostMapping("user/login")
    fun login(@RequestBody body: LoginRequestDto, response: HttpServletResponse): ResponseEntity<Any> {
        userService.login(body,response)
        return ResponseEntity.ok("success")
    }
}