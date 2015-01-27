var ieTanksVisualization = angular.module('ieTanksVisualization', ['ngTable']);

ieTanksVisualization.controller('GameCtrl', ['$scope', '$interval', '$routeParams', 'REST', 'ngTableParams',
    function ($scope, $interval, $routeParams, REST, ngTableParams) {
        var events = [];
        var intervalStepping = undefined;
        $scope.gameBorder = 600;
        $scope.sizes = [];
        $scope.games = [];
        $scope.currentStep = 0;
        $scope.gameLength = 0;
        $scope.gameStats = undefined; //{"tankMoves":11,"bulletsShot":2,"roundsPlayed":6};
        $scope.tankStats = []; //[{"tankMoves":"6","bulletsShot":"1","tankId":"bot1"},{"tankMoves":"5","bulletsShot":"1","tankId":"bot2"}];

        $scope.changeStep = function(amount) {
            if($scope.currentStep+amount>=$scope.gameLength) {
                $scope.currentStep = $scope.gameLength-1;
            } else {
                if($scope.currentStep + amount <= 0) {
                    $scope.currentStep = 0;
                } else {
                    $scope.currentStep += amount;
                }
            }
        };

        for(var i = 150; i<1000; i+=150) {
            $scope.sizes.push(i);
        }

        $scope.$watch('selectedGame', function() {
            if($scope.selectedGame) {
                var gameInfo = REST.game.query({gameId: $scope.selectedGame.gameId}, function () {
                    events = gameInfo['events'];
                    $scope.map = gameInfo['map'];
                    $scope.gameLength = events.length;
                    $scope.currentStep = 0;
                    if(angular.isDefined(intervalStepping)) {
                        $interval.cancel(intervalStepping);
                    }
                    intervalStepping = $interval(function () {
                        if ($scope.currentStep < $scope.gameLength) {
                            $scope.state = events[$scope.currentStep];
                            ++($scope.currentStep);
                        } else {
                            console.log('No more events to display.');
                        }
                    }, 2000);
                }, function () {
                    console.log('Failed to load game events.');
                });
                var allGameStat = REST.stat.get({gameId: $scope.selectedGame.gameId }, function () {
                    $scope.gameStats = allGameStat['gameStats'];
                    $scope.tankStats = allGameStat['tanksStats'];

                    $scope.tanksStatsTableParams.reload();
                });

            }
        });

        $scope.$watch('currentStep', function(){
            if (!isNaN($scope.currentStep) && $scope.currentStep < $scope.gameLength) {
                $scope.state = events[$scope.currentStep];
            }
        });

        $scope.games = REST.game.query(function() {}, function() {
            console.log('Failed to load list of games.');
            alertify.error('Failed to load list of games.');
        });

        $scope.tanksStatsTableParams = new ngTableParams({
            page: 1,            // show first page
            count: 10           // count per page
        }, {
            total: function () { $scope.tankStats.length }, // length of data
            getData: function($defer, params) {
                $defer.resolve($scope.tankStats.slice((params.page() - 1) * params.count(), params.page() * params.count()));
            }
        });

        // TODO: REMOVE/COMMENT EVERYTHING UNDER WHEN REST SERVICES ARE READY

        for(var i = 1; i< 100; i++) {
            $scope.games.push({gameId:i});
        }

        $scope.map = {
            width: 18,
            height: 20,
            obstacles: [{type: '', x: 4, y: 9}, {type: '', x: 10, y: 7}, {type: '', x: 11, y: 7}, {
                type: '',
                x: 12,
                y: 7
            }]
        };

        events = [{
            players: [{playerId: 'blabla', action: 'move', x: '10', y: '5', dirX:1, dirY:0}, {
                playerId: 'blabla2',
                action: 'move',
                x: '3',
                y: '2', dirX:-1, dirY:0
            }], missiles: []
        },
            {
                players: [{playerId: 'blabla', action: 'move', x: '6', y: '2', dirX: -1, dirY:-1}, {
                    playerId: 'blabla2',
                    action: 'move',
                    x: '11',
                    y: '2', dirX: 1, dirY:0
                }], missiles: []
            },
            {
                players: [{playerId: 'blabla', action: 'move', x: '14', y: '19', dirX:1, dirY:-1}, {
                    playerId: 'blabla2',
                    action: 'move',
                    x: '5',
                    y: '5', dirX:-1, dirY:-1
                }], missiles: []
            },
            {
                players: [{playerId: 'blabla', action: 'move', x: '0', y: '16', dirX: -1, dirY:1}, {
                    playerId: 'blabla2',
                    action: 'move',
                    x: '7',
                    y: '0', dirX: 1, dirY:1
                }], missiles: []
            },
            {
                players: [{playerId: 'blabla', action: 'move', x: '0', y: '14', dirX: -1, dirY:1}, {
                    playerId: 'blabla2',
                    action: 'shoot',
                    x: '7',
                    y: '0', dirX:0, dirY:0
                }], missiles: [{playerId: 'blabla2', missileId: '1', x: '7', y: '1', dirX:0, dirY:-1}]
            },
            {
                players: [{playerId: 'blabla', action: 'move', x: '5', y: '12', dirX:1, dirY:1}, {
                    playerId: 'blabla2',
                    action: 'move',
                    x: '0',
                    y: '10', dirX:-1, dirY:-1
                }], missiles: [{playerId: 'blabla2', missileId: '1', x: '5', y: '12', dirX:-1, dirY:-1}]
            },
            {players: [{playerId: 'blabla2', action: 'move', x: '7', y: '12', dirX:1, dirY:-1}], missiles: []},
            {players: [{playerId: 'blabla2', action: 'move', x: '0', y: '0', dirX:-1, dirY:1}], missiles: []}];

        var changeMap = false;
        $scope.gameLength = events.length;
        $interval(function () {
            if ($scope.currentStep==0 && changeMap) {
                var mapWidth = Math.floor(Math.random()*4)+18;
                var mapHeight = Math.floor(Math.random()*4)+20;
                $scope.map = {
                    width: mapWidth,
                    height: mapHeight,
                    obstacles: [{type: '', x: 4, y: 9}, {type: '', x: 10, y: 7}, {type: '', x: 11, y: 7}, {
                        type: '',
                        x: 12,
                        y: 7
                    }]
                };
                $scope.gameLength = events.length;
                changeMap = false;
            } else {
                //$scope.state = events[$scope.currentStep];
                $scope.currentStep = (1 + $scope.currentStep) % events.length;
                changeMap = true;
            }
        }, 1500);

    }
])
    .controller('GameHistory', ['$scope', '$interval', 'REST',
        function ($scope, $interval, REST) {
            var gameHistory = REST.games.query(function () {
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
                state: '=',
                maximalBorder: '='
            },
            link: function ($scope) {

                var gameBorder = $scope.maximalBorder || 600, maximalWidth, maximalHeight;
                var tileSize = 65;
                var mapTileSize = 128;
                var game, ratio, scale, scaledGrid, players, missiles, obstacles, mapScale, scaledMapGrid, leadGrid;

                var directionMap = {1: {"-1":135, 0 : 90, "1": 45},
                                0: {"-1": 180, 0: undefined, "1": 0 },
                                "-1": {"-1": 225, 0: 270, "1": 315}};

                var Player = function (id, tank, turret, direction, color) {
                    this.id = id;
                    this.element = tank;
                    this.turret = turret;
                    this.direction = direction;
                    this.color = color;
                };

                var Missile = function (id, bullet, direction) {
                    this.id = id;
                    this.element = bullet;
                    this.direction = direction;
                };

                var createPlayer = function (id, x, y) {
                    x = Number(x);
                    y = Number(y);
                    var tank = game.add.sprite((x+0.5) * scaledGrid, (y+0.5) * scaledGrid, 'tank');
                    var turret = game.add.sprite(0, 0, 'turret');
                    turret.anchor.setTo(0.5, 0.5);
                    tank.anchor.setTo(0.5, 0.5);
                    tank.addChild(turret);
                    tank.scale.x = scale;
                    tank.scale.y = scale;
                    var color = Phaser.Color.getRandomColor();
                    tank.tint = color;
                    turret.tint = color;
                    return new Player(id, tank, turret, 0, color);
                };

                var createMissile = function (id, x, y, color) {
                    x = Number(x);
                    y = Number(y);
                    var bullet = game.add.sprite((x+0.5) * scaledGrid, (y+0.5) * scaledGrid, 'bullet');
                    bullet.anchor.setTo(0.5, 0.5);
                    bullet.scale.x = scale;
                    bullet.scale.y = scale;
                    bullet.tint = color;
                    return new Missile(id, bullet, 0);
                };

                var sameAngles = function(angle1, angle2) {
                    return angle1 == angle2 || angle1 - angle2 == 360 || angle1 - angle2 == -360
                };

                Missile.prototype.moveTo = Player.prototype.moveTo = function (x, y, dirX, dirY) {
                    x = Number(x);
                    y = Number(y);
                    var newAngle = directionMap[dirX][dirY];
                    if(newAngle - this.element.angle > 180) {
                        newAngle = newAngle - 360;
                    }

                    var newPosition = {
                        x: Math.floor(tileSize * scale) * (x + 0.5),
                        y: Math.floor(tileSize * scale) * (y + 0.5)};

                    if(newAngle === undefined ||  sameAngles(newAngle, this.element.angle)) {
                        game.add.tween(this.element)
                            .to(newPosition, 500, Phaser.Easing.Quadratic.InOut, true);
                    } else {

                        game.add.tween(this.element)
                            .to({angle: newAngle}, 300, Phaser.Easing.Linear.InOut, true)
                            .to(newPosition, 500, Phaser.Easing.Quadratic.InOut, true);
                    }
                };

                Player.prototype.turnTurret = function(dirX, dirY) {
                    var newAngle = directionMap[dirX][dirY] - Number(this.element.angle);
                    if(newAngle - this.element.angle > 180) {
                        newAngle = newAngle - 360;
                    }

                    if(newAngle !== undefined && !sameAngles(newAngle, this.element.angle)) {
                        game.add.tween(this.turret)
                            .to({angle: newAngle}, 300, Phaser.Easing.Linear.InOut, true);
                    }
                };

                var removeOldItems = function (itemsSet, identifiers) {
                    for (var item in itemsSet) {
                        if (itemsSet.hasOwnProperty(item) && identifiers.indexOf(item) === -1) {
                            itemsSet[item].element.destroy();
                            delete itemsSet[item];
                        }
                    }
                    return itemsSet;
                };

                function preload() {
                    //load image which will be used as ground texture
                    game.load.image('ground', 'assets/Land_DirtGrass_main.png');
                    game.load.image('top_ground', 'assets/Land_DirtGrass_top.png');
                    game.load.image('left_ground', 'assets/Land_DirtGrass_left.png');
                    game.load.image('bottom_ground', 'assets/Land_DirtGrass_bottom.png');
                    game.load.image('right_ground', 'assets/Land_DirtGrass_right.png');
                    game.load.image('right_bot_ground', 'assets/Land_DirtGrass_right_bot.png');
                    game.load.image('right_top_ground', 'assets/Land_DirtGrass_right_top.png');
                    game.load.image('left_bot_ground', 'assets/Land_DirtGrass_left_bot.png');
                    game.load.image('left_top_ground', 'assets/Land_DirtGrass_left_top.png');
                    game.load.image('dirt_patch', 'assets/Land_DirtGrass_dirt_patch.png');
                    game.load.image('flower_1', 'assets/Flower (1).png');
                    game.load.image('flower_2', 'assets/Flower (2).png');
                    game.load.image('flower_3', 'assets/Flower (3).png');
                    game.load.image('flower_4', 'assets/Flower (4).png');
                    game.load.image('flower_5', 'assets/Flower (5).png');
                    game.load.image('flower_6', 'assets/Flower (6).png');
                    game.load.image('flower_7', 'assets/Flower (7).png');
                    game.load.image('flower_8', 'assets/Flower (8).png');
                    game.load.image('flower_9', 'assets/Flower (9).png');
                    game.load.image('flower_10', 'assets/Flower (10).png');
                    game.load.image('flower_11', 'assets/Flower (11).png');
                    game.load.image('flower_12', 'assets/Flower (12).png');
                    game.load.image('bullet', 'assets/bulletWhite.png');
                    game.load.image('tank', 'assets/PanzerStrWhite.png');
                    game.load.image('turret', 'assets/PanzerStrTurretWhite.png');
                    game.load.image('wall', 'assets/Stone_wall.png');
                    //  This sets a limit on the up-scale
                    game.scale.maxHeight = maximalHeight;
                    game.scale.maxWidth = maximalWidth;

                    //  Then we tell Phaser that we want it to scale up to whatever the browser can handle, but to do it proportionally
                    game.scale.scaleMode = Phaser.ScaleManager.SHOW_ALL;
                    game.scale.setScreenSize();
                }

                function prepareStaticMap() {
                    game.add.tileSprite(0, 0, game.width, game.height, 'ground');
                    var s = game.add.tileSprite(scaledMapGrid, 0, game.width - scaledMapGrid * 2, scaledMapGrid, 'top_ground');
                    s.tileScale.y = mapScale;
                    s.tileScale.x = mapScale;
                    s = game.add.tileSprite(0, scaledMapGrid, scaledMapGrid, game.height - scaledMapGrid * 2, 'left_ground');
                    s.tileScale.y = mapScale;
                    s.tileScale.x = mapScale;
                    s = game.add.tileSprite(game.width - scaledMapGrid, scaledMapGrid, scaledMapGrid, game.height - scaledMapGrid * 2, 'right_ground');
                    s.tileScale.y = mapScale;
                    s.tileScale.x = mapScale;
                    s = game.add.tileSprite(scaledMapGrid, game.height - scaledMapGrid, game.width - scaledMapGrid * 2, scaledMapGrid, 'bottom_ground');
                    s.tileScale.y = mapScale;
                    s.tileScale.x = mapScale;
                    s = game.add.sprite(game.width - scaledMapGrid, game.height - scaledMapGrid, 'right_bot_ground');
                    s.scale.x = mapScale;
                    s.scale.y = mapScale;
                    s = game.add.sprite(game.width - scaledMapGrid, 0, 'right_top_ground');
                    s.scale.x = mapScale;
                    s.scale.y = mapScale;
                    s = game.add.sprite(0, game.height - scaledMapGrid, 'left_bot_ground');
                    s.scale.x = mapScale;
                    s.scale.y = mapScale;
                    s = game.add.sprite(0, 0, 'left_top_ground');
                    s.scale.x = mapScale;
                    s.scale.y = mapScale;

                    for (var i = 1; i < leadGrid; i++) {
                        var flower = Math.floor((Math.random() * 15) + 1);
                        var x = Math.floor((Math.random() * gameBorder));
                        var y = Math.floor((Math.random() * gameBorder));

                        if (flower >= 13) {
                            s = game.add.sprite(x, y, 'dirt_patch');
                            s.scale.x = mapScale;
                            s.scale.y = mapScale;
                        } else {
                            s = game.add.sprite(x, y, 'flower_' + flower.toString());
                            s.scale.x = mapScale * 2;
                            s.scale.y = mapScale * 2;
                        }

                    }

                }

                function create() {
                    prepareStaticMap();
                    //Adds background to map (x_pos, y_pos, x_size, y_size) in pixels
                    obstacles.forEach(function (obstacle) {
                        var obs = game.add.sprite(obstacle['x'] * scaledGrid, obstacle['y'] * scaledGrid, 'wall');
                        obs.scale.x = mapScale;
                        obs.scale.y = mapScale;
                    });

                }

                loadGame();

                function loadGame(map) {
                    if (!map) {
                        return;
                    }
                    if (game) {
                        if (game.canvas) {
                            Phaser.Canvas.removeFromDOM(game.canvas)
                        }
                        game.destroy();
                    }

                    var gridWidth = $scope.map["width"];
                    var gridHeight = $scope.map["height"];
                    var gameHeight, gameWidth;
                    ratio = gridWidth / gridHeight;
                    if(gridWidth>=gridHeight) {
                        gameWidth = gameBorder;
                        gameHeight = gameBorder/ratio;
                        leadGrid = gridWidth;
                    } else {
                        gameWidth = gameBorder * ratio;
                        gameHeight = gameBorder;
                        leadGrid = gridHeight;
                    }
                    maximalWidth = gameWidth;
                    maximalHeight = gameHeight;
                    game = new Phaser.Game(gameWidth, gameHeight, Phaser.CANVAS, 'game-window', {}, true);

                    game.state.add('animation', {
                        preload: preload,
                        create: create
                    });
                    players = {};
                    missiles = {};
                    obstacles = $scope.map["obstacles"] || [];
                    scale = (gameBorder / leadGrid) / tileSize;
                    mapScale = (gameBorder / leadGrid) / mapTileSize;
                    scaledGrid = tileSize * scale;
                    scaledMapGrid = mapScale * mapTileSize;
                    game.state.start('animation');
                }

                function loadState(state) {
                    if (!game || !(game.add) || !$scope.map || !state) {
                        return;
                    }

                    state['players'].forEach(function (state) {
                        if (!players.hasOwnProperty(state.playerId)) {
                            players[state.playerId] = createPlayer(state.playerId, state.x, state.y);
                        }
                        players[state.playerId].moveTo(state.x, state.y, state.dirX, state.dirY);
                    });


                    state['missiles'].forEach(function (state) {
                        if (!missiles.hasOwnProperty(state.missileId)) {
                            var missile_color;
                            if (players.hasOwnProperty(state.playerId)) {
                                missile_color = players[state.playerId].color;
                                players[state.playerId].turnTurret(state.dirX, state.dirY);
                            }
                            else {
                                missile_color = Phaser.Color.getRandomColor();
                            }
                            missiles[state.missileId] = createMissile(state.missileId, state.x, state.y, missile_color);
                        }
                        missiles[state.missileId].moveTo(state.x, state.y, state.dirX, state.dirY);
                    });

                    players = removeOldItems(players, state['players'].map(function (state) {
                        return state.playerId;
                    }));
                    missiles = removeOldItems(missiles, state['missiles'].map(function (state) {
                        return state.missileId;
                    }));
                }

                $scope.$watch('map', loadGame, true);

                $scope.$watch('state', loadState);

                $scope.$watch('maximalBorder', function()
                {
                    if($scope.maximalBorder != gameBorder) {
                        gameBorder = $scope.maximalBorder || 600;
                        loadGame($scope.map);
                        if (game) {
                            loadState($scope.state);
                        }
                    }
                });

                $scope.$on('$destroy', function () {
                    game.$destroy();
                });

            }
        }
    });


