package com.school.controller.protal;

import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.pojo.User;
import com.school.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * (前端)用户接口
 */
@Controller
@ResponseBody
@RequestMapping("/user/")
public class UserController {


    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public ServerResponse<User> login(String username, String password, HttpSession session){
        ServerResponse<User> response = userService.login(username,password);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 退出登录
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "loginout",method = RequestMethod.GET)
    public ServerResponse<User> loginout(HttpSession httpSession){
        httpSession.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccessMessage("退出成功");
    }
    /**
     * 注册
     */
    @RequestMapping(value = "register",method = RequestMethod.GET)
    public ServerResponse<User> register(User user){

        return userService.register(user);
    }
    /**
     * 验证数据
     */
    @RequestMapping(value = "check_valid",method = RequestMethod.GET)
    public ServerResponse<String> checkValid(String str, String type){
        return userService.checkValid(str,type);
    }

    /**
     * 获取用户登录信息
     * @param session
     * @return
     */
    @RequestMapping(value = "get_user_info",method = RequestMethod.GET)
    public ServerResponse<User> getUserInfo(HttpSession session){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user !=null){
            return ServerResponse.createBySuccessMessage(user);
        }
        return ServerResponse.createByErrorMessage("用户未登录，无法获取当前用户的信息");
    }

    /**
     * 验证用户名是否正确提供找回密码的问题
     * @param username
     * @return
     */
    @RequestMapping(value = "forget_get_question",method = RequestMethod.GET)
    public ServerResponse<String> forgetGetQuestion(String username){
        return userService.selectQuestion(username);
    }

    /**
         * 忘记密码的答案验证
     * @param username
     * @param question
     * @param answer
     * @return
     */
    @RequestMapping(value = "forget_check_answer",method = RequestMethod.GET)
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return userService.checkAnswer(username,question,answer);
    }

    /**
     * 重置密码(忘记密码中)
     * @param username
     * @param passwordNew
     * @param forgetToken
     * @return
     */
    @RequestMapping(value = "forget_reset_password",method = RequestMethod.GET)
    public ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
        return userService.forgetResetPassword(username,passwordNew,forgetToken);
    }

    /**
     * 更改密码(登录状态)
     * @param session
     * @param passwordOld
     * @param passwordNew
     * @return
     */
    @RequestMapping(value = "reset_password",method = RequestMethod.GET)
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        return userService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新用户信息
     * @param session
     * @param user
     * @return
     */
    @RequestMapping(value = "update_information",method = RequestMethod.GET)
    public ServerResponse<User> updateInformation(HttpSession session,User user){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorMessage("用户未登录");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<User> response = userService.updateInformation(user);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 获取用户详细信息(登录)
     * @param session
     * @return
     */
    @RequestMapping(value = "get_information",method = RequestMethod.GET)
    public ServerResponse<User> getInformation(HttpSession session){
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"未登录，需要强制登录status=10");
        }

        return userService.getInformation(currentUser.getId());
    }




}
