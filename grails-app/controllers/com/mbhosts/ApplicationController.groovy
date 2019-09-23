package com.mbhosts

import grails.converters.JSON
import grails.core.GrailsApplication
import grails.plugins.*

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication
    GrailsPluginManager pluginManager

    def index() {
        render([noviews: ":)"] as JSON)
        //[grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}
