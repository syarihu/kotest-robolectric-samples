package net.syarihu.android.junit5_spec_extension_sample

import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.extensions.SpecExtension
import io.kotest.core.spec.Spec
import io.kotest.extensions.robolectric.ContainedRobolectricRunner
import io.kotest.extensions.robolectric.RobolectricTest
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

class SpecRobolectricExtension : ConstructorExtension, SpecExtension {
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
        containedRobolectricRunner.containedBefore()
    }

    private fun afterTest(containedRobolectricRunner: ContainedRobolectricRunner) {
        containedRobolectricRunner.containedAfter()
        Thread.currentThread().contextClassLoader =
            SpecRobolectricExtension::class.java.classLoader
    }
}
