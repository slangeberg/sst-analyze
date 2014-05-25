package com.greekadonis.sst.services

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.services.ServiceUnitTestMixin} for usage instructions
 */
@TestFor(DataReaderService)
class DataReaderServiceSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Can get file"() {
        expect: new DataReaderService().getFile() != null
    }

    void "Can get SST from file"(){
        def sst = new DataReaderService().getAnalysedSST()

        log.info "sst: $sst"

        expect:
        sst != null
    }
}
