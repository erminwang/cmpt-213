#!/bin/sh -x
SERVER=localhost:8080


#################################################
#   General
#################################################
# Read /about
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/about

# Dump model
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/dump-model

#################################################
#   Data Access
#################################################

# NOTE: Your ID's will likely be different from these!
# Change them to match your ID's for your own testing.
DEPT=2      # CMPT
COURSE=103  # 213
OFFERING=7063  # Brian's 2018 offering


# Get departments
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments

# Get courses of CMPT
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/${DEPT}/courses

# Get sections of course
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/${DEPT}/courses/${COURSE}/offerings

# Get specific course offering
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/${DEPT}/courses/${COURSE}/offerings/${OFFERING}


# errors
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/99999/courses
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/${DEPT}/courses/99999/offerings
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/9999/courses/${COURSE}/offerings
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/99999/courses/${COURSE}/offerings/${OFFERING}
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/${DEPT}/courses/9999999/offerings/${OFFERING}
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/departments/${DEPT}/courses/${COURSE}/offerings/99999


# Get graph data
# ------------------------------
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/stats/students-per-semester?deptId=${DEPT}

# error
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/stats/students-per-semester?deptId=9999


# Add Offering
# -------------------------------
# NOTE: Adding data to the model may require refreshing the UI
# (F5 in the browser, or reload the page by navigating to another page)
curl -i -H "Content-Type: application/json" \
    -X POST  -d '{
        "semester": 1191,
        "subjectName": "CMPT",
        "catalogNumber": "213",
        "location": "SURREY",
        "enrollmentCap": 90,
        "component": "LEC",
        "enrollmentTotal": 89,
        "instructor": "Brian Fraser"
    }' ${SERVER}/api/addoffering

curl -i -H "Content-Type: application/json" \
    -X POST  -d '{
        "semester": 1181,
        "subjectName": "CMPT",
        "catalogNumber": "300",
        "location": "SURREY",
        "enrollmentCap": 101,
        "component": "TUT",
        "enrollmentTotal": 100,
        "instructor": "Dr. Evil"
    }' ${SERVER}/api/addoffering

# Test adding new course
curl -i -H "Content-Type: application/json" \
    -X POST  -d '{
        "semester": 1191,
        "subjectName": "CMPT",
        "catalogNumber": "XX9",
        "location": "SURREY",
        "enrollmentCap": 10,
        "component": "TUT",
        "enrollmentTotal": 6,
        "instructor": "Dr. Alice"
    }' ${SERVER}/api/addoffering

# Test adding new department
curl -i -H "Content-Type: application/json" \
    -X POST  -d '{
        "semester": 1191,
        "subjectName": "ABCD",
        "catalogNumber": "101",
        "location": "Vancouver",
        "enrollmentCap": 123,
        "component": "LEC",
        "enrollmentTotal": 110,
        "instructor": "Mystery Instructor"
    }' ${SERVER}/api/addoffering

# Watchers
# -------------------------------
# List watchers
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/watchers

# Create watcher
curl -i -s -H "Content-Type: application/json" \
    -X POST -d "{
        \"deptId\": ${DEPT},
        \"courseId\": ${COURSE}
    }" ${SERVER}/api/watchers

# Get events for watcher
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/watchers/1

# Delete Watcher
curl -i -X DELETE ${SERVER}/api/watchers/1

# errors
curl -i -s -H "Content-Type: application/json" \
    -X POST -d "{
        \"deptId\": 999999,
        \"courseId\": ${COURSE}
    }" ${SERVER}/api/watchers
curl -i -s -H "Content-Type: application/json" \
    -X POST -d "{
        \"deptId\": ${DEPT},
        \"courseId\": 99999
    }" ${SERVER}/api/watchers
curl -i -s -H "Content-Type: application/json" \
    -X GET ${SERVER}/api/watchers/999999
curl -i -X DELETE ${SERVER}/api/watchers/9999999
