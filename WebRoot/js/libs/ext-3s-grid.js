Ext.data.Record.prototype.isNew=function(){
  return this.newRecord;
};
Ext.data.Record.prototype.isDeleted=function(){
  return this.deleted;
};
Ext.namespace('Ext.ux');
Ext.ux.data.Store=Ext.extend(Ext.data.Store,{constructor:function(config){
  Ext.ux.data.Store.superclass.constructor.call(this,config);
  this.addListener("beforeload",function(action,options,response){
    this.deleted=[];
    this.modified=[];
    delete this.serverCount;
  });
},load:function(options){
   if(this.grid){
    if(!options)
      options={};
    options.scope=this;
    options.callback=function(rs,options,success){
      if(success)
        options.scope.grid.initSubmitValue();
    };
  }
  Ext.ux.data.Store.superclass.load.call(this,options);
},reload:function(options){
  this.removeAll();
  this.relaodAction.desktop=this.desktop;
  var relaodAction=new Ext.sss.Action(this.relaodAction);
  var submitParams=relaodAction.getSubmitParams();
  if(!submitParams){
    this.desktop.alert(this.invalidForm,"");
    return;
  }
  submitParams["reload"]="true";
  options=options||{};
  options.params=Ext.applyIf(options.params||{},submitParams);
  this.load(Ext.applyIf(options,this.lastOptions));
},getServerCount:function(){
  if(!this.serverCount)
    this.serverCount=this.getCount();
  return this.serverCount;
},getServerCountNew:function(){
  this.serverCount=this.getServerCount()+1;
  return this.serverCount;
},findRecordByField:function(fieldName,fieldValue){
  var index=this.find(fieldName,fieldValue);
  if(index<0)
    return;
  return this.getAt(index);
},removeField:function(name){
  this.recordType.prototype.fields.removeKey(name);
  this.each(function(r){
    delete r.data[name];
    if(r.modified){
      delete r.modified[name];
    }
  });
},addField:function(field,index){
  if(typeof field==="string"){
    field={name:field};
  }
  if(Ext.isEmpty(this.recordType)){
    this.recordType=Ext.data.Record.create([]);
  }
  field=new Ext.data.Field(field);
  if(Ext.isEmpty(index)||index===-1){
    this.recordType.prototype.fields.replace(field);
  }else{
    this.recordType.prototype.fields.insert(index,field);
  }
  if(typeof field.defaultValue!=="undefined"){
    this.each(function(r){
      if(typeof r.data[field.name]==="undefined"){
        r.data[field.name]=field.defaultValue;
      }
    });
  }
}});
Ext.ns('Ext.ux.grid');
Ext.ux.grid.CheckColumn=Ext.extend(Ext.grid.Column,{submitOffValue:'false',submitOnValue:'true',sortable:false,hideable:false,width:25,menuDisabled:true,
  resizable:false,headerId:'',changeValueHandler:false,constructor:function(config){
    Ext.ux.grid.CheckColumn.superclass.constructor.call(this,config);
    if(!config.hideable){
      this.headerId=Ext.Component.AUTO_ID;
      this.header={tag:'div',id:this.headerId,cls:'x-grid3-check-col'};
      if(this.editable)
        this.header.onmousedown=this.onMouseDownHd();
      this.header=Ext.DomHelper.createTemplate(this.header);
      this.header.compile();
      this.header=this.header.html;
      this.width=25;
    }else{
      this.resizable=true;
    }
  },onMouseDownHd:function(){
    return String.format(Ext.ux.grid.CheckColumn.tempalteonMouseDownHd,this.headerId,this.gridId,this.submitOffValue,this.submitOnValue,this.dataIndex);
  },processEvent:function(name,e,grid,rowIndex,colIndex){
    if(name=='mousedown'&&this.editable){
      var record=grid.store.getAt(rowIndex);
      value=!this.convertValue(record.data[this.dataIndex]);
      record.set(this.dataIndex,value?this.submitOnValue:this.submitOffValue);
      return false;
    }else{
      return Ext.grid.ActionColumn.superclass.processEvent.apply(this,arguments);
    }
  },convertValue:function(v){
    return(v===true||v==='true'||v==1||v===this.submitOnValue||String(v).toLowerCase()==='on');
  },renderer:function(value,metadata,record,rowIndex,colIndex,store){
    metadata.css+=' x-grid3-check-col-td';
    var isCheck=this.convertValue(value);
    if(Ext.isDefined(this.changeValueHandler)&&Ext.isFunction(this.changeValueHandler))
      this.changeValueHandler.call(this,isCheck,metadata,record,rowIndex,colIndex,store);
    return String.format('<div class="x-grid3-check-col{0}">&#160;</div>',isCheck?'-on':'');
  }});
Ext.ux.grid.CheckColumn.tempalteonMouseDownHd="Ext.ux.grid.CheckColumn.onMouseDownHdEvent(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\')";
Ext.ux.grid.CheckColumn.onMouseDownHdEvent=function(id,gridId,submitOffValue,submitOnValue,colIndex){
  var eltHead=Ext.get(id);
  var grid=Ext.getCmp(gridId);
  var isCheck=eltHead.hasClass('x-grid3-check-col');
  var curentClass='x-grid3-check-col'+(isCheck?'':'-on');
  var classVale='x-grid3-check-col'+(isCheck?'-on':'');
  eltHead.replaceClass(curentClass,classVale);
  for(var index=0;index<grid.store.getCount();index++){
    var record=grid.store.getAt(index);
    record.set(colIndex,isCheck?submitOnValue:submitOffValue);
  }
};
Ext.grid.Column.types.checkcolumn=Ext.ux.grid.CheckColumn;
Ext.preg('checkcolumn',Ext.ux.grid.CheckColumn);
Ext.ns('Ext.ux.liveGrid');
Ext.ux.liveGrid.CheckColumn=Ext.extend(Ext.grid.Column,{submitOffValue:'false',submitOnValue:'true',sortable:false,hideable:false,width:25,menuDisabled:true,
  resizable:false,headerId:'',changeValueHandler:false,constructor:function(config){
    Ext.ux.liveGrid.CheckColumn.superclass.constructor.call(this,config);
    if(!config.hideable){
      this.headerId=Ext.Component.AUTO_ID;
      this.header={tag:'div',id:this.headerId,cls:'x-grid3-check-col'};
      if(this.editable)
        this.header.onmousedown=this.onMouseDownHd();
      this.header=Ext.DomHelper.createTemplate(this.header);
      this.header.compile();
      this.header=this.header.html;
      this.width=25;
    }
    this.editabless=this.editable;
    this.editable=false;
  },onMouseDownHd:function(){
    return String.format(Ext.ux.liveGrid.CheckColumn.tempalteonMouseDownHd,this.headerId,this.gridId,this.submitOffValue,this.submitOnValue,this.dataIndex);
  },processEvent:function(name,e,grid,rowIndex,colIndex){
    if(name=='mousedown'&&this.editabless){
      var record=grid.store.getAt(rowIndex);
      value=!this.convertValue(record.data[this.dataIndex]);
      fieldValue=value?this.submitOnValue:this.submitOffValue;
      var submitFieldRecords=new Object();
      submitFieldRecords[this.dataIndex+"["+record.data['index']+"]"]=fieldValue;
      var values={};
      values["values"]=submitFieldRecords;
      values=Ext.encode(values);
      var params={};
      params[grid.id]=values;
      grid.desktop.createHandelServerAction({eventName:'save',isWait:false,componentId:grid.id,xtype:'ajax',params:params}).call(this);
      record.set(this.dataIndex,fieldValue);
      return false;
    }else{
      return Ext.grid.ActionColumn.superclass.processEvent.apply(this,arguments);
    }
  },convertValue:function(v){
    return(v===true||v==='true'||v==1||v===this.submitOnValue||String(v).toLowerCase()==='on');
  },renderer:function(value,metadata,record,rowIndex,colIndex,store){
    metadata.css+=' x-grid3-check-col-td';
    var isCheck=this.convertValue(value);
    if(Ext.isDefined(this.changeValueHandler)&&Ext.isFunction(this.changeValueHandler))
      this.changeValueHandler.call(this,isCheck,metadata,record,rowIndex,colIndex,store);
    return String.format('<div class="x-grid3-check-col{0}">&#160;</div>',isCheck?'-on':'');
  }});
