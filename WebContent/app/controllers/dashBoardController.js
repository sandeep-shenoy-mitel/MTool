/**
 *  //Controller to show dashboard page
 */
angular.module('home')
.controller('dashBoardController', function(ngDialog,$scope, dashBoardService, ModalService,$route,moment,$http, $window,Upload) {

	

	var vm = this;
  dashBoardService.getRelease().then(function(data) {
	  $scope.releases = data;
	  vm.releaseItems = data;
	  
	  $scope.calendarView = "month";
	  $scope.todayDate = new Date();
	  $scope.calendarTitle = "Release Plan";
	  
	  var releaseEvents=[] ;
	  var numberOfReleases = vm.releaseItems.length;
	 console.log("Number of releases"+numberOfReleases);
	  for(cnt=0;cnt<numberOfReleases;cnt++) {
		  var releaseItem = vm.releaseItems[cnt];
		  console.log(releaseItem.endDate);
		 // var titleEvent = "("+releaseItem.owner+")" + releaseItem.name;
		  var releaseEvent = {
                  title: releaseItem.name, // The title of the event
                  type: 'important', // The type of the event (determines its color). Can be important, warning, info, inverse, success or special
                  startsAt:  new Date(releaseItem.endDate), // A javascript date object for when the event starts
                  //endsAt: new Date(2014,8,26,15), // Optional - a javascript date object for when the event ends
                  editable: false, // If edit-event-html is set and this field is explicitly set to false then dont make it editable.
                  deletable: false, // If delete-event-html is set and this field is explicitly set to false then dont make it deleteable
                  draggable: true, //Allow an event to be dragged and dropped
                  resizable: true, //Allow an event to be resizable
                  incrementsBadgeTotal: true, //If set to false then will not count towards the badge total amount on the month and year view
                  //recursOn: 'year', // If set the event will recur on the given period. Valid values are year or month
                  cssClass: 'a-css-class-name', //A CSS class (or more, just separate with spaces) that will be added to the event when it is displayed on each view. Useful for marking an event as selected / active etc
                  allDay: true // set to true to display the event as an all day event on the day view
                }
		  releaseEvents[cnt] = releaseEvent;
	  }
	  
	  $scope.events = releaseEvents;
	  
	  
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
  
  
  $scope.clickToUpload = function(productName,releaseName) {
	 	$scope.product = 	productName;
	 	$scope.release = releaseName;
	 	
	  ngDialog.open({
      template: 'upLoadId',
      className: 'ngdialog-theme-default',
      scope : $scope 
    });

        
        
    }; 
    
    
    $scope.clickToOpen = function(productName,releaseName) {
   	 
    	$scope.docs = "";
   	 
    	dashBoardService.getDocList(productName,releaseName).then(function(data) {
   		 $scope.docs = data;
   	 });
    			        
    	        	 ngDialog.open({
    	                    template: 'firstDialogId',
    	                    className: 'ngdialog-theme-default',
    	                    scope : $scope
    	                  });
    	        	 
    	        	
    			        
    			        
    };
    
    $scope.download = function(product,release,file) {
        
    	$window.location.href = "http://10.3.3.44:8080/mTool/download?productName="+product+"&releaseName="+release+"&fileName="+file;
    	
    };
    
    
    $scope.submit = function(file,product,release) {

    	var fd = new FormData();
    	fd.append('file', file);
    	
    	file.upload = Upload.http({
    	      url: 'http://10.3.3.44:8080/mTool/upload?productName='+product+'&releaseName='+release,
    	      method: 'POST',
    	      transformRequest: angular.identity,
    	      headers: {
    	        'Content-Type': undefined
    	      },
    	      data: fd
    	     
    	    });
    
    	
    	 file.upload.then(function (response) {
	            file.result = response.data;
	          }, function (response) {
	            if (response.status > 0)
	              $scope.errorMsg = response.status + ': ' + response.data;
	          });
	        
	        file.upload.progress(function (evt) {
	            file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
	          });
    };
  
	

	


});