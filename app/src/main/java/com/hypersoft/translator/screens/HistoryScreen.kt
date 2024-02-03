package com.hypersoft.translator.screens

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GTranslate
import androidx.compose.material.icons.rounded.Cancel
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hypersoft.translator.R
import com.hypersoft.translator.database.TranslationHistory
import com.hypersoft.translator.extentions.toasty
import com.hypersoft.translator.viewmodels.DatabaseViewModel

@Composable
fun SearchScreen(
    context: Context = LocalContext.current,
    historyViewModel: DatabaseViewModel = hiltViewModel()
) {
    val historyList by historyViewModel.readAllData.observeAsState()
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(16.dp)
    ) {
        historyList?.let {
            if (it.isNotEmpty()) {
                items(it) { historyItem ->
                    HistoryList(
                        historyViewModel,
                        historyItem,
                        historyItem.firstLanguage,
                        historyItem.secondLanguage,
                        historyItem.firstText,
                        historyItem.secondText
                    )
                }
            } else {
                context.toasty("data not found")
            }
        }
    }
}

@Composable
fun HistoryList(
    historyViewModel: DatabaseViewModel,
    historyItem: TranslationHistory,
    firstLang: String = "FirstLang",
    secondLang: String = "Second Lang",
    firstText: String = "First Text",
    secondText: String = "Second Text"
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row {

                Image(
                    painter = painterResource(id = R.drawable.icon_language),
                    contentDescription = "first language",
                )

                Text(
                    text = firstLang,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {
                        historyViewModel.deleteHistory(historyItem)
                    },
                    modifier = Modifier.size(22.dp)
                ) {
                    Icon(
                        Icons.Rounded.Cancel,
                        contentDescription = "Text to speech"
                    )
                }
            }
            Text(
                text = firstText,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            Divider(
                color = Color.Blue, thickness = 1.dp,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            Row {
                Icon(imageVector = Icons.Default.GTranslate, contentDescription = "second icon")
                Text(
                    text = secondLang,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
            Text(
                text = secondText,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
        }
    }
}