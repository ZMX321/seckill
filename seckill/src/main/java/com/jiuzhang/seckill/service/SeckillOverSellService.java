package com.jiuzhang.seckill.service;

import com.jiuzhang.seckill.db.dao.SeckillActivityDao;
import com.jiuzhang.seckill.db.po.SeckillActivity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeckillOverSellService {

    @Autowired
    private SeckillActivityDao seckillActivityDao;


    public String processSeckill(long activityId){
        SeckillActivity seckillActivity = seckillActivityDao.querySeckillActivityById(activityId);
        long availableStock = seckillActivity.getAvailableStock();
        String res;

        if(availableStock > 0){
            res = "You order has been placed";
            availableStock -= 1;
            seckillActivity.setAvailableStock(new Integer("" + availableStock));
            seckillActivityDao.updateSeckillActivity(seckillActivity);
            System.out.println(res);
        }else {
            res = "Sorry,out of stock";
            System.out.println(res);
        }

        return res;
    }

}
