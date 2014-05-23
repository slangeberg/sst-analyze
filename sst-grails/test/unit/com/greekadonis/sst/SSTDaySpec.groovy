package com.greekadonis.sst

import grails.test.mixin.TestFor
import spock.lang.Specification

import java.text.DateFormat

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(SSTDay)
class SSTDaySpec extends Specification {

//    def setup() {
//    }
//
//    def cleanup() {
//    }

    void "Can persist day"() {
        new SSTDay().save()
        expect:
        SSTDay.count() == 1
    }

//--> NOw do real query to fetch latitudes between x and y
//    --> then get latitudes between x and y for days between a and b ??

    void "Can persist day with latitude entries"() {

        List<SSTDayLatitude> latitudes = [
            new SSTDayLatitude(),
            new SSTDayLatitude(),
            new SSTDayLatitude(),
            new SSTDayLatitude(),
            new SSTDayLatitude(),
            new SSTDayLatitude(),
            new SSTDayLatitude()
        ]

        SSTDay day = new SSTDay(latitudes: latitudes)
        day.save()

        expect:
        SSTDay.get(1).latitudes.size() == latitudes.size()
    }

    void "Can persist day with latitude and longitude entries"() {

        def longitudes = [
            new SSTDayLongitude()
        ]
        SSTDay day = new SSTDay(latitudes: [
            new SSTDayLatitude(longitudes: longitudes)
        ])
        day.save()

        expect:
        SSTDay.get(1).latitudes[0].longitudes.size() == longitudes.size()
    }


}
