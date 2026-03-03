const char html_page[] PROGMEM = R"rawSrting(
<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
  body {
    font-family: sans-serif;
    background-color: cyan;
    text-align: center;
  }

  .box {
    border: 3px solid black;
    display: inline-block;
    padding: 15px 40px;
    font-size: 25px;
    margin-top: 20px;
    background-color: white;
  }

  .valueBox {
    border: 3px solid black;
    display: inline-block;
    padding: 20px 60px;
    font-size: 40px;
    margin-top: 10px;
    background-color: white;
    color: #4CAF50;
  }

  canvas {
    background-color: white;
    border: 3px solid black;
    margin-top: 30px;
  }
</style>
</head>

<body>

<div class="box">Temperature</div><br>
<div class="valueBox"><span id="TempValue">0</span>&deg;C</div><br>

<div class="box">Max: <span id="maxTemp">0</span>&deg;C</div><br>
<div class="box">Min: <span id="minTemp">0</span>&deg;C</div><br>
<div class="box">Average: <span id="avgTemp">0</span>&deg;C</div>

<br><br>

<canvas id="tempChart" width="400" height="200"></canvas>

<script>
let maxTemp = -100;
let minTemp = 100;
let sumTemp = 0;
let count = 0;

const ctx = document.getElementById('tempChart').getContext('2d');
const chart = new Chart(ctx, {
  type: 'line',
  data: {
    labels: [],
    datasets: [{
      label: 'Temperature (°C)',
      data: [],
      borderColor: 'red',
      fill: false
    }]
  },
  options: {
    animation: false,
    scales: {
      y: {
        beginAtZero: false
      }
    }
  }
});

setInterval(function() {
  fetch("/readTemp")
    .then(response => response.text())
    .then(data => {
      let temp = parseFloat(data);

      document.getElementById("TempValue").innerHTML = temp;

      // Atualiza estatísticas
      if (temp > maxTemp) maxTemp = temp;
      if (temp < minTemp) minTemp = temp;
      sumTemp += temp;
      count++;
      let avgTemp = (sumTemp / count).toFixed(2);

      document.getElementById("maxTemp").innerHTML = maxTemp.toFixed(2);
      document.getElementById("minTemp").innerHTML = minTemp.toFixed(2);
      document.getElementById("avgTemp").innerHTML = avgTemp;

      // Atualiza gráfico
      chart.data.labels.push('');
      chart.data.datasets[0].data.push(temp);

      if (chart.data.labels.length > 20) {
        chart.data.labels.shift();
        chart.data.datasets[0].data.shift();
      }

      chart.update();
    });
},1000);
</script>

</body>
</html>
)rawSrting";