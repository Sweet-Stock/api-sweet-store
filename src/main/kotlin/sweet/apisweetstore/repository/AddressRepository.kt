package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import sweet.apisweetstore.model.Address

interface AddressRepository:JpaRepository<Address, String>{

    fun deleteByUuid(uuid: String)

    fun findByUuid(uuid: String): String

    fun findAllByUuid(uuid: String): String

    fun findByCepAndNumber(cep: String, number :Int): String

    fun findByCep(cep: String): String



}