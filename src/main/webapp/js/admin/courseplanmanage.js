
// 学生控制器

appControllers.controller('courseplanmanageCtrl',['$scope','$mdDialog', '$http', function($scope, $mdDialog, $http){
    var openAlert = openAlertFactory($mdDialog);
    var openConfirm = openConfirmFactory($mdDialog);

    $scope.items = [];

    $scope.cvtr = {
        "ELECTIVE": "选修",
        "COMPULSORY": "必修"
    }

    $scope.refresh = function() {
        $http.get("api/course_plan").then(
            function (result) {
                var d = result.data;
                if (d.status) {
                    $scope.items = d.plans;
                }
            }, function (err) {

            }
        );
    };

    $scope.add = function(){
        $mdDialog.show({
            controller: addCtrl,
            templateUrl: 'admin/addCourseplan.html',
            parent: angular.element(document.body),
            //targetEvent: ev,
            clickOutsideToClose:true
        }).then(
            function(obj) {
                $http.post("api/course_plan", obj).then(
                    function (result) {
                        var d = result.data;
                        if(d.status) {
                            openAlert( "添加成功");
                            $scope.refresh();
                        }else
                            openAlert("添加失败");
                    }, function() {
                        openAlert("添加失败");
                    }
                )
            }, function() {});
    }

    function addCtrl($scope, $mdDialog) {

        $scope.type = "";
        $scope.name = "";

        $scope.cancel = function () {
            $mdDialog.cancel();
        };

        $scope.ok = function () {
            $mdDialog.hide({
                name: $scope.name,
                type: $scope.type
            });
        }

    }

    $scope.del = function(id) {
        openConfirm("确定删除？").then(function() {
            $http.delete('api/course_plan/' + id).then(
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