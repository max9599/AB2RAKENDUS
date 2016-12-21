(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('HomeController', HomeController);

    HomeController.$inject = ['$scope', 'Principal', 'LoginService', '$state', 'Auth', '$rootScope', '$http'];

    function HomeController ($scope, Principal, LoginService, $state, Auth, $rootScope, $http) {
        var vm = this;

        vm.account = null;
        vm.isAuthenticated = null;
        vm.login = login;
        vm.overview = overview;

        function overview () {
            $http.get("/api/product-overview").then(function (response) {
                vm.overviews = response.data;
                if (vm.overviews) {
                    vm.totalProducts = 0;
                    vm.overviews.forEach(function (overview) {
                        vm.totalProducts += overview.total;
                    });
                }
            });
        }


        function login () {
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');
            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        vm.logout = logout;

        function logout() {
            Auth.logout();
            $state.go('home');
        }

        vm.register = register;
        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();

        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;
                vm.overview();

            });
        }
        function register () {
            $state.go('register');
        }
    }
})();
