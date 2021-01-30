package net.syarihu.android.kotest

import io.kotest.core.spec.style.FunSpec
import io.kotest.extensions.robolectric.RobolectricTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.robolectric.RuntimeEnvironment

@RobolectricTest
class RoboSpec : FunSpec({
    context("hoge") {
        context("fuga") {
            test("piyo") {
                1 + 1 shouldBe 2
                RuntimeEnvironment.systemContext shouldNotBe null
            }
        }
    }
    test("hoge2") {
        1 + 1 shouldBe 2
        RuntimeEnvironment.systemContext shouldNotBe null
    }
})