<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" charset="utf-8">
	var loginDialog;
	var defaultUserInfoDialog;
	$(function() {
		$('#loginForm input').keyup(function(event) {
			if (event.keyCode == '13') {
				loginFun();
			}
		});

		loginDialog = $('#loginDialog').show().dialog({
			modal : true,
			closable : false,
			buttons : [ {
				text : '注册',
				handler : function() {
					$('#registerDialog').dialog('open');
				}
			}, {
				text : '登录',
				handler : function() {
					loginFun();
				}
			} ]
		});

		defaultUserInfoDialog = $('#defaultUserInfoDialog').show().dialog({
			top : 0,
			left : 200
		});

		var sessionInfo_userId = '${sessionInfo.id}';
		if (sessionInfo_userId) {/*目的是，如果已经登陆过了，那么刷新页面后也不需要弹出登录窗体*/
			loginDialog.dialog('close');
			defaultUserInfoDialog.dialog('close');
		}
	});
	function loginFun() {
		if (layout_west_tree) {//当west功能菜单树加载成功后再执行登录
			if ($('#loginForm').form('validate')) {
				parent.$.messager.progress({
					title : '提示',
					text : '数据处理中，请稍后....'
				});
				$.post('${pageContext.request.contextPath}/userController/login', $('#loginForm').serialize(), function(result) {
					if (result.success) {
						if (!layout_west_tree_url) {
							layout_west_tree.tree({
								url : '${pageContext.request.contextPath}/resourceController/tree'
							});
						}
						layout_west_tree.tree('reload');//刷新系统菜单，这个菜单会根据用户ID，查找出他应该看到的菜单，没有权限就不会显示
						$('#loginDialog').dialog('close');
						$('#sessionInfoDiv').html($.formatString('[<strong>{0}</strong>]，欢迎你！您使用[<strong>{1}</strong>]IP登录！', result.obj.name, result.obj.ip));
					} else {
						$.messager.alert('错误', result.msg, 'error');
					}
					parent.$.messager.progress('close');
				}, "JSON");
			}
		}
	}
</script>
<div id="loginDialog" title="用户登录" style="width: 300px; height: 180px; overflow: hidden; display: none;">
	<form id="loginForm" method="post">
		<table class="table table-hover table-condensed">
			<tr>
				<th>登录名</th>
				<td><input name="name" type="text" placeholder="请输入登录名" class="easyui-validatebox" data-options="required:true" value="孙宇"></td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password" placeholder="请输入密码" class="easyui-validatebox" data-options="required:true" value="123456"></td>
			</tr>
		</table>
	</form>
</div>

<div id="defaultUserInfoDialog" title="系统测试默认账号" style="width: 300px; height: 260px; overflow: hidden; display: none;">
	<div class="well well-small" style="margin: 3px;">请大家不要随意更改系统默认账户的信息，如果想测试，请自己新建立用户进行测试</div>
	<div class="well well-small" style="margin: 3px;">
		<div>
			<span class="badge">1</span>超管：孙宇/123456
		</div>
		<div>
			<span class="badge badge-success">2</span>资源管理员：admin1/123456
		</div>
		<div>
			<span class="badge badge-warning">3</span>角色管理员：admin2/123456
		</div>
		<div>
			<span class="badge badge-important">4</span>用户管理员：admin3/123456
		</div>
		<div>
			<span class="badge badge-info">5</span>数据源管理员：admin4/123456
		</div>
		<div>
			<span class="badge badge-inverse">6</span>BUG管理员：admin5/123456
		</div>
		<div>
			<span class="badge">7</span>来宾用户：guest/123456
		</div>
	</div>
</div>