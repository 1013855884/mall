package com.school.service;

import com.github.pagehelper.PageInfo;
import com.school.common.ServerResponse;
import com.school.pojo.Product;
import com.school.vo.ProductDetailVO;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVO> manageProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize);

    ServerResponse<PageInfo> searchProduct(String productName, Integer productId,
                                           Integer pageNum, Integer pageSize);

    ServerResponse<ProductDetailVO> getProductDetail(Integer productId);

    ServerResponse<PageInfo> getProductByKeywordCategory(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy);



}
