package data

import spock.lang.Specification

class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_ReaderSpec extends Specification {

    def "I store the raw results"() {
        given: "Reader inited with raw string result"
            SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader("HI");

        when: "the datastore is queried for data"
            String value = reader.getRawResult();

        then: "raw result matches"
            value == "HI"
    }

}

