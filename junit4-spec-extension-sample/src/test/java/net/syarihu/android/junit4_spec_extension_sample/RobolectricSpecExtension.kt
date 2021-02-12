package net.syarihu.android.junit4_spec_extension_sample

import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.extensions.SpecExtension
import io.kotest.core.spec.Spec
import io.kotest.extensions.robolectric.ContainedRobolectricRunner
import io.kotest.extensions.robolectric.RobolectricTest
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Original:
 * https://github.com/kotest/kotest/blob/master/kotest-extensions/kotest-extensions-robolectric/src/jvmMain/kotlin/io/kotest/extensions/robolectric/RobolectricExtension.kt
 */
class RobolectricSpecExtension : ConstructorExtension, SpecExtension {
    private val containedRobolectricRunner = ContainedRobolectricRunner()

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz.isNotRobolectricClass()) return null
        return containedRobolectricRunner.sdkEnvironment.bootstrappedClass<Spec>(clazz.java)
            .newInstance()
    }

    private fun <T : Spec> KClass<T>.isNotRobolectricClass() =
        findAnnotation<RobolectricTest>() == null

    override suspend fun intercept(spec: Spec, execute: suspend (Spec) -> Unit) {
        if (spec::class.isNotRobolectricClass()) {
            return execute(spec)
        }

        val containedRobolectricRunner = ContainedRobolectricRunner()

        beforeTest(containedRobolectricRunner)
        val result = execute(spec)
        afterTest(containedRobolectricRunner)
        return result
    }

    private fun beforeTest(containedRobolectricRunner: ContainedRobolectricRunner) {
        Thread.currentThread().contextClassLoader =
            containedRobolectricRunner.sdkEnvironment.robolectricClassLoader
        kotlin.runCatching {
            containedRobolectricRunner.containedBefore()
        }
    }

    private fun afterTest(containedRobolectricRunner: ContainedRobolectricRunner) {
        kotlin.runCatching {
            containedRobolectricRunner.containedAfter()
        }
        Thread.currentThread().contextClassLoader =
            RobolectricSpecExtension::class.java.classLoader
    }
}
