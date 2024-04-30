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
	<h1>Board detail Page</h1>

	<table border="1">
		<tr>
			<th>bno</th>
			<td>${bvo.bno }</td>
		</tr>
		<tr>
			<th>title</th>
			<td>${bvo.title }</td>
		</tr>
		<tr>
			<th>writer</th>
			<td>${bvo.writer }</td>
		</tr>
		<tr>
			<th>regdate</th>
			<td>${bvo.regdate }</td>
		</tr>
		<tr>
			<th>moddate</th>
			<td>${bvo.moddate }</td>
		</tr>
		<tr>
			<th>content</th>
			<td>${bvo.content }</td>
		</tr>
		<tr>
			<th>image</th>
			<td><img alt="" src="/_fileUpload/${bvo.imageFile }"></td>
		</tr>
	</table>
	<c:if test="${bvo.writer eq ses.id }">
	<a href="/brd/modify?bno=${bvo.bno }"><button>modify</button></a>
	<a href="/brd/remove?bno=${bvo.bno }"><button>remove</button></a>
	</c:if>
	<a href="/brd/list"><button>list</button></a>
	
	<!-- comment line -->
	<hr>
	<div>
		comment line <br>
		<input type="text" id="cmtWriter" value="${ses.id }" readonly="readonly">
		<input type="text" id="cmtText" placeholder="Add Comment...">
		<button type="button" id="cmtAddBtn">comment post</button>
	</div>
	<br>
	<hr>
	
	<!-- 댓글 출력  -->
	<div id="commentLine">
		<div>
			<div>cno, bno, writer, regdate</div>
			<div>
				<button>수정</button> <button>삭제</button> <br>
				<input type="text" value="content">
			</div>
		</div>
	</div>
	
	<script type="text/javascript">
		const bnoVal = `<c:out value="${bvo.bno}"/>`;
		const id = `<c:out value="${ses.id}"/>`;
		console.log(bnoVal);
		console.log(id);
	</script>
	
	<script type="text/javascript" src="/resources/board_detail.js"></script>
	
	<script type="text/javascript">
		printCommentList(bnoVal);
	</script>
</body>
</html>