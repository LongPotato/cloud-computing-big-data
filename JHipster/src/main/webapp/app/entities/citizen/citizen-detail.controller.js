(function() {
    'use strict';

    angular
        .module('jHipsterApp')
        .controller('CitizenDetailController', CitizenDetailController);

    CitizenDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Citizen', 'Passport', 'Country'];

    function CitizenDetailController($scope, $rootScope, $stateParams, previousState, entity, Citizen, Passport, Country) {
        var vm = this;

        vm.citizen = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('jHipsterApp:citizenUpdate', function(event, result) {
            vm.citizen = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
