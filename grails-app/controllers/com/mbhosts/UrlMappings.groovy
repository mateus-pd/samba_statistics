package com.mbhosts

class UrlMappings {

    static mappings = {

        "/videos" {
            controller = "video"
            action = [POST: "apiAddVideo", DELETE: "apiDeleteVideos"]
        }

        "/statistics" {
            controller = "statistics"
            action = [GET: "apiStatistics"]
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }

}