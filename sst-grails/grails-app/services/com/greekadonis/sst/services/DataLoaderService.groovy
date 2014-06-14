package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.data.SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader
import grails.transaction.Transactional
import org.apache.commons.lang3.time.StopWatch
import org.springframework.transaction.annotation.Propagation

import javax.servlet.ServletResponse


//import com.google.appengine.api.urlfetch.HTTPResponse
//import data.SSTDay
//
//import data.SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader
//
//import org.apache.commons.lang3.time.StopWatch

@Transactional(propagation = Propagation.SUPPORTS)
class DataLoaderService {


////////////////////////

    SSTDay loadDay(){
        loadDay("[0:1:0][0:1:5][0:1:10]")
    }

    SSTDay loadDay(String analysed_sst) {
        //[time][lat][lon]
        //final String analysed_sst = params.analysed_sst ?:

        //--> TODO: Move URL to Reader as well
        String baseUrl = "http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii"
        String analysedSSTUrl = "$baseUrl?analysed_sst"
        String url = "$analysedSSTUrl$analysed_sst"

        log.info("url: $url")

        ///

        StopWatch timer = new StopWatch()
        timer.start()

        SSTDay day // = SSTDay.get(analysed_sst)
//        //assert SSTDay.count() == 1
//        //assert day         == SSTDay.get(analysed_sst)/
//        //assert SSTDay.findAll { where analysedSSTKey == analysed_sst } == 1
//        if (day) {
//            log.info "Datastore HIT for key: $analysed_sst, query time: ${timer.time}ms"
//
//        } else {
//            log.info "Datastore MISS for key: $analysed_sst"

           // ServletResponse response = null
           // try {

                //--> TODO - look at:
                //    maximum deadline (task queue and cron job handler)	10 minutes

                //response = url.toURL().getContent()//get(deadline: 60) //deadline: sec.

            //List<String> lines = url.toURL().readLines()
            String content = url.toURL().text

//            } catch (SocketTimeoutException ste) {
//                throw new RuntimeException("Remote fetch for analysed_sst: $analysed_sst timed out after ${timer.time}ms", ste)
//            }
//            if (response.statusCode != 200) {
//                throw new RuntimeException("Remote fetch for analysed_sst: $analysed_sst failed with message: ${response.text}")
//            }

            log.info("analysed_sst: $analysed_sst, response time: ${timer.getTime()}ms")

            SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader =
                    new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(content) //response.text)
            day = reader.getDay() //analysed_sst)
//        }

        log.info("analysed_sst: $analysed_sst, total time: ${timer.getTime()}ms")

        day
    }
}
