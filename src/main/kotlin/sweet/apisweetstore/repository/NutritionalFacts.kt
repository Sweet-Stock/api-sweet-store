package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import sweet.apisweetstore.model.NutritionalFacts

interface NutritionalFacts: JpaRepository<NutritionalFacts, String> {

    fun deleteByUuid(uuid: String)

    fun findByUuid(uuid: String): String

    fun findAllByUuid(uuid: String): String
}