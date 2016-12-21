(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('ProductViewDetailController', ProductViewDetailController);

    ProductViewDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ProductView'];

    function ProductViewDetailController($scope, $rootScope, $stateParams, previousState, entity, ProductView) {
        var vm = this;

        vm.productView = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('toysApp:productViewUpdate', function(event, result) {
            vm.productView = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
