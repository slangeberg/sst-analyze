package com.greekadonis.sst

import grails.gorm.DetachedCriteria

class SSTDayController {
    static scaffold = true

    //def dayService

    def day = {
        if( params.id ){
            render "id: ${params.id}, day: ${SSTDay.get(params.id)}"
        } else {
            render "days: <p>${SSTDay.findAll()}</p>"
        }
    }


    def test = {

        log.info "sstday.count: ${SSTDay.count()}, sstdaylat.count: ${SSTDayLatitude.count()}"


        render query.findAll()
    }
}
