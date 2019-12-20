package com.school.controller.backend;

import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.pojo.Product;
import com.school.pojo.User;
import com.school.service.ProductService;
import com.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 后台商品
 */

@ResponseBody
@Controller
@RequestMapping("/manage/product/")
public class ProductManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    /**
     * 新增和更新商品
     * @param session
     * @param product
     * @return
     */
    @RequestMapping("save")
    public ServerResponse productSave(HttpSession session, Product product){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            return productService.saveOrUpdateProduct(product);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }

    @RequestMapping("set_sale_status")
    public ServerResponse setSaleStatus(HttpSession session,Integer productId,Integer status){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            return productService.setSaleStatus(productId,status);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }

    /**
     * (detail)详情
     * 产品详情
     * @param session
     * @param productId
     * @return
     */
    @RequestMapping("detail")
    public ServerResponse getDetail(HttpSession session,Integer productId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            return productService.manageProductDetail(productId);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }




    @RequestMapping("list")
    public ServerResponse list(Integer pageNum,Integer pageSize){
        return null;
    }
}
