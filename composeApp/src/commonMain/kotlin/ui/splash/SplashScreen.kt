package ui.splash

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.login.LoginState
import ui.splash.view_model.LoginEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.getValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import haloapp.composeapp.generated.resources.Res
import haloapp.composeapp.generated.resources.logo
import haloapp.composeapp.generated.resources.setting
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun SplashScreen(
) {
    var startAnimation by remember { mutableStateOf(false) }

    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = androidx.compose.animation.core.tween(
            durationMillis = 2000
        )
    )
    LaunchedEffect(key1 = true) {
        startAnimation = true
        delay(3000) // Delay for splash screen
        // Navigate to the next screen
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(Res.drawable.logo), // Replace with your WebP image resource
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(150.dp)
                        .alpha(alphaAnim.value)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Welcome to HaloApp",
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.alpha(alphaAnim.value)
                )
            }
        }
    }



}