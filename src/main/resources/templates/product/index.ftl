<html>

<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">
<#--sidebar-->
        <#include "../common/nav.ftl">
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" method="post" action="/sell/seller/product/save">
                        <input type="hidden" value="${(productInfo.getProductId())!''}" name="productId"/>
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" class="form-control" name="productName" value="${(productInfo.getProductName())!''}"/>
                        </div>
                        <div class="form-group">
                            <label>价格</label>
                            <input type="text" class="form-control" name="productPrice" value="${(productInfo.getProductPrice())!''}" />
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(productInfo.getProductStock())!''}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input type="text" class="form-control" name="productDescription" value="${(productInfo.getProductDescription())!''}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(productInfo.getProductIcon())!''}" width="100" height="100"/>
                            <input type="text" class="form-control" name="productIcon" value="${(productInfo.getProductIcon())!''}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                                <#list category as item>
                                    <#if  (productInfo.getCategoryType())?? && item.getCategoryType() == (productInfo.getCategoryType())> <!--??表示是否存在当前属性-->
                                        <option selected="selected" value="${item.getCategoryType()}">
                                            ${item.getCategoryName()}
                                        </option>
                                    <#else>
                                    <option value="${item.getCategoryType()}">
                                        ${item.getCategoryName()}
                                    </option>
                                    </#if>
                                </#list>
                            </select>
                        </div>
                        <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>