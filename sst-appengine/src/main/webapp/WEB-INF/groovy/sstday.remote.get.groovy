import com.google.appengine.api.urlfetch.HTTPResponse
import data.SSTDay
import data.SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader
import org.apache.commons.lang3.time.StopWatch

////////////////////////

//[time][lat][lon]
final String analysed_sst = params.analysed_sst ?: "[0:1:0][0:1:5][0:1:10]"

Map<String, String> analysedSSTCache = session.analysedSSTCache ?: new HashMap<String, String>()

session.analysedSSTCache  = analysedSSTCache

String baseUrl = "http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii"
String analysedSSTUrl = "$baseUrl?analysed_sst"
String url = "$analysedSSTUrl$analysed_sst"

log.info("url: $url")

///

StopWatch timer = new StopWatch()
timer.start()

String result = null

if( analysedSSTCache.containsKey(analysed_sst) ){
    log.info "Cache HIT for key: $analysed_sst, time: ${timer.getTime()}ms"
    result = analysedSSTCache.get(analysed_sst);

} else {
    log.info "Cache MISS for key: $analysed_sst"

    HTTPResponse response = url.toURL().get(deadline: 30) //30sec.

    assert response.statusCode == 200

    result = response.text

    analysedSSTCache.put(analysed_sst, result);
}

SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(result)
SSTDay sstDay = reader.getDay()

// result = new JSON(result)
log.info("getAnalysedSSt() - analysed_sst: $analysed_sst, time: ${timer.getTime()}ms")

/////

html.html {
    body {
        p "analysed_sst: ${analysed_sst} - time: ${timer.time}ms, result:"
        h3 "SSTDay:"
        p "${sstDay}"
        h3 "dataSet:"
        p "${reader.dataSet}"
        h3 "rawResult: "
        p "$result"
    }
}