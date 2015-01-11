angular.module('botsForm', [])
    .controller('BotsController', ['$scope', '$http', function($scope, $http) {
        $scope.master = {};

        $scope.update = function(form) {

            var req = {
                method: 'POST',
                url: 'http://localhost:8888/rest/bots',
                headers: {
                    'Content-Type': 'application/json'
                },
                data: angular.copy(form)
            };

            $http(req).success(function(data, status, headers, config) {
                $scope.form = 'success';
            }).
                error(function(data, status, headers, config) {
                    $scope.form = 'fail';
                });

        };
    }]);