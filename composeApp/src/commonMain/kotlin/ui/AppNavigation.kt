package ui

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cc.loac.kalo.ui.screens.login.LoginScreen
import haloapp.composeapp.generated.resources.Res
import haloapp.composeapp.generated.resources.*
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import ui.core.theme.MIDDLE

/**
 * 封装底部导航栏 Item 的密封类
 * @param route 导航到的页面
 * @param name Item 名称
 * @param icon Item 图标
 */
private sealed class Screen(
    val route: AppScreen,
    val name: String,
    val icon: DrawableResource
) {
    object Me: Screen(AppScreen.ME, "我",  Res.drawable.user)
    object Home: Screen(AppScreen.HOME, "主页", Res.drawable.home)
}

/**
 * App 页面导航及底部导航栏
 */
@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val snackBarHostState = remember { SnackbarHostState() }
    // 底部导航栏页面项
    val item = listOf(
        Screen.Home,
        Screen.Me
    )

    // 获取导航的当前路由
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRout = navBackStackEntry?.destination?.route

    Scaffold (
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        },
        bottomBar = {
            // 只有在底部导航栏的页面才显示底部导航栏
            if (currentRout in listOf(AppScreen.ME.route, AppScreen.HOME.route)) {
                NavigationBar {
                    item.forEach {
                        NavigationBarItem(
                            alwaysShowLabel = false,
                            selected = currentRout == it.route.route,
                            onClick = {
                                navController.navigate(it.route.route) {
                                    // 清空栈内到 popUpTo ID 之间的所有 Item
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            // 保存页面状态
                                            saveState = true
                                            inclusive = true
                                        }
                                    }
                                    // 避免多次点击产生多个实例
                                    launchSingleTop = true
                                    // 再次点击之前的 Item 时恢复状态
                                    restoreState = true
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(it.icon),
                                    contentDescription = it.name,
                                    modifier = Modifier.size(MIDDLE)
                                )
                            },
                            label = {
                                Text(it.name)
                            }
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = AppScreen.LOGIN.route,
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            enterTransition = {
                fadeIn()
            },
            exitTransition = {
                fadeOut()
            }
        ) {
            // 登录页面
            composable(AppScreen.LOGIN.route) {
                LoginScreen(navController)
            }
//            // 主页
//            composable(AppScreen.HOME.route) {
//                HomeScreen(navController)
//            }
//
//            // “我” 页面
//            composable(AppScreen.ME.route) {
//                MeScreen(navController)
//            }
//
//            // 插件页面
//            composable(AppScreen.PLUGIN.route) {
//                PluginScreen(navController)
//            }
        }
    }
}

/**
 * 所有页面枚举类
 */
enum class AppScreen(val route: String) {
    LOGIN("login"),
    ME("me"),
    HOME("home"),
    PLUGIN("plugin")
}