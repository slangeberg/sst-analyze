package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.catchup.CatchupProcessState
import grails.transaction.Transactional
import org.apache.commons.lang3.time.StopWatch

@Transactional
class CatchupService {

  def sstDayService

  def runCatchup(){
    boolean isRunning = catchupRunning()
    log.debug("runCatchup() - isRunning: $isRunning")

    if( !isRunning ){
      //start one

      StopWatch timer = new StopWatch()
      timer.start()


//--> TODO: Don't hard-code upper limit!!

      for( i in 0..4000 ) {
        SSTDay day = sstDayService.findBySstIndex(i)
        if( !day ){
          log.debug "Day missing at index: ${i}, check for file"

        }
      }

      log.info "runCatchup() - went thru days in ${timer.time}ms"

      return "...."
    }
  }

  boolean catchupRunning() {
    return getProcessState().running
  }

  CatchupProcessState getProcessState(){
    CatchupProcessState.findOrSaveWhere([id: 1L]) //only one instance
  }
}