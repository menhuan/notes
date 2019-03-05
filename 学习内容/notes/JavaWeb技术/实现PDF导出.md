# 实现PDF 导出

刚过完年，公司在年前有个需求需要在后台管理端实现word导出与pdf导出，在年前就开始查看资料怎么去写word导出，大部分都是用itext来实现，这次word导出我是用freemarker来实现，麻烦点可能是在需要将其将其文档改成xml然后在改成ftl文件。如果不懂ftl文件语法请去看freemarker语法。这里就不再说了。程序用的是maven  ,导入freemarkerjar包

```Maven
<dependency>
   <groupId>org.freemarker</groupId>
    <artifactId>freemarker</artifactId>
   <version>2.3.20</version>
</dependency>
 ```

因为程序是在公司的程序中没有拆出来就手写下文档构造不截图了

ftl文件是放在了webapp下的WEB-INF/views/ftl下

图片是存放在webapp下的images中 

这里的路径是为了方便找文件图片为什么没有放在views下是因为有在做pdf时需要用到，那里需要用到这里的链接访问图片.

因为程序是web端的。路径直接获取的绝对路径

访问程序为
```Java

@RequestMapping(value="download",method={RequestMethod.GET,RequestMethod.POST})
public void exportWord( HttpServletRequest request, HttpServletResponse response) throws Exception {
Map<String,Object>  dateMap=new HashMap<String,Object>();
dateMap.put("title","个人简历");
dateMap.put("userName","xueyucheng");
dateMap.put("userSex","man");
dateMap.put("userAge","17");
dateMap.put("height","161");
dateMap.put("address","handan");
dateMap.put("University","ligong");
dateMap.put("skill","jineng");
dateMap.put("content","asdasasd");
String ftlName="test.ftl";
String imageName="1.jpg";
try {
    
    //这里是获得数据 然后在客户端中实现下载，当然如果指定位置下载也可以实现下面的那个方法
     ByteArrayOutputStream bos=	WordUtil.createWord(dateMap, ftlName, request,imageName);/**字节*/
     WordUtil.renderWord(response, bos.toByteArray(), "123");

        } catch (Exception e) {
          logger.error("word导出出错......",e);	
      }
}
  ``` 
  
word导出做成工具类了，直接从工具类中调用

