package net.syarihu.android.junit4_spec_extension_sample

import io.kotest.extensions.robolectric.RobolectricTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.runner.junit4.FunSpec
import org.robolectric.RuntimeEnvironment

@RobolectricTest
class RoboSpec2 : FunSpec({
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
    test("hoge3") {
        1 + 1 shouldBe 2
        RuntimeEnvironment.systemContext shouldNotBe null
    }
})