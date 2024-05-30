package cc.loac.kalo.ui.screens.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import core.UIComponent
import common.formatURL
import core.JAlertResponse
import haloapp.composeapp.generated.resources.Res
import haloapp.composeapp.generated.resources.eye
import haloapp.composeapp.generated.resources.eye_slash
import haloapp.composeapp.generated.resources.logo
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import ui.core.components.ProgressAlert
import ui.login.LoginViewModel
import ui.core.theme.LARGE
import ui.core.theme.MIDDLE
import ui.core.theme.SMALL

/**
 * 登录页面
 */
@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel()
) {
    var url by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }



    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Title()
        Inputs(
            urlValue = url,
            onUrlChange = { url = it },
            usernameValue = username,
            onUsernameChange = { username = it },
            passwordValue = password,
            onPasswordChange = { password = it }
        )
        LoginBtn(
            url = url,
            username = username,
            password = password,
            loginViewModel = loginViewModel,
            navController = navController
        )
        BottomTips()
    }
}

/**
 * Kalo 大字标题
 */
@Composable
private fun Title() {
    // 控制动画的状态，从 false 变为 true，让动画在开屏就执行
    val state = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    AnimatedVisibility(
        visibleState = state,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = 500
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = LARGE * 2, bottom = LARGE * 2),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(Res.drawable.logo),
                contentDescription = "Kalo",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(150.dp)
            )
        }
    }

}

/**
 * 输入框组
 * @param urlValue Halo 站点地址数据
 * @param onUrlChange Halo 站点地址改变事件
 * @param usernameValue 用户名数据
 * @param onUsernameChange 用户名改变事件
 * @param passwordValue 密码数据
 * @param onPasswordChange 密码改变事件
 */
@Composable
private fun Inputs(
    urlValue: String,
    onUrlChange: (String) -> Unit,
    usernameValue: String,
    onUsernameChange: (String) -> Unit,
    passwordValue: String,
    onPasswordChange: (String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = MIDDLE, end = MIDDLE, bottom = MIDDLE)
    ) {
        Input(
            label = "Halo 站点地址",
            placeholder = "URL 或 IP 地址",
            value = urlValue,
            onValueChange = onUrlChange
        )

        Input(
            label = "用户名",
            placeholder = "Halo 用户名",
            value = usernameValue,
            onValueChange = onUsernameChange
        )

        Input(
            label = "密码",
            placeholder = "Halo 登录密码",
            value = passwordValue,
            onValueChange = onPasswordChange,
            isPassword = true
        )
    }
}

/**
 * 输入框
 * @param label 输入框标签
 * @param placeholder 输入框占位符（提示）
 * @param value 输入框值
 * @param onValueChange 输入框内容改变事件
 * @param isPassword 是否是密码框
 */
@Composable
private fun Input(
    label: String,
    placeholder: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPassword: Boolean = false
) {
    // 是否显示密码
    var showPassword by remember { mutableStateOf(false) }

    OutlinedTextField(
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        value = value,
        onValueChange = onValueChange,
        visualTransformation = if (isPassword && !showPassword) {
            PasswordVisualTransformation()
        } else {
            VisualTransformation.None
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(SMALL),
        trailingIcon = {
            if (isPassword) {
                IconButton(onClick = { showPassword = !showPassword }) {
                    Icon(
                        painter = if (showPassword) painterResource(Res.drawable.eye)
                            else painterResource(Res.drawable.eye_slash),
                        contentDescription = "显示密码",
                        tint = if (showPassword) MaterialTheme.colorScheme.primary
                            else LocalContentColor.current,
                        modifier = Modifier.size(MIDDLE)

                    )
                }

            }
        },
        shape = CardDefaults.shape
    )
}

/**
 * 登录按钮
 * @param url Halo 站点地址
 * @param username Halo 用户名
 * @param password Halo 密码
 * @param loginViewModel ViewModel
 */
@Composable
private fun LoginBtn(
    url: String,
    username: String,
    password: String,
    loginViewModel: LoginViewModel,
    navController: NavController
) {
    // 协程作用域
    val scope = rememberCoroutineScope()

    // 动画状态
    val animateState = remember {
        MutableTransitionState(false).apply {
            targetState = true
        }
    }

    // 是否显示对话框
    var showAlert by remember { mutableStateOf(false) }
    var alertTitle by remember { mutableStateOf("") }
    var alertText by remember { mutableStateOf("") }

    // 是否显示正在登录对话框
    var showLoadingAlert by remember { mutableStateOf(false) }

    // 登录状态
    val loginStatus by loginViewModel.state



    AnimatedVisibility(
        visibleState = animateState,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = 500
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(start = LARGE, end = LARGE)
        ) {
            Button(
                onClick = {
                    // 有空白信息
                    if (url.isEmpty() || username.isEmpty() || password.isEmpty()) {
                        alertTitle = "登录失败"
                        alertText = "请将信息输入完整"
                        showAlert = true
                        return@Button
                    }

                    // 判断并格式化网址
                    val formatUrl = url.formatURL()
                    if (formatUrl.isEmpty()) {
                        alertTitle = "登录失败"
                        alertText = "站点地址有误，请重新输入，\n" +
                                "URL 需要加上 https:// 或 http://"
                        showAlert = true
                        return@Button
                    }

                    showLoadingAlert = true
                    // 启动协程处理登录操作
                    scope.launch {
                        UIComponent.Toast(JAlertResponse("正在登录","开发中"))
                        // 尝试登录
//                        loginViewModel.login(formatUrl, username, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = CardDefaults.shape
            ) {
                Text("登录")
            }
        }
    }

    // 显示对话框话框
    if (showAlert) {
        // 显示对话框前先确认
        showAlert = false
        "正在登录...".ProgressAlert(5000) {
            showAlert = false
        }
    }

//
//    // 登录状态改变
//    if (loginStatus.isNotNone()) {
//        if (loginStatus.isSuccessful()) {
//            // 登录成功
//            // 进入底部导航页面
//            showLoadingAlert = false
//            navController.navigate(AppScreen.ME.route) {
//                popUpTo(AppScreen.LOGIN.route) {
//                    inclusive = true
//                }
//            }
//        } else {
//            // 登录失败，弹出对话框
//            alertTitle = "登录失败"
//            alertText = loginStatus.errMsg
//            showAlert = true
//        }
//
//    }

    // 显示正在登录对话框
    if (showLoadingAlert) {
        "正在登录...".ProgressAlert(5000) {
            showLoadingAlert = false
        }
    }
}

/**
 * 页面底部提示按钮
 */
@Composable
private fun BottomTips() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextButton(
            modifier = Modifier.padding(bottom = 5.dp),
            onClick = {}
        ) {
            Text(
                text = "支持 Halo 2.0 +",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.secondary,
            )
        }
    }
}

