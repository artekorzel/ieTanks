var ieTanksVisualization = angular.module('ieTanksVisualization', []);

ieTanksVisualization.controller('GameCtrl', ['$scope', '$interval', '$routeParams', 'REST',
    function ($scope, $interval, $routeParams, REST) {
        $scope.map = {border: 20, obstacles:[{type:'', x:5, y:10}]};

        /* TODO uncomment when REST interface is ready
        var step = 0;
        $interval(function () {
            var events = REST.events.query({ gameId: $routeParams.gameId }, function() {
                if (step < events.length) {
                    $scope.state = events[step];
                    ++step;
                } else {
                    console.log('No more events to display.');
                }
            }, function () {
                console.log('Failed to load game events.');
            });
        }, 3000);
        */
        var states = [{players:[{id:'blabla', action:'move', x:'10', y:'5'}, {id:'blabla2', action:'move', x:'3', y:'2'}], missiles:[]},
            {players:[{id:'blabla', action:'move', x:'6', y:'2'}, {id:'blabla2', action:'move', x:'11', y:'2'}], missiles:[]},
            {players:[{id:'blabla', action:'move', x:'15', y:'19'}, {id:'blabla2', action:'move', x:'5', y:'5'}], missiles:[]},
            {players:[{id:'blabla', action:'move', x:'0', y:'16'}, {id:'blabla2', action:'move', x:'7', y:'0'}], missiles:[]},
            {players:[{id:'blabla', action:'move', x:'0', y:'14'}, {id:'blabla2', action:'shoot', x:'7', y:'0'}], missiles:[{player_id:'blabla2', id:'1',x:'5',y:'4'}]},
            {players:[{id:'blabla', action:'move', x:'5', y:'12'}, {id:'blabla2', action:'move', x:'0', y:'10'}], missiles:[{player_id:'blabla2', id:'1',x:'5',y:'12'}]},
            {players:[{id:'blabla2', action:'move', x:'7', y:'12'}], missiles:[]},
            {players:[{id:'blabla2', action:'move', x:'0', y:'0'}], missiles:[]}];
        var ind = 0;
        $interval(function () {
            //var ind = Math.floor(Math.random()*states.length);
            $scope.state = states[ind];
            ind = (1 + ind) % states.length;
        }, 3000);

    }
])
    .controller('GameHistory', ['$scope', '$interval', 'REST',
        function ($scope, $interval, REST) {
            var gameHistory = REST.finishedGames.query(function () {
                $scope.gameHistory = gameHistory;
            }, function () {
                console.log('Failed to retrieve finished games list.');
            });
        }
    ])
    .directive('tankGame', function () {
        return {
            scope: {
                map: '=',
                state: '='
            },
            link: function ($scope) {
                var gameBorder = 400;
                var tileSize = 32;
                var game, scale, scaledGrid, players, missiles, isInit;
                scale=(gameBorder/$scope.gridBorder)/tileSize;
                scaledGrid = tileSize * scale;
                isInit = true;

                var Player = function(id, tank, turret, direction) {
                    this.id = id;
                    this.element = tank;
                    this.turret = turret;
                    this.direction = direction;
                };

                var Missile = function(id, bullet, direction) {
                    this.id = id;
                    this.element = bullet;
                    this.direction = direction;
                };

                var createPlayer = function(id, x, y) {
                    var tank = game.add.sprite(x * scaledGrid, y * scaledGrid, 'tank');
                    var turret = game.add.sprite(0, 0, 'turret');
                    tank.addChild(turret);
                    tank.scale.x = scale;
                    tank.scale.y = scale;
                    var color = Phaser.Color.getRandomColor();
                    tank.tint = color;
                    turret.tint = color;
                    return new Player(id, tank, turret, 0);
                };

                var createMissile = function(id, x, y) {
                    var bullet = game.add.sprite(x * scaledGrid, y * scaledGrid, 'bullet');
                    bullet.scale.x = scale;
                    bullet.scale.y = scale;
                    bullet.tint = Phaser.Color.getRandomColor();
                    return new Missile(id, bullet, 0);
                };

                Missile.prototype.moveTo = Player.prototype.moveTo = function(x, y){
                    game.add.tween(this.element).to({
                        x: Math.floor(tileSize*scale)*x,
                        y: Math.floor(tileSize*scale)*y
                    }, 500, Phaser.Easing.Quadratic.InOut, true);
                };

                var removeOldItems = function(set, subset) {
                    var identifiers = subset.map(function(state) {
                        return state.id;
                    });
                    for (var item in set) {
                        if (set.hasOwnProperty(item) && identifiers.indexOf(item) === -1) {
                            set[item].element.destroy();
                            delete set[item];
                        }
                    }
                    return set;
                };

                game = new Phaser.Game(gameBorder, gameBorder, Phaser.CANVAS, 'game-window', {}, true);

                game.state.add('animation', {
                    preload: preload,
                    create: create });

                function preload() {
                    //load image which will be used as ground texture
                    game.load.image('ground', 'assets/ice_land.png');
                    game.load.image('bullet', 'assets/bulletWhite.png');
                    game.load.image('tank', 'assets/tankBaseWhite.png');
                    game.load.image('turret', 'assets/tankTurretWhite.png');
                    game.load.image('wall', 'assets/tile_backwall.png');
                }

                function create() {
                    //Adds background to map (x_pos, y_pos, x_size, y_size) in pixels
                    game.add.tileSprite(0, 0, game.width, game.height, 'ground');
                }

                loadGame();

                function loadGame(map) {
                    if (!map) {
                        return;
                    }
                    isInit = true;
                    players = {};
                    missiles = {};
                    scale = (gameBorder / map["border"]) / tileSize;
                    scaledGrid = tileSize * scale;
                    game.state.start('animation');
                }

                function loadState(state) {
                    if (!state) {
                        return;
                    }

                    console.log(state); // FIXME debug info
                    if (isInit) {
                        console.log("init");
                        isInit = false;
                    } else {
                        console.log("update");
                    }

                    state['missiles'].forEach(function (state) {
                        if (!missiles.hasOwnProperty(state.id)) {
                            missiles[state.id] = createMissile(state.id, state.x, state.y);
                        }
                        missiles[state.id].moveTo(state.x, state.y);
                    });
                    state['players'].forEach(function (state) {
                        if (!players.hasOwnProperty(state.id)) {
                            players[state.id] = createPlayer(state.id, state.x, state.y);
                        }
                        players[state.id].moveTo(state.x, state.y);
                    });
                    players = removeOldItems(players, state['players']);
                    missiles = removeOldItems(missiles, state['missiles']);
                }

                $scope.$watch('map', loadGame, true);

                $scope.$watch('state', loadState);


                $scope.$on('$destroy', function() {
                    game.$destroy();
                });

            }
        }
    });