Ext.ux.liveGrid.CheckColumn.tempalteonMouseDownHd="Ext.ux.liveGrid.CheckColumn.onMouseDownHdEvent(\'{0}\',\'{1}\',\'{2}\',\'{3}\',\'{4}\')";
Ext.ux.liveGrid.CheckColumn.onMouseDownHdEvent=function(id,gridId,submitOffValue,submitOnValue,colIndex){
  var eltHead=Ext.get(id);
  var grid=Ext.getCmp(gridId);
  var isCheck=eltHead.hasClass('x-grid3-check-col');
  var curentClass='x-grid3-check-col'+(isCheck?'':'-on');
  var classVale='x-grid3-check-col'+(isCheck?'-on':'');
  eltHead.replaceClass(curentClass,classVale);
  var params={};
  params[grid.id+"_chechAll_"+colIndex]=isCheck;
  grid.desktop.createHandelServerAction({eventName:'checkAll',componentId:grid.id,xtype:'ajax',params:params,isWait:false}).call(this);
  grid.store.each(function(r){
    r.set(colIndex,isCheck?submitOnValue:submitOffValue);
  });
};
Ext.grid.Column.types.checkLivecolumn=Ext.ux.liveGrid.CheckColumn;
Ext.preg('checklivecolumn',Ext.ux.liveGrid.CheckColumn);
Ext.ux.grid.ComboColumn=Ext.extend(Ext.grid.Column,{renderer:function(value,metaData,recordGrid,rowIndex,colIndex,store){
  var editorStore=Ext.StoreMgr.lookup(this.editorStoreId);
  if(this.editorRenderer)
    editorRenderer.call(this,value,metaData,record,rowIndex,colIndex,store);
  var record=this.findRecord(this.valueField,value);
  if(record){
    value=record.get(this.displayField);
  }else if(this.dataIndexDisplay){
    value=recordGrid.get(this.dataIndexDisplay);
  }
  return value;
},findRecord:function(prop,value){
  var editorStore=Ext.StoreMgr.lookup(this.editorStoreId);
  var record;
  if(editorStore.getCount()>0){
    editorStore.each(function(r){
      if(r.data[prop]==value){
        record=r;
        return false;
      }
    });
  }
  return record;
}});
Ext.grid.Column.types.comboColumn=Ext.ux.grid.ComboColumn;
Ext.preg('comboColumn',Ext.ux.grid.ComboColumn);
Ext.ux.grid.Validator=function(config){
  this.init=function(grid){
    Ext.apply(grid,{isCellValid:function(col,row){
      if(!this.colModel.isCellEditable(col,row)){
        return true;
      }
      var ed=this.colModel.getCellEditor(col,row);
      if(!ed){
        return true;
      }
      var record=this.store.getAt(row);
      if(!record){
        return true;
      }
      var field=this.colModel.getDataIndex(col);
      ed.field.setValue(record.data[field]);
      var valid=ed.field.isValid(true);
      if(!valid){
        this.markCellInvalid(row,col,'Valeur dans cette cellule est invalide');
      }else{
        this.clearCellInvalid(row,col);
      }
      return valid;
    },isActiveEditorValid:function(col,row){
      if(!this.colModel.isCellEditable(col,row)){
        return true;
      }
      var ed=this.colModel.getCellEditor(col,row);
      if(!ed){
        return true;
      }
      var error=ed.field.getErrors(ed.field.getValue());
      if(!Ext.sss.StringUtils.isBlank(error)){
        this.markCellInvalid(row,col,error);
        return false;
      }else{
        this.clearCellInvalid(row,col);
        return true;
      }
    },isValid:function(editInvalid){
      var cols=this.colModel.getColumnCount();
      var rows=this.store.getCount();
      var r,c;
      var valid=true;
      for(r=0;r<rows;r++){
        for(c=0;c<cols;c++){
          if(!this.isCellValid(c,r)){
            valid=false;
          }
        }
      }
      if(editInvalid&&!valid){
        this.startEditing(r,c);
      }
      return valid;
    },markCellInvalid:function(row,col,qtip){
      var cell=this.view.getCell(row,col);
      if(cell){
        Ext.fly(cell).addClass('ux-grid3-invalid-cell');
        Ext.fly(cell).dom.qtip=qtip;
        Ext.fly(cell).dom.qclass='x-form-invalid-tip';
        if(Ext.QuickTips){
          Ext.QuickTips.enable();
        }
      }
    },clearCellInvalid:function(row,col){
      var cell=this.view.getCell(row,col);
      if(cell){
        Ext.fly(cell).removeClass('ux-grid3-invalid-cell');
        Ext.fly(cell).dom.qtip='';
      }
    }});
  };
};
Ext.grid.ColumnModel.override({isCellEditable:function(colIndex,rowIndex){
  if(Ext.isFunction(this.grid.editableHandler)){
    var store=this.grid.store;
    var record=store.getAt(rowIndex);
    var callDefauld=this.isDefauldCellEditable.createDelegate(this,[colIndex,rowIndex
    ]);
    return this.grid.editableHandler.call(this,colIndex,rowIndex,this.grid,store,record,callDefauld);
  }
  return this.isDefauldCellEditable(colIndex,rowIndex);
},isDefauldCellEditable:function(colIndex,rowIndex){
  var c=this.config[colIndex],ed=c.editable;
  return !!(ed||(!Ext.isDefined(ed)&&c.editor));
}});
Ext.ux.grid.GridPanel=Ext.extend(Ext.grid.GridPanel,{indexRowName:'index',constructor:function(config){
  Ext.ux.grid.EditorGridPanel.superclass.constructor.call(this,config);
  this.desktop.addEditorGrid(this.id);
},getRecord:function(index){
  return this.store.getAt(index);
},getSelectedRecord:function(){
  if(!this.getSelectionModel().hasSelection())
    return;
  if(Ext.isInstanceof(this.getSelectionModel(),Ext.grid.CellSelectionModel)){
    var sel=this.getSelectionModel().getSelectedCell();
    return this.getRecord(sel[0]);
  }
  return this.getSelectionModel().getSelected();
},getSelectedServerIndex:function(){
  return this.getSelectedRecord().get(this.indexRowName);
},getSelected:function(){
  if(!this.getSelectionModel().hasSelection())
    return;
  if(Ext.isInstanceof(this.getSelectionModel(),Ext.grid.CellSelectionModel))
    return this.getSelectionModel().getSelectedCell();
  var record=this.getSelectionModel().getSelected();
  return [this.store.indexOf(record),-1
  ];
},setEditFocus:function(){
  var fieldObjectNext=Ext.getCmp(this.nextElementId);
  if(fieldObjectNext&&fieldObjectNext.nextFieldEvent)
    fieldObjectNext.nextFieldEvent();
},select:function(rowIndex,colIndex){
  if(Ext.isInstanceof(this.getSelectionModel(),Ext.grid.CellSelectionModel)){
    if(!colIndex||colIndex<0)
      colIndex=0;
    this.getSelectionModel().select(rowIndex,colIndex);
  }else
    this.getSelectionModel().selectRow(index);
}});
Ext.ux.grid.EditorGridPanel=Ext.extend(Ext.grid.EditorGridPanel,{clicksToEdit:2,indexRowName:'index',isEditorGrid:true,newRecordHandler:null,
  isUpAndDowRowMenu:true,isSavedMenu:true,editableHandler:null,supMenuItemText:'Supprimer(Ctrl+r)',supMenuItemIcon:'images/action/supprimer.png',
  addMenuItemText:'Ajouter(Ctrl+i)',addMenuItemIcon:'images/action/creer.png',upMenuItemText:'DÃ©lacer vers le haut(Ctrl+u)',
  upMenuItemIcon:'images/action/up.png',downMenuItemText:'DÃ©placer vers le bas(Ctrl+d)',downMenuItemIcon:'images/action/down.png',
  saveMenuItemText:'Enregistrer(Ctrl+s)',saveMenuItemIcon:'images/action/save.png',keyUpValue:"u",keyDownValue:"d",keyAddValue:"i",keyRemoveValue:"r",
  keySaveValue:"s",keyExitValue:"q",keySelValue:Ext.EventObject.ENTER,constructor:function(config){
    config.plugins=config.plugins?config.plugins:[];
    config.plugins.push(new Ext.ux.grid.Validator());
    config.colModel.grid=this;
    this.deleted=[];
    if(config.selModel){
      config.selModel.onEditorKey=function(field,e){};
    }
    if(config.colModel){
      var columns=config.colModel.config;
      for(i=0;i<columns.length;i++){
        if(columns[i].editor){
          columns[i].editor.addListener("specialkey",function(field,e){
            switch(e.keyCode){
              case e.TAB:
                e.stopEvent();
                this.copySlectedCellRecord();
              break;
              case e.ENTER:
                if(!e.ctrlKey){
                  e.stopEvent();
                  this.nextEditableCellActive();
                }else{
                  e.stopEvent();
                  var fieldObjectNext=Ext.getCmp(this.nextElementId);
                  if(fieldObjectNext&&fieldObjectNext.setEditFocus){
                    fieldObjectNext.setEditFocus();
                  }else if(fieldObjectNext&&fieldObjectNext.focus){
                    fieldObjectNext.focus();
                  }
                }
              break;
            }
          },this);
        }
      }
    }
    Ext.ux.grid.EditorGridPanel.superclass.constructor.call(this,config);
    if(this.getStore()){
      this.getStore().grid=this;
    }
    this.initKeys();
    this.saveEvent=this.desktop.createHandelServerAction({eventName:'save',isWait:false,success:function(response,options){
      var grid=Ext.getCmp(options.componentId);
      grid.initSubmitValue();
    },componentId:this.id,isssGrid:false,xtype:'ajax',paramsField:[this.id
    ]});
    this.createCtxMenu();
    if(this.isctxRowMenu)
      this.addListener('containercontextmenu',function(grid,e){
        e.stopEvent();
        this.ctxContainerMenu.showAt(e.xy);
      });
    if(this.isctxContainerMenu)
      this.addListener('rowcontextmenu',function(grid,rowIndex,e){
        e.stopEvent();
        this.ctxRowMenu.showAt(e.xy);
      });
    this.on('beforeedit',this.beforeeditValue);
    this.desktop.addEditorGrid(this.id);
  },setEditFocusFirst:function(){
    this.startEditingEdit();
  },setEditFocus:function(){
    this.startEditingEdit();
  },onRender:function(ct,position){
    Ext.ux.grid.EditorGridPanel.superclass.onRender.call(this,ct,position);
  },initKeys:function(){
    this.keys=this.keys?this.keys:[];
    if(this.isUpAndDowRowMenu){
      this.addKeyValue(this.keyUpValue,function(){
        this.moveRecordToUp();
      });
      this.addKeyValue(this.keyDownValue,function(){
        this.moveRecordToDown();
      });
    }
    if(!this.fixedNumberRow||!this.isDeleteRecord())
      this.addKeyValue(this.keyRemoveValue,function(){
        this.deleteRecord();
      });
    if(!this.fixedNumberRow||!this.isAddRecord())
      this.addKeyValue(this.keyAddValue,function(){
        this.addRecord();
      });
    this.addKeyValue(this.keySaveValue,function(){
      this.saveData();
    });
    if(this.nextElementId)
      this.addKeyValue(this.keyExitValue,function(){
        var fieldObjectNext=Ext.getCmp(this.nextElementId);
        if(fieldObjectNext&&fieldObjectNext.setEditFocus){
          fieldObjectNext.setEditFocus();
        }else if(fieldObjectNext&&fieldObjectNext.focus){
          fieldObjectNext.focus();
        }
      },false);
    this.addKeyValue(this.keySelValue,function(){
      var row=0;
      var cell=-1;
      var select=this.getSelected();
      if(select){
        row=select[0];
        cell=select[1];
      }
      this.nextEditableCell(row,cell);
    });
  },addKeyValue:function(key,fn,ctrl){
    if(!Ext.isDefined(ctrl))
      ctrl=true;
    this.keys.push({key:key,ctrl:ctrl,stopEvent:true,fn:fn,scope:this});
  },createCtxMenu:function(){
    this.isctxRowMenu=false;
    this.isctxContainerMenu=false;
    this.ctxRowMenu=new Ext.menu.Menu();
    this.ctxContainerMenu=new Ext.menu.Menu();
    this.isIn=false;
    this.fixedNumberRow=Ext.isDefined(this.fixedNumberRow)?this.fixedNumberRow:false;
    var supMenuItem=this.createCtxMenuItem(this.supMenuItemText,this.supMenuItemIcon,this.deleteRecord);
    var addMenuItem=this.createCtxMenuItem(this.addMenuItemText,this.addMenuItemIcon,this.addRecord);
    var upMenuItem=this.createCtxMenuItem(this.upMenuItemText,this.upMenuItemIcon,this.moveRecordToUp);
    var downMenuItem=this.createCtxMenuItem(this.downMenuItemText,this.downMenuItemIcon,this.moveRecordToDown);
    var saveMenuItem=this.createCtxMenuItem(this.saveMenuItemText,this.saveMenuItemIcon,this.saveData);
    if(!this.fixedNumberRow&&this.isAddRecord()){
      this.isctxRowMenu=true;
      this.ctxRowMenu.addItem(addMenuItem);
      if(this.bbar)
        this.bbar.addItem(addMenuItem);
    }
    if(!this.fixedNumberRow&&this.isDeleteRecord()){
      this.isctxRowMenu=true;
      this.ctxRowMenu.addItem(supMenuItem);
    }
    if(this.isUpAndDowRowMenu){
      this.isctxRowMenu=true;
      this.ctxRowMenu.addItem(upMenuItem);
      this.ctxRowMenu.addItem(downMenuItem);
    }
    if(this.isSavedMenu){
      this.isctxRowMenu=true;
      this.ctxRowMenu.addItem(saveMenuItem);
      if(this.bbar)
        this.bbar.addItem(saveMenuItem);
    }
    if(!this.fixedNumberRow||!this.isAddRecord()){
      this.isctxContainerMenu=true;
      this.ctxContainerMenu.addItem(addMenuItem);
    }
    if(this.isSavedMenu){
      this.isctxContainerMenu=true;
      this.ctxContainerMenu.addItem(saveMenuItem);
    }
    this.isIn=true;
  },createCtxMenuItem:function(text,icon,handler){
    return {text:text,icon:icon,handler:handler.createDelegate(this)};
  },getRecord:function(index){
    return this.store.getAt(index);
  },getSelectedServerIndex:function(){
    return this.getSelectedRecord().get(this.indexRowName);
  },getSelectedRecord:function(){
    if(!this.getSelectionModel().hasSelection())
      return;
    if(Ext.isInstanceof(this.getSelectionModel(),Ext.grid.CellSelectionModel)){
      var sel=this.getSelectionModel().getSelectedCell();
      return this.getRecord(sel[0]);
    }
    return this.getSelectionModel().getSelected();
  },getSelected:function(){
    if(!this.getSelectionModel().hasSelection())
      return;
    if(Ext.isInstanceof(this.getSelectionModel(),Ext.grid.CellSelectionModel))
      return this.getSelectionModel().getSelectedCell();
    var record=this.getSelectionModel().getSelected();
    return [this.store.indexOf(record),-1
    ];
  },select:function(rowIndex,colIndex){
    if(Ext.isInstanceof(this.getSelectionModel(),Ext.grid.CellSelectionModel)){
      if(!colIndex||colIndex<0)
        colIndex=0;
      this.getSelectionModel().select(rowIndex,colIndex);
    }else
      this.getSelectionModel().selectRow(index);
  },getDeletedRecords:function(){
    return this.deleted;
  },copySlectedCellRecord:function(){
    var row=this.activeEditor.row;
    var col=this.activeEditor.col;
    var col=this.getColumnModel().config[col];
    if(row-1<0)
      return;
    rec=this.store.getAt(row-1);
    this.activeEditor.setValue(rec.data[col.dataIndex]);
  },moveRecord:function(i,j){
    var totalRows=this.store.getCount()-1;
    if(i<0||i>totalRows||j<0||j>totalRows)
      return false;
    reci=this.store.getAt(i);
    recj=this.store.getAt(j);
    this.store.remove(reci);
    this.store.remove(recj);
    this.store.insert(j,reci);
    this.store.insert(i,recj);
    return true;
  },moveRecordToDown:function(){
    var select=this.getSelected();
    if(!select)
      return false;
    if(this.moveRecord(select[0]+1,select[0]))
      this.select(select[0]+1,select[1]);
  },moveRecordToUp:function(){
    var select=this.getSelected();
    if(!select)
      return false;
    if(this.moveRecord(select[0],select[0]-1))
      this.select(select[0]-1,select[1]);
  },isDeleteRecord:function(idx){
    var isDeleteRow=true;
    if(Ext.isDefined(this.isDeleteRow)){
      if(Ext.isFunction(this.isDeleteRow)){
        if(!idx&&this.isIn)
          idx=this.store.getCount();
        isDeleteRow=this.isDeleteRow.call(this,this.store,idx,this.isIn?this.store.getAt(idx):false);
      }else{
        isDeleteRow=this.isDeleteRow;
      }
    }
    return isDeleteRow;
  },isAddRecord:function(idx){
    var isAdded=true;
    if(Ext.isDefined(this.isAddRow)){
      if(Ext.isFunction(this.isAddRow)){
        if(!idx&&this.isIn)
          idx=this.store.getCount();
        isAdded=this.isAddRow.call(this,this.store,idx,this.isIn?this.store.getAt(idx):false);
      }else{
        isAdded=this.isAddRow;
      }
    }
    return isAdded;
  },deleteRecord:function(){
    var select=this.getSelected();
    if(!select)
      return false;
    var record=this.getRecord(select[0]);
    var idx=select[0];
    var isDeleteRow=this.isDeleteRecord(idx);
    if(!isDeleteRow)
      return false;
    this.store.removeAt(idx);
    if(idx>0)
      idx--;
    record.deleted=true;
    if(!record.isNew()){
      this.deleted.push(record.data[this.indexRowName]);
    }
    if(idx<this.store.getCount())
      this.select(idx,select[1]);
    if(Ext.isFunction(this.deleteRecordHandler)){
      this.deleteRecordHandler.call(this,this.store,idx);
    }
    return true;
  },addRecord:function(){
    this.fixedNumberRow=Ext.isDefined(this.fixedNumberRow)?this.fixedNumberRow:false;
    this.maxNumberRow=Ext.isDefined(this.maxNumberRow)&&this.maxNumberRow!=0?this.maxNumberRow:-1;
    if(this.fixedNumberRow)
      return false;
    var totalRows=this.store.getCount();
    if(this.maxNumberRow==totalRows)
      return false;
    var idx=totalRows;
    var select=this.getSelected();
    if(select)
      idx=select[0]+1;
    var isAdded=this.isAddRecord(idx);
    if(!isAdded)
      return false;
    var values={};
    if(Ext.isFunction(this.newRecordHandler)){
      Ext.apply(values,this.newRecordHandler.call(this,this.store,idx));
    }
    values[this.indexRowName]=this.store.getServerCountNew()-1;
    var record=new this.store.recordType(values,values[this.indexRowName]);
    record.newRecord=true;
    this.store.insert(idx,record);
    if(!Ext.isDefined(this.store.writer)&&this.store.modified.indexOf(record)===-1){
      this.store.modified.push(record);
    }
    this.nextEditableCell(idx,-1,true);
    return true;
  },
  addRecordValue:function(values){
	    var totalRows=this.store.getCount();
	    values[this.indexRowName]=this.store.getServerCountNew()-1;
	    var record=new this.store.recordType(values,values[this.indexRowName]);
	    record.newRecord=true;
	    this.store.insert(totalRows,record);
	    if(!Ext.isDefined(this.store.writer)&&this.store.modified.indexOf(record)===-1){
	      this.store.modified.push(record);
	    }
	    this.nextEditableCell(totalRows,-1,true);
	    return true;
  },
  startEditingEdit:function(){
    var next=this.nextEditableCell(0,-1);
  },isBlankRow:function(recordData){
    var isBlankRow=true;
    for( var fieldName in recordData){
      if(recordData[fieldName]!=""&&fieldName!=this.indexRowName){
        isBlankRow=false;
        break;
      }
    }
    return isBlankRow;
  },saveData:function(){
    this.saveEvent.call(this);
  },initSubmitValue:function(){
    this.deleted=[];
    this.modified=[];
    for(var i=0;i<this.store.getCount();i++){
      var record=this.store.getAt(i);
      record.newRecord=false;
      record.deleted=false;
      record.set(this.indexRowName,i);
    }
    delete this.serverCount;
  },getValues:function(){
    this.store.clearFilter();
    var deletedRecords=this.getDeletedRecords();
    var submitFieldRecords=new Object();
    var recordsIndex={};
    var isDel=deletedRecords.length>0;
    var isSubmitField=false;
    for(var i=0;i<this.store.getCount();i++){
      var record=this.store.getAt(i);
      var recordData=record.data;
      var indexServer=parseInt(recordData[this.indexRowName]);
      recordsIndex[i]=indexServer;
        for( var fieldName in recordData){
        if(fieldName!=this.indexRowName){
          if(record.isNew()){
            isSubmitField=true;
            var fieldValue=recordData[fieldName];
            submitFieldRecords[fieldName+"["+i+"]"]=fieldValue;
           }else if(record.isModified(fieldName)){
            isSubmitField=true;
            var fieldValue=recordData[fieldName];
            submitFieldRecords[fieldName+"["+i+"]"]=fieldValue;
           }
        }
      }
    }
    var values={};
    if(isDel)
      values["deleted"]=deletedRecords;
    values["indexed"]=recordsIndex;
    if(isSubmitField)
      values["values"]=submitFieldRecords;
    return Ext.encode(values);
  },firstNextEditableCell:function(row,col,isAdd){
    var nextCol=undefined;
    var totalCols=this.colModel.config.length;
    var totalRows=this.view.getRows().length;
    if(totalRows==0&&!isAdd){
      this.addRecord();
      return undefined;
    }
    for(i=col+1;i<totalCols;i++){
      if(!this.colModel.config[i].hidden&&this.colModel.isCellEditable(i,row)){
        nextCol=i;
        break;
      }
    }
    if(Ext.isDefined(nextCol))
      return {row:row,col:nextCol};
    row++;
    if(row==totalRows){
      if(!isAdd)
        this.addRecord();
      return undefined;
    }
    return this.firstNextEditableCell(row,-1);
  },nextEditableCellActive:function(){
    if(this.isActiveEditorValid(this.activeEditor.col,this.activeEditor.row)){
      var valid=this.activeEditor.field.isValid(true);
      if(valid)
        this.nextEditableCell(this.activeEditor.row,this.activeEditor.col);
      else if(Ext.isInstanceof(this.activeEditor.field,Ext.form.ComboBox)){
        this.activeEditor.field.expand();
      }
    }else{
      this.startEditing(this.activeEditor.row,this.activeEditor.col);
    }
  },nextEditableCell:function(row,col,isAdd){
    var next=this.firstNextEditableCell(row,col,isAdd);
    if(!next)
      return;
    this.select(next.row,next.col);
    this.startEditing(next.row,next.col);
  },beforeeditValue:function(e){
    var col=this.getColumnModel().config[e.column];
    if(col.editor)
      col.editor.eventColumn=e;
  }});
