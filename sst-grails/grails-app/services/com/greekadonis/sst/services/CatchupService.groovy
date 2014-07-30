package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.catchup.CatchupProcessState
import grails.transaction.Transactional

@Transactional
class CatchupService {

  def sstDayService

  def runCatchup(){
    boolean isRunning = catchupRunning()
    log.debug("runCatchup() - isRunning: $isRunning")

    if( !isRunning ){
      //start one
      //throw new RuntimeException("TBD")

      //-- determine index to start with, homey!!

      List<SSTDay> days = sstDayService.findAllOrderedBySSTIndex()
      return days
    }
  }

  boolean catchupRunning() {
    return getProcessState().running
  }

  CatchupProcessState getProcessState(){
    CatchupProcessState.findOrSaveWhere([id: 1L]) //only one instance
  }
}