<html>
    <head><title>Current Time</title></head>
    <body>
        <div class="jumbotron">
        <h1>Current Date</h1>

        <p>
            <%
                log.info "outputing the datetime attribute"
            %>
            The current date and time: <%= request.getAttribute('datetime') %> <br/>
            Test.insert.result: <%= request.getAttribute('test.insert.result') %>
        </p>
        </div>
    </body>
</html>
