
// 学生控制器

appControllers.controller('stumanageCtrl',['$scope','$mdDialog', '$http', function($scope, $mdDialog, $http){
    var openAlert = openAlertFactory($mdDialog);
    var openConfirm = openConfirmFactory($mdDialog);

    $scope.items = [];/*[ {
     id: "1",
     username: "2013221104220024",
     name: "刘宇辉",
     majorClass: {
     name: "软工2013",
     major: {
     name: "软件工程"
     }
     },
     year: "2013"
     }, {
     id: "2",
     username: "2013221104220023",
     name: "柯波",
     majorClass: {
     name: "软工2013",
     major: {
     name: "软件工程"
     }
     },
     year: "2013"
     }];*/

    $scope.refresh = function() {
        $http.get("api/student").then(
            function (result) {
                var d = result.data;
                if (d.status) {
                    $scope.items = d.students;
                }
            }, function (err) {

            }
        );
    };

    $scope.add = function(){
        $mdDialog.show({
            controller: addCtrl,
            templateUrl: 'admin/addStu.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true
        }).then(
            function(obj) {
                $http.post("api/student", obj).then(
                    function (result) {
                        var d = result.data;
                        if(d.status) {
                            openAlert( "添加成功");
                            $scope.refresh();
                        }else
                            openAlert("添加失败");
                    }, function() {
                        openAlert("添加成功");
                    }
                )
            }, function() {});
    }

    function addCtrl($scope, $mdDialog) {

        $scope.username = "";
        $scope.name = "";
        $scope.department = [];
        $scope.major = [];
        $scope.class = [];
        $scope.classid;

        $scope.getMajor= function (did) {
            $http.get("api/department/" + did + "/majors").then(function (result) {
                var d = result.data;
                if(d.status)
                    $scope.major = d.majors;
            })
        };

        $scope.getClasses = function (mid) {
            $http.get("api/major/" + mid + "/classes").then(function (result) {
                var d = result.data;
                if(d.status)
                    $scope.class = d.classes;
            })
        };

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        $scope.ok = function () {
            $mdDialog.hide({
                year: new Date().getFullYear(),
                username: $scope.username,
                name: $scope.name,
                majorClassId: $scope.classid,
                password: $scope.username
            });
        }

        $http.get("api/department").then(function (result) {
            var d = result.data;
            if(d.status)
                $scope.department = d.departments;
        })
    }

    $scope.del = function(id) {
        openConfirm("确定删除？").then(function() {
            $http.delete('api/student/' + id).then(
                function (r) {
                    var d = r.data;
                    if(d.status) {
                        openAlert("删除成功");
                        $scope.refresh();
                    }
                });
        })

    }

    $scope.refresh();

}]);