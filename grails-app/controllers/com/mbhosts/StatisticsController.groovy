package com.mbhosts

import grails.converters.JSON

class StatisticsController {

    StatisticsService statisticsService

	static responseFormats = ['json']

    def apiStatistics() {
        def statistics = statisticsService.getStatistics()

        if (statistics)
            render(status: 201, contentType: "application/json", statistics as JSON)
        else
            render(status: 204)
    }

}