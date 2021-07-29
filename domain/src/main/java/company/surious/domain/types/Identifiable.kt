package company.surious.domain.types

interface Identifiable<Type : Any> {
    var id: Type

    fun isIdentical(another: Identifiable<Type>) = id == another.id
}