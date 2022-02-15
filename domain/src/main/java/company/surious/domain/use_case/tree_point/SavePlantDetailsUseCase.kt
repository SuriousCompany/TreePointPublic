package company.surious.domain.use_case.tree_point

import company.surious.domain.entities.identification.result.details.PlantDetails
import company.surious.domain.entities.users.CreditsAddedEvent
import company.surious.domain.preferences.InnerPreferences
import company.surious.domain.repositories.CurrentUserRepository
import company.surious.domain.repositories.PlantDetailsRepository
import company.surious.domain.rules.CreditRules
import company.surious.domain.use_case.base.MaybeUseCase
import company.surious.domain.use_case.delegates.checkers.CurrentUserChecker
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.schedulers.Schedulers

class SavePlantDetailsUseCase(
    private val plantDetailsRepository: PlantDetailsRepository,
    private val userRepository: CurrentUserRepository,
    private val preferences: InnerPreferences
) : MaybeUseCase<List<PlantDetails>, CreditsAddedEvent>() {

    override fun createMaybe(params: List<PlantDetails>): Maybe<CreditsAddedEvent> =
        CurrentUserChecker.idOrErrorMaybe(preferences.currentUserId) { currentUserId ->
            plantDetailsRepository.savePlantDetails(params).flatMapMaybe { newPlantsCount ->
                if (newPlantsCount != 0) {
                    userRepository.addCredits(
                        currentUserId,
                        CreditRules.calculateCreditsForNewPlants(newPlantsCount)
                    ).subscribeOn(Schedulers.io()).toMaybe()
                } else {
                    Maybe.empty()
                }
            }
        }
}