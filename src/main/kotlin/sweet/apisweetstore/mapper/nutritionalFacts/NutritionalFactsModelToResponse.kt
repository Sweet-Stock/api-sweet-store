package sweet.apisweetstore.mapper.nutritionalFacts

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.NutritionalFactsResponse
import sweet.apisweetstore.dto.response.StoreResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.NutritionalFacts
import sweet.apisweetstore.model.Store

@Component
class NutritionalFactsModelToResponse: Mapper<NutritionalFacts, NutritionalFactsResponse> {
    override fun map(t: NutritionalFacts): NutritionalFactsResponse {
     return NutritionalFactsResponse(
         t.calories,
         t.sodium,
         t.sugar,
         t.fat,
         t.weight,
         t.gluten
     )
    }

}