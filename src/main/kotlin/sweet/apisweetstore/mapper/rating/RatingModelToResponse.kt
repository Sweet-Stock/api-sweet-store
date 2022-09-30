package sweet.apisweetstore.mapper.rating

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.RatingResponse
import sweet.apisweetstore.dto.response.StoreResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Rating
import sweet.apisweetstore.model.Store

@Component
class RatingModelToResponse: Mapper<Rating, RatingResponse> {
    override fun map(t: Rating): RatingResponse {
     return RatingResponse(
         t.starsNumber,
         t.title,
         t.comment,
         t.user,
         t.product
     )
    }

}