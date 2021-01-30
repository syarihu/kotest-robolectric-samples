package net.syarihu.android.junit4_sample

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NormalSpec : FunSpec({
    context("this outer block is enabled") {
        1 + 1 shouldBe 2
        test("this test is disabled") {
            // test here
            1 + 1 shouldBe 2
        }
    }
    context("this block is disabled") {
        1 + 1 shouldBe 2
        test("disabled by inheritance from the parent") {
            1 + 1 shouldBe 2
        }
    }
})