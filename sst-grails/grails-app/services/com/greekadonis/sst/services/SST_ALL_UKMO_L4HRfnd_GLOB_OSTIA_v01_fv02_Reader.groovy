package com.greekadonis.sst.services

//import com.google.appengine.labs.repackaged.org.json.JSONArray
//import com.google.appengine.labs.repackaged.org.json.JSONObject
import com.greekadonis.sst.SSTDay
import grails.compiler.GrailsCompileStatic
import org.codehaus.groovy.grails.web.json.JSONArray

import java.util.logging.Logger
/*
* See: http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.html
* DDS:
Dataset {
    Float32 lat[lat = 3600];
    Float32 lon[lon = 7200];
    Grid {
     ARRAY:
        Int16 analysed_sst[time = 2951][lat = 3600][lon = 7200];
     MAPS:
        String time[time = 2951];
        Float32 lat[lat = 3600];
        Float32 lon[lon = 7200];
    } analysed_sst;
    Grid {
     ARRAY:
        Int16 analysis_error[time = 2951][lat = 3600][lon = 7200];
     MAPS:
        String time[time = 2951];
        Float32 lat[lat = 3600];
        Float32 lon[lon = 7200];
    } analysis_error;
    Grid {
     ARRAY:
        Byte sea_ice_fraction[time = 2951][lat = 3600][lon = 7200];
     MAPS:
        String time[time = 2951];
        Float32 lat[lat = 3600];
        Float32 lon[lon = 7200];
    } sea_ice_fraction;
    Grid {
     ARRAY:
        Byte mask[time = 2951][lat = 3600][lon = 7200];
     MAPS:
        String time[time = 2951];
        Float32 lat[lat = 3600];
        Float32 lon[lon = 7200];
    } mask;
    String time[time = 2951];
} sea_surface_temperature%2fALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02%2enc;
 */

@GrailsCompileStatic
class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader {

    private static final Logger log = Logger.getLogger(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader.class.name)

    // See: DDS
    public static final int MAX_TIME = 2951 - 1;
    public static final int MAX_LAT = 3600 - 1;
    public static final int MAX_LON = 7200 - 1;

    //
    // Takes raw results as returned from SST JPL remote service
    //
    String rawResult
    String dataset

    String analysedSst

/* @param rawResult
- Example - src:
http://thredds.jpl.nasa.gov/thredds/dodsC/sea_surface_temperature/ALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02.nc.ascii?analysed_sst[0:1:0][0:1:1][0:1:1]
--------------------------------------------------

Dataset {
    Grid {
     ARRAY:
        Int16 analysed_sst[time = 1][lat = 2][lon = 2];
     MAPS:
        String time[time = 1];
        Float32 lat[lat = 2];
        Float32 lon[lon = 2];
    } analysed_sst;
} sea_surface_temperature%2fALL_UKMO-L4HRfnd-GLOB-OSTIA_v01-fv02%2enc;
---------------------------------------------
analysed_sst.analysed_sst[1][2][2]
[0][0], -32768, -32768
[0][1], -32768, -32768

analysed_sst.time[1]
"2006-04-01T00:00:00Z"

analysed_sst.lat[2]
-89.975, -89.925

analysed_sst.lon[2]
-179.975, -179.925
 */
    public SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(String rawResult){

        this.rawResult = rawResult

       // log.info "rawResult: $rawResult"
        
        this.dataset = ""
        this.analysedSst = ""

     //   List<List<Integer>> sstVals = new ArrayList<List<Integer>>()
        JSONArray sstVals = new JSONArray()

        boolean isDataSet = true
        boolean isAnalysedSst = false
        boolean isTime = false

        rawResult.eachLine { String line ->

           // log.info line

            //Where?
            if( isDataSet && line.startsWith("------") ) {
                isDataSet = false

            } else if ( line.startsWith("analysed_sst.analysed_sst") ){
                isDataSet = false
                isAnalysedSst = true
                return; //skipToNext

            } else if( isAnalysedSst && line.equals("") ) {
                isAnalysedSst = false
                isTime = true
                return; //skip..
            }

            //What?
            if( isDataSet ) {
                //keep in data set
                dataset += "$line\n"

            } else if ( isAnalysedSst ){
                isAnalysedSst = true

                line = line.replaceAll(" ", "")
                List split = line.split(",") as List
                split.remove(0) //should be coordinates, like: [lat][lon]

                JSONArray lon = new JSONArray()
                split.each {
                    lon.put(Integer.valueOf((it as String).trim()))
                }
                sstVals.put(lon)
            }
        }
        analysedSst = sstVals.toString()  //json encode
   }

    public SSTDay getDay(String analysedSSTKey){
        SSTDay day = new SSTDay() //analysedSSTKey, this)
        return day
    }
}
