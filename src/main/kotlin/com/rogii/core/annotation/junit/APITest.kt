package com.rogii.core.annotation.junit

import org.junit.jupiter.api.Test

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@Test
annotation class APITest()