Ext.namespace("Ext.ux.plugins");
Ext.ux.plugins.NavigationEditableGridPanel=function(config){
  Ext.apply(this,config);
};
Ext.ns("Ext.ux.grid");
Ext.ux.grid.GridSummary=function(config){
  Ext.apply(this,config);
};
Ext
    .extend(Ext.ux.grid.GridSummary,Ext.util.Observable,{
      init:function(grid){
        this.grid=grid;
        this.cm=grid.getColumnModel();
        this.view=grid.getView();
        var v=this.view;
        v.onLayout=this.onLayout;
        v.on('layout',this.setupUpdates,this);
        v.doGroupEnd=this.doGroupEnd.createDelegate(this);
        v.afterMethod('render',this.refreshSummary,this);
        v.afterMethod('refresh',this.refreshSummary,this);
        v.afterMethod('onUpdate',this.doUpdate,this);
        v.afterMethod('onRemove',this.doRemove,this);
        v.afterMethod('syncScroll',this.syncSummaryScroll,this);
        v.afterMethod('onColumnWidthUpdated',this.doWidth,this);
        v.afterMethod('onAllColumnWidthsUpdated',this.doAllWidths,this);
        grid.store.on('add',this.doUpdate,this);
        grid.store.on('remove',this.doUpdate,this);
        grid.store.on('clear',this.doUpdate,this);
        if(!this.rowTpl){
          this.rowTpl=new Ext.Template('<div class="x-grid3-summary-row x-grid3-gridsummary-row-offset">','<table class="x-grid3-summary-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">','<tbody><tr>{cells}</tr></tbody>','</table>','</div>');
          this.rowTpl.disableFormats=true;
        }
        this.rowTpl.compile();
        if(!this.cellTpl){
          this.cellTpl=new Ext.Template('<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}">','<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on">{value}</div>','</td>');
          this.cellTpl.disableFormats=true;
        }
        this.cellTpl.compile();
      },calculate:function(rs,cs){
        var data={},r,c,cfg=this.cm.config,cf;
        for(var j=0,jlen=rs.length;j<jlen;j++){
          r=rs[j];
          for(var i=0,len=cs.length;i<len;i++){
            c=cs[i];
            cf=cfg[i];
            if(cf.summaryType){
              data[c.name]=Ext.ux.grid.GridSummary.Calculations[cf.summaryType](data[c.name]||0,r,c.name,data);
            }
          }
        }
        return data;
      },onLayout:function(vw,vh){
        this.fireEvent('layout');
        if('number'!=Ext.type(vh)){
          return;
        }
        if(!this.grid.getGridEl().hasClass('x-grid-hide-gridsummary')){
          this.scroller.setHeight(vh-this.summary.getHeight());
        }
      },syncSummaryScroll:function(){
        var mb=this.view.scroller.dom;
        this.view.summaryWrap.dom.scrollLeft=mb.scrollLeft;
        this.view.summaryWrap.dom.scrollLeft=mb.scrollLeft;
      },doWidth:function(col,w,tw){
        if(this.view.summary){
          this.refreshSummary();
        }
      },doAllWidths:function(ws,tw){
        if(this.view.summary){
          this.refreshSummary();
        }
      },renderSummary:function(o,cs){
        cs=cs||this.view.getColumnData();
        var cfg=this.cm.config;
        var buf=[],c,p={},cf,last=cs.length-1;
        for(var i=0,len=cs.length;i<len;i++){
          c=cs[i];
          cf=cfg[i];
          p.id=c.id;
          p.style=c.style;
          p.css=i==0?'x-grid3-cell-first ':(i==last?'x-grid3-cell-last ':'');
          p.tg=true;
          if(cf.summaryType||cf.summaryRenderer){
            p.value=(cf.summaryRenderer||c.renderer)(o.data[c.name],p,o);
          }else{
            p.value='';
          }
          if(p.value==undefined||p.value==="")
            p.value="&#160;";
          buf[buf.length]=this.cellTpl.apply(p);
        }
        return this.rowTpl.apply({tstyle:'width:'+this.view.getTotalWidth()+';',cells:buf.join('')});
      },refreshSummary:function(gid){
        var g=this.grid,ds=g.store,data,rs=[];
        var cs=this.view.getColumnData();
        // Summary for group
        if(gid){
          ds.each(function(r){
            if(r._groupId==gid){
              rs[rs.length]=r;
            }
          });
          data=this.calculate(rs,cs);
          var markup=this.renderSummary({data:data},cs);
          var group=Ext.get(gid);
          var existing=this.getSummaryNode(gid);
          if(existing){
            group.dom.removeChild(existing);
          }
          if(group)
            Ext.DomHelper.append(group,markup);
        }
        // Summary for grid
        rs=ds.getRange();
        data=this.calculate(rs,cs);
        var buf=this.renderSummary({data:data},cs);
        if(!this.view.summaryWrap){
          this.view.summaryWrap=Ext.DomHelper.insertAfter(this.view.scroller,{tag:'div',cls:'x-grid3-gridsummary-row-inner'},true);
        }else{
          this.view.summary.remove();
        }
        this.view.summary=this.view.summaryWrap.insertHtml('afterbegin',buf,true);
      },toggleSummary:function(visible){ // true
        var el=this.grid.getGridEl();
        if(el){
          if(visible===undefined){
            visible=el.hasClass('x-grid-hide-gridsummary');
            if(Ext.isInstanceof(this.grid.view,Ext.grid.GroupingView)){
              groupVisibility=el.hasClass('x-grid-hide-summary');
            }
          }
          el[visible?'removeClass':'addClass']('x-grid-hide-gridsummary');
          if(Ext.isInstanceof(this.grid.view,Ext.grid.GroupingView)){
            el[groupVisibility?'removeClass':'addClass']('x-grid-hide-summary');
          }
          this.view.layout(); // readjust
          // gridview
          // height
        }
      },doUpdate:function(ds,record){
        this.refreshSummary(record._groupId);
      },doRemove:function(ds,record,index,isUpdate){
        if(!isUpdate){
          this.refreshSummary(record._groupId);
        }
      },setupUpdates:function(){
        this.refreshSummary();
      },doGroupEnd:function(buf,g,cs,ds,colCount){
        var data=this.calculate(g.rs,cs);
        buf.push('</div>',this.renderSummary({data:data},cs),'</div>');
      },getSummaryNode:function(gid){
        // Summary node of
        // group
        if(gid){
          var g=Ext.fly(gid,'_gsummary');
          if(g){
            return g.down('.x-grid3-summary-row',true);
          }
        }
        // Summary node of
        // grid
        return this.view.summary;
      }});
