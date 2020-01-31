initList = function() {
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
		this.assetHeight = 0;
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
		var tds = new Array();
		for (var i = 0; i < this.linkedValues.length; i++) {
			if (this.linkedValues[i].dispaly) {
				tds[i] = {
					tag : 'td',
					width : this.linkedValues[i].width,
					html : this.linkedValues[i].header
				};
			}
		}
		var t = {
			tag : 'table',
			width : "100%",
			children : [ {
				tag : 'tr',
				children : tds
			} ]
		};
		this.selectall = this.list.createChild(t);
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
			
			this.tpl = '{' + this.displayField + '}';
			
		}
		
		this.view = new Ext.DataView({
			applyTo : this.innerList,
			tpl : this.tpl,
			singleSelect : true,
			selectedClass : this.selectedClass,
			itemSelector : this.itemSelector || '.' + cls + '-item',
			emptyText : this.listEmptyText,
			deferEmptyText : false
		});
		
		this.mon(this.view, {
			containerclick : this.onViewClick,
			click : this.onViewClick,
			scope : this
		});
		
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
};