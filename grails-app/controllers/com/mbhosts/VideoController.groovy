package com.mbhosts

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestBody

class VideoController {

    VideoService videoService

    static responseFormats = ['json']
	
    def apiAddVideo(@RequestBody Video video) {
        if (!video) {
            render(status: HttpStatus.NO_CONTENT.value())
            return
        }

        if (video.validateVideo())
            videoService.addVideo(video)
        else {
            render(status: HttpStatus.NO_CONTENT.value())
            return
        }

        render(status: HttpStatus.CREATED.value())
    }

    def apiDeleteVideos() {
        videoService.clearVideos()

        render(status: HttpStatus.NO_CONTENT.value())
    }

}