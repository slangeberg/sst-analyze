package com.greekadonis.sst.catchup

class SstCatchupProcessController {

  static scaffold = true

  def sstCatchupService

  def report() {
    render {
      if( sstCatchupService.catchupRunning() ) {
        render 'Catchup is running: ' + sstCatchupService.getAllRunning()

      } else {
        render {
          p 'None are running'
         // p catchupService.getProcessState()
          br()
          a(href: './run', 'Start catchup...')
          br()
          // a( href: './tbd', 'TBD...' )
        }
      }
    }
  }

  def run() {
    render 'Started catchup: ' + sstCatchupService.runCatchup()
  }
}