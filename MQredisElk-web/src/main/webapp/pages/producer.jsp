<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>消息队列-生产者</title>
<%
	String path = request.getContextPath();
%>
<script type="text/javascript"
	src="<%=path%>/jquery/jquery-2.1.1.min.js"></script>
<script type="text/javascript">
	function submit1() {
		var begin = $("#begin").val().trim();
		var end = $("#end").val().trim();
		$.ajax({
			dataType : "text",
			async : false,
			type : "POST",
			url : "Producer",
			data : {
				begin : begin,
				end : end
			},

			success : function(data) {
				var str = eval("(" + data + ")");
				var result = "";
				for ( var iter in str) {
					var str1 = str[iter].messageQueue;
					result += "<tr>" + "<td>" + str[iter].msgId + "</td>"
							+ "<td>" + str[iter].sendStatus + "</td>" + "<td>"
							+ str1.topic + "</td>" + "<td>" + str1.queueId
							+ "</td>" + "<td>" + str1.brokerName + "</td>"
							+ "<td>" + str[iter].queueOffset + "</td>"
							+ "</tr>";
				}
				$("#table1").css('display', 'block');
				$("#tbody1").html(result);
			}
		})
	}
</script>
</head>
<body>
	请输入account_number的范围：
	<br> begin:
	<input type="text" id="begin" /> end:
	<input type="text" id="end" />&nbsp;&nbsp;
	<button onclick="submit1();">提交</button>
	<table id="table1" style="text-align: center; display: none"
		cellpadding="0" border="0" cellspacing="0">
		<tr>
			<td colspan="6">返回的消息队列信息</td>
		</tr>
		<tbody>
			<tr>
				<td rowspan="2" style="border: 1px solid; border-right: 0px">msgId</td>
				<td rowspan="2" style="border: 1px solid; border-right: 0px">sendStatus</td>
				<td colspan="3"
					style="border: 1px solid; border-right: 0px; border-bottom: 0px">消息队列数据结构(messageQueue)</td>
				<td rowspan="2" style="border: 1px solid">queueOffset</td>
			</tr>
			<tr>
				<td style="border: 1px solid; border-right: 0px">topic</td>
				<td style="border: 1px solid; border-right: 0px">queueId</td>
				<td style="border: 1px solid; border-right: 0px">brokerName</td>
			</tr>
		</tbody>
		<tbody id="tbody1"></tbody>
	</table>
</body>
</html>