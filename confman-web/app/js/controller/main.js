'use strict';

angular.module('confman').controller('MainCtrl',['$rootScope', function ($rootScope) {
    $rootScope.callbackOK();
    $rootScope.currentPage = {
        code: 'home',
        name : "Welcome in Confman",
        actionbar : []
    };
}])