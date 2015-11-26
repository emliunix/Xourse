var appControllers=angular.module('appControllers',['ngMaterial','ngMessages']);

appControllers.provider('hubuIdProvider',function(){
	this.id='';
	var tar=this;
	this.$get=function(){
		return {
			getId:function(){
				return tar.id;
			},
			setId:function(str){
				tar.id=str;
			}
		};
	};
});

appControllers.controller('pageCtrl',['$scope','$mdSidenav','$mdDialog',function($scope,$mdSidenav,$mdDialog){
		$scope.username="liuhongsen";
		
		$scope.menu=[
			//学生部分
			{'href':'#/welcome','text':'首页'},
			{'href':'#/readnews','text':'查看新闻'},
			{'href':'#/chooseSubject','text':'选课与退课'},
			{'href':'#/querySubject','text':'查询课程信息'},
			{'href':'#/ext/queryHubuScore','text':'湖大考试成绩'},
			//教师部分
			//{'href':'#/teachSubject','text':'公选课程选择'},
			{'href':'#/teachSubject','text':'公选课程选择'},
			{'href':'#/selectYear','text':'成绩结算'}
		];

		$scope.openLeftMenu =function(){
			$mdSidenav('left').toggle();
		};
		$scope.close=function(){
			$mdSidenav('left').close();
		};
	
	//注销操作
	$scope.logout=function(ev){
		$.ajax({
			type:'get',
			url:'/StudentManage/sys/logout',
			success:function(result){
				var json=$.parseJSON(result);
				if(json.status)
					window.location.href="login.html";
				else{
					$mdDialog.show(
						$mdDialog.alert()
							.parent(angular.element(document.body))
							.clickOutsideToClose(true)
							.content('系统忙，请稍后再试！')
							.ariaLabel('Alert Dialog Demo')
							.ok('确定')
							.targetEvent(ev))
				}
			},
			error:function(result){
				$mdDialog.show(
					$mdDialog.alert()
						.parent(angular.element(document.body))
						.clickOutsideToClose(true)
						.content('系统忙，请稍后再试！')
						.ariaLabel('Alert Dialog Demo')
						.ok('确定')
						.targetEvent(ev))
			}			
		});
	}
		
}]);

