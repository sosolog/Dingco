<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8">
</head>
<body>
<div class="container">
    <div class="py-5 text-center"> <h2>상품 등록 폼</h2>
    </div>
    <h4 class="mb-3">상품 입력</h4>
    <form action="fileUpload" method="post" enctype="multipart/form-data">
       파일<input type="file" name="files" multiple="multiple"><br>
        <input type="submit"/>
    </form>
</div> <!-- /container -->
</body>
</html>
