
get "/", forward: "/WEB-INF/pages/index.gtpl"

get "/datetime", forward: "/datetime.groovy"
get "/test/insert", forward: "/datastoreGroovlet.groovy"

get "/sstday/remote", forward: "/sstday.remote.get.groovy"

get "/sstday/@id?", forward: "/sstday.get.groovy"


get "/favicon.ico", redirect: "/images/gaelyk-small-favicon.png"
