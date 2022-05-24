package ru.daniilxt.feature.template

import android.annotation.SuppressLint
import ru.daniilxt.feature.template.Utils.toSnakeCase

@SuppressLint("NewApi")
class GenerateFeatureModule(private val moduleName: String, private val className: String) {
    var packageName = "ru.daniilxt"
    var directoryPath = "feature/src/main/java/ru/daniilxt/feature/"
    var directoryLayoutPath = "feature/src/main/res/layout/"

    fun main() {
        Utils.createFile(
            "$directoryPath/$moduleName/di",
            "${className}Component.kt",
            generateDiComponent()
        )
        Utils.createFile(
            "$directoryPath/$moduleName/di",
            "${className}Module.kt",
            generateDiModule()
        )
        Utils.createFile(
            "$directoryPath/$moduleName/presentation",
            "${className}Fragment.kt",
            generateFragment()
        )
        Utils.createFile(
            "$directoryPath/$moduleName/presentation",
            "${className}ViewModel.kt",
            generateViewModel()
        )
        Utils.createFile(
            directoryLayoutPath,
            "fragment${className.toSnakeCase()}.xml",
            generateFragmentXmlLayout()
        )
    }

    private fun generateDiComponent() =
        """
            package $packageName.feature.$moduleName.di
            import androidx.fragment.app.Fragment
            import dagger.BindsInstance
            import dagger.Subcomponent
            import $packageName.common.di.scope.ScreenScope
            import $packageName.feature.$moduleName.presentation.${className}Fragment

            @Subcomponent(
                modules = [
                    ${className}Module::class,
                ]
            )
            @ScreenScope
            interface ${className}Component {

                @Subcomponent.Factory
                interface Factory {
                    fun create(@BindsInstance fragment: Fragment): ${className}Component
                }

                fun inject(${className.replaceFirstChar { it.lowercase() }}Fragment: ${className}Fragment)
            }
        """.trimIndent()

    private fun generateDiModule() =
        """
            package $packageName.feature.$moduleName.di

            import androidx.fragment.app.Fragment
            import androidx.lifecycle.ViewModel
            import androidx.lifecycle.ViewModelProvider
            import dagger.Module
            import dagger.Provides
            import dagger.multibindings.IntoMap
            import $packageName.common.di.viewmodel.ViewModelKey
            import $packageName.common.di.viewmodel.ViewModelModule
            import $packageName.feature.FeatureRouter
            import $packageName.feature.$moduleName.presentation.${className}ViewModel

            @Module(
                includes = [
                    ViewModelModule::class
                ]
            )
            class ${className}Module {

                @Provides
                @IntoMap
                @ViewModelKey(${className}ViewModel::class)
                fun provideViewModel(
                    navigator: FeatureRouter
                ): ViewModel {
                    return ${className}ViewModel(
                        navigator
                    )
                }

                @Provides
                fun provideViewModelCreator(
                    fragment: Fragment,
                    viewModelFactory: ViewModelProvider.Factory
                ): ${className}ViewModel {
                    return ViewModelProvider(fragment, viewModelFactory).get(${className}ViewModel::class.java)
                }
            }
        """.trimIndent()

    private fun generateFragment() =
        """
            package $packageName.feature.$moduleName.presentation

            import android.os.Bundle
            import android.view.View
            import $packageName.common.base.BaseFragment
            import $packageName.common.di.FeatureUtils
            import $packageName.common.extensions.setLightStatusBar
            import $packageName.common.extensions.setStatusBarColor
            import $packageName.common.extensions.viewBinding
            import $packageName.feature.R
            import $packageName.feature.databinding.Fragment${className}Binding
            import $packageName.feature.di.FeatureApi
            import $packageName.feature.di.FeatureComponent

            class ${className}Fragment : BaseFragment<${className}ViewModel>(R.layout.fragment${className.toSnakeCase()}) {

                override val binding: Fragment${className}Binding by viewBinding(Fragment${className}Binding::bind)

                override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                    super.onViewCreated(view, savedInstanceState)
                    requireActivity().setStatusBarColor(R.color.white)
                    requireView().setLightStatusBar()
                }

                override fun inject() {
                    FeatureUtils.getFeature<FeatureComponent>(this, FeatureApi::class.java)
                        .${className.replaceFirstChar { it.lowercase() }}ComponentFactory()
                        .create(this)
                        .inject(this)
                }
            }
        """.trimIndent()

    private fun generateViewModel() =
        """
            package $packageName.feature.$moduleName.presentation

            import $packageName.common.base.BaseViewModel
            import $packageName.feature.FeatureRouter

            class ${className}ViewModel(private val router: FeatureRouter) : BaseViewModel()
        """.trimIndent()

    private fun generateFragmentXmlLayout() =
        """
            <?xml version="1.0" encoding="utf-8"?>
            <androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
                xmlns:tools="http://schemas.android.com/tools"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/background_primary"
                tools:context=".$moduleName.presentation.${className}Fragment">

                <TextView
                    android:id="@+id/et_start"
                    android:layout_width="match_parent"
                    android:layout_height="match_parent"
                    android:text="hello" />
            </androidx.constraintlayout.widget.ConstraintLayout>
        """.trimIndent()
}
