package com.mbhosts

import groovy.time.TimeCategory

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
        def date = use (TimeCategory) { return new Date() - 60.seconds }
        return videoMap.tailMap(date.getTime())
    }

    ConcurrentNavigableMap<Long, Video> fetchVideosOutDated() {
        def date = use (TimeCategory) { return new Date() - 60.seconds }
        return videoMap.headMap(date.getTime())
    }

    void clearVideos() {
        videoMap.clear()
    }

}