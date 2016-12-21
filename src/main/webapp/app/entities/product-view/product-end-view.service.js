(function() {
    'use strict';
    angular
        .module('toysApp')
        .factory('ProductEndView', ProductEndView);

    ProductEndView.$inject = ['$resource', 'DateUtils'];

    function ProductEndView ($resource, DateUtils) {
        var resourceUrl =  'api/product-end-views/:code';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.registrationDate = DateUtils.convertLocalDateFromServer(data.registrationDate);
                    }
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.registrationDate = DateUtils.convertLocalDateToServer(copy.registrationDate);
                    return angular.toJson(copy);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    var copy = angular.copy(data);
                    copy.registrationDate = DateUtils.convertLocalDateToServer(copy.registrationDate);
                    return angular.toJson(copy);
                }
            }
        });
    }
})();
