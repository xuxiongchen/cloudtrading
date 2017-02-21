<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="sitemesh" uri="/WEB-INF/tlds/sitemesh-decorator.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <%@ taglib prefix="shiro" uri="/WEB-INF/tlds/shiros.tld" %> --%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html style="overflow-x: auto; overflow-y: auto;">
<head>
<title><sitemesh:title /> - 日先App管理后台</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />

<!-- basic styles -->

<link href="${ctx}/static/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link rel="stylesheet"
	href="${ctx}/static/bootstrap/css/font-awesome.min.css" />
<!--[if IE 7]>
	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/font-awesome-ie7.min.css" />
<![endif]-->

<!-- page specific plugin styles -->
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/colorbox.css" />
<!-- fonts -->
<!-- <link rel="stylesheet"
			href="http://fonts.googleapis.com/css?family=Open+Sans:400,300" /> -->

<!-- ace styles -->
<link rel="stylesheet" href="${ctx}/static/bootstrap/css/ace.min.css" />
<link rel="stylesheet"
	href="${ctx}/static/bootstrap/css/ace-rtl.min.css" />
<link rel="stylesheet"
	href="${ctx}/static/bootstrap/css/ace-skins.min.css" />

<!--[if lte IE 8]>
	<link rel="stylesheet" href="${ctx}/static/bootstrap/css/ace-ie.min.css" />
<![endif]-->

<!-- inline styles related to this page -->
<!-- ace settings handler -->
<script src="${ctx}/static/bootstrap/js/ace-extra.min.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
	<script src="${ctx}/static/bootstrap/js/html5shiv.js"></script>
	<script src="${ctx}/static/bootstrap/js/respond.min.js"></script>
<![endif]-->
<link href='${ctx}/static/uploadify/uploadify.css' rel='stylesheet'>
<sitemesh:head />

<!-- basic scripts -->
<script type="text/javascript">
	window.jQuery
			|| document
					.write("<script src='${ctx}/static/bootstrap/js/jquery-2.0.3.min.js'>"
							+ "<"+"/script>");
</script>
<!--[if IE]>
		<script type="text/javascript">
		 window.jQuery || document.write("<script src='${ctx}/static/bootstrap/js/jquery-1.10.2.min.js'>"+"<"+"/script>");
		</script>
	<![endif]-->
<script type="text/javascript">
	if ("ontouchend" in document)
		document
				.write("<script src='${ctx}/static/bootstrap/js/jquery.mobile.custom.min.js'>"
						+ "<"+"/script>");
</script>
<script src="${ctx}/static/bootstrap/js/bootstrap.min.js"></script>
<script src="${ctx}/static/bootstrap/js/typeahead-bs2.min.js"></script>

<!-- page specific plugin scripts -->
<script src="${ctx}/static/bootstrap/js/jquery.dataTables.min.js"></script>
<script src="${ctx}/static/bootstrap/js/jquery.dataTables.bootstrap.js"></script>
<script src="${ctx}/static/bootstrap/js/jquery.colorbox-min.js"></script>
<script src="${ctx}/static/bootstrap/js/bootbox.min.js"></script>

<!-- ace scripts -->
<script src="${ctx}/static/bootstrap/js/ace-elements.min.js"></script>
<script src="${ctx}/static/bootstrap/js/ace.min.js"></script>


<script src="${ctx}/static/bootstrap/js/jquery.validate.min.js"></script>
<script
	src="${ctx}/static/bootstrap/js/jquery_validate_messages_zh.min.js"></script>
<script src="${ctx}/static/bootstrap/js/jquery.bootstrap.min.js"></script>
<script src="${ctx}/static/uploadify/jquery.uploadify-3.1.min.js"></script>

<script type="text/javascript">
	//将form转为AJAX提交
	function ajaxSubmit(frm, fn) {
		var dataPara = getFormJson(frm);
		$.ajax({
			url : frm.action,
			type : frm.method,
			data : dataPara,
			success : fn
		});
	}

	//将form中的值转换为键值对。
	function getFormJson(frm) {
		var o = {};
		var a = $(frm).serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});

		return o;
	}
</script>
</head>
<body>
	<sitemesh:body />
	
	<script type="text/javascript">
		$(function() {
			var colorbox_params = {
				reposition : true,
				scalePhotos : true,
				scrolling : false,
				//previous : '<i class="icon-arrow-left"></i>',
				//next : '<i class="icon-arrow-right"></i>',
				close : '&times;',
				//current : '{current} of {total}',
				maxWidth : '100%',
				maxHeight : '100%',
				onOpen : function() {
					document.body.style.overflow = 'hidden';
				},
				onClosed : function() {
					document.body.style.overflow = 'auto';
				},
				onComplete : function() {
					$.colorbox.resize();
				}
			};
			$('[data-rel="colorbox"]').colorbox(colorbox_params);
		});
	</script>
</body>
</html>