Ext.ux.grid.GroupSummary.Calculations={'sum':function(v,record,field){
  return smartsoft.Number.getAsString(smartsoft.Number.getAsObject(v)+smartsoft.Number.getAsObject(record.data[field]));
},'count':function(v,record,field){
  return record.data[field+'count']?++record.data[field+'count']:(record.data[field+'count']=1);
},'max':function(v,record,field){
  var v=getNumberAsObject(record.data[field]);
  var max=record.data[field+'max']===undefined?(record.data[field+'max']=v):record.data[field+'max'];
  return v>max?(record.data[field+'max']=v):max;
},'min':function(v,record,field){
  var v=record.data[field];
  var min=record.data[field+'min']===undefined?(record.data[field+'min']=v):record.data[field+'min'];
  return v<min?(record.data[field+'min']=v):min;
},'average':function(v,record,field){
  var c=record.data[field+'count']?++record.data[field+'count']:(record.data[field+'count']=1);
  var t=(record.data[field+'total']=((data[field+'total']||0)+(record.data[field]||0)));
  return t===0?0:t/c;
},'total':function(v,record,field){
  if(!v){
    return record.data[field+'Total'];
  }
  return v;
}};
Ext.ux.grid.GridSummary.Calculations={'sum':function(v,record,field,data,rowIdx){
  return smartsoft.Number.getAsString(smartsoft.Number.getAsObject(v)+smartsoft.Number.getAsObject(record.data[field]));
},'count':function(v,record,field,data,rowIdx){
  return data[field+'count']?++data[field+'count']:(data[field+'count']=1);
},'max':function(v,record,field,data){
  var v=getNumberAsObject(record.data[field]);
  var max=data[field+'max']===undefined?(data[field+'max']=v):data[field+'max'];
  return v>max?(data[field+'max']=v):max;
},'min':function(v,record,field,data,rowIdx){
  var v=record.data[field];
  var min=data[field+'min']===undefined?(data[field+'min']=v):data[field+'min'];
  return v<min?(data[field+'min']=v):min;
},'average':function(v,record,field,data,rowIdx){
  var c=data[field+'count']?++data[field+'count']:(data[field+'count']=1);
  var t=(data[field+'total']=((data[field+'total']||0)+(record.data[field]||0)));
  return t===0?0:t/c;
},'total':function(v,record,field,data,rowIdx){
  if(!v){
    return record.data[field+'Total'];
  }
  return v;
}};
Ext.namespace('Ext.tree');
Ext.tree.AbstractTreeStore=Ext
    .extend(Ext.data.Store,{
      leaf_field_name:'isLeaf',
      page_offset:0,
      active_node:null,
      constructor:function(config){
        Ext.tree.AbstractTreeStore.superclass.constructor.call(this,config);
        if(!this.paramNames.active_node){
          this.paramNames.active_node='anode';
        }
        if(this.isExpandAllParam){
          this.addListener("load",function(store){
            store.expandAll();
          });
        }
        this.addEvents('beforeexpandnode','expandnode','expandnodefailed','beforecollapsenode','collapsenode','beforeactivenodechange','activenodechange');
      },
      remove:function(record){
        if(record===this.active_node){
          this.setActiveNode(null);
        }
        this.removeNodeDescendants(record);
        // ----- End of modification
        Ext.tree.AbstractTreeStore.superclass.remove.call(this,record);
      },
      removeNodeDescendants:function(rc){
        var i,len,children=this.getNodeChildren(rc);
        for(i=0,len=children.length;i<len;i++){
          this.remove(children[i]);
        }
      },
      load:function(options){
        if(options){
          if(options.params){
            if(options.params[this.paramNames.active_node]===undefined){
              options.params[this.paramNames.active_node]=this.active_node?this.active_node.id:null;
            }
          }else{
            options.params={};
            options.params[this.paramNames.active_node]=this.active_node?this.active_node.id:null;
          }
        }else{
          options={params:{}};
          options.params[this.paramNames.active_node]=this.active_node?this.active_node.id:null;
        }
        if(options.params[this.paramNames.active_node]!==null){
          options.add=true;
        }
        return Ext.tree.AbstractTreeStore.superclass.load.call(this,options);
      },
      loadRecords:function(o,options,success){
        if(!o||success===false){
          if(success!==false){
            this.fireEvent("load",this,[],options);
          }
          if(options.callback){
            options.callback.call(options.scope||this,[],options,false);
          }
          return;
        }
        var r=o.records,t=o.totalRecords||r.length,page_offset=this.getPageOffsetFromOptions(options),loaded_node_id=this.getLoadedNodeIdFromOptions(options),loaded_node,i,len,prev_record,record,idx,updated,self=this;
        if(!options||options.add!==true){
          if(this.pruneModifiedRecords){
            this.modified=[];
          }
          for(var i=0,len=r.length;i<len;i++){
            r[i].join(this);
          }
          if(this.snapshot){
            this.data=this.snapshot;
            delete this.snapshot;
          }
          this.data.clear();
          this.data.addAll(r);
          this.page_offset=page_offset;
          this.totalLength=t;
          this.applySort();
          this.fireEvent("datachanged",this);
        }else{
          if(loaded_node_id){
            loaded_node=this.getById(loaded_node_id);
          }
          if(loaded_node){
            this.setNodeLoaded(loaded_node,true);
            this.setNodeChildrenOffset(loaded_node,page_offset);
            this.setNodeChildrenTotalCount(loaded_node,Math.max(t,r.length));
            this.removeNodeDescendants(loaded_node);
          }
          this.suspendEvents();
          updated={};
          for(i=0,len=r.length;i<len;i++){
            record=r[i];
            idx=this.indexOfId(record.id);
            if(idx==-1){
              updated[record.id]=false;
              this.add(record);
            }else{
              updated[record.id]=true;
              prev_record=this.getAt(idx);
              prev_record.reject();
              prev_record.data=record.data;
              r[i]=prev_record;
            }
          }
          this.applySort();
          this.resumeEvents();
          r.sort(function(r1,r2){
            var idx1=self.data.indexOf(r1),idx2=self.data.indexOf(r2),result;
            if(idx1>idx2){
              result=1;
            }else{
              result=-1;
            }
            return result;
          });
          for(i=0,len=r.length;i<len;i++){
            record=r[i];
            if(updated[record.id]==true){
              this.fireEvent('update',this,record,Ext.data.Record.COMMIT);
            }else{
              this.fireEvent("add",this,[record
              ],this.data.indexOf(record));
            }
          }
        }
        this.fireEvent("load",this,r,options);
        if(options.callback){
          options.callback.call(options.scope||this,r,options,true);
        }
      },sort:function(fieldName,dir){
        if(this.remoteSort){
          this.setActiveNode(null);
          if(this.lastOptions){
            this.lastOptions.add=false;
            if(this.lastOptions.params){
              this.lastOptions.params[this.paramNames.active_node]=null;
            }
          }
        }
        return Ext.tree.AbstractTreeStore.superclass.sort.call(this,fieldName,dir);
      },applySort:function(){
        if(this.sortInfo&&!this.remoteSort){
          var s=this.sortInfo,f=s.field;
          this.sortData(f,s.direction);
        }else{
          this.applyTreeSort();
        }
      },sortData:function(f,direction){
        direction=direction||'ASC';
        var st=this.fields.get(f).sortType;
        var fn=function(r1,r2){
          var v1=st(r1.data[f]),v2=st(r2.data[f]);
          return v1>v2?1:(v1<v2?-1:0);
        };
        this.data.sort(direction,fn);
        if(this.snapshot&&this.snapshot!=this.data){
          this.snapshot.sort(direction,fn);
        }
        this.applyTreeSort();
      },applyTreeSort:function(){
        var i,len,temp,rec,records=[],roots=this.getRootNodes();
        for(i=0,len=roots.length;i<len;i++){
          rec=roots[i];
          records.push(rec);
          this.collectNodeChildrenTreeSorted(records,rec);
        }
        if(records.length>0){
          this.data.clear();
          this.data.addAll(records);
        }
        if(this.snapshot&&this.snapshot!==this.data){
          temp=this.data;
          this.data=this.snapshot;
          this.snapshot=null;
          this.applyTreeSort();
          this.snapshot=this.data;
          this.data=temp;
        }
      },collectNodeChildrenTreeSorted:function(records,rec){
        var i,len,child,children=this.getNodeChildren(rec);
        for(i=0,len=children.length;i<len;i++){
          child=children[i];
          records.push(child);
          this.collectNodeChildrenTreeSorted(records,child);
        }
      },getActiveNode:function(){
        return this.active_node;
      },setActiveNode:function(rc){
        if(this.active_node!==rc){
          if(rc){
            if(this.data.indexOf(rc)!=-1){
              if(this.fireEvent('beforeactivenodechange',this,this.active_node,rc)!==false){
                this.active_node=rc;
                this.fireEvent('activenodechange',this,this.active_node,rc);
              }
            }else{
              throw "Given record is not from the store.";
            }
          }else{
            if(this.fireEvent('beforeactivenodechange',this,this.active_node,rc)!==false){
              this.active_node=rc;
              this.fireEvent('activenodechange',this,this.active_node,rc);
            }
          }
        }
      },isExpandedNode:function(rc){
        return rc.ux_maximgb_tg_expanded===true;
      },setNodeExpanded:function(rc,value){
        rc.ux_maximgb_tg_expanded=value;
      },isVisibleNode:function(rc){
        var i,len,ancestors=this.getNodeAncestors(rc),result=true;
        for(i=0,len=ancestors.length;i<len;i++){
          result=result&&this.isExpandedNode(ancestors[i]);
          if(!result){
            break;
          }
        }
        return result;
      },isLeafNode:function(rc){
        return rc.get(this.leaf_field_name)==true;
      },isLoadedNode:function(rc){
        var result;
        if(rc.ux_maximgb_tg_loaded!==undefined){
          result=rc.ux_maximgb_tg_loaded;
        }else if(this.isLeafNode(rc)||this.hasChildNodes(rc)){
          result=true;
        }else{
          result=false;
        }
        return result;
      },setNodeLoaded:function(rc,value){
        rc.ux_maximgb_tg_loaded=value;
      },getNodeChildrenOffset:function(rc){
        return rc.ux_maximgb_tg_offset||0;
      },setNodeChildrenOffset:function(rc,value){
        rc.ux_maximgb_tg_offset=value;
      },getNodeChildrenTotalCount:function(rc){
        return rc.ux_maximgb_tg_total||0;
      },setNodeChildrenTotalCount:function(rc,value){
        rc.ux_maximgb_tg_total=value;
      },collapseNode:function(rc){
        if(this.isExpandedNode(rc)&&this.fireEvent('beforecollapsenode',this,rc)!==false){
          this.setNodeExpanded(rc,false);
          this.fireEvent('collapsenode',this,rc);
        }
      },expandNode:function(rc){
        var params;
        if(!this.isExpandedNode(rc)&&this.fireEvent('beforeexpandnode',this,rc)!==false){
          if(this.isLoadedNode(rc)){
            this.setNodeExpanded(rc,true);
            this.fireEvent('expandnode',this,rc);
          }else{
            params={};
            params[this.paramNames.active_node]=rc.id;
            this.load({add:true,params:params,callback:this.expandNodeCallback,scope:this});
          }
        }
      },expandNodeCallback:function(r,options,success){
        var rc=this.getById(options.params[this.paramNames.active_node]);
        if(success&&rc){
          this.setNodeExpanded(rc,true);
          this.fireEvent('expandnode',this,rc);
        }else{
          this.fireEvent('expandnodefailed',this,options.params[this.paramNames.active_node],rc);
        }
      },expandAll:function(){
        var r,i,len,records=this.data.getRange();
        this.suspendEvents();
        for(i=0,len=records.length;i<len;i++){
          r=records[i];
          if(!this.isExpandedNode(r)){
            this.expandNode(r);
          }
        }
        this.resumeEvents();
        this.fireEvent('datachanged',this);
      },collapseAll:function(){
        var r,i,len,records=this.data.getRange();
        this.suspendEvents();
        for(i=0,len=records.length;i<len;i++){
          r=records[i];
          if(this.isExpandedNode(r)){
            this.collapseNode(r);
          }
        }
        this.resumeEvents();
        this.fireEvent('datachanged',this);
      },getLoadedNodeIdFromOptions:function(options){
        var result=null;
        if(options&&options.params&&options.params[this.paramNames.active_node]){
          result=options.params[this.paramNames.active_node];
        }
        return result;
      },getPageOffsetFromOptions:function(options){
        var result=0;
        if(options&&options.params&&options.params[this.paramNames.start]){
          result=parseInt(options.params[this.paramNames.start],10);
          if(isNaN(result)){
            result=0;
          }
        }
        return result;
      },hasNextSiblingNode:function(rc){
        return this.getNodeNextSibling(rc)!==null;
      },hasPrevSiblingNode:function(rc){
        return this.getNodePrevSibling(rc)!==null;
      },hasChildNodes:function(rc){
        return this.getNodeChildrenCount(rc)>0;
      },getNodeAncestors:function(rc){
        var ancestors=[],parent;
        parent=this.getNodeParent(rc);
        while(parent){
          ancestors.push(parent);
          parent=this.getNodeParent(parent);
        }
        return ancestors;
      },getNodeChildrenCount:function(rc){
        return this.getNodeChildren(rc).length;
      },getNodeNextSibling:function(rc){
        var siblings,parent,index,result=null;
        parent=this.getNodeParent(rc);
        if(parent){
          siblings=this.getNodeChildren(parent);
        }else{
          siblings=this.getRootNodes();
        }
        index=siblings.indexOf(rc);
        if(index<siblings.length-1){
          result=siblings[index+1];
        }
        return result;
      },getNodePrevSibling:function(rc){
        var siblings,parent,index,result=null;
        parent=this.getNodeParent(rc);
        if(parent){
          siblings=this.getNodeChildren(parent);
        }else{
          siblings=this.getRootNodes();
        }
        index=siblings.indexOf(rc);
        if(index>0){
          result=siblings[index-1];
        }
        return result;
      },getRootNodes:function(){
        throw 'Abstract method call';
      },getNodeDepth:function(rc){
        throw 'Abstract method call';
      },getNodeParent:function(rc){
        throw 'Abstract method call';
      },getNodeChildren:function(rc){
        throw 'Abstract method call';
      },addToNode:function(parent,child){
        throw 'Abstract method call';
      },removeFromNode:function(parent,child){
        throw 'Abstract method call';
      },getPageOffset:function(){
        return this.page_offset;
      },getActiveNodePageOffset:function(){
        var result;
        if(this.active_node){
          result=this.getNodeChildrenOffset(this.active_node);
        }else{
          result=this.getPageOffset();
        }
        return result;
      },getActiveNodeCount:function(){
        var result;
        if(this.active_node){
          result=this.getNodeChildrenCount(this.active_node);
        }else{
          result=this.getRootNodes().length;
        }
        return result;
      },getActiveNodeTotalCount:function(){
        var result;
        if(this.active_node){
          result=this.getNodeChildrenTotalCount(this.active_node);
        }else{
          result=this.getTotalCount();
        }
        return result;
      }});
