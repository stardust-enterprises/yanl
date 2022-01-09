package me.xtrm.yanl.test

import fr.stardustenterprises.plat4k.EnumArchitecture
import fr.stardustenterprises.yanl.NativeLayout
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

const val libName = "test"

private val winAarch64 = FakeWindowsContext(EnumArchitecture.AARCH_64)
private val lin64 = FakeLinuxContext(EnumArchitecture.X86_64)
private val linMips64 = FakeLinuxContext(EnumArchitecture.MIPS_64)

const val root1 = "/layout1"
val layout1 = NativeLayout.HIERARCHICAL_LAYOUT

const val root2 = "/layout2"
val layout2 = NativeLayout.FLAT_LAYOUT

/**
 * Holds the unit tests that ensure the project works.
 */
class LayoutTests {
    @Test
    fun `library1 not found on provided architecture`() =
        Assertions.assertNull(layout1.locateLibrary(root1, libName, linMips64))

    @Test
    fun `library1 found on provided architecture`() =
        Assertions.assertNotNull(
            layout1.locateLibrary(root1, libName, winAarch64)
        )

    @Test
    fun `library1 base name not found`() =
        Assertions.assertNull(layout1.locateLibrary(root1, libName, lin64))

    @Test
    fun `library1 extended name found`() =
        Assertions.assertNotNull(
            layout1.locateLibrary(root1, "${libName}64", lin64)
        )

    @Test
    fun `library2 found on provided architecture`() =
        Assertions.assertNotNull(
            layout2.locateLibrary(root2, "${libName}64", winAarch64)
        )

    @Test
    fun `library2 base name found`() =
        Assertions.assertNotNull(layout2.locateLibrary(root2, libName, lin64))

    @Test
    fun `library2 extended name found`() =
        Assertions.assertNotNull(
            layout2.locateLibrary(root2, "${libName}64", lin64)
        )
}
