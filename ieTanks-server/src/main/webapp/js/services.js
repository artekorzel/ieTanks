var ieTanksServices = angular.module('ieTanksServices', []);

ieTanksServices.factory('REST', ['$resource',
    function ($resource) {
        return {
            stat:  $resource('/api/stats/:gameId'), // GET
            games:  $resource('/api/game'),             // GET
            game:   $resource('/api/game/:gameId'),     // GET|POST
            bot:    $resource('/api/bot'),              // GET|POST
            leagues:$resource('/api/league'),           // GET|POST
            league: $resource('/api/league/:leagueId'), // GET
            maps:   $resource('/api/board'),            // GET
            auth:   $resource('/api/login')             // POST
            // add more calls to external components
        };
    }
]);