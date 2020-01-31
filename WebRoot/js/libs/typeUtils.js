Ext.ns('Ext.sss');
Ext.sss.Utils = function() {
	return {
		reloadGrid : function(response, desktop, gridId) {
			var object = Ext.util.JSON.decode(response.responseText);
			if (object.empty) {
				desktop.getCmp(gridId).hide();
				desktop.alert(object.message, "info");
			} else {
				desktop.getCmp(gridId).getStore().load();
				desktop.getCmp(gridId).show();
				
			}
			if (object.script) {
				eval(object.script);
			}
		},
		reloadOrg : function(response, desktop, gridId) {
			var object = Ext.util.JSON.decode(response.responseText);
			if (object.empty) {
				desktop.getCmp(gridId).hide();
				desktop.alert(object.message, "info");
			} else {
				desktop.getCmp(gridId).getStore().load();
				desktop.getCmp(gridId).show();
				desktop.getCmp("org").loadOrg(object.org[0]);
				
			}
			if (object.script) {
				eval(object.script);
			}
		},
		
		treeCheck : function(v, meta, record, row_idx, col_idx, store) {
			var imgMaster = '<img src="' + Ext.BLANK_IMAGE_URL
					+ '" class="tg-mastercol-icon" />';
			var checkboxMaster = '<input type="checkbox" class="tg-mastercol-cb" ext:record-id="'
					+ record.id + '"/>';
			var labelMaster = '<span class="tg-mastercol-editorplace">' + v
					+ '</span>';
			return [ imgMaster, checkboxMaster, labelMaster ].join('');
		},
		treeRoot : function(v, meta, record, row_idx, col_idx, store) {
			var imgMaster = '<img src="' + Ext.BLANK_IMAGE_URL
					+ '" class="tg-mastercol-icon" />';
			var labelMaster = '<span class="tg-mastercol-editorplace">' + v
					+ '</span>';
			return [ imgMaster, labelMaster ].join('');
		},
		setColorInMeta : function(metaData, color) {
			metaData.attr = 'style="background-color:' + color + '"';
		},
		setStyleInMeta : function(metaData, style) {
			metaData.attr = 'style="' + style + '"';
		},
		reloadForm : function(response, options, desktop) {
			var object = Ext.util.JSON.decode(response.responseText);
			if (!object.success) {
				desktop.alert(object.message, "info");
			} else {
				desktop.getForm().setValues(object.data);
			}
		},
		beforeQueryCombo : function(qe, params) {
			var isLaod = false;
			var store = qe.combo.getStore();
			Ext.iterate(params, function(key, value, os) {
				if (store[key] != value) {
					isLaod = true;
				}
				store[key] = value;
			}, this);
			if (!isLaod)
				return true;
			options = {
				params : params
			};
			store.reload(options);
			return true;
		}
	};
}();
Ext.sss.StringUtils = function() {
	return {
		startsWith : function(str, value) {
			if (str == null || !str)
				return false;
			return str.match("^" + value) !== null;
		},
		equals : function(str, value) {
			return value == str;
		},
		isBlank : function(str) {
			if (str == null || !str)
				return true;
			return str.length <= 0;
		},
		endsWith : function(str, value) {
			if (str == null || str)
				return false;
			return str.match(value + "$") !== null;
		},
		getCmp : function(str, value) {
			var d = id.split("."), o = window[d[0]];
			Ext.each(d.slice(1), function(v) {
				if (!o) {
					return null;
				}
				o = o[v];
			});
			return o ? Ext.getCmp(o.id) : null;
		}
	};
}();
smartsoft = {
	Number : {
		decimal : 2,
		separateur : " ",
		decimalSeparateur : ",",
		arrondi : function(a) {
			var x = Math.round(a * 10000);
			x /= 10000;
			return x;
		},
		somme : function(a, b) {
			x = a == null ? 0 : this.arrondi(a);
			y = b == null ? 0 : this.arrondi(b);
			return this.arrondi(x + y);
		},
		soustrait : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return this.arrondi(x - y);
		},
		multiplier : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return this.arrondi(x * y);
		},
		diviser : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return this.arrondi(x / y);
		},
		isStrictementSuperieur : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return x > y;
		},
		isStrictementInferieur : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return x < y;
		},
		isInferieur : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return x <= y;
		},
		isSuperieur : function(a, b) {
			var x = a == null ? 0 : this.arrondi(a);
			var y = b == null ? 0 : this.arrondi(b);
			return x >= y;
		},
		isEgale : function(a, b) {
			return this.isInferieur(a, b) && this.isSuperieur(a, b);
		},
		isEntre : function(a, start, end) {
			return this.isSuperieur(a, start) && this.isInferieur(a, end);
		},
		getAsObject : function(valeur) {
			if (!valeur)
				return 0;
			var val_format = "";
			var x = -1;
			for ( var index = 0; index < valeur.length; index++) {
				if (valeur.charAt(index) != smartsoft.Number.separateur
						&& valeur.charAt(index) != smartsoft.Number.decimalSeparateur) {
					val_format = val_format + valeur.charAt(index);
					if (x >= 0)
						x++;
				} else if (valeur.charAt(index) == smartsoft.Number.decimalSeparateur) {
					val_format = val_format + ".";
					x++;
				}
				if (x == smartsoft.Number.decimal)
					break;
			}
			return parseFloat(val_format);
		},
		getAsString : function(valeur) {
			if (!valeur)
				return "";
			var deci = Math.round(Math.pow(10, smartsoft.Number.decimal)
					* (Math.abs(valeur) - Math.floor(Math.abs(valeur))));
			var val = Math.floor(Math.abs(valeur));
			if ((smartsoft.Number.decimal == 0)
					|| (deci == Math.pow(10, smartsoft.Number.decimal))) {
				val = Math.floor(Math.abs(valeur));
				deci = 0;
			}
			var val_format = val + "";
			var nb = val_format.length;
			for ( var i = 1; i < 4; i++) {
				if (val >= Math.pow(10, (3 * i))) {
					val_format = val_format.substring(0, nb - (3 * i))
							+ smartsoft.Number.separateur
							+ val_format.substring(nb - (3 * i));
				}
			}
			if (smartsoft.Number.decimal > 0) {
				var decim = "";
				for ( var j = 0; j < (smartsoft.Number.decimal - deci
						.toString().length); j++) {
					decim += "0";
				}
				deci = decim + deci.toString();
				val_format = val_format + smartsoft.Number.decimalSeparateur
						+ deci;
			}
			if (parseFloat(valeur) < 0) {
				val_format = "-" + val_format;
			}
			return val_format;
		}
	},
	Date : {
		format : "d/m/Y",
		getAsString : function(date, format) {
			return date.format(format || smartsoft.Date.format);
		},
		getAsObject : function(date, format) {
			return Date.parseDate(date, format || smartsoft.Date.format);
		},
		getCourantDate : function() {
			return new Date();
		},
		getAnnees : function(date) {
			return date.getFullYear();
		},
		getMois : function(date) {
			return date.getMonth();
		},
		getJours : function(date) {
			return date.getDate();
		},
		endOfMonth : function(date) {
			var mois = smartsoft.Date.getMois(date);
			mois = smartsoft.Number.somme(mois, 1);
			var annee = smartsoft.Date.getAnnees(date);
			var jours = new Date(annee, mois, 0).getDate();
			mois = smartsoft.Number.soustrait(mois, 1);
			return new Date(annee, mois, jours);
		},
		addMonth : function(date, nbr) {
			if (nbr == 0)
				return date;
			var mois = smartsoft.Number.somme(date.getMonth(), nbr);
			mois = smartsoft.Number.somme(mois, 1);
			var annee = smartsoft.Date.getAnnees(date);
			var jours = "";
			if (mois == 2)
				jours = new Date(annee, mois, 0).getDate();
			else
				jours = date.getDate();
			mois = smartsoft.Number.soustrait(mois, 1);
			return new Date(annee, mois, jours);
		},
		addDay : function(date, nbr) {
			if (nbr == 0)
				return date;
			var jours = smartsoft.Number.somme(date.getDate(), nbr);
			var mois = smartsoft.Date.getMois(date);
			var annee = smartsoft.Date.getAnnees(date);
			return new Date(annee, mois, jours);
		},
		compare : function(date1, date2) {
			diff = date1.getTime() - date2.getTime();
			return (diff == 0 ? diff : diff / Math.abs(diff));
		},

		diffDateEnJours : function(d1, d2) {
			return this.diffDate(d1, d2, 'd');
		},
		diffDateEnHeurs : function(d1, d2) {
			return this.diffDate(d1, d2, 'h');
		},
		diffDate : function(d1, d2, u) {
			div = 1;
			switch (u) {
			case 's':
				div = 1000;
				break;
			case 'm':
				div = 1000 * 60;
				break;
			case 'h':
				div = 1000 * 60 * 60;
				break;
			case 'd':
				div = 1000 * 60 * 60 * 24;
				break;
			}

			diff = d2.getTime() - d1.getTime();
			return Math.ceil((Diff / div));
		}

	},
	grid : {
		getCell : function(grid, row, col) {
			return grid.getView().getCell(row, col);
		},
		updateRow : function(gridId, rowIndex, value) {
			var grid = Ext.getCmp(gridId);
			var store = grid.getStore();
			var record = store.getAt(rowIndex);
			if (!record)
				return;
			for (key in value) {
				record.set(key, value[key]);
			}
		},
		getEditorValue : function(column, value) {
			if (column.editor
					&& Ext.isInstanceof(column.editor, Ext.form.ComboBox)) {
				combo = column.editor;
				var idx = combo.store.find(combo.valueField, value);
				if (idx < 0) {
					return value;
				}
				var rec = combo.store.getAt(idx);
				return rec.get(combo.displayField);
			}
			return value;
		},
		getRow : function(grid, row) {
			return grid.getView().getRow(row);
		},
		focusRow : function(grid, row) {
			grid.getView().focusRow(row);
		},
		focusFirstRow : function(grid) {
			grid.getView().focusRow(0);
		},
		focusLastRow : function(grid) {
			grid.getView().focusRow(grid.getStore().getCount() - 1);
		},
		selectRow : function(grid, row) {
			grid.getSelectionModel().selectRow(row);
		},
		selectLastRow : function(grid) {
			grid.getSelectionModel().selectRow(grid.getStore().getCount() - 1);
		},
		selectFirstRow : function(grid) {
			grid.getSelectionModel().selectRow(0);
		},
		createRecord : function(store, data) {
			return new store.recordType(data);
		},
		highlight : function(obj, couleurIndexValue) {
			Ext.fly(obj).highlight(couleurIndexValue, {
				attr : "background-color",
				easing : 'easeOut',
				duration : 1.0
			});
		}
	}
};
Ext.util.Format.date = function(v, format) {
	if (!v) {
		return "";
	}
	if (!Ext.isDate(v)) {
		v = smartsoft.Date.getAsObject(v, format);
	}
	return smartsoft.Date.getAsString(v, format);
};
function suggestRender(value, metaData, record, rowIndex, colIndex, store,handler) {
	if(handler)
		handler.call(this,value, metaData, record, rowIndex, colIndex, store);
 	if (this.editor && Ext.isInstanceof(this.editor, Ext.form.ComboBox)) {
		var combo = this.editor;
	 	if (combo.dataIndexDisplayColumn) {
	 	  return record.get(combo.dataIndexDisplayColumn);
	 	}
	}else if(this.dataIndexDisplay && !Ext.sss.StringUtils.isBlank(value)){
		return record.get(this.dataIndexDisplay);
	}
 	return value;
}
function selectFile(attachmentsWindowName, fileProperty, viewFileId ) {
	fp = Ext.getCmp(attachmentsWindowName).desktopContainer;
	var url = desktop.resolveUrl(fileProperty, "fileselected");
	var exp = /^.*.(JPEG|JPG|ICO|Exif|TIFF|RAW|GIF|BMP|PNG|PPM|PGM|PBM|PNM|PFM|PAM|WEBP|jpeg|jpg|ico|exif|tiff|raw|gif|bmp|png|ppm|pgm|pbm|pnm|pfm|pam|webp)$/;
 	if (!exp.test(Ext.getDom(fileProperty).value)) {
		Ext.MessageBox.alert('Change Image', 'Only JPEG|JPG|ICO|Exif|TIFF|RAW|GIF|BMP|PNG|PPM|PGM|PBM|PNM|PFM|PAM|WEBP, please.');
		return;
	}
	fp.getForm().submit({
		url : url,
		waitMsg : 'Requéte en cours de traitement...',
		success : function(response, options) {
			var object = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp(attachmentsWindowName).hide();
			var logoView = document.getElementById(viewFileId);
			if (logoView) {
				var srs = logoView.src;
				if (srs.indexOf("&rand") > 0)
					srs = srs.substring(0, srs.indexOf("&rand"));
				logoView.src = srs + "&rand=" + Math.random();
			}
		},
		failure : function(response, options) {
			var object = Ext.util.JSON.decode(response.responseText);
			Ext.getCmp(attachmentsWindowName).hide();
			var logoView = document.getElementById(viewFileId);
			if (logoView) {
				var srs = logoView.src;
				if (srs.indexOf("&rand") > 0)
					srs = srs.substring(0, srs.indexOf("&rand"));
				logoView.src = srs + "&rand=" + Math.random();
			}
		}
	});
}