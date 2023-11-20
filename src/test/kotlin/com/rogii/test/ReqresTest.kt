package com.rogii.test

import com.rogii.annotations.RogiiAbstractApiBaseTest
import com.rogii.api.services.RegisterServiceApi
import com.rogii.api.services.ResourcesServiceApi
import com.rogii.api.services.UsersServiceApi
import com.rogii.core.annotation.junit.APITest
import com.rogii.core.config.api.RogiiApiClient
import com.rogii.core.extentions.kotlin.step
import io.kotest.assertions.ktor.client.shouldHaveStatus
import io.kotest.matchers.collections.shouldNotBeEmpty
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import io.ktor.client.*
import org.junit.jupiter.api.DisplayName
import org.springframework.beans.factory.annotation.Autowired

@RogiiAbstractApiBaseTest
@DisplayName("[API] Тест")
class ReqresTest @Autowired constructor(
    val users: UsersServiceApi,
    val resources: ResourcesServiceApi,
    val register: RegisterServiceApi,
    @RogiiApiClient
    private val client: HttpClient
) {

    @APITest
    @DisplayName("Тест получение списка пользователей")
    fun getUsersTest() {
        val response = users.getUsers(2)
        step("Выполняю проверку ответа запроса на получение пользователей") {
            response.shouldNotBeEmpty()
        }
    }

    @APITest
    @DisplayName("Тест получение пользователя по id")
    fun getUser() {
        val response = users.getUser(2)
        step("Выполняю проверку пользователя") {
            response.firstName.shouldContain("Janet")
        }
    }

    @APITest
    @DisplayName("Тест получение пользователя по id(NotFound)")
    fun getUserNotFound() {
        val response = users.getUser(23)
        step("Выполняю проверку пользователя") {
            response.firstName.shouldContain("Janet")
        }
    }

    @APITest
    @DisplayName("Тест загрузки списка ресурсов")
    fun getResources() {
        val response = resources.getResources()
        step("Проверка загрузки ресурса") {
            response.shouldNotBeEmpty()
        }
    }

    @APITest
    @DisplayName("Тест загрузки ресурса по id")
    fun getResource() {
        val response = resources.getResource(id = 2)
        step("Проверка загрузки ресурса") {
            response.name.shouldContain("fuchsia rose")
        }
    }

    @APITest
    @DisplayName("Тест загрузки ресурса по id(NotFound)")
    fun getResourceNotFound() {
        val response = resources.getResource(id = 23)
        step("Проверка загрузки ресурса") {
            response.name.shouldContain("fuchsia rose")
        }
    }

    @APITest
    @DisplayName("Регистрация пользователя")
    fun registerSuccessful() {
        val response = register.register("eve.holt@reqres.in", "pistol")
        step("Проверяем данные после регистрации") {
            response.id.shouldBe(4)
        }
    }

    @APITest
    @DisplayName("Регистрация пользователя(Unsuccessful)")
    fun registerUnsuccessful() {
        val response = register.register("eve.holt@reqres.in")
        step("Проверяем данные после регистрации") {
            response.id.shouldBe(4)
        }
    }

    @APITest
    @DisplayName("Обновление пользовательских данных по id")
    fun updateUser() {
        val name = "morpheus"
        val job = "zion resident"
        val response = users.updateUser(2, name, job)
        step("Проверяем данные пользователя после обновления") {
            response.name.shouldContain(name)
            response.job.shouldContain(job)
        }
    }

    @APITest
    @DisplayName("Частичное обновление пользовательских данных по id")
    fun updateUserName() {
        val name = "morpheus"
        val response = users.updateUser(2, name)
        step("Проверяем данные пользователя после обновления") {
            response.name.shouldContain(name)
        }
    }

    @APITest
    @DisplayName("Удаляю пользователя по id")
    fun deleteUser() {
        val id = 2
        val response = users.deleteUser(id)
        step("Проверяем что пользователь удален") {
            response.shouldHaveStatus(204)
        }
    }


}