```Java

public class WordUtil {  

/**
	 * 根据模板创建word
	 * @auther fengruiqi
	 * 2017年2月4日  下午10:46:09
	 * @param dataMap  需要展示的数据
	 * @param templateName  word模板的名称 比如：test.ftl
	 * @param imageName 图片的名称
	 * 
	 */
	public static ByteArrayOutputStream createWord(Map dataMap,String templateName,HttpServletRequest request,String imageName)  throws Exception{
		String basePath = request.getSession().getServletContext().getRealPath("/");//绝对路径  
		//创建配置实例 
		Configuration configuration = new Configuration();
		
		//设置编码
		configuration.setDefaultEncoding("UTF-8");
       try {
           
           //ftl模板文件统一放至 资源文件ftl下面 包下面
           configuration.setDirectoryForTemplateLoading(new File(basePath + "/WEB-INF/views/ftl/"));  
           
           //获取模板 
           Template template = configuration.getTemplate(templateName);
           template.setEncoding("utf-8");
         
           if(imageName!=null && !imageName.equals("")){
        	   /** 图片路径 **/
               String imagePath =basePath+"/images/"+imageName;
               /** 将图片转化为**/
               InputStream in = null;
               byte[] data = null;
               try {
                   in = new FileInputStream(imagePath);
                   data = new byte[in.available()];
                   in.read(data);
                   in.close();
               } catch (Exception e) {
            	   throw new Exception(e);
               }finally {
                   if(in != null){
                       in.close();
                   }
               }
               /** 进行base64位编码 **/
               BASE64Encoder encoder = new BASE64Encoder();
               /** 图片数据       **/
               dataMap.put("image",encoder.encode(data));
           }
          
       /*    //输出文件
           File outFile = new File(filePath+File.separator+fileName);
           
           //如果输出目标文件夹不存在，则创建
           if (!outFile.getParentFile().exists()){
               outFile.getParentFile().mkdirs();
           }*/
           
           //将模板和数据模型合并生成文件 
          // Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
           Writer out = new StringWriter();  
           //生成文件
           template.process(dataMap, out);
           String str = out.toString();  
           ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
           //关闭流
           baos.write(str.getBytes());
           out.flush();
           out.close();
           baos.close();
           return baos;
       } catch (Exception e) {
    	   throw new Exception(e);  
       }
    
   }
	/**
	 * 处理包含多个图片的word问题
	 * @author  fengruiqi
	 * 创建时间 2017年2月6日 下午3:25:59
	 * @param dataMap  要插入的数据
	 * @param templateName  ftl名字
	 * @param request
	 * @param imageName  图片组合昵称
	 * @return
	 * @throws Exception
	 */
	public static ByteArrayOutputStream createWordContinManyPicture(Map dataMap,String templateName,HttpServletRequest request,List<String> imageName)  throws Exception{
		String basePath = request.getSession().getServletContext().getRealPath("/");//绝对路径  
		//创建配置实例 
		Configuration configuration = new Configuration();
		
		//设置编码
		configuration.setDefaultEncoding("UTF-8");
       try {
           
           //ftl模板文件统一放至 资源文件ftl下面 包下面
           configuration.setDirectoryForTemplateLoading(new File(basePath + "/WEB-INF/views/ftl/"));  
           
           //获取模板 
           Template template = configuration.getTemplate(templateName);
           template.setEncoding("utf-8");
           for(String image :imageName){
        	   if(image!=null && !image.equals("")){
            	   /** 图片路径 **/
                   String imagePath =basePath+"/images/"+image;
                   /** 将图片转化为**/
                   InputStream in = null;
                   byte[] data = null;
                   try {
                       in = new FileInputStream(imagePath);
                       data = new byte[in.available()];
                       in.read(data);
                       in.close();
                   } catch (Exception e) {
                	   throw new Exception(e);
                   }finally {
                       if(in != null){
                           in.close();
                       }
                   }
                   /** 进行base64位编码 **/
                   BASE64Encoder encoder = new BASE64Encoder();
                   /** 用图片的名称作为存储图片数据的名称   在页面时应该与改名字对应**/
                   if(image.toLowerCase().contains(".jpg") ){
                	   image=StringUtils.remove(image, ".jpg");
                	   dataMap.put(image,encoder.encode(data));
                   }else if(image.toLowerCase().contains(".png")){
                	   image=StringUtils.remove(image, ".png");
                	   dataMap.put(image,encoder.encode(data));
                   }
                 
               }
           }
          
          
       /*    //输出文件
           File outFile = new File(filePath+File.separator+fileName);
           
           //如果输出目标文件夹不存在，则创建
           if (!outFile.getParentFile().exists()){
               outFile.getParentFile().mkdirs();
           }*/
           
           //将模板和数据模型合并生成文件 
          // Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
           Writer out = new StringWriter();  
           //生成文件
           template.process(dataMap, out);
           String str = out.toString();  
           ByteArrayOutputStream baos = new ByteArrayOutputStream(); 
           //关闭流
           baos.write(str.getBytes());
           out.flush();
           out.close();
           baos.close();
           return baos;
       } catch (Exception e) {
    	   throw new Exception(e);  
       }
	}
	
	
	
	/**
	 * 客户端下载word
	 * @author  fengruiqi
	 * 创建时间 2017年2月5日 上午1:10:38
	 * @param response
	 * @param bytes
	 * @param filename
	 */
    public static void renderWord(HttpServletResponse response, final byte[] bytes, final String filename) {  
      /*  initResponseHeader(response, PDF_TYPE);  */
        setFileDownloadHeader(response, filename);  
        if (null != bytes) {  
            try {  
                response.getOutputStream().write(bytes);  
                response.getOutputStream().flush();  
            } catch (IOException e) {  
                throw new IllegalArgumentException(e);  
            }  
        }  
    }  
    /**
     *  设置让浏览器弹出下载对话框的Header
     * @author  fengruiqi
     * 创建时间 2017年2月4日 下午6:58:44
     * @param response
     * @param fileName
     * @param fileType
     */
    public static void setFileDownloadHeader(HttpServletResponse response, String fileName) {  
        try {  
            // 中文文件名支持  
            String encodedfileName = new String(fileName.getBytes("GBK"), "ISO8859-1");   
            response.setContentType("application/msword");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedfileName + "\"");  
        } catch (UnsupportedEncodingException e) {  
        }  
    }  
}

```

解释下上面那个带多个图片的word导出与导出一个图片的方法其实可以用一个只不过前面的需要将其改成list

当时是没有考虑到多个图片的问题才这样做。多个图片那里处理用的图片的名字生成的图片内容填充到静态模板中。如果有其他好的方法大家一起交流下。在程序中可以指定位置存储或者用浏览器客户端下载保存，看自己的需求了。或者两个方式都可以采用。注意注释的部分。

word导出的难点可能就是在freemarker那里吧其他文件需求还不清楚。所以就导出功能就先做到这
