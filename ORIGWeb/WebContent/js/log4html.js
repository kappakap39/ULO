/**
 * Log 4 HTML (Similar Log4J)
 * @author Norrapat Nimmanee
 * @version initial
 */

/*
 * Console Checker for Unsupported
 */
if (typeof console != "object") {
	console = {
		log: function() {}
	};
}


var log = {
	options: {
		displayLevel: 'all'
	},
	console: console || {
		log: function() {}
	},
	// functions
	log: function(str) {
		this.console.log(str);
	},
	getFunc: function(funcstr) {
		return funcstr.substr(0, funcstr.indexOf("{")).trim();
	},
	info: function(str) {
		try {
			var caller = this.getFunc(arguments.callee.caller.toString());
		} catch (e) { caller = 'Log4H'; }
		this.console.log(caller + " - [INFO] - " + str);
	},
	debug: function(str) {
		try {
			var caller = this.getFunc(arguments.callee.caller.toString());
		} catch (e) { caller = 'Log4H'; }
		this.console.log(caller + " - [DEBUG] - " + str);
	},
	warn: function(str) {
		try {
			var caller = this.getFunc(arguments.callee.caller.toString());
		} catch (e) { caller = 'Log4H'; }
		this.console.log(caller + " - [WARN] - " + str);
	},
	error: function(str) {
		try {
			var caller = this.getFunc(arguments.callee.caller.toString());
		} catch (e) { caller = 'Log4H'; }
		this.console.log(caller + " - [ERROR] - " + str);
	},
	fatal: function(str) {
		try {
			var caller = this.getFunc(arguments.callee.caller.toString());
		} catch (e) { caller = 'Log4H'; }
		this.console.log(caller + " - [FATAL] - " + str);
	}
};

var logger = log;