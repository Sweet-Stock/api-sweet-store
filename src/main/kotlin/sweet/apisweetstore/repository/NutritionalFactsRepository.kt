package sweet.apisweetstore.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import sweet.apisweetstore.integration.NutritionalFactsResponseAPI
import sweet.apisweetstore.model.NutritionalFacts

@Repository
interface NutritionalFactsRepository: JpaRepository<NutritionalFacts, Int> {

//    fun deleteById(id: Int)
//
//    fun findById(id: Int): String
//
//    fun findAllByUuid(id: Int): String
}