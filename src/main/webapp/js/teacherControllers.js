/****************************教师**************************/
appControllers.controller('teacheSubCtrl',['$scope','$mdSidenav','$log',function($scope,$mdSidenav,$log){
	$scope.toppings = [
		{ name: 'Pepperoni', wanted: true },
		{ name: 'Sausage', wanted: false },
		{ name: 'Black Olives', wanted: true },
		{ name: 'Green Peppers', wanted: false },
		{ name: 'Pepperoni', wanted: true },
		{ name: 'Sausage', wanted: false },
		{ name: 'Black Olives', wanted: true },
		{ name: 'Green Peppers', wanted: false },
		{ name: 'Pepperoni', wanted: true },
		{ name: 'Sausage', wanted: false },
		{ name: 'Black Olives', wanted: true },
		{ name: 'Green Peppers', wanted: false }
	];
}]);

appControllers.controller('selectYearCtrl',['$scope','$location','$mdDialog',function($scope,$location,$mdDialog){
	/***实验数据 */
	$scope.cur_year=['2013-2-3','2013-4-12','2015-2-3','2013-5-3','2123-2-3'];
	
	
	/**实际开发用的ajax，用于获得select的所有年份 */
	// $.ajax({
	// 	type:'get',
	// 	url:'/StudentManage/api/teacher/courses/year',
	// 	success:function(result){
	// 		var json=$.parseJSON(result);
	// 		if(result.status)
	// 			$scope.cur_year=json.years;
	// 	},
	// 	error:function(result){

	// 	}
	// });
	
	/***选好年份，点确定按钮，跳转 */
	$scope.selectYear=function(ev){
		if($scope.selectedYear==''||$scope.selectedYear==undefined){
			$mdDialog.show(
				$mdDialog.alert()
					.parent(angular.element(document.body))
					.clickOutsideToClose(true)
					.content('请先选择一个年份！')
					.ariaLabel('Alert Dialog Demo')
					.ok('确定')
					.targetEvent(ev));
			return;
		}
			
		$location.path('/assessScore/'+$scope.selectedYear);
	}
}])

appControllers.controller('accessScoreCtrl',['$scope','$mdSidenav','$routeParams','$mdDialog','$location',function($scope,$mdSidenav,$routeParams,$mdDialog,$location){
	/**获取选定的年份 */
	$scope.year=$routeParams.year;
	
	/***实践数据****/
	$scope.subs=[
		{id:'1',year:'2013-3-4',name:'计算机任务程序艺术',type:'公共选修',department:'计信',major:'软件工程',teacher:'付文文',class:'软工1班',isSubmitted:false},
		{id:'1',year:'2014-4-4',name:'操作系统',type:'公共选修',department:'计信',major:'计科1',teacher:'蜂窝网',class:'软工2班',isSubmitted:false},
		{id:'1',year:'2015-3-4',name:'算法设计艺术',type:'公共基础',department:'计信',major:'软件工程',teacher:'范文芳',class:'软工3班',isSubmitted:true},
		{id:'1',year:'2015-6-4',name:'计算机网络',type:'专业必修',department:'计信',major:'信息安全',teacher:'格格热',class:'软工4班',isSubmitted:false},
		{id:'1',year:'2013-8-4',name:'Spring揭秘',type:'公共选修',department:'计信',major:'计科1',teacher:'付文文',class:'计科1班',isSubmitted:false},
		{id:'1',year:'2013-12-4',name:'Web程序设计',type:'公共选修',department:'计信',major:'软件工程',teacher:'付文文',class:'计科1班',isSubmitted:true},
		{id:'1',year:'2013-11-4',name:'J2EE技术解析',type:'专业必修',department:'计信',major:'计科2',teacher:'付文文',class:'计科1班',isSubmitted:false}
	];
	$scope.courses=$scope.subs;

	/*****用作真实发送数据,获得某一年份的所有课程 */
	// $.ajax({
	// 	type:'get',
	// 	url:'/StudentManage/api/teacher/courses/year/'+$scope.year,
	// 	success:function(result){
	// 		var json=$.parseJSON(result);
	// 		if(result.status){
	// 			$scope.courses=json.courses;
	// 		}
	// 	},
	// 	error:function(result){
	// 		$scope.courses=$scope.subs;
	// 	}
	// });
	
	

	/****显示平时、考试比例设置对话框，并跳转 */
	$scope.showRate=function(ev,index){
		// ng-href="#/calculateScore/软件工程导论/公共选修/软件工程"
		$mdDialog.show({
			controller: selectRateController,
			templateUrl: 'teacher/selectRate.html',
			parent: angular.element(document.body),
			targetEvent: ev,
			clickOutsideToClose:true
			})
			.then(function(pRate,jRate) {
				//alert(pScore+jScore);
				
				//TODO:将pRate,jRate传回后台存储
				
				var o=$scope.courses;
				$location.path('/calculateScore/'+o[index].name+'/'+o[index].type+'/'+o[index].class);
			}, function() {});
	}
	function selectRateController($scope, $mdDialog){   //selectRate Dialog 的控制器
		$scope.hide = function() {
			$mdDialog.hide();
		};
		$scope.cancel = function() {
			$mdDialog.cancel();
		};
		$scope.answer = function(error,pRate,jRate) {
			if(!error)
				$mdDialog.hide(pRate,jRate);
		};
	}
	
	
}]);


