import com.google.appengine.api.urlfetch.HTTPResponse
import data.SSTDay

import data.SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader

import org.apache.commons.lang3.time.StopWatch

////////////////////////

//[time][lat][lon]
final String analysed_sst = params.analysed_sst ?: "[0:1:0][0:1:5][0:1:10]"

String baseUrl = "http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii"
String analysedSSTUrl = "$baseUrl?analysed_sst"
String url = "$analysedSSTUrl$analysed_sst"

log.info("url: $url")

///

StopWatch timer = new StopWatch()
timer.start()

SSTDay day = SSTDay.get(analysed_sst)
//assert SSTDay.count() == 1
//assert day         == SSTDay.get(analysed_sst)/
//assert SSTDay.findAll { where analysedSSTKey == analysed_sst } == 1
if( day ){
    log.info "Datastore HIT for key: $analysed_sst, time: ${timer.time}ms"

} else {
    log.info "Datastore MISS for key: $analysed_sst"

    HTTPResponse response = url.toURL().get(deadline: 30) //30sec.
    assert response.statusCode == 200

    SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader =
        new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(response.text)
    day = reader.getDay(analysed_sst)
    day.save()
}

log.info("analysed_sst: $analysed_sst, time: ${timer.getTime()}ms")

/////

html.html {
    body {
        p "analysed_sst: ${analysed_sst} - time: ${timer.time}ms"

        h3 "day.dataset:"
        p "${day.dataset}"
        h3 "day.dateTime: "
        p "${day.dateTime}"
        h3 "day.lat: "
        p "${day.lat}"
        h3 "day.lon: "
        p "${day.lon}"
        h3 "day.analysed_sst: "
        p "${day.analysedSst}"
    }
}