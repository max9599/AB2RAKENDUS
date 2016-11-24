(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('SpecialityDialogController', SpecialityDialogController);

    SpecialityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Speciality'];

    function SpecialityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Speciality) {
        var vm = this;

        vm.speciality = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.speciality.code !== null) {
                Speciality.update(vm.speciality, onSaveSuccess, onSaveError);
            } else {
                Speciality.save(vm.speciality, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('toysApp:specialityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
