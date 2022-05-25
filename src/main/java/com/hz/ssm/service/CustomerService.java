package com.hz.ssm.service;

import com.github.pagehelper.PageInfo;
import com.hz.ssm.pojo.City;
import com.hz.ssm.pojo.Customer;
import com.hz.ssm.pojo.Province;


import java.util.List;

public interface CustomerService {
    /**
     * 分页查询的方法
     * @param indexPage
     * @return
     */
    public PageInfo<Customer> findCustInfoByPage(Integer indexPage,String custName,String lever);

    /**
     * 校验用户名是否已经存在
     * @param custName
     * @return
     */
    int checkCustName(String custName);

    /**
     * 显示customer表中所有等级
     * @return
     */
    List<String> findCustLeverList();

    /**
     * 查询所有省份列表信息
     * @return
     */
    List<Province> findAllProvinceInfo();

    /**
     * 根据省份编号查询对应的城市信息
     * @param pid
     * @return
     */
    List<City> selectCityByProid(Integer pid);

    /**
     * 添加
     * @param c
     * @return
     */
    int addCustomerInfo(Customer c);

    /**
     * 根据主键id的删除方法
     * @param custId
     * @return
     */
    int checkDelById(Long custId);

    /**
     * 根据主键Id的查询方法
     * @param custId
     * @return
     */
    Customer findCustById(Long custId);
}
