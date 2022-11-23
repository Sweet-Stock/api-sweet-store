package sweet.apisweetstore.mapper.store

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.response.AddressResponse
import sweet.apisweetstore.dto.response.StoreResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Address
import sweet.apisweetstore.model.Store

@Component
class StoreModelToResponse : Mapper<Store, StoreResponse> {
    override fun map(t: Store): StoreResponse {
        return StoreResponse(
            t.name,
            t.cnpj,
            t.email,
            t.phone,
            t.image,
            t.address
        )
    }

}