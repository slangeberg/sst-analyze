package com.greekadonis.sst.catchup

class CatchupController {

  def catchupService

  def index() {
    render {
      if( catchupService.catchupRunning() ) {
        render 'Catchup is running: ' + catchupService.getProcessState()

      } else {
        render {
          p 'Current process state:'
          p catchupService.getProcessState()
          br()
          a(href: './run', 'Start catchup...')
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