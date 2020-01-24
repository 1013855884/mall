package com.school.service;

import com.school.common.ServerResponse;
import com.school.vo.CartVO;

public interface CartService {

    ServerResponse<CartVO> add(Integer userId, Integer peroductId, Integer count);

    ServerResponse<CartVO> update(Integer userId,Integer productId,Integer count);

    ServerResponse<CartVO> deleteProduct(Integer userId,String productIds);

    ServerResponse<CartVO> list (Integer userId);

    ServerResponse<CartVO> selectOrUnSelect (Integer userId,Integer productId,Integer checked);

    ServerResponse<Integer> getCartProductCount(Integer userId);
}
