package company.surious.domain.errors

sealed class TreeError(
    override val cause: Throwable? = null,
    open val customMessage: String? = null
) : Throwable(cause) {
    override fun toString(): String =
        if (customMessage != null) {
            "$customMessage: ${super.toString()}"
        } else {
            super.toString()
        }
}


sealed class CommunicationError(
    override val cause: Throwable? = null,
    override val customMessage: String? = null
) : TreeError(cause, customMessage)

sealed class FirestoreError(
    override val cause: Throwable? = null,
    override val customMessage: String? = null
) : CommunicationError(cause, customMessage)

//TODO add a separate error message. Make colection and document optional
class DetailedFirestoreError(
    override val cause: Throwable? = null,
    collectionName: String,
    documentName: String
) : CommunicationError(
    cause,
    "Collection: $collectionName, document: $documentName"
)

class NoFirestoreDocumentError(modelName: String, id: String) :
    FirestoreError(null, "No $modelName with id $id")

class LoginError(
    override val cause: Throwable? = null,
    override val customMessage: String? = null
) : CommunicationError(cause, customMessage)

class NotUniqueUsernameError(
    username: String
) : CommunicationError(null, username)

sealed class LocalError(
    override val cause: Throwable? = null,
    override val customMessage: String? = null
) : TreeError(cause, customMessage)

class PreferencesError(
    override val cause: Throwable? = null,
    override val customMessage: String? = null
) : LocalError(cause, customMessage)

class NoItemInCollectionError(
    val collectionName: String,
    val item: String
) : LocalError(null, "No $item found in the $collectionName")

class ScreenStartError(override val customMessage: String? = null) : LocalError(null, customMessage)

class UiError(
    override val cause: Throwable? = null,
    override val customMessage: String? = null
) : TreeError(cause, customMessage)

