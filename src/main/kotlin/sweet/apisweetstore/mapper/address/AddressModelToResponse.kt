package sweet.apisweetstore.mapper.address

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.AddressResponse
import sweet.apisweetstore.dto.response.ProductResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Address
import sweet.apisweetstore.model.Product

@Component
class AddressModelToResponse: Mapper<Address, AddressResponse> {
    override fun map(t: Address): AddressResponse {
     return AddressResponse(
         t.city,
         t.complement,
         t.neighborhood,
         t.complement,
         t.state,
         t.street,
         t.cep
     )
    }

}