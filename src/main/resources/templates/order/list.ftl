<html>
<head>
    <meta charset="UTF-8">
    <title>seller后台</title>
    <link href="https://cdn.bootcss.com/bootstrap/3.0.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container">
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>
                        订单编号
                    </th>
                    <th>
                        姓名
                    </th>
                    <th>
                       手机号
                    </th>
                    <th>
                        地址
                    </th>
                    <th>
                        金额
                    </th>
                    <th>
                        订单状态
                    </th>
                    <th>
                        支付状态
                    </th>
                    <th>
                        创建时间
                    </th>
                    <th colspan="2">
                        操作
                    </th>
                </tr>
                </thead>
                <tbody>
                <#list orderDTOpage.content as orderDTO>
                <tr>
                    <td>
                        ${orderDTO.orderId}
                    </td>
                    <td>
                        ${orderDTO.buyerName}
                    </td>
                    <td>
                        ${orderDTO.buyerPhone}
                    </td>
                    <td>
                        ${orderDTO.buyerAddress}
                    </td>
                    <td>
                        ${orderDTO.orderAmount}
                    </td>
                    <td>
                        ${orderDTO.getOrderStatusEnum()}
                    </td>
                    <td>
                        ${orderDTO.getPayStatusEnum()}
                    </td>
                    <td>
                        ${orderDTO.createTime}
                    </td>
                    <td>
                        <a href="#">详情</a>
                    </td>
                    <td>
                        <a href="#">取消</a>
                    </td>
                </tr>
                </#list>
                </tbody>
            </table>
        </div>
        <div class="col-md-12 column">
            <ul class="pagination pull-right">
                <li>
                    <a href="#">Prev</a>
                </li>
                <#list 1..orderDTOpage.getTotalPages() as index> <!--因为前面的方法返回的是一个数 所以使用 0.. 表示从0到3-->
                    <#if currPage == index>
                        <li><a href="#" aria-disabled="true">当前页</a></li>
                <#else>
                        <li><a href="#">${index}</a></li>
                    </#if>
                </#list>
                <li>
                    <a href="#">Next</a>
                </li>
            </ul>
        </div>
    </div>


</div>

</body>

</html>



