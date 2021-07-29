package company.surious.data.config

class DataConfig {

    companion object {
        init {
            System.loadLibrary("config")
        }

        external fun getWebClientId(): String
    }
}