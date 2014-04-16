import com.google.appengine.api.datastore.*

///



int count = datastore.execute { select count from Person }

log.info "datastoreGroovlet() - count: $count"

Entity e = null

if( count <= 20 ) {
    e = new Entity("Person")
    e.firstname = 'Marco'
    e.lastname = "Vermeulen_$count"
    e.save()

    log.info("Count is low, DID create Person, ln: ${e.lastname}")
    log.info  "e.key: ${e.key}"

} else {
    log.info("Count is high, will NOT create Person")
}

/////////////////////////////////////////////////////////////

html.html {
    body {
        if( e != null )   p("inserted: e.key: ${e.key}")

        List entities = datastore.execute {
            select all from Person
            limit 20
        }

        h4 "you have: #${entities.size()} records"
        entities.each { Entity person ->
            String msg = ""
            person.getProperties().each { k, v ->
                msg += "$k: ${person.getProperty(k)} | "
            }
            p msg
        }
    }
}