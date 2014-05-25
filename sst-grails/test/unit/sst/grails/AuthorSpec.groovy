package sst.grails

import grails.test.mixin.TestFor
import grails.test.mixin.TestMixin
import spock.lang.Specification

import grails.test.mixin.hibernate.*

@TestMixin(HibernateTestMixin)
class AuthorSpec extends Specification {

    def setupSpec() {
        hibernateDomain([Author])
    }

    def setup() {
    }

    def cleanup() {
    }

    void "Can count"() {
        expect:
        Author.count() == 0
    }
}
