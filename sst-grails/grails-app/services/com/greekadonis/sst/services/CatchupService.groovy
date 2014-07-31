package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.catchup.CatchupProcessState
import grails.transaction.Transactional
import org.apache.commons.lang3.time.StopWatch

@Transactional
class CatchupService {

  def dataLoaderService

  def runCatchup(){
    boolean isRunning = catchupRunning()
    log.debug("runCatchup() - isRunning: $isRunning")

    if( !isRunning ){
      //start one

      StopWatch timer = new StopWatch()
      timer.start()

//--> TODO: Don't hard-code upper limit!!

      SSTDay day

      for( i in 0..4000 ) {
        day = dataLoaderService.loadDay(i)
        if( !day ){
          log.debug "Day missing at index: ${i}, check for file"
          throw new RuntimeException("Unable to fetch data for day @ $i")
        }
      }

      log.info "runCatchup() - went thru days in ${timer.time}ms"

      return "Last loaded day: $day"
    }
  }

  boolean catchupRunning() {
    return getProcessState().running
  }

  CatchupProcessState getProcessState(){
    CatchupProcessState.findOrSaveWhere([id: 1L]) //only one instance
  }
}