package data

import java.util.logging.Logger

class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader {

    private static final Logger log = Logger.getLogger(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader.class.name)
    //
    // Takes raw results as returned from SST JPL remote service
    //
    String rawResult
    String dataset

    public SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(String rawResult){

        this.rawResult = rawResult

       // log.info "rawResult: $rawResult"
        
        this.dataset = ""
        
        boolean isDataSet = true

        rawResult.split("\n").each { String line ->

           // log.info line

            if( isDataSet ) {
                if( line.startsWith("------") ) {
                    isDataSet = false
                } else {
                    //keep in data set
                    dataset += "$line\n"
                }
            }
        }
    }

    public SSTDay getDay(){
        SSTDay day = new SSTDay()
        day.dataset = dataset
        return day
    }
}
