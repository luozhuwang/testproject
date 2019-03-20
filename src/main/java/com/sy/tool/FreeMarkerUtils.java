package com.sy.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerUtils {
	/** 
     * 获取指定目录下的模板文件 
     * @param name       模板文件的名称 
     * @param pathPrefix 模板文件的目录 
     */  
    public Template getTemplate(String name, String pathPrefix) throws IOException{  
        Configuration cfg = new Configuration(); //通过FreeMarker的Configuration对象可以读取ftl文件  
        cfg.setClassForTemplateLoading(this.getClass(), pathPrefix); //设置模板文件的目录  
        cfg.setDefaultEncoding("UTF-8");       //Set the default charset of the template files  
        Template temp = cfg.getTemplate(name); //在模板文件目录中寻找名为"name"的模板文件  
        return temp; //此时FreeMarker就会到类路径下的"pathPrefix"文件夹中寻找名为"name"的模板文件  
    }  


    /** 
     * 根据模板文件输出内容到控制台 
     * @param name       模板文件的名称 
     * @param pathPrefix 模板文件的目录 
     * @param rootMap    模板的数据模型 
     */  
    public String getContent(String pathPrefix, String name, Map<String,Object> rootMap) throws TemplateException, IOException{  
        StringWriter writer = new StringWriter();
        this.getTemplate(name, pathPrefix).process(rootMap, writer); 
        String jsonStr = writer.toString();
        JsonObject returnData = new JsonParser().parse(jsonStr).getAsJsonObject();//先将模板文件转为json对象，再转为json字符串
        return returnData.toString();
    }


    /** 
     * 根据模板文件输出内容到指定的文件中 
     * @param name       模板文件的名称 
     * @param pathPrefix 模板文件的目录 
     * @param rootMap    模板的数据模型 
     * @param file       内容的输出文件 
     */  
    public void printFile(String pathPrefix, String name,Map<String,Object> rootMap, File file) throws TemplateException, IOException{  
        Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8"));  
        this.getTemplate(name, pathPrefix).process(rootMap, out); //将模板文件内容以UTF-8编码输出到相应的流中  
        if(null != out){  
            out.close();  
        }  
    }  
    
    
    public static void main(String[] args) {

        FreeMarkerUtils utl = new FreeMarkerUtils();

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("size", 0);
        map.put("gte_val", "1497283200000");
        map.put("lte_val", "1497928996980");
        map.put("min_val", "1497283200000");
        map.put("max_val", "1497928996980");
        map.put("interval", "21526566ms");

        try {
            //获取工程路径
            String url = System.getProperty("src/main/resources/ftl");
            System.out.println(url);
            String content =  utl.getContent("ftl","eventflow.ftl",map);
            //将数据写入eventflow.json文件
            utl.printFile("src/main/resources/ftl","eventflow.ftl", map, new File(url + "src/main/resources/ftleventflow.json"));
            System.out.println("返回的json" + "\n" + content + "\n");
        } catch (TemplateException | IOException e) {
            e.printStackTrace();
        }

    }
}
