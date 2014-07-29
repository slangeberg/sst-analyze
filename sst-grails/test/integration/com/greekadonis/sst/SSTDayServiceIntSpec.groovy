package com.greekadonis.sst

import com.greekadonis.sst.services.SSTDayService
import grails.test.mixin.TestFor
import org.joda.time.LocalDate
import spock.lang.Specification

@TestFor(SSTDayService)
class SSTDayServiceIntSpec extends Specification {

    void "First loaded day is null, if no days populated"() {

        SSTDay first = service.findFirstLoadedDay()

        expect:
        !first
    }

    void "Can find first loaded day"() {

        [ new SSTDay(sstIndex: 0, time:LocalDate.now().toDate()),
          new SSTDay(sstIndex: 1, time:LocalDate.now().plusDays(1).toDate())
        ]*.save(flush: true, failOnError: true)

        SSTDay first = service.findFirstLoadedDay()

        expect:
        first.sstIndex == 0
    }
}
