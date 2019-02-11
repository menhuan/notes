前一篇我们简单的说了下用其他的方式来创建SprignMVC，我们这一篇说下怎么处理multipart形式的数据。
我们通常在表单提交中处理数据一般都是通过&这个字符来进行组合。但是这种方式就不符合我们去进行二进制数据的传输了。而multipart它呢就将数据拆分成很多小部分，每个部分都对应的一个输入域，输入域一般对应的是文本型数据，但是在这里会处理的是二进制数据。

![来自Spring实战第四版](http://upload-images.jianshu.io/upload_images/4237685-ed20509ad3740e7c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
在这部分中我们可以看到name=profilePicture的部分与其他部分有些不同，在这里还设置了content-type代表这个数据是图片类型。
#配置myltipart解析器
   从Spring3.1版本开始，Spring内部就准备两个选择让我们来实现上传的方法。

      1、CommonMultipartResolver:使用Jakarta Commons FileUpload解析multipart请求；
      2、StandardServletMultipartResolver：这个依赖于Servlet3.0对multipart请求的支持。
如果我们要使用 StandardServletMultipartResolver ，那么我们需要在java程序中需要这样写
     
    @Bean
      public MultipartResolver multipartResolver() throws IOException{
	 return new StandardServletMultipartResolver();
     }
这个是最简单的配置，但是我们可能需要其他的内容来限制文件上传，比如文件的大小，或者文件上传类型等等。我们这些不是在我们的这个bean中设置了就需要在web.xml中或者Servlet初始化类的时候进行配置。

    DispatcherServlet  dis=new DispatcherServlet();
    Dynamic  registration=(Dynamic) servletContext.addServlet("appServlet", dis);
    registration.addMapping("/");
    registration.setMultipartConfig(new MultipartConfigElement("/tmp/spitter/uploads",2097152,4194304,0));
    这个在程序中引用的是servlet中的Dynamic包。
第一个参数时路径，然后是文件的最大容量，multipart请求的最大容量，不会关系有多少个part和每个part的大小。最后一个参数时文件大小达到一个指定最大容量，将会写入到路径中默认值为0，所有文件都会写入到磁盘当中。
如果我们不用java类来写，在配置文件中配置那么可以这样
    
		<servlet>
			<servlet-name>appServlet</servlet-name>
			<servlet-class>
				org.springframwork.web.servlet.DispatcherServlet
			</servlet-class>
			<load-on-startup>1</load-on-startup>
			<multipart-config>
				<location>/tmp/uploads</location>
				<max-file-size>2097152</max-file-size>
				<max-request-size>4194304</max-request-size>
			</multipart-config>
		</servlet>
   不管用哪种配置方式临时路径是必须配置 
#配置Jakarta Commons FileUpload multipart解析器
    @Bean
	public MultipartResolver multipartResolver() throws IOException{
		CommonsMultipartResolver   cus=new CommonsMultipartResolver();
		cus.setUploadTempDir(new FileSystemResource("/tmp/spittr/uploads"));
		
		return cus;
	}
我们还可以设置上传大小等等。具体可以参考api。
#页面写上传数据

    <script type="text/javascript">
	function  upload(){
		var formData = new FormData($( "#uploadApp" )[0]);  
		var file=$("#file").val();
		
		var subbif=file.substring(file.length-3,file.length);
		if(subbif !='apk'){
			alert("请上传安卓客户端");
			return false;
		}
		if(window.confirm("你确定要上传？")){
			$(this).unbind('click');
			$("#btnFormBtm").hide();
		     $.ajax({  
		          url: '自己的路径' ,  
		          type: 'POST',  
		          data: formData,  
		          async: false,  
		          cache: false,  
		          contentType: false,  
		          processData: false,  
		          success: function (returndata) {  
		              var obj=eval(returndata); 
		              if(obj==1){
		            	  alert("上传成功");
		            	  window.location.href ="/manage.onigiri/systemUpdate/getAppVersion/index"
		              }
		              if(obj<0){
		            	  alert("上传失败,请重新上传");
		              }
		              if(obj==222){
		            	  alert("请先选择文件")
		            	  
		              }
		          },  
		          error: function (returndata) {  
		              alert(returndata);  
		          }  
		     });  
		}
	}
    </script>
    </head>
    <body>
	<form id="uploadApp" class="form">
			选择上传的文件<span  style="color:red"></span>:<input class="daoru" id="file" type="file" name="file" />
			<input type="button" value="上传" onclick="upload()" class="btnFormBtm" id="btnFormBtm"  />
	</form>
    </body>
这里主要是用来上传文件的，因为能需要用到页面反馈信息，当初自己不知道怎么弄进度条就弄了个ajax等上传完毕后就返回成功。来告知。
    
    public String uploadApp(@RequestParam(value = "file") MultipartFile file ,HttpServletRequest request,HttpServletResponse response,RedirectAttributes redirectAttributes) {
		 	logger.info("开始上传andriodApp。。。");
		 	// 判断文件是否为空  
	        if (!file.isEmpty()) {  
	        	try {
	        		Thread.sleep(100);
	        		UploadAction upa=new UploadAction();
	        		String url=STATIC_PATH+appUrl+file.getOriginalFilename();
					upa.sendMultiFile(file.getInputStream(),url);
					SystemMaintainBean bean=new SystemMaintainBean();
					bean.setDab1Key(APPVERSIONKEY);

						
					}
					
				} catch (Exception e) {
					logger.error("上传失败"+e);
				}
	        } else{
	        	/**让其选择文件*/
	        	return "222".toString();
	        }
	        return "-111".toString();
	  }
具体上传这一部分先写道这里下一片将介绍另外的上传内容    。但是基本上是大同小异。
