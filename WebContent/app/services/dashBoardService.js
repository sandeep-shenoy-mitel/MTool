angular.module('home').factory('dashBoardService',function($http){
	var factory = {}; 
	
	//retrieve list of releases
	factory.getRelease = function() {
			
			var releaseInfo =  $http.get("http://10.3.3.44:8080/mTool/rest/dashboardService/release/list")
			    .then(function(response) {
			    	console.log("In Service");
			    	releaseInfo = response.data;
			    	return response.data;
			    	
			    });
			 console.log(releaseInfo);
			 return releaseInfo;
		}
	
			

	//add a release
	factory.addRelease= function(release) {
		
		
		var data =  JSON.stringify({
            	oidIndex: release.oidIndex,
            	productName: release.productName,
            	name: release.name,
            	status: release.status,
            	owner: release.owner,
            	startDate: release.startDate,
            	endDate: release.endDate,
            	mnodeAvailable: release.mnodeAvailable,
            	comments: release.comments
            });
        
		var decodeData = decodeURI(data); 
		$http.post("http://10.3.3.44:8080/mTool/rest/dashboardService/release/add",decodeData).success(function(response){
			console.log(response);
			return response;
		});
		return data;
	}
	
	//update release
	factory.updateRelease= function(release) {
		
		
			var data =  JSON.stringify({
	            	oidIndex: release.oidIndex,
	            	productName: release.productName,
	            	name: release.name,
	            	status: release.status,
	            	owner: release.owner,
	            	startDate: release.startDate,
	            	endDate: release.endDate,
	            	mnodeAvailable: release.mnodeAvailable,
	            	comments: release.comments
	            });
	        
			var decodeData = decodeURI(data); 
			$http.post("http://10.3.3.44:8080/mTool/rest/dashboardService/release/update",decodeData).success(function(response){
				console.log(response);
				return response;
			});
			return data;
	}
	
	//delete release
	factory.deleteRelease= function(release) {
		var data =  JSON.stringify({
        	oidIndex: release.oidIndex,
        	productName: release.productName,
        	name: release.name,
        	status: release.status,
        	owner: release.owner,
        	startDate: release.startDate,
        	endDate: release.endDate,
        	mnodeAvailable: release.mnodeAvailable,
        	comments: release.comments
        });
    
		var decodeData = decodeURI(data); 
		$http.post("http://10.3.3.44:8080/mTool/rest/dashboardService/release/delete",decodeData).success(function(response){
			console.log(response);
			return response;
		});
		return data;
	}
		
	
	factory.getDocList = function(product,release) {
		

		var releaseInfo =  $http.get("http://10.3.3.44:8080/mTool/rest/dashboardService/documents/list/"+product+","+release)
	    .then(function(response) {
	    	console.log("In Service");
	    	releaseInfo = response.data;
	    	return response.data;
	    	
	    });
	 console.log(releaseInfo);
	 return releaseInfo;
	}
	
	
	return factory;
	
});
