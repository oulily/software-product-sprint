// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

function getRandomGreeting() {
    fetch('/data').then(response => response.json()).then((greetings) => {
        const greetingsListElement = document.getElementById('greeting-container');
        greetingsListElement.innerHTML = greetings;
        console.log(greetings);
    });
}

function getLoginStatus() {

}

google.charts.load('current', {'packages':['corechart']});
google.charts.setOnLoadCallback(drawPetChart);
google.charts.setOnLoadCallback(drawLanguageChart);
google.charts.setOnLoadCallback(drawFoodChart);

/** Creates a chart and adds it to the page. */
function drawPetChart() {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Animal');
    data.addColumn('number', 'Count');
        data.addRows([
            ['Fish', 158.1],
            ['Cat', 94.2],
            ['Dog', 89.7],
            ['Bird', 20.3],
            ['Reptile', 9.4]
        ]); 
    const options = {
        'title': 'Pets in America',
        'width':450,
        'height':300
    };
    const chart = new google.visualization.PieChart(document.getElementById('pet-chart-container'));
    chart.draw(data, options);
}

/** Fetches language data and uses it to create a chart. */
function drawLanguageChart() {
  fetch('/language-data').then(response => response.json())
  .then((mostSpokenLanguages) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Language');
    data.addColumn('number', 'Population (Millions)');
    Object.keys(mostSpokenLanguages).forEach((language) => {
      data.addRow([language, mostSpokenLanguages[language]]);
    });

    const options = {
      'title': 'Most Spoken Languages in the World',
      'width':700,
      'height':450
    };

    const chart = new google.visualization.BarChart(
        document.getElementById('language-chart-container'));
    chart.draw(data, options);
  });
}

/** Fetches food data and uses it to create a chart. */
function drawFoodChart() {
  fetch('/food-data').then(response => response.json())
  .then((foodVotes) => {
    const data = new google.visualization.DataTable();
    data.addColumn('string', 'Preference');
    data.addColumn('number', 'Votes');
    Object.keys(foodVotes).forEach((preference) => {
      data.addRow([preference, foodVotes[preference]]);
    });

    const options = {
      'title': 'Food Preferences',
      'width':500,
      'height':450
    };

    const chart = new google.visualization.ColumnChart(
        document.getElementById('food-chart-container'));
    chart.draw(data, options);
  });
}
