package com.example.tickets.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tickets.R
import com.example.tickets.api.SchoolDetailData
import com.example.tickets.viewmodel.SchoolDetViewModel

@Composable
fun DetailCompose(dbn: String?) {
    val viewModel = viewModel(modelClass = SchoolDetViewModel::class.java)
    LaunchedEffect(dbn){
        viewModel.getSchoolDetail("$dbn")
    }
    val empty by viewModel.noSchool.collectAsState()
    val error by viewModel.error.collectAsState()
    val schoolDetail by viewModel.schoolDetail.collectAsState()
    ShowError(error)
    SchoolDetails(empty, schoolDetail)
}

@Composable
fun ShowError(error: Boolean) {
    if (error) {
        Toast.makeText(LocalContext.current, "Something went wrong", Toast.LENGTH_SHORT).show()
    }
}

@Composable
fun SchoolDetails(empty: Boolean, schoolDetail: SchoolDetailData) {
    if (empty) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            NoSchool()
        }
    } else {
        TextDetails(schoolDetail)
    }
}

@Composable
fun TextDetails(schoolDetail: SchoolDetailData) {

    Column {
        Text(
            text = "${schoolDetail.schoolName}",
            fontWeight = FontWeight.Bold
        )
        Text( modifier = Modifier.padding(top = 8.dp),
            text = "SAT Writing Avg Score: ${schoolDetail.satWritingAvgScore}" +
                "\nSat math Avg Score: ${schoolDetail.satMathAvgScore}" +
                "\nSat Critical Reading Avg Score: ${schoolDetail.satCriticalReadingAvgScore}" +
                "\n Num of Sat Test Takers${schoolDetail.numOfSatTestTakers}")
    }
}

@Composable
fun NoSchool() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.baseline_school_24),
            contentDescription = ""
        )
        Text(text = "No SAT Found")
    }
}
@Composable
@Preview
fun Prev(){
    SchoolDetails(
        empty = false,
        schoolDetail = SchoolDetailData(
            schoolName = "Maryville University"
        )
    )
}