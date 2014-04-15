package services

import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.logging.LoggerAccessor

import java.util.logging.Logger

//import org.apache.commons.lang.time.StopWatch

/**
 * http://thredds.jpl.nasa.gov/thredds/catalog.html
 * http://thredds.jpl.nasa.gov/thredds/podaac_catalogs/UKMO-L4HRfnd-GLOB-OSTIA_catalog.html
 * http://thredds.jpl.nasa.gov/thredds/podaac_catalogs/UKMO-L4HRfnd-GLOB-OSTIA_catalog.html?dataset=ALL_UKMO-L4HRfnd-GLOB-OSTIA
 * http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.html
 * http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii?analysed_sst[0:1:0][0:1:0][0:1:0]
 *
 * time: Array of Strings [time = 0..2918]
 *  lat: Array of 32 bit Reals [lat = 0..3599]
 *  lon: Array of 32 bit Reals [lon = 0..7199]
 */
@GaelykBindings
class UkmoL4HRfndGlobOSTIARemoteService {

    private static Logger log = Logger.getLogger(UkmoL4HRfndGlobOSTIARemoteService.class.name)

    Map<String, String> analysedSSTCache = new HashMap<String, String>()

    String baseUrl = "http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii"
    String analysedSSTUrl = "$baseUrl?analysed_sst"

    //[time][lat][lon]
    static final String defaultParams = "[0:1:0][0:1:5][0:1:10]"

    String loadAnalysedSST(String params) {

        params = params ?: defaultParams

//        StopWatch timer = new StopWatch()
//        timer.start()

        if( analysedSSTCache.containsKey(params) ){
            println "Cache HIT for key: $params, time: ${timer.getTime()}ms"
            return analysedSSTCache.get(params);
        }
        log.info "Cache MISS for key: $params"

        def result = "$analysedSSTUrl$params"
        result = result.toURL().getText()

        analysedSSTCache.put(params, result);

        // result = new JSON(result)
        log.info("getAnalysedSSt() - userParams: $params,  Results: $result")//time: ${timer.getTime()}ms,

        return result
    }

    String loadAnalysedSST() {
        return loadAnalysedSST(defaultParams)
    }

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
}
