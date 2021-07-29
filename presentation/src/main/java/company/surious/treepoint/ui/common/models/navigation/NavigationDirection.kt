package company.surious.treepoint.ui.common.models.navigation

import company.surious.domain.entities.LoggedInUser
import company.surious.domain.entities.RegisteredUser

sealed class NavigationDirection

data class MainNavigationDirection(var user: RegisteredUser) : NavigationDirection()

object LoginNavigationDirection : NavigationDirection()

data class ErrorNavigationDirection(var error: Throwable) : NavigationDirection()

data class RegistrationNavigationDirection(var user: LoggedInUser) : NavigationDirection()
