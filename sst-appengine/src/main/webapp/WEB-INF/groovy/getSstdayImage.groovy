import com.google.appengine.api.blobstore.BlobKey
import com.google.appengine.api.blobstore.BlobstoreServiceFactory
import data.SSTDay
import org.apache.commons.lang3.time.StopWatch

final String analysed_sst = params.analysed_sst ?: "[0:1:0][0:1:5][0:1:10]"

//BlobstoreServiceFactory.getBlobstoreService()
//BlobKey blob = analysed_sst as BlobKey
//blobstore.getUploads request

SSTDay day = SSTDay.get(analysed_sst)

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

List<String> lines = new ArrayList<String>();
//int imgSize = values.getLength();
List<Integer> pixels = []; //[imgSize];
//int index = 0;

//for (int lat = 0; lat < shape[1]; lat++) {
for( List<Short> lon : day.analysedSst ) {
    String line = "";
    for( short val : lon ) {
        //short val = values.getValue(index);
        if (val == EMPTY_VAL) {
            line += ",";
            pixels = appendHexAsRGBtoList(0x914545, pixels);
        } else {
            // double celsius = val * SCALE_FACTOR;

            line += val + ",";

            pixels = appendHexAsRGBtoList(getColor(TEMP_THRESHOLDS, COLOR_BANDS, val), pixels);
        }
        //index++;
    }
    lines.add(line);
}

//////////////////////////////////////////////

println "${pixels.toString()}"

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