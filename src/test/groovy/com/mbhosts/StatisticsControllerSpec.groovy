package com.mbhosts

import grails.converters.JSON
import grails.plugins.rest.client.RestBuilder
import grails.plugins.rest.client.RestResponse
import grails.testing.mixin.integration.Integration
import grails.testing.web.controllers.ControllerUnitTest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

import java.time.Instant

@Integration
class StatisticsControllerSpec extends Specification implements ControllerUnitTest<StatisticsController> {

    @Value('${local.server.port}')
    Integer serverPort
    @Shared
    def rest = new RestBuilder(connectTimeout: 1000, readTimeout: 10000)
    @Shared // User: sambatech Pass: sambatech
    def token = "Basic c2FtYmF0ZWNoOnNhbWJhdGVjaA=="
    @Autowired
    VideoService videoService

    @Unroll('validate the API Rest /statistics GET')
    void "test apiStatistics()"() {
        when:
            def params = []
            params.add(new Video(duration: 100.2, timestamp: Instant.now().plusSeconds(3).toEpochMilli()))
            params.add(new Video(duration: 231.3, timestamp: Instant.now().plusSeconds(4).toEpochMilli()))
            params.add(new Video(duration: 200.6, timestamp: Instant.now().plusSeconds(5).toEpochMilli()))
            params.add(new Video(duration: 134.1, timestamp: Instant.now().plusSeconds(6).toEpochMilli()))
            params.add(new Video(duration: 300.7, timestamp: Instant.now().plusSeconds(7).toEpochMilli()))
            params.add(new Video(duration: 351.9, timestamp: Instant.now().plusSeconds(8).toEpochMilli()))
            params.add(new Video(duration: 400.4, timestamp: Instant.now().plusSeconds(9).toEpochMilli()))

            // Delete All Videos
            videoService.clearVideos()
            // Add Videos
            params.each { video -> videoService.addVideo(video) }
            // Get Statistics
            println("/statistics GET")
            RestResponse resp = rest.get("http://localhost:${serverPort}/statistics") {
                header("Authorization", token)
                contentType("application/json")
            }

        then:
            resp.status == HttpStatus.OK.value()

        and:
            resp.json.max == 400.4
            resp.json.min == 100.2
            resp.json.sum == 1719.2
            resp.json.avg == 245.6
            resp.json.count == 7
    }

}