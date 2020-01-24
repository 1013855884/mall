package com.school.service;

import com.school.common.ServerResponse;

public interface OrderService {
    ServerResponse pay(Long orderNo, Integer userId, String path);
}
