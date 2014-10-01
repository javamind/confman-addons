'use strict';

var restActions = {
    'get':    {method:'GET'},
    'save':   {method:'POST'},
    'update': {method:'PUT'},
    'query':  {method:'GET', isArray:true},
    'delete': {method:'DELETE'}
};

angular.module('confman')
    .factory('Environment', ['$resource', 'constants', function Environment($resource, constants) {
        return $resource(constants.urlserver + 'environment/:id', { id: '@_id' },restActions);
    }])
    .factory('SoftwareSuite', ['$resource', 'constants', function SoftwareSuite($resource, constants) {
        return $resource(constants.urlserver + 'softwaresuite/:id', { id: '@_id' },restActions);
    }])
    .factory('Application', ['$resource', 'constants', function Application($resource, constants) {
        return $resource(constants.urlserver + 'application/:id', { id: '@_id' },restActions);
    }])
    .factory('ApplicationVersion', ['$resource', 'constants', function ApplicationVersion($resource, constants) {
        return $resource(constants.urlserver + 'applicationversion/:id', { id: '@_id' },restActions);
    }])
    .factory('TrackingVersion', ['$resource', 'constants', function TrackingVersion($resource, constants) {
        return $resource(constants.urlserver + 'trackingversion/:id', { id: '@_id' },restActions);
    }])
    .factory('Instance', ['$resource', 'constants', function Instance($resource, constants) {
        return $resource(constants.urlserver + 'instance/:id', { id: '@_id' },restActions);
    }])
    .factory('Parameter', ['$resource', 'constants', function Parameter($resource, constants) {
        return $resource(constants.urlserver + 'parameter/:id', { id: '@_id' },restActions);
    }])
    .factory('ParamaterGroupment', ['$resource', 'constants', function ParamaterGroupment($resource, constants) {
        return $resource(constants.urlserver + 'parametergroupment/:id', { id: '@_id' },restActions);
    }]);