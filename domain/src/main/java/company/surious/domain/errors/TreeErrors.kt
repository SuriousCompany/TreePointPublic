package company.surious.domain.errors

sealed class TreeError(
    override val cause: Throwable? = null,
    open val customMessage: String? = null
) : Throwable(cause) {

    sealed class LogicError(message: String) : TreeError(null, message) {
        class NotUniqueUsernameError(var username: String) :
            LogicError("Username '$username' already exists")

        class NoCreditsForIdentificationError(remainingCredits: Double) :
            LogicError("User spent all credits. Remaining credits: $remainingCredits")
    }

    sealed class CommunicationError(
        override val cause: Throwable? = null,
        override val customMessage: String? = null
    ) : TreeError(cause, customMessage) {

        sealed class FirestoreError(
            override val cause: Throwable? = null,
            override val customMessage: String? = null
        ) : CommunicationError(cause, customMessage) {
            class NoFirestoreDocumentError(modelName: String, id: String) :
                FirestoreError(null, "No $modelName with id $id")
        }

        //TODO add a separate error message. Make collection and document optional
        class DetailedFirestoreError(
            override val cause: Throwable? = null,
            collectionName: String,
            documentName: String
        ) : CommunicationError(
            cause,
            "Collection: $collectionName, document: $documentName"
        )

        class LoginError(
            override val cause: Throwable? = null,
            override val customMessage: String? = null
        ) : CommunicationError(cause, customMessage)

        class NoUserFoundError(id: String) : CommunicationError(null, id)
    }

    //Local errors:
    sealed class LocalError(
        override val cause: Throwable? = null,
        override val customMessage: String? = null
    ) : TreeError(cause, customMessage) {

    }

    class PreferencesError(
        override val cause: Throwable? = null,
        override val customMessage: String? = null
    ) : LocalError(cause, customMessage)

    class NoItemInCollectionError(
        val collectionName: String,
        val item: String
    ) : LocalError(null, "No $item found in the $collectionName")

    class ScreenStartError(override val customMessage: String? = null) :
        LocalError(null, customMessage)

    class UiError(
        override val cause: Throwable? = null,
        override val customMessage: String? = null
    ) : TreeError(cause, customMessage)

    override fun toString(): String {
        val message = customMessage
        val errorCause = cause
        return when {
            message != null && errorCause != null -> "$message: ${errorCause.stackTraceToString()}"
            message != null -> message
            errorCause != null -> errorCause.stackTraceToString()
            else -> "Fully unknown error"
        }
    }
}
