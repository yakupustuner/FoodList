package com.learn.foodlist.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.learn.foodlist.R
import com.learn.foodlist.ui.theme.INFO_ICON_SIZE
import com.learn.foodlist.ui.theme.SMALL_PADDING

@Composable
fun InfoBox(
    icon:Painter,
    iconColor:Color,
    titleText:String,
    smallText:String,
    textColor:Color
){
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            modifier = Modifier
                .padding(end = SMALL_PADDING)
                .size(INFO_ICON_SIZE),
            painter = icon,
            contentDescription = stringResource(R.string.info_icon),
            tint = iconColor
        )
        Column {
            Text(
                text = titleText,
                color = textColor,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.Black
            )
            Text(
                modifier = Modifier.alpha(ContentAlpha.medium),
                text = smallText,
                color = textColor,
                fontSize = MaterialTheme.typography.overline.fontSize
            )
        }
    }
}

