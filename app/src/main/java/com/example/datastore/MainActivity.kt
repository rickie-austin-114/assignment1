package com.example.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.datastore.data.UserStore
import com.example.datastore.ui.theme.DataStoreTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Main()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun Main() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val idValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val userNameValue = remember {
        mutableStateOf(TextFieldValue())
    }
    val courseNameValue = remember {
        mutableStateOf(TextFieldValue())
    }

    val store = UserStore(context)

    val idDisplay = remember { mutableStateOf("") }
    var userNameDisplay = remember { mutableStateOf("") }
    var courseNameDisplay = remember { mutableStateOf("") }

    val idText = store.getId.collectAsState(initial = "")
    val userNameText = store.getUserName.collectAsState(initial = "")
    val courseNameText = store.getCourseName.collectAsState(initial = "")


    Column(
        modifier = Modifier.clickable { keyboardController?.hide() },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "Student Detail", fontWeight = FontWeight.Bold)



        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = idValue.value,
            onValueChange = { idValue.value = it },
            placeholder = { Text("ID") },

        )





        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = userNameValue.value,
            onValueChange = { userNameValue.value = it },
            placeholder = { Text("Username") },
            )




        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            value = courseNameValue.value,
            onValueChange = { courseNameValue.value = it },
            placeholder = { Text("Course Name") },
            )

        Spacer(modifier = Modifier.height(30.dp))

        Row(
            modifier = Modifier.width(300.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            Button(
                onClick = {
                    idDisplay.value = idText.value
                    userNameDisplay.value = userNameText.value
                    courseNameDisplay.value = courseNameText.value
                },
                modifier = Modifier.width(140.dp) // Set the button width to 200 dp

            ) {
                Text(text = "Load")
            }

            Button(
                onClick = {
                    CoroutineScope(Dispatchers.IO).launch {
                        store.saveId(idValue.value.text)
                        store.saveUserName(userNameValue.value.text)
                        store.saveCourseName(courseNameValue.value.text)
                    }
                },modifier = Modifier.width(140.dp) // Set the button width to 200 dp

            ) {
                Text(text = "Store")
            }
        }
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    store.reset()
                    idDisplay.value = ""
                    userNameDisplay.value = ""
                    courseNameDisplay.value = ""
                }
            },  modifier = Modifier.width(290.dp) // Set the button width to 200 dp
        ) {
            Text(text = "Reset")
        }
        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "ID: ${idDisplay.value}")

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Username: ${userNameDisplay.value}")

        Spacer(modifier = Modifier.height(15.dp))

        Text(text = "Course Name: ${courseNameDisplay.value}")
    }
}