/**
 * Xourse App
 *
 * Created by Liu Yuhui on 2015/11/7.
 */

var XourseApp = angular.module("XourseApp", ["ngMaterial"]);

/**
 * Main Controller
 */
XourseApp.controller("MainController", ["$scope", function($scope) {
    $scope.students = [
        {username: "Liu Yuhui"},
        {username: "Ke Bo"},
        {username: "Liu Hongsen"}
    ]
}]);