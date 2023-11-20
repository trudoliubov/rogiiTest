package com.rogii.core.extentions.kotlin

import io.qameta.allure.Allure
import io.qameta.allure.AllureLifecycle
import io.qameta.allure.model.Status
import io.qameta.allure.model.StepResult
import io.qameta.allure.util.ResultsUtils
import java.util.*

var lifecycle: AllureLifecycle = Allure.getLifecycle()

/**
 * Выполняет [блок кода][block] под степом. Создает степ с [именем][name] и предустановленными [параметрами][parameters]
 * Статус степа зависит от [блока][block]. Если внутри блока произошла ошибка, то статус будет [Status.FAILED]
 */
fun <T> step(name: String, vararg parameters: Pair<String, Any?>, block: (Allure.StepContext) -> T): T =
    step(name = name, status = Status.PASSED, parameters = parameters, block = block)

fun <T> step(
    name: String,
    status: Status = Status.PASSED,
    vararg parameters: Pair<String, Any?>,
    block: (Allure.StepContext) -> T,
): T {
    val uuid = UUID.randomUUID().toString()
    lifecycle.startStep(uuid, StepResult().apply { setName(name) })

    return try {
        block(
            DefaultStepContext(uuid).apply {
                parameters.forEach { (name, value) -> this.parameter(name, value) }
            }
        ).also {
            lifecycle.updateStep(uuid) { it.status = status }
        }
    } catch (throwable: Throwable) {
        lifecycle.updateStep {
            with(it) {
                this.status = io.qameta.allure.util.ResultsUtils.getStatus(throwable).orElse(io.qameta.allure.model.Status.BROKEN)
                this.statusDetails = io.qameta.allure.util.ResultsUtils.getStatusDetails(throwable).orElse(null)
            }
        }
        sneakyThrow<RuntimeException>(throwable)
    } finally {
        lifecycle.stopStep(uuid)
    }
}

inline fun <reified T : Throwable> sneakyThrow(throwable: Throwable): Nothing {
    if (throwable is T) throw throwable
    else throw IllegalArgumentException("Throwable is not of expected type ${T::class.java.name}", throwable)
}

private class DefaultStepContext(private val uuid: String) : Allure.StepContext {

    override fun name(name: String) {
        lifecycle.updateStep(uuid) { it.name = name }
    }

    override fun <T> parameter(name: String, value: T): T {
        val param = ResultsUtils.createParameter(name, value)
        lifecycle.updateStep(uuid) { it.parameters.add(param) }
        return value
    }
}
