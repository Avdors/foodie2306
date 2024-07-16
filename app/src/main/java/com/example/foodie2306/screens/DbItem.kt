package com.example.foodie2306.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.ProductFromDb
import com.example.foodie2306.R
import com.example.foodie2306.viewmodel.DbViewModel
import com.example.foodie2306.viewmodel.MainEvent

@Composable
fun DbItem(
    productFromDb: ProductFromDb,
   // dbViewModel: DbViewModel,
    dbList: State<List<ProductFromDb>>,
    onEvent: (MainEvent) -> Unit,
    modifier: Modifier
) {
    Box(modifier = modifier
        .fillMaxSize()
        .background(color = Color.White)){
        AsyncImage(model = productFromDb.image,
            contentDescription = null,
            error = painterResource(id = R.drawable.photo_product),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(96.dp))
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .width(135.dp)
                .padding(bottom = 6.dp)
        ) {
            Text(text = productFromDb.name,
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Box(modifier = Modifier.fillMaxWidth()){
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .clickable { onEvent(MainEvent.MinusCount(productFromDb)) },
                    painter = painterResource(id = R.drawable.minus_gray),
                    contentDescription = null)
                Text(
                    text = productFromDb.count.toString(),
                    modifier =Modifier.align(Alignment.Center)
                    )
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onEvent(MainEvent.PlusCount(productFromDb)) },
                    painter = painterResource(id = R.drawable.plus_gray),
                    contentDescription = null)
            }
        }
        Column(modifier = Modifier
            .align(Alignment.CenterEnd)
            .padding(end = 6.dp),
            horizontalAlignment = Alignment.End) {
            Text(text = stringResource(id = R.string.pricedb, productFromDb.price / 100),
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontWeight = FontWeight.Bold
            )
            if(productFromDb.oldPrice != null){
                Text(text = stringResource(id = R.string.pricedb, productFromDb.oldPrice!! / 100),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    textDecoration = TextDecoration.LineThrough
                )
            }
        }
        HorizontalDivider(
            modifier = Modifier.align(Alignment.BottomCenter),
            thickness = 1.dp
        )
    }
}