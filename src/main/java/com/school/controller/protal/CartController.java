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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 购物车
 */
@Controller
    @RequestMapping("/cart/")
@ResponseBody
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

    /**
     * 更新购物车
     * @param session
     * @param productId
     * @param count
     * @return
     */
    @RequestMapping("update")
    public ServerResponse<CartVO> update(HttpSession session, Integer productId, Integer count) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.update(user.getId(),productId,count);
    }

    /**
     * 删除商品
     * @param session
     * @param productIds
     * @return
     */
    @RequestMapping("delete_product")
    public ServerResponse<CartVO> deleteProduct(HttpSession session,String productIds){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.deleteProduct(user.getId(),productIds);
    }


    /**
     * 全选
     * @param session
     * @return
     */
    @RequestMapping("select_all")
    public ServerResponse<CartVO> selectAll(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.CHECKED);
    }

    /**
     * 取消全选
     * @param session
     * @return
     */
    @RequestMapping("un_select_all")
    public ServerResponse<CartVO> unSelectAll(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnSelect(user.getId(),null,Const.Cart.UN_CHECKED);
    }


    /**
     * 选中某个商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("select")
    public ServerResponse<CartVO> select(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.CHECKED);
    }

    /**
     * 购物车取消选中某个商品
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("un_select")
    public ServerResponse<CartVO> unSelect(HttpSession session,Integer productId){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),ResponseCode.NEED_LOGIN.getMessage());
        }
        return cartService.selectOrUnSelect(user.getId(),productId,Const.Cart.UN_CHECKED);
    }


    /**
     * 获取购物车商品数量
     * @param session
     * @return
     */
    @RequestMapping("get_cart_product_count")
    public ServerResponse<Integer> getCartProductCount(HttpSession session){
        User user = (User)session.getAttribute(Const.CURRENT_USER);
        if(user ==null){
            return ServerResponse.createBySuccessMessage(0);
        }
        return cartService.getCartProductCount(user.getId());
    }
}
