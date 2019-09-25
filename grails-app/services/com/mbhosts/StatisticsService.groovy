package com.mbhosts

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
        return new Statistics(_stats.getSum(), _stats.getAverage(), _stats.getMax(), _stats.getMin(), _stats.getCount())
    }

}