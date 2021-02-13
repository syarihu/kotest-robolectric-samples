package net.syarihu.android.junit5_sample

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

    context("hoge2") {
        context("fuga2") {
            test("piyo2") {
                1 + 1 shouldBe 2
                RuntimeEnvironment.systemContext shouldNotBe null
            }
        }
    }

    test("hoge3") {
        1 + 1 shouldBe 2
        RuntimeEnvironment.systemContext shouldNotBe null
    }
    test("hoge4") {
        1 + 1 shouldBe 2
        RuntimeEnvironment.systemContext shouldNotBe null
    }
})