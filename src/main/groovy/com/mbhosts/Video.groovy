package com.mbhosts

import java.time.Instant

class Video {

    private Double duration
    private Long timestamp

    Video() {  }
    Video(Double _duration, Long _timestamp) {
        this.duration = _duration
        this.timestamp = _timestamp
    }

    Double getDuration() { return this.duration }
    Long getTimestamp() { return this.timestamp }

    void setDuration(Double _duration) { this.duration = _duration }
    void setTimestamp(Long _timestamp) { this.timestamp = _timestamp }

    Boolean validateVideo() {
        if (this.duration && this.timestamp)
            return !(this.timestamp < Instant.now().toEpochMilli())
        else
            return false
    }

}