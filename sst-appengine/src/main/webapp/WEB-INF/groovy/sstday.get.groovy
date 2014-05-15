import data.SSTDay

///

log.info("params.analysed_sst: ${params.analysed_sst}")

List<SSTDay> days = []

if( params.analysed_sst ) {
    days = [SSTDay.get(params.analysed_sst)]
} else {
    days = SSTDay.findAll()
}

html.div {
    days.each { SSTDay day ->
        a href: "/sstday/${day.analysedSSTKey}", "${day.analysedSSTKey}"
    }
}