import com.google.appengine.api.blobstore.BlobKey
import com.google.appengine.api.blobstore.BlobstoreServiceFactory
import com.google.appengine.labs.repackaged.org.json.JSONObject
import data.SSTDay
import org.apache.commons.lang3.time.StopWatch
import services.LogService

import java.nio.ByteBuffer

//////////////////////////////////////////

final String analysed_sst = params.analysed_sst ?: "[0:1:0][0:1:5][0:1:10]"

StopWatch timer = new StopWatch()
timer.start()

new LogService(this).test()

//BlobstoreServiceFactory.getBlobstoreService()
//BlobKey blob = analysed_sst as BlobKey
//blobstore.getUploads request


SSTDay day = SSTDay.get(analysed_sst)
log.info "found day: $day"

String analKey = "${analysed_sst}.analysed_sst"
String analPixelsKey = "${analKey}.pixels"
List<Integer> pixels = memcache.get(analPixelsKey)
List<List<Integer>> analysedSst = memcache.get(analKey)
if( !analysedSst ) {
    log.info("memcache.analysedSst.get is MISS")
    analysedSst = SSTDay.get(analysed_sst)?.analysedSst;
    if( analysedSst ){
        log.info("memcache.analysedSst.get in ${timer.time}ms")
        memcache.put(analKey, analysedSst)
    } else {
        throw new RuntimeException("No image data found for sst: $analysed_sst")
    }
}
if( !pixels ) {
    pixels = buildPixels(analysedSst)
    memcache.put(analPixelsKey, pixels)
}

//////////////////////////////////////////////

JSONObject result = new JSONObject([width: analysedSst[0].size(), height: analysedSst.size(), data: pixels])

log.info("built result in ${timer.time}ms")

println result.toString()

///////////////////////////////////////////////

List<Integer> buildPixels(List<List<Integer>> analysedSst){

    StopWatch timer = new StopWatch()
    timer.start()

    // These attributes populated in DAS, and probably should be read from there!
    double SCALE_FACTOR = 0.01D;
    short VALID_MIN = -300;
    short VALID_MAX = 4500;
    List<Integer> COLOR_BANDS = [  //low to high
        0x00fff3, //cyan
        0x5691fe, //blue
        0x03ff00, //green
        0xefff00, //yellow
        0xff0020 //red
    ]

    List<Short> TEMP_THRESHOLDS = [];
//private static final SystemProperties systemProperties = new SystemProperties();

// Static init

//low to high
    double span = VALID_MAX - VALID_MIN;
    double step = span / COLOR_BANDS.size();

//TEMP_THRESHOLDS = []; // new short[COLOR_BANDS.length];
    int i = 0;
    for ( int band : COLOR_BANDS ) {
        TEMP_THRESHOLDS << (short) Math.round(VALID_MIN + step * (i++));
    }
    int EMPTY_VAL = -32768;

    List<Integer> pixels = []
//List<String> lines = new ArrayList<String>();
    for( List<Short> lat : analysedSst ) {
        String line = "";
        for( short val : lat ) {
            //short val = values.getValue(index);
            if (EMPTY_VAL.equals(val)) {
                //  line += ",";
                pixels = appendHexAsRGBtoList(0x914545, pixels);
            } else {
                // double celsius = val * SCALE_FACTOR;

                //line += val + ",";

                pixels = appendHexAsRGBtoList(getColor(TEMP_THRESHOLDS, COLOR_BANDS, val), pixels);
            }
            //index++;
        }
        //  lines.add(line);
    }
    log.info("buildPixels() - ${analysedSst.size()}x${analysedSst[0].size()} pixels in ${timer.time}ms")
    return pixels;
}

List<Integer> appendHexAsRGBtoList(int hex, List<Integer> list) {
    int r = (hex & 0xFF0000) >> 16;
    int g = (hex & 0xFF00) >> 8;
    int b = (hex & 0xFF);
    int a = 255;

    list << r;
    list << g;
    list << b;
    list << a;

    return list
}
int getColor(List<Short> thresholds, List<Integer> colors, short sstValue) {
    int color = 0x000000;
    for (int j = thresholds.size() - 1; j >= 0; j--) {
//    thresholds.each { short threshold ->
        //loop high to low
        if( sstValue >= thresholds[j] ) {
            color = colors[j];
            return color;
        }
    }
    return color;
}