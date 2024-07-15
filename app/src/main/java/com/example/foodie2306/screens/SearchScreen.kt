package com.example.foodie2306.screens

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.Product
import com.example.domain.usecase.CategoryListUseCase
import com.example.foodie2306.R
import com.example.foodie2306.viewmodel.DbViewModel
import com.example.foodie2306.viewmodel.ProductsViewModel
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter") // подавление предупреждений об неиспользовании параметров в лямбда выражении
@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun SearchScreen(
    modifier: Modifier,
    productsViewModel: ProductsViewModel,
    dbViewModel: DbViewModel = koinViewModel(),
    onBackClick: () -> Unit // Added onBackClick parameter to handle back navigation
) {

    val searchText by productsViewModel.searchText.collectAsState()
    val isSearching by productsViewModel.isSearching.collectAsState()
    val productList by productsViewModel.searchList.collectAsState()
    var showList by remember {
        mutableStateOf(true)
    }

    var showDetails by remember {
        mutableStateOf(true)
    }
    var productToHandle by remember {
        mutableStateOf<Product?>(null)
    }

    val dbList = dbViewModel.list.collectAsState()

    Scaffold(
        modifier = Modifier,
        topBar = {
            SearchBar(query = searchText,  //Текущий текст поиска.
                onQueryChange = productsViewModel::onSearchTextChange, //Обратный вызов для обработки изменений в тексте поиска.
                onSearch = productsViewModel::onSearchTextChange, //Обратный вызов для обработки действия поиска.
                active = isSearching, //Указывает, активна ли панель поиска.
                onActiveChange = { productsViewModel.onToogleSearch()}, //Обратный вызов для переключения активного состояния панели поиска.
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = if (isSearching) 0.dp else 16.dp
                    ),
                colors = SearchBarDefaults.colors(
                    containerColor = Color.White,
                    dividerColor = Color.Gray
                ),
                //Отображает значок на ведущей стороне панели поиска
                leadingIcon = {
                    IconButton(onClick = {
                        if (isSearching) {
                            productsViewModel.onToogleSearch()
                        } else {
                            onBackClick()
                        }
                    }) {
                        val iconRes = if (isSearching) R.drawable.arrowback else R.drawable.back
                        Image(
                            painter = painterResource(id = iconRes),
                            contentDescription = null
                        )
                    }
                },
                //Отображает значок в конце панели поиска, когда она активна.
                trailingIcon ={
                    AnimatedVisibility(isSearching) {
                        IconButton(onClick = {
                            productsViewModel.onSearchTextChange("")
                        }) {
                            Image(painter = painterResource(id = R.drawable.cancel), contentDescription = null)
                        }

                    }
                },
                // Текст-заместитель, отображаемый в панели поиска, когда она пуста.
                placeholder = {
                    Text(text = stringResource(id = R.string.find_food))
                }
            ) {
                // Показывает список товаров, если showList равен true и productList не пуст.
                    AnimatedVisibility(visible = showList && productList.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = modifier.fillMaxSize()
                                .padding(start = 14.dp, end = 14.dp)
                        ) {
                            items(productList,
                                key = {
                                    it.id
                                }){product ->
                                ProductItem(item = product,
                                    modifier = Modifier.animateItemPlacement(
                                    spring(
                                    dampingRatio =Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessLow
                                    )
                                    ),
                                    dbViewModel = dbViewModel,
                                    // Обратный вызов при нажатии на продукт, обновляющий productToHandle, showList и showDetails.
                                    onClick = {
                                        productToHandle = product
                                        showList = false
                                        showDetails = true
                                    }
                                )
                            }
                            
                        }
                        
                    }
                // Показывает сообщение, если productList пуст.
                AnimatedVisibility(visible = productList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize()){
                        Text(text = stringResource(id = R.string.nothing),
                            modifier = Modifier.align(Alignment.Center))
                    }
                    
                }
                // Показывает детали продукта, если showDetails равно true.
                AnimatedVisibility(visible = showDetails) {
                    productToHandle?.let {
                        val foundItem = dbList.value.find { productFromDb ->
                            productFromDb.id == productToHandle!!.id
                        }
                        DetailScreen(
                            modifier = Modifier.navigationBarsPadding(),
                            item = it,
                            //Обратный вызов при нажатии кнопки на экране подробностей, обновляющий базу данных и переключающий showDetails и showList.
                            onButtonClick = {
                                //Если productToHandle не равен null, отображает подробную информацию о выбранном товаре.
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
                            }
                        ) {
                            showList = true
                            showDetails = false
                        }
                    }
                }
            }
        }
    ) {

    }

}

//@Preview(showBackground = true)
//@Composable
//fun PreviewSearch() {
//    val sampleProduct = Product(
//        carbohydrates_per_100_grams = 20.00,
//        category_id = 664,
//        description = "This is a sample product description.",
//        energy_per_100_grams = 250.00,
//        fats_per_100_grams = 5.50,
//        id = 77,
//        image = "photo_product.png", // Placeholder image URL
//        measure = 200,
//        measure_unit = "g",
//        name = "Sample Product",
//        price_current = 12000,
//        price_old = 15000,
//        proteins_per_100_grams = 10.00,
//        tag_ids = listOf(2, 3, 4)
//    )
//
//    val productsViewModel = koinViewModel<ProductsViewModel>()
//
//    SearchScreen(Modifier, productsViewModel)
//}