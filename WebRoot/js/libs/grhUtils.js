var parseNumber = function(val, def) {
	var defaultValue = (def != undefined) ? def : 0;

	if (isNaN(val) || val == '') {
		return defaultValue;
	}

	return parseInt(val);
};

util = {
	addWindow : function(idWindow, panelId) {

		win = new Ext.Window({
			id : idWindow,
			layout : 'fit',
			autoHeight : true,
			// autoWidth:true,
			width : 500,
			height : 400,
			closeAction : 'hide',
			buttonAlign : 'center',
			modal : true,
			plain : true,
			items : panelId,
			buttons : [ {
				text : 'Close',
				handler : function() {
					win.hide();
				}
			} ]
		});

		return win;
	},

	reloadStoreField : function(response, id) {
		var object = Ext.util.JSON.decode(response.responseText);

		desktop.getCmp(id).clearValue();
		desktop.getCmp(id).getStore().removeAll();
		desktop.getCmp(id).getStore().load();
	},

	replaceSpace : function(value) {
		return value.replace(/[\s]{1,}/g, "_");
	},
	toMajiscule : function(value) {
		return this.replaceSpace(value).toUpperCase();
	},
	duplicateRow : function(e, columnIndexName) {

		var grid = e.grid;
		var rowIndex = e.row;

		var count = grid.getStore().getCount();
		var value = e.record.get(columnIndexName);
		var originalValue = e.originalValue;

		for ( var i = 0; i < count; i++) {
			if (i != rowIndex) {
				var record = grid.getStore().getAt(i);
				if (record.get(columnIndexName) == value) {
					e.record.set(columnIndexName, originalValue);
					return false;
				}
			}
		}
		return true;
	},

	grid : {

		remove : function(dataGrid, id) {
			var store = dataGrid.getStore();
			var count = store.getCount();
			for ( var i = 0; i < count; i++) {
				var record = store.getAt(i);
				if (record.get(id) == "true") {
					store.removeAt(i);
				}
			}

		},

		add : function(dataGrid) {
			dataGrid.addRecord();
		}
	},

	css : {

		getCSSRule : function(ruleName, deleteFlag) { // Return requested
			// style obejct
			ruleName = ruleName.toLowerCase(); // Convert test string to lower
			// case.
			if (document.styleSheets) { // If browser can play with stylesheets
				for ( var i = 0; i < document.styleSheets.length; i++) { // For
					// each
					// stylesheet
					var styleSheet = document.styleSheets[i]; // Get the
					// current
					// Stylesheet
					var ii = 0; // Initialize subCounter.
					var cssRule = false; // Initialize cssRule.
					do { // For each rule in stylesheet
						if (styleSheet.cssRules) { // Browser uses cssRules?
							cssRule = styleSheet.cssRules[ii]; // Yes --Mozilla
							// Style
						} else { // Browser usses rules?
							cssRule = styleSheet.rules[ii]; // Yes IE style.
						} // End IE check.
						if (cssRule) { // If we found a rule...
							if (cssRule.selectorText.toLowerCase() == ruleName) { // match
								// ruleName?
								if (deleteFlag == 'delete') { // Yes. Are we
									// deleteing?
									if (styleSheet.cssRules) { // Yes,
										// deleting...
										styleSheet.deleteRule(ii); // Delete
										// rule, Moz
										// Style
									} else { // Still deleting.
										styleSheet.removeRule(ii); // Delete
										// rule IE
										// style.
									} // End IE check.
									return true; // return true, class
									// deleted.
								} else { // found and not deleting.
									return cssRule; // return the style object.
								} // End delete Check
							} // End found rule name
						} // end found cssRule
						ii++; // Increment sub-counter
					} while (cssRule) // end While loop
				} // end For loop
			} // end styleSheet ability check
			return false; // we found NOTHING!
		}, // end getCSSRule

		killCSSRule : function(ruleName) { // Delete a CSS rule
			return this.getCSSRule(ruleName, 'delete'); // just call getCSSRule
			// w/delete flag.
		}, // end killCSSRule

		addCSSRule : function(ruleName) { // Create a new css rule
			if (document.styleSheets) { // Can browser do styleSheets?
				if (!this.getCSSRule(ruleName)) { // if rule doesn't exist...
					if (document.styleSheets[0].addRule) { // Browser is IE?
						document.styleSheets[0].addRule(ruleName, null, 0); // Yes,
						// add
						// IE
						// style
					} else { // Browser is IE?
						document.styleSheets[0]
								.insertRule(ruleName + ' { }', 0); // Yes, add
						// Moz
						// style.
					} // End browser check
				} // End already exist check.
			} // End browser ability check.
			return this.getCSSRule(ruleName); // return rule we just created.
		}
	}

};

function getCaretPos(id) {

	var el = document.getElementById(id);
	var rng, ii = -1;

	if (typeof el.selectionStart == "number") {

		ii = el.selectionStart;

	} else

	if (document.selection && el.createTextRange) {
		rng = document.selection.createRange();
		rng.collapse(true);
		rng.moveStart("character", -el.value.length);
		ii = rng.text.length;
	}

	return ii;

};

function setCaretPos(id, pos) {

	var obj = document.getElementById(id);

	if (obj.createTextRange) {
		var range = obj.createTextRange();
		range.move("character", pos);
		range.select();
	} else if (obj.selectionStart) {
		obj.focus();
		obj.setSelectionRange(pos, pos);
	}

};

function insertAtCursor(id, startPos, v) {

	var text_field = document.getElementById(id);
	if (text_field) {

		var endPos = text_field.selectionEnd;
		text_field.value = text_field.value.substring(0, startPos) + v
				+ text_field.value.substring(startPos);
		setCaretPos(id, startPos + v.length);

	}

};

function greenChange(val) {
	return colorChange(val, 'green');
}

function redChange(val) {
	return colorChange(val, 'red');
}

function blueChange(val) {
	return colorChange(val, 'blue');
}

function blackChange(val) {
	return '<span style="color:black;font-weight:bold;">' + val + '</span>';
}

function colorChange(val, color) {
	return '<span style="color:' + color + ';">' + val + '</span>';
}

function getCellValue(grid, rowIndex, colIndex) {
    var record = grid.store.getAt(rowIndex);
    alert(grid.getColumnModel());
    var name = grid.getColumnModel().getDataIndex(colIndex);
    return record.get(name);
}
