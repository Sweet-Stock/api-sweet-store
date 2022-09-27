package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import sweet.apisweetstore.model.Store

interface StoreRepository:JpaRepository<Store, String> {
    fun deleteByUuid(uuid: String)

    fun findByUuid(uuid: String): String

    fun findAllByUuid(uuid: String): String

    fun findStoreByCnpjEquals(cnpj: String): String

}