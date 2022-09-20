package sweet.apisweetstore.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sweet.apisweetstore.dto.request.UserRequest
import sweet.apisweetstore.service.UserService

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {
    @PostMapping("/register")
    fun register(@RequestBody user: UserRequest): Boolean{
        userService.register(user)
        return true
    }
}