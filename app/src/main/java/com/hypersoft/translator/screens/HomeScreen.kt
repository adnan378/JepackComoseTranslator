package com.hypersoft.translator.screens

import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.FileCopy
import androidx.compose.material.icons.filled.KeyboardVoice
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.rounded.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hypersoft.translator.R
import com.hypersoft.translator.database.TranslationHistory
import com.hypersoft.translator.dropdowns.SpinnerRows
import com.hypersoft.translator.dropdowns.getLanguageList
import com.hypersoft.translator.enums.WhichLang
import com.hypersoft.translator.extentions.startTranslation
import com.hypersoft.translator.extentions.toasty
import com.hypersoft.translator.speechrecognizer.SpeechRecognizerContract
import com.hypersoft.translator.ui.theme.TranslatorTheme
import com.hypersoft.translator.utils.shareText
import com.hypersoft.translator.viewmodels.DatabaseViewModel
import com.hypersoft.translator.viewmodels.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Preview(showSystemUi = true)
@Composable
fun HomeScreen(
    context: Context = LocalContext.current,
    historyViewModel: DatabaseViewModel = hiltViewModel(),
    viewModel: MainViewModel = viewModel(),
    clipboardManager: ClipboardManager = LocalClipboardManager.current
) {

    var secondLanguage by remember { mutableStateOf(getLanguageList()[0]) }
    var text by rememberSaveable { mutableStateOf("") }
    var textTranslated by rememberSaveable { mutableStateOf("") }
    val showProgressBar = remember { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val state = viewModel.state.value

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract(),
        onResult = {
            Log.d("testing_text", "$it")
            it?.let { resultText ->
                text = resultText[0]
            } ?: run {
                context.toasty("sorry speech did not recognized")
            }
        }
    )

    TranslatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp, 0.dp, 15.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {

                SpinnerRows { whichLang, name ->
                    if (whichLang == WhichLang.Default) {
//                        firstLanguage = name
                    } else {
                        secondLanguage = name
                    }
                }
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight()
//                        .weight(1f)
//                        .padding(horizontal = 15.dp, vertical = 10.dp),
//                    //set card elevation of the card
//                    elevation = CardDefaults.cardElevation(
//                        defaultElevation = 4.dp,
//                    ),
//                    content = {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(horizontal = 0.dp, vertical = 4.dp)
                            .clip(MaterialTheme.shapes.large)
                            .background(
                                colorResource(R.color.cards_color),
                                // rounded corner to match with the OutlinedTextField
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(14.dp, 2.dp, 14.dp, 2.dp)
                            ) {
                                Text(
                                    text = "Auto",
                                )
                                IconButton(
                                    onClick = {
                                        if (text.isNotEmpty()) {
                                            viewModel.textToSpeech(context, text)
                                        } else {
                                            context.toasty("write something")
                                        }
                                    },
                                    modifier = Modifier.padding(
                                        horizontal = 14.dp,
                                        vertical = 0.dp
                                    ),
                                    enabled = state.isButtonEnabled
                                ) {
                                    Icon(
                                        Icons.Rounded.VolumeUp,
                                        contentDescription = "Text to speech"
                                    )
                                }
                                /*Icon(
                                    Icons.Rounded.VolumeUp,
                                    contentDescription = "Text to speech",
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 0.dp)
                                )*/
                                Spacer(modifier = Modifier.weight(1f))
                                IconButton(onClick = {
                                    if (text.isNotEmpty()) {
                                        text = ""
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Cancel,
                                        contentDescription = "Cancel Icon"
                                    )
                                }
                            }
                            TextField(
                                value = text,
                                onValueChange = { mText: String ->
                                    text = mText
                                },
                                placeholder = { Text("Enter text") },
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                                keyboardActions = KeyboardActions(
                                    onDone = { keyboardController?.hide() }),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f),
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(vertical = 5.dp)
                            ) {
                                IconButton(
                                    onClick = {
                                        speechRecognizerLauncher.launch(Unit)
                                    },
                                    modifier = Modifier.padding(14.dp, 0.dp, 0.dp, 0.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.KeyboardVoice,
                                        contentDescription = "speech to text"
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        clipboardManager.getText()?.text?.let {
                                            text = it
                                        } ?: run {
                                            context.toasty("clipboard is empty")
                                        }
                                    }
                                ) {
                                    Icon(
                                        Icons.Filled.ContentPaste,
                                        contentDescription = "speech to text"
                                    )
                                }

                                Spacer(modifier = Modifier.weight(1f))
                                Button(onClick = {
                                    if (text.isNotEmpty()) {
                                        showProgressBar.value = true
                                        keyboardController?.hide()
                                        /*(context as MainActivity).hideKeyboard()*/
                                        context.startTranslation(
                                            text,
                                            secondLanguage.langCode
                                        ) { resultText ->
                                            showProgressBar.value = false
                                            textTranslated = resultText

                                            insertDataToDatabase(
                                                historyViewModel,
                                                "Auto",
                                                secondLanguage.title,
                                                text,
                                                textTranslated
                                            )
                                        }
                                    } else {
                                        context.toasty("write something to translate it")
                                    }
                                }, modifier = Modifier.padding(0.dp, 0.dp, 14.dp, 0.dp)) {
                                    Text(text = "Translate")
                                }
                            }
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .weight(1f)
                            .padding(horizontal = 0.dp, vertical = 4.dp)
                            .clip(MaterialTheme.shapes.large)
                            .background(
                                colorResource(R.color.cards_color),
                                // rounded corner to match with the OutlinedTextField
                                shape = RoundedCornerShape(4.dp)
                            )
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            if (showProgressBar.value) {
                                LinearProgressIndicator(
                                    modifier = mModifiers()
                                )
                            } else {
                                Spacer(modifier = Modifier.height(5.dp))
                            }
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        horizontal = 14.dp,
                                        vertical = 9.dp
                                    )
                            ) {
                                Text(
                                    text = secondLanguage.title,
                                )
                                Icon(
                                    Icons.Rounded.VolumeUp,
                                    contentDescription = stringResource(id = R.string.swipe_image_desc),
                                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 0.dp)
                                )
                            }
                            Text(
                                text = textTranslated,
                                Modifier
                                    .weight(1f)
                                    .padding(horizontal = 14.dp, vertical = 0.dp)
                            )
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.End,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 0.dp, 10.dp, 10.dp)
                            ) {

                                IconButton(onClick = {
                                    if (textTranslated.isNotEmpty()) {
                                        context.shareText(textTranslated)
                                    } else {
                                        context.toasty("first translate something")
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Share,
                                        contentDescription = "Share Icon"
                                    )
                                }
                                IconButton(onClick = {
                                    clipboardManager.setText(AnnotatedString((text)))
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.FileCopy,
                                        contentDescription = "Copy Icon"
                                    )
                                }
                            }
                        }
                    }
                }
//                    }
//                )
                /*Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                        .clip(MaterialTheme.shapes.large)
                ) {
                    Image(
                        painter = painterResource(R.drawable.images),
                        contentDescription = "home_screen_bg",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                Text(
                    "Home Screen",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(vertical = 20.dp)
                )*/
            }
        }
    }
}

@Composable
private fun mModifiers() = Modifier
    .fillMaxWidth()
    .height(5.dp)

@Composable
private fun CustomLinearProgressBar() {
    Column(modifier = Modifier.fillMaxWidth()) {
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .height(15.dp)
        )
    }
}

fun insertDataToDatabase(
    dbViewModel: DatabaseViewModel,
    firstLang: String,
    secondLang: String,
    firstText: String,
    secondText: String
) {
    val history = TranslationHistory(0, firstLang, secondLang, firstText, secondText)
    dbViewModel.insertHistory(history)
    /*val longValue =  dbViewModel.insertHistory(history)
      Log.d("testing_insertion", "$longValue")*/
}

