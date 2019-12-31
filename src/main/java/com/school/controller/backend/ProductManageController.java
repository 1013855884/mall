package com.school.controller.backend;

import com.google.common.collect.Maps;
import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.pojo.Product;
import com.school.pojo.User;
import com.school.service.FileService;
import com.school.service.ProductService;
import com.school.service.UserService;
import com.school.util.PropertiesUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @Autowired
    private FileService fileService;
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

    /**
     * 修改商品销售状态
     * @param session
     * @param productId
     * @param status
     * @return
     */
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

    /**
     * 商品列表
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */


    @RequestMapping("list")
    public ServerResponse getList(HttpSession session ,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            return productService.getProductList(pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }

    /**
     * 搜索
     * @param session
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("search")
    public ServerResponse productSearch(HttpSession session ,
                                  String productName,Integer productId,
                                  @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                  @RequestParam(value = "pageSize", defaultValue = "10")Integer pageSize){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            return productService.searchProduct(productName,productId,pageNum,pageSize);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }
//MultipartFile前台传过来的文件

    /**
     * 上传
     * @param file
     * @param request
     * @return
     */
    @RequestMapping("upload")
    public ServerResponse upload(HttpSession session,@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request){
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.NEED_LOGIN.getStatus(),"用户未登录，请登录管理员");
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            String path  = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file,path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;


            Map fileMap = Maps.newHashMap();
            fileMap.put("uri",targetFileName);
            fileMap.put("url",url);

            return ServerResponse.createBySuccessMessage(fileMap);
        }else {
            return ServerResponse.createByErrorMessage("无权操作");
        }
    }

    /**
     * 富文本上传
     * @param session
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("richtext_img_upload")
    public Map richtextImgUpload(HttpSession session, @RequestParam(value = "upload_file",required = false) MultipartFile file,
                                 HttpServletRequest request, HttpServletResponse response){
        Map resultMap = Maps.newHashMap();
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            resultMap.put("success",false);
            resultMap.put("message","请登录管理员");
            return resultMap;
        }
        if (userService.checkAdminRole(user).isSuccess()){
            //是管理员
            //业务
            String path  = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = fileService.upload(file,path);
            if (StringUtils.isBlank(targetFileName)){
                resultMap.put("success",false);
                resultMap.put("message","上传失败");
                return resultMap;
            }

            String url = PropertiesUtil.getProperty("ftp.server.http.prefix")+targetFileName;


            resultMap.put("success",true);
            resultMap.put("message","上传成功");
            resultMap.put("file_path",url);
            response.addHeader("Access-Control-Allow-Headers","X-File-Name");

            return resultMap;
        }else {
            resultMap.put("success",false);
            resultMap.put("message","无权操作");
            return resultMap;
        }
    }


}
