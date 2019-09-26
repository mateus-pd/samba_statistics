package com.mbhosts

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.testing.mixin.integration.Integration
import grails.testing.web.controllers.ControllerUnitTest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

@Integration
class VideoControllerSpec extends Specification implements ControllerUnitTest<VideoController> {

    @Value('${local.server.port}')
    Integer serverPort
    @Shared
    def rest = new RestBuilder(connectTimeout: 1000, readTimeout: 10000)
    @Shared // User: sambatech Pass: sambatech
    def token = "Basic c2FtYmF0ZWNoOnNhbWJhdGVjaA=="

    @Unroll('validate the API Rest /videos POST with a Valid Video')
    void "test apiAddVideo()"() {
        when:
            RestResponse respValid = rest.post("http://localhost:${serverPort}/videos") {
                accept("*/*")
                header("Authorization", token)
                contentType("application/json")
                body(new Video(duration: 178.2, timestamp: System.currentTimeMillis()+6000) as JSON)
            }
        then:
            respValid.status == HttpStatus.CREATED.value()
    }

    @Unroll('validate the API Rest /videos POST with a Invalid Video')
    void "test apiAddVideo() invalidVideo"() {
        when:
            RestResponse respInvalid = rest.post("http://localhost:${serverPort}/videos") {
                accept("*/*")
                header("Authorization", token)
                contentType("application/json")
                body(new Video(duration: 188.2, timestamp: System.currentTimeMillis()-6000) as JSON)
            }
        then:
            respInvalid.status == HttpStatus.NO_CONTENT.value()
    }

    @Unroll('validate the API Rest /videos DELETE')
    void "test apiDeleteVideos()"() {
        when:
            RestResponse resp = rest.delete("http://localhost:${serverPort}/videos") {
                header("Authorization", token)
            }
        then:
            resp.status == HttpStatus.NO_CONTENT.value()
    }

}