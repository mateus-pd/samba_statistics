import com.mbhosts.Video
import com.mbhosts.VideoService

import java.util.concurrent.ConcurrentNavigableMap

class CleanVideosJob {

    VideoService videoService

    static triggers = {
        simple repeatInterval: 10000l // execute job once in 10 seconds
    }

    def execute() {
        final ConcurrentNavigableMap<Long, Video> oldVideos = videoService.fetchVideosOutDated()
        if (oldVideos) {
            println "CleanVideosJob.execute: Cleaned ${oldVideos.size()} old Videos"
            oldVideos.clear()
        }
    }
}