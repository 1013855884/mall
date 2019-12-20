package com.school.controller.backend;

import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.pojo.User;
import com.school.service.CategoryManageService;
import com.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 分类管理
 */
//category分类
@Controller
@RequestMapping("/manage/category/")
@ResponseBody
public class CategoryManageController {

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryManageService categoryManageService;

    /**
     * 添加品类
     * @param session
     * @param categoryName 品类名称
     * @param parentId 父节点id
     * @return
     */
    @RequestMapping(value = "add_category",method = RequestMethod.GET)
    public ServerResponse addCategory(HttpSession session,String categoryName,@RequestParam(value = "parentId",defaultValue = "0") Integer parentId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //业务
            return categoryManageService.addCategory(categoryName,parentId);
        }else {
            return ServerResponse.createByErrorMessage("无效操作，需要管理员权限");
        }
    }

    /**
     * 修改品类名称
     * @param session
     * @param categorpId id
     * @param cateporyName 品类名称
     * @return
     */
    @RequestMapping(value = "set_category_name",method = RequestMethod.GET)
    public ServerResponse setCategoryName(HttpSession session,Integer categorpId,String cateporyName){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //业务
            return categoryManageService.updateCategoryName(categorpId,cateporyName);
        }else {
            return ServerResponse.createByErrorMessage("无效操作，需要管理员权限");
        }
    }

    /**
     * 查询平行子节点
     * @param session
     * @param categoryId
     * @return
     */
    //Parallel平行
    @RequestMapping(value = "get_category",method = RequestMethod.GET)
    public ServerResponse getChildrenParallelCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //查询子节点的category信息，并且不递归,保持平级
            return categoryManageService.getChildrenParallelCategory(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无效操作，需要管理员权限");
        }
    }

    /**
     *递归查询功能
     * @param session
     * @param categoryId
     * @return
     */

    @RequestMapping(value = "get_deep_category",method = RequestMethod.GET)
    public ServerResponse getCategoryAndDeepChildrenCategory(HttpSession session,@RequestParam(value = "categoryId",defaultValue = "0") Integer categoryId){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录");
        }
        if (userService.checkAdminRole(user).isSuccess()) {
            //是管理员
            //查询当前节点id和子节点的id
            return categoryManageService.selectCategoryAndChildrenById(categoryId);
        }else {
            return ServerResponse.createByErrorMessage("无效操作，需要管理员权限");
        }
    }
}
