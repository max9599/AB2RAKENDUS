(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('SpecialityDetailController', SpecialityDetailController);

    SpecialityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Speciality'];

    function SpecialityDetailController($scope, $rootScope, $stateParams, previousState, entity, Speciality) {
        var vm = this;

        vm.speciality = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('toysApp:specialityUpdate', function(event, result) {
            vm.speciality = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
