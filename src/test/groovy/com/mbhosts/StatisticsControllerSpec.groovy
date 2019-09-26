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
            params.add(new Video(duration: 100.2, timestamp: System.currentTimeMillis()+3000))
            params.add(new Video(duration: 231.3, timestamp: System.currentTimeMillis()+4000))
            params.add(new Video(duration: 200.6, timestamp: System.currentTimeMillis()+5000))
            params.add(new Video(duration: 134.1, timestamp: System.currentTimeMillis()+6000))
            params.add(new Video(duration: 300.7, timestamp: System.currentTimeMillis()+7000))
            params.add(new Video(duration: 351.9, timestamp: System.currentTimeMillis()+8000))
            params.add(new Video(duration: 400.4, timestamp: System.currentTimeMillis()+9000))

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