
// 必修课控制器

appControllers.controller('compulsorymanageCtrl',['$scope','$mdDialog', '$http', function($scope, $mdDialog, $http){
    var openAlert = openAlertFactory($mdDialog);
    var openConfirm = openConfirmFactory($mdDialog);

    $scope.items = [];

    $scope.refresh = function() {
        $http.get("api/compulsory").then(
            function (result) {
                var d = result.data;
                if (d.status) {
                    $scope.items = d.courses;
                }
            }, function (err) {

            }
        );
    };

    $scope.add = function(){
        $mdDialog.show({
            controller: addCtrl,
            templateUrl: 'admin/addCompulsory.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true
        }).then(
            function(obj) {
                $http.post("api/compulsory", obj).then(
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

        $scope.name = "";
        $scope.department = [];
        $scope.major = [];
        $scope.class = [];
        $scope.teacher = [];
        $scope.plan = [];
        $scope.classId = "";
        $scope.teacherId = "";

        $scope.departmentChanged = function (did) {
            $http.get("api/department/" + did + "/majors").then(function (result) {
                var d = result.data;
                if(d.status)
                    $scope.major = d.majors;
            })

            $http.get("api/department/" + did + "/teachers").then(function (result) {
                var d = result.data;
                if(d.status)
                    $scope.teacher = d.teachers;
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
                year: getYear(),
                name: $scope.name,
                classId: $scope.classId,
                teacherId: $scope.teacherId
            });
        }

        $http.get("api/department").then(function (result) {
            var d = result.data;
            if(d.status)
                $scope.department = d.departments;
        })

        $http.get("api/compulsory/plans").then(function (result) {
            var d = result.data;
            if(d.status)
                $scope.plan = d.plans;
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