appControllers.controller('calScoreCtrl',['$scope','$routeParams','$mdSidenav',function($scope,$routeParams,$mdSidenav){
	
	//显示挂牌信息
	$scope.sub=$routeParams.sub;
	$scope.pro=$routeParams.pro;
	$scope.major=$routeParams.major;
	
	$scope.isVisible=false;
	$scope.students=[
		{id:'2013221104220014',name:'王五'},
		{id:'2013221104220015',name:'王五'},
		{id:'2013221104220016',name:'王五'},
		{id:'2013221104220017',name:'王五'},
		{id:'2013221104220018',name:'王五'},
		{id:'2013221104220019',name:'王五'},
		{id:'2013221104220020',name:'王五'},
		{id:'2013221104220021',name:'王五'},
		{id:'2013221104220022',name:'王五'},
		{id:'2013221104220023',name:'王五'},
		{id:'2013221104220024',name:'王五'},
		{id:'2013221104220025',name:'王五'},
		{id:'2013221104220036',name:'王五'},
		{id:'2013221104220037',name:'王五'}
	];
	
	
	/***********对平时、卷面成绩进行相关操作***********/
	$scope.p=$scope.students.map(function(tar,index){
		return '0';
	});
	
	$scope.j=$scope.p.slice(0);
	$scope.z=$scope.p.slice(0);

	
	$scope.isNumError=$scope.students.map(function(tar,index){
		return false;
	});
	$scope.isOverRange=$scope.isNumError.slice(0);
	
	$scope.calTotalScore=function(index){
		$scope.isNumError[index]=false;
		$scope.isOverRange[index]=false;
		if(($scope.p[index]!=''&&isNaN($scope.p[index]))||($scope.j[index]!=''&&isNaN($scope.j[index])))
		{
			$scope.isNumError[index]=true;
			$scope.z[index]='';
			return;
		}

		
		if(($scope.p[index]!=''&&(parseFloat($scope.p[index])>100||parseFloat($scope.p[index])<0))
			||($scope.j[index]!=''&&(parseFloat($scope.j[index])>100||parseFloat($scope.j[index])<0)))
		{
			$scope.isOverRange[index]=true;
			$scope.z[index]='';	
			return;
		}
		
		if($scope.p[index]!=''&&$scope.j[index]!='')
			$scope.z[index]=parseFloat($scope.p[index])*0.4+parseFloat($scope.j[index])*0.6;
		else if($scope.p[index]!='')
			$scope.z[index]=parseFloat($scope.p[index])*0.4;
		else if($scope.j[index]!='')
			$scope.z[index]=parseFloat($scope.j[index])*0.6;
		else 
			$scope.z[index]='';
			
	}
}]);

