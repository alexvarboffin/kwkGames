package mostb.vaid.casib.retab

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import mostb.vaid.casib.retab.ui.theme.CricketTheme


class SplashScreenActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CricketTheme {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable {
                            startMainActivity()
                            finish()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_splashscreen),
                        contentDescription = "Splash Screen",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.padding(bottom = 70.dp).height(170.dp),
                    ) {
                        ClickToStartButton(modifier = Modifier, onClick = { startMainActivity() })
                    }
                }
            }
        }
    }
}

@Composable
fun ClickToStartButton(modifier: Modifier, text: String = "CLICK TO START", onClick: ()-> Unit){
    BeautifulButton(onClick = onClick, modifier = Modifier.fillMaxWidth(), text = text)

}

fun Activity.startMainActivity(){
    startActivity(Intent(this, MainActivity::class.java))
}
