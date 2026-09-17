package com.example.listycity3

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.listycity3.ui.theme.ListyCity3Theme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.FloatingActionButton
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContentProviderCompat.requireContext

@Composable
fun CityListScreen(
    cities: List<City>,
    onAddCity: (City) -> Unit,
    onEditCity: (City, City) -> Unit,
    modifier: Modifier = Modifier
) {
    var newCityName by remember {mutableStateOf("")}
    var newProvinceName by remember {mutableStateOf("")}
    var newEditCityName by remember {mutableStateOf("")}
    var newEditProvinceName by remember {mutableStateOf("")}
    var showAddCityFields by remember {mutableStateOf(false)}
    var selectedCity by remember {mutableStateOf<City?>(null)}
    var showEditCityFields by remember {mutableStateOf(false)}
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            //horizontalArrangement = Arrangement.End
        ) {
            if (selectedCity != null) {
                FloatingActionButton(modifier = Modifier.padding(16.dp), onClick = {
                    showEditCityFields = !showEditCityFields
                    showAddCityFields = false
                }) {
                    Text("Edit")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            FloatingActionButton(modifier = Modifier.padding(16.dp), onClick = {
                showAddCityFields = !showAddCityFields
                showEditCityFields = false
                selectedCity = null
            }) {
                Text("+")
            }
        }

        if (showEditCityFields) {
            Row (modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                OutlinedTextField(
                    value = newEditCityName,
                    onValueChange = { newEditCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newEditProvinceName,
                    onValueChange = { newEditProvinceName = it },
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newEditCityName.isNotBlank() && newEditProvinceName.isNotBlank()) {
                            onEditCity(selectedCity!!, City(newEditCityName, newEditProvinceName))
                            newEditCityName = ""
                            newEditProvinceName = ""
                            showEditCityFields = false
                            selectedCity = null
                        }
                    }
                ) {
                    Text("Edit City")
                }
            }
        }

        if (showAddCityFields) {
            Row(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                OutlinedTextField(
                    value = newCityName,
                    onValueChange = { newCityName = it },
                    label = { Text("City") },
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(8.dp))

                OutlinedTextField(
                    value = newProvinceName,
                    onValueChange = {newProvinceName = it},
                    label = { Text("Province") },
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    modifier = Modifier.padding(vertical = 12.dp),
                    onClick = {
                        if (newCityName.isNotBlank() && newProvinceName.isNotBlank()) {
                            onAddCity(
                                City(name = newCityName, province = newProvinceName)
                            )
                            newCityName = ""
                            newProvinceName = ""
                            showAddCityFields = false
                        }
                    }
                ) {
                    Text("Add City")
                }
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(cities) { index, city ->
                if (selectedCity == city) {
                    CityRow(city = city, {
                        selectedCity = null
                        showEditCityFields = false
                            }, modifier = Modifier.background(Color.LightGray))
                } else {
                    CityRow(city = city, { selectedCity = city })
                }


                if (index < cities.lastIndex) {
                    HorizontalDivider()
                }
            }
        }
    }
}

@Composable
fun CityRow(city: City, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .clickable { onClick() }
    ) {
        Text(
            text = city.name,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )

        Text(
            text = city.province,
            fontSize = 30.sp,
            modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CityListScreenPreview() {
    ListyCity3Theme {
        CityListScreen(
            cities = listOf(
                City("Edmonton", "AB"),
                City("Vancouver", "BC"),
                City("Calgary", "AB")
            ),

            onAddCity = {},
            onEditCity = {a: City, b: City -> }
        )
    }
}