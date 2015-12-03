# Xourse project

## Api Documentation

返回结果通过`status`来标志是否成功，该字段有两个值：~~`success`,`fail`。~~现在改为`true`和`false`，json中的布尔值。

### 登陆登出

* `/sys/login`
  * POST {username, password} -> {status, msg}
* `/sys/check_user`
  * GET -> {status, msg, user: {id, username, role}}
* `/sys/logout`
  * GET -> {status, msg}
  
### 用户相关

politicalStatus 是 政治形象的意思
signature 是 个人签名的意思
idCardNumber 是 身份证号的意思
residence 是 籍贯的意思

* `/api/user`
  * GET -> {msg, status, users: [{id, username, role}, ... ]}
  * `/self` 代理给当前登录的用户的具体用户类型的处理接口 `/api/<userType>/{id}`
  * `/{id}`
    * GET -> {msg, status, user: {id, username, role}}
    * PUT {id, username, password} -> {msg, status, user: {id, username, role}}
    * DELETE -> {msg, status}
* `/api/student`
  * GET -> {msg, status, students: [{id, name, year, studentId, majorInfo: {id, name, year}}, ...]}
  * POST {name, year, studentId, majorId, idCardNumber, telNumber, email, residence, politicalStatus, signature}
    -> {msg, status, student: {id, name, year, studentId, majorInfo: {id, name, year}, idCardNumber, telNumber, email, residence, politicalStatus, signature}}
  * `/self` 代理给具体的 `/{id}`
  * `/{id}`
    * GET -> {msg, status, student: {id, name, year, studentId, majorInfo: {id, name, year}, idCardNumber, telNumber, email, residence, politicalStatus, signature}}
    * PUT {name, year, studentId, majorId, idCardNumber, telNumber, email, residence, politicalStatus, signature}
      -> {msg, status, student: {id, name, year, studentId, majorInfo: {id, name, year}, idCardNumber, telNumber, email, residence, politicalStatus, signature}}
    * DELETE -> {msg, status}
* `/api/teacher`
  * GET -> {msg, status, teachers: [{id, name, year, teacherId, department: {id, name}}, ...]}
  * POST {name, year, teacherId, departmentId, idCardNumber, telNumber, email, residence, politicalStatus, title, signature}
    -> {msg, status, teacher: {id, name, year, teacherId, department: {id, name}, idCardNumber, telNumber, email, residence, politicalStatus, title, signature}}
  * `/self` 代理给具体的 `/{id}`
  * `/{id}`
    * GET -> {msg, status, teacher: {id, name, year, teacherId, department: {id, name}, idCardNumber, telNumber, email, residence, politicalStatus, title, signature}}
    * PUT {name, year, teacherId, departmentId, idCardNumber, telNumber, email, residence, politicalStatus, title, signature}
    -> {msg, status, teacher: {id, name, year, teacherId, department: {id, name}, idCardNumber, telNumber, email, residence, politicalStatus, title, signature}}
    * DELETE -> {msg, status}
* `/api/admin`
  * GET -> {msg, status, admins: [{id, username, role}, ...]}
  * POST {username, password} -> {msg, status, admin: {id, username, role}}
  * `/self` 代理给具体的 `/{id}`
  * `/{id}`
    * GET -> {msg, status, admin: {id, username, role}}
    * PUT {username, password} -> {msg, status, admin: {id, username, role}}
    * DELETE -> {msg, status}

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

管理员负责管理用户，课程，新闻。
对课程的管理功能包括对必修和选修课程的添加。

* `/compulsory`
  * `/year`
    * GET
* `/elective`

### 学院，专业，班级数据

* `/api/department`
  * GET -> {status, msg, departments: [{id, name}, ... ]}
  * POST {name} -> {status, msg, department: {id, name}}
  * `/:id`
    * GET -> {status, msg, department: {id, name}} 
    * PUT {name} -> {status, msg, department: {id, name}} 
    * DELETE -> {status, msg}
    * `/majors`
      * GET -> {msg, satus, majors: [{id, name}, ... ]}
    * `/teachers`
      * GET -> {msg, status, teachers: [{id, name, teacherId}, ... ]}
* `/api/major`
  * GET -> {status, msg, majors: [{id, name}, ... ]}
  * POST {name, departmentId} -> {status, msg, major: {id, name, department: {id, name}}} // department.name 目前没有
  * `/{id}`
    * GET -> {status, msg, major: {id, name, department: {id, name}}}
    * PUT {name， departmentId} -> {status, msg, major: {id, name, department: {id, name}}
    * DELETE -> {status, msg}
    * `/classes`
      * GET -> {msg, status, majorClasses: [{id, name, year}, ... ]}
* `/api/major_class`
  * GET -> {msg, status, majorClasses: [{id, name, year}, ... ]}
  * POST {majorId, year, name} -> {msg, status, majorClass: {id, name, year, major: {id, name, department}}} // major 目前只有id
  * `/{id}`
    * GET -> {msg, status, majorClass: {id, name, year, major: {id, name, department}}} // department 没有
    * PUT {majorId, year, name} -> {msg, status, majorClass: {id, name, year, major: {id, name, department}}} // department 没有
    * DELETE -> {msg, status}
    * `/students`
      * GET -> {msg, status, students: [{id, name, studentId}, ... ]}

## 其他数据

* `/year`
  GET -> {msg, status, years: [year, ... ]}