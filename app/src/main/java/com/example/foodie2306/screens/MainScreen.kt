package com.example.foodie2306.screens

import android.app.Application
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.data.states.States
import com.example.domain.model.Product
import com.example.foodie2306.R
import com.example.foodie2306.viewmodel.DbViewModel
import com.example.foodie2306.viewmodel.ProductsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    modifier: Modifier,
    navController: NavController,
    productsViewModel: ProductsViewModel,
    dbViewModel : DbViewModel = koinViewModel() //В данном случае функция koinViewModel() создаёт экземпляр класса DbViewModel, который будет использоваться в функции @Composable fun MainScreen().
) {

    val dbList = dbViewModel.list.collectAsState()  //collectAsState() позволяет преобразовать поток данных (Flow) в объект состояния (State). Это полезно, когда вы хотите использовать данные из потока в своём пользовательском интерфейсе.

    val sum = dbList.value.sumOf {
        it.price * it.count
    }
    var showList by remember { //сохраняет значение переменной и не пересчитывает его заново при каждом рендере
        mutableStateOf(true) // В качестве начального значения переменной showList, используется выражение mutableStateOf(true),
    // которое создаёт объект MutableState со значением true.
    }

    var showDetails by remember {
        mutableStateOf(true)
    }

    var productToHandle by remember {
        mutableStateOf<Product?>(null)
    }

    val categories = productsViewModel.categories.collectAsState() // вопрос откуда заполняется этот flow
    val tags = productsViewModel.tags.collectAsState()
    val state = productsViewModel.states.collectAsState()
    var tabIndex by remember {
    mutableStateOf(0)
    }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember {
    mutableStateOf(false)
    }
    val selectedOptions = remember {
    mutableStateOf(listOf<Int>())
    }
    val context = LocalContext.current

    if (categories.value.isNotEmpty()) {
        LaunchedEffect(Unit) {
            productsViewModel.selectCategory(categories.value, tabIndex)
        }
    }

    AnimatedVisibility(visible = showList) {
        if(showBottomSheet){
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet = false },
                sheetState = sheetState,
                contentColor = Color.White,
            ) {
                Text(
                    text = stringResource(R.string.choose),
                    modifier = Modifier.padding(16.dp),
                    fontSize = 20.sp,
                    fontFamily = FontFamily(
                        Font(R.font.roboto)
                    ),
                    fontWeight = FontWeight.Bold
                )

                tags.value.forEach {tag ->
                    Box(modifier = Modifier.fillMaxWidth()){
                        Text(
                            text = tag.name,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 3.dp, bottom = 6.dp)
                                .align(Alignment.CenterStart), // выравнивает элемент по центру относительно начала родительского элемента
                            fontSize = 16.sp,
                            fontFamily = FontFamily(
                                Font(R.font.roboto)
                            )
                        )
                        Checkbox(
                            colors = CheckboxColors(
                                checkedBorderColor = Color(0xFFF15412),
                                checkedBoxColor = Color(0xFFF15412),
                                checkedCheckmarkColor = Color.White,
                                disabledBorderColor = Color.Transparent,
                                disabledCheckedBoxColor = Color.Transparent,
                                disabledIndeterminateBorderColor = Color.Transparent,
                                disabledUncheckedBoxColor = Color.Transparent,
                                disabledUncheckedBorderColor = Color.Transparent,
                                disabledIndeterminateBoxColor = Color.Transparent,
                                uncheckedBorderColor = Color.Gray,
                                uncheckedBoxColor = Color.Transparent,
                                uncheckedCheckmarkColor = Color.Transparent
                            ),
                            modifier = Modifier.align(Alignment.CenterEnd),
                            checked = selectedOptions.value.contains(tag.id),
                            onCheckedChange = {selected ->
                                val currentSelected = selectedOptions.value.toMutableList()
                                if(selected){
                                    currentSelected.add(tag.id)
                                } else {
                                    currentSelected.remove(tag.id)
                                }
                                selectedOptions.value = currentSelected
                            })
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }

                }
                
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF15412)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(343.dp)
                        .height(48.dp),
                    onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if(!sheetState.isVisible){
                                showBottomSheet = false
                            }
                        }
                        productsViewModel.selectTag(
                            categories.value,
                            tabIndex,
                            selectedOptions.value,
                            context.applicationContext as Application
                        )
                    }
                ) {
                    Text(stringResource(R.string.ok))
                    
                }
            }
        }
        Box(modifier = Modifier
            .fillMaxSize()
            .navigationBarsPadding())
        {
            Column(modifier = Modifier.fillMaxSize()) {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.White
                    ),
                    title = {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Box(modifier = Modifier.padding(start = 1.dp)
                                .align(Alignment.CenterStart)) {
                                if (selectedOptions.value.isNotEmpty()) {
                                    Box(
                                        modifier = Modifier
                                            .align(Alignment.TopEnd)
                                            .width(22.dp)
                                            .height(22.dp)
                                            .background(
                                                color = Color(0xFFF15412),
                                                shape = RoundedCornerShape(50.dp)
                                            )
                                    ) {
                                        Text(
                                            modifier = Modifier.align(Alignment.Center),
                                            color = Color.White,
                                            fontSize = 10.sp,
                                            lineHeight = 22.sp,
                                            text = selectedOptions.value.size.toString()
                                        )
                                    }
                                }
                                IconButton(onClick = { showBottomSheet = true }, modifier = Modifier.padding(start = 1.dp)) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.indicator_off),
                                        contentDescription = "filter"
                                    )
                                }
                            }
                            Image(
                                painter = painterResource(id = R.drawable.logo),
                                contentDescription = "logo",
                                modifier = Modifier.align(Alignment.Center)
                            )
                            IconButton(
                                onClick = { navController.navigate("search") },
                                Modifier
                                    .padding(end = 0.dp)
                                    .align(Alignment.CenterEnd)

                            ) {
                                Icon(Icons.Filled.Search, contentDescription = "search")

                            }
                        }
                    }
                )
                ScrollableTabRow(
                    indicator = {
                        TabRowDefaults.SecondaryIndicator(
                            color = Color.Transparent
                        )
                    },
                    divider = {},
                    edgePadding = 1.dp,
                    modifier = Modifier.padding(top = 6.dp, start = 14.dp),
                    selectedTabIndex = tabIndex
                ) {
                    categories.value.forEachIndexed { index, category ->
                        val color = remember {
                            Animatable(Color(0xFFF15412))
                        }
                        scope.launch {
                            color.animateTo(
                                if (tabIndex == index) {
                                    Color(0xFFF15412)
                                } else {
                                    Color.White
                                }
                            )
                        }
                        val tabmodifier = Modifier
                            .background(color = color.value, shape = RoundedCornerShape(8.dp))
                            .height(40.dp)
                        Tab(
                            text = {
                                Text(
                                    category.name,
                                    style = if (tabIndex == index) {
                                        TextStyle(
                                            color = Color.White,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(R.font.roboto)
                                            )
                                        )
                                    } else {
                                        TextStyle(
                                            color = Color.Black,
                                            fontSize = 16.sp,
                                            fontFamily = FontFamily(
                                                Font(R.font.roboto)
                                            )
                                        )
                                    }
                                )
                            },
                            selected = tabIndex == index,
                            onClick = {
                                tabIndex = index
                                productsViewModel.selectCategory(categories.value, index)
                                selectedOptions.value = emptyList()
                            },
                            modifier = tabmodifier
                        )
                    }

                }

                when (val currenState = state.value) {
                    is States.Error -> {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            currenState.error?.let {
                                Text(
                                    fontFamily = FontFamily(
                                        Font(R.font.roboto)
                                    ),
                                    fontSize = 16.sp,
                                    modifier = Modifier.padding(16.dp),
                                    textAlign = TextAlign.Center,
                                    text = it
                                )
                            }
                        }
                    }

                    States.Loading -> {
                        Box(
                            modifier = modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }

                    is States.Success -> {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(bottom = if (dbList.value.isEmpty()) 0.dp else 10.dp)
                                .padding(start = 14.dp, end = 14.dp),
                            columns = GridCells.Fixed(2)
                        ) {
                            items(currenState.list,
                                key = {
                                    it.id
                                }) { product ->

                                ProductItem(
                                    item = product,
                                    modifier = Modifier.animateItemPlacement(
                                        spring(
                                            dampingRatio = Spring.DampingRatioMediumBouncy,
                                            stiffness = Spring.StiffnessLow
                                        )
                                    ),
                                    dbList = dbViewModel.list.collectAsState(),
                                    onEvent = dbViewModel::onEvent,
                                    onClick = {
                                        productToHandle = product
                                        showList = false
                                        showDetails = true

                                    }

                                )
                            }
                        }
                    }
                }
            }
            AnimatedVisibility(
                visible = dbList.value.isNotEmpty(),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                Button(
                    onClick = {
                        navController.navigate("basketScreen")
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(16.dp)
                        .width(343.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF15412)
                    )
                ) {
                    Row {
                        Image(
                            painter = painterResource(id = R.drawable.cart),
                            contentDescription = null,
                            modifier = Modifier.padding(horizontal = 6.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.sum, sum / 100),
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto))
                        )
                    }
                }
            }
        }
    }
    AnimatedVisibility(visible = showDetails){

    productToHandle?.let{
    val foundItem = dbList.value.find { productFromDb ->
        productFromDb.id == productToHandle!!.id
    }
        DetailScreen(modifier = modifier, item = it, onButtonClick = {
        if(foundItem == null){
            dbViewModel.upsert(
                count = 1,
                id = productToHandle!!.id,
                price = productToHandle!!.price_current,
                oldPrice = productToHandle!!.price_old,
                name = productToHandle!!.name,
                image = productToHandle!!.image
            )
        } else {
            dbViewModel.plusCount(foundItem)
        }
            showDetails = false
            showList = true
        },
            onBackClick = {
                showDetails = false
                showList = true
            }
            )
    }
    }


}


@Preview(showBackground = true)
@Composable
fun PreviewMainScreen() {
//    val sampleProduct = Product(
//        carbohydrates_per_100_grams = 20.00,
//        category_id = 664,
//        description = "This is a sample product description.",
//        energy_per_100_grams = 250.00,
//        fats_per_100_grams = 5.50,
//        id = 77,
//        image = "photo_product.png", // Use a placeholder image URL
//        measure = 200,
//        measure_unit = "g",
//        name = "Sample Product",
//        price_current = 12000,
//        price_old = 15000,
//        proteins_per_100_grams = 10.00,
//        tag_ids = listOf(2,3,4)
//
//
//    )

    val navController = rememberNavController()
    val productsViewModel = koinViewModel<ProductsViewModel>()
   // MainScreen(modifier = Modifier, navController = navController, productsViewModel = productsViewModel)
}

