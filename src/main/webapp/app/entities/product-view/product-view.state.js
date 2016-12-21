(function() {
    'use strict';

    angular
        .module('toysApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('product-view', {
            parent: 'entity',
            url: '/product-view?page&sort&search',
            data: {
                authorities: [],
                pageTitle: 'ProductViews'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-view/product-views.html',
                    controller: 'ProductViewController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'code,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }]
            }
        })
        .state('product-view-detail', {
            parent: 'entity',
            url: '/product-view/{code}',
            data: {
                authorities: [],
                pageTitle: 'ProductView'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/product-view/product-view-detail.html',
                    controller: 'ProductViewDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ProductView', function($stateParams, ProductView) {
                    return ProductView.get({code : $stateParams.code}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'product-view',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
            .state('product-end-view', {
                parent: 'entity',
                url: '/product-end-view?page&sort&search',
                data: {
                    authorities: [],
                    pageTitle: 'ProductEndViews'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/product-view/product-end-views.html',
                        controller: 'ProductViewController',
                        controllerAs: 'vm'
                    }
                },
                params: {
                    page: {
                        value: '1',
                        squash: true
                    },
                    sort: {
                        value: 'code,asc',
                        squash: true
                    },
                    search: null
                },
                resolve: {
                    pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                        return {
                            page: PaginationUtil.parsePage($stateParams.page),
                            sort: $stateParams.sort,
                            predicate: PaginationUtil.parsePredicate($stateParams.sort),
                            ascending: PaginationUtil.parseAscending($stateParams.sort),
                            search: $stateParams.search
                        };
                    }]
                }
            })
            .state('product-end-view.delete', {
                parent: 'product-end-view',
                url: '/{code}/delete',
                data: {
                    authorities: []
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'app/entities/product-view/product-view-delete-dialog.html',
                        controller: 'ProductEndViewDeleteController',
                        controllerAs: 'vm',
                        size: 'md',
                        resolve: {
                            entity: ['ProductEndView', function(ProductEndView) {
                                return ProductEndView.get({code : $stateParams.code}).$promise;
                            }]
                        }
                    }).result.then(function() {
                        $state.go('product-end-view', null, { reload: 'product-end-view' });
                    }, function() {
                        $state.go('^');
                    });
                }]
            });
    }

})();
