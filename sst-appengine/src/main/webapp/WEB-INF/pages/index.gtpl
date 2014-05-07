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
                <h2>Today's weather:</h2>
                <p>Well, maybe 'today'.</p>
                <canvas id="myCanvas">Your browser does not support the HTML5 canvas tag.</canvas>
            </div>
        </div>
    </body>
</html>

<script>
    var sst = "[0:1:0][1000:10:3400][0:10:7199]"

    jQuery.getJSON("/sstday/image?analysed_sst=" + sst,
            function(data) {
                console.log(data)
                update(data)
            });

    var c=document.getElementById("myCanvas");
    var ctx=c.getContext("2d");
    // ctx.fillStyle="red";
    ctx.fillRect(0,0,300,200);

    function update(imgInfo) {

        jQuery(myCanvas).css("width", imgInfo.width);
        jQuery(myCanvas).css("height", imgInfo.height);

        var data = imgInfo.data

        var imageData = ctx.getImageData(0,0,imgInfo.width,imgInfo.height);

        var index = 0

        for (y = 0; y < imgInfo.height; y++) {
            outpos = y * imgInfo.width * 4;// *4 for 4 ints per pixel
            for (x = 0; x < imgInfo.width; x++) {
                imageData.data[outpos++] = data[index++]; // r;
                imageData.data[outpos++] = data[index++]; // g;
                imageData.data[outpos++] = data[index++]; // b;
                imageData.data[outpos++] = data[index++]; // a;
            }
        }

        //console.log(imageData)
        ctx.putImageData(imageData,0,0);
    }
</script>