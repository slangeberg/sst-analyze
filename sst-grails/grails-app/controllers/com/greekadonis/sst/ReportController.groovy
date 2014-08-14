package com.greekadonis.sst

import org.apache.commons.lang3.time.StopWatch

class ReportController {

  def index() {
    render """
<h3>Reports</h3>
<p>
  <a href='./averages'>Averages</a>
</p>
"""
  }

  def averages() {

    StopWatch timer = new StopWatch()
    timer.start()

    Map<SSTDay, Double> dailyAverages = new LinkedHashMap<SSTDay, Double>()

    List<SSTDay> days = [
      createDay(0, 10),
      createDay(1, 20),
      createDay(2, 30),
      createDay(3, 40)
    ]
    days.each { SSTDay day ->
      int count = 0
      Double sum = 0
      day.latitudes.each { SSTDayLatitude lat ->
        lat.longitudes.each { SSTDayLongitude lon ->
          count++
          sum += lon.values[0].analysed_sst
        }
      }
      dailyAverages[day] = sum / count
    }

    String page = """
#days: ${days.size()}, #latitudes/day: ${days[0].latitudes.size()}, #longitudes/day: ${days[0].latitudes[0].longitudes.size()}, time: ${timer.time}ms<br/>
<br/>
<h3>avg daily temps:</h3> <br/>
<table>
  <hr>
    <td>SST Index</td>
    <td>Daily avg.</td>
  </hr>
"""
    dailyAverages.each{ SSTDay key, Double val ->
      page += "<tr><td>${key.sstIndex}</td><td>${val}</td></tr>"
    }
    page += "</table>"
    render page
  }

  protected SSTDay createDay(int index, int value) {
    List<SSTDayLatitude> latitudes = []
    (0..99).each {
      latitudes << createLatitude(it, value)
    }
    new SSTDay(sstIndex: index, latitudes: latitudes)
  }

  protected SSTDayLatitude createLatitude(int lat, int analysed_sst) {
    List<SSTDayLatitude> longitudes = []
    (0..99).each {
      longitudes << new SSTDayLongitude(lon: it,
        values: [
          new SSTDayLongitudeValue(analysed_sst: analysed_sst)
        ])
    }
    new SSTDayLatitude(lat: lat, longitudes: longitudes)
  }
}