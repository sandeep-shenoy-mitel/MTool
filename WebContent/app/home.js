//module for landing page 
var homeModule = angular.module('home', ['ngDialog','ngRoute','angularModalService','ngAnimate', 'ui.bootstrap','mwl.calendar','ngFileUpload']);

 
// configure routes here
 homeModule.config(function($routeProvider) {
        $routeProvider

            // route for the dashboard page
            .when('/dashboard', {
                templateUrl : '../views/dashboard.html',
                controller  : 'dashBoardController'
            })

        
  });