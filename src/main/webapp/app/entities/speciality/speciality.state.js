(function() {
    'use strict';

    angular
        .module('toysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('speciality', {
            parent: 'entity',
            url: '/speciality',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Specialities'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/speciality/specialities.html',
                    controller: 'SpecialityController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('speciality-detail', {
            parent: 'entity',
            url: '/speciality/{code}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Speciality'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/speciality/speciality-detail.html',
                    controller: 'SpecialityDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Speciality', function($stateParams, Speciality) {
                    return Speciality.get({code : $stateParams.code}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'speciality',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('speciality-detail.edit', {
            parent: 'speciality-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/speciality/speciality-dialog.html',
                    controller: 'SpecialityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Speciality', function(Speciality) {
                            return Speciality.get({code : $stateParams.code}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('speciality.new', {
            parent: 'speciality',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/speciality/speciality-dialog.html',
                    controller: 'SpecialityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                code: null,
                                name: null,
                                description: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('speciality', null, { reload: 'speciality' });
                }, function() {
                    $state.go('speciality');
                });
            }]
        })
        .state('speciality.edit', {
            parent: 'speciality',
            url: '/{code}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/speciality/speciality-dialog.html',
                    controller: 'SpecialityDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Speciality', function(Speciality) {
                            return Speciality.get({code : $stateParams.code}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('speciality', null, { reload: 'speciality' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('speciality.delete', {
            parent: 'speciality',
            url: '/{code}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/speciality/speciality-delete-dialog.html',
                    controller: 'SpecialityDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Speciality', function(Speciality) {
                            return Speciality.get({code : $stateParams.code}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('speciality', null, { reload: 'speciality' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
