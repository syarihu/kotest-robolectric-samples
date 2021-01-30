package net.syarihu.android.kotest

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

    override suspend fun intercept(spec: KClass<out Spec>, process: suspend () -> Unit) {
        if (spec.isNotRobolectricClass()) {
            process()
            return
        }

        val containedRobolectricRunner = ContainedRobolectricRunner()

        beforeTest(containedRobolectricRunner)
        process()
        afterTest(containedRobolectricRunner)
    }

    private fun beforeTest(containedRobolectricRunner: ContainedRobolectricRunner) {
        Thread.currentThread().contextClassLoader =
            containedRobolectricRunner.sdkEnvironment.robolectricClassLoader
        containedRobolectricRunner.containedBefore()
    }

    private fun afterTest(containedRobolectricRunner: ContainedRobolectricRunner) {
        containedRobolectricRunner.containedAfter()
        Thread.currentThread().contextClassLoader =
            RobolectricSpecExtension::class.java.classLoader
    }
}
