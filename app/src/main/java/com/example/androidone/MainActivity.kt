package com.example.androidone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.androidone.ui.theme.AndroidOneTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var products by remember { mutableStateOf(listOf<Product>()) }

            AndroidOneTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    content = { innerPadding ->
                        Column(modifier = Modifier.padding(innerPadding)) {
                            AddProductView { product ->
                                products = products + product
                            }
                            ShoppingListView(products)
                        }
                    }
                )
            }
        }
    }


    @Composable
fun AddProductView(onAddProduct: (Product) -> Unit) {
    var name by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Product Name") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = quantity,
            onValueChange = { quantity = it },
            label = { Text("Quantity") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = price,
            onValueChange = { price = it },
            label = { Text("Price") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if (name.isNotBlank()) {
                onAddProduct(
                    Product(
                        name = name,
                        quantity = quantity.toIntOrNull(),
                        price = price.toDoubleOrNull()
                    )
                )
                name = ""
                quantity = ""
                price = ""
            }
        }) {
            Text("Add Product")
        }
    }
}

    @Composable
fun ShoppingListView(products: List<Product>) {
    val totalProducts = products.size
    val totalPrice = products.sumOf { it.price ?: 0.0 }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Total Products: $totalProducts")
        Text("Total Price: $totalPrice")
        Spacer(modifier = Modifier.height(16.dp))
        products.forEach { product ->
            Text("Name: ${product.name}, Quantity: ${product.quantity ?: "N/A"}, Price: ${product.price ?: "N/A"}")
        }
    }
}
}