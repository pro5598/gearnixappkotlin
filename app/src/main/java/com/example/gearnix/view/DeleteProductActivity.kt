package com.example.gearnix.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.gearnix.model.ProductModel
import com.example.gearnix.repository.ProductRepositoryImpl
import com.example.gearnix.ui.theme.GearnixTheme
import com.example.gearnix.viewmodel.ProductViewModel

class DeleteProductActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GearnixTheme {
                DeleteProductBody()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
sp
                                )
                                Text(
                                    text = when {
                                        validProducts.isEmpty() -> "NO PRODUCTS ADDED YET"
                                        searchQuery.isNotEmpty() -> "NO MATCHING PRODUCTS"
                                        else -> "ALL PRODUCTS DELETED"
                                    },
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = when {
                                        validProducts.isEmpty() -> neonPurple
                                        searchQuery.isNotEmpty() -> neonPurple
                                        else -> neonGreen
                                    },
                                    textAlign = TextAlign.Center,
                                    letterSpacing = 1.sp
                                )
                                Text(
                                    text = when {
                                        validProducts.isEmpty() -> "Add some gaming products first to manage them"
                                        searchQuery.isNotEmpty() -> "Try adjusting your search terms"
                                        else -> "Your inventory is completely clean"
                                    },
                                    fontSize = 14.sp,
                                    color = placeholderColor,
                                    textAlign = TextAlign.Center
                                )

                                if (validProducts.isEmpty()) {
                                    Spacer(modifier = Modifier.height(16.dp))

                                    Button(
                                        onClick = {
                                            val intent = Intent(context, AddProductActivity::class.java)
                                            context.startActivity(intent)
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = neonBlue),
                                        shape = RoundedCornerShape(12.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Add,
                                            contentDescription = null,
                                            modifier = Modifier.size(16.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            "ADD FIRST PRODUCT",
                                            fontWeight = FontWeight.Bold,
                                            letterSpacing = 1.sp
                                        )
                                    }
                                }
                            }
                        }
                    }

                    else -> {
                        // Products List
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(filteredProducts) { product ->
                                DeletableProductCard(
                                    product = product,
                                    onDeleteClick = {
                                        selectedProduct = product
                                        showDeleteDialog = true
                                    },
                                    cardColor = cardColor,
                                    neonBlue = neonBlue,
                                    neonPurple = neonPurple,
                                    neonGreen = neonGreen,
                                    dangerRed = dangerRed,
                                    textColor = textColor,
                                    placeholderColor = placeholderColor
                                )
                            }
                        }
                    }
                }
            }

            // 🔥 IMPROVED: Delete Confirmation Dialog
            if (showDeleteDialog && selectedProduct != null) {
                AlertDialog(
                    onDismissRequest = {
                        if (!isDeleting) {
                            showDeleteDialog = false
                            selectedProduct = null
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = dangerRed,
                            modifier = Modifier.size(32.dp)
                        )
                    },
                    title = {
                        Text(
                            text = "DELETE GAMING GEAR?",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = dangerRed,
                            textAlign = TextAlign.Center
                        )
                    },
                    text = {
                        Column {
                            Text(
                                text = "Are you sure you want to permanently delete:",
                                fontSize = 14.sp,
                                color = textColor,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = selectedProduct!!.productName,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = neonBlue,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "⚠️ This action cannot be undone!",
                                fontSize = 12.sp,
                                color = dangerRed,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    },
                    confirmButton = {
                        Button(
                            onClick = {
                                isDeleting = true
                                Log.d("DeleteProductActivity", "Deleting product: ${selectedProduct!!.productName}")

                                productViewModel.deleteProduct(selectedProduct!!.productID) { success, message ->
                                    isDeleting = false
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()

                                    if (success) {
                                        Log.d("DeleteProductActivity", "Product deleted successfully, refreshing list")
                                        showDeleteDialog = false
                                        selectedProduct = null
                                        // 🔥 CRITICAL FIX: Force refresh after deletion
                                        productViewModel.refreshProducts()
                                    } else {
                                        Log.e("DeleteProductActivity", "Failed to delete product: $message")
                                    }
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = dangerRed),
                            shape = RoundedCornerShape(8.dp),
                            enabled = !isDeleting
                        ) {
                            if (isDeleting) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    color = Color.White,
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(
                                if (isDeleting) "DELETING..." else "DELETE",
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                        }
                    },
                    dismissButton = {
                        Button(
                            onClick = {
                                if (!isDeleting) {
                                    showDeleteDialog = false
                                    selectedProduct = null
                                }
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF30363D)),
                            shape = RoundedCornerShape(8.dp),
                            enabled = !isDeleting
                        ) {
                            Text(
                                "CANCEL",
                                fontWeight = FontWeight.Bold,
                                color = textColor
                            )
                        }
                    },
                    containerColor = cardColor,
                    shape = RoundedCornerShape(16.dp)
                )
            }
        }
    }
}

@Composable
fun DeletableProductCard(
    product: ProductModel,
    onDeleteClick: () -> Unit,
    cardColor: Color,
    neonBlue: Color,
    neonPurple: Color,
    neonGreen: Color,
    dangerRed: Color,
    textColor: Color,
    placeholderColor: Color
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(16.dp),
                ambientColor = dangerRed.copy(alpha = 0.1f)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image
            Card(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                if (product.image.isNotEmpty()) {
                    AsyncImage(
                        model = product.image,
                        contentDescription = product.productName,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(neonBlue.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "🎮",
                            fontSize = 24.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Product Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = product.productName,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = textColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = product.description,
                    fontSize = 14.sp,
                    color = placeholderColor,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Row(
                    modifier = Modifier.padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = null,
                        tint = neonGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = "$${product.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = neonGreen,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }

            // Delete Button
            IconButton(
                onClick = onDeleteClick,
                modifier = Modifier.size(40.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete Product",
                    tint = dangerRed,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun DeleteProductPreview() {
    GearnixTheme {
        DeleteProductBody()
    }
}
