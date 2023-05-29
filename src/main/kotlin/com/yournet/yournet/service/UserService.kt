package com.yournet.yournet.service


import com.petition.petition.model.payload.auth.response.JwtResponseDto
import com.yournet.yournet.common.exception.InvalidUserException
import com.yournet.yournet.common.exception.UnauthenticatedException
import com.yournet.yournet.model.entity.User
import com.yournet.yournet.model.payload.auth.request.LoginRequestDto
import com.yournet.yournet.model.payload.auth.request.RegisterRequestDto
import com.yournet.yournet.model.payload.auth.response.UserResponseDto
import com.yournet.yournet.repository.UserRepository
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletResponse

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun saveUser(
        body: RegisterRequestDto,
        clientIp: String
    ): UserResponseDto {
        val user = User(
            password = body.password,
            name = body.name,
            email = body.email,
            userIp = clientIp
        )
        val savedUser = userRepository.save(user)
        val responseDto = UserResponseDto(
            userId = savedUser.userId,
            name = user.name,
            email = user.email,
            userIP = user.userIp,
            createdAt = user.createdAt,
            updatedAt = user.updatedAt
        )
        return responseDto
    }

    fun findByEmail(email: String): User? {
        return userRepository.findByEmail(email)
    }

    fun getById(id: Int): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun checkValidUser(body: LoginRequestDto) {
        val findUser: User = findByEmail(body.email) ?: throw InvalidUserException("User not found")
        if (!findUser.comparePassword(body.password)) {
            throw InvalidUserException("password is not correct")
        }

    }

    //TODO: secret키 하드코딩한거 수정
    fun generateJwt(body: LoginRequestDto): String? {
        val issuer = body.email
        val jwt = Jwts.builder()
            .setIssuer(issuer)
            .setExpiration(Date(System.currentTimeMillis() + 60 * 24 * 1000)) // 1 day
            .signWith(SignatureAlgorithm.HS512, "secret").compact()

        return jwt
    }

    fun getValidUser(jwt: String?): User? {
        if (jwt == null) {
            throw UnauthenticatedException("unauthenticated")
        }

        val body = Jwts.parser().setSigningKey("secret").parseClaimsJws(jwt).body

        return findByEmail(body.issuer)
    }

    //jwt 반영
    fun login(body:LoginRequestDto,response: HttpServletResponse): JwtResponseDto{
        //유효한 유저인지 검증하는 로직
        //TODO: 숭실대 학생인지 검증하는 로직 추가 필요
        checkValidUser(body)
        //JWT 생성
        val jwt = generateJwt(body)
        val findUser = getValidUser(jwt)

        val responseDto = JwtResponseDto(
            jwt=jwt,
            userId = findUser?.userId
        )
        //JWT를 저장
        response.addHeader("Authorization", "$jwt")
        return responseDto
    }
}