package com.mbhosts

import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

class VideoSpec extends Specification {

    @Unroll('validate a Video Object with duration "#duration" and/or "#timestamp" should have returned "#shouldBeValid"')
    void "test method validateVideo()"() {
        expect:
            new Video(duration: duration, timestamp: timestamp).validateVideo() == shouldBeValid
        where:
            duration | timestamp                                                        | shouldBeValid
            100      | Instant.now().plusSeconds(10).toEpochMilli()         | true
            110      | Instant.now().minusSeconds(10).toEpochMilli()     | false
            130      | null                                                             | false
            null     | Instant.now().plusSeconds(10).toEpochMilli()         | false
            null     | null                                                             | false
    }

}