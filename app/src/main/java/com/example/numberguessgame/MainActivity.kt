package com.example.numberguessgame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.numberguessgame.ui.theme.NumberGuessGameTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NumberGuessGameTheme {
                NumberGuessGame()
            }
        }
    }
}

@Composable
fun NumberGuessGame() {
    var resultText by remember { mutableStateOf("1~100 사이 숫자를 맞춰보세요!") }
    var guessText by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf(Random.nextInt(1, 101)) }
    var attempts by remember { mutableStateOf(0) }
    var resultColor by remember { mutableStateOf(Color.Black) } // 텍스트 색상

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = resultText,
            fontSize = 20.sp,
            color = resultColor,
            modifier = Modifier.padding(16.dp)
        )
        Text(
            text = "시도 횟수: $attempts",
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = guessText,
            onValueChange = { guessText = it },
            label = { Text("숫자를 입력하세요") }
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (guessText.isEmpty()) {
                resultText = "숫자를 입력하세요!"
                resultColor = Color.Black
                return@Button
            }
            val guess = guessText.toInt()
            attempts++ // 시도 횟수 증가

            when {
                guess > answer -> {
                    resultText = "더 낮은 숫자입니다!"
                    resultColor = Color.Red
                }
                guess < answer -> {
                    resultText = "더 높은 숫자입니다!"
                    resultColor = Color.Red
                }
                else -> {
                    resultText = "🎉 정답입니다! 새 숫자를 생성합니다! 🎉"
                    resultColor = Color.Green
                    answer = Random.nextInt(1, 101) // 새 정답 생성
                    attempts = 0
                    guessText = ""
                }
            }
        }) {
            Text("확인하기")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            answer = Random.nextInt(1, 101)
            attempts = 0
            guessText = ""
            resultText = "게임이 재시작되었습니다! 1~100 사이 숫자를 맞춰보세요!"
            resultColor = Color.Black
        }) {
            Text("재시작")
        }
    }
}
