'use strict';

/* App Module */

var ieTanksApp = angular.module('ieTanksApp', [
    'ieTanksVisualization',
    'ieTanksServices',
    'ngRoute',
    'ngResource'
]); // add other modules if dependant here

ieTanksApp.config(['$routeProvider',
    function($routeProvider) {
        $routeProvider.
            when('/game/:gameId', {
                templateUrl: 'html/game.html',
                controller: 'GameCtrl'
            }).
            when('/history', {
                templateUrl: 'html/history.html',
                controller: 'GameHistory'
            }).
            otherwise({
                redirectTo: '/'
            });
    }]);

ieTanksApp.run(function($rootScope, $location) {
    $rootScope.newGameId = Math.floor(Math.random()*1000); // FIXME get ID from server instead?
    $rootScope.isActive = function (viewLocation) {
        return $location.path().indexOf(viewLocation) === 0;
    };
});