var app = angular.module('home',[]);
app.controller('dashBoardController', function($scope) {
    
	$scope.releases = [{prodName: 'TAS-10.1', Name: 'Drop-1',Status:'Not Started',StartDate:'5-2-2016',EndDate:'15-2-2016',MnodeAvailable:'Yes',Comments:'None',FRS:'http://aa'}];
	
});
