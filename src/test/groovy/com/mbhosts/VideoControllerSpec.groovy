package com.mbhosts

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.testing.mixin.integration.Integration
import grails.testing.web.controllers.ControllerUnitTest
import org.springframework.beans.factory.annotation.Value
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

    @Unroll('validate the API Rest /videos POST with "#duration" and "#timestamp" should have returned status "#shouldBeValid"')
    void "test apiAddVideo()"() {
        given:
            def params = [duration: duration, timestamp: timestamp]

        when:
            println "timestamp to Date -> ${new Date(timestamp).dateTimeString}"
            RestResponse resp = rest.post("http://localhost:${serverPort}/videos") {
                accept("*/*")
                header("Authorization", token)
                header("Accept-Encoding", "gzip, deflate, br")
                header("Accept-Language", "pt-BR, pt, en-US, en")
                contentType("application/json")
                body(params as JSON)
            }

        then:
            resp.status == shouldBeValid

        where:
            // Adding to Timestamp 'cause when the test starts the System.currentTimeMillis() is on the past
            duration | timestamp                            | shouldBeValid
            100.4    | System.currentTimeMillis()+60000     | 201
            120.5    | System.currentTimeMillis()+70000     | 201
            104.1    | System.currentTimeMillis()+80000     | 201
            198.2    | System.currentTimeMillis()+90000     | 201
            212.5    | System.currentTimeMillis()-60000     | 204
            151.2    | System.currentTimeMillis()-70000     | 204
    }

    @Unroll('validate the API Rest /videos DELETE')
    void "test apiDeleteVideos()"() {
        when:
            RestResponse resp = rest.delete("http://localhost:${serverPort}/videos") {
                header("Authorization", token)
            }
        then:
            resp.status == 204
    }

}