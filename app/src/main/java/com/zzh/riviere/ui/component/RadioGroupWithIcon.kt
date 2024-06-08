package com.zzh.riviere.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.riviere.R
import com.zzh.riviere.data.Category
import com.zzh.riviere.ui.theme.AppTheme
import com.zzh.riviere.ui.theme.onOutcomeLight
import com.zzh.riviere.ui.theme.outcomeContainerLight
import com.zzh.riviere.ui.theme.outcomeLight

@Composable
fun RadioGroupWithIcon(options: List<Category>) {
    var selected by remember { mutableStateOf(options[0]) }
    LazyRow {
        items(options.size) { index ->
            val item = options[index]
            val tint = if (item == selected) outcomeLight else Color.Black
            val iconBg = if (item == selected) Color.White else outcomeContainerLight
            val buttonBg = if (item == selected) outcomeLight else MaterialTheme.colorScheme.surface
            val fontColor = if (item == selected) onOutcomeLight else MaterialTheme.colorScheme.onSurface

            Surface(
                modifier = Modifier
                    .height(40.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable {
                        selected = options[index]
                    }
            ) {
                Row(
                    modifier = Modifier
                        .background(buttonBg)
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(options[index].icon),
                        contentDescription = "Category Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(iconBg)
                            .padding(4.dp),
                        tint = tint,
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = options[index].name,
                        style = TextStyle(color = fontColor, fontSize = 12.sp)
                    )
                }
            }
            if (index != options.size - 1)
                Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Preview
@Composable
fun RadioGroupPreview() {
    AppTheme {
        val list = listOf(
            Category(1, "早餐", R.drawable.icon_restaurant),
            Category(2, "饮料", R.drawable.icon_drink)
        )
        RadioGroupWithIcon(list)
    }
}
