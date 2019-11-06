package com.ca.mq.rocketmq.dt.service;

import com.ca.mq.rocketmq.dt.dao.PointDao;
import com.ca.mq.rocketmq.dt.exception.BusinessException;
import com.ca.mq.rocketmq.dt.model.Point;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import javax.annotation.Resource;

@Service
public class PointService {

    @Resource
    private PointDao dao;

    @Transactional(rollbackFor = Exception.class)
    public String savePoint(Point point) {
        if ((point != null) && (point.getUserId() != null)) {
            Point queryPoint = dao.getByUserId(point.getUserId());
            if (queryPoint != null) {
                return queryPoint.getId();
            } else {
                return dao.insert(point);
            }
        } else {
            throw new BusinessException("入参不能为空！");
        }
    }

}
