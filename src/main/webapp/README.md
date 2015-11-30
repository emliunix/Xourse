# Xourse project

## Api Documentation

返回结果通过`status`来标志是否成功，该字段有两个值：`success`,`fail`。

### 登陆登出

* `/sys/login`
  * POST {username, password} -> {status, msg}
* `/sys/logout`
  * GET -> {status, msg}
  
### 用户相关

* `/api/me`
  * GET -> {status, msg, user: {id, username, rolename, uilinks: [{name, url}, ... ]}}

### 教师功能

教师功能挂在`/api/teacher/[:id/]下面

* `/elective_courses` "教师选择想教的选修课"
  * GET -> {status, msg,IsSubmitted(新增), courses: [{id, year, name, type, department}, ... ]} ///////加一个标志为IsSubmitted
  * POST [{id}, ...] -> {status, msg}  //////{data:[{id}, ...]}
* `/courses` "教师对其课程的操作"
  * GET -> {status, msg, courses: [{id, year, name, type, department, major, teacher, class, isSubmitted}, ...]}
* `/courses/year/:year` "操作对应年份的课程"
  * GET 和 `/courses` "结果格式相同"
* `/courses/:id` "教师评分"
  * GET -> {status, msg, regularRate, finalExamRate, isSubmitted, students: [{id, name, gender, regularGrade, finalExamGrade, finalGrade}, ... ]}
  * POST data:'[{id, regularGrade, finalExamGrade}] -> {status, msg} 
  
  ///////////post数据还要加上是提交还是保存，加一个isSubmitted来标志{isSubmitted,students:[{id, regularGrade, finalExamGrade,finalGrade},....]}
  
### 学生功能
  
学生功能放在`/api/student/[:id/]`下面
  
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
  
  
  
  //修改个人信息（教师、学生端都有）
  url:你来定
  Get 我需要的json格式：{status,user:[id,name(姓名),number(学号),political(政治面貌),birthplace(籍贯),hobby,phone,email}...]}
  post:{data:JSON.stringfy([id,name(姓名),number(学号),political(政治面貌),birthplace(籍贯),hobby,phone,email}...])}
  
  
 //修改密码（教师、学生端都有）：
 url:你来定
 post:{oldpwd,newpwd}  =>{status}(如果旧密码不同，返回false)