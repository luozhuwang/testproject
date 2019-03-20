$(document).ready(function(){
	$("#username").blur(function(){
        if($(this).val().length == 0) {
//            $("#errorname").html("登陆名不为空");
        	alert("登陆名不为空");
        }
    });
	$("#password").blur(function() {
        if($(this).val().length == 0) {
//            $("#errorpassword").html("密码不为空");
        	alert("密码不为空");
        }
    });
});

