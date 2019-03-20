$(document).ready(function(){
    $("#user_code").blur(function(){
        if($(this).val().length == 0) {
            $("#errorname").html("登陆名不为空");
            a=false;
        }
        else{
            var reg = /^[0-9a-zA-Z]{4,10}$/;
            if(!reg.test($(this).val())) {
                $("#errorname").html("4-10个英文字母或数字");
                a=false;
            }
            else{
                $("#errorname").html("");
                a=true;
            }
        }
    });
    $("#user_pwd").blur(function() {
        if($(this).val().length == 0) {
            $("#errorpassword").html("密码不为空");
            b=false;
        }
        else{
            var reg = /^[0-9a-zA-Z]{6,15}$/;
            if(!reg.test($(this).val())) {
                $("#errorpassword").html("6-15个英文字母或数字");
                b=false;
            }
            else{
                $("#errorpassword").html("");
                b=true;
            }
        }
    });
    $("#user_name").blur(function() {
        if($(this).val().length == 0) {
            $("#errorname").html("名字不为空");
            b=false;
        }
    });
    $("#email").blur(function() {
        if($(this).val().length == 0) {
            $("#erroremail").html("电子邮箱不为空");
            d=false;
        }
        else{
            var reg=/^\w+@\w+(.\w+)+$/;
            if(!reg.test($(this).val())) {
                $("#erroremail").html("电子邮箱格式错误");
                d=false;
            }
            else{
                $("#erroremail").html("");
                d=true;
            }
        }
    });
	
});

