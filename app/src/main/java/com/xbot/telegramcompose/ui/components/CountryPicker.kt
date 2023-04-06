package com.xbot.telegramcompose.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.xbot.telegramcompose.ui.theme.elevation
import com.xbot.telegramcompose.ui.utils.*

@Composable
fun CountryPicker(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    enabled: Boolean = true,
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit
) {
    val countryPickerUtils = rememberCountryPickerUtils()
    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    var lastTextValue by remember(value) { mutableStateOf(value) }

    val selectedCountry = countryPickerUtils.getCountryFromNumber(textFieldValueState.text)
    val selectedCountryState by remember(selectedCountry) {
        derivedStateOf { countryPickerUtils.getCountryFromNumber(textFieldValueState.text) }
    }

    CountryPickerContent(
        modifier = modifier,
        selectedCountry = selectedCountryState,
        label = label,
        placeholder = placeholder,
        enabled = enabled,
        onSelection = { country ->
            val newValue = countryPickerUtils.changeNumberCountryCode(
                numberToParse = textFieldValueState.text,
                countryCode = country.code
            )
            textFieldValueState = TextFieldValue(
                text = newValue,
                selection = TextRange(newValue.length)
            )
        },
        value = textFieldValueState,
        onValueChange = { newTextFieldValueState ->
            textFieldValueState = formatPhoneNumber(newTextFieldValueState)

            val stringChangedSinceLastInvocation = lastTextValue != newTextFieldValueState.text
            lastTextValue = newTextFieldValueState.text

            if (stringChangedSinceLastInvocation) {
                onValueChange(newTextFieldValueState.text)
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryPickerContent(
    modifier: Modifier = Modifier,
    selectedCountry: Country,
    label: @Composable () -> Unit,
    placeholder: @Composable () -> Unit,
    onSelection: (Country) -> Unit,
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    enabled: Boolean,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val countries = remember { getCountriesList().toMutableStateList() }

        CountryPickerButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            selectedCountry = selectedCountry,
            countries = countries,
            onSelection = onSelection
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            label = label,
            placeholder = placeholder,
            visualTransformation = phoneNumberVisualTransformation(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Next
            ),
            singleLine = true
        )
    }
}

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun CountryPickerButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selectedCountry: Country,
    countries: List<Country>,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onSelection: (Country) -> Unit,
) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier
            .height(CountryPickerHeight)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = enabled,
                role = Role.Button
            ) {
                showDialog = true
            },
        shape = MaterialTheme.shapes.extraSmall,
        tonalElevation = MaterialTheme.elevation.level2
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AnimatedContent(
                modifier = Modifier.weight(1f),
                targetState = selectedCountry
            ) { country ->
                ListItem(
                    headlineText = {
                        Text(
                            text = country.fullName,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    },
                    leadingContent = {
                        Text(text = country.getFlagEmoji())
                    }
                )
            }
            IconButton(
                modifier = Modifier.padding(horizontal = 3.dp),
                onClick = { showDialog = true },
                interactionSource = interactionSource,
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = ""
                )
            }
        }
    }

    if (showDialog) {
        CountryCodePickerDialog(
            countries = countries,
            onSelection = onSelection
        ) {
            showDialog = false
        }
    }
}

@Composable
private fun CountryCodePickerDialog(
    modifier: Modifier = Modifier,
    countries: List<Country>,
    onSelection: (Country) -> Unit,
    onDismiss: () -> Unit
) {
    DialogWithAppbar(
        modifier = modifier,
        title = {
            Text(text = "Choose a country")
        },
        onDismissRequest = onDismiss
    ) {
        items(items = countries) { country ->
            CountryPickerListItem(country = country) {
                onSelection(country)
                onDismiss()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CountryPickerListItem(
    modifier: Modifier = Modifier,
    country: Country,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        onClick = onClick
    ) {
        ListItem(
            headlineText = {
                Text(
                    text = country.fullName,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            },
            leadingContent = {
                Text(text = country.getFlagEmoji())
            },
            trailingContent = {
                Text(
                    text = "+${country.code}",
                    color = MaterialTheme.colorScheme.primary
                )
            }
        )
    }
}

private fun formatPhoneNumber(textFieldValueState: TextFieldValue): TextFieldValue {
    val text = textFieldValueState.text
    return if (text.isNotEmpty() && !text.startsWith("+")) {
        val value = "+${text}"
        textFieldValueState.copy(
            text = value,
            selection = TextRange(value.length)
        )
    } else {
        textFieldValueState
    }
}

@Composable
private fun rememberCountryPickerUtils(): CountryPickerUtils {
    val context = LocalContext.current
    return remember(context) { CountryPickerUtils.getInstance(context) }
}

private val CountryPickerHeight = 56.dp