<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>게시판 쓰기</title>
<!-- 합쳐지고 최소화된 최신 CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- 부가적인 테마 -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<!-- 합쳐지고 최소화된 최신 자바스크립트 -->

<style type="text/css">
body{
	width: 100%;
	margin: auto;
}
table{
	width: 100%;
}
input, textarea{
	width: 100%;
}
.title{
	text-align: center
}
.btn{
	width:100px;
    background-color: #f8585b;
    border: none;
    color:#fff;
    padding: 15px 0;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 15px;
    margin: 4px;
    cursor: pointer;
    border-radius:10px;
}
.btn:hover {
    background-color: blue;
}
</style>
<script type="text/javascript">
window.onload = function(){
		document.getElementById("submitBtn").onclick = function(){
			var str = document.getElementById("textarea").value;
			str = str.replace(/(?:\r\n|\r|\n)/g, '<br />');
			document.getElementById("content").value = str;
			document.getElementById("frm").submit();
			return false; 
		}
		//높이 설정
	// 	ifrm.attr('onload', "resizeFrame(this,'"+board_height+"px')");
	// 	parent.resizeFrame(ifrm, board_height);
}

</script>
</head>
<body>
<form action="commWrite.do" method="post" id="frm">
	<a href="commMain.do" class="btn">목록</a>
	<input type="button" value="등록" id="submitBtn" class="btn"/>
	<input type="reset" value="취소" class="btn"/>
	<table class="table">
		<tr>
			<td class="title">제목</td>
			<td>
			<c:if test="${list != 0}">
			<c:forEach var="num" begin="0" end="${level}">
			답변 : 
			</c:forEach>
			</c:if>
			<input type="text" name="title" ></td>
		</tr>
		<tr>
			<td class="title">내용</td>
			<td><textarea id="textarea" rows="30"></textarea></td>
		</tr>
		
	</table>
	
	<input type="hidden" name="list" value="${list}"/>
	<input type="hidden" name="level" value="${level}"/>
	<input type="hidden" name="ridx" value="${ridx}"/> 
	<input type="hidden" id="content" name="content"/>
	
</form>
</body>
</html>