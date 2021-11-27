package fr.stardustenterprises.yanl.platform.features

import fr.stardustenterprises.yanl.platform.CPUType
import fr.stardustenterprises.yanl.api.platform.feature.CPUFeature
import java.util.*

enum class Aarch64Feature constructor(nativeName: String? = null) : CPUFeature {
    AES, ASIMD, ASIMDDP, ASIMDFHM, ASIMDHP, ASIMDRDM, ATOMICS, BF16, BTI, CPUID, CRC32, DCPODP, DCPOP, DGH, DIT, EVTSTRM, FCMA, FLAGM, FLAGM2, FP, FPHP, FRINT, I8MM, ILRCPC, JSCVT, LRCPC, PACA, PACG, PMULL, RNG, SB, SHA1, SHA2, SHA3, SHA512, SM3, SM4, SSBS, SVE, SVE2, SVEAES, SVEBF16, SVEBITPERM, SVEF32MM, SVEF64MM, SVEI8MM, SVEPMULL, SVESHA3, SVESM4, USCAT;

    private val nativeName: String
    override fun cpuType(): CPUType {
        return CPUType.AARCH64
    }

    override fun nativeName(): String {
        return nativeName
    }

    init {
        this.nativeName = nativeName ?: name.lowercase(Locale.getDefault())
    }
}