Ext.tree.AdjacencyListStore=Ext.extend(Ext.tree.AbstractTreeStore,{parent_id_field_name:'parent',getRootNodes:function(){
  var i,len,result=[],records=this.data.getRange();
  for(i=0,len=records.length;i<len;i++){
    if(records[i].get(this.parent_id_field_name)==null){
      result.push(records[i]);
    }
  }
  return result;
},getNodeDepth:function(rc){
  return this.getNodeAncestors(rc).length;
},getNodeParent:function(rc){
  return this.getById(rc.get(this.parent_id_field_name));
},getNodeChildren:function(rc){
  var i,len,result=[],records=this.data.getRange();
  for(i=0,len=records.length;i<len;i++){
    if(records[i].get(this.parent_id_field_name)==rc.id){
      result.push(records[i]);
    }
  }
  return result;
},addToNode:function(parent,child){
  child.set(this.parent_id_field_name,parent.id);
  this.addSorted(child);
},removeFromNode:function(parent,child){
  this.remove(child);
}});
Ext.reg('Ext.tree.AdjacencyListStore',Ext.tree.AdjacencyListStore);
Ext.tree.NestedSetStore=Ext.extend(Ext.tree.AbstractTreeStore,{left_field_name:'lft',right_field_name:'rgt',level_field_name:'level',root_node_level:1,
  getRootNodes:function(){
    var i,len,result=[],records=this.data.getRange();
    for(i=0,len=records.length;i<len;i++){
      if(records[i].get(this.level_field_name)==this.root_node_level){
        result.push(records[i]);
      }
    }
    return result;
  },getNodeDepth:function(rc){
    return rc.get(this.level_field_name)-this.root_node_level;
  },getNodeParent:function(rc){
    var result=null,rec,records=this.data.getRange(),i,len,lft,r_lft,rgt,r_rgt,level,r_level;
    lft=rc.get(this.left_field_name);
    rgt=rc.get(this.right_field_name);
    level=rc.get(this.level_field_name);
    for(i=0,len=records.length;i<len;i++){
      rec=records[i];
      r_lft=rec.get(this.left_field_name);
      r_rgt=rec.get(this.right_field_name);
      r_level=rec.get(this.level_field_name);
      if(r_level==level-1&&r_lft<lft&&r_rgt>rgt){
        result=rec;
        break;
      }
    }
    return result;
  },getNodeChildren:function(rc){
    var lft,r_lft,rgt,r_rgt,level,r_level,records,rec,result=[];
    records=this.data.getRange();
    lft=rc.get(this.left_field_name);
    rgt=rc.get(this.right_field_name);
    level=rc.get(this.level_field_name);
    for(i=0,len=records.length;i<len;i++){
      rec=records[i];
      r_lft=rec.get(this.left_field_name);
      r_rgt=rec.get(this.right_field_name);
      r_level=rec.get(this.level_field_name);
      if(r_level==level+1&&r_lft>lft&&r_rgt<rgt){
        result.push(rec);
      }
    }
    return result;
  }});
