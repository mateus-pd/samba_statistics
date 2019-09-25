package com.mbhosts

import spock.lang.Specification
import spock.lang.Unroll

class VideoSpec extends Specification {

    @Unroll('validate a Video Object with duration "#duration" and/or "#timestamp" should have returned "#shouldBeValid"')
    void "test method validateVideo()"() {
        expect:
            new Video(duration: duration, timestamp: timestamp).validateVideo() == shouldBeValid
        where:
            duration | timestamp                        | shouldBeValid
            100      | System.currentTimeMillis() + 100 | true
            110      | System.currentTimeMillis() - 100 | false
            120      | null                             | false
            null     | System.currentTimeMillis() + 100 | false
            null     | null                             | false
    }

}