package sweet.apisweetstore.mapper

import org.springframework.stereotype.Component
import sweet.apisweetstore.dto.request.AddressRequest
import sweet.apisweetstore.model.Address

@Component
class AddressRequestToModel: Mapper<AddressRequest, Address> {

    override fun map(t: AddressRequest): Address {
        return Address(
            city = t?.city,
            complement = t?.complement,
            neighborhood = t?.neighborhood,
            number = t?.number,
            state = t?.state,
            street = t?.street,
            cep = t?.cep
        )
    }
}