
// 教师控制器

appControllers.controller('teamanageCtrl',['$scope','$mdDialog', '$http', function($scope, $mdDialog, $http){
    var openAlert = openAlertFactory($mdDialog);
    var openConfirm = openConfirmFactory($mdDialog);

    $scope.items = [];

    $scope.refresh = function() {
        $http.get("api/teacher").then(
            function (result) {
                var d = result.data;
                if (d.status) {
                    $scope.items = d.teachers;
                }
            }, function (err) {

            }
        );
    };

    $scope.add = function(){
        $mdDialog.show({
            controller: addCtrl,
            templateUrl: 'admin/addTea.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true
        }).then(
            function(obj) {
                $http.post("api/teacher", obj).then(
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
        $scope.departmentId;

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        $scope.ok = function () {
            $mdDialog.hide({
                year: new Date().getFullYear(),
                username: $scope.username,
                name: $scope.name,
                departmentId: $scope.departmentId,
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
            $http.delete('api/teacher/' + id).then(
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