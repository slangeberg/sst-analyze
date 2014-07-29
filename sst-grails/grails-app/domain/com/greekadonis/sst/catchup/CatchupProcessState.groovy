package com.greekadonis.sst.catchup

class CatchupProcessState {

  static constraints = {
  }

  boolean running
  Integer sstIndex
  Integer lastCompletedSSTIndex
}
