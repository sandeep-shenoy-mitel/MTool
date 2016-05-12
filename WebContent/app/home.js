//module for landing page 
var homeModule = angular.module('home');
//Controller to show dashboard page
homeModule.controller('dashBoardController', function($scope, dashBoardService, ModalService,$route) {
	
	  dashBoardService.getRelease().then(function(data) {
		  $scope.releases = data;
	 });
	
	  //broadcast method to refresh the list 
	  $scope.$on("updateList",function(){
		   dashBoardService.getRelease().then(function(data) {
				  $scope.releases = data;
				 
				  $route.reload();
				  
			 });
		});
	  	//static data for testing
		/*$scope.releases = [{prodName: 'TAS-10.1', Name: 'Drop-1',Status:'Not Started',Owner:'Sandeep',StartDate:'5-2-2016',EndDate:'15-2-2016',MnodeAvailable:'Yes',Comments:'None'},
		                   {prodName: 'RMS-9.8', Name: 'CR-68',Status:'Not Started',Owner:'Sandeep',StartDate:'25-4-2016',EndDate:'15-6-2016',MnodeAvailable:'No',Comments:'None'}
		];*/
		
	  //open modal to add a release
	  $scope.show = function() {
	    	var modalInstance =   ModalService.showModal({
	            templateUrl: 'addRelease.html',
	            controller: "ModalController"
	           
	        });
	    	modalInstance.then(function(modal) {
	            modal.element.modal();
	            modal.close.then(function(result) {
	            	if(result!='Close') {
			            var resp = dashBoardService.addRelease(result);
			            console.log("IN CONTOLLER ADD"+result);
			            $scope.$broadcast('updateList');
	            	}
	            	$('.modal-backdrop').remove();
	            });
	          
	        });
	  };
	 
	  //open modal to edit release
	  $scope.edit = function(release) {
		  
	    	
	    	var editmodalInstance =   ModalService.showModal({
	            templateUrl: 'editRelease.html',
	            controller: 'EditModalController',
	            inputs: { releaseItem: release }
	        });
	    	
	    	editmodalInstance.then(function(modal) {
	            modal.element.modal();
	            modal.close.then(function(result) {
	            	
	            		var resp = dashBoardService.updateRelease(result);
	            		console.log("IN CONTOLLER UPDATE"+result);
	            		$scope.$broadcast('updateList');
	            	
	            	
	 	         });
	          });
	          
	     
	  };
	  
	  //delete release
	  $scope.deleteRelease = function(release) {
		  dashBoardService.deleteRelease(release);
		  $scope.$broadcast('updateList');
	  };
		
});//dashBoardController  ends
 
 
 //controller to handle modal for adding a release
 homeModule.controller('ModalController', function($scope, close) {
	 
	 $scope.close = function(result) {
	 	close(result); // close, but give 500ms for bootstrap to animate
	 };
	 
	//handle add release of modal
	 $scope.addRelease = function() {
		 close($scope.release,500);
	 };    
	    
	    
	    $scope.today = function() {
	        $scope.dt = new Date();
	      };
	      $scope.today();

	      $scope.clear = function() {
	        $scope.dt = null;
	      };

	      $scope.inlineOptions = {
	        customClass: getDayClass,
	        minDate: new Date(),
	        showWeeks: true
	      };

	      $scope.dateOptions = {
	        dateDisabled: disabled,
	        formatYear: 'yy',
	        maxDate: new Date(2020, 5, 22),
	        minDate: new Date(),
	        startingDay: 1
	      };

	      // Disable weekend selection
	      function disabled(data) {
	        var date = data.date,
	          mode = data.mode;
	        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	      }

	      $scope.toggleMin = function() {
	        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
	        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
	      };

	      $scope.toggleMin();

	      $scope.open1 = function() {
	        $scope.popup1.opened = true;
	      };
	      

	      $scope.open2 = function() {
	        $scope.popup2.opened = true;
	      };

	      $scope.setDate = function(year, month, day) {
	        $scope.dt = new Date(year, month, day);
	      };

	      $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	      $scope.format = $scope.formats[0];
	      $scope.altInputFormats = ['M!/d!/yyyy'];

	      $scope.popup1 = {
	        opened: false
	      };

	      $scope.popup2 = {
	        opened: false
	      };

	      var tomorrow = new Date();
	      tomorrow.setDate(tomorrow.getDate() + 1);
	      var afterTomorrow = new Date();
	      afterTomorrow.setDate(tomorrow.getDate() + 1);
	      $scope.events = [
	        {
	          date: tomorrow,
	          status: 'full'
	        },
	        {
	          date: afterTomorrow,
	          status: 'partially'
	        }
	      ];

	      function getDayClass(data) {
	        var date = data.date,
	          mode = data.mode;
	        if (mode === 'day') {
	          var dayToCheck = new Date(date).setHours(0,0,0,0);

	          for (var i = 0; i < $scope.events.length; i++) {
	            var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

	            if (dayToCheck === currentDay) {
	              return $scope.events[i].status;
	            }
	          }
	        }

	        return '';
	      }
	    
	    
	    
	    
	});
 
 
 //controller to handle modal for updating a release
 homeModule.controller('EditModalController', function($scope,close,releaseItem) {
	 
	    $scope.release = releaseItem;
	    
	    $scope.editRelease = function() {
			 close($scope.release,500);
		 };   
	    
	    $scope.today = function() {
	        $scope.dt = new Date();
	      };
	      $scope.today();

	      $scope.clear = function() {
	        $scope.dt = null;
	      };

	      $scope.inlineOptions = {
	        customClass: getDayClass,
	        minDate: new Date(),
	        showWeeks: true
	      };

	      $scope.dateOptions = {
	        dateDisabled: disabled,
	        formatYear: 'yy',
	        maxDate: new Date(2020, 5, 22),
	        minDate: new Date(),
	        startingDay: 1
	      };

	      // Disable weekend selection
	      function disabled(data) {
	        var date = data.date,
	          mode = data.mode;
	        return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
	      }

	      $scope.toggleMin = function() {
	        $scope.inlineOptions.minDate = $scope.inlineOptions.minDate ? null : new Date();
	        $scope.dateOptions.minDate = $scope.inlineOptions.minDate;
	      };

	      $scope.toggleMin();

	      $scope.open1 = function() {
	        $scope.popup1.opened = true;
	      };
	      

	      $scope.open2 = function() {
	        $scope.popup2.opened = true;
	      };

	      $scope.setDate = function(year, month, day) {
	        $scope.dt = new Date(year, month, day);
	      };

	      $scope.formats = ['dd-MMMM-yyyy', 'yyyy/MM/dd', 'dd.MM.yyyy', 'shortDate'];
	      $scope.format = $scope.formats[0];
	      $scope.altInputFormats = ['M!/d!/yyyy'];

	      $scope.popup1 = {
	        opened: false
	      };

	      $scope.popup2 = {
	        opened: false
	      };

	      var tomorrow = new Date();
	      tomorrow.setDate(tomorrow.getDate() + 1);
	      var afterTomorrow = new Date();
	      afterTomorrow.setDate(tomorrow.getDate() + 1);
	      $scope.events = [
	        {
	          date: tomorrow,
	          status: 'full'
	        },
	        {
	          date: afterTomorrow,
	          status: 'partially'
	        }
	      ];

	      function getDayClass(data) {
	        var date = data.date,
	          mode = data.mode;
	        if (mode === 'day') {
	          var dayToCheck = new Date(date).setHours(0,0,0,0);

	          for (var i = 0; i < $scope.events.length; i++) {
	            var currentDay = new Date($scope.events[i].date).setHours(0,0,0,0);

	            if (dayToCheck === currentDay) {
	              return $scope.events[i].status;
	            }
	          }
	        }

	        return '';
	      }
	    
	    
	    
	    
	});
 
 
 
 // configure routes here
 homeModule.config(function($routeProvider) {
        $routeProvider

            // route for the dashboard page
            .when('/dashboard', {
                templateUrl : '../views/dashboard.html',
                controller  : 'dashBoardController'
            })

        
    });