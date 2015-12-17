
// 班级控制器

appControllers.controller('classmanageCtrl',['$scope','$mdDialog', '$http', function($scope, $mdDialog, $http){
    var openAlert = openAlertFactory($mdDialog);
    var openConfirm = openConfirmFactory($mdDialog);

    $scope.items = [];

    $scope.refresh = function() {
        $http.get("api/major_class").then(
            function (result) {
                var d = result.data;
                if (d.status) {
                    $scope.items = d.classes;
                }
            }, function (err) {

            }
        );
    };

    $scope.add = function(){
        $mdDialog.show({
            controller: addCtrl,
            templateUrl: 'admin/addClass.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true
        }).then(
            function(obj) {
                $http.post("api/major_class", obj).then(
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
        $scope.majorId = "";

        $scope.getMajor= function (did) {
            $http.get("api/department/" + did + "/majors").then(function (result) {
                var d = result.data;
                if(d.status)
                    $scope.major = d.majors;
            })
        };

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        $scope.ok = function () {
            $mdDialog.hide({
                name: $scope.name,
                majorId: $scope.majorId,
                year: new Date().getFullYear()
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
            $http.delete('api/major_class/' + id).then(
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