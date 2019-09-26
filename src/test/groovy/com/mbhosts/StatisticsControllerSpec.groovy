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
class StatisticsControllerSpec extends Specification implements ControllerUnitTest<StatisticsController> {

    @Value('${local.server.port}')
    Integer serverPort
    @Shared
    def rest = new RestBuilder(connectTimeout: 1000, readTimeout: 10000)
    @Shared
    def url = "http://localhost:${serverPort}/statistics"
    @Shared
    def urlVideos = "http://localhost:${serverPort}/videos"
    @Shared // User: sambatech Pass: sambatech
    def token = "Basic c2FtYmF0ZWNoOnNhbWJhdGVjaA=="

    @Unroll('validate the API Rest /statistics GET')
    void "test apiStatistics()"() {
        given:
            def params = []
            params.add([duration: 100.2, timestamp: System.currentTimeMillis()+60000])
            params.add([duration: 231.3, timestamp: System.currentTimeMillis()+65000])
            params.add([duration: 200.6, timestamp: System.currentTimeMillis()+70000])
            params.add([duration: 134.1, timestamp: System.currentTimeMillis()+75000])
            params.add([duration: 300.7, timestamp: System.currentTimeMillis()+80000])
            params.add([duration: 351.9, timestamp: System.currentTimeMillis()+85000])
            params.add([duration: 400.4, timestamp: System.currentTimeMillis()+90000])


        when:
            // Delete All Videos
            println("/videos DELETE")
            rest.delete(urlVideos) {
                header("Authorization", token)
            }
            // Add Videos
            params.each { map ->
                println "/videos POST: timestamp to Date -> ${new Date(map.timestamp).dateTimeString}"
                rest.post(urlVideos) {
                    accept("*/*")
                    header("Authorization", token)
                    header("Accept-Encoding", "gzip, deflate, br")
                    header("Accept-Language", "pt-BR, pt, en-US, en")
                    contentType("application/json")
                    body(map as JSON)
                }
            }
            // Get Statistics
            println("/statistics GET")
            RestResponse resp = rest.get(url) {
                header("Authorization", token)
            }

        then:
            resp.status == 201
            resp.json.max == 400.4
            resp.json.min == 100.2
            resp.json.sum == 1719.2
            resp.json.avg == 245.6
            resp.json.count == 7
    }

}