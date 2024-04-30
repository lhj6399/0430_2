<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Board List Page</h1>
	
	<!-- Search Line -->
	<div>
		<form action="/brd/list" method="get">
			<div>
				<select name="type">
					<option ${ph.pgvo.type eq null ? 'selcted' : ' '}>Choose...</option>
					<option value="t" ${ph.pgvo.type eq 't' ? 'selcted' : ' '}>Title</option>
					<option value="w" ${ph.pgvo.type eq 'w' ? 'selcted' : ' '}>Writer</option>
					<option value="c" ${ph.pgvo.type eq 'c' ? 'selcted' : ' '}>Content</option>
				</select>
				
				<input type="text" name="keyword" placeholder="Search" value="${ph.pgvo.keyword }">
				<input type="hidden" name="pageNo" value="1">
				<input type="hidden" name="qty" value="${ph.pgvo.qty }">
				<button type="submit">Search</button>
				<span>totalCount : ${ph.totalCount }</span>
			</div>
		</form>
	</div>
	<table border="1">
		<tr>
			<th>bno</th>
			<th>title</th>
			<th>writer</th>
			<th>regdate</th>
		</tr>
		<!-- DB에서 가져온 리스트를 c:foreach를 통해 반복 출력 -->
		<c:forEach items="${list }" var="bvo">
			<tr>
				<td>${bvo.bno }</td>
				<td> <a href="/brd/detail?bno=${bvo.bno }"> <img alt="" src="/_fileUpload/_th_${bvo.imageFile }"> ${bvo.title } </a> </td>
				<td>${bvo.writer }</td>
				<td>${bvo.regdate }</td>
			</tr>
		</c:forEach>
	</table>
	<a href="/index.jsp"> <button type="button">index</button> </a>
	
	<!-- Paging Line -->
	<div>
		<!-- prev -->
			<c:if test="${ph.prev }">
				<a href="/brd/list?pageNo=${ph.startPage-1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}">☜ </a>
			</c:if>
		<!-- paging -->
			<c:forEach begin="${ph.startPage }" end="${ph.endPage }" var="i">
				<a href="/brd/list?pageNo=${i }&qty=${ph.pgvo.qty}">${i }</a>
			</c:forEach>
		<!-- next -->
			<c:if test="${ph.next }">	
				<a href="/brd/list?pageNo=${ph.endPage+1 }&qty=${ph.pgvo.qty}&type=${ph.pgvo.type}&keyword=${ph.pgvo.keyword}"> ☞</a>
			</c:if>
	</div>
</body>
</html>