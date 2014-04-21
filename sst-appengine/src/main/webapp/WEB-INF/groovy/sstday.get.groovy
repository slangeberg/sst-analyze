List<List<Integer>> latitude = [[1,2,3]]

Integer id = (params.id ?: null) as Integer

log.info("id: $id")

def value = id ? latitude[0][id] : latitude[0]

html.html {
    body {
        p "You've got latitude: $value"
    }
}