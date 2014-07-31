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


}
