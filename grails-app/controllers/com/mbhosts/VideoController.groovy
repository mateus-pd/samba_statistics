package com.mbhosts

import org.springframework.web.bind.annotation.RequestBody

class VideoController {

    VideoService videoService

    static responseFormats = ['json']
	
    def apiAddVideo(@RequestBody Video video) {
        if (!video) {
            render(status: 204)
            return
        }

        if (video.validateVideo())
            videoService.addVideo(video)
        else {
            render(status: 204)
            return
        }

        render(status: 201)
    }

    def apiDeleteVideos() {
        videoService.clearVideos()

        render(status: 204)
    }

}