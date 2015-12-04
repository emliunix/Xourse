/***************************管理员控制器**************************/

//管理新闻
appControllers.controller('newsmanageCtrl',['$scope','$mdDialog',function($scope,$mdDialog){
 var imagePath = 'front_frame/angular-material/img/list/60.jpeg';
 
 /******测试数据********/
 $scope.news = [
      {
        face : imagePath,
        title: '一拳超人的简介',
        year: '2014-5-9',
        when: '3:08PM',
        content: " 《一拳超人[1]  》（日语：ワンパンマン），又有译作《一击男》，是日本网络漫画家ONE的幽默格斗漫画，该作品以一个漫原稿软件ComicStudio PC 制作，于2009年7月3日在ONE的个人网站开始连载,虽然画功粗糙，但却于日本网络大热，根据日本NHK2012年9月2日播出的“网络漫画革命”调查，		《一拳超人》于该年观看总数超过1000万次，平均每天有高达20,000点击。现在持续于ONE的网站连载中"
      },
      {
        face : imagePath,
        title: 'Brunch this weekend?',
        year: '2014-5-9',
        when: '3:08PM',
        content: "《一拳超人[1]  》（日语：ワンパンマン），又有译作《一击男》，是日本网络漫画家ONE的幽默格斗漫画，该作品以一个漫原稿软件ComicStudio PC 制作，于2009年7月3日在ONE的个人网站开始连载,虽然画功粗糙，但却于日本网络大热，根据日本NHK2012年9月2日播出的“网络漫画革命”调查"
      },
      {
        face : imagePath,
        title: 'Brunch this weekend?',
        year: '2014-5-9',
        when: '3:08PM',
        content: "《一拳超人[1]  》（日语：ワンパンマン），又有译作《一击男》，是日本网络漫画家ONE的幽默格斗漫画，该作品以一个漫原稿软件ComicStudio PC 制作，于2009年7月3日在ONE的个人网站开始连载,虽然画功粗糙，但却于日本网络大热，根据日本NHK2012年9月2日播出的“网络漫画革命”调查，		《一拳超人》于该年观看总数超过1000万次，平均每天有高达20,000点击。现在持续于ONE的网站连载中"
      },
      {
        face : imagePath,
        title: 'Brunch this weekend?',
        year: '2014-5-9',
        when: '3:08PM',
        content: "《一拳超人[1]  》（日语：ワンパンマン），又有译作《一击男》，是日本网络漫画家ONE的幽默格斗漫画，该作品以一个漫原稿软件ComicStudio PC 制作，于2009年7月3日在ONE的个人网站开始连载,虽然画功粗糙，但却于日本网络大热，根据日本NHK2012年9月2日播出的“网络漫画革命”调查，		《一拳超人》于该年观看总数超过1000万次，平均每天有高达20,000点击。现在持续于ONE的网站连载中"
      },
      {
        face : imagePath,
        title: 'Brunch this weekend?',
        year: 'Min Li Chan',
        when: '3:08PM',
        content: "《一拳超人[1]  》（日语：ワンパンマン），又有译作《一击男》，是日本网络漫画家ONE的幽默格斗漫画，该作品以一"
      },
    ];

    /************获取新闻***************/
    //json格式：{status,news:[{id,title,year,content}...]}
    // $.ajax({
    //     type:'get',
    //     url:'',     /*****获取新闻的url*******/
    //     success:function(result){
    //         var j=$.parseJSON(result);
    //         if(j.status){
    //             $scope.news=j.news.map(function(tar,index){
    //                tar.face='front_frame/angular-material/img/list/60.jpeg';
    //                return tar;
    //             });
    //         }
    //     }
    // });
    
    
    /*****添加新闻******/
    $scope.add_editnews=function(ev,index){
        var news_o=$scope.news;
		$mdDialog.show({
			controller: add_editnewsController,
			templateUrl: 'admin/add_editnew.html',
			parent: angular.element(document.body),
			openFrom:{top: 200,width: 50,height: 100 }
			})
			.then(function(news) {
				/****post数据*****/
				$.ajax({
					type:'post',
					url:'fw.jsp',/*************添加新闻的URL****/
					data:{title:news.title,date:news.date,content:news.content},  //格式：{title,year,content}
					success:function(result){
						if($.parseJSON(result).status){
							 openAlert(ev,'添加新闻成功！');
						}
					},
					error:function(r){
						openAlert(ev,'系统忙，请稍候再试！');
					}
				});
			}, function() {});
			
		/******对话框控制器******/	
		function add_editnewsController($scope, $mdDialog){
            if(index==-1){
                $scope.myDate=new Date();
                $scope.title='';
                $scope.content='';
            }else{
                $scope.myDate=new Date(news_o[index].year);
                $scope.title=news_o[index].title;
                $scope.content=news_o[index].content;
            }
            
			$scope.cancel = function(ev) {
				ev.preventDefault();
				$mdDialog.cancel();
			};
			$scope.ok = function(ev) {
                ev.preventDefault();
                if($scope.myDate==undefined){
                    alert('请选择一个日期');
                    return;
                }
                if($scope.title==''||$scope.content==''){
                    alert('输入区域不能为空!');
                    return;
                }
                var news={};
                news.title=$scope.title;
                var year=$scope.myDate.getFullYear();
                var month=$scope.myDate.getMonth()+1;
                var day=$scope.myDate.getDate();
                var date=year+'-'+month+'-'+day;
                news.date=date;
                news.content=$scope.content;
                
                $mdDialog.hide(news);
			};
		}
    }
    
    
    // /***********编辑新闻*********/
    // $scope.editnews=function(ev,index){

    // }
	
    $scope.deletenews=function(ev,index){
		var confirm = $mdDialog.confirm()
                .title('删除操作')
                .content('<div style="color:red;padding-top:15px;">您确定要删除这条新闻吗？</div>')
                .ok('确定')
                .cancel('取消');
                
			 $mdDialog.show(confirm).then(function(){
				/****删除新闻****/
				$.ajax({
					type:'post',
					url:'e.jsp', /****删除新闻url*****/
					data:{id:index},    //post删除新闻的id
					success:function(result){
						var j=$.parseJSON(result);
						if(j.status){
							openAlert(ev,'删除成功');
							$scope.news.splice(index,1);
						}
					},
					error:function(result){
						openAlert(ev,'系统忙，请稍后再试!');
					}
				});
                
            });
            
    }
    
    function openAlert(ev,msg){
        $mdDialog.show(
            $mdDialog.alert()
                .parent(angular.element(document.body))
                .clickOutsideToClose(true)
                .content(msg)
                .ariaLabel('Alert Dialog Demo')
                .ok('确定'));
    }
}]);
	