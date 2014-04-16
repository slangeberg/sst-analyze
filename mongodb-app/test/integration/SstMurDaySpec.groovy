import spock.lang.*

/**
 *
 */
class SstMurDaySpec extends Specification {

    def setup() {
    }

    def cleanup() {
//--> TODO: Tie into any Mongo transaction support?
        SstMurDay.deleteAll(SstMurDay.all)
    }

    void "Can save lon field"() {
        when:
        SstMurDay day = new SstMurDay()
        day.time = 123
        day.lon = [1, 2, 3, 4, 5, 6]
        day.save(flush: true, failOnError: true)

        then:
        SstMurDay.count > 0
    }

    void "Can save temperatures"() {
        when:
        SstMurDay day = new SstMurDay()
        day.time = 123
        day.lat = [0, 1]
        day.lon = [0, 1, 2, 3]
        day.temperatures = [
            [100, 90, 80, 105],
            [100, 90, 80, 105]
        ]
        day.save(flush: true, failOnError: true)

        then:
        SstMurDay.count > 0
        SstMurDay.last().temperatures.get(0).get(0) == 100
    }
}
