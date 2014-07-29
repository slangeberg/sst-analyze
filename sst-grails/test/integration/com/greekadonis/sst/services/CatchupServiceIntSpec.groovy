package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import grails.test.mixin.TestFor
import org.joda.time.LocalDate
import spock.lang.Specification

@TestFor(CatchupService)
class CatchupServiceIntSpec extends Specification {

  void "Can run catchup"() {
    def myService = Mock(SSTDayService){
      1 * findFirstLoadedDay() >> new SSTDay()
    }
    service.sstDayService = myService

    service.runCatchup()

    expect:
    service.catchupRunning()
  }
}