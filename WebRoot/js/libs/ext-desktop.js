Ext.ns("Ext.sss");
Ext.QuickTips.init();
Ext.form.Field.prototype.msgTarget = 'qtip';
var cp = new Ext.state.CookieProvider({
	path : "/cgi-bin/",
	expires : new Date(new Date().getTime() + (1000 * 60 * 60 * 24 * 30)), // 30
	// days
	domain : "localhost:8080/werp"
});
Ext.state.Manager.setProvider(cp);
Ext.ns("Ext.sss");
Ext.chart.Chart.CHART_URL = 'charts/charts.swf';
msgGolobalBef = true;
function isImg(extention) {
	return (extention == "JPEG" || extention == "JPG" || extention == "ICO" || extention == "Exif" || extention == "TIFF" || extention == "RAW" || extention == "GIF" || extention == "BMP"
			|| extention == "PNG" || extention == "PPM" || extention == "PGM" || extention == "PBM" || extention == "PNM" || extention == "PFM" || extention == "PAM" || extention == "WEBP");
};
function OnBeforeUnLoad(e) {
	if (msgGolobalBef)
		return "Toutes les données que vous avez saisies seront perdues!";
};
function OnLoad(e) {
	msgGolobalBef = true;
};
window.onbeforeunload = OnBeforeUnLoad;
window.onload = OnLoad;
Ext.sss.ActionMgr = function() {
	var types = {};
	return {
		reg : function(xtype, cls) {
			types[xtype] = cls;
			cls.xtype = xtype;
		},
		create : function(config) {
			if (Ext.isInstanceof(config, Ext.sss.Action)) {
				return config;
			}
			return new types[config.xtype](config);
		}
	};
}();
Ext.sss.Action = Ext.extend(Object, {
	waitMsg : 'Requéte en cours de traitement...',
	invalidForm : "Formulaire est invalide.",
	method : 'post',
	msgConfirm : 'Vous voulez continuer cette opération',
	msgConfirmTitle : 'Confirmation',
	isConfirmMsg : false,
	isWait : true,
	actionPattern : 'action',
	etype : '',
	paramsField : new Array(),
	conditionHandler : false,
	constructor : function(config) {
		Ext.apply(this, config);
	},
	join : function(desktop) {
		this.desktop = desktop;
	},
	getUrl : function(submitParams) {
		var url = this.desktop.resolveUrl(this.componentId, this.eventName, this.actionPattern);
		submitParams = Ext.apply({
			eventsss : this.etype
		}, submitParams);
		url = Ext.urlAppend(url, Ext.urlEncode(submitParams));
		return url;
	},
	getMsgContainer : function() {
		if (!this.container)
			return Ext.getBody();
		else {
			var cmp = Ext.getCmp(this.container);
			if (cmp.label) {
				this.waitMsg = "";
				return cmp.label;
			}
			if (cmp.getEl)
				return cmp.getEl();
			return Ext.fly(this.container);
		}
	},
	addParam : function(name, value) {
		this.params = Ext.apply({}, this.params);
		this.params[name] = value;
	},
	addParamField : function(name) {
		this.paramsField = Ext.apply({}, this.paramsField);
		this.paramsField[name] = name;
	},
	run : function() {
		var isrun = true;
		if (this.conditionHandler) {
			var handel = this.conditionHandler.createDelegate(this, this.args);
			isrun = handel();
		}
		if (!isrun)
			return;
		if (this.isConfirmMsg) {
			Ext.MessageBox.confirm(this.msgConfirmTitle, this.msgConfirm, function(btn) {
				if ('yes' == btn) {
					this.doRun();
					Ext.iterate(this.gridSubmit, function(item, index, allItems) {
						var grid = this.desktop.getCmp(item);
						if (grid && !Ext.isInstanceof(grid, Ext.tree.GridPanel) && grid.isEditorGrid && grid.isValid()) {
							grid.initSubmitValue();
						}
					}, this);
				}
			}.createDelegate(this));
		} else {
			this.doRun();
			Ext.iterate(this.gridSubmit, function(item, index, allItems) {
				var grid = this.desktop.getCmp(item);
				if (grid && !Ext.isInstanceof(grid, Ext.tree.GridPanel) && grid.isEditorGrid && grid.isValid()) {
					grid.initSubmitValue();
				}
			}, this);
		}
	},
	doRun : function() {
	},
	getSubmitParams : function() {
		var params = {};
		this.gridSubmit = new Array();
		if (Ext.isFunction(this.params)) {
			params = this.params.createDelegate(this, this.args)();
		} else if (this.params) {
			params = Ext.apply({}, this.params);
		}
		params["gridValids"] = "true";
		if (this.allField) {
			if (this.desktop.getForm().isValid()) {
				Ext.apply(params, this.desktop.getForm().getValues());
				this.isVal = true;
				Ext.iterate(this.desktop.getEditorGrids(), function(item, index, allItems) {
					var grid = this.desktop.getCmp(item);
					if (grid && !Ext.isInstanceof(grid, Ext.tree.GridPanel) && grid.isEditorGrid && grid.isValid()) {
						params[grid.getId()] = grid.getValues();
						this.gridSubmit.push(grid.getId());
					} else if (!Ext.isInstanceof(grid, Ext.tree.GridPanel) && grid.isVisible()) {
						this.isVal = false;
					}
				}, this);
				params["gridValids"] = this.isVal;
			} else
				return;
		} else if (this.paramsField) {
			if (Ext.isFunction(this.paramsField)) {
				this.paramsField = this.paramsField.createDelegate(this, this.args)();
			}
			this.isVal = true;
			Ext.iterate(this.paramsField, function(fieldId, i, allItems) {
				if (Ext.isFunction(fieldId)) {
					var paramsFct = fieldId.createDelegate(this, this.args)();
					params = Ext.apply(params, paramsFct);
				} else {
					var field = this.desktop.getCmp(fieldId);
					if (field && field.isEditorGrid && !Ext.isInstanceof(field, Ext.tree.GridPanel)) {
						if (!field.isValid()) {
							params["gridValids"] = false;
						}
						params[fieldId] = field.getValues();
						this.gridSubmit.push(fieldId);
					} else if (field && !Ext.isInstanceof(field, Ext.tree.GridPanel) && field.getValue) {
						if (!field.validate()) {
							// this.isVal=false;
						} else {
							if (Ext.isInstanceof(field, Ext.form.DateField) || Ext.isInstanceof(field, Ext.form.NumberField))
								params[fieldId] = field.getRawValue();
							else if (Ext.isInstanceof(field, Ext.form.RadioGroup) || Ext.isInstanceof(field, Ext.form.CheckboxGroup))
								params[fieldId] = field.getValue().getRawValue();
							else {
								params[fieldId] = field.getValue();
							}
						}
					}
				}
			}, this);
			if (!this.isVal)
				return;
		}
		return params;
	},
	loadMask : function() {
		this.getMsgContainer().mask(this.waitMsg);
	},
	unLoadMask : function() {
		this.getMsgContainer().unmask();
	}
});
Ext.sss.ActionMgr.reg("action", Ext.sss.Action);
Ext.sss.ScriptAction = Ext.extend(Ext.sss.Action, {
	etype : 'script',
	constructor : function(config) {
		Ext.apply(this, config);
		if (this.handel) {
			this.handel = this.handel.createDelegate(this, this.args);
		} else {
			this.handel = Ext.emptyFn;
		}
		var actions = Ext.apply({}, this.actions);
		this.actions = {};
		Ext.iterate(actions, function(key, value, os) {
			this.actions[key] = this.desktop.createServerAction(value);
		}, this);
	},
	doRun : function() {
		this.handel();
	},
	getEventAction : function(name) {
		if (this.actions)
			return this.actions[name];
		return false;
	},
	fireEventAction : function(name) {
		action = this.getEventAction(name);
		if (action)
			action.run();
	}
});
Ext.sss.ActionMgr.reg("script", Ext.sss.ScriptAction);
Ext.sss.SubmitAction = Ext.extend(Ext.sss.Action, {
	etype : 'submit',
	doRun : function() {
		msgGolobalBef = false;
		if (!this.desktop.getForm().isValid()) {
			this.desktop.alert(this.invalidForm, "");
			return false;
		}
		var isValidForm = true;
		Ext.iterate(this.desktop.getEditorGrids(), function(item, index, allItems) {
			var grid = this.desktop.getCmp(item);
			if (grid && grid.isVisible() && grid.isEditorGrid) {
				if (grid.isValid()) {
					this.desktop.addToContainer({
						xtype : 'hidden',
						name : grid.getId(),
						value : grid.getValues()
					});
					this.desktop.desktopContainer.doLayout();
				} else
					isValidForm = false;
			}
		}, this);
		if (!isValidForm) {
			this.desktop.alert(this.invalidForm, "");
			return false;
		}
		this.desktop.getForm().getEl().dom.action = this.getUrl();
		this.desktop.getForm().standardSubmit = true;
		this.loadMask();
		this.desktop.getForm().submit(this);
	}
});
Ext.sss.ActionMgr.reg("submit", Ext.sss.SubmitAction);
Ext.sss.AjaxAction = Ext.extend(Ext.sss.Action, {
	etype : 'ajax',
	doRun : function() {
		_this = this;
		var submitParams = this.getSubmitParams();
		if (!submitParams || !submitParams["gridValids"]) {
			this.desktop.alert(this.invalidForm, "");
			return;
		}
		var failure = this.failure ? this.failure : function(response, options) {
			switch (response.failureType) {
			case Ext.form.Action.CLIENT_INVALID:
				Ext.Msg.alert('Echec', 'Les champs du formulaire ne peut Ã©tre soumis des valeurs invalides');
				break;
			case Ext.form.Action.CONNECT_FAILURE:
				Ext.Msg.alert('Echec', 'communication a Ã©chouÃ©');
				break;
			case Ext.form.Action.SERVER_INVALID:
				Ext.Msg.alert('Echec', action.result.msg);
			}
		};
		var success = this.success ? this.success : Ext.emptyFn;
		var options = {
			method : this.method,
			componentId : this.componentId,
			success : success,
			failure : failure,
			timeout : 600000,
			url : this.getUrl(),
			params : submitParams,
			clientValidation : this.clientValidation
		};
		var listeners = {};
		if (this.isWait) {
			listeners["beforerequest"] = {
				fn : _this.loadMask,
				scope : _this
			};
			listeners["requestcomplete"] = {
				fn : _this.unLoadMask,
				scope : _this
			};
			listeners["requestexception"] = {
				fn : _this.unLoadMask,
				scope : _this
			};
		}
		var connection = new Ext.data.Connection({
			listeners : listeners
		});
		connection.request(options);
	}
});
Ext.sss.ActionMgr.reg("ajax", Ext.sss.AjaxAction);
Ext.sss.MediaAction = Ext.extend(Ext.sss.Action, {
	etype : 'media',
	doRun : function() {
		var submitParams = this.getSubmitParams();
		submitParams.isview = "false";
		if (!submitParams || !submitParams["gridValids"]) {
			this.desktop.alert(this.invalidForm, "");
			return;
		}
		if (!Ext.ux.Media.mediaTypes[this.extention] && isImg(this.extention))
			this.extention = "PNG";
		if (Ext.ux.Media.mediaTypes[this.extention] && !this.isload) {
			submitParams.isview = "true";
			this.actionUrl = this.getUrl(submitParams);
			var mediaWindow = new Ext.sss.MediaWindow(this);
			mediaWindow.show();
		} else {
			msgGolobalBef = false;
			this.actionUrl = this.getUrl(submitParams);
			frm = '<form action="{0}" id="exportFile" accept-charset="UNKNOWN" target = "_parent" enctype="application/x-www-form-urlencoded">';
			frm = String.format(frm, this.getUrl({}));
			txtin = '<input name="{0}" type="text" value="{1}"/>';
			Ext.iterate(submitParams, function(fieldId, i, allItems) {
				frm = frm + String.format(txtin, fieldId, i);
			});
			frm = frm + '</form>';
			Ext.get('exportDivFile').update(frm);
			document.forms["exportFile"].submit();
		}
	}
});
Ext.sss.ActionMgr.reg("media", Ext.sss.MediaAction);
Ext.sss.ExportFileAction = Ext.extend(Ext.sss.Action, {
	etype : 'exportFile',
	doRun : function() {
		msgGolobalBef = false;
		var submitParams = this.getSubmitParams();
		if (!submitParams || !submitParams["gridValids"]) {
			this.desktop.alert(this.invalidForm, "");
			return;
		}
		frm = '<form action="{0}" id="exportFile" accept-charset="UNKNOWN" target = "_parent" enctype="application/x-www-form-urlencoded">';
		frm = String.format(frm, this.getUrl({}));
		txtin = '<input name="{0}" type="text" value="{1}"/>';
		Ext.iterate(submitParams, function(fieldId, i, allItems) {
			frm = frm + String.format(txtin, fieldId, i);
		});
		frm = frm + '</form>';
		Ext.get('exportDivFile').update(frm);
		document.forms["exportFile"].submit();
	}
});
Ext.sss.ActionMgr.reg("exportFile", Ext.sss.ExportFileAction);
Ext.sss.HrefAction = Ext.extend(Ext.sss.Action, {
	etype : 'href',
	doRun : function() {
		msgGolobalBef = false;
		var submitParams = this.getSubmitParams();
		if (!submitParams || !submitParams["gridValids"]) {
			this.desktop.alert(this.invalidForm, "");
			return;
		}
		this.loadMask();
		window.location.href = this.getUrl(submitParams);
	}
});
Ext.sss.ActionMgr.reg("href", Ext.sss.HrefAction);
Ext.sss.MediaWindow = Ext.extend(Ext.ux.MediaWindow, {
	constructor : function(config) {
		config.iconCls = config.iconCls ? config.iconCls : 'media-' + config.extention;
		config = Ext.apply({
			layout : "fit",
			width : "80%",
			height : "600",
			title : "Export Pdf",
			autoScroll : false,
			maximizable : true,
			collapsible : true,
			autoSize : true,
			modal : true,
			mediaCfg : {
				mediaType : config.extention,
				url : config.actionUrl,
				autoMask : true,
				start : true,
				params : config.params
			},
			bbar : {
				buttonAlign : "center",
				items : [ {
					text : 'Fermer',
					'icon' : 'images/action/close.png',
					handler : function() {
						this.destroy();
					}.createDelegate(this)
				} ]
			}
		}, config);
		Ext.sss.MediaWindow.superclass.constructor.call(this, config);
	}
});
Ext.reg('3smediaWindow', Ext.sss.MediaWindow);
Ext.sss.DesktopActionExplorer = Ext.extend(Ext.tree.TreePanel, {
	constructor : function(config) {
		Ext.apply(config, {
			split : true,
			width : 230,
			minSize : 200,
			maxSize : 250,
			ctCls : 'west-panel',
			rootVisible : false,
			lines : false,
			autoScroll : true,
			collapseFirst : true,
			collapsed : true,
			root : new Ext.tree.TreeNode()
		});
		Ext.sss.DesktopActionExplorer.superclass.constructor.call(this, config);
		if (this.isCollapseEvent) {
			this.addCollapseEvent('collapse', true);
			this.addCollapseEvent('expand', false);
		}
	},
	addCollapseEvent : function(name, isCollapse) {
		this.addListener(name, this.desktop.createHandelServerAction({
			xtype : 'ajax',
			componentId : 'collapse',
			eventName : 'Click',
			isWait : false,
			actionPattern : this.desktop.modulePattern,
			params : {
				collapse : isCollapse
			}
		}));
	},
	addAction : function(module) {
		module.cls = "module-label";
		var node = new Ext.tree.TreeNode(module);
		node.addListener("Click", module.handler);
		this.root.appendChild(node);
	}
});
Ext.reg('DesktopActionExplorer', Ext.sss.DesktopActionExplorer);
Ext.sss.DesktopMenuBar = Ext
		.extend(
				Ext.Panel,
				{
					tplItem : ' <img  src ="{0}" width="18" height="18" align="middle" style="vertical-align:middle;margin: 0px 2px 2px 0px;"/><span class=" x-btn-text" style="vertical-align:middle" id="{2}">{1}</span>  ',
					constructor : function(config) {
						_this = this;
						this.menuBar = new Ext.Toolbar({
							cls : "tool-bar-menu"
						});
						var imageUrl = config.desktop.resolveUrl("logo", "load", "res") + "?expression=$PS[file]";
						var sociteHtml = String.format(this.tplItem, imageUrl, config.desktop.sociteLabel, "soc", "left");
						var clockHtml = String.format(this.tplItem, "images/clock.png", "", "clock", "right");
						this.toolsBar = new Ext.Toolbar({
							itemId : "barInfo",
							cls : "info-Bar"
						});
						Ext.apply(config, {
							region : "north",
							margins : "0 3 0 3",
							autoHeight : true,
							items : [ new Ext.Panel({
								bodyStyle : "display:none !important;",
								tbar : _this.menuBar,
								bbar : _this.toolsBar
							}) ]
						});
						Ext.sss.DesktopMenuBar.superclass.constructor.call(this, config);
					},
					addAction : function(action) {
						if (this.toolsBar) {
							this.toolsBar.addItem(action);
						}
					},
					addLabelAction : function(label) {
						if (this.toolsBar) {
							this.toolsBar.addItem({
								xtype : "label",
								html : String.format("<b class='label-Action'> {0}</b>", label)
							});
						}
					},
					addMenu : function(menu) {
						return this.menuBar.addItem(menu);
					},
					addToolsBarItem : function(toolsBarItem) {
						if (this.toolsBar) {
							this.toolsBar.addItem(toolsBarItem);
						}
					}
				});
