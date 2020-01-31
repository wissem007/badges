
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<link rel="shortcut icon" href="./favicon.ico" />
		<title>loginWindow</title>
		<link href='css/ext-all.css' type='text/css' rel='stylesheet'>
		<link href='css/ext-ux-all.css' type='text/css' rel='stylesheet'>
		<link href='css/ext-ux-livegrid.css' type='text/css' rel='stylesheet'>
		<link href='css/ext-3s.css' type='text/css' rel='stylesheet'>
		<link href='css/ext-suggest.css' type='text/css' rel='stylesheet'>
		<script src='js/ext/ext-base.js' language='JavaScript'></script>
		<script src='js/ext/ext-all.js' language='JavaScript'></script>
		<script src='js/ext/ext-ux-all.js' language='JavaScript'></script>
		<script src='js/ext/ext-3s.js' language='JavaScript'></script>
		<script src='js/ext/ext-livegrid-all.js' language='JavaScript'></script>
		<script src='js/typeUtils.js' language='JavaScript'></script>
		<script src='js/ext/ext-desktop.js' language='JavaScript'></script>
		<script src='js/ext/ext-suggest.js' language='JavaScript'></script>
		<script src='js/ext/ext-3s-grid.js' language='JavaScript'></script>
		<script src='js/ext/ext-lang-fr.js' language='JavaScript'></script>
	</head>
	<body>
		<script language='JavaScript'>
	//********************** list des fonctions **************************** 
	var desktop=new Ext.sss.Desktop({
		'titelModule':'Module Explorer',
		'isDisplayedModuleBar':false,
		'isDisplayedMenuBar':false,
		'isDisplayedStatusBar':true,
		'collapsedModule':true,
		'appContainerLayout':'hbox',
		'isDisplayedActionBar':false,
		'appContainerLayoutConfig':{
			'align':'middle',
			'pack':'center'
		},
		'isDisplayedToolbar':false,
		'path':'/fwDev/loginWindow',
		'labelsAction':[],
		'isAction':false
	});
	//********************** list des composants**************************** 
	Ext
			.onReady(function(){
				var id5=
						new Ext.BoxComponent(
								{
									'id':'id5',
									'hidden':false,
									'desktop':desktop,
									'html':'<table width=\"100%\"><tr><td width=\"110\" rowspan=\"4\" class=\"login-app-label\"><img height=\"91\" width=\"110\" src=\"images/logo/logo.gif\"></td></tr><tr><td align=\"center\" class=\"login-app-label\"></td></tr></table>'
								});
				var loginBtn=new Ext.Button({
					'id':'loginBtn',
					'listeners':{
						'click':function(el,e){
							 window.location.href="http://localhost:8081/manager/reload?path=/fwDev";
						}
					},
					'icon':'images/action/valider.png',
					'text':'Essayer',
					'hidden':false,
					'desktop':desktop
				});
				var detailErreur=new Ext.BoxComponent({
					'id':'detailErreur',
					'hidden':false,
					'desktop':desktop,
					'html':'<table width=\"100%\"><tr><td align=\"center\" class=\"login-app-label\"><p>Détail :</td></tr><tr><td align=\"center\" class=\"login-app-label\"><p> </td></tr></table>'
				});
			 	var id4=new Ext.Panel({
					'bodyStyle':'padding:15px;background-color:#ffffff;',
					'labelWidth':100,
					'width':320,
					'desktop':desktop,
					'autoWidth':false,
				 	'id':'id4',
				 	'buttons':[],
					'title':"Erreur d'initialisation d'application",
					'height':250,
					'frame':true,
					'items':[id5,detailErreur],
					'hidden':false,
					'layout':'form',
					'labelSeparator':' ',
					'buttonAlign':'center',
					'labelAlign':'left'
				});
				desktop.addToContainer(id4);
				//********************** alertMessage       **************************** 
				desktop.doInitDesktop();
			});
</script>
	</body>
</html>
