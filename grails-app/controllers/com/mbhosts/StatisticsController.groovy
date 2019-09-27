package com.mbhosts

import grails.converters.JSON
import org.springframework.http.HttpStatus

class StatisticsController {

    StatisticsService statisticsService

	static responseFormats = ['json']

    synchronized def apiStatistics() {
        def statistics = statisticsService.getStatistics()

        render(status: HttpStatus.OK.value(), contentType: "application/json", statistics as JSON)
    }

}