//the namespace for this tutorial
Ext.ns('com.quizzpot.tutorial');

com.quizzpot.tutorial.Charts = {
	init: function(){
		//code here
		var data = [['janvier',1200,500],['fevrier',200,500],['mars',300,500]];
		
		
		
		var store = new Ext.data.ArrayStore({
			fields:[{name:'moisIndex'},{name:'valeurIndex', type:'float'},{name:'valeur2Index', type:'float'}]
		});
		store.loadData(data);
		
		var columnChart = new Ext.chart.ColumnChart({
			store: store,
			//url:'../ext-3.0-rc1/resources/charts.swf',
			xField: 'moisIndex',
			yField: 'valeurIndex',
			//seriesStyles:[{color: '#FF0000'}]
			
			
			series:[{'yField':'valeurIndex','style':{'color':['#FF0000']},'displayName':'valeur1'},
			         {'yField':'valeur2Index','style':{'color':['#339966']},'displayName':'valeur1'}],
			
			  
			    extraStyle:{            //Step 1  
			        legend:{        //Step 2  
			            display: 'bottom'//Step 3  
			        }  
			    } 
		});
		
		
		var pieChart = new Ext.chart.PieChart({  
		    store: store,  
		    dataField: 'valeurIndex', //information to display in the chart  
		    categoryField : 'moisIndex' //tags or categories  
		});  
		
		
		
		var panel1 = new Ext.Panel({
			title: 'Column chart example',
			items:[columnChart]
		});
		
		var panel2 = new Ext.Panel({
			title: 'Column chart example',
			items:[pieChart]
		});
		
		
		
		var main = new Ext.Panel({
			renderTo: 'frame',
			width:450,
			defaults: {
				height:250,
				collapsible: true,
				border:false,
				titleCollapse: true
			},
			items: [panel1,panel2]
		});
		
	}
}

Ext.onReady(com.quizzpot.tutorial.Charts.init,com.quizzpot.tutorial.Charts);