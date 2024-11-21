package frgp.utn.edu.ar.quepasa.data.model.auth

data class TotpDetails(
    val enabled: Boolean,
    val qr: String,
    val url: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TotpDetails

        if (enabled != other.enabled) return false
        if (url != other.url) return false

        return true
    }

    override fun hashCode(): Int {
        var result = enabled.hashCode()
        result = 31 * result + url.hashCode()
        return result
    }
}
