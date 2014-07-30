package com.greekadonis.sst.services

import com.greekadonis.sst.SSTDay
import grails.transaction.Transactional

@Transactional
class SstDayService {

  List<SSTDay> findAllOrderedBySSTIndex() {
    SSTDay.list(order: 'asc', sort: 'sstIndex')
  }

  SSTDay findFirstLoadedDay() {
    SSTDay.where {
      sstIndex == min(sstIndex)
    }
    .find()
  }
}