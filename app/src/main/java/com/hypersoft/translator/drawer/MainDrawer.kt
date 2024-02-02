package com.hypersoft.translator.drawer

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hypersoft.translator.scaffold_pattern.ScaffoldPattern
import com.hypersoft.translator.data.DrawerMenu
import com.hypersoft.translator.utils.menus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Preview(showSystemUi = true)
@Composable
fun ComposeDrawer(
    context: Context = LocalContext.current,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {

    /* Drawer in compose */
    ModalNavigationDrawer(
        drawerState = drawerState,
        scrimColor = Color.Transparent,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 60.dp, 0.dp)
                    .fillMaxHeight()
            ) {
                /* procedure one to write it here */
              //  DrawerContent(context, coroutineScope, drawerState)

                /* procedure Two */
                DrawerContent(menus) {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    /* it for fragment navigaition from drawer */
                   // navController.navigate(it)
                }
            }
        }) {

        ScaffoldPattern {
            coroutineScope.launch {
                drawerState.apply {
                    if (isClosed) open() else close()
                }
            }
        }
    }
}



@Composable
private fun DrawerContent(
    menus: Array<DrawerMenu>,
    onMenuClick: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.size(130.dp),
                imageVector = Icons.Filled.AccountCircle,
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
        menus.forEach {
            NavigationDrawerItem(
                label = { Text(text = it.title) },
                icon = { Icon(imageVector = it.icon, contentDescription = null) },
                selected = false,
                onClick = {
                    onMenuClick(it.route)
                }
            )
        }
    }
}


@Composable
private fun DrawerContent(
    context: Context,
    coroutineScope: CoroutineScope,
    drawerState: DrawerState
) {
    Text(
        text = "Translator",
        modifier = Modifier.padding(16.dp)
    )
    Divider()
    NavigationDrawerItem(
        label = { Text(text = "Close Drawer") },
        selected = false,
        onClick = {
            coroutineScope.launch {
                drawerState.close()
            }
        })
}
