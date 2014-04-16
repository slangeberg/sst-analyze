package data

import java.util.logging.Logger

class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader {

    private static final Logger log = Logger.getLogger(SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader.class.name)
    //
    // Takes raw results as returned from SST JPL remote service
    //
    private String rawResult

    String getRawResult() { return rawResult }

    public SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(String rawResult){

        this.rawResult = rawResult

        log.info "rawResult: $rawResult"
    }
}
