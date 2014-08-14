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
      new SSTDay(sstIndex: 0,
        latitudes: [
          createLatitude(0, 10),
          createLatitude(1, 10)
      ]),
      new SSTDay(sstIndex: 1,
        latitudes: [
          createLatitude(0, 20),
          createLatitude(1, 20)
      ])
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
#days: ${days.size()}, time: ${timer.time}ms<br/>
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

  protected SSTDayLatitude createLatitude(int lat, int analysed_sst) {
    new SSTDayLatitude(lat: lat,
      longitudes: [
        new SSTDayLongitude(lon: 0,
          values: [
            new SSTDayLongitudeValue(analysed_sst: analysed_sst)
          ]),
        new SSTDayLongitude(lon: 1,
          values: [
            new SSTDayLongitudeValue(analysed_sst: analysed_sst)
          ])
      ])
  }
}