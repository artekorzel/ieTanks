angular.module('botsForm', [])
    .controller('BotsController', ['$scope', '$http', 'REST', function($scope, $http, REST) {
        $scope.master = {};

        $scope.update = function(form) {
            REST.bot.save(form)

        };
    }]);
