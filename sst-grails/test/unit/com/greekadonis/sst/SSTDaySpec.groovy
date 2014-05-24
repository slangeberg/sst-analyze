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
        query.findAll().size() == 1
    }
}