package sst.grails

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.SSTDayLatitude
import com.greekadonis.sst.SSTDayLongitude
import spock.lang.*

/**
 *
 */
class SSTDaySpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Can query..."() {

        def longitudes = [
            new SSTDayLongitude()
        ]
        new SSTDay(
            time: new Date(2006, 04, 03), //"yyyy-MM-dd'T'HH:mm:ss.SSSZ"
            latitudes: [
                new SSTDayLatitude(lat: 20.5, longitudes: longitudes)
            ])
            .save(flush: true)

        expect:
        SSTDayLatitude found = SSTDayLatitude.where {
            day.time > new Date(2006, 04, 03)
            //, "2006-04-02T00:00:00Z", "2006-04-03T00:00:00Z", "2006-04-04T00:00:00Z", "2006-04-05T00:00:00Z")
        }.find()

        SSTDay.get(1) == found
    }
}
