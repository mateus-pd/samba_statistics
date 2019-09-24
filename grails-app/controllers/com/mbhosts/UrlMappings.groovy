package com.mbhosts

class UrlMappings {

    static mappings = {

        "/videos" {
            controller = "video"
            action = [POST: "apiAddVideo", DELETE: "apiDeleteVideos"]
        }

        "/"(controller: 'application', action:'index')
        "500"(view: '/error')
        "404"(view: '/notFound')
    }

}