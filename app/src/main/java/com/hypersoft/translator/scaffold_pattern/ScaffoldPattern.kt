package com.hypersoft.translator.scaffold_pattern

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hypersoft.translator.MainActivity
import com.hypersoft.translator.data.sealed.Screens
import com.hypersoft.translator.dialogues.ExitDialog
import com.hypersoft.translator.extentions.HandleBackPress
import com.hypersoft.translator.extentions.toasty
import com.hypersoft.translator.scaffold_pattern.data.BottomNavigationItem
import com.hypersoft.translator.screens.HomeScreen
import com.hypersoft.translator.screens.ProfileScreen
import com.hypersoft.translator.screens.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldPattern(openDrawer: () -> Unit) {
    val mContext: Context = LocalContext.current
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Translator")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        openDrawer()
                    }) {
                        Icon(
                            // internal hamburger menu
                            Icons.Rounded.Menu,
                            contentDescription = "MenuButton"
                        )
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar {
                BottomNavigationItem().bottomNavigationItems().forEachIndexed { _, navigationItem ->
                    NavigationBarItem(
                        selected = navigationItem.route == currentDestination?.route,
                        label = {
                            Text(navigationItem.label)
                        },
                        icon = {
                            Icon(
                                navigationItem.icon,
                                contentDescription = navigationItem.label
                            )
                        },
                        onClick = {
                            navController.navigate(navigationItem.route) {
                                /* Pop up to the start destination of the graph to
                                avoid building up a large stack of destinations
                                on the back stack as users select items */
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                /* Avoid multiple copies of the same destination when re_selecting the same item */
                                launchSingleTop = true
                                /* Restore state when re_selecting a previously selected item */
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        val openDialog = remember { mutableStateOf(false) }
        HandleBackPress {
            if (navController.currentDestination?.route == Screens.Home.route) {
//                mContext.toasty("show exit dialog")
                openDialog.value = true
            } else {
                navController.popBackStack()
            }
        }
        if (openDialog.value) {
            ExitDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                onConfirmation = {
                    openDialog.value = false
                    (mContext as MainActivity).finishAffinity()
                })
        }
        NavHost(
            navController = navController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(paddingValues = paddingValues)
        ) {
            composable(Screens.Home.route) {
                HomeScreen()
            }
            composable(Screens.History.route) {
                SearchScreen()
            }
            composable(Screens.Settings.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
fun AlertDialogSample(openDialog: Boolean, showDialog: (Boolean) -> Unit) {
    if (openDialog) {
        AlertDialog(
            onDismissRequest = {
                showDialog.invoke(false)
            },
            title = {
                Text(text = "Dialog Title Will Show Here")
            },
            text = {
                Text("Here is a description text of the dialog")
            },
            confirmButton = {
                Button(

                    onClick = {
                        showDialog.invoke(false)
                    }) {
                    Text("Confirm Button")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        showDialog.invoke(false)
                    }) {
                    Text("Dismiss Button")
                }
            }
        )
    }
}
