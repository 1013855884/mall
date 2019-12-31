package com.school.controller.protal;

import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.pojo.User;
import com.school.service.CartService;
import com.school.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * 购物车
 */
@Controller
@RequestMapping("/cart/")
public class CartController {


    @Autowired
    private CartService cartService;


    /**
     * 加入购物车
     *
     * @return
     */

    @RequestMapping("add")
    public ServerResponse<CartVO> add(HttpSession session, Integer productId, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }


        return cartService.add(user.getId(),productId,count);

    }
}
