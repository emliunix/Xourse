
// 院系控制器

appControllers.controller('departmentmanageCtrl',['$scope','$mdDialog', '$http', function($scope, $mdDialog, $http){
    var openAlert = openAlertFactory($mdDialog);
    var openConfirm = openConfirmFactory($mdDialog);

    $scope.items = [];

    $scope.refresh = function() {
        $http.get("api/department").then(
            function (result) {
                var d = result.data;
                if (d.status) {
                    $scope.items = d.departments;
                }
            }, function (err) {

            }
        );
    };

    $scope.add = function(){
        $mdDialog.show({
            controller: addCtrl,
            templateUrl: 'admin/addDepartment.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true
        }).then(
            function(obj) {
                $http.post("api/department", obj).then(
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

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        $scope.ok = function () {
            $mdDialog.hide({
                name: $scope.name,
            });
        }
    }

    $scope.del = function(id) {
        openConfirm("确定删除？").then(function() {
            $http.delete('api/department/' + id).then(
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