var ieTanksServices = angular.module('ieTanksServices', []);

ieTanksServices.factory('REST', ['$resource',
    function ($resource) {
        return {
            games:  $resource('/api/game'),             // GET
            game:   $resource('/api/game/:gameId', {gameId:'@id'}),     // GET|POST
            bot:    $resource('/api/bot'),              // GET|POST
            leagues:$resource('/api/league'),           // GET|POST
            league: $resource('/api/league/:leagueId'), // GET
            maps:   $resource('/api/api/board'),        // GET
            auth:   $resource('/api/login')             // POST
            // add more calls to external components
        };
    }
]);
