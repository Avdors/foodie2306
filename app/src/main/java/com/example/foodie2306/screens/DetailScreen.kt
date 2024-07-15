package com.example.foodie2306.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.model.Product
import com.example.foodie2306.R


@Composable
fun DetailScreen(
    modifier: Modifier,
    item: Product,
    onButtonClick: () -> Unit,
    onBackClick: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()){
    Image(painter = painterResource(id = R.drawable.back) , contentDescription = null,
        modifier = Modifier
            .statusBarsPadding() // чтобы быть ниже панели навигации
            .padding(6.dp)
            .align(Alignment.TopStart)
            .clickable { onBackClick() })

        Column(modifier = modifier
            .fillMaxSize()
            .padding(bottom = 70.dp, top = 50.dp)
            .verticalScroll(rememberScrollState())) {
            Box(modifier = Modifier.fillMaxWidth()){
                AsyncImage(model = item.image, contentDescription = null,
                    error = painterResource(id = R.drawable.photo_product),
                    modifier = Modifier.align(Alignment.Center))
                if(item.price_old != null){
                    Image(painter = painterResource(id = R.drawable.discount),
                        contentDescription = null,
                        alignment = Alignment.TopStart,
                        modifier = Modifier.padding(18.dp))
                }
            }
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontSize = 34.sp,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = item.name,
                    lineHeight = 40.sp)
                Text(
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp),
                    text = item.description,
                    color = Color.Gray
                )
                HorizontalDivider(thickness = 2.dp)
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.weight)
                    )
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.measure.toString()
                        )
                        Text(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.measure_unit
                        )

                    }
                }
                HorizontalDivider(thickness = 2.dp)
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.enerdy)
                    )
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = stringResource(id = R.string.kkal, item.energy_per_100_grams)
                        )


                    }
                }
                HorizontalDivider(thickness = 2.dp)
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.protein)
                    )
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.proteins_per_100_grams.toBigDecimal().stripTrailingZeros().toPlainString() // здесь убираес лишние нули при выводе
                        )
                        Text(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.measure_unit
                        )

                    }
                }
                HorizontalDivider(thickness = 2.dp)
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.fat)
                    )
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.fats_per_100_grams.toBigDecimal().stripTrailingZeros().toPlainString()
                        )
                        Text(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.measure_unit
                        )

                    }
                }
                HorizontalDivider(thickness = 2.dp)
                Box(modifier = Modifier.fillMaxWidth()){
                    Text(
                        modifier = Modifier
                            .align(Alignment.CenterStart)
                            .padding(16.dp),
                        color = Color.Gray,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 16.sp,
                        text = stringResource(id = R.string.sugar)
                    )
                    Row(modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(16.dp)) {
                        Text(
                            modifier = Modifier
                                .padding(end = 3.dp),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.carbohydrates_per_100_grams.toBigDecimal().stripTrailingZeros().toPlainString()
                        )
                        Text(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            fontSize = 16.sp,
                            text = item.measure_unit
                        )

                    }
                }
                HorizontalDivider(thickness = 2.dp)

            }
        }
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF15412)
            ),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding() // чтобы быть выше навигационных кнопок
                .padding(16.dp)
                .width(343.dp)
                .height(43.dp),
            shape = RoundedCornerShape(8.dp),
            onClick = onButtonClick
        ) {
            Row {
                Text(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontSize = 16.sp,
                    text = stringResource(id = R.string.buy, item.price_current / 100)
                )
                if(item.price_old != null){
                Text(
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.LineThrough,
                    modifier = Modifier.padding(start = 10.dp),
                    text = stringResource(id = R.string.twoprice, item.price_old!! / 100)
                )
                }
            }
            
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDetailScreen() {
    val sampleProduct = Product(
        carbohydrates_per_100_grams = 20.00,
        category_id = 664,
        description = "This is a sample product description.",
        energy_per_100_grams = 250.00,
        fats_per_100_grams = 5.50,
        id = 77,
        image = "photo_product.png", // Use a placeholder image URL
        measure = 200,
        measure_unit = "g",
        name = "Sample Product",
        price_current = 12000,
        price_old = 15000,
        proteins_per_100_grams = 10.00,
        tag_ids = listOf(2,3,4)


    )

    DetailScreen(
        modifier = Modifier,
        item = sampleProduct,
        onButtonClick = {},
        onBackClick = {}
    )
}