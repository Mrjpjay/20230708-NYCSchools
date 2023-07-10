package com.example.tickets.view

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tickets.api.SchoolData
import com.example.tickets.viewmodel.SchoolsViewModel

@Composable
fun MainCompose(mainActivity: MainActivity) {
    val viewModel = viewModel(modelClass = SchoolsViewModel::class.java)
    val loading by viewModel.loading.collectAsState()
    MyProgress(loading)
    val list by viewModel.list.collectAsState()
    LazyColumn {
        items(list) { school ->
            SchoolCard(school,mainActivity)
        }
    }
}

@Composable
fun MyProgress(loading: Boolean) {
    if (loading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolCard(school: SchoolData, mainActivity: MainActivity?) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        onClick = {
            val intent = Intent(mainActivity, DetailActivity::class.java).apply {
                this.putExtra("dbn",school.dbn)
            }
            mainActivity?.startActivity(intent)
        }) {
        Box(
            modifier = Modifier
                .background(Color.White)
                .padding(8.dp)
                .fillMaxSize()
            ,
        ){
        SchoolText(school)
        }
    }
}

@Composable
private fun SchoolText(school: SchoolData) {
    Column(modifier = Modifier.fillMaxHeight()) {
        Text(text = "${school.schoolName}",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.background(Color.Transparent)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                contentAlignment = Alignment.Center
            ){
                Text(text = school.location)
            }
            Card{ Text(text = school.website) }
        }
    }
}

@Composable
@Preview
fun Preview() {
    SchoolCard(
        school = SchoolData(
            schoolName = "Maryville",
            location = "123 E aven, tx PA 19107",
        website = "www.lkasjdlkerg.so"),
        mainActivity = null)
}