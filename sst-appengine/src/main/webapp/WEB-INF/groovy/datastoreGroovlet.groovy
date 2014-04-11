import com.google.appengine.api.datastore.*

Entity e = new Entity("person")
e.firstname = 'Marco'
e.lastname = 'Vermeulen'
e.save()

log.info  "e.key: ${e.key}"

request.setAttribute 'test.insert.result', e.key.id