package ui.core.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults.textFieldColors
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

val Shapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

val ZERO = 0.dp
val VERY_SMALL = 5.dp
val SMALL = 10.dp
val MIDDLE_MIDDLE = 15.dp
val MIDDLE = 20.dp
val LARGE_MIDDLE = 25.dp
val LARGE = 30.dp

val NINETY_NINE = 99.dp

val LARGE_IMAGE = 96.dp
val MIDDLE_IMAGE = 48.dp

@Composable
fun DefaultImageButtonTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.primary,
    // disabledBackgroundColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary
)

@Composable
fun DefaultButtonWithBorderPrimaryTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.background,
    contentColor = MaterialTheme.colorScheme.primary,
    disabledContainerColor =  MaterialTheme.colorScheme.background,
    // disabledBackgroundColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary
)

@Composable
fun DefaultButtonTheme() = buttonColors(
    containerColor = MaterialTheme.colorScheme.primary,
    contentColor = MaterialTheme.colorScheme.background,
    // disabledBackgroundColor = MaterialTheme.colorScheme.background,
    disabledContentColor = MaterialTheme.colorScheme.primary
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultTextFieldTheme() = textFieldColors(
    focusedContainerColor = TextFieldColor,
    unfocusedContainerColor = TextFieldColor,
    cursorColor = MaterialTheme.colorScheme.onBackground,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledContainerColor = TextFieldColor,
    disabledTextColor = MaterialTheme.colorScheme.onBackground,
    disabledIndicatorColor = Color.Transparent,
)


@Composable
fun DefaultCheckBoxTheme() = CheckboxDefaults.colors(
    checkedColor = MaterialTheme.colorScheme.primary,
    uncheckedColor = MaterialTheme.colorScheme.primary,
    checkmarkColor = MaterialTheme.colorScheme.background
)


@Composable
fun DefaultNavigationBarItemTheme() = NavigationBarItemDefaults.colors(
    selectedIconColor = MaterialTheme.colorScheme.primary,
    unselectedIconColor = MaterialTheme.colorScheme.primary,
    unselectedTextColor = MaterialTheme.colorScheme.primary.copy(.7f),
    selectedTextColor = MaterialTheme.colorScheme.primary,
    indicatorColor = MaterialTheme.colorScheme.background,
)



@Composable
fun TextFieldWithTransparentTheme() = TextFieldDefaults.colors(
   // cursorColor = Color.Transparent,
    focusedIndicatorColor = Color.Transparent,
    unfocusedIndicatorColor = Color.Transparent,
    disabledIndicatorColor = Color.Transparent,
    focusedContainerColor = Color.Transparent,
    disabledContainerColor = Color.Transparent,
    unfocusedContainerColor = Color.Transparent
)
