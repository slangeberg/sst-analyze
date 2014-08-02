package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import com.greekadonis.sst.catchup.CatchupProcessState
import com.greekadonis.sst.catchup.SstCatchupProcess
import grails.transaction.Transactional
import org.apache.commons.lang3.time.StopWatch
import org.joda.time.LocalDateTime

@Transactional
class SstCatchupService {

  def dataLoaderService

  def runCatchup(){
    boolean isRunning = catchupRunning()
    log.debug("runCatchup() - isRunning: $isRunning")

    //CatchupProcessState catchupProcessState = getProcessState()

    if( isRunning ){
      def running = getAllRunning()
      return "Catchup is already running ${running.size()} processes: <br/>${running}"

    } else {
      //start one

      StopWatch timer = new StopWatch()
      timer.start()

      SstCatchupProcess process = null

      SSTDay day = null

//--> TODO: Don't hard-code upper limit!!
      for( i in 0..3000 ) {
        day = runCatchupForDay(i)
        if( !day ){
          //let's stop trying, for now ;)
          break
        }
      }
//      catchupProcessState.running = false
//      catchupProcessState.save(failOnError: true, flush: true)

      log.info "runCatchup() - went thru days in ${timer.time}ms"

      return "Last loaded day: $day"
    }
  }

  SSTDay runCatchupForDay( int sstIndex ) {

    log.info "runCatchupForDay($sstIndex)"

    SSTDay day = null

    if( catchupRunning() ){
      log.warn "runCatchupForDay($sstIndex) requested, but process is already running"

    } else {
      SstCatchupProcess process = new SstCatchupProcess(
          sstIndex: sstIndex, running: true, analysed_sst: dataLoaderService.getAnalysedSstParams(sstIndex),
          startDate: LocalDateTime.now())
        .save(flush: true, failOnError: true)

//      catchupProcessState.running = true
//      catchupProcessState.sstIndex = sstIndex
    //  catchupProcessState.save(failOnError: true, flush: true)

      day = dataLoaderService.loadDay(sstIndex) //@NOTE: Potentially long-running
      if (day) {
//        catchupProcessState.sstIndex = null
//        catchupProcessState.lastCompletedSSTIndex = sstIndex
//        catchupProcessState.running = false
        process.running = false
        process.save(failOnError: true, flush: true)

      } else {
        log.debug "Day missing at index: ${sstIndex}, check for file"
        throw new RuntimeException("Unable to fetch data for day @ $sstIndex")
      }

//throw new RuntimeException("create row for each process - may get two threads running")
//track: start / end date/time; sstIndex; can get?: analysed_sst param, file name?

//      catchupProcessState.save(failOnError: true, flush: true)
    }
    day
  }

  boolean catchupRunning() {
    //return getProcessState().running
    List running = getAllRunning()
    log.info("catchupRunning(): running: $running")
    !running.empty
  }

  List<SstCatchupProcess> getAllRunning() {
    SstCatchupProcess.findAll(new SstCatchupProcess(running: true))
  }
//
//  CatchupProcessState getProcessState(){
//    CatchupProcessState.findOrSaveWhere([id: 1L]) //only one instance
//  }
}