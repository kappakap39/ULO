/**
 * Disable right mouse click Script By Maximus (maximus@nsimail.com) w/ mods by
 * DynamicDrive For full source code, visit http://www.dynamicdrive.com
 * @link http://www.dynamicdrive.com/dynamicindex9/noright.htm
 * @author Maximus
 * @modified Norrapat Nimmanee
 */

var RightClick = new function() {
	var message = undefined;
	var oldCallback, oldContextMenu;

	// backup callback
	oldCallback = document.onmousedown;
	oldContextMenu = document.oncontextmenu;

	this.Display = function() {
		if (message === undefined) {
			return;
		}

		if (typeof alertBox == "function") {
			// For Bootstrap AlertBox
			if ($('.modal.bootstrap-dialog').length == 0) {
				alertBox(message);
			}
		} else {
			// For native browser Alert
			alert(message);
		}
		return false;
	};

	var clickIE4 = function(e) {
		if (e.button == 2) {
			this.Display();
			return false;
		}
	};

	var clickNS4 = function(e) {
		if (document.layers || document.getElementById && !document.all) {
			if (e.which == 2 || e.which == 3) {
				this.Display();
				return false;
			}
		}
	};

	/**
	 * DisableRightClick
	 * @param msg Message show up on Right click. Don't send parameter if no Alert msg.
	 */
	this.DisableRightClick = function(msg) {
		// backup callback
		if (oldCallback === undefined)
			oldCallback = document.onmousedown;
		if (oldContextMenu === undefined)
			oldContextMenu = document.oncontextmenu;

		if (document.layers) {
			document.captureEvents(Event.MOUSEDOWN);
			document.onmousedown = clickNS4;
		} else if (document.all && !document.getElementById) {
			document.onmousedown = clickIE4;
		}

		message = undefined;
		if (undefined !== msg) {
			message = msg;
		}
		document.oncontextmenu = new Function("RightClick.Display(); return false");
	};

	this.EnableRightClick = function() {
		if (document.layers) {
			document.captureEvents(Event.MOUSEDOWN);
			document.onmousedown = oldCallback;
		} else if (document.all && !document.getElementById) {
			document.onmousedown = oldCallback;
		}

		document.oncontextmenu = oldContextMenu;
	};
};
