var ieTanksServices = angular.module('ieTanksServices', []);

ieTanksServices.factory('REST', ['$resource',
    function ($resource) {
        return {
            events: $resource('/api/events/:gameId.:format', { format: 'json' }),
            activeGames: $resource('/api/games.:format', { format: 'json' }),
            finishedGames: $resource('/api/history.:format', { format: 'json' })
            // add more calls to external components
        };
    }
]);