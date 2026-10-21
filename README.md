# KOTLIN CALCULADORA IMC

Este projeto é uma calculadora que calcula o Índice de Massa Corporal (IMC) que você escolher, feita totalmente em Kotlin com a proposta de fazer os calculos e classificar ele em niveis diferentes de acordo com o IMC.

## Tecnologias usadas

- **Kotlin** (1.9.24)
- **Jetpack Compose** (Material 3)
- **Android Studio Ladybug** (2024.2.1) — IDE utilizada no desenvolvimento
- **Gradle** (Kotlin DSL) com *version catalog*
- **JDK 17** e **Android SDK API 35**

## Funcionalidades

- Entrada de **peso** e **altura** com teclado numérico decimal.
- Cálculo do IMC pela fórmula `peso / altura²`.
- **Classificação automática**
- Opção de **arredondar** o resultado para o inteiro acima.
- Layout rolável e com *safe drawing padding*, funcionando em telas pequenas e respeitando as bordas do sistema.

## Como compilar e executar

Antes de começar, você precisa ter:

- o **Android Studio** instalado (Ladybug 2024.2.1 ou mais novo);
- o **JDK 17** (já vem junto com o Android Studio);
- o **SDK do Android** com a **API 35** instalada;
- internet para baixar as dependências na primeira vez.

Passo a passo:

1. Copie o projeto para o seu computador:

   ```bash
   git clone https://github.com/assucenacosta-b/kotlin-calculadora-imc
   ```

2. Abra o Android Studio em `File > Open...` e escolha a pasta do projeto. Espere o **Gradle Sync** terminar.

3. Crie um emulador em `Tools > Device Manager > Create Device` (ex.: Pixel 7, API 35). Ou ligue um celular no USB com a **Depuração USB** ativada.

4. Clique no botão **Run ▶** (`Shift + F10`). Ou pelo terminal:

   ```bash
   ./gradlew assembleDebug
   ./gradlew installDebug
   ```

5. O app deve abrir, sem travar. Digite o peso e a altura, aperte **Calcular** e o IMC junto com a classificação aparecem abaixo do botão.

## Estrutura de arquivos

```
kotlin-calculadora-imc/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/example/kotlin_calculadora_imc/
│       │   ├── MainActivity.kt
│       │   └── ui/theme/
│       │       ├── Color.kt
│       │       ├── Theme.kt
│       │       └── Type.kt
│       └── res/ (ícone, strings, temas do Android)
├── gradle/libs.versions.toml
├── build.gradle.kts
└── settings.gradle.kts
```

## Descrição dos arquivos e das funções

### `MainActivity.kt`

Arquivo principal do app — contém a `Activity`, as *composable functions* da tela e as funções de cálculo.

| Item | Responsabilidade |
|---|---|
| `MainActivity` | `ComponentActivity` que é o ponto de entrada do app. Em `onCreate()` chama `enableEdgeToEdge()` (desenho de ponta a ponta) e `setContent { }`, que aplica o tema e monta a tela chamando `TipTimeLayout()`. |
| `TipTimeLayout()` | **Composable principal da tela.** Guarda o estado (`pesoContaInput`, `alturaContaInput`, `arredondarResultado`, `imcResultado`) com `remember { mutableStateOf(...) }`. Organiza tudo numa `Column` rolável (`verticalScroll`) e centralizada. Ao clicar no botão, converte os textos em `Double`, chama `calcularIMC()` e guarda o resultado; depois formata e exibe o IMC e a classificação. |
| `EditNumberField(label, value, onValueChange, keyboardOptions, modifier)` | **Campo de texto reutilizável** (`TextField` do Material 3) para entrada de números. Recebe rótulo, valor, função de atualização e as opções de teclado — é usada duas vezes na tela (peso e altura). |
| `ArredondarRow(arredondar, onArredondarChanged, modifier)` | **Linha com rótulo + `Switch`** que liga/desliga a opção de arredondar o resultado. O texto fica à esquerda e o switch à direita (`Arrangement.SpaceBetween`). |
| `classificar(valor: Double): String` | Função de **regra de negócio** (não é composable). Recebe o IMC e devolve a faixa em texto: "Abaixo do peso", "Peso normal", "Sobrepeso" ou "Obesidade". |
| `calcularIMC(pesoConta, alturaConta, arredondar): Double` | Função de **cálculo**. Retorna `0.0` se peso ou altura forem menores/iguais a zero (entrada inválida); caso contrário calcula `peso / altura²` e, se `arredondar` for `true`, aplica `ceil()` para arredondar para cima. |

## Hierarquia de composables

```
MainActivity (ComponentActivity)
└── KotlincalculadoraimcTheme
    └── TipTimeLayout                          ← tela principal (Column rolável)
        ├── Text("Calcular IMC")               ← título
        ├── EditNumberField("Peso (kg)")       ← campo de texto (TextField)
        ├── EditNumberField("Altura (m)")      ← campo de texto (TextField)
        ├── ArredondarRow                      ← Row
        │   ├── Text("Arredondar resultado")
        │   └── Switch
        ├── Button("Calcular")                 ← onClick: calcularIMC()
        │   └── Text("Calcular")
        ├── Text("IMC: ...")                   ← resultado formatado
        └── Text("Classificação: ...")         ← resultado de classificar()
```

## Autor

- **Aluno:** Assucena Costa B. dos reis
 **Turma:** 3º Módulo — Laboratório de Programação para Dispositivos Móveis (LPDM)
- **Turma:** 3º ano — Curso Técnico em Informática (CEFET-MG)
- **Repositório:** https://github.com/assucenacosta-b/kotlin-calculadora-imc
