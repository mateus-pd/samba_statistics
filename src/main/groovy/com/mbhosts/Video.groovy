package com.mbhosts

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

}