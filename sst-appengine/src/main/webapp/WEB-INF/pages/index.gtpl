<html>
    <head>
        <title>Home</title>

        <script src="/js/jquery-1.11.0.js"></script>
    </head>

    <body>
        <div class="jumbotron center">
            <a href="http://gaelyk.appspot.com"><img alt="Gaelyk Logo" src="/images/gaelyk.png"/></a>
            <br/>
            <p>
                Congratulations, you've just created your first
                <a href="http://gaelyk.appspot.com">Gaelyk</a> application.
            </p>

            <p>
                <a href="/datetime" class="btn btn-primary btn-large">Show current time &raquo;</a>
            </p>
        </div>
        <div class="row">
            <div class="col-md-4">
                <h2>Start Experimenting</h2>
                <p>This template contains following sample files<ul><li><code>datetime.groovy</code></li><li><code>WEB-INF/pages/datetime.gtpl</code></li></ul>Try to edit them and watch the changes.</p>
            </div>

                <script>
                    jQuery.getJSON("/sstday/image?analysed_sst=[0:1:0][0:100:3599][0:100:7199]",
                        function(data) {
                            console.log(data)
                        });

                </script>
        </div>
    </body>
</html>

