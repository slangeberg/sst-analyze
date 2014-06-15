package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.data.SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader
import grails.transaction.Transactional
import org.apache.commons.lang.time.StopWatch
import org.springframework.transaction.annotation.Propagation

@Transactional(propagation = Propagation.SUPPORTS)
class DataReaderService {

    String getAnalysedSST() {
        getReader().analysedSst
    }

    SSTDay getDay() {
        getDay(false)
    }
    SSTDay getDay(boolean remote) {
        getReader().day
    }

    private SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader getReader() {
        File file = getFile()

        StopWatch timer = new StopWatch()
        timer.start()

        String contents = file.getText()

        println "getReader() - file.text - time: ${timer.time}ms"

        SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader =
            new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(contents)

        println "getReader() - new Reader() - time: ${timer.time}ms"

        reader
    }

    private File getFile() {
        StopWatch timer = new StopWatch()
        timer.start()

        String base = "${System.getProperty("user.dir")}/data" // "C:\\data\\lci\\sst\\data"
        String name = "ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii_analysed_sst[0.1.0][0.10.3599][0.10.7199].txt"
                //"/Users/scott/Dropbox/Projects/com.greekadonis/sst-analyze/sst-grails/test/unit/data/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii_analysed_sst[0.1.0][0.10.3599][0.10.7199].txt"//"ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii_analysed_sst[0.1.0][0.10.3599][0.10.7199].txt"
        //ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii_analysed_sst[0.1.0][0.2.3599][0.2.7199].txt

        File file = new File("$base/$name")

        println "getFile(): $file, time: ${timer.time}ms"

        file
    }
}
