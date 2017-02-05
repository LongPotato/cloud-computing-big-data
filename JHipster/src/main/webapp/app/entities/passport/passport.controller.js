(function() {
    'use strict';

    angular
        .module('jHipsterApp')
        .controller('PassportController', PassportController);

    PassportController.$inject = ['$scope', '$state', 'Passport'];

    function PassportController ($scope, $state, Passport) {
        var vm = this;

        vm.passports = [];

        loadAll();

        function loadAll() {
            Passport.query(function(result) {
                vm.passports = result;
                vm.searchQuery = null;
            });
        }
    }
})();