Ext.tree.GridView=Ext
    .extend(Ext.grid.GridView,{
      expanded_icon_class:'sss-elbow-minus',
      last_expanded_icon_class:'sss-elbow-end-minus',
      collapsed_icon_class:'sss-elbow-plus',
      last_collapsed_icon_class:'sss-elbow-end-plus',
      skip_width_update_class:'sss-skip-width-update',
      // private - overriden
      initTemplates:function(){
        var ts=this.templates||{};
        if(!ts.row){
          ts.row=new Ext.Template('<div class="x-grid3-row sss-level-{level} {alt}" style="{tstyle} {display_style}">','<table class="x-grid3-row-table" border="0" cellspacing="0" cellpadding="0" style="{tstyle}">','<tbody>','<tr>{cells}</tr>',(this.enableRowBody
              ?'<tr class="x-grid3-row-body-tr" style="{bodyStyle}">'+'<td colspan="{cols}" class="x-grid3-body-cell" tabIndex="0" hidefocus="on">'
                  +'<div class="x-grid3-row-body">{body}</div>'+'</td>'+'</tr>':''),'</tbody>','</table>','</div>');
        }
        if(!ts.mastercell){
          ts.mastercell=new Ext.Template('<td class="x-grid3-col x-grid3-cell x-grid3-td-{id} {css}" style="{style}" tabIndex="0" {cellAttr}>','<div class="sss-mastercell-wrap">','{treeui}','<div class="x-grid3-cell-inner x-grid3-col-{id}" unselectable="on" {attr}>{value}</div>','</div>','</td>');
        }
        if(!ts.treeui){
          ts.treeui=new Ext.Template('<div class="sss-uiwrap" style="width: {wrap_width}px">','{elbow_line}','<div style="left: {left}px" class="{cls}">&#160;</div>','</div>');
        }
        if(!ts.elbow_line){
          ts.elbow_line=new Ext.Template('<div style="left: {left}px" class="{cls}">&#160;</div>');
        }
        this.templates=ts;
        Ext.tree.GridView.superclass.initTemplates.call(this);
      },
      // Private - Overriden
      doRender:function(cs,rs,ds,startRow,colCount,stripe){
        var ts=this.templates,ct=ts.cell,rt=ts.row,last=colCount-1;
        var tstyle='width:'+this.getTotalWidth()+';';
        // buffers
        var buf=[],cb,c,p={},rp={tstyle:tstyle},r;
        for(var j=0,len=rs.length;j<len;j++){
          r=rs[j];
          cb=[];
          var rowIndex=(j+startRow);
          var row_render_res=this.renderRow(r,rowIndex,colCount,ds,this.cm.getTotalWidth());
          if(row_render_res===false){
            for(var i=0;i<colCount;i++){
              c=cs[i];
              p.id=c.id;
              p.css=i==0?'x-grid3-cell-first ':(i==last?'x-grid3-cell-last ':'');
              p.attr=p.cellAttr="";
              p.value=c.renderer.call(c.scope,r.data[c.name],p,r,rowIndex,i,ds);
              p.style=c.style;
              if(Ext.isEmpty(p.value)){
                p.value="&#160;";
              }
              if(this.markDirty&&r.dirty&&typeof r.modified[c.name]!=='undefined'){
                p.css+=' x-grid3-dirty-cell';
              }
              // ----- Modification start
              if(c.id==this.grid.masterColumnId){
                p.treeui=this.renderCellTreeUI(r,ds);
                ct=ts.mastercell;
              }else{
                ct=ts.cell;
              }
              // ----- End of modification
              cb[cb.length]=ct.apply(p);
            }
          }else{
            cb.push(row_render_res);
          }
          var alt=[];
          if(stripe&&((rowIndex+1)%2==0)){
            alt[0]="x-grid3-row-alt";
          }
          if(r.dirty){
            alt[1]=" x-grid3-dirty-row";
          }
          rp.cols=colCount;
          if(this.getRowClass){
            alt[2]=this.getRowClass(r,rowIndex,rp,ds);
          }
          rp.alt=alt.join(" ");
          rp.cells=cb.join("");
          // ----- Modification start
          if(!ds.isVisibleNode(r)){
            rp.display_style='display: none;';
          }else{
            rp.display_style='';
          }
          rp.level=ds.getNodeDepth(r);
          // ----- End of modification
          buf[buf.length]=rt.apply(rp);
        }
        return buf.join("");
      },renderCellTreeUI:function(record,store){
        var tpl=this.templates.treeui,line_tpl=this.templates.elbow_line,tpl_data={},rec,parent,depth=level=store.getNodeDepth(record);
        tpl_data.wrap_width=(depth+1)*16;
        if(level>0){
          tpl_data.elbow_line='';
          rec=record;
          left=0;
          while(level--){
            parent=store.getNodeParent(rec);
            if(parent){
              if(store.hasNextSiblingNode(parent)){
                tpl_data.elbow_line=line_tpl.apply({left:level*16,cls:'sss-elbow-line'})+tpl_data.elbow_line;
              }else{
                tpl_data.elbow_line=line_tpl.apply({left:level*16,cls:'sss-elbow-empty'})+tpl_data.elbow_line;
              }
            }else{
              throw ["Tree inconsistency can't get level ",level+1," node(id=",rec.id,") parent."
              ].join("");
            }
            rec=parent;
          }
        }
        if(store.isLeafNode(record)){
          if(store.hasNextSiblingNode(record)){
            tpl_data.cls='sss-elbow';
          }else{
            tpl_data.cls='sss-elbow-end';
          }
        }else{
          tpl_data.cls='sss-elbow-active ';
          if(store.isExpandedNode(record)){
            if(store.hasNextSiblingNode(record)){
              tpl_data.cls+=this.expanded_icon_class;
            }else{
              tpl_data.cls+=this.last_expanded_icon_class;
            }
          }else{
            if(store.hasNextSiblingNode(record)){
              tpl_data.cls+=this.collapsed_icon_class;
            }else{
              tpl_data.cls+=this.last_collapsed_icon_class;
            }
          }
        }
        tpl_data.left=1+depth*16;
        return tpl.apply(tpl_data);
      },renderRow:function(record,index,col_count,ds,total_width){
        return false;
      },afterRender:function(){
        Ext.tree.GridView.superclass.afterRender.call(this);
        this.updateAllColumnWidths();
      },updateAllColumnWidths:function(){
        var tw=this.getTotalWidth(),clen=this.cm.getColumnCount(),ws=[],len,i;
        for(i=0;i<clen;i++){
          ws[i]=this.getColumnWidth(i);
        }
        this.innerHd.firstChild.style.width=this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width=tw;
        this.mainBody.dom.style.width=tw;
        for(i=0;i<clen;i++){
          var hd=this.getHeaderCell(i);
          hd.style.width=ws[i];
        }
        var ns=this.getRows(),row,trow;
        for(i=0,len=ns.length;i<len;i++){
          row=ns[i];
          row.style.width=tw;
          if(row.firstChild){
            row.firstChild.style.width=tw;
            trow=row.firstChild.rows[0];
            for(var j=0;j<clen&&j<trow.childNodes.length;j++){
              if(!Ext.fly(trow.childNodes[j]).hasClass(this.skip_width_update_class)){
                trow.childNodes[j].style.width=ws[j];
              }
            }
          }
        }
        this.onAllColumnWidthsUpdated(ws,tw);
      },updateColumnWidth:function(col,width){
        var w=this.getColumnWidth(col);
        var tw=this.getTotalWidth();
        this.innerHd.firstChild.style.width=this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width=tw;
        this.mainBody.dom.style.width=tw;
        var hd=this.getHeaderCell(col);
        hd.style.width=w;
        var ns=this.getRows(),row;
        for(var i=0,len=ns.length;i<len;i++){
          row=ns[i];
          row.style.width=tw;
          if(row.firstChild){
            row.firstChild.style.width=tw;
            if(col<row.firstChild.rows[0].childNodes.length){
              if(!Ext.fly(row.firstChild.rows[0].childNodes[col]).hasClass(this.skip_width_update_class)){
                row.firstChild.rows[0].childNodes[col].style.width=w;
              }
            }
          }
        }
        this.onColumnWidthUpdated(col,w,tw);
      },updateColumnHidden:function(col,hidden){
        var tw=this.getTotalWidth();
        this.innerHd.firstChild.style.width=this.getOffsetWidth();
        this.innerHd.firstChild.firstChild.style.width=tw;
        this.mainBody.dom.style.width=tw;
        var display=hidden?'none':'';
        var hd=this.getHeaderCell(col);
        hd.style.display=display;
        var ns=this.getRows(),row,cell;
        for(var i=0,len=ns.length;i<len;i++){
          row=ns[i];
          row.style.width=tw;
          if(row.firstChild){
            row.firstChild.style.width=tw;
            if(col<row.firstChild.rows[0].childNodes.length){
              if(!Ext.fly(row.firstChild.rows[0].childNodes[col]).hasClass(this.skip_width_update_class)){
                row.firstChild.rows[0].childNodes[col].style.display=display;
              }
            }
          }
        }
        this.onColumnHiddenUpdated(col,hidden,tw);
        delete this.lastViewWidth; // force recalc
        this.layout();
      },processRows:function(startRow,skipStripe){
        var processed_cnt=0;
        if(this.ds.getCount()<1){
          return;
        }
        skipStripe=!this.grid.stripeRows; // skipStripe
        startRow=startRow||0;
        var rows=this.getRows();
        var processed_cnt=0;
        Ext.each(rows,function(row,idx){
          row.rowIndex=idx;
          row.className=row.className.replace(this.rowClsRe,' ');
          if(row.style.display!='none'){
            if(!skipStripe&&((processed_cnt+1)%2===0)){
              row.className+=' x-grid3-row-alt';
            }
            processed_cnt++;
          }
        },this);
        Ext.fly(rows[0]).addClass(this.firstRowCls);
        Ext.fly(rows[rows.length-1]).addClass(this.lastRowCls);
      },ensureVisible:function(row,col,hscroll){
        var ancestors,record=this.ds.getAt(row);
        if(!this.ds.isVisibleNode(record)){
          ancestors=this.ds.getNodeAncestors(record);
          while(ancestors.length>0){
            record=ancestors.shift();
            if(!this.ds.isExpandedNode(record)){
              this.ds.expandNode(record);
            }
          }
        }
        return Ext.tree.GridView.superclass.ensureVisible.call(this,row,col,hscroll);
      },expandRow:function(record,skip_process){
        var ds=this.ds,i,len,row,pmel,children,index,child_index;
        if(typeof record=='number'){
          index=record;
          record=ds.getAt(index);
        }else{
          index=ds.indexOf(record);
        }
        skip_process=skip_process||false;
        row=this.getRow(index);
        pmel=Ext.fly(row).child('.sss-elbow-active');
        if(pmel){
          if(ds.hasNextSiblingNode(record)){
            pmel.removeClass(this.collapsed_icon_class);
            pmel.removeClass(this.last_collapsed_icon_class);
            pmel.addClass(this.expanded_icon_class);
          }else{
            pmel.removeClass(this.collapsed_icon_class);
            pmel.removeClass(this.last_collapsed_icon_class);
            pmel.addClass(this.last_expanded_icon_class);
          }
        }
        if(ds.isVisibleNode(record)){
          children=ds.getNodeChildren(record);
          for(i=0,len=children.length;i<len;i++){
            child_index=ds.indexOf(children[i]);
            row=this.getRow(child_index);
            row.style.display='block';
            if(ds.isExpandedNode(children[i])){
              this.expandRow(child_index,true);
            }
          }
        }
        if(!skip_process){
          this.processRows(0);
        }
      },collapseRow:function(record,skip_process){
        var ds=this.ds,i,len,children,row,index,child_index;
        if(typeof record=='number'){
          index=record;
          record=ds.getAt(index);
        }else{
          index=ds.indexOf(record);
        }
        skip_process=skip_process||false;
        row=this.getRow(index);
        pmel=Ext.fly(row).child('.sss-elbow-active');
        if(pmel){
          if(ds.hasNextSiblingNode(record)){
            pmel.removeClass(this.expanded_icon_class);
            pmel.removeClass(this.last_expanded_icon_class);
            pmel.addClass(this.collapsed_icon_class);
          }else{
            pmel.removeClass(this.expanded_icon_class);
            pmel.removeClass(this.last_expanded_icon_class);
            pmel.addClass(this.last_collapsed_icon_class);
          }
        }
        children=ds.getNodeChildren(record);
        for(i=0,len=children.length;i<len;i++){
          child_index=ds.indexOf(children[i]);
          row=this.getRow(child_index);
          if(row.style.display!='none'){
            row.style.display='none';
            this.collapseRow(child_index,true);
          }
        }
        if(!skip_process){
          this.processRows(0);
        }
      },initData:function(ds,cm){
        Ext.tree.GridView.superclass.initData.call(this,ds,cm);
        if(this.ds){
          this.ds.un('expandnode',this.onStoreExpandNode,this);
          this.ds.un('collapsenode',this.onStoreCollapseNode,this);
        }
        if(ds){
          ds.on('expandnode',this.onStoreExpandNode,this);
          ds.on('collapsenode',this.onStoreCollapseNode,this);
        }
      },onLoad:function(store,records,options){
        var ridx;
        if(options&&options.params&&(options.params[store.paramNames.active_node]===null||store.indexOfId(options.params[store.paramNames.active_node])==-1)){
          Ext.tree.GridView.superclass.onLoad.call(this,store,records,options);
        }
      },onAdd:function(ds,records,index){
        Ext.tree.GridView.superclass.onAdd.call(this,ds,records,index);
        if(this.mainWrap){
          // this.refresh();
          this.processRows(0);
        }
      },onRemove:function(ds,record,index,isUpdate){
        Ext.tree.GridView.superclass.onRemove.call(this,ds,record,index,isUpdate);
        if(isUpdate!==true){
          if(this.mainWrap){
            // this.refresh();
            this.processRows(0);
          }
        }
      },onUpdate:function(ds,record){
        Ext.tree.GridView.superclass.onUpdate.call(this,ds,record);
        if(this.mainWrap){
          // this.refresh();
          this.processRows(0);
        }
      },onStoreExpandNode:function(store,rc){
        this.expandRow(rc);
      },onStoreCollapseNode:function(store,rc){
        this.collapseRow(rc);
      }});
