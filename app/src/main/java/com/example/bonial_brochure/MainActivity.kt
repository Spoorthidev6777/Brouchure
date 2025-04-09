package com.example.bonial_brochure

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.bonial_brochure.data.Brochure
import com.example.bonial_brochure.viewmodel.BrochureViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val brochureViewModel: BrochureViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            Scaffold(
                topBar = {

                    TopAppBar(
                        title = { Text("Brochures", style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                    )
                },
                content = { paddingValues ->

                    Column(modifier = Modifier.padding(paddingValues)) {
                        // Observe and load brochures from ViewModel
                        val brochures = brochureViewModel.brochures.observeAsState(initial = emptyList())

                        LaunchedEffect(Unit) {
                            brochureViewModel.loadBrochures()
                        }

                        BrochureList(brochures = brochures.value)
                    }
                }
            )
        }
    }
}

@Composable
fun BrochureList(brochures: List<Brochure>) {
    LazyColumn {
        items(brochures) { brochure ->
            BrochureItem(brochure = brochure)
        }
    }
}

@Composable
fun BrochureItem(brochure: Brochure) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(brochure.imageUrl),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(end = 8.dp)
            ) {
                Text(
                    text = "${brochure.retailerName}",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
