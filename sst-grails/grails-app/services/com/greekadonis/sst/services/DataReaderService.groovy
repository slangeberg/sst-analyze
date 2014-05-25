package com.greekadonis.sst.services

import grails.transaction.Transactional
import org.apache.commons.lang.time.StopWatch
import org.springframework.transaction.annotation.Propagation

@Transactional(propagation = Propagation.SUPPORTS)
class DataReaderService {

    File getFile() {
        StopWatch timer = new StopWatch()
        timer.start()

        String base = "C:\\data\\lci\\sst\\data"
        String name = "ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii_analysed_sst[0.1.0][0.10.3599][0.10.7199].txt"
            //ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii_analysed_sst[0.1.0][0.2.3599][0.2.7199].txt

        File file = new File("$base\\$name")

        println "getFile(): $file, time: ${timer.time}ms"

        file
    }

    String getAnalysedSST() {
        File file = getFile()

        StopWatch timer = new StopWatch()
        timer.start()

        String contents = file.getText()

        println "getAnalysedSST() - file.text - time: ${timer.time}ms"

        SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader =
            new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(contents)

        println "getAnalysedSST() - new Reader() - time: ${timer.time}ms"

        String sst = reader.analysedSst

        println "getAnalysedSST() - time: ${timer.time}ms, sst: ${sst.substring(sst.length()-1000, sst.length())}"

        sst
    }
}
