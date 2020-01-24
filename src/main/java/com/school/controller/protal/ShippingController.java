package com.school.controller.protal;

import com.github.pagehelper.PageInfo;
import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.pojo.Shipping;
import com.school.pojo.User;
import com.school.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/***
 * 收货地址
 */
@RequestMapping("/shipping/")
@Controller
@ResponseBody
public class ShippingController {

    @Autowired
    private ShippingService shippingService;

    /**
     * 新增收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping("add")
    public ServerResponse add(HttpSession session, Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.add(user.getId(),shipping);
    }


    /**
     * 删除收货地址
     * @param session
     * @param shippingId
     * @return
     */
    @RequestMapping("del")
    public ServerResponse del(HttpSession session, Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.del(user.getId(),shippingId);
    }

    /**
     * 更新收货地址
     * @param session
     * @param shipping
     * @return
     */
    @RequestMapping("update")
    public ServerResponse update(HttpSession session,Shipping shipping){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.update(user.getId(),shipping);
    }

    @RequestMapping("select")
    public ServerResponse select(HttpSession session, Integer shippingId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.select(user.getId(),shippingId);
    }

    @RequestMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "pageNum",defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                         HttpSession session
                                         ){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return shippingService.list(user.getId(),pageNum,pageSize);
    }
}
