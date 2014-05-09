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

String analKey = "${analysed_sst}.analysd_sst"
List<List<Integer>> analysedSst = memcache.get(analKey)

log.info("memcache.analysedSst.get in ${timer.time}ms")

if( !analysedSst ) {
    analysedSst = SSTDay.get(analysed_sst)?.analysedSst
}
if( analysedSst ){
    memcache.put(analKey, analysedSst)
} else {
    throw new RuntimeException("No image data found for sst: $analysed_sst")
}

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

//List<String> lines = new ArrayList<String>();
List<Integer> pixels = [];

for( List<Short> lat : analysedSst ) {
    String line = "";
    for( short val : lat ) {
        //short val = values.getValue(index);
        if (val == EMPTY_VAL) {
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

//////////////////////////////////////////////

byte[] pixel_array = [0xFF, 0, 0, 0xFF, 0, 0, 0xFF, 0, 0, 0xa, 0, 0, 0xFF, 0, 0]
ByteBuffer buffer = ByteBuffer.allocate(255)
0..254.each {
    buffer.putInt(0xFF0000)
}
byte[] bytes = buffer.array();
for (byte b : bytes) {
  // sout.format("0x%x ", b);
}
String result = Base64Encoder.encode(pixel_array)// new JSONObject(.toString())//  new JSONObject([width: day.analysedSst[0].size(), height: day.analysedSst.size(), data: pixels])

println result

///////////////////////////////////////////////

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