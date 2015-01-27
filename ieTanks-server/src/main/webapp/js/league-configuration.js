var ieTanksVisualization = angular.module('ieTanksVisualization');

ieTanksVisualization.controller('LeagueConfiguration', ['$scope', '$interval', 'REST',
    function ($scope, $interval, REST) {
        var intervalPeriodOptions = ['seconds', 'minutes', 'hours', 'days', 'weeks', 'months'],
            range0to23 = _.range(0, 1 + 23),
            range1to20 = _.range(1, 1 + 20),
            range0to59 = _.range(0, 1 + 59);

        $scope.numberOfGamesOptions = range1to20;
        $scope.intervalCountOptions = range0to59;
        $scope.intervalPeriodOptions = intervalPeriodOptions;
        $scope.boardOptions = []; //[{id: 'board1', max: 5}, {id: 'board2', max: 3}, {id: 'board3', max: 6}, {id: 'board4', max: 4}];

        $scope.selectedNumberOfGames = $scope.numberOfGamesOptions[0];
        $scope.selectedIntervalCount = $scope.intervalCountOptions[0];
        $scope.selectedIntervalPeriod = $scope.intervalPeriodOptions[0];
        $scope.selectedStartDateTime = moment().format();
        $scope.selectedBoard = null;
        $scope.numberOfPlayers = 0;
        $scope.selectedPlayers = [];

        $scope.$watch('selectedBoard', function () {
            var idToSearch = $scope.selectedBoard,
                selectedBoard = _.find($scope.boardOptions, function (boardOption) {
                    return boardOption.id === idToSearch;
                }),
                numberOfPlayers = selectedBoard ? selectedBoard.max : 0;
            $scope.selectedPlayers = Array(numberOfPlayers);
            $scope.numberOfSelectedPlayers = _.range(0, numberOfPlayers);
        });

        $scope.save = function () {
            var payload = getSaveLeagueConfigurationPayload();
            REST.leagues.query(payload, function () {
                    console.log('League configuration saved');
                },
                function (e) {
                    console.log('Unable to save league configuration: ' + e.statusText);
                });
        };

        function getSaveLeagueConfigurationPayload() {
            return {
                games_number: parseInt($scope.selectedNumberOfGames),   //int
                interval: {
                    value: parseInt($scope.selectedIntervalCount),      //int
                    unit: $scope.selectedIntervalPeriod                 //string
                },
                first_game_datetime: $scope.selectedStartDateTime,      //string
                board_id: $scope.selectedBoard,                         //string
                players: _.compact($scope.selectedPlayers)              //string[]
            };
        }

        function loadBoardOptions() {
            $scope.boardOptions = REST.maps.query(function () {
                console.log('List of boards loaded');
            }, function (e) {
                console.log('Unable to load list of boards: ' + e.statusText);
            });
        }

        loadBoardOptions();
    }
])
.directive('eonasdanDatetimePicker', function () {
          return {
              restrict: 'A',
              require: 'ngModel',
              link: function (scope, element, attributes, ctrl) {
                  $(element).datetimepicker({ locale: 'en-gb' });
                  $(element).on('dp.change', function (event) {
                      scope.$apply(function() {
                          ctrl.$setViewValue(event.date.format());
                      });
                  });
              }
          };
      });
