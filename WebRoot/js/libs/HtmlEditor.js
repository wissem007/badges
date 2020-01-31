/** 
 * Copyright Intermesh
 * 
 * This file is part of Group-Office. You should have received a copy of the
 * Group-Office license along with Group-Office. See the file /LICENSE.TXT
 * 
 * If you have questions write an e-mail to info@intermesh.nl
 * 
 * @version $Id: HtmlEditorImageInsert.js 17133 2014-03-20 08:25:24Z mschering $
 * @copyright Copyright Intermesh
 * @author Merijn Schering <mschering@intermesh.nl>
 */

GO.plugins.HtmlEditorImageInsert = function(config) {
    
    config = config || {};
    
    Ext.apply(this, config);
    
    this.init = function(htmlEditor) {
        this.editor = htmlEditor;
        this.editor.on('render', this.onRender, this);
    };

    this.filesFilter='jpg,png,gif,jpeg,bmp';
    this.addEvents({
        'insert' : true
    });
}

Ext.extend(GO.plugins.HtmlEditorImageInsert, Ext.util.Observable, {


    root_folder_id : 0,
    folder_id : 0,
    
    isTempFile : true,
    
    onRender :  function() {

        var element={};

        element.itemId='htmlEditorImage';
        element.cls='x-btn-icon go-edit-insertimage';
        element.enableToggle=false;
        element.scope=this;
        element.clickEvent='mousedown';
        element.tabIndex=-1;
        element.tooltip={
            title:GO.lang.image,
            text:GO.lang.insertImage
        }
        element.overflowText=GO.lang.insertImage;
        
                            
        this.uploadForm = new GO.UploadPCForm();

        this.uploadForm.on('upload', function(e, files)
        {
            this.selectTempImage(files[0]);
        },this);

        var menuItems = [
        this.uploadForm
        ];

        if(GO.files){
            menuItems.push({
                iconCls:'btn-groupoffice',
                text : GO.email.lang.attachFilesGO.replace('{product_name}', GO.settings.config.product_name),
                handler : function()
                {
                    this.showFileBrowser();
                },
                scope : this
            });
        }

        this.menu = element.menu = new Ext.menu.Menu({
            items:menuItems
        });
        
        
        this.editor.tb.add(element);
    },
    
    showFileBrowser : function (){
    

        GO.files.createSelectFileBrowser();

        GO.selectFileBrowser.setFileClickHandler(this.selectImage, this);

        GO.selectFileBrowser.setFilesFilter(this.filesFilter);
        GO.selectFileBrowser.setRootID(this.root_folder_id, this.files_folder_id);
        GO.selectFileBrowserWindow.show();

        GO.selectFileBrowserWindow.show.defer(200, GO.selectFileBrowserWindow);
    },

    selectTempImage : function(path)
    {
        
        var token = GO.base.util.MD5(path);
        
        this.selectedUrl = GO.url("core/downloadTempFile", {path:path, token: token});

        this.selectedPath = path;   
        

        var html = '<img src="'+this.selectedUrl+'" border="0" />';

        this.fireEvent('insert', this,  this.selectedPath, true, token);
        
        this.menu.hide();

        this.editor.focus();
        this.editor.insertAtCursor(html);       
    },
    
    selectImage : function(r){  
        

        this.selectedRecord = r;
        this.selectedPath = r.data.path;
        
        var token = GO.base.util.MD5(r.data.name);
        
        //filename is added as parameter. This is only for matching the url in the body of the html in GO\\Base\\Mail\\Message::handleEmailFormInput with preg_match.
        this.selectedUrl = GO.url("files/file/download",{id:r.data.id,token:token});
                        
        var html = '<img src="'+this.selectedUrl+'" border="0" />';
                                
        this.fireEvent('insert', this, this.selectedPath, false, token);

        this.editor.focus();
            
        this.editor.insertAtCursor(html);
        
        GO.selectFileBrowserWindow.hide();
    }
    
});
/** 
 * RPM Solutions UK Ltd
 * Using aspell and pspell to enbale spell checking.
 * 
 * @Code By RPM Solutions UK Ltd
 * @author Shaun Forsyth <shaun@rpm-solutions.co.uk>
 */

GO.plugins.HtmlEditorSpellCheck = function(emailComposer) {
    
    this.EmailComposer = emailComposer;
    
    /*Ext.apply(this, config);*/
    
    this.init = function(htmlEditor) {
        this.editor = htmlEditor;
        this.editor.on('render', this.onRender, this);
    };
    
    this.addEvents({'insert' : true});
}

