package sweet.apisweetstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
//@EnableFeignClients
class ApiSweetStoreApplication

fun main(args: Array<String>) {
	runApplication<ApiSweetStoreApplication>(*args)
}
