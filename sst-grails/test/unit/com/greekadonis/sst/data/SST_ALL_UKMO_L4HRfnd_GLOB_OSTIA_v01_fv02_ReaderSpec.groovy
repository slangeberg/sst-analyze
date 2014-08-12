package com.greekadonis.sst.data

import com.greekadonis.sst.SSTDay
import org.joda.time.LocalDate
import org.joda.time.format.ISODateTimeFormat
import spock.lang.Specification

class SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_ReaderSpec extends Specification {

   def setup() {

   }

   def cleanup() {
   }

   def "Reads dataset"(){

      when:
      SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader =
         new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(createResult());

      then:
      reader.dataset.startsWith "Dataset {"
      !reader.dataset.contains("------")
   }

   def "Creates SSTDay"(){

      SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader reader =
         new SST_ALL_UKMO_L4HRfnd_GLOB_OSTIA_v01_fv02_Reader(createResult());
      SSTDay day = reader.day
      LocalDate time = ISODateTimeFormat.localDateParser().parseLocalDate("2006-04-02")

      expect:
      day.time == time
      day.latitudes.size() == 5
      day.latitudes[0].longitudes.size() == 5
   }


   String createResult() {
      new SstDataHelper().createResult()
   }
}