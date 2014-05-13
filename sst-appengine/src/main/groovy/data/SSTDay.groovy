package data

import com.google.appengine.labs.repackaged.org.json.JSONArray
import com.google.appengine.repackaged.org.joda.time.LocalDateTime
import groovyx.gaelyk.GaelykBindings
import groovyx.gaelyk.datastore.Entity
import groovyx.gaelyk.datastore.Key
import groovyx.gaelyk.datastore.Unindexed
import groovyx.gaelyk.datastore.Ignore
import org.apache.commons.lang3.time.StopWatch

import java.util.logging.Logger

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
//@GaelykBindings //<-- Psshht... used for plugins! Instantiate your bean/service in binding {}
@Entity(unindexed=false)
class SSTDay {//implements Serializable {

//    static final long serialVersionUID = 1L;

//    Logger log = Logger.getLogger(this.class.name)

    @Key String analysedSSTKey //[time][lat][lon]

    String dataset
    LocalDateTime dateTime
    List<Double> lat
    List<Double> lon

    @Unindexed String analysedSstValue

    @Ignore List<List<Integer>> analysedSst // [lat][lon]

    public SSTDay() {}

    public SSTDay(String analysedSSTKey, SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader){
        log = Logger.getLogger(getClass().name)

        this.analysedSSTKey = analysedSSTKey
        this.dataset = reader.dataset
        this.analysedSstValue = reader.analysedSst
    }

    //Lazy init
    List<List<Integer>> getAnalysedSst(){
        StopWatch timer = new StopWatch()
        timer.start()

        if( !analysedSst ){
            analysedSst = new ArrayList<List<Integer>>()
            JSONArray sstArr = new JSONArray(analysedSstValue)
            for (int i = 0; i < sstArr.length(); i++) {
                List<Integer> lon = new ArrayList<Integer>()
                JSONArray lonArr = sstArr.getJSONArray(i)
                for (int j = 0; j < lonArr.length(); j++) {
                    lon.add(lonArr.getInt(j))
                }
                analysedSst.add(lon)
            }
            // sst service populates world upside down
            Collections.reverse(analysedSst)
        }

//        log.info "getAnalysedSst() - time: ${timer.time}ms"
        return analysedSst
    }

    @Override
    public String toString(){
        return "[SSTDay - analysedSSTKey: $analysedSSTKey, dateTime: $dateTime]"
    }
}
