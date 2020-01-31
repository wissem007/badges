Ext.UpdateManager.defaults.indicatorText = '<div class="loading-indicator">En cours de chargement...</div>';
if (Ext.View) {
	Ext.View.prototype.emptyText = "";
}
if (Ext.grid.Grid) { 
	Ext.grid.Grid.prototype.ddText = "{0} ligne(s) sÃ©lectionnÃ©(s)";
}
if (Ext.TabPanelItem) {
	Ext.TabPanelItem.prototype.closeText = "Fermer cet onglet";
}
if (Ext.form.Field) {
	Ext.form.Field.prototype.invalidText = "La valeur de ce champ est invalide";
}
if (Ext.LoadMask) {
	Ext.LoadMask.prototype.msg = "En cours de chargement...";
}
Date.monthNames = [ "Janvier", "FÃ©vrier", "Mars", "Avril", "Mai", "Juin", "Juillet", "AoÃ»t", "Septembre", "Octobre", "Novembre", "DÃ©cembre" ];
Date.dayNames = [ "Dimanche", "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi" ];
if (Ext.MessageBox) {
	Ext.MessageBox.buttonText = {
		ok : "OK",
		cancel : "Annuler",
		yes : "Oui",
		no : "Non"
	};
}
if (Ext.DatePicker) {
	Ext.apply(Ext.DatePicker.prototype, {
		todayText : "Aujourd'hui",
		minText : "Cette date est plus petite que la date minimum",
		maxText : "Cette date est plus grande que la date maximum",
		disabledDaysText : "",
		disabledDatesText : "",
		monthNames : Date.monthNames,
		dayNames : Date.dayNames,
		nextText : 'Prochain mois (CTRL+FlÃ©che droite)',
		prevText : "Mois prÃ©cÃ©dent (CTRL+FlÃ©che gauche)",
		monthYearText : "Choisissez un mois (CTRL+FlÃ©che haut ou bas pour changer d\'annÃ©e.)",
		todayTip : "{0} (Barre d'espace)",
		okText : "&#160;OK&#160;",
		cancelText : "Annuler",
		format : "d/m/Y",
		startDay : 1,
		setValue : function(value) {
			if (value && !Ext.isDate(value)) {
				value = Date.parseDate(value, this.format);
			}
			if (Ext.isDate(value)) {
				this.value = value.clearTime(true);
				this.update(this.value);
			}
		}
	});
}
if (Ext.PagingToolbar) {
	Ext.apply(Ext.PagingToolbar.prototype, {
		beforePageText : "Page",
		afterPageText : "sur {0}",
		firstText : "PremiÃ¨re page",
		prevText : "Page prÃ©cÃ©dente",
		nextText : "Page suivante",
		lastText : "DerniÃ¨re page",
		refreshText : "Actualiser la page",
		displayMsg : "Page courante {0} - {1} sur {2}",
		emptyMsg : 'Aucune donnÃ©e Ã  afficher'
	});
}
if (Ext.form.TextField) {
	Ext.apply(Ext.form.TextField.prototype, {
		minLengthText : "La longueur minimum de ce champ est de {0} caractÃ¨res",
		maxLengthText : "La longueur maximum de ce champ est de {0} caractÃ¨res",
		blankText : "Ce champ est obligatoire",
		regexText : "",
		emptyText : null
	});
}
if (Ext.form.NumberField) {
	Ext.apply(Ext.form.NumberField.prototype, {
		minText : "La valeur minimum de ce champ doit Ãªtre de {0}",
		maxText : "La valeur maximum de ce champ doit Ãªtre de {0}",
		nanText : "{0} n'est pas un nombre valide",
		decimalSeparator : ","
	});
}
if (Ext.form.DateField) {
	Ext.apply(Ext.form.DateField.prototype, {
		disabledDaysText : "DÃ©sactivÃ©",
		disabledDatesText : "DÃ©sactivÃ©",
		minText : "La date de ce champ doit Ãªtre avant le {0}",
		maxText : "La date de ce champ doit Ãªtre aprÃ¨s le {0}",
		invalidText : "{0} n'est pas une date valide - elle doit Ãªtre au format suivant: {1}",
		format : "d/m/Y",
		setValue : function(date) {
			if (date == this.emptyText || date == "") {
				date = this.emptyText;
				return Ext.form.DateField.superclass.setValue.call(this, date);
			}
			return Ext.form.DateField.superclass.setValue.call(this, this.formatDate(this.parseDate(date)));
		},
		preFocus : function() {
			Ext.form.DateField.superclass.preFocus.call(this);
			if (this.getValue() == "")
				this.setValue(this.emptyText);
			this.el.dom.select();

		}
	});
}
if (Ext.grid.DateColumn) {
	Ext.apply(Ext.grid.DateColumn.prototype, {
		format : "d/m/Y"
	});
}
if (Ext.form.TimeField) {
	Ext.apply(Ext.form.TimeField.prototype, {
		minText : "La valeur minimum de ce champ doit Ãªtre de {0}",
		maxText : "La valeur maximum de ce champ doit Ãªtre de {0}",
		invalidText : "{0} n'est pas une date valide - elle doit Ãªtre au format suivant: {1}",
		format : "h:i"
	});
}
if (Ext.form.ComboBox) {
	Ext.apply(Ext.form.ComboBox.prototype, {
		loadingText : "En cours de chargement...",
		valueNotFoundText : undefined
	});
}
if (Ext.form.VTypes) {
	Ext.apply(Ext.form.VTypes, {
		emailText : 'Ce champ doit contenir une adresse email au format: "usager@domaine.com"',
		urlText : 'Ce champ doit contenir une URL au format suivant: "http:/' + '/www.domaine.com"',
		alphaText : 'Ce champ ne peut contenir que des lettres et le caractÃ¨re soulignÃ© (_)',
		alphanumText : 'Ce champ ne peut contenir que des caractÃ¨res alphanumÃ©riques ainsi que le caractÃ¨re soulignÃ© (_)'
	});
}
if (Ext.form.HtmlEditor) {
	Ext.apply(Ext.form.HtmlEditor.prototype, {
		createLinkText : "Veuillez entrer l'URL pour ce lien:",
		buttonTips : {
			bold : {
				title : 'Gras (Ctrl+B)',
				text : 'Met le texte sÃ©lectionnÃ© en gras.',
				cls : 'x-html-editor-tip'
			},
			italic : {
				title : 'Italique (Ctrl+I)',
				text : 'Met le texte sÃ©lectionnÃ© en italique.',
				cls : 'x-html-editor-tip'
			},
			underline : {
				title : 'SoulignÃ© (Ctrl+U)',
				text : 'Souligne le texte sÃ©lectionnÃ©.',
				cls : 'x-html-editor-tip'
			},
			increasefontsize : {
				title : 'Agrandir la police',
				text : 'Augmente la taille de la police.',
				cls : 'x-html-editor-tip'
			},
			decreasefontsize : {
				title : 'RÃ©duire la police',
				text : 'RÃ©duit la taille de la police.',
				cls : 'x-html-editor-tip'
			},
			backcolor : {
				title : 'Couleur de surbrillance',
				text : 'Modifie la couleur de fond du texte sÃ©lectionnÃ©.',
				cls : 'x-html-editor-tip'
			},
			forecolor : {
				title : 'Couleur de police',
				text : 'Modifie la couleur du texte sÃ©lectionnÃ©.',
				cls : 'x-html-editor-tip'
			},
			justifyleft : {
				title : 'Aligner Ã  gauche',
				text : 'Aligne le texte Ã  gauche.',
				cls : 'x-html-editor-tip'
			},
			justifycenter : {
				title : 'Centrer',
				text : 'Centre le texte.',
				cls : 'x-html-editor-tip'
			},
			justifyright : {
				title : 'Aligner Ã  droite',
				text : 'Aligner le texte Ã  droite.',
				cls : 'x-html-editor-tip'
			},
			insertunorderedlist : {
				title : 'Liste Ã  puce',
				text : 'DÃ©marre une liste Ã  puce.',
				cls : 'x-html-editor-tip'
			},
			insertorderedlist : {
				title : 'Liste numÃ©rotÃ©e',
				text : 'DÃ©marre une liste numÃ©rotÃ©e.',
				cls : 'x-html-editor-tip'
			},
			createlink : {
				title : 'Lien hypertexte',
				text : 'Transforme en lien hypertexte.',
				cls : 'x-html-editor-tip'
			},
			sourceedit : {
				title : 'Code source',
				text : 'Basculer en mode Ã©dition du code source.',
				cls : 'x-html-editor-tip'
			}
		}
	});
}
if (Ext.ux.grid.LockingGridView) {
	Ext.apply(Ext.ux.grid.LockingGridView.prototype, {
		sortAscText : "Tri croissant",
		sortDescText : "Tri dÃ©croissant",
		lockText : "Verrouiller la colonne",
		unlockText : "DÃ©verrouiller la colonne",
		columnsText : "Colonnes"
	});
}
if (Ext.grid.PropertyColumnModel) {
	Ext.apply(Ext.grid.PropertyColumnModel.prototype, {
		nameText : "PropriÃ©tÃ©",
		valueText : "Valeur",
		dateFormat : "d/m/Y"
	});
}
if (Ext.SplitLayoutRegion) {
	Ext.apply(Ext.SplitLayoutRegion.prototype, {
		splitTip : "Cliquer et glisser pour redimensionner le panneau.",
		collapsibleSplitTip : "Cliquer et glisser pour redimensionner le panneau. Double-cliquer pour le cacher."
	});
}
if (Ext.ux.grid.livegrid.Toolbar) {
	Ext.apply(Ext.ux.grid.livegrid.Toolbar.prototype, {
		displayMsg : "Affichage {0} - {1} de {2}",
		emptyMsg : "Aucune donnée à afficher",
		refreshText : 'Actualiser',
		displayInfo : true
	});
}
// *********3s******
if (Ext.ux.grid.GridPanel) {
	Ext.apply(Ext.ux.grid.GridPanel.prototype, {
		emptyMsg : "Aucune donnée à afficher",
		displayMsg : "Total Des Lignes: {0}"
	});
}
if (Ext.ux.grid.EditorGridPanel) {
	Ext.apply(Ext.ux.grid.EditorGridPanel.prototype, {
		emptyMsg : "Aucune donnée à afficher",
		displayMsg : "Total Des Lignes: {0}"
	});
}
if (Ext.ux.NumberField) {
	Ext.apply(Ext.ux.NumberField.prototype, {
		minText : "La valeur minimum de ce champ doit Ãªtre de {0}",
		maxText : "La valeur maximum de ce champ doit Ãªtre de {0}",
		nanText : "{0} n'est pas un nombre valide",
		decimalSeparator : ","
	});
}
