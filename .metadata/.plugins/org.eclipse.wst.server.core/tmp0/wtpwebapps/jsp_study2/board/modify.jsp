<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>Board modify Page</h1>

	<form action="/brd/update" method="post" enctype="multipart/form-data">
		<table border="1">
		<tr>
			<th>bno</th>
			<td><input type="text" name="bno" value="${bvo.bno }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>title</th>
			<td><input type="text" name="title" value="${bvo.title }"></td>
		</tr>
		<tr>
			<th>writer</th>
			<td><input type="text" name="writer" value="${bvo.writer }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>regdate</th>
			<td><input type="text" name="regdate" value="${bvo.regdate }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>moddate</th>
			<td><input type="text" name="moddate" value="${bvo.moddate }" readonly="readonly"></td>
		</tr>
		<tr>
			<th>content</th>
			<td><textarea rows="10" cols="30" name="content">${bvo.content }</textarea> </td>
		</tr>
		<tr>
			<th>image</th>
			<td><img alt="" src="/_fileUpload/${bvo.imageFile }"></td>
		</tr>
		<tr>
			<th>imageFile</th>
			<td> 
			<input type="hidden" name="imageFile" value="${bvo.imageFile }">
			<input type="file" name="newFile"> 
			</td>
		</tr>
		</table>
		<button type="submit">수정</button>
	</form>
</body>
</html>