package fr.stardustenterprises.yanl.platform.features

import fr.stardustenterprises.yanl.platform.CPUType
import fr.stardustenterprises.yanl.api.platform.feature.CPUFeature
import java.util.*

enum class ArmFeature constructor(nativeName: String? = null) : CPUFeature {
    AES, ARM_26BIT("26bit"), CRC32, CRUNCH, EDSP, EVTSTRM, FASTMULT, FPA, HALF, IDIVA, IDIVT, IWMMXT, JAVA, LPAE, NEON, PMULL, SHA1, SHA2, SWP, THUMB, THUMBEE, TLS, VFP, VFPD32, VFPV3, VFPV3D16, VFPV4;

    private val nativeName: String
    override fun cpuType(): CPUType {
        return CPUType.ARM
    }

    override fun nativeName(): String {
        return nativeName
    }

    init {
        this.nativeName = nativeName ?: name.lowercase(Locale.getDefault())
    }
}