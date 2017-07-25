package com.aissure.packet.packet.job;

/**
 * Created by Administrator on 2017/7/14.
 */

public class JobFactory implements IJobFactory{
    @Override
    public BaseAccessbilityJob createWeiXinJob() {


        return new WeChatJob();
    }
}
