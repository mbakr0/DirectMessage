package com.example.compose

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.ui.theme.ComposeTheme
import com.example.compose.ui.theme.Shapes

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoTexts()
        }
    }

    @Preview
    @Composable
    fun TwoTexts(
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
        )
        {


            ComposeTheme {
                var text by remember { mutableStateOf(TextFieldValue("")) }
                val maxChar = 11
                val interactionSource = remember { MutableInteractionSource() }
                Column(modifier = Modifier.width(width = 250.dp)) {
                    BasicTextField(
                        value = text,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        onValueChange = { newText ->
                            newText.text.take(maxChar)
                            if (newText.text.length <= 11)
                                text = newText

                        },
                        modifier = Modifier
                            .clickable(indication = null, interactionSource = interactionSource) {

                            }
                            .fillMaxWidth()
                            .align(alignment = Alignment.Start)
                            .background(
                                Color.Transparent,
                                shape = RoundedCornerShape(
                                    topStart = 4.dp,
                                    topEnd = 4.dp,
                                    bottomEnd = 0.dp,
                                    bottomStart = 0.dp
                                )
                            )
                            .padding(top = 4.dp, bottom = 4.dp)
                            ,
                        singleLine = true,
                        textStyle = typography.body1,
                        cursorBrush = SolidColor(Color.Blue)
                    )
                   Column(horizontalAlignment = Alignment.CenterHorizontally,
                       modifier = Modifier.fillMaxWidth()) {
                       Divider(
                           modifier = Modifier
                               .animateContentSize(animationSpec = tween(150))
                               .height(2.dp)
                               .width(250.dp)
                               .background(color = MaterialTheme.colors.primary)
                       )
                   }
                }

                CreateSpacer()
                //WhatsApp button
                Button(
                    modifier = Modifier.width(250.dp),
                    onClick = {
                        onClick(text.text, "com.whatsapp")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = Shapes.medium


                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_wa),
                        contentDescription = "WhatApp"
                    )
                    Text("WhatsApp")
                }

                CreateSpacer()

                //WhatsApp business button
                Button(
                    modifier = Modifier.width(250.dp),
                    onClick = {
                        onClick(text.text, "com.whatsapp.w4b")
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary),
                    shape = Shapes.medium

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_wabis),
                        contentDescription = "WhatApp"
                    )
                    Text("WhatsApp Business")
                }
            }


        }
    }
    @Composable
    private fun CreateSpacer() {
        Spacer(modifier = Modifier.height(16.dp))
    }

    private fun onClick(text: String, packageName: String) {
        if (text.length < 11) {
            makeToast("short number")
            return
        }
        val url = "https://api.whatsapp.com/send?phone=+2$text"
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        i.setPackage(packageName)
        try {
            startActivity(i)
        } catch (e: Exception) {
            makeToast("No whatsApp Installed")
        }
    }

    private fun makeToast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }


}