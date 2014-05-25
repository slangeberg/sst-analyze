package sst.grails



import spock.lang.*

/**
 *
 */
class AuthorIntSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "Can count authors"() {
        expect: Author.count() == 0
    }
}
