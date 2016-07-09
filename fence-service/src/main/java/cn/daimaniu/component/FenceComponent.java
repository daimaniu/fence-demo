package cn.daimaniu.component;

import cn.daimaniu.dao.FenceMapper;
import cn.daimaniu.model.Fence;
import cn.daimaniu.pojo.Position;
import cn.daimaniu.type.FenceType;
import cn.daimaniu.util.DistanceUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Author :chenqisheng
 * Date   :2016-07-09 19:31.
 */
@Component
public class FenceComponent {
    @Autowired
    FenceMapper fenceMapper;

    public boolean checkInFence(double lng, double lat) {
        List<Fence> fences = fenceMapper.selectAll();
        if (fences == null || fences.size() == 0) {
            //如果无 电子围栏限制 则返回正确
            return false;
        } else {
            for (Fence fence : fences) {
                if (fence.getType() == FenceType.CIRCLE.getValue()) {
                    if (fence.getCircle() != null && !fence.getCircle().trim().equals("")) {
                        String[] circleParams = fence.getCircle().split(",");
                        boolean isInCircle = DistanceUtil.pointInCircle(lng, lat, Double.parseDouble(circleParams[0])
                                , Double.parseDouble(circleParams[1]), Integer.parseInt(circleParams[2]));
                        if (isInCircle) return true;
                    } else {
                        //如果 无围栏 则跳过
                        continue;
                    }
                } else {
                    if (fence.getPolygon() != null && !fence.getPolygon().trim().equals("")) {
                        String[] polygonParams = fence.getPolygon().split(",");
                        List<Position> positions = new ArrayList<>();
                        for (int i = 0; i < polygonParams.length; i = i + 2) {
                            Position position = new Position();
                            position.setLng(Double.parseDouble(polygonParams[i]));
                            position.setLat(Double.parseDouble(polygonParams[i + 1]));
                            positions.add(position);
                        }
                        boolean isInPolygon = DistanceUtil.pointerInPolygon(lng, lat, positions);
                        if (isInPolygon) return true;
                    } else {
                        //如果 无围栏 则跳过
                        continue;
                    }
                }
            }
        }
        return false;
    }
}
