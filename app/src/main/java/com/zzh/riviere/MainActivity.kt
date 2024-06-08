package com.zzh.riviere

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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
//        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceContainer
                ) {
                    HomeScreen {

                        AddBill()
                        BillBox()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(content: @Composable () -> Unit) {
    MaterialTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            content()
        }
    }
}



@Composable
fun AddBill() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(348.dp),
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 15.dp, start = 16.dp, end = 16.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.shape_star),
                        contentDescription = "icon background",
                        tint = outcomeLight
                    )
                    Text(text = "一", style = TextStyle(fontSize = 24.sp, color = Color.White))
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "0",
                    style = TextStyle(
                        fontSize = 57.sp,
                        fontWeight = FontWeight.W400,
                        color = outlineVariantLight
                    )

                )
                Text(
                    text = "¥",
                    style = TextStyle(fontSize = 57.sp, fontWeight = FontWeight.W400),
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
            Row (
                modifier = Modifier.padding(top = 28.dp, start = 16.dp, end = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val list = listOf(
                    Category(1, "早餐", R.drawable.icon_restaurant),
                    Category(2, "饮料", R.drawable.icon_drink)
                )
                RadioGroupWithIcon(list)
                Spacer(modifier = Modifier.weight(1f))
                Box (
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
            Row (
                modifier = Modifier.padding(start = 16.dp, top = 30.dp, end = 16.dp)
            ) {
                Text("快速记录", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500))
                Spacer(modifier = Modifier.weight(1f))
                Text("+", style = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.W500))
            }
            Row (
                modifier = Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)
            ) {
                val cateSb = Category(1, "腥巴氪", R.drawable.icon_drink)
                val cateBreakfast = Category(2, "早饭", R.drawable.icon_restaurant)
                val quickSb = QuickAdd(1, cateSb, 45f)
                val quickBreakfast = QuickAdd(1, cateBreakfast, 6.5f)
                QuickAdd(listOf(quickSb, quickBreakfast))
            }
        }
    }
}


@Composable
fun BillBox() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(544.dp)
                .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
                .align(Alignment.BottomCenter),
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
}


@Preview
@Composable
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen {
            AddBill()
            BillBox()
        }
    }
}