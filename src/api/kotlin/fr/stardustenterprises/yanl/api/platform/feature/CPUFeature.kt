package fr.stardustenterprises.yanl.api.platform.feature

import fr.stardustenterprises.yanl.platform.CPUType

interface CPUFeature {
    /**
     * @return The type of CPU this feature applies to.
     */
    fun cpuType(): CPUType

    /**
     * @return The name of this feature, as returned by the detector library
     * and which should be appended to the loaded natives.
     */
    fun nativeName(): String

    companion object {
        fun custom(type: CPUType, nativeName: String): CPUFeature {
            return object : CPUFeature {
                override fun cpuType(): CPUType {
                    return type
                }

                override fun nativeName(): String {
                    return nativeName
                }
            }
        }
    }
}