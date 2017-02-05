'use strict';

describe('Controller Tests', function() {

    describe('Citizen Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockCitizen, MockPassport, MockCountry;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockCitizen = jasmine.createSpy('MockCitizen');
            MockPassport = jasmine.createSpy('MockPassport');
            MockCountry = jasmine.createSpy('MockCountry');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Citizen': MockCitizen,
                'Passport': MockPassport,
                'Country': MockCountry
            };
            createController = function() {
                $injector.get('$controller')("CitizenDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'jHipsterApp:citizenUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
