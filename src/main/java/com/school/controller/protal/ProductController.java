package com.school.controller.protal;

import com.github.pagehelper.PageInfo;
import com.school.common.ServerResponse;
import com.school.service.ProductService;
import com.school.vo.ProductDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前台商品页面
 */
@RequestMapping("/product/")
@Controller
@ResponseBody
public class ProductController {
    @Autowired
    private ProductService productService;

    /**
     * 商品详情
     * @param productId
     * @return
     */
    @RequestMapping("detail")
    public ServerResponse<ProductDetailVO> detail(Integer productId){
        return productService.getProductDetail(productId);
    }

    /**
     * 商品列表和动态排序
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param orderBy
     * @return
     */
    @RequestMapping("list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                                         @RequestParam(value = "keyword",required = false) String keyword,
                                         @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                         @RequestParam(value = "pageSize",defaultValue = "10") Integer pageSize,
                                         @RequestParam(value = "orderBy",defaultValue = "") String orderBy){

        return productService.getProductByKeywordCategory(categoryId,keyword,pageNum,pageSize,orderBy);
    }

}
