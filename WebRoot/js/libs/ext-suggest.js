function totalRow(v, params, data, msg) {
	msg = String.format(msg, v);
	params.attr = 'ext:qtip="' + msg + ' "';
	return msg;
}
function totalCol(v, params, data) {
	params.attr = 'ext:qtip=Total';
	return smartsoft.Number.getAsString(v);
}
function defaultSuggestHandlerValue(record, option) {
	var fieldRef = this.getLinkedField(option);
	if (!fieldRef)
		return;
	if (!record)
		return fieldRef.clearValue();
	var displayaValue = record.data[option.dataIndex];
	if (fieldRef.setManuelValue) {
		fieldRef.setManuelValue(this.getValue(), displayaValue);
		fieldRef.clearInvalid();
	} else {
		fieldRef.setValue(displayaValue);
		fieldRef.clearInvalid();
	}
}
function navBarAction(response, options) {
	desktop.updateDataForm(response, options, function(object) {
		var isFirst = (object.index == 0);
		var isLast = (object.size - 1 == object.index);
		this.doActionBtn["doFirst"].setDisabled(isFirst);
		this.doActionBtn["doNext"].setDisabled(isLast);
		this.doActionBtn["doPrevious"].setDisabled(isFirst);
		this.doActionBtn["doLast"].setDisabled(isLast);
		if (object.updateDataForm) {
			eval(object.updateDataForm + "(object);");
		}
		var imageView = document.getElementById("imageView");
		if (imageView) {
			var srs = imageView.src;
			if (srs.indexOf("&rand") > 0)
				srs = srs.substring(0, srs.indexOf("&rand"));
			imageView.src = srs + "&rand=" + Math.random();
		}
		var mailView = Ext.getCmp("mailView");
		if (mailView) {
			mailView.update(objectRes.data.mailViewInput);
		}
		var historys = Ext.getCmp("historys");
		if (historys) {
			historys.getStore().reload();
		}
	});
};
 
