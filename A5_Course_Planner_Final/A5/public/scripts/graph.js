"use strict";


// Graph code from https://canvasjs.com/javascript-charts/line-chart-data-markers/
const myAppObj = new Vue({
    el: "#graphApp",
    data: {
        departments: [],
        selectedDept: null,

        graphData: [],
    },
    methods: {
    },
    watch: {
        selectedDept: function (val) {
            loadGraphData();
        },
        graphData: function(val) {
            drawChart();
        }
    }
});



$(document).ready(function() {
    loadDepartments();
    if (myAppObj.selectedDept != null) {
        loadDepartmentCourses(myAppObj.selectedDept.deptId);
    }
    drawChart()
});

function loadDepartments() {
    axios.get('/api/departments', {})
      .then(function (response) {
        console.log(response);
        myAppObj.departments = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
}
function loadGraphData() {
    axios.get('/api/stats/students-per-semester', {
         params: {
           deptId: myAppObj.selectedDept.deptId
         }
      })
      .then(function (response) {
        console.log(response);
        myAppObj.graphData = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
}

function makeData() {
    var data = [];
    for (var i = 0; i < myAppObj.graphData.length; i++) {
        var point = myAppObj.graphData[i];
        data.push( {
            label: point.semesterCode,
            y: point.totalCoursesTaken,
        })
    }
    return data;
}

function drawChart() {
    // From: https://canvasjs.com/javascript-charts/line-chart-data-markers/
    var chart = new CanvasJS.Chart("chartContainer", {
        theme: "light2", // "light1", "light2", "dark1", "dark2"
        animationEnabled: false,
        title:{
            text: "Lecture Seats Taken per Semester"
        },
        axisX: {
            title: "Semester",
            interval: 1,
        },
        axisY:{
            title: "# Seats Taken",
        },
        data: [{
            type: "line",
            markerSize: 12,
            dataPoints: makeData()
        }]
    });
    chart.render();
}