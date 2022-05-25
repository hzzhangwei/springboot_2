<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2022-5-24
  Time: 16:18
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" enctype="multipart/form-data" action="/addCustomerInfo">
    <table width="60%" border="1px solid red">
        <tr>
            <td>编辑</td>

        </tr>
        <tr>
            <td>客户名称</td>
            <td><input type="text" name="custName" value="${cust.custName}">
            </td>
        </tr>
        <tr>
            <td>客户来源</td>
            <td><input type="text" name="custSource" value="${cust.custSource}"></td>
        </tr>
        <tr>
            <td>客户等级</td>
            <td>
                <select name="custLevel">

                </select>
            </td>
        </tr>
        <tr>
            <td>所属联系人</td>
            <td><input type="text" name="linkman"></td>
        </tr>
        <tr>
            <td>移动电话</td>
            <td><input type="text" name="phone"></td>
        </tr>
        <tr>
            <td>出生年月</td>
            <td><input type="date" name="birthday"></td>
        </tr>

        <tr>
            <td>提交</td>
            <td><input type="submit"></td>
        </tr>
    </table>
</form>
</body>
</html>
