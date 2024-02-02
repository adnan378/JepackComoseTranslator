package com.hypersoft.translator.extentions

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import com.hypersoft.translator.interfaces.SpeechText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.http.HttpResponse
import org.apache.http.StatusLine
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.json.JSONArray
import org.jsoup.Jsoup
import java.io.ByteArrayOutputStream


@Composable
fun HandleBackPress(navigateBack: () -> Unit){
    BackHandler {
        navigateBack.invoke()
    }
}

var speechText: SpeechText?=null

fun Context.toasty(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Context.startTranslation(text: String, toLanguage: String, resultText: (String) -> Unit){

    val handler = CoroutineExceptionHandler { _, exception ->
        Log.e("Translator_Tag", "$exception")
    }

    CoroutineScope(Dispatchers.IO+handler).launch {
        /*
        Jsoup is an open source Java library used mainly for extracting data from HTML.
         It also allows you to manipulate and output HTML.It provides a very convenient API
         for extracting and manipulating data, using the best of DOM, CSS, and jquery-like methods.

         How do you use kotlin jsoup?
         Using jsoup with Kotlin to parse HTML
         Fetch HTML response from a URL.
         Parse it and scrape information from it.
         Dump it somewhere.
         */
        val doc = Jsoup.connect(
            "https://translate.google.com/m?hl=en" +
                    "&sl=auto" +
                    "&tl=$toLanguage" +
                    "&ie=UTF-8&prev=_m" +
                    "&q=$text"
        ).timeout(6000).get()

        withContext(Dispatchers.Main) {
            val element = doc.getElementsByClass("result-container")
            val textIs: String
            if (element.isNotEmpty()) {
                textIs = element[0].text()
                resultText.invoke(textIs)
            }else{
                toasty("response is null")
            }
        }
    }
}