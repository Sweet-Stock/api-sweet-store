package sweet.apisweetstore.mapper.address

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.AddressRequest
import sweet.apisweetstore.dto.response.AddressResponse
import sweet.apisweetstore.dto.response.ProductResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Address
import sweet.apisweetstore.model.Product

@Component
class AddressRequestToModel: Mapper<Address, AddressRequest> {
    override fun map(t: Address): AddressRequest {
      return AddressRequest(
          t.city,
          t.complement,
          t.neighborhood,
          t.number,
          t.state,
          t.street,
          t.cep
      )
    }


}