package company.surious.domain.rules

object CreditRules {
    const val INITIAL_CREDITS = 10.0

    fun calculateCreditsForNewPlants(plantsCount: Int) = plantsCount / 3.0
}