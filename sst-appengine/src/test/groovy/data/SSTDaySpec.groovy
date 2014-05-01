package data

import spock.lang.Specification

class SSTDaySpec extends Specification {

    SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader = Mock()
    SSTDay day = new SSTDay("[0][0][0]", reader)

    def "GetAnalysedSst"() {
        expect: day.analysedSst.size() > 0
    }
}
