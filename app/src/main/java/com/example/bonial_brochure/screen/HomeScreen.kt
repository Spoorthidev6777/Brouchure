package com.example.bonial_brochure.screen

import androidx.compose.runtime.Composable


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.bonial_brochure.data.Brochure
import com.example.bonial_brochure.viewmodel.BrochureViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: BrochureViewModel) {
    val brochures by viewModel.brochures.observeAsState(emptyList())

    // Load data once
    LaunchedEffect(Unit) {
        viewModel.loadBrochures()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Brochures",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 40.sp
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                BrochureList(brochures)
            }
        }
    )
}

@Composable
fun BrochureList(brochures: List<Brochure>) {
    LazyColumn {
        items(brochures) { brochure ->
            BrochureItem(brochure)
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
                brochure.retailerName?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}
