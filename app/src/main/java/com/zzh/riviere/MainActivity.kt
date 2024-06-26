package com.zzh.riviere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.coerceIn
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.riviere.data.BillItem
import com.zzh.riviere.data.Category
import com.zzh.riviere.data.QuickAdd
import com.zzh.riviere.ui.component.BillItem
import com.zzh.riviere.ui.component.QuickAdd
import com.zzh.riviere.ui.component.RadioGroupWithIcon
import com.zzh.riviere.ui.theme.AppTheme
import com.zzh.riviere.ui.theme.incomeLight
import com.zzh.riviere.ui.theme.outcomeLight
import com.zzh.riviere.ui.theme.outlineVariantLight


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var offset by remember { mutableStateOf(300.dp) }
    var alpha by remember { mutableFloatStateOf(1f)}
    val animatedOffset: Dp by animateDpAsState(offset, label = "offset animation")
    val animatedAlpha: Float by animateFloatAsState(alpha, label = "alpha animation")
    val quickAddOffset = if (animatedOffset >= 300.dp) {
        animatedOffset - 150.dp
    } else {
        150.dp
    }
//    val quickAddOffset = 150.dp
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column {
            Spacer(
                modifier = Modifier.height(
                    WindowInsets
                        .systemBars.asPaddingValues()
                        .calculateTopPadding(),
                )
            )
            Surface (
                color = MaterialTheme.colorScheme.surfaceContainer
            ) {
                AddBill(
                    modifier = Modifier
                        .alpha(animatedAlpha)
                )
                QuickAdd(quickAddOffset)
                BillBox(
                    modifier = Modifier
                        .offset(y = animatedOffset)
                        .pointerInput(Unit) {
                            detectVerticalDragGestures(
                                onVerticalDrag = { _, dragAmount ->
                                    val newOffset = offset + dragAmount.toDp()
                                    val newAlpha: Float =
                                        if (newOffset >= 300.dp) {
                                            1f
                                        } else {
                                            newOffset / 300.dp
                                        }
                                    offset = newOffset.coerceIn(0.dp, 600.dp)
                                    alpha = newAlpha.coerceIn(0f, 1f)
                                },
                                onDragEnd = {
                                    offset = if (offset < 150.dp) {
                                        alpha = 0f
                                        0.dp
                                    } else if (150.dp < offset && offset < 450.dp) {
                                        alpha = 1f
                                        300.dp
                                    } else {
                                        alpha = 1f
                                        600.dp
                                    }
                                }
                            )
                        }
                )
            }
        }
    }
}


@Composable
fun AddBill(modifier: Modifier) {
    var inputAmount by remember { mutableStateOf("") }
    var billMode by remember { mutableStateOf("add") }
    val calcIcon = if (billMode == "add") painterResource(R.drawable.icon_add) else painterResource(R.drawable.icon_subtract)
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp),
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, start = 16.dp, end = 16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.clickable (
                        indication = null,
                        interactionSource = interactionSource,
                    ) {
                        billMode = if (billMode == "add") "subtract" else "add"
                    }
                ) {
                    Icon(
                        painter = painterResource(R.drawable.shape_star),
                        contentDescription = "icon background",
                        tint = outcomeLight
                    )

                    Crossfade(targetState = calcIcon, label = "calc icon fade animation") { icon ->
                        Icon(
                            painter = icon,
                            contentDescription = "calc mode icon",
                            tint = Color.White,
                        )
                    }
                    
                }
                Spacer(modifier = Modifier.weight(1f))
                BasicTextField(
                    value = inputAmount,
                    singleLine = true,
                    onValueChange = { newValue ->
                        inputAmount = newValue
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.width(200.dp),
                    textStyle = TextStyle(
                        fontSize = 57.sp,
                        fontWeight = FontWeight.W400,
                        textAlign = TextAlign.Right
                    ),
                    decorationBox = { innerTextField ->
                        Box(modifier = Modifier.background(Color.Transparent), contentAlignment = Alignment.CenterEnd) {
                            if (inputAmount.isEmpty()) {
                                Text(
                                    text = "0",
                                    style = TextStyle(
                                        fontSize = 57.sp,
                                        fontWeight = FontWeight.W400,
                                        color = outlineVariantLight,
                                        textAlign = TextAlign.Right
                                    )
                                )
                            }
                            innerTextField()
                        }
                    }
                )
                Text(
                    text = "¥",
                    style = TextStyle(fontSize = 57.sp, fontWeight = FontWeight.W400),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row(
                modifier = Modifier.padding(top = 28.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val list = listOf(
                    Category(1, "早餐", R.drawable.icon_restaurant),
                    Category(2, "饮料", R.drawable.icon_drink)
                )
                RadioGroupWithIcon(list)
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                    contentAlignment = Alignment.Center,

                    ) {
                    Icon(
                        painter = painterResource(R.drawable.icon_arrow_down),
                        contentDescription = "arrow down",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}


@Composable
fun BillBox(modifier: Modifier) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
        color = MaterialTheme.colorScheme.surface,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .height(195.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = stringResource(R.string.expense),
                    modifier = Modifier.padding(top = 12.dp, start = 16.dp)
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = "¥",
                        style = TextStyle(fontSize = 36.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "-123.",
                        style = TextStyle(fontSize = 57.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                    Text(
                        text = "45",
                        style = TextStyle(fontSize = 44.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.income),
                        style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "+789.01" + "¥",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.budget),
                        style = TextStyle(fontSize = 12.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "2000" + "¥",
                        style = TextStyle(fontSize = 14.sp),
                        modifier = Modifier.alignByBaseline()
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                ) {
                    LinearProgressIndicator(
                        progress = { 0.3f },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Row(
                modifier = Modifier.padding(bottom = 10.dp, start = 32.dp, end = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "今日",
                    style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "+100" + "¥",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        color = incomeLight
                    )
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(
                    text = "-200" + "¥",
                    style = TextStyle(
                        fontSize = 12.sp,
                        fontWeight = FontWeight.W500,
                        color = outcomeLight
                    )
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                LazyColumn {
                    items(20) {
                        val drink = Category(1, "星巴克", R.drawable.icon_drink)
                        val testItem = BillItem(1, drink, "15:40", -11.00f, "喝饮料")
                        BillItem(testItem)
                    }
                    item {
                        Spacer(
                            modifier = Modifier.height(
                                WindowInsets
                                    .systemBars.asPaddingValues()
                                    .calculateBottomPadding(),
                            )
                        )
                    }
                }
            }

        }
    }
}

@Composable
fun QuickAdd(quickAddOffset: Dp) {
    Column (
        modifier = Modifier.offset(y = quickAddOffset)
    ) {
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 30.dp, end = 16.dp)
        ) {
            Text("快速记录", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500))
            Spacer(modifier = Modifier.weight(1f))
            Text("+", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500))
        }
        Row(
            modifier = Modifier
                .padding(start = 16.dp, top = 16.dp, end = 16.dp)
        ) {
            val cateSb = Category(1, "腥巴氪", R.drawable.icon_drink)
            val cateBreakfast = Category(2, "早饭", R.drawable.icon_restaurant)
            val quickSb = QuickAdd(1, cateSb, 45f)
            val quickBreakfast = QuickAdd(1, cateBreakfast, 6.5f)
            QuickAdd(listOf(quickSb, quickBreakfast))
        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        Column {
            AddBill(modifier = Modifier)
            BillBox(modifier = Modifier)
        }
    }
}