Ext.tree.GridPanel=Ext
    .extend(Ext.ux.grid.GridPanel,{
      masterColumnId:0,
      tg_cls:'sss-panel',
      initComponent:function(){
        this.initComponentPreOverride();
        Ext.tree.GridPanel.superclass.initComponent.call(this);
        this.getSelectionModel().on('selectionchange',this.onTreeGridSelectionChange,this);
        this.initComponentPostOverride();
      },
      initComponentPreOverride:Ext.emptyFn,
      initComponentPostOverride:Ext.emptyFn,
      // Private
      onRender:function(ct,position){
        Ext.tree.GridPanel.superclass.onRender.call(this,ct,position);
        this.el.addClass(this.tg_cls);
      },
      getView:function(){
        if(!this.view){
          this.view=new Ext.tree.GridView(this.viewConfig);
        }
        return this.view;
      },
      onClick:function(e){
        var target=e.getTarget(),view=this.getView(),row=view.findRowIndex(target),store=this.getStore(),sm=this.getSelectionModel(),record,record_id,do_default=true;
        if(row!==false){
          if(Ext.fly(target).hasClass('sss-elbow-active')){
            record=store.getAt(row);
            if(store.isExpandedNode(record)){
              store.collapseNode(record);
            }else{
              store.expandNode(record);
            }
            do_default=false;
          }
        }
        if(do_default){
          Ext.tree.GridPanel.superclass.onClick.call(this,e);
        }
      },onMouseDown:function(e){
        var target=e.getTarget();
        if(!Ext.fly(target).hasClass('sss-elbow-active')){
          Ext.tree.GridPanel.superclass.onMouseDown.call(this,e);
        }
      },onTreeGridSelectionChange:function(sm,selection){
        var record,ancestors,store=this.getStore();
        // Row selection model
        if(sm.getSelected){
          record=sm.getSelected();
          store.setActiveNode(record);
        }
        // Cell selection model
        else if(sm.getSelectedCell&&selection){
          record=selection.record;
          store.setActiveNode(record);
        }
        // Ensuring that selected node is visible.
        if(record){
          if(!store.isVisibleNode(record)){
            ancestors=store.getNodeAncestors(record);
            while(ancestors.length>0){
              store.expandNode(ancestors.pop());
            }
          }
        }
      }});
