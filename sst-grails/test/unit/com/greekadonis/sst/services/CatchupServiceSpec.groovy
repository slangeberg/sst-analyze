package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.catchup.CatchupProcessState
import grails.test.mixin.Mock

//import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CatchupService)
@Mock(CatchupProcessState)
class CatchupServiceSpec extends Specification {

  def setup() {
  }

  def cleanup() {
  }

//  void "Can run catchup"() {
//    def myService = Mock(SSTDayService){
//      1 * findFirstLoadedDay() >> new SSTDay()
//    }
//    service.sstDayService = myService
//
//    service.runCatchup()
//
//    expect:
//    service.catchupRunning()
//  }
}
