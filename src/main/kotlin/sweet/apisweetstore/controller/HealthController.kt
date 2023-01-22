package sweet.apisweetstore.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/health")
@CrossOrigin(origins = arrayOf("*"))
class HealthController {

    @GetMapping
    fun health(): ResponseEntity<String> {
        return ResponseEntity.ok("Api sweet store funcionando corretamente!")
    }
}