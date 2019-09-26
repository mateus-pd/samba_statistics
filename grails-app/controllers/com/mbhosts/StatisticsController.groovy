package com.mbhosts

import grails.converters.JSON
import org.springframework.http.HttpStatus

class StatisticsController {

    StatisticsService statisticsService

	static responseFormats = ['json']

    def apiStatistics() {
        def statistics = statisticsService.getStatistics()

        if (statistics)
            render(status: HttpStatus.OK.value(), contentType: "application/json", statistics as JSON)
        else
            render(status: HttpStatus.NO_CONTENT.value())
    }

}