Ext.tree.EditorGridPanel=Ext
    .extend(Ext.ux.grid.EditorGridPanel,{
      masterColumnId:0,
      initComponent:function(){
        this.initComponentPreOverride();
        Ext.tree.EditorGridPanel.superclass.initComponent.call(this);
        this.getSelectionModel().on('selectionchange',this.onTreeGridSelectionChange,this);
        this.initComponentPostOverride();
      },
      initComponentPreOverride:Ext.emptyFn,
      initComponentPostOverride:Ext.emptyFn,
      onRender:function(ct,position){
        Ext.tree.EditorGridPanel.superclass.onRender.call(this,ct,position);
        this.el.addClass('sss-panel');
      },
      getView:function(){
        if(!this.view){
          this.view=new Ext.tree.GridView(this.viewConfig);
        }
        return this.view;
      },
      onClick:function(e){
        var target=e.getTarget(),view=this.getView(),row=view.findRowIndex(target),store=this.getStore(),sm=this.getSelectionModel(),record,record_id,do_default=true;
        if(row!==false){
          if(Ext.fly(target).hasClass('sss-elbow-active')){
            record=store.getAt(row);
            if(store.isExpandedNode(record)){
              store.collapseNode(record);
            }else{
              store.expandNode(record);
            }
            do_default=false;
          }
        }
        if(do_default){
          Ext.tree.EditorGridPanel.superclass.onClick.call(this,e);
        }
      },onMouseDown:function(e){
        var target=e.getTarget();
        if(!Ext.fly(target).hasClass('sss-elbow-active')){
          Ext.tree.EditorGridPanel.superclass.onMouseDown.call(this,e);
        }
      },onTreeGridSelectionChange:function(sm,selection){
        var record,ancestors,store=this.getStore();
        if(sm.getSelected){
          record=sm.getSelected();
          store.setActiveNode(record);
        }else if(sm.getSelectedCell&&selection){
          record=selection.record;
          store.setActiveNode(record);
        }
        if(record){
          if(!store.isVisibleNode(record)){
            ancestors=store.getNodeAncestors(record);
            while(ancestors.length>0){
              store.expandNode(ancestors.pop());
            }
          }
        }
      }});
Ext.tree.PagingToolbar=Ext.extend(Ext.PagingToolbar,{onRender:function(ct,position){
  Ext.tree.PagingToolbar.superclass.onRender.call(this,ct,position);
  this.updateUI();
},getPageData:function(){
  var total=0,cursor=0;
  if(this.store){
    cursor=this.store.getActiveNodePageOffset();
    total=this.store.getActiveNodeTotalCount();
  }
  return {total:total,activePage:Math.ceil((cursor+this.pageSize)/this.pageSize),pages:total<this.pageSize?1:Math.ceil(total/this.pageSize)};
},updateInfo:function(){
  var count=0,cursor=0,total=0,msg;
  if(this.displayItem){
    if(this.store){
      cursor=this.store.getActiveNodePageOffset();
      count=this.store.getActiveNodeCount();
      total=this.store.getActiveNodeTotalCount();
    }
    msg=count==0?this.emptyMsg:String.format(this.displayMsg,cursor+1,cursor+count,total);
    this.displayItem.setText(msg);
  }
},updateUI:function(){
  var d=this.getPageData(),ap=d.activePage,ps=d.pages;
  this.afterTextItem.setText(String.format(this.afterPageText,d.pages));
  this.inputItem.setValue(ap);
  this.first.setDisabled(ap==1);
  this.prev.setDisabled(ap==1);
  this.next.setDisabled(ap==ps);
  this.last.setDisabled(ap==ps);
  this.refresh.enable();
  this.updateInfo();
},bindStore:function(store,initial){
  if(!initial&&this.store){
    this.store.un('activenodechange',this.onStoreActiveNodeChange,this);
  }
  if(store){
    store.on('activenodechange',this.onStoreActiveNodeChange,this);
  }
  Ext.tree.PagingToolbar.superclass.bindStore.call(this,store,initial);
},beforeLoad:function(store,options){
  var paramNames=this.getParams();
  Ext.tree.PagingToolbar.superclass.beforeLoad.call(this,store,options);
  if(options&&options.params){
    if(options.params[paramNames.start]===undefined){
      options.params[paramNames.start]=0;
    }
    if(options.params[paramNames.limit]===undefined){
      options.params[paramNames.limit]=this.pageSize;
    }
  }
},moveFirst:function(){
  this.doLoad(0);
},movePrevious:function(){
  var store=this.store,cursor=store?store.getActiveNodePageOffset():0;
  this.doLoad(Math.max(0,cursor-this.pageSize));
},moveNext:function(){
  var store=this.store,cursor=store?store.getActiveNodePageOffset():0;
  this.doLoad(cursor+this.pageSize);
},moveLast:function(){
  var store=this.store,cursor=store?store.getActiveNodePageOffset():0,total=store?store.getActiveNodeTotalCount():0,extra=total%this.pageSize;
  this.doLoad(extra?(total-extra):total-this.pageSize);
},onStoreActiveNodeChange:function(store,old_rec,new_rec){
  if(this.rendered){
    this.updateUI();
  }
}});
Ext.namespace('Ext.tree.grid');
Ext.tree.grid.CheckColumn=Ext.extend(Ext.grid.Column,{submitOffValue:'0',submitOnValue:'1',labelField:'',preixStyleField:'',changeValueHandler:false,
  dependenceField:'',constructor:function(config){
    Ext.ux.grid.CheckColumn.superclass.constructor.call(this,config);
  },updateParentValue:function(store,record,value){
    var parent=store.getNodeParent(record);
    if(parent){
      var pvalue=parent.get(this.dataIndex);
      var pchildren=store.getNodeChildren(parent);
      var cy=0;
      for(var i=0;i<pchildren.length;i++){
        if(this.convertValue(pchildren[i].get(this.dataIndex))==value){
          cy++;
        }
      }
      if(cy==pchildren.length||((cy==1)&&value)){
        parent.set(this.dataIndex,value?this.submitOnValue:this.submitOffValue);
        this.updateParentValue(store,parent,value);
      }
    }
  },updateFieldValue:function(store,record,value){
    record.set(this.dataIndex,value?this.submitOnValue:this.submitOffValue);
    var children=store.getNodeChildren(record);
    for(var i=0;i<children.length;i++){
      this.updateFieldValue(store,children[i],value);
    }
  },processEvent:function(name,e,grid,rowIndex,colIndex){
    var target=e.getTarget();
    if(name=='mousedown'&&target.type=='checkbox'){
      var record=grid.getStore().getAt(rowIndex);
      value=!this.convertValue(record.data[this.dataIndex]);
      this.updateFieldValue(grid.getStore(),record,value);
      this.updateParentValue(grid.getStore(),record,value);
      grid.getView().refresh();
      return true;
    }else{
      return Ext.tree.grid.CheckColumn.superclass.processEvent.apply(this,arguments);
    }
  },convertValue:function(v){
    return(v===true||v==='true'||v==1||v===this.submitOnValue||String(v).toLowerCase()==='on');
  },renderer:function(value,metadata,record,rowIndex,colIndex,store){
    var value=this.convertValue(value);
    var imgMaster='<img src="'+Ext.BLANK_IMAGE_URL+'" class="tg-'+record.get(this.preixStyleField)+'-icon" />';
    var checkboxMaster='<input type="checkbox" class="tg-mastercol-cb" '+(value?'checked':'')+'/>';
    var labelMaster='<span class="tg-mastercol-editorplace">'+record.get(this.labelField)+'</span>';
    return [imgMaster,checkboxMaster,labelMaster
    ].join('');
  }});
Ext.reg('Ext.tree.GridPanel',Ext.tree.GridPanel);
Ext.reg('Ext.tree.EditorGridPanel',Ext.tree.EditorGridPanel);
Ext.reg('Ext.tree.PagingToolbar',Ext.tree.PagingToolbar);
