package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.catchup.CatchupProcessState
import grails.transaction.Transactional

@Transactional
class CatchupService {

  def sstDayService

  def runCatchup(){
    if( !catchupRunning() ){
      //start one
      log.info("runCatchup() - Catchup is not running. Next step: TBD!!")
      throw new RuntimeException("TBD")

      //-- determine index to start with, homey!!
    }
  }

  boolean catchupRunning() {
    return getProcessState().running
  }

  CatchupProcessState getProcessState(){
    CatchupProcessState.findOrSaveWhere([id: 1L]) //only one instance
  }
}