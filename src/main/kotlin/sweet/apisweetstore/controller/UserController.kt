package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder
import sweet.apisweetstore.dto.request.ChangePasswordRequest
//import sweet.apisweetstore.dto.request.ResetPasswordRequest
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.dto.response.ChangePasswordResponse
import sweet.apisweetstore.dto.request.LoginRequest

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
        return userService.register(user, uriBuilder)
    }

    @PostMapping("/login")
    fun login(
        @RequestBody loginRequest: LoginRequest
    ): ResponseEntity<LoginResponse> {
        return userService.login(loginRequest)
    }

    @PostMapping("/change-password")
    fun changePassword(@RequestBody resetPasswordRequest: ChangePasswordRequest): ResponseEntity<ChangePasswordResponse> {
        return userService.changePassword(resetPasswordRequest)
    }
    @PutMapping("/change-profile")
    fun updateProfile(@RequestBody userToUpdate:UserRequest, uuid: String): ResponseEntity<Unit> {
        return userService.updateProfile(userToUpdate, uuid)
    }
}