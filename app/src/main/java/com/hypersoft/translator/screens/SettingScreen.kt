package com.hypersoft.translator.screens

import android.content.Context
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hypersoft.translator.MainActivity
import com.hypersoft.translator.dataprovider.DataProvider
import com.hypersoft.translator.ui.theme.TranslatorTheme
import com.hypersoft.translator.utils.privacyPolicy
import com.hypersoft.translator.utils.rateUs
import com.hypersoft.translator.utils.shareApp

@Composable
@Preview(showSystemUi = true)
fun ProfileScreen(context: Context = LocalContext.current) {

    val settingsList = DataProvider.getSettingsData()

    TranslatorTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {

                /*items(settingsList) { settingsData ->
                    ShowSettingsList(
                        settingsData.icon, settingsData.title
                    )
                }*/

                itemsIndexed(settingsList) { index, settingsData ->
                    ShowSettingsList(
                      context, index, settingsData.icon, settingsData.title
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowSettingsList(context: Context, index: Int, mIcon: ImageVector, mTitle: String) {

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        onClick = {
            when(index){
                0 -> (context as MainActivity).rateUs()
                1 -> (context as MainActivity).shareApp()
                2 -> (context as MainActivity).privacyPolicy()
            }
        }
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = mIcon, contentDescription = "settings icon",
                modifier = Modifier.padding(10.dp, 8.dp, 8.dp, 8.dp))
            Text(
                text = mTitle,
                modifier = Modifier
                    .padding(horizontal = 8.dp)
            )
        }
    }
}