'use strict';

/* App Module */

var ieTanksApp = angular.module('ieTanksApp', [
    'ieTanksVisualization',
    'ieTanksServices',
    'ngRoute',
    'ngResource',
    'satellizer'
]); // add other modules if dependant here

ieTanksApp.config(['$routeProvider',
    function ($routeProvider) {
        $routeProvider.
            when('/game', {
                templateUrl: 'html/game.html',
                controller: 'GameCtrl'
            }).
            when('/games', {
                templateUrl: 'html/history.html',
                controller: 'GameHistory'
            }).
            when('/login', {
                url: '/login',
                templateUrl: 'html/login.html',
                controller: 'AuthCtrl'
            }).
            otherwise({
                redirectTo: '/'
            });
    }
]);

ieTanksApp.config(
    function ($authProvider) {
        $authProvider.google({
            url: 'api/auth/google',
            clientId: '835037334788-4drs5n9ugjk4cflsp2pc6kod1apv8sk3.apps.googleusercontent.com'
        });
    }
);

ieTanksApp.controller('AuthCtrl', function ($scope, $auth) {
    $scope.isAuthenticated = function () {
        return $auth.isAuthenticated();
    };

    $scope.authenticate = function (provider) {
        $auth.authenticate(provider)
            .then(function () {
                alert('You have successfully logged in');
            })
            .catch(function (response) {
                alert('Login failure');
            });
    };

    $scope.logout = function () {
        if (!$auth.isAuthenticated()) {
            return;
        }
        $auth.logout()
            .then(function () {
                alert('You have been logged out');
            })
    };
});

ieTanksApp.run(function ($rootScope, $location) {
    $rootScope.isActive = function (viewLocation) {
        return $location.path().indexOf(viewLocation) === 0;
    };
});