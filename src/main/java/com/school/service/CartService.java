package com.school.service;

import com.school.common.ServerResponse;
import com.school.vo.CartVO;

public interface CartService {

    ServerResponse<CartVO> add(Integer userId, Integer peroductId, Integer count);
}
