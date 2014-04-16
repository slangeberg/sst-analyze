import org.apache.commons.lang.time.StopWatch

class SstController {
    def hi() {
        render "hi"
    }
    def insert() {
        StopWatch timer = new StopWatch()
        timer.start()

        SstMurDay day = new SstMurDay(
            lat: [-48.1586, 39.7321]
            , lon: [Math.round(Math.random()*200), -136.049, -92.1039, -48.1586]
            , time: 1041411600 )
        day.temperatures = [
            [100, 90, 80, 105],
            [100, 90, 80, 105]
        ]
        day.save(flush: true, failOnError: true)

        render "Inserted Day: ${day}, Time: ${timer.getTime()}ms"
    }
    def results() {
        StopWatch timer = new StopWatch()
        timer.start()
        render """
            All days: ${SstMurDay.all}<br/>
            SstMurDay.last.time: ${SstMurDay.last()?.time} <br/>
            SstMurDay.last.lat: ${SstMurDay.last()?.lat} <br/>
            SstMurDay.last.lon: ${SstMurDay.last()?.lon} <br/>
            SstMurDay.last.temperatures.size(): ${SstMurDay.last()?.temperatures?.size()} <br/>
            SstMurDay.last.temperatures[0].size(): ${SstMurDay.last()?.temperatures[0]?.size()} <br/>
            Time: ${timer.getTime()}ms
        """
    }
    def last() {
        SstMurDay last = SstMurDay.last()
        render(contentType: "text/json") {
            analysed_sst(time: last.time, lat: last.lat)
        }
    }
}