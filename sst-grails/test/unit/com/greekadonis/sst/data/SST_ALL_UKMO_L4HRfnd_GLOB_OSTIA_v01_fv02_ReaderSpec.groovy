package com.greekadonis.sst.data

import com.greekadonis.sst.data.SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader

//import grails.test.mixin.TestFor
import spock.lang.Specification

//@TestFor(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader)
class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_ReaderSpec extends Specification {


    def "Reads dataset"(){
        given: "have result string"
        String result = createResult()

        when:
        SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(result);

        then:
        reader.dataset.startsWith "Dataset {"
        !reader.dataset.contains("------")
    }
//
//    def "Populates analysed_sst string"(){
//        given: "have result string"
//        String result = createResult()
//        when:
//        SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(result);
//
//        then:
//        reader.analysedSst.startsWith "[[0,-32768,-32768,-32768,-32768],"
//        reader.analysedSst.endsWith ",[2,-32768,-32768,-32768,-32768]]"
//    }
//
//    def "Populates SSTDay"(){
//        String result = createResult()
//        SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(result);
//
//        expect:
//        reader.day != null
//    }

    String createResult() {
        """Dataset {
    Grid {
     ARRAY:
        Int16 analysed_sst[time = 1][lat = 3][lon = 5];
     MAPS:
        String time[time = 1];
        Float32 lat[lat = 3];
        Float32 lon[lon = 5];
    } analysed_sst;
} sea_surface_temperature%2fALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02%2enc;
---------------------------------------------
analysed_sst.analysed_sst[1][3][5]
[0][0], 0, -32768, -32768, -32768, -32768
[0][1], 1, -32768, -32768, -32768, -32768
[0][2], 2, -32768, -32768, -32768, -32768

analysed_sst.time[1]
"2006-04-02T00:00:00Z"

analysed_sst.lat[3]
-89.975, -89.925, -89.875

analysed_sst.lon[5]
-179.975, -179.925, -179.875, -179.825, -179.775
"""
    }
}