Ext.namespace('sss.ext');
sss.ext.LinkButton = Ext.extend(Ext.BoxComponent, {
	extend : 'Ext.Component',
	xtype : 'linkbutton',
	autoEl : 'a',
	renderTpl : '<a href=\"javascript:;\" id="{id}-btnEl">{text}</a>',
	config : {
		text : '',
		handler : function() {
		}
	},
	initComponent : function() {
		var me = this;
		me.callParent(arguments);
		this.renderData = {
			text : this.getText()
		};
	},
	onRender : function(ct, position) {
		var me = this, btn;
		me.addChildEls('btnEl');
		me.callParent(arguments);
		btn = me.btnEl;
		me.mon(btn, 'click', me.onClick, me);
	},
	onClick : function(e) {
		var me = this;
		if (me.preventDefault || (me.disabled && me.getHref()) && e) {
			e.preventDefault();
		}
		if (e.button !== 0) {
			return;
		}
		if (!me.disabled) {
			me.fireHandler(e);
		}
	},
	fireHandler : function(e) {
		var me = this, handler = me.handler;
		me.fireEvent('click', me, e);
		if (handler) {
			handler.call(me.scope || me, me, e);
		}
	}
});
sss.ext.Legends = Ext.extend(Ext.BoxComponent, {
	colorField : 'color',
	labelField : 'label',
	initComponent : function() {
		sss.ext.Legends.superclass.initComponent.call(this);
		this.tpl = new Ext.XTemplate([ '<div class="wax-legend"><div class="legend-scale"> <tpl for="."><ul class="legend-labels"><li><span style="background:{' + this.colorField + '};"></span>{'
				+ this.labelField + '}</li></ul></tpl></div></div>' ]);
		this.store = Ext.StoreMgr.lookup(this.store);
	},
	collectData : function(records, startIndex) {
		var r = [];
		for (var i = 0, len = records.length; i < len; i++) {
			r[r.length] = records[i].data;
		}
		return r;
	},
	onRender : function(ct, position) {
		sss.ext.Legends.superclass.onRender.call(this, ct, position);
	},
	afterRender : function() {
		sss.ext.Legends.superclass.afterRender.call(this);
		if (this.store) {
			this.bindStore(this.store, true);
		}
	},
	bindStore : function(store, initial) {
		if (!initial && this.store) {
			if (store !== this.store && this.store.autoDestroy) {
				this.store.destroy();
			} else {
				this.store.un("beforeload", this.refresh, this);
				this.store.un("datachanged", this.refresh, this);
				this.store.un("add", this.refresh, this);
				this.store.un("remove", this.refresh, this);
				this.store.un("update", this.refresh, this);
				this.store.un("clear", this.refresh, this);
			}
			if (!store) {
				this.store = null;
			}
		}
		if (store) {
			store = Ext.StoreMgr.lookup(store);
			store.on({
				scope : this,
				beforeload : this.refresh,
				datachanged : this.refresh,
				add : this.refresh,
				remove : this.refresh,
				update : this.refresh,
				clear : this.refresh
			});
		}
		this.store = store;
		if (store) {
			this.refresh();
		}
	},
	refresh : function() {
		var data = [];
		if (this.store) {
			data = this.collectData(this.store.getRange());
		}
		if (!data) {
			data = [];
		}
		this.tpl.overwrite(this.el, data);
	}
});
Ext.ux.NavToolbar = Ext.extend(Ext.Toolbar, {
	initComponent : function() {
		this.addItem("->");
		this.doActionBtn = {};
		this.addActionButton(this.doFirstActionConfig);
		this.addActionButton(this.doPreviousActionConfig);
		this.addItem(new Ext.Toolbar.Separator());
		this.addActionButton(this.doNextActionConfig);
		this.addActionButton(this.doLastActionConfig);
	},
	addActionButton : function(actionConfig) {
		var actionButton = new Ext.Button({
			disabled : actionConfig.disabled,
			handler : this.doActionHandel.createCallback(actionConfig.action, this),
			'hidden' : false,
			tooltip : actionConfig.label,
			'icon' : actionConfig.icon
		});
		this.doActionBtn[actionConfig.action] = actionButton;
		this.addItem(actionButton);
	},
	doActionHandel : function(action, scope, el, e) {
		scope.fireEvent(action, scope, el, e);
	}
});
Ext.ux.Suggest = Ext.extend(Ext.form.ComboBox, {
	dependecyField : {},
	typeAhead : false,
	mode : "local",
	triggerAction : "all",
	forceSelection : true,
	selectOnFocus : false,
	suggestOption : false,
	filterField : undefined,
	matchChar : /^[-\sa-zA-Z]+$/,
	viewConfig : {
		forceFit : true
	},
	loadMask : {
		'msg' : 'Loading...'
	},
	itemSelectorDefauld : 'suggest-item',
	headColTemp : '<th class="listCorrHead" width="{0}">{1}</th>',
	bodyColTemp : '<td class="listPoperty" align="{0}">{{1}}</td>',
	tableTemp : '<table class="listCorr"><thead><tr>{0}</tr></thead><tbody><tpl for="."><tr class="{2}">{1}{excerpt}</tr></tpl></tbody></table>',
	constructor : function(config) {
		if (!config.isCombo) {
			this.hideTrigger = true;
			this.itemSelector = "tr." + this.itemSelectorDefauld;
			var mni = 0;
			var thead = '';
			var tbody = '';
			for (var i = 0; i < config.linkedValues.length; i++) {
				mni = mni + config.linkedValues[i].width;
				if (config.linkedValues[i].dispaly) {
					if (!this.filterField && i == 1)
						this.filterField = config.linkedValues[i].dataIndex;
					thead = thead + String.format(this.headColTemp, config.linkedValues[i].width, config.linkedValues[i].header);
					tbody = tbody + String.format(this.bodyColTemp, config.linkedValues[i].align, config.linkedValues[i].dataIndex);
				}
			}
			this.tpl = String.format(this.tableTemp, thead, tbody, this.itemSelectorDefauld);
			this.minListWidth = mni;
			this.tpl = new Ext.XTemplate(this.tpl);
		}
		if (config.dataIndexDisplayColumn) {
			if (!config.linkedValues)
				config.linkedValues = [];
			config.linkedValues[config.linkedValues.length] = {
				'handlerValue' : function(record, o, combo) {
					if (combo.eventColumn && record)
						combo.eventColumn.record.set(combo.dataIndexDisplayColumn, record.get(combo.displayField));
				},
				'columnRef' : 1
			};
		}
		Ext.ux.Suggest.superclass.constructor.call(this, config);
		this.selectOnFocus = false;
		this.addListener("change", function(filed, newValue, oldValue) {
			filed.setLinkedValue(newValue);
		});
	},
	setValue : function(value) {
		var r = this.findRecord(this.valueField, value);
		if (r) {
			displayText = r.data[this.displayField];
			this.setManuelValue(value, displayText);
			this.setLinkedValue(value);
		} else {
			displayText = !this.eventColumn ? this.displayText : this.eventColumn.record.get(this.dataIndexDisplayColumn);
			if (!displayText)
				displayText = value;
			this.setManuelValue(value, displayText);
		}
	},
	getDisplayDataValue : function(value) {
		var r = this.findRecord(this.valueField, value);
		var displayText;
		if (r) {
			displayText = r.data[this.displayField];
		} else {
			displayText = !this.eventColumn ? this.displayText : this.eventColumn.record.get(this.dataIndexDisplayColumn);
			if (!displayText)
				displayText = value;
		}
		return displayText;
	},
	initValue : function() {
		displayText = !this.eventColumn ? this.displayText : this.eventColumn.record.get(this.dataIndexDisplayColumn);
		this.setManuelValue(this.value, displayText);
	},
	setManuelValue : function(value, displayText) {
		this.lastSelectionText = displayText;
		if (this.hiddenField) {
			this.hiddenField.value = Ext.value(value, '');
		}
		Ext.form.ComboBox.superclass.setValue.call(this, displayText);
		this.clearInvalid();
		this.value = value;
		return this;
	},
	getLinkedColumn : function(index) {
		if (!this.eventColumn)
			return;
		var columns = this.eventColumn.grid.getColumnModel().config;
		return columns[index];
	},
	getLinkedColumnField : function(index) {
		var col = this.getLinkedColumn(index);
		if (col)
			return col.editor;
	},
	getLinkedField : function(linkedOption) {
		var fieldRef;
		if (linkedOption.fieldRef) {
			return Ext.getCmp(linkedOption.fieldRef);
		} else {
			return this.getLinkedColumnField(linkedOption.columnRef);
		}
	},
	doQuery : function(q, forceAll) {
		q = Ext.isEmpty(q) ? '' : q;
		var qe = {
			query : q,
			forceAll : forceAll,
			combo : this,
			cancel : false
		};
		if (this.fireEvent('beforequery', qe) === false || qe.cancel) {
			return false;
		}
		q = qe.query;
		forceAll = qe.forceAll;
		if (forceAll === true || (q.length >= this.minChars)) {
			if (this.lastQuery !== q) {
				this.lastQuery = q;
				if (this.mode == 'local') {
					this.selectedIndex = -1;
					if (forceAll) {
						this.store.clearFilter();
					} else {
						this.store.filter(this.displayField, q);
						if (this.suggestOption && this.store.getCount() == 0) {
							this.store.clearFilter();
							this.store.filter(this.filterField, q);
						}
					}
					this.onLoad();
				} else {
					this.store.baseParams[this.queryParam] = q;
					this.store.load({
						params : this.getParams(q)
					});
					this.expand();
				}
			} else {
				this.selectedIndex = -1;
				this.onLoad();
			}
		}
	},
	selectByValue : function(v, scrollIntoView) {
		if (!Ext.isEmpty(v, true)) {
			var r = this.findRecord(this.valueField || this.displayField, v);
			if (r) {
				this.select(this.store.indexOf(r), scrollIntoView);
				return true;
			} else {
				if (this.suggestOption)
					r = this.findRecord(this.filterField, v);
				if (r) {
					this.select(this.store.indexOf(r), scrollIntoView);
					return true;
				}
			}
		}
		return false;
	},
	setLinkedValue : function(value) {
		var record = this.findRecord(this.valueField, value);
		for (var i = 0; i < this.linkedValues.length; i++) {
			var option = this.linkedValues[i];
			if (option.handlerValue) {
				option.handlerValue.call(this, record, option, this);
			}
		}
		return this;
	}
});
Ext.reg('suggest', Ext.ux.Suggest);
Ext.ux.form.LovCombo = Ext.extend(Ext.form.ComboBox, {
	checkField : 'checked',
	separator : ',',
	showSelectAll : true,
	resizable : true,
	initComponent : function() {
		Ext.ux.form.LovCombo.superclass.initComponent.call(this);
		var checkTpl = '<img src="' + Ext.BLANK_IMAGE_URL + '" class="ux-lovcombo-icon ux-lovcombo-icon-{[values.' + this.checkField + '?"checked":"unchecked"]}">';
		var tbody = '';
		if (!this.linkedValues)
			this.linkedValues = [];
		var w = 0;
		for (var i = 0; i < this.linkedValues.length; i++) {
			if (this.linkedValues[i].dispaly) {
				w = w + this.linkedValues[i].width;
			}
		}
		this.minListWidth = w;
		for (var i = 0; i < this.linkedValues.length; i++) {
			if (this.linkedValues[i].dispaly) {
				var wc = parseInt((this.linkedValues[i].width * 100 / w));
				tbody = tbody + '<td class ="headColSuggest"  width="' + wc + '%">';
				tbody = tbody + (i == 0 ? checkTpl : '');
				tbody = tbody + '<div class="ux-lovcombo-item-text">{' + (this.linkedValues[i].dataIndex || 'text') + '}</div></td>';
				if (!this.filterField && i == 1)
					this.filterField = this.linkedValues[i].dataIndex;
			}
		}
		this.tpl = [ '<table class="headTableBodySuggest"><tpl for=".">', '<tr class="x-combo-list-item">', tbody, '</tr>', '</tpl></table>' ].join("");
		this.on({
			scope : this,
			expand : function() {
				this.getValue() && this.setValue(this.getValue());
			}
		});

		this.onLoad = this.onLoad.createSequence(function() {
			if (this.el) {
				var v = this.el.dom.value;
				this.el.dom.value = v;
			}
		});
		this.store.on("load", function() {
			this.getValue() && this.setValue(this.getValue());
		}, this);
	},
	initEvents : function() {
		Ext.ux.form.LovCombo.superclass.initEvents.apply(this, arguments);
		this.keyNav.tab = false;
	},
	clearValue : function() {
		this.value = '';
		this.setRawValue(this.value);
		this.store.clearFilter();
		this.store.each(function(r) {
			r.set(this.checkField, false);
		}, this);
		if (this.hiddenField) {
			this.hiddenField.value = '';
		}
		this.applyEmptyText();
	},
	getCheckedDisplay : function() {
		var re = new RegExp(this.separator, "g");
		return this.getCheckedValue(this.displayField).replace(re, this.separator + ' ');
	},
	getCheckedValue : function(field) {
		field = field || this.valueField;
		var c = [];
		var snapshot = this.store.snapshot || this.store.data;
		snapshot.each(function(r) {
			if (r.get(this.checkField)) {
				c.push(r.get(field));
			}
		}, this);
		return c.join(this.separator);
	},

	onBeforeQuery : function(qe) {
		qe.query = qe.query.replace(new RegExp(this.getCheckedDisplay() + '[ ' + this.separator + ']*'), '');
	},

	onSelect : function(record, index) {
		if (this.fireEvent('beforeselect', this, record, index) !== false) {
			record.set(this.checkField, !record.get(this.checkField));
			if (this.store.isFiltered()) {
				this.doQuery(this.allQuery);
			}
			this.setValue(this.getCheckedValue());
			this.fireEvent('select', this, record, index);
		}
	},
	setValue : function(v) {
		if (v) {
			v = '' + v;
			if (this.valueField) {
				this.store.clearFilter();
				this.store.each(function(r) {
					var checked = !(!v.match('(^|' + this.separator + ')' + RegExp.escape(r.get(this.valueField)) + '(' + this.separator + '|$)'));
					r.set(this.checkField, checked);
				}, this);
				this.value = this.getCheckedValue() || v;
				this.setRawValue(this.store.getCount() > 0 ? this.getCheckedDisplay() : this.value);
				if (this.hiddenField) {
					this.hiddenField.value = this.value;
				}
			} else {
				this.value = v;
				this.setRawValue(v);
				if (this.hiddenField) {
					this.hiddenField.value = v;
				}
			}
			if (this.el) {
				this.el.removeClass(this.emptyClass);
			}
			if (this.selectall) {
				if (this.getCheckedValue().split(",").length == this.store.getCount()) {
					this.selectall.replaceClass("ux-combo-selectall-icon-unchecked", "ux-combo-selectall-icon-checked");
				} else {
					this.selectall.replaceClass("ux-combo-selectall-icon-checked", "ux-combo-selectall-icon-unchecked");
				}
			}
		} else {
			this.clearValue();
		}

	},
	initList : function() {
		if (!this.list) {
			var cls = 'x-combo-list', listParent = Ext.getDom(this.getListParent() || Ext.getBody()), zindex = parseInt(Ext.fly(listParent).getStyle('z-index'), 10);

			if (!zindex) {
				zindex = this.getParentZIndex();
			}

			this.list = new Ext.Layer({
				parentEl : listParent,
				shadow : this.shadow,
				cls : [ cls, this.listClass ].join(' '),
				constrain : false,
				zindex : (zindex || 12000) + 5
			});

			var lw = this.listWidth || Math.max(this.wrap.getWidth(), this.minListWidth);
			this.list.setSize(lw, 0);
			this.list.swallowEvent('mousewheel');
			this.assetHeight = 10;
			if (this.syncFont !== false) {
				this.list.setStyle('font-size', this.el.getStyle('font-size'));
			}
			if (this.title) {
				this.header = this.list.createChild({
					cls : cls + '-hd',
					html : this.title
				});
				this.assetHeight += this.header.getHeight();
			}
			if (this.showSelectAll) {
				var tds = new Array();
				var w = 0;
				for (var i = 0; i < this.linkedValues.length; i++) {
					if (this.linkedValues[i].dispaly) {
						w = w + this.linkedValues[i].width;
					}
				}
				for (var i = 0; i < this.linkedValues.length; i++) {
					if (this.linkedValues[i].dispaly) {
						var wc = parseInt((this.linkedValues[i].width * 100 / w));
						if (i == 0)
							tds[i] = {
								tag : 'td',
								width : wc + "%",
								cls : 'headColSuggest',
								children : [ {
									tag : 'div',
									cls : cls + 'item ux-combo-selectall-icon-unchecked ux-combo-selectall-icon',
									html : this.linkedValues[i].header
								} ]
							};
						else
							tds[i] = {
								tag : 'td',
								width : wc + "%",
								cls : 'headColSuggest',
								html : this.linkedValues[i].header
							};
					}
				}
				var t = {
					tag : 'table',
					cls : "headTableHederSuggest",
					children : [ {
						tag : 'tr',
						children : tds
					} ]
				};
				this.selectall = this.list.createChild(t);
				this.selectall = this.selectall.query(".ux-combo-selectall-icon")[0];
				this.selectall = Ext.get(this.selectall);
				this.selectall.on("click", function(el) {
					if (this.selectall.hasClass("ux-combo-selectall-icon-checked")) {
						this.selectall.replaceClass("ux-combo-selectall-icon-checked", "ux-combo-selectall-icon-unchecked");
						this.deselectAll();
					} else {
						this.selectall.replaceClass("ux-combo-selectall-icon-unchecked", "ux-combo-selectall-icon-checked");
						this.selectAll();
					}
				}, this);
				this.assetHeight += this.selectall.getHeight();
			}
			this.innerList = this.list.createChild({
				cls : cls + '-inner'
			});
			this.mon(this.innerList, 'mouseover', this.onViewOver, this);
			this.mon(this.innerList, 'mousemove', this.onViewMove, this);
			this.innerList.setWidth(lw - this.list.getFrameWidth('lr'));
			if (this.pageSize) {
				this.footer = this.list.createChild({
					cls : cls + '-ft'
				});
				this.pageTb = new Ext.PagingToolbar({
					store : this.store,
					pageSize : this.pageSize,
					renderTo : this.footer
				});
				this.assetHeight += this.footer.getHeight();
			}
			if (!this.tpl) {
				this.tpl = '<tpl for="."><div class="' + cls + '-item">{' + this.displayField + '}</div></tpl>';
			}
			this.view = new Ext.DataView({
				applyTo : this.innerList,
				tpl : this.tpl,
				singleSelect : true,
				selectedClass : this.selectedClass,
				itemSelector : this.itemSelector || '.' + cls + '-item',
				emptyText : this.listEmptyText
			});
			this.mon(this.view, 'click', this.onViewClick, this);
			this.bindStore(this.store, true);
			if (this.resizable) {
				this.resizer = new Ext.Resizable(this.list, {
					pinned : true,
					handles : 'se'
				});
				this.mon(this.resizer, 'resize', function(r, w, h) {
					this.maxHeight = h - this.handleHeight - this.list.getFrameWidth('tb') - this.assetHeight;
					this.listWidth = w;
					this.innerList.setWidth(w - this.list.getFrameWidth('lr'));
					this.restrictHeight();
				}, this);
				this[this.pageSize ? 'footer' : 'innerList'].setStyle('margin-bottom', this.handleHeight + 'px');
			}
		}
	},

	doQuery : function(q, forceAll) {
		q = Ext.isEmpty(q) ? '' : q;
		var qe = {
			query : q,
			forceAll : forceAll,
			combo : this,
			cancel : false
		};
		if (this.fireEvent('beforequery', qe) === false || qe.cancel) {
			return false;
		}
		q = qe.query;
		forceAll = qe.forceAll;
		if (forceAll === true || (q.length >= this.minChars)) {
			if (this.lastQuery !== q) {
				this.lastQuery = q;
				if (this.mode == 'local') {
					this.selectedIndex = -1;
					if (forceAll) {
						this.store.clearFilter();
					} else {
						this.store.filter(this.displayField, q);
						if (this.suggestOption && this.store.getCount() == 0) {
							this.store.clearFilter();
							this.store.filter(this.filterField, q);
						}
					}
					this.onLoad();
				} else {
					this.store.baseParams[this.queryParam] = q;
					this.store.load({
						params : this.getParams(q)
					});
					this.expand();
				}
			} else {
				this.selectedIndex = -1;
				this.onLoad();
			}
		}
	},
	expand : function() {
		if (this.isExpanded() || !this.hasFocus) {
			return;
		}
		this.list.alignTo(this.wrap, this.listAlign);
		this.list.show();
		if (Ext.isGecko2) {
			this.innerList.setOverflow('auto'); // necessary for FF 2.0/Mac
		}
		Ext.getDoc().on({
			scope : this,
			mousewheel : this.collapseIf,
			mousedown : this.collapseIf
		});
		this.fireEvent('expand', this);
	},
	selectAll : function() {
		this.store.each(function(record) {
			record.set(this.checkField, true);
		}, this);
		this.setValue(this.getCheckedValue());
		this.doQuery(this.allQuery);
	},
	deselectAll : function() {
		this.clearValue();
	},
	assertValue : Ext.emptyFn,
	beforeBlur : Ext.emptyFn
});
Ext.reg('lovcombo', Ext.ux.form.LovCombo);

