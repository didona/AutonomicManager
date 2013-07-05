<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<html>
<head>
    <title><s:text name="MyCloud.message"/></title>
</head>

<body>
	<!-- Promo -->
	<div id="col-top"></div>
	<div class="box" id="col">
    
    <div id="ribbon"></div> <!-- /ribbon (design/ribbon.gif) -->
        
    <!-- Screenshot in browser (replace tmp/browser.gif) -->
    <div id="col-browser"></div> 
    
  	<div id="col-text">
        
        <h2>Workload Analysis</h2>
        
	   	<!-- <h2 id="slogan"><span><s:property value="message"/></span></h2> -->
	   				
      	<!--  <a href="${pageContext.request.contextPath}/registration.jsp">Register</a>  -->
			
		<sj:accordion id="accordion" heightStyle="content" animate="true">
        	<sj:accordionItem title="Node01: 172.31.0.22">
                <sj:div id="divInAccrodionItem1" href="%{urlajax1}"/>
        	</sj:accordionItem>
        	
        	<sj:accordionItem title="Node02: 172.31.0.23">
                
        	</sj:accordionItem>
        	
        	
		</sj:accordion>

	</div> <!-- /col-text -->
    
    </div> <!-- /col -->
    <div id="col-bottom"></div>
    
    <hr class="noscreen">
    <hr class="noscreen">        
</body>
</html>



