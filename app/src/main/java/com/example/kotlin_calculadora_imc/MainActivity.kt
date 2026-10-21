package com.example.kotlin_calculadora_imc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.kotlin_calculadora_imc.ui.theme.KotlincalculadoraimcTheme
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlincalculadoraimcTheme {
                TipTimeLayout()
            }
        }
    }
}

@Composable
fun TipTimeLayout() {
    var pesoContaInput by remember { mutableStateOf("") }
    var alturaContaInput by remember { mutableStateOf("") }
    var arredondarResultado by remember { mutableStateOf(false) }

    var imcResultado by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(all = 32.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Calcular IMC",
            fontSize = 24.sp,
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )

        EditNumberField(
            label = "Peso (kg)",
            value = pesoContaInput,
            onValueChange = { pesoContaInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        EditNumberField(
            label = "Altura (m)",
            value = alturaContaInput,
            onValueChange = { alturaContaInput = it },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .fillMaxWidth()
        )

        ArredondarRow(
            arredondar = arredondarResultado,
            onArredondarChanged = { arredondarResultado = it },
            modifier = Modifier.padding(bottom = 24.dp)
        )

        Button(
            onClick = {
                val peso = pesoContaInput.replace(',', '.').toDoubleOrNull() ?: 0.0
                val altura = alturaContaInput.replace(',', '.').toDoubleOrNull() ?: 0.0
                imcResultado = calcularIMC(peso, altura, arredondarResultado)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calcular")
        }

        val imc = imcResultado
        val imcTexto = if (imc != null && imc > 0) NumberFormat.getNumberInstance().format(imc) else "--"
        val classificacao = if (imc != null && imc > 0) classificar(imc) else "--"

        Text(
            text = "IMC: $imcTexto",
            fontSize = 24.sp,
            modifier = Modifier.padding(top = 24.dp)
        )

        Text(
            text = "Classificação: $classificacao",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Composable
fun EditNumberField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    keyboardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChange,
        label = { Text(label) },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun ArredondarRow(
    arredondar: Boolean,
    onArredondarChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "Arredondar resultado")
        Switch(checked = arredondar, onCheckedChange = onArredondarChanged)
    }
}

fun classificar(valor: Double): String = when {
    valor < 18.5 -> "Abaixo do peso"
    valor in 18.5..24.9 -> "Peso normal"
    valor in 25.0..29.9 -> "Sobrepeso"
    valor >= 30.0 -> "Obesidade"
    else -> "Valor inválido"
}

fun calcularIMC(
    pesoConta: Double,
    alturaConta: Double,
    arredondar: Boolean = false
): Double {
    if (alturaConta <= 0.0 || pesoConta <= 0.0) return 0.0
    var imc = pesoConta / (alturaConta * alturaConta)
    if (arredondar) {
        imc = ceil(imc)
    }
    return imc
}
