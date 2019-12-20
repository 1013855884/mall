package com.school.service;

import com.school.common.ServerResponse;
import com.school.pojo.Product;
import com.school.vo.ProductDetailVO;

public interface ProductService {

    ServerResponse saveOrUpdateProduct(Product product);

    ServerResponse setSaleStatus(Integer productId,Integer status);

    ServerResponse<ProductDetailVO> manageProductDetail(Integer productId);
}
