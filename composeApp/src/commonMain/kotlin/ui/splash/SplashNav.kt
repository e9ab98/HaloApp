package ui.splash

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import cc.loac.kalo.ui.screens.login.LoginScreen
import common.ChangeStatusBarColors
import org.koin.compose.koinInject
import ui.core.navigation.SplashNavigation
import ui.login.LoginViewModel

@Composable
internal fun SplashNav(viewModel: LoginViewModel = koinInject(), navigateToMain: () -> Unit) {
    val navigator = rememberNavController()

    ChangeStatusBarColors(Color.Black)
    NavHost(
        startDestination = SplashNavigation.Splash.route,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(route = SplashNavigation.Splash.route) {
            SplashScreen()

        }
//        composable(route = SplashNavigation.Login.route) {
//            LoginScreen(
//                navigateToMain = navigateToMain, navigateToRegister = {
//                    navigator.navigate(SplashNavigation.Register.route)
//                },
//                state = viewModel.state.value,
//                events = viewModel::onTriggerEvent
//            )
//        }
//        composable(route = SplashNavigation.Register.route) {
//            RegisterScreen(
//                navigateToMain = navigateToMain, popUp = {
//                    navigator.popBackStack()
//                }, state = viewModel.state.value,
//                events = viewModel::onTriggerEvent
//            )
//        }
    }

}