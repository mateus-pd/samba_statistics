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
    void "test apiAddVideo() validVideo"() {
        when:
            def params = [duration: 198.2, timestamp: System.currentTimeMillis()+60000]
            println "timestamp to Date -> ${new Date(params.timestamp).dateTimeString}"
            RestResponse resp = rest.post("http://localhost:${serverPort}/videos") {
                accept("*/*")
                header("Authorization", token)
                header("Accept-Encoding", "gzip, deflate, br")
                header("Accept-Language", "pt-BR, pt, en-US, en")
                contentType("application/json")
                body(params as JSON)
            }

        then:
            resp.status == HttpStatus.CREATED.value()
    }

    @Unroll('validate the API Rest /videos POST with a Invalid Video')
    void "test apiAddVideo() invalidVideo"() {
        when:
            def params = [duration: 198.2, timestamp: System.currentTimeMillis()-60000]
            println "timestamp to Date -> ${new Date(params.timestamp).dateTimeString}"
            RestResponse resp = rest.post("http://localhost:${serverPort}/videos") {
                accept("*/*")
                header("Authorization", token)
                header("Accept-Encoding", "gzip, deflate, br")
                header("Accept-Language", "pt-BR, pt, en-US, en")
                contentType("application/json")
                body(params as JSON)
            }

        then:
            resp.status == HttpStatus.NO_CONTENT.value()
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