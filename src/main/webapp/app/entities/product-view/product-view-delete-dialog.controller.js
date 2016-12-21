(function() {
    'use strict';

    angular
        .module('toysApp')
        .controller('ProductEndViewDeleteController',ProductEndViewDeleteController);

    ProductEndViewDeleteController.$inject = ['$uibModalInstance', 'entity', 'ProductEndView'];

    function ProductEndViewDeleteController($uibModalInstance, entity, ProductEndView) {
        var vm = this;

        vm.productEndView = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (code) {
            ProductEndView.delete({code: code},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
