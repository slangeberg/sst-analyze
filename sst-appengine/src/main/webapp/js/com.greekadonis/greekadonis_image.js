window.greekadonis_image = window.greekadonis_image || new function(){

    var id = "sstImageCanvas";
    var canvas = document.getElementById(id);
    if( !canvas ){
        throw "Canvas '" + id + "' not found in page"
    }
    var ctx = canvas.getContext("2d");
    ctx.fillRect(0,0,300,150);

    this.getSstImage = function( sst ) {
        sst = sst ? sst : "[0:1:0][1000:10:3400][0:10:7199]";

        jQuery.getJSON("/sstday/image?analysed_sst=" + sst)
            .done(function(data) {
                console.log("getSstImage() - data:", data);
                update(data);
            })
            .fail(function(error){
                console.log("getSstImage() - error: ", error);
            });
    }

    function update(imgInfo) {

    //        jQuery(myCanvas).css("width", imgInfo.width);
    //        jQuery(myCanvas).css("height", imgInfo.height);

        var data = imgInfo.data;

        var imageData = ctx.getImageData(0,0,imgInfo.width,imgInfo.height);

        var index = 0;

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

        jQuery(".content-loading").hide();

        var dataURL = canvas.toDataURL();

        console.log("dataURL: ", dataURL);

//        jQuery.ajax({
//            type: "POST",
//            url: "script.php",
//            data: {
//                imgBase64: dataURL
//            }
//        }).done(function(o) {
//            console.log('saved');
//            // If you want the file to be visible in the browser
//            // - please modify the callback in javascript. All you
//            // need is to return the url to the file, you just saved
//            // and than put the image in your browser.
//        });
    }
}();
