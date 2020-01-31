Ext.ns("ss.data");
ss.data.Supervision = Ext.extend(Object, {
	disconnecting : false,
	subscriptionValues : new Object(),
	subscriptions : new Object(),
	cometdURL : null,
	connected : false,
	connectionBrokenHandler : null,
	connectionClosedHandler : null,
	logLevel : 'error',
	constructor : function(config) {
		this.cometdURL = location.protocol + "//" + location.host + config.contextPath + "/cometd";
		if (config.logLevel) {
			this.logLevel = config.logLevel;
		}
		this.connectionBrokenHandler = config.connectionBrokenHandler;
		this.connectionClosedHandler = config.connectionClosedHandler;
	},
	addSubscription : function(chanelName, handler) {
		this.subscriptionValues[chanelName] = handler.createDelegate(this);
	},
	subscribe : function() {
		for ( var subscriptionName in this.subscriptionValues) {
			var subscription = this.subscriptionValues[subscriptionName];
			this.subscriptions[subscriptionName] = $.cometd.subscribe(subscriptionName, subscription);
		}
	},
	unsubscribe : function() {
		if (this.subscriptions) {
			for ( var subscriptionName in this.subscriptions) {
				$.cometd.unsubscribe(this.subscriptions[subscriptionName]);
			}
		}
		this.subscriptions = new Object();
	},
	join : function() {
		this.disconnecting = false;
		$.cometd.configure({
			url : this.cometdURL,
			logLevel : this.logLevel
		});
		$.cometd.handshake();
	},
	leave : function() {
		$.cometd.startBatch();
		this.unsubscribe();
		$.cometd.disconnect();
		$.cometd.endBatch();
		this.disconnecting = true;
	},
	connectionEstablished : function() {
		$.cometd.startBatch();
		this.unsubscribe();
		this.subscribe();
		$.cometd.endBatch();
	},
	connectionBroken : function() {
		if (this.connectionBrokenHandler)
			this.connectionBrokenHandler.call();
	},
	connectionClosed : function() {
		if (this.connectionClosedHandler)
			this.connectionClosedHandler.call();
	},
	listenerConnect : function(message) {
		if (this.disconnecting) {
			this.connected = false;
			this.connectionClosed();
		} else {
			var wasConnected = this.connected;
			this.connected = message.successful === true;
			if (!wasConnected && this.connected) {
				this.connectionEstablished();
			} else if (wasConnected && !this.connected) {
				this.connectionBroken();
			}
		}
	},
	init : function() {
		$.cometd.addListener('/meta/connect', this.listenerConnect.createDelegate(this));
		$(window).unload(this.leave);
		this.join();
	}
});