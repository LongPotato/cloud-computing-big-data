(function() {
    'use strict';
    angular
        .module('jHipsterApp')
        .factory('Citizen', Citizen);

    Citizen.$inject = ['$resource', 'DateUtils'];

    function Citizen ($resource, DateUtils) {
        var resourceUrl =  'api/citizens/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.dob = DateUtils.convertDateTimeFromServer(data.dob);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