Ext.ux.NumberField = Ext.extend(Ext.form.TextField, {
	baseChars : "0123456789",
	decimalSeparator : ',',
	minValue : Number.NEGATIVE_INFINITY,
	maxValue : Number.MAX_VALUE,
	minText : "The minimum value for this field is {0}",
	maxText : "The maximum value for this field is {0}",
	nanText : "{0} is not a valid number",
	isMony : true,
	allowNegative : true,
	initComponent : function() {
		Ext.ux.NumberField.superclass.initComponent.call(this);
		this.baseChars = "0123456789 .";
	},
	initEvents : function() {
		var allowed = this.baseChars + '';
		if (this.isMony) {
			allowed += this.decimalSeparator;
		}
		if (this.allowNegative) {
			allowed += '-';
		}
		allowed = Ext.escapeRe(allowed);
		this.maskRe = new RegExp('[' + allowed + ']');
		Ext.form.NumberField.superclass.initEvents.call(this);
	},
	getValue : function() {
		var v = Ext.ux.NumberField.superclass.getValue.call(this);
		if (this.isMony) {
			v = smartsoft.Number.getAsObject(v);
			v = isNaN(v) ? '' : smartsoft.Number.getAsString(v);
		}
		return v;
	},
	setMinValue : function(value) {
		this.minValue = Ext.num(value, Number.NEGATIVE_INFINITY);
	},
	setMaxValue : function(value) {
		this.maxValue = Ext.num(value, Number.MAX_VALUE);
	},
	getErrors : function(value) {
		var errors = Ext.ux.NumberField.superclass.getErrors.apply(this, arguments);
		value = Ext.isDefined(value) ? value : this.processValue(this.getRawValue());
		if (value.length < 1) {
			return errors;
		}
		if (this.isMony) {
			value = smartsoft.Number.getAsObject(value);
		}
		if (isNaN(value)) {
			errors.push(String.format(this.nanText, value));
		}
		var num = value;
		if (num < this.minValue) {
			errors.push(String.format(this.minText, this.minValue));
		}
		if (num > this.maxValue) {
			errors.push(String.format(this.maxText, this.maxValue));
		}
		return errors;
	},
	setValue : function(v) {
		if (this.isMony) {
			v = Ext.isNumber(v) ? v : smartsoft.Number.getAsObject(v);
			v = isNaN(v) ? '' : smartsoft.Number.getAsString(v);
		}
		return Ext.ux.NumberField.superclass.setValue.call(this, v);
	},
	beforeBlur : function() {
		var v = this.getRawValue();
		if (this.isMony) {
			v = Ext.isNumber(v) ? v : smartsoft.Number.getAsObject(v);
			v = isNaN(v) ? '' : smartsoft.Number.getAsString(v);
		}
		if (!Ext.isEmpty(v)) {
			this.setValue(v);
		}
	}
});
/**
 * Table form layout
 */
