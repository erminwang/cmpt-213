"use strict";

const myAppObj = new Vue({
    el: "#browseApp",
    data: {
        departments: [],
        selectedDept: null,

        courses: [],
        selectedCourse: null,

        courseOfferings: [],
        selectedOffering: null,

        offeringDetails: []
    },
    methods: {
        selectCourse: function(course) {
            myAppObj.selectedCourse = course;
        },
        selectOffering: function(offering) {
            myAppObj.selectedOffering = offering;
        },
    },
    watch: {
        selectedDept: function (val) {
            console.log("Running course update on department ID " + myAppObj.selectedDept.deptId)
            myAppObj.selectedCourse = null;
            myAppObj.selectedOffering = null;
            loadDepartmentCourses(myAppObj.selectedDept.deptId);
        },
        selectedCourse: function(val) {
            myAppObj.selectedOffering = null;
            if (myAppObj.selectedCourse) {
                console.log("Running course offerings update on course ID " + myAppObj.selectedCourse.courseId)
                loadCourseOfferings(myAppObj.selectedDept.deptId, myAppObj.selectedCourse.courseId);
            }
        },
        selectedOffering: function(val) {
            if (myAppObj.selectedOffering) {
                console.log("Getting data for offerings ID " + myAppObj.selectedOffering.courseOfferingId);
                loadOfferingDetails(
                    myAppObj.selectedDept.deptId,
                    myAppObj.selectedCourse.courseId,
                    myAppObj.selectedOffering.courseOfferingId);
            }
        },
    }
});



$(document).ready(function() {
    loadDepartments();
    if (myAppObj.selectedDept != null) {
        loadDepartmentCourses(myAppObj.selectedDept.deptId);
    }
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
function loadCourseOfferings(deptId, courseId) {
    axios.get('/api/departments/' + deptId + "/courses/" + courseId + "/offerings", {})
          .then(function (response) {
            console.log(response);
            myAppObj.courseOfferings = response.data;
          })
          .catch(function (error) {
            console.log(error);
          });
}
function loadOfferingDetails(deptId, courseId, offeringId) {
      axios.get('/api/departments/' + deptId + "/courses/" + courseId + "/offerings/" + offeringId, {})
            .then(function (response) {
              console.log(response);
              myAppObj.offeringDetails = response.data;
            })
            .catch(function (error) {
              console.log(error);
            });
}