package net.syarihu.android.junit4_sample

import io.kotest.matchers.shouldBe
import io.kotest.runner.junit4.FunSpec

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