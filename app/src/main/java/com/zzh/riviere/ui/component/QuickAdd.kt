package com.zzh.riviere.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.riviere.R
import com.zzh.riviere.data.Category
import com.zzh.riviere.data.QuickAdd
import com.zzh.riviere.ui.theme.AppTheme
import com.zzh.riviere.ui.theme.outcomeContainerLight
import com.zzh.riviere.ui.theme.outcomeLight

@Composable
fun QuickAdd(list: List<QuickAdd>) {
    LazyRow {
        items(list.size) { index: Int ->
            Box(
                modifier = Modifier
                    .width(128.dp)
                    .height(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(outcomeContainerLight)
                    .padding(8.dp)
            ) {
                Row {
                    Column {
                        Box(
                            modifier = Modifier
                                .size(24.dp)
                                .clip(CircleShape)
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(list[index].category.icon),
                                contentDescription = "Icon",
                                tint = outcomeLight,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            list[index].category.name,
                            style = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.W500),
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Column {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            list[index].amount.toString() + "¥",
                            style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W400),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
        }

        item {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(16.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    painter = painterResource(R.drawable.icon_add), contentDescription = "add",
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}


@Preview
@Composable
fun QuickAddPreview() {
    AppTheme {
        val cateSb = Category(1, "腥巴氪", R.drawable.icon_drink)
        val cateBreakfast = Category(2, "早饭", R.drawable.icon_restaurant)
        val quickSb = QuickAdd(1, cateSb, 45f)
        val quickBreakfast = QuickAdd(1, cateBreakfast, 6.5f)
        QuickAdd(listOf(quickSb, quickBreakfast))
    }
}