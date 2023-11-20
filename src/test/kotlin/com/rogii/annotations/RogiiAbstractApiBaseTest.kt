package com.rogii.annotations

import com.rogii.core.config.api.ClientApiConfig
import com.rogii.core.extentions.junit.annotations.AllureConfigExtendWith
import org.junit.jupiter.api.TestInstance
import org.springframework.boot.test.context.SpringBootTest

@Retention(AnnotationRetention.RUNTIME)
@SpringBootTest(
    classes = [
        ClientApiConfig::class,
        ]
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AllureConfigExtendWith
annotation class RogiiAbstractApiBaseTest{

}
