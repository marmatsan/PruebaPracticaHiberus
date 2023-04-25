package com.example.androidtechnicaltestcanary.ui

import android.content.Intent
import android.os.Build
import android.util.Patterns
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidtechnicaltestcanary.R
import com.example.androidtechnicaltestcanary.activities.MasterActivity
import com.example.androidtechnicaltestcanary.models.TextFieldState
import com.example.androidtechnicaltestcanary.utils.InputError
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LoginScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(18.dp)
    ) {
        Login(modifier = Modifier.align(Alignment.Center))
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Login(modifier: Modifier) {
    val calendarState = rememberUseCaseState()

    val textName = remember { TextFieldState() }
    val textPassword = remember { TextFieldState() }
    val textEmail = remember { TextFieldState() }

    val textDate = remember { mutableStateOf(value = String()) }

    val textError = remember { mutableStateOf(value = true) }
    val passwordError = remember { mutableStateOf(value = true) }
    val emailError = remember { mutableStateOf(value = true) }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderImage(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(
            modifier = Modifier.padding(16.dp)
        )
        CustomTextField(
            textState = textName,
            iconId = Icons.Default.Person,
            hasError = textError,
            hint = R.string.label_user,
            isInputText = true
        )
        Spacer(
            modifier = Modifier.padding(12.dp)
        )
        CustomTextField(
            textState = textPassword,
            iconId = Icons.Default.Lock,
            hasError = passwordError,
            hint = R.string.label_password,
            isPassword = true
        )
        Spacer(
            modifier = Modifier.padding(12.dp)
        )
        CustomTextField(
            textState = textEmail,
            iconId = Icons.Default.Email,
            hasError = emailError,
            hint = R.string.label_email,
            isEmail = true

        )
        CustomCalendarDialog(state = calendarState, selectedDate = textDate)
        Spacer(
            modifier = Modifier.padding(12.dp)
        )
        CustomTextField(
            iconId = Icons.Default.DateRange,
            hint = R.string.label_date,
            isDatePicker = true,
            calendarState = calendarState,
            selectedDate = textDate
        )
        Spacer(
            modifier = Modifier.padding(12.dp)
        )
        CustomButton(
            textError = textError.value,
            passwordError = passwordError.value,
            emailError = emailError.value
        )
    }
}

@Composable
fun HeaderImage(modifier: Modifier) {
    Image(
        painter = painterResource(id = R.drawable.img_header_logo),
        contentDescription = "Header Logo",
        modifier = modifier
            .width(300.dp)
            .height(120.dp)
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    textState: TextFieldState = remember {
        TextFieldState()
    },
    iconId: ImageVector,
    @StringRes hint: Int,
    isInputText: Boolean = false,
    isPassword: Boolean = false,
    isEmail: Boolean = false,
    isDatePicker: Boolean = false,
    hasError: MutableState<Boolean> = remember { mutableStateOf(value = false) },
    calendarState: UseCaseState = UseCaseState(),
    selectedDate: MutableState<String> = mutableStateOf(value = String())
) {
    var isPasswordVisible by remember { mutableStateOf(value = false) }
    var error by remember { mutableStateOf(value = InputError.NO_ERROR) }

    val onValueChangeDate: (String) -> Unit = {}
    val onValueChangeText: (String) -> Unit = {
        textState.value = it

        val text = textState.value

        error = if (isInputText) {
            if (text.isEmpty()) {
                InputError.EMPTY_NAME
            } else if (text.trim().split("\\s+".toRegex()).size < 2) {
                InputError.SHORT_NAME
            } else {
                InputError.NO_ERROR
            }
        } else if (isPassword) {
            if (text.isEmpty()) {
                InputError.EMPTY_PASSWORD
            } else if (text.length < 6) {
                InputError.SHORT_PASSWORD
            } else {
                InputError.NO_ERROR
            }
        } else if (isEmail) {
            if (text.isEmpty()) {
                InputError.EMPTY_EMAIL
            } else if (!Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                InputError.INCORRECT_EMAIL_FORMAT
            } else {
                InputError.NO_ERROR
            }
        } else if (isDatePicker) {
            if (text.isEmpty()) {
                InputError.EMPTY_DATE
            } else {
                InputError.NO_ERROR
            }
        } else {
            InputError.NO_ERROR
        }
        hasError.value = error.isError()
    }

    OutlinedTextField(
        value = if (isDatePicker) selectedDate.value else textState.value,
        label = {
            Text(text = stringResource(id = hint))
        },
        onValueChange = if (!isDatePicker) onValueChangeText else onValueChangeDate,
        leadingIcon = {
            if (!isDatePicker) {
                Icon(
                    painter = rememberVectorPainter(image = iconId),
                    contentDescription = "Icon ID",
                    modifier = Modifier
                        .height(40.dp)
                )
            }
        },
        trailingIcon = {
            if (isPassword) {
                Icon(
                    painter = painterResource(
                        id = if (!isPasswordVisible)
                            R.drawable.ic_visible_on
                        else
                            R.drawable.ic_visible_off
                    ),
                    contentDescription = "User Logo",
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                            isPasswordVisible = !isPasswordVisible
                        }
                )
            } else if (isDatePicker) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Default.DateRange),
                    contentDescription = "User Logo",
                    modifier = Modifier
                        .height(40.dp)
                        .clickable {
                            calendarState.show()
                        }
                )
            } else if (error.isError()) {
                Icon(
                    painter = rememberVectorPainter(image = Icons.Filled.Warning),
                    contentDescription = "error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        },
        visualTransformation = if (!isPassword)
            VisualTransformation.None
        else if (isPasswordVisible)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        isError = error.isError(),
        supportingText = {
            if (error.isError()) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = error.getErrorMessage(),
                    color = MaterialTheme.colorScheme.error
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun CustomButton(
    textError: Boolean,
    passwordError: Boolean,
    emailError: Boolean
) {
    val context = LocalContext.current

    Button(
        onClick = {
            if (textError || passwordError || emailError) {
                Toast.makeText(
                    context,
                    "Resuelve los errores antes de continuar",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                context.startActivity(Intent(context, MasterActivity::class.java))
            }
        },
        modifier = Modifier.wrapContentSize(),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.hsv(
                hue = 356F,
                saturation = 0.84F,
                value = 0.85F
            )
        )
    ) {
        Text(text = "ACCEDER")
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCalendarDialog(state: UseCaseState, selectedDate: MutableState<String>) {
    CalendarDialog(
        state = state,
        config = CalendarConfig(
            yearSelection = true,
            monthSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { date ->
            selectedDate.value = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@MyCustomPreviews
@Composable
fun PreviewLoginScreen() {
    LoginScreen()
}

@Preview(showSystemUi = true, name = "NEXUS_10", device = Devices.NEXUS_10)
@Preview(showSystemUi = true, name = "NEXUS_5", device = Devices.NEXUS_5)
@Preview(showSystemUi = true, name = "NEXUS_5X", device = Devices.NEXUS_5X)
@Preview(showSystemUi = true, name = "NEXUS_7", device = Devices.NEXUS_7)
@Preview(showSystemUi = true, name = "FOLDABLE", device = Devices.FOLDABLE)
@Preview(showSystemUi = true, name = "PIXEL_XL", device = Devices.PIXEL_XL)
annotation class MyCustomPreviews