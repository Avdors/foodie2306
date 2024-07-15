package com.example.foodie2306.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.domain.model.Product
import com.example.foodie2306.R
import com.example.foodie2306.viewmodel.DbViewModel
import com.example.foodie2306.viewmodel.ProductsViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProductItem(
    item : Product,
    modifier: Modifier,
    dbViewModel: DbViewModel,
    onClick:() ->Unit
) {

    val dbList = dbViewModel.list.collectAsState()

    val foundItem = dbList.value.find {
        it.id == item.id
    }

    Card(
        modifier = modifier
            .padding(3.dp)
            .width(167.dp)
            .height(290.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            contentColor = Color(0xFFF5F5F5)
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()
            ) {
            Box(modifier = Modifier.fillMaxSize()){
                AsyncImage(
                    model = item.image,
                    contentDescription = "image",
                    error = painterResource(id = R.drawable.photo_product),
                    modifier = Modifier.align(Alignment.Center))
                if(item.price_old != null){
                    Image(
                        painter = painterResource(id = R.drawable.discount),
                        contentDescription = "discount",
                        alignment = Alignment.TopStart,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
            Box(modifier = Modifier.fillMaxSize())
            {
                Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                    Text(
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2,
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 12.sp,
                        text = item.name
                    )
                    Row(modifier = Modifier.padding(top = 6.dp)) {
                        // количество
                        Text(
                            text = item.measure.toString(),
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            color = Color.Gray,
                            fontSize = 12.sp
                        )
                        //единица измерения
                        Text(
                            fontFamily = FontFamily(Font(R.font.roboto)),
                            color = Color.Gray,
                            fontSize = 14.sp,
                            text = item.measure_unit
                        )
                    }
                }
                if(foundItem == null) {
                    Button(
                        onClick = {
                            dbViewModel.upsert(
                                count = 1,
                                id = item.id,
                                name = item.name,
                                price = item.price_current,
                                oldPrice = item.price_old,
                                image = item.image
                            )
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .width(160.dp)
                            .padding(bottom = 10.dp)
                            .height(40.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Row {
                            Text(
                                text = stringResource(R.string.price, item.price_current / 100),
                                color = Color.Black,
                                fontSize = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto))
                            )
                            if (item.price_old != null) {
                                Text(
                                    text = stringResource(
                                        R.string.twoprice,
                                        item.price_old!! / 100
                                    ),
                                    modifier = Modifier.padding(start = 5.dp),
                                    textDecoration = TextDecoration.LineThrough,
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto))
                                )
                            }
                        }
                    }
                } else{
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.minus),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .clickable { dbViewModel.minusCount(foundItem) })
                        Text(
                            text = foundItem.count.toString(),
                            modifier = Modifier.align(Alignment.Center)
                            )
                        Image(
                            painter = painterResource(id = R.drawable.plus),
                            contentDescription = null,
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable { dbViewModel.plusCount(foundItem) }
                        )
                    }
                }
            }
        }
    }
}

