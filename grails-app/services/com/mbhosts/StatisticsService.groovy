package com.mbhosts

class StatisticsService {

    VideoService videoService

    Statistics getStatistics() {
        List<Video> videoList = videoService.getAllVideos()
        DoubleSummaryStatistics statistics = videoList.stream().mapToDouble().summaryStatistics()
        return castSummaryStatistics(statistics)
    }

    Statistics castSummaryStatistics(DoubleSummaryStatistics _stats) {
        return new Statistics(_stats.getSum(), _stats.getAverage(), _stats.getMax(), _stats.getMin(), _stats.getCount())
    }

}