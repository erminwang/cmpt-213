"use strict";
const myAppObj = new Vue({
    el: "#watcherApp",
    data: {
        watchers: [],
        selectedWatcher: null,

        departments: [],
        selectedDept: null,

        courses: [],
        selectedCourse: null,

    },
    methods: {
        selectWatcher: function(watcher) {
            myAppObj.selectedWatcher = watcher;
            loadWatchers();
        },
        makeWatcher: function() {
            serverCreateWatcher(
                myAppObj.selectedDept.deptId,
                myAppObj.selectedCourse.courseId);
        },
    },
    watch: {
        selectedDept: function (val) {
            console.log("Running course update on department ID " + myAppObj.selectedDept.deptId)
            myAppObj.selectedCourse = null;
            loadDepartmentCourses(myAppObj.selectedDept.deptId);
        },
    }
});

$(document).ready(function() {
    loadWatchers();
    loadDepartments();
});


function loadWatchers() {
    axios.get('/api/watchers', {})
      .then(function (response) {
        console.log(response);
        myAppObj.watchers = response.data;
      })
      .catch(function (error) {
        console.log(error);
      });
}
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

function loadDepartmentCourses(deptId) {
    axios.get('/api/departments/' + deptId + "/courses", {})
          .then(function (response) {
            console.log(response);
            myAppObj.courses = response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
}
function serverCreateWatcher(deptId, courseId) {
    axios.post('/api/watchers', {
        deptId: deptId,
        courseId: courseId,
      })
      .then(function (response) {
        console.log(response);
        loadWatchers();
      })
      .catch(function (error) {
        console.log(error);
      });
}