Ext.reg('desktopMenuBar', Ext.sss.DesktopMenuBar);
Ext.sss.DesktopStatusBar = Ext.extend(Ext.ux.StatusBar, {
	constructor : function(config) {
		Ext.apply(config, {
			defaultText : "&nbsp;",
			id : "right-statusbar",
			statusAlign : "left",
			region : "south"
		});
		Ext.sss.DesktopStatusBar.superclass.constructor.call(this, config);
	},
	addLabel : function(statusLabel) {
		this.addItem({
			text : statusLabel,
			id : "right-statusbar",
			iconCls : 'icon-info-bar'
		});
	}
});
Ext.reg('desktopStatusBar', Ext.sss.DesktopStatusBar);
Ext.sss.DesktopContainer = Ext.extend(Ext.FormPanel, {
	editorGrids : new Array(),
	listenerContainer : new Object(),
	constructor : function(config) {
		Ext.apply(config, {
			region : "center",
			autoScroll : true,
			fileUpload : true,
			bodyStyle : "background:url(./images/fondEcr.jpg)no-repeat top left; background-size:100% 100%;padding:10px;",
			deferredRender : false,
			margins : "0 3 0 " + config.marg
		});
		if (config.isDisplayToolbar) {
			this.toolbar = new Ext.Toolbar({
				buttonAlign : 'right',
				desktop : config.desktop
			});
			config.tbar = this.toolbar;
		}
		Ext.sss.DesktopContainer.superclass.constructor.call(this, config);
	},
	addToolItem : function(action) {
		if (this.toolbar)
			this.toolbar.add(action);
	},
	addListenerContainer : function(name, handler) {
		this.getForm().addListener(name, handler);
	},
	addEditorGrid : function(id) {
		this.editorGrids.push(id);
	},
	getEditorGrids : function() {
		return this.editorGrids;
	},
	isFirstFocusFieldId : function() {
		return Ext.isDefined(this.firstFocusFieldId);
	},
	setFirstFocusFieldId : function(firstFocusFieldId) {
		this.firstFocusFieldId = firstFocusFieldId;
	},
	updateDataForm : function(response, options, fct) {
		var object = Ext.util.JSON.decode(response.responseText);
		if (!object.success && object.message) {
			this.alert(object.message, "info");
		} else {
			this.getForm().setValues(object.data);
			for (var index = 0; index < this.editorGrids.length; index++) {
				this.getCmp(this.editorGrids[index]).getStore().load();
			}
			if (fct)
				fct.call(this, object);
		}
		if (object.script) {
			eval(object.script);
		}
	},
	intNavBar : function(config) {
		this.desktop.desktopContainer.toolbar.addItem("->");
		this.doActionBtn = {};
		this.desktop.navToolbarId = config['id'];
		this.addActionButton(config.doFirstActionConfig);
		this.addActionButton(config.doPreviousActionConfig);
		this.desktop.desktopContainer.toolbar.addItem(new Ext.Toolbar.Separator());
		this.addActionButton(config.doNextActionConfig);
		this.addActionButton(config.doLastActionConfig);
		this.desktop.desktopContainer.toolbar.addItem(new Ext.Toolbar.Separator());
	},
	addActionButton : function(actionConfig) {
		var actionButton = new Ext.Button({
			disabled : actionConfig.disabled,
			doActionBtn : this.doActionBtn,
			handler : this.desktop.createHandelServerAction({
				'xtype' : 'ajax',
				'eventName' : actionConfig.action,
				'componentId' : this.desktop.navToolbarId,
				'success' : function(response, options) {
					navBarAction(response, options);
				}
			}),
			'hidden' : false,
			tooltip : actionConfig.label,
			'icon' : actionConfig.icon
		});
		this.doActionBtn[actionConfig.action] = actionButton;
		this.desktop.desktopContainer.toolbar.addItem(actionButton);
	},
	initFields : function() {
		if (this.firstFocusFieldId && Ext.getCmp(this.firstFocusFieldId)) {
			if (!Ext.getCmp(this.firstFocusFieldId).getStore) {
				var firstFocusField = Ext.getCmp(this.firstFocusFieldId);
				firstFocusField.setEditFocusFirst();
			}
		}
	},
	getCmp : function(id) {
		return Ext.getCmp(id);
	},
	getStore : function(id) {
		return Ext.StoreMgr.lookup(id);
	},
	alert : function(message, title, focusField) {
		var icon = Ext.MessageBox.INFO;
		Ext.Msg.show({
			msg : message,
			title : title,
			icon : icon,
			buttons : Ext.MessageBox.OK
		});
		if (focusField && this.getCmp(this.focusField)) {
			this.getCmp(this.focusField).focus();
		} else if (this.firstFocusFieldId && this.getCmp(this.firstFocusFieldId)) {
			this.getCmp(this.firstFocusFieldId).focus();
		}
	}
});
Ext.reg('desktopContainer', Ext.sss.DesktopContainer);
Ext.sss.Dialog = Ext.extend(Ext.Window, {
	actionLoad : false,
	autoScroll : true,
	closeAction : 'hide',
	modal : true,
	isOpen : false,
	callbackFunction : undefined,// el, success ,response ,options
	dynamique : true,
	constructor : function(config) {
		var regions = new Array();
		this.desktopContainer = new Ext.sss.DesktopContainer({
			desktop : this,
			marg : 1,
			layout : "fit"
		});
		this.panelContainer = new Ext.Panel({
			border : false,
			desktop : this
		});
		this.desktopContainer.add(this.panelContainer);
		delete config.layout;
		delete config.layoutConfig;
		regions.push(this.desktopContainer);
		config = Ext.applyIf(config, {
			layout : "border",
			listeners : Ext.applyIf(config.listeners || {}, {
				show : function() {
					var submitParams = this.actionLoad.getSubmitParams();
					if (!submitParams) {
						this.actionLoad.desktop.alert(this.actionLoad.invalidForm, "");
						this.hide();
						return;
					}
					if (!this.dynamique && this.isOpen) {
						if (Ext.isFunction(callbackFunction))
							this.callbackFunction.call(this, this.getEl());
						return;
					}
					this.isOpen = true;
					this.panelContainer.removeAll();
					this.panelContainer.load({
						url : this.actionLoad.getUrl(),
						params : submitParams,
						discardUrl : false,
						callback : this.callbackFunction,
						nocache : false,
						text : this.actionLoad.waitMsg,
						scripts : true
					});
				}
			})
		});
		config.items = regions;
		Ext.sss.Dialog.superclass.constructor.call(this, config);
	},
	addRequestParam : function(name, value) {
		this.actionLoad.addParam(name, value);
	},
	addRequestField : function(name) {
		this.actionLoad.addParamField(name);
	},
	addToContainer : function(item) {
		this.panelContainer.add(item);
	},
	addMessage : function(key, messageValue, messageType) {
		MessageManager.setMessage(key, messageValue, messageType);
	},
	getMessage : function(key) {
		return MessageManager.getMessage(key);
	},
	getFormattedMessage : function(key) {
		var args = Ext.toArray(arguments, 1);
		return MessageManager.formatMessage(key, args);
	},
	addListenerContainer : function(name, handler) {
		this.desktopContainer.addListenerContainer(name, handler);
	},
	getEditorGrids : function() {
		return this.desktopContainer.getEditorGrids();
	},
	setFirstFocusFieldId : function(firstFocusFieldId) {
		this.desktopContainer.setFirstFocusFieldId(firstFocusFieldId);
	},
	isFirstFocusFieldId : function() {
		return this.desktopContainer.isFirstFocusFieldId();
	},
	addEditorGrid : function(id) {
		this.desktopContainer.addEditorGrid(id);
	},
	updateDataForm : function(response, options, fct) {
		this.desktopContainer.updateDataForm(response, options, fct);
	},
	createServerAction : function(action) {
		action.desktop = this;
		action = Ext.sss.ActionMgr.create(action);
		return action;
	},
	createHandelServerAction : function(action) {
		action = this.createServerAction(action);
		return function() {
			action.run();
		}.createDelegate(this);
	},
	createStyleSheet : function(cssText, id) {
		return this.desktop.createStyleSheet(cssText, id);
	},
	createToolSheet : function(icon, id) {
		return this.desktop.createToolSheet(icon, id);
	},
	intNavBar : function(config) {
		return this.desktopContainer.intNavBar(config);
	},
	doInitDesktop : function() {
		this.desktopContainer.initFields();
		this.doLayout();
		Ext.iterate(this.onStartUpAction, function(onstart, index, allItems) {
			var cmp = this.getCmp(onstart.item);
			if (cmp) {
				cmp.fireEvent(onstart.action, cmp);
			}
		}, this);
	},
	getCmp : function(id) {
		return this.desktopContainer.getCmp(id);
	},
	getStore : function(id) {
		return this.desktopContainer.getStore(id);
	},
	getForm : function() {
		return this.desktopContainer.getForm();
	},
	alert : function(message, title) {
		this.desktopContainer.alert(message, title);
	},
	resolveUrl : function(componentId, eventName, pattern) {
		return this.desktop.resolveUrl(componentId, eventName, pattern);
	}
});
Ext.sss.Desktop = Ext.extend(Ext.Viewport, {
	acessPattern : "acess",
	toolBarPattern : "toolBar",
	modulePattern : "module",
	menuPattern : "menu",
	actionBarPattern : "actionBar",
	actionPattern : "action",
	viewPattern : "view",
	rendered : false,
	layout : "border",
	onStartUpAction : [],
	menuCont : 0,
	desktopEl : Ext.getBody(),
	constructor : function(config) {
		var regions = new Array();
		this.sociteLabel = config.sociteLabel;
		this.path = config.path;
		var marg = config.isDisplayedModuleBar ? 0 : 3;
		this.desktopContainer = new Ext.sss.DesktopContainer({
			desktop : this,
			layout : config.appContainerLayout,
			layoutConfig : config.appContainerLayoutConfig,
			marg : marg,
			isDisplayToolbar : config.isDisplayToolbar,
			isDisplayedMenuBar : config.isDisplayedMenuBar,
			firstFocusFieldId : config.firstFocusFieldId
		});
		regions.push(this.desktopContainer);
		if (config.isDisplayedModuleBar) {
			this.desktopModuleExplorer = new Ext.sss.DesktopActionExplorer({
				desktop : this,
				title : config.titelModule,
				collapsed : config.collapsedModule,
				region : "west",
				collapsible : true,
				margins : "0 0 0 3",
				isCollapseEvent : true
			});
			regions.push(this.desktopModuleExplorer);
		}
		if (config.isShortcutMenuExplorer) {
			this.desktopShortcutMenuExplorer = new Ext.sss.DesktopActionExplorer({
				desktop : this,
				title : config.titelShortcutMenu,
				region : "east",
				collapsible : false,
				margins : "0 3 0 0",
				isCollapseEvent : false
			});
			regions.push(this.desktopShortcutMenuExplorer);
		}
		if (config.isDisplayedMenuBar) {
			this.desktopMenuBar = new Ext.sss.DesktopMenuBar({
				desktop : this,
				isDisplayedToolbar : config.isDisplayedToolbar,
				labelsAction : config.labelsAction,
				isDisplayedActionBar : config.isDisplayedActionBar,
				isAction : config.isAction
			});
			regions.push(this.desktopMenuBar);
		}
		if (config.isDisplayedStatusBar) {
			this.desktopStatusBar = new Ext.sss.DesktopStatusBar({
				desktop : this
			});
			regions.push(this.desktopStatusBar);
		}
		config.items = regions;
		Ext.sss.Desktop.superclass.constructor.call(this, config);
	},
	addMenu : function(menu) {
		if (this.desktopMenuBar) {
			menu = this.desktopMenuBar.addMenu(menu);
			this.menuCont++;
			this.addKeyValue("a", function() {
				menu.fireEvent('activate');
			});
		}
	},
	addKeyValue : function(key, fn, ctrl) {
		if (!Ext.isDefined(ctrl))
			ctrl = true;
		var kValue = {
			key : key,
			ctrl : ctrl,
			stopEvent : true,
			fn : fn,
			scope : this
		};
		if (!this.desktopKeyMap)
			this.desktopKeyMap = new Ext.KeyMap(document, kValue);
		else
			this.desktopKeyMap.addBinding(kValue);
	},
	addToolsBarItem : function(toolsBarItem) {
		if (this.desktopMenuBar) {
			this.desktopMenuBar.addToolsBarItem(toolsBarItem);
		}
	},
	addModule : function(module) {
		if (this.desktopModuleExplorer)
			this.desktopModuleExplorer.addAction(module);
	},
	addShortcutMenu : function(shortcutMenu) {
		if (this.desktopShortcutMenuExplorer)
			this.desktopShortcutMenuExplorer.addAction(shortcutMenu);
	},
	addStartUpAction : function(item, action) {
		this.onStartUpAction.push({
			item : item,
			action : action
		});
	},
	addAction : function(action) {
		if (this.desktopMenuBar)
			this.desktopMenuBar.addAction(action);
	},
	addLabelAction : function(label) {
		this.desktopMenuBar.addLabelAction(label);
	},
	addToContainer : function(item) {
		this.desktopContainer.add(item);
	},
	addMessage : function(key, messageValue, messageType) {
		MessageManager.setMessage(key, messageValue, messageType);
	},
	getMessage : function(key) {
		return MessageManager.getMessage(key);
	},
	getFormattedMessage : function(key) {
		var args = Ext.toArray(arguments, 1);
		return MessageManager.formatMessage(key, args);
	},
	addStatusLabel : function(statusLabel) {
		if (this.desktopStatusBar) {
			this.desktopStatusBar.addLabel(statusLabel);
		}
	},
	addListenerContainer : function(name, handler) {
		this.desktopContainer.addListenerContainer(name, handler);
	},
	getEditorGrids : function() {
		return this.desktopContainer.getEditorGrids();
	},
	setFirstFocusFieldId : function(firstFocusFieldId) {
		this.desktopContainer.setFirstFocusFieldId(firstFocusFieldId);
	},
	isFirstFocusFieldId : function() {
		return this.desktopContainer.isFirstFocusFieldId();
	},
	addEditorGrid : function(id) {
		this.desktopContainer.addEditorGrid(id);
	},
	updateDataForm : function(response, options, fct) {
		this.desktopContainer.updateDataForm(response, options, fct);
	},
	createServerAction : function(action) {
		action.desktop = this;
		action = Ext.sss.ActionMgr.create(action);
		return action;
	},
	createHandelServerAction : function(action) {
		action = this.createServerAction(action);
		return function() {
			action.run();
		}.createDelegate(this);
	},
	createStyleSheet : function(cssText, id) {
		Ext.util.CSS.createStyleSheet(cssText, id);
	},
	createToolSheet : function(icon, id) {
		this.createStyleSheet(".x-tool-" + id + " {cursor: pointer;background-image: url(" + icon + ") !important;background-position: center;background-repeat: no-repeat;}", id);
	},
	goToSheet : function(name) {
		Ext.util.CSS.swapStyleSheet('theme', "css/" + name + ".css");
	},
	addToolItem : function(action) {
		this.desktopContainer.addToolItem(action);
	},
	intNavBar : function(config) {
		return this.desktopContainer.intNavBar(config);
	},
	doInitDesktop : function() {
		this.addAction("-");
		if (this.desktopMenuBar) {
			var clockHtml = String.format(this.desktopMenuBar.tplItem, "images/clock.png", "", "clock", "right");
			// var clockMenu=new
			// Ext.Button({'icon':"images/clock.png",'id':"clock",'text':' '});
			this.addAction(clockHtml);
		}
		this.doLayout();
		this.desktopContainer.initFields();
		Ext.iterate(this.onStartUpAction, function(onstart, index, allItems) {
			var cmp = this.getCmp(onstart.item);
			if (cmp) {
				cmp.fireEvent(onstart.action, cmp);
			}
		}, this);
		if (this.desktopMenuBar) {
			var task = {
				run : function() {
					Ext.fly('clock').update(new Date().format('d/m/Y G:i:s'));
				},
				interval : 1000
			};
			var runner = new Ext.util.TaskRunner();
			runner.start(task);
		}
	},
	createSupervision : function(config) {
		var p = this.path.split('/');
		var configvar = {
			// contextPath : "/fwDev",
			contextPath : "/" + p[1],
			logLevel : 'warn',
			connectionBrokenHandler : function() {
			},
			connectionClosedHandler : function() {
				desktop.alert('Application ferme');
			}
		};
		Ext.apply(configvar, config || {});
		return new ss.data.Supervision(configvar);
	},
	getCmp : function(id) {
		return this.desktopContainer.getCmp(id);
	},
	getStore : function(id) {
		return this.desktopContainer.getStore(id);
	},
	getForm : function() {
		return this.desktopContainer.getForm();
	},
	alert : function(message, title) {
		this.desktopContainer.alert(message, title);
	},
	resolveUrl : function(componentId, eventName, pattern) {
		pattern = !pattern ? this.actionPattern : pattern;
		var url = this.path + "." + componentId + "." + eventName + "." + pattern;
		return url;
	}
});
Ext.reg('desktop', Ext.sss.Desktop);
var Message = new Object();
Message.prototype = {
	initialize : function(value, type) {
		this.setValue(value);
		this.setType(type);
	},
	setValue : function(value) {
		this.value = value;
	},
	getValue : function() {
		return this.value;
	},
	setType : function(type) {
		this.type = type;
	},
	getType : function() {
		return this.type;
	}
};
var MessageManager = {
	messages : new Object(),
	setMessage : function(key, messageValue, messageType) {
		this.messages[key] = new Message(messageValue, messageType);
	},
	getMessage : function(key) {
		var messageExpression = this.messages[key].getValue();
		return messageExpression;
	},
	getMessages : function() {
		return this.messages;
	},
	format : function(key) {
		var args = Ext.toArray(arguments, 1);
		return String.format(this.getMessage(key), args);
	}
};
