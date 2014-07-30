package com.greekadonis.sst.catchup

class CatchupController {

  def catchupService

  def index() {
    render {
      if( catchupService.catchupRunning() ) {
        render 'Catchup is running: ' + catchupService.getProcessState()

      } else {
        render {
          a(href: './run', 'run...')
          br()
          // a( href: './tbd', 'TBD...' )
        }
      }
    }
  }

  def run() {
    render 'Started catchup: ' + catchupService.runCatchup()
  }
}