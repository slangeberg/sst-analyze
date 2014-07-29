package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(CatchupService)
class CatchupServiceSpec extends Specification {


    void "test something"() {
      expect:
      0 == service.findFirstLoadedDay()
    }
}
