(function() {
    'use strict';
    angular
        .module('jHipsterApp')
        .factory('Passport', Passport);

    Passport.$inject = ['$resource', 'DateUtils'];

    function Passport ($resource, DateUtils) {
        var resourceUrl =  'api/passports/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.endDate = DateUtils.convertDateTimeFromServer(data.endDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
