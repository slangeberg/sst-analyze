package com.greekadonis.sst.catchup

class CatchupProcessState {

  static constraints = {
    lastCompletedSSTIndex nullable: true
    sstIndex nullable: true
  }

  boolean running
  Integer sstIndex
  Integer lastCompletedSSTIndex

  @Override
  public String toString() {
    "[CatchupProcessState - sstIndex: $sstIndex, lastCompletedSSTIndex: $lastCompletedSSTIndex, running: $running]"
  }
}
