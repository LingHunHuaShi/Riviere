package com.zzh.riviere.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zzh.riviere.R
import com.zzh.riviere.data.BillItem
import com.zzh.riviere.data.Category
import com.zzh.riviere.ui.theme.AppTheme
import com.zzh.riviere.ui.theme.outcomeContainerLight

@Composable
fun BillItem(item: BillItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(outcomeContainerLight),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.icon_drink),
                contentDescription = "category icon",
                modifier = Modifier.size(16.dp)
            )
        }
        Column {
            Text(
                item.title,
                modifier = Modifier.padding(start = 12.dp),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.W500)
            )
            Text(
                text = item.time + "  " + item.category.name,
                modifier = Modifier.padding(start = 12.dp),
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.W400)
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Row {
            Text(
                text = item.amount.toString(),
                style = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.W400),
                modifier = Modifier.alignByBaseline()
            )
            Text(
                text = "¥",
                style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.W500),
                modifier = Modifier
                    .padding(start = 5.dp)
                    .alignByBaseline()
            )
        }
    }
}

@Preview
@Composable
fun BillItemPreview() {
    AppTheme {
        val drink = Category(1, "星巴克", R.drawable.icon_drink)
        val testItem = BillItem(1, drink, "15:40", -11.00f, "喝饮料")
        BillItem(testItem)
    }
}