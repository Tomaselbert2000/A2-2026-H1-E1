package ar.edu.unlam.mobile.scaffolding

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import ar.edu.unlam.mobile.scaffolding.ui.screens.enums.AppScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.feed.FeedScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.login.LoginScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.post.PostCreationScreen
import ar.edu.unlam.mobile.scaffolding.ui.screens.register.RegisterScreen
import ar.edu.unlam.mobile.scaffolding.ui.theme.ScaffoldingV2Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            var appScreen by remember { mutableStateOf(AppScreen.LOGIN) }

            ScaffoldingV2Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {

                    when (appScreen) {

                        AppScreen.LOGIN -> {

                            LoginScreen(
                                hiltViewModel(),
                                onLoginSuccess = { appScreen = AppScreen.FEED },
                                onNavigateToRegister = {
                                    appScreen =
                                        AppScreen.REGISTER
                                }
                            )
                        }

                        AppScreen.REGISTER -> {
                            RegisterScreen(hiltViewModel()) { appScreen = AppScreen.FEED }
                        }

                        AppScreen.FEED -> {

                            FeedScreen(hiltViewModel(), onNavigateToCreatePost = {
                                appScreen = AppScreen.CREATE_NEW_POST
                            }
                            )
                        }

                        AppScreen.CREATE_NEW_POST -> {

                            PostCreationScreen(
                                hiltViewModel(),
                                onPostAction = { appScreen = AppScreen.FEED }
                            ) { appScreen = AppScreen.FEED }
                        }
                    }
                }
            }
        }
    }
}
