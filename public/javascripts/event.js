var eventSearch = angular.module("eventSearch", []);

eventSearch.service("EventService", function($http) {
    return({
        getInfo: infoList,
        searchEvents: search,
        planBudget: plan
    });

    function infoList() {
        return $http({
            method: "get",
            url: "info"
        }).then(function(response) { return response.data })
    }

    /**
     * Search for events by specified predicates
     * @param city - city name
     * @param date - event date
     * @param eventType - type of event
     * @param category - event category
     * @returns {*} - list of events
     */
    function search(city, date, eventType, category) {
        var request = {};
        if (city) { request.city = city }
        if (date) { request.date = date }
        if (eventType) { request.eventType = eventType }
        if (category) { request.category = category }
        return $http.post("/events", request).then(function(response) {
            return response.data;
        })
    }

    /**
     * Plans budget for user
     * @param budget - user's available budget
     * @returns {*} - list of event groups
     */
    function plan(budget) {
        return $http.post("/planBudget", { "budget" : budget, "recordCount" : 10 }).then(function(response) {
            return response.data;
        })
    }
});

eventSearch.controller("eventSearchCtrl", function ($scope, EventService) {
    EventService.getInfo().then(function (info) {
        $scope.info = info;
        console.log($scope.info);
    });

    $scope.search = function (city, date, eventType, category) {
        console.log("Searching for events by filter: [City : " + city + ", date : " + date + ", type : " + eventType
            + ", category : " + category + "]");
        EventService.searchEvents(city, date, eventType, category).then(function (data) {
            $scope.events = data;
        })
    };

    $scope.search(null, null, null, null);
});

eventSearch.controller("budgetCtrl", function ($scope, EventService) {
    $scope.plan = function (budget) {
        console.log("Planing budget: " + budget);
        EventService.planBudget(budget).then(function (budget) {
            $scope.budget = budget;
        })
    }
});