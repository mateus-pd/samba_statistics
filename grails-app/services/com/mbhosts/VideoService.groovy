package com.mbhosts

import groovy.time.TimeCategory

import java.time.LocalDateTime
import java.time.ZoneId
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
        return videoMap.tailMap(date.toInstant().getEpochSecond())
    }

    ConcurrentNavigableMap<Long, Video> fetchVideosOutOfTime() {
        def date = use (TimeCategory) { return new Date() - 60.seconds }
        return videoMap.headMap(date.toInstant().getEpochSecond())
    }

    void clearVideos() {
        videoMap.clear()
    }

}