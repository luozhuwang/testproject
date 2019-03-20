<%@ page language="java" contentType="text/html; charset=utf-8"  
    pageEncoding="utf-8"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">  
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<html>  
<head>  
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">  
    <title>Insert title here</title>  
   <script type="text/javascript"  src="<%=basePath%>jQuery/2.1.1/jquery.js"></script>
<script  type="text/javascript" src="<%=basePath%>jQuery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript">  
        $(function(){  
            $("#bu1").click(function(){  
                $.get("ajax1",   
                        { username: "John", password: "2pm" },  
                        function(data){  
                            alert("Data Loaded: " + data);  
                        });  
            });  
            $("#bu2").click(function(){  
                $.get("ajax2",   
                        { username: "John", password: "2pm" },  
                        function(data){  
                            alert("Data Loaded: " + data);  
                        });  
            });  
            $("#bu3").click(function(){  
                $.get("ajax3",   
                        { username: "John", password: "2pm" },  
                        function(data){  
                            alert("Data Loaded: " + data);  
                        });  
            });  
            $("#bu4").click(function(){  
                alert();  
                $.ajax( {  
                    type : "GET",  
                    url : "ajax4",  
                    data : { username: "John", password: "2pm" },  
                    dataType: "text",  
                    success : function(msg) {  
                        alert(msg);  
                    }  
                });  
            });  
            $("#bu5").click(function(){  
                alert();  
                $.ajax( {  
                    type : "POST",  
                    url : "ajax5",  
                    data : { username: "John", password: "2pm" },  
                    dataType: "text",  
                    success : function(msg) {  
                        alert(msg);  
                    }  
                });  
            });  
            $("#bu6").click(function(){  
                alert();  
                $.ajax( {  
                    type : "GET",  
                    url : "ajax6",  
                    data : { username: "John", password: "2pm" },  
                    dataType: "text",  
                    success : function(msg) {  
                        alert(msg);  
                        var array=jQuery.parseJSON(msg);  
                        var str = "";  
                        for(var i=0;i<array.length;i++){  
                            var u = array[i];  
                            str += "<tr><td>" + u.id + "</td><td>" + u.name + "</td></tr>";  
                        }  
                        $("#tb").fadeIn(1000);  
                        $("#tb").html(str);  
                    }  
                });  
            });  
        });  
    </script>  
</head>  
<body>  
    <center>  
        <br>  
        <h2>GET/POST方式提交参数三种方式</h2>  
        <button id="bu1">Ajax1</button>  
        <button id="bu2">Ajax2</button>  
        <button id="bu3">Ajax3</button>  
        <br>  
        <h2>GET/POST返回对象</h2>  
        <button id="bu4">get</button>  
        <button id="bu5">post</button>  
        <br>  
        <h2>GET/POST返回List</h2>  
        <button id="bu6">Ajax6</button>  
        <table id="tb" style="display: none" border="1px">  
            <tr><td colspan="1">table</td></tr>  
        </table>  
    </center>  
</body>  
</html>