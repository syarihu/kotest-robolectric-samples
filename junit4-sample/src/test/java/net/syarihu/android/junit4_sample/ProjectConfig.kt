package net.syarihu.android.junit4_sample

import io.kotest.core.config.AbstractProjectConfig
import io.kotest.core.extensions.Extension
import io.kotest.core.spec.IsolationMode

object ProjectConfig : AbstractProjectConfig() {
    override val isolationMode: IsolationMode = IsolationMode.InstancePerTest
    override fun extensions(): List<Extension> = listOf(RobolectricTestCaseExtension())
}