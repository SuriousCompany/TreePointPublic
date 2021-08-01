package company.surious.domain.logging

class UnhandledException(message: String?, error: Throwable?) : Exception(message, error)