package com.greekadonis.sst

class SSTDayLatitude {

    static hasMany = [longitudes: SSTDayLongitude]

//    static belongsTo = [day:SSTDayValue]

    Double lat
}
