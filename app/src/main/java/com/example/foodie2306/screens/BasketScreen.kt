package com.example.foodie2306.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.foodie2306.R
import com.example.foodie2306.viewmodel.DbViewModel
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun BasketScreen(
    modifier: Modifier,
    navController: NavController,
    dbViewModel: DbViewModel = koinViewModel()
) {
    val dbList = dbViewModel.list.collectAsState()
    val sum = dbList.value.sumOf {
        it.price * it.count
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .navigationBarsPadding()){
        AnimatedVisibility(visible = dbList.value.isEmpty(), modifier = Modifier.align(Alignment.Center)) {
            Text(
                fontFamily = FontFamily(Font(R.font.roboto)),
                fontSize = 16.sp,
                text = stringResource(id = R.string.empty
                ))
        }
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(30.dp),
                    verticalAlignment =Alignment.CenterVertically
                ) {
                    Image(painter = painterResource(id = R.drawable.arrowback),
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            navController.popBackStack()
                        })
                    Text(text = stringResource(id = R.string.basket),
                        fontFamily = FontFamily(Font(R.font.roboto)),
                        fontSize = 18.sp)
                }
            })
            AnimatedVisibility(visible = dbList.value.isNotEmpty()) {
                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    items(dbList.value,
                        key = {
                            it.id
                        }){dbProduct ->
                            DbItem(productFromDb = dbProduct, dbViewModel = dbViewModel,
                                modifier = Modifier.animateItemPlacement(
                                    spring(
                                        dampingRatio =Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                ))
                    }
                    item{
                        Spacer(modifier = Modifier.height(50.dp))
                    }

                }
                
            }

        }
        AnimatedVisibility(visible = dbList.value.isNotEmpty(),
            modifier = Modifier.
        align(Alignment.BottomCenter)) {
            Button(
                colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFF15412)
                ),
                modifier = Modifier
                    .padding(16.dp)
                    .width(343.dp)
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {
            navController.navigate("animation")
                dbViewModel.clearDb()
            }) {
                Text(
                    fontFamily = FontFamily(Font(R.font.roboto)),
                    fontSize = 16.sp,
                    color = Color.White,
                    text = stringResource(id = R.string.order, sum /100))
            }

        }
    }
}