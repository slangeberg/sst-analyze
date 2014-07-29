package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import grails.transaction.Transactional

@Transactional
class SSTDayService {

  SSTDay findFirstLoadedDay() {
    SSTDay.where {
      sstIndex == min(sstIndex)
    }
    .find()
  }
}