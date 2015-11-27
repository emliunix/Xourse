# Xourse project

## Api Documentation

返回结果通过`status`来标志是否成功，该字段有两个值：`success`,`fail`。

### 登陆登出

* `/sys/login`
  * POST {username, password} -> {status, msg}
* `/sys/check_user`
  * GET -> {status, msg, user: {id, username, role}}
* `/sys/logout`
  * GET -> {status, msg}
  
### 用户相关

* `/api/me`
  * GET -> {status, msg, user: {id, username, rolename, uilinks: [{name, url}, ... ]}}

### 教师功能

教师功能挂在`/api/teacherself` 或者`/api/teacher/:id/`下面

* `/elective_courses` "教师选择想教的选修课"
  * GET -> {status, msg, courses: [{id, year, name, type, department, teacher}, ... ]}
  * POST [{flag, id}, ...] -> {status, msg}
* `/courses` "教师对其课程的操作"
  * GET -> {status, msg, courses: [{id, year, name, type, department, major, teacher, class, isSubmitted}, ...]}
* `/courses/year`
  * GET -> {status, msg, years: [year, ... ]}
* `/courses/year/:year` "操作对应年份的课程"
  * GET 和 `/courses` "结果格式相同"
* `/courses/:id` "教师评分"
  * GET -> {status, msg, regularRate, finalExamRate, isSubmitted, students: [{id, name, gender, regularGrade, finalExamGrade, finalGrade}, ... ]}
  * POST [{id, regularGrade, finalExamGrade}] -> {status, msg}
* `/courses/:id/rate`
  * GET -> {status, msg, regularRate, finalExamRate}
  * POST {regularRate, finalExamRate} -> {status, msg}
  
### 学生功能
  
学生功能放在`/api/studentself`或`/api/student/:id/`下面
  
* `/elective_courses` "学生查看选修课，选择选修课"
  * GET -> {status, msg, isModifiable, courses: [{id, year, name, type, department, teacher}, ... ]}
  * POST [{action, id}, ... ] -> {status, msg}
* `/courses` "查看课程"
  * GET -> {status, msg, courses: [{id, year, name, type, department, major, teacher, class, regularGrade, finalExamGrade, finalGrade}, ...]}
    
### 管理员功能

### 学院，专业，班级数据

* `/api/department`
  * GET -> {status, msg, departments: [{id, name, links: { detail }}, ... ]}
  * POST {name} -> {status, msg, department: {id, name}}
* `/api/department/:id`
  * GET -> {status, msg, department: {id, name, links: {majors, teachers}}} 
  * PUT {id, name} -> {status, msg, department: {id, name}} 
  * DELETE {id} -> {status, msg}
* `/api/department/:id/majors`
  * ***Unimplemented***
* `/api/department/:id/teachers`
  * ***Unimplemented***

* `/api/major`
  * GET -> {status, msg, majors: [{id, name, detail}, ... ]}
  * POST {name} -> {status, msg, department: {id, name}}
* `/api/major/:id`
  * GET -> {status, msg, major: {id, name, links: {department, classes}}}
  * PUT {id, name} -> {status, msg, department: {id, name}} 
  * DELETE {id} -> {status, msg}