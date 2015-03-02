/** Chat Style Game Controllers */
angular.module('wb.controllers', ['wb.services']).
    controller('gameCtrl', function ($scope, $http, gameModel) {
        $scope.games = gameModel.getGames();
        $scope.msgs = [];
        $scope.inputText = "";
        $scope.user = "Jane Doe #" + Math.floor((Math.random() * 100) + 1);
        $scope.currentGame = $scope.game[0];

        /** change current game, restart EventSource connection */
        $scope.setCurrentGame = function (game) {
            $scope.currentGame = game;
            $scope.boardFeed.close();
            $scope.listen();
        };

        /** posting chat text to server */
        $scope.submitPlay = function () {
            $http.post("/play", { text: $scope.inputText, user: $scope.user,
                time: (new Date()).toUTCString(), game: $scope.currentGame.value });
            $scope.inputText = "";
        };

        /** handle incoming messages: add to messages array */
        $scope.addPlay = function (play) {
            $scope.$apply(function () { $scope.play.push(JSON.parse(play.data)); });
        };

        /** start listening on messages from selected room */
        $scope.listen = function () {
            $scope.gameFeed = new EventSource("/gameFeed/" + $scope.currentGame.value);
            $scope.gameFeed.addEventListener("Play", $scope.addPlay, false);
        };

        $scope.listen();
    });