package sweet.apisweetstore.integration

import feign.codec.ErrorDecoder
import org.springframework.context.annotation.Bean

class FeignSimpleEncoderConfig {
    @Bean
    fun encoder(): ErrorDecoder {
        return CustomErrorDecoder()
    }
}