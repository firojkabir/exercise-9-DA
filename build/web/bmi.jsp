<html>

<head>
    <title>BMI Calculator</title>
    <style>
        body {
            text-align: center;
            background: #D3D3D3;
        }
        table{
            width: 400px;
        }
        td {
            padding-bottom: 6px;
        }
    </style>
</head>

<body>
    <h1>BMI Form (Web Version 2021)</h1>
    <table align="center">
        <tr>
            <td>Name</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>Weight (kg)</td>
            <td><input type="number" name="weight"></td>
        </tr>
        <tr>
            <td>Height (m)</td>
            <td><input type="number" name="height"></td>
        </tr>
    </table>

    <button type="button" onclick="reset()">Reset</button>
    <button type="button" onclick="validate()">Submit</button>
    <br>
    <h2 id="output"></h2>


    <script>
        function reset() {
            document.getElementById("output").innerText = "";
            var elements = document.getElementsByTagName("input");
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].type == "text" || elements[i].type == "number") {
                    elements[i].value = "";
                }
            }
        }

        function validate() {
            var elements = document.getElementsByTagName("input");
            for (var i = 0; i < elements.length; i++) {
                if (elements[i].value == "") {
                    alert("Enter all values correctly");
                    return;
                }
            }
            calculateBMI();
        }

        function getResultString(name, BMI) {
            return `Name: ${name}\nBMI: ${BMI}\n`;
        }

        function makeUrl(name, height, weight) {
            return '/exercise-9?name=' + name + '&height=' + height + '&weight=' + weight;
        }

        function calculateBMI() {
            document.getElementById("output").innerText = "Processing...";
            var name = document.getElementsByName("name")[0].value;
            var height = Number(document.getElementsByName("height")[0].value);
            var weight = Number(document.getElementsByName("weight")[0].value);

            var xhttp = new XMLHttpRequest();
            xhttp.onreadystatechange = function () {
                if (this.readyState == 4 && this.status == 200) {
                    document.getElementById("output").innerText = this.responseText;
                }
            };
            var url = makeUrl(name, height, weight);
            console.log('URL = ' + url);
            xhttp.open("GET", url, true);
            xhttp.send();
        }
    </script>
</body>