Ext.namespace("Ext.ux.layout");
Ext.ux.layout.TableFormLayout = Ext.extend(Ext.layout.TableLayout, {
	monitorResize : true,
	setContainer : function() {
		Ext.layout.FormLayout.prototype.setContainer.apply(this, arguments);
		this.currentRow = 0;
		this.currentColumn = 0;
		this.cells = [];
	},
	renderItem : function(c, position, target) {
		if (c && !c.rendered) {
			var cell = Ext.get(this.getNextCell(c));
			cell.addClass("x-table-layout-column-" + this.currentColumn);
			Ext.layout.FormLayout.prototype.renderItem.call(this, c, 0, cell);
		}
	},
	getLayoutTargetSize : Ext.layout.AnchorLayout.prototype.getLayoutTargetSize,
	getTemplateArgs : Ext.layout.FormLayout.prototype.getTemplateArgs,
	onLayout : function(ct, target) {
		Ext.ux.layout.TableFormLayout.superclass.onLayout.call(this, ct, target);
		if (!target.hasClass("x-table-form-layout-ct")) {
			target.addClass("x-table-form-layout-ct");
		}
		var viewSize = this.getLayoutTargetSize(ct, target);
		var aw, ah;
		if (ct.anchorSize) {
			if (typeof ct.anchorSize == "number") {
				aw = ct.anchorSize;
			} else {
				aw = ct.anchorSize.width;
				ah = ct.anchorSize.height;
			}
		} else {
			aw = ct.initialConfig.width;
			ah = ct.initialConfig.height;
		}
		var cs = this.getRenderedItems(ct), len = cs.length, i, c, a, cw, ch, el, vs, boxes = [];
		var x, w, h, col, colWidth, offset;
		for (i = 0; i < len; i++) {
			c = cs[i];
			x = c.getEl().parent(".x-table-layout-cell");
			if (this.columnWidths) {
				col = parseInt(x.dom.className.replace(/.*x\-table\-layout\-column\-([\d]+).*/, "$1"));
				// get cell width (based on column widths)
				colWidth = 0, offset = 0;
				for (j = col; j < (col + (c.colspan || 1)); j++) {
					colWidth += this.columnWidths[j];
					offset += 10;
				}
				w = (viewSize.width * colWidth) - offset;
			} else {
				// get cell width
				w = (viewSize.width / this.columns) * (c.colspan || 1);
			}
			// set table cell width
			x.setWidth(w);
			// get cell width (-10 for spacing between cells) & height
			// to be base width of anchored component
			w = w - 10;
			h = x.getHeight();
			// If a child container item has no anchor and no specific
			// width, set the child to the default anchor size
			if (!c.anchor && c.items && !Ext.isNumber(c.width) && !(Ext.isIE6 && Ext.isStrict)) {
				c.anchor = this.defaultAnchor;
			}
			if (c.anchor) {
				a = c.anchorSpec;
				if (!a) { // cache all anchor values
					vs = c.anchor.split(' ');
					c.anchorSpec = a = {
						right : this.parseAnchor(vs[0], c.initialConfig.width, aw),
						bottom : this.parseAnchor(vs[1], c.initialConfig.height, ah)
					};
				}
				cw = a.right ? this.adjustWidthAnchor(a.right(w), c) : undefined;
				ch = a.bottom ? this.adjustHeightAnchor(a.bottom(h), c) : undefined;
				if (cw || ch) {
					boxes.push({
						comp : c,
						width : cw || undefined,
						height : ch || undefined
					});
				}
			}
		}
		for (i = 0, len = boxes.length; i < len; i++) {
			c = boxes[i];
			c.comp.setSize(c.width, c.height);
		}
	},
	parseAnchor : function(a, start, cstart) {
		if (a && a != "none") {
			var last;
			if (/^(r|right|b|bottom)$/i.test(a)) {
				var diff = cstart - start;
				return function(v) {
					if (v !== last) {
						last = v;
						return v - diff;
					}
				};
			} else if (a.indexOf("%") != -1) {
				var ratio = parseFloat(a.replace("%", "")) * .01;
				return function(v) {
					if (v !== last) {
						last = v;
						return Math.floor(v * ratio);
					}
				};
			} else {
				a = parseInt(a, 10);
				if (!isNaN(a)) {
					return function(v) {
						if (v !== last) {
							last = v;
							return v + a;
						}
					};
				}
			}
		}
		return false;
	},
	adjustWidthAnchor : function(value, comp) {
		return value - (comp.isFormField ? (comp.hideLabel ? 0 : this.labelAdjust) : 0);
	},
	adjustHeightAnchor : function(value, comp) {
		return value;
	},
	// private
	isValidParent : function(c, target) {
		return c.getPositionEl().up('table', 6).dom.parentNode === (target.dom || target);
	},
	getLabelStyle : Ext.layout.FormLayout.prototype.getLabelStyle,
	labelSeparator : Ext.layout.FormLayout.prototype.labelSeparator,
	trackLabels : Ext.layout.FormLayout.prototype.trackLabels,
	onFieldShow : Ext.layout.FormLayout.prototype.onFieldShow,
	onFieldHide : Ext.layout.FormLayout.prototype.onFieldHide
});
Ext.Container.LAYOUTS["tform"] = Ext.ux.layout.TableFormLayout;
Ext.ns("Ext.ux.form");
Ext.ux.form.Checkbox = Ext.extend(Ext.form.Checkbox, {
	submitOffValue : 'false',
	submitOnValue : 'true',
	focusClass : 'x-form-focus',
	onRender : function() {
		this.inputValue = this.submitOnValue;
		Ext.ux.form.Checkbox.superclass.onRender.apply(this, arguments);
		this.hiddenField = this.wrap.insertFirst({
			tag : 'input',
			type : 'hidden'
		});
		if (this.tooltip) {
			this.imageEl.set({
				qtip : this.tooltip
			});
		}
		this.updateHidden();
	},
	setValue : function(v) {
		v = this.convertValue(v);
		this.updateHidden(v);
		Ext.ux.form.Checkbox.superclass.setValue.apply(this, arguments);
	},
	updateHidden : function(v) {
		v = undefined !== v ? v : this.checked;
		v = this.convertValue(v);
		if (this.hiddenField) {
			this.hiddenField.dom.value = v ? this.submitOnValue : this.submitOffValue;
			this.hiddenField.dom.name = v ? '' : this.el.dom.name;
		}
	},
	convertValue : function(v) {
		return (v === true || v === 'true' || v == 1 || v === this.submitOnValue || String(v).toLowerCase() === 'on');
	}
});
// register xtype
Ext.reg('xcheckbox', Ext.ux.form.Checkbox);
Ext.ns('Ext.ux.form');
Ext.ux.form.ColorField = Ext.extend(Ext.form.TriggerField, {
	invalidText : "'{0}' is not a valid color - it must be in a the hex format (# followed by 3 or 6 letters/numbers 0-9 A-F)",
	triggerClass : 'x-form-color-trigger',
	defaultAutoCreate : {
		tag : "input",
		type : "text",
		size : "10",
		maxlength : "7",
		autocomplete : "off"
	},
	maskRe : /[#a-f0-9]/i,
	validateValue : function(value) {
		if (!Ext.ux.form.ColorField.superclass.validateValue.call(this, value)) {
			return false;
		}
		if (value.length < 1) { // if it's blank and textfield didn't
			// flag it then it's valid
			this.setColor('');
			return true;
		}
		var parseOK = this.parseColor(value);
		if (!value || (parseOK == false)) {
			this.markInvalid(String.format(this.invalidText, value));
			return false;
		}
		this.setColor(value);
		return true;
	},
	setColor : function(color) {
		if (color == '' || color == undefined) {
			if (this.emptyText != '' && this.parseColor(this.emptyText))
				color = this.emptyText;
			else
				color = 'transparent';
		}
		if (this.trigger) {
			this.el.setStyle({
				'background-color' : color
			});
			this.trigger.setStyle({
				'background-color' : color
			});
		} else {
			this.on('render', function() {
				this.setColor(color);
			}, this);
		}
	},
	validateBlur : function() {
		return !this.menu || !this.menu.isVisible();
	},
	getValue : function() {
		return Ext.ux.form.ColorField.superclass.getValue.call(this) || "";
	},
	setValue : function(color) {
		Ext.ux.form.ColorField.superclass.setValue.call(this, this.formatColor(color));
		this.setColor(this.formatColor(color));
	},
	// private
	parseColor : function(value) {
		return (!value || (value.substring(0, 1) != '#')) ? false : (value.length == 4 || value.length == 7);
	},
	// private
	formatColor : function(value) {
		if (!value || this.parseColor(value))
			return value;
		if (value.length == 3 || value.length == 6) {
			return '#' + value;
		}
		return '';
	},
	// private
	menuListeners : {
		select : function(e, c) {
			this.setValue(c);
		},
		show : function() { // retain focus styling
			this.onFocus();
		},
		hide : function() {
			this.focus.defer(10, this);
			var ml = this.menuListeners;
			this.menu.un("select", ml.select, this);
			this.menu.un("show", ml.show, this);
			this.menu.un("hide", ml.hide, this);
		}
	},
	onTriggerClick : function() {
		if (this.disabled) {
			return;
		}
		if (this.menu == null) {
			this.menu = new Ext.menu.ColorMenu();
		}
		this.menu.on(Ext.apply({}, this.menuListeners, {
			scope : this
		}));
		this.menu.show(this.el, "tl-bl?");
	}
});
Ext.reg('colorfield', Ext.ux.form.ColorField);
Ext.form.Field.override({
	hideWithLabel : true,
	selectOnFocus : true,
	setReadOnly : function(readOnly) {
		if (this.rendered) {
			this.el.dom.setAttribute("readOnly", readOnly);
			this.el.dom.readOnly = readOnly;
		} else {
			this.readOnly = readOnly;
		}
	},
	getReadOnly : function() {
		return this.rendered ? this.el.dom.readOnly : this.readOnly;
	},
	adjustWidth : function(tag, w) {
		if (typeof w === "number" && (Ext.isIE6 || !Ext.isStrict) && /input|textarea/i.test(tag) && !this.inEditor) {
			return w - 3;
		}
		return w;
	},
	hideNote : function() {
		if (!Ext.isEmpty(this.note, false) && this.noteEl) {
			this.noteEl.addClass("x-hide-" + this.hideMode);
		}
		if (this.noteAlign === "top" && this.label) {
			this.label.removeClass("x-top-note-label");
		}
	},
	showNote : function() {
		if (!Ext.isEmpty(this.note, false) && this.noteEl) {
			this.noteEl.removeClass("x-hide-" + this.hideMode);
		}
		if (this.noteAlign === "top" && this.label) {
			this.label.addClass("x-top-note-label");
		}
	},
	setNote : function(t, encode) {
		this.note = t;
		if (this.rendered) {
			this.noteEl.dom.innerHTML = encode !== false ? Ext.util.Format.htmlEncode(t) : t;
		}
	},
	setNoteCls : function(cls) {
		if (this.rendered) {
			this.noteEl.removeClass(this.noteCls);
			this.noteEl.addClass(cls);
		}
		this.noteCls = cls;
	},
	clear : function() {
		this.setValue("");
	},
	hideFieldLabel : function() {
		if (this.label && this.hideWithLabel) {
			var parent = this.getActionEl().parent(".x-form-item");
			if (!Ext.isEmpty(parent)) {
				parent.addClass("x-hide-" + this.hideMode);
			}
		}
	},
	showFieldLabel : function() {
		if (this.label && this.hideWithLabel) {
			var parent = this.getActionEl().parent(".x-form-item");
			if (!Ext.isEmpty(parent)) {
				parent.removeClass("x-hide-" + this.hideMode);
			}
		}
	},
	clearIndicator : function() {
		this.setIndicator("");
		this.setIndicatorCls("");
		this.setIndicatorIconCls("");
		this.setIndicatorTip("");
	},
	setIndicator : function(t, encode) {
		this.indicatorText = t;
		if (this.rendered && this.indicatorEl) {
			this.indicatorEl.dom.innerHTML = encode !== false ? Ext.util.Format.htmlEncode(t) : t;
		}
		this.initIndicator();
	},
	setEditFocus : function() {
		if (!this.disabled && !this.hidden) {
			this.focus();
		} else {
			var fieldObjectNext = Ext.getCmp(this.nextElementId);
			if (fieldObjectNext && fieldObjectNext.nextFieldEvent) {
				fieldObjectNext.setEditFocus();
				fieldObjectNext.nextFieldEvent();
			}
		}
	},
	setEditFocusFirst : function() {
		if (!this.disabled && !this.hidden) {
			this.focus();
		} else {
			var fieldObjectNext = Ext.getCmp(this.nextElementId);
			if (fieldObjectNext && fieldObjectNext.setEditFocusFirst) {
				fieldObjectNext.setEditFocusFirst();
			}
		}
	},
	nextFieldEvent : function() {
		var nexName = this.nextElementId;
		if (Ext.sss.StringUtils.startsWith(this.nextElementId, "#"))
			if (Ext.isFunction(eval(this.nextElementId.substring(1)))) {
				nexName = eval(this.nextElementId.substring(1))(this);
			}
		var fieldObjectNext = Ext.getCmp(nexName);
		if (!fieldObjectNext)
			return;
		if (Ext.isInstanceof(this, Ext.form.ComboBox)) {
			var r = this.findRecord(this.valueField || this.displayField, this.getValue());
			if (!r)
				this.clearValue();
		}
		if (this.isValid()) {
			var v = this.getValue();
			if (String(v) !== String(this.startValue)) {
				this.fireEvent('change', this, v, this.startValue);
				this.startValue = this.getValue();
			}
			if (Ext.isInstanceof(fieldObjectNext, Ext.form.ComboBox)) {
				storeCount = fieldObjectNext.getStore().getCount();
				nexValue = fieldObjectNext.getValue();
				nexAllowBlank = fieldObjectNext.allowBlank;
				var r = fieldObjectNext.findRecord(fieldObjectNext.valueField || fieldObjectNext.displayField, fieldObjectNext.getValue());
				if (!r) {
					fieldObjectNext.clearValue();
				}
				if (!nexAllowBlank && storeCount == 1) {
					rec = fieldObjectNext.getStore().getAt(0);
					fieldObjectNext.setValue(rec.data[fieldObjectNext.valueField || fieldObjectNext.displayField]);
					fieldObjectNext.fireEvent("select");
					fieldObjectNext.nextFieldEvent();
				} else {
					fieldObjectNext.setEditFocus();
				}
			} else {
				if (fieldObjectNext.setEditFocus)
					fieldObjectNext.setEditFocus();
				else
					fieldObjectNext.focus();
			}
		}
	},
	setIndicatorCls : function(cls) {
		if (this.rendered && this.indicatorEl) {
			this.indicatorEl.removeClass(this.indicatorCls);
			this.indicatorEl.addClass(cls);
		}
		this.indicatorCls = cls;
		this.initIndicator();
	},
	setIndicatorIconCls : function(cls) {
		if (this.rendered && this.indicatorEl) {
			this.indicatorEl.removeClass(this.indicatorIconCls);
			this.indicatorEl.addClass(cls);
		}
		this.indicatorIconCls = cls;
		this.initIndicator();
	},
	setIndicatorTip : function(tip) {
		if (this.rendered && this.indicatorEl) {
			this.indicatorEl.dom.qtip = tip;
		}
		this.indicatorTip = tip;
		this.initIndicator();
	},
	showIndicator : function() {
		if (this.indicatorEl && !this.indicatorElIsVisible) {
			this.indicatorEl.removeClass("x-hide-display");
			this.indicatorElIsVisible = true;
			this.alignIndicator.defer(10, this);
		}
		if (!this.indicatorEl) {
			this.initIndicator();
		}
	},
	hideIndicator : function() {
		if (this.indicatorEl && this.indicatorElIsVisible) {
			this.indicatorEl.addClass("x-hide-display");
			this.indicatorElIsVisible = false;
		}
	},
	getAlignIndicatorEl : function() {
		if (this.getTriggerWidth) {
			return this.wrap;
		}
		if ((!Ext.isEmpty(this.boxLabel, false) && this.boxLabel !== "&#160;") && this.labelEl) {
			return this.labelEl;
		}
		return this.getResizeEl();
	},
	getAlignIndicatorOffset : function() {
		var yShift = (this.noteAlign !== "top" && this.childrenHasTopNote()) ? 14 : 0, xShift = 2;
		if (Ext.isInstanceof(this, Ext.form.Checkbox)) {
			yShift = -3;
		}
		return [ xShift, yShift ];
	},
	alignIndicator : function() {
		this.indicatorEl.alignTo(this.getAlignIndicatorEl(), "tl-tr", this.getAlignIndicatorOffset());
	},
	initIndicator : function() {
		if (this.indicatorEl) {
			this.alignIndicator();
			return;
		}
		var f = function() {
			if (!Ext.isEmpty(this.indicatorText, false) || !Ext.isEmpty(this.indicatorIconCls, false)) {
				if (!this.indicatorEl) {
					var elp = this.getErrorCt();
					if (!elp) {
						return;
					}
					this.on("hide", function() {
						this.hideIndicator();
					});
					this.on("invalid", function() {
						if (this.msgTarget === "side" && this.errorIcon && this.errorIcon.isVisible()) {
							this.hideIndicator();
						}
					});
					this.on("show", function() {
						this.showIndicator();
					});
					this.on("valid", function() {
						if (this.msgTarget === "side") {
							this.showIndicator();
						}
					});
					this.indicatorEl = elp.createChild({
						cls : "x-form-indicator " + (this.indicatorCls || "") + (this.indicatorCls ? " " : "") + (this.indicatorIconCls || ""),
						html : this.indicatorText || "",
						style : this.indicatorIconCls ? "padding-left: 18px;" : ""
					});
					if (this.ownerCt) {
						this.ownerCt.on("afterlayout", this.alignIndicator, this);
						this.ownerCt.on("expand", this.alignIndicator, this);
					}
					this.on("resize", this.alignIndicator, this);
					this.on("autosize", this.alignIndicator, this);
					this.on("destroy", function() {
						Ext.destroy(this.indicatorEl);
					}, this);
				}
				this.alignIndicator();
				if (this.indicatorTip) {
					this.indicatorEl.dom.qtip = this.indicatorTip;
				}
				this.showIndicator();
				this.indicatorElIsVisible = true;
				if (this.hidden) {
					this.hideIndicator();
				}
			}
		};
		if (this.rendered) {
			f.call(this);
		} else {
			this.on("render", f, this);
		}
	},
	childrenHasTopNote : function() {
		if (this.items && this.items.each) {
			var r = false;
			this.items.each(function(item) {
				if (item.noteAlign === "top" && item.hidden !== true && (!item.isVisible || item.isVisible())) {
					r = true;
					return false;
				}
			});
			return r;
		}
		return false;
	},
	initNote : function() {
		this.on("hide", function() {
			if (!Ext.isEmpty(this.note, false)) {
				this.hideNote();
			}
		});
		this.on("show", function() {
			if (!Ext.isEmpty(this.note, false)) {
				this.showNote();
			}
		});
		this.on("render", function() {
			if (this.hidden) {
				this.hideFieldLabel();
			}
			if (!Ext.isEmpty(this.note, false)) {
				var noteWrap = false;
				if (!this.wrap) {
					this.wrap = this.wrap || this.el.wrap();
					if (!this.labeler) {
						this.positionEl = this.wrap;
						this.resizeEl = this.wrap;
						this.actionMode = "wrap";
						noteWrap = true;
					}
				}
				if (this.noteAlign === "top") {
					this.wrap.addClass("x-top-note");
				}
				this.note = this.noteEncode ? Ext.util.Format.htmlEncode(this.note) : this.note;
				this.noteEl = this.wrap[this.noteAlign === "top" ? "insertFirst" : "createChild"]({
					cls : "x-field-note " + (this.noteCls || ""),
					html : this.note
				}, undefined);
				this.noteEl.noteWrap = noteWrap;
				if ((this.noteAlign === "top" || this.childrenHasTopNote()) && this.label) {
					this.label.addClass("x-top-note-label");
				}
				if (this.hidden) {
					this.hideNote();
				}
			} else {
				if (this.label && this.childrenHasTopNote()) {
					this.label.addClass("x-top-note-label");
				}
			}
		});
	},
	getNoteWidthAjustment : function() {
		return 0;
	},
	onResize : function(w, h, rawWidth, rawHeight) {
		Ext.form.Field.superclass.onResize.call(this, w, h, rawWidth, rawHeight);
		if (this.noteEl && this.noteEl.noteWrap) {
			if (w && h && w !== "auto" && h !== "auto") {
				this.el.setSize(w - this.getNoteWidthAjustment(), h - this.noteEl.getHeight() - this.el.getMargins("tb"));
			} else {
				if (w && w !== "auto") {
					this.el.setWidth(w - this.getNoteWidthAjustment());
				}
				if (h && h !== "auto") {
					this.el.setHeight(h - this.noteEl.getHeight() - this.el.getMargins("tb"));
				}
			}
		}
	}
});
Ext.form.TriggerField.override({
	standardTrigger : true,
	checkTab : function(e, me) {
		if (!e.getKey) {
			var t = e;
			e = me;
			me = t;
		}
		if (e.getKey() === e.TAB && !this.inEditor) {
			this.triggerBlur();
		}
	},
	getTriggerWidth : function() {
		var tw = this.trigger.getWidth(), noTrigger = true;
		if (tw < 1) {
			tw = 0;
			Ext.each(this.triggers, function(t) {
				if (t.dom.style.display !== "none") {
					noTrigger = false;
					tw += this.defaultTriggerWidth;
				}
			}, this);
			if (this.trigger && noTrigger) {
				if (this.trigger.dom.style.display !== "none") {
					noTrigger = false;
					tw += this.defaultTriggerWidth;
				}
			}
			if (noTrigger) {
				return 0;
			}
		}
		return tw;
	},
	getNoteWidthAjustment : function() {
		return this.getTriggerWidth();
	},
	initComponent : function() {
		Ext.form.TriggerField.superclass.initComponent.call(this);
		this.addEvents("triggerclick");
		if (this.triggersConfig) {
			var cn = [], triggerCfg, isSimple, i = 0;
			for (i; i < this.triggersConfig.length; i++) {
				var trigger = this.triggersConfig[i], triggerIcon = trigger.iconCls || this.triggerClass;
				triggerCfg = {
					"ext:tid" : trigger.tag || "",
					tag : "img",
					"ext:qtip" : trigger.qtip || "",
					src : Ext.BLANK_IMAGE_URL,
					cls : "x-form-trigger" + (trigger.triggerCls || "") + " " + triggerIcon
				};
				if (Ext.sss.StringUtils.startsWith(triggerIcon || "", "x-form-simple")) {
					if (i !== 0 || this.shiftLastSimpleIcon) {
						triggerCfg.cls += " shift-trigger";
					}
					isSimple = true;
				}
				if (trigger.hideTrigger) {
					Ext.apply(triggerCfg, {
						style : "display:none",
						hidden : true
					});
				}
				cn.push(triggerCfg);
			}
			if (this.standardTrigger) {
				triggerCfg = {
					tag : "img",
					src : Ext.BLANK_IMAGE_URL,
					cls : "x-form-trigger"
				};
				if (!Ext.isEmpty(this.triggerClass, false)) {
					triggerCfg.cls += " " + this.triggerClass;
				}
				if (Ext.sss.StringUtils.startsWith(this.triggerClass || "", "x-form-simple")) {
					triggerCfg.cls += " shift-trigger";
					isSimple = true;
				}
				if (this.hideTrigger) {
					Ext.apply(triggerCfg, {
						style : "display:none",
						hidden : true
					});
					this.hideTrigger = false;
				}
				if (this.firstBaseTrigger) {
					cn.splice(0, 0, triggerCfg);
				} else {
					cn.push(triggerCfg);
				}
			}
			if (isSimple) {
				this.addClass("clear-right");
			}
			this.triggerConfig = {
				tag : "span",
				cls : "x-form-twin-triggers",
				cn : cn
			};
		}
		if (Ext.isEmpty(this.triggersConfig) && Ext.sss.StringUtils.startsWith(this.triggerClass || "", "x-form-simple")) {
			this.addClass("clear-right");
		}
	},
	getTrigger : function(index) {
		return this.triggers[index];
	},
	initTrigger : function() {
		if (!this.triggersConfig) {
			this.mon(this.trigger, "click", this.onTriggerClick, this, {
				preventDefault : true
			});
			this.trigger.addClassOnOver("x-form-trigger-over");
			this.trigger.addClassOnClick("x-form-trigger-click");
			return;
		}
		var ts = this.trigger.select(".x-form-trigger", true), triggerField = this;
		this.wrap.setStyle("overflow", "hidden");
		ts.each(function(t, all, index, length) {
			t.hide = function() {
				var w = triggerField.wrap.getWidth();
				if (w === 0) {
					w = triggerField.wrap.getStyleSize().width;
				}
				this.hidden = true;
				this.dom.style.display = "none";
				triggerField.el.setWidth(w - triggerField.getTriggerWidth());
			};
			t.show = function() {
				var w = triggerField.wrap.getWidth();
				if (w === 0) {
					w = triggerField.wrap.getStyleSize().width || 0;
				}
				this.dom.style.display = "";
				this.dom.removeAttribute("hidden");
				this.hidden = false;
				triggerField.el.setWidth(w - triggerField.getTriggerWidth());
			};
			if ((this.firstBaseTrigger && index === 0) || (!this.firstBaseTrigger && index === (all.getCount() - 1))) {
				t.on("click", this.onTriggerClick, this);
			} else {
				t.on("click", this.onCustomTriggerClick, this, {
					index : index,
					t : t,
					tag : t.getAttributeNS("ext", "tid"),
					preventDefault : true
				});
			}
			t.addClassOnOver("x-form-trigger-over");
			t.addClassOnClick("x-form-trigger-click");
		}, this);
		this.triggers = ts.elements;
	},
	onCustomTriggerClick : function(evt, el, o) {
		if (!this.disabled) {
			this.fireEvent("triggerclick", this, o.t, o.index, o.tag, evt);
		}
	},
	initDefaultWidth : function() {
		if (!this.width) {
			var w = this.el.getWidth(), tw = this.getTriggerWidth();
			if (w < 1) {
				w = 90 - tw;
				this.el.setWidth(w);
			}
			this.wrap.setWidth(w + tw);
		}
	},
	onRender : function(ct, position) {
		this.doc = Ext.isIE ? Ext.getBody() : Ext.getDoc();
		Ext.form.TriggerField.superclass.onRender.call(this, ct, position);
		this.wrap = this.el.wrap({
			cls : "x-form-field-wrap x-form-field-trigger-wrap"
		});
		this.trigger = this.wrap.createChild(this.triggerConfig || {
			tag : "img",
			src : Ext.BLANK_IMAGE_URL,
			cls : "x-form-trigger " + this.triggerClass
		});
		this.initTrigger();
		this.initDefaultWidth();
		this.resizeEl = this.positionEl = this.wrap;
		/* fix for Chrome : trigger is misaligned if Note is used */
		if (this.trigger && this.trigger.setStyle && Ext.isWebKit && this.note) {
			this.trigger.setStyle("position", "inherit");
			this.trigger.setStyle.defer(10, this.trigger, [ "position", "absolute" ]);
		}
	},
	removeTriggersWidth : function(w) {
		if (!Ext.isNumber(w) || w === 0) {
			return;
		}
		var tw = this.getTriggerWidth();
		if (Ext.isNumber(w)) {
			this.el.setWidth(w - tw);
		}
		this.wrap.setWidth((this.el.getWidth() || (w - tw)) + tw);
	},
	onResize : function(w, h) {
		Ext.form.TriggerField.superclass.onResize.call(this, w, h);
		this.removeTriggersWidth(w);
	},
	autoSize : function() {
		if (!this.grow || !this.rendered) {
			return;
		}
		if (!this.metrics) {
			this.metrics = Ext.util.TextMetrics.createInstance(this.el);
		}
		var el = this.el, v = el.dom.value, d = document.createElement("div");
		d.appendChild(document.createTextNode(v));
		v = d.innerHTML;
		Ext.removeNode(d);
		d = null;
		v += "&#160;";
		var w = Math.min(this.growMax, Math.max(this.metrics.getWidth(v) + 10, this.growMin)), tw = this.getTriggerWidth();
		this.el.setWidth(w);
		this.wrap.setWidth(w + tw);
		this.fireEvent("autosize", this, w + tw);
	}
});
Ext.form.Field.prototype.initComponent = Ext.form.Field.prototype.initComponent.createSequence(function() {
	this.initNote();
	this.initIndicator();
	if (this.desktop && this.desktop && !this.desktop.isFirstFocusFieldId()) {
		this.desktop.setFirstFocusFieldId(this.id);
	}
	if (this.nextElementId)
		this.addListener("specialkey", function(item, e) {
			if (e.getKey() == e.ENTER) {
				if (!this.nextElementId)
					return;
				this.nextFieldEvent();
			}
		});
});
Ext.sss.TriggerField = Ext.extend(Ext.form.TriggerField, {
	standardTrigger : false,
	initTrigger : function() {
		var ts = this.trigger.select(".x-form-trigger", true), triggerField = this;
		this.wrap.setStyle("overflow", "hidden");
		ts.each(function(t, all, index) {
			t.hide = function() {
				var w = triggerField.wrap.getWidth();
				this.dom.style.display = "none";
				triggerField.el.setWidth(w - triggerField.trigger.getWidth());
			};
			t.show = function() {
				var w = triggerField.wrap.getWidth();
				this.dom.style.display = "";
				this.dom.removeAttribute("hidden");
				triggerField.el.setWidth(w - triggerField.trigger.getWidth());
			};
			t.on("click", this.onCustomTriggerClick, this, {
				index : index,
				t : t,
				tag : t.getAttributeNS("ext", "tid"),
				preventDefault : true
			});
			t.addClassOnOver("x-form-trigger-over");
			t.addClassOnClick("x-form-trigger-click");
		}, this);
		this.triggers = ts.elements;
	}
});
Ext.reg("ssstrigger", Ext.sss.TriggerField);
// @source core/form/DropDownField.js
Ext.sss.DropDownField = Ext.extend(Ext.sss.TriggerField, {
	lazyInit : true,
	componentAlign : "tl-bl?",
	allowBlur : false,
	mode : "text",
	syncValue : Ext.emptyFn,
	initComponent : function() {
		Ext.sss.DropDownField.superclass.initComponent.call(this);
		this.addEvents("expand", "collapse");
		var cn = [], triggerCfg, isSimple;
		triggerCfg = {
			tag : "img",
			src : Ext.BLANK_IMAGE_URL,
			cls : "x-form-trigger"
		};
		if (!Ext.isEmpty(this.triggerClass, false)) {
			triggerCfg.cls += " " + this.triggerClass;
		}
		if (Ext.sss.StringUtils.startsWith(this.triggerClass || "", "x-form-simple")) {
			if (this.triggersConfig && this.triggersConfig.length > 0) {
				triggerCfg.cls += " shift-trigger";
			}
			isSimple = true;
		}
		if (this.hideTrigger) {
			Ext.apply(triggerCfg, {
				style : "display:none",
				hidden : true
			});
			this.hideTrigger = false;
		}
		if (isSimple) {
			this.addClass("clear-right");
		}
		if (this.triggersConfig) {
			this.triggerConfig.cn.push(triggerCfg);
		} else {
			cn.push(triggerCfg);
			this.triggerConfig = {
				tag : "span",
				cls : "x-form-twin-triggers",
				cn : cn
			};
		}
	},
	initTrigger : function() {
		Ext.sss.DropDownField.superclass.initTrigger.call(this);
		this.triggers[this.triggers.length - 1].on("click", this.onTriggerClick, this);
	},
	initDropDownComponent : function() {
		if (this.component && !this.component.render) {
			this.component.floating = true;
			this.component = new Ext.ComponentMgr.create(this.component, "panel");
		}
		var renderTo = this.componentRenderTo || document.body, zindex = parseInt(Ext.fly(renderTo).getStyle("z-index"), 10);
		if (this.ownerCt && !zindex) {
			this.findParentBy(function(ct) {
				zindex = parseInt(ct.getPositionEl().getStyle("z-index"), 10);
				return !!zindex;
			});
		}
		this.component.setWidth(this.component.initialConfig.width || this.getWidth());
		this.component.dropDownField = this;
		this.component.render(renderTo);
		this.component.hide();
		this.first = true;
		this.component.getPositionEl().position("absolute", (zindex || 12000) + 5);
		if (this.component.initialConfig.height) {
			this.component.setHeight(this.component.initialConfig.height);
		}
		this.syncValue(this.getValue(), this.getText());
	},
	onRender : function(ct, position) {
		Ext.sss.DropDownField.superclass.onRender.call(this, ct, position);
		if (Ext.isGecko) {
			this.el.dom.setAttribute("autocomplete", "off");
		}
		if (!this.lazyInit) {
			this.initDropDownComponent();
		} else {
			this.on("focus", this.initDropDownComponent, this, {
				single : true
			});
		}
		if (this.mode !== "text") {
			this.getUnderlyingValueField().render(ct);
		}
	},
	isExpanded : function() {
		return this.component && this.component.isVisible && this.component.isVisible();
	},
	collapse : function() {
		if (!this.isExpanded()) {
			return;
		}
		this.component.hide();
		if (this.allowBlur === false) {
			Ext.getDoc().un("mousewheel", this.collapseIf, this);
			Ext.getDoc().un("mousedown", this.collapseIf, this);
		}
		this.fireEvent("collapse", this);
	},
	collapseIf : function(e) {
		if (!e.within(this.wrap) && !e.within(this.component.el)) {
			this.collapse();
		}
	},
	expand : function() {
		if (this.isExpanded() || !this.hasFocus) {
			return;
		}
		if (this.first) {
			this.doResize(this.el.getWidth() + this.getTriggerWidth());
			delete this.first;
		} else if (this.bufferSize) {
			this.doResize(this.bufferSize);
			delete this.bufferSize;
		}
		var el = this.component.getPositionEl();
		el.setLeft(0);
		el.setTop(0);
		if (Ext.isIE6 || Ext.isIE7) {
			this.component.show();
		}
		el.alignTo(this.wrap, this.componentAlign);
		if (!(Ext.isIE6 || Ext.isIE7)) {
			this.component.show();
		}
		if (this.allowBlur === false) {
			this.mon(Ext.getDoc(), {
				scope : this,
				mousewheel : this.collapseIf,
				mousedown : this.collapseIf
			});
		}
		this.fireEvent("expand", this);
	},
	onTriggerClick : function() {
		if (this.readOnly || this.disabled) {
			return;
		}
		if (this.isExpanded()) {
			this.collapse();
		} else {
			this.onFocus({});
			this.expand();
		}
		this.el.focus();
	},
	validateBlur : function() {
		return !this.component || !this.component.isVisible();
	},
	onResize : function(w, h) {
		Ext.sss.DropDownField.superclass.onResize.apply(this, arguments);
		if (this.isVisible() && this.component && this.componentAlign.render) {
			this.doResize(w);
		} else {
			this.bufferSize = w;
		}
	},
	doResize : function(w) {
		if (!Ext.isDefined(this.component.initialConfig.width)) {
			this.component.setWidth(w);
		}
	},
	checkTab : function(me, e) {
		if (!this.isExpanded() && e.getKey() === e.TAB) {
			this.triggerBlur();
		}
	},
	onDestroy : function() {
		if (this.component && this.component.rendered) {
			this.component.destroy();
		}
		if (this.underlyingValueField && this.underlyingValueField.rendered) {
			this.underlyingValueField.destroy();
		}
		Ext.sss.DropDownField.superclass.onDestroy.call(this);
	},
	setValue : function(value, text, collapse) {
		if (this.mode === "text") {
			collapse = text;
			text = value;
		}
		Ext.sss.DropDownField.superclass.setValue.apply(this, [ text ]);
		this.getUnderlyingValueField().setValue(value);
		if (!this.isExpanded()) {
			this.syncValue(value, text);
		}
		if (collapse !== false) {
			this.collapse();
		}
		return this;
	},
	setRawValue : function(value, text) {
		Ext.sss.DropDownField.superclass.setRawValue.call(this, value);
		this.getUnderlyingValueField().setValue(value);
		if (!this.isExpanded()) {
			this.syncValue(value, text);
		}
		return this;
	},
	initEvents : function() {
		Ext.sss.DropDownField.superclass.initEvents.call(this);
		this.keyNav = new Ext.KeyNav(this.el, {
			"down" : function(e) {
				if (!this.isExpanded()) {
					this.onTriggerClick();
				}
			},
			"esc" : function(e) {
				this.collapse();
			},
			"tab" : function(e) {
				this.collapse();
				return true;
			},
			scope : this,
			doRelay : function(e, h, hname) {
				if (hname === "down" || this.scope.isExpanded()) {
					var relay = Ext.KeyNav.prototype.doRelay.apply(this, arguments);
					if (!Ext.isIE && Ext.EventManager.useKeydown) {
						this.scope.fireKey(e);
					}
					return relay;
				}
				return true;
			},
			forceKeyDown : true,
			defaultEventAction : "stopEvent"
		});
	},
	getUnderlyingValueField : function() {
		if (!this.underlyingValueField) {
			this.underlyingValueField = new Ext.form.Hidden({
				id : this.id + "_Value",
				name : this.id + "_Value",
				value : this.underlyingValue || ""
			});
			this.on("beforedestroy", function() {
				if (this.rendered) {
					this.destroy();
				}
			}, this.underlyingValueField);
		}
		return this.underlyingValueField;
	},
	getText : function() {
		return Ext.sss.DropDownField.superclass.getValue.call(this);
	},
	getValue : function() {
		return this.getUnderlyingValueField().getValue();
	},
	getRawValue : function() {
		return this.getValue();
	},
	reset : function() {
		if (this.isTextMode()) {
			this.setValue(this.originalText, false);
		} else {
			this.setValue(this.originalValue, this.originalText, false);
		}
		this.clearInvalid();
		this.applyEmptyText();
	},
	isTextMode : function() {
		return this.mode === "text";
	},
	initValue : function() {
		Ext.sss.DropDownField.superclass.initValue.call(this);
		if (this.text !== undefined) {
			if (this.isTextMode()) {
				this.setValue(this.text, false);
			} else {
				this.setValue(this.getValue(), this.text, false);
			}
		}
		this.originalText = this.getText();
	},
	clearValue : function() {
		this.setRawValue("", "");
		this.applyEmptyText();
	}
});
Ext.reg("sssdropdown", Ext.sss.DropDownField);
Ext.sss.InputTextMask = function(mask, clearWhenInvalid) {
	if (clearWhenInvalid === undefined) {
		this.clearWhenInvalid = true;
	} else {
		this.clearWhenInvalid = clearWhenInvalid;
	}
	this.rawMask = mask;
	this.viewMask = '';
	this.maskArray = [];
	var mai = 0;
	var regexp = '';
	for (var i = 0; i < mask.length; i++) {
		if (regexp) {
			if (regexp == 'X') {
				regexp = '';
			}
			if (mask.charAt(i) == 'X') {
				this.maskArray[mai] = regexp;
				mai++;
				regexp = '';
			} else {
				regexp += mask.charAt(i);
			}
		} else if (mask.charAt(i) == 'X') {
			regexp += 'X';
			this.viewMask += '_';
		} else if (mask.charAt(i) == '9' || mask.charAt(i) == 'L' || mask.charAt(i) == 'l' || mask.charAt(i) == 'A') {
			this.viewMask += '_';
			this.maskArray[mai] = mask.charAt(i);
			mai++;
		} else {
			this.viewMask += mask.charAt(i);
			this.maskArray[mai] = RegExp.escape(mask.charAt(i));
			mai++;
		}
	}
	this.specialChars = this.viewMask.replace(/(L|l|9|A|_|X)/g, '');
};
Ext.sss.InputTextMask.prototype = {
	init : function(field) {
		this.field = field;
		if (field.rendered) {
			this.assignEl();
		} else {
			field.on('render', this.assignEl, this);
		}
		field.on('blur', this.removeValueWhenInvalid, this);
		field.on('focus', this.processMaskFocus, this);
	},
	assignEl : function() {
		this.inputTextElement = this.field.getEl().dom;
		this.field.getEl().on('keypress', this.processKeyPress, this);
		this.field.getEl().on('keydown', this.processKeyDown, this);
		if (Ext.isSafari || Ext.isIE) {
			this.field.getEl().on('paste', this.startTask, this);
			this.field.getEl().on('cut', this.startTask, this);
		}
		if (Ext.isGecko || Ext.isOpera) {
			this.field.getEl().on('mousedown', this.setPreviousValue, this);
		}
		if (Ext.isGecko) {
			this.field.getEl().on('input', this.onInput, this);
		}
		if (Ext.isOpera) {
			this.field.getEl().on('input', this.onInputOpera, this);
		}
	},
	onInput : function() {
		this.startTask(false);
	},
	onInputOpera : function() {
		if (!this.prevValueOpera) {
			this.startTask(false);
		} else {
			this.manageBackspaceAndDeleteOpera();
		}
	},
	manageBackspaceAndDeleteOpera : function() {
		this.inputTextElement.value = this.prevValueOpera.cursorPos.previousValue;
		this.manageTheText(this.prevValueOpera.keycode, this.prevValueOpera.cursorPos);
		this.prevValueOpera = null;
	},
	setPreviousValue : function(event) {
		this.oldCursorPos = this.getCursorPosition();
	},
	getValidatedKey : function(keycode, cursorPosition) {
		var maskKey = this.maskArray[cursorPosition.start];
		if (maskKey == '9') {
			return keycode.pressedKey.match(/[0-9]/);
		} else if (maskKey == 'L') {
			return (keycode.pressedKey.match(/[A-Za-z]/)) ? keycode.pressedKey.toUpperCase() : null;
		} else if (maskKey == 'l') {
			return (keycode.pressedKey.match(/[A-Za-z]/)) ? keycode.pressedKey.toLowerCase() : null;
		} else if (maskKey == 'A') {
			return keycode.pressedKey.match(/[A-Za-z0-9]/);
		} else if (maskKey) {
			return (keycode.pressedKey.match(new RegExp(maskKey)));
		}
		return (null);
	},
	removeValueWhenInvalid : function() {
		if (this.clearWhenInvalid && this.inputTextElement.value.indexOf('_') > -1) {
			this.inputTextElement.value = '';
		}
	},
	managePaste : function() {
		if (this.oldCursorPos === null) {
			return;
		}
		var valuePasted = this.inputTextElement.value.substring(this.oldCursorPos.start, this.inputTextElement.value.length - (this.oldCursorPos.previousValue.length - this.oldCursorPos.end));
		if (this.oldCursorPos.start < this.oldCursorPos.end) {// there is
			// selection...
			this.oldCursorPos.previousValue = this.oldCursorPos.previousValue.substring(0, this.oldCursorPos.start) + this.viewMask.substring(this.oldCursorPos.start, this.oldCursorPos.end)
					+ this.oldCursorPos.previousValue.substring(this.oldCursorPos.end, this.oldCursorPos.previousValue.length);
			valuePasted = valuePasted.substr(0, this.oldCursorPos.end - this.oldCursorPos.start);
		}
		this.inputTextElement.value = this.oldCursorPos.previousValue;
		keycode = {
			unicode : '',
			isShiftPressed : false,
			isTab : false,
			isBackspace : false,
			isLeftOrRightArrow : false,
			isDelete : false,
			pressedKey : ''
		};
		var charOk = false;
		for (var i = 0; i < valuePasted.length; i++) {
			keycode.pressedKey = valuePasted.substr(i, 1);
			keycode.unicode = valuePasted.charCodeAt(i);
			this.oldCursorPos = this.skipMaskCharacters(keycode, this.oldCursorPos);
			if (this.oldCursorPos === false) {
				break;
			}
			if (this.injectValue(keycode, this.oldCursorPos)) {
				charOk = true;
				this.moveCursorToPosition(keycode, this.oldCursorPos);
				this.oldCursorPos.previousValue = this.inputTextElement.value;
				this.oldCursorPos.start = this.oldCursorPos.start + 1;
			}
		}
		if (!charOk && this.oldCursorPos !== false) {
			this.moveCursorToPosition(null, this.oldCursorPos);
		}
		this.oldCursorPos = null;
	},
	processKeyDown : function(e) {
		this.processMaskFormatting(e, 'keydown');
	},
	processKeyPress : function(e) {
		this.processMaskFormatting(e, 'keypress');
	},
	startTask : function(setOldCursor) {
		if (this.task === undefined) {
			this.task = new Ext.util.DelayedTask(this.managePaste, this);
		}
		if (setOldCursor !== false) {
			this.oldCursorPos = this.getCursorPosition();
		}
		this.task.delay(0);
	},
	skipMaskCharacters : function(keycode, cursorPos) {
		if (cursorPos.start != cursorPos.end && (keycode.isDelete || keycode.isBackspace)) {
			return (cursorPos);
		}
		while (this.specialChars.match(RegExp.escape(this.viewMask.charAt(((keycode.isBackspace) ? cursorPos.start - 1 : cursorPos.start))))) {
			if (keycode.isBackspace) {
				cursorPos.dec();
			} else {
				cursorPos.inc();
			}
			if (cursorPos.start >= cursorPos.previousValue.length || cursorPos.start < 0) {
				return false;
			}
		}
		return (cursorPos);
	},
	isManagedByKeyDown : function(keycode) {
		if (keycode.isDelete || keycode.isBackspace) {
			return (true);
		}
		return (false);
	},
	processMaskFormatting : function(e, type) {
		this.oldCursorPos = null;
		var cursorPos = this.getCursorPosition();
		var keycode = this.getKeyCode(e, type);
		if (keycode.unicode === 0) {
			return;
		}
		if ((keycode.unicode == 67 || keycode.unicode == 99) && e.ctrlKey) {
			return;
		}
		if ((keycode.unicode == 88 || keycode.unicode == 120) && e.ctrlKey) {
			this.startTask();
			return;
		}
		if ((keycode.unicode == 86 || keycode.unicode == 118) && e.ctrlKey) {
			this.startTask();
			return;
		}
		if ((keycode.isBackspace || keycode.isDelete) && Ext.isOpera) {
			this.prevValueOpera = {
				cursorPos : cursorPos,
				keycode : keycode
			};
			return;
		}
		if (type == 'keydown' && !this.isManagedByKeyDown(keycode)) {
			return true;
		}
		if (type == 'keypress' && this.isManagedByKeyDown(keycode)) {
			return true;
		}
		if (this.handleEventBubble(e, keycode, type)) {
			return true;
		}
		return (this.manageTheText(keycode, cursorPos));
	},
	manageTheText : function(keycode, cursorPos) {
		if (this.inputTextElement.value.length === 0) {
			this.inputTextElement.value = this.viewMask;
		}
		cursorPos = this.skipMaskCharacters(keycode, cursorPos);
		if (cursorPos === false) {
			return false;
		}
		if (this.injectValue(keycode, cursorPos)) {
			this.moveCursorToPosition(keycode, cursorPos);
		}
		return (false);
	},
	processMaskFocus : function() {
		if (this.inputTextElement.value.length === 0) {
			var cursorPos = this.getCursorPosition();
			this.inputTextElement.value = this.viewMask;
			this.moveCursorToPosition(null, cursorPos);
		}
	},
	isManagedByBrowser : function(keyEvent, keycode, type) {
		if (((type == 'keypress' && (keyEvent.charCode === 0 || (Ext.isOpera && keyEvent.charCode == null))) || type == 'keydown')
				&& (keycode.unicode == Ext.EventObject.TAB || keycode.unicode == Ext.EventObject.RETURN || keycode.unicode == Ext.EventObject.ENTER || keycode.unicode == Ext.EventObject.SHIFT
						|| keycode.unicode == Ext.EventObject.CONTROL || keycode.unicode == Ext.EventObject.ESC || keycode.unicode == Ext.EventObject.PAGEUP
						|| keycode.unicode == Ext.EventObject.PAGEDOWN || keycode.unicode == Ext.EventObject.END || keycode.unicode == Ext.EventObject.HOME || keycode.unicode == Ext.EventObject.LEFT
						|| keycode.unicode == Ext.EventObject.UP || keycode.unicode == Ext.EventObject.RIGHT || keycode.unicode == Ext.EventObject.DOWN)) {
			return (true);
		}
		return (false);
	},
	handleEventBubble : function(keyEvent, keycode, type) {
		try {
			if (keycode && this.isManagedByBrowser(keyEvent, keycode, type)) {
				return true;
			}
			keyEvent.stopEvent();
			return false;
		} catch (e) {
			alert(e.message);
		}
	},
	getCursorPosition : function() {
		var s, e, r;
		if (this.inputTextElement.createTextRange && !Ext.isOpera) {
			r = document.selection.createRange().duplicate();
			r.moveEnd('character', this.inputTextElement.value.length);
			if (r.text === '') {
				s = this.inputTextElement.value.length;
			} else {
				s = this.inputTextElement.value.lastIndexOf(r.text);
			}
			r = document.selection.createRange().duplicate();
			r.moveStart('character', -this.inputTextElement.value.length);
			e = r.text.length;
		} else {
			s = this.inputTextElement.selectionStart;
			e = this.inputTextElement.selectionEnd;
		}
		return this.CursorPosition(s, e, r, this.inputTextElement.value);
	},
	moveCursorToPosition : function(keycode, cursorPosition) {
		var p = (!keycode || (keycode && keycode.isBackspace)) ? cursorPosition.start : cursorPosition.start + 1;
		if (this.inputTextElement.createTextRange && !Ext.isOpera) {
			cursorPosition.range.move('character', p);
			cursorPosition.range.select();
		} else {
			this.inputTextElement.selectionStart = p;
			this.inputTextElement.selectionEnd = p;
		}
	},
	injectValue : function(keycode, cursorPosition) {
		if (!keycode.isDelete && keycode.unicode == cursorPosition.previousValue.charCodeAt(cursorPosition.start)) {
			return true;
		}
		var key;
		if (!keycode.isDelete && !keycode.isBackspace) {
			key = this.getValidatedKey(keycode, cursorPosition);
		} else {
			if (cursorPosition.start == cursorPosition.end) {
				key = '_';
				if (keycode.isBackspace) {
					cursorPosition.dec();
				}
			} else {
				key = this.viewMask.substring(cursorPosition.start, cursorPosition.end);
			}
		}
		if (key) {
			this.inputTextElement.value = cursorPosition.previousValue.substring(0, cursorPosition.start);
			this.inputTextElement.value += key + cursorPosition.previousValue.substring(cursorPosition.start + key.length, cursorPosition.previousValue.length);
			return true;
		}
		return false;
	},
	getKeyCode : function(onKeyDownEvent, type) {
		var keycode = {};
		keycode.unicode = onKeyDownEvent.getKey();
		keycode.isShiftPressed = onKeyDownEvent.shiftKey;
		keycode.isDelete = ((onKeyDownEvent.getKey() == Ext.EventObject.DELETE && type == 'keydown') || (type == 'keypress' && onKeyDownEvent.charCode === 0 && onKeyDownEvent.keyCode == Ext.EventObject.DELETE)) ? true
				: false;
		keycode.isTab = (onKeyDownEvent.getKey() == Ext.EventObject.TAB) ? true : false;
		keycode.isBackspace = (onKeyDownEvent.getKey() == Ext.EventObject.BACKSPACE) ? true : false;
		keycode.isLeftOrRightArrow = (onKeyDownEvent.getKey() == Ext.EventObject.LEFT || onKeyDownEvent.getKey() == Ext.EventObject.RIGHT) ? true : false;
		keycode.pressedKey = String.fromCharCode(keycode.unicode);
		return (keycode);
	},
	CursorPosition : function(start, end, range, previousValue) {
		var cursorPosition = {};
		cursorPosition.start = isNaN(start) ? 0 : start;
		cursorPosition.end = isNaN(end) ? 0 : end;
		cursorPosition.range = range;
		cursorPosition.previousValue = previousValue;
		cursorPosition.inc = function() {
			cursorPosition.start++;
			cursorPosition.end++;
		};
		cursorPosition.dec = function() {
			cursorPosition.start--;
			cursorPosition.end--;
		};
		return (cursorPosition);
	}
};
Ext.applyIf(RegExp, {
	escape : function(str) {
		return str.replace(/([.*+?^=!:${}()|[\]\/\\])/g, '\\$1');
	}
});
Ext.apply(Ext.form.VTypes, {
	daterange : function(val, field) {
		var date = field.parseDate(val);
		if (!date) {
			return;
		}
		if (field.startDateField && (!this.dateRangeMax || (date.getTime() != this.dateRangeMax.getTime()))) {
			var start = Ext.getCmp(field.startDateField);
			start.setMaxValue(date);
			start.validate();
			this.dateRangeMax = date;
		} else if (field.endDateField && (!this.dateRangeMin || (date.getTime() != this.dateRangeMin.getTime()))) {
			var end = Ext.getCmp(field.endDateField);
			end.setMinValue(date);
			end.validate();
			this.dateRangeMin = date;
		}
		return true;
	},
	password : function(val, field) {
		if (field.initialPassField) {
			var pwd = Ext.getCmp(field.initialPassField);
			return (val == pwd.getValue());
		}
		return true;
	},
	passwordText : 'mots de passe ne correspondent pas'
});
Ext.ns('Ext.ux.grid');
// Check RegExp.escape dependency
if ('function' !== typeof RegExp.escape) {
	throw ('RegExp.escape function is missing. Include Ext.ux.util.js file.');
}
/**
 * Creates new Search plugin
 * 
 * @constructor
 * @param {Object}
 *            A config object
 */
Ext.ux.grid.Search = function(config) {
	Ext.apply(this, config);
	Ext.ux.grid.Search.superclass.constructor.call(this);
}; // eo constructor
Ext.extend(Ext.ux.grid.Search, Ext.util.Observable, {
	/**
	 * @cfg {Boolean} autoFocus Try to focus the input field on each store load
	 *      if set to true (defaults to undefined)
	 */
	/**
	 * @cfg {String} searchText Text to display on menu button
	 */
	searchText : 'Recherche'
	/**
	 * @cfg {String} searchTipText Text to display as input tooltip. Set to ''
	 *      for no tooltip
	 */
	,
	searchTipText : 'Type a text to search and press Enter'
	/**
	 * @cfg {String} selectAllText Text to display on menu item that selects all
	 *      fields
	 */
	,
	selectAllText : 'S&#233;lectionner tout',
	minChars : 2
	/**
	 * @cfg {String} position Where to display the search controls. Valid values
	 *      are top and bottom Corresponding toolbar has to exist at least with
	 *      mimimum configuration tbar:[] for position:top or bbar:[] for
	 *      position bottom. Plugin does NOT create any toolbar.(defaults to
	 *      "bottom")
	 */
	,
	position : 'top'
	/**
	 * @cfg {String} iconCls Icon class for menu button (defaults to
	 *      "icon-magnifier")
	 */
	,
	iconCls : 'icon-zoom'
	/**
	 * @cfg {String/Array} checkIndexes Which indexes to check by default. Can
	 *      be either 'all' for all indexes or array of dataIndex names, e.g.
	 *      ['persFirstName', 'persLastName'] (defaults to "all")
	 */
	,
	autoFocus : true,
	checkIndexes : 'all'
	/**
	 * @cfg {Array} disableIndexes Array of index names to disable (not show in
	 *      the menu), e.g. ['persTitle', 'persTitle2'] (defaults to [] - empty
	 *      array)
	 */
	,
	disableIndexes : []
	/**
	 * Field containing search text (read-only)
	 * 
	 * @property field
	 * @type {Ext.form.TwinTriggerField}
	 */
	/**
	 * @cfg {String} dateFormat How to format date values. If undefined (the
	 *      default) date is formatted as configured in colummn model
	 */
	/**
	 * @cfg {Boolean} showSelectAll Select All item is shown in menu if true
	 *      (defaults to true)
	 */
	,
	showSelectAll : true
	/**
	 * Menu containing the column module fields menu with checkboxes (read-only)
	 * 
	 * @property menu
	 * @type {Ext.menu.Menu}
	 */
	/**
	 * @cfg {String} menuStyle Valid values are 'checkbox' and 'radio'. If
	 *      menuStyle is radio then only one field can be searched at a time and
	 *      selectAll is automatically switched off. (defaults to "checkbox")
	 */
	,
	menuStyle : 'checkbox'
	/**
	 * @cfg {Number} minChars Minimum characters to type before the request is
	 *      made. If undefined (the default) the trigger field shows magnifier
	 *      icon and you need to click it or press enter for search to start. If
	 *      it is defined and greater than 0 then maginfier is not shown and
	 *      search starts after minChars are typed. (defaults to undefined)
	 */
	/**
	 * @cfg {String} minCharsTipText Tooltip to display if minChars is > 1
	 */
	,
	minCharsTipText : 'Tapez au moins {0} caract&#233;res'
	/**
	 * @cfg {String} mode Use 'remote' for remote stores or 'local' for local
	 *      stores. If mode is local no data requests are sent to server the
	 *      grid's store is filtered instead (defaults to "remote")
	 */
	,
	mode : 'local'
	/**
	 * @cfg {Array} readonlyIndexes Array of index names to disable (show in
	 *      menu disabled), e.g. ['persTitle', 'persTitle2'] (defaults to
	 *      undefined)
	 */
	/**
	 * @cfg {Number} width Width of input field in pixels (defaults to 100)
	 */
	,
	width : 300
	/**
	 * @cfg {String} xtype xtype is usually not used to instantiate this plugin
	 *      but you have a chance to identify it
	 */
	,
	xtype : 'gridsearch'
	/**
	 * @cfg {Object} paramNames Params name map (defaults to {fields:"fields",
	 *      query:"query"}
	 */
	,
	paramNames : {
		fields : 'fields',
		query : 'query'
	}
	/**
	 * @cfg {String} shortcutKey Key to fucus the input field (defaults to r =
	 *      Sea_r_ch). Empty string disables shortcut
	 */
	,
	shortcutKey : 'r'
	/**
	 * @cfg {String} shortcutModifier Modifier for shortcutKey. Valid values:
	 *      alt, ctrl, shift (defaults to "alt")
	 */
	,
	shortcutModifier : 'alt'
	/**
	 * @cfg {String} align "left" or "right" (defaults to "left")
	 */
	/**
	 * @cfg {Number} minLength Force user to type this many character before he
	 *      can make a search (defaults to undefined)
	 */
	/**
	 * @cfg {Ext.Panel/String} toolbarContainer Panel (or id of the panel) which
	 *      contains toolbar we want to render search controls to (defaults to
	 *      this.grid, the grid this plugin is plugged-in into)
	 */
	// {{{
	/**
	 * @private
	 * @param {Ext.grid.GridPanel/Ext.grid.EditorGrid}
	 *            grid reference to grid this plugin is used for
	 */
	,
	init : function(grid) {
		this.grid = grid;
		// setup toolbar container if id was given
		if ('string' === typeof this.toolbarContainer) {
			this.toolbarContainer = Ext.getCmp(this.toolbarContainer);
		}
		// do our processing after grid render and reconfigure
		grid.onRender = grid.onRender.createSequence(this.onRender, this);
		grid.reconfigure = grid.reconfigure.createSequence(this.reconfigure, this);
	} // eo function init
	// }}}
	// {{{
	/**
	 * adds plugin controls to <b>existing</b> toolbar and calls reconfigure
	 * 
	 * @private
	 */
	,
	onRender : function() {
		var panel = this.toolbarContainer || this.grid;
		var tb = 'bottom' === this.position ? panel.bottomToolbar : panel.topToolbar;
		// add menu
		this.menu = new Ext.menu.Menu();
		// handle position
		if ('right' === this.align) {
			tb.addFill();
		} else {
			if (tb.items && 0 < tb.items.getCount()) {
				tb.addSeparator();
			}
		}
		// add menu button
		tb.add({
			text : this.searchText,
			menu : this.menu,
			iconCls : this.iconCls
		});
		// add input field (TwinTriggerField in fact)
		this.field = new Ext.form.TwinTriggerField({
			width : this.width,
			selectOnFocus : undefined === this.selectOnFocus ? true : this.selectOnFocus,
			trigger1Class : 'x-form-clear-trigger',
			trigger2Class : this.minChars ? 'x-hide-display' : 'x-form-search-trigger',
			onTrigger1Click : this.onTriggerClear.createDelegate(this),
			onTrigger2Click : this.minChars ? Ext.emptyFn : this.onTriggerSearch.createDelegate(this),
			minLength : this.minLength
		});
		// install event handlers on input field
		this.field.on('render', function() {
			// register quick tip on the way to search
			if ((undefined === this.minChars || 1 < this.minChars) && this.minCharsTipText) {
				Ext.QuickTips.register({
					target : this.field.el,
					text : this.minChars ? String.format(this.minCharsTipText, this.minChars) : this.searchTipText
				});
			}
			if (this.minChars) {
				this.field.el.on({
					scope : this,
					buffer : 300,
					keyup : this.onKeyUp
				});
			}
			// install key map
			var map = new Ext.KeyMap(this.field.el, [ {
				key : Ext.EventObject.ENTER,
				scope : this,
				fn : this.onTriggerSearch
			}, {
				key : Ext.EventObject.ESC,
				scope : this,
				fn : this.onTriggerClear
			} ]);
			map.stopEvent = true;
		}, this, {
			single : true
		});
		tb.add(this.field);
		// re-layout the panel if the toolbar is outside
		if (panel !== this.grid) {
			this.toolbarContainer.doLayout();
		}
		// reconfigure
		this.reconfigure();
		// keyMap
		if (this.shortcutKey && this.shortcutModifier) {
			var shortcutEl = this.grid.getEl();
			var shortcutCfg = [ {
				key : this.shortcutKey,
				scope : this,
				stopEvent : true,
				fn : function() {
					this.field.focus();
				}
			} ];
			shortcutCfg[0][this.shortcutModifier] = true;
			this.keymap = new Ext.KeyMap(shortcutEl, shortcutCfg);
		}
		if (true === this.autoFocus) {
			this.grid.store.on({
				scope : this,
				load : function() {
					this.field.focus();
				}
			});
		}
	} // eo function onRender
	// }}}
	// {{{
	/**
	 * field el keypup event handler. Triggers the search
	 * 
	 * @private
	 */
	,
	onKeyUp : function(e, t, o) {
		// ignore special keys
		if (e.isNavKeyPress()) {
			return;
		}
		var length = this.field.getValue().toString().length;
		if (0 === length || this.minChars <= length) {
			this.onTriggerSearch();
		}
	} // eo function onKeyUp
	// }}}
	// {{{
	/**
	 * Clear Trigger click handler
	 * 
	 * @private
	 */
	,
	onTriggerClear : function() {
		if (this.field.getValue()) {
			this.field.setValue('');
			this.field.focus();
			this.onTriggerSearch();
		}
	} // eo function onTriggerClear
	// }}}
	// {{{
	/**
	 * Search Trigger click handler (executes the search, local or remote)
	 * 
	 * @private
	 */
	,
	onTriggerSearch : function() {
		if (!this.field.isValid()) {
			return;
		}
		var val = this.field.getValue();
		var store = this.grid.store;
		// grid's store filter
		if ('local' === this.mode) {
			store.clearFilter();
			if (val) {
				store.filterBy(function(r) {
					var retval = false;
					this.menu.items.each(function(item) {
						if (!item.checked || retval) {
							return;
						}
						var rv = r.get(item.dataIndex);
						rv = Ext.isInstanceof(rv, Date) ? rv.format(this.dateFormat || r.fields.get(item.dataIndex).dateFormat) : rv;
						var re = new RegExp(RegExp.escape(val), 'gi');
						retval = re.test(rv);
					}, this);
					if (retval) {
						return true;
					}
					return retval;
				}, this);
			} else {
			}
		}
		// ask server to filter records
		else {
			// clear start (necessary if we have paging)
			if (store.lastOptions && store.lastOptions.params) {
				store.lastOptions.params[store.paramNames.start] = 0;
			}
			// get fields to search array
			var fields = [];
			this.menu.items.each(function(item) {
				if (item.checked) {
					fields.push(item.dataIndex);
				}
			});
			// add fields and query to baseParams of store
			delete (store.baseParams[this.paramNames.fields]);
			delete (store.baseParams[this.paramNames.query]);
			if (store.lastOptions && store.lastOptions.params) {
				delete (store.lastOptions.params[this.paramNames.fields]);
				delete (store.lastOptions.params[this.paramNames.query]);
			}
			if (fields.length) {
				store.baseParams[this.paramNames.fields] = Ext.encode(fields);
				store.baseParams[this.paramNames.query] = val;
			}
			// reload store
			store.reload();
		}
	} // eo function onTriggerSearch
	// }}}
	// {{{
	/**
	 * @param {Boolean}
	 *            true to disable search (TwinTriggerField), false to enable
	 */
	,
	setDisabled : function() {
		this.field.setDisabled.apply(this.field, arguments);
	} // eo function setDisabled
	// }}}
	// {{{
	/**
	 * Enable search (TwinTriggerField)
	 */
	,
	enable : function() {
		this.setDisabled(false);
	} // eo function enable
	// }}}
	// {{{
	/**
	 * Disable search (TwinTriggerField)
	 */
	,
	disable : function() {
		this.setDisabled(true);
	} // eo function disable
	// }}}
	// {{{
	/**
	 * (re)configures the plugin, creates menu items from column model
	 * 
	 * @private
	 */
	,
	reconfigure : function() {
		// {{{
		// remove old items
		var menu = this.menu;
		menu.removeAll();
		// add Select All item plus separator
		if (this.showSelectAll && 'radio' !== this.menuStyle) {
			menu.add(new Ext.menu.CheckItem({
				text : this.selectAllText,
				checked : !(Ext.isInstanceof(this.checkIndexes, Array)),
				hideOnClick : false,
				handler : function(item) {
					var checked = !item.checked;
					item.parentMenu.items.each(function(i) {
						if (item !== i && i.setChecked && !i.disabled) {
							i.setChecked(checked);
						}
					});
				}
			}), '-');
		}
		// }}}
		// {{{
		// add new items
		var cm = this.grid.colModel;
		var group = undefined;
		if ('radio' === this.menuStyle) {
			group = 'g' + (new Date).getTime();
		}
		Ext.each(cm.config, function(config) {
			var disable = false;
			if (config.header && config.dataIndex) {
				Ext.each(this.disableIndexes, function(item) {
					disable = disable ? disable : item === config.dataIndex;
				});
				if (!disable) {
					menu.add(new Ext.menu.CheckItem({
						text : config.header,
						hideOnClick : false,
						group : group,
						checked : 'all' === this.checkIndexes,
						dataIndex : config.dataIndex
					}));
				}
			}
		}, this);
		// }}}
		// {{{
		// check items
		if (Ext.isInstanceof(this.check, Array)) {
			Ext.each(this.checkIndexes, function(di) {
				var item = menu.items.find(function(itm) {
					return itm.dataIndex === di;
				});
				if (item) {
					item.setChecked(true, true);
				}
			}, this);
		}
		// }}}
		// {{{
		// disable items
		if (Ext.isInstanceof(this.readonlyIndexes, Array)) {
			Ext.each(this.readonlyIndexes, function(di) {
				var item = menu.items.find(function(itm) {
					return itm.dataIndex === di;
				});
				if (item) {
					item.disable();
				}
			}, this);
		}
		// }}}
	} // eo function reconfigure
// }}}
}); // eo extend
// eof
