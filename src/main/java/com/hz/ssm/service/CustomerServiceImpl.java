package com.hz.ssm.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hz.ssm.dao.CityMapper;
import com.hz.ssm.dao.CustomerMapper;
import com.hz.ssm.dao.ProvinceMapper;

import com.hz.ssm.pojo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private ProvinceMapper provinceMapper;
    @Autowired
    private CityMapper cityMapper;

    public PageInfo<Customer> findCustInfoByPage(Integer indexPage,String custName,String lever){
            //默认首页
            if(indexPage == null){
                indexPage = 1;
            }
            //StringUtils.isBlank此方法是
            if(StringUtils.isBlank(custName)){
                custName = "";
            }



        custName = "%" + custName + "%";

            //分页参数初始化
        PageHelper.startPage(indexPage,5);
        //创建封装查询条件对象
        CustomerExample example = new CustomerExample();
        //追加查询的条件
        CustomerExample.Criteria criteria = example.createCriteria();
       //追加输入的客户名
        criteria.andCustNameLike(custName);
       //参入的参数是客户等级是提示值，不需要查询
        if(!lever.equals("客户等级")){
            criteria.andCustLevelEqualTo(lever);
        }



        //查询当前页码数据
        List<Customer> customerList = customerMapper.selectByExample(example);
        //创建pageInfo对象完成分页处理
        PageInfo<Customer> pageInfo = new PageInfo<Customer>(customerList);

        return pageInfo;
    }

    /**
     * 去持久层验证用户名是否可用
     * @param custName
     * @return
     */
    @Override
    public int checkCustName(String custName) {
        //创建封装条件的CustomerExample类
        CustomerExample example = new CustomerExample();
        //创建离线查询条件对象
        CustomerExample.Criteria criteria = example.createCriteria();
        //把查询条件设置到线查询条件对象中
        criteria.andCustNameEqualTo(custName);


        List<Customer> customerList = customerMapper.selectByExample(example);
        //说明数据库查询到了该用户名
        if(customerList !=null && customerList.size() >0){
            return 1;   //代表存在该用户名
        }

        return 0;   //代表输入的用户名可用
    }

    @Override
    public List<String> findCustLeverList() {
        List<String> levers =  customerMapper.findCustLeverList();

        return levers;
    }

    /**
     * 查询所有省份列表信息
     * @return
     */
    @Override
    public List<Province> findAllProvinceInfo() {
        ProvinceExample example = new ProvinceExample();

        List<Province> provinceList = provinceMapper.selectByExample(example);

        return provinceList;
    }

    @Override
    public List<City> selectCityByProid(Integer pid) {
        CityExample example = new CityExample();

        CityExample.Criteria criteria = example.createCriteria();
        criteria.andProvinceidEqualTo(pid);



        List<City> cityList = cityMapper.selectByExample(example);

        return cityList;
    }

    @Override
    public int addCustomerInfo(Customer c) {

        int rows = customerMapper.insertSelective(c);
        return rows;
    }

    @Override
    public int checkDelById(Long custId) {
        return customerMapper.deleteByPrimaryKey(custId);
    }

    @Override
    public Customer findCustById(Long custId) {
        return customerMapper.selectByPrimaryKey(custId);
    }


}
