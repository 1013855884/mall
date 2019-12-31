package com.school.service.Impl;

import com.google.common.collect.Lists;
import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.mapper.CartMapper;
import com.school.mapper.ProductMapper;
import com.school.pojo.Cart;
import com.school.pojo.Product;
import com.school.service.CartService;
import com.school.util.BigDecimalUtil;
import com.school.util.PropertiesUtil;
import com.school.vo.CartProductVO;
import com.school.vo.CartVO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private CartMapper cartMapper;

    public ServerResponse<CartVO> add(Integer userId,Integer productId,Integer count){
        if (productId == null || count == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Cart cart = cartMapper.selectCartByUserIdProductId(userId,productId);
        if (cart == null){
            //这个商品不存在于购物车，需要重新添加
            Cart cartItem = new Cart();
            cartItem.setQuantity(count);
            cartItem.setProductId(productId);
            cartItem.setUserId(userId);
            cartItem.setChecked(Const.Cart.CHECKED);
            cartMapper.insert(cartItem);
        }else {
            //商品已经存在购物车，数量相加
            count = cart.getQuantity()+count;
            cart.setQuantity(count);
            cartMapper.updateByPrimaryKeySelective(cart);
        }
        CartVO cartVO = this.getCartVOLimit(userId);
        return ServerResponse.createBySuccessMessage(cartVO);
    }


    public CartVO getCartVOLimit(Integer userId){
        CartVO cartVO = new CartVO();
        List<Cart> cartList = cartMapper.selectCartByUserId(userId);
        List<CartProductVO> cartProductVOList = Lists.newArrayList();
        BigDecimal cartTotalPrice = new BigDecimal("0");

        if (CollectionUtils.isNotEmpty(cartList)){
            for (Cart cartItem : cartList) {
                CartProductVO cartProductVO = new CartProductVO();
                cartProductVO.setId(cartItem.getId());
                cartProductVO.setUserId(userId);
                cartProductVO.setQuantity(cartItem.getProductId());

                Product product = productMapper.selectByPrimaryKey(cartItem.getProductId());
                if (product != null){
                    cartProductVO.setProductMainImage(product.getMainImage());
                    cartProductVO.setProductName(product.getName());
                    cartProductVO.setProductSubtitle(product.getSubtitle());
                    cartProductVO.setProductStatus(product.getStatus());
                    cartProductVO.setProductPrice(product.getPrice());
                    cartProductVO.setProductStock(product.getStock());
                    //判断库存
                    int buyLimitCount = 0;
                    if (product.getStatus() >=cartItem.getQuantity()){
                        //库存充足时候
                        buyLimitCount = cartItem.getQuantity();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_SUCCESS);
                    }else {
                        buyLimitCount = product.getStock();
                        cartProductVO.setLimitQuantity(Const.Cart.LIMIT_NUM_FAIL);
                        //购物车中更新有效库存
                        Cart cartForQuantity = new Cart();
                        cartForQuantity.setId(cartItem.getId());
                        cartForQuantity.setQuantity(buyLimitCount);
                        cartMapper.updateByPrimaryKeySelective(cartForQuantity);
                    }
                    cartProductVO.setQuantity(buyLimitCount);
                    //计算总价
                    cartProductVO.setProductTotalPrice(BigDecimalUtil.mul(product.getPrice().doubleValue(),cartProductVO.getQuantity()));
                    cartProductVO.setProductChecked(cartItem.getChecked());
                }
                if (cartItem.getChecked() == Const.Cart.CHECKED){
                    //如果已经勾选添加到商品的总价中
                    cartTotalPrice = BigDecimalUtil.add(cartTotalPrice.doubleValue(),cartProductVO.getProductTotalPrice().doubleValue());

                }
                cartProductVOList.add(cartProductVO);
            }
        }
        cartVO.setCartTotalPrice(cartTotalPrice);
        cartVO.setCartProductVOList(cartProductVOList);
        cartVO.setAllChecked(this.getAllCheckedStatus(userId));
        cartVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix"));
        return cartVO;
    }

    private boolean getAllCheckedStatus(Integer userId){
        if (userId == null){
            return false;
        }
        return cartMapper.selectCartProductCheckedStatusByUserId(userId) == 0;
    }
}
