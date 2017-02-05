(function() {
    'use strict';

    angular
        .module('jHipsterApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('passport', {
            parent: 'entity',
            url: '/passport',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Passports'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/passport/passports.html',
                    controller: 'PassportController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('passport-detail', {
            parent: 'passport',
            url: '/passport/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'Passport'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/passport/passport-detail.html',
                    controller: 'PassportDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Passport', function($stateParams, Passport) {
                    return Passport.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'passport',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('passport-detail.edit', {
            parent: 'passport-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passport/passport-dialog.html',
                    controller: 'PassportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Passport', function(Passport) {
                            return Passport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('passport.new', {
            parent: 'passport',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passport/passport-dialog.html',
                    controller: 'PassportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                startDate: null,
                                endDate: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('passport', null, { reload: 'passport' });
                }, function() {
                    $state.go('passport');
                });
            }]
        })
        .state('passport.edit', {
            parent: 'passport',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passport/passport-dialog.html',
                    controller: 'PassportDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Passport', function(Passport) {
                            return Passport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('passport', null, { reload: 'passport' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('passport.delete', {
            parent: 'passport',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/passport/passport-delete-dialog.html',
                    controller: 'PassportDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Passport', function(Passport) {
                            return Passport.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('passport', null, { reload: 'passport' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
