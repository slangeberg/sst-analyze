/*
Dataset: 20140101-JPL-L4UHfnd-GLOB-v01-fv04-MUR.nc
analysed_sst.lon, -179.995, -136.049, -92.1039, -48.1586, -4.21326, 39.7321, 83.6774, 127.623, 171.568
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=-89.9945], -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768, -32768
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=-68.0219], -26494, -26390, -25952, -26800, -26799, -26800, -32768, -32768, -26779
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=-46.0492], -11068, -14050, -13574, -13242, -18899, -18066, -16932, -14184, -11621
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=-24.0765], 890, 2054, -3707, -32768, -2360, 2659, 668, -32768, 26
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=-2.10388], 4142, 375, -1164, -32768, 420, -32768, 3359, 5040, 3958
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=19.8688], 1161, -1993, 349, 642, -32768, 2603, -32768, -868, 2152
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=41.8414], -13911, -10699, -32768, -14683, -32768, -12878, -32768, -32768, -12759
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=63.8141], -26779, -32768, -32768, -32768, -21087, -32768, -32768, -24705, -32768
analysed_sst.analysed_sst[analysed_sst.time=1041411600][analysed_sst.lat=85.7867], -26800, -26800, -26800, -26800, -26800, -26800, -26800, -26800, -26800
 */

class SstMurDay {

    Integer time;
    List<Integer> lat;
    List<Integer> lon;
    //List<List<Integer>> - grails bug breaks on nested List type: http://jira.grails.org/browse/GPMONGODB-267
    List<List> temperatures;  //2D array: [lat][lon] = temp
}
