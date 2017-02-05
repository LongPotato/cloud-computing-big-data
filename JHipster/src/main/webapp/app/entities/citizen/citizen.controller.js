(function() {
    'use strict';

    angular
        .module('jHipsterApp')
        .controller('CitizenController', CitizenController);

    CitizenController.$inject = ['$scope', '$state', 'Citizen'];

    function CitizenController ($scope, $state, Citizen) {
        var vm = this;

        vm.citizens = [];

        loadAll();

        function loadAll() {
            Citizen.query(function(result) {
                vm.citizens = result;
                vm.searchQuery = null;
            });
        }
    }
})();
