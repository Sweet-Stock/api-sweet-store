package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
//import sweet.apisweetstore.dto.request.ResetPasswordRequest
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.dto.response.LoginRequest
import sweet.apisweetstore.dto.response.LoginResponse
import sweet.apisweetstore.dto.response.UserResponse
import sweet.apisweetstore.service.UserService

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(
        @RequestBody user: UserRequest,
        uriBuilder: UriComponentsBuilder
    ): ResponseEntity<UserResponse> {
        val user = userService.register(user)
        val uri = uriBuilder.path("/user/${user.uuid}").build().toUri()
        return ResponseEntity.created(uri).body(user)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<LoginResponse> {
        return ResponseEntity.ok().body(userService.login(loginRequest))
    }

//    @PostMapping("/reset-password")
//    fun resetPassword(@RequestBody resetPasswordRequest: ResetPasswordRequest) {
//        return ResponseEntity.ok().body(userService.resetPassword(resetPasswordRequest))
//    }
}