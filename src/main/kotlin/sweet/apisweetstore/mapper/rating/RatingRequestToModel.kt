package sweet.apisweetstore.mapper.rating

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.RatingRequest
import sweet.apisweetstore.dto.request.StoreRequest
import sweet.apisweetstore.dto.response.RatingResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Rating
import sweet.apisweetstore.model.Store

@Component
class RatingRequestToModel: Mapper<Rating, RatingRequest> {
    override fun map(t: Rating): RatingRequest {
        return RatingRequest(
            t.starsNumber,
            t.title,
            t.comment,
            t.user,
            t.product
        )
    }

}