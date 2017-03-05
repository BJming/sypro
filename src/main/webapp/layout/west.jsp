<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript">
	var layout_west_tree;
	var layout_west_tree_url = '';
	var sessionInfo_userId = '${sessionInfo.id}';
	if (sessionInfo_userId) {
		layout_west_tree_url = '${pageContext.request.contextPath}/resourceController/tree';
	}
	$(function() {
		layout_west_tree = $('#layout_west_tree').tree({
			url : layout_west_tree_url,
			parentField : 'pid',
			//lines : true,
			onClick : function(node) {
				if (node.attributes && node.attributes.url) {
					var url;
					var iframe;
					if (node.attributes.url.indexOf('/') == 0) {
						url = '${pageContext.request.contextPath}' + node.attributes.url;
					} else {
						url = node.attributes.url;
					}
					//iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;" sandbox></iframe>';
					iframe = '<iframe src="' + url + '" frameborder="0" style="border:0;width:100%;height:98%;"></iframe>';
					var t = $('#index_tabs');
					var opts = {
						title : node.text,
						closable : true,
						iconCls : node.iconCls,
						content : iframe,
						border : false,
						fit : true
					};
					if (t.tabs('exists', opts.title)) {
						t.tabs('select', opts.title);
					} else {
						t.tabs('add', opts);
					}
				}
			}
		});
	});
</script>
<div class="easyui-accordion" data-options="fit:true,border:false">
	<div title="树形系统菜单" style="padding: 5px;" data-options="border:false,isonCls:'cog',tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
					$('#layout_west_tree').tree('reload');
				}
			}, {
				iconCls : 'resultset_next',
				handler : function() {
					var node = $('#layout_west_tree').tree('getSelected');
					if (node) {
						$('#layout_west_tree').tree('expandAll', node.target);
					} else {
						$('#layout_west_tree').tree('expandAll');
					}
				}
			}, {
				iconCls : 'resultset_previous',
				handler : function() {
					var node = $('#layout_west_tree').tree('getSelected');
					if (node) {
						$('#layout_west_tree').tree('collapseAll', node.target);
					} else {
						$('#layout_west_tree').tree('collapseAll');
					}
				}
			} ]">
		<div class="well well-small">
			<ul id="layout_west_tree"></ul>
		</div>
	</div>
	<div title="普通系统菜单" data-options="border:false,iconCls:'icon-reload'">
		<ul>
			<li>菜单</li>
			<li>菜单</li>
			<li>菜单</li>
			<li>菜单</li>
		</ul>
	</div>
</div>