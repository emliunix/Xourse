/**
 * Created by Liu Yuhui on 2015/12/18.
 */

function openAlertFactory($mdDialog) {
    return function openAlert(msg){
        return $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .clickOutsideToClose(true)
                .content(msg)
                .ariaLabel('Alert Dialog Demo')
                .ok('确定'));
    }
}

function openConfirmFactory($mdDialog) {
    return function openAlert(msg){
        return $mdDialog.show(
            $mdDialog.confirm()
                .parent(angular.element(document.body))
                .clickOutsideToClose(true)
                .content(msg)
                .ariaLabel('Alert Dialog Demo')
                .ok('确定')
                .cancel('取消')
        );
    }
}
