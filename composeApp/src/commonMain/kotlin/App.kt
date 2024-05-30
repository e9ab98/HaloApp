import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil3.ImageLoader
import coil3.compose.setSingletonImageLoaderFactory
import coil3.fetch.NetworkFetcher
import common.Context
import di.appModule
import org.jetbrains.compose.ui.tooling.preview.Preview

import org.koin.compose.KoinApplication
import ui.core.navigation.AppNavigation
import ui.core.theme.AppTheme
import ui.splash.SplashNav

@Composable
@Preview
fun App(context: Context) {
    KoinApplication(application = {
        modules(appModule(context))
    }){
        //coil3
        setSingletonImageLoaderFactory { context ->
            ImageLoader.Builder(context)
                .components {
                    add(NetworkFetcher.Factory())
                }
                .build()
        }
        UI()
    }

}
@Composable
fun UI() {
    AppTheme {
        val navigator = rememberNavController()

        Box(modifier = Modifier.fillMaxSize()) {
            NavHost(
                navController = navigator,
                startDestination = AppNavigation.Splash.route,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = AppNavigation.Splash.route) {
                    SplashNav(navigateToMain = {
                        navigator.popBackStack()
                        navigator.navigate(AppNavigation.Main.route)
                    })
                }
//                composable(route = AppNavigation.Main.route) {
//                    MainNav {
//                        navigator.popBackStack()
//                        navigator.navigate(AppNavigation.Splash.route)
//                    }
//                }
            }
        }
    }
}