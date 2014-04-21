package data

import java.util.logging.Logger

class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader {

    private static final Logger log = Logger.getLogger(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader.class.name)
    //
    // Takes raw results as returned from SST JPL remote service
    //
    private String rawResult
    private String dataSet

    String getRawResult() { return rawResult }
    String getDataSet() { return dataSet }

    public SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(String rawResult){

        this.rawResult = rawResult

       // log.info "rawResult: $rawResult"
        
        this.dataSet = ""
        
        boolean isDataSet = true

        rawResult.split("\n").each { String line ->

           // log.info line

            if( isDataSet ) {
                if( line.startsWith("------") ) {
                    isDataSet = false
                } else {
                    //keep in data set
                    dataSet += "$line\n"
                }
            }
        }
    }
}
