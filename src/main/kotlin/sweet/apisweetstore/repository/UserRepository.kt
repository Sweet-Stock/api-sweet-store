package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import sweet.apisweetstore.model.User

interface UserRepository: JpaRepository<User, String> {


}