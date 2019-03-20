参考demo.jsp进行配置
<script type="text/javascript" >
var editor;
KindEditor.ready(function(K) {
editor = K.create('textarea[name="content"]', {
    	allowFileManager : true,
		cssPath : '<%=basePath%>kindeditor/plugins/code/prettify.css',
		uploadJson : '<%=basePath%>kindeditor/jsp/upload_json.jsp',
		fileManagerJson : '<%=basePath%>kindeditor/jsp/file_manager_json.jsp',
		resizeType : 1,
        allowPreviewEmoticons: false,
        allowImageUpload:true,
        afterUpload: function(){this.sync();}, 
        afterBlur: function(){this.sync();}  
    });
});
function send_data(){
	//直接获取富文本中的内容
	var str=editor.html();
	if(str.indexOf('请在此处自行填写本次上线需求...')!=-1){
		alert('请填写本次上线的需求列表信息！');
		return false;
	}
	else{
		$("input#txt_info").val(str);
		return true;
	}
}
</script>

如果需要上传图片，需要处理配置kindeditor.js--host数据



备注：可以定义1个kindeditor-model.js文件：
var editor;
KindEditor.ready(function(K) {
editor = K.create('textarea[name="content"]', {
    	allowFileManager : true,
		cssPath : '../kindeditor/plugins/code/prettify.css',
		uploadJson : '../kindeditor/jsp/upload_json.jsp',
		fileManagerJson : '../kindeditor/jsp/file_manager_json.jsp',
		resizeType : 1,
        allowPreviewEmoticons: false,
        allowImageUpload:true,
        afterUpload: function(){this.sync();}, 
        afterBlur: function(){this.sync();}  
    });
    K('input[name=getHtml]').click(function(e) {
        alert(editor.html());
    });
    K('input[name=isEmpty]').click(function(e) {
        alert(editor.isEmpty());
    });
    K('input[name=getText]').click(function(e) {
        alert(editor.text());
    });
    K('input[name=selectedHtml]').click(function(e) {
        alert(editor.selectedHtml());
    });
    K('input[name=setHtml]').click(function(e) {
        editor.html('<h3>Hello KindEditor</h3>');
    });
    K('input[name=setText]').click(function(e) {
        editor.text('<h3>Hello KindEditor</h3>');
    });
    K('input[name=insertHtml]').click(function(e) {
        editor.insertHtml('<strong>插入HTML</strong>');
    });
    K('input[name=appendHtml]').click(function(e) {
        editor.appendHtml('<strong>添加HTML</strong>');
    });
    K('input[name=clear]').click(function(e) {
        editor.html('');
    });
});

2.直接引用JS即可
<script charset="utf-8" src="<%=basePath%>kindeditor/kindeditor-model.js"></script>