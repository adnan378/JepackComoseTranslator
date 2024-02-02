package com.hypersoft.translator.dropdowns

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.rounded.SwapHoriz
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.hypersoft.translator.R
import com.hypersoft.translator.data.FirstLang
import com.hypersoft.translator.enums.WhichLang
import com.hypersoft.translator.extentions.toasty

/*val languages = arrayOf("English", "Urdu", "Hindi", "Chinese", "Japanese")*/

fun getLanguageList(): ArrayList<FirstLang> {
    val mList = ArrayList<FirstLang>().apply {
        add(FirstLang("Arabic", "ar"))
        add(FirstLang("urdu", "ur"))
        add(FirstLang("Hindi", "hi"))
        add(FirstLang("Chinese", "zh-CN"))
        add(FirstLang("English", "en"))
        add(FirstLang("French", "fr"))
    }
    return mList
}

@Composable
fun SpinnerRows(whichLang: (WhichLang, FirstLang) -> Unit) {
    Box {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 5.dp),
            //set card elevation of the card
            elevation = CardDefaults.cardElevation(
                defaultElevation = 4.dp,
            ),
            content = {
                Row {
                    LanguageFirst(whichLang)
                }
            }
        )
    }
}

@Composable
fun LanguageFirst(selectedLang: (WhichLang, FirstLang) -> Unit) {
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var secondLanguage by remember { mutableStateOf(getLanguageList()[0].title) }

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_language),
                    contentDescription = "first language",
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp)
                )
                ClickableText(
                    text = AnnotatedString("Auto"),
                    modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                    onClick = {
                        /*isFirstLang = true
                        expanded = !expanded*/
                        context.toasty("Not changeable")
                    }
                )
            }
            Icon(
                Icons.Rounded.SwapHoriz,
                contentDescription = stringResource(id = R.string.swipe_image_desc),
                modifier = Modifier.padding(5.dp)
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(0.dp, 0.dp, 10.dp, 0.dp),
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 10.dp, 0.dp),
                ) {
                    Icon(imageVector = Icons.Default.GTranslate, contentDescription = "second icon")
                    ClickableText(
                        text = AnnotatedString(secondLanguage),
                        modifier = Modifier.padding(10.dp, 0.dp, 0.dp, 0.dp),
                        onClick = {
                            expanded = !expanded
                        }
                    )
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    getLanguageList().forEach { currentLanguage ->
                        DropdownMenuItem(
                            text = { Text(currentLanguage.title) },
                            onClick = {
                                expanded = !expanded
                                secondLanguage = currentLanguage.title
                                selectedLang.invoke(WhichLang.Translated, currentLanguage)
                            }
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerOne() {

    val context = LocalContext.current
    val languages = arrayOf("English", "Urdu", "Hindi", "Chinese", "Japanese")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(languages[0]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {

        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .padding(8.dp)
                .fillMaxWidth(0.48f)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpinnerTwo() {
    val context = LocalContext.current
    val languages = arrayOf("English", "Urdu", "Hindi", "Chinese", "Japanese")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf(languages[1]) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = {
            expanded = !expanded
        }
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier
                .menuAnchor()
                .padding(8.dp)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            languages.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        selectedText = item
                        expanded = false
                        Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }
}


