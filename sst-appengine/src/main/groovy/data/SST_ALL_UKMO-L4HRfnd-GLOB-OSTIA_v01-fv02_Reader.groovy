package data

import com.google.appengine.labs.repackaged.com.google.common.collect.Lists

import java.util.logging.Logger

class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader {

    private static final Logger log = Logger.getLogger(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader.class.name)
    //
    // Takes raw results as returned from SST JPL remote service
    //
    String rawResult
    String dataset

    String analysedSstValue
    List<List<Integer>> analysedSst

/* Example - src:
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
        this.analysedSstValue = ""
        this.analysedSst = new ArrayList<List<Integer>>()

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
                analysedSstValue += "$line\n"
                List split = line.split(/,/)
                split.remove(0) //should be coordinates, like: [lat][lon]
                List<Integer> lon = new ArrayList<Integer>()
                split.each {
                    lon.add(Integer.valueOf(it.trim()))
                }
                analysedSst.add(lon)
            }
        }
    }

    public SSTDay getDay(){
        SSTDay day = new SSTDay(this)
        return day
    }
}
