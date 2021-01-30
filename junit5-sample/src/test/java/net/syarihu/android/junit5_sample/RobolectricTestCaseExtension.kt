package net.syarihu.android.junit5_sample

import io.kotest.core.extensions.ConstructorExtension
import io.kotest.core.extensions.SpecExtension
import io.kotest.core.extensions.TestCaseExtension
import io.kotest.core.spec.Spec
import io.kotest.core.test.TestCase
import io.kotest.core.test.TestResult
import io.kotest.core.test.TestType
import io.kotest.extensions.robolectric.ContainedRobolectricRunner
import io.kotest.extensions.robolectric.RobolectricTest
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

/**
 * Original:
 * https://github.com/kotest/kotest/blob/master/kotest-extensions/kotest-extensions-robolectric/src/jvmMain/kotlin/io/kotest/extensions/robolectric/RobolectricExtension.kt
 */
class RobolectricTestCaseExtension : ConstructorExtension, TestCaseExtension {
    private val containedRobolectricRunner = ContainedRobolectricRunner()

    override fun <T : Spec> instantiate(clazz: KClass<T>): Spec? {
        if (clazz.isNotRobolectricClass()) return null
        return containedRobolectricRunner.sdkEnvironment.bootstrappedClass<Spec>(clazz.java)
            .newInstance()
    }

    private fun <T : Spec> KClass<T>.isNotRobolectricClass() =
        findAnnotation<RobolectricTest>() == null

    override suspend fun intercept(
        testCase: TestCase,
        execute: suspend (TestCase) -> TestResult
    ): TestResult {

        if (testCase.spec::class.isNotRobolectricClass()) {
            return execute(testCase)
        }

        val containedRobolectricRunner = ContainedRobolectricRunner()

        if (testCase.type == TestType.Test)
            beforeTest(containedRobolectricRunner)
        val result = execute(testCase)
        if (testCase.type == TestType.Test)
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
            RobolectricTestCaseExtension::class.java.classLoader
    }
}
