package com.greekadonis.sst.data

import com.greekadonis.sst.SSTDay
import org.apache.log4j.Logger
import org.codehaus.groovy.grails.web.json.JSONArray
import org.joda.time.LocalDate
import org.joda.time.format.ISODateTimeFormat

//@GrailsCompileStatic
class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader {

   private static final Logger log = Logger.getLogger(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader.class.name)

   LocalDate time

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

      log.info "SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader()"

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

         } else if( line.startsWith("analysed_sst.time") ) {
            println "isTime: TRUE"
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

         } else if( isTime ){
            println "isTime - line: $line"
            if( line.startsWith("\"") && line.size() > 2 ){
               time = ISODateTimeFormat.localDateParser().parseLocalDate(line.replace("\"", "").split("T")[0])
            }
         }
      }
      analysedSst = sstVals.toString()  //json encode
   }

   public SSTDay getDay(){
      new SSTDay(time: time)
   }
}
