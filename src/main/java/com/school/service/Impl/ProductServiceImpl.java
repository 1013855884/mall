package com.school.service.Impl;

import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.mapper.ProductMapper;
import com.school.pojo.Product;
import com.school.service.ProductService;
import com.school.util.PropertiesUtil;
import com.school.vo.ProductDetailVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    /**
     * 更新和新增产品
     * @param product
     * @return
     */
    public ServerResponse saveOrUpdateProduct(Product product) {
        //todo 待理解存图片集合？？
        if (product != null) {
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }
            //根据id判断是更新还是新增
            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("更新产品成功");
                }
                return ServerResponse.createByErrorMessage("更新产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccessMessage("新增产品成功");
                }
                return ServerResponse.createByErrorMessage("新增产品失败");
            }
        }
        return ServerResponse.createByErrorMessage("新增和更新商品参数不正确");
    }

    /**
     * 上下架商品
     * @param productId
     * @param status
     * @return
     */
    public ServerResponse setSaleStatus(Integer productId,Integer status){
        if (productId ==null || status == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
            Product product = new Product();
            product.setId(productId);
            product.setStatus(status);
             int rowCount = productMapper.updateByPrimaryKeySelective(product);
             if (rowCount > 0){
                 return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
             }
             return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    /**
     * 产品详情
     * @param productId
     * @return
     */

    public ServerResponse<ProductDetailVO> manageProductDetail(Integer productId){
        if (productId == null){
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(),ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null){
            return ServerResponse.createByErrorMessage("产品不存在或者已下架");
        }

        return null;
    }

    public ProductDetailVO assembleProductDetailVo(Product product){
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setSubImages(product.getSubImages());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setName(product.getName());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());

        productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix","http://img.happymmall.com/"));





        return null;
    }
}
