import org.apache.commons.lang3.time.StopWatch

/* 1 day of results - 6 lat elems, 11 lon elems

analysed_sst.analysed_sst[1][6][11]
[0][0], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768
[0][1], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768
[0][2], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768
[0][3], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768
[0][4], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768
[0][5], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768

analysed_sst.time[1]
"2006-04-01T00:00:00Z"

analysed_sst.lat[6]
-89.975, -89.925, -89.875, -89.825, -89.775, -89.725

analysed_sst.lon[11]
-179.975, -179.925, -179.875, -179.825, -179.775, -179.725, -179.675, -179.625, -179.575, -179.525, -179.475
 */
//[time][lat][lon]
final String analysed_sst = params.analysed_sst ?: "[0:1:0][0:1:5][0:1:10]"

///

Map<String, String> analysedSSTCache = session.analysedSSTCache ?: new HashMap<String, String>()

session.analysedSSTCache  = analysedSSTCache

String baseUrl = "http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii"
String analysedSSTUrl = "$baseUrl?analysed_sst"

///

StopWatch timer = new StopWatch()
timer.start()

String result = null

if( analysedSSTCache.containsKey(analysed_sst) ){
    log.info "Cache HIT for key: $analysed_sst, time: ${timer.getTime()}ms"
    result = analysedSSTCache.get(analysed_sst);

} else {
    log.info "Cache MISS for key: $analysed_sst"


    String url = "$analysedSSTUrl$analysed_sst"

    result = urlFetch.fetch(url.toURL()).text

    analysedSSTCache.put(analysed_sst, result);
}

// result = new JSON(result)
log.info("getAnalysedSSt() - analysed_sst: $analysed_sst, time: ${timer.getTime()}ms, Result: $result")

/////

html.html {
    body {
        p "analysedSST - time: ${timer.time}ms, result:"
        p "$result"
    }
}