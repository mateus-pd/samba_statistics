package com.mbhosts

import java.time.Instant
import java.util.concurrent.ConcurrentNavigableMap
import java.util.concurrent.ConcurrentSkipListMap
import java.util.stream.Collectors

class VideoService {

    ConcurrentSkipListMap<Long, Video> videoMap = new ConcurrentSkipListMap<>()

    void addVideo(Video video) {
        videoMap.put(video.getTimestamp(), video)
    }

    List<Video> getAllVideos() {
        return fetchVideosInTime().values().stream().collect(Collectors.toList())
    }

    ConcurrentNavigableMap<Long, Video> fetchVideosInTime() {
        def date = Instant.now().minusSeconds(60).toEpochMilli()
        return videoMap.tailMap(date)
    }

    ConcurrentNavigableMap<Long, Video> fetchVideosOutDated() {
        def date = Instant.now().plusSeconds(60).toEpochMilli()
        return videoMap.headMap(date)
    }

    void clearVideos() {
        videoMap.clear()
    }

}