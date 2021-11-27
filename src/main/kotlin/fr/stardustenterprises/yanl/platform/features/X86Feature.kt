package fr.stardustenterprises.yanl.platform.features

import fr.stardustenterprises.yanl.platform.CPUType
import fr.stardustenterprises.yanl.api.platform.feature.CPUFeature
import java.util.*

enum class X86Feature constructor(nativeName: String? = null) : CPUFeature {
    AES, AMX_BF16, AMX_INT8, AMX_TILE, AVX, AVX2, AVX512BITALG, AVX512BW, AVX512CD, AVX512DQ, AVX512ER, AVX512F, AVX512IFMA, AVX512PF, AVX512VBMI, AVX512VBMI2, AVX512VL, AVX512VNNI, AVX512VPOPCNTDQ, AVX512_4FMAPS, AVX512_4VBMI2, AVX512_4VNNIW, AVX512_BF16, AVX512_SECOND_FMA, AVX512_VP2INTERSECT, BMI1, BMI2, CLFLUSHOPT, CLFSH, CLWB, CX16, CX8, DCA, ERMS, F16C, FMA3, FMA4, FPU, HLE, MMX, MOVBE, PCLMULQDQ, POPCNT, RDRND, RDSEED, RTM, SGX, SHA, SMX, SS, SSE, SSE2, SSE3, SSE4A, SSE4_1, SSE4_2, SSSE3, TSC, VAES, VPCLMULQDQ;

    private val nativeName: String
    override fun cpuType(): CPUType {
        return CPUType.X86
    }

    override fun nativeName(): String {
        return nativeName
    }

    init {
        this.nativeName = nativeName ?: name.lowercase(Locale.getDefault())
    }
}