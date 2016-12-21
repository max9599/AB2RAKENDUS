(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('ProductViewDialogController', ProductViewDialogController);

    ProductViewDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ProductView'];

    function ProductViewDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ProductView) {
        var vm = this;

        vm.productView = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.productView.code !== null) {
                ProductView.update(vm.productView, onSaveSuccess, onSaveError);
            } else {
                ProductView.save(vm.productView, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('toysApp:productViewUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.registrationDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
