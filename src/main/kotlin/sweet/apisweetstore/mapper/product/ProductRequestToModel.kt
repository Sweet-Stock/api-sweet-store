package sweet.apisweetstore.mapper.product

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.ProductResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Product

@Component
class ProductRequestToModel: Mapper<Product, ProductResponse> {
    override fun map(t: Product): ProductResponse {
        
    }

}