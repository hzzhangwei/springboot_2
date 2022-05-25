package com.hz.ssm.controller;

import com.github.pagehelper.PageInfo;
import com.hz.ssm.pojo.City;
import com.hz.ssm.pojo.Customer;
import com.hz.ssm.pojo.Province;

import com.hz.ssm.service.CustomerService;
import com.hz.ssm.utils.UploadUtils;


import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerService customerService;
    @RequestMapping("/findCustById/{id}")
    public ModelAndView findCustById(@PathVariable("id") Long custId){
        ModelAndView mv = new ModelAndView();
        Customer c =  customerService.findCustById(custId);

        mv.addObject("cust",c);
        mv.setViewName("/editCustInfo.jsp");
        return mv;
    }

    /**
     * 分页查询的方法
     * @param indexPage
     * @return
     */
    @RequestMapping("/findCustInfoByPage")
    public PageInfo<Customer> findCustInfoByPage(Integer indexPage,String custName,String lever){
        System.out.println("custName=="+custName+",lever=="+lever);

        PageInfo<Customer> pageInfo =  customerService.findCustInfoByPage(indexPage,custName,lever);
        return pageInfo;
    }

    /**
     * 异步校验用户名的方法
     * @param custName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkCustName")
    public int checkCustName(String custName){

        System.out.println("ajax查询调用了"+custName);
        //调用service相关方法，完成验证
       int result =  customerService.checkCustName(custName);

        System.out.println("result=="+result);
        if(result > 0){  //已存在
            return 1;
        }
        return 0;   //可用
    }
    @RequestMapping("/checkDelById/{id}")
    public int checkDelById(@PathVariable("id") Long custId){
        System.out.println("================="+custId);
         int rows =  customerService.checkDelById(custId);
        return 0;
    }

    /**
     * 查询等级列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/findCustLeverList")
    public List<String> findCustLeverList(){
        //调用Service获取数据
        List<String> leverList =   customerService.findCustLeverList();


        return leverList;
    }

    /**
     * MultipartFile 是springMvc框架提供的一个上传组件可以方便接收文件名称
     * @param picFile  变量名称只能取name属性值
     * @param c
     * @return
     */

    @RequestMapping("/addCustomerInfo")
    public ModelAndView addCustomerInfo(MultipartFile picFile, Customer c, String provinceName, String city, String addDeail) throws IOException {

       /* System.out.println("provinceName=="+provinceName);
        System.out.println("city=="+city);
        System.out.println("addDeail=="+addDeail);*/
        String newAddress =  provinceName +"-"+city+"-"+addDeail;

        c.setCustAddress(newAddress);

        if(picFile != null){
            //文件上传
            //获取上传的文件名称
            String fileName = picFile.getOriginalFilename();
            System.out.println("fileName=="+fileName);
            //重命名
            String newFileName = UploadUtils.getUUIDName(fileName);
            //创建一个file对象，指定文件上传的路径url地址
            File file = new File("D:\\image\\"+newFileName);
            //执行文件上传
            picFile.transferTo(file);
        }

       int rows =    customerService.addCustomerInfo(c);
        ModelAndView mv = new ModelAndView();

        mv.setViewName("redirect:/loadCustInfoByPage.html");

        return mv;
    }

    /**
     * 演示批量上传
     * @param picFiles
     * @param c
     * @return
     * @throws IOException

    @RequestMapping("/addCustomerInfo")
    public String addCustomerInfo(MultipartFile[] picFiles,Customer c) throws IOException {

        if(picFiles != null && picFiles.length >0){
            for(MultipartFile picFile: picFiles){
                //文件上传
                //获取上传的文件名称
                String fileName = picFile.getOriginalFilename();
                System.out.println("fileName=="+fileName);
                //重命名
                String newFileName = UploadUtils.getUUIDName(fileName);
                //创建一个file对象，指定文件上传的路径url地址
                File file = new File("D:\\image\\"+newFileName);
                //执行文件上传
                picFile.transferTo(file);
            }
        }


        return "";
    }
     */
    /**
     * 演示文件下载
     * @param request
     * @param fileName
     * @return
     * @throws IOException
     */
    @RequestMapping("/download")
    public ResponseEntity<byte[]> download(HttpServletRequest request, String fileName) throws IOException {
        System.out.println("fileName=="+fileName);
        //创建file对象
        File file = new File("D:\\image\\"+fileName);
        //设置响应头
        HttpHeaders headers = new HttpHeaders();
        //通知浏览器以下载的方式打开文件
        headers.setContentDispositionFormData("attachment",fileName);
        //定义以流的形式下载返回文件数据 application/octet-stream 二进制数据流（最常见的文件下载）
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        //读取文件对象
        // byte[] bytes = FileUtils.readFileToByteArray(file);
        //使用springmvc框架的ResponseEntity对象封装返回数据
        ResponseEntity<byte[]> entity = new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers, HttpStatus.OK);

        return entity;
    }

    /**
     * 获取所有省份列表的信息的方法
     * @return
     */
    @RequestMapping("/findAllProvinceInfo")
    public  List<Province> findAllProvinceInfo(){
        List<Province> proList =  customerService.findAllProvinceInfo();

        return proList;
    }

    @RequestMapping("/selectCityByProid")
    public List<City> selectCityByProid(Integer pid){
        List<City> cityList = customerService.selectCityByProid(pid);
        return cityList;
    }






}