Ext.extend(GO.plugins.HtmlEditorSpellCheck, Ext.util.Observable, {
    onRender :  function() {
      if (!Ext.isSafari) {

                var langs = [];
                var lang;
                for(var i=0;i<GO.Languages.length;i++)
                {
                    lang = {
                        text:GO.Languages[i][1],
                        lang:GO.Languages[i][0],
                        handler:function(item){
                            this.spellcheck(item.lang);
                        },
                        scope:this
                    };
                    if(GO.settings.language==lang.lang){
                        langs.unshift(lang,'-');
                    }else
                    {
                        langs.push(lang);
                    }
                }

        this.editor.tb.add({
            itemId : 'htmlSpellCheck',
            cls : 'x-btn-icon go-edit-spellcheck',
            enableToggle: false,
            scope: this,            
            menu:{
                items:langs
            },
            clickEvent:'mousedown',
            tabIndex:-1,
            overflowText: GO.lang.spellcheck,
            tooltip:{title:GO.lang.spellcheck, text:GO.lang.spellcheckdetails}
        });
    }
    },
    
    spellcheck : function (lang) {
        if (this.EmailComposer.getName() == 'htmlbody') {
            var self = this;
            Ext.Ajax.request({
                url: GO.url("core/spellcheck"),
                success: function (result,request){
                    self.processResults(result,request,self);
                },
                failure: this.problem,
                params: {tocheck: this.editor.getValue(),lang: lang}
            });
            //This should be non blocking.. the user attempted a spell check
            this.editor.SpellCheck = true;
        }else{
            //Only Supports HTML, Plain doesn't have a tool bar
        }
    },
    
    processResults : function(result, request,self){
        var jsonData = Ext.util.JSON.decode(result.responseText);
        if (jsonData.errorcount == 0){
            Ext.Msg.alert(GO.lang.spellcheck,GO.lang.spellcheckNoError);
        }else{
            self.showSpellChecker(jsonData.errorcount,jsonData.text,self);
        }
    },
    
    showSpellChecker : function (errors, text, self){
        
        /*htmlobj = Ext.DomHelper.createDom(text);*/
        
        var PanelTitle = GO.lang.spellcheckNoErrors.replace(/\{1\}/ig,errors);
        
        self.textarea = new Ext.Panel({
                    title: PanelTitle,
                    region: 'north',
                    cls : 'go-form-panel x-spell-checker',
                    autoScroll : true,
                    html: text
                    });
        
        self.wnd = new Ext.Window({
            width: 600,
            height: 400,
            modal : true,
            closeAction: 'close',
            closable : true,
            layout:'fit',
            title: GO.lang.spellcheck,
            items : [self.textarea],
            buttons: [
                {
                    text:GO.lang.cmdSave,
                    handler: function(){
                        self.UpdateEditorValue(self);
                    }
                },
                {
                    text: GO.lang.cmdCancel,
                    handler: function(){
                            self.closeWindow(self);
                        }
                }]
        });
        self.wnd.show();
        
        Ext.select('span.spelling',self.textarea).on('click',function (e) {self.showSuggestions(e,this);});
        
    },
    
    UpdateEditorValue: function (self){
        //There has to be a better way to do this.
        var html = this.textarea.el.dom.childNodes[1].childNodes[0].innerHTML;
        
        //Remove left over spans from words which were not corrected
        var Pattern = new RegExp('<span [\\s\\S]*?class=(")?spelling(")?[\\s\\S]*?>(\\w+)[\\s\\S]*?<\/span>','mig');
        
        //html = this.decode(html);
        html = html.replace(Pattern,'$3 ');
        
        self.editor.setValue(html);
        self.closeWindow(self);
    },
    
    closeWindow: function (self){
        self.wnd.destroy(); 
    },
    
    showSuggestions : function (e,self){
        //e is the event used for xy
        //self is the span that was clicked
        //this (not understanding scope and how it works this time!) is the spelling object
        var items = self.getElementsByTagName('li');
        var menuitems = new Array();
        var speller = this;
        for (var i = 0; i < items.length; i++){
            var word = items[i].innerHTML;
            menuitems.push (
                new Ext.Action({
                    text: word,
                    handler: function (ev,target) {speller.replaceSpelling(this.text,self);}
                })
            );
        }
        
        this.Suggestions = new Ext.menu.Menu({
                        items: menuitems                                     
        });
        
        this.Suggestions.showAt(e.getXY());
        
    },
    
    replaceSpelling : function (word,self){     
        var replaceobject;
        //IE doesn't treat whitespace as dom elements like firefox and chrome.
        if (Ext.isIE && self.getAttribute('ieAfterObject') == ' '){
            replaceobject = document.createTextNode(word+' ');  
        }else{
            replaceobject = document.createTextNode(word);
        }
        self.parentNode.replaceChild(replaceobject,self);
        this.Suggestions.destroy();
    },
    
    problem : function (result,request) {
        Ext.Msg.show({
            title: GO.lang.spellcheck,
            msg: GO.lang.spellcheckServerError,
            buttons: Ext.Msg.OK,
            icon: Ext.Msg.WARNING
        }); 
    }
});
/*!
 * MIT
 */
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.MidasCommand
 * @extends Ext.util.Observable
 * <p>A base plugin for extending to create standard Midas command buttons.</p>
 */
Ext.ns('Ext.ux.form.HtmlEditor');

