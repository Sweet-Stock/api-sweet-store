package sweet.apisweetstore.mapper.store

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.AddressRequest
import sweet.apisweetstore.dto.request.StoreRequest
import sweet.apisweetstore.dto.response.StoreResponse
import sweet.apisweetstore.mapper.Mapper
import sweet.apisweetstore.model.Address
import sweet.apisweetstore.model.Store

@Component
class StoreRequestToModel : Mapper<Store, StoreRequest> {
    override fun map(t: Store): StoreRequest {
        return StoreRequest(
            t.name,
            t.cnpj,
            t.email,
            t.phone,
            t.image,
            t.address
        )
    }


}