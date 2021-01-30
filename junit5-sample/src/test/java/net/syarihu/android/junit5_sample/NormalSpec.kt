package net.syarihu.android.junit5_sample

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class NormalSpec : FunSpec({
    context("hoge") {
        1 + 1 shouldBe 2
        test("fuga") {
            // test here
            1 + 1 shouldBe 2
        }
    }
    context("hoge2") {
        1 + 1 shouldBe 2
        test("fuga2") {
            1 + 1 shouldBe 2
        }
    }
})