package company.surious.treepoint.di

import company.surious.treepoint.ui.common.providers.TextResourcesProvider
import company.surious.treepoint.ui.common.view_models.CheckCurrentUserViewModel
import company.surious.treepoint.ui.common.view_models.LoginViewModel
import company.surious.treepoint.ui.common.view_models.RegistrationViewModel
import company.surious.treepoint.ui.common.view_models.identification.IdentificationUsageInfoViewModel
import company.surious.treepoint.ui.common.view_models.identification.IdentificationViewModel
import company.surious.treepoint.ui.common.view_models.photos.TreePointPhotosViewModel
import company.surious.treepoint.ui.common.view_models.photos.UploadPhotosViewModel
import company.surious.treepoint.ui.common.view_models.tree_point.AllTreePointsViewModel
import company.surious.treepoint.ui.common.view_models.tree_point.CreateTreePointViewModel
import company.surious.treepoint.ui.common.view_models.tree_point.TreePointViewModel
import company.surious.treepoint.ui.common.view_models.tree_type.AllTreeTypesViewModel
import company.surious.treepoint.ui.common.view_models.tree_type.TreeTypeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object PresentationModules {
    val VIEW_MODELS = module {
        viewModel { LoginViewModel(get(), get(), get()) }
        viewModel { RegistrationViewModel(get(), get()) }
        viewModel { CheckCurrentUserViewModel(get(), get(), get()) }
        viewModel { TreeTypeViewModel(get(), get()) }
        viewModel { AllTreeTypesViewModel(get(), get()) }
        viewModel { AllTreePointsViewModel(get(), get()) }
        viewModel { TreePointViewModel(get(), get()) }
        viewModel { CreateTreePointViewModel(get()) }
        viewModel { UploadPhotosViewModel(get()) }
        viewModel { TreePointPhotosViewModel(get()) }
        viewModel { IdentificationUsageInfoViewModel(get()) }
        viewModel { IdentificationViewModel(get()) }
    }

    val PROVIDERS = module {
        single { TextResourcesProvider(get()) }
    }

    val ALL = arrayOf(VIEW_MODELS, PROVIDERS)
}