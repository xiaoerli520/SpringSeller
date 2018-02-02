<html>

<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">
<#--sidebar-->
        <#include "../common/nav.ftl">
<#--主要内容区-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <table class="table table-striped table-hover">
                        <thead>
                        <tr>
                            <th>
                                商品id
                            </th>
                            <th>
                                名称
                            </th>
                            <th>
                                图片
                            </th>
                            <th>
                                单价
                            </th>
                            <th>
                                库存
                            </th>
                            <th>
                                描述
                            </th>
                            <th>
                                类目
                            </th>
                            <th>
                                创建时间
                            </th>
                            <th>
                                修改时间
                            </th>
                            <th colspan="2">
                                操作
                            </th>
                        </tr>
                        </thead>
                        <tbody>
                <#list productInfoPage.getContent() as productInfo>
                <tr>
                    <td>
                        ${productInfo.getProductId()}
                    </td>
                    <td>
                        ${productInfo.getProductName()}
                    </td>
                    <td>
                        <img src="${productInfo.getProductIcon()}" width="100">
                    </td>
                    <td>
                        ${productInfo.getProductPrice()}
                    </td>
                    <td>
                        ${productInfo.getProductStock()}
                    </td>
                    <td>
                        ${productInfo.getProductDescription()}
                    </td>
                    <td>
                        ${productInfo.getCategoryType()}
                    </td>
                    <td>
                        ${productInfo.getCreateTime()}
                    </td>
                    <td>
                        ${productInfo.getUpdateTime()}
                    </td>
                    <td>
                        <a href="/sell/seller/product/index?productId=${productInfo.getProductId()}">修改</a>
                    </td>
                    <td>
                    <#if productInfo.getProductStatusEnum().code == 0>
                        <a href="/sell/seller/product/off_sale?productId=${productInfo.getProductId()}">下架</a>
                        <#else>
                        <a href="/sell/seller/product/cancel?productId=${productInfo.getProductId()}">上架</a>
                    </#if>



                    </td>
                </tr>
                </#list>
                        </tbody>
                    </table>
                </div>
                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                <#if currPage == 1>
                    <li><a href="#">Prev</a></li>
                <#else>
                    <li><a href="/sell/seller/product/list?page=${currPage - 1}&size=${size}">Prev</a></li>
                </#if>

                <#list 1..productInfoPage.getTotalPages() as index> <!--因为前面的方法返回的是一个数 所以使用 0.. 表示从0到3-->
                    <#if currPage == index>
                        <li><a href="#" aria-disabled="true">当前页</a></li>
                    <#else>
                        <li><a href="/sell/seller/product/list?page=${index}&size=${size}">${index}</a></li>
                    </#if>
                </#list>
                <#if currPage == productInfoPage.getTotalPages()>
                    <li><a href="#">Next</a></li>
                <#else>
                    <li><a href="/sell/seller/product/list?page=${currPage + 1}&size=${size}">Next</a></li>
                </#if>

                    </ul>
                </div>

            </div>


        </div>
    </div>
</div>
</body>
</html>