package sst.grails

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.SSTDayLatitude
import com.greekadonis.sst.SSTDayLongitude
import grails.test.mixin.TestMixin
import spock.lang.*

class SSTDayIntSpec extends Specification {

//    def setup() {
//    }
//
//    def cleanup() {
//    }
    void "Can query latitude by day"() {

        SSTDay day = new SSTDay(time: new Date(2006, 04, 03))
            .addToLatitudes(new SSTDayLatitude(lat: 20.5))
            .save(flush: true, failOnError: true)

        SSTDayLatitude found = SSTDayLatitude.where {
            day.time > new Date(2006, 04, 03)
            //, "2006-04-02T00:00:00Z", "2006-04-03T00:00:00Z", "2006-04-04T00:00:00Z", "2006-04-05T00:00:00Z")
        }.find()

        expect:
        day.time == found.day.time
    }

    void "Can query latitudes"() {

        SSTDay day = new SSTDay(time: new Date(2006, 04, 03))
            .addToLatitudes(new SSTDayLatitude(lat: 0.5))
            .addToLatitudes(new SSTDayLatitude(lat: 10.0))
            .addToLatitudes(new SSTDayLatitude(lat: 15.0))
            .addToLatitudes(new SSTDayLatitude(lat: 25.0))
            .save(flush: true, failOnError: true)

        def query = SSTDayLatitude.where {
            lat > 5 && lat < 20
        }

        expect:
        Set all = query.findAll()
        all.size() == 2
        all[0].lat == 10
        all[1].lat == 15
    }
}
