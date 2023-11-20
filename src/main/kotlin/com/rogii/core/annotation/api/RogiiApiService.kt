package com.rogii.core.annotation.api

import org.springframework.stereotype.Service

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Service
annotation class RogiiApiService()
