(function() {
    'use strict';

    angular
        .module('jHipsterApp')
        .controller('CitizenDialogController', CitizenDialogController);

    CitizenDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Citizen', 'Passport', 'Country'];

    function CitizenDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Citizen, Passport, Country) {
        var vm = this;

        vm.citizen = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.passports = Passport.query({filter: 'citizen-is-null'});
        $q.all([vm.citizen.$promise, vm.passports.$promise]).then(function() {
            if (!vm.citizen.passport || !vm.citizen.passport.id) {
                return $q.reject();
            }
            return Passport.get({id : vm.citizen.passport.id}).$promise;
        }).then(function(passport) {
            vm.passports.push(passport);
        });
        vm.countries = Country.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.citizen.id !== null) {
                Citizen.update(vm.citizen, onSaveSuccess, onSaveError);
            } else {
                Citizen.save(vm.citizen, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('jHipsterApp:citizenUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dob = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
