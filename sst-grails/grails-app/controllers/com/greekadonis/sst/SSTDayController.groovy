package com.greekadonis.sst

import grails.gorm.DetachedCriteria

class SSTDayController {
    static scaffold = true

    def day = {
        if( params.id ){
            render "id: ${params.id}, day: ${SSTDay.get(params.id)}"
        } else {
            render "days: <p>${SSTDay.findAll()}</p>"
        }
    }

    //
    def test = {
        if( SSTDayLatitude.count() > 0 )
            SSTDayLatitude.where{ id != null }.deleteAll()
        if( SSTDay.count() > 0 )
            SSTDay.where{ id != null }.deleteAll()

        if( SSTDayLatitude.count() == 0 ){
//            def longitudes = [
//                new SSTDayLongitude()
//            ]
            SSTDay day = new SSTDay( time: new Date(2006, 04, 03) )
                .save(flush: true, failOnError: true)

            def latitude = new SSTDayLatitude(
                day: day, lat: 20.5/*, longitudes: longitudes*/)
                    .save(flush: true, failOnError: true)
//            day.latitudes = [
//                latitude
//            ]
        }

        def query = SSTDayLatitude.where {
            lat > 30
        }

        render query.find()
    }
}
