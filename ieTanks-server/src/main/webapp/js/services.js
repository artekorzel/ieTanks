var ieTanksServices = angular.module('ieTanksServices', []);

ieTanksServices.factory('REST', ['$resource',
    function ($resource) {
        return {
            game:   $resource('/api/game/:gameId'), // GET|POST
            bot:    $resource('/api/bot'),          // GET|POST
            maps:   $resource('/api/board'),       // GET
            auth:   $resource('/api/login')         // POST
            // add more calls to external components
        };
    }
]);