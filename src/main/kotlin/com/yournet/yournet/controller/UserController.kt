package com.yournet.yournet.controller


import com.yournet.yournet.model.payload.auth.request.LoginRequestDto
import com.yournet.yournet.model.payload.auth.request.RegisterRequestDto
import com.yournet.yournet.model.payload.auth.response.UserResponseDto
import com.yournet.yournet.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletResponse

@RestController
class UserController(
    private val userService: UserService
) {
    @PostMapping("user/register")
    fun register(@RequestBody body: RegisterRequestDto): ResponseEntity<UserResponseDto> {

        //Service에서 User 저장 로직 수행
        return ResponseEntity.ok(userService.saveUser(body))
    }

    @PostMapping("user/login")
    fun login(@RequestBody body: LoginRequestDto, response: HttpServletResponse): ResponseEntity<Any> {
        userService.login(body,response)
        return ResponseEntity.ok("success")
    }
}