package company.surious.domain.entities

import android.os.Parcelable
import company.surious.domain.types.Identifiable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TreeType(
    override var id: String = "",
    var typeName: String = "",
    var typeNameRu: String = "",
    var typeNameUa: String = "",
    var iconUrl: String = ""
) : Parcelable, Identifiable<String> {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TreeType) return false
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    override fun toString(): String {
        return "TreeType(id=$id, typeName='$typeName', typeNameRu='$typeNameRu', typeNameUa='$typeNameUa', iconUrl='$iconUrl')"
    }

}