Ext.ux.form.HtmlEditor.MidasCommand = Ext.extend(Ext.util.Observable, {
    // private
    init: function(cmp){
        this.cmp = cmp;
        this.btns = [];
        this.cmp.on('render', this.onRender, this);
        this.cmp.on('initialize', this.onInit, this, {
            delay: 100,
            single: true
        });
    },
    // private
    onInit: function(){
        Ext.EventManager.on(this.cmp.getDoc(), {
            'mousedown': this.onEditorEvent,
            'dblclick': this.onEditorEvent,
            'click': this.onEditorEvent,
            'keyup': this.onEditorEvent,
            buffer: 100,
            scope: this
        });
    },
    // private
    onRender: function(){
        var midasCmdButton, tb = this.cmp.getToolbar(), btn;
        Ext.each(this.midasBtns, function(b){
            //if (Ext.isObject(b)) {
                        if (typeof(b)=='object') {
                midasCmdButton = {
                                        tabIndex:-1,
                    iconCls: 'x-edit-' + b.cmd,
                    handler: function(){
                        this.cmp.relayCmd(b.cmd);
                    },
                    scope: this,
                    tooltip: b.tooltip ||
                    {
                        title: b.title
                    },
                    overflowText: b.overflowText || b.title
                };
            } else {
                midasCmdButton = new Ext.Toolbar.Separator();
            }
            btn = tb.addButton(midasCmdButton);
            if (b.enableOnSelection) {
                btn.disable();
            }
            this.btns.push(btn);
        }, this);
    },
    // private
    onEditorEvent: function(){
        var doc = this.cmp.getDoc();
        Ext.each(this.btns, function(b, i){
            if (this.midasBtns[i].enableOnSelection || this.midasBtns[i].disableOnSelection) {
                if (doc.getSelection) {
                    if ((this.midasBtns[i].enableOnSelection && doc.getSelection() !== '') || (this.midasBtns[i].disableOnSelection && doc.getSelection() === '')) {
                        b.enable();
                    } else {
                        b.disable();
                    }
                } else if (doc.selection) {
                    if ((this.midasBtns[i].enableOnSelection && doc.selection.createRange().text !== '') || (this.midasBtns[i].disableOnSelection && doc.selection.createRange().text === '')) {
                        b.enable();
                    } else {
                        b.disable();
                    }
                }
            }
            if (this.midasBtns[i].monitorCmdState) {
                b.toggle(doc.queryCommandState(this.midasBtns[i].cmd));
            }
        }, this);
    }
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.Divider
 * @extends Ext.util.Observable
 * <p>A plugin that creates a divider on the HtmlEditor. Used for separating additional buttons.</p>
 */
Ext.ux.form.HtmlEditor.Divider = Ext.extend(Ext.util.Observable, {
    // private
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
    },
    // private
    onRender: function(){
        this.cmp.getToolbar().addButton([new Ext.Toolbar.Separator()]);
    }
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.IndentOutdent
 * @extends Ext.ux.form.HtmlEditor.MidasCommand
 * <p>A plugin that creates two buttons on the HtmlEditor for indenting and outdenting of selected text.</p>
 */
Ext.ux.form.HtmlEditor.IndentOutdent = Ext.extend(Ext.ux.form.HtmlEditor.MidasCommand, {
    // private
    midasBtns: ['|', {
        cmd: 'indent',
        tooltip: {
            title: GO.lang.indent
        },
        overflowText: GO.lang.indent
    }, {
        cmd: 'outdent',
        tooltip: {
            title: GO.lang.outdent
        },
        overflowText: GO.lang.outdent
    }]
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.RemoveFormat
 * @extends Ext.ux.form.HtmlEditor.MidasCommand
 * <p>A plugin that creates a button on the HtmlEditor that will remove all formatting on selected text.</p>
 */
Ext.ux.form.HtmlEditor.RemoveFormat = Ext.extend(Ext.ux.form.HtmlEditor.MidasCommand, {
    midasBtns: ['|', {
        enableOnSelection: true,
        cmd: 'removeFormat',
        tooltip: {
            title: GO.lang.removeFormatting
        },
        overflowText: GO.lang.removeFormatting
    }]
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.SubSuperScript
 * @extends Ext.ux.form.HtmlEditor.MidasCommand
 * <p>A plugin that creates two buttons on the HtmlEditor for superscript and subscripting of selected text.</p>
 */
Ext.ux.form.HtmlEditor.SubSuperScript = Ext.extend(Ext.ux.form.HtmlEditor.MidasCommand, {
    // private
    midasBtns: ['|', {
        enableOnSelection: true,
        cmd: 'subscript',
        tooltip: {
            title: 'Subscript'
        },
        overflowText: 'Subscript'
    }, {
        enableOnSelection: true,
        cmd: 'superscript',
        tooltip: {
            title: 'Superscript'
        },
        overflowText: 'Superscript'
    }]
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.SpecialCharacters
 * @extends Ext.util.Observable
 * <p>A plugin that creates a button on the HtmlEditor for inserting special characters.</p>
 */
Ext.ux.form.HtmlEditor.SpecialCharacters = Ext.extend(Ext.util.Observable, {
    /**
     * @cfg {Array} specialChars
     * An array of additional characters to display for user selection.  Uses numeric portion of the ASCII HTML Character Code only. For example, to use the Copyright symbol, which is &#169; we would just specify <tt>169</tt> (ie: <tt>specialChars:[169]</tt>).
     */
    specialChars: [],
    /**
     * @cfg {Array} charRange
     * Two numbers specifying a range of ASCII HTML Characters to display for user selection. Defaults to <tt>[160, 256]</tt>.
     */
    charRange: [160, 256],
    // private
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
    },
    // private
    onRender: function(){
        var cmp = this.cmp;
        var btn = this.cmp.getToolbar().addButton({
            iconCls: 'x-edit-char',
                        tabIndex:-1,
            handler: function(){
                if (this.specialChars.length) {
                    Ext.each(this.specialChars, function(c, i){
                        this.specialChars[i] = ['&#' + c + ';'];
                    }, this);
                }
                for (i = this.charRange[0]; i < this.charRange[1]; i++) {
                    this.specialChars.push(['&#' + i + ';']);
                }
                var charStore = new Ext.data.ArrayStore({
                    fields: ['char'],
                    data: this.specialChars
                });
                this.charWindow = new Ext.Window({
                    title: 'Insert Special Character',
                    width: 436,
                    autoHeight: true,
                    layout: 'fit',
                    items: [{
                        xtype: 'dataview',
                        store: charStore,
                        ref: '../charView',
                        autoHeight: true,
                        multiSelect: true,
                        tpl: new Ext.XTemplate('<tpl for="."><div class="char-item">{char}</div></tpl><div class="x-clear"></div>'),
                        overClass: 'char-over',
                        itemSelector: 'div.char-item',
                        listeners: {
                            dblclick: function(t, i, n, e){
                                this.insertChar(t.getStore().getAt(i).get('char'));
                                this.charWindow.close();
                            },
                            scope: this
                        }
                    }],
                    buttons: [{
                        text: 'Insert',
                        handler: function(){
                            Ext.each(this.charWindow.charView.getSelectedRecords(), function(rec){
                                var c = rec.get('char');
                                this.insertChar(c);
                            }, this);
                            this.charWindow.close();
                        },
                        scope: this
                    }, {
                        text: 'Cancel',
                        handler: function(){
                            this.charWindow.close();
                        },
                        scope: this
                    }]
                });
                this.charWindow.show();
            },
            scope: this,
            tooltip: {
                title: 'Insert Special Character'
            },
            overflowText: 'Special Characters'
        });
    },
    /**
     * Insert a single special character into the document.
     * @param c String The special character to insert (not just the numeric code, but the entire ASCII HTML entity).
     */
    insertChar: function(c){
        if (c) {
            this.cmp.insertAtCursor(c);
        }
    }
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.Table
 * @extends Ext.util.Observable
 * <p>A plugin that creates a button on the HtmlEditor for making simple tables.</p>
 */
Ext.ux.form.HtmlEditor.Table = Ext.extend(Ext.util.Observable, {
    // private
    cmd: 'table',
    /**
     * @cfg {Array} tableBorderOptions
     * A nested array of value/display options to present to the user for table border style. Defaults to a simple list of 5 varrying border types.
     */
    tableBorderOptions: [['none', 'None'], ['1px solid #000', 'Sold Thin'], ['2px solid #000', 'Solid Thick'], ['1px dashed #000', 'Dashed'], ['1px dotted #000', 'Dotted']],
    // private
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
    },
    // private
    onRender: function(){
        var cmp = this.cmp;
        var btn = this.cmp.getToolbar().addButton({
            iconCls: 'x-edit-table',
                        tabIndex:-1,
            handler: function(){
                if (!this.tableWindow){
                    this.tableWindow = new Ext.Window({
                        title: 'Insert Table',
                        closeAction: 'hide',
                        items: [{
                            itemId: 'insert-table',
                            xtype: 'form',
                            border: false,
                            plain: true,
                            bodyStyle: 'padding: 10px;',
                            labelWidth: 60,
                            labelAlign: 'right',
                            items: [{
                                xtype: 'numberfield',
                                allowBlank: false,
                                allowDecimals: false,
                                fieldLabel: 'Rows',
                                name: 'row',
                                width: 60
                            }, {
                                xtype: 'numberfield',
                                allowBlank: false,
                                allowDecimals: false,
                                fieldLabel: 'Columns',
                                name: 'col',
                                width: 60
                            }, {
                                xtype: 'combo',
                                fieldLabel: 'Border',
                                name: 'border',
                                forceSelection: true,
                                mode: 'local',
                                store: new Ext.data.ArrayStore({
                                    autoDestroy: true,
                                    fields: ['spec', 'val'],
                                    data: this.tableBorderOptions
                                }),
                                triggerAction: 'all',
                                value: 'none',
                                displayField: 'val',
                                valueField: 'spec',
                                width: 90
                            }]
                        }],
                        buttons: [{
                            text: 'Insert',
                            handler: function(){
                                var frm = this.tableWindow.getComponent('insert-table').getForm();
                                if (frm.isValid()) {
                                    var border = frm.findField('border').getValue();
                                    var rowcol = [frm.findField('row').getValue(), frm.findField('col').getValue()];
                                    if (rowcol.length == 2 && rowcol[0] > 0 && rowcol[0] < 10 && rowcol[1] > 0 && rowcol[1] < 10) {
                                        var html = "<table>";
                                        for (var row = 0; row < rowcol[0]; row++) {
                                            html += "<tr>";
                                            for (var col = 0; col < rowcol[1]; col++) {
                                                html += "<td width='20%' style='border: " + border + ";'>" + row + "-" + col + "</td>";
                                            }
                                            html += "</tr>";
                                        }
                                        html += "</table>";
                                        this.cmp.insertAtCursor(html);
                                    }
                                    this.tableWindow.hide();
                                }else{
                                    if (!frm.findField('row').isValid()){
                                        frm.findField('row').getEl().frame();
                                    }else if (!frm.findField('col').isValid()){
                                        frm.findField('col').getEl().frame();
                                    }
                                }
                            },
                            scope: this
                        }, {
                            text: 'Cancel',
                            handler: function(){
                                this.tableWindow.hide();
                            },
                            scope: this
                        }]
                    });

                }else{
                    this.tableWindow.getEl().frame();
                }
                this.tableWindow.show();
            },
            scope: this,
            tooltip: {
                title: 'Insert Table'
            },
            overflowText: 'Table'
        });
    }
});
/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.Word
 * @extends Ext.util.Observable
 * <p>A plugin that creates a button on the HtmlEditor for pasting text from Word without all the jibberish html.</p>
 */
Ext.ux.form.HtmlEditor.Word = Ext.extend(Ext.util.Observable, {
    curLength: 0,
    lastLength: 0,
    lastValue: '',
    wordPasteEnabled: true,
    // private
    init: function(cmp){

        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
        this.cmp.on('initialize', this.onInit, this, {delay:100, single: true});

    },
    // private
    onInit: function(){

        /*Ext.EventManager.on(this.cmp.getDoc(), {
            'keyup': this.checkIfPaste,
            scope: this
        });*/
        this.lastValue = this.cmp.getValue();
        this.curLength = this.lastValue.length;
        this.lastLength = this.lastValue.length;

    },
    // private
    /*checkIfPaste: function(e){

        var diffAt = 0;

        if (e.V == e.getKey() && e.ctrlKey && this.wordPasteEnabled){

            //this.cmp.setValue(this.fixWordPaste(this.cmp.getValue()));
            //return;

            //console.log('ja');

            this.curLength = this.cmp.getValue().length;

            this.cmp.suspendEvents();

            diffAt = this.findValueDiffAt(this.cmp.getValue());
            var parts = [
                this.cmp.getValue().substr(0, diffAt),
                this.fixWordPaste(this.cmp.getValue().substr(diffAt, (this.curLength - this.lastLength))),
                this.cmp.getValue().substr((this.curLength - this.lastLength)+diffAt, this.curLength)
            ];
            this.cmp.setValue(parts.join(''));

            this.cmp.resumeEvents();

            this.lastLength = this.cmp.getValue().length;
            this.lastValue = this.cmp.getValue();
        }



    },*/
    // private
    findValueDiffAt: function(val){

        for (i=0;i<this.curLength;i++){
            if (this.lastValue[i] != val[i]){
                return i;
            }
        }

    },
    /**
     * Cleans up the jubberish html from Word pasted text.
     * @param wordPaste String The text that needs to be cleansed of Word jibberish html.
     * @return {String} The passed in text with all Word jibberish html removed.
     */
    fixWordPaste: function(wordPaste) {

        //console.log('fix paste');

        ///<\/?H2[^>]*>/ig, /<\/?H3[^>]*>/g, /<\/?H4[^>]*>/g,/<\/?H5[^>]*>/g, /<\/?H6[^>]*>/g,

        var removals = [/&nbsp;/ig, /[\r\n]/g, /<(xml|style)[^>]*>.*?<\/\1>/ig, /<\/?(meta|object|span)[^>]*>/ig,
            /<\/?[A-Z0-9]*:[A-Z]*[^>]*>/ig, /(lang|class|type|href|name|title|id|clear)=\"[^\"]*\"/ig, /style=(\'\'|\"\")/ig, /<![\[-].*?-*>/g,
            /MsoNormal/g, /<\\?\?xml[^>]*>/g, /<\/?o:p[^>]*>/g, /<\/?v:[^>]*>/g, /<\/?o:[^>]*>/g, /<\/?st1:[^>]*>/g, /&nbsp;/g,
            /<\/?SPAN[^>]*>/ig, /<\/?FONT[^>]*>/ig, /<\/?STRONG[^>]*>/ig, /<\/?H1[^>]*>/ig,  /<\/?P[^>]*><\/P>/g, /<!--(.*)-->/g, /<!--(.*)>/g, /<!(.*)-->/g, /<\\?\?xml[^>]*>/g,
            /<\/?o:p[^>]*>/g, /<\/?v:[^>]*>/g, /<\/?o:[^>]*>/g, /<\/?st1:[^>]*>/g, /style=\"[^\"]*\"/g, /style=\'[^\"]*\'/g, /lang=\"[^\"]*\"/g,
            /lang=\'[^\"]*\'/g, /class=\"[^\"]*\"/g, /class=\'[^\"]*\'/g, /type=\"[^\"]*\"/g, /type=\'[^\"]*\'/g, /href=\'#[^\"]*\'/g,
            /href=\"#[^\"]*\"/g, /name=\"[^\"]*\"/g, /name=\'[^\"]*\'/g, / clear=\"all\"/g, /id=\"[^\"]*\"/g, /title=\"[^\"]*\"/g,
            /<span[^>]*>/g, /<\/?span[^>]*>/g, /class=/g,/<script[^>]*>.*<\/script>/ig];

        Ext.each(removals, function(s){
            wordPaste = wordPaste.replace(s, "");
        });

        // keep the divs in paragraphs
        wordPaste = wordPaste.replace(/<div[^>]*>/g, "<p>");
        wordPaste = wordPaste.replace(/<\/?div[^>]*>/g, "</p>");
        return wordPaste;

    },
    // private
    onRender: function() {

        this.cmp.getToolbar().add({
            iconCls: 'x-edit-wordpaste',
            /*pressed: true,*/
                        tabIndex:-1,
            handler: function(t){
                //t.toggle(!t.pressed);
                //this.wordPasteEnabled = !this.wordPasteEnabled;
                this.cmp.setValue(this.fixWordPaste(this.cmp.getValue()));
            },
            scope: this,
            overflowText:'Word paste',
            tooltip: {
                text: GO.lang.pasteFromWord
            }
        });

    }
});/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.HR
 * @extends Ext.util.Observable
 * <p>A plugin that creates a button on the HtmlEditor for inserting a horizontal rule.</p>
 */
Ext.ux.form.HtmlEditor.HR = Ext.extend(Ext.util.Observable, {
    // private
    cmd: 'hr',
    // private
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
    },
    // private
    onRender: function(){
        var cmp = this.cmp;
        var btn = this.cmp.getToolbar().addButton({
            iconCls: 'x-edit-hr',
                        tabIndex:-1,
            handler: function(){
                if (!this.hrWindow){

                    this.hrWindow = new Ext.Window({
                                                width:400,
                                                autoHeight:true,
                                                resizable:false,
                        title: GO.lang.insertHorizontalRule,
                        closeAction: 'hide',
                                                focus: function(){
                                                    this.items.get(0).form.findField('hrwidth').focus(true);
                                                },
                        items: [{
                            itemId: 'insert-hr',
                            xtype: 'form',
                            border: false,
                            bodyStyle: 'padding: 10px;',
                            labelWidth: 60,
                            labelAlign: 'right',
                            items: [{
                                xtype: 'label',
                                html: GO.lang.insertHRtext+'<br/>&nbsp;'
                            }, {

                                xtype: 'textfield',
                                maskRe: /[0-9]|%/,
                                regex: /^[1-9][0-9%]{1,3}/,
                                fieldLabel: GO.lang.width,
                                name: 'hrwidth',
                                                                value:'100%',
                                width: 60,
                                 listeners: {
                                    specialkey: function(f, e){
                                        if ((e.getKey() == e.ENTER || e.getKey() == e.RETURN) && f.isValid()) {
                                            this.doInsertHR();
                                        }else{
                                            f.getEl().frame();
                                        }
                                    },
                                    scope: this
                                }
                            }]
                        }],
                        buttons: [{
                            text: GO.lang.cmdInsert,
                            handler: function(){
                                var frm = this.hrWindow.getComponent('insert-hr').getForm();
                                if (frm.isValid()){
                                    this.doInsertHR();
                                }else{
                                    frm.findField('hrwidth').getEl().frame();
                                }
                            },
                            scope: this
                        }, {
                            text: GO.lang.cmdCancel,
                            handler: function(){
                                this.hrWindow.hide();
                            },
                            scope: this
                        }]
                    });
                }else{
                    this.hrWindow.getEl().frame();
                }
                this.hrWindow.show();
            },
            scope: this,
            tooltip: {
                title: GO.lang.insertHorizontalRule
            },
            overflowText: GO.lang.insertHorizontalRule
        });
    },
    // private
    doInsertHR: function(){
        var frm = this.hrWindow.getComponent('insert-hr').getForm();
        if (frm.isValid()) {
            var hrwidth = frm.findField('hrwidth').getValue();
            if (hrwidth) {
                this.insertHR(hrwidth);
            } else {
                this.insertHR('100%');
            }
            frm.reset();
            this.hrWindow.hide();
        }
    },
    /**
     * Insert a horizontal rule into the document.
     * @param w String The width of the horizontal rule as the <tt>width</tt> attribute of the HR tag expects. ie: '100%' or '400' (pixels).
     */
    insertHR: function(w){
        this.cmp.insertAtCursor('<hr width="' + w + '">');
    }
});


/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @contributor Somani - http://www.sencha.com/forum/member.php?51567-Somani
 * @class Ext.ux.form.HtmlEditor.HeadingButtons
 * @extends Ext.ux.form.HtmlEditor.MidasCommand
 * <p>A plugin that creates a button on the HtmlEditor that will have H1 and H2 options. This is used when you want to restrict users to just a few heading types.</p>
 * NOTE: while 'heading' should be the command used, it is not supported in IE, so 'formatblock' is used instead. Thank you IE.
 */

Ext.ux.form.HtmlEditor.HeadingButtons = Ext.extend(Ext.ux.form.HtmlEditor.MidasCommand, {
    // private
    midasBtns: ['|', {
        enableOnSelection: true,
        cmd: 'formatblock',
        value: '<h1>',
        tooltip: {
            title: '1st Heading'
        },
        overflowText: '1st Heading'
    }, {
        enableOnSelection: true,
        cmd: 'formatblock',
        value: '<h2>',
        tooltip: {
            title: '2nd Heading'
        },
        overflowText: '2nd Heading'
    }]
}); 

/**
 * @author Shea Frederick - http://www.vinylfox.com
 * @class Ext.ux.form.HtmlEditor.HeadingMenu
 * @extends Ext.util.Observable
 * <p>A plugin that creates a menu on the HtmlEditor for selecting a heading size. Takes up less room than the heading buttons if your going to have all six heading sizes available.</p>
 */
Ext.ux.form.HtmlEditor.HeadingMenu = Ext.extend(Ext.util.Observable, {
    init: function(cmp){
        this.cmp = cmp;
        this.cmp.on('render', this.onRender, this);
    },
    // private
    onRender: function(){
        var cmp = this.cmp;
        var btn = this.cmp.getToolbar().addItem({
            xtype: 'combo',
            displayField: 'display',
            valueField: 'value',
            name: 'headingsize',
            forceSelection: true,
            mode: 'local',
            triggerAction: 'all',
            width: 100,
            emptyText: 'Heading',
            store: {
                xtype: 'arraystore',
                autoDestroy: true,
                fields: ['value','display'],
                data: [['H1','1st Heading'],['H2','2nd Heading'],['H3','3rd Heading'],['H4','4th Heading'],['H5','5th Heading'],['H6','6th Heading'],['P','Paragraph']]
            },
            listeners: {
                'select': function(combo,rec){
                    this.relayCmd('formatblock', '<'+rec.get('value')+'>');
                    combo.reset();
                },
                scope: cmp
            }
        });
    }
});

GO.form.HtmlEditor = function(config){
	config = config||{};
	
	Ext.applyIf(config, {
		border: false,
		enableFont:false,
		style: GO.settings.html_editor_font
	});
		
	config.plugins = config.plugins || [];

	if(!Ext.isArray(config.plugins))
		config.plugins=[config.plugins];
		
	var spellcheckInsertPlugin = new GO.plugins.HtmlEditorSpellCheck(this);
	var wordPastePlugin = new Ext.ux.form.HtmlEditor.Word();
	//var dividePlugin = new Ext.ux.form.HtmlEditor.Divider();
	//var tablePlugin = new Ext.ux.form.HtmlEditor.Table();
	var hrPlugin = new Ext.ux.form.HtmlEditor.HR();
	var ioDentPlugin = new Ext.ux.form.HtmlEditor.IndentOutdent();
	var ssScriptPlugin = new Ext.ux.form.HtmlEditor.SubSuperScript();
	var rmFormatPlugin = new Ext.ux.form.HtmlEditor.RemoveFormat();	
	
	if(GO.settings.pspellSupport)
		config.plugins.unshift(spellcheckInsertPlugin);

	config.plugins.unshift(
		wordPastePlugin,
		hrPlugin,
		ioDentPlugin,
		rmFormatPlugin,
		ssScriptPlugin
		);

	GO.form.HtmlEditor.superclass.constructor.call(this, config);
};

Ext.extend(GO.form.HtmlEditor,Ext.form.HtmlEditor, {

	setValue: function(value){
		
		if(this.win && Ext.isChrome){
			
			//set cursor position on top
			var range = this.win.document.createRange();
			range.setStart(this.win.document.body, 0);
			range.setEnd(this.win.document.body, 0);
			
			var sel = this.win.document.getSelection();
			
			sel.removeAllRanges();
			sel.addRange(range);
		}
		GO.form.HtmlEditor.superclass.setValue.call(this, value);
	},
	
//	syncValue: function(){
//		//In BasicForm.js this method is called by EXT
//		// When using the editor in sourceEdit then it may not call the syncValue function
//		if(!this.sourceEditMode){			
//			GO.form.HtmlEditor.superclass.syncValue.call(this);
//		}
//	},	

//	correctify: function(full, prefix, letter){
//		var regex = /([:\?]\s+)(.)/g;
//		return prefix + letter.toUpperCase();
//	},

//	urlify : function () {
//		
//		var inputText = this.getEditorBody().innerHTML;
//		var replacedText, replacePattern1, replacePattern2, replacePattern3;
//
////		//URLs starting with http://, https://, or ftp://
////		replacePattern1 = /(\b(https?|ftp):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gim;
////		replacedText = inputText.replace(replacePattern1, '<a href="$1" target="_blank">$1</a>');
////
////		//URLs starting with "www." (without // before it, or it'd re-link the ones done above).
////		replacePattern2 = /(^|[^\/])(www\.[\S]+(\b|$))/gim;
////		replacedText = replacedText.replace(replacePattern2, '$1<a href="http://$2" target="_blank">$2</a>');
////
////		//Change email addresses to mailto:: links.
////		replacePattern3 = /(\w+@[a-zA-Z_]+?\.[a-zA-Z]{2,6})/gim;
////		replacedText = replacedText.replace(replacePattern3, '<a href="mailto:$1">$1</a>');
//
//	replacedText = inputText.replace(/(?:^|[^\>])((ftp|http|https|file):\/\/[\S]+(\b|$))/gim, '<a href="http://$1">$1</a>');
//
//		
//		this.getEditorBody().innerHTML=replacedText;
//		
//		console.log(this.getEditorBody().innerHTML);
//		
//	},
	onFirstFocus : function(){
		
		this.initPunctuationCorrection();
		
		this.activated = true;
		this.disableItems(this.readOnly);
		if(Ext.isGecko){ // prevent silly gecko errors
			/*this.win.focus();
            var s = this.win.getSelection();
            if(!s.focusNode || s.focusNode.nodeType != 3){
                var r = s.getRangeAt(0);
                r.selectNodeContents(this.getEditorBody());
                r.collapse(true);
                this.deferFocus();
            }*/
			try{
				this.execCmd('useCSS', true);
				this.execCmd('styleWithCSS', false);
			}catch(e){}
		}
		this.fireEvent('activate', this);
	},
	createToolbar : Ext.form.HtmlEditor.prototype.createToolbar.createSequence(function(editor){
		this.tb.enableOverflow=true;
	}),
	
	initPunctuationCorrection: function() {
		if(GO.settings.auto_punctuation!=1)
			return;
		
		var me = this;
    var doc = me.getDoc();

    Ext.EventManager.on(doc, 'keydown', me.correctPunctuation, me);
	},
	
	lastChar: false,
	autoCapitalizeNextChar: true,
	
	correctPunctuation : function(event) {
		var		enter=13,
				spacechar=32,
				dotchar=190,
				questonmark=191,
				exclamationmark=49,
				achar=65,
				zchar=90;
		
		//IE doesn't has event.button and uses event.keyCode
		var key = event.button+1 || event.keyCode;	
			
		var sentenceEnds = [dotchar, exclamationmark, questonmark];
		
		if(!this.autoCapitalizeNextChar){
			switch(key){

				case spacechar:
					if(sentenceEnds.indexOf(this.lastChar)!=-1){
						this.autoCapitalizeNextChar=true;
					}
				break;
				case enter:				
						this.autoCapitalizeNextChar=true;
					break;
			}
		}else
		{
			if(key>=achar && key<=zchar) {
				var char = String.fromCharCode(key);
				event.preventDefault();
				this.insertAtCursor(char.toUpperCase());
				
				this.autoCapitalizeNextChar=false;
				
			}else if(key!=spacechar && key!=enter){			
				this.autoCapitalizeNextChar=false;
			}
		}
		
		this.lastChar=key;
	},

	getDocMarkup : function(){
		var h = Ext.fly(this.iframe).getHeight() - this.iframePad * 2;
		return String.format('<html><head><style type="text/css">body,p,td,div,span{'+GO.settings.html_editor_font+'};body{border: 0; margin: 0; padding: {0}px; height: {1}px; cursor: text}body p{margin:0px;}</style></head><body></body></html>', this.iframePad, h);
	},
	fixKeys : function(){ // load time branching for fastest keydown performance
		if(Ext.isIE){
			return function(e){
				var k = e.getKey(),
				doc = this.getDoc(),
				r;
				if(k == e.TAB){
					e.stopEvent();
					r = doc.selection.createRange();
					if(r){
						r.collapse(true);
						r.pasteHTML('&nbsp;&nbsp;&nbsp;&nbsp;');
						this.deferFocus();
					}
				}else if(k == e.ENTER){
				//                    r = doc.selection.createRange();
				//                    if(r){
				//                        var target = r.parentElement();
				//                        if(!target || target.tagName.toLowerCase() != 'li'){
				//                            e.stopEvent();
				//                            r.pasteHTML('<br />');
				//                            r.collapse(false);
				//                            r.select();
				//                        }
				//                    }
				}
			};
		}else if(Ext.isOpera){
			return function(e){
				var k = e.getKey();
				if(k == e.TAB){
					e.stopEvent();
					this.win.focus();
					this.execCmd('InsertHTML','&nbsp;&nbsp;&nbsp;&nbsp;');
					this.deferFocus();
				}
			};
		}else if(Ext.isWebKit){
			return function(e){
				var k = e.getKey();
				if(k == e.TAB){
					e.stopEvent();
					this.execCmd('InsertText','\t');
					this.deferFocus();
				}else if(k == e.ENTER){
					e.stopEvent();
					var doc = this.getDoc();
					if (doc.queryCommandState('insertorderedlist') ||
						doc.queryCommandState('insertunorderedlist')) {
						this.execCmd('InsertHTML', '</li><br /><li>');
					} else {
						this.execCmd('InsertHtml','<br />&nbsp;');
						this.execCmd('delete');
					}
					this.deferFocus();
				}
			};
		}
	}(),
        
	//Overwritten to fix font size bug in chrome
	adjustFont: function(btn){
		var adjust = btn.getItemId() == 'increasefontsize' ? 1 : -1,
		doc = this.getDoc(),
		v = parseInt(doc.queryCommandValue('FontSize') || 2, 10);
		if(Ext.isAir){
            
            
			if(v <= 10){
				v = 1 + adjust;
			}else if(v <= 13){
				v = 2 + adjust;
			}else if(v <= 16){
				v = 3 + adjust;
			}else if(v <= 18){
				v = 4 + adjust;
			}else if(v <= 24){
				v = 5 + adjust;
			}else {
				v = 6 + adjust;
			}
			v = v.constrain(1, 6);
		}else{
			v = Math.max(1, v+adjust);
		}
		this.execCmd('FontSize', v);
	},      
          
	// private
//	onEditorEvent : function(e){
//		this.updateToolbar();
////		console.log(e);
//		
//		if(e.keyCode==32 || e.keyCode==12)
//			this.urlify();
//	},
	updateToolbar: function(){

		/*
				 * I override the default function here to increase performance.
				 * ExtJS syncs value every 100ms while typing. This is slow with large
				 * html documents. I manually call syncvalue when the message is sent
				 * so it's certain the right content is submitted.
				 */

		GO.mainLayout.timeout(0); // stop logout timer

		if(this.readOnly){
			return;
		}

		if(!this.activated){
			this.onFirstFocus();
			return;
		}

		var btns = this.tb.items.map,
		doc = this.getDoc();

		if(this.enableFont && !Ext.isSafari2){
			var name = (doc.queryCommandValue('FontName')||this.defaultFont).toLowerCase();
			if(name != this.fontSelect.dom.value){
				this.fontSelect.dom.value = name;
			}
		}
		if(this.enableFormat){
			btns.bold.toggle(doc.queryCommandState('bold'));
			btns.italic.toggle(doc.queryCommandState('italic'));
			btns.underline.toggle(doc.queryCommandState('underline'));
		}
		if(this.enableAlignments){
			btns.justifyleft.toggle(doc.queryCommandState('justifyleft'));
			btns.justifycenter.toggle(doc.queryCommandState('justifycenter'));
			btns.justifyright.toggle(doc.queryCommandState('justifyright'));
		}
		if(!Ext.isSafari2 && this.enableLists){
			btns.insertorderedlist.toggle(doc.queryCommandState('insertorderedlist'));
			btns.insertunorderedlist.toggle(doc.queryCommandState('insertunorderedlist'));
		}

		Ext.menu.MenuMgr.hideAll();

		//This property is set in javascript/focus.js. When the mouse goes into
		//the editor iframe it thinks it has lost the focus.
		GO.hasFocus=true;

		
	//this.syncValue();
	}
		
});

Ext.reg('xhtmleditor', GO.form.HtmlEditor);