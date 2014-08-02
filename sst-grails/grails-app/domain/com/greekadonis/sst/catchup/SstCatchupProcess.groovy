package com.greekadonis.sst.catchup

import org.joda.time.LocalDate
import org.joda.time.LocalDateTime

class SstCatchupProcess {

  static scaffold = true

  static constraints = {
//    sstIndex nullable: true
//    analysed_sst nullable: true
    endDate nullable: true
  }

  Integer sstIndex
  boolean running
  String analysed_sst

  LocalDateTime startDate
  LocalDateTime endDate

  @Override
  public String toString() {
    "[SstCatchupProcess - sstIndex: $sstIndex" \
      + ", running: $running, startDate: $startDate, endDate: $endDate]"
  }
}
