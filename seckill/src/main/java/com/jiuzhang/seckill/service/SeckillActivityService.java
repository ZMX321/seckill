package com.jiuzhang.seckill.service;

import com.alibaba.fastjson.JSON;
import com.jiuzhang.seckill.db.dao.OrderDao;
import com.jiuzhang.seckill.db.dao.SeckillActivityDao;
import com.jiuzhang.seckill.db.po.Order;
import com.jiuzhang.seckill.db.po.SeckillActivity;
import com.jiuzhang.seckill.mq.RocketMQService;
import com.jiuzhang.seckill.util.SnowFlake;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class SeckillActivityService {

    @Resource
    private OrderDao orderDao;

    @Resource
    private RedisService service;

    @Resource
    private SeckillActivityDao seckillActivityDao;

    @Resource
    private RocketMQService rocketMQService;

    private SnowFlake snowFlake = new SnowFlake(1,1);

    public boolean seckillStockValidator(long activityId){
        String key = "stock:" + activityId;
        return service.stockDeductValidation(key);
    }

    public Order createOrder(long seckillActivityId, long userId) throws Exception{
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(seckillActivityId);
        Order order = new Order();

        order.setOrderNo(String.valueOf(snowFlake.nextId()));
        order.setSeckillActivityId(seckillActivityId);
        order.setUserId(userId);
        order.setOrderAmount(seckillActivity.getSeckillPrice().longValue());

        rocketMQService.sendMessage("seckill_order", JSON.toJSONString(order));

        return order;

    }

    public void payOrderProcess(String orderNo) {
        Order order = orderDao.queryOrder(orderNo);
        boolean deductStockResult = seckillActivityDao.deductStock(order.getSeckillActivityId());
        if(deductStockResult){
            order.setPayTime(new Date());
            order.setOrderStatus(2);
            orderDao.updateOrder(order);
        }
    }
}
