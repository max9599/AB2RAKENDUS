(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('SpecialityDeleteController',SpecialityDeleteController);

    SpecialityDeleteController.$inject = ['$uibModalInstance', 'entity', 'Speciality'];

    function SpecialityDeleteController($uibModalInstance, entity, Speciality) {
        var vm = this;

        vm.speciality = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (code) {
            Speciality.delete({code: code},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
