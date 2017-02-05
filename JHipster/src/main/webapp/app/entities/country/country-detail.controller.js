(function() {
    'use strict';

    angular
        .module('jHipsterApp')
        .controller('CountryDetailController', CountryDetailController);

    CountryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Country', 'Citizen'];

    function CountryDetailController($scope, $rootScope, $stateParams, previousState, entity, Country, Citizen) {
        var vm = this;

        vm.country = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterApp:countryUpdate', function(event, result) {
            vm.country = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
