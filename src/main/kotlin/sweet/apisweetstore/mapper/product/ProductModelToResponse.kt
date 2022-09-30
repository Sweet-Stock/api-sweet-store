package sweet.apisweetstore.mapper.product

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.ProductResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Product

@Component
class ProductModelToResponse: Mapper<Product, ProductResponse> {
    override fun map(t: Product): ProductResponse {
      return ProductResponse(
          t.name,
          t.description,
          t.price,
          t.image,
          t.category,
          t.nutritionalValue,
          t.store
      )
    }
}