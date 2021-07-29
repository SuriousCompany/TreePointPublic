package company.surious.domain.validators

import java.util.regex.Pattern

object UsernameValidator {

    fun isValid(username: String) =
        Pattern.compile("^(?=.{6,18}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9_]+(?<![_.])$")
            .matcher(username)
            .matches()

}