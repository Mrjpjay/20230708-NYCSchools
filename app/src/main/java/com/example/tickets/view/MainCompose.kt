package com.example.tickets.view

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun SchoolCard(school: SchoolData, mainActivity: MainActivity) {
    Card(modifier = Modifier.padding(6.dp),
        onClick = {
            val intent = Intent(mainActivity, DetailActivity::class.java).apply {
                this.putExtra("dbn",school.dbn)
            }
            mainActivity.startActivity(intent)
        }) {
        SchoolText(school)
    }
}

@Composable
private fun SchoolText(school: SchoolData) {
    Text(
        text = "Name: ${school.schoolName}" +
                "\nNeighborhood: ${school.neighborhood} " +
                "\nLocation:${school.location}" +
                "\nWebsite: ${school.website}",
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
@Preview
fun Preview() {
    SchoolText(
        school = SchoolData(
            schoolName = "MaryVille",
            neighborhood = "Chelsea-Union Sq",
            location = "10 East 15th Street, Manhattan NY 10003 (40.736526, -73.992727)",
            website = "www.me.com"
        )
    )
}