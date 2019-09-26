package com.mbhosts

import java.math.RoundingMode

class StatisticsService {

    VideoService videoService

    Statistics getStatistics() {
        List<Video> videoList = videoService.getAllVideos()
        if (videoList) {
            DoubleSummaryStatistics statistics = videoList.stream().mapToDouble{it.getDuration()}.summaryStatistics()
            return castSummaryStatistics(statistics)
        }
        else return new Statistics(0,0,0,0,0)
    }

    Statistics castSummaryStatistics(DoubleSummaryStatistics _stats) {
        return new Statistics(roundToDecimals(_stats.getSum()), roundToDecimals(_stats.getAverage()),
                              roundToDecimals(_stats.getMax()), roundToDecimals(_stats.getMin()), _stats.getCount())
    }

    static Double roundToDecimals(double value) {
        return new BigDecimal(value).setScale(2, RoundingMode.HALF_EVEN).toDouble()
    }

}