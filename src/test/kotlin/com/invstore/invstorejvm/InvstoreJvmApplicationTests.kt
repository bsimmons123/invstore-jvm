package com.invstore.invstorejvm

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Profile
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class InvstoreJvmApplicationTests {

    @Test
    fun contextLoads() {
    }

}
