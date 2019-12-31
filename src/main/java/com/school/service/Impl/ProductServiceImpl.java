package com.school.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.school.common.Const;
import com.school.common.ResponseCode;
import com.school.common.ServerResponse;
import com.school.mapper.CategoryMapper;
import com.school.mapper.ProductMapper;
import com.school.pojo.Category;
import com.school.pojo.Product;
import com.school.service.CategoryManageService;
import com.school.service.ProductService;
import com.school.util.DateTimeUtil;
import com.school.util.PropertiesUtil;
import com.school.vo.ProductDetailVO;
import com.school.vo.ProductListVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryManageService categoryManageService;

    /**
     * 更新和新增产品
     *
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
     *
     * @param productId
     * @param status
     * @return
     */
    public ServerResponse setSaleStatus(Integer productId, Integer status) {
        if (productId == null || status == null) {
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccessMessage("修改产品销售状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品销售状态失败");
    }

    /**
     * 产品详情
     *
     * @param productId
     * @return
     */

    public ServerResponse<ProductDetailVO> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品不存在或者已下架");
        }
        ProductDetailVO productDetailVO = assembleProductDetailVo(product);
        return ServerResponse.createBySuccessMessage(productDetailVO);
    }

    //封装vo返回给前端
    private ProductDetailVO assembleProductDetailVo(Product product) {
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

        productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());
        if (category == null) {
            productDetailVO.setParentCategoryId(0);
        } else {
            productDetailVO.setParentCategoryId(category.getParentId());
        }
        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVO;
    }

    /**
     * 列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductList(Integer pageNum, Integer pageSize) {

        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();

        List<ProductListVO> productListVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);
        return ServerResponse.createBySuccessMessage(pageInfo);
    }


    private ProductListVO assembleProductListVO(Product product) {
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setName(product.getName());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        productListVO.setMainImage(product.getMainImage());
        productListVO.setPrice(product.getPrice());
        productListVO.setSubtitle(product.getSubtitle());
        productListVO.setStatus(product.getStatus());
        return productListVO;
    }

    /**
     * 搜素
     *
     * @param productName
     * @param productId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId,
                                                  Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            //模糊查询,拼接字符串
            productName = new StringBuffer().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVO> productListVOList = new ArrayList<>();
        for (Product product : productList) {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);
        return ServerResponse.createBySuccessMessage(pageInfo);
    }

    //前台接口

    /**
     * 获取商品详情
     *
     * @param productId
     * @return
     */
    public ServerResponse<ProductDetailVO> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品不存在或者已下架");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品不存在或者已下架");
        }
        ProductDetailVO productDetailVO = assembleProductDetailVo(product);
        return ServerResponse.createBySuccessMessage(productDetailVO);
    }

    /**
     * 商品列表（分页）
     *
     * @param categoryId
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    public ServerResponse<PageInfo> getProductByKeywordCategory(Integer categoryId, String keyword, Integer pageNum, Integer pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorStatusMessage(ResponseCode.ILLEGAL_ARGUMENT.getStatus(), ResponseCode.ILLEGAL_ARGUMENT.getMessage());
        }
        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null) {
                //没有该分类，并且还没有关键字，这个时候返回一个空结果集，不报错
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.createBySuccessMessage(pageInfo);
            }
            categoryIdList = categoryManageService.selectCategoryAndChildrenById(category.getId()).getData();
        }
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }
        PageHelper.startPage(pageNum, pageSize);
        //动态排序
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }

        List<Product> productList = productMapper.selectByNameAndCategoryId(keyword, categoryIdList);
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product product : productList) {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);
        return ServerResponse.createBySuccessMessage(pageInfo);
    }
}
