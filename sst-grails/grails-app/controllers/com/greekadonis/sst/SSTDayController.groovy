package com.greekadonis.sst

import com.greekadonis.sst.services.DataReaderService
import grails.gorm.DetachedCriteria

class SSTDayController {
    static scaffold = true

    DataReaderService dataReaderService

    def day = {
        if( params.id ){
            render "id: ${params.id}, day: ${SSTDay.get(params.id)}"
        } else {
            render "days: <p>${SSTDay.findAll()}</p>"
        }
    }

    def data = {
//        def sst = dataReaderService.analysedSST
        def day = dataReaderService.day
        def value = day.toString()
            // sst.substring(sst.length()-1000, sst.length())

        log.info "data() - value: $value"

        render value
    }


    def test = {

        log.info "sstday.count: ${SSTDay.count()}, sstdaylat.count: ${SSTDayLatitude.count()}"

        render query.findAll()
    }
}
