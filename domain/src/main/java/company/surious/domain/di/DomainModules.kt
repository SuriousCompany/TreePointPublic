package company.surious.domain.di

import company.surious.domain.assemblers.TreePointAssembler
import company.surious.domain.use_case.*
import company.surious.domain.use_case.tree_point.*
import company.surious.domain.use_case.tree_type.GetAllTreeTypesUseCase
import company.surious.domain.use_case.tree_type.GetTreeTypeUseCase
import company.surious.domain.use_case.tree_type.ObserveAllTreeTypesUseCase
import company.surious.domain.use_case.tree_type.ObserveTreeTypeUseCase
import org.koin.dsl.module

object DomainModules {
    val USE_CASES = module {
        //Auth:
        factory { LoginUseCase(get(), get()) }
        factory { RegistrationUseCase(get(), get(), get()) }
        factory { LogoutUseCase(get(), get()) }
        factory { GetLoginIntentUseCase(get()) }
        //User:
        factory { GetCurrentUserUseCase(get(), get()) }
        factory { GetLoggedInUserUseCase(get()) }
        //Tree types:
        factory { GetAllTreeTypesUseCase(get()) }
        factory { GetTreeTypeUseCase(get()) }
        factory { ObserveTreeTypeUseCase(get()) }
        factory { ObserveAllTreeTypesUseCase(get()) }
        //TreePoint-s:
        factory { CreateTreePointUseCase(get(), get(), get()) }
        factory { ObserveAllTreePointsUseCase(get(), get()) }
        factory { GetAllTreePointsUseCase(get(), get()) }
        factory { GetTreePointUseCase(get(), get()) }
        factory { ObserveTreePointUseCase(get(), get()) }
    }

    val ASSEMBLERS = module {
        single { TreePointAssembler(get()) }
    }

    val ALL = arrayOf(USE_CASES, ASSEMBLERS)
}