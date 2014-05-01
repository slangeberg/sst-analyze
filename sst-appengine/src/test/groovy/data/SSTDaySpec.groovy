package data

import spock.lang.Specification

class SSTDaySpec extends Specification {

    SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = Mock()
    SSTDay day = new SSTDay("[0][0][0]", reader)

    def "GetAnalysedSst"() {
        day.analysedSstValue = "[[1,2,3], [4,5,6]]"

        expect:
        day.analysedSst[0] == [1,2,3]
        day.analysedSst[1] == [4,5,6]
    }
}
