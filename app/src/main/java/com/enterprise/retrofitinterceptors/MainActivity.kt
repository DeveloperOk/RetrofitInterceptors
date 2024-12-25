package com.enterprise.retrofitinterceptors

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.enterprise.retrofitinterceptors.ui.theme.RetrofitInterceptorsTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val nYTimesApi = RetrofitNYTimes.getRetrofitNewYorkTimesApi(context = this)

        //Retrofit Interceptors Medium Post
        //https://medium.com/@myofficework000/retrofit-interceptors-for-beginners-76943e987ad5

        setContent {
            RetrofitInterceptorsTheme {
                RetrofitInterceptorsApp(nYTimesApi = nYTimesApi)
            }
        }


    }
}

@Composable
fun RetrofitInterceptorsApp(nYTimesApi: NYTimesApi) {

    val displayText = rememberSaveable { mutableStateOf("") }
    val isProgressBarVisible = rememberSaveable { mutableStateOf(false) }

    val context = LocalContext.current

    val isNoInternetConnectionDialogVisible = rememberSaveable { mutableStateOf(false) }

    if(isNoInternetConnectionDialogVisible.value){

        NoInternetConnectionDialog(isDialogVisible = isNoInternetConnectionDialogVisible)

    }

    Column(horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier.fillMaxSize().background(color = Color.Green)){

        Scaffold(modifier = Modifier.systemBarsPadding().fillMaxSize()) { innerPadding ->

            Column(horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(innerPadding).fillMaxSize()
                    .background(color = Color.White)){


                if(isProgressBarVisible.value){

                    CircularProgressIndicator(color = Color.Green)

                }

                Text(text = displayText.value)

                Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                    onClick = {

                        sendRequest(displayText = displayText,
                            nYTimesApi = nYTimesApi,
                            isProgressBarVisible = isProgressBarVisible,
                            context = context,
                            isNoInternetConnectionDialogVisible = isNoInternetConnectionDialogVisible)

                }) {

                    Text(text = stringResource(id = R.string.main_activity_send_request_button_text))
                }

            }

        }

    }



}

fun sendRequest(
    displayText: MutableState<String>,
    nYTimesApi: NYTimesApi,
    isProgressBarVisible: MutableState<Boolean>,
    context: Context,
    isNoInternetConnectionDialogVisible: MutableState<Boolean>
) {

    GlobalScope.launch(Dispatchers.Main) {

        val emptyString = ""
        displayText.value = emptyString
        isProgressBarVisible.value = true

        GlobalScope.launch(Dispatchers.IO) {

            try {

                val booksDataResponse = nYTimesApi.getBooks(
                    NytimesApiConstants.Date,
                    NytimesApiConstants.List,
                    NytimesApiConstants.ApiKey
                )

                GlobalScope.launch(Dispatchers.Main) {

                    isProgressBarVisible.value = false

                }

                if (booksDataResponse.isSuccessful) {

                    val booksData = booksDataResponse.body()

                    val books = booksData?.results?.books

                    val spaceString = " "
                    displayText.value =
                        context.getString(R.string.main_activity_retrieved_books_text) +
                                spaceString +
                                books?.count().toString()


                } else {

                    GlobalScope.launch(Dispatchers.Main) {

                        Toast.makeText(
                            context,
                            R.string.retrofit_error_message,
                            Toast.LENGTH_LONG
                        ).show()

                    }

                }

            } catch (exception: Exception) {

                val TAGMainActivity = "MainActivity"

                Log.d(TAGMainActivity, exception.message.toString())

                GlobalScope.launch(Dispatchers.Main) {

                    isProgressBarVisible.value = false

                    if(exception is NoInternetConnectionException){

                        isNoInternetConnectionDialogVisible.value = true

                    }

                }

            }

        }
    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoInternetConnectionDialog(isDialogVisible: MutableState<Boolean>) {

    BasicAlertDialog(onDismissRequest = {
        // Dismiss the dialog when the user clicks outside the dialog or on the back button.
        //isDialogVisible.value = false
    }){

        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.wrapContentHeight()
                .wrapContentWidth()
                .background(color = Color.White, RoundedCornerShape(size = 15.dp))
                .border(width = 3.dp, color = Color.Green, RoundedCornerShape(size = 15.dp))
                .padding(15.dp)){

            Row(horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().wrapContentHeight()){

                Icon(imageVector = Icons.Default.Info,
                    contentDescription
                    = stringResource(id = R.string.no_internet_connection_popup_icon_content_description),
                    tint = Color.Blue)

                Spacer(modifier = Modifier.width(5.dp))

                Text(text = stringResource(id = R.string.no_internet_connection_popup_title))


            }

            Spacer(modifier = Modifier.height(15.dp))

            Text(text = stringResource(id = R.string.no_internet_connection_popup_message))

            Spacer(modifier = Modifier.height(15.dp))

            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                onClick = {

                    isDialogVisible.value = false

                }) {

                Text(text = stringResource(id = R.string.no_internet_connection_neutral_button_text))

            }

        }

    }

}

@Preview(showBackground = true)
@Composable
fun RetrofitInterceptorsAppPreview() {

}