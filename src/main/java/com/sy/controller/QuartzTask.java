package com.sy.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.sy.service.PublishService;
import com.sy.service.UserService;

/**
 * 定时任务，调用service中的方法
 * 参考文献：https://blog.csdn.net/qq_33556185/article/details/51852537
 * **/
//@Component("taskJob")
public class QuartzTask {
    
	@Autowired
	private UserService userService;
	
	@Autowired 
	private PublishService	publishService;

    /**
     * CRON表达式                含义 
    "0/20 * * * * ?"			20秒执行一次	      
    "0 0 12 * * ?"            每天中午十二点触发 
    "0 15 10 ? * *"            每天早上10：15触发 
    "0 15 10 * * ?"            每天早上10：15触发 
    "0 15 10 * * ? *"        每天早上10：15触发 
    "0 15 10 * * ? 2005"    2005年的每天早上10：15触发 
    "0 * 14 * * ?"            每天从下午2点开始到2点59分每分钟一次触发 
    "0 0/5 14 * * ?"        每天从下午2点开始到2：55分结束每5分钟一次触发 
    "0 0/5 14,18 * * ?"        每天的下午2点至2：55和6点至6点55分两个时间段内每5分钟一次触发 
    "0 0-5 14 * * ?"        每天14:00至14:05每分钟一次触发 
    "0 10,44 14 ? 3 WED"    三月的每周三的14：10和14：44触发 
    "0 15 10 ? * MON-FRI"   每个周一、周二、周三、周四、周五的10：15触发
     */
    
    /**
     * 每20秒执行一下查询
     */
    @Scheduled(cron = " 0/20 * * * * ?")
    public void testTask(){
        System.out.println(System.currentTimeMillis());
       System.out.println("20秒执行一次定时任务");
//       UserInfo cc=userService.checkUser("luojuwang", "123456");
//       System.out.println("email:"+cc.getEmail());
    }
    

}
