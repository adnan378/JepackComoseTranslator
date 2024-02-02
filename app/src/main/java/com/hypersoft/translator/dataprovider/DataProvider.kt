package com.hypersoft.translator.dataprovider

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.StarRate
import com.hypersoft.translator.data.SettingsData

object DataProvider {

    fun getSettingsData() : ArrayList<SettingsData>{
        val list = ArrayList<SettingsData>().apply {
            add(SettingsData(Icons.Default.StarRate, "Rate Us"))
            add(SettingsData(Icons.Default.Share, "Share App"))
            add(SettingsData(Icons.Default.PrivacyTip, "Privacy Policy"))
        }

        return list
    }

}