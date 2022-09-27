package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import sweet.apisweetstore.model.Rating

interface RatingRepository:JpaRepository<Rating, String> {
    fun deleteByUuid(uuid: String)

    fun findByUuid(uuid: String): String

    fun findAllByUuid(uuid: String): String

    fun findRatingByStarsNumberIsLessThanEqual(starsNumbers:Long): String
}