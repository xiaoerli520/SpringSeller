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
                    <form role="form" method="post" action="/sell/seller/category/save">
                        <input type="hidden" value="${(productCategory.getCategoryId())!''}" name="categoryId"/>
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" class="form-control" name="categoryName" value="${(productCategory.getCategoryName())!''}"/>
                        </div>
                        <div class="form-group">
                            <label>Type</label>
                            <input type="text" class="form-control" name="categoryType" value="${(productCategory.getCategoryType())!''}" />
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