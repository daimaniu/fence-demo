package cn.daimaniu.util;

import cn.daimaniu.pojo.Position;

import java.util.List;

/**
 * Author :chenqisheng
 * Date   :2016-06-16 13:22.
 */
public class DistanceUtil {
    /**
     * 计算地球上任意两点(经纬度)距离
     *
     * @param long1 第一点经度
     * @param lat1  第一点纬度
     * @param long2 第二点经度
     * @param lat2  第二点纬度
     * @return 返回距离 单位：米
     */
    public static double distance(double long1, double lat1, double long2, double lat2) {
        double a, b, R;
        R = 6378137; // 地球半径
        lat1 = lat1 * Math.PI / 180.0;
        lat2 = lat2 * Math.PI / 180.0;
        a = lat1 - lat2;
        b = (long1 - long2) * Math.PI / 180.0;
        double d;
        double sa2, sb2;
        sa2 = Math.sin(a / 2.0);
        sb2 = Math.sin(b / 2.0);
        d = 2 * R * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1) * Math.cos(lat2) * sb2 * sb2));
        return d;
    }

    /**
     * 判断是否 在 圆形范围内
     *
     * @param lng
     * @param lat
     * @param centerLng
     * @param centerLat
     * @param radius
     * @return
     */
    public static boolean pointInCircle(double lng, double lat, double centerLng, double centerLat, int radius) {
        Double radius1 = distance(lng, lat, centerLng, centerLat);
        return radius1 <= radius;
    }

    /**
     * 判断 是否在 多边形内
     *
     * @param lng
     * @param lat
     * @param paths
     * @return
     * @see {link http://alienryderflex.com/polygon/}
     */
    public static boolean pointerInPolygon(double lng, double lat, List<Position> paths) {
        int polyCorners = paths.size();
        int i, j = paths.size() - 1;
        boolean oddNodes = false;

        double[] polyX = new double[paths.size()];
        double[] polyY = new double[paths.size()];

        for (int k = 0; k < paths.size(); k++) {
            polyX[k] = paths.get(k).getLng();
            polyY[k] = paths.get(k).getLat();
        }
        for (i = 0; i < polyCorners; i++) {
            if ((polyY[i] < lat && polyY[j] >= lat
                    || polyY[j] < lat && polyY[i] >= lat)
                    && (polyX[i] <= lng || polyX[j] <= lng)) {
                oddNodes ^= (polyX[i] + (lat - polyY[i]) / (polyY[j] - polyY[i]) * (polyX[j] - polyX[i]) < lng);
            }
            j = i;
        }

        return oddNodes;
    }
}
