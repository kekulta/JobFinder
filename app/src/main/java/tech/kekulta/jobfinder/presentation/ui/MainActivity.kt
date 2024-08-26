package tech.kekulta.jobfinder.presentation.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import tech.kekulta.jobfinder.R
import tech.kekulta.jobfinder.databinding.ActivityMainBinding
import tech.kekulta.jobfinder.presentation.navigation.getDialog
import tech.kekulta.jobfinder.presentation.navigation.getFragment
import tech.kekulta.jobfinder.presentation.ui.events.interceptBackPressed
import tech.kekulta.jobfinder.presentation.viewmodels.MainViewModel
import tech.kekulta.navigation.Destination
import tech.kekulta.navigation.Router
import tech.kekulta.navigation.getNavState
import tech.kekulta.navigation.restoreState
import tech.kekulta.navigation.saveToBundle
import tech.kekulta.navigation.setupWithBottomNavigator
import tech.kekulta.uikit.R as uikit


class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)
    private val viewModel by viewModel<MainViewModel>()
    private var router: Router<Destination>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val appLocale = LocaleListCompat.forLanguageTags("ru")
        AppCompatDelegate.setApplicationLocales(appLocale)

        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        setContentView(R.layout.activity_main)

        setupNavigation(savedInstanceState)
        setupBadge()
    }

    override fun onStart() {
        super.onStart()
        router?.let { router ->
            viewModel.getNavigator().attachRouter(router)
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.getNavigator().detachRouter()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        router?.saveState()?.saveToBundle(NAV_STATE_KEY, outState)
    }

    private fun setupNavigation(savedInstanceState: Bundle?) {
        interceptBackPressed(viewModel)

        val navState = savedInstanceState?.getNavState<Destination>(NAV_STATE_KEY)
        router = Router(
            supportFragmentManager, R.id.root_container, ::finish, ::getFragment, ::getDialog
        ).apply { restoreState(navState, viewModel.getStartDestination()) }

        binding.bottomNavigation.setupWithBottomNavigator(viewModel.getBottomNavigator())
    }

    private fun setupBadge() {
        val badge = binding.bottomNavigation.getOrCreateBadge(R.id.liked_item)
        badge.backgroundColor = ResourcesCompat.getColor(resources, uikit.color.red, theme)
        badge.badgeTextColor = ResourcesCompat.getColor(resources, uikit.color.text_color, theme)
        badge.setTextAppearance(uikit.style.TextAppearance_JobFinder_TabText)

        lifecycleScope.launch {
            viewModel.observeBadgeStatus().collect {
                badge.isVisible = it > 0
                badge.number = it
            }
        }
    }

    companion object {
        private const val NAV_STATE_KEY = "NAV_STATE_KEY"
    }
}
