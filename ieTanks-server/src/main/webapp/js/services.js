var ieTanksServices = angular.module('ieTanksServices', []);

ieTanksServices.factory('REST', ['$resource',
    function ($resource) {
        return {
            games:  $resource('/api/game'),         // GET
            game:   $resource('/api/game/:gameId'), // GET|POST
            bot:    $resource('/api/bot'),          // GET|POS                             T
            maps:   $resource('/api/board'),       // GET
            auth:   $resource('/api/login')         // POST
            // add more calls to external components
        };
    }
]);