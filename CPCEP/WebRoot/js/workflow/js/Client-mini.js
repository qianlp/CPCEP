var Client = {
	VERSION: "3.0.1.1",
	IS_IE: 0 <= navigator.userAgent.indexOf("MSIE"),
	IS_IE6: 0 <= navigator.userAgent.indexOf("MSIE 6"),
	IS_IE11: !! navigator.userAgent.match(/Trident\/7\./),
	IS_QUIRKS: 0 <= navigator.userAgent.indexOf("MSIE") && (null == document.documentMode || 5 == document.documentMode),
	VML_PREFIX: "v",
	OFFICE_PREFIX: "o",
	IS_NS: 0 <= navigator.userAgent.indexOf("Mozilla/") && 0 > navigator.userAgent.indexOf("MSIE"),
	IS_OP: 0 <= navigator.userAgent.indexOf("Opera/"),
	IS_OT: 0 > navigator.userAgent.indexOf("Presto/2.4.") && 0 > navigator.userAgent.indexOf("Presto/2.3.") && 0 > navigator.userAgent.indexOf("Presto/2.2.") && 0 > navigator.userAgent.indexOf("Presto/2.1.") && 0 > navigator.userAgent.indexOf("Presto/2.0.") && 0 > navigator.userAgent.indexOf("Presto/1."),
	IS_SF: 0 <= navigator.userAgent.indexOf("AppleWebKit/") && 0 > navigator.userAgent.indexOf("Chrome/"),
	IS_IOS: navigator.userAgent.match(/(iPad|iPhone|iPod)/g) ? !0 : !1,
	IS_GC: 0 <= navigator.userAgent.indexOf("Chrome/"),
	IS_FF: 0 <= navigator.userAgent.indexOf("Firefox/"),
	IS_MT: 0 <= navigator.userAgent.indexOf("Firefox/") && 0 > navigator.userAgent.indexOf("Firefox/1.") && 0 > navigator.userAgent.indexOf("Firefox/2.") || 0 <= navigator.userAgent.indexOf("Iceweasel/") && 0 > navigator.userAgent.indexOf("Iceweasel/1.") && 0 > navigator.userAgent.indexOf("Iceweasel/2.") || 0 <= navigator.userAgent.indexOf("SeaMonkey/") && 0 > navigator.userAgent.indexOf("SeaMonkey/1.") || 0 <= navigator.userAgent.indexOf("Iceape/") && 0 > navigator.userAgent.indexOf("Iceape/1."),
	IS_SVG: 0 <= navigator.userAgent.indexOf("Firefox/") || 0 <= navigator.userAgent.indexOf("Iceweasel/") || 0 <= navigator.userAgent.indexOf("Seamonkey/") || 0 <= navigator.userAgent.indexOf("Iceape/") || 0 <= navigator.userAgent.indexOf("Galeon/") || 0 <= navigator.userAgent.indexOf("Epiphany/") || 0 <= navigator.userAgent.indexOf("AppleWebKit/") || 0 <= navigator.userAgent.indexOf("Gecko/") || 0 <= navigator.userAgent.indexOf("Opera/") || null != document.documentMode && 9 <= document.documentMode,
	NO_FO: !document.createElementNS || "[object SVGForeignObjectElement]" != document.createElementNS("http://www.w3.org/2000/svg", "foreignObject") || 0 <= navigator.userAgent.indexOf("Opera/"),
	IS_VML: "MICROSOFT INTERNET EXPLORER" == navigator.appName.toUpperCase(),
	IS_WIN: 0 < navigator.appVersion.indexOf("Win"),
	IS_MAC: 0 < navigator.appVersion.indexOf("Mac"),
	IS_TOUCH: "ontouchstart" in document.documentElement,
	IS_POINTER: null != window.navigator.msPointerEnabled ? window.navigator.msPointerEnabled : !1,
	IS_LOCAL: 0 > document.location.href.indexOf("http://") && 0 > document.location.href.indexOf("https://"),
	isBrowserSupported: function() {
		return Client.IS_VML || Client.IS_SVG
	},
	link: function(a, b, c) {
		c = c || document;
		if (Client.IS_IE6) c.write('<link rel="' + a + '" href="' + b + '" charset="utf-8" type="text/css"/>');
		else {
			var d = c.createElement("link");
			d.setAttribute("rel", a);
			d.setAttribute("href", b);
			d.setAttribute("charset", "utf-8");
			d.setAttribute("type", "text/css");
			c.getElementsByTagName("head")[0].appendChild(d)
		}
	},
	include: function(a) {
		document.write('<script src="' + a + '"></script>')
	},
	dispose: function() {
		for (var a = 0; a < Event.objects.length; a++) null != Event.objects[a].ListenerList && Event.removeAllListeners(Event.objects[a])
	}
};
"undefined" == typeof LoadResources && (LoadResources = !0);
"undefined" == typeof ResourceExtension && (ResourceExtension = ".properties");
"undefined" == typeof LoadStylesheets && (LoadStylesheets = !0);
"undefined" != typeof BasePath && 0 < BasePath.length ? ("/" == BasePath.substring(BasePath.length - 1) && (BasePath = BasePath.substring(0, BasePath.length - 1)), Client.basePath = BasePath) : Client.basePath = ".";
"undefined" != typeof ImageBasePath && 0 < ImageBasePath.length ? ("/" == ImageBasePath.substring(ImageBasePath.length - 1) && (ImageBasePath = ImageBasePath.substring(0, ImageBasePath.length - 1)), Client.imageBasePath = ImageBasePath) : Client.imageBasePath = Client.basePath + "/images";
Client.language = "undefined" != typeof Language && null != Language ? Language : Client.IS_IE ? navigator.userLanguage : navigator.language;
Client.defaultLanguage = "undefined" != typeof DefaultLanguage && null != DefaultLanguage ? DefaultLanguage : "en";
LoadStylesheets && Client.link("stylesheet", Client.basePath + "/css/common.css");
"undefined" != typeof Languages && null != Languages && (Client.languages = Languages);
if (Client.IS_VML) if (Client.IS_SVG) Client.IS_VML = !1;
else {
	8 == document.documentMode ? (document.namespaces.add(Client.VML_PREFIX, "urn:schemas-microsoft-com:vml", "#default#VML"), document.namespaces.add(Client.OFFICE_PREFIX, "urn:schemas-microsoft-com:office:office", "#default#VML")) : (document.namespaces.add(Client.VML_PREFIX, "urn:schemas-microsoft-com:vml"), document.namespaces.add(Client.OFFICE_PREFIX, "urn:schemas-microsoft-com:office:office"));
	var ss = document.createStyleSheet();
	ss.cssText = Client.VML_PREFIX + "\\:*{behavior:url(#default#VML)}" + Client.OFFICE_PREFIX + "\\:*{behavior:url(#default#VML)}";
	LoadStylesheets && Client.link("stylesheet", Client.basePath + "/css/explorer.css");
	window.attachEvent("onunload", Client.dispose)
}
var Log = {
	consoleName: "Console",
	TRACE: !1,
	DEBUG: !0,
	WARN: !0,
	buffer: "",
	init: function() {
		if (null == Log.window && null != document.body) {
			var a = Log.consoleName + " - Graph " + Client.VERSION,
				b = document.createElement("table");
			b.setAttribute("width", "100%");
			b.setAttribute("height", "100%");
			var c = document.createElement("tbody"),
				d = document.createElement("tr"),
				e = document.createElement("td");
			e.style.verticalAlign = "top";
			Log.textarea = document.createElement("textarea");
			Log.textarea.setAttribute("readOnly", "true");
			Log.textarea.style.height = "100%";
			Log.textarea.style.resize = "none";
			Log.textarea.value = Log.buffer;
			Log.textarea.style.width = Client.IS_NS && "BackCompat" != document.compatMode ? "99%" : "100%";
			e.appendChild(Log.textarea);
			d.appendChild(e);
			c.appendChild(d);
			d = document.createElement("tr");
			Log.td = document.createElement("td");
			Log.td.style.verticalAlign = "top";
			Log.td.setAttribute("height", "30px");
			d.appendChild(Log.td);
			c.appendChild(d);
			b.appendChild(c);
			Log.addButton("Info", function(a) {
				Log.info()
			});
			Log.addButton("DOM", function(a) {
				a = Utils.getInnerHtml(document.body);
				Log.debug(a)
			});
			Log.addButton("Trace", function(a) {
				Log.TRACE = !Log.TRACE;
				Log.TRACE ? Log.debug("Tracing enabled") : Log.debug("Tracing disabled")
			});
			Log.addButton("Copy", function(a) {
				try {
					Utils.copy(Log.textarea.value)
				} catch (b) {
					Utils.alert(b)
				}
			});
			Log.addButton("Show", function(a) {
				try {
					Utils.popup(Log.textarea.value)
				} catch (b) {
					Utils.alert(b)
				}
			});
			Log.addButton("Clear", function(a) {
				Log.textarea.value = ""
			});
			d = c = 0;
			"number" === typeof window.innerWidth ? (c = window.innerHeight, d = window.innerWidth) : (c = document.documentElement.clientHeight || document.body.clientHeight, d = document.body.clientWidth);
			Log.window = new Window(a, b, Math.max(0, d - 320), Math.max(0, c - 210), 300, 160);
			Log.window.setMaximizable(!0);
			Log.window.setScrollable(!1);
			Log.window.setResizable(!0);
			Log.window.setClosable(!0);
			Log.window.destroyOnClose = !1;
			if ((Client.IS_NS || Client.IS_IE) && !Client.IS_GC && !Client.IS_SF && "BackCompat" != document.compatMode || 11 == document.documentMode) {
				var f = Log.window.getElement(),
					a = function(a, b) {
						Log.textarea.style.height = Math.max(0, f.offsetHeight - 70) + "px"
					};
				Log.window.addListener(Event.RESIZE_END, a);
				Log.window.addListener(Event.MAXIMIZE, a);
				Log.window.addListener(Event.NORMALIZE, a);
				Log.textarea.style.height = "92px"
			}
		}
	},
	info: function() {
		Log.writeln(Utils.toString(navigator))
	},
	addButton: function(a, b) {
		var c = document.createElement("button");
		Utils.write(c, a);
		Event.addListener(c, "click", b);
		Log.td.appendChild(c)
	},
	isVisible: function() {
		return null != Log.window ? Log.window.isVisible() : !1
	},
	show: function() {
		Log.setVisible(!0)
	},
	setVisible: function(a) {
		null == Log.window && Log.init();
		null != Log.window && Log.window.setVisible(a)
	},
	enter: function(a) {
		if (Log.TRACE) return Log.writeln("Entering " + a), (new Date).getTime()
	},
	leave: function(a, b) {
		if (Log.TRACE) {
			var c = 0 != b ? " (" + ((new Date).getTime() - b) + " ms)" : "";
			Log.writeln("Leaving " + a + c)
		}
	},
	debug: function() {
		Log.DEBUG && Log.writeln.apply(this, arguments)
	},
	warn: function() {
		Log.WARN && Log.writeln.apply(this, arguments)
	},
	write: function() {
		for (var a = "", b = 0; b < arguments.length; b++) a += arguments[b], b < arguments.length - 1 && (a += " ");
		null != Log.textarea ? (Log.textarea.value += a, 0 <= navigator.userAgent.indexOf("Presto/2.5") && (Log.textarea.style.visibility = "hidden", Log.textarea.style.visibility = "visible"), Log.textarea.scrollTop = Log.textarea.scrollHeight) : Log.buffer += a
	},
	writeln: function() {
		for (var a = "", b = 0; b < arguments.length; b++) a += arguments[b], b < arguments.length - 1 && (a += " ");
		Log.write(a + "\n")
	}
},
	ObjectIdentity = {
		FIELD_NAME: "ObjectId",
		counter: 0,
		get: function(a) {
			if ("object" == typeof a && null == a[ObjectIdentity.FIELD_NAME]) {
				var b = Utils.getFunctionName(a.constructor);
				a[ObjectIdentity.FIELD_NAME] = b + "#" + ObjectIdentity.counter++
			}
			return a[ObjectIdentity.FIELD_NAME]
		},
		clear: function(a) {
			"object" == typeof a && delete a[ObjectIdentity.FIELD_NAME]
		}
	};

function Dictionary() {
	this.clear()
}
Dictionary.prototype.map = null;
Dictionary.prototype.clear = function() {
	this.map = {}
};
Dictionary.prototype.get = function(a) {
	a = ObjectIdentity.get(a);
	return this.map[a]
};
Dictionary.prototype.put = function(a, b) {
	var c = ObjectIdentity.get(a),
		d = this.map[c];
	this.map[c] = b;
	return d
};
Dictionary.prototype.remove = function(a) {
	a = ObjectIdentity.get(a);
	var b = this.map[a];
	delete this.map[a];
	return b
};
Dictionary.prototype.getKeys = function() {
	var a = [],
		b;
	for (b in this.map) a.push(b);
	return a
};
Dictionary.prototype.getValues = function() {
	var a = [],
		b;
	for (b in this.map) a.push(this.map[b]);
	return a
};
Dictionary.prototype.visit = function(a) {
	for (var b in this.map) a(b, this.map[b])
};
var Resources = {
	resources: [],
	extension: ResourceExtension,
	resourcesEncoded: !0,
	loadDefaultBundle: !0,
	loadSpecialBundle: !0,
	isLanguageSupported: function(a) {
		return null != Client.languages ? 0 <= Utils.indexOf(Client.languages, a) : !0
	},
	getDefaultBundle: function(a, b) {
		return Resources.loadDefaultBundle || !Resources.isLanguageSupported(b) ? a + Resources.extension : null
	},
	getSpecialBundle: function(a, b) {
		if (null == Client.languages || !this.isLanguageSupported(b)) {
			var c = b.indexOf("-");
			0 < c && (b = b.substring(0, c))
		}
		return Resources.loadSpecialBundle && Resources.isLanguageSupported(b) && b != Client.defaultLanguage ? a + "_" + b + Resources.extension : null
	},
	add: function(a, b) {
		b = null != b ? b : Client.language.toLowerCase();
		if (b != Constants.NONE) {
			var c = Resources.getDefaultBundle(a, b);
			if (null != c) try {
				var d = Utils.load(c);
				d.isReady() && Resources.parse(d.getText())
			} catch (e) {}
			c = Resources.getSpecialBundle(a, b);
			if (null != c) try {
				d = Utils.load(c), d.isReady() && Resources.parse(d.getText())
			} catch (f) {}
		}
	},
	parse: function(a) {
		if (null != a) {
			a = a.split("\n");
			for (var b = 0; b < a.length; b++) if ("#" != a[b].charAt(0)) {
				var c = a[b].indexOf("=");
				if (0 < c) {
					var d = a[b].substring(0, c),
						e = a[b].length;
					13 == a[b].charCodeAt(e - 1) && e--;
					c = a[b].substring(c + 1, e);
					this.resourcesEncoded ? (c = c.replace(/\\(?=u[a-fA-F\d]{4})/g, "%"), Resources.resources[d] = unescape(c)) : Resources.resources[d] = c
				}
			}
		}
	},
	get: function(a, b, c) {
		a = Resources.resources[a];
		null == a && (a = c);
		if (null != a && null != b) {
			c = [];
			for (var d = null, e = 0; e < a.length; e++) {
				var f = a.charAt(e);
				"{" == f ? d = "" : null != d && "}" == f ? (d = parseInt(d) - 1, 0 <= d && d < b.length && c.push(b[d]), d = null) : null != d ? d += f : c.push(f)
			}
			a = c.join("")
		}
		return a
	}
};

function Point(a, b) {
	this.x = null != a ? a : 0;
	this.y = null != b ? b : 0
}
Point.prototype.x = null;
Point.prototype.y = null;
Point.prototype.equals = function(a) {
	return null != a && a.x == this.x && a.y == this.y
};
Point.prototype.clone = function() {
	return Utils.clone(this)
};

function Rectangle(a, b, c, d) {
	Point.call(this, a, b);
	this.width = null != c ? c : 0;
	this.height = null != d ? d : 0
}
Rectangle.prototype = new Point;
Rectangle.prototype.constructor = Rectangle;
Rectangle.prototype.width = null;
Rectangle.prototype.height = null;
Rectangle.prototype.setRect = function(a, b, c, d) {
	this.x = a;
	this.y = b;
	this.width = c;
	this.height = d
};
Rectangle.prototype.getCenterX = function() {
	return this.x + this.width / 2
};
Rectangle.prototype.getCenterY = function() {
	return this.y + this.height / 2
};
Rectangle.prototype.add = function(a) {
	if (null != a) {
		var b = Math.min(this.x, a.x),
			c = Math.min(this.y, a.y),
			d = Math.max(this.x + this.width, a.x + a.width);
		a = Math.max(this.y + this.height, a.y + a.height);
		this.x = b;
		this.y = c;
		this.width = d - b;
		this.height = a - c
	}
};
Rectangle.prototype.grow = function(a) {
	this.x -= a;
	this.y -= a;
	this.width += 2 * a;
	this.height += 2 * a
};
Rectangle.prototype.getPoint = function() {
	return new Point(this.x, this.y)
};
Rectangle.prototype.equals = function(a) {
	return null != a && a.x == this.x && a.y == this.y && a.width == this.width && a.height == this.height
};
var Effects = {
	animateChanges: function(a, b, c) {
		var d = 0,
			e = function() {
				for (var g = !1, k = 0; k < b.length; k++) {
					var l = b[k];
					if (l instanceof GeometryChange || l instanceof TerminalChange || l instanceof ValueChange || l instanceof ChildChange || l instanceof StyleChange) {
						var m = a.getView().getState(l.cell || l.child, !1);
						if (null != m) if (g = !0, l.constructor != GeometryChange || a.model.isEdge(l.cell)) Utils.setOpacity(m.shape.node, 100 * d / 10);
						else {
							var n = a.getView().scale,
								p = (l.geometry.x - l.previous.x) * n,
								q = (l.geometry.y - l.previous.y) * n,
								r = (l.geometry.width - l.previous.width) * n,
								n = (l.geometry.height - l.previous.height) * n;
							0 == d ? (m.x -= p, m.y -= q, m.width -= r, m.height -= n) : (m.x += p / 10, m.y += q / 10, m.width += r / 10, m.height += n / 10);
							a.cellRenderer.redraw(m);
							Effects.cascadeOpacity(a, l.cell, 100 * d / 10)
						}
					}
				}
				10 > d && g ? (d++, window.setTimeout(e, f)) : null != c && c()
			},
			f = 30;
		e()
	},
	cascadeOpacity: function(a, b, c) {
		for (var d = a.model.getChildCount(b), e = 0; e < d; e++) {
			var f = a.model.getChildAt(b, e),
				g = a.getView().getState(f);
			null != g && (Utils.setOpacity(g.shape.node, c), Effects.cascadeOpacity(a, f, c))
		}
		b = a.model.getEdges(b);
		if (null != b) for (e = 0; e < b.length; e++) d = a.getView().getState(b[e]), null != d && Utils.setOpacity(d.shape.node, c)
	},
	fadeOut: function(a, b, c, d, e, f) {
		d = d || 40;
		e = e || 30;
		var g = b || 100;
		Utils.setOpacity(a, g);
		if (f || null == f) {
			var k = function() {
					g = Math.max(g - d, 0);
					Utils.setOpacity(a, g);
					0 < g ? window.setTimeout(k, e) : (a.style.visibility = "hidden", c && a.parentNode && a.parentNode.removeChild(a))
				};
			window.setTimeout(k, e)
		} else a.style.visibility = "hidden", c && a.parentNode && a.parentNode.removeChild(a)
	}
},
	Utils = {
		errorResource: "none" != Client.language ? "error" : "",
		closeResource: "none" != Client.language ? "close" : "",
		errorImage: Client.imageBasePath + "/error.gif",
		removeCursors: function(a) {
			null != a.style && (a.style.cursor = "");
			a = a.childNodes;
			if (null != a) for (var b = a.length, c = 0; c < b; c += 1) Utils.removeCursors(a[c])
		},
		getCurrentStyle: function() {
			return Client.IS_IE ?
			function(a) {
				return null != a ? a.currentStyle : null
			} : function(a) {
				return null != a ? window.getComputedStyle(a, "") : null
			}
		}(),
		setPrefixedStyle: function() {
			var a = null;
			Client.IS_OP && Client.IS_OT ? a = "O" : Client.IS_SF || Client.IS_GC ? a = "Webkit" : Client.IS_MT ? a = "Moz" : Client.IS_IE && (9 <= document.documentMode && 10 > document.documentMode) && (a = "ms");
			return function(b, c, d) {
				b[c] = d;
				null != a && 0 < c.length && (c = a + c.substring(0, 1).toUpperCase() + c.substring(1), b[c] = d)
			}
		}(),
		hasScrollbars: function(a) {
			a = Utils.getCurrentStyle(a);
			return null != a && ("scroll" == a.overflow || "auto" == a.overflow)
		},
		bind: function(a, b) {
			return function() {
				return b.apply(a, arguments)
			}
		},
		eval: function(a) {
			var b = null;
			if (0 <= a.indexOf("function")) try {
				eval("var _JavaScriptExpression=" + a), b = _JavaScriptExpression, _JavaScriptExpression = null
			} catch (c) {
				Log.warn(c.message + " while evaluating " + a)
			} else try {
				b = eval(a)
			} catch (d) {
				Log.warn(d.message + " while evaluating " + a)
			}
			return b
		},
		findNode: function(a, b, c) {
			var d = a.getAttribute(b);
			if (null != d && d == c) return a;
			for (a = a.firstChild; null != a;) {
				d = Utils.findNode(a, b, c);
				if (null != d) return d;
				a = a.nextSibling
			}
			return null
		},
		findNodeByAttribute: function() {
			return 9 <= document.documentMode ?
			function(a, b, c) {
				var d = null;
				if (null != a) if (a.nodeType == Constants.NODETYPE_ELEMENT && a.getAttribute(b) == c) d = a;
				else for (a = a.firstChild; null != a && null == d;) d = Utils.findNodeByAttribute(a, b, c), a = a.nextSibling;
				return d
			} : Client.IS_IE ?
			function(a, b, c) {
				return null == a ? null : a.ownerDocument.selectSingleNode("//*[@" + b + "='" + c + "']")
			} : function(a, b, c) {
				return null == a ? null : a.ownerDocument.evaluate("//*[@" + b + "='" + c + "']", a.ownerDocument, null, XPathResult.ANY_TYPE, null).iterateNext()
			}
		}(),
		getFunctionName: function(a) {
			var b = null;
			if (null != a) if (null != a.name) b = a.name;
			else {
				a = a.toString();
				for (b = 9;
				" " == a.charAt(b);) b++;
				var c = a.indexOf("(", b),
					b = a.substring(b, c)
			}
			return b
		},
		indexOf: function(a, b) {
			if (null != a && null != b) for (var c = 0; c < a.length; c++) if (a[c] == b) return c;
			return -1
		},
		remove: function(a, b) {
			var c = null;
			if ("object" == typeof b) for (var d = Utils.indexOf(b, a); 0 <= d;) b.splice(d, 1), c = a, d = Utils.indexOf(b, a);
			for (var e in b) b[e] == a && (delete b[e], c = a);
			return c
		},
		isNode: function(a, b, c, d) {
			return null != a && !isNaN(a.nodeType) && (null == b || a.nodeName.toLowerCase() == b.toLowerCase()) ? null == c || a.getAttribute(c) == d : !1
		},
		isAncestorNode: function(a, b) {
			for (var c = b; null != c;) {
				if (c == a) return !0;
				c = c.parentNode
			}
			return !1
		},
		getChildNodes: function(a, b) {
			b = b || Constants.NODETYPE_ELEMENT;
			for (var c = [], d = a.firstChild; null != d;) d.nodeType == b && c.push(d), d = d.nextSibling;
			return c
		},
		importNode: function(a, b, c) {
			if (Client.IS_IE && (null == document.documentMode || 10 > document.documentMode)) switch (b.nodeType) {
			case 1:
				var d = a.createElement(b.nodeName);
				if (b.attributes && 0 < b.attributes.length) {
					for (var e = 0; e < b.attributes.length; e++) d.setAttribute(b.attributes[e].nodeName, b.getAttribute(b.attributes[e].nodeName));
					if (c && b.childNodes && 0 < b.childNodes.length) for (e = 0; e < b.childNodes.length; e++) d.appendChild(Utils.importNode(a, b.childNodes[e], c))
				}
				return d;
			case 3:
			case 4:
			case 8:
				return a.createTextNode(b.nodeValue)
			} else return a.importNode(b, c)
		},
		createXmlDocument: function() {
			var a = null;
			document.implementation && document.implementation.createDocument && 9 != document.documentMode ? a = document.implementation.createDocument("", "", null) : window.ActiveXObject && (a = new ActiveXObject("Microsoft.XMLDOM"));
			return a
		},
		parseXml: function() {
			return window.DOMParser && 9 != document.documentMode ?
			function(a) {
				return (new DOMParser).parseFromString(a, "text/xml")
			} : function(a) {
				var b = Utils.createXmlDocument();
				b.async = "false";
				b.loadXML(a);
				return b
			}
		}(),
		clearSelection: function() {
			return document.selection ?
			function() {
				document.selection.empty()
			} : window.getSelection ?
			function() {
				window.getSelection().removeAllRanges()
			} : function() {}
		}(),
		getPrettyXml: function(a, b, c) {
			var d = [];
			if (null != a) if (b = b || "  ", c = c || "", a.nodeType == Constants.NODETYPE_TEXT) d.push(a.nodeValue);
			else {
				d.push(c + "<" + a.nodeName);
				var e = a.attributes;
				if (null != e) for (var f = 0; f < e.length; f++) {
					var g = Utils.htmlEntities(e[f].nodeValue);
					d.push(" " + e[f].nodeName + '="' + g + '"')
				}
				e = a.firstChild;
				if (null != e) {
					for (d.push(">\n"); null != e;) d.push(Utils.getPrettyXml(e, b, c + b)), e = e.nextSibling;
					d.push(c + "</" + a.nodeName + ">\n")
				} else d.push("/>\n")
			}
			return d.join("")
		},
		removeWhitespace: function(a, b) {
			for (var c = b ? a.previousSibling : a.nextSibling; null != c && c.nodeType == Constants.NODETYPE_TEXT;) {
				var d = b ? c.previousSibling : c.nextSibling,
					e = Utils.getTextContent(c);
				0 == Utils.trim(e).length && c.parentNode.removeChild(c);
				c = d
			}
		},
		htmlEntities: function(a, b) {
			a = (a || "").replace(/&/g, "&amp;");
			a = a.replace(/"/g, "&quot;");
			a = a.replace(/\'/g, "&#39;");
			a = a.replace(/</g, "&lt;");
			a = a.replace(/>/g, "&gt;");
			if (null == b || b) a = a.replace(/\n/g, "&#xa;");
			return a
		},
		isVml: function(a) {
			return null != a && "urn:schemas-microsoft-com:vml" == a.tagUrn
		},
		getXml: function(a, b) {
			var c = "";
			null != window.XMLSerializer && 9 != document.documentMode ? c = (new XMLSerializer).serializeToString(a) : null != a.xml && (c = a.xml.replace(/\r\n\t[\t]*/g, "").replace(/>\r\n/g, ">").replace(/\r\n/g, "\n"));
			return c = c.replace(/\n/g, b || "&#xa;")
		},
		getTextContent: function(a) {
			return void 0 !== a.innerText ? a.innerText : null != a ? a[void 0 === a.textContent ? "text" : "textContent"] : ""
		},
		setTextContent: function(a, b) {
			void 0 !== a.innerText ? a.innerText = b : a[void 0 === a.textContent ? "text" : "textContent"] = b
		},
		getInnerHtml: function() {
			return Client.IS_IE ?
			function(a) {
				return null != a ? a.innerHTML : ""
			} : function(a) {
				return null != a ? (new XMLSerializer).serializeToString(a) : ""
			}
		}(),
		getOuterHtml: function() {
			return Client.IS_IE ?
			function(a) {
				if (null != a) {
					if (null != a.outerHTML) return a.outerHTML;
					var b = [];
					b.push("<" + a.nodeName);
					var c = a.attributes;
					if (null != c) for (var d = 0; d < c.length; d++) {
						var e = c[d].nodeValue;
						null != e && 0 < e.length && (b.push(" "), b.push(c[d].nodeName), b.push('="'), b.push(e), b.push('"'))
					}
					0 == a.innerHTML.length ? b.push("/>") : (b.push(">"), b.push(a.innerHTML), b.push("</" + a.nodeName + ">"));
					return b.join("")
				}
				return ""
			} : function(a) {
				return null != a ? (new XMLSerializer).serializeToString(a) : ""
			}
		}(),
		write: function(a, b) {
			var c = a.ownerDocument.createTextNode(b);
			null != a && a.appendChild(c);
			return c
		},
		writeln: function(a, b) {
			var c = a.ownerDocument.createTextNode(b);
			null != a && (a.appendChild(c), a.appendChild(document.createElement("br")));
			return c
		},
		br: function(a, b) {
			b = b || 1;
			for (var c = null, d = 0; d < b; d++) null != a && (c = a.ownerDocument.createElement("br"), a.appendChild(c));
			return c
		},
		button: function(a, b, c) {
			c = null != c ? c : document;
			c = c.createElement("button");
			Utils.write(c, a);
			Event.addListener(c, "click", function(a) {
				b(a)
			});
			return c
		},
		para: function(a, b) {
			var c = document.createElement("p");
			Utils.write(c, b);
			null != a && a.appendChild(c);
			return c
		},
		addTransparentBackgroundFilter: function(a) {
			a.style.filter += "progid:DXImageTransform.Microsoft.AlphaImageLoader(src='" + Client.imageBasePath + "/transparent.gif', sizingMethod='scale')"
		},
		linkAction: function(a, b, c, d, e) {
			return Utils.link(a, b, function() {
				c.execute(d)
			}, e)
		},
		linkInvoke: function(a, b, c, d, e, f) {
			return Utils.link(a, b, function() {
				c[d](e)
			}, f)
		},
		link: function(a, b, c, d) {
			var e = document.createElement("span");
			e.style.color = "blue";
			e.style.textDecoration = "underline";
			e.style.cursor = "pointer";
			null != d && (e.style.paddingLeft = d + "px");
			Event.addListener(e, "click", c);
			Utils.write(e, b);
			null != a && a.appendChild(e);
			return e
		},
		fit: function(a) {
			var b = parseInt(a.offsetLeft),
				c = parseInt(a.offsetWidth),
				d = Utils.getDocumentScrollOrigin(a.ownerDocument),
				e = d.x,
				d = d.y,
				f = document.body,
				g = document.documentElement,
				k = e + (f.clientWidth || g.clientWidth);
			b + c > k && (a.style.left = Math.max(e, k - c) + "px");
			b = parseInt(a.offsetTop);
			c = parseInt(a.offsetHeight);
			f = d + Math.max(f.clientHeight || 0, g.clientHeight);
			b + c > f && (a.style.top = Math.max(d, f - c) + "px")
		},
		load: function(a) {
			a = new XmlRequest(a, null, "GET", !1);
			a.send();
			return a
		},
		get: function(a, b, c) {
			return (new XmlRequest(a, null, "GET")).send(b, c)
		},
		post: function(a, b, c, d) {
			return (new XmlRequest(a, b)).send(c, d)
		},
		submit: function(a, b, c, d) {
			return (new XmlRequest(a, b)).simulate(c, d)
		},
		loadInto: function(a, b, c) {
			Client.IS_IE ? b.onreadystatechange = function() {
				4 == b.readyState && c()
			} : b.addEventListener("load", c, !1);
			b.load(a)
		},
		getValue: function(a, b, c) {
			a = null != a ? a[b] : null;
			null == a && (a = c);
			return a
		},
		getNumber: function(a, b, c) {
			a = null != a ? a[b] : null;
			null == a && (a = c || 0);
			return Number(a)
		},
		getColor: function(a, b, c) {
			a = null != a ? a[b] : null;
			null == a ? a = c : a == Constants.NONE && (a = null);
			return a
		},
		clone: function(a, b, c) {
			c = null != c ? c : !1;
			var d = null;
			if (null != a && "function" == typeof a.constructor) {
				var d = new a.constructor,
					e;
				for (e in a) if (e != ObjectIdentity.FIELD_NAME && (null == b || 0 > Utils.indexOf(b, e))) d[e] = !c && "object" == typeof a[e] ? Utils.clone(a[e]) : a[e]
			}
			return d
		},
		equalPoints: function(a, b) {
			if (null == a && null != b || null != a && null == b || null != a && null != b && a.length != b.length) return !1;
			if (null != a && null != b) for (var c = 0; c < a.length; c++) if (a[c] == b[c] || null != a[c] && !a[c].equals(b[c])) return !1;
			return !0
		},
		equalEntries: function(a, b) {
			if (null == a && null != b || null != a && null == b || null != a && null != b && a.length != b.length) return !1;
			if (null != a && null != b) for (var c in a) if ((!Utils.isNaN(a[c]) || !Utils.isNaN(b[c])) && a[c] != b[c]) return !1;
			return !0
		},
		isNaN: function(a) {
			return "number" == typeof a && isNaN(a)
		},
		extend: function(a, b) {
			var c = function() {};
			c.prototype = b.prototype;
			a.prototype = new c;
			a.prototype.constructor = a
		},
		toString: function(a) {
			var b = "",
				c;
			for (c in a) try {
				if (null == a[c]) b += c + " = [null]\n";
				else if ("function" == typeof a[c]) b += c + " => [Function]\n";
				else if ("object" == typeof a[c]) var d = Utils.getFunctionName(a[c].constructor),
					b = b + (c + " => [" + d + "]\n");
				else b += c + " = " + a[c] + "\n"
			} catch (e) {
				b += c + "=" + e.message
			}
			return b
		},
		toRadians: function(a) {
			return Math.PI * a / 180
		},
		arcToCurves: function(a, b, c, d, e, f, g, k, l) {
			k -= a;
			l -= b;
			if (0 === c || 0 === d) return q;
			c = Math.abs(c);
			d = Math.abs(d);
			var m = -k / 2,
				n = -l / 2,
				p = Math.cos(e * Math.PI / 180),
				q = Math.sin(e * Math.PI / 180);
			e = p * m + q * n;
			var m = -1 * q * m + p * n,
				n = e * e,
				r = m * m,
				s = c * c,
				t = d * d,
				u = n / s + r / t;
			1 < u ? (c *= Math.sqrt(u), d *= Math.sqrt(u), f = 0) : (u = 1, f === g && (u = -1), f = u * Math.sqrt((s * t - s * r - t * n) / (s * r + t * n)));
			n = f * c * m / d;
			r = -1 * f * d * e / c;
			k = p * n - q * r + k / 2;
			l = q * n + p * r + l / 2;
			s = Math.atan2((m - r) / d, (e - n) / c) - Math.atan2(0, 1);
			f = 0 <= s ? s : 2 * Math.PI + s;
			s = Math.atan2((-m - r) / d, (-e - n) / c) - Math.atan2((m - r) / d, (e - n) / c);
			e = 0 <= s ? s : 2 * Math.PI + s;
			0 == g && 0 < e ? e -= 2 * Math.PI : 0 != g && 0 > e && (e += 2 * Math.PI);
			g = 2 * e / Math.PI;
			g = Math.ceil(0 > g ? -1 * g : g);
			e /= g;
			m = 8 / 3 * Math.sin(e / 4) * Math.sin(e / 4) / Math.sin(e / 2);
			n = p * c;
			p *= d;
			c *= q;
			d *= q;
			for (var x = Math.cos(f), y = Math.sin(f), r = -m * (n * y + d * x), s = -m * (c * y - p * x), u = t = 0, q = [], v = 0; v < g; ++v) {
				f += e;
				var x = Math.cos(f),
					y = Math.sin(f),
					t = n * x - d * y + k,
					u = c * x + p * y + l,
					z = -m * (n * y + d * x),
					x = -m * (c * y - p * x),
					y = 6 * v;
				q[y] = Number(r + a);
				q[y + 1] = Number(s + b);
				q[y + 2] = Number(t - z + a);
				q[y + 3] = Number(u - x + b);
				q[y + 4] = Number(t + a);
				q[y + 5] = Number(u + b);
				r = t + z;
				s = u + x
			}
			return q
		},
		getBoundingBox: function(a, b, c) {
			var d = null;
			if (null != a && null != b && 0 != b) {
				b = Utils.toRadians(b);
				var d = Math.cos(b),
					e = Math.sin(b);
				c = null != c ? c : new Point(a.x + a.width / 2, a.y + a.height / 2);
				var f = new Point(a.x, a.y);
				b = new Point(a.x + a.width, a.y);
				var g = new Point(b.x, a.y + a.height);
				a = new Point(a.x, g.y);
				f = Utils.getRotatedPoint(f, d, e, c);
				b = Utils.getRotatedPoint(b, d, e, c);
				g = Utils.getRotatedPoint(g, d, e, c);
				a = Utils.getRotatedPoint(a, d, e, c);
				d = new Rectangle(f.x, f.y, 0, 0);
				d.add(new Rectangle(b.x, b.y, 0, 0));
				d.add(new Rectangle(g.x, g.y, 0, 0));
				d.add(new Rectangle(a.x, a.y, 0, 0))
			}
			return d
		},
		getRotatedPoint: function(a, b, c, d) {
			d = null != d ? d : new Point;
			var e = a.x - d.x;
			a = a.y - d.y;
			return new Point(e * b - a * c + d.x, a * b + e * c + d.y)
		},
		getPortConstraints: function(a, b, c, d) {
			b = Utils.getValue(a.style, Constants.STYLE_PORT_CONSTRAINT, null);
			if (null == b) return d;
			d = b.toString();
			b = Constants.DIRECTION_MASK_NONE;
			c = 0;
			1 == Utils.getValue(a.style, Constants.STYLE_PORT_CONSTRAINT_ROTATION, 0) && (c = Utils.getValue(a.style, Constants.STYLE_ROTATION, 0));
			a = 0;
			45 < c ? (a = 1, 135 <= c && (a = 2)) : -45 > c && (a = 3, -135 >= c && (a = 2));
			if (0 <= d.indexOf(Constants.DIRECTION_NORTH)) switch (a) {
			case 0:
				b |= Constants.DIRECTION_MASK_NORTH;
				break;
			case 1:
				b |= Constants.DIRECTION_MASK_EAST;
				break;
			case 2:
				b |= Constants.DIRECTION_MASK_SOUTH;
				break;
			case 3:
				b |= Constants.DIRECTION_MASK_WEST
			}
			if (0 <= d.indexOf(Constants.DIRECTION_WEST)) switch (a) {
			case 0:
				b |= Constants.DIRECTION_MASK_WEST;
				break;
			case 1:
				b |= Constants.DIRECTION_MASK_NORTH;
				break;
			case 2:
				b |= Constants.DIRECTION_MASK_EAST;
				break;
			case 3:
				b |= Constants.DIRECTION_MASK_SOUTH
			}
			if (0 <= d.indexOf(Constants.DIRECTION_SOUTH)) switch (a) {
			case 0:
				b |= Constants.DIRECTION_MASK_SOUTH;
				break;
			case 1:
				b |= Constants.DIRECTION_MASK_WEST;
				break;
			case 2:
				b |= Constants.DIRECTION_MASK_NORTH;
				break;
			case 3:
				b |= Constants.DIRECTION_MASK_EAST
			}
			if (0 <= d.indexOf(Constants.DIRECTION_EAST)) switch (a) {
			case 0:
				b |= Constants.DIRECTION_MASK_EAST;
				break;
			case 1:
				b |= Constants.DIRECTION_MASK_SOUTH;
				break;
			case 2:
				b |= Constants.DIRECTION_MASK_WEST;
				break;
			case 3:
				b |= Constants.DIRECTION_MASK_NORTH
			}
			return b
		},
		reversePortConstraints: function(a) {
			var b = 0,
				b = (a & Constants.DIRECTION_MASK_WEST) << 3,
				b = b | (a & Constants.DIRECTION_MASK_NORTH) << 1,
				b = b | (a & Constants.DIRECTION_MASK_SOUTH) >> 1;
			return b |= (a & Constants.DIRECTION_MASK_EAST) >> 3
		},
		findNearestSegment: function(a, b, c) {
			var d = -1;
			if (0 < a.absolutePoints.length) for (var e = a.absolutePoints[0], f = null, g = 1; g < a.absolutePoints.length; g++) {
				var k = a.absolutePoints[g],
					e = Utils.ptSegDistSq(e.x, e.y, k.x, k.y, b, c);
				if (null == f || e < f) f = e, d = g - 1;
				e = k
			}
			return d
		},
		rectangleIntersectsSegment: function(a, b, c) {
			var d = a.y,
				e = a.x,
				f = d + a.height,
				g = e + a.width;
			a = b.x;
			var k = c.x;
			b.x > c.x && (a = c.x, k = b.x);
			k > g && (k = g);
			a < e && (a = e);
			if (a > k) return !1;
			var e = b.y,
				g = c.y,
				l = c.x - b.x;
			1E-7 < Math.abs(l) && (c = (c.y - b.y) / l, b = b.y - c * b.x, e = c * a + b, g = c * k + b);
			e > g && (b = g, g = e, e = b);
			g > f && (g = f);
			e < d && (e = d);
			return e > g ? !1 : !0
		},
		contains: function(a, b, c) {
			return a.x <= b && a.x + a.width >= b && a.y <= c && a.y + a.height >= c
		},
		intersects: function(a, b) {
			var c = a.width,
				d = a.height,
				e = b.width,
				f = b.height;
			if (0 >= e || 0 >= f || 0 >= c || 0 >= d) return !1;
			var g = a.x,
				k = a.y,
				l = b.x,
				m = b.y,
				e = e + l,
				f = f + m,
				c = c + g,
				d = d + k;
			return (e < l || e > g) && (f < m || f > k) && (c < g || c > l) && (d < k || d > m)
		},
		intersectsHotspot: function(a, b, c, d, e, f) {
			d = null != d ? d : 1;
			e = null != e ? e : 0;
			f = null != f ? f : 0;
			if (0 < d) {
				var g = a.getCenterX(),
					k = a.getCenterY(),
					l = a.width,
					m = a.height,
					n = Utils.getValue(a.style, Constants.STYLE_STARTSIZE) * a.view.scale;
				0 < n && (Utils.getValue(a.style, Constants.STYLE_HORIZONTAL, !0) ? (k = a.y + n / 2, m = n) : (g = a.x + n / 2, l = n));
				l = Math.max(e, l * d);
				m = Math.max(e, m * d);
				0 < f && (l = Math.min(l, f), m = Math.min(m, f));
				d = new Rectangle(g - l / 2, k - m / 2, l, m);
				g = Utils.toRadians(Utils.getValue(a.style, Constants.STYLE_ROTATION) || 0);
				0 != g && (e = Math.cos(-g), f = Math.sin(-g), g = new Point(a.getCenterX(), a.getCenterY()), a = Utils.getRotatedPoint(new Point(b, c), e, f, g), b = a.x, c = a.y);
				return Utils.contains(d, b, c)
			}
			return !0
		},
		getOffset: function(a, b) {
			var c = 0,
				d = 0;
			if (null != b && b) var e = Utils.getDocumentScrollOrigin(a.ownerDocument),
				c = c + e.x,
				d = d + e.y;
			for (; a.offsetParent;) c += a.offsetLeft, d += a.offsetTop, a = a.offsetParent;
			return new Point(c, d)
		},
		getDocumentScrollOrigin: function(a) {
			if (Client.IS_QUIRKS) return new Point(a.body.scrollLeft, a.body.scrollTop);
			a = a.defaultView || a.parentWindow;
			return new Point(null != a && void 0 !== window.pageXOffset ? window.pageXOffset : (document.documentElement || document.body.parentNode || document.body).scrollLeft, null != a && void 0 !== window.pageYOffset ? window.pageYOffset : (document.documentElement || document.body.parentNode || document.body).scrollTop)
		},
		getScrollOrigin: function(a) {
			for (var b = document.body, c = document.documentElement, d = Utils.getDocumentScrollOrigin(null != a ? a.ownerDocument : document); null != a && a != b && a != c;)!isNaN(a.scrollLeft) && !isNaN(a.scrollTop) && (d.x += a.scrollLeft, d.y += a.scrollTop), a = a.parentNode;
			return d
		},
		convertPoint: function(a, b, c) {
			var d = Utils.getScrollOrigin(a);
			a = Utils.getOffset(a);
			a.x -= d.x;
			a.y -= d.y;
			return new Point(b - a.x, c - a.y)
		},
		ltrim: function(a, b) {
			return a.replace(RegExp("^[" + (b || "\\s") + "]+", "g"), "")
		},
		rtrim: function(a, b) {
			return a.replace(RegExp("[" + (b || "\\s") + "]+$", "g"), "")
		},
		trim: function(a, b) {
			return Utils.ltrim(Utils.rtrim(a, b), b)
		},
		isNumeric: function(a) {
			return !isNaN(parseFloat(a)) && isFinite(a) && ("string" != typeof a || 0 > a.toLowerCase().indexOf("0x"))
		},
		mod: function(a, b) {
			return (a % b + b) % b
		},
		intersection: function(a, b, c, d, e, f, g, k) {
			var l = (k - f) * (c - a) - (g - e) * (d - b);
			g = ((g - e) * (b - f) - (k - f) * (a - e)) / l;
			e = ((c - a) * (b - f) - (d - b) * (a - e)) / l;
			return 0 <= g && 1 >= g && 0 <= e && 1 >= e ? new Point(a + g * (c - a), b + g * (d - b)) : null
		},
		ptSegDistSq: function(a, b, c, d, e, f) {
			c -= a;
			d -= b;
			e -= a;
			f -= b;
			0 >= e * c + f * d ? c = 0 : (e = c - e, f = d - f, a = e * c + f * d, c = 0 >= a ? 0 : a * a / (c * c + d * d));
			e = e * e + f * f - c;
			0 > e && (e = 0);
			return e
		},
		relativeCcw: function(a, b, c, d, e, f) {
			c -= a;
			d -= b;
			e -= a;
			f -= b;
			a = e * d - f * c;
			0 == a && (a = e * c + f * d, 0 < a && (a = (e - c) * c + (f - d) * d, 0 > a && (a = 0)));
			return 0 > a ? -1 : 0 < a ? 1 : 0
		},
		animateChanges: function(a, b) {
			Effects.animateChanges.apply(this, arguments)
		},
		cascadeOpacity: function(a, b, c) {
			Effects.cascadeOpacity.apply(this, arguments)
		},
		fadeOut: function(a, b, c, d, e, f) {
			Effects.fadeOut.apply(this, arguments)
		},
		setOpacity: function(a, b) {
			Utils.isVml(a) ? a.style.filter = 100 <= b ? null : "alpha(opacity=" + b / 5 + ")" : Client.IS_IE && ("undefined" === typeof document.documentMode || 9 > document.documentMode) ? a.style.filter = 100 <= b ? null : "alpha(opacity=" + b + ")" : a.style.opacity = b / 100
		},
		createImage: function(a) {
			var b = null;
			Client.IS_IE6 && "CSS1Compat" != document.compatMode ? (b = document.createElement(Client.VML_PREFIX + ":image"), b.setAttribute("src", a), b.style.borderStyle = "none") : (b = document.createElement("img"), b.setAttribute("src", a), b.setAttribute("border", "0"));
			return b
		},
		sortCells: function(a, b) {
			b = null != b ? b : !0;
			var c = new Dictionary;
			a.sort(function(a, e) {
				var f = c.get(a);
				null == f && (f = CellPath.create(a).split(CellPath.PATH_SEPARATOR), c.put(a, f));
				var g = c.get(e);
				null == g && (g = CellPath.create(e).split(CellPath.PATH_SEPARATOR), c.put(e, g));
				f = CellPath.compare(f, g);
				return 0 == f ? 0 : 0 < f == b ? 1 : -1
			});
			return a
		},
		getStylename: function(a) {
			return null != a && (a = a.split(";")[0], 0 > a.indexOf("=")) ? a : ""
		},
		getStylenames: function(a) {
			var b = [];
			if (null != a) {
				a = a.split(";");
				for (var c = 0; c < a.length; c++) 0 > a[c].indexOf("=") && b.push(a[c])
			}
			return b
		},
		indexOfStylename: function(a, b) {
			if (null != a && null != b) for (var c = a.split(";"), d = 0, e = 0; e < c.length; e++) {
				if (c[e] == b) return d;
				d += c[e].length + 1
			}
			return -1
		},
		addStylename: function(a, b) {
			0 > Utils.indexOfStylename(a, b) && (null == a ? a = "" : 0 < a.length && ";" != a.charAt(a.length - 1) && (a += ";"), a += b);
			return a
		},
		removeStylename: function(a, b) {
			var c = [];
			if (null != a) for (var d = a.split(";"), e = 0; e < d.length; e++) d[e] != b && c.push(d[e]);
			return c.join(";")
		},
		removeAllStylenames: function(a) {
			var b = [];
			if (null != a) {
				a = a.split(";");
				for (var c = 0; c < a.length; c++) 0 <= a[c].indexOf("=") && b.push(a[c])
			}
			return b.join(";")
		},
		setCellStyles: function(a, b, c, d) {
			if (null != b && 0 < b.length) {
				a.beginUpdate();
				try {
					for (var e = 0; e < b.length; e++) if (null != b[e]) {
						var f = Utils.setStyle(a.getStyle(b[e]), c, d);
						a.setStyle(b[e], f)
					}
				} finally {
					a.endUpdate()
				}
			}
		},
		setStyle: function(a, b, c) {
			var d = null != c && ("undefined" == typeof c.length || 0 < c.length);
			if (null == a || 0 == a.length) d && (a = b + "=" + c);
			else {
				var e = a.indexOf(b + "=");
				0 > e ? d && (d = ";" == a.charAt(a.length - 1) ? "" : ";", a = a + d + b + "=" + c) : (b = d ? b + "=" + c : "", c = a.indexOf(";", e), d || c++, a = a.substring(0, e) + b + (c > e ? a.substring(c) : ""))
			}
			return a
		},
		setCellStyleFlags: function(a, b, c, d, e) {
			if (null != b && 0 < b.length) {
				a.beginUpdate();
				try {
					for (var f = 0; f < b.length; f++) if (null != b[f]) {
						var g = Utils.setStyleFlag(a.getStyle(b[f]), c, d, e);
						a.setStyle(b[f], g)
					}
				} finally {
					a.endUpdate()
				}
			}
		},
		setStyleFlag: function(a, b, c, d) {
			if (null == a || 0 == a.length) a = d || null == d ? b + "=" + c : b + "=0";
			else {
				var e = a.indexOf(b + "=");
				if (0 > e) e = ";" == a.charAt(a.length - 1) ? "" : ";", a = d || null == d ? a + e + b + "=" + c : a + e + b + "=0";
				else {
					var f = a.indexOf(";", e),
						g = "",
						g = 0 > f ? a.substring(e + b.length + 1) : a.substring(e + b.length + 1, f),
						g = null == d ? parseInt(g) ^ c : d ? parseInt(g) | c : parseInt(g) & ~c;
					a = a.substring(0, e) + b + "=" + g + (0 <= f ? a.substring(f) : "")
				}
			}
			return a
		},
		getAlignmentAsPoint: function(a, b) {
			var c = 0,
				d = 0;
			a == Constants.ALIGN_CENTER ? c = -0.5 : a == Constants.ALIGN_RIGHT && (c = -1);
			b == Constants.ALIGN_MIDDLE ? d = -0.5 : b == Constants.ALIGN_BOTTOM && (d = -1);
			return new Point(c, d)
		},
		getSizeForString: function(a, b, c, d) {
			b = null != b ? b : Constants.DEFAULT_FONTSIZE;
			c = null != c ? c : Constants.DEFAULT_FONTFAMILY;
			var e = document.createElement("div");
			e.style.fontFamily = c;
			e.style.fontSize = Math.round(b) + "px";
			e.style.lineHeight = Math.round(b * Constants.LINE_HEIGHT) + "px";
			e.style.position = "absolute";
			e.style.visibility = "hidden";
			e.style.display = Client.IS_QUIRKS ? "inline" : "inline-block";
			e.style.zoom = "1";
			null != d ? (e.style.width = d + "px", e.style.whiteSpace = "normal") : e.style.whiteSpace = "nowrap";
			e.innerHTML = a;
			document.body.appendChild(e);
			a = new Rectangle(0, 0, e.offsetWidth, e.offsetHeight);
			document.body.removeChild(e);
			return a
		},
		getViewXml: function(a, b, c, d, e) {
			d = null != d ? d : 0;
			e = null != e ? e : 0;
			b = null != b ? b : 1;
			null == c && (c = [a.getModel().getRoot()]);
			var f = a.getView(),
				g = null,
				k = f.isEventsEnabled();
			f.setEventsEnabled(!1);
			var l = f.drawPane,
				m = f.overlayPane;
			a.dialect == Constants.DIALECT_SVG ? (f.drawPane = document.createElementNS(Constants.NS_SVG, "g"), f.canvas.appendChild(f.drawPane), f.overlayPane = document.createElementNS(Constants.NS_SVG, "g")) : (f.drawPane = f.drawPane.cloneNode(!1), f.canvas.appendChild(f.drawPane), f.overlayPane = f.overlayPane.cloneNode(!1));
			f.canvas.appendChild(f.overlayPane);
			var n = f.getTranslate();
			f.translate = new Point(d, e);
			b = new TemporaryCellStates(a.getView(), b, c);
			try {
				g = (new Codec).encode(a.getView())
			} finally {
				b.destroy(), f.translate = n, f.canvas.removeChild(f.drawPane), f.canvas.removeChild(f.overlayPane), f.drawPane = l, f.overlayPane = m, f.setEventsEnabled(k)
			}
			return g
		},
		getScaleForPageCount: function(a, b, c, d) {
			if (1 > a) return 1;
			c = null != c ? c : Constants.PAGE_FORMAT_A4_PORTRAIT;
			d = null != d ? d : 0;
			var e = c.width - 2 * d;
			c = c.height - 2 * d;
			d = b.getGraphBounds().clone();
			b = b.getView().getScale();
			d.width /= b;
			d.height /= b;
			b = d.width;
			c = b / d.height / (e / c);
			d = Math.sqrt(a);
			var f = Math.sqrt(c);
			c = d * f;
			d /= f;
			if (1 > c && d > a) {
				var g = d / a;
				d = a;
				c /= g
			}
			1 > d && c > a && (g = c / a, c = a, d /= g);
			g = Math.ceil(c) * Math.ceil(d);
			for (f = 0; g > a;) {
				var g = Math.floor(c) / c,
					k = Math.floor(d) / d;
				1 == g && (g = Math.floor(c - 1) / c);
				1 == k && (k = Math.floor(d - 1) / d);
				g = g > k ? g : k;
				c *= g;
				d *= g;
				g = Math.ceil(c) * Math.ceil(d);
				f++;
				if (10 < f) break
			}
			return 0.99999 * (e * c / b)
		},
		show: function(a, b, c, d, e, f) {
			c = null != c ? c : 0;
			d = null != d ? d : 0;
			null == b ? b = window.open().document : b.open();
			var g = a.getGraphBounds(),
				k = -g.x + c,
				l = -g.y + d;
			null == e && (e = g.width + c);
			null == f && (f = g.height + d);
			if (Client.IS_IE || 11 == document.documentMode) {
				d = "<html><head>";
				g = document.getElementsByTagName("base");
				for (c = 0; c < g.length; c++) d += g[c].outerHTML;
				d += "<style>";
				for (c = 0; c < document.styleSheets.length; c++) try {
					d += document.styleSheets(c).cssText
				} catch (m) {}
				d = d + "</style></head><body>" + ('<div style="position:absolute;overflow:hidden;width:' + e + "px;height:" + f + 'px;"><div style="position:relative;left:' + k + "px;top:" + l + 'px;">');
				d += a.container.innerHTML;
				d += "</div></div></body><html>";
				b.writeln(d);
				b.close()
			} else {
				b.writeln("<html><head>");
				g = document.getElementsByTagName("base");
				for (c = 0; c < g.length; c++) b.writeln(Utils.getOuterHtml(g[c]));
				d = document.getElementsByTagName("link");
				for (c = 0; c < d.length; c++) b.writeln(Utils.getOuterHtml(d[c]));
				d = document.getElementsByTagName("style");
				for (c = 0; c < d.length; c++) b.writeln(Utils.getOuterHtml(d[c]));
				b.writeln("</head><body></body></html>");
				b.close();
				c = b.createElement("div");
				c.position = "absolute";
				c.overflow = "hidden";
				c.style.width = e + "px";
				c.style.height = f + "px";
				e = b.createElement("div");
				e.style.position = "relative";
				e.style.left = k + "px";
				e.style.top = l + "px";
				for (a = a.container.firstChild; null != a;) k = a.cloneNode(!0), e.appendChild(k), a = a.nextSibling;
				c.appendChild(e);
				b.body.appendChild(c)
			}
			Utils.removeCursors(b.body);
			return b
		},
		printScreen: function(a) {
			var b = window.open();
			Utils.show(a, b.document);
			a = function() {
				b.focus();
				b.print();
				b.close()
			};
			Client.IS_GC ? b.setTimeout(a, 500) : a()
		},
		popup: function(a, b) {
			if (b) {
				var c = document.createElement("div");
				c.style.overflow = "scroll";
				c.style.width = "636px";
				c.style.height = "460px";
				var d = document.createElement("pre");
				d.innerHTML = Utils.htmlEntities(a, !1).replace(/\n/g, "<br>").replace(/ /g, "&nbsp;");
				c.appendChild(d);
				var d = document.body.clientWidth,
					e = Math.max(document.body.clientHeight || 0, document.documentElement.clientHeight),
					c = new Window("Popup Window", c, d / 2 - 320, e / 2 - 240, 640, 480, !1, !0);
				c.setClosable(!0);
				c.setVisible(!0)
			} else Client.IS_NS ? (c = window.open(), c.document.writeln("<pre>" + Utils.htmlEntities(a) + "</pre"), c.document.close()) : (c = window.open(), d = c.document.createElement("pre"), d.innerHTML = Utils.htmlEntities(a, !1).replace(/\n/g, "<br>").replace(/ /g, "&nbsp;"), c.document.body.appendChild(d))
		},
		alert: function(a) {
			alert(a)
		},
		prompt: function(a, b) {
			return prompt(a, null != b ? b : "")
		},
		confirm: function(a) {
			return confirm(a)
		},
		error: function(a, b, c, d) {
			var e = document.createElement("div");
			e.style.padding = "20px";
			var f = document.createElement("img");
			f.setAttribute("src", d || Utils.errorImage);
			f.setAttribute("valign", "bottom");
			f.style.verticalAlign = "middle";
			e.appendChild(f);
			e.appendChild(document.createTextNode(" "));
			e.appendChild(document.createTextNode(" "));
			e.appendChild(document.createTextNode(" "));
			Utils.write(e, a);
			a = document.body.clientWidth;
			d = document.body.clientHeight || document.documentElement.clientHeight;
			var g = new Window(Resources.get(Utils.errorResource) || Utils.errorResource, e, (a - b) / 2, d / 4, b, null, !1, !0);
			c && (Utils.br(e), b = document.createElement("p"), c = document.createElement("button"), Client.IS_IE ? c.style.cssText = "float:right" : c.setAttribute("style", "float:right"), Event.addListener(c, "click", function(a) {
				g.destroy()
			}), Utils.write(c, Resources.get(Utils.closeResource) || Utils.closeResource), b.appendChild(c), e.appendChild(b), Utils.br(e), g.setClosable(!0));
			g.setVisible(!0);
			return g
		},
		makeDraggable: function(a, b, c, d, e, f, g, k, l, m) {
			a = new DragSource(a, c);
			a.dragOffset = new Point(null != e ? e : 0, null != f ? f : Constants.TOOLTIP_VERTICAL_OFFSET);
			a.autoscroll = g;
			a.setGuidesEnabled(!1);
			null != l && (a.highlightDropTargets = l);
			null != m && (a.getDropTarget = m);
			a.getGraphForEvent = function(a) {
				return "function" == typeof b ? b(a) : b
			};
			null != d && (a.createDragElement = function() {
				return d.cloneNode(!0)
			}, k && (a.createPreviewElement = function(a) {
				var b = d.cloneNode(!0),
					c = parseInt(b.style.width),
					e = parseInt(b.style.height);
				b.style.width = Math.round(c * a.view.scale) + "px";
				b.style.height = Math.round(e * a.view.scale) + "px";
				return b
			}));
			return a
		}
	},
	Constants = {
		DEFAULT_HOTSPOT: 0.3,
		MIN_HOTSPOT_SIZE: 8,
		MAX_HOTSPOT_SIZE: 0,
		RENDERING_HINT_EXACT: "exact",
		RENDERING_HINT_FASTER: "faster",
		RENDERING_HINT_FASTEST: "fastest",
		DIALECT_SVG: "svg",
		DIALECT_VML: "vml",
		DIALECT_MIXEDHTML: "mixedHtml",
		DIALECT_PREFERHTML: "preferHtml",
		DIALECT_STRICTHTML: "strictHtml",
		NS_SVG: "http://www.w3.org/2000/svg",
		NS_XHTML: "http://www.w3.org/1999/xhtml",
		NS_XLINK: "http://www.w3.org/1999/xlink",
		SHADOWCOLOR: "gray",
		SHADOW_OFFSET_X: 2,
		SHADOW_OFFSET_Y: 3,
		SHADOW_OPACITY: 1,
		NODETYPE_ELEMENT: 1,
		NODETYPE_ATTRIBUTE: 2,
		NODETYPE_TEXT: 3,
		NODETYPE_CDATA: 4,
		NODETYPE_ENTITY_REFERENCE: 5,
		NODETYPE_ENTITY: 6,
		NODETYPE_PROCESSING_INSTRUCTION: 7,
		NODETYPE_COMMENT: 8,
		NODETYPE_DOCUMENT: 9,
		NODETYPE_DOCUMENTTYPE: 10,
		NODETYPE_DOCUMENT_FRAGMENT: 11,
		NODETYPE_NOTATION: 12,
		TOOLTIP_VERTICAL_OFFSET: 16,
		DEFAULT_VALID_COLOR: "#00FF00",
		DEFAULT_INVALID_COLOR: "#FF0000",
		OUTLINE_HIGHLIGHT_COLOR: "#00FF00",
		OUTLINE_HIGHLIGHT_STROKEWIDTH: 5,
		HIGHLIGHT_STROKEWIDTH: 3,
		CURSOR_MOVABLE_VERTEX: "move",
		CURSOR_MOVABLE_EDGE: "move",
		CURSOR_LABEL_HANDLE: "default",
		CURSOR_BEND_HANDLE: "pointer",
		CURSOR_CONNECT: "pointer",
		HIGHLIGHT_COLOR: "#00FF00",
		CONNECT_TARGET_COLOR: "#0000FF",
		INVALID_CONNECT_TARGET_COLOR: "#FF0000",
		DROP_TARGET_COLOR: "#0000FF",
		VALID_COLOR: "#00FF00",
		INVALID_COLOR: "#FF0000",
		EDGE_SELECTION_COLOR: "#00FF00",
		VERTEX_SELECTION_COLOR: "#00FF00",
		VERTEX_SELECTION_STROKEWIDTH: 1,
		EDGE_SELECTION_STROKEWIDTH: 1,
		VERTEX_SELECTION_DASHED: !0,
		EDGE_SELECTION_DASHED: !0,
		GUIDE_COLOR: "#FF0000",
		GUIDE_STROKEWIDTH: 1,
		OUTLINE_COLOR: "#0099FF",
		OUTLINE_STROKEWIDTH: Client.IS_IE ? 2 : 3,
		HANDLE_SIZE: 7,
		LABEL_HANDLE_SIZE: 4,
		HANDLE_FILLCOLOR: "#00FF00",
		HANDLE_STROKECOLOR: "black",
		LABEL_HANDLE_FILLCOLOR: "yellow",
		CONNECT_HANDLE_FILLCOLOR: "#0000FF",
		LOCKED_HANDLE_FILLCOLOR: "#FF0000",
		OUTLINE_HANDLE_FILLCOLOR: "#00FFFF",
		OUTLINE_HANDLE_STROKECOLOR: "#0033FF",
		DEFAULT_FONTFAMILY: "Arial,Helvetica",
		DEFAULT_FONTSIZE: 11,
		LINE_HEIGHT: 1.2,
		ABSOLUTE_LINE_HEIGHT: !1,
		DEFAULT_FONTSTYLE: 0,
		DEFAULT_STARTSIZE: 40,
		DEFAULT_MARKERSIZE: 6,
		DEFAULT_IMAGESIZE: 24,
		ENTITY_SEGMENT: 30,
		RECTANGLE_ROUNDING_FACTOR: 0.15,
		LINE_ARCSIZE: 20,
		ARROW_SPACING: 10,
		ARROW_WIDTH: 30,
		ARROW_SIZE: 30,
		PAGE_FORMAT_A4_PORTRAIT: new Rectangle(0, 0, 826, 1169),
		PAGE_FORMAT_A4_LANDSCAPE: new Rectangle(0, 0, 1169, 826),
		PAGE_FORMAT_LETTER_PORTRAIT: new Rectangle(0, 0, 850, 1100),
		PAGE_FORMAT_LETTER_LANDSCAPE: new Rectangle(0, 0, 1100, 850),
		NONE: "none",
		STYLE_PERIMETER: "perimeter",
		STYLE_SOURCE_PORT: "sourcePort",
		STYLE_TARGET_PORT: "targetPort",
		STYLE_PORT_CONSTRAINT: "portConstraint",
		STYLE_PORT_CONSTRAINT_ROTATION: "portConstraintRotation",
		STYLE_OPACITY: "opacity",
		STYLE_TEXT_OPACITY: "textOpacity",
		STYLE_OVERFLOW: "overflow",
		STYLE_ORTHOGONAL: "orthogonal",
		STYLE_EXIT_X: "exitX",
		STYLE_EXIT_Y: "exitY",
		STYLE_EXIT_PERIMETER: "exitPerimeter",
		STYLE_ENTRY_X: "entryX",
		STYLE_ENTRY_Y: "entryY",
		STYLE_ENTRY_PERIMETER: "entryPerimeter",
		STYLE_WHITE_SPACE: "whiteSpace",
		STYLE_ROTATION: "rotation",
		STYLE_FILLCOLOR: "fillColor",
		STYLE_SWIMLANE_FILLCOLOR: "swimlaneFillColor",
		STYLE_MARGIN: "margin",
		STYLE_GRADIENTCOLOR: "gradientColor",
		STYLE_GRADIENT_DIRECTION: "gradientDirection",
		STYLE_STROKECOLOR: "strokeColor",
		STYLE_SEPARATORCOLOR: "separatorColor",
		STYLE_STROKEWIDTH: "strokeWidth",
		STYLE_ALIGN: "align",
		STYLE_VERTICAL_ALIGN: "verticalAlign",
		STYLE_LABEL_WIDTH: "labelWidth",
		STYLE_LABEL_POSITION: "labelPosition",
		STYLE_VERTICAL_LABEL_POSITION: "verticalLabelPosition",
		STYLE_IMAGE_ASPECT: "imageAspect",
		STYLE_IMAGE_ALIGN: "imageAlign",
		STYLE_IMAGE_VERTICAL_ALIGN: "imageVerticalAlign",
		STYLE_GLASS: "glass",
		STYLE_IMAGE: "image",
		STYLE_IMAGE_WIDTH: "imageWidth",
		STYLE_IMAGE_HEIGHT: "imageHeight",
		STYLE_IMAGE_BACKGROUND: "imageBackground",
		STYLE_IMAGE_BORDER: "imageBorder",
		STYLE_FLIPH: "flipH",
		STYLE_FLIPV: "flipV",
		STYLE_NOLABEL: "noLabel",
		STYLE_NOEDGESTYLE: "noEdgeStyle",
		STYLE_LABEL_BACKGROUNDCOLOR: "labelBackgroundColor",
		STYLE_LABEL_BORDERCOLOR: "labelBorderColor",
		STYLE_LABEL_PADDING: "labelPadding",
		STYLE_INDICATOR_SHAPE: "indicatorShape",
		STYLE_INDICATOR_IMAGE: "indicatorImage",
		STYLE_INDICATOR_COLOR: "indicatorColor",
		STYLE_INDICATOR_STROKECOLOR: "indicatorStrokeColor",
		STYLE_INDICATOR_GRADIENTCOLOR: "indicatorGradientColor",
		STYLE_INDICATOR_SPACING: "indicatorSpacing",
		STYLE_INDICATOR_WIDTH: "indicatorWidth",
		STYLE_INDICATOR_HEIGHT: "indicatorHeight",
		STYLE_INDICATOR_DIRECTION: "indicatorDirection",
		STYLE_SHADOW: "shadow",
		STYLE_SEGMENT: "segment",
		STYLE_ENDARROW: "endArrow",
		STYLE_STARTARROW: "startArrow",
		STYLE_ENDSIZE: "endSize",
		STYLE_STARTSIZE: "startSize",
		STYLE_SWIMLANE_LINE: "swimlaneLine",
		STYLE_ENDFILL: "endFill",
		STYLE_STARTFILL: "startFill",
		STYLE_DASHED: "dashed",
		STYLE_DASH_PATTERN: "dashPattern",
		STYLE_ROUNDED: "rounded",
		STYLE_CURVED: "curved",
		STYLE_ARCSIZE: "arcSize",
		STYLE_SMOOTH: "smooth",
		STYLE_SOURCE_PERIMETER_SPACING: "sourcePerimeterSpacing",
		STYLE_TARGET_PERIMETER_SPACING: "targetPerimeterSpacing",
		STYLE_PERIMETER_SPACING: "perimeterSpacing",
		STYLE_SPACING: "spacing",
		STYLE_SPACING_TOP: "spacingTop",
		STYLE_SPACING_LEFT: "spacingLeft",
		STYLE_SPACING_BOTTOM: "spacingBottom",
		STYLE_SPACING_RIGHT: "spacingRight",
		STYLE_HORIZONTAL: "horizontal",
		STYLE_DIRECTION: "direction",
		STYLE_ELBOW: "elbow",
		STYLE_FONTCOLOR: "fontColor",
		STYLE_FONTFAMILY: "fontFamily",
		STYLE_FONTSIZE: "fontSize",
		STYLE_FONTSTYLE: "fontStyle",
		STYLE_ASPECT: "aspect",
		STYLE_AUTOSIZE: "autosize",
		STYLE_FOLDABLE: "foldable",
		STYLE_EDITABLE: "editable",
		STYLE_BENDABLE: "bendable",
		STYLE_MOVABLE: "movable",
		STYLE_RESIZABLE: "resizable",
		STYLE_ROTATABLE: "rotatable",
		STYLE_CLONEABLE: "cloneable",
		STYLE_DELETABLE: "deletable",
		STYLE_SHAPE: "shape",
		STYLE_EDGE: "edgeStyle",
		STYLE_LOOP: "loopStyle",
		STYLE_ROUTING_CENTER_X: "routingCenterX",
		STYLE_ROUTING_CENTER_Y: "routingCenterY",
		FONT_BOLD: 1,
		FONT_ITALIC: 2,
		FONT_UNDERLINE: 4,
		FONT_SHADOW: 8,
		SHAPE_RECTANGLE: "rectangle",
		SHAPE_ELLIPSE: "ellipse",
		SHAPE_DOUBLE_ELLIPSE: "doubleEllipse",
		SHAPE_RHOMBUS: "rhombus",
		SHAPE_LINE: "line",
		SHAPE_IMAGE: "image",
		SHAPE_ARROW: "arrow",
		SHAPE_LABEL: "label",
		SHAPE_CYLINDER: "cylinder",
		SHAPE_SWIMLANE: "swimlane",
		SHAPE_CONNECTOR: "connector",
		SHAPE_ACTOR: "actor",
		SHAPE_CLOUD: "cloud",
		SHAPE_TRIANGLE: "triangle",
		SHAPE_HEXAGON: "hexagon",
		ARROW_CLASSIC: "classic",
		ARROW_BLOCK: "block",
		ARROW_OPEN: "open",
		ARROW_OVAL: "oval",
		ARROW_DIAMOND: "diamond",
		ARROW_DIAMOND_THIN: "diamondThin",
		ALIGN_LEFT: "left",
		ALIGN_CENTER: "center",
		ALIGN_RIGHT: "right",
		ALIGN_TOP: "top",
		ALIGN_MIDDLE: "middle",
		ALIGN_BOTTOM: "bottom",
		DIRECTION_NORTH: "north",
		DIRECTION_SOUTH: "south",
		DIRECTION_EAST: "east",
		DIRECTION_WEST: "west",
		DIRECTION_MASK_NONE: 0,
		DIRECTION_MASK_WEST: 1,
		DIRECTION_MASK_NORTH: 2,
		DIRECTION_MASK_SOUTH: 4,
		DIRECTION_MASK_EAST: 8,
		DIRECTION_MASK_ALL: 15,
		ELBOW_VERTICAL: "vertical",
		ELBOW_HORIZONTAL: "horizontal",
		EDGESTYLE_ELBOW: "elbowEdgeStyle",
		EDGESTYLE_ENTITY_RELATION: "entityRelationEdgeStyle",
		EDGESTYLE_LOOP: "loopEdgeStyle",
		EDGESTYLE_SIDETOSIDE: "sideToSideEdgeStyle",
		EDGESTYLE_TOPTOBOTTOM: "topToBottomEdgeStyle",
		EDGESTYLE_ORTHOGONAL: "orthogonalEdgeStyle",
		EDGESTYLE_SEGMENT: "segmentEdgeStyle",
		PERIMETER_ELLIPSE: "ellipsePerimeter",
		PERIMETER_RECTANGLE: "rectanglePerimeter",
		PERIMETER_RHOMBUS: "rhombusPerimeter",
		PERIMETER_HEXAGON: "hexagonPerimeter",
		PERIMETER_TRIANGLE: "trianglePerimeter"
	};

function EventObject(a) {
	this.name = a;
	this.properties = [];
	for (var b = 1; b < arguments.length; b += 2) null != arguments[b + 1] && (this.properties[arguments[b]] = arguments[b + 1])
}
EventObject.prototype.name = null;
EventObject.prototype.properties = null;
EventObject.prototype.consumed = !1;
EventObject.prototype.getName = function() {
	return this.name
};
EventObject.prototype.getProperties = function() {
	return this.properties
};
EventObject.prototype.getProperty = function(a) {
	return this.properties[a]
};
EventObject.prototype.isConsumed = function() {
	return this.consumed
};
EventObject.prototype.consume = function() {
	this.consumed = !0
};

function MouseEvent(a, b) {
	this.evt = a;
	this.state = b
}
MouseEvent.prototype.consumed = !1;
MouseEvent.prototype.evt = null;
MouseEvent.prototype.graphX = null;
MouseEvent.prototype.graphY = null;
MouseEvent.prototype.state = null;
MouseEvent.prototype.getEvent = function() {
	return this.evt
};
MouseEvent.prototype.getSource = function() {
	return Event.getSource(this.evt)
};
MouseEvent.prototype.isSource = function(a) {
	return null != a ? Utils.isAncestorNode(a.node, this.getSource()) : !1
};
MouseEvent.prototype.getX = function() {
	return Event.getClientX(this.getEvent())
};
MouseEvent.prototype.getY = function() {
	return Event.getClientY(this.getEvent())
};
MouseEvent.prototype.getGraphX = function() {
	return this.graphX
};
MouseEvent.prototype.getGraphY = function() {
	return this.graphY
};
MouseEvent.prototype.getState = function() {
	return this.state
};
MouseEvent.prototype.getCell = function() {
	var a = this.getState();
	return null != a ? a.cell : null
};
MouseEvent.prototype.isPopupTrigger = function() {
	return Event.isPopupTrigger(this.getEvent())
};
MouseEvent.prototype.isConsumed = function() {
	return this.consumed
};
MouseEvent.prototype.consume = function(a) {
	(null != a ? a : 1) && this.evt.preventDefault && this.evt.preventDefault();
	Client.IS_IE && (this.evt.returnValue = !0);
	this.consumed = !0
};

function EventSource(a) {
	this.setEventSource(a)
}
EventSource.prototype.eventListeners = null;
EventSource.prototype.eventsEnabled = !0;
EventSource.prototype.eventSource = null;
EventSource.prototype.isEventsEnabled = function() {
	return this.eventsEnabled
};
EventSource.prototype.setEventsEnabled = function(a) {
	this.eventsEnabled = a
};
EventSource.prototype.getEventSource = function() {
	return this.eventSource
};
EventSource.prototype.setEventSource = function(a) {
	this.eventSource = a
};
EventSource.prototype.addListener = function(a, b) {
	null == this.eventListeners && (this.eventListeners = []);
	this.eventListeners.push(a);
	this.eventListeners.push(b)
};
EventSource.prototype.removeListener = function(a) {
	if (null != this.eventListeners) for (var b = 0; b < this.eventListeners.length;) this.eventListeners[b + 1] == a ? this.eventListeners.splice(b, 2) : b += 2
};
EventSource.prototype.fireEvent = function(a, b) {
	if (null != this.eventListeners && this.isEventsEnabled()) {
		null == a && (a = new EventObject);
		null == b && (b = this.getEventSource());
		null == b && (b = this);
		for (var c = [b, a], d = 0; d < this.eventListeners.length; d += 2) {
			var e = this.eventListeners[d];
			(null == e || e == a.getName()) && this.eventListeners[d + 1].apply(this, c)
		}
	}
};
var Event = {
	objects: [],
	addListener: function() {
		var a = function(a, c, d) {
				null == a.ListenerList && (a.ListenerList = [], Event.objects.push(a));
				a.ListenerList.push({
					name: c,
					f: d
				})
			};
		return window.addEventListener ?
		function(b, c, d) {
			b.addEventListener(c, d, !1);
			a(b, c, d)
		} : function(b, c, d) {
			b.attachEvent("on" + c, d);
			a(b, c, d)
		}
	}(),
	removeListener: function() {
		var a = function(a, c, d) {
				if (null != a.ListenerList) {
					c = a.ListenerList.length;
					for (var e = 0; e < c; e++) if (a.ListenerList[e].f == d) {
						a.ListenerList.splice(e, 1);
						break
					}
					0 == a.ListenerList.length && (a.ListenerList = null, a = Utils.indexOf(Event.objects, a), 0 <= a && Event.objects.splice(a, 1))
				}
			};
		return window.removeEventListener ?
		function(b, c, d) {
			b.removeEventListener(c, d, !1);
			a(b, c, d)
		} : function(b, c, d) {
			b.detachEvent("on" + c, d);
			a(b, c, d)
		}
	}(),
	removeAllListeners: function(a) {
		var b = a.ListenerList;
		if (null != b) for (; 0 < b.length;) {
			var c = b[0];
			Event.removeListener(a, c.name, c.f)
		}
	},
	addGestureListeners: function(a, b, c, d) {
		null != b && Event.addListener(a, Client.IS_POINTER ? "MSPointerDown" : "mousedown", b);
		null != c && Event.addListener(a, Client.IS_POINTER ? "MSPointerMove" : "mousemove", c);
		null != d && Event.addListener(a, Client.IS_POINTER ? "MSPointerUp" : "mouseup", d);
		!Client.IS_POINTER && Client.IS_TOUCH && (null != b && Event.addListener(a, "touchstart", b), null != c && Event.addListener(a, "touchmove", c), null != d && Event.addListener(a, "touchend", d))
	},
	removeGestureListeners: function(a, b, c, d) {
		null != b && Event.removeListener(a, Client.IS_POINTER ? "MSPointerDown" : "mousedown", b);
		null != c && Event.removeListener(a, Client.IS_POINTER ? "MSPointerMove" : "mousemove", c);
		null != d && Event.removeListener(a, Client.IS_POINTER ? "MSPointerUp" : "mouseup", d);
		!Client.IS_POINTER && Client.IS_TOUCH && (null != b && Event.removeListener(a, "touchstart", b), null != c && Event.removeListener(a, "touchmove", c), null != d && Event.removeListener(a, "touchend", d))
	},
	redirectMouseEvents: function(a, b, c, d, e, f, g) {
		var k = function(a) {
				return "function" == typeof c ? c(a) : c
			};
		Event.addGestureListeners(a, function(a) {
			null != d ? d(a) : Event.isConsumed(a) || b.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(a, k(a)))
		}, function(a) {
			null != e ? e(a) : Event.isConsumed(a) || b.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(a, k(a)))
		}, function(a) {
			null != f ? f(a) : Event.isConsumed(a) || b.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(a, k(a)))
		});
		Event.addListener(a, "dblclick", function(a) {
			if (null != g) g(a);
			else if (!Event.isConsumed(a)) {
				var c = k(a);
				b.dblClick(a, null != c ? c.cell : null)
			}
		})
	},
	release: function(a) {
		if (null != a && (Event.removeAllListeners(a), a = a.childNodes, null != a)) for (var b = a.length, c = 0; c < b; c += 1) Event.release(a[c])
	},
	addMouseWheelListener: function(a) {
		if (null != a) {
			var b = function(b) {
					null == b && (b = window.event);
					var d = 0,
						d = Client.IS_FF ? -b.detail / 2 : b.wheelDelta / 120;
					0 != d && a(b, 0 < d)
				};
			Client.IS_NS && null == document.documentMode ? Event.addListener(window, Client.IS_SF || Client.IS_GC ? "mousewheel" : "DOMMouseScroll", b) : Event.addListener(document, "mousewheel", b)
		}
	},
	disableContextMenu: function() {
		return Client.IS_IE && ("undefined" === typeof document.documentMode || 9 > document.documentMode) ?
		function(a) {
			Event.addListener(a, "contextmenu", function() {
				return !1
			})
		} : function(a) {
			a.setAttribute("oncontextmenu", "return false;")
		}
	}(),
	getSource: function(a) {
		return null != a.srcElement ? a.srcElement : a.target
	},
	isConsumed: function(a) {
		return null != a.isConsumed && a.isConsumed
	},
	isTouchEvent: function(a) {
		return null != a.pointerType ? "touch" == a.pointerType || a.pointerType === a.MSPOINTER_TYPE_TOUCH : null != a.mozInputSource ? 5 == a.mozInputSource : 0 == a.type.indexOf("touch")
	},
	isMultiTouchEvent: function(a) {
		return null != a.type && 0 == a.type.indexOf("touch") && null != a.touches && 1 < a.touches.length
	},
	isMouseEvent: function(a) {
		return null != a.pointerType ? "mouse" == a.pointerType || a.pointerType === a.MSPOINTER_TYPE_MOUSE : null != a.mozInputSource ? 1 == a.mozInputSource : 0 == a.type.indexOf("mouse")
	},
	isLeftMouseButton: function(a) {
		return a.button == (Client.IS_IE && ("undefined" === typeof document.documentMode || 9 > document.documentMode) ? 1 : 0)
	},
	isMiddleMouseButton: function(a) {
		return a.button == (Client.IS_IE && ("undefined" === typeof document.documentMode || 9 > document.documentMode) ? 4 : 1)
	},
	isRightMouseButton: function(a) {
		return 2 == a.button
	},
	isPopupTrigger: function(a) {
		return Event.isRightMouseButton(a) || Client.IS_MAC && Event.isControlDown(a) && !Event.isShiftDown(a) && !Event.isMetaDown(a) && !Event.isAltDown(a)
	},
	isShiftDown: function(a) {
		return null != a ? a.shiftKey : !1
	},
	isAltDown: function(a) {
		return null != a ? a.altKey : !1
	},
	isControlDown: function(a) {
		return null != a ? a.ctrlKey : !1
	},
	isMetaDown: function(a) {
		return null != a ? a.metaKey : !1
	},
	getMainEvent: function(a) {
		("touchstart" == a.type || "touchmove" == a.type) && null != a.touches && null != a.touches[0] ? a = a.touches[0] : "touchend" == a.type && (null != a.changedTouches && null != a.changedTouches[0]) && (a = a.changedTouches[0]);
		return a
	},
	getClientX: function(a) {
		return Event.getMainEvent(a).clientX
	},
	getClientY: function(a) {
		return Event.getMainEvent(a).clientY
	},
	consume: function(a, b, c) {
		c = null != c ? c : !0;
		if (null != b ? b : 1) a.preventDefault ? (c && a.stopPropagation(), a.preventDefault()) : c && (a.cancelBubble = !0);
		a.isConsumed = !0;
		a.preventDefault || (a.returnValue = !1)
	},
	LABEL_HANDLE: -1,
	ROTATION_HANDLE: -2,
	MOUSE_DOWN: "mouseDown",
	MOUSE_MOVE: "mouseMove",
	MOUSE_UP: "mouseUp",
	ACTIVATE: "activate",
	RESIZE_START: "resizeStart",
	RESIZE: "resize",
	RESIZE_END: "resizeEnd",
	MOVE_START: "moveStart",
	MOVE: "move",
	MOVE_END: "moveEnd",
	PAN_START: "panStart",
	PAN: "pan",
	PAN_END: "panEnd",
	MINIMIZE: "minimize",
	NORMALIZE: "normalize",
	MAXIMIZE: "maximize",
	HIDE: "hide",
	SHOW: "show",
	CLOSE: "close",
	DESTROY: "destroy",
	REFRESH: "refresh",
	SIZE: "size",
	SELECT: "select",
	FIRED: "fired",
	FIRE_MOUSE_EVENT: "fireMouseEvent",
	GESTURE: "gesture",
	TAP_AND_HOLD: "tapAndHold",
	GET: "get",
	RECEIVE: "receive",
	CONNECT: "connect",
	DISCONNECT: "disconnect",
	SUSPEND: "suspend",
	RESUME: "resume",
	MARK: "mark",
	SESSION: "session",
	ROOT: "root",
	POST: "post",
	OPEN: "open",
	SAVE: "save",
	BEFORE_ADD_VERTEX: "beforeAddVertex",
	ADD_VERTEX: "addVertex",
	AFTER_ADD_VERTEX: "afterAddVertex",
	DONE: "done",
	EXECUTE: "execute",
	EXECUTED: "executed",
	BEGIN_UPDATE: "beginUpdate",
	START_EDIT: "startEdit",
	END_UPDATE: "endUpdate",
	END_EDIT: "endEdit",
	BEFORE_UNDO: "beforeUndo",
	UNDO: "undo",
	REDO: "redo",
	CHANGE: "change",
	NOTIFY: "notify",
	LAYOUT_CELLS: "layoutCells",
	CLICK: "click",
	SCALE: "scale",
	TRANSLATE: "translate",
	SCALE_AND_TRANSLATE: "scaleAndTranslate",
	UP: "up",
	DOWN: "down",
	ADD: "add",
	REMOVE: "remove",
	CLEAR: "clear",
	ADD_CELLS: "addCells",
	CELLS_ADDED: "cellsAdded",
	MOVE_CELLS: "moveCells",
	CELLS_MOVED: "cellsMoved",
	RESIZE_CELLS: "resizeCells",
	CELLS_RESIZED: "cellsResized",
	TOGGLE_CELLS: "toggleCells",
	CELLS_TOGGLED: "cellsToggled",
	ORDER_CELLS: "orderCells",
	CELLS_ORDERED: "cellsOrdered",
	REMOVE_CELLS: "removeCells",
	CELLS_REMOVED: "cellsRemoved",
	GROUP_CELLS: "groupCells",
	UNGROUP_CELLS: "ungroupCells",
	REMOVE_CELLS_FROM_PARENT: "removeCellsFromParent",
	FOLD_CELLS: "foldCells",
	CELLS_FOLDED: "cellsFolded",
	ALIGN_CELLS: "alignCells",
	LABEL_CHANGED: "labelChanged",
	CONNECT_CELL: "connectCell",
	CELL_CONNECTED: "cellConnected",
	SPLIT_EDGE: "splitEdge",
	FLIP_EDGE: "flipEdge",
	START_EDITING: "startEditing",
	ADD_OVERLAY: "addOverlay",
	REMOVE_OVERLAY: "removeOverlay",
	UPDATE_CELL_SIZE: "updateCellSize",
	ESCAPE: "escape",
	CLICK: "click",
	DOUBLE_CLICK: "doubleClick",
	START: "start",
	RESET: "reset"
};

function XmlRequest(a, b, c, d, e, f) {
	this.url = a;
	this.params = b;
	this.method = c || "POST";
	this.async = null != d ? d : !0;
	this.username = e;
	this.password = f
}
XmlRequest.prototype.url = null;
XmlRequest.prototype.params = null;
XmlRequest.prototype.method = null;
XmlRequest.prototype.async = null;
XmlRequest.prototype.binary = !1;
XmlRequest.prototype.withCredentials = !1;
XmlRequest.prototype.username = null;
XmlRequest.prototype.password = null;
XmlRequest.prototype.request = null;
XmlRequest.prototype.decodeSimulateValues = !1;
XmlRequest.prototype.isBinary = function() {
	return this.binary
};
XmlRequest.prototype.setBinary = function(a) {
	this.binary = a
};
XmlRequest.prototype.getText = function() {
	return this.request.responseText
};
XmlRequest.prototype.isReady = function() {
	return 4 == this.request.readyState
};
XmlRequest.prototype.getDocumentElement = function() {
	var a = this.getXml();
	return null != a ? a.documentElement : null
};
XmlRequest.prototype.getXml = function() {
	var a = this.request.responseXML;
	if (9 <= document.documentMode || null == a || null == a.documentElement) a = Utils.parseXml(this.request.responseText);
	return a
};
XmlRequest.prototype.getText = function() {
	return this.request.responseText
};
XmlRequest.prototype.getStatus = function() {
	return this.request.status
};
XmlRequest.prototype.create = function() {
	if (window.XMLHttpRequest) return function() {
		var a = new XMLHttpRequest;
		this.isBinary() && a.overrideMimeType && a.overrideMimeType("text/plain; charset=x-user-defined");
		return a
	};
	if ("undefined" != typeof ActiveXObject) return function() {
		return new ActiveXObject("Microsoft.XMLHTTP")
	}
}();
XmlRequest.prototype.send = function(a, b) {
	this.request = this.create();
	null != this.request && (null != a && (this.request.onreadystatechange = Utils.bind(this, function() {
		this.isReady() && (a(this), this.onreadystatechaange = null)
	})), this.request.open(this.method, this.url, this.async, this.username, this.password), this.setRequestHeaders(this.request, this.params), window.XMLHttpRequest && this.withCredentials && (this.request.withCredentials = "true"), this.request.send(this.params))
};
XmlRequest.prototype.setRequestHeaders = function(a, b) {
	null != b && a.setRequestHeader("Content-Type", "application/x-www-form-urlencoded")
};
XmlRequest.prototype.simulate = function(a, b) {
	a = a || document;
	var c = null;
	a == document && (c = window.onbeforeunload, window.onbeforeunload = null);
	var d = a.createElement("form");
	d.setAttribute("method", this.method);
	d.setAttribute("action", this.url);
	null != b && d.setAttribute("target", b);
	d.style.display = "none";
	d.style.visibility = "hidden";
	for (var e = 0 < this.params.indexOf("&") ? this.params.split("&") : this.params.split(), f = 0; f < e.length; f++) {
		var g = e[f].indexOf("=");
		if (0 < g) {
			var k = e[f].substring(0, g),
				g = e[f].substring(g + 1);
			this.decodeSimulateValues && (g = decodeURIComponent(g));
			var l = a.createElement("textarea");
			l.setAttribute("name", k);
			Utils.write(l, g);
			d.appendChild(l)
		}
	}
	a.body.appendChild(d);
	d.submit();
	a.body.removeChild(d);
	null != c && (window.onbeforeunload = c)
};
var Clipboard = {
	STEPSIZE: 10,
	insertCount: 1,
	cells: null,
	setCells: function(a) {
		Clipboard.cells = a
	},
	getCells: function() {
		return Clipboard.cells
	},
	isEmpty: function() {
		return null == Clipboard.getCells()
	},
	cut: function(a, b) {
		b = Clipboard.copy(a, b);
		Clipboard.insertCount = 0;
		Clipboard.removeCells(a, b);
		return b
	},
	removeCells: function(a, b) {
		a.removeCells(b)
	},
	copy: function(a, b) {
		b = b || a.getSelectionCells();
		var c = a.getExportableCells(b);
		Clipboard.insertCount = 1;
		Clipboard.setCells(a.cloneCells(c));
		return c
	},
	paste: function(a) {
		if (!Clipboard.isEmpty()) {
			var b = a.getImportableCells(Clipboard.getCells()),
				c = Clipboard.insertCount * Clipboard.STEPSIZE,
				d = a.getDefaultParent(),
				b = a.importCells(b, c, c, d);
			Clipboard.insertCount++;
			a.setSelectionCells(b)
		}
	}
};

function Window(a, b, c, d, e, f, g, k, l, m) {
	null != b && (g = null != g ? g : !0, this.content = b, this.init(c, d, e, f, m), this.installMaximizeHandler(), this.installMinimizeHandler(), this.installCloseHandler(), this.setMinimizable(g), this.setTitle(a), (null == k || k) && this.installMoveHandler(), null != l && null != l.parentNode ? l.parentNode.replaceChild(this.div, l) : document.body.appendChild(this.div))
}
Window.prototype = new EventSource;
Window.prototype.constructor = Window;
Window.prototype.closeImage = Client.imageBasePath + "/close.gif";
Window.prototype.minimizeImage = Client.imageBasePath + "/minimize.gif";
Window.prototype.normalizeImage = Client.imageBasePath + "/normalize.gif";
Window.prototype.maximizeImage = Client.imageBasePath + "/maximize.gif";
Window.prototype.resizeImage = Client.imageBasePath + "/resize.gif";
Window.prototype.visible = !1;
Window.prototype.minimumSize = new Rectangle(0, 0, 50, 40);
Window.prototype.destroyOnClose = !0;
Window.prototype.contentHeightCorrection = 8 == document.documentMode || 7 == document.documentMode ? 6 : 2;
Window.prototype.title = null;
Window.prototype.content = null;
Window.prototype.init = function(a, b, c, d, e) {
	e = null != e ? e : "Window";
	this.div = document.createElement("div");
	this.div.className = e;
	this.div.style.left = a + "px";
	this.div.style.top = b + "px";
	this.table = document.createElement("table");
	this.table.className = e;
	Client.IS_POINTER && (this.div.style.msTouchAction = "none");
	null != c && (Client.IS_QUIRKS || (this.div.style.width = c + "px"), this.table.style.width = c + "px");
	null != d && (Client.IS_QUIRKS || (this.div.style.height = d + "px"), this.table.style.height = d + "px");
	a = document.createElement("tbody");
	b = document.createElement("tr");
	this.title = document.createElement("td");
	this.title.className = e + "Title";
	b.appendChild(this.title);
	a.appendChild(b);
	b = document.createElement("tr");
	this.td = document.createElement("td");
	this.td.className = e + "Pane";
	7 == document.documentMode && (this.td.style.height = "100%");
	this.contentWrapper = document.createElement("div");
	this.contentWrapper.className = e + "Pane";
	this.contentWrapper.style.width = "100%";
	this.contentWrapper.appendChild(this.content);
	if (Client.IS_QUIRKS || "DIV" != this.content.nodeName.toUpperCase()) this.contentWrapper.style.height = "100%";
	this.td.appendChild(this.contentWrapper);
	b.appendChild(this.td);
	a.appendChild(b);
	this.table.appendChild(a);
	this.div.appendChild(this.table);
	e = Utils.bind(this, function(a) {
		this.activate()
	});
	Event.addGestureListeners(this.title, e);
	Event.addGestureListeners(this.table, e);
	this.hide()
};
Window.prototype.setTitle = function(a) {
	for (var b = this.title.firstChild; null != b;) {
		var c = b.nextSibling;
		b.nodeType == Constants.NODETYPE_TEXT && b.parentNode.removeChild(b);
		b = c
	}
	Utils.write(this.title, a || "")
};
Window.prototype.setScrollable = function(a) {
	0 > navigator.userAgent.indexOf("Presto/2.5") && (this.contentWrapper.style.overflow = a ? "auto" : "hidden")
};
Window.prototype.activate = function() {
	if (Window.activeWindow != this) {
		var a = Utils.getCurrentStyle(this.getElement()),
			a = null != a ? a.zIndex : 3;
		if (Window.activeWindow) {
			var b = Window.activeWindow.getElement();
			null != b && null != b.style && (b.style.zIndex = a)
		}
		b = Window.activeWindow;
		this.getElement().style.zIndex = parseInt(a) + 1;
		Window.activeWindow = this;
		this.fireEvent(new EventObject(Event.ACTIVATE, "previousWindow", b))
	}
};
Window.prototype.getElement = function() {
	return this.div
};
Window.prototype.fit = function() {
	Utils.fit(this.div)
};
Window.prototype.isResizable = function() {
	return null != this.resize ? "none" != this.resize.style.display : !1
};
Window.prototype.setResizable = function(a) {
	if (a) if (null == this.resize) {
		this.resize = document.createElement("img");
		this.resize.style.position = "absolute";
		this.resize.style.bottom = "2px";
		this.resize.style.right = "2px";
		this.resize.setAttribute("src", Client.imageBasePath + "/resize.gif");
		this.resize.style.cursor = "nw-resize";
		var b = null,
			c = null,
			d = null,
			e = null;
		a = Utils.bind(this, function(a) {
			this.activate();
			b = Event.getClientX(a);
			c = Event.getClientY(a);
			d = this.div.offsetWidth;
			e = this.div.offsetHeight;
			Event.addGestureListeners(document, null, f, g);
			this.fireEvent(new EventObject(Event.RESIZE_START, "event", a));
			Event.consume(a)
		});
		var f = Utils.bind(this, function(a) {
			if (null != b && null != c) {
				var f = Event.getClientX(a) - b,
					g = Event.getClientY(a) - c;
				this.setSize(d + f, e + g);
				this.fireEvent(new EventObject(Event.RESIZE, "event", a));
				Event.consume(a)
			}
		}),
			g = Utils.bind(this, function(a) {
				null != b && null != c && (c = b = null, Event.removeGestureListeners(document, null, f, g), this.fireEvent(new EventObject(Event.RESIZE_END, "event", a)), Event.consume(a))
			});
		Event.addGestureListeners(this.resize, a, f, g);
		this.div.appendChild(this.resize)
	} else this.resize.style.display = "inline";
	else null != this.resize && (this.resize.style.display = "none")
};
Window.prototype.setSize = function(a, b) {
	a = Math.max(this.minimumSize.width, a);
	b = Math.max(this.minimumSize.height, b);
	Client.IS_QUIRKS || (this.div.style.width = a + "px", this.div.style.height = b + "px");
	this.table.style.width = a + "px";
	this.table.style.height = b + "px";
	Client.IS_QUIRKS || (this.contentWrapper.style.height = this.div.offsetHeight - this.title.offsetHeight - this.contentHeightCorrection + "px")
};
Window.prototype.setMinimizable = function(a) {
	this.minimize.style.display = a ? "" : "none"
};
Window.prototype.getMinimumSize = function() {
	return new Rectangle(0, 0, 0, this.title.offsetHeight)
};
Window.prototype.installMinimizeHandler = function() {
	this.minimize = document.createElement("img");
	this.minimize.setAttribute("src", this.minimizeImage);
	this.minimize.setAttribute("align", "right");
	this.minimize.setAttribute("title", "Minimize");
	this.minimize.style.cursor = "pointer";
	this.minimize.style.marginRight = "1px";
	this.minimize.style.display = "none";
	this.title.appendChild(this.minimize);
	var a = !1,
		b = null,
		c = null,
		d = Utils.bind(this, function(d) {
			this.activate();
			if (a) a = !1, this.minimize.setAttribute("src", this.minimizeImage), this.minimize.setAttribute("title", "Minimize"), this.contentWrapper.style.display = "", this.maximize.style.display = b, Client.IS_QUIRKS || (this.div.style.height = c), this.table.style.height = c, null != this.resize && (this.resize.style.visibility = ""), this.fireEvent(new EventObject(Event.NORMALIZE, "event", d));
			else {
				a = !0;
				this.minimize.setAttribute("src", this.normalizeImage);
				this.minimize.setAttribute("title", "Normalize");
				this.contentWrapper.style.display = "none";
				b = this.maximize.style.display;
				this.maximize.style.display = "none";
				c = this.table.style.height;
				var f = this.getMinimumSize();
				0 < f.height && (Client.IS_QUIRKS || (this.div.style.height = f.height + "px"), this.table.style.height = f.height + "px");
				0 < f.width && (Client.IS_QUIRKS || (this.div.style.width = f.width + "px"), this.table.style.width = f.width + "px");
				null != this.resize && (this.resize.style.visibility = "hidden");
				this.fireEvent(new EventObject(Event.MINIMIZE, "event", d))
			}
			Event.consume(d)
		});
	Event.addGestureListeners(this.minimize, d)
};
Window.prototype.setMaximizable = function(a) {
	this.maximize.style.display = a ? "" : "none"
};
Window.prototype.installMaximizeHandler = function() {
	this.maximize = document.createElement("img");
	this.maximize.setAttribute("src", this.maximizeImage);
	this.maximize.setAttribute("align", "right");
	this.maximize.setAttribute("title", "Maximize");
	this.maximize.style.cursor = "default";
	this.maximize.style.marginLeft = "1px";
	this.maximize.style.cursor = "pointer";
	this.maximize.style.display = "none";
	this.title.appendChild(this.maximize);
	var a = !1,
		b = null,
		c = null,
		d = null,
		e = null,
		f = Utils.bind(this, function(f) {
			this.activate();
			if ("none" != this.maximize.style.display) {
				if (a) {
					a = !1;
					this.maximize.setAttribute("src", this.maximizeImage);
					this.maximize.setAttribute("title", "Maximize");
					this.contentWrapper.style.display = "";
					this.minimize.style.visibility = "";
					this.div.style.left = b + "px";
					this.div.style.top = c + "px";
					if (!Client.IS_QUIRKS && (this.div.style.height = d, this.div.style.width = e, k = Utils.getCurrentStyle(this.contentWrapper), "auto" == k.overflow || null != this.resize)) this.contentWrapper.style.height = this.div.offsetHeight - this.title.offsetHeight - this.contentHeightCorrection + "px";
					this.table.style.height = d;
					this.table.style.width = e;
					null != this.resize && (this.resize.style.visibility = "");
					this.fireEvent(new EventObject(Event.NORMALIZE, "event", f))
				} else {
					a = !0;
					this.maximize.setAttribute("src", this.normalizeImage);
					this.maximize.setAttribute("title", "Normalize");
					this.contentWrapper.style.display = "";
					this.minimize.style.visibility = "hidden";
					b = parseInt(this.div.style.left);
					c = parseInt(this.div.style.top);
					d = this.table.style.height;
					e = this.table.style.width;
					this.div.style.left = "0px";
					this.div.style.top = "0px";
					k = Math.max(document.body.clientHeight || 0, document.documentElement.clientHeight || 0);
					Client.IS_QUIRKS || (this.div.style.width = document.body.clientWidth - 2 + "px", this.div.style.height = k - 2 + "px");
					this.table.style.width = document.body.clientWidth - 2 + "px";
					this.table.style.height = k - 2 + "px";
					null != this.resize && (this.resize.style.visibility = "hidden");
					if (!Client.IS_QUIRKS) {
						var k = Utils.getCurrentStyle(this.contentWrapper);
						if ("auto" == k.overflow || null != this.resize) this.contentWrapper.style.height = this.div.offsetHeight - this.title.offsetHeight - this.contentHeightCorrection + "px"
					}
					this.fireEvent(new EventObject(Event.MAXIMIZE, "event", f))
				}
				Event.consume(f)
			}
		});
	Event.addGestureListeners(this.maximize, f);
	Event.addListener(this.title, "dblclick", f)
};
Window.prototype.installMoveHandler = function() {
	this.title.style.cursor = "move";
	Event.addGestureListeners(this.title, Utils.bind(this, function(a) {
		var b = Event.getClientX(a),
			c = Event.getClientY(a),
			d = this.getX(),
			e = this.getY(),
			f = Utils.bind(this, function(a) {
				var f = Event.getClientX(a) - b,
					g = Event.getClientY(a) - c;
				this.setLocation(d + f, e + g);
				this.fireEvent(new EventObject(Event.MOVE, "event", a));
				Event.consume(a)
			}),
			g = Utils.bind(this, function(a) {
				Event.removeGestureListeners(document, null, f, g);
				this.fireEvent(new EventObject(Event.MOVE_END, "event", a));
				Event.consume(a)
			});
		Event.addGestureListeners(document, null, f, g);
		this.fireEvent(new EventObject(Event.MOVE_START, "event", a));
		Event.consume(a)
	}));
	Client.IS_POINTER && (this.title.style.msTouchAction = "none")
};
Window.prototype.setLocation = function(a, b) {
	this.div.style.left = a + "px";
	this.div.style.top = b + "px"
};
Window.prototype.getX = function() {
	return parseInt(this.div.style.left)
};
Window.prototype.getY = function() {
	return parseInt(this.div.style.top)
};
Window.prototype.installCloseHandler = function() {
	this.closeImg = document.createElement("img");
	this.closeImg.setAttribute("src", this.closeImage);
	this.closeImg.setAttribute("align", "right");
	this.closeImg.setAttribute("title", "Close");
	this.closeImg.style.marginLeft = "2px";
	this.closeImg.style.cursor = "pointer";
	this.closeImg.style.display = "none";
	this.title.insertBefore(this.closeImg, this.title.firstChild);
	Event.addGestureListeners(this.closeImg, Utils.bind(this, function(a) {
		this.fireEvent(new EventObject(Event.CLOSE, "event", a));
		this.destroyOnClose ? this.destroy() : this.setVisible(!1);
		Event.consume(a)
	}))
};
Window.prototype.setImage = function(a) {
	this.image = document.createElement("img");
	this.image.setAttribute("src", a);
	this.image.setAttribute("align", "left");
	this.image.style.marginRight = "4px";
	this.image.style.marginLeft = "0px";
	this.image.style.marginTop = "-2px";
	this.title.insertBefore(this.image, this.title.firstChild)
};
Window.prototype.setClosable = function(a) {
	this.closeImg.style.display = a ? "" : "none"
};
Window.prototype.isVisible = function() {
	return null != this.div ? "none" != this.div.style.display : !1
};
Window.prototype.setVisible = function(a) {
	null != this.div && this.isVisible() != a && (a ? this.show() : this.hide())
};
Window.prototype.show = function() {
	this.div.style.display = "";
	this.activate();
	var a = Utils.getCurrentStyle(this.contentWrapper);
	if (!Client.IS_QUIRKS && ("auto" == a.overflow || null != this.resize)) this.contentWrapper.style.height = this.div.offsetHeight - this.title.offsetHeight - this.contentHeightCorrection + "px";
	this.fireEvent(new EventObject(Event.SHOW))
};
Window.prototype.hide = function() {
	this.div.style.display = "none";
	this.fireEvent(new EventObject(Event.HIDE))
};
Window.prototype.destroy = function() {
	this.fireEvent(new EventObject(Event.DESTROY));
	null != this.div && (Event.release(this.div), this.div.parentNode.removeChild(this.div), this.div = null);
	this.contentWrapper = this.content = this.title = null
};

function Form(a) {
	this.table = document.createElement("table");
	this.table.className = a;
	this.body = document.createElement("tbody");
	this.table.appendChild(this.body)
}
Form.prototype.table = null;
Form.prototype.body = !1;
Form.prototype.getTable = function() {
	return this.table
};
Form.prototype.addButtons = function(a, b) {
	var c = document.createElement("tr"),
		d = document.createElement("td");
	c.appendChild(d);
	var d = document.createElement("td"),
		e = document.createElement("button");
	Utils.write(e, Resources.get("ok") || "OK");
	d.appendChild(e);
	Event.addListener(e, "click", function() {
		a()
	});
	e = document.createElement("button");
	Utils.write(e, Resources.get("cancel") || "Cancel");
	d.appendChild(e);
	Event.addListener(e, "click", function() {
		b()
	});
	c.appendChild(d);
	this.body.appendChild(c)
};
Form.prototype.addText = function(a, b) {
	var c = document.createElement("input");
	c.setAttribute("type", "text");
	c.value = b;
	return this.addField(a, c)
};
Form.prototype.addCheckbox = function(a, b) {
	var c = document.createElement("input");
	c.setAttribute("type", "checkbox");
	this.addField(a, c);
	b && (c.checked = !0);
	return c
};
Form.prototype.addTextarea = function(a, b, c) {
	var d = document.createElement("textarea");
	Client.IS_NS && c--;
	d.setAttribute("rows", c || 2);
	d.value = b;
	return this.addField(a, d)
};
Form.prototype.addCombo = function(a, b, c) {
	var d = document.createElement("select");
	null != c && d.setAttribute("size", c);
	b && d.setAttribute("multiple", "true");
	return this.addField(a, d)
};
Form.prototype.addOption = function(a, b, c, d) {
	var e = document.createElement("option");
	Utils.writeln(e, b);
	e.setAttribute("value", c);
	d && e.setAttribute("selected", d);
	a.appendChild(e)
};
Form.prototype.addField = function(a, b) {
	var c = document.createElement("tr"),
		d = document.createElement("td");
	Utils.write(d, a);
	c.appendChild(d);
	d = document.createElement("td");
	d.appendChild(b);
	c.appendChild(d);
	this.body.appendChild(c);
	return b
};

function Image(a, b, c) {
	this.src = a;
	this.width = b;
	this.height = c
}
Image.prototype.src = null;
Image.prototype.width = null;
Image.prototype.height = null;

function DivResizer(a, b) {
	if ("div" == a.nodeName.toLowerCase()) {
		null == b && (b = window);
		this.div = a;
		var c = Utils.getCurrentStyle(a);
		null != c && (this.resizeWidth = "auto" == c.width, this.resizeHeight = "auto" == c.height);
		Event.addListener(b, "resize", Utils.bind(this, function(a) {
			this.handlingResize || (this.handlingResize = !0, this.resize(), this.handlingResize = !1)
		}));
		this.resize()
	}
}
DivResizer.prototype.resizeWidth = !0;
DivResizer.prototype.resizeHeight = !0;
DivResizer.prototype.handlingResize = !1;
DivResizer.prototype.resize = function() {
	var a = this.getDocumentWidth(),
		b = this.getDocumentHeight(),
		c = parseInt(this.div.style.left),
		d = parseInt(this.div.style.right),
		e = parseInt(this.div.style.top),
		f = parseInt(this.div.style.bottom);
	this.resizeWidth && (!isNaN(c) && !isNaN(d) && 0 <= c && 0 <= d && 0 < a - d - c) && (this.div.style.width = a - d - c + "px");
	this.resizeHeight && (!isNaN(e) && !isNaN(f) && 0 <= e && 0 <= f && 0 < b - e - f) && (this.div.style.height = b - e - f + "px")
};
DivResizer.prototype.getDocumentWidth = function() {
	return document.body.clientWidth
};
DivResizer.prototype.getDocumentHeight = function() {
	return document.body.clientHeight
};

function DragSource(a, b) {
	this.element = a;
	this.dropHandler = b;
	Event.addGestureListeners(a, Utils.bind(this, function(a) {
		this.mouseDown(a)
	}))
}
DragSource.prototype.element = null;
DragSource.prototype.dropHandler = null;
DragSource.prototype.dragOffset = null;
DragSource.prototype.dragElement = null;
DragSource.prototype.previewElement = null;
DragSource.prototype.enabled = !0;
DragSource.prototype.currentGraph = null;
DragSource.prototype.currentDropTarget = null;
DragSource.prototype.currentPoint = null;
DragSource.prototype.currentGuide = null;
DragSource.prototype.currentHighlight = null;
DragSource.prototype.autoscroll = !0;
DragSource.prototype.guidesEnabled = !0;
DragSource.prototype.gridEnabled = !0;
DragSource.prototype.highlightDropTargets = !0;
DragSource.prototype.dragElementZIndex = 100;
DragSource.prototype.dragElementOpacity = 70;
DragSource.prototype.isEnabled = function() {
	return this.enabled
};
DragSource.prototype.setEnabled = function(a) {
	this.enabled = a
};
DragSource.prototype.isGuidesEnabled = function() {
	return this.guidesEnabled
};
DragSource.prototype.setGuidesEnabled = function(a) {
	this.guidesEnabled = a
};
DragSource.prototype.isGridEnabled = function() {
	return this.gridEnabled
};
DragSource.prototype.setGridEnabled = function(a) {
	this.gridEnabled = a
};
DragSource.prototype.getGraphForEvent = function(a) {
	return null
};
DragSource.prototype.getDropTarget = function(a, b, c) {
	return a.getCellAt(b, c)
};
DragSource.prototype.createDragElement = function(a) {
	return this.element.cloneNode(!0)
};
DragSource.prototype.createPreviewElement = function(a) {
	return null
};
DragSource.prototype.mouseDown = function(a) {
	this.enabled && (!Event.isConsumed(a) && null == this.mouseMoveHandler) && (this.startDrag(a), this.mouseMoveHandler = Utils.bind(this, this.mouseMove), this.mouseUpHandler = Utils.bind(this, this.mouseUp), Event.addGestureListeners(document, null, this.mouseMoveHandler, this.mouseUpHandler), Client.IS_TOUCH && !Event.isMouseEvent(a) && (this.eventSource = Event.getSource(a), Event.addGestureListeners(this.eventSource, null, this.mouseMoveHandler, this.mouseUpHandler)), Event.consume(a, !0, !1))
};
DragSource.prototype.startDrag = function(a) {
	this.dragElement = this.createDragElement(a);
	this.dragElement.style.position = "absolute";
	this.dragElement.style.zIndex = this.dragElementZIndex;
	Utils.setOpacity(this.dragElement, this.dragElementOpacity)
};
DragSource.prototype.stopDrag = function(a) {
	null != this.dragElement && (null != this.dragElement.parentNode && this.dragElement.parentNode.removeChild(this.dragElement), this.dragElement = null)
};
DragSource.prototype.graphContainsEvent = function(a, b) {
	var c = Event.getClientX(b),
		d = Event.getClientY(b),
		e = Utils.getOffset(a.container),
		f = Utils.getScrollOrigin();
	return c >= e.x - f.x && d >= e.y - f.y && c <= e.x - f.x + a.container.offsetWidth && d <= e.y - f.y + a.container.offsetHeight
};
DragSource.prototype.mouseMove = function(a) {
	var b = this.getGraphForEvent(a);
	null != b && !this.graphContainsEvent(b, a) && (b = null);
	b != this.currentGraph && (null != this.currentGraph && this.dragExit(this.currentGraph, a), this.currentGraph = b, null != this.currentGraph && this.dragEnter(this.currentGraph, a));
	null != this.currentGraph && this.dragOver(this.currentGraph, a);
	if (null != this.dragElement && (null == this.previewElement || "visible" != this.previewElement.style.visibility)) {
		var b = Event.getClientX(a),
			c = Event.getClientY(a);
		null == this.dragElement.parentNode && document.body.appendChild(this.dragElement);
		this.dragElement.style.visibility = "visible";
		null != this.dragOffset && (b += this.dragOffset.x, c += this.dragOffset.y);
		var d = Utils.getDocumentScrollOrigin(document);
		this.dragElement.style.left = b + d.x + "px";
		this.dragElement.style.top = c + d.y + "px"
	} else null != this.dragElement && (this.dragElement.style.visibility = "hidden");
	Event.consume(a)
};
DragSource.prototype.mouseUp = function(a) {
	if (null != this.currentGraph) {
		if (null != this.currentPoint && (null == this.previewElement || "hidden" != this.previewElement.style.visibility)) {
			var b = this.currentGraph.view.scale,
				c = this.currentGraph.view.translate;
			this.drop(this.currentGraph, a, this.currentDropTarget, this.currentPoint.x / b - c.x, this.currentPoint.y / b - c.y)
		}
		this.dragExit(this.currentGraph)
	}
	this.stopDrag(a);
	null != this.eventSource && (Event.removeGestureListeners(this.eventSource, null, this.mouseMoveHandler, this.mouseUpHandler), this.eventSource = null);
	Event.removeGestureListeners(document, null, this.mouseMoveHandler, this.mouseUpHandler);
	this.currentGraph = this.mouseUpHandler = this.mouseMoveHandler = null;
	Event.consume(a)
};
DragSource.prototype.dragEnter = function(a, b) {
	a.isMouseDown = !0;
	a.isMouseTrigger = Event.isMouseEvent(b);
	this.previewElement = this.createPreviewElement(a);
	this.isGuidesEnabled() && null != this.previewElement && (this.currentGuide = new Guide(a, a.graphHandler.getGuideStates()));
	this.highlightDropTargets && (this.currentHighlight = new CellHighlight(a, Constants.DROP_TARGET_COLOR))
};
DragSource.prototype.dragExit = function(a, b) {
	this.currentPoint = this.currentDropTarget = null;
	a.isMouseDown = !1;
	null != this.previewElement && (null != this.previewElement.parentNode && this.previewElement.parentNode.removeChild(this.previewElement), this.previewElement = null);
	null != this.currentGuide && (this.currentGuide.destroy(), this.currentGuide = null);
	null != this.currentHighlight && (this.currentHighlight.destroy(), this.currentHighlight = null)
};
DragSource.prototype.dragOver = function(a, b) {
	var c = Utils.getOffset(a.container),
		d = Utils.getScrollOrigin(a.container),
		e = Event.getClientX(b) - c.x + d.x,
		c = Event.getClientY(b) - c.y + d.y;
	a.autoScroll && (null == this.autoscroll || this.autoscroll) && a.scrollPointToVisible(e, c, a.autoExtend);
	null != this.currentHighlight && a.isDropEnabled() && (this.currentDropTarget = this.getDropTarget(a, e, c), d = a.getView().getState(this.currentDropTarget), this.currentHighlight.highlight(d));
	if (null != this.previewElement) {
		null == this.previewElement.parentNode && (a.container.appendChild(this.previewElement), this.previewElement.style.zIndex = "3", this.previewElement.style.position = "absolute");
		var d = this.isGridEnabled() && a.isGridEnabledEvent(b),
			f = !0;
		if (null != this.currentGuide && this.currentGuide.isEnabledForEvent(b)) var f = parseInt(this.previewElement.style.width),
			g = parseInt(this.previewElement.style.height),
			f = new Rectangle(0, 0, f, g),
			c = new Point(e, c),
			c = this.currentGuide.move(f, c, d),
			f = !1,
			e = c.x,
			c = c.y;
		else if (d) var d = a.view.scale,
			g = a.view.translate,
			k = a.gridSize / 2,
			e = (a.snap(e / d - g.x - k) + g.x) * d,
			c = (a.snap(c / d - g.y - k) + g.y) * d;
		null != this.currentGuide && f && this.currentGuide.hide();
		null != this.previewOffset && (e += this.previewOffset.x, c += this.previewOffset.y);
		this.previewElement.style.left = Math.round(e) + "px";
		this.previewElement.style.top = Math.round(c) + "px";
		this.previewElement.style.visibility = "visible"
	}
	this.currentPoint = new Point(e, c)
};
DragSource.prototype.drop = function(a, b, c, d, e) {
	this.dropHandler(a, b, c, d, e);
	"hidden" != a.container.style.visibility && a.container.focus()
};

function Toolbar(a) {
	this.container = a
}
Toolbar.prototype = new EventSource;
Toolbar.prototype.constructor = Toolbar;
Toolbar.prototype.container = null;
Toolbar.prototype.enabled = !0;
Toolbar.prototype.noReset = !1;
Toolbar.prototype.updateDefaultMode = !0;
Toolbar.prototype.addItem = function(a, b, c, d, e, f) {
	var g = document.createElement(null != b ? "img" : "button"),
		k = e || (null != f ? "ToolbarMode" : "ToolbarItem");
	g.className = k;
	g.setAttribute("src", b);
	null != a && (null != b ? g.setAttribute("title", a) : Utils.write(g, a));
	this.container.appendChild(g);
	null != c && (Event.addListener(g, "click", c), Client.IS_TOUCH && Event.addListener(g, "touchend", c));
	a = Utils.bind(this, function(a) {
		null != d ? g.setAttribute("src", b) : g.style.backgroundColor = ""
	});
	Event.addGestureListeners(g, Utils.bind(this, function(a) {
		null != d ? g.setAttribute("src", d) : g.style.backgroundColor = "gray";
		if (null != f) {
			null == this.menu && (this.menu = new PopupMenu, this.menu.init());
			var b = this.currentImg;
			this.menu.isMenuShowing() && this.menu.hideMenu();
			b != g && (this.currentImg = g, this.menu.factoryMethod = f, b = new Point(g.offsetLeft, g.offsetTop + g.offsetHeight), this.menu.popup(b.x, b.y, null, a), this.menu.isMenuShowing() && (g.className = k + "Selected", this.menu.hideMenu = function() {
				PopupMenu.prototype.hideMenu.apply(this);
				g.className = k;
				this.currentImg = null
			}))
		}
	}), null, a);
	Event.addListener(g, "mouseout", a);
	return g
};
Toolbar.prototype.addCombo = function(a) {
	var b = document.createElement("div");
	b.style.display = "inline";
	b.className = "ToolbarComboContainer";
	var c = document.createElement("select");
	c.className = a || "ToolbarCombo";
	b.appendChild(c);
	this.container.appendChild(b);
	return c
};
Toolbar.prototype.addActionCombo = function(a, b) {
	var c = document.createElement("select");
	c.className = b || "ToolbarCombo";
	this.addOption(c, a, null);
	Event.addListener(c, "change", function(a) {
		var b = c.options[c.selectedIndex];
		c.selectedIndex = 0;
		null != b.funct && b.funct(a)
	});
	this.container.appendChild(c);
	return c
};
Toolbar.prototype.addOption = function(a, b, c) {
	var d = document.createElement("option");
	Utils.writeln(d, b);
	"function" == typeof c ? d.funct = c : d.setAttribute("value", c);
	a.appendChild(d);
	return d
};
Toolbar.prototype.addSwitchMode = function(a, b, c, d, e) {
	var f = document.createElement("img");
	f.initialClassName = e || "ToolbarMode";
	f.className = f.initialClassName;
	f.setAttribute("src", b);
	f.altIcon = d;
	null != a && f.setAttribute("title", a);
	Event.addListener(f, "click", Utils.bind(this, function(a) {
		a = this.selectedMode.altIcon;
		null != a ? (this.selectedMode.altIcon = this.selectedMode.getAttribute("src"), this.selectedMode.setAttribute("src", a)) : this.selectedMode.className = this.selectedMode.initialClassName;
		this.updateDefaultMode && (this.defaultMode = f);
		this.selectedMode = f;
		a = f.altIcon;
		null != a ? (f.altIcon = f.getAttribute("src"), f.setAttribute("src", a)) : f.className = f.initialClassName + "Selected";
		this.fireEvent(new EventObject(Event.SELECT));
		c()
	}));
	this.container.appendChild(f);
	null == this.defaultMode && (this.defaultMode = f, this.selectMode(f), c());
	return f
};
Toolbar.prototype.addMode = function(a, b, c, d, e, f) {
	f = null != f ? f : !0;
	var g = document.createElement(null != b ? "img" : "button");
	g.initialClassName = e || "ToolbarMode";
	g.className = g.initialClassName;
	g.setAttribute("src", b);
	g.altIcon = d;
	null != a && g.setAttribute("title", a);
	this.enabled && f && (Event.addListener(g, "click", Utils.bind(this, function(a) {
		this.selectMode(g, c);
		this.noReset = !1
	})), Event.addListener(g, "dblclick", Utils.bind(this, function(a) {
		this.selectMode(g, c);
		this.noReset = !0
	})), null == this.defaultMode && (this.defaultMode = g, this.defaultFunction = c, this.selectMode(g, c)));
	this.container.appendChild(g);
	return g
};
Toolbar.prototype.selectMode = function(a, b) {
	if (this.selectedMode != a) {
		if (null != this.selectedMode) {
			var c = this.selectedMode.altIcon;
			null != c ? (this.selectedMode.altIcon = this.selectedMode.getAttribute("src"), this.selectedMode.setAttribute("src", c)) : this.selectedMode.className = this.selectedMode.initialClassName
		}
		this.selectedMode = a;
		c = this.selectedMode.altIcon;
		null != c ? (this.selectedMode.altIcon = this.selectedMode.getAttribute("src"), this.selectedMode.setAttribute("src", c)) : this.selectedMode.className = this.selectedMode.initialClassName + "Selected";
		this.fireEvent(new EventObject(Event.SELECT, "function", b))
	}
};
Toolbar.prototype.resetMode = function(a) {
	(a || !this.noReset) && this.selectedMode != this.defaultMode && this.selectMode(this.defaultMode, this.defaultFunction)
};
Toolbar.prototype.addSeparator = function(a) {
	return this.addItem(null, a, null)
};
Toolbar.prototype.addBreak = function() {
	Utils.br(this.container)
};
Toolbar.prototype.addLine = function() {
	var a = document.createElement("hr");
	a.style.marginRight = "6px";
	a.setAttribute("size", "1");
	this.container.appendChild(a)
};
Toolbar.prototype.destroy = function() {
	Event.release(this.container);
	this.selectedMode = this.defaultFunction = this.defaultMode = this.container = null;
	null != this.menu && this.menu.destroy()
};

function Session(a, b, c, d) {
	this.model = a;
	this.urlInit = b;
	this.urlPoll = c;
	this.urlNotify = d;
	null != a && (this.codec = new Codec, this.codec.lookup = function(b) {
		return a.getCell(b)
	});
	a.addListener(Event.NOTIFY, Utils.bind(this, function(a, b) {
		var c = b.getProperty("edit");
		(null != c && this.debug || this.connected && !this.suspended) && this.notify("<edit>" + this.encodeChanges(c.changes, c.undone) + "</edit>")
	}))
}
Session.prototype = new EventSource;
Session.prototype.constructor = Session;
Session.prototype.model = null;
Session.prototype.urlInit = null;
Session.prototype.urlPoll = null;
Session.prototype.urlNotify = null;
Session.prototype.codec = null;
Session.prototype.linefeed = "&#xa;";
Session.prototype.escapePostData = !0;
Session.prototype.significantRemoteChanges = !0;
Session.prototype.sent = 0;
Session.prototype.received = 0;
Session.prototype.debug = !1;
Session.prototype.connected = !1;
Session.prototype.suspended = !1;
Session.prototype.polling = !1;
Session.prototype.start = function() {
	this.debug ? (this.connected = !0, this.fireEvent(new EventObject(Event.CONNECT))) : this.connected || this.get(this.urlInit, Utils.bind(this, function(a) {
		this.connected = !0;
		this.fireEvent(new EventObject(Event.CONNECT));
		this.poll()
	}))
};
Session.prototype.suspend = function() {
	this.connected && !this.suspended && (this.suspended = !0, this.fireEvent(new EventObject(Event.SUSPEND)))
};
Session.prototype.resume = function(a, b, c) {
	this.connected && this.suspended && (this.suspended = !1, this.fireEvent(new EventObject(Event.RESUME)), this.polling || this.poll())
};
Session.prototype.stop = function(a) {
	this.connected && (this.connected = !1);
	this.fireEvent(new EventObject(Event.DISCONNECT, "reason", a))
};
Session.prototype.poll = function() {
	this.connected && !this.suspended && null != this.urlPoll ? (this.polling = !0, this.get(this.urlPoll, Utils.bind(this, function() {
		this.poll()
	}))) : this.polling = !1
};
Session.prototype.notify = function(a, b, c) {
	null != a && 0 < a.length && (null != this.urlNotify && (this.debug ? (Log.show(), Log.debug("Session.notify: " + this.urlNotify + " xml=" + a)) : (a = "<message><delta>" + a + "</delta></message>", this.escapePostData && (a = encodeURIComponent(a)), Utils.post(this.urlNotify, "xml=" + a, b, c))), this.sent += a.length, this.fireEvent(new EventObject(Event.NOTIFY, "url", this.urlNotify, "xml", a)))
};
Session.prototype.get = function(a, b, c) {
	if ("undefined" != typeof Utils) {
		var d = Utils.bind(this, function(a) {
			null != c ? c(a) : this.stop(a)
		});
		Utils.get(a, Utils.bind(this, function(c) {
			if ("undefined" != typeof Utils) if (c.isReady() && 404 != c.getStatus()) {
				if (this.received += c.getText().length, this.fireEvent(new EventObject(Event.GET, "url", a, "request", c)), this.isValidResponse(c)) {
					if (0 < c.getText().length) {
						var f = c.getDocumentElement();
						null == f ? d("Invalid response: " + c.getText()) : this.receive(f)
					}
					null != b && b(c)
				}
			} else d("Response not ready")
		}), function(a) {
			d("Transmission error")
		})
	}
};
Session.prototype.isValidResponse = function(a) {
	return 0 > a.getText().indexOf("<?php")
};
Session.prototype.encodeChanges = function(a, b) {
	for (var c = "", d = b ? -1 : 1, e = b ? a.length - 1 : 0; 0 <= e && e < a.length; e += d) var f = this.codec.encode(a[e]),
		c = c + Utils.getXml(f, this.linefeed);
	return c
};
Session.prototype.receive = function(a) {
	if (null != a && a.nodeType == Constants.NODETYPE_ELEMENT) {
		var b = a.getAttribute("namespace");
		null != b && (this.model.prefix = b + "-");
		for (b = a.firstChild; null != b;) {
			var c = b.nodeName.toLowerCase();
			"state" == c ? this.processState(b) : "delta" == c && this.processDelta(b);
			b = b.nextSibling
		}
		this.fireEvent(new EventObject(Event.RECEIVE, "node", a))
	}
};
Session.prototype.processState = function(a) {
	(new Codec(a.ownerDocument)).decode(a.firstChild, this.model)
};
Session.prototype.processDelta = function(a) {
	for (a = a.firstChild; null != a;)"edit" == a.nodeName && this.processEdit(a), a = a.nextSibling
};
Session.prototype.processEdit = function(a) {
	a = this.decodeChanges(a);
	if (0 < a.length) {
		var b = this.createUndoableEdit(a);
		this.model.fireEvent(new EventObject(Event.CHANGE, "edit", b, "changes", a));
		this.model.fireEvent(new EventObject(Event.UNDO, "edit", b));
		this.fireEvent(new EventObject(Event.FIRED, "edit", b))
	}
};
Session.prototype.createUndoableEdit = function(a) {
	var b = new UndoableEdit(this.model, this.significantRemoteChanges);
	b.changes = a;
	b.notify = function() {
		b.source.fireEvent(new EventObject(Event.CHANGE, "edit", b, "changes", b.changes));
		b.source.fireEvent(new EventObject(Event.NOTIFY, "edit", b, "changes", b.changes))
	};
	return b
};
Session.prototype.decodeChanges = function(a) {
	this.codec.document = a.ownerDocument;
	var b = [];
	for (a = a.firstChild; null != a;) {
		var c = this.decodeChange(a);
		null != c && b.push(c);
		a = a.nextSibling
	}
	return b
};
Session.prototype.decodeChange = function(a) {
	var b = null;
	a.nodeType == Constants.NODETYPE_ELEMENT && (b = "RootChange" == a.nodeName ? (new Codec(a.ownerDocument)).decode(a) : this.codec.decode(a), null != b && (b.model = this.model, b.execute(), "ChildChange" == a.nodeName && null == b.parent && this.cellRemoved(b.child)));
	return b
};
Session.prototype.cellRemoved = function(a, b) {
	this.codec.putObject(a.getId(), a);
	for (var c = this.model.getChildCount(a), d = 0; d < c; d++) this.cellRemoved(this.model.getChildAt(a, d))
};

function UndoableEdit(a, b) {
	this.source = a;
	this.changes = [];
	this.significant = null != b ? b : !0
}
UndoableEdit.prototype.source = null;
UndoableEdit.prototype.changes = null;
UndoableEdit.prototype.significant = null;
UndoableEdit.prototype.undone = !1;
UndoableEdit.prototype.redone = !1;
UndoableEdit.prototype.isEmpty = function() {
	return 0 == this.changes.length
};
UndoableEdit.prototype.isSignificant = function() {
	return this.significant
};
UndoableEdit.prototype.add = function(a) {
	this.changes.push(a)
};
UndoableEdit.prototype.notify = function() {};
UndoableEdit.prototype.die = function() {};
UndoableEdit.prototype.undo = function() {
	if (!this.undone) {
		this.source.fireEvent(new EventObject(Event.START_EDIT));
		for (var a = this.changes.length - 1; 0 <= a; a--) {
			var b = this.changes[a];
			null != b.execute ? b.execute() : null != b.undo && b.undo();
			this.source.fireEvent(new EventObject(Event.EXECUTED, "change", b))
		}
		this.undone = !0;
		this.redone = !1;
		this.source.fireEvent(new EventObject(Event.END_EDIT))
	}
	this.notify()
};
UndoableEdit.prototype.redo = function() {
	if (!this.redone) {
		this.source.fireEvent(new EventObject(Event.START_EDIT));
		for (var a = this.changes.length, b = 0; b < a; b++) {
			var c = this.changes[b];
			null != c.execute ? c.execute() : null != c.redo && c.redo();
			this.source.fireEvent(new EventObject(Event.EXECUTED, "change", c))
		}
		this.undone = !1;
		this.redone = !0;
		this.source.fireEvent(new EventObject(Event.END_EDIT))
	}
	this.notify()
};

function UndoManager(a) {
	this.size = null != a ? a : 100;
	this.clear()
}
UndoManager.prototype = new EventSource;
UndoManager.prototype.constructor = UndoManager;
UndoManager.prototype.size = null;
UndoManager.prototype.history = null;
UndoManager.prototype.indexOfNextAdd = 0;
UndoManager.prototype.isEmpty = function() {
	return 0 == this.history.length
};
UndoManager.prototype.clear = function() {
	this.history = [];
	this.indexOfNextAdd = 0;
	this.fireEvent(new EventObject(Event.CLEAR))
};
UndoManager.prototype.canUndo = function() {
	return 0 < this.indexOfNextAdd
};
UndoManager.prototype.undo = function() {
	for (; 0 < this.indexOfNextAdd;) {
		var a = this.history[--this.indexOfNextAdd];
		a.undo();
		if (a.isSignificant()) {
			this.fireEvent(new EventObject(Event.UNDO, "edit", a));
			break
		}
	}
};
UndoManager.prototype.canRedo = function() {
	return this.indexOfNextAdd < this.history.length
};
UndoManager.prototype.redo = function() {
	for (var a = this.history.length; this.indexOfNextAdd < a;) {
		var b = this.history[this.indexOfNextAdd++];
		b.redo();
		if (b.isSignificant()) {
			this.fireEvent(new EventObject(Event.REDO, "edit", b));
			break
		}
	}
};
UndoManager.prototype.undoableEditHappened = function(a) {
	this.trim();
	0 < this.size && this.size == this.history.length && this.history.shift();
	this.history.push(a);
	this.indexOfNextAdd = this.history.length;
	this.fireEvent(new EventObject(Event.ADD, "edit", a))
};
UndoManager.prototype.trim = function() {
	if (this.history.length > this.indexOfNextAdd) for (var a = this.history.splice(this.indexOfNextAdd, this.history.length - this.indexOfNextAdd), b = 0; b < a.length; b++) a[b].die()
};
var UrlConverter = function() {};
UrlConverter.prototype.enabled = !0;
UrlConverter.prototype.baseUrl = null;
UrlConverter.prototype.baseDomain = null;
UrlConverter.prototype.updateBaseUrl = function() {
	this.baseDomain = location.protocol + "//" + location.host;
	this.baseUrl = this.baseDomain + location.pathname;
	var a = this.baseUrl.lastIndexOf("/");
	0 < a && (this.baseUrl = this.baseUrl.substring(0, a + 1))
};
UrlConverter.prototype.isEnabled = function() {
	return this.enabled
};
UrlConverter.prototype.setEnabled = function(a) {
	this.enabled = a
};
UrlConverter.prototype.getBaseUrl = function() {
	return this.baseUrl
};
UrlConverter.prototype.setBaseUrl = function(a) {
	this.baseUrl = a
};
UrlConverter.prototype.getBaseDomain = function() {
	return this.baseDomain
};
UrlConverter.prototype.setBaseDomain = function(a) {
	this.baseDomain = a
};
UrlConverter.prototype.isRelativeUrl = function(a) {
	return "//" != a.substring(0, 2) && "http://" != a.substring(0, 7) && "https://" != a.substring(0, 8) && "data:image" != a.substring(0, 10)
};
UrlConverter.prototype.convert = function(a) {
	this.isEnabled() && this.isRelativeUrl(a) && (null == this.getBaseUrl() && this.updateBaseUrl(), a = "/" == a.charAt(0) ? this.getBaseDomain() + a : this.getBaseUrl() + a);
	return a
};

function PanningManager(a) {
	this.thread = null;
	this.active = !1;
	this.dy = this.dx = this.t0y = this.t0x = this.tdy = this.tdx = 0;
	this.scrollbars = !1;
	this.scrollTop = this.scrollLeft = 0;
	this.mouseListener = {
		mouseDown: function(a, b) {},
		mouseMove: function(a, b) {},
		mouseUp: Utils.bind(this, function(a, b) {
			this.active && this.stop()
		})
	};
	a.addMouseListener(this.mouseListener);
	Event.addListener(document, "mouseup", Utils.bind(this, function() {
		this.active && this.stop()
	}));
	var b = Utils.bind(this, function() {
		this.scrollbars = Utils.hasScrollbars(a.container);
		this.scrollLeft = a.container.scrollLeft;
		this.scrollTop = a.container.scrollTop;
		return window.setInterval(Utils.bind(this, function() {
			this.tdx -= this.dx;
			this.tdy -= this.dy;
			if (this.scrollbars) {
				var b = -a.container.scrollLeft - Math.ceil(this.dx),
					d = -a.container.scrollTop - Math.ceil(this.dy);
				a.panGraph(b, d);
				a.panDx = this.scrollLeft - a.container.scrollLeft;
				a.panDy = this.scrollTop - a.container.scrollTop;
				a.fireEvent(new EventObject(Event.PAN))
			} else a.panGraph(this.getDx(), this.getDy())
		}), this.delay)
	});
	this.isActive = function() {
		return active
	};
	this.getDx = function() {
		return Math.round(this.tdx)
	};
	this.getDy = function() {
		return Math.round(this.tdy)
	};
	this.start = function() {
		this.t0x = a.view.translate.x;
		this.t0y = a.view.translate.y;
		this.active = !0
	};
	this.panTo = function(c, d, e, f) {
		this.active || this.start();
		this.scrollLeft = a.container.scrollLeft;
		this.scrollTop = a.container.scrollTop;
		f = null != f ? f : 0;
		var g = a.container;
		this.dx = c + (null != e ? e : 0) - g.scrollLeft - g.clientWidth;
		0 > this.dx && Math.abs(this.dx) < this.border ? this.dx = this.border + this.dx : this.dx = this.handleMouseOut ? Math.max(this.dx, 0) : 0;
		0 == this.dx && (this.dx = c - g.scrollLeft, this.dx = 0 < this.dx && this.dx < this.border ? this.dx - this.border : this.handleMouseOut ? Math.min(0, this.dx) : 0);
		this.dy = d + f - g.scrollTop - g.clientHeight;
		0 > this.dy && Math.abs(this.dy) < this.border ? this.dy = this.border + this.dy : this.dy = this.handleMouseOut ? Math.max(this.dy, 0) : 0;
		0 == this.dy && (this.dy = d - g.scrollTop, this.dy = 0 < this.dy && this.dy < this.border ? this.dy - this.border : this.handleMouseOut ? Math.min(0, this.dy) : 0);
		0 != this.dx || 0 != this.dy ? (this.dx *= this.damper, this.dy *= this.damper, null == this.thread && (this.thread = b())) : null != this.thread && (window.clearInterval(this.thread), this.thread = null)
	};
	this.stop = function() {
		if (this.active) if (this.active = !1, null != this.thread && (window.clearInterval(this.thread), this.thread = null), this.tdy = this.tdx = 0, this.scrollbars) a.panDx = 0, a.panDy = 0, a.fireEvent(new EventObject(Event.PAN));
		else {
			var b = a.panDx,
				d = a.panDy;
			if (0 != b || 0 != d) a.panGraph(0, 0), a.view.setTranslate(this.t0x + b / a.view.scale, this.t0y + d / a.view.scale)
		}
	};
	this.destroy = function() {
		a.removeMouseListener(this.mouseListener)
	}
}
PanningManager.prototype.damper = 1 / 6;
PanningManager.prototype.delay = 10;
PanningManager.prototype.handleMouseOut = !0;
PanningManager.prototype.border = 0;

function PopupMenu(a) {
	this.factoryMethod = a;
	null != a && this.init()
}
PopupMenu.prototype = new EventSource;
PopupMenu.prototype.constructor = PopupMenu;
PopupMenu.prototype.submenuImage = Client.imageBasePath + "/submenu.gif";
PopupMenu.prototype.zIndex = 10006;
PopupMenu.prototype.factoryMethod = null;
PopupMenu.prototype.useLeftButtonForPopup = !1;
PopupMenu.prototype.enabled = !0;
PopupMenu.prototype.itemCount = 0;
PopupMenu.prototype.autoExpand = !1;
PopupMenu.prototype.smartSeparators = !1;
PopupMenu.prototype.labels = !0;
PopupMenu.prototype.init = function() {
	this.table = document.createElement("table");
	this.table.className = "PopupMenu";
	this.tbody = document.createElement("tbody");
	this.table.appendChild(this.tbody);
	this.div = document.createElement("div");
	this.div.className = "PopupMenu";
	this.div.style.display = "inline";
	this.div.style.zIndex = this.zIndex;
	this.div.appendChild(this.table);
	Event.disableContextMenu(this.div)
};
PopupMenu.prototype.isEnabled = function() {
	return this.enabled
};
PopupMenu.prototype.setEnabled = function(a) {
	this.enabled = a
};
PopupMenu.prototype.isPopupTrigger = function(a) {
	return a.isPopupTrigger() || this.useLeftButtonForPopup && Event.isLeftMouseButton(a.getEvent())
};
PopupMenu.prototype.addItem = function(a, b, c, d, e, f) {
	d = d || this;
	this.itemCount++;
	d.willAddSeparator && (d.containsItems && this.addSeparator(d, !0), d.willAddSeparator = !1);
	d.containsItems = !0;
	var g = document.createElement("tr");
	g.className = "PopupMenuItem";
	var k = document.createElement("td");
	k.className = "PopupMenuIcon";
	null != b ? (e = document.createElement("img"), e.src = b, k.appendChild(e)) : null != e && (Client.IS_QUIRKS || 8 == document.documentMode ? (b = document.createElement("a"), b.setAttribute("href", "#")) : b = document.createElement("div"), b.className = e, k.appendChild(b));
	g.appendChild(k);
	this.labels && (k = document.createElement("td"), k.className = "PopupMenuItem" + (null != f && !f ? " Disabled" : ""), Utils.write(k, a), k.align = "left", g.appendChild(k), a = document.createElement("td"), a.className = "PopupMenuItem" + (null != f && !f ? " Disabled" : ""), a.style.paddingRight = "6px", a.style.textAlign = "right", g.appendChild(a), null == d.div && this.createSubmenu(d));
	d.tbody.appendChild(g);
	if (null == f || f) Event.addGestureListeners(g, Utils.bind(this, function(a) {
		this.eventReceiver = g;
		d.activeRow != g && d.activeRow != d && (null != d.activeRow && null != d.activeRow.div.parentNode && this.hideSubmenu(d), null != g.div && (this.showSubmenu(d, g), d.activeRow = g));
		Event.consume(a)
	}), Utils.bind(this, function(a) {
		d.activeRow != g && d.activeRow != d && (null != d.activeRow && null != d.activeRow.div.parentNode && this.hideSubmenu(d), this.autoExpand && null != g.div && (this.showSubmenu(d, g), d.activeRow = g));
		g.className = "PopupMenuItemHover"
	}), Utils.bind(this, function(a) {
		this.eventReceiver == g && (d.activeRow != g && this.hideMenu(), null != c && c(a));
		this.eventReceiver = null;
		Event.consume(a)
	})), Event.addListener(g, "mouseout", Utils.bind(this, function(a) {
		g.className = "PopupMenuItem"
	}));
	return g
};
PopupMenu.prototype.createSubmenu = function(a) {
	a.table = document.createElement("table");
	a.table.className = "PopupMenu";
	a.tbody = document.createElement("tbody");
	a.table.appendChild(a.tbody);
	a.div = document.createElement("div");
	a.div.className = "PopupMenu";
	a.div.style.position = "absolute";
	a.div.style.display = "inline";
	a.div.style.zIndex = this.zIndex;
	a.div.appendChild(a.table);
	var b = document.createElement("img");
	b.setAttribute("src", this.submenuImage);
	td = a.firstChild.nextSibling.nextSibling;
	td.appendChild(b)
};
PopupMenu.prototype.showSubmenu = function(a, b) {
	if (null != b.div) {
		b.div.style.left = a.div.offsetLeft + b.offsetLeft + b.offsetWidth - 1 + "px";
		b.div.style.top = a.div.offsetTop + b.offsetTop + "px";
		document.body.appendChild(b.div);
		var c = parseInt(b.div.offsetLeft),
			d = parseInt(b.div.offsetWidth),
			e = Utils.getDocumentScrollOrigin(document),
			f = document.documentElement;
		if (c + d > e.x + (document.body.clientWidth || f.clientWidth)) b.div.style.left = a.div.offsetLeft - d + (Client.IS_IE ? 6 : -6) + "px";
		Utils.fit(b.div)
	}
};
PopupMenu.prototype.addSeparator = function(a, b) {
	a = a || this;
	if (this.smartSeparators && !b) a.willAddSeparator = !0;
	else if (null != a.tbody) {
		a.willAddSeparator = !1;
		var c = document.createElement("tr"),
			d = document.createElement("td");
		d.className = "PopupMenuIcon";
		d.style.padding = "0 0 0 0px";
		c.appendChild(d);
		d = document.createElement("td");
		d.style.padding = "0 0 0 0px";
		d.setAttribute("colSpan", "2");
		var e = document.createElement("hr");
		e.setAttribute("size", "1");
		d.appendChild(e);
		c.appendChild(d);
		a.tbody.appendChild(c)
	}
};
PopupMenu.prototype.popup = function(a, b, c, d) {
	if (null != this.div && null != this.tbody && null != this.factoryMethod) {
		this.div.style.left = a + "px";
		for (this.div.style.top = b + "px"; null != this.tbody.firstChild;) Event.release(this.tbody.firstChild), this.tbody.removeChild(this.tbody.firstChild);
		this.itemCount = 0;
		this.factoryMethod(this, c, d);
		0 < this.itemCount && (this.showMenu(), this.fireEvent(new EventObject(Event.SHOW)))
	}
};
PopupMenu.prototype.isMenuShowing = function() {
	return null != this.div && this.div.parentNode == document.body
};
PopupMenu.prototype.showMenu = function() {
	9 <= document.documentMode && (this.div.style.filter = "none");
	document.body.appendChild(this.div);
	Utils.fit(this.div)
};
PopupMenu.prototype.hideMenu = function() {
	null != this.div && (null != this.div.parentNode && this.div.parentNode.removeChild(this.div), this.hideSubmenu(this), this.containsItems = !1)
};
PopupMenu.prototype.hideSubmenu = function(a) {
	null != a.activeRow && (this.hideSubmenu(a.activeRow), null != a.activeRow.div.parentNode && a.activeRow.div.parentNode.removeChild(a.activeRow.div), a.activeRow = null)
};
PopupMenu.prototype.destroy = function() {
	null != this.div && (Event.release(this.div), null != this.div.parentNode && this.div.parentNode.removeChild(this.div), this.div = null)
};

function AutoSaveManager(a) {
	this.changeHandler = Utils.bind(this, function(a, c) {
		this.isEnabled() && this.graphModelChanged(c.getProperty("edit").changes)
	});
	this.setGraph(a)
}
AutoSaveManager.prototype = new EventSource;
AutoSaveManager.prototype.constructor = AutoSaveManager;
AutoSaveManager.prototype.graph = null;
AutoSaveManager.prototype.autoSaveDelay = 10;
AutoSaveManager.prototype.autoSaveThrottle = 2;
AutoSaveManager.prototype.autoSaveThreshold = 5;
AutoSaveManager.prototype.ignoredChanges = 0;
AutoSaveManager.prototype.lastSnapshot = 0;
AutoSaveManager.prototype.enabled = !0;
AutoSaveManager.prototype.changeHandler = null;
AutoSaveManager.prototype.isEnabled = function() {
	return this.enabled
};
AutoSaveManager.prototype.setEnabled = function(a) {
	this.enabled = a
};
AutoSaveManager.prototype.setGraph = function(a) {
	null != this.graph && this.graph.getModel().removeListener(this.changeHandler);
	this.graph = a;
	null != this.graph && this.graph.getModel().addListener(Event.CHANGE, this.changeHandler)
};
AutoSaveManager.prototype.save = function() {};
AutoSaveManager.prototype.graphModelChanged = function(a) {
	a = ((new Date).getTime() - this.lastSnapshot) / 1E3;
	a > this.autoSaveDelay || this.ignoredChanges >= this.autoSaveThreshold && a > this.autoSaveThrottle ? (this.save(), this.reset()) : this.ignoredChanges++
};
AutoSaveManager.prototype.reset = function() {
	this.lastSnapshot = (new Date).getTime();
	this.ignoredChanges = 0
};
AutoSaveManager.prototype.destroy = function() {
	this.setGraph(null)
};

function Animation(a) {
	this.delay = null != a ? a : 20
}
Animation.prototype = new EventSource;
Animation.prototype.constructor = Animation;
Animation.prototype.delay = null;
Animation.prototype.thread = null;
Animation.prototype.isRunning = function() {
	return null != this.thread
};
Animation.prototype.startAnimation = function() {
	null == this.thread && (this.thread = window.setInterval(Utils.bind(this, this.updateAnimation), this.delay))
};
Animation.prototype.updateAnimation = function() {
	this.fireEvent(new EventObject(Event.EXECUTE))
};
Animation.prototype.stopAnimation = function() {
	null != this.thread && (window.clearInterval(this.thread), this.thread = null, this.fireEvent(new EventObject(Event.DONE)))
};

function Morphing(a, b, c, d) {
	Animation.call(this, d);
	this.graph = a;
	this.steps = null != b ? b : 6;
	this.ease = null != c ? c : 1.5
}
Morphing.prototype = new Animation;
Morphing.prototype.constructor = Morphing;
Morphing.prototype.graph = null;
Morphing.prototype.steps = null;
Morphing.prototype.step = 0;
Morphing.prototype.ease = null;
Morphing.prototype.cells = null;
Morphing.prototype.updateAnimation = function() {
	var a = new CellStatePreview(this.graph);
	if (null != this.cells) for (var b = 0; b < this.cells.length; b++) this.animateCell(this.cells[b], a, !1);
	else this.animateCell(this.graph.getModel().getRoot(), a, !0);
	this.show(a);
	(a.isEmpty() || this.step++ >= this.steps) && this.stopAnimation()
};
Morphing.prototype.show = function(a) {
	a.show()
};
Morphing.prototype.animateCell = function(a, b, c) {
	var d = this.graph.getView().getState(a),
		e = null;
	if (null != d && (e = this.getDelta(d), this.graph.getModel().isVertex(a) && (0 != e.x || 0 != e.y))) {
		var f = this.graph.view.getTranslate(),
			g = this.graph.view.getScale();
		e.x += f.x * g;
		e.y += f.y * g;
		b.moveState(d, -e.x / this.ease, -e.y / this.ease)
	}
	if (c && !this.stopRecursion(d, e)) {
		d = this.graph.getModel().getChildCount(a);
		for (e = 0; e < d; e++) this.animateCell(this.graph.getModel().getChildAt(a, e), b, c)
	}
};
Morphing.prototype.stopRecursion = function(a, b) {
	return null != b && (0 != b.x || 0 != b.y)
};
Morphing.prototype.getDelta = function(a) {
	var b = this.getOriginForCell(a.cell),
		c = this.graph.getView().getTranslate(),
		d = this.graph.getView().getScale();
	return new Point((b.x - (a.x / d - c.x)) * d, (b.y - (a.y / d - c.y)) * d)
};
Morphing.prototype.getOriginForCell = function(a) {
	var b = null;
	if (null != a) {
		var c = this.graph.getModel().getParent(a);
		a = this.graph.getCellGeometry(a);
		b = this.getOriginForCell(c);
		null != a && (a.relative ? (c = this.graph.getCellGeometry(c), null != c && (b.x += a.x * c.width, b.y += a.y * c.height)) : (b.x += a.x, b.y += a.y))
	}
	null == b && (b = this.graph.view.getTranslate(), b = new Point(-b.x, -b.y));
	return b
};

function ImageBundle(a) {
	this.images = [];
	this.alt = null != a ? a : !1
}
ImageBundle.prototype.images = null;
ImageBundle.prototype.images = null;
ImageBundle.prototype.putImage = function(a, b, c) {
	this.images[a] = {
		value: b,
		fallback: c
	}
};
ImageBundle.prototype.getImage = function(a) {
	var b = null;
	null != a && (a = this.images[a], null != a && (b = this.alt ? a.fallback : a.value));
	return b
};

function ImageExport() {}
ImageExport.prototype.includeOverlays = !1;
ImageExport.prototype.drawState = function(a, b) {
	null != a && (this.visitStatesRecursive(a, b, Utils.bind(this, function() {
		this.drawCellState.apply(this, arguments)
	})), this.includeOverlays && this.visitStatesRecursive(a, b, Utils.bind(this, function() {
		this.drawOverlays.apply(this, arguments)
	})))
};
ImageExport.prototype.visitStatesRecursive = function(a, b, c) {
	if (null != a) {
		c(a, b);
		for (var d = a.view.graph, e = d.model.getChildCount(a.cell), f = 0; f < e; f++) {
			var g = d.view.getState(d.model.getChildAt(a.cell, f));
			this.visitStatesRecursive(g, b, c)
		}
	}
};
ImageExport.prototype.getLinkForCellState = function(a, b) {
	return null
};
ImageExport.prototype.drawCellState = function(a, b) {
	var c = this.getLinkForCellState(a, b);
	null != c && b.setLink(c);
	a.shape instanceof Shape && (b.save(), a.shape.paint(b), b.restore());
	null != a.text && (b.save(), a.text.paint(b), b.restore());
	null != c && b.setLink(null)
};
ImageExport.prototype.drawOverlays = function(a, b) {
	null != a.overlays && a.overlays.visit(function(a, d) {
		d instanceof Shape && d.paint(b)
	})
};

function AbstractCanvas2D() {
	this.converter = this.createUrlConverter();
	this.reset()
}
AbstractCanvas2D.prototype.state = null;
AbstractCanvas2D.prototype.states = null;
AbstractCanvas2D.prototype.path = null;
AbstractCanvas2D.prototype.rotateHtml = !0;
AbstractCanvas2D.prototype.lastX = 0;
AbstractCanvas2D.prototype.lastY = 0;
AbstractCanvas2D.prototype.moveOp = "M";
AbstractCanvas2D.prototype.lineOp = "L";
AbstractCanvas2D.prototype.quadOp = "Q";
AbstractCanvas2D.prototype.curveOp = "C";
AbstractCanvas2D.prototype.closeOp = "Z";
AbstractCanvas2D.prototype.pointerEvents = !1;
AbstractCanvas2D.prototype.createUrlConverter = function() {
	return new UrlConverter
};
AbstractCanvas2D.prototype.reset = function() {
	this.state = this.createState();
	this.states = []
};
AbstractCanvas2D.prototype.createState = function() {
	return {
		dx: 0,
		dy: 0,
		scale: 1,
		alpha: 1,
		fillColor: null,
		fillAlpha: 1,
		gradientColor: null,
		gradientAlpha: 1,
		gradientDirection: null,
		strokeColor: null,
		strokeWidth: 1,
		dashed: !1,
		dashPattern: "3 3",
		lineCap: "flat",
		lineJoin: "miter",
		miterLimit: 10,
		fontColor: "#000000",
		fontBackgroundColor: null,
		fontBorderColor: null,
		fontSize: Constants.DEFAULT_FONTSIZE,
		fontFamily: Constants.DEFAULT_FONTFAMILY,
		fontStyle: 0,
		shadow: !1,
		shadowColor: Constants.SHADOWCOLOR,
		shadowAlpha: Constants.SHADOW_OPACITY,
		shadowDx: Constants.SHADOW_OFFSET_X,
		shadowDy: Constants.SHADOW_OFFSET_Y,
		rotation: 0,
		rotationCx: 0,
		rotationCy: 0
	}
};
AbstractCanvas2D.prototype.format = function(a) {
	return Math.round(parseFloat(a))
};
AbstractCanvas2D.prototype.addOp = function() {
	if (null != this.path && (this.path.push(arguments[0]), 2 < arguments.length)) for (var a = this.state, b = 2; b < arguments.length; b += 2) this.lastX = arguments[b - 1], this.lastY = arguments[b], this.path.push(this.format((this.lastX + a.dx) * a.scale)), this.path.push(this.format((this.lastY + a.dy) * a.scale))
};
AbstractCanvas2D.prototype.rotatePoint = function(a, b, c, d, e) {
	c *= Math.PI / 180;
	return Utils.getRotatedPoint(new Point(a, b), Math.cos(c), Math.sin(c), new Point(d, e))
};
AbstractCanvas2D.prototype.save = function() {
	this.states.push(this.state);
	this.state = Utils.clone(this.state)
};
AbstractCanvas2D.prototype.restore = function() {
	this.state = this.states.pop()
};
AbstractCanvas2D.prototype.setLink = function(a) {};
AbstractCanvas2D.prototype.scale = function(a) {
	this.state.scale *= a;
	this.state.strokeWidth *= a
};
AbstractCanvas2D.prototype.translate = function(a, b) {
	this.state.dx += a;
	this.state.dy += b
};
AbstractCanvas2D.prototype.setAlpha = function(a) {
	this.state.alpha = a
};
AbstractCanvas2D.prototype.setFillColor = function(a) {
	a == Constants.NONE && (a = null);
	this.state.fillColor = a;
	this.state.gradientColor = null
};
AbstractCanvas2D.prototype.setGradient = function(a, b, c, d, e, f, g, k, l) {
	c = this.state;
	c.fillColor = a;
	c.fillAlpha = null != k ? k : 1;
	c.gradientColor = b;
	c.gradientAlpha = null != l ? l : 1;
	c.gradientDirection = g
};
AbstractCanvas2D.prototype.setStrokeColor = function(a) {
	a == Constants.NONE && (a = null);
	this.state.strokeColor = a
};
AbstractCanvas2D.prototype.setStrokeWidth = function(a) {
	this.state.strokeWidth = a
};
AbstractCanvas2D.prototype.setDashed = function(a) {
	this.state.dashed = a
};
AbstractCanvas2D.prototype.setDashPattern = function(a) {
	this.state.dashPattern = a
};
AbstractCanvas2D.prototype.setLineCap = function(a) {
	this.state.lineCap = a
};
AbstractCanvas2D.prototype.setLineJoin = function(a) {
	this.state.lineJoin = a
};
AbstractCanvas2D.prototype.setMiterLimit = function(a) {
	this.state.miterLimit = a
};
AbstractCanvas2D.prototype.setFontColor = function(a) {
	a == Constants.NONE && (a = null);
	this.state.fontColor = a
};
AbstractCanvas2D.prototype.setFontBackgroundColor = function(a) {
	a == Constants.NONE && (a = null);
	this.state.fontBackgroundColor = a
};
AbstractCanvas2D.prototype.setFontBorderColor = function(a) {
	a == Constants.NONE && (a = null);
	this.state.fontBorderColor = a
};
AbstractCanvas2D.prototype.setFontSize = function(a) {
	this.state.fontSize = a
};
AbstractCanvas2D.prototype.setFontFamily = function(a) {
	this.state.fontFamily = a
};
AbstractCanvas2D.prototype.setFontStyle = function(a) {
	null == a && (a = 0);
	this.state.fontStyle = a
};
AbstractCanvas2D.prototype.setShadow = function(a) {
	this.state.shadow = a
};
AbstractCanvas2D.prototype.setShadowColor = function(a) {
	a == Constants.NONE && (a = null);
	this.state.shadowColor = a
};
AbstractCanvas2D.prototype.setShadowAlpha = function(a) {
	this.state.shadowAlpha = a
};
AbstractCanvas2D.prototype.setShadowOffset = function(a, b) {
	this.state.shadowDx = a;
	this.state.shadowDy = b
};
AbstractCanvas2D.prototype.begin = function() {
	this.lastY = this.lastX = 0;
	this.path = []
};
AbstractCanvas2D.prototype.moveTo = function(a, b) {
	this.addOp(this.moveOp, a, b)
};
AbstractCanvas2D.prototype.lineTo = function(a, b) {
	this.addOp(this.lineOp, a, b)
};
AbstractCanvas2D.prototype.quadTo = function(a, b, c, d) {
	this.addOp(this.quadOp, a, b, c, d)
};
AbstractCanvas2D.prototype.curveTo = function(a, b, c, d, e, f) {
	this.addOp(this.curveOp, a, b, c, d, e, f)
};
AbstractCanvas2D.prototype.arcTo = function(a, b, c, d, e, f, g) {
	a = Utils.arcToCurves(this.lastX, this.lastY, a, b, c, d, e, f, g);
	if (null != a) for (b = 0; b < a.length; b += 6) this.curveTo(a[b], a[b + 1], a[b + 2], a[b + 3], a[b + 4], a[b + 5])
};
AbstractCanvas2D.prototype.close = function(a, b, c, d, e, f) {
	this.addOp(this.closeOp)
};
AbstractCanvas2D.prototype.end = function() {};

function XmlCanvas2D(a) {
	AbstractCanvas2D.call(this);
	this.root = a;
	this.writeDefaults()
}
Utils.extend(XmlCanvas2D, AbstractCanvas2D);
XmlCanvas2D.prototype.textEnabled = !0;
XmlCanvas2D.prototype.compressed = !0;
XmlCanvas2D.prototype.writeDefaults = function() {
	var a;
	a = this.createElement("fontfamily");
	a.setAttribute("family", Constants.DEFAULT_FONTFAMILY);
	this.root.appendChild(a);
	a = this.createElement("fontsize");
	a.setAttribute("size", Constants.DEFAULT_FONTSIZE);
	this.root.appendChild(a);
	a = this.createElement("shadowcolor");
	a.setAttribute("color", Constants.SHADOWCOLOR);
	this.root.appendChild(a);
	a = this.createElement("shadowalpha");
	a.setAttribute("alpha", Constants.SHADOW_OPACITY);
	this.root.appendChild(a);
	a = this.createElement("shadowoffset");
	a.setAttribute("dx", Constants.SHADOW_OFFSET_X);
	a.setAttribute("dy", Constants.SHADOW_OFFSET_Y);
	this.root.appendChild(a)
};
XmlCanvas2D.prototype.format = function(a) {
	return parseFloat(parseFloat(a).toFixed(2))
};
XmlCanvas2D.prototype.createElement = function(a) {
	return this.root.ownerDocument.createElement(a)
};
XmlCanvas2D.prototype.save = function() {
	this.compressed && AbstractCanvas2D.prototype.save.apply(this, arguments);
	this.root.appendChild(this.createElement("save"))
};
XmlCanvas2D.prototype.restore = function() {
	this.compressed && AbstractCanvas2D.prototype.restore.apply(this, arguments);
	this.root.appendChild(this.createElement("restore"))
};
XmlCanvas2D.prototype.scale = function(a) {
	if (this.compressed) {
		if (this.state.scale == a) return;
		AbstractCanvas2D.prototype.setAlpha.apply(this, arguments)
	}
	var b = this.createElement("scale");
	b.setAttribute("scale", a);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.translate = function(a, b) {
	var c = this.createElement("translate");
	c.setAttribute("dx", this.format(a));
	c.setAttribute("dy", this.format(b));
	this.root.appendChild(c)
};
XmlCanvas2D.prototype.rotate = function(a, b, c, d, e) {
	var f = this.createElement("rotate");
	if (0 != a || b || c) f.setAttribute("theta", this.format(a)), f.setAttribute("flipH", b ? "1" : "0"), f.setAttribute("flipV", c ? "1" : "0"), f.setAttribute("cx", this.format(d)), f.setAttribute("cy", this.format(e)), this.root.appendChild(f)
};
XmlCanvas2D.prototype.setAlpha = function(a) {
	if (this.compressed) {
		if (this.state.alpha == a) return;
		AbstractCanvas2D.prototype.setAlpha.apply(this, arguments)
	}
	var b = this.createElement("alpha");
	b.setAttribute("alpha", this.format(a));
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setFillColor = function(a) {
	a == Constants.NONE && (a = null);
	if (this.compressed) {
		if (this.state.fillColor == a) return;
		AbstractCanvas2D.prototype.setFillColor.apply(this, arguments)
	}
	var b = this.createElement("fillcolor");
	b.setAttribute("color", null != a ? a : Constants.NONE);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setGradient = function(a, b, c, d, e, f, g, k, l) {
	if (null != a && null != b) {
		AbstractCanvas2D.prototype.setGradient.apply(this, arguments);
		var m = this.createElement("gradient");
		m.setAttribute("c1", a);
		m.setAttribute("c2", b);
		m.setAttribute("x", this.format(c));
		m.setAttribute("y", this.format(d));
		m.setAttribute("w", this.format(e));
		m.setAttribute("h", this.format(f));
		null != g && m.setAttribute("direction", g);
		null != k && m.setAttribute("alpha1", k);
		null != l && m.setAttribute("alpha2", l);
		this.root.appendChild(m)
	}
};
XmlCanvas2D.prototype.setStrokeColor = function(a) {
	a == Constants.NONE && (a = null);
	if (this.compressed) {
		if (this.state.strokeColor == a) return;
		AbstractCanvas2D.prototype.setStrokeColor.apply(this, arguments)
	}
	var b = this.createElement("strokecolor");
	b.setAttribute("color", null != a ? a : Constants.NONE);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setStrokeWidth = function(a) {
	if (this.compressed) {
		if (this.state.strokeWidth == a) return;
		AbstractCanvas2D.prototype.setStrokeWidth.apply(this, arguments)
	}
	var b = this.createElement("strokewidth");
	b.setAttribute("width", this.format(a));
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setDashed = function(a) {
	if (this.compressed) {
		if (this.state.dashed == a) return;
		AbstractCanvas2D.prototype.setDashed.apply(this, arguments)
	}
	var b = this.createElement("dashed");
	b.setAttribute("dashed", a ? "1" : "0");
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setDashPattern = function(a) {
	if (this.compressed) {
		if (this.state.dashPattern == a) return;
		AbstractCanvas2D.prototype.setDashPattern.apply(this, arguments)
	}
	var b = this.createElement("dashpattern");
	b.setAttribute("pattern", a);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setLineCap = function(a) {
	if (this.compressed) {
		if (this.state.lineCap == a) return;
		AbstractCanvas2D.prototype.setLineCap.apply(this, arguments)
	}
	var b = this.createElement("linecap");
	b.setAttribute("cap", a);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setLineJoin = function(a) {
	if (this.compressed) {
		if (this.state.lineJoin == a) return;
		AbstractCanvas2D.prototype.setLineJoin.apply(this, arguments)
	}
	var b = this.createElement("linejoin");
	b.setAttribute("join", a);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setMiterLimit = function(a) {
	if (this.compressed) {
		if (this.state.miterLimit == a) return;
		AbstractCanvas2D.prototype.setMiterLimit.apply(this, arguments)
	}
	var b = this.createElement("miterlimit");
	b.setAttribute("limit", a);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setFontColor = function(a) {
	if (this.textEnabled) {
		a == Constants.NONE && (a = null);
		if (this.compressed) {
			if (this.state.fontColor == a) return;
			AbstractCanvas2D.prototype.setFontColor.apply(this, arguments)
		}
		var b = this.createElement("fontcolor");
		b.setAttribute("color", null != a ? a : Constants.NONE);
		this.root.appendChild(b)
	}
};
XmlCanvas2D.prototype.setFontBackgroundColor = function(a) {
	if (this.textEnabled) {
		a == Constants.NONE && (a = null);
		if (this.compressed) {
			if (this.state.fontBackgroundColor == a) return;
			AbstractCanvas2D.prototype.setFontBackgroundColor.apply(this, arguments)
		}
		var b = this.createElement("fontbackgroundcolor");
		b.setAttribute("color", null != a ? a : Constants.NONE);
		this.root.appendChild(b)
	}
};
XmlCanvas2D.prototype.setFontBorderColor = function(a) {
	if (this.textEnabled) {
		a == Constants.NONE && (a = null);
		if (this.compressed) {
			if (this.state.fontBorderColor == a) return;
			AbstractCanvas2D.prototype.setFontBorderColor.apply(this, arguments)
		}
		var b = this.createElement("fontbordercolor");
		b.setAttribute("color", null != a ? a : Constants.NONE);
		this.root.appendChild(b)
	}
};
XmlCanvas2D.prototype.setFontSize = function(a) {
	if (this.textEnabled) {
		if (this.compressed) {
			if (this.state.fontSize == a) return;
			AbstractCanvas2D.prototype.setFontSize.apply(this, arguments)
		}
		var b = this.createElement("fontsize");
		b.setAttribute("size", a);
		this.root.appendChild(b)
	}
};
XmlCanvas2D.prototype.setFontFamily = function(a) {
	if (this.textEnabled) {
		if (this.compressed) {
			if (this.state.fontFamily == a) return;
			AbstractCanvas2D.prototype.setFontFamily.apply(this, arguments)
		}
		var b = this.createElement("fontfamily");
		b.setAttribute("family", a);
		this.root.appendChild(b)
	}
};
XmlCanvas2D.prototype.setFontStyle = function(a) {
	if (this.textEnabled) {
		null == a && (a = 0);
		if (this.compressed) {
			if (this.state.fontStyle == a) return;
			AbstractCanvas2D.prototype.setFontStyle.apply(this, arguments)
		}
		var b = this.createElement("fontstyle");
		b.setAttribute("style", a);
		this.root.appendChild(b)
	}
};
XmlCanvas2D.prototype.setShadow = function(a) {
	if (this.compressed) {
		if (this.state.shadow == a) return;
		AbstractCanvas2D.prototype.setShadow.apply(this, arguments)
	}
	var b = this.createElement("shadow");
	b.setAttribute("enabled", a ? "1" : "0");
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setShadowColor = function(a) {
	if (this.compressed) {
		a == Constants.NONE && (a = null);
		if (this.state.shadowColor == a) return;
		AbstractCanvas2D.prototype.setShadowColor.apply(this, arguments)
	}
	var b = this.createElement("shadowcolor");
	b.setAttribute("color", null != a ? a : Constants.NONE);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setShadowAlpha = function(a) {
	if (this.compressed) {
		if (this.state.shadowAlpha == a) return;
		AbstractCanvas2D.prototype.setShadowAlpha.apply(this, arguments)
	}
	var b = this.createElement("shadowalpha");
	b.setAttribute("alpha", a);
	this.root.appendChild(b)
};
XmlCanvas2D.prototype.setShadowOffset = function(a, b) {
	if (this.compressed) {
		if (this.state.shadowDx == a && this.state.shadowDy == b) return;
		AbstractCanvas2D.prototype.setShadowOffset.apply(this, arguments)
	}
	var c = this.createElement("shadowoffset");
	c.setAttribute("dx", a);
	c.setAttribute("dy", b);
	this.root.appendChild(c)
};
XmlCanvas2D.prototype.rect = function(a, b, c, d) {
	var e = this.createElement("rect");
	e.setAttribute("x", this.format(a));
	e.setAttribute("y", this.format(b));
	e.setAttribute("w", this.format(c));
	e.setAttribute("h", this.format(d));
	this.root.appendChild(e)
};
XmlCanvas2D.prototype.roundrect = function(a, b, c, d, e, f) {
	var g = this.createElement("roundrect");
	g.setAttribute("x", this.format(a));
	g.setAttribute("y", this.format(b));
	g.setAttribute("w", this.format(c));
	g.setAttribute("h", this.format(d));
	g.setAttribute("dx", this.format(e));
	g.setAttribute("dy", this.format(f));
	this.root.appendChild(g)
};
XmlCanvas2D.prototype.ellipse = function(a, b, c, d) {
	var e = this.createElement("ellipse");
	e.setAttribute("x", this.format(a));
	e.setAttribute("y", this.format(b));
	e.setAttribute("w", this.format(c));
	e.setAttribute("h", this.format(d));
	this.root.appendChild(e)
};
XmlCanvas2D.prototype.image = function(a, b, c, d, e, f, g, k) {
	e = this.converter.convert(e);
	var l = this.createElement("image");
	l.setAttribute("x", this.format(a));
	l.setAttribute("y", this.format(b));
	l.setAttribute("w", this.format(c));
	l.setAttribute("h", this.format(d));
	l.setAttribute("src", e);
	l.setAttribute("aspect", f ? "1" : "0");
	l.setAttribute("flipH", g ? "1" : "0");
	l.setAttribute("flipV", k ? "1" : "0");
	this.root.appendChild(l)
};
XmlCanvas2D.prototype.begin = function() {
	this.root.appendChild(this.createElement("begin"));
	this.lastY = this.lastX = 0
};
XmlCanvas2D.prototype.moveTo = function(a, b) {
	var c = this.createElement("move");
	c.setAttribute("x", this.format(a));
	c.setAttribute("y", this.format(b));
	this.root.appendChild(c);
	this.lastX = a;
	this.lastY = b
};
XmlCanvas2D.prototype.lineTo = function(a, b) {
	var c = this.createElement("line");
	c.setAttribute("x", this.format(a));
	c.setAttribute("y", this.format(b));
	this.root.appendChild(c);
	this.lastX = a;
	this.lastY = b
};
XmlCanvas2D.prototype.quadTo = function(a, b, c, d) {
	var e = this.createElement("quad");
	e.setAttribute("x1", this.format(a));
	e.setAttribute("y1", this.format(b));
	e.setAttribute("x2", this.format(c));
	e.setAttribute("y2", this.format(d));
	this.root.appendChild(e);
	this.lastX = c;
	this.lastY = d
};
XmlCanvas2D.prototype.curveTo = function(a, b, c, d, e, f) {
	var g = this.createElement("curve");
	g.setAttribute("x1", this.format(a));
	g.setAttribute("y1", this.format(b));
	g.setAttribute("x2", this.format(c));
	g.setAttribute("y2", this.format(d));
	g.setAttribute("x3", this.format(e));
	g.setAttribute("y3", this.format(f));
	this.root.appendChild(g);
	this.lastX = e;
	this.lastY = f
};
XmlCanvas2D.prototype.close = function() {
	this.root.appendChild(this.createElement("close"))
};
XmlCanvas2D.prototype.text = function(a, b, c, d, e, f, g, k, l, m, n, p) {
	if (this.textEnabled && null != e) {
		Utils.isNode(e) && (e = Utils.getOuterHtml(e));
		var q = this.createElement("text");
		q.setAttribute("x", this.format(a));
		q.setAttribute("y", this.format(b));
		q.setAttribute("w", this.format(c));
		q.setAttribute("h", this.format(d));
		q.setAttribute("str", e);
		null != f && q.setAttribute("align", f);
		null != g && q.setAttribute("valign", g);
		q.setAttribute("wrap", k ? "1" : "0");
		null == l && (l = "");
		q.setAttribute("format", l);
		null != m && q.setAttribute("overflow", m);
		null != n && q.setAttribute("clip", n ? "1" : "0");
		null != p && q.setAttribute("rotation", p);
		this.root.appendChild(q)
	}
};
XmlCanvas2D.prototype.stroke = function() {
	this.root.appendChild(this.createElement("stroke"))
};
XmlCanvas2D.prototype.fill = function() {
	this.root.appendChild(this.createElement("fill"))
};
XmlCanvas2D.prototype.fillAndStroke = function() {
	this.root.appendChild(this.createElement("fillstroke"))
};

function SvgCanvas2D(a, b) {
	AbstractCanvas2D.call(this);
	this.root = a;
	this.gradients = [];
	this.defs = null;
	this.styleEnabled = null != b ? b : !1;
	var c = null;
	if (a.ownerDocument != document) for (c = a; null != c && "svg" != c.nodeName;) c = c.parentNode;
	null != c && (0 < c.getElementsByTagName("defs").length && (this.defs = c.getElementsByTagName("defs")[0]), null == this.defs && (this.defs = this.createElement("defs"), null != c.firstChild ? c.insertBefore(this.defs, c.firstChild) : c.appendChild(this.defs)), this.styleEnabled && this.defs.appendChild(this.createStyle()))
}
Utils.extend(SvgCanvas2D, AbstractCanvas2D);
SvgCanvas2D.prototype.node = null;
SvgCanvas2D.prototype.matchHtmlAlignment = !0;
SvgCanvas2D.prototype.textEnabled = !0;
SvgCanvas2D.prototype.foEnabled = !0;
SvgCanvas2D.prototype.foAltText = "[Object]";
SvgCanvas2D.prototype.strokeTolerance = 0;
SvgCanvas2D.prototype.refCount = 0;
SvgCanvas2D.prototype.blockImagePointerEvents = !1;
SvgCanvas2D.prototype.lineHeightCorrection = 1.05;
SvgCanvas2D.prototype.pointerEventsValue = "all";
SvgCanvas2D.prototype.fontMetricsPadding = 10;
SvgCanvas2D.prototype.getBaseUrl = function() {
	var a = window.location.href,
		b = a.lastIndexOf("#");
	0 < b && (a = a.substring(0, b));
	return a
};
SvgCanvas2D.prototype.reset = function() {
	AbstractCanvas2D.prototype.reset.apply(this, arguments);
	this.gradients = []
};
SvgCanvas2D.prototype.createStyle = function(a) {
	a = this.createElement("style");
	a.setAttribute("type", "text/css");
	Utils.write(a, "svg{font-family:" + Constants.DEFAULT_FONTFAMILY + ";font-size:" + Constants.DEFAULT_FONTSIZE + ";fill:none;stroke-miterlimit:10}");
	return a
};
SvgCanvas2D.prototype.createElement = function(a, b) {
	if (null != this.root.ownerDocument.createElementNS) return this.root.ownerDocument.createElementNS(b || Constants.NS_SVG, a);
	var c = this.root.ownerDocument.createElement(a);
	null != b && c.setAttribute("xmlns", b);
	return c
};
SvgCanvas2D.prototype.createAlternateContent = function(a, b, c, d, e, f, g, k, l, m, n, p, q) {
	return null != this.foAltText ? (a = this.state, b = this.createElement("text"), b.setAttribute("x", Math.round(d / 2)), b.setAttribute("y", Math.round((e + a.fontSize) / 2)), b.setAttribute("fill", a.fontColor || "black"), b.setAttribute("text-anchor", "middle"), b.setAttribute("font-size", Math.round(a.fontSize) + "px"), b.setAttribute("font-family", a.fontFamily), (a.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD && b.setAttribute("font-weight", "bold"), (a.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC && b.setAttribute("font-style", "italic"), (a.fontStyle & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE && b.setAttribute("text-decoration", "underline"), Utils.write(b, this.foAltText), b) : null
};
SvgCanvas2D.prototype.createGradientId = function(a, b, c, d, e) {
	"#" == a.charAt(0) && (a = a.substring(1));
	"#" == b.charAt(0) && (b = b.substring(1));
	a = a.toLowerCase() + "-" + c;
	b = b.toLowerCase() + "-" + d;
	c = null;
	null == e || e == Constants.DIRECTION_SOUTH ? c = "s" : e == Constants.DIRECTION_EAST ? c = "e" : (d = a, a = b, b = d, e == Constants.DIRECTION_NORTH ? c = "s" : e == Constants.DIRECTION_WEST && (c = "e"));
	return "gradient-" + a + "-" + b + "-" + c
};
SvgCanvas2D.prototype.getSvgGradient = function(a, b, c, d, e) {
	var f = this.createGradientId(a, b, c, d, e),
		g = this.gradients[f];
	if (null == g) {
		var k = this.root.ownerSVGElement,
			l = 0,
			m = f + "-" + l;
		if (null != k) for (g = k.ownerDocument.getElementById(m); null != g && g.ownerSVGElement != k;) m = f + "-" + l++, g = k.ownerDocument.getElementById(m);
		else m = "id" + ++this.refCount;
		null == g && (g = this.createSvgGradient(a, b, c, d, e), g.setAttribute("id", m), null != this.defs ? this.defs.appendChild(g) : k.appendChild(g));
		this.gradients[f] = g
	}
	return g.getAttribute("id")
};
SvgCanvas2D.prototype.createSvgGradient = function(a, b, c, d, e) {
	var f = this.createElement("linearGradient");
	f.setAttribute("x1", "0%");
	f.setAttribute("y1", "0%");
	f.setAttribute("x2", "0%");
	f.setAttribute("y2", "0%");
	null == e || e == Constants.DIRECTION_SOUTH ? f.setAttribute("y2", "100%") : e == Constants.DIRECTION_EAST ? f.setAttribute("x2", "100%") : e == Constants.DIRECTION_NORTH ? f.setAttribute("y1", "100%") : e == Constants.DIRECTION_WEST && f.setAttribute("x1", "100%");
	c = 1 > c ? ";stop-opacity:" + c : "";
	e = this.createElement("stop");
	e.setAttribute("offset", "0%");
	e.setAttribute("style", "stop-color:" + a + c);
	f.appendChild(e);
	c = 1 > d ? ";stop-opacity:" + d : "";
	e = this.createElement("stop");
	e.setAttribute("offset", "100%");
	e.setAttribute("style", "stop-color:" + b + c);
	f.appendChild(e);
	return f
};
SvgCanvas2D.prototype.addNode = function(a, b) {
	var c = this.node,
		d = this.state;
	if (null != c) {
		if ("path" == c.nodeName) if (null != this.path && 0 < this.path.length) c.setAttribute("d", this.path.join(" "));
		else return;
		a && null != d.fillColor ? this.updateFill() : this.styleEnabled || ("ellipse" == c.nodeName && Client.IS_FF ? c.setAttribute("fill", "transparent") : c.setAttribute("fill", "none"), a = !1);
		b && null != d.strokeColor ? this.updateStroke() : this.styleEnabled || c.setAttribute("stroke", "none");
		null != d.transform && 0 < d.transform.length && c.setAttribute("transform", d.transform);
		d.shadow && this.root.appendChild(this.createShadow(c));
		0 < this.strokeTolerance && !a && this.root.appendChild(this.createTolerance(c));
		this.pointerEvents && ("path" != c.nodeName || this.path[this.path.length - 1] == this.closeOp) ? c.setAttribute("pointer-events", this.pointerEventsValue) : !this.pointerEvents && null == this.originalRoot && c.setAttribute("pointer-events", "none");
		this.root.appendChild(c)
	}
};
SvgCanvas2D.prototype.updateFill = function() {
	var a = this.state;
	1 > a.alpha && this.node.setAttribute("fill-opacity", a.alpha);
	null != a.fillColor && (null != a.gradientColor ? (a = this.getSvgGradient(a.fillColor, a.gradientColor, a.fillAlpha, a.gradientAlpha, a.gradientDirection), this.root.ownerDocument == document ? this.node.setAttribute("fill", "url(" + this.getBaseUrl() + "#" + a + ")") : this.node.setAttribute("fill", "url(#" + a + ")")) : this.node.setAttribute("fill", a.fillColor.toLowerCase()))
};
SvgCanvas2D.prototype.getCurrentStrokeWidth = function() {
	return Math.max(1, this.format(this.state.strokeWidth * this.state.scale))
};
SvgCanvas2D.prototype.updateStroke = function() {
	var a = this.state;
	this.node.setAttribute("stroke", a.strokeColor.toLowerCase());
	1 > a.alpha && this.node.setAttribute("stroke-opacity", a.alpha);
	var b = this.getCurrentStrokeWidth();
	1 != b && this.node.setAttribute("stroke-width", b);
	"path" == this.node.nodeName && this.updateStrokeAttributes();
	a.dashed && this.node.setAttribute("stroke-dasharray", this.createDashPattern(a.strokeWidth * a.scale))
};
SvgCanvas2D.prototype.updateStrokeAttributes = function() {
	var a = this.state;
	null != a.lineJoin && "miter" != a.lineJoin && this.node.setAttribute("stroke-linejoin", a.lineJoin);
	if (null != a.lineCap) {
		var b = a.lineCap;
		"flat" == b && (b = "butt");
		"butt" != b && this.node.setAttribute("stroke-linecap", b)
	}
	null != a.miterLimit && (!this.styleEnabled || 10 != a.miterLimit) && this.node.setAttribute("stroke-miterlimit", a.miterLimit)
};
SvgCanvas2D.prototype.createDashPattern = function(a) {
	var b = [];
	if ("string" === typeof this.state.dashPattern) {
		var c = this.state.dashPattern.split(" ");
		if (0 < c.length) for (var d = 0; d < c.length; d++) b[d] = Number(c[d]) * a
	}
	return b.join(" ")
};
SvgCanvas2D.prototype.createTolerance = function(a) {
	a = a.cloneNode(!0);
	var b = parseFloat(a.getAttribute("stroke-width") || 1) + this.strokeTolerance;
	a.setAttribute("pointer-events", "stroke");
	a.setAttribute("visibility", "hidden");
	a.removeAttribute("stroke-dasharray");
	a.setAttribute("stroke-width", b);
	a.setAttribute("fill", "none");
	a.setAttribute("stroke", Client.IS_OP ? "none" : "white");
	return a
};
SvgCanvas2D.prototype.createShadow = function(a) {
	a = a.cloneNode(!0);
	var b = this.state;
	"none" != a.getAttribute("fill") && a.setAttribute("fill", b.shadowColor);
	"none" != a.getAttribute("stroke") && a.setAttribute("stroke", b.shadowColor);
	a.setAttribute("transform", "translate(" + this.format(b.shadowDx * b.scale) + "," + this.format(b.shadowDy * b.scale) + ")" + (b.transform || ""));
	a.setAttribute("opacity", b.shadowAlpha);
	return a
};
SvgCanvas2D.prototype.setLink = function(a) {
	if (null == a) this.root = this.originalRoot;
	else {
		this.originalRoot = this.root;
		var b = this.createElement("a");
		null == b.setAttributeNS || this.root.ownerDocument != document && null == document.documentMode ? b.setAttribute("xlink:href", a) : b.setAttributeNS(Constants.NS_XLINK, "xlink:href", a);
		this.root.appendChild(b);
		this.root = b
	}
};
SvgCanvas2D.prototype.rotate = function(a, b, c, d, e) {
	if (0 != a || b || c) {
		var f = this.state;
		d += f.dx;
		e += f.dy;
		d *= f.scale;
		e *= f.scale;
		f.transform = f.transform || "";
		if (b && c) a += 180;
		else if (b ^ c) {
			var g = b ? d : 0,
				k = b ? -1 : 1,
				l = c ? e : 0,
				m = c ? -1 : 1;
			f.transform += "translate(" + this.format(g) + "," + this.format(l) + ")scale(" + this.format(k) + "," + this.format(m) + ")translate(" + this.format(-g) + "," + this.format(-l) + ")"
		}
		if (b ? !c : c) a *= -1;
		0 != a && (f.transform += "rotate(" + this.format(a) + "," + this.format(d) + "," + this.format(e) + ")");
		f.rotation += a;
		f.rotationCx = d;
		f.rotationCy = e
	}
};
SvgCanvas2D.prototype.begin = function() {
	AbstractCanvas2D.prototype.begin.apply(this, arguments);
	this.node = this.createElement("path")
};
SvgCanvas2D.prototype.rect = function(a, b, c, d) {
	var e = this.state,
		f = this.createElement("rect");
	f.setAttribute("x", this.format((a + e.dx) * e.scale));
	f.setAttribute("y", this.format((b + e.dy) * e.scale));
	f.setAttribute("width", this.format(c * e.scale));
	f.setAttribute("height", this.format(d * e.scale));
	this.node = f
};
SvgCanvas2D.prototype.roundrect = function(a, b, c, d, e, f) {
	this.rect(a, b, c, d);
	0 < e && this.node.setAttribute("rx", this.format(e * this.state.scale));
	0 < f && this.node.setAttribute("ry", this.format(f * this.state.scale))
};
SvgCanvas2D.prototype.ellipse = function(a, b, c, d) {
	var e = this.state,
		f = this.createElement("ellipse");
	f.setAttribute("cx", Math.round((a + c / 2 + e.dx) * e.scale));
	f.setAttribute("cy", Math.round((b + d / 2 + e.dy) * e.scale));
	f.setAttribute("rx", c / 2 * e.scale);
	f.setAttribute("ry", d / 2 * e.scale);
	this.node = f
};
SvgCanvas2D.prototype.image = function(a, b, c, d, e, f, g, k) {
	e = this.converter.convert(e);
	f = null != f ? f : !0;
	g = null != g ? g : !1;
	k = null != k ? k : !1;
	var l = this.state;
	a += l.dx;
	b += l.dy;
	var m = this.createElement("image");
	m.setAttribute("x", this.format(a * l.scale));
	m.setAttribute("y", this.format(b * l.scale));
	m.setAttribute("width", this.format(c * l.scale));
	m.setAttribute("height", this.format(d * l.scale));
	null == m.setAttributeNS ? m.setAttribute("xlink:href", e) : m.setAttributeNS(Constants.NS_XLINK, "xlink:href", e);
	f || m.setAttribute("preserveAspectRatio", "none");
	1 > l.alpha && m.setAttribute("opacity", l.alpha);
	e = this.state.transform || "";
	if (g || k) {
		var n = f = 1,
			p = 0,
			q = 0;
		g && (f = -1, p = -c - 2 * a);
		k && (n = -1, q = -d - 2 * b);
		e += "scale(" + f + "," + n + ")translate(" + p + "," + q + ")"
	}
	0 < e.length && m.setAttribute("transform", e);
	this.pointerEvents || m.setAttribute("pointer-events", "none");
	this.root.appendChild(m);
	this.blockImagePointerEvents && (m.setAttribute("style", "pointer-events:none"), m = this.createElement("rect"), m.setAttribute("visibility", "hidden"), m.setAttribute("pointer-events", "fill"), m.setAttribute("x", this.format(a * l.scale)), m.setAttribute("y", this.format(b * l.scale)), m.setAttribute("width", this.format(c * l.scale)), m.setAttribute("height", this.format(d * l.scale)), this.root.appendChild(m))
};
SvgCanvas2D.prototype.createDiv = function(a, b, c, d, e) {
	var f = this.state;
	c = Constants.ABSOLUTE_LINE_HEIGHT ? Math.round(f.fontSize * Constants.LINE_HEIGHT) + "px" : Constants.LINE_HEIGHT * this.lineHeightCorrection;
	d = "display:inline-block;font-size:" + Math.round(f.fontSize) + "px;font-family:" + f.fontFamily + ";color:" + f.fontColor + ";line-height:" + c + ";" + d;
	(f.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD && (d += "font-weight:bold;");
	(f.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC && (d += "font-style:italic;");
	(f.fontStyle & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE && (d += "text-decoration:underline;");
	c = "";
	b == Constants.ALIGN_CENTER ? d += "text-align:center;" : b == Constants.ALIGN_RIGHT && (d += "text-align:right;");
	null != f.fontBackgroundColor && (c += "background-color:" + f.fontBackgroundColor + ";");
	null != f.fontBorderColor && (c += "border:1px solid " + f.fontBorderColor + ";");
	Utils.isNode(a) || (b = document.createElement("textarea"), b.innerHTML = a.replace(/&lt;/g, "&amp;lt;").replace(/&gt;/g, "&amp;gt;").replace(/</g, "&lt;").replace(/>/g, "&gt;"), a = b.value, "fill" != e && "width" != e ? a = '<div xmlns="http://www.w3.org/1999/xhtml" style="display:inline-block;text-align:inherit;text-decoration:inherit;' + c + '">' + a + "</div>" : d += c);
	if (!Client.IS_IE && !Client.IS_IE11 && document.createElementNS) return e = document.createElementNS("http://www.w3.org/1999/xhtml", "div"), e.setAttribute("style", d), Utils.isNode(a) ? this.root.ownerDocument != document ? e.appendChild(a.cloneNode(!0)) : e.appendChild(a) : e.innerHTML = a, e;
	Utils.isNode(a) && this.root.ownerDocument != document && (a = a.outerHTML);
	a = a.replace(/<br>/g, "<br />").replace(/<hr>/g, "<hr />");
	return Utils.parseXml('<div xmlns="http://www.w3.org/1999/xhtml" style="' + d + '">' + a + "</div>").documentElement
};
SvgCanvas2D.prototype.text = function(a, b, c, d, e, f, g, k, l, m, n, p) {
	if (this.textEnabled && null != e) {
		p = null != p ? p : 0;
		var q = this.state;
		a += q.dx;
		b += q.dy;
		if (this.foEnabled && "html" == l) {
			var r = "vertical-align:top;";
			n ? r += "overflow:hidden;max-height:" + Math.round(d) + "px;width:" + Math.round(c) + "px;" : "fill" == m ? r += "width:" + Math.round(c) + "px;height:" + Math.round(d) + "px;" : "width" == m && (r += "width:" + Math.round(c) + "px;", 0 < d && (r += "max-height:" + Math.round(d) + "px;"));
			var r = k && 0 < c ? r + ("width:" + Math.round(c) + "px;white-space:normal;") : r + "white-space:nowrap;",
				s = this.createElement("g");
			1 > q.alpha && s.setAttribute("opacity", q.alpha);
			var t = this.createElement("foreignObject");
			t.setAttribute("pointer-events", "all");
			r = this.createDiv(e, f, g, r, m);
			if (null != r) {
				s.appendChild(t);
				this.root.appendChild(s);
				var u = 0,
					x = 0,
					y = x = 2;
				if (Client.IS_IE && (9 == document.documentMode || !Client.IS_SVG)) {
					var v = document.createElement("div");
					v.style.cssText = r.getAttribute("style");
					v.style.display = Client.IS_QUIRKS ? "inline" : "inline-block";
					v.style.position = "absolute";
					v.style.visibility = "hidden";
					var z = document.createElement("div");
					z.style.display = Client.IS_QUIRKS ? "inline" : "inline-block";
					z.innerHTML = Utils.isNode(e) ? e.outerHTML : e;
					v.appendChild(z);
					document.body.appendChild(v);
					8 != document.documentMode && (9 != document.documentMode && null != q.fontBorderColor) && (x += 2, y += 2);
					if (k && 0 < c) {
						var u = z.offsetWidth,
							B = 0;
						if (!n && this.root.ownerDocument != document) {
							var A = v.style.whiteSpace;
							v.style.whiteSpace = "nowrap";
							if (u == z.offsetWidth) x += this.fontMetricsPadding;
							else if (8 == document.documentMode || 9 == document.documentMode) B = -2;
							v.style.whiteSpace = A
						}
						u += x;
						n && (u = Math.min(u, c));
						v.style.width = u + "px";
						u = z.offsetWidth + x + B;
						x = z.offsetHeight + y;
						v.style.display = "inline-block";
						v.style.position = "";
						v.style.visibility = "";
						v.style.width = u + "px";
						r.setAttribute("style", v.style.cssText)
					} else u = z.offsetWidth + x, x = z.offsetHeight + y;
					v.parentNode.removeChild(v);
					t.appendChild(r)
				} else this.root.ownerDocument != document || Client.IS_FF ? (r.style.visibility = "hidden", document.body.appendChild(r)) : t.appendChild(r), v = r, null != v.firstChild && "DIV" == v.firstChild.nodeName && (v = v.firstChild), u = v.offsetWidth, !n && (k && 0 < c && this.root.ownerDocument != document) && (A = r.style.whiteSpace, r.style.whiteSpace = "nowrap", u == v.offsetWidth && (x += this.fontMetricsPadding), r.style.whiteSpace = A), u += x, k && (n && (u = Math.min(u, c)), r.style.width = u + "px"), u = v.offsetWidth + x, x = v.offsetHeight + 2, r.parentNode != t && (t.appendChild(r), r.style.visibility = "");
				n && (x = Math.min(x, d));
				"fill" == m ? (c = Math.max(c, u), d = Math.max(d, x)) : (c = "width" == m ? Math.max(c, u) : u, d = x);
				1 > q.alpha && s.setAttribute("opacity", q.alpha);
				x = r = 0;
				f == Constants.ALIGN_CENTER ? r -= c / 2 : f == Constants.ALIGN_RIGHT && (r -= c);
				a += r;
				g == Constants.ALIGN_MIDDLE ? x -= d / 2 : g == Constants.ALIGN_BOTTOM && (x -= d);
				b += x;
				v = 1 != q.scale ? "scale(" + q.scale + ")" : "";
				0 != q.rotation && this.rotateHtml ? (v += "rotate(" + q.rotation + "," + c / 2 + "," + d / 2 + ")", b = this.rotatePoint((a + c / 2) * q.scale, (b + d / 2) * q.scale, q.rotation, q.rotationCx, q.rotationCy), a = b.x - c * q.scale / 2, b = b.y - d * q.scale / 2) : (a *= q.scale, b *= q.scale);
				0 != p && (v += "rotate(" + p + "," + -r + "," + -x + ")");
				s.setAttribute("transform", "translate(" + Math.round(a) + "," + Math.round(b) + ")" + v);
				t.setAttribute("width", Math.round(Math.max(1, c)));
				t.setAttribute("height", Math.round(Math.max(1, d)));
				this.root.ownerDocument != document && (a = this.createAlternateContent(t, a, b, c, d, e, f, g, k, l, m, n, p), null != a && (t.setAttribute("requiredFeatures", "http://www.w3.org/TR/SVG11/feature#Extensibility"), c = this.createElement("switch"), c.appendChild(t), c.appendChild(a), s.appendChild(c)))
			}
		} else this.plainText(a, b, c, d, e, f, g, k, m, n, p)
	}
};
SvgCanvas2D.prototype.createClip = function(a, b, c, d) {
	a = Math.round(a);
	b = Math.round(b);
	c = Math.round(c);
	d = Math.round(d);
	for (var e = "clip-" + a + "-" + b + "-" + c + "-" + d, f = 0, g = e + "-" + f; null != document.getElementById(g);) g = e + "-" + ++f;
	clip = this.createElement("clipPath");
	clip.setAttribute("id", g);
	e = this.createElement("rect");
	e.setAttribute("x", a);
	e.setAttribute("y", b);
	e.setAttribute("width", c);
	e.setAttribute("height", d);
	clip.appendChild(e);
	return clip
};
SvgCanvas2D.prototype.plainText = function(a, b, c, d, e, f, g, k, l, m, n) {
	n = null != n ? n : 0;
	k = this.state;
	var p = Math.round(k.fontSize),
		q = this.createElement("g"),
		r = k.transform || "";
	this.updateFont(q);
	0 != n && (r += "rotate(" + n + "," + this.format(a * k.scale) + "," + this.format(b * k.scale) + ")");
	if (m && 0 < c && 0 < d) {
		var s = a;
		n = b;
		f == Constants.ALIGN_CENTER ? s -= c / 2 : f == Constants.ALIGN_RIGHT && (s -= c);
		"fill" != l && (g == Constants.ALIGN_MIDDLE ? n -= d / 2 : g == Constants.ALIGN_BOTTOM && (n -= d));
		n = this.createClip(s * k.scale - 2, n * k.scale - 2, c * k.scale + 4, d * k.scale + 4);
		null != this.defs ? this.defs.appendChild(n) : this.root.appendChild(n);
		this.root.ownerDocument == document ? q.setAttribute("clip-path", "url(" + this.getBaseUrl() + "#" + n.getAttribute("id") + ")") : q.setAttribute("clip-path", "url(#" + n.getAttribute("id") + ")")
	}
	n = f == Constants.ALIGN_RIGHT ? "end" : f == Constants.ALIGN_CENTER ? "middle" : "start";
	"start" != n && q.setAttribute("text-anchor", n);
	(!this.styleEnabled || p != Constants.DEFAULT_FONTSIZE) && q.setAttribute("font-size", Math.round(p * k.scale) + "px");
	0 < r.length && q.setAttribute("transform", r);
	1 > k.alpha && q.setAttribute("opacity", k.alpha);
	var r = e.split("\n"),
		s = Math.round(p * Constants.LINE_HEIGHT),
		t = p + (r.length - 1) * s;
	n = b + p - 1;
	g == Constants.ALIGN_MIDDLE ? "fill" == l ? n -= d / 2 : (m = (this.matchHtmlAlignment && m && 0 < d ? Math.min(t, d) : t) / 2, n -= m + 1) : g == Constants.ALIGN_BOTTOM && ("fill" == l ? n -= d : (m = this.matchHtmlAlignment && m && 0 < d ? Math.min(t, d) : t, n -= m + 2));
	for (m = 0; m < r.length; m++) 0 < r[m].length && 0 < Utils.trim(r[m]).length && (p = this.createElement("text"), p.setAttribute("x", this.format(a * k.scale)), p.setAttribute("y", this.format(n * k.scale)), Utils.write(p, r[m]), q.appendChild(p)), n += s;
	this.root.appendChild(q);
	this.addTextBackground(q, e, a, b, c, "fill" == l ? d : t, f, g, l)
};
SvgCanvas2D.prototype.updateFont = function(a) {
	var b = this.state;
	a.setAttribute("fill", b.fontColor);
	(!this.styleEnabled || b.fontFamily != Constants.DEFAULT_FONTFAMILY) && a.setAttribute("font-family", b.fontFamily);
	(b.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD && a.setAttribute("font-weight", "bold");
	(b.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC && a.setAttribute("font-style", "italic");
	(b.fontStyle & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE && a.setAttribute("text-decoration", "underline")
};
SvgCanvas2D.prototype.addTextBackground = function(a, b, c, d, e, f, g, k, l) {
	var m = this.state;
	if (null != m.fontBackgroundColor || null != m.fontBorderColor) {
		var n = null;
		if ("fill" == l || "width" == l) g == Constants.ALIGN_CENTER ? c -= e / 2 : g == Constants.ALIGN_RIGHT && (c -= e), k == Constants.ALIGN_MIDDLE ? d -= f / 2 : k == Constants.ALIGN_BOTTOM && (d -= f), n = new Rectangle((c + 1) * m.scale, d * m.scale, (e - 2) * m.scale, (f + 2) * m.scale);
		else if (null != a.getBBox && this.root.ownerDocument == document) try {
			var n = a.getBBox(),
				p = Client.IS_IE && Client.IS_SVG,
				n = new Rectangle(n.x, n.y + (p ? 0 : 1), n.width, n.height + (p ? 1 : 0))
		} catch (q) {} else n = document.createElement("div"), n.style.lineHeight = Constants.ABSOLUTE_LINE_HEIGHT ? Math.round(m.fontSize * Constants.LINE_HEIGHT) + "px" : Constants.LINE_HEIGHT, n.style.fontSize = Math.round(m.fontSize) + "px", n.style.fontFamily = m.fontFamily, n.style.whiteSpace = "nowrap", n.style.position = "absolute", n.style.visibility = "hidden", n.style.display = Client.IS_QUIRKS ? "inline" : "inline-block", n.style.zoom = "1", (m.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD && (n.style.fontWeight = "bold"), (m.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC && (n.style.fontStyle = "italic"), b = Utils.htmlEntities(b, !1), n.innerHTML = b.replace(/\n/g, "<br/>"), document.body.appendChild(n), e = n.offsetWidth, f = n.offsetHeight, n.parentNode.removeChild(n), g == Constants.ALIGN_CENTER ? c -= e / 2 : g == Constants.ALIGN_RIGHT && (c -= e), k == Constants.ALIGN_MIDDLE ? d -= f / 2 : k == Constants.ALIGN_BOTTOM && (d -= f), n = new Rectangle((c + 1) * m.scale, (d + 2) * m.scale, e * m.scale, (f + 1) * m.scale);
		null != n && (b = this.createElement("rect"), b.setAttribute("fill", m.fontBackgroundColor || "none"), b.setAttribute("stroke", m.fontBorderColor || "none"), b.setAttribute("x", Math.floor(n.x - 1)), b.setAttribute("y", Math.floor(n.y - 1)), b.setAttribute("width", Math.ceil(n.width + 2)), b.setAttribute("height", Math.ceil(n.height)), m = null != m.fontBorderColor ? Math.max(1, this.format(m.scale)) : 0, b.setAttribute("stroke-width", m), this.root.ownerDocument == document && 1 == Utils.mod(m, 2) && b.setAttribute("transform", "translate(0.5, 0.5)"), a.insertBefore(b, a.firstChild))
	}
};
SvgCanvas2D.prototype.stroke = function() {
	this.addNode(!1, !0)
};
SvgCanvas2D.prototype.fill = function() {
	this.addNode(!0, !1)
};
SvgCanvas2D.prototype.fillAndStroke = function() {
	this.addNode(!0, !0)
};
var VmlCanvas2D = function(a) {
		AbstractCanvas2D.call(this);
		this.root = a
	};
Utils.extend(VmlCanvas2D, AbstractCanvas2D);
VmlCanvas2D.prototype.node = null;
VmlCanvas2D.prototype.textEnabled = !0;
VmlCanvas2D.prototype.moveOp = "m";
VmlCanvas2D.prototype.lineOp = "l";
VmlCanvas2D.prototype.curveOp = "c";
VmlCanvas2D.prototype.closeOp = "x";
VmlCanvas2D.prototype.rotatedHtmlBackground = "";
VmlCanvas2D.prototype.vmlScale = 1;
VmlCanvas2D.prototype.createElement = function(a) {
	return document.createElement(a)
};
VmlCanvas2D.prototype.createVmlElement = function(a) {
	return this.createElement(Client.VML_PREFIX + ":" + a)
};
VmlCanvas2D.prototype.addNode = function(a, b) {
	var c = this.node,
		d = this.state;
	if (null != c) {
		if ("shape" == c.nodeName) if (null != this.path && 0 < this.path.length) c.path = this.path.join(" ") + " e", c.style.width = this.root.style.width, c.style.height = this.root.style.height, c.coordsize = parseInt(c.style.width) + " " + parseInt(c.style.height);
		else return;
		c.strokeweight = this.format(Math.max(1, d.strokeWidth * d.scale / this.vmlScale)) + "px";
		d.shadow && this.root.appendChild(this.createShadow(c, a && null != d.fillColor, b && null != d.strokeColor));
		b && null != d.strokeColor ? (c.stroked = "true", c.strokecolor = d.strokeColor) : c.stroked = "false";
		c.appendChild(this.createStroke());
		a && null != d.fillColor ? c.appendChild(this.createFill()) : this.pointerEvents && ("shape" != c.nodeName || this.path[this.path.length - 1] == this.closeOp) ? c.appendChild(this.createTransparentFill()) : c.filled = "false";
		this.root.appendChild(c)
	}
};
VmlCanvas2D.prototype.createTransparentFill = function() {
	var a = this.createVmlElement("fill");
	a.src = Client.imageBasePath + "/transparent.gif";
	a.type = "tile";
	return a
};
VmlCanvas2D.prototype.createFill = function() {
	var a = this.state,
		b = this.createVmlElement("fill");
	b.color = a.fillColor;
	if (null != a.gradientColor) {
		b.type = "gradient";
		b.method = "none";
		b.color2 = a.gradientColor;
		var c = 180 - a.rotation,
			c = a.gradientDirection == Constants.DIRECTION_WEST ? c - (90 + ("x" == this.root.style.flip ? 180 : 0)) : a.gradientDirection == Constants.DIRECTION_EAST ? c + (90 + ("x" == this.root.style.flip ? 180 : 0)) : a.gradientDirection == Constants.DIRECTION_NORTH ? c - (180 + ("y" == this.root.style.flip ? -180 : 0)) : c + ("y" == this.root.style.flip ? -180 : 0);
		if ("x" == this.root.style.flip || "y" == this.root.style.flip) c *= -1;
		b.angle = Utils.mod(c, 360);
		b.opacity = 100 * a.alpha * a.fillAlpha + "%";
		b.setAttribute(Client.OFFICE_PREFIX + ":opacity2", 100 * a.alpha * a.gradientAlpha + "%")
	} else 1 > a.alpha && (b.opacity = 100 * a.alpha + "%");
	return b
};
VmlCanvas2D.prototype.createStroke = function() {
	var a = this.state,
		b = this.createVmlElement("stroke");
	b.endcap = a.lineCap || "flat";
	b.joinstyle = a.lineJoin || "miter";
	b.miterlimit = a.miterLimit || "10";
	1 > a.alpha && (b.opacity = 100 * a.alpha + "%");
	a.dashed && (b.dashstyle = this.getVmlDashStyle());
	return b
};
VmlCanvas2D.prototype.getVmlDashStyle = function() {
	var a = "dash";
	if ("string" === typeof this.state.dashPattern) {
		var b = this.state.dashPattern.split(" ");
		0 < b.length && 1 == b[0] && (a = "0 2")
	}
	return a
};
VmlCanvas2D.prototype.createShadow = function(a, b, c) {
	var d = this.state,
		e = -d.rotation * (Math.PI / 180),
		f = Math.cos(e),
		e = Math.sin(e),
		g = d.shadowDx * d.scale,
		k = d.shadowDy * d.scale;
	"x" == this.root.style.flip ? g *= -1 : "y" == this.root.style.flip && (k *= -1);
	var l = a.cloneNode(!0);
	l.style.marginLeft = Math.round(g * f - k * e) + "px";
	l.style.marginTop = Math.round(g * e + k * f) + "px";
	8 == document.documentMode && (l.strokeweight = a.strokeweight, "shape" == a.nodeName && (l.path = this.path.join(" ") + " e", l.style.width = this.root.style.width, l.style.height = this.root.style.height, l.coordsize = parseInt(a.style.width) + " " + parseInt(a.style.height)));
	c ? (l.strokecolor = d.shadowColor, l.appendChild(this.createShadowStroke())) : l.stroked = "false";
	b ? l.appendChild(this.createShadowFill()) : l.filled = "false";
	return l
};
VmlCanvas2D.prototype.createShadowFill = function() {
	var a = this.createVmlElement("fill");
	a.color = this.state.shadowColor;
	a.opacity = 100 * this.state.alpha * this.state.shadowAlpha + "%";
	return a
};
VmlCanvas2D.prototype.createShadowStroke = function() {
	var a = this.createStroke();
	a.opacity = 100 * this.state.alpha * this.state.shadowAlpha + "%";
	return a
};
VmlCanvas2D.prototype.rotate = function(a, b, c, d, e) {
	b && c ? a += 180 : b ? this.root.style.flip = "x" : c && (this.root.style.flip = "y");
	if (b ? !c : c) a *= -1;
	this.root.style.rotation = a;
	this.state.rotation += a;
	this.state.rotationCx = d;
	this.state.rotationCy = e
};
VmlCanvas2D.prototype.begin = function() {
	AbstractCanvas2D.prototype.begin.apply(this, arguments);
	this.node = this.createVmlElement("shape");
	this.node.style.position = "absolute"
};
VmlCanvas2D.prototype.quadTo = function(a, b, c, d) {
	var e = this.state,
		f = (this.lastX + e.dx) * e.scale,
		g = (this.lastY + e.dy) * e.scale;
	a = (a + e.dx) * e.scale;
	b = (b + e.dy) * e.scale;
	c = (c + e.dx) * e.scale;
	d = (d + e.dy) * e.scale;
	var g = g + 2 / 3 * (b - g),
		k = c + 2 / 3 * (a - c);
	b = d + 2 / 3 * (b - d);
	this.path.push("c " + this.format(f + 2 / 3 * (a - f)) + " " + this.format(g) + " " + this.format(k) + " " + this.format(b) + " " + this.format(c) + " " + this.format(d));
	this.lastX = c / e.scale - e.dx;
	this.lastY = d / e.scale - e.dy
};
VmlCanvas2D.prototype.createRect = function(a, b, c, d, e) {
	var f = this.state;
	a = this.createVmlElement(a);
	a.style.position = "absolute";
	a.style.left = this.format((b + f.dx) * f.scale) + "px";
	a.style.top = this.format((c + f.dy) * f.scale) + "px";
	a.style.width = this.format(d * f.scale) + "px";
	a.style.height = this.format(e * f.scale) + "px";
	return a
};
VmlCanvas2D.prototype.rect = function(a, b, c, d) {
	this.node = this.createRect("rect", a, b, c, d)
};
VmlCanvas2D.prototype.roundrect = function(a, b, c, d, e, f) {
	this.node = this.createRect("roundrect", a, b, c, d);
	this.node.setAttribute("arcsize", Math.max(100 * e / c, 100 * f / d) + "%")
};
VmlCanvas2D.prototype.ellipse = function(a, b, c, d) {
	this.node = this.createRect("oval", a, b, c, d)
};
VmlCanvas2D.prototype.image = function(a, b, c, d, e, f, g, k) {
	var l = null;
	f ? (l = this.createRect("rect", a, b, c, d), l.stroked = "false", a = this.createVmlElement("fill"), a.aspect = f ? "atmost" : "ignore", a.rotate = "true", a.type = "frame", a.src = e, l.appendChild(a)) : (l = this.createRect("image", a, b, c, d), l.src = e);
	g && k ? l.style.rotation = "180" : g ? l.style.flip = "x" : k && (l.style.flip = "y");
	1 > this.state.alpha && (l.style.filter += "alpha(opacity=" + 100 * this.state.alpha + ")");
	this.root.appendChild(l)
};
VmlCanvas2D.prototype.createDiv = function(a, b, c, d) {
	c = this.createElement("div");
	var e = this.state,
		f = "";
	null != e.fontBackgroundColor && (f += "background-color:" + e.fontBackgroundColor + ";");
	null != e.fontBorderColor && (f += "border:1px solid " + e.fontBorderColor + ";");
	Utils.isNode(a) ? c.appendChild(a) : "fill" != d && "width" != d ? (d = this.createElement("div"), d.style.cssText = f, d.style.display = Client.IS_QUIRKS ? "inline" : "inline-block", d.style.zoom = "1", d.style.textDecoration = "inherit", d.innerHTML = a, c.appendChild(d)) : (c.style.cssText = f, c.innerHTML = a);
	a = c.style;
	a.fontSize = Math.round(e.fontSize / this.vmlScale) + "px";
	a.fontFamily = e.fontFamily;
	a.color = e.fontColor;
	a.verticalAlign = "top";
	a.textAlign = b || "left";
	a.lineHeight = Constants.ABSOLUTE_LINE_HEIGHT ? Math.round(e.fontSize * Constants.LINE_HEIGHT / this.vmlScale) + "px" : Constants.LINE_HEIGHT;
	(e.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD && (a.fontWeight = "bold");
	(e.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC && (a.fontStyle = "italic");
	(e.fontStyle & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE && (a.textDecoration = "underline");
	return c
};
VmlCanvas2D.prototype.text = function(a, b, c, d, e, f, g, k, l, m, n, p) {
	if (this.textEnabled && null != e) {
		var q = this.state;
		if ("html" == l) {
			null != q.rotation && (b = this.rotatePoint(a, b, q.rotation, q.rotationCx, q.rotationCy), a = b.x, b = b.y);
			8 == document.documentMode ? (a += q.dx, b += q.dy) : (a *= q.scale, b *= q.scale);
			l = 8 == document.documentMode ? this.createVmlElement("group") : this.createElement("div");
			l.style.position = "absolute";
			l.style.display = "inline";
			l.style.left = this.format(a) + "px";
			l.style.top = this.format(b) + "px";
			l.style.zoom = q.scale;
			var r = this.createElement("div");
			r.style.position = "relative";
			r.style.display = "inline";
			var s = Utils.getAlignmentAsPoint(f, g),
				t = s.x,
				s = s.y;
			e = this.createDiv(e, f, g, m);
			f = this.createElement("div");
			k && 0 < c ? (n || (e.style.width = Math.round(c) + "px"), e.style.whiteSpace = "normal") : e.style.whiteSpace = "nowrap";
			p = q.rotation + (p || 0);
			this.rotateHtml && 0 != p ? (f.style.display = "inline", f.style.zoom = "1", f.appendChild(e), 8 == document.documentMode && "DIV" != this.root.nodeName ? (r.appendChild(f), l.appendChild(r)) : l.appendChild(f)) : 8 == document.documentMode ? (r.appendChild(e), l.appendChild(r)) : (e.style.display = "inline", l.appendChild(e));
			"DIV" != this.root.nodeName ? (g = this.createVmlElement("rect"), g.stroked = "false", g.filled = "false", g.appendChild(l), this.root.appendChild(g)) : this.root.appendChild(l);
			n ? (e.style.overflow = "hidden", e.style.width = Math.round(c) + "px", Client.IS_QUIRKS || (e.style.maxHeight = Math.round(d) + "px")) : "fill" == m ? (e.style.width = c + "px", e.style.height = d + "px") : "width" == m && (e.style.width = c + "px", 0 < d && (e.style.maxHeight = Math.round(d) + "px"));
			if (this.rotateHtml && 0 != p) {
				var u = p * (Math.PI / 180);
				p = parseFloat(parseFloat(Math.cos(u)).toFixed(8));
				g = parseFloat(parseFloat(Math.sin(-u)).toFixed(8));
				u %= 2 * Math.PI;
				0 > u && (u += 2 * Math.PI);
				u %= Math.PI;
				u > Math.PI / 2 && (u = Math.PI - u);
				var x = Math.cos(u),
					u = Math.sin(u);
				8 == document.documentMode && (e.style.display = "inline-block", f.style.display = "inline-block", r.style.display = "inline-block");
				e.style.visibility = "hidden";
				e.style.position = "absolute";
				document.body.appendChild(e);
				var y = e;
				null != y.firstChild && "DIV" == y.firstChild.nodeName && (y = y.firstChild);
				r = y.offsetWidth + 3;
				y = y.offsetHeight;
				n ? (c = Math.min(c, r), y = Math.min(y, d)) : c = r;
				k && (e.style.width = c + "px");
				if (Client.IS_QUIRKS && (n || "width" == m) && y > d) y = d, e.style.height = y + "px";
				d = y;
				n = (d - d * x + c * -u) / 2 - g * c * (t + 0.5) + p * d * (s + 0.5);
				k = (c - c * x + d * -u) / 2 + p * c * (t + 0.5) + g * d * (s + 0.5);
				"group" == l.nodeName && "DIV" == this.root.nodeName ? (m = this.createElement("div"), m.style.display = "inline-block", m.style.position = "absolute", m.style.left = this.format(a + (k - c / 2) * q.scale) + "px", m.style.top = this.format(b + (n - d / 2) * q.scale) + "px", l.parentNode.appendChild(m), m.appendChild(l)) : (q = 8 == document.documentMode ? 1 : q.scale, l.style.left = this.format(a + (k - c / 2) * q) + "px", l.style.top = this.format(b + (n - d / 2) * q) + "px");
				f.style.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + p + ", M12=" + g + ", M21=" + -g + ", M22=" + p + ", sizingMethod='auto expand')";
				f.style.backgroundColor = this.rotatedHtmlBackground;
				1 > this.state.alpha && (f.style.filter += "alpha(opacity=" + 100 * this.state.alpha + ")");
				f.appendChild(e);
				e.style.position = "";
				e.style.visibility = ""
			} else 8 != document.documentMode ? (e.style.verticalAlign = "top", 1 > this.state.alpha && (l.style.filter = "alpha(opacity=" + 100 * this.state.alpha + ")"), q = e.parentNode, e.style.visibility = "hidden", document.body.appendChild(e), c = e.offsetWidth, y = e.offsetHeight, Client.IS_QUIRKS && (n && y > d) && (y = d, e.style.height = y + "px"), d = y, e.style.visibility = "", q.appendChild(e), l.style.left = this.format(a + c * t * this.state.scale) + "px", l.style.top = this.format(b + d * s * this.state.scale) + "px") : (1 > this.state.alpha && (e.style.filter = "alpha(opacity=" + 100 * this.state.alpha + ")"), r.style.left = 100 * t + "%", r.style.top = 100 * s + "%")
		} else this.plainText(a, b, c, d, Utils.htmlEntities(e, !1), f, g, k, l, m, n, p)
	}
};
VmlCanvas2D.prototype.plainText = function(a, b, c, d, e, f, g, k, l, m, n, p) {
	k = this.state;
	a = (a + k.dx) * k.scale;
	b = (b + k.dy) * k.scale;
	c = this.createVmlElement("shape");
	c.style.width = "1px";
	c.style.height = "1px";
	c.stroked = "false";
	d = this.createVmlElement("fill");
	d.color = k.fontColor;
	d.opacity = 100 * k.alpha + "%";
	c.appendChild(d);
	d = this.createVmlElement("path");
	d.textpathok = "true";
	d.v = "m " + this.format(0) + " " + this.format(0) + " l " + this.format(1) + " " + this.format(0);
	c.appendChild(d);
	d = this.createVmlElement("textpath");
	d.style.cssText = "v-text-align:" + f;
	d.style.align = f;
	d.style.fontFamily = k.fontFamily;
	d.string = e;
	d.on = "true";
	f = Math.round(k.fontSize * k.scale / this.vmlScale);
	d.style.fontSize = f + "px";
	(k.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD && (d.style.fontWeight = "bold");
	(k.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC && (d.style.fontStyle = "italic");
	(k.fontStyle & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE && (d.style.textDecoration = "underline");
	e = e.split("\n");
	k = f + (e.length - 1) * f * Constants.LINE_HEIGHT;
	f = e = 0;
	g == Constants.ALIGN_BOTTOM ? f = -k / 2 : g != Constants.ALIGN_MIDDLE && (f = k / 2);
	null != p && (c.style.rotation = p, g = p * (Math.PI / 180), e = Math.sin(g) * f, f *= Math.cos(g));
	c.appendChild(d);
	c.style.left = this.format(a - e) + "px";
	c.style.top = this.format(b + f) + "px";
	this.root.appendChild(c)
};
VmlCanvas2D.prototype.stroke = function() {
	this.addNode(!1, !0)
};
VmlCanvas2D.prototype.fill = function() {
	this.addNode(!0, !1)
};
VmlCanvas2D.prototype.fillAndStroke = function() {
	this.addNode(!0, !0)
};

function Guide(a, b) {
	this.graph = a;
	this.setStates(b)
}
Guide.prototype.graph = null;
Guide.prototype.states = null;
Guide.prototype.horizontal = !0;
Guide.prototype.vertical = !0;
Guide.prototype.guideX = null;
Guide.prototype.guideY = null;
Guide.prototype.setStates = function(a) {
	this.states = a
};
Guide.prototype.isEnabledForEvent = function(a) {
	return !0
};
Guide.prototype.getGuideTolerance = function() {
	return this.graph.gridSize * this.graph.view.scale / 2
};
Guide.prototype.createGuideShape = function(a) {
	a = new Polyline([], Constants.GUIDE_COLOR, Constants.GUIDE_STROKEWIDTH);
	a.isDashed = !0;
	return a
};
Guide.prototype.move = function(a, b, c) {
	if (null != this.states && (this.horizontal || this.vertical) && null != a && null != b) {
		var d = this.graph.getView().translate,
			e = this.graph.getView().scale,
			f = b.x,
			g = b.y,
			k = !1,
			l = !1,
			m = this.getGuideTolerance(),
			n = m,
			p = m,
			m = a.clone();
		m.x += b.x;
		m.y += b.y;
		var q = m.x,
			r = m.x + m.width,
			s = m.getCenterX(),
			t = m.y,
			u = m.y + m.height,
			x = m.getCenterY();
		b = function(b) {
			b += this.graph.panDx;
			var c = !1;
			Math.abs(b - s) < n ? (f = b - a.getCenterX(), n = Math.abs(b - s), c = !0) : Math.abs(b - q) < n ? (f = b - a.x, n = Math.abs(b - q), c = !0) : Math.abs(b - r) < n && (f = b - a.x - a.width, n = Math.abs(b - r), c = !0);
			if (c) {
				null == this.guideX && (this.guideX = this.createGuideShape(!0), this.guideX.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG, this.guideX.pointerEvents = !1, this.guideX.init(this.graph.getView().getOverlayPane()));
				var d = this.graph.container;
				b -= this.graph.panDx;
				this.guideX.points = [new Point(b, -this.graph.panDy), new Point(b, d.scrollHeight - 3 - this.graph.panDy)]
			}
			k = k || c
		};
		for (var m = function(b) {
				b += this.graph.panDy;
				var c = !1;
				Math.abs(b - x) < p ? (g = b - a.getCenterY(), p = Math.abs(b - x), c = !0) : Math.abs(b - t) < p ? (g = b - a.y, p = Math.abs(b - t), c = !0) : Math.abs(b - u) < p && (g = b - a.y - a.height, p = Math.abs(b - u), c = !0);
				if (c) {
					null == this.guideY && (this.guideY = this.createGuideShape(!1), this.guideY.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG, this.guideY.pointerEvents = !1, this.guideY.init(this.graph.getView().getOverlayPane()));
					var d = this.graph.container;
					b -= this.graph.panDy;
					this.guideY.points = [new Point(-this.graph.panDx, b), new Point(d.scrollWidth - 3 - this.graph.panDx, b)]
				}
				l = l || c
			}, y = 0; y < this.states.length; y++) {
			var v = this.states[y];
			null != v && (this.horizontal && (b.call(this, v.getCenterX()), b.call(this, v.x), b.call(this, v.x + v.width)), this.vertical && (m.call(this, v.getCenterY()), m.call(this, v.y), m.call(this, v.y + v.height)))
		}!k && null != this.guideX ? this.guideX.node.style.visibility = "hidden" : null != this.guideX && (this.guideX.node.style.visibility = "visible", this.guideX.redraw());
		!l && null != this.guideY ? this.guideY.node.style.visibility = "hidden" : null != this.guideY && (this.guideY.node.style.visibility = "visible", this.guideY.redraw());
		c && (k || (c = a.x - (this.graph.snap(a.x / e - d.x) + d.x) * e, f = this.graph.snap(f / e) * e - c), l || (d = a.y - (this.graph.snap(a.y / e - d.y) + d.y) * e, g = this.graph.snap(g / e) * e - d));
		b = new Point(f, g)
	}
	return b
};
Guide.prototype.hide = function() {
	null != this.guideX && (this.guideX.node.style.visibility = "hidden");
	null != this.guideY && (this.guideY.node.style.visibility = "hidden")
};
Guide.prototype.destroy = function() {
	null != this.guideX && (this.guideX.destroy(), this.guideX = null);
	null != this.guideY && (this.guideY.destroy(), this.guideY = null)
};

function Stencil(a) {
	this.desc = a;
	this.parseDescription();
	this.parseConstraints()
}
Stencil.defaultLocalized = !1;
Stencil.prototype.desc = null;
Stencil.prototype.constraints = null;
Stencil.prototype.aspect = null;
Stencil.prototype.w0 = null;
Stencil.prototype.h0 = null;
Stencil.prototype.bgNode = null;
Stencil.prototype.fgNode = null;
Stencil.prototype.strokewidth = null;
Stencil.prototype.parseDescription = function() {
	this.fgNode = this.desc.getElementsByTagName("foreground")[0];
	this.bgNode = this.desc.getElementsByTagName("background")[0];
	this.w0 = Number(this.desc.getAttribute("w") || 100);
	this.h0 = Number(this.desc.getAttribute("h") || 100);
	var a = this.desc.getAttribute("aspect");
	this.aspect = null != a ? a : "variable";
	a = this.desc.getAttribute("strokewidth");
	this.strokewidth = null != a ? a : "1"
};
Stencil.prototype.parseConstraints = function() {
	var a = this.desc.getElementsByTagName("connections")[0];
	if (null != a && (a = Utils.getChildNodes(a), null != a && 0 < a.length)) {
		this.constraints = [];
		for (var b = 0; b < a.length; b++) this.constraints.push(this.parseConstraint(a[b]))
	}
};
Stencil.prototype.parseConstraint = function(a) {
	var b = Number(a.getAttribute("x")),
		c = Number(a.getAttribute("y"));
	a = "1" == a.getAttribute("perimeter");
	return new ConnectionConstraint(new Point(b, c), a)
};
Stencil.prototype.evaluateTextAttribute = function(a, b, c) {
	b = this.evaluateAttribute(a, b, c);
	a = a.getAttribute("localized");
	if (Stencil.defaultLocalized && null == a || "1" == a) b = Resources.get(b);
	return b
};
Stencil.prototype.evaluateAttribute = function(a, b, c) {
	b = a.getAttribute(b);
	null == b && (a = Utils.getTextContent(a), null != a && (a = Utils.eval(a), "function" == typeof a && (b = a(c))));
	return b
};
Stencil.prototype.drawShape = function(a, b, c, d, e, f) {
	this.drawChildren(a, b, c, d, e, f, this.bgNode, !1);
	this.drawChildren(a, b, c, d, e, f, this.fgNode, !0)
};
Stencil.prototype.drawChildren = function(a, b, c, d, e, f, g, k) {
	if (null != g && 0 < e && 0 < f) {
		var l = Utils.getValue(b.style, Constants.STYLE_DIRECTION, null);
		c = this.computeAspect(b.style, c, d, e, f, l);
		d = Math.min(c.width, c.height);
		d = "inherit" == this.strokewidth ? Number(Utils.getNumber(b.style, Constants.STYLE_STROKEWIDTH, 1)) : Number(this.strokewidth) * d;
		a.setStrokeWidth(d);
		for (g = g.firstChild; null != g;) g.nodeType == Constants.NODETYPE_ELEMENT && this.drawNode(a, b, g, c, k), g = g.nextSibling
	}
};
Stencil.prototype.computeAspect = function(a, b, c, d, e, f) {
	a = b;
	b = d / this.w0;
	var g = e / this.h0;
	if (f = f == Constants.DIRECTION_NORTH || f == Constants.DIRECTION_SOUTH) {
		g = d / this.h0;
		b = e / this.w0;
		var k = (d - e) / 2;
		a += k;
		c -= k
	}
	"fixed" == this.aspect && (b = g = Math.min(b, g), f ? (a += (e - this.w0 * b) / 2, c += (d - this.h0 * g) / 2) : (a += (d - this.w0 * b) / 2, c += (e - this.h0 * g) / 2));
	return new Rectangle(a, c, b, g)
};
Stencil.prototype.drawNode = function(a, b, c, d, e) {
	var f = c.nodeName,
		g = d.x,
		k = d.y,
		l = d.width,
		m = d.height,
		n = Math.min(l, m);
	if ("save" == f) a.save();
	else if ("restore" == f) a.restore();
	else if ("path" == f) {
		a.begin();
		for (c = c.firstChild; null != c;) c.nodeType == Constants.NODETYPE_ELEMENT && this.drawNode(a, b, c, d, e), c = c.nextSibling
	} else if ("close" == f) a.close();
	else if ("move" == f) a.moveTo(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m);
	else if ("line" == f) a.lineTo(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m);
	else if ("quad" == f) a.quadTo(g + Number(c.getAttribute("x1")) * l, k + Number(c.getAttribute("y1")) * m, g + Number(c.getAttribute("x2")) * l, k + Number(c.getAttribute("y2")) * m);
	else if ("curve" == f) a.curveTo(g + Number(c.getAttribute("x1")) * l, k + Number(c.getAttribute("y1")) * m, g + Number(c.getAttribute("x2")) * l, k + Number(c.getAttribute("y2")) * m, g + Number(c.getAttribute("x3")) * l, k + Number(c.getAttribute("y3")) * m);
	else if ("arc" == f) a.arcTo(Number(c.getAttribute("rx")) * l, Number(c.getAttribute("ry")) * m, Number(c.getAttribute("x-axis-rotation")), Number(c.getAttribute("large-arc-flag")), Number(c.getAttribute("sweep-flag")), g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m);
	else if ("rect" == f) a.rect(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m, Number(c.getAttribute("w")) * l, Number(c.getAttribute("h")) * m);
	else if ("roundrect" == f) b = Number(c.getAttribute("arcsize")), 0 == b && (b = 100 * Constants.RECTANGLE_ROUNDING_FACTOR), n = Number(c.getAttribute("w")) * l, d = Number(c.getAttribute("h")) * m, b = Number(b) / 100, b = Math.min(n * b, d * b), a.roundrect(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m, n, d, b, b);
	else if ("ellipse" == f) a.ellipse(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m, Number(c.getAttribute("w")) * l, Number(c.getAttribute("h")) * m);
	else if ("image" == f) b = this.evaluateAttribute(c, "src", b), a.image(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m, Number(c.getAttribute("w")) * l, Number(c.getAttribute("h")) * m, b, !1, "1" == c.getAttribute("flipH"), "1" == c.getAttribute("flipV"));
	else if ("text" == f) {
		n = this.evaluateTextAttribute(c, "str", b);
		d = "1" == c.getAttribute("vertical") ? -90 : 0;
		if ("0" == c.getAttribute("align-shape")) {
			var p = b.rotation,
				q = 1 == Utils.getValue(b.style, Constants.STYLE_FLIPH, 0);
			b = 1 == Utils.getValue(b.style, Constants.STYLE_FLIPV, 0);
			d = q && b ? d - p : q || b ? d + p : d - p
		}
		d -= c.getAttribute("rotation");
		a.text(g + Number(c.getAttribute("x")) * l, k + Number(c.getAttribute("y")) * m, 0, 0, n, c.getAttribute("align") || "left", c.getAttribute("valign") || "top", !1, "", null, !1, d)
	} else if ("include-shape" == f) p = StencilRegistry.getStencil(c.getAttribute("name")), null != p && (g += Number(c.getAttribute("x")) * l, k += Number(c.getAttribute("y")) * m, n = Number(c.getAttribute("w")) * l, d = Number(c.getAttribute("h")) * m, p.drawShape(a, b, g, k, n, d));
	else if ("fillstroke" == f) a.fillAndStroke();
	else if ("fill" == f) a.fill();
	else if ("stroke" == f) a.stroke();
	else if ("strokewidth" == f) l = "1" == c.getAttribute("fixed") ? 1 : n, a.setStrokeWidth(Number(c.getAttribute("width")) * l);
	else if ("dashed" == f) a.setDashed("1" == c.getAttribute("dashed"));
	else if ("dashpattern" == f) {
		if (c = c.getAttribute("pattern"), null != c) {
			c = c.split(" ");
			l = [];
			for (m = 0; m < c.length; m++) 0 < c[m].length && l.push(Number(c[m]) * n);
			c = l.join(" ");
			a.setDashPattern(c)
		}
	} else "strokecolor" == f ? a.setStrokeColor(c.getAttribute("color")) : "linecap" == f ? a.setLineCap(c.getAttribute("cap")) : "linejoin" == f ? a.setLineJoin(c.getAttribute("join")) : "miterlimit" == f ? a.setMiterLimit(Number(c.getAttribute("limit"))) : "fillcolor" == f ? a.setFillColor(c.getAttribute("color")) : "alpha" == f ? a.setAlpha(c.getAttribute("alpha")) : "fontcolor" == f ? a.setFontColor(c.getAttribute("color")) : "fontstyle" == f ? a.setFontStyle(c.getAttribute("style")) : "fontfamily" == f ? a.setFontFamily(c.getAttribute("family")) : "fontsize" == f && a.setFontSize(Number(c.getAttribute("size")) * n);
	e && ("fillstroke" == f || "fill" == f || "stroke" == f) && a.setShadow(!1)
};

function Shape(a) {
	this.stencil = a;
	this.strokewidth = 1;
	this.rotation = 0;
	this.opacity = 100;
	this.flipV = this.flipH = !1
}
Shape.prototype.dialect = null;
Shape.prototype.scale = 1;
Shape.prototype.bounds = null;
Shape.prototype.points = null;
Shape.prototype.node = null;
Shape.prototype.state = null;
Shape.prototype.style = null;
Shape.prototype.boundingBox = null;
Shape.prototype.stencil = null;
Shape.prototype.svgStrokeTolerance = 8;
Shape.prototype.pointerEvents = !0;
Shape.prototype.svgPointerEvents = "all";
Shape.prototype.shapePointerEvents = !1;
Shape.prototype.stencilPointerEvents = !1;
Shape.prototype.vmlScale = 1;
Shape.prototype.outline = !1;
Shape.prototype.visible = !0;
Shape.prototype.init = function(a) {
	null == this.node && (this.node = this.create(a), null != a && a.appendChild(this.node))
};
Shape.prototype.isParseVml = function() {
	return !0
};
Shape.prototype.isHtmlAllowed = function() {
	return !1
};
Shape.prototype.getSvgScreenOffset = function() {
	return 1 == Utils.mod(Math.max(1, Math.round((this.stencil && "inherit" != this.stencil.strokewidth ? Number(this.stencil.strokewidth) : this.strokewidth) * this.scale)), 2) ? 0.5 : 0
};
Shape.prototype.create = function(a) {
	var b = null;
	return b = null != a && null != a.ownerSVGElement ? this.createSvg(a) : 8 == document.documentMode || !Client.IS_VML || this.dialect != Constants.DIALECT_VML && this.isHtmlAllowed() ? this.createHtml(a) : this.createVml(a)
};
Shape.prototype.createSvg = function() {
	return document.createElementNS(Constants.NS_SVG, "g")
};
Shape.prototype.createVml = function() {
	var a = document.createElement(Client.VML_PREFIX + ":group");
	a.style.position = "absolute";
	return a
};
Shape.prototype.createHtml = function() {
	var a = document.createElement("div");
	a.style.position = "absolute";
	return a
};
Shape.prototype.reconfigure = function() {
	this.redraw()
};
Shape.prototype.redraw = function() {
	this.updateBoundsFromPoints();
	this.visible && this.checkBounds() ? (this.node.style.visibility = "visible", this.clear(), "DIV" == this.node.nodeName && (this.isHtmlAllowed() || !Client.IS_VML) ? this.redrawHtmlShape() : this.redrawShape(), this.updateBoundingBox()) : (this.node.style.visibility = "hidden", this.boundingBox = null)
};
Shape.prototype.clear = function() {
	if (null != this.node.ownerSVGElement) for (; null != this.node.lastChild;) this.node.removeChild(this.node.lastChild);
	else this.node.style.cssText = "position:absolute;", this.node.innerHTML = ""
};
Shape.prototype.updateBoundsFromPoints = function() {
	var a = this.points;
	if (null != a && 0 < a.length && null != a[0]) {
		this.bounds = new Rectangle(Number(a[0].x), Number(a[0].y), 1, 1);
		for (var b = 1; b < this.points.length; b++) null != a[b] && this.bounds.add(new Rectangle(Number(a[b].x), Number(a[b].y), 1, 1))
	}
};
Shape.prototype.getLabelBounds = function(a) {
	return a
};
Shape.prototype.checkBounds = function() {
	return null != this.bounds && !isNaN(this.bounds.x) && !isNaN(this.bounds.y) && !isNaN(this.bounds.width) && !isNaN(this.bounds.height) && 0 < this.bounds.width && 0 < this.bounds.height
};
Shape.prototype.createVmlGroup = function() {
	var a = document.createElement(Client.VML_PREFIX + ":group");
	a.style.position = "absolute";
	a.style.width = this.node.style.width;
	a.style.height = this.node.style.height;
	return a
};
Shape.prototype.redrawShape = function() {
	var a = this.createCanvas();
	null != a && (a.pointerEvents = this.pointerEvents, this.paint(a), this.node != a.root && this.node.insertAdjacentHTML("beforeend", a.root.outerHTML), "DIV" == this.node.nodeName && 8 == document.documentMode && (this.node.style.filter = "", Utils.addTransparentBackgroundFilter(this.node)), this.destroyCanvas(a))
};
Shape.prototype.createCanvas = function() {
	var a = null;
	null != this.node.ownerSVGElement ? a = this.createSvgCanvas() : Client.IS_VML && (this.updateVmlContainer(), a = this.createVmlCanvas());
	this.outline && (a.setStrokeWidth(this.strokewidth), a.setStrokeColor(this.stroke), null != this.isDashed && a.setDashed(this.isDashed), a.setStrokeWidth = function() {}, a.setStrokeColor = function() {}, a.setFillColor = function() {}, a.setGradient = function() {}, a.setDashed = function() {});
	return a
};
Shape.prototype.createSvgCanvas = function() {
	var a = new SvgCanvas2D(this.node, !1);
	a.strokeTolerance = this.pointerEvents ? this.svgStrokeTolerance : 0;
	a.pointerEventsValue = this.svgPointerEvents;
	a.blockImagePointerEvents = Client.IS_FF;
	var b = this.getSvgScreenOffset();
	0 != b ? this.node.setAttribute("transform", "translate(" + b + "," + b + ")") : this.node.removeAttribute("transform");
	return a
};
Shape.prototype.createVmlCanvas = function() {
	var a = 8 == document.documentMode && this.isParseVml() ? this.createVmlGroup() : this.node,
		b = new VmlCanvas2D(a, !1);
	if ("" != a.tagUrn) {
		var c = Math.max(1, Math.round(this.bounds.width)),
			d = Math.max(1, Math.round(this.bounds.height));
		a.coordsize = c * this.vmlScale + "," + d * this.vmlScale;
		b.scale(this.vmlScale);
		b.vmlScale = this.vmlScale
	}
	a = this.scale;
	b.translate(-Math.round(this.bounds.x / a), -Math.round(this.bounds.y / a));
	return b
};
Shape.prototype.updateVmlContainer = function() {
	this.node.style.left = Math.round(this.bounds.x) + "px";
	this.node.style.top = Math.round(this.bounds.y) + "px";
	var a = Math.max(1, Math.round(this.bounds.width)),
		b = Math.max(1, Math.round(this.bounds.height));
	this.node.style.width = a + "px";
	this.node.style.height = b + "px";
	this.node.style.overflow = "visible"
};
Shape.prototype.redrawHtmlShape = function() {
	this.updateHtmlBounds(this.node);
	this.updateHtmlFilters(this.node);
	this.updateHtmlColors(this.node)
};
Shape.prototype.updateHtmlFilters = function(a) {
	var b = "";
	100 > this.opacity && (b += "alpha(opacity=" + this.opacity + ")");
	this.isShadow && (b += "progid:DXImageTransform.Microsoft.dropShadow (OffX='" + Math.round(Constants.SHADOW_OFFSET_X * this.scale) + "', OffY='" + Math.round(Constants.SHADOW_OFFSET_Y * this.scale) + "', Color='" + Constants.SHADOWCOLOR + "')");
	if (this.gradient) {
		var c = this.fill,
			d = this.gradient,
			e = "0",
			f = {
				east: 0,
				south: 1,
				west: 2,
				north: 3
			},
			g = null != this.direction ? f[this.direction] : 0;
		null != this.gradientDirection && (g = Utils.mod(g + f[this.gradientDirection] - 1, 4));
		1 == g ? (e = "1", f = c, c = d, d = f) : 2 == g ? (f = c, c = d, d = f) : 3 == g && (e = "1");
		b += "progid:DXImageTransform.Microsoft.gradient(startColorStr='" + c + "', endColorStr='" + d + "', gradientType='" + e + "')"
	}
	a.style.filter = b
};
Shape.prototype.updateHtmlColors = function(a) {
	var b = this.stroke;
	null != b && b != Constants.NONE ? (a.style.borderColor = b, this.isDashed ? a.style.borderStyle = "dashed" : 0 < this.strokewidth && (a.style.borderStyle = "solid"), a.style.borderWidth = Math.max(1, Math.ceil(this.strokewidth * this.scale)) + "px") : a.style.borderWidth = "0px";
	b = this.fill;
	null != b && b != Constants.NONE ? (a.style.backgroundColor = b, a.style.backgroundImage = "none") : this.pointerEvents ? a.style.backgroundColor = "transparent" : 8 == document.documentMode ? Utils.addTransparentBackgroundFilter(a) : this.setTransparentBackgroundImage(a)
};
Shape.prototype.updateHtmlBounds = function(a) {
	var b = 9 <= document.documentMode ? 0 : Math.ceil(this.strokewidth * this.scale);
	a.style.borderWidth = Math.max(1, b) + "px";
	a.style.overflow = "hidden";
	a.style.left = Math.round(this.bounds.x - b / 2) + "px";
	a.style.top = Math.round(this.bounds.y - b / 2) + "px";
	"CSS1Compat" == document.compatMode && (b = -b);
	a.style.width = Math.round(Math.max(0, this.bounds.width + b)) + "px";
	a.style.height = Math.round(Math.max(0, this.bounds.height + b)) + "px"
};
Shape.prototype.destroyCanvas = function(a) {
	if (a instanceof SvgCanvas2D) {
		for (var b in a.gradients) {
			var c = a.gradients[b];
			c.RefCount = (c.RefCount || 0) + 1
		}
		this.releaseSvgGradients(this.oldGradients);
		this.oldGradients = a.gradients
	}
};
Shape.prototype.paint = function(a) {
	var b = this.scale,
		c = this.bounds.x / b,
		d = this.bounds.y / b,
		e = this.bounds.width / b,
		f = this.bounds.height / b;
	if (this.isPaintBoundsInverted()) var g = (e - f) / 2,
		c = c + g,
		d = d - g,
		g = e,
		e = f,
		f = g;
	this.updateTransform(a, c, d, e, f);
	this.configureCanvas(a, c, d, e, f);
	g = null;
	if (null == this.stencil && null == this.points && this.shapePointerEvents || null != this.stencil && this.stencilPointerEvents) {
		var k = this.createBoundingBox();
		this.dialect == Constants.DIALECT_SVG ? (g = this.createTransparentSvgRectangle(k.x, k.y, k.width, k.height), this.node.appendChild(g)) : (k = a.createRect("rect", k.x / b, k.y / b, k.width / b, k.height / b), k.appendChild(a.createTransparentFill()), k.stroked = "false", a.root.appendChild(k))
	}
	if (null != this.stencil) this.stencil.drawShape(a, this, c, d, e, f);
	else if (a.setStrokeWidth(this.strokewidth), null != this.points) {
		c = [];
		for (d = 0; d < this.points.length; d++) null != this.points[d] && c.push(new Point(this.points[d].x / b, this.points[d].y / b));
		this.paintEdgeShape(a, c)
	} else this.paintVertexShape(a, c, d, e, f);
	null != g && (null != a.state && null != a.state.transform) && g.setAttribute("transform", a.state.transform)
};
Shape.prototype.configureCanvas = function(a, b, c, d, e) {
	var f = null;
	null != this.style && (f = this.style.dashPattern);
	a.setAlpha(this.opacity / 100);
	null != this.isShadow && a.setShadow(this.isShadow);
	null != this.isDashed && a.setDashed(this.isDashed);
	null != f && a.setDashPattern(f);
	null != this.gradient ? (b = this.getGradientBounds(a, b, c, d, e), a.setGradient(this.fill, this.gradient, b.x, b.y, b.width, b.height, this.gradientDirection)) : a.setFillColor(this.fill);
	a.setStrokeColor(this.stroke)
};
Shape.prototype.getGradientBounds = function(a, b, c, d, e) {
	return new Rectangle(b, c, d, e)
};
Shape.prototype.updateTransform = function(a, b, c, d, e) {
	a.scale(this.scale);
	a.rotate(this.getShapeRotation(), this.flipH, this.flipV, b + d / 2, c + e / 2)
};
Shape.prototype.paintVertexShape = function(a, b, c, d, e) {
	this.paintBackground(a, b, c, d, e);
	a.setShadow(!1);
	this.paintForeground(a, b, c, d, e)
};
Shape.prototype.paintBackground = function(a, b, c, d, e) {};
Shape.prototype.paintForeground = function(a, b, c, d, e) {};
Shape.prototype.paintEdgeShape = function(a, b) {};
Shape.prototype.getArcSize = function(a, b) {
	var c = Utils.getValue(this.style, Constants.STYLE_ARCSIZE, 100 * Constants.RECTANGLE_ROUNDING_FACTOR) / 100;
	return Math.min(a * c, b * c)
};
Shape.prototype.paintGlassEffect = function(a, b, c, d, e, f) {
	var g = Math.ceil(this.strokewidth / 2);
	a.setGradient("#ffffff", "#ffffff", b, c, d, 0.6 * e, "south", 0.9, 0.1);
	a.begin();
	f += 2 * g;
	this.isRounded ? (a.moveTo(b - g + f, c - g), a.quadTo(b - g, c - g, b - g, c - g + f), a.lineTo(b - g, c + 0.4 * e), a.quadTo(b + 0.5 * d, c + 0.7 * e, b + d + g, c + 0.4 * e), a.lineTo(b + d + g, c - g + f), a.quadTo(b + d + g, c - g, b + d + g - f, c - g)) : (a.moveTo(b - g, c - g), a.lineTo(b - g, c + 0.4 * e), a.quadTo(b + 0.5 * d, c + 0.7 * e, b + d + g, c + 0.4 * e), a.lineTo(b + d + g, c - g));
	a.close();
	a.fill()
};
Shape.prototype.apply = function(a) {
	this.state = a;
	this.style = a.style;
	if (null != this.style) {
		this.fill = Utils.getValue(this.style, Constants.STYLE_FILLCOLOR, this.fill);
		this.gradient = Utils.getValue(this.style, Constants.STYLE_GRADIENTCOLOR, this.gradient);
		this.gradientDirection = Utils.getValue(this.style, Constants.STYLE_GRADIENT_DIRECTION, this.gradientDirection);
		this.opacity = Utils.getValue(this.style, Constants.STYLE_OPACITY, this.opacity);
		this.stroke = Utils.getValue(this.style, Constants.STYLE_STROKECOLOR, this.stroke);
		this.strokewidth = Utils.getNumber(this.style, Constants.STYLE_STROKEWIDTH, this.strokewidth);
		this.arrowStrokewidth = Utils.getNumber(this.style, Constants.STYLE_STROKEWIDTH, this.strokewidth);
		this.spacing = Utils.getValue(this.style, Constants.STYLE_SPACING, this.spacing);
		this.startSize = Utils.getNumber(this.style, Constants.STYLE_STARTSIZE, this.startSize);
		this.endSize = Utils.getNumber(this.style, Constants.STYLE_ENDSIZE, this.endSize);
		this.startArrow = Utils.getValue(this.style, Constants.STYLE_STARTARROW, this.startArrow);
		this.endArrow = Utils.getValue(this.style, Constants.STYLE_ENDARROW, this.endArrow);
		this.rotation = Utils.getValue(this.style, Constants.STYLE_ROTATION, this.rotation);
		this.direction = Utils.getValue(this.style, Constants.STYLE_DIRECTION, this.direction);
		this.flipH = 1 == Utils.getValue(this.style, Constants.STYLE_FLIPH, 0);
		this.flipV = 1 == Utils.getValue(this.style, Constants.STYLE_FLIPV, 0);
		null != this.stencil && (this.flipH = 1 == Utils.getValue(this.style, "stencilFlipH", 0) || this.flipH, this.flipV = 1 == Utils.getValue(this.style, "stencilFlipV", 0) || this.flipV);
		if (this.direction == Constants.DIRECTION_NORTH || this.direction == Constants.DIRECTION_SOUTH) a = this.flipH, this.flipH = this.flipV, this.flipV = a;
		this.isShadow = 1 == Utils.getValue(this.style, Constants.STYLE_SHADOW, this.isShadow);
		this.isDashed = 1 == Utils.getValue(this.style, Constants.STYLE_DASHED, this.isDashed);
		this.isRounded = 1 == Utils.getValue(this.style, Constants.STYLE_ROUNDED, this.isRounded);
		this.glass = 1 == Utils.getValue(this.style, Constants.STYLE_GLASS, this.glass);
		"none" == this.fill && (this.fill = null);
		"none" == this.gradient && (this.gradient = null);
		"none" == this.stroke && (this.stroke = null)
	}
};
Shape.prototype.setCursor = function(a) {
	null == a && (a = "");
	this.cursor = a;
	null != this.node && (this.node.style.cursor = a)
};
Shape.prototype.getCursor = function() {
	return this.cursor
};
Shape.prototype.updateBoundingBox = function() {
	if (null != this.bounds) {
		var a = this.createBoundingBox();
		if (null != a) {
			this.augmentBoundingBox(a);
			var b = this.getShapeRotation();
			0 != b && (a = Utils.getBoundingBox(a, b))
		}
		this.boundingBox = a
	}
};
Shape.prototype.createBoundingBox = function() {
	var a = this.bounds.clone();
	if (null != this.stencil && (this.direction == Constants.DIRECTION_NORTH || this.direction == Constants.DIRECTION_SOUTH) || this.isPaintBoundsInverted()) {
		var b = (a.width - a.height) / 2;
		a.x += b;
		a.y -= b;
		b = a.width;
		a.width = a.height;
		a.height = b
	}
	return a
};
Shape.prototype.augmentBoundingBox = function(a) {
	this.isShadow && (a.width += Math.ceil(Constants.SHADOW_OFFSET_X * this.scale), a.height += Math.ceil(Constants.SHADOW_OFFSET_Y * this.scale));
	a.grow(this.strokewidth * this.scale / 2)
};
Shape.prototype.isPaintBoundsInverted = function() {
	return null == this.stencil && (this.direction == Constants.DIRECTION_NORTH || this.direction == Constants.DIRECTION_SOUTH)
};
Shape.prototype.getRotation = function() {
	return null != this.rotation ? this.rotation : 0
};
Shape.prototype.getTextRotation = function() {
	var a = this.getRotation();
	1 != Utils.getValue(this.style, Constants.STYLE_HORIZONTAL, 1) && (a += Text.prototype.verticalTextRotation);
	return a
};
Shape.prototype.getShapeRotation = function() {
	var a = this.getRotation();
	null != this.direction && (this.direction == Constants.DIRECTION_NORTH ? a += 270 : this.direction == Constants.DIRECTION_WEST ? a += 180 : this.direction == Constants.DIRECTION_SOUTH && (a += 90));
	return a
};
Shape.prototype.createTransparentSvgRectangle = function(a, b, c, d) {
	var e = document.createElementNS(Constants.NS_SVG, "rect");
	e.setAttribute("x", a);
	e.setAttribute("y", b);
	e.setAttribute("width", c);
	e.setAttribute("height", d);
	e.setAttribute("fill", "none");
	e.setAttribute("stroke", "none");
	e.setAttribute("pointer-events", "all");
	return e
};
Shape.prototype.setTransparentBackgroundImage = function(a) {
	a.style.backgroundImage = "url('" + Client.imageBasePath + "/transparent.gif')"
};
Shape.prototype.releaseSvgGradients = function(a) {
	if (null != a) for (var b in a) {
		var c = a[b];
		c.RefCount = (c.RefCount || 0) - 1;
		0 == c.RefCount && null != c.parentNode && c.parentNode.removeChild(c)
	}
};
Shape.prototype.destroy = function() {
	null != this.node && (Event.release(this.node), null != this.node.parentNode && this.node.parentNode.removeChild(this.node), this.node = null);
	this.releaseSvgGradients(this.oldGradients);
	this.oldGradients = null
};
var StencilRegistry = {
	stencils: [],
	addStencil: function(a, b) {
		StencilRegistry.stencils[a] = b
	},
	getStencil: function(a) {
		return StencilRegistry.stencils[a]
	}
},
	Marker = {
		markers: [],
		addMarker: function(a, b) {
			Marker.markers[a] = b
		},
		createMarker: function(a, b, c, d, e, f, g, k, l, m) {
			var n = Marker.markers[c];
			return null != n ? n(a, b, c, d, e, f, g, k, l, m) : null
		}
	};
(function() {
	function a(a, b, e, f, g, k, l, m, n, p) {
		b = 1.118 * g * n;
		m = 1.118 * k * n;
		g *= l + n;
		k *= l + n;
		var q = f.clone();
		q.x -= b;
		q.y -= m;
		l = e != Constants.ARROW_CLASSIC ? 1 : 0.75;
		f.x += -g * l - b;
		f.y += -k * l - m;
		return function() {
			a.begin();
			a.moveTo(q.x, q.y);
			a.lineTo(q.x - g - k / 2, q.y - k + g / 2);
			e == Constants.ARROW_CLASSIC && a.lineTo(q.x - 3 * g / 4, q.y - 3 * k / 4);
			a.lineTo(q.x + k / 2 - g, q.y - k - g / 2);
			a.close();
			p ? a.fillAndStroke() : a.stroke()
		}
	}
	function b(a, b, e, f, g, k, l, m, n, p) {
		m = e == Constants.ARROW_DIAMOND ? 0.7071 : 0.9862;
		b = g * n * m;
		m *= k * n;
		g *= l + n;
		k *= l + n;
		var q = f.clone();
		q.x -= b;
		q.y -= m;
		f.x += -g - b;
		f.y += -k - m;
		var r = e == Constants.ARROW_DIAMOND ? 2 : 3.4;
		return function() {
			a.begin();
			a.moveTo(q.x, q.y);
			a.lineTo(q.x - g / 2 - k / r, q.y + g / r - k / 2);
			a.lineTo(q.x - g, q.y - k);
			a.lineTo(q.x - g / 2 + k / r, q.y - k / 2 - g / r);
			a.close();
			p ? a.fillAndStroke() : a.stroke()
		}
	}
	Marker.addMarker("classic", a);
	Marker.addMarker("block", a);
	Marker.addMarker("open", function(a, b, e, f, g, k, l, m, n, p) {
		b = 1.118 * g * n;
		e = 1.118 * k * n;
		g *= l + n;
		k *= l + n;
		var q = f.clone();
		q.x -= b;
		q.y -= e;
		f.x += 2 * -b;
		f.y += 2 * -e;
		return function() {
			a.begin();
			a.moveTo(q.x - g - k / 2, q.y - k + g / 2);
			a.lineTo(q.x, q.y);
			a.lineTo(q.x + k / 2 - g, q.y - k - g / 2);
			a.stroke()
		}
	});
	Marker.addMarker("oval", function(a, b, e, f, g, k, l, m, n, p) {
		var q = l / 2,
			r = f.clone();
		f.x -= g * q;
		f.y -= k * q;
		return function() {
			a.ellipse(r.x - q, r.y - q, l, l);
			p ? a.fillAndStroke() : a.stroke()
		}
	});
	Marker.addMarker("diamond", b);
	Marker.addMarker("diamondThin", b)
})();

function Actor(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(Actor, Shape);
Actor.prototype.paintVertexShape = function(a, b, c, d, e) {
	a.translate(b, c);
	a.begin();
	this.redrawPath(a, b, c, d, e);
	a.fillAndStroke()
};
Actor.prototype.redrawPath = function(a, b, c, d, e) {
	b = d / 3;
	a.moveTo(0, e);
	a.curveTo(0, 3 * e / 5, 0, 2 * e / 5, d / 2, 2 * e / 5);
	a.curveTo(d / 2 - b, 2 * e / 5, d / 2 - b, 0, d / 2, 0);
	a.curveTo(d / 2 + b, 0, d / 2 + b, 2 * e / 5, d / 2, 2 * e / 5);
	a.curveTo(d, 2 * e / 5, d, 3 * e / 5, d, e);
	a.close()
};

function Cloud(a, b, c, d) {
	Actor.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(Cloud, Actor);
Cloud.prototype.redrawPath = function(a, b, c, d, e) {
	a.moveTo(0.25 * d, 0.25 * e);
	a.curveTo(0.05 * d, 0.25 * e, 0, 0.5 * e, 0.16 * d, 0.55 * e);
	a.curveTo(0, 0.66 * e, 0.18 * d, 0.9 * e, 0.31 * d, 0.8 * e);
	a.curveTo(0.4 * d, e, 0.7 * d, e, 0.8 * d, 0.8 * e);
	a.curveTo(d, 0.8 * e, d, 0.6 * e, 0.875 * d, 0.5 * e);
	a.curveTo(d, 0.3 * e, 0.8 * d, 0.1 * e, 0.625 * d, 0.2 * e);
	a.curveTo(0.5 * d, 0.05 * e, 0.3 * d, 0.05 * e, 0.25 * d, 0.25 * e);
	a.close()
};

function RectangleShape(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(RectangleShape, Shape);
RectangleShape.prototype.isHtmlAllowed = function() {
	return !this.isRounded && !this.glass && 0 == this.rotation
};
RectangleShape.prototype.paintBackground = function(a, b, c, d, e) {
	if (this.isRounded) {
		var f = Utils.getValue(this.style, Constants.STYLE_ARCSIZE, 100 * Constants.RECTANGLE_ROUNDING_FACTOR) / 100,
			f = Math.min(d * f, e * f);
		a.roundrect(b, c, d, e, f, f)
	} else a.rect(b, c, d, e);
	a.fillAndStroke()
};
RectangleShape.prototype.paintForeground = function(a, b, c, d, e) {
	this.glass && this.paintGlassEffect(a, b, c, d, e, this.getArcSize(d + this.strokewidth, e + this.strokewidth))
};

function Ellipse(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(Ellipse, Shape);
Ellipse.prototype.paintVertexShape = function(a, b, c, d, e) {
	a.ellipse(b, c, d, e);
	a.fillAndStroke()
};

function DoubleEllipse(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(DoubleEllipse, Shape);
DoubleEllipse.prototype.vmlScale = 10;
DoubleEllipse.prototype.paintBackground = function(a, b, c, d, e) {
	a.ellipse(b, c, d, e);
	a.fillAndStroke()
};
DoubleEllipse.prototype.paintForeground = function(a, b, c, d, e) {
	if (!this.outline) {
		var f = Utils.getValue(this.style, Constants.STYLE_MARGIN, Math.min(3 + this.strokewidth, Math.min(d / 5, e / 5)));
		d -= 2 * f;
		e -= 2 * f;
		0 < d && 0 < e && a.ellipse(b + f, c + f, d, e);
		a.stroke()
	}
};

function Rhombus(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(Rhombus, Shape);
Rhombus.prototype.paintVertexShape = function(a, b, c, d, e) {
	var f = d / 2,
		g = e / 2;
	a.begin();
	a.moveTo(b + f, c);
	a.lineTo(b + d, c + g);
	a.lineTo(b + f, c + e);
	a.lineTo(b, c + g);
	a.close();
	a.fillAndStroke()
};

function Polyline(a, b, c) {
	Shape.call(this);
	this.points = a;
	this.stroke = b;
	this.strokewidth = null != c ? c : 1
}
Utils.extend(Polyline, Shape);
Polyline.prototype.getRotation = function() {
	return 0
};
Polyline.prototype.getShapeRotation = function() {
	return 0
};
Polyline.prototype.isPaintBoundsInverted = function() {
	return !1
};
Polyline.prototype.paintEdgeShape = function(a, b) {
	null == this.style || 1 != this.style[Constants.STYLE_CURVED] ? this.paintLine(a, b, this.isRounded) : this.paintCurvedLine(a, b)
};
Polyline.prototype.paintLine = function(a, b, c) {
	var d = Utils.getValue(this.style, Constants.STYLE_ARCSIZE, Constants.LINE_ARCSIZE) / 2,
		e = b[0],
		f = b[b.length - 1];
	a.begin();
	a.moveTo(e.x, e.y);
	for (var g = 1; g < b.length - 1; g++) {
		var k = b[g],
			l = e.x - k.x,
			e = e.y - k.y;
		if (c && g < b.length - 1 && (0 != l || 0 != e)) {
			var m = Math.sqrt(l * l + e * e),
				l = l * Math.min(d, m / 2) / m,
				e = e * Math.min(d, m / 2) / m;
			a.lineTo(k.x + l, k.y + e);
			for (e = b[g + 1]; g < b.length - 2 && 0 == Math.round(e.x - k.x) && 0 == Math.round(e.y - k.y);) e = b[g + 2], g++;
			l = e.x - k.x;
			e = e.y - k.y;
			m = Math.max(1, Math.sqrt(l * l + e * e));
			l = l * Math.min(d, m / 2) / m;
			e = e * Math.min(d, m / 2) / m;
			l = k.x + l;
			e = k.y + e;
			a.quadTo(k.x, k.y, l, e);
			k = new Point(l, e)
		} else a.lineTo(k.x, k.y);
		e = k
	}
	a.lineTo(f.x, f.y);
	a.stroke()
};
Polyline.prototype.paintCurvedLine = function(a, b) {
	a.begin();
	var c = b[0],
		d = b.length;
	a.moveTo(c.x, c.y);
	for (c = 1; c < d - 2; c++) {
		var e = b[c],
			f = b[c + 1];
		a.quadTo(e.x, e.y, (e.x + f.x) / 2, (e.y + f.y) / 2)
	}
	e = b[d - 2];
	f = b[d - 1];
	a.quadTo(e.x, e.y, f.x, f.y);
	a.stroke()
};

function Arrow(a, b, c, d, e, f, g) {
	Shape.call(this);
	this.points = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1;
	this.arrowWidth = null != e ? e : Constants.ARROW_WIDTH;
	this.spacing = null != f ? f : Constants.ARROW_SPACING;
	this.endSize = null != g ? g : Constants.ARROW_SIZE
}
Utils.extend(Arrow, Shape);
Arrow.prototype.paintEdgeShape = function(a, b) {
	var c = Constants.ARROW_SPACING,
		d = Constants.ARROW_WIDTH,
		e = Constants.ARROW_SIZE,
		f = b[0],
		g = b[b.length - 1],
		k = g.x - f.x,
		l = g.y - f.y,
		m = Math.sqrt(k * k + l * l),
		n = m - 2 * c - e,
		k = k / m,
		l = l / m,
		m = d * l / 3,
		d = -d * k / 3,
		e = f.x - m / 2 + c * k,
		f = f.y - d / 2 + c * l,
		p = e + m,
		q = f + d,
		r = p + n * k,
		n = q + n * l,
		s = r + m,
		t = n + d,
		u = s - 3 * m,
		x = t - 3 * d;
	a.begin();
	a.moveTo(e, f);
	a.lineTo(p, q);
	a.lineTo(r, n);
	a.lineTo(s, t);
	a.lineTo(g.x - c * k, g.y - c * l);
	a.lineTo(u, x);
	a.lineTo(u + m, x + d);
	a.close();
	a.fillAndStroke()
};

function Text(a, b, c, d, e, f, g, k, l, m, n, p, q, r, s, t, u, x, y, v) {
	Shape.call(this);
	this.value = a;
	this.bounds = b;
	this.color = null != e ? e : "black";
	this.align = null != c ? c : "";
	this.valign = null != d ? d : "";
	this.family = null != f ? f : Constants.DEFAULT_FONTFAMILY;
	this.size = null != g ? g : Constants.DEFAULT_FONTSIZE;
	this.fontStyle = null != k ? k : Constants.DEFAULT_FONTSTYLE;
	this.spacing = parseInt(l || 2);
	this.spacingTop = this.spacing + parseInt(m || 0);
	this.spacingRight = this.spacing + parseInt(n || 0);
	this.spacingBottom = this.spacing + parseInt(p || 0);
	this.spacingLeft = this.spacing + parseInt(q || 0);
	this.horizontal = null != r ? r : !0;
	this.background = s;
	this.border = t;
	this.wrap = null != u ? u : !1;
	this.clipped = null != x ? x : !1;
	this.overflow = null != y ? y : "visible";
	this.labelPadding = null != v ? v : 0;
	this.rotation = 0;
	this.updateMargin()
}
Utils.extend(Text, Shape);
Text.prototype.baseSpacingTop = 0;
Text.prototype.baseSpacingBottom = 0;
Text.prototype.baseSpacingLeft = 0;
Text.prototype.baseSpacingRight = 0;
Text.prototype.replaceLinefeeds = !0;
Text.prototype.verticalTextRotation = -90;
Text.prototype.ignoreClippedStringSize = !0;
Text.prototype.ignoreStringSize = !1;
Text.prototype.textWidthPadding = 8 == document.documentMode ? 4 : 3;
Text.prototype.isParseVml = function() {
	return !1
};
Text.prototype.isHtmlAllowed = function() {
	return 8 != document.documentMode
};
Text.prototype.getSvgScreenOffset = function() {
	return 0
};
Text.prototype.checkBounds = function() {
	return null != this.bounds && !isNaN(this.bounds.x) && !isNaN(this.bounds.y) && !isNaN(this.bounds.width) && !isNaN(this.bounds.height)
};
Text.prototype.apply = function(a) {
	Shape.prototype.apply.apply(this, arguments);
	null != this.style && (this.fontStyle = Utils.getValue(this.style, Constants.STYLE_FONTSTYLE, this.fontStyle), this.family = Utils.getValue(this.style, Constants.STYLE_FONTFAMILY, this.family), this.size = Utils.getValue(this.style, Constants.STYLE_FONTSIZE, this.size), this.color = Utils.getValue(this.style, Constants.STYLE_FONTCOLOR, this.color), this.align = Utils.getValue(this.style, Constants.STYLE_ALIGN, this.align), this.valign = Utils.getValue(this.style, Constants.STYLE_VERTICAL_ALIGN, this.valign), this.spacingTop = Utils.getValue(this.style, Constants.STYLE_SPACING_TOP, this.spacingTop), this.spacingRight = Utils.getValue(this.style, Constants.STYLE_SPACING_RIGHT, this.spacingRight), this.spacingBottom = Utils.getValue(this.style, Constants.STYLE_SPACING_BOTTOM, this.spacingBottom), this.spacingLeft = Utils.getValue(this.style, Constants.STYLE_SPACING_LEFT, this.spacingLeft), this.horizontal = Utils.getValue(this.style, Constants.STYLE_HORIZONTAL, this.horizontal), this.background = Utils.getValue(this.style, Constants.STYLE_LABEL_BACKGROUNDCOLOR, this.background), this.border = Utils.getValue(this.style, Constants.STYLE_LABEL_BORDERCOLOR, this.border), this.updateMargin())
};
Text.prototype.updateBoundingBox = function() {
	var a = this.node;
	this.boundingBox = this.bounds.clone();
	var b = this.getTextRotation(),
		c = null != this.style ? Utils.getValue(this.style, Constants.STYLE_LABEL_POSITION, Constants.ALIGN_CENTER) : null,
		d = null != this.style ? Utils.getValue(this.style, Constants.STYLE_VERTICAL_LABEL_POSITION, Constants.ALIGN_MIDDLE) : null;
	if (!this.ignoreStringSize && null != a && "fill" != this.overflow && (!this.clipped || !this.ignoreClippedStringSize || c != Constants.ALIGN_CENTER || d != Constants.ALIGN_MIDDLE)) {
		d = c = null;
		if (null != a.ownerSVGElement) if (null != a.firstChild && null != a.firstChild.firstChild && "foreignObject" == a.firstChild.firstChild.nodeName) a = a.firstChild.firstChild, c = parseInt(a.getAttribute("width")) * this.scale, d = parseInt(a.getAttribute("height")) * this.scale;
		else try {
			var e = a.getBBox();
			if ("string" == typeof this.value && 0 == Utils.trim(this.value) || 0 == e.width && 0 == e.height) return;
			this.boundingBox = new Rectangle(e.x, e.y, e.width, e.height);
			b = 0
		} catch (f) {} else c = null != this.state ? this.state.view.textDiv : null, null != this.offsetWidth && null != this.offsetHeight ? (c = this.offsetWidth * this.scale, d = this.offsetHeight * this.scale) : (null != c && (this.updateFont(c), this.updateSize(c, !1), this.updateInnerHtml(c), a = c), e = a, 8 == document.documentMode ? (d = Math.round(this.bounds.width / this.scale), this.wrap && 0 < d ? (a.whiteSpace = "normal", a = e.getElementsByTagName("div"), 0 < a.length && (e = a[a.length - 1]), c = e.offsetWidth + 2, a = this.node.getElementsByTagName("div"), this.clipped && (c = Math.min(d, c)), 1 < a.length && (a[a.length - 2].style.width = c + "px")) : a.whiteSpace = "nowrap") : null != e.firstChild && "DIV" == e.firstChild.nodeName && (e = e.firstChild), c = (e.offsetWidth + this.textWidthPadding) * this.scale, d = e.offsetHeight * this.scale);
		null != c && null != d && (this.boundingBox = new Rectangle(this.bounds.x + this.margin.x * c, this.bounds.y + this.margin.y * d, c, d))
	} else this.boundingBox.x += this.margin.x * this.boundingBox.width, this.boundingBox.y += this.margin.y * this.boundingBox.height;
	null != this.boundingBox && 0 != b && (b = Utils.getBoundingBox(this.boundingBox, b), this.boundingBox.x = b.x, this.boundingBox.y = b.y, Client.IS_QUIRKS || (this.boundingBox.width = b.width, this.boundingBox.height = b.height))
};
Text.prototype.getShapeRotation = function() {
	return 0
};
Text.prototype.getTextRotation = function() {
	return null != this.state && null != this.state.shape ? this.state.shape.getTextRotation() : 0
};
Text.prototype.isPaintBoundsInverted = function() {
	return !this.horizontal && null != this.state && this.state.view.graph.model.isVertex(this.state.cell)
};
Text.prototype.configureCanvas = function(a, b, c, d, e) {
	Shape.prototype.configureCanvas.apply(this, arguments);
	a.setFontColor(this.color);
	a.setFontBackgroundColor(this.background);
	a.setFontBorderColor(this.border);
	a.setFontFamily(this.family);
	a.setFontSize(this.size);
	a.setFontStyle(this.fontStyle)
};
Text.prototype.updateVmlContainer = function() {
	this.node.style.left = Math.round(this.bounds.x) + "px";
	this.node.style.top = Math.round(this.bounds.y) + "px";
	this.node.style.width = "1px";
	this.node.style.height = "1px";
	this.node.style.overflow = "visible"
};
Text.prototype.paint = function(a) {
	var b = this.scale,
		c = this.bounds.x / b,
		d = this.bounds.y / b,
		e = this.bounds.width / b,
		b = this.bounds.height / b;
	this.updateTransform(a, c, d, e, b);
	this.configureCanvas(a, c, d, e, b);
	var f = Utils.isNode(this.value) || this.dialect == Constants.DIALECT_STRICTHTML,
		g = f || a instanceof VmlCanvas2D ? "html" : "",
		k = this.value;
	!f && "html" == g && (k = Utils.htmlEntities(k, !1));
	k = !Utils.isNode(this.value) && this.replaceLinefeeds && "html" == g ? k.replace(/\n/g, "<br/>") : k;
	a.text(c, d, e, b, k, this.align, this.valign, this.wrap, g, this.overflow, this.clipped, this.getTextRotation())
};
Text.prototype.redrawHtmlShape = function() {
	var a = this.node.style;
	a.opacity = 1 > this.opacity ? this.opacity : "";
	a.whiteSpace = "normal";
	a.overflow = "";
	a.width = "";
	a.height = "";
	this.updateValue();
	this.updateFont(this.node);
	this.updateSize(this.node, null == this.state || null == this.state.view.textDiv);
	this.offsetHeight = this.offsetWidth = null;
	Client.IS_IE && (null == document.documentMode || 8 >= document.documentMode) ? this.updateHtmlFilter() : this.updateHtmlTransform()
};
Text.prototype.updateHtmlTransform = function() {
	var a = this.getTextRotation(),
		b = this.node.style,
		c = this.margin.x,
		d = this.margin.y;
	0 != a ? (Utils.setPrefixedStyle(b, "transformOrigin", 100 * -c + "% " + 100 * -d + "%"), Utils.setPrefixedStyle(b, "transform", "translate(" + 100 * c + "%," + 100 * d + "%)scale(" + this.scale + ") rotate(" + a + "deg)")) : (Utils.setPrefixedStyle(b, "transformOrigin", "0% 0%"), Utils.setPrefixedStyle(b, "transform", "scale(" + this.scale + ")translate(" + 100 * c + "%," + 100 * d + "%)"));
	b.left = Math.round(this.bounds.x) + "px";
	b.top = Math.round(this.bounds.y) + "px"
};
Text.prototype.updateInnerHtml = function(a) {
	if (Utils.isNode(this.value)) a.innerHTML = this.value.outerHTML;
	else {
		var b = this.value;
		this.dialect != Constants.DIALECT_STRICTHTML && (b = Utils.htmlEntities(b, !1));
		b = this.replaceLinefeeds ? b.replace(/\n/g, "<br/>") : b;
		a.innerHTML = '<div style="display:inline-block;_display:inline;">' + b + "</div>"
	}
};
Text.prototype.updateHtmlFilter = function() {
	var a = this.node.style,
		b = this.margin.x,
		c = this.margin.y,
		d = this.scale;
	a.filter = "";
	var e = 0,
		f = 0,
		g = null != this.state ? this.state.view.textDiv : null,
		k = this.node;
	if (null != g) {
		g.style.overflow = "";
		g.style.height = "";
		g.style.width = "";
		this.updateFont(g);
		this.updateSize(g, !1);
		this.updateInnerHtml(g);
		var l = Math.round(this.bounds.width / this.scale);
		this.wrap && 0 < l ? (g.whiteSpace = "normal", e = l, this.clipped && (e = Math.min(e, this.bounds.width)), g.style.width = e + "px") : g.whiteSpace = "nowrap";
		k = g;
		null != k.firstChild && "DIV" == k.firstChild.nodeName && (k = k.firstChild);
		!this.clipped && (this.wrap && 0 < l) && (e = k.offsetWidth + this.textWidthPadding, g.style.width = e + "px");
		f = k.offsetHeight + 2;
		Client.IS_QUIRKS && (null != this.border && this.border != Constants.NONE) && (f += 3)
	} else null != k.firstChild && "DIV" == k.firstChild.nodeName && (k = k.firstChild, f = k.offsetHeight);
	e = k.offsetWidth + this.textWidthPadding;
	this.clipped && (f = Math.min(f, this.bounds.height));
	this.offsetWidth = e;
	this.offsetHeight = f;
	l = this.bounds.width / d;
	g = this.bounds.height / d;
	Client.IS_QUIRKS && (this.clipped || "width" == this.overflow && 0 < g) ? (g = Math.min(g, f), a.height = Math.round(g) + "px") : g = f;
	if ("fill" != this.overflow && "width" != this.overflow && (this.clipped && (e = Math.min(l, e)), l = e, Client.IS_QUIRKS && this.clipped || this.wrap)) a.width = Math.round(l) + "px";
	g *= d;
	l *= d;
	e = this.getTextRotation() * (Math.PI / 180);
	f = parseFloat(parseFloat(Math.cos(e)).toFixed(8));
	k = parseFloat(parseFloat(Math.sin(-e)).toFixed(8));
	e %= 2 * Math.PI;
	0 > e && (e += 2 * Math.PI);
	e %= Math.PI;
	e > Math.PI / 2 && (e = Math.PI - e);
	var m = Math.cos(e),
		n = Math.sin(-e),
		b = l * -(b + 0.5),
		c = g * -(c + 0.5),
		p = (g - g * m + l * n) / 2 + k * b - f * c;
	0 != e && (a.filter = "progid:DXImageTransform.Microsoft.Matrix(M11=" + f + ", M12=" + k + ", M21=" + -k + ", M22=" + f + ", sizingMethod='auto expand')");
	a.zoom = d;
	a.left = Math.round(this.bounds.x + ((l - l * m + g * n) / 2 - f * b - k * c) - l / 2) + "px";
	a.top = Math.round(this.bounds.y + p - g / 2) + "px"
};
Text.prototype.updateValue = function() {
	if (Utils.isNode(this.value)) this.node.innerHTML = "", this.node.appendChild(this.value);
	else {
		var a = this.value;
		this.dialect != Constants.DIALECT_STRICTHTML && (a = Utils.htmlEntities(a, !1));
		var a = this.replaceLinefeeds ? a.replace(/\n/g, "<br/>") : a,
			b = null != this.background && this.background != Constants.NONE ? this.background : null,
			c = null != this.border && this.border != Constants.NONE ? this.border : null;
		if ("fill" == this.overflow || "width" == this.overflow) null != b && (this.node.style.backgroundColor = b), null != c && (this.node.style.border = "1px solid " + c);
		else {
			var d = "";
			null != b && (d += "background-color:" + b + ";");
			null != c && (d += "border:1px solid " + c + ";");
			a = '<div style="zoom:1;' + d + "display:inline-block;_display:inline;text-decoration:inherit;padding-bottom:1px;padding-right:1px;line-height:" + this.node.style.lineHeight + '">' + a + "</div>";
			this.node.style.lineHeight = ""
		}
		this.node.innerHTML = a
	}
};
Text.prototype.updateFont = function(a) {
	a = a.style;
	a.lineHeight = Constants.ABSOLUTE_LINE_HEIGHT ? Math.round(this.size * Constants.LINE_HEIGHT) + "px" : Constants.LINE_HEIGHT;
	a.fontSize = Math.round(this.size) + "px";
	a.fontFamily = this.family;
	a.verticalAlign = "top";
	a.color = this.color;
	a.fontWeight = (this.fontStyle & Constants.FONT_BOLD) == Constants.FONT_BOLD ? "bold" : "";
	a.fontStyle = (this.fontStyle & Constants.FONT_ITALIC) == Constants.FONT_ITALIC ? "italic" : "";
	a.textDecoration = (this.fontStyle & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE ? "underline" : "";
	a.textAlign = this.align == Constants.ALIGN_CENTER ? "center" : this.align == Constants.ALIGN_RIGHT ? "right" : "left"
};
Text.prototype.updateSize = function(a, b) {
	var c = Math.round(this.bounds.width / this.scale),
		d = Math.round(this.bounds.height / this.scale),
		e = a.style;
	this.clipped ? (e.overflow = "hidden", e.width = c + "px", Client.IS_QUIRKS || (e.maxHeight = d + "px")) : "fill" == this.overflow ? (e.width = c + "px", e.height = d + "px") : "width" == this.overflow && (e.width = c + "px", e.maxHeight = d + "px");
	this.wrap && 0 < c ? (e.whiteSpace = "normal", e.width = c + "px", b && (d = a, null != d.firstChild && "DIV" == d.firstChild.nodeName && (d = d.firstChild), d = d.offsetWidth + 3, this.clipped && (d = Math.min(d, c)), e.width = d + "px")) : e.whiteSpace = "nowrap"
};
Text.prototype.updateMargin = function() {
	this.margin = Utils.getAlignmentAsPoint(this.align, this.valign)
};
Text.prototype.getSpacing = function() {
	var a = 0,
		b = 0,
		a = this.align == Constants.ALIGN_CENTER ? (this.spacingLeft - this.spacingRight) / 2 : this.align == Constants.ALIGN_RIGHT ? -this.spacingRight - this.baseSpacingRight : this.spacingLeft + this.baseSpacingLeft,
		b = this.valign == Constants.ALIGN_MIDDLE ? (this.spacingTop - this.spacingBottom) / 2 : this.valign == Constants.ALIGN_BOTTOM ? -this.spacingBottom - this.baseSpacingBottom : this.spacingTop + this.baseSpacingTop;
	return new Point(a, b)
};

function Triangle() {
	Actor.call(this)
}
Utils.extend(Triangle, Actor);
Triangle.prototype.redrawPath = function(a, b, c, d, e) {
	a.moveTo(0, 0);
	a.lineTo(d, 0.5 * e);
	a.lineTo(0, e);
	a.close()
};

function Hexagon() {
	Actor.call(this)
}
Utils.extend(Hexagon, Actor);
Hexagon.prototype.redrawPath = function(a, b, c, d, e) {
	a.moveTo(0.25 * d, 0);
	a.lineTo(0.75 * d, 0);
	a.lineTo(d, 0.5 * e);
	a.lineTo(0.75 * d, e);
	a.lineTo(0.25 * d, e);
	a.lineTo(0, 0.5 * e);
	a.close()
};

function Line(a, b, c) {
	Shape.call(this);
	this.bounds = a;
	this.stroke = b;
	this.strokewidth = null != c ? c : 1
}
Utils.extend(Line, Shape);
Line.prototype.paintVertexShape = function(a, b, c, d, e) {
	c += e / 2;
	a.begin();
	a.moveTo(b, c);
	a.lineTo(b + d, c);
	a.stroke()
};

function ImageShape(a, b, c, d, e) {
	Shape.call(this);
	this.bounds = a;
	this.image = b;
	this.fill = c;
	this.stroke = d;
	this.strokewidth = null != e ? e : 1;
	this.shadow = !1
}
Utils.extend(ImageShape, RectangleShape);
ImageShape.prototype.preserveImageAspect = !0;
ImageShape.prototype.getSvgScreenOffset = function() {
	return !Client.IS_IE ? 0.5 : 0
};
ImageShape.prototype.apply = function(a) {
	Shape.prototype.apply.apply(this, arguments);
	this.gradient = this.stroke = this.fill = null;
	null != this.style && (this.preserveImageAspect = 1 == Utils.getNumber(this.style, Constants.STYLE_IMAGE_ASPECT, 1), this.flipH = this.flipH || 1 == Utils.getValue(this.style, "imageFlipH", 0), this.flipV = this.flipV || 1 == Utils.getValue(this.style, "imageFlipV", 0))
};
ImageShape.prototype.isHtmlAllowed = function() {
	return !this.preserveImageAspect
};
ImageShape.prototype.createHtml = function() {
	var a = document.createElement("div");
	a.style.position = "absolute";
	return a
};
ImageShape.prototype.paintVertexShape = function(a, b, c, d, e) {
	if (null != this.image) {
		var f = Utils.getValue(this.style, Constants.STYLE_IMAGE_BACKGROUND, null),
			g = Utils.getValue(this.style, Constants.STYLE_IMAGE_BORDER, null);
		if (null != f || null != g) a.setFillColor(f), a.setStrokeColor(g), a.rect(b, c, d, e), a.fillAndStroke();
		a.image(b, c, d, e, this.image, this.preserveImageAspect, !1, !1)
	} else RectangleShape.prototype.paintBackground.apply(this, arguments)
};
ImageShape.prototype.redrawHtmlShape = function() {
	this.node.style.left = Math.round(this.bounds.x) + "px";
	this.node.style.top = Math.round(this.bounds.y) + "px";
	this.node.style.width = Math.max(0, Math.round(this.bounds.width)) + "px";
	this.node.style.height = Math.max(0, Math.round(this.bounds.height)) + "px";
	this.node.innerHTML = "";
	if (null != this.image) {
		var a = Utils.getValue(this.style, Constants.STYLE_IMAGE_BACKGROUND, ""),
			b = Utils.getValue(this.style, Constants.STYLE_IMAGE_BORDER, "");
		this.node.style.backgroundColor = a;
		this.node.style.borderColor = b;
		a = document.createElement(Client.IS_IE6 || (null == document.documentMode || 8 >= document.documentMode) && 0 != this.rotation ? Client.VML_PREFIX + ":image" : "img");
		a.style.position = "absolute";
		a.src = this.image;
		b = 100 > this.opacity ? "alpha(opacity=" + this.opacity + ")" : "";
		this.node.style.filter = b;
		this.flipH && this.flipV ? b += "progid:DXImageTransform.Microsoft.BasicImage(rotation=2)" : this.flipH ? b += "progid:DXImageTransform.Microsoft.BasicImage(mirror=1)" : this.flipV && (b += "progid:DXImageTransform.Microsoft.BasicImage(rotation=2, mirror=1)");
		a.style.filter != b && (a.style.filter = b);
		"image" == a.nodeName ? a.style.rotation = this.rotation : 0 != this.rotation ? Utils.setPrefixedStyle(a.style, "transform", "rotate(" + this.rotation + "deg)") : Utils.setPrefixedStyle(a.style, "transform", "");
		a.style.width = this.node.style.width;
		a.style.height = this.node.style.height;
		this.node.style.backgroundImage = "";
		this.node.appendChild(a)
	} else this.setTransparentBackgroundImage(this.node)
};

function Label(a, b, c, d) {
	RectangleShape.call(this, a, b, c, d)
}
Utils.extend(Label, RectangleShape);
Label.prototype.imageSize = Constants.DEFAULT_IMAGESIZE;
Label.prototype.spacing = 2;
Label.prototype.indicatorSize = 10;
Label.prototype.indicatorSpacing = 2;
Label.prototype.init = function(a) {
	Shape.prototype.init.apply(this, arguments);
	null != this.indicatorShape && (this.indicator = new this.indicatorShape, this.indicator.dialect = this.dialect, this.indicator.init(this.node))
};
Label.prototype.redraw = function() {
	null != this.indicator && (this.indicator.fill = this.indicatorColor, this.indicator.stroke = this.indicatorStrokeColor, this.indicator.gradient = this.indicatorGradientColor, this.indicator.direction = this.indicatorDirection);
	Shape.prototype.redraw.apply(this, arguments)
};
Label.prototype.isHtmlAllowed = function() {
	return RectangleShape.prototype.isHtmlAllowed.apply(this, arguments) && null == this.indicatorColor && null == this.indicatorShape
};
Label.prototype.paintForeground = function(a, b, c, d, e) {
	this.paintImage(a, b, c, d, e);
	this.paintIndicator(a, b, c, d, e);
	RectangleShape.prototype.paintForeground.apply(this, arguments)
};
Label.prototype.paintImage = function(a, b, c, d, e) {
	null != this.image && (b = this.getImageBounds(b, c, d, e), a.image(b.x, b.y, b.width, b.height, this.image, !1, !1, !1))
};
Label.prototype.getImageBounds = function(a, b, c, d) {
	var e = Utils.getValue(this.style, Constants.STYLE_IMAGE_ALIGN, Constants.ALIGN_LEFT),
		f = Utils.getValue(this.style, Constants.STYLE_IMAGE_VERTICAL_ALIGN, Constants.ALIGN_MIDDLE),
		g = Utils.getNumber(this.style, Constants.STYLE_IMAGE_WIDTH, Constants.DEFAULT_IMAGESIZE),
		k = Utils.getNumber(this.style, Constants.STYLE_IMAGE_HEIGHT, Constants.DEFAULT_IMAGESIZE),
		l = Utils.getNumber(this.style, Constants.STYLE_SPACING, this.spacing) + 5;
	a = e == Constants.ALIGN_CENTER ? a + (c - g) / 2 : e == Constants.ALIGN_RIGHT ? a + (c - g - l) : a + l;
	b = f == Constants.ALIGN_TOP ? b + l : f == Constants.ALIGN_BOTTOM ? b + (d - k - l) : b + (d - k) / 2;
	return new Rectangle(a, b, g, k)
};
Label.prototype.paintIndicator = function(a, b, c, d, e) {
	null != this.indicator ? (this.indicator.bounds = this.getIndicatorBounds(b, c, d, e), this.indicator.paint(a)) : null != this.indicatorImage && (b = this.getIndicatorBounds(b, c, d, e), a.image(b.x, b.y, b.width, b.height, this.indicatorImage, !1, !1, !1))
};
Label.prototype.getIndicatorBounds = function(a, b, c, d) {
	var e = Utils.getValue(this.style, Constants.STYLE_IMAGE_ALIGN, Constants.ALIGN_LEFT),
		f = Utils.getValue(this.style, Constants.STYLE_IMAGE_VERTICAL_ALIGN, Constants.ALIGN_MIDDLE),
		g = Utils.getNumber(this.style, Constants.STYLE_INDICATOR_WIDTH, this.indicatorSize),
		k = Utils.getNumber(this.style, Constants.STYLE_INDICATOR_HEIGHT, this.indicatorSize),
		l = this.spacing + 5;
	a = e == Constants.ALIGN_RIGHT ? a + (c - g - l) : e == Constants.ALIGN_CENTER ? a + (c - g) / 2 : a + l;
	b = f == Constants.ALIGN_BOTTOM ? b + (d - k - l) : f == Constants.ALIGN_TOP ? b + l : b + (d - k) / 2;
	return new Rectangle(a, b, g, k)
};
Label.prototype.redrawHtmlShape = function() {
	for (RectangleShape.prototype.redrawHtmlShape.apply(this, arguments); this.node.hasChildNodes();) this.node.removeChild(this.node.lastChild);
	if (null != this.image) {
		var a = document.createElement("img");
		a.style.position = "relative";
		a.setAttribute("border", "0");
		var b = this.getImageBounds(this.bounds.x, this.bounds.y, this.bounds.width, this.bounds.height);
		b.x -= this.bounds.x;
		b.y -= this.bounds.y;
		a.style.left = Math.round(b.x) + "px";
		a.style.top = Math.round(b.y) + "px";
		a.style.width = Math.round(b.width) + "px";
		a.style.height = Math.round(b.height) + "px";
		a.src = this.image;
		this.node.appendChild(a)
	}
};

function Cylinder(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(Cylinder, Shape);
Cylinder.prototype.maxHeight = 40;
Cylinder.prototype.svgStrokeTolerance = 0;
Cylinder.prototype.paintVertexShape = function(a, b, c, d, e) {
	a.translate(b, c);
	a.begin();
	this.redrawPath(a, b, c, d, e, !1);
	a.fillAndStroke();
	a.setShadow(!1);
	a.begin();
	this.redrawPath(a, b, c, d, e, !0);
	a.stroke()
};
Cylinder.prototype.redrawPath = function(a, b, c, d, e, f) {
	b = Math.min(this.maxHeight, Math.round(e / 5));
	if (f && null != this.fill || !f && null == this.fill) a.moveTo(0, b), a.curveTo(0, 2 * b, d, 2 * b, d, b), f || (a.stroke(), a.begin());
	f || (a.moveTo(0, b), a.curveTo(0, -b / 3, d, -b / 3, d, b), a.lineTo(d, e - b), a.curveTo(d, e + b / 3, 0, e + b / 3, 0, e - b), a.close())
};

function Connector(a, b, c) {
	Polyline.call(this, a, b, c)
}
Utils.extend(Connector, Polyline);
Connector.prototype.paintEdgeShape = function(a, b) {
	var c = this.createMarker(a, b, !0),
		d = this.createMarker(a, b, !1);
	Polyline.prototype.paintEdgeShape.apply(this, arguments);
	a.setFillColor(this.stroke);
	a.setShadow(!1);
	a.setDashed(!1);
	null != c && c();
	null != d && d()
};
Connector.prototype.createMarker = function(a, b, c) {
	var d = null,
		e = b.length,
		f = c ? b[1] : b[e - 2],
		g = c ? b[0] : b[e - 1];
	if (null != f && null != g) {
		for (d = 1; d < e - 1 && 0 == Math.round(f.x - g.x) && 0 == Math.round(f.y - g.y);) f = c ? b[1 + d] : b[e - 2 - d], d++;
		b = g.x - f.x;
		e = g.y - f.y;
		d = Math.max(1, Math.sqrt(b * b + e * e));
		f = b / d;
		b = e / d;
		e = Utils.getNumber(this.style, c ? Constants.STYLE_STARTSIZE : Constants.STYLE_ENDSIZE, Constants.DEFAULT_MARKERSIZE);
		d = Utils.getValue(this.style, c ? Constants.STYLE_STARTARROW : Constants.STYLE_ENDARROW);
		d = Marker.createMarker(a, this, d, g, f, b, e, c, this.arrowStrokewidth, 0 != this.style[c ? Constants.STYLE_STARTFILL : Constants.STYLE_ENDFILL])
	}
	return d
};
Connector.prototype.augmentBoundingBox = function(a) {
	Shape.prototype.augmentBoundingBox.apply(this, arguments);
	var b = 0;
	Utils.getValue(this.style, Constants.STYLE_STARTARROW, Constants.NONE) != Constants.NONE && (b = Utils.getNumber(this.style, Constants.STYLE_STARTSIZE, Constants.DEFAULT_MARKERSIZE) + 1);
	Utils.getValue(this.style, Constants.STYLE_ENDARROW, Constants.NONE) != Constants.NONE && (b = Math.max(b, Utils.getNumber(this.style, Constants.STYLE_ENDSIZE, Constants.DEFAULT_MARKERSIZE)) + 1);
	a.grow(Math.ceil(b * this.scale))
};

function Swimlane(a, b, c, d) {
	Shape.call(this);
	this.bounds = a;
	this.fill = b;
	this.stroke = c;
	this.strokewidth = null != d ? d : 1
}
Utils.extend(Swimlane, Shape);
Swimlane.prototype.imageSize = 16;
Swimlane.prototype.getTitleSize = function() {
	return Math.max(0, Utils.getValue(this.style, Constants.STYLE_STARTSIZE, Constants.DEFAULT_STARTSIZE))
};
Swimlane.prototype.getLabelBounds = function(a) {
	var b = this.getTitleSize();
	a = new Rectangle(a.x, a.y, a.width, a.height);
	var c = this.isHorizontal(),
		d = 1 == Utils.getValue(this.style, Constants.STYLE_FLIPH, 0),
		e = 1 == Utils.getValue(this.style, Constants.STYLE_FLIPV, 0),
		f = this.direction == Constants.DIRECTION_NORTH || this.direction == Constants.DIRECTION_SOUTH,
		c = c == !f,
		d = !c && d != (this.direction == Constants.DIRECTION_SOUTH || this.direction == Constants.DIRECTION_WEST),
		e = c && e != (this.direction == Constants.DIRECTION_SOUTH || this.direction == Constants.DIRECTION_WEST);
	if (f) {
		b = Math.min(a.width, b * this.scale);
		if (d || e) a.x += a.width - b;
		a.width = b
	} else {
		b = Math.min(a.height, b * this.scale);
		if (d || e) a.y += a.height - b;
		a.height = b
	}
	return a
};
Swimlane.prototype.getGradientBounds = function(a, b, c, d, e) {
	a = this.getTitleSize();
	if (this.isHorizontal()) return a = Math.min(a, e), new Rectangle(b, c, d, a);
	a = Math.min(a, d);
	return new Rectangle(b, c, a, e)
};
Swimlane.prototype.getArcSize = function(a, b, c) {
	a = Utils.getValue(this.style, Constants.STYLE_ARCSIZE, 100 * Constants.RECTANGLE_ROUNDING_FACTOR) / 100;
	return 3 * c * a
};
Swimlane.prototype.isHorizontal = function() {
	return 1 == Utils.getValue(this.style, Constants.STYLE_HORIZONTAL, 1)
};
Swimlane.prototype.paintVertexShape = function(a, b, c, d, e) {
	var f = this.getTitleSize(),
		g = Utils.getValue(this.style, Constants.STYLE_SWIMLANE_FILLCOLOR, Constants.NONE),
		k = 1 == Utils.getValue(this.style, Constants.STYLE_SWIMLANE_LINE, 1),
		l = 0,
		f = this.isHorizontal() ? Math.min(f, e) : Math.min(f, d);
	a.translate(b, c);
	this.isRounded ? (l = this.getArcSize(d, e, f), this.paintRoundedSwimlane(a, b, c, d, e, f, l, g, k)) : this.paintSwimlane(a, b, c, d, e, f, g, k);
	g = Utils.getValue(this.style, Constants.STYLE_SEPARATORCOLOR, Constants.NONE);
	this.paintSeparator(a, b, c, d, e, f, g);
	null != this.image && (e = this.getImageBounds(b, c, d, e), a.image(e.x - b, e.y - c, e.width, e.height, this.image, !1, !1, !1));
	this.glass && (a.setShadow(!1), this.paintGlassEffect(a, 0, 0, d, f, l))
};
Swimlane.prototype.paintSwimlane = function(a, b, c, d, e, f, g, k) {
	g != Constants.NONE && (a.save(), a.setFillColor(g), a.rect(0, 0, d, e), a.fillAndStroke(), a.restore(), a.setShadow(!1));
	a.begin();
	this.isHorizontal() ? (a.moveTo(0, f), a.lineTo(0, 0), a.lineTo(d, 0), a.lineTo(d, f), k && a.close(), a.fillAndStroke(), f < e && g == Constants.NONE && (a.pointerEvents = !1, a.begin(), a.moveTo(0, f), a.lineTo(0, e), a.lineTo(d, e), a.lineTo(d, f), a.stroke())) : (a.moveTo(f, 0), a.lineTo(0, 0), a.lineTo(0, e), a.lineTo(f, e), k && a.close(), a.fillAndStroke(), f < d && g == Constants.NONE && (a.pointerEvents = !1, a.begin(), a.moveTo(f, 0), a.lineTo(d, 0), a.lineTo(d, e), a.lineTo(f, e), a.stroke()))
};
Swimlane.prototype.paintRoundedSwimlane = function(a, b, c, d, e, f, g, k, l) {
	k != Constants.NONE && (a.save(), a.setFillColor(k), a.roundrect(0, 0, d, e, g, g), a.fillAndStroke(), a.restore(), a.setShadow(!1));
	a.begin();
	this.isHorizontal() ? (a.moveTo(d, f), a.lineTo(d, g), a.quadTo(d, 0, d - Math.min(d / 2, g), 0), a.lineTo(Math.min(d / 2, g), 0), a.quadTo(0, 0, 0, g), a.lineTo(0, f), l && a.close(), a.fillAndStroke(), f < e && k == Constants.NONE && (a.pointerEvents = !1, a.begin(), a.moveTo(0, f), a.lineTo(0, e - g), a.quadTo(0, e, Math.min(d / 2, g), e), a.lineTo(d - Math.min(d / 2, g), e), a.quadTo(d, e, d, e - g), a.lineTo(d, f), a.stroke())) : (a.moveTo(f, 0), a.lineTo(g, 0), a.quadTo(0, 0, 0, Math.min(e / 2, g)), a.lineTo(0, e - Math.min(e / 2, g)), a.quadTo(0, e, g, e), a.lineTo(f, e), l && a.close(), a.fillAndStroke(), f < d && k == Constants.NONE && (a.pointerEvents = !1, a.begin(), a.moveTo(f, e), a.lineTo(d - g, e), a.quadTo(d, e, d, e - Math.min(e / 2, g)), a.lineTo(d, Math.min(e / 2, g)), a.quadTo(d, 0, d - g, 0), a.lineTo(f, 0), a.stroke()))
};
Swimlane.prototype.paintSeparator = function(a, b, c, d, e, f, g) {
	g != Constants.NONE && (a.setStrokeColor(g), a.setDashed(!0), a.begin(), this.isHorizontal() ? (a.moveTo(d, f), a.lineTo(d, e)) : (a.moveTo(f, 0), a.lineTo(d, 0)), a.stroke(), a.setDashed(!1))
};
Swimlane.prototype.getImageBounds = function(a, b, c, d) {
	return this.isHorizontal() ? new Rectangle(a + c - this.imageSize, b, this.imageSize, this.imageSize) : new Rectangle(a, b, this.imageSize, this.imageSize)
};

function GraphLayout(a) {
	this.graph = a
}
GraphLayout.prototype.graph = null;
GraphLayout.prototype.useBoundingBox = !0;
GraphLayout.prototype.parent = null;
GraphLayout.prototype.moveCell = function(a, b, c) {};
GraphLayout.prototype.execute = function(a) {};
GraphLayout.prototype.getGraph = function() {
	return this.graph
};
GraphLayout.prototype.getConstraint = function(a, b, c, d) {
	c = this.graph.view.getState(b);
	b = null != c ? c.style : this.graph.getCellStyle(b);
	return null != b ? b[a] : null
};
GraphLayout.traverse = function(a, b, c, d, e) {
	if (null != c && null != a) {
		b = null != b ? b : !0;
		e = e || [];
		var f = CellPath.create(a);
		if (null == e[f] && (e[f] = a, d = c(a, d), null == d || d)) if (d = this.graph.model.getEdgeCount(a), 0 < d) for (f = 0; f < d; f++) {
			var g = this.graph.model.getEdgeAt(a, f),
				k = this.graph.model.getTerminal(g, !0) == a;
			if (!b || k) k = this.graph.view.getVisibleTerminal(g, !k), this.traverse(k, b, c, g, e)
		}
	}
};
GraphLayout.prototype.isVertexMovable = function(a) {
	return this.graph.isCellMovable(a)
};
GraphLayout.prototype.isVertexIgnored = function(a) {
	return !this.graph.getModel().isVertex(a) || !this.graph.isCellVisible(a)
};
GraphLayout.prototype.isEdgeIgnored = function(a) {
	var b = this.graph.getModel();
	return !b.isEdge(a) || !this.graph.isCellVisible(a) || null == b.getTerminal(a, !0) || null == b.getTerminal(a, !1)
};
GraphLayout.prototype.setEdgeStyleEnabled = function(a, b) {
	this.graph.setCellStyles(Constants.STYLE_NOEDGESTYLE, b ? "0" : "1", [a])
};
GraphLayout.prototype.setOrthogonalEdge = function(a, b) {
	this.graph.setCellStyles(Constants.STYLE_ORTHOGONAL, b ? "1" : "0", [a])
};
GraphLayout.prototype.getParentOffset = function(a) {
	var b = new Point;
	if (null != a && a != this.parent) {
		var c = this.graph.getModel();
		if (c.isAncestor(this.parent, a)) for (var d = c.getGeometry(a); a != this.parent;) b.x += d.x, b.y += d.y, a = c.getParent(a), d = c.getGeometry(a)
	}
	return b
};
GraphLayout.prototype.setEdgePoints = function(a, b) {
	if (null != a) {
		var c = this.graph.model,
			d = c.getGeometry(a);
		null == d ? (d = new Geometry, d.setRelative(!0)) : d = d.clone();
		if (null != this.parent && null != b) for (var e = c.getParent(a), e = this.getParentOffset(e), f = 0; f < b.length; f++) b[f].x -= e.x, b[f].y -= e.y;
		d.points = b;
		c.setGeometry(a, d)
	}
};
GraphLayout.prototype.setVertexLocation = function(a, b, c) {
	var d = this.graph.getModel(),
		e = d.getGeometry(a),
		f = null;
	if (null != e) {
		f = new Rectangle(b, c, e.width, e.height);
		if (this.useBoundingBox) {
			var g = this.graph.getView().getState(a);
			if (null != g && null != g.text && null != g.text.boundingBox) {
				var k = this.graph.getView().scale,
					l = g.text.boundingBox;
				g.text.boundingBox.x < g.x && (b += (g.x - l.x) / k, f.width = l.width);
				g.text.boundingBox.y < g.y && (c += (g.y - l.y) / k, f.height = l.height)
			}
		}
		null != this.parent && (g = d.getParent(a), null != g && g != this.parent && (g = this.getParentOffset(g), b -= g.x, c -= g.y));
		if (e.x != b || e.y != c) e = e.clone(), e.x = b, e.y = c, d.setGeometry(a, e)
	}
	return f
};
GraphLayout.prototype.getVertexBounds = function(a) {
	var b = this.graph.getModel().getGeometry(a);
	if (this.useBoundingBox) {
		var c = this.graph.getView().getState(a);
		if (null != c && null != c.text && null != c.text.boundingBox) var d = this.graph.getView().scale,
			e = c.text.boundingBox,
			f = Math.max(c.x - e.x, 0) / d,
			g = Math.max(c.y - e.y, 0) / d,
			k = Math.max(e.x + e.width - (c.x + c.width), 0) / d,
			c = Math.max(e.y + e.height - (c.y + c.height), 0) / d,
			b = new Rectangle(b.x - f, b.y - g, b.width + f + k, b.height + g + c)
	}
	null != this.parent && (a = this.graph.getModel().getParent(a), b = b.clone(), null != a && a != this.parent && (a = this.getParentOffset(a), b.x += a.x, b.y += a.y));
	return new Rectangle(b.x, b.y, b.width, b.height)
};
GraphLayout.prototype.arrangeGroups = function(a, b) {
	this.graph.getModel().beginUpdate();
	try {
		for (var c = a.length - 1; 0 <= c; c--) {
			var d = a[c],
				e = this.graph.getChildVertices(d),
				f = this.graph.getBoundingBoxFromGeometry(e),
				g = this.graph.getCellGeometry(d),
				k = 0,
				l = 0;
			if (this.graph.isSwimlane(d)) var m = this.graph.getStartSize(d),
				k = m.width,
				l = m.height;
			null != f && null != g && (g = g.clone(), g.x = g.x + f.x - b - k, g.y = g.y + f.y - b - l, g.width = f.width + 2 * b + k, g.height = f.height + 2 * b + l, this.graph.getModel().setGeometry(d, g), this.graph.moveCells(e, b + k - f.x, b + l - f.y))
		}
	} finally {
		this.graph.getModel().endUpdate()
	}
};

function StackLayout(a, b, c, d, e, f) {
	GraphLayout.call(this, a);
	this.horizontal = null != b ? b : !0;
	this.spacing = null != c ? c : 0;
	this.x0 = null != d ? d : 0;
	this.y0 = null != e ? e : 0;
	this.border = null != f ? f : 0
}
StackLayout.prototype = new GraphLayout;
StackLayout.prototype.constructor = StackLayout;
StackLayout.prototype.horizontal = null;
StackLayout.prototype.spacing = null;
StackLayout.prototype.x0 = null;
StackLayout.prototype.y0 = null;
StackLayout.prototype.border = 0;
StackLayout.prototype.marginTop = 0;
StackLayout.prototype.marginLeft = 0;
StackLayout.prototype.marginRight = 0;
StackLayout.prototype.marginBottom = 0;
StackLayout.prototype.keepFirstLocation = !1;
StackLayout.prototype.fill = !1;
StackLayout.prototype.resizeParent = !1;
StackLayout.prototype.resizeLast = !1;
StackLayout.prototype.wrap = null;
StackLayout.prototype.borderCollapse = !0;
StackLayout.prototype.isHorizontal = function() {
	return this.horizontal
};
StackLayout.prototype.moveCell = function(a, b, c) {
	var d = this.graph.getModel(),
		e = d.getParent(a),
		f = this.isHorizontal();
	if (null != a && null != e) {
		var g = 0,
			k = 0,
			l = d.getChildCount(e);
		b = f ? b : c;
		g = this.graph.getView().getState(e);
		null != g && (b -= f ? g.x : g.y);
		for (g = 0; g < l; g++) if (c = d.getChildAt(e, g), c != a && (c = d.getGeometry(c), null != c)) {
			c = f ? c.x + c.width / 2 : c.y + c.height / 2;
			if (k < b && c > b) break;
			k = c
		}
		f = e.getIndex(a);
		f = Math.max(0, g - (g > f ? 1 : 0));
		d.add(e, a, f)
	}
};
StackLayout.prototype.getParentSize = function(a) {
	var b = this.graph.getModel(),
		c = b.getGeometry(a);
	if (null != this.graph.container && (null == c && b.isLayer(a) || a == this.graph.getView().currentRoot)) c = new Rectangle(0, 0, this.graph.container.offsetWidth - 1, this.graph.container.offsetHeight - 1);
	return c
};
StackLayout.prototype.execute = function(a) {
	if (null != a) {
		var b = this.getParentSize(a),
			c = this.isHorizontal(),
			d = this.graph.getModel(),
			e = null;
		null != b && (e = c ? b.height - this.marginTop - this.marginBottom : b.width - this.marginLeft - this.marginRight);
		var e = e - (2 * this.spacing + 2 * this.border),
			f = this.x0 + this.border + this.marginLeft,
			g = this.y0 + this.border + this.marginTop;
		if (this.graph.isSwimlane(a)) {
			var k = this.graph.getCellStyle(a),
				l = Utils.getNumber(k, Constants.STYLE_STARTSIZE, Constants.DEFAULT_STARTSIZE),
				k = 1 == Utils.getValue(k, Constants.STYLE_HORIZONTAL, !0);
			null != b && (l = k ? Math.min(l, b.height) : Math.min(l, b.width));
			c == k && (e -= l);
			k ? g += l : f += l
		}
		d.beginUpdate();
		try {
			for (var l = 0, k = null, m = 0, n = d.getChildCount(a), p = 0; p < n; p++) {
				var q = d.getChildAt(a, p);
				if (!this.isVertexIgnored(q) && this.isVertexMovable(q)) {
					var r = d.getGeometry(q);
					if (null != r) {
						r = r.clone();
						if (null != this.wrap && null != k && (c && k.x + k.width + r.width + 2 * this.spacing > this.wrap || !c && k.y + k.height + r.height + 2 * this.spacing > this.wrap)) k = null, c ? g += l + this.spacing : f += l + this.spacing, l = 0;
						var l = Math.max(l, c ? r.height : r.width),
							s = 0;
						if (!this.borderCollapse) var t = this.graph.getCellStyle(q),
							s = Utils.getNumber(t, Constants.STYLE_STROKEWIDTH, 1);
						null != k ? c ? r.x = m + this.spacing + Math.floor(s / 2) : r.y = m + this.spacing + Math.floor(s / 2) : this.keepFirstLocation || (c ? r.x = f : r.y = g);
						c ? r.y = g : r.x = f;
						this.fill && null != e && (c ? r.height = e : r.width = e);
						this.setChildGeometry(q, r);
						k = r;
						m = c ? k.x + k.width + Math.floor(s / 2) : k.y + k.height + Math.floor(s / 2)
					}
				}
			}
			this.resizeParent && null != b && null != k && !this.graph.isCellCollapsed(a) ? this.updateParentGeometry(a, b, k) : this.resizeLast && (null != b && null != k) && (c ? k.width = b.width - k.x - this.spacing - this.marginRight - this.marginLeft : k.height = b.height - k.y - this.spacing - this.marginBottom)
		} finally {
			d.endUpdate()
		}
	}
};
StackLayout.prototype.setChildGeometry = function(a, b) {
	this.graph.getModel().setGeometry(a, b)
};
StackLayout.prototype.updateParentGeometry = function(a, b, c) {
	var d = this.isHorizontal(),
		e = this.graph.getModel();
	b = b.clone();
	d ? b.width = c.x + c.width + this.spacing + this.marginRight : b.height = c.y + c.height + this.spacing + this.marginBottom;
	e.setGeometry(a, b)
};

function PartitionLayout(a, b, c, d) {
	GraphLayout.call(this, a);
	this.horizontal = null != b ? b : !0;
	this.spacing = c || 0;
	this.border = d || 0
}
PartitionLayout.prototype = new GraphLayout;
PartitionLayout.prototype.constructor = PartitionLayout;
PartitionLayout.prototype.horizontal = null;
PartitionLayout.prototype.spacing = null;
PartitionLayout.prototype.border = null;
PartitionLayout.prototype.resizeVertices = !0;
PartitionLayout.prototype.isHorizontal = function() {
	return this.horizontal
};
PartitionLayout.prototype.moveCell = function(a, b, c) {
	c = this.graph.getModel();
	var d = c.getParent(a);
	if (null != a && null != d) {
		for (var e = 0, f = 0, g = c.getChildCount(d), e = 0; e < g; e++) {
			var k = c.getChildAt(d, e),
				k = this.getVertexBounds(k);
			if (null != k) {
				k = k.x + k.width / 2;
				if (f < b && k > b) break;
				f = k
			}
		}
		b = d.getIndex(a);
		b = Math.max(0, e - (e > b ? 1 : 0));
		c.add(d, a, b)
	}
};
PartitionLayout.prototype.execute = function(a) {
	var b = this.isHorizontal(),
		c = this.graph.getModel(),
		d = c.getGeometry(a);
	if (null != this.graph.container && (null == d && c.isLayer(a) || a == this.graph.getView().currentRoot)) d = new Rectangle(0, 0, this.graph.container.offsetWidth - 1, this.graph.container.offsetHeight - 1);
	if (null != d) {
		for (var e = [], f = c.getChildCount(a), g = 0; g < f; g++) {
			var k = c.getChildAt(a, g);
			!this.isVertexIgnored(k) && this.isVertexMovable(k) && e.push(k)
		}
		f = e.length;
		if (0 < f) {
			var l = this.border,
				m = this.border,
				n = b ? d.height : d.width,
				n = n - 2 * this.border;
			a = this.graph.isSwimlane(a) ? this.graph.getStartSize(a) : new Rectangle;
			n -= b ? a.height : a.width;
			l += a.width;
			m += a.height;
			a = this.border + (f - 1) * this.spacing;
			d = b ? (d.width - l - a) / f : (d.height - m - a) / f;
			if (0 < d) {
				c.beginUpdate();
				try {
					for (g = 0; g < f; g++) {
						var k = e[g],
							p = c.getGeometry(k);
						null != p && (p = p.clone(), p.x = l, p.y = m, b ? (this.resizeVertices && (p.width = d, p.height = n), l += d + this.spacing) : (this.resizeVertices && (p.height = d, p.width = n), m += d + this.spacing), c.setGeometry(k, p))
					}
				} finally {
					c.endUpdate()
				}
			}
		}
	}
};

function CompactTreeLayout(a, b, c) {
	GraphLayout.call(this, a);
	this.horizontal = null != b ? b : !0;
	this.invert = null != c ? c : !1
}
CompactTreeLayout.prototype = new GraphLayout;
CompactTreeLayout.prototype.constructor = CompactTreeLayout;
CompactTreeLayout.prototype.horizontal = null;
CompactTreeLayout.prototype.invert = null;
CompactTreeLayout.prototype.resizeParent = !0;
CompactTreeLayout.prototype.groupPadding = 10;
CompactTreeLayout.prototype.parentsChanged = null;
CompactTreeLayout.prototype.moveTree = !1;
CompactTreeLayout.prototype.visited = null;
CompactTreeLayout.prototype.levelDistance = 10;
CompactTreeLayout.prototype.nodeDistance = 20;
CompactTreeLayout.prototype.resetEdges = !0;
CompactTreeLayout.prototype.prefHozEdgeSep = 5;
CompactTreeLayout.prototype.prefVertEdgeOff = 4;
CompactTreeLayout.prototype.minEdgeJetty = 8;
CompactTreeLayout.prototype.channelBuffer = 4;
CompactTreeLayout.prototype.edgeRouting = !0;
CompactTreeLayout.prototype.sortEdges = !1;
CompactTreeLayout.prototype.alignRanks = !1;
CompactTreeLayout.prototype.maxRankHeight = null;
CompactTreeLayout.prototype.root = null;
CompactTreeLayout.prototype.node = null;
CompactTreeLayout.prototype.isVertexIgnored = function(a) {
	return GraphLayout.prototype.isVertexIgnored.apply(this, arguments) || 0 == this.graph.getConnections(a).length
};
CompactTreeLayout.prototype.isHorizontal = function() {
	return this.horizontal
};
CompactTreeLayout.prototype.execute = function(a, b) {
	this.parent = a;
	var c = this.graph.getModel();
	if (null == b) if (0 < this.graph.getEdges(a, c.getParent(a), this.invert, !this.invert, !1).length) this.root = a;
	else {
		var d = this.graph.findTreeRoots(a, !0, this.invert);
		if (0 < d.length) for (var e = 0; e < d.length; e++) if (!this.isVertexIgnored(d[e]) && 0 < this.graph.getEdges(d[e], null, this.invert, !this.invert, !1).length) {
			this.root = d[e];
			break
		}
	} else this.root = b;
	if (null != this.root) {
		this.parentsChanged = this.resizeParent ? {} : null;
		c.beginUpdate();
		try {
			if (this.visited = {}, this.node = this.dfs(this.root, a), this.alignRanks && (this.maxRankHeight = [], this.findRankHeights(this.node, 0), this.setCellHeights(this.node, 0)), null != this.node) {
				this.layout(this.node);
				var f = this.graph.gridSize,
					d = f;
				if (!this.moveTree) {
					var g = this.getVertexBounds(this.root);
					null != g && (f = g.x, d = g.y)
				}
				g = null;
				g = this.isHorizontal() ? this.horizontalLayout(this.node, f, d) : this.verticalLayout(this.node, null, f, d);
				if (null != g) {
					var k = e = 0;
					0 > g.x && (e = Math.abs(f - g.x));
					0 > g.y && (k = Math.abs(d - g.y));
					(0 != e || 0 != k) && this.moveNode(this.node, e, k);
					this.resizeParent && this.adjustParents();
					this.edgeRouting && this.localEdgeProcessing(this.node)
				}
			}
		} finally {
			c.endUpdate()
		}
	}
};
CompactTreeLayout.prototype.moveNode = function(a, b, c) {
	a.x += b;
	a.y += c;
	this.apply(a);
	for (a = a.child; null != a;) this.moveNode(a, b, c), a = a.next
};
CompactTreeLayout.prototype.sortOutgoingEdges = function(a, b) {
	var c = new Dictionary;
	b.sort(function(b, e) {
		var f = b.getTerminal(b.getTerminal(!1) == a),
			g = c.get(f);
		null == g && (g = CellPath.create(f).split(CellPath.PATH_SEPARATOR), c.put(f, g));
		var f = e.getTerminal(e.getTerminal(!1) == a),
			k = c.get(f);
		null == k && (k = CellPath.create(f).split(CellPath.PATH_SEPARATOR), c.put(f, k));
		return CellPath.compare(g, k)
	})
};
CompactTreeLayout.prototype.findRankHeights = function(a, b) {
	if (null == this.maxRankHeight[b] || this.maxRankHeight[b] < a.height) this.maxRankHeight[b] = a.height;
	for (var c = a.child; null != c;) this.findRankHeights(c, b + 1), c = c.next
};
CompactTreeLayout.prototype.setCellHeights = function(a, b) {
	null != this.maxRankHeight[b] && this.maxRankHeight[b] > a.height && (a.height = this.maxRankHeight[b]);
	for (var c = a.child; null != c;) this.setCellHeights(c, b + 1), c = c.next
};
CompactTreeLayout.prototype.dfs = function(a, b) {
	var c = CellPath.create(a),
		d = null;
	if (null != a && null == this.visited[c] && !this.isVertexIgnored(a)) {
		this.visited[c] = a;
		var d = this.createNode(a),
			c = this.graph.getModel(),
			e = null,
			f = this.graph.getEdges(a, b, this.invert, !this.invert, !1, !0),
			g = this.graph.getView();
		this.sortEdges && this.sortOutgoingEdges(a, f);
		for (var k = 0; k < f.length; k++) {
			var l = f[k];
			if (!this.isEdgeIgnored(l)) {
				this.resetEdges && this.setEdgePoints(l, null);
				this.edgeRouting && (this.setEdgeStyleEnabled(l, !1), this.setEdgePoints(l, null));
				var m = g.getState(l),
					l = null != m ? m.getVisibleTerminal(this.invert) : g.getVisibleTerminal(l, this.invert),
					m = this.dfs(l, b);
				null != m && null != c.getGeometry(l) && (null == e ? d.child = m : e.next = m, e = m)
			}
		}
	}
	return d
};
CompactTreeLayout.prototype.layout = function(a) {
	if (null != a) {
		for (var b = a.child; null != b;) this.layout(b), b = b.next;
		null != a.child ? this.attachParent(a, this.join(a)) : this.layoutLeaf(a)
	}
};
CompactTreeLayout.prototype.horizontalLayout = function(a, b, c, d) {
	a.x += b + a.offsetX;
	a.y += c + a.offsetY;
	d = this.apply(a, d);
	b = a.child;
	if (null != b) {
		d = this.horizontalLayout(b, a.x, a.y, d);
		c = a.y + b.offsetY;
		for (var e = b.next; null != e;) d = this.horizontalLayout(e, a.x + b.offsetX, c, d), c += e.offsetY, e = e.next
	}
	return d
};
CompactTreeLayout.prototype.verticalLayout = function(a, b, c, d, e) {
	a.x += c + a.offsetY;
	a.y += d + a.offsetX;
	e = this.apply(a, e);
	b = a.child;
	if (null != b) {
		e = this.verticalLayout(b, a, a.x, a.y, e);
		c = a.x + b.offsetY;
		for (d = b.next; null != d;) e = this.verticalLayout(d, a, c, a.y + b.offsetX, e), c += d.offsetY, d = d.next
	}
	return e
};
CompactTreeLayout.prototype.attachParent = function(a, b) {
	var c = this.nodeDistance + this.levelDistance,
		d = (b - a.width) / 2 - this.nodeDistance,
		e = d + a.width + 2 * this.nodeDistance - b;
	a.child.offsetX = c + a.height;
	a.child.offsetY = e;
	a.contour.upperHead = this.createLine(a.height, 0, this.createLine(c, e, a.contour.upperHead));
	a.contour.lowerHead = this.createLine(a.height, 0, this.createLine(c, d, a.contour.lowerHead))
};
CompactTreeLayout.prototype.layoutLeaf = function(a) {
	var b = 2 * this.nodeDistance;
	a.contour.upperTail = this.createLine(a.height + b, 0);
	a.contour.upperHead = a.contour.upperTail;
	a.contour.lowerTail = this.createLine(0, -a.width - b);
	a.contour.lowerHead = this.createLine(a.height + b, 0, a.contour.lowerTail)
};
CompactTreeLayout.prototype.join = function(a) {
	var b = 2 * this.nodeDistance,
		c = a.child;
	a.contour = c.contour;
	for (var d = c.width + b, e = d, c = c.next; null != c;) {
		var f = this.merge(a.contour, c.contour);
		c.offsetY = f + d;
		c.offsetX = 0;
		d = c.width + b;
		e += f + d;
		c = c.next
	}
	return e
};
CompactTreeLayout.prototype.merge = function(a, b) {
	for (var c = 0, d = 0, e = 0, f = a.lowerHead, g = b.upperHead; null != g && null != f;) {
		var k = this.offset(c, d, g.dx, g.dy, f.dx, f.dy),
			d = d + k,
			e = e + k;
		c + g.dx <= f.dx ? (c += g.dx, d += g.dy, g = g.next) : (c -= f.dx, d -= f.dy, f = f.next)
	}
	null != g ? (c = this.bridge(a.upperTail, 0, 0, g, c, d), a.upperTail = null != c.next ? b.upperTail : c, a.lowerTail = b.lowerTail) : (c = this.bridge(b.lowerTail, c, d, f, 0, 0), null == c.next && (a.lowerTail = c));
	a.lowerHead = b.lowerHead;
	return e
};
CompactTreeLayout.prototype.offset = function(a, b, c, d, e, f) {
	var g = 0;
	if (e <= a || 0 >= a + c) return 0;
	g = 0 < e * d - c * f ? 0 > a ? a * d / c - b : 0 < a ? a * f / e - b : -b : e < a + c ? f - (b + (e - a) * d / c) : e > a + c ? (c + a) * f / e - (b + d) : f - (b + d);
	return 0 < g ? g : 0
};
CompactTreeLayout.prototype.bridge = function(a, b, c, d, e, f) {
	b = e + d.dx - b;
	e = e = 0;
	0 == d.dx ? e = d.dy : (e = b * d.dy, e /= d.dx);
	b = this.createLine(b, e, d.next);
	a.next = this.createLine(0, f + d.dy - e - c, b);
	return b
};
CompactTreeLayout.prototype.createNode = function(a) {
	var b = {};
	b.cell = a;
	b.x = 0;
	b.y = 0;
	b.width = 0;
	b.height = 0;
	a = this.getVertexBounds(a);
	null != a && (this.isHorizontal() ? (b.width = a.height, b.height = a.width) : (b.width = a.width, b.height = a.height));
	b.offsetX = 0;
	b.offsetY = 0;
	b.contour = {};
	return b
};
CompactTreeLayout.prototype.apply = function(a, b) {
	var c = this.graph.getModel(),
		d = a.cell,
		e = c.getGeometry(d);
	null != d && null != e && (this.isVertexMovable(d) && (e = this.setVertexLocation(d, a.x, a.y), this.resizeParent && (c = c.getParent(d), d = CellPath.create(c), null == this.parentsChanged[d] && (this.parentsChanged[d] = c))), b = null == b ? new Rectangle(e.x, e.y, e.width, e.height) : new Rectangle(Math.min(b.x, e.x), Math.min(b.y, e.y), Math.max(b.x + b.width, e.x + e.width), Math.max(b.y + b.height, e.y + e.height)));
	return b
};
CompactTreeLayout.prototype.createLine = function(a, b, c) {
	var d = {};
	d.dx = a;
	d.dy = b;
	d.next = c;
	return d
};
CompactTreeLayout.prototype.adjustParents = function() {
	var a = [],
		b;
	for (b in this.parentsChanged) a.push(this.parentsChanged[b]);
	this.arrangeGroups(Utils.sortCells(a, !0), this.groupPadding)
};
CompactTreeLayout.prototype.localEdgeProcessing = function(a) {
	this.processNodeOutgoing(a);
	for (a = a.child; null != a;) this.localEdgeProcessing(a), a = a.next
};
CompactTreeLayout.prototype.processNodeOutgoing = function(a) {
	for (var b = a.child, c = a.cell, d = 0, e = []; null != b;) {
		d++;
		var f = b.x;
		this.horizontal && (f = b.y);
		e.push(new WeightedCellSorter(b, f));
		b = b.next
	}
	e.sort(WeightedCellSorter.prototype.compare);
	var f = a.width,
		g = (d + 1) * this.prefHozEdgeSep;
	f > g + 2 * this.prefHozEdgeSep && (f -= 2 * this.prefHozEdgeSep);
	a = f / d;
	b = a / 2;
	f > g + 2 * this.prefHozEdgeSep && (b += this.prefHozEdgeSep);
	for (var f = this.minEdgeJetty - this.prefVertEdgeOff, g = 0, k = this.getVertexBounds(c), l = 0; l < e.length; l++) {
		for (var m = e[l].cell.cell, n = this.getVertexBounds(m), m = this.graph.getEdgesBetween(c, m, !1), p = [], q = 0, r = 0, s = 0; s < m.length; s++) this.horizontal ? (q = k.x + k.width, r = k.y + b, p.push(new Point(q, r)), q = k.x + k.width + f, p.push(new Point(q, r)), r = n.y + n.height / 2) : (q = k.x + b, r = k.y + k.height, p.push(new Point(q, r)), r = k.y + k.height + f, p.push(new Point(q, r)), q = n.x + n.width / 2), p.push(new Point(q, r)), this.setEdgePoints(m[s], p);
		l < d / 2 ? f += this.prefVertEdgeOff : l > d / 2 && (f -= this.prefVertEdgeOff);
		b += a;
		g = Math.max(g, f)
	}
};

function WeightedCellSorter(a, b) {
	this.cell = a;
	this.weightedValue = b
}
WeightedCellSorter.prototype.weightedValue = 0;
WeightedCellSorter.prototype.nudge = !1;
WeightedCellSorter.prototype.visited = !1;
WeightedCellSorter.prototype.rankIndex = null;
WeightedCellSorter.prototype.cell = null;
WeightedCellSorter.prototype.compare = function(a, b) {
	return null != a && null != b ? b.weightedValue > a.weightedValue ? 1 : b.weightedValue < a.weightedValue ? -1 : b.nudge ? 1 : -1 : 0
};

function RadialTreeLayout(a) {
	CompactTreeLayout.call(this, a, !1)
}
Utils.extend(RadialTreeLayout, CompactTreeLayout);
RadialTreeLayout.prototype.angleOffset = 0.5;
RadialTreeLayout.prototype.rootx = 0;
RadialTreeLayout.prototype.rooty = 0;
RadialTreeLayout.prototype.levelDistance = 10;
RadialTreeLayout.prototype.nodeDistance = 20;
RadialTreeLayout.prototype.autoRadius = !1;
RadialTreeLayout.prototype.sortEdges = !1;
RadialTreeLayout.prototype.rowMinX = [];
RadialTreeLayout.prototype.rowMaxX = [];
RadialTreeLayout.prototype.rowMinCenX = [];
RadialTreeLayout.prototype.rowMaxCenX = [];
RadialTreeLayout.prototype.rowRadi = [];
RadialTreeLayout.prototype.row = [];
RadialTreeLayout.prototype.isVertexIgnored = function(a) {
	return GraphLayout.prototype.isVertexIgnored.apply(this, arguments) || 0 == this.graph.getConnections(a).length
};
RadialTreeLayout.prototype.execute = function(a, b) {
	this.parent = a;
	this.edgeRouting = this.useBoundingBox = !1;
	this.levelDistance = 120;
	this.nodeDistance = 10;
	CompactTreeLayout.prototype.execute.apply(this, arguments);
	var c = null,
		d = this.getVertexBounds(this.root);
	this.centerX = d.x + d.width / 2;
	this.centerY = d.y + d.height / 2;
	for (var e in this.visited) {
		var f = this.getVertexBounds(this.visited[e]),
			c = null != c ? c : f.clone();
		c.add(f)
	}
	this.calcRowDims([this.node], 0);
	for (var g = 0, k = 0, c = 0; c < this.row.length; c++) e = (this.rowMaxX[c] - this.centerX - this.nodeDistance) / this.rowRadi[c], g = Math.max(g, (this.centerX - this.rowMinX[c] - this.nodeDistance) / this.rowRadi[c]), k = Math.max(k, e);
	for (c = 0; c < this.row.length; c++) {
		var l = this.centerX - this.nodeDistance - g * this.rowRadi[c],
			m = this.centerX + this.nodeDistance + k * this.rowRadi[c] - l;
		for (e = 0; e < this.row[c].length; e++) f = this.row[c], d = f[e], f = this.getVertexBounds(d.cell), d.theta = 2 * Math.PI * ((f.x + f.width / 2 - l) / m)
	}
	for (c = this.row.length - 2; 0 <= c; c--) {
		f = this.row[c];
		for (e = 0; e < f.length; e++) {
			d = f[e];
			g = d.child;
			for (l = k = 0; null != g;) l += g.theta, k++, g = g.next;
			0 < k && (g = l / k, g > d.theta && e < f.length - 1 ? d.theta = Math.min(g, f[e + 1].theta - Math.PI / 10) : g < d.theta && 0 < e && (d.theta = Math.max(g, f[e - 1].theta + Math.PI / 10)))
		}
	}
	for (c = 0; c < this.row.length; c++) for (e = 0; e < this.row[c].length; e++) f = this.row[c], d = f[e], f = this.getVertexBounds(d.cell), this.setVertexLocation(d.cell, this.centerX - f.width / 2 + this.rowRadi[c] * Math.cos(d.theta), this.centerY - f.height / 2 + this.rowRadi[c] * Math.sin(d.theta))
};
RadialTreeLayout.prototype.calcRowDims = function(a, b) {
	if (!(null == a || 0 == a.length)) {
		this.rowMinX[b] = this.centerX;
		this.rowMaxX[b] = this.centerX;
		this.rowMinCenX[b] = this.centerX;
		this.rowMaxCenX[b] = this.centerX;
		this.row[b] = [];
		for (var c = !1, d = 0; d < a.length; d++) for (var e = a[d].child; null != e;) vertexBounds = this.getVertexBounds(e.cell), this.rowMinX[b] = Math.min(vertexBounds.x, this.rowMinX[b]), this.rowMaxX[b] = Math.max(vertexBounds.x + vertexBounds.width, this.rowMaxX[b]), this.rowMinCenX[b] = Math.min(vertexBounds.x + vertexBounds.width / 2, this.rowMinCenX[b]), this.rowMaxCenX[b] = Math.max(vertexBounds.x + vertexBounds.width / 2, this.rowMaxCenX[b]), this.rowRadi[b] = vertexBounds.y - this.getVertexBounds(this.root).y, null != e.child && (c = !0), this.row[b].push(e), e = e.next;
		c && this.calcRowDims(this.row[b], b + 1)
	}
};

function FastOrganicLayout(a) {
	GraphLayout.call(this, a)
}
FastOrganicLayout.prototype = new GraphLayout;
FastOrganicLayout.prototype.constructor = FastOrganicLayout;
FastOrganicLayout.prototype.useInputOrigin = !0;
FastOrganicLayout.prototype.resetEdges = !0;
FastOrganicLayout.prototype.disableEdgeStyle = !0;
FastOrganicLayout.prototype.forceConstant = 50;
FastOrganicLayout.prototype.forceConstantSquared = 0;
FastOrganicLayout.prototype.minDistanceLimit = 2;
FastOrganicLayout.prototype.maxDistanceLimit = 500;
FastOrganicLayout.prototype.minDistanceLimitSquared = 4;
FastOrganicLayout.prototype.initialTemp = 200;
FastOrganicLayout.prototype.temperature = 0;
FastOrganicLayout.prototype.maxIterations = 0;
FastOrganicLayout.prototype.iteration = 0;
FastOrganicLayout.prototype.allowedToRun = !0;
FastOrganicLayout.prototype.isVertexIgnored = function(a) {
	return GraphLayout.prototype.isVertexIgnored.apply(this, arguments) || 0 == this.graph.getConnections(a).length
};
FastOrganicLayout.prototype.execute = function(a) {
	var b = this.graph.getModel();
	this.vertexArray = [];
	for (var c = this.graph.getChildVertices(a), d = 0; d < c.length; d++) this.isVertexIgnored(c[d]) || this.vertexArray.push(c[d]);
	var e = this.useInputOrigin ? this.graph.getBoundingBoxFromGeometry(this.vertexArray) : null,
		f = this.vertexArray.length;
	this.indices = [];
	this.dispX = [];
	this.dispY = [];
	this.cellLocation = [];
	this.isMoveable = [];
	this.neighbours = [];
	this.radius = [];
	this.radiusSquared = [];
	0.001 > this.forceConstant && (this.forceConstant = 0.001);
	this.forceConstantSquared = this.forceConstant * this.forceConstant;
	for (d = 0; d < this.vertexArray.length; d++) {
		var g = this.vertexArray[d];
		this.cellLocation[d] = [];
		var k = CellPath.create(g);
		this.indices[k] = d;
		var l = this.getVertexBounds(g),
			m = l.width,
			n = l.height,
			p = l.x,
			q = l.y;
		this.cellLocation[d][0] = p + m / 2;
		this.cellLocation[d][1] = q + n / 2;
		this.radius[d] = Math.min(m, n);
		this.radiusSquared[d] = this.radius[d] * this.radius[d]
	}
	b.beginUpdate();
	try {
		for (d = 0; d < f; d++) {
			this.dispX[d] = 0;
			this.dispY[d] = 0;
			this.isMoveable[d] = this.isVertexMovable(this.vertexArray[d]);
			var r = this.graph.getConnections(this.vertexArray[d], a),
				c = this.graph.getOpposites(r, this.vertexArray[d]);
			this.neighbours[d] = [];
			for (m = 0; m < c.length; m++) {
				this.resetEdges && this.graph.resetEdge(r[m]);
				this.disableEdgeStyle && this.setEdgeStyleEnabled(r[m], !1);
				var k = CellPath.create(c[m]),
					s = this.indices[k];
				this.neighbours[d][m] = null != s ? s : d
			}
		}
		this.temperature = this.initialTemp;
		0 == this.maxIterations && (this.maxIterations = 20 * Math.sqrt(f));
		for (this.iteration = 0; this.iteration < this.maxIterations; this.iteration++) {
			if (!this.allowedToRun) return;
			this.calcRepulsion();
			this.calcAttraction();
			this.calcPositions();
			this.reduceTemperature()
		}
		a = c = null;
		for (d = 0; d < this.vertexArray.length; d++) g = this.vertexArray[d], this.isVertexMovable(g) && (l = this.getVertexBounds(g), null != l && (this.cellLocation[d][0] -= l.width / 2, this.cellLocation[d][1] -= l.height / 2, p = this.graph.snap(this.cellLocation[d][0]), q = this.graph.snap(this.cellLocation[d][1]), this.setVertexLocation(g, p, q), c = null == c ? p : Math.min(c, p), a = null == a ? q : Math.min(a, q)));
		d = -(c || 0) + 1;
		g = -(a || 0) + 1;
		null != e && (d += e.x, g += e.y);
		this.graph.moveCells(this.vertexArray, d, g)
	} finally {
		b.endUpdate()
	}
};
FastOrganicLayout.prototype.calcPositions = function() {
	for (var a = 0; a < this.vertexArray.length; a++) if (this.isMoveable[a]) {
		var b = Math.sqrt(this.dispX[a] * this.dispX[a] + this.dispY[a] * this.dispY[a]);
		0.001 > b && (b = 0.001);
		var c = this.dispX[a] / b * Math.min(b, this.temperature),
			b = this.dispY[a] / b * Math.min(b, this.temperature);
		this.dispX[a] = 0;
		this.dispY[a] = 0;
		this.cellLocation[a][0] += c;
		this.cellLocation[a][1] += b
	}
};
FastOrganicLayout.prototype.calcAttraction = function() {
	for (var a = 0; a < this.vertexArray.length; a++) for (var b = 0; b < this.neighbours[a].length; b++) {
		var c = this.neighbours[a][b];
		if (a != c && this.isMoveable[a] && this.isMoveable[c]) {
			var d = this.cellLocation[a][0] - this.cellLocation[c][0],
				e = this.cellLocation[a][1] - this.cellLocation[c][1],
				f = d * d + e * e - this.radiusSquared[a] - this.radiusSquared[c];
			f < this.minDistanceLimitSquared && (f = this.minDistanceLimitSquared);
			var g = Math.sqrt(f),
				f = f / this.forceConstant,
				d = d / g * f,
				e = e / g * f;
			this.dispX[a] -= d;
			this.dispY[a] -= e;
			this.dispX[c] += d;
			this.dispY[c] += e
		}
	}
};
FastOrganicLayout.prototype.calcRepulsion = function() {
	for (var a = this.vertexArray.length, b = 0; b < a; b++) for (var c = b; c < a; c++) {
		if (!this.allowedToRun) return;
		if (c != b && this.isMoveable[b] && this.isMoveable[c]) {
			var d = this.cellLocation[b][0] - this.cellLocation[c][0],
				e = this.cellLocation[b][1] - this.cellLocation[c][1];
			0 == d && (d = 0.01 + Math.random());
			0 == e && (e = 0.01 + Math.random());
			var f = Math.sqrt(d * d + e * e),
				g = f - this.radius[b] - this.radius[c];
			g > this.maxDistanceLimit || (g < this.minDistanceLimit && (g = this.minDistanceLimit), g = this.forceConstantSquared / g, d = d / f * g, e = e / f * g, this.dispX[b] += d, this.dispY[b] += e, this.dispX[c] -= d, this.dispY[c] -= e)
		}
	}
};
FastOrganicLayout.prototype.reduceTemperature = function() {
	this.temperature = this.initialTemp * (1 - this.iteration / this.maxIterations)
};

function CircleLayout(a, b) {
	GraphLayout.call(this, a);
	this.radius = null != b ? b : 100
}
CircleLayout.prototype = new GraphLayout;
CircleLayout.prototype.constructor = CircleLayout;
CircleLayout.prototype.radius = null;
CircleLayout.prototype.moveCircle = !1;
CircleLayout.prototype.x0 = 0;
CircleLayout.prototype.y0 = 0;
CircleLayout.prototype.resetEdges = !0;
CircleLayout.prototype.disableEdgeStyle = !0;
CircleLayout.prototype.execute = function(a) {
	var b = this.graph.getModel();
	b.beginUpdate();
	try {
		for (var c = 0, d = null, e = null, f = [], g = b.getChildCount(a), k = 0; k < g; k++) {
			var l = b.getChildAt(a, k);
			if (this.isVertexIgnored(l)) this.isEdgeIgnored(l) || (this.resetEdges && this.graph.resetEdge(l), this.disableEdgeStyle && this.setEdgeStyleEnabled(l, !1));
			else {
				f.push(l);
				var m = this.getVertexBounds(l),
					d = null == d ? m.y : Math.min(d, m.y),
					e = null == e ? m.x : Math.min(e, m.x),
					c = Math.max(c, Math.max(m.width, m.height))
			}
		}
		var n = this.getRadius(f.length, c);
		this.moveCircle && (e = this.x0, d = this.y0);
		this.circle(f, n, e, d)
	} finally {
		b.endUpdate()
	}
};
CircleLayout.prototype.getRadius = function(a, b) {
	return Math.max(a * b / Math.PI, this.radius)
};
CircleLayout.prototype.circle = function(a, b, c, d) {
	for (var e = a.length, f = 2 * Math.PI / e, g = 0; g < e; g++) this.isVertexMovable(a[g]) && this.setVertexLocation(a[g], c + b + b * Math.sin(g * f), d + b + b * Math.cos(g * f))
};

function ParallelEdgeLayout(a) {
	GraphLayout.call(this, a)
}
ParallelEdgeLayout.prototype = new GraphLayout;
ParallelEdgeLayout.prototype.constructor = ParallelEdgeLayout;
ParallelEdgeLayout.prototype.spacing = 20;
ParallelEdgeLayout.prototype.execute = function(a) {
	a = this.findParallels(a);
	this.graph.model.beginUpdate();
	try {
		for (var b in a) {
			var c = a[b];
			1 < c.length && this.layout(c)
		}
	} finally {
		this.graph.model.endUpdate()
	}
};
ParallelEdgeLayout.prototype.findParallels = function(a) {
	for (var b = this.graph.getModel(), c = [], d = b.getChildCount(a), e = 0; e < d; e++) {
		var f = b.getChildAt(a, e);
		if (!this.isEdgeIgnored(f)) {
			var g = this.getEdgeId(f);
			null != g && (null == c[g] && (c[g] = []), c[g].push(f))
		}
	}
	return c
};
ParallelEdgeLayout.prototype.getEdgeId = function(a) {
	var b = this.graph.getView(),
		c = b.getVisibleTerminal(a, !0);
	a = b.getVisibleTerminal(a, !1);
	return null != c && null != a ? (c = CellPath.create(c), a = CellPath.create(a), c > a ? a + "-" + c : c + "-" + a) : null
};
ParallelEdgeLayout.prototype.layout = function(a) {
	var b = a[0],
		c = this.graph.getView(),
		d = this.graph.getModel(),
		e = d.getGeometry(c.getVisibleTerminal(b, !0)),
		d = d.getGeometry(c.getVisibleTerminal(b, !1));
	if (e == d) for (var b = e.x + e.width + this.spacing, c = e.y + e.height / 2, f = 0; f < a.length; f++) this.route(a[f], b, c), b += this.spacing;
	else if (null != e && null != d) {
		var b = e.x + e.width / 2,
			c = e.y + e.height / 2,
			f = d.x + d.width / 2 - b,
			g = d.y + d.height / 2 - c,
			d = Math.sqrt(f * f + g * g);
		if (0 < d) {
			e = g * this.spacing / d;
			d = f * this.spacing / d;
			b = b + f / 2 + e * (a.length - 1) / 2;
			c = c + g / 2 - d * (a.length - 1) / 2;
			for (f = 0; f < a.length; f++) this.route(a[f], b, c), b -= e, c += d
		}
	}
};
ParallelEdgeLayout.prototype.route = function(a, b, c) {
	this.graph.isCellMovable(a) && this.setEdgePoints(a, [new Point(b, c)])
};

function CompositeLayout(a, b, c) {
	GraphLayout.call(this, a);
	this.layouts = b;
	this.master = c
}
CompositeLayout.prototype = new GraphLayout;
CompositeLayout.prototype.constructor = CompositeLayout;
CompositeLayout.prototype.layouts = null;
CompositeLayout.prototype.master = null;
CompositeLayout.prototype.moveCell = function(a, b, c) {
	null != this.master ? this.master.move.apply(this.master, arguments) : this.layouts[0].move.apply(this.layouts[0], arguments)
};
CompositeLayout.prototype.execute = function(a) {
	var b = this.graph.getModel();
	b.beginUpdate();
	try {
		for (var c = 0; c < this.layouts.length; c++) this.layouts[c].execute.apply(this.layouts[c], arguments)
	} finally {
		b.endUpdate()
	}
};

function EdgeLabelLayout(a, b) {
	GraphLayout.call(this, a)
}
EdgeLabelLayout.prototype = new GraphLayout;
EdgeLabelLayout.prototype.constructor = EdgeLabelLayout;
EdgeLabelLayout.prototype.execute = function(a) {
	for (var b = this.graph.view, c = this.graph.getModel(), d = [], e = [], f = c.getChildCount(a), g = 0; g < f; g++) {
		var k = c.getChildAt(a, g),
			l = b.getState(k);
		null != l && (this.isVertexIgnored(k) ? this.isEdgeIgnored(k) || d.push(l) : e.push(l))
	}
	this.placeLabels(e, d)
};
EdgeLabelLayout.prototype.placeLabels = function(a, b) {
	var c = this.graph.getModel();
	c.beginUpdate();
	try {
		for (var d = 0; d < b.length; d++) {
			var e = b[d];
			if (null != e && null != e.text && null != e.text.boundingBox) for (var f = 0; f < a.length; f++) {
				var g = a[f];
				null != g && this.avoid(e, g)
			}
		}
	} finally {
		c.endUpdate()
	}
};
EdgeLabelLayout.prototype.avoid = function(a, b) {
	var c = this.graph.getModel(),
		d = a.text.boundingBox;
	if (Utils.intersects(d, b)) {
		var e = -d.y - d.height + b.y,
			f = -d.y + b.y + b.height,
			e = Math.abs(e) < Math.abs(f) ? e : f,
			f = -d.x - d.width + b.x,
			d = -d.x + b.x + b.width,
			d = Math.abs(f) < Math.abs(d) ? f : d;
		Math.abs(d) < Math.abs(e) ? e = 0 : d = 0;
		f = c.getGeometry(a.cell);
		null != f && (f = f.clone(), null != f.offset ? (f.offset.x += d, f.offset.y += e) : f.offset = new Point(d, e), c.setGeometry(a.cell, f))
	}
};

function GraphAbstractHierarchyCell() {
	this.x = [];
	this.y = [];
	this.temp = []
}
GraphAbstractHierarchyCell.prototype.maxRank = -1;
GraphAbstractHierarchyCell.prototype.minRank = -1;
GraphAbstractHierarchyCell.prototype.x = null;
GraphAbstractHierarchyCell.prototype.y = null;
GraphAbstractHierarchyCell.prototype.width = 0;
GraphAbstractHierarchyCell.prototype.height = 0;
GraphAbstractHierarchyCell.prototype.nextLayerConnectedCells = null;
GraphAbstractHierarchyCell.prototype.previousLayerConnectedCells = null;
GraphAbstractHierarchyCell.prototype.temp = null;
GraphAbstractHierarchyCell.prototype.getNextLayerConnectedCells = function(a) {
	return null
};
GraphAbstractHierarchyCell.prototype.getPreviousLayerConnectedCells = function(a) {
	return null
};
GraphAbstractHierarchyCell.prototype.isEdge = function() {
	return !1
};
GraphAbstractHierarchyCell.prototype.isVertex = function() {
	return !1
};
GraphAbstractHierarchyCell.prototype.getGeneralPurposeVariable = function(a) {
	return null
};
GraphAbstractHierarchyCell.prototype.setGeneralPurposeVariable = function(a, b) {
	return null
};
GraphAbstractHierarchyCell.prototype.setX = function(a, b) {
	this.isVertex() ? this.x[0] = b : this.isEdge() && (this.x[a - this.minRank - 1] = b)
};
GraphAbstractHierarchyCell.prototype.getX = function(a) {
	return this.isVertex() ? this.x[0] : this.isEdge() ? this.x[a - this.minRank - 1] : 0
};
GraphAbstractHierarchyCell.prototype.setY = function(a, b) {
	this.isVertex() ? this.y[0] = b : this.isEdge() && (this.y[a - this.minRank - 1] = b)
};

function GraphHierarchyNode(a) {
	GraphAbstractHierarchyCell.apply(this, arguments);
	this.cell = a;
	this.id = ObjectIdentity.get(a);
	this.connectsAsTarget = [];
	this.connectsAsSource = []
}
GraphHierarchyNode.prototype = new GraphAbstractHierarchyCell;
GraphHierarchyNode.prototype.constructor = GraphHierarchyNode;
GraphHierarchyNode.prototype.cell = null;
GraphHierarchyNode.prototype.id = null;
GraphHierarchyNode.prototype.connectsAsTarget = null;
GraphHierarchyNode.prototype.connectsAsSource = null;
GraphHierarchyNode.prototype.hashCode = !1;
GraphHierarchyNode.prototype.getRankValue = function(a) {
	return this.maxRank
};
GraphHierarchyNode.prototype.getNextLayerConnectedCells = function(a) {
	if (null == this.nextLayerConnectedCells) {
		this.nextLayerConnectedCells = [];
		this.nextLayerConnectedCells[0] = [];
		for (var b = 0; b < this.connectsAsTarget.length; b++) {
			var c = this.connectsAsTarget[b]; - 1 == c.maxRank || c.maxRank == a + 1 ? this.nextLayerConnectedCells[0].push(c.source) : this.nextLayerConnectedCells[0].push(c)
		}
	}
	return this.nextLayerConnectedCells[0]
};
GraphHierarchyNode.prototype.getPreviousLayerConnectedCells = function(a) {
	if (null == this.previousLayerConnectedCells) {
		this.previousLayerConnectedCells = [];
		this.previousLayerConnectedCells[0] = [];
		for (var b = 0; b < this.connectsAsSource.length; b++) {
			var c = this.connectsAsSource[b]; - 1 == c.minRank || c.minRank == a - 1 ? this.previousLayerConnectedCells[0].push(c.target) : this.previousLayerConnectedCells[0].push(c)
		}
	}
	return this.previousLayerConnectedCells[0]
};
GraphHierarchyNode.prototype.isVertex = function() {
	return !0
};
GraphHierarchyNode.prototype.getGeneralPurposeVariable = function(a) {
	return this.temp[0]
};
GraphHierarchyNode.prototype.setGeneralPurposeVariable = function(a, b) {
	this.temp[0] = b
};
GraphHierarchyNode.prototype.isAncestor = function(a) {
	if (null != a && null != this.hashCode && null != a.hashCode && this.hashCode.length < a.hashCode.length) {
		if (this.hashCode == a.hashCode) return !0;
		if (null == this.hashCode || null == this.hashCode) return !1;
		for (var b = 0; b < this.hashCode.length; b++) if (this.hashCode[b] != a.hashCode[b]) return !1;
		return !0
	}
	return !1
};
GraphHierarchyNode.prototype.getCoreCell = function() {
	return this.cell
};

function GraphHierarchyEdge(a) {
	GraphAbstractHierarchyCell.apply(this, arguments);
	this.edges = a;
	this.ids = [];
	for (var b = 0; b < a.length; b++) this.ids.push(ObjectIdentity.get(a[b]))
}
GraphHierarchyEdge.prototype = new GraphAbstractHierarchyCell;
GraphHierarchyEdge.prototype.constructor = GraphHierarchyEdge;
GraphHierarchyEdge.prototype.edges = null;
GraphHierarchyEdge.prototype.ids = null;
GraphHierarchyEdge.prototype.source = null;
GraphHierarchyEdge.prototype.target = null;
GraphHierarchyEdge.prototype.isReversed = !1;
GraphHierarchyEdge.prototype.invert = function(a) {
	a = this.source;
	this.source = this.target;
	this.target = a;
	this.isReversed = !this.isReversed
};
GraphHierarchyEdge.prototype.getNextLayerConnectedCells = function(a) {
	if (null == this.nextLayerConnectedCells) {
		this.nextLayerConnectedCells = [];
		for (var b = 0; b < this.temp.length; b++) this.nextLayerConnectedCells[b] = [], b == this.temp.length - 1 ? this.nextLayerConnectedCells[b].push(this.source) : this.nextLayerConnectedCells[b].push(this)
	}
	return this.nextLayerConnectedCells[a - this.minRank - 1]
};
GraphHierarchyEdge.prototype.getPreviousLayerConnectedCells = function(a) {
	if (null == this.previousLayerConnectedCells) {
		this.previousLayerConnectedCells = [];
		for (var b = 0; b < this.temp.length; b++) this.previousLayerConnectedCells[b] = [], 0 == b ? this.previousLayerConnectedCells[b].push(this.target) : this.previousLayerConnectedCells[b].push(this)
	}
	return this.previousLayerConnectedCells[a - this.minRank - 1]
};
GraphHierarchyEdge.prototype.isEdge = function() {
	return !0
};
GraphHierarchyEdge.prototype.getGeneralPurposeVariable = function(a) {
	return this.temp[a - this.minRank - 1]
};
GraphHierarchyEdge.prototype.setGeneralPurposeVariable = function(a, b) {
	this.temp[a - this.minRank - 1] = b
};
GraphHierarchyEdge.prototype.getCoreCell = function() {
	return null != this.edges && 0 < this.edges.length ? this.edges[0] : null
};

function GraphHierarchyModel(a, b, c, d, e) {
	a.getGraph();
	this.tightenToSource = e;
	this.roots = c;
	this.parent = d;
	this.vertexMapper = new Dictionary;
	this.edgeMapper = new Dictionary;
	this.maxRank = 0;
	c = [];
	null == b && (b = this.graph.getChildVertices(d));
	this.maxRank = this.SOURCESCANSTARTRANK;
	this.createInternalCells(a, b, c);
	for (d = 0; d < b.length; d++) {
		e = c[d].connectsAsSource;
		for (var f = 0; f < e.length; f++) {
			var g = e[f],
				k = g.edges;
			if (null != k && 0 < k.length) {
				var k = k[0],
					l = a.getVisibleTerminal(k, !1),
					l = this.vertexMapper.get(l);
				c[d] == l && (l = a.getVisibleTerminal(k, !0), l = this.vertexMapper.get(l));
				null != l && c[d] != l && (g.target = l, 0 == l.connectsAsTarget.length && (l.connectsAsTarget = []), 0 > Utils.indexOf(l.connectsAsTarget, g) && l.connectsAsTarget.push(g))
			}
		}
		c[d].temp[0] = 1
	}
}
GraphHierarchyModel.prototype.maxRank = null;
GraphHierarchyModel.prototype.vertexMapper = null;
GraphHierarchyModel.prototype.edgeMapper = null;
GraphHierarchyModel.prototype.ranks = null;
GraphHierarchyModel.prototype.roots = null;
GraphHierarchyModel.prototype.parent = null;
GraphHierarchyModel.prototype.dfsCount = 0;
GraphHierarchyModel.prototype.SOURCESCANSTARTRANK = 1E8;
GraphHierarchyModel.prototype.tightenToSource = !1;
GraphHierarchyModel.prototype.createInternalCells = function(a, b, c) {
	for (var d = a.getGraph(), e = 0; e < b.length; e++) {
		c[e] = new GraphHierarchyNode(b[e]);
		this.vertexMapper.put(b[e], c[e]);
		var f = a.getEdges(b[e]);
		c[e].connectsAsSource = [];
		for (var g = 0; g < f.length; g++) {
			var k = a.getVisibleTerminal(f[g], !1);
			if (k != b[e] && a.graph.model.isVertex(k) && !a.isVertexIgnored(k)) {
				var l = a.getEdgesBetween(b[e], k, !1),
					k = a.getEdgesBetween(b[e], k, !0);
				if (null != l && 0 < l.length && null == this.edgeMapper.get(l[0]) && 2 * k.length >= l.length) {
					for (var k = new GraphHierarchyEdge(l), m = 0; m < l.length; m++) {
						var n = l[m];
						this.edgeMapper.put(n, k);
						d.resetEdge(n);
						a.disableEdgeStyle && (a.setEdgeStyleEnabled(n, !1), a.setOrthogonalEdge(n, !0))
					}
					k.source = c[e];
					0 > Utils.indexOf(c[e].connectsAsSource, k) && c[e].connectsAsSource.push(k)
				}
			}
		}
		c[e].temp[0] = 0
	}
};
GraphHierarchyModel.prototype.initialRank = function() {
	var a = [];
	if (null != this.roots) for (var b = 0; b < this.roots.length; b++) {
		var c = this.vertexMapper.get(this.roots[b]);
		null != c && a.push(c)
	}
	for (var d = this.vertexMapper.getValues(), b = 0; b < d.length; b++) d[b].temp[0] = -1;
	for (var e = a.slice(); 0 < a.length;) {
		var c = a[0],
			f, g;
		f = c.connectsAsTarget;
		g = c.connectsAsSource;
		for (var k = !0, l = this.SOURCESCANSTARTRANK, b = 0; b < f.length; b++) {
			var m = f[b];
			if (5270620 == m.temp[0]) m = m.source, l = Math.min(l, m.temp[0] - 1);
			else {
				k = !1;
				break
			}
		}
		if (k) {
			c.temp[0] = l;
			this.maxRank = Math.min(this.maxRank, l);
			if (null != g) for (b = 0; b < g.length; b++) m = g[b], m.temp[0] = 5270620, m = m.target, -1 == m.temp[0] && (a.push(m), m.temp[0] = -2);
			a.shift()
		} else if (b = a.shift(), a.push(c), b == c && 1 == a.length) break
	}
	for (b = 0; b < d.length; b++) d[b].temp[0] -= this.maxRank;
	for (b = 0; b < e.length; b++) {
		c = e[b];
		a = 0;
		f = c.connectsAsSource;
		for (d = 0; d < f.length; d++) m = f[d], m = m.target, c.temp[0] = Math.max(a, m.temp[0] + 1), a = c.temp[0]
	}
	this.maxRank = this.SOURCESCANSTARTRANK - this.maxRank
};
GraphHierarchyModel.prototype.fixRanks = function() {
	var a = [];
	this.ranks = [];
	for (var b = 0; b < this.maxRank + 1; b++) a[b] = [], this.ranks[b] = a[b];
	var c = null;
	if (null != this.roots) for (var d = this.roots, c = [], b = 0; b < d.length; b++) {
		var e = this.vertexMapper.get(d[b]);
		c[b] = e
	}
	this.visit(function(b, c, d, e, m) {
		0 == m && (0 > c.maxRank && 0 > c.minRank) && (a[c.temp[0]].push(c), c.maxRank = c.temp[0], c.minRank = c.temp[0], c.temp[0] = a[c.maxRank].length - 1);
		if (null != b && null != d && 1 < b.maxRank - c.maxRank) {
			d.maxRank = b.maxRank;
			d.minRank = c.maxRank;
			d.temp = [];
			d.x = [];
			d.y = [];
			for (b = d.minRank + 1; b < d.maxRank; b++) a[b].push(d), d.setGeneralPurposeVariable(b, a[b].length - 1)
		}
	}, c, !1, null)
};
GraphHierarchyModel.prototype.visit = function(a, b, c, d) {
	if (null != b) {
		for (var e = 0; e < b.length; e++) {
			var f = b[e];
			null != f && (null == d && (d = {}), c ? (f.hashCode = [], f.hashCode[0] = this.dfsCount, f.hashCode[1] = e, this.extendedDfs(null, f, null, a, d, f.hashCode, e, 0)) : this.dfs(null, f, null, a, d, 0))
		}
		this.dfsCount++
	}
};
GraphHierarchyModel.prototype.dfs = function(a, b, c, d, e, f) {
	if (null != b) {
		var g = b.id;
		if (null == e[g]) {
			e[g] = b;
			d(a, b, c, f, 0);
			a = b.connectsAsSource.slice();
			for (c = 0; c < a.length; c++) g = a[c], this.dfs(b, g.target, g, d, e, f + 1)
		} else d(a, b, c, f, 1)
	}
};
GraphHierarchyModel.prototype.extendedDfs = function(a, b, c, d, e, f, g, k) {
	if (null != b) {
		if (null != a && (null == b.hashCode || b.hashCode[0] != a.hashCode[0])) f = a.hashCode.length + 1, b.hashCode = a.hashCode.slice(), b.hashCode[f - 1] = g;
		g = b.id;
		if (null == e[g]) {
			e[g] = b;
			d(a, b, c, k, 0);
			a = b.connectsAsSource.slice();
			for (c = 0; c < a.length; c++) g = a[c], this.extendedDfs(b, g.target, g, d, e, b.hashCode, c, k + 1)
		} else d(a, b, c, k, 1)
	}
};

function SwimlaneModel(a, b, c, d, e) {
	a.getGraph();
	this.tightenToSource = e;
	this.roots = c;
	this.parent = d;
	this.vertexMapper = {};
	this.edgeMapper = {};
	this.maxRank = 0;
	c = [];
	null == b && (b = this.graph.getChildVertices(d));
	this.maxRank = this.SOURCESCANSTARTRANK;
	this.createInternalCells(a, b, c);
	for (d = 0; d < b.length; d++) {
		e = c[d].connectsAsSource;
		for (var f = 0; f < e.length; f++) {
			var g = e[f],
				k = g.edges;
			if (null != k && 0 < k.length) {
				var k = k[0],
					l = a.getVisibleTerminal(k, !1),
					l = CellPath.create(l),
					l = this.vertexMapper[l];
				c[d] == l && (l = a.getVisibleTerminal(k, !0), l = CellPath.create(l), l = this.vertexMapper[l]);
				null != l && c[d] != l && (g.target = l, 0 == l.connectsAsTarget.length && (l.connectsAsTarget = []), 0 > Utils.indexOf(l.connectsAsTarget, g) && l.connectsAsTarget.push(g))
			}
		}
		c[d].temp[0] = 1
	}
}
SwimlaneModel.prototype.maxRank = null;
SwimlaneModel.prototype.vertexMapper = null;
SwimlaneModel.prototype.edgeMapper = null;
SwimlaneModel.prototype.ranks = null;
SwimlaneModel.prototype.roots = null;
SwimlaneModel.prototype.parent = null;
SwimlaneModel.prototype.dfsCount = 0;
SwimlaneModel.prototype.SOURCESCANSTARTRANK = 1E8;
SwimlaneModel.prototype.ranksPerGroup = null;
SwimlaneModel.prototype.createInternalCells = function(a, b, c) {
	for (var d = a.getGraph(), e = a.swimlanes, f = 0; f < b.length; f++) {
		c[f] = new GraphHierarchyNode(b[f]);
		var g = CellPath.create(b[f]);
		this.vertexMapper[g] = c[f];
		c[f].swimlaneIndex = -1;
		for (g = 0; g < e.length; g++) if (d.model.getParent(b[f]) == e[g]) {
			c[f].swimlaneIndex = g;
			break
		}
		g = a.getEdges(b[f]);
		c[f].connectsAsSource = [];
		for (var k = 0; k < g.length; k++) {
			var l = a.getVisibleTerminal(g[k], !1);
			if (l != b[f] && a.graph.model.isVertex(l) && !a.isVertexIgnored(l)) {
				var m = a.getEdgesBetween(b[f], l, !1),
					n = a.getEdgesBetween(b[f], l, !0),
					l = CellPath.create(m[0]);
				if (null != m && 0 < m.length && null == this.edgeMapper[l] && 2 * n.length >= m.length) {
					for (var n = new GraphHierarchyEdge(m), p = 0; p < m.length; p++) {
						var q = m[p],
							l = CellPath.create(q);
						this.edgeMapper[l] = n;
						d.resetEdge(q);
						a.disableEdgeStyle && (a.setEdgeStyleEnabled(q, !1), a.setOrthogonalEdge(q, !0))
					}
					n.source = c[f];
					0 > Utils.indexOf(c[f].connectsAsSource, n) && c[f].connectsAsSource.push(n)
				}
			}
		}
		c[f].temp[0] = 0
	}
};
SwimlaneModel.prototype.initialRank = function() {
	this.ranksPerGroup = [];
	var a = [],
		b = {};
	if (null != this.roots) for (var c = 0; c < this.roots.length; c++) {
		var d = CellPath.create(this.roots[c]),
			d = this.vertexMapper[d];
		this.maxChainDfs(null, d, null, b, 0);
		null != d && a.push(d)
	}
	d = [];
	b = [];
	for (c = this.ranksPerGroup.length - 1; 0 <= c; c--) d[c] = c == this.ranksPerGroup.length - 1 ? 0 : b[c + 1] + 1, b[c] = d[c] + this.ranksPerGroup[c];
	this.maxRank = b[0];
	for (var e in this.vertexMapper) d = this.vertexMapper[e], d.temp[0] = -1;
	for (a.slice(); 0 < a.length;) {
		var d = a[0],
			f;
		e = d.connectsAsTarget;
		f = d.connectsAsSource;
		for (var g = !0, k = b[0], c = 0; c < e.length; c++) {
			var l = e[c];
			if (5270620 == l.temp[0]) l = l.source, k = Math.min(k, l.temp[0] - 1);
			else {
				g = !1;
				break
			}
		}
		if (g) {
			k > b[d.swimlaneIndex] && (k = b[d.swimlaneIndex]);
			d.temp[0] = k;
			if (null != f) for (c = 0; c < f.length; c++) l = f[c], l.temp[0] = 5270620, l = l.target, -1 == l.temp[0] && (a.push(l), l.temp[0] = -2);
			a.shift()
		} else if (c = a.shift(), a.push(d), c == d && 1 == a.length) break
	}
};
SwimlaneModel.prototype.maxChainDfs = function(a, b, c, d, e) {
	if (null != b && (a = CellPath.create(b.cell), null == d[a])) {
		d[a] = b;
		a = b.swimlaneIndex;
		if (null == this.ranksPerGroup[a] || this.ranksPerGroup[a] < e) this.ranksPerGroup[a] = e;
		a = b.connectsAsSource.slice();
		for (c = 0; c < a.length; c++) {
			var f = a[c],
				g = f.target;
			b.swimlaneIndex < g.swimlaneIndex ? this.maxChainDfs(b, g, f, Utils.clone(d, null, !0), 0) : b.swimlaneIndex == g.swimlaneIndex && this.maxChainDfs(b, g, f, Utils.clone(d, null, !0), e + 1)
		}
	}
};
SwimlaneModel.prototype.fixRanks = function() {
	var a = [];
	this.ranks = [];
	for (var b = 0; b < this.maxRank + 1; b++) a[b] = [], this.ranks[b] = a[b];
	var c = null;
	if (null != this.roots) for (var d = this.roots, c = [], b = 0; b < d.length; b++) {
		var e = CellPath.create(d[b]);
		c[b] = this.vertexMapper[e]
	}
	this.visit(function(b, c, d, e, m) {
		0 == m && (0 > c.maxRank && 0 > c.minRank) && (null == a[c.temp[0]] && Log.show(), a[c.temp[0]].push(c), c.maxRank = c.temp[0], c.minRank = c.temp[0], c.temp[0] = a[c.maxRank].length - 1);
		if (null != b && null != d && 1 < b.maxRank - c.maxRank) {
			d.maxRank = b.maxRank;
			d.minRank = c.maxRank;
			d.temp = [];
			d.x = [];
			d.y = [];
			for (b = d.minRank + 1; b < d.maxRank; b++) a[b].push(d), d.setGeneralPurposeVariable(b, a[b].length - 1)
		}
	}, c, !1, null)
};
SwimlaneModel.prototype.visit = function(a, b, c, d) {
	if (null != b) {
		for (var e = 0; e < b.length; e++) {
			var f = b[e];
			null != f && (null == d && (d = {}), c ? (f.hashCode = [], f.hashCode[0] = this.dfsCount, f.hashCode[1] = e, this.extendedDfs(null, f, null, a, d, f.hashCode, e, 0)) : this.dfs(null, f, null, a, d, 0))
		}
		this.dfsCount++
	}
};
SwimlaneModel.prototype.dfs = function(a, b, c, d, e, f) {
	if (null != b) {
		var g = CellPath.create(b.cell);
		if (null == e[g]) {
			e[g] = b;
			d(a, b, c, f, 0);
			a = b.connectsAsSource.slice();
			for (c = 0; c < a.length; c++) g = a[c], this.dfs(b, g.target, g, d, e, f + 1)
		} else d(a, b, c, f, 1)
	}
};
SwimlaneModel.prototype.extendedDfs = function(a, b, c, d, e, f, g, k) {
	if (null != b) {
		if (null != a && (null == b.hashCode || b.hashCode[0] != a.hashCode[0])) f = a.hashCode.length + 1, b.hashCode = a.hashCode.slice(), b.hashCode[f - 1] = g;
		g = CellPath.create(b.cell);
		if (null == e[g]) {
			e[g] = b;
			d(a, b, c, k, 0);
			a = b.connectsAsSource.slice();
			c = b.connectsAsTarget.slice();
			for (g = 0; g < a.length; g++) {
				f = a[g];
				var l = f.target;
				null == l && Log.show();
				b.swimlaneIndex <= l.swimlaneIndex && this.extendedDfs(b, l, f, d, e, b.hashCode, g, k + 1)
			}
			for (g = 0; g < c.length; g++) f = c[g], l = f.source, b.swimlaneIndex < l.swimlaneIndex && this.extendedDfs(b, l, f, d, e, b.hashCode, g, k + 1)
		} else d(a, b, c, k, 1)
	}
};

function HierarchicalLayoutStage() {}
HierarchicalLayoutStage.prototype.execute = function(a) {};

function MedianHybridCrossingReduction(a) {
	this.layout = a
}
MedianHybridCrossingReduction.prototype = new HierarchicalLayoutStage;
MedianHybridCrossingReduction.prototype.constructor = MedianHybridCrossingReduction;
MedianHybridCrossingReduction.prototype.layout = null;
MedianHybridCrossingReduction.prototype.maxIterations = 24;
MedianHybridCrossingReduction.prototype.nestedBestRanks = null;
MedianHybridCrossingReduction.prototype.currentBestCrossings = 0;
MedianHybridCrossingReduction.prototype.iterationsWithoutImprovement = 0;
MedianHybridCrossingReduction.prototype.maxNoImprovementIterations = 2;
MedianHybridCrossingReduction.prototype.execute = function(a) {
	a = this.layout.getModel();
	this.nestedBestRanks = [];
	for (var b = 0; b < a.ranks.length; b++) this.nestedBestRanks[b] = a.ranks[b].slice();
	for (var c = 0, d = this.calculateCrossings(a), b = 0; b < this.maxIterations && c < this.maxNoImprovementIterations; b++) {
		this.weightedMedian(b, a);
		this.transpose(b, a);
		var e = this.calculateCrossings(a);
		if (e < d) {
			d = e;
			for (e = c = 0; e < this.nestedBestRanks.length; e++) for (var f = a.ranks[e], g = 0; g < f.length; g++) {
				var k = f[g];
				this.nestedBestRanks[e][k.getGeneralPurposeVariable(e)] = k
			}
		} else {
			c++;
			for (e = 0; e < this.nestedBestRanks.length; e++) {
				f = a.ranks[e];
				for (g = 0; g < f.length; g++) k = f[g], k.setGeneralPurposeVariable(e, g)
			}
		}
		if (0 == d) break
	}
	c = [];
	d = [];
	for (b = 0; b < a.maxRank + 1; b++) d[b] = [], c[b] = d[b];
	for (b = 0; b < this.nestedBestRanks.length; b++) for (e = 0; e < this.nestedBestRanks[b].length; e++) d[b].push(this.nestedBestRanks[b][e]);
	a.ranks = c
};
MedianHybridCrossingReduction.prototype.calculateCrossings = function(a) {
	for (var b = a.ranks.length, c = 0, d = 1; d < b; d++) c += this.calculateRankCrossing(d, a);
	return c
};
MedianHybridCrossingReduction.prototype.calculateRankCrossing = function(a, b) {
	for (var c = 0, d = b.ranks[a], e = b.ranks[a - 1], f = [], g = 0; g < d.length; g++) {
		for (var k = d[g], l = k.getGeneralPurposeVariable(a), k = k.getPreviousLayerConnectedCells(a), m = [], n = 0; n < k.length; n++) {
			var p = k[n].getGeneralPurposeVariable(a - 1);
			m.push(p)
		}
		m.sort(function(a, b) {
			return a - b
		});
		f[l] = m
	}
	d = [];
	for (g = 0; g < f.length; g++) d = d.concat(f[g]);
	for (f = 1; f < e.length;) f <<= 1;
	l = 2 * f - 1;
	f -= 1;
	e = [];
	for (g = 0; g < l; ++g) e[g] = 0;
	for (g = 0; g < d.length; g++) {
		l = d[g] + f;
		for (++e[l]; 0 < l;) l % 2 && (c += e[l + 1]), l = l - 1 >> 1, ++e[l]
	}
	return c
};
MedianHybridCrossingReduction.prototype.transpose = function(a, b) {
	for (var c = !0, d = 0; c && 10 > d++;) for (var e = 1 == a % 2 && 1 == d % 2, c = !1, f = 0; f < b.ranks.length; f++) {
		for (var g = b.ranks[f], k = [], l = 0; l < g.length; l++) {
			var m = g[l],
				n = m.getGeneralPurposeVariable(f);
			0 > n && (n = l);
			k[n] = m
		}
		for (var p = n = m = null, q = null, r = null, s = null, t = null, u = null, x = null, y = null, l = 0; l < g.length - 1; l++) {
			if (0 == l) {
				for (var x = k[l], m = x.getNextLayerConnectedCells(f), n = x.getPreviousLayerConnectedCells(f), r = [], s = [], v = 0; v < m.length; v++) r[v] = m[v].getGeneralPurposeVariable(f + 1);
				for (v = 0; v < n.length; v++) s[v] = n[v].getGeneralPurposeVariable(f - 1)
			} else m = p, n = q, r = t, s = u, x = y;
			y = k[l + 1];
			p = y.getNextLayerConnectedCells(f);
			q = y.getPreviousLayerConnectedCells(f);
			t = [];
			u = [];
			for (v = 0; v < p.length; v++) t[v] = p[v].getGeneralPurposeVariable(f + 1);
			for (v = 0; v < q.length; v++) u[v] = q[v].getGeneralPurposeVariable(f - 1);
			for (var z = 0, B = 0, v = 0; v < r.length; v++) for (var A = 0; A < t.length; A++) r[v] > t[A] && z++, r[v] < t[A] && B++;
			for (v = 0; v < s.length; v++) for (A = 0; A < u.length; A++) s[v] > u[A] && z++, s[v] < u[A] && B++;
			if (B < z || B == z && e) p = x.getGeneralPurposeVariable(f), x.setGeneralPurposeVariable(f, y.getGeneralPurposeVariable(f)), y.setGeneralPurposeVariable(f, p), p = m, q = n, t = r, u = s, y = x, e || (c = !0)
		}
	}
};
MedianHybridCrossingReduction.prototype.weightedMedian = function(a, b) {
	var c = 0 == a % 2;
	if (c) for (var d = b.maxRank - 1; 0 <= d; d--) this.medianRank(d, c);
	else for (d = 1; d < b.maxRank; d++) this.medianRank(d, c)
};
MedianHybridCrossingReduction.prototype.medianRank = function(a, b) {
	for (var c = this.nestedBestRanks[a].length, d = [], e = [], f = 0; f < c; f++) {
		var g = this.nestedBestRanks[a][f],
			k = new MedianCellSorter;
		k.cell = g;
		var l;
		l = b ? g.getNextLayerConnectedCells(a) : g.getPreviousLayerConnectedCells(a);
		var m;
		m = b ? a + 1 : a - 1;
		null != l && 0 != l.length ? (k.medianValue = this.medianValue(l, m), d.push(k)) : e[g.getGeneralPurposeVariable(a)] = !0
	}
	d.sort(MedianCellSorter.prototype.compare);
	for (f = 0; f < c; f++) null == e[f] && (g = d.shift().cell, g.setGeneralPurposeVariable(a, f))
};
MedianHybridCrossingReduction.prototype.medianValue = function(a, b) {
	for (var c = [], d = 0, e = 0; e < a.length; e++) {
		var f = a[e];
		c[d++] = f.getGeneralPurposeVariable(b)
	}
	c.sort(function(a, b) {
		return a - b
	});
	if (1 == d % 2) return c[Math.floor(d / 2)];
	if (2 == d) return (c[0] + c[1]) / 2;
	e = d / 2;
	f = c[e - 1] - c[0];
	d = c[d - 1] - c[e];
	return (c[e - 1] * d + c[e] * f) / (f + d)
};

function MedianCellSorter() {}
MedianCellSorter.prototype.medianValue = 0;
MedianCellSorter.prototype.cell = !1;
MedianCellSorter.prototype.compare = function(a, b) {
	return null != a && null != b ? b.medianValue > a.medianValue ? -1 : b.medianValue < a.medianValue ? 1 : 0 : 0
};

function MinimumCycleRemover(a) {
	this.layout = a
}
MinimumCycleRemover.prototype = new HierarchicalLayoutStage;
MinimumCycleRemover.prototype.constructor = MinimumCycleRemover;
MinimumCycleRemover.prototype.layout = null;
MinimumCycleRemover.prototype.execute = function(a) {
	a = this.layout.getModel();
	for (var b = {}, c = a.vertexMapper.getValues(), d = {}, e = 0; e < c.length; e++) d[c[e].id] = c[e];
	c = null;
	if (null != a.roots) for (var f = a.roots, c = [], e = 0; e < f.length; e++) c[e] = a.vertexMapper.get(f[e]);
	a.visit(function(a, c, e, f, n) {
		c.isAncestor(a) && (e.invert(), Utils.remove(e, a.connectsAsSource), a.connectsAsTarget.push(e), Utils.remove(e, c.connectsAsTarget), c.connectsAsSource.push(e));
		b[c.id] = c;
		delete d[c.id]
	}, c, !0, null);
	e = Utils.clone(b, null, !0);
	a.visit(function(a, c, e, f, n) {
		c.isAncestor(a) && (e.invert(), Utils.remove(e, a.connectsAsSource), c.connectsAsSource.push(e), a.connectsAsTarget.push(e), Utils.remove(e, c.connectsAsTarget));
		b[c.id] = c;
		delete d[c.id]
	}, d, !0, e)
};

function CoordinateAssignment(a, b, c, d, e, f) {
	this.layout = a;
	this.intraCellSpacing = b;
	this.interRankCellSpacing = c;
	this.orientation = d;
	this.initialX = e;
	this.parallelEdgeSpacing = f
}
var HierarchicalEdgeStyle = {
	ORTHOGONAL: 1,
	POLYLINE: 2,
	STRAIGHT: 3,
	CURVE: 4
};
CoordinateAssignment.prototype = new HierarchicalLayoutStage;
CoordinateAssignment.prototype.constructor = CoordinateAssignment;
CoordinateAssignment.prototype.layout = null;
CoordinateAssignment.prototype.intraCellSpacing = 30;
CoordinateAssignment.prototype.interRankCellSpacing = 100;
CoordinateAssignment.prototype.parallelEdgeSpacing = 10;
CoordinateAssignment.prototype.maxIterations = 8;
CoordinateAssignment.prototype.prefHozEdgeSep = 5;
CoordinateAssignment.prototype.prefVertEdgeOff = 2;
CoordinateAssignment.prototype.minEdgeJetty = 12;
CoordinateAssignment.prototype.channelBuffer = 4;
CoordinateAssignment.prototype.jettyPositions = null;
CoordinateAssignment.prototype.orientation = Constants.DIRECTION_NORTH;
CoordinateAssignment.prototype.initialX = null;
CoordinateAssignment.prototype.limitX = null;
CoordinateAssignment.prototype.currentXDelta = null;
CoordinateAssignment.prototype.widestRank = null;
CoordinateAssignment.prototype.rankTopY = null;
CoordinateAssignment.prototype.rankBottomY = null;
CoordinateAssignment.prototype.widestRankValue = null;
CoordinateAssignment.prototype.rankWidths = null;
CoordinateAssignment.prototype.rankY = null;
CoordinateAssignment.prototype.fineTuning = !0;
CoordinateAssignment.prototype.edgeStyle = HierarchicalEdgeStyle.POLYLINE;
CoordinateAssignment.prototype.nextLayerConnectedCache = null;
CoordinateAssignment.prototype.previousLayerConnectedCache = null;
CoordinateAssignment.prototype.groupPadding = 10;
CoordinateAssignment.prototype.printStatus = function() {
	var a = this.layout.getModel();
	Log.show();
	Log.writeln("======Coord assignment debug=======");
	for (var b = 0; b < a.ranks.length; b++) {
		Log.write("Rank ", b, " : ");
		for (var c = a.ranks[b], d = 0; d < c.length; d++) Log.write(c[d].getGeneralPurposeVariable(b), "  ");
		Log.writeln()
	}
	Log.writeln("====================================")
};
CoordinateAssignment.prototype.execute = function(a) {
	this.jettyPositions = {};
	a = this.layout.getModel();
	this.currentXDelta = 0;
	this.initialCoords(this.layout.getGraph(), a);
	this.fineTuning && this.minNode(a);
	var b = 1E8;
	if (this.fineTuning) for (var c = 0; c < this.maxIterations; c++) {
		0 != c && (this.medianPos(c, a), this.minNode(a));
		if (this.currentXDelta < b) {
			for (var d = 0; d < a.ranks.length; d++) for (var e = a.ranks[d], f = 0; f < e.length; f++) {
				var g = e[f];
				g.setX(d, g.getGeneralPurposeVariable(d))
			}
			b = this.currentXDelta
		} else for (d = 0; d < a.ranks.length; d++) {
			e = a.ranks[d];
			for (f = 0; f < e.length; f++) g = e[f], g.setGeneralPurposeVariable(d, g.getX(d))
		}
		this.minPath(this.layout.getGraph(), a);
		this.currentXDelta = 0
	}
	this.setCellLocations(this.layout.getGraph(), a)
};
CoordinateAssignment.prototype.minNode = function(a) {
	for (var b = [], c = new Dictionary, d = [], e = 0; e <= a.maxRank; e++) {
		d[e] = a.ranks[e];
		for (var f = 0; f < d[e].length; f++) {
			var g = d[e][f],
				k = new WeightedCellSorter(g, e);
			k.rankIndex = f;
			k.visited = !0;
			b.push(k);
			c.put(g, k)
		}
	}
	a = 10 * b.length;
	for (f = 0; 0 < b.length && f <= a;) {
		var g = b.shift(),
			e = g.cell,
			l = g.weightedValue,
			m = parseInt(g.rankIndex),
			k = e.getNextLayerConnectedCells(l),
			n = e.getPreviousLayerConnectedCells(l),
			p = k.length,
			q = n.length,
			r = this.medianXValue(k, l + 1),
			s = this.medianXValue(n, l - 1),
			t = p + q,
			u = e.getGeneralPurposeVariable(l),
			x = u;
		0 < t && (x = (r * p + s * q) / t);
		p = !1;
		x < u - 1 ? 0 == m ? (e.setGeneralPurposeVariable(l, x), p = !0) : (m = d[l][m - 1], u = m.getGeneralPurposeVariable(l), u = u + m.width / 2 + this.intraCellSpacing + e.width / 2, u < x ? (e.setGeneralPurposeVariable(l, x), p = !0) : u < e.getGeneralPurposeVariable(l) - 1 && (e.setGeneralPurposeVariable(l, u), p = !0)) : x > u + 1 && (m == d[l].length - 1 ? (e.setGeneralPurposeVariable(l, x), p = !0) : (m = d[l][m + 1], u = m.getGeneralPurposeVariable(l), u = u - m.width / 2 - this.intraCellSpacing - e.width / 2, u > x ? (e.setGeneralPurposeVariable(l, x), p = !0) : u > e.getGeneralPurposeVariable(l) + 1 && (e.setGeneralPurposeVariable(l, u), p = !0)));
		if (p) {
			for (e = 0; e < k.length; e++) l = k[e], l = c.get(l), null != l && !1 == l.visited && (l.visited = !0, b.push(l));
			for (e = 0; e < n.length; e++) l = n[e], l = c.get(l), null != l && !1 == l.visited && (l.visited = !0, b.push(l))
		}
		g.visited = !1;
		f++
	}
};
CoordinateAssignment.prototype.medianPos = function(a, b) {
	if (0 == a % 2) for (var c = b.maxRank; 0 < c; c--) this.rankMedianPosition(c - 1, b, c);
	else for (c = 0; c < b.maxRank - 1; c++) this.rankMedianPosition(c + 1, b, c)
};
CoordinateAssignment.prototype.rankMedianPosition = function(a, b, c) {
	b = b.ranks[a];
	for (var d = [], e = {}, f = 0; f < b.length; f++) {
		var g = b[f];
		d[f] = new WeightedCellSorter;
		d[f].cell = g;
		d[f].rankIndex = f;
		e[g.id] = d[f];
		var k = null,
			k = c < a ? g.getPreviousLayerConnectedCells(a) : g.getNextLayerConnectedCells(a);
		d[f].weightedValue = this.calculatedWeightedValue(g, k)
	}
	d.sort(WeightedCellSorter.prototype.compare);
	for (f = 0; f < d.length; f++) {
		var l = 0,
			g = d[f].cell,
			l = 0,
			k = c < a ? g.getPreviousLayerConnectedCells(a).slice() : g.getNextLayerConnectedCells(a).slice();
		null != k && (l = k.length, l = 0 < l ? this.medianXValue(k, c) : g.getGeneralPurposeVariable(a));
		for (var m = 0, k = -1E8, n = d[f].rankIndex - 1; 0 <= n;) {
			var p = e[b[n].id];
			if (null != p) {
				var q = p.cell;
				p.visited ? (k = q.getGeneralPurposeVariable(a) + q.width / 2 + this.intraCellSpacing + m + g.width / 2, n = -1) : (m += q.width + this.intraCellSpacing, n--)
			}
		}
		m = 0;
		q = 1E8;
		for (n = d[f].rankIndex + 1; n < d.length;) if (p = e[b[n].id], null != p) {
			var r = p.cell;
			p.visited ? (q = r.getGeneralPurposeVariable(a) - r.width / 2 - this.intraCellSpacing - m - g.width / 2, n = d.length) : (m += r.width + this.intraCellSpacing, n++)
		}
		l >= k && l <= q ? g.setGeneralPurposeVariable(a, l) : l < k ? (g.setGeneralPurposeVariable(a, k), this.currentXDelta += k - l) : l > q && (g.setGeneralPurposeVariable(a, q), this.currentXDelta += l - q);
		d[f].visited = !0
	}
};
CoordinateAssignment.prototype.calculatedWeightedValue = function(a, b) {
	for (var c = 0, d = 0; d < b.length; d++) {
		var e = b[d];
		a.isVertex() && e.isVertex() ? c++ : c = a.isEdge() && e.isEdge() ? c + 8 : c + 2
	}
	return c
};
CoordinateAssignment.prototype.medianXValue = function(a, b) {
	if (0 == a.length) return 0;
	for (var c = [], d = 0; d < a.length; d++) c[d] = a[d].getGeneralPurposeVariable(b);
	c.sort(function(a, b) {
		return a - b
	});
	if (1 == a.length % 2) return c[Math.floor(a.length / 2)];
	d = a.length / 2;
	return (c[d - 1] + c[d]) / 2
};
CoordinateAssignment.prototype.initialCoords = function(a, b) {
	this.calculateWidestRank(a, b);
	for (var c = this.widestRank; 0 <= c; c--) c < b.maxRank && this.rankCoordinates(c, a, b);
	for (c = this.widestRank + 1; c <= b.maxRank; c++) 0 < c && this.rankCoordinates(c, a, b)
};
CoordinateAssignment.prototype.rankCoordinates = function(a, b, c) {
	b = c.ranks[a];
	c = 0;
	for (var d = this.initialX + (this.widestRankValue - this.rankWidths[a]) / 2, e = !1, f = 0; f < b.length; f++) {
		var g = b[f];
		if (g.isVertex()) {
			var k = this.layout.getVertexBounds(g.cell);
			null != k ? this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_SOUTH ? (g.width = k.width, g.height = k.height) : (g.width = k.height, g.height = k.width) : e = !0;
			c = Math.max(c, g.height)
		} else g.isEdge() && (k = 1, null != g.edges ? k = g.edges.length : Log.warn("edge.edges is null"), g.width = (k - 1) * this.parallelEdgeSpacing);
		d += g.width / 2;
		g.setX(a, d);
		g.setGeneralPurposeVariable(a, d);
		d += g.width / 2;
		d += this.intraCellSpacing
	}!0 == e && Log.warn("At least one cell has no bounds")
};
CoordinateAssignment.prototype.calculateWidestRank = function(a, b) {
	var c = -this.interRankCellSpacing,
		d = 0;
	this.rankWidths = [];
	this.rankY = [];
	for (var e = b.maxRank; 0 <= e; e--) {
		for (var f = 0, g = b.ranks[e], k = this.initialX, l = !1, m = 0; m < g.length; m++) {
			var n = g[m];
			if (n.isVertex()) {
				var p = this.layout.getVertexBounds(n.cell);
				null != p ? this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_SOUTH ? (n.width = p.width, n.height = p.height) : (n.width = p.height, n.height = p.width) : l = !0;
				f = Math.max(f, n.height)
			} else n.isEdge() && (p = 1, null != n.edges ? p = n.edges.length : Log.warn("edge.edges is null"), n.width = (p - 1) * this.parallelEdgeSpacing);
			k += n.width / 2;
			n.setX(e, k);
			n.setGeneralPurposeVariable(e, k);
			k += n.width / 2;
			k += this.intraCellSpacing;
			k > this.widestRankValue && (this.widestRankValue = k, this.widestRank = e);
			this.rankWidths[e] = k
		}!0 == l && Log.warn("At least one cell has no bounds");
		this.rankY[e] = c;
		k = f / 2 + d / 2 + this.interRankCellSpacing;
		d = f;
		c = this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_WEST ? c + k : c - k;
		for (m = 0; m < g.length; m++) g[m].setY(e, c)
	}
};
CoordinateAssignment.prototype.minPath = function(a, b) {
	for (var c = b.edgeMapper.getValues(), d = 0; d < c.length; d++) {
		var e = c[d];
		if (!(1 > e.maxRank - e.minRank - 1)) {
			for (var f = e.getGeneralPurposeVariable(e.minRank + 1), g = !0, k = 0, l = e.minRank + 2; l < e.maxRank; l++) {
				var m = e.getGeneralPurposeVariable(l);
				f != m ? (g = !1, f = m) : k++
			}
			if (!g) {
				for (var g = f = 0, m = [], n = [], p = e.getGeneralPurposeVariable(e.minRank + 1), l = e.minRank + 1; l < e.maxRank - 1; l++) {
					var q = e.getX(l + 1);
					p == q ? (m[l - e.minRank - 1] = p, f++) : this.repositionValid(b, e, l + 1, p) ? (m[l - e.minRank - 1] = p, f++) : p = m[l - e.minRank - 1] = q
				}
				p = e.getX(l);
				for (l = e.maxRank - 1; l > e.minRank + 1; l--) q = e.getX(l - 1), p == q ? (n[l - e.minRank - 2] = p, g++) : this.repositionValid(b, e, l - 1, p) ? (n[l - e.minRank - 2] = p, g++) : (n[l - e.minRank - 2] = e.getX(l - 1), p = q);
				if (g > k || f > k) if (g >= f) for (l = e.maxRank - 2; l > e.minRank; l--) e.setX(l, n[l - e.minRank - 1]);
				else if (f > g) for (l = e.minRank + 2; l < e.maxRank; l++) e.setX(l, m[l - e.minRank - 2])
			}
		}
	}
};
CoordinateAssignment.prototype.repositionValid = function(a, b, c, d) {
	a = a.ranks[c];
	for (var e = -1, f = 0; f < a.length; f++) if (b == a[f]) {
		e = f;
		break
	}
	if (0 > e) return !1;
	f = b.getGeneralPurposeVariable(c);
	if (d < f) {
		if (0 == e) return !0;
		a = a[e - 1];
		c = a.getGeneralPurposeVariable(c);
		c = c + a.width / 2 + this.intraCellSpacing + b.width / 2;
		if (!(c <= d)) return !1
	} else if (d > f) {
		if (e == a.length - 1) return !0;
		a = a[e + 1];
		c = a.getGeneralPurposeVariable(c);
		c = c - a.width / 2 - this.intraCellSpacing - b.width / 2;
		if (!(c >= d)) return !1
	}
	return !0
};
CoordinateAssignment.prototype.setCellLocations = function(a, b) {
	this.rankTopY = [];
	this.rankBottomY = [];
	for (var c = 0; c < b.ranks.length; c++) this.rankTopY[c] = Number.MAX_VALUE, this.rankBottomY[c] = 0;
	var d = null;
	this.layout.resizeParent && (d = {});
	for (var e = b.vertexMapper.getValues(), c = 0; c < e.length; c++) if (this.setVertexLocation(e[c]), this.layout.resizeParent) {
		var f = a.model.getParent(e[c].cell),
			g = ObjectIdentity.get(f);
		null == d[g] && (d[g] = f)
	}
	this.layout.resizeParent && null != d && this.adjustParents(d);
	(this.edgeStyle == HierarchicalEdgeStyle.ORTHOGONAL || this.edgeStyle == HierarchicalEdgeStyle.POLYLINE || this.edgeStyle == HierarchicalEdgeStyle.CURVE) && this.localEdgeProcessing(b);
	d = b.edgeMapper.getValues();
	for (c = 0; c < d.length; c++) this.setEdgePosition(d[c])
};
CoordinateAssignment.prototype.adjustParents = function(a) {
	var b = [],
		c;
	for (c in a) b.push(a[c]);
	this.layout.arrangeGroups(Utils.sortCells(b, !0), this.groupPadding)
};
CoordinateAssignment.prototype.localEdgeProcessing = function(a) {
	for (var b = 0; b < a.ranks.length; b++) for (var c = a.ranks[b], d = 0; d < c.length; d++) {
		var e = c[d];
		if (e.isVertex()) for (var f = e.getPreviousLayerConnectedCells(b), g = b - 1, k = 0; 2 > k; k++) {
			if (-1 < g && g < a.ranks.length && null != f && 0 < f.length) {
				for (var l = [], m = 0; m < f.length; m++) {
					var n = new WeightedCellSorter(f[m], f[m].getX(g));
					l.push(n)
				}
				l.sort(WeightedCellSorter.prototype.compare);
				for (var n = e.x[0] - e.width / 2, p = n + e.width, q = f = 0, g = [], m = 0; m < l.length; m++) {
					var r = l[m].cell,
						s;
					if (r.isVertex()) {
						s = 0 == k ? e.connectsAsSource : e.connectsAsTarget;
						for (var t = 0; t < s.length; t++) if (s[t].source == r || s[t].target == r) f += s[t].edges.length, q++, g.push(s[t])
					} else f += r.edges.length, q++, g.push(r)
				}
				e.width > (f + 1) * this.prefHozEdgeSep + 2 * this.prefHozEdgeSep && (n += this.prefHozEdgeSep, p -= this.prefHozEdgeSep);
				l = (p - n) / f;
				n += l / 2;
				p = this.minEdgeJetty - this.prefVertEdgeOff;
				for (m = q = 0; m < g.length; m++) {
					r = g[m].edges.length;
					s = this.jettyPositions[g[m].ids[0]];
					null == s && (s = [], this.jettyPositions[g[m].ids[0]] = s);
					m < f / 2 ? p += this.prefVertEdgeOff : m > f / 2 && (p -= this.prefVertEdgeOff);
					for (t = 0; t < r; t++) s[4 * t + 2 * k] = n, n += l, s[4 * t + 2 * k + 1] = p;
					q = Math.max(q, p)
				}
			}
			f = e.getNextLayerConnectedCells(b);
			g = b + 1
		}
	}
};
CoordinateAssignment.prototype.setEdgePosition = function(a) {
	var b = 0;
	if (101207 != a.temp[0]) {
		var c = a.maxRank,
			d = a.minRank;
		c == d && (c = a.source.maxRank, d = a.target.minRank);
		for (var e = 0, f = this.jettyPositions[a.ids[0]], g = a.isReversed ? a.target.cell : a.source.cell, k = this.layout.graph, l = 0; l < a.edges.length; l++) {
			var m = a.edges[l],
				n = this.layout.getVisibleTerminal(m, !0),
				p = k.model.getTerminal(m, !0),
				q = [],
				r = a.isReversed;
			n != g && (r = !r);
			if (null != f) {
				var s = r ? 2 : 0,
					t = r ? this.rankTopY[d] : this.rankBottomY[c],
					u = f[4 * e + 1 + s];
				r && (u = -u);
				t += u;
				s = f[4 * e + s];
				p = k.model.getTerminal(m, !0);
				this.layout.isPort(p) && k.model.getParent(p) == n && (s = k.view.getState(p), s = null != s ? s.x : n.geometry.x + a.source.width * p.geometry.x);
				this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_SOUTH ? (q.push(new Point(s, t)), this.edgeStyle == HierarchicalEdgeStyle.CURVE && q.push(new Point(s, t + u))) : (q.push(new Point(t, s)), this.edgeStyle == HierarchicalEdgeStyle.CURVE && q.push(new Point(t + u, s)))
			}
			s = a.x.length - 1;
			t = u = -1;
			n = a.maxRank - 1;
			r && (s = 0, u = a.x.length, t = 1, n = a.minRank + 1);
			for (; a.maxRank != a.minRank && s != u; s += t) {
				var p = a.x[s] + b,
					x = (this.rankTopY[n] + this.rankBottomY[n + 1]) / 2,
					y = (this.rankTopY[n - 1] + this.rankBottomY[n]) / 2;
				if (r) var v = x,
					x = y,
					y = v;
				this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_SOUTH ? (q.push(new Point(p, x)), q.push(new Point(p, y))) : (q.push(new Point(x, p)), q.push(new Point(y, p)));
				this.limitX = Math.max(this.limitX, p);
				n += t
			}
			null != f && (s = r ? 2 : 0, t = r ? this.rankBottomY[c] : this.rankTopY[d], u = f[4 * e + 3 - s], r && (u = -u), t -= u, s = f[4 * e + 2 - s], r = k.model.getTerminal(m, !1), n = this.layout.getVisibleTerminal(m, !1), this.layout.isPort(r) && k.model.getParent(r) == n && (s = k.view.getState(r), s = null != s ? s.x : n.geometry.x + a.target.width * r.geometry.x), this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_SOUTH ? (this.edgeStyle == HierarchicalEdgeStyle.CURVE && q.push(new Point(s, t - u)), q.push(new Point(s, t))) : (this.edgeStyle == HierarchicalEdgeStyle.CURVE && q.push(new Point(t - u, s)), q.push(new Point(t, s))));
			a.isReversed && this.processReversedEdge(a, m);
			this.layout.setEdgePoints(m, q);
			b = 0 == b ? this.parallelEdgeSpacing : 0 < b ? -b : -b + this.parallelEdgeSpacing;
			e++
		}
		a.temp[0] = 101207
	}
};
CoordinateAssignment.prototype.setVertexLocation = function(a) {
	var b = a.cell,
		c = a.x[0] - a.width / 2,
		d = a.y[0] - a.height / 2;
	this.rankTopY[a.minRank] = Math.min(this.rankTopY[a.minRank], d);
	this.rankBottomY[a.minRank] = Math.max(this.rankBottomY[a.minRank], d + a.height);
	this.orientation == Constants.DIRECTION_NORTH || this.orientation == Constants.DIRECTION_SOUTH ? this.layout.setVertexLocation(b, c, d) : this.layout.setVertexLocation(b, d, c);
	this.limitX = Math.max(this.limitX, c + a.width)
};
CoordinateAssignment.prototype.processReversedEdge = function(a, b) {};

function WeightedCellSorter(a, b) {
	this.cell = a;
	this.weightedValue = b
}
WeightedCellSorter.prototype.weightedValue = 0;
WeightedCellSorter.prototype.nudge = !1;
WeightedCellSorter.prototype.visited = !1;
WeightedCellSorter.prototype.rankIndex = null;
WeightedCellSorter.prototype.cell = null;
WeightedCellSorter.prototype.compare = function(a, b) {
	return null != a && null != b ? b.weightedValue > a.weightedValue ? -1 : b.weightedValue < a.weightedValue ? 1 : b.nudge ? -1 : 1 : 0
};

function SwimlaneOrdering(a) {
	this.layout = a
}
SwimlaneOrdering.prototype = new HierarchicalLayoutStage;
SwimlaneOrdering.prototype.constructor = SwimlaneOrdering;
SwimlaneOrdering.prototype.layout = null;
SwimlaneOrdering.prototype.execute = function(a) {
	a = this.layout.getModel();
	var b = Utils.clone(a.vertexMapper, null, !0),
		c = null;
	if (null != a.roots) for (var d = a.roots, c = [], e = 0; e < d.length; e++) {
		var f = CellPath.create(d[e]);
		c[e] = a.vertexMapper[f]
	}
	a.visit(function(a, c, d, e, f) {
		e = null != a && a.swimlaneIndex == c.swimlaneIndex && c.isAncestor(a);
		f = null != a && null != d && a.swimlaneIndex < c.swimlaneIndex && d.source == c;
		e ? (d.invert(), Utils.remove(d, a.connectsAsSource), c.connectsAsSource.push(d), a.connectsAsTarget.push(d), Utils.remove(d, c.connectsAsTarget)) : f && (d.invert(), Utils.remove(d, a.connectsAsTarget), c.connectsAsTarget.push(d), a.connectsAsSource.push(d), Utils.remove(d, c.connectsAsSource));
		a = CellPath.create(c.cell);
		delete b[a]
	}, c, !0, null)
};

function HierarchicalLayout(a, b, c) {
	GraphLayout.call(this, a);
	this.orientation = null != b ? b : Constants.DIRECTION_NORTH;
	this.deterministic = null != c ? c : !0
}
HierarchicalLayout.prototype = new GraphLayout;
HierarchicalLayout.prototype.constructor = HierarchicalLayout;
HierarchicalLayout.prototype.roots = null;
HierarchicalLayout.prototype.resizeParent = !1;
HierarchicalLayout.prototype.moveParent = !1;
HierarchicalLayout.prototype.parentBorder = 0;
HierarchicalLayout.prototype.intraCellSpacing = 30;
HierarchicalLayout.prototype.interRankCellSpacing = 100;
HierarchicalLayout.prototype.interHierarchySpacing = 60;
HierarchicalLayout.prototype.parallelEdgeSpacing = 10;
HierarchicalLayout.prototype.orientation = Constants.DIRECTION_NORTH;
HierarchicalLayout.prototype.fineTuning = !0;
HierarchicalLayout.prototype.tightenToSource = !0;
HierarchicalLayout.prototype.disableEdgeStyle = !0;
HierarchicalLayout.prototype.traverseAncestors = !0;
HierarchicalLayout.prototype.model = null;
HierarchicalLayout.prototype.edgesCache = null;
HierarchicalLayout.prototype.edgeSourceTermCache = null;
HierarchicalLayout.prototype.edgesTargetTermCache = null;
HierarchicalLayout.prototype.getModel = function() {
	return this.model
};
HierarchicalLayout.prototype.execute = function(a, b) {
	this.parent = a;
	var c = this.graph.model;
	this.edgesCache = new Dictionary;
	this.edgeSourceTermCache = new Dictionary;
	this.edgesTargetTermCache = new Dictionary;
	null != b && !(b instanceof Array) && (b = [b]);
	if (!(null == b && null == a)) {
		if (null != b && null != a) {
			for (var d = [], e = 0; e < b.length; e++) c.isAncestor(a, b[e]) && d.push(b[e]);
			this.roots = d
		} else this.roots = b;
		c.beginUpdate();
		try {
			this.run(a), this.resizeParent && !this.graph.isCellCollapsed(a) && this.graph.updateGroupBounds([a], this.parentBorder, this.moveParent)
		} finally {
			c.endUpdate()
		}
	}
};
HierarchicalLayout.prototype.findRoots = function(a, b) {
	var c = [];
	if (null != a && null != b) {
		var d = this.graph.model,
			e = null,
			f = -1E5,
			g;
		for (g in b) {
			var k = b[g];
			if (d.isVertex(k) && this.graph.isCellVisible(k)) {
				for (var l = this.getEdges(k), m = 0, n = 0, p = 0; p < l.length; p++) this.getVisibleTerminal(l[p], !0) == k ? m++ : n++;
				0 == n && 0 < m && c.push(k);
				l = m - n;
				l > f && (f = l, e = k)
			}
		}
		0 == c.length && null != e && c.push(e)
	}
	return c
};
HierarchicalLayout.prototype.getEdges = function(a) {
	var b = this.edgesCache.get(a);
	if (null != b) return b;
	for (var c = this.graph.model, b = [], d = this.graph.isCellCollapsed(a), e = c.getChildCount(a), f = 0; f < e; f++) {
		var g = c.getChildAt(a, f);
		if (this.isPort(g)) b = b.concat(c.getEdges(g, !0, !0));
		else if (d || !this.graph.isCellVisible(g)) b = b.concat(c.getEdges(g, !0, !0))
	}
	b = b.concat(c.getEdges(a, !0, !0));
	c = [];
	for (f = 0; f < b.length; f++) d = this.getVisibleTerminal(b[f], !0), e = this.getVisibleTerminal(b[f], !1), (d == e || d != e && (e == a && (null == this.parent || this.graph.isValidAncestor(d, this.parent, this.traverseAncestors)) || d == a && (null == this.parent || this.graph.isValidAncestor(e, this.parent, this.traverseAncestors)))) && c.push(b[f]);
	this.edgesCache.put(a, c);
	return c
};
HierarchicalLayout.prototype.getVisibleTerminal = function(a, b) {
	var c = this.edgesTargetTermCache;
	b && (c = this.edgeSourceTermCache);
	var d = c.get(a);
	if (null != d) return d;
	var d = this.graph.view.getState(a),
		e = null != d ? d.getVisibleTerminal(b) : this.graph.view.getVisibleTerminal(a, b);
	null == e && (e = null != d ? d.getVisibleTerminal(b) : this.graph.view.getVisibleTerminal(a, b));
	this.isPort(e) && (e = this.graph.model.getParent(e));
	c.put(a, e);
	return e
};
HierarchicalLayout.prototype.run = function(a) {
	var b = [],
		c = [];
	if (null == this.roots && null != a) {
		var d = {};
		this.filterDescendants(a, d);
		this.roots = [];
		var e = !0,
			f;
		for (f in d) if (null != d[f]) {
			e = !1;
			break
		}
		for (; !e;) {
			for (var g = this.findRoots(a, d), e = 0; e < g.length; e++) {
				var k = {};
				b.push(k);
				this.traverse(g[e], !0, null, c, k, b, d)
			}
			for (e = 0; e < g.length; e++) this.roots.push(g[e]);
			e = !0;
			for (f in d) if (null != d[f]) {
				e = !1;
				break
			}
		}
	} else for (e = 0; e < this.roots.length; e++) k = {}, b.push(k), this.traverse(this.roots[e], !0, null, c, k, b, null);
	for (e = c = 0; e < b.length; e++) {
		k = b[e];
		d = [];
		for (f in k) d.push(k[f]);
		this.model = new GraphHierarchyModel(this, d, this.roots, a, this.tightenToSource);
		this.cycleStage(a);
		this.layeringStage();
		this.crossingStage(a);
		c = this.placementStage(c, a)
	}
};
HierarchicalLayout.prototype.filterDescendants = function(a, b) {
	var c = this.graph.model;
	c.isVertex(a) && (a != this.parent && this.graph.isCellVisible(a)) && (b[ObjectIdentity.get(a)] = a);
	if (this.traverseAncestors || a == this.parent && this.graph.isCellVisible(a)) for (var d = c.getChildCount(a), e = 0; e < d; e++) {
		var f = c.getChildAt(a, e);
		this.isPort(f) || this.filterDescendants(f, b)
	}
};
HierarchicalLayout.prototype.isPort = function(a) {
	return a.geometry.relative ? !0 : !1
};
HierarchicalLayout.prototype.getEdgesBetween = function(a, b, c) {
	c = null != c ? c : !1;
	for (var d = this.getEdges(a), e = [], f = 0; f < d.length; f++) {
		var g = this.getVisibleTerminal(d[f], !0),
			k = this.getVisibleTerminal(d[f], !1);
		(g == a && k == b || !c && g == b && k == a) && e.push(d[f])
	}
	return e
};
HierarchicalLayout.prototype.traverse = function(a, b, c, d, e, f, g) {
	if (null != a && null != d) {
		var k = ObjectIdentity.get(a);
		if (null == d[k] && (null == g || null != g[k])) {
			null == e[k] && (e[k] = a);
			null == d[k] && (d[k] = a);
			null !== g && delete g[k];
			var l = this.getEdges(a),
				k = [];
			for (c = 0; c < l.length; c++) k[c] = this.getVisibleTerminal(l[c], !0) == a;
			for (c = 0; c < l.length; c++) if (!b || k[c]) {
				a = this.getVisibleTerminal(l[c], !k[c]);
				for (var m = 1, n = 0; n < l.length; n++) if (n != c) {
					var p = k[n];
					this.getVisibleTerminal(l[n], !p) == a && (p ? m++ : m--)
				}
				0 <= m && (e = this.traverse(a, b, l[c], d, e, f, g))
			}
		} else if (null == e[k]) for (c = 0; c < f.length; c++) if (b = f[c], null != b[k]) {
			for (l in b) e[l] = b[l];
			f.splice(c, 1);
			break
		}
	}
	return e
};
HierarchicalLayout.prototype.cycleStage = function(a) {
	(new MinimumCycleRemover(this)).execute(a)
};
HierarchicalLayout.prototype.layeringStage = function() {
	this.model.initialRank();
	this.model.fixRanks()
};
HierarchicalLayout.prototype.crossingStage = function(a) {
	(new MedianHybridCrossingReduction(this)).execute(a)
};
HierarchicalLayout.prototype.placementStage = function(a, b) {
	var c = new CoordinateAssignment(this, this.intraCellSpacing, this.interRankCellSpacing, this.orientation, a, this.parallelEdgeSpacing);
	c.fineTuning = this.fineTuning;
	c.execute(b);
	return c.limitX + this.interHierarchySpacing
};

function SwimlaneLayout(a, b, c) {
	GraphLayout.call(this, a);
	this.orientation = null != b ? b : Constants.DIRECTION_NORTH;
	this.deterministic = null != c ? c : !0
}
SwimlaneLayout.prototype = new GraphLayout;
SwimlaneLayout.prototype.constructor = SwimlaneLayout;
SwimlaneLayout.prototype.roots = null;
SwimlaneLayout.prototype.swimlanes = null;
SwimlaneLayout.prototype.dummyVertices = null;
SwimlaneLayout.prototype.dummyVertexWidth = 50;
SwimlaneLayout.prototype.resizeParent = !1;
SwimlaneLayout.prototype.moveParent = !1;
SwimlaneLayout.prototype.parentBorder = 30;
SwimlaneLayout.prototype.intraCellSpacing = 30;
SwimlaneLayout.prototype.interRankCellSpacing = 100;
SwimlaneLayout.prototype.interHierarchySpacing = 60;
SwimlaneLayout.prototype.parallelEdgeSpacing = 10;
SwimlaneLayout.prototype.orientation = Constants.DIRECTION_NORTH;
SwimlaneLayout.prototype.fineTuning = !0;
SwimlaneLayout.prototype.tightenToSource = !0;
SwimlaneLayout.prototype.disableEdgeStyle = !0;
SwimlaneLayout.prototype.traverseAncestors = !0;
SwimlaneLayout.prototype.model = null;
SwimlaneLayout.prototype.edgesCache = null;
SwimlaneLayout.prototype.getModel = function() {
	return this.model
};
SwimlaneLayout.prototype.execute = function(a, b) {
	this.parent = a;
	var c = this.graph.model;
	this.edgesCache = {};
	if (!(null == b || 1 > b.length)) {
		null == a && (a = c.getParent(b[0]));
		this.swimlanes = b;
		this.dummyVertices = [];
		for (var d = 0; d < b.length; d++) {
			var e = this.graph.getChildCells(b[d]);
			if (null == e || 0 == e.length) e = this.graph.insertVertex(b[d], null, null, 0, 0, this.dummyVertexWidth, 0), this.dummyVertices.push(e)
		}
		c.beginUpdate();
		try {
			this.run(a), this.resizeParent && !this.graph.isCellCollapsed(a) && this.updateGroupBounds(), this.graph.removeCells(this.dummyVertices)
		} finally {
			c.endUpdate()
		}
	}
};
SwimlaneLayout.prototype.updateGroupBounds = function() {
	var a = [],
		b = this.model,
		c;
	for (c in b.edgeMapper) for (var d = b.edgeMapper[c], e = 0; e < d.edges.length; e++) a.push(d.edges[e]);
	a = this.graph.getBoundingBoxFromGeometry(a, !0);
	b = [];
	for (e = 0; e < this.swimlanes.length; e++) {
		var f = this.swimlanes[e];
		c = this.graph.getCellGeometry(f);
		if (null != c) {
			var g = this.graph.getChildCells(f),
				d = this.graph.isSwimlane(f) ? this.graph.getStartSize(f) : new Rectangle,
				f = this.graph.getBoundingBoxFromGeometry(g);
			b[e] = f;
			d = f.y + c.y - d.height - this.parentBorder;
			c = f.y + c.y + f.height;
			null == a ? a = new Rectangle(0, d, 0, c - d) : (a.y = Math.min(a.y, d), c = Math.max(a.y + a.height, c), a.height = c - a.y)
		}
	}
	for (e = 0; e < this.swimlanes.length; e++) if (f = this.swimlanes[e], c = this.graph.getCellGeometry(f), null != c) {
		var g = this.graph.getChildCells(f),
			d = this.graph.isSwimlane(f) ? this.graph.getStartSize(f) : new Rectangle,
			k = c.clone(),
			l = 0 == e ? this.parentBorder : this.interRankCellSpacing / 2;
		k.x += b[e].x - d.width - l;
		k.y = k.y + a.y - c.y - this.parentBorder;
		k.width = b[e].width + d.width + this.interRankCellSpacing / 2 + l;
		k.height = a.height + d.height + 2 * this.parentBorder;
		this.graph.model.setGeometry(f, k);
		this.graph.moveCells(g, -b[e].x + d.width + l, c.y - a.y + this.parentBorder)
	}
};
SwimlaneLayout.prototype.findRoots = function(a, b) {
	var c = [];
	if (null != a && null != b) {
		var d = this.graph.model,
			e = null,
			f = -1E5,
			g;
		for (g in b) {
			var k = b[g];
			if (null != k && d.isVertex(k) && this.graph.isCellVisible(k) && d.isAncestor(a, k)) {
				for (var l = this.getEdges(k), m = 0, n = 0, p = 0; p < l.length; p++) {
					var q = this.getVisibleTerminal(l[p], !0);
					q == k ? (q = this.getVisibleTerminal(l[p], !1), d.isAncestor(a, q) && m++) : d.isAncestor(a, q) && n++
				}
				0 == n && 0 < m && c.push(k);
				l = m - n;
				l > f && (f = l, e = k)
			}
		}
		0 == c.length && null != e && c.push(e)
	}
	return c
};
SwimlaneLayout.prototype.getEdges = function(a) {
	var b = CellPath.create(a);
	if (null != this.edgesCache[b]) return this.edgesCache[b];
	for (var c = this.graph.model, d = [], e = this.graph.isCellCollapsed(a), f = c.getChildCount(a), g = 0; g < f; g++) {
		var k = c.getChildAt(a, g);
		if (this.isPort(k)) d = d.concat(c.getEdges(k, !0, !0));
		else if (e || !this.graph.isCellVisible(k)) d = d.concat(c.getEdges(k, !0, !0))
	}
	d = d.concat(c.getEdges(a, !0, !0));
	c = [];
	for (g = 0; g < d.length; g++) e = this.getVisibleTerminal(d[g], !0), f = this.getVisibleTerminal(d[g], !1), (e == f || e != f && (f == a && (null == this.parent || this.graph.isValidAncestor(e, this.parent, this.traverseAncestors)) || e == a && (null == this.parent || this.graph.isValidAncestor(f, this.parent, this.traverseAncestors)))) && c.push(d[g]);
	return this.edgesCache[b] = c
};
SwimlaneLayout.prototype.getVisibleTerminal = function(a, b) {
	var c = this.graph.view.getState(a),
		c = null != c ? c.getVisibleTerminal(b) : this.graph.view.getVisibleTerminal(a, b);
	this.isPort(c) && (c = this.graph.model.getParent(c));
	return c
};
SwimlaneLayout.prototype.run = function(a) {
	var b = [],
		c = [];
	if (null != this.swimlanes && 0 < this.swimlanes.length && null != a) {
		for (var d = {}, e = 0; e < this.swimlanes.length; e++) this.filterDescendants(this.swimlanes[e], d);
		this.roots = [];
		var e = !0,
			f;
		for (f in d) if (null != d[f]) {
			e = !1;
			break
		}
		for (var g = 0; !e && g < this.swimlanes.length;) {
			var k = this.findRoots(this.swimlanes[g], d);
			if (0 == k.length) g++;
			else {
				for (e = 0; e < k.length; e++) {
					var l = {};
					b.push(l);
					this.traverse(k[e], !0, null, c, l, b, d, g)
				}
				for (e = 0; e < k.length; e++) this.roots.push(k[e]);
				e = !0;
				for (f in d) if (null != d[f]) {
					e = !1;
					break
				}
			}
		}
	} else for (e = 0; e < this.roots.length; e++) l = {}, b.push(l), this.traverse(this.roots[e], !0, null, c, l, b, null);
	b = [];
	for (f in c) b.push(c[f]);
	this.model = new SwimlaneModel(this, b, this.roots, a, this.tightenToSource);
	this.cycleStage(a);
	this.layeringStage();
	this.crossingStage(a);
	initialX = this.placementStage(0, a)
};
SwimlaneLayout.prototype.filterDescendants = function(a, b) {
	var c = this.graph.model;
	c.isVertex(a) && (a != this.parent && c.getParent(a) != this.parent && this.graph.isCellVisible(a)) && (b[CellPath.create(a)] = a);
	if (this.traverseAncestors || a == this.parent && this.graph.isCellVisible(a)) for (var d = c.getChildCount(a), e = 0; e < d; e++) {
		var f = c.getChildAt(a, e);
		this.isPort(f) || this.filterDescendants(f, b)
	}
};
SwimlaneLayout.prototype.isPort = function(a) {
	return a.geometry.relative ? !0 : !1
};
SwimlaneLayout.prototype.getEdgesBetween = function(a, b, c) {
	c = null != c ? c : !1;
	for (var d = this.getEdges(a), e = [], f = 0; f < d.length; f++) {
		var g = this.getVisibleTerminal(d[f], !0),
			k = this.getVisibleTerminal(d[f], !1);
		(g == a && k == b || !c && g == b && k == a) && e.push(d[f])
	}
	return e
};
SwimlaneLayout.prototype.traverse = function(a, b, c, d, e, f, g, k) {
	if (null != a && null != d) {
		var l = CellPath.create(a);
		if (null == d[l] && (null == g || null != g[l])) {
			null == e[l] && (e[l] = a);
			null == d[l] && (d[l] = a);
			null !== g && delete g[l];
			var m = this.getEdges(a),
				l = this.graph.model;
			for (c = 0; c < m.length; c++) {
				var n = this.getVisibleTerminal(m[c], !0),
					p = n == a;
				p && (n = this.getVisibleTerminal(m[c], !1));
				for (var q = 0, q = 0; q < this.swimlanes.length && !l.isAncestor(this.swimlanes[q], n); q++);
				if (!(q >= this.swimlanes.length) && (q > k || (!b || p) && q == k)) e = this.traverse(n, b, m[c], d, e, f, g, q)
			}
		} else if (null == e[l]) for (c = 0; c < f.length; c++) if (a = f[c], null != a[l]) {
			for (m in a) e[m] = a[m];
			f.splice(c, 1);
			break
		}
	}
	return e
};
SwimlaneLayout.prototype.cycleStage = function(a) {
	(new SwimlaneOrdering(this)).execute(a)
};
SwimlaneLayout.prototype.layeringStage = function() {
	this.model.initialRank();
	this.model.fixRanks()
};
SwimlaneLayout.prototype.crossingStage = function(a) {
	(new MedianHybridCrossingReduction(this)).execute(a)
};
SwimlaneLayout.prototype.placementStage = function(a, b) {
	var c = new CoordinateAssignment(this, this.intraCellSpacing, this.interRankCellSpacing, this.orientation, a, this.parallelEdgeSpacing);
	c.fineTuning = this.fineTuning;
	c.execute(b);
	return c.limitX + this.interHierarchySpacing
};

function GraphModel(a) {
	this.currentEdit = this.createUndoableEdit();
	null != a ? this.setRoot(a) : this.clear()
}
GraphModel.prototype = new EventSource;
GraphModel.prototype.constructor = GraphModel;
GraphModel.prototype.root = null;
GraphModel.prototype.cells = null;
GraphModel.prototype.maintainEdgeParent = !0;
GraphModel.prototype.createIds = !0;
GraphModel.prototype.prefix = "";
GraphModel.prototype.postfix = "";
GraphModel.prototype.nextId = 0;
GraphModel.prototype.currentEdit = null;
GraphModel.prototype.updateLevel = 0;
GraphModel.prototype.endingUpdate = !1;
GraphModel.prototype.clear = function() {
	this.setRoot(this.createRoot())
};
GraphModel.prototype.isCreateIds = function() {
	return this.createIds
};
GraphModel.prototype.setCreateIds = function(a) {
	this.createIds = a
};
GraphModel.prototype.createRoot = function() {
	var a = new Cell;
	a.insert(new Cell);
	return a
};
GraphModel.prototype.getCell = function(a) {
	return null != this.cells ? this.cells[a] : null
};
GraphModel.prototype.filterCells = function(a, b) {
	var c = null;
	if (null != a) for (var c = [], d = 0; d < a.length; d++) b(a[d]) && c.push(a[d]);
	return c
};
GraphModel.prototype.getDescendants = function(a) {
	return this.filterDescendants(null, a)
};
GraphModel.prototype.filterDescendants = function(a, b) {
	var c = [];
	b = b || this.getRoot();
	(null == a || a(b)) && c.push(b);
	for (var d = this.getChildCount(b), e = 0; e < d; e++) var f = this.getChildAt(b, e),
		c = c.concat(this.filterDescendants(a, f));
	return c
};
GraphModel.prototype.getRoot = function(a) {
	var b = a || this.root;
	if (null != a) for (; null != a;) b = a, a = this.getParent(a);
	return b
};
GraphModel.prototype.setRoot = function(a) {
	this.execute(new RootChange(this, a));
	return a
};
GraphModel.prototype.rootChanged = function(a) {
	var b = this.root;
	this.root = a;
	this.nextId = 0;
	this.cells = null;
	this.cellAdded(a);
	return b
};
GraphModel.prototype.isRoot = function(a) {
	return null != a && this.root == a
};
GraphModel.prototype.isLayer = function(a) {
	return this.isRoot(this.getParent(a))
};
GraphModel.prototype.isAncestor = function(a, b) {
	for (; null != b && b != a;) b = this.getParent(b);
	return b == a
};
GraphModel.prototype.contains = function(a) {
	return this.isAncestor(this.root, a)
};
GraphModel.prototype.getParent = function(a) {
	return null != a ? a.getParent() : null
};
GraphModel.prototype.add = function(a, b, c) {
	if (b != a && null != a && null != b) {
		null == c && (c = this.getChildCount(a));
		var d = a != this.getParent(b);
		this.execute(new ChildChange(this, a, b, c));
		this.maintainEdgeParent && d && this.updateEdgeParents(b)
	}
	return b
};
GraphModel.prototype.cellAdded = function(a) {
	if (null != a) {
		null == a.getId() && this.createIds && a.setId(this.createId(a));
		if (null != a.getId()) {
			var b = this.getCell(a.getId());
			if (b != a) {
				for (; null != b;) a.setId(this.createId(a)), b = this.getCell(a.getId());
				null == this.cells && (this.cells = {});
				this.cells[a.getId()] = a
			}
		}
		Utils.isNumeric(a.getId()) && (this.nextId = Math.max(this.nextId, a.getId()));
		for (var b = this.getChildCount(a), c = 0; c < b; c++) this.cellAdded(this.getChildAt(a, c))
	}
};
GraphModel.prototype.createId = function(a) {
	a = this.nextId;
	this.nextId++;
	return this.prefix + a + this.postfix
};
GraphModel.prototype.updateEdgeParents = function(a, b) {
	b = b || this.getRoot(a);
	for (var c = this.getChildCount(a), d = 0; d < c; d++) {
		var e = this.getChildAt(a, d);
		this.updateEdgeParents(e, b)
	}
	e = this.getEdgeCount(a);
	c = [];
	for (d = 0; d < e; d++) c.push(this.getEdgeAt(a, d));
	for (d = 0; d < c.length; d++) e = c[d], this.isAncestor(b, e) && this.updateEdgeParent(e, b)
};
GraphModel.prototype.updateEdgeParent = function(a, b) {
	for (var c = this.getTerminal(a, !0), d = this.getTerminal(a, !1), e = null; null != c && !this.isEdge(c) && null != c.geometry && c.geometry.relative;) c = this.getParent(c);
	for (; null != d && !this.isEdge(d) && null != d.geometry && d.geometry.relative;) d = this.getParent(d);
	if (this.isAncestor(b, c) && this.isAncestor(b, d) && (e = c == d ? this.getParent(c) : this.getNearestCommonAncestor(c, d), null != e && (this.getParent(e) != this.root || this.isAncestor(e, a)) && this.getParent(a) != e)) {
		c = this.getGeometry(a);
		if (null != c) {
			var f = this.getOrigin(this.getParent(a)),
				g = this.getOrigin(e),
				d = g.x - f.x,
				f = g.y - f.y,
				c = c.clone();
			c.translate(-d, -f);
			this.setGeometry(a, c)
		}
		this.add(e, a, this.getChildCount(e))
	}
};
GraphModel.prototype.getOrigin = function(a) {
	var b = null;
	null != a ? (b = this.getOrigin(this.getParent(a)), this.isEdge(a) || (a = this.getGeometry(a), null != a && (b.x += a.x, b.y += a.y))) : b = new Point;
	return b
};
GraphModel.prototype.getNearestCommonAncestor = function(a, b) {
	if (null != a && null != b) {
		var c = CellPath.create(b);
		if (null != c && 0 < c.length) {
			var d = a,
				e = CellPath.create(d);
			if (c.length < e.length) var d = b,
				f = e,
				e = c,
				c = f;
			for (; null != d;) {
				f = this.getParent(d);
				if (0 == c.indexOf(e + CellPath.PATH_SEPARATOR) && null != f) return d;
				e = CellPath.getParentPath(e);
				d = f
			}
		}
	}
	return null
};
GraphModel.prototype.remove = function(a) {
	a == this.root ? this.setRoot(null) : null != this.getParent(a) && this.execute(new ChildChange(this, null, a));
	return a
};
GraphModel.prototype.cellRemoved = function(a) {
	if (null != a && null != this.cells) {
		for (var b = this.getChildCount(a) - 1; 0 <= b; b--) this.cellRemoved(this.getChildAt(a, b));
		null != this.cells && null != a.getId() && delete this.cells[a.getId()]
	}
};
GraphModel.prototype.parentForCellChanged = function(a, b, c) {
	var d = this.getParent(a);
	null != b ? (b != d || d.getIndex(a) != c) && b.insert(a, c) : null != d && (c = d.getIndex(a), d.remove(c));
	!this.contains(d) && null != b ? this.cellAdded(a) : null == b && this.cellRemoved(a);
	return d
};
GraphModel.prototype.getChildCount = function(a) {
	return null != a ? a.getChildCount() : 0
};
GraphModel.prototype.getChildAt = function(a, b) {
	return null != a ? a.getChildAt(b) : null
};
GraphModel.prototype.getChildren = function(a) {
	return null != a ? a.children : null
};
GraphModel.prototype.getChildVertices = function(a) {
	return this.getChildCells(a, !0, !1)
};
GraphModel.prototype.getChildEdges = function(a) {
	return this.getChildCells(a, !1, !0)
};
GraphModel.prototype.getChildCells = function(a, b, c) {
	b = null != b ? b : !1;
	c = null != c ? c : !1;
	for (var d = this.getChildCount(a), e = [], f = 0; f < d; f++) {
		var g = this.getChildAt(a, f);
		(!c && !b || c && this.isEdge(g) || b && this.isVertex(g)) && e.push(g)
	}
	return e
};
GraphModel.prototype.getTerminal = function(a, b) {
	return null != a ? a.getTerminal(b) : null
};
GraphModel.prototype.setTerminal = function(a, b, c) {
	var d = b != this.getTerminal(a, c);
	this.execute(new TerminalChange(this, a, b, c));
	this.maintainEdgeParent && d && this.updateEdgeParent(a, this.getRoot());
	return b
};
GraphModel.prototype.setTerminals = function(a, b, c) {
	this.beginUpdate();
	try {
		this.setTerminal(a, b, !0), this.setTerminal(a, c, !1)
	} finally {
		this.endUpdate()
	}
};
GraphModel.prototype.terminalForCellChanged = function(a, b, c) {
	var d = this.getTerminal(a, c);
	null != b ? b.insertEdge(a, c) : null != d && d.removeEdge(a, c);
	return d
};
GraphModel.prototype.getEdgeCount = function(a) {
	return null != a ? a.getEdgeCount() : 0
};
GraphModel.prototype.getEdgeAt = function(a, b) {
	return null != a ? a.getEdgeAt(b) : null
};
GraphModel.prototype.getDirectedEdgeCount = function(a, b, c) {
	for (var d = 0, e = this.getEdgeCount(a), f = 0; f < e; f++) {
		var g = this.getEdgeAt(a, f);
		g != c && this.getTerminal(g, b) == a && d++
	}
	return d
};
GraphModel.prototype.getConnections = function(a) {
	return this.getEdges(a, !0, !0, !1)
};
GraphModel.prototype.getIncomingEdges = function(a) {
	return this.getEdges(a, !0, !1, !1)
};
GraphModel.prototype.getOutgoingEdges = function(a) {
	return this.getEdges(a, !1, !0, !1)
};
GraphModel.prototype.getEdges = function(a, b, c, d) {
	b = null != b ? b : !0;
	c = null != c ? c : !0;
	d = null != d ? d : !0;
	for (var e = this.getEdgeCount(a), f = [], g = 0; g < e; g++) {
		var k = this.getEdgeAt(a, g),
			l = this.getTerminal(k, !0),
			m = this.getTerminal(k, !1);
		(d && l == m || l != m && (b && m == a || c && l == a)) && f.push(k)
	}
	return f
};
GraphModel.prototype.getEdgesBetween = function(a, b, c) {
	c = null != c ? c : !1;
	var d = this.getEdgeCount(a),
		e = this.getEdgeCount(b),
		f = a,
		g = d;
	e < d && (g = e, f = b);
	d = [];
	for (e = 0; e < g; e++) {
		var k = this.getEdgeAt(f, e),
			l = this.getTerminal(k, !0),
			m = this.getTerminal(k, !1),
			n = m == a && l == b;
		(l == a && m == b || !c && n) && d.push(k)
	}
	return d
};
GraphModel.prototype.getOpposites = function(a, b, c, d) {
	c = null != c ? c : !0;
	d = null != d ? d : !0;
	var e = [];
	if (null != a) for (var f = 0; f < a.length; f++) {
		var g = this.getTerminal(a[f], !0),
			k = this.getTerminal(a[f], !1);
		g == b && null != k && k != b && d ? e.push(k) : k == b && (null != g && g != b && c) && e.push(g)
	}
	return e
};
GraphModel.prototype.getTopmostCells = function(a) {
	for (var b = [], c = 0; c < a.length; c++) {
		for (var d = a[c], e = !0, f = this.getParent(d); null != f;) {
			if (0 <= Utils.indexOf(a, f)) {
				e = !1;
				break
			}
			f = this.getParent(f)
		}
		e && b.push(d)
	}
	return b
};
GraphModel.prototype.isVertex = function(a) {
	return null != a ? a.isVertex() : !1
};
GraphModel.prototype.isEdge = function(a) {
	return null != a ? a.isEdge() : !1
};
GraphModel.prototype.isConnectable = function(a) {
	return null != a ? a.isConnectable() : !1
};
GraphModel.prototype.getValue = function(a) {
	return null != a ? a.getValue() : null
};
GraphModel.prototype.setValue = function(a, b) {
	this.execute(new ValueChange(this, a, b));
	return b
};
GraphModel.prototype.valueForCellChanged = function(a, b) {
	return a.valueChanged(b)
};
GraphModel.prototype.getGeometry = function(a) {
	return null != a ? a.getGeometry() : null
};
GraphModel.prototype.setGeometry = function(a, b) {
	b != this.getGeometry(a) && this.execute(new GeometryChange(this, a, b));
	return b
};
GraphModel.prototype.geometryForCellChanged = function(a, b) {
	var c = this.getGeometry(a);
	a.setGeometry(b);
	return c
};
GraphModel.prototype.getStyle = function(a) {
	return null != a ? a.getStyle() : null
};
GraphModel.prototype.setStyle = function(a, b) {
	b != this.getStyle(a) && this.execute(new StyleChange(this, a, b));
	return b
};
GraphModel.prototype.styleForCellChanged = function(a, b) {
	var c = this.getStyle(a);
	a.setStyle(b);
	return c
};
GraphModel.prototype.isCollapsed = function(a) {
	return null != a ? a.isCollapsed() : !1
};
GraphModel.prototype.setCollapsed = function(a, b) {
	b != this.isCollapsed(a) && this.execute(new CollapseChange(this, a, b));
	return b
};
GraphModel.prototype.collapsedStateForCellChanged = function(a, b) {
	var c = this.isCollapsed(a);
	a.setCollapsed(b);
	return c
};
GraphModel.prototype.isVisible = function(a) {
	return null != a ? a.isVisible() : !1
};
GraphModel.prototype.setVisible = function(a, b) {
	b != this.isVisible(a) && this.execute(new VisibleChange(this, a, b));
	return b
};
GraphModel.prototype.visibleStateForCellChanged = function(a, b) {
	var c = this.isVisible(a);
	a.setVisible(b);
	return c
};
GraphModel.prototype.execute = function(a) {
	a.execute();
	this.beginUpdate();
	this.currentEdit.add(a);
	this.fireEvent(new EventObject(Event.EXECUTE, "change", a));
	this.fireEvent(new EventObject(Event.EXECUTED, "change", a));
	this.endUpdate()
};
GraphModel.prototype.beginUpdate = function() {
	this.updateLevel++;
	this.fireEvent(new EventObject(Event.BEGIN_UPDATE));
	1 == this.updateLevel && this.fireEvent(new EventObject(Event.START_EDIT))
};
GraphModel.prototype.endUpdate = function() {
	this.updateLevel--;
	0 == this.updateLevel && this.fireEvent(new EventObject(Event.END_EDIT));
	if (!this.endingUpdate) {
		this.endingUpdate = 0 == this.updateLevel;
		this.fireEvent(new EventObject(Event.END_UPDATE, "edit", this.currentEdit));
		try {
			if (this.endingUpdate && !this.currentEdit.isEmpty()) {
				this.fireEvent(new EventObject(Event.BEFORE_UNDO, "edit", this.currentEdit));
				var a = this.currentEdit;
				this.currentEdit = this.createUndoableEdit();
				a.notify();
				this.fireEvent(new EventObject(Event.UNDO, "edit", a))
			}
		} finally {
			this.endingUpdate = !1
		}
	}
};
GraphModel.prototype.createUndoableEdit = function() {
	var a = new UndoableEdit(this, !0);
	a.notify = function() {
		a.source.fireEvent(new EventObject(Event.CHANGE, "edit", a, "changes", a.changes));
		a.source.fireEvent(new EventObject(Event.NOTIFY, "edit", a, "changes", a.changes))
	};
	return a
};
GraphModel.prototype.mergeChildren = function(a, b, c) {
	c = null != c ? c : !0;
	this.beginUpdate();
	try {
		var d = {};
		this.mergeChildrenImpl(a, b, c, d);
		for (var e in d) {
			var f = d[e],
				g = this.getTerminal(f, !0);
			null != g && (g = d[CellPath.create(g)], this.setTerminal(f, g, !0));
			g = this.getTerminal(f, !1);
			null != g && (g = d[CellPath.create(g)], this.setTerminal(f, g, !1))
		}
	} finally {
		this.endUpdate()
	}
};
GraphModel.prototype.mergeChildrenImpl = function(a, b, c, d) {
	this.beginUpdate();
	try {
		for (var e = a.getChildCount(), f = 0; f < e; f++) {
			var g = a.getChildAt(f);
			if ("function" == typeof g.getId) {
				var k = g.getId(),
					l = null != k && (!this.isEdge(g) || !c) ? this.getCell(k) : null;
				if (null == l) {
					var m = g.clone();
					m.setId(k);
					m.setTerminal(g.getTerminal(!0), !0);
					m.setTerminal(g.getTerminal(!1), !1);
					l = b.insert(m);
					this.cellAdded(l)
				}
				d[CellPath.create(g)] = l;
				this.mergeChildrenImpl(g, l, c, d)
			}
		}
	} finally {
		this.endUpdate()
	}
};
GraphModel.prototype.getParents = function(a) {
	var b = [];
	if (null != a) for (var c = {}, d = 0; d < a.length; d++) {
		var e = this.getParent(a[d]);
		if (null != e) {
			var f = CellPath.create(e);
			null == c[f] && (c[f] = e, b.push(e))
		}
	}
	return b
};
GraphModel.prototype.cloneCell = function(a) {
	return null != a ? this.cloneCells([a], !0)[0] : null
};
GraphModel.prototype.cloneCells = function(a, b) {
	for (var c = {}, d = [], e = 0; e < a.length; e++) null != a[e] ? d.push(this.cloneCellImpl(a[e], c, b)) : d.push(null);
	for (e = 0; e < d.length; e++) null != d[e] && this.restoreClone(d[e], a[e], c);
	return d
};
GraphModel.prototype.cloneCellImpl = function(a, b, c) {
	var d = this.cellCloned(a);
	b[ObjectIdentity.get(a)] = d;
	if (c) {
		c = this.getChildCount(a);
		for (var e = 0; e < c; e++) {
			var f = this.cloneCellImpl(this.getChildAt(a, e), b, !0);
			d.insert(f)
		}
	}
	return d
};
GraphModel.prototype.cellCloned = function(a) {
	return a.clone()
};
GraphModel.prototype.restoreClone = function(a, b, c) {
	var d = this.getTerminal(b, !0);
	null != d && (d = c[ObjectIdentity.get(d)], null != d && d.insertEdge(a, !0));
	d = this.getTerminal(b, !1);
	null != d && (d = c[ObjectIdentity.get(d)], null != d && d.insertEdge(a, !1));
	for (var d = this.getChildCount(a), e = 0; e < d; e++) this.restoreClone(this.getChildAt(a, e), this.getChildAt(b, e), c)
};

function RootChange(a, b) {
	this.model = a;
	this.previous = this.root = b
}
RootChange.prototype.execute = function() {
	this.root = this.previous;
	this.previous = this.model.rootChanged(this.previous)
};

function ChildChange(a, b, c, d) {
	this.model = a;
	this.previous = this.parent = b;
	this.child = c;
	this.previousIndex = this.index = d
}
ChildChange.prototype.execute = function() {
	var a = this.model.getParent(this.child),
		b = null != a ? a.getIndex(this.child) : 0;
	null == this.previous && this.connect(this.child, !1);
	a = this.model.parentForCellChanged(this.child, this.previous, this.previousIndex);
	null != this.previous && this.connect(this.child, !0);
	this.parent = this.previous;
	this.previous = a;
	this.index = this.previousIndex;
	this.previousIndex = b
};
ChildChange.prototype.connect = function(a, b) {
	b = null != b ? b : !0;
	var c = a.getTerminal(!0),
		d = a.getTerminal(!1);
	null != c && (b ? this.model.terminalForCellChanged(a, c, !0) : this.model.terminalForCellChanged(a, null, !0));
	null != d && (b ? this.model.terminalForCellChanged(a, d, !1) : this.model.terminalForCellChanged(a, null, !1));
	a.setTerminal(c, !0);
	a.setTerminal(d, !1);
	c = this.model.getChildCount(a);
	for (d = 0; d < c; d++) this.connect(this.model.getChildAt(a, d), b)
};

function TerminalChange(a, b, c, d) {
	this.model = a;
	this.cell = b;
	this.previous = this.terminal = c;
	this.source = d
}
TerminalChange.prototype.execute = function() {
	this.terminal = this.previous;
	this.previous = this.model.terminalForCellChanged(this.cell, this.previous, this.source)
};

function ValueChange(a, b, c) {
	this.model = a;
	this.cell = b;
	this.previous = this.value = c
}
ValueChange.prototype.execute = function() {
	this.value = this.previous;
	this.previous = this.model.valueForCellChanged(this.cell, this.previous)
};

function StyleChange(a, b, c) {
	this.model = a;
	this.cell = b;
	this.previous = this.style = c
}
StyleChange.prototype.execute = function() {
	this.style = this.previous;
	this.previous = this.model.styleForCellChanged(this.cell, this.previous)
};

function GeometryChange(a, b, c) {
	this.model = a;
	this.cell = b;
	this.previous = this.geometry = c
}
GeometryChange.prototype.execute = function() {
	this.geometry = this.previous;
	this.previous = this.model.geometryForCellChanged(this.cell, this.previous)
};

function CollapseChange(a, b, c) {
	this.model = a;
	this.cell = b;
	this.previous = this.collapsed = c
}
CollapseChange.prototype.execute = function() {
	this.collapsed = this.previous;
	this.previous = this.model.collapsedStateForCellChanged(this.cell, this.previous)
};

function VisibleChange(a, b, c) {
	this.model = a;
	this.cell = b;
	this.previous = this.visible = c
}
VisibleChange.prototype.execute = function() {
	this.visible = this.previous;
	this.previous = this.model.visibleStateForCellChanged(this.cell, this.previous)
};

function CellAttributeChange(a, b, c) {
	this.cell = a;
	this.attribute = b;
	this.previous = this.value = c
}
CellAttributeChange.prototype.execute = function() {
	var a = this.cell.getAttribute(this.attribute);
	null == this.previous ? this.cell.value.removeAttribute(this.attribute) : this.cell.setAttribute(this.attribute, this.previous);
	this.previous = a
};

function Cell(a, b, c) {
	this.value = a;
	this.setGeometry(b);
	this.setStyle(c);
	if (null != this.onInit) this.onInit()
}
Cell.prototype.id = null;
Cell.prototype.value = null;
Cell.prototype.geometry = null;
Cell.prototype.style = null;
Cell.prototype.vertex = !1;
Cell.prototype.edge = !1;
Cell.prototype.connectable = !0;
Cell.prototype.visible = !0;
Cell.prototype.collapsed = !1;
Cell.prototype.parent = null;
Cell.prototype.source = null;
Cell.prototype.target = null;
Cell.prototype.children = null;
Cell.prototype.edges = null;
Cell.prototype.Transient = "id value parent source target children edges".split(" ");
Cell.prototype.getId = function() {
	return this.id
};
Cell.prototype.setId = function(a) {
	this.id = a
};
Cell.prototype.getValue = function() {
	return this.value
};
Cell.prototype.setValue = function(a) {
	this.value = a
};
Cell.prototype.valueChanged = function(a) {
	var b = this.getValue();
	this.setValue(a);
	return b
};
Cell.prototype.getGeometry = function() {
	return this.geometry
};
Cell.prototype.setGeometry = function(a) {
	this.geometry = a
};
Cell.prototype.getStyle = function() {
	return this.style
};
Cell.prototype.setStyle = function(a) {
	this.style = a
};
Cell.prototype.isVertex = function() {
	return this.vertex
};
Cell.prototype.setVertex = function(a) {
	this.vertex = a
};
Cell.prototype.isEdge = function() {
	return this.edge
};
Cell.prototype.setEdge = function(a) {
	this.edge = a
};
Cell.prototype.isConnectable = function() {
	return this.connectable
};
Cell.prototype.setConnectable = function(a) {
	this.connectable = a
};
Cell.prototype.isVisible = function() {
	return this.visible
};
Cell.prototype.setVisible = function(a) {
	this.visible = a
};
Cell.prototype.isCollapsed = function() {
	return this.collapsed
};
Cell.prototype.setCollapsed = function(a) {
	this.collapsed = a
};
Cell.prototype.getParent = function() {
	return this.parent
};
Cell.prototype.setParent = function(a) {
	this.parent = a
};
Cell.prototype.getTerminal = function(a) {
	return a ? this.source : this.target
};
Cell.prototype.setTerminal = function(a, b) {
	b ? this.source = a : this.target = a;
	return a
};
Cell.prototype.getChildCount = function() {
	return null == this.children ? 0 : this.children.length
};
Cell.prototype.getIndex = function(a) {
	return Utils.indexOf(this.children, a)
};
Cell.prototype.getChildAt = function(a) {
	return null == this.children ? null : this.children[a]
};
Cell.prototype.insert = function(a, b) {
	null != a && (null == b && (b = this.getChildCount(), a.getParent() == this && b--), a.removeFromParent(), a.setParent(this), null == this.children ? (this.children = [], this.children.push(a)) : this.children.splice(b, 0, a));
	return a
};
Cell.prototype.remove = function(a) {
	var b = null;
	null != this.children && 0 <= a && (b = this.getChildAt(a), null != b && (this.children.splice(a, 1), b.setParent(null)));
	return b
};
Cell.prototype.removeFromParent = function() {
	if (null != this.parent) {
		var a = this.parent.getIndex(this);
		this.parent.remove(a)
	}
};
Cell.prototype.getEdgeCount = function() {
	return null == this.edges ? 0 : this.edges.length
};
Cell.prototype.getEdgeIndex = function(a) {
	return Utils.indexOf(this.edges, a)
};
Cell.prototype.getEdgeAt = function(a) {
	return null == this.edges ? null : this.edges[a]
};
Cell.prototype.insertEdge = function(a, b) {
	if (null != a && (a.removeFromTerminal(b), a.setTerminal(this, b), null == this.edges || a.getTerminal(!b) != this || 0 > Utils.indexOf(this.edges, a))) null == this.edges && (this.edges = []), this.edges.push(a);
	return a
};
Cell.prototype.removeEdge = function(a, b) {
	if (null != a) {
		if (a.getTerminal(!b) != this && null != this.edges) {
			var c = this.getEdgeIndex(a);
			0 <= c && this.edges.splice(c, 1)
		}
		a.setTerminal(null, b)
	}
	return a
};
Cell.prototype.removeFromTerminal = function(a) {
	var b = this.getTerminal(a);
	null != b && b.removeEdge(this, a)
};
Cell.prototype.getAttribute = function(a, b) {
	var c = this.getValue();
	return (null != c && c.nodeType == Constants.NODETYPE_ELEMENT ? c.getAttribute(a) : null) || b
};
Cell.prototype.setAttribute = function(a, b) {
	var c = this.getValue();
	null != c && c.nodeType == Constants.NODETYPE_ELEMENT && c.setAttribute(a, b)
};
Cell.prototype.clone = function() {
	var a = Utils.clone(this, this.Transient);
	a.setValue(this.cloneValue());
	return a
};
Cell.prototype.cloneValue = function() {
	var a = this.getValue();
	null != a && ("function" == typeof a.clone ? a = a.clone() : isNaN(a.nodeType) || (a = a.cloneNode(!0)));
	return a
};

function Geometry(a, b, c, d) {
	Rectangle.call(this, a, b, c, d)
}
Geometry.prototype = new Rectangle;
Geometry.prototype.constructor = Geometry;
Geometry.prototype.TRANSLATE_CONTROL_POINTS = !0;
Geometry.prototype.alternateBounds = null;
Geometry.prototype.sourcePoint = null;
Geometry.prototype.targetPoint = null;
Geometry.prototype.points = null;
Geometry.prototype.offset = null;
Geometry.prototype.relative = !1;
Geometry.prototype.swap = function() {
	if (null != this.alternateBounds) {
		var a = new Rectangle(this.x, this.y, this.width, this.height);
		this.x = this.alternateBounds.x;
		this.y = this.alternateBounds.y;
		this.width = this.alternateBounds.width;
		this.height = this.alternateBounds.height;
		this.alternateBounds = a
	}
};
Geometry.prototype.getTerminalPoint = function(a) {
	return a ? this.sourcePoint : this.targetPoint
};
Geometry.prototype.setTerminalPoint = function(a, b) {
	b ? this.sourcePoint = a : this.targetPoint = a;
	return a
};
Geometry.prototype.translate = function(a, b) {
	this.clone();
	this.relative || (this.x += a, this.y += b);
	null != this.sourcePoint && (this.sourcePoint.x += a, this.sourcePoint.y += b);
	null != this.targetPoint && (this.targetPoint.x += a, this.targetPoint.y += b);
	if (this.TRANSLATE_CONTROL_POINTS && null != this.points) for (var c = this.points.length, d = 0; d < c; d++) {
		var e = this.points[d];
		null != e && (e.x += a, e.y += b)
	}
};
Geometry.prototype.equals = function(a) {
	return Rectangle.prototype.equals.apply(this, arguments) && this.relative == a.relative && (null == this.sourcePoint && null == a.sourcePoint || null != this.sourcePoint && this.sourcePoint.equals(a.sourcePoint)) && (null == this.targetPoint && null == a.targetPoint || null != this.targetPoint && this.targetPoint.equals(a.targetPoint)) && (null == this.points && null == a.points || null != this.points && Utils.equalPoints(this.points, a.points)) && (null == this.alternateBounds && null == a.alternateBounds || null != this.alternateBounds && this.alternateBounds.equals(a.alternateBounds)) && (null == this.offset && null == a.offset || null != this.offset && this.offset.equals(a.offset))
};
var CellPath = {
	PATH_SEPARATOR: ".",
	create: function(a) {
		var b = "";
		if (null != a) for (var c = a.getParent(); null != c;) b = c.getIndex(a) + CellPath.PATH_SEPARATOR + b, a = c, c = a.getParent();
		a = b.length;
		1 < a && (b = b.substring(0, a - 1));
		return b
	},
	getParentPath: function(a) {
		if (null != a) {
			var b = a.lastIndexOf(CellPath.PATH_SEPARATOR);
			if (0 <= b) return a.substring(0, b);
			if (0 < a.length) return ""
		}
		return null
	},
	resolve: function(a, b) {
		var c = a;
		if (null != b) for (var d = b.split(CellPath.PATH_SEPARATOR), e = 0; e < d.length; e++) c = c.getChildAt(parseInt(d[e]));
		return c
	},
	compare: function(a, b) {
		for (var c = Math.min(a.length, b.length), d = 0, e = 0; e < c; e++) if (a[e] != b[e]) {
			0 == a[e].length || 0 == b[e].length ? d = a[e] == b[e] ? 0 : a[e] > b[e] ? 1 : -1 : (c = parseInt(a[e]), e = parseInt(b[e]), d = c == e ? 0 : c > e ? 1 : -1);
			break
		}
		0 == d && (c = a.length, e = b.length, c != e && (d = c > e ? 1 : -1));
		return d
	}
},
	Perimeter = {
		RectanglePerimeter: function(a, b, c, d) {
			b = a.getCenterX();
			var e = a.getCenterY(),
				f = Math.atan2(c.y - e, c.x - b),
				g = new Point(0, 0),
				k = Math.PI,
				l = Math.PI / 2 - f,
				m = Math.atan2(a.height, a.width);
			f < -k + m || f > k - m ? (g.x = a.x, g.y = e - a.width * Math.tan(f) / 2) : f < -m ? (g.y = a.y, g.x = b - a.height * Math.tan(l) / 2) : f < m ? (g.x = a.x + a.width, g.y = e + a.width * Math.tan(f) / 2) : (g.y = a.y + a.height, g.x = b + a.height * Math.tan(l) / 2);
			d && (c.x >= a.x && c.x <= a.x + a.width ? g.x = c.x : c.y >= a.y && c.y <= a.y + a.height && (g.y = c.y), c.x < a.x ? g.x = a.x : c.x > a.x + a.width && (g.x = a.x + a.width), c.y < a.y ? g.y = a.y : c.y > a.y + a.height && (g.y = a.y + a.height));
			return g
		},
		EllipsePerimeter: function(a, b, c, d) {
			var e = a.x,
				f = a.y,
				g = a.width / 2,
				k = a.height / 2,
				l = e + g,
				m = f + k;
			b = c.x;
			c = c.y;
			var n = parseInt(b - l),
				p = parseInt(c - m);
			if (0 == n && 0 != p) return new Point(l, m + k * p / Math.abs(p));
			if (0 == n && 0 == p) return new Point(b, c);
			if (d) {
				if (c >= f && c <= f + a.height) return a = c - m, a = Math.sqrt(g * g * (1 - a * a / (k * k))) || 0, b <= e && (a = -a), new Point(l + a, c);
				if (b >= e && b <= e + a.width) return a = b - l, a = Math.sqrt(k * k * (1 - a * a / (g * g))) || 0, c <= f && (a = -a), new Point(b, m + a)
			}
			e = p / n;
			m -= e * l;
			f = g * g * e * e + k * k;
			a = -2 * l * f;
			k = Math.sqrt(a * a - 4 * f * (g * g * e * e * l * l + k * k * l * l - g * g * k * k));
			g = (-a + k) / (2 * f);
			k = (-a - k) / (2 * f);
			l = e * g + m;
			m = e * k + m;
			e = Math.sqrt(Math.pow(g - b, 2) + Math.pow(l - c, 2));
			b = Math.sqrt(Math.pow(k - b, 2) + Math.pow(m - c, 2));
			f = c = 0;
			e < b ? (c = g, f = l) : (c = k, f = m);
			return new Point(c, f)
		},
		RhombusPerimeter: function(a, b, c, d) {
			b = a.x;
			var e = a.y,
				f = a.width;
			a = a.height;
			var g = b + f / 2,
				k = e + a / 2,
				l = c.x;
			c = c.y;
			if (g == l) return k > c ? new Point(g, e) : new Point(g, e + a);
			if (k == c) return g > l ? new Point(b, k) : new Point(b + f, k);
			var m = g,
				n = k;
			d && (l >= b && l <= b + f ? m = l : c >= e && c <= e + a && (n = c));
			return l < g ? c < k ? Utils.intersection(l, c, m, n, g, e, b, k) : Utils.intersection(l, c, m, n, g, e + a, b, k) : c < k ? Utils.intersection(l, c, m, n, g, e, b + f, k) : Utils.intersection(l, c, m, n, g, e + a, b + f, k)
		},
		TrianglePerimeter: function(a, b, c, d) {
			b = null != b ? b.style[Constants.STYLE_DIRECTION] : null;
			var e = b == Constants.DIRECTION_NORTH || b == Constants.DIRECTION_SOUTH,
				f = a.x,
				g = a.y,
				k = a.width;
			a = a.height;
			var l = f + k / 2,
				m = g + a / 2,
				n = new Point(f, g),
				p = new Point(f + k, m),
				q = new Point(f, g + a);
			b == Constants.DIRECTION_NORTH ? (n = q, p = new Point(l, g), q = new Point(f + k, g + a)) : b == Constants.DIRECTION_SOUTH ? (p = new Point(l, g + a), q = new Point(f + k, g)) : b == Constants.DIRECTION_WEST && (n = new Point(f + k, g), p = new Point(f, m), q = new Point(f + k, g + a));
			var r = c.x - l,
				s = c.y - m,
				r = e ? Math.atan2(r, s) : Math.atan2(s, r),
				t = e ? Math.atan2(k, a) : Math.atan2(a, k),
				s = !1,
				s = b == Constants.DIRECTION_NORTH || b == Constants.DIRECTION_WEST ? r > -t && r < t : r < -Math.PI + t || r > Math.PI - t,
				t = null;
			s ? t = d && (e && c.x >= n.x && c.x <= q.x || !e && c.y >= n.y && c.y <= q.y) ? e ? new Point(c.x, n.y) : new Point(n.x, c.y) : b == Constants.DIRECTION_NORTH ? new Point(f + k / 2 + a * Math.tan(r) / 2, g + a) : b == Constants.DIRECTION_SOUTH ? new Point(f + k / 2 - a * Math.tan(r) / 2, g) : b == Constants.DIRECTION_WEST ? new Point(f + k, g + a / 2 + k * Math.tan(r) / 2) : new Point(f, g + a / 2 - k * Math.tan(r) / 2) : (d && (d = new Point(l, m), c.y >= g && c.y <= g + a ? (d.x = e ? l : b == Constants.DIRECTION_WEST ? f + k : f, d.y = c.y) : c.x >= f && c.x <= f + k && (d.x = c.x, d.y = !e ? m : b == Constants.DIRECTION_NORTH ? g + a : g), l = d.x, m = d.y), t = e && c.x <= f + k / 2 || !e && c.y <= g + a / 2 ? Utils.intersection(c.x, c.y, l, m, n.x, n.y, p.x, p.y) : Utils.intersection(c.x, c.y, l, m, p.x, p.y, q.x, q.y));
			null == t && (t = new Point(l, m));
			return t
		},
		HexagonPerimeter: function(a, b, c, d) {
			var e = a.x,
				f = a.y,
				g = a.width,
				k = a.height,
				l = a.getCenterX();
			a = a.getCenterY();
			var m = c.x,
				n = c.y,
				p = -Math.atan2(n - a, m - l),
				q = Math.PI,
				r = Math.PI / 2,
				s = new Point(l, a);
			b = null != b ? Utils.getValue(b.style, Constants.STYLE_DIRECTION, Constants.DIRECTION_EAST) : Constants.DIRECTION_EAST;
			var t = b == Constants.DIRECTION_NORTH || b == Constants.DIRECTION_SOUTH;
			b = new Point;
			s = new Point;
			if (m < e && n < f || m < e && n > f + k || m > e + g && n < f || m > e + g && n > f + k) d = !1;
			if (d) {
				if (t) {
					if (m == l) {
						if (n <= f) return new Point(l, f);
						if (n >= f + k) return new Point(l, f + k)
					} else if (m < e) {
						if (n == f + k / 4) return new Point(e, f + k / 4);
						if (n == f + 3 * k / 4) return new Point(e, f + 3 * k / 4)
					} else if (m > e + g) {
						if (n == f + k / 4) return new Point(e + g, f + k / 4);
						if (n == f + 3 * k / 4) return new Point(e + g, f + 3 * k / 4)
					} else if (m == e) {
						if (n < a) return new Point(e, f + k / 4);
						if (n > a) return new Point(e, f + 3 * k / 4)
					} else if (m == e + g) {
						if (n < a) return new Point(e + g, f + k / 4);
						if (n > a) return new Point(e + g, f + 3 * k / 4)
					}
					if (n == f) return new Point(l, f);
					if (n == f + k) return new Point(l, f + k);
					m < l ? n > f + k / 4 && n < f + 3 * k / 4 ? (b = new Point(e, f), s = new Point(e, f + k)) : n < f + k / 4 ? (b = new Point(e - Math.floor(0.5 * g), f + Math.floor(0.5 * k)), s = new Point(e + g, f - Math.floor(0.25 * k))) : n > f + 3 * k / 4 && (b = new Point(e - Math.floor(0.5 * g), f + Math.floor(0.5 * k)), s = new Point(e + g, f + Math.floor(1.25 * k))) : m > l && (n > f + k / 4 && n < f + 3 * k / 4 ? (b = new Point(e + g, f), s = new Point(e + g, f + k)) : n < f + k / 4 ? (b = new Point(e, f - Math.floor(0.25 * k)), s = new Point(e + Math.floor(1.5 * g), f + Math.floor(0.5 * k))) : n > f + 3 * k / 4 && (b = new Point(e + Math.floor(1.5 * g), f + Math.floor(0.5 * k)), s = new Point(e, f + Math.floor(1.25 * k))))
				} else {
					if (n == a) {
						if (m <= e) return new Point(e, f + k / 2);
						if (m >= e + g) return new Point(e + g, f + k / 2)
					} else if (n < f) {
						if (m == e + g / 4) return new Point(e + g / 4, f);
						if (m == e + 3 * g / 4) return new Point(e + 3 * g / 4, f)
					} else if (n > f + k) {
						if (m == e + g / 4) return new Point(e + g / 4, f + k);
						if (m == e + 3 * g / 4) return new Point(e + 3 * g / 4, f + k)
					} else if (n == f) {
						if (m < l) return new Point(e + g / 4, f);
						if (m > l) return new Point(e + 3 * g / 4, f)
					} else if (n == f + k) {
						if (m < l) return new Point(e + g / 4, f + k);
						if (n > a) return new Point(e + 3 * g / 4, f + k)
					}
					if (m == e) return new Point(e, a);
					if (m == e + g) return new Point(e + g, a);
					n < a ? m > e + g / 4 && m < e + 3 * g / 4 ? (b = new Point(e, f), s = new Point(e + g, f)) : m < e + g / 4 ? (b = new Point(e - Math.floor(0.25 * g), f + k), s = new Point(e + Math.floor(0.5 * g), f - Math.floor(0.5 * k))) : m > e + 3 * g / 4 && (b = new Point(e + Math.floor(0.5 * g), f - Math.floor(0.5 * k)), s = new Point(e + Math.floor(1.25 * g), f + k)) : n > a && (m > e + g / 4 && m < e + 3 * g / 4 ? (b = new Point(e, f + k), s = new Point(e + g, f + k)) : m < e + g / 4 ? (b = new Point(e - Math.floor(0.25 * g), f), s = new Point(e + Math.floor(0.5 * g), f + Math.floor(1.5 * k))) : m > e + 3 * g / 4 && (b = new Point(e + Math.floor(0.5 * g), f + Math.floor(1.5 * k)), s = new Point(e + Math.floor(1.25 * g), f)))
				}
				d = l;
				p = a;
				m >= e && m <= e + g ? (d = m, p = n < a ? f + k : f) : n >= f && n <= f + k && (p = n, d = m < l ? e + g : e);
				s = Utils.intersection(d, p, c.x, c.y, b.x, b.y, s.x, s.y)
			} else {
				if (t) {
					m = Math.atan2(k / 4, g / 2);
					if (p == m) return new Point(e + g, f + Math.floor(0.25 * k));
					if (p == r) return new Point(e + Math.floor(0.5 * g), f);
					if (p == q - m) return new Point(e, f + Math.floor(0.25 * k));
					if (p == -m) return new Point(e + g, f + Math.floor(0.75 * k));
					if (p == -r) return new Point(e + Math.floor(0.5 * g), f + k);
					if (p == -q + m) return new Point(e, f + Math.floor(0.75 * k));
					p < m && p > -m ? (b = new Point(e + g, f), s = new Point(e + g, f + k)) : p > m && p < r ? (b = new Point(e, f - Math.floor(0.25 * k)), s = new Point(e + Math.floor(1.5 * g), f + Math.floor(0.5 * k))) : p > r && p < q - m ? (b = new Point(e - Math.floor(0.5 * g), f + Math.floor(0.5 * k)), s = new Point(e + g, f - Math.floor(0.25 * k))) : p > q - m && p <= q || p < -q + m && p >= -q ? (b = new Point(e, f), s = new Point(e, f + k)) : p < -m && p > -r ? (b = new Point(e + Math.floor(1.5 * g), f + Math.floor(0.5 * k)), s = new Point(e, f + Math.floor(1.25 * k))) : p < -r && p > -q + m && (b = new Point(e - Math.floor(0.5 * g), f + Math.floor(0.5 * k)), s = new Point(e + g, f + Math.floor(1.25 * k)))
				} else {
					m = Math.atan2(k / 2, g / 4);
					if (p == m) return new Point(e + Math.floor(0.75 * g), f);
					if (p == q - m) return new Point(e + Math.floor(0.25 * g), f);
					if (p == q || p == -q) return new Point(e, f + Math.floor(0.5 * k));
					if (0 == p) return new Point(e + g, f + Math.floor(0.5 * k));
					if (p == -m) return new Point(e + Math.floor(0.75 * g), f + k);
					if (p == -q + m) return new Point(e + Math.floor(0.25 * g), f + k);
					0 < p && p < m ? (b = new Point(e + Math.floor(0.5 * g), f - Math.floor(0.5 * k)), s = new Point(e + Math.floor(1.25 * g), f + k)) : p > m && p < q - m ? (b = new Point(e, f), s = new Point(e + g, f)) : p > q - m && p < q ? (b = new Point(e - Math.floor(0.25 * g), f + k), s = new Point(e + Math.floor(0.5 * g), f - Math.floor(0.5 * k))) : 0 > p && p > -m ? (b = new Point(e + Math.floor(0.5 * g), f + Math.floor(1.5 * k)), s = new Point(e + Math.floor(1.25 * g), f)) : p < -m && p > -q + m ? (b = new Point(e, f + k), s = new Point(e + g, f + k)) : p < -q + m && p > -q && (b = new Point(e - Math.floor(0.25 * g), f), s = new Point(e + Math.floor(0.5 * g), f + Math.floor(1.5 * k)))
				}
				s = Utils.intersection(l, a, c.x, c.y, b.x, b.y, s.x, s.y)
			}
			return null == s ? new Point(l, a) : s
		}
	};

function Stylesheet() {
	this.styles = {};
	this.putDefaultVertexStyle(this.createDefaultVertexStyle());
	this.putDefaultEdgeStyle(this.createDefaultEdgeStyle())
}
Stylesheet.prototype.createDefaultVertexStyle = function() {
	var a = {};
	a[Constants.STYLE_SHAPE] = Constants.SHAPE_RECTANGLE;
	a[Constants.STYLE_PERIMETER] = Perimeter.RectanglePerimeter;
	a[Constants.STYLE_VERTICAL_ALIGN] = Constants.ALIGN_MIDDLE;
	a[Constants.STYLE_ALIGN] = Constants.ALIGN_CENTER;
	a[Constants.STYLE_FILLCOLOR] = "#C3D9FF";
	a[Constants.STYLE_STROKECOLOR] = "#6482B9";
	a[Constants.STYLE_FONTCOLOR] = "#774400";
	return a
};
Stylesheet.prototype.createDefaultEdgeStyle = function() {
	var a = {};
	a[Constants.STYLE_SHAPE] = Constants.SHAPE_CONNECTOR;
	a[Constants.STYLE_ENDARROW] = Constants.ARROW_CLASSIC;
	a[Constants.STYLE_VERTICAL_ALIGN] = Constants.ALIGN_MIDDLE;
	a[Constants.STYLE_ALIGN] = Constants.ALIGN_CENTER;
	a[Constants.STYLE_STROKECOLOR] = "#6482B9";
	a[Constants.STYLE_FONTCOLOR] = "#446299";
	return a
};
Stylesheet.prototype.putDefaultVertexStyle = function(a) {
	this.putCellStyle("defaultVertex", a)
};
Stylesheet.prototype.putDefaultEdgeStyle = function(a) {
	this.putCellStyle("defaultEdge", a)
};
Stylesheet.prototype.getDefaultVertexStyle = function() {
	return this.styles.defaultVertex
};
Stylesheet.prototype.getDefaultEdgeStyle = function() {
	return this.styles.defaultEdge
};
Stylesheet.prototype.putCellStyle = function(a, b) {
	this.styles[a] = b
};
Stylesheet.prototype.getCellStyle = function(a, b) {
	var c = b;
	if (null != a && 0 < a.length) for (var d = a.split(";"), c = null != c && ";" != a.charAt(0) ? Utils.clone(c) : {}, e = 0; e < d.length; e++) {
		var f = d[e],
			g = f.indexOf("=");
		if (0 <= g) {
			var k = f.substring(0, g),
				f = f.substring(g + 1);
			f == Constants.NONE ? delete c[k] : Utils.isNumeric(f) ? c[k] = parseFloat(f) : c[k] = f
		} else if (f = this.styles[f], null != f) for (k in f) c[k] = f[k]
	}
	return c
};

function CellState(a, b, c) {
	this.view = a;
	this.cell = b;
	this.style = c;
	this.origin = new Point;
	this.absoluteOffset = new Point
}
CellState.prototype = new Rectangle;
CellState.prototype.constructor = CellState;
CellState.prototype.view = null;
CellState.prototype.cell = null;
CellState.prototype.style = null;
CellState.prototype.invalid = !0;
CellState.prototype.origin = null;
CellState.prototype.absolutePoints = null;
CellState.prototype.absoluteOffset = null;
CellState.prototype.visibleSourceState = null;
CellState.prototype.visibleTargetState = null;
CellState.prototype.terminalDistance = 0;
CellState.prototype.length = 0;
CellState.prototype.segments = null;
CellState.prototype.shape = null;
CellState.prototype.text = null;
CellState.prototype.getPerimeterBounds = function(a, b) {
	a = a || 0;
	b = null != b ? b : new Rectangle(this.x, this.y, this.width, this.height);
	if (null != this.shape && null != this.shape.stencil) {
		var c = this.shape.stencil.computeAspect(this.style, b.x, b.y, b.width, b.height);
		b.x = c.x;
		b.y = c.y;
		b.width = this.shape.stencil.w0 * c.width;
		b.height = this.shape.stencil.h0 * c.height
	}
	0 != a && b.grow(a);
	return b
};
CellState.prototype.setAbsoluteTerminalPoint = function(a, b) {
	b ? (null == this.absolutePoints && (this.absolutePoints = []), 0 == this.absolutePoints.length ? this.absolutePoints.push(a) : this.absolutePoints[0] = a) : null == this.absolutePoints ? (this.absolutePoints = [], this.absolutePoints.push(null), this.absolutePoints.push(a)) : 1 == this.absolutePoints.length ? this.absolutePoints.push(a) : this.absolutePoints[this.absolutePoints.length - 1] = a
};
CellState.prototype.setCursor = function(a) {
	null != this.shape && this.shape.setCursor(a);
	null != this.text && this.text.setCursor(a)
};
CellState.prototype.getVisibleTerminal = function(a) {
	a = this.getVisibleTerminalState(a);
	return null != a ? a.cell : null
};
CellState.prototype.getVisibleTerminalState = function(a) {
	return a ? this.visibleSourceState : this.visibleTargetState
};
CellState.prototype.setVisibleTerminalState = function(a, b) {
	b ? this.visibleSourceState = a : this.visibleTargetState = a
};
CellState.prototype.destroy = function() {
	this.view.graph.cellRenderer.destroy(this)
};
CellState.prototype.clone = function() {
	var a = new CellState(this.view, this.cell, this.style);
	if (null != this.absolutePoints) {
		a.absolutePoints = [];
		for (var b = 0; b < this.absolutePoints.length; b++) a.absolutePoints[b] = this.absolutePoints[b].clone()
	}
	null != this.origin && (a.origin = this.origin.clone());
	null != this.absoluteOffset && (a.absoluteOffset = this.absoluteOffset.clone());
	null != this.boundingBox && (a.boundingBox = this.boundingBox.clone());
	a.terminalDistance = this.terminalDistance;
	a.segments = this.segments;
	a.length = this.length;
	a.x = this.x;
	a.y = this.y;
	a.width = this.width;
	a.height = this.height;
	return a
};

function GraphSelectionModel(a) {
	this.graph = a;
	this.cells = []
}
GraphSelectionModel.prototype = new EventSource;
GraphSelectionModel.prototype.constructor = GraphSelectionModel;
GraphSelectionModel.prototype.doneResource = "none" != Client.language ? "done" : "";
GraphSelectionModel.prototype.updatingSelectionResource = "none" != Client.language ? "updatingSelection" : "";
GraphSelectionModel.prototype.graph = null;
GraphSelectionModel.prototype.singleSelection = !1;
GraphSelectionModel.prototype.isSingleSelection = function() {
	return this.singleSelection
};
GraphSelectionModel.prototype.setSingleSelection = function(a) {
	this.singleSelection = a
};
GraphSelectionModel.prototype.isSelected = function(a) {
	return null != a ? 0 <= Utils.indexOf(this.cells, a) : !1
};
GraphSelectionModel.prototype.isEmpty = function() {
	return 0 == this.cells.length
};
GraphSelectionModel.prototype.clear = function() {
	this.changeSelection(null, this.cells)
};
GraphSelectionModel.prototype.setCell = function(a) {
	null != a && this.setCells([a])
};
GraphSelectionModel.prototype.setCells = function(a) {
	if (null != a) {
		this.singleSelection && (a = [this.getFirstSelectableCell(a)]);
		for (var b = [], c = 0; c < a.length; c++) this.graph.isCellSelectable(a[c]) && b.push(a[c]);
		this.changeSelection(b, this.cells)
	}
};
GraphSelectionModel.prototype.getFirstSelectableCell = function(a) {
	if (null != a) for (var b = 0; b < a.length; b++) if (this.graph.isCellSelectable(a[b])) return a[b];
	return null
};
GraphSelectionModel.prototype.addCell = function(a) {
	null != a && this.addCells([a])
};
GraphSelectionModel.prototype.addCells = function(a) {
	if (null != a) {
		var b = null;
		this.singleSelection && (b = this.cells, a = [this.getFirstSelectableCell(a)]);
		for (var c = [], d = 0; d < a.length; d++)!this.isSelected(a[d]) && this.graph.isCellSelectable(a[d]) && c.push(a[d]);
		this.changeSelection(c, b)
	}
};
GraphSelectionModel.prototype.removeCell = function(a) {
	null != a && this.removeCells([a])
};
GraphSelectionModel.prototype.removeCells = function(a) {
	if (null != a) {
		for (var b = [], c = 0; c < a.length; c++) this.isSelected(a[c]) && b.push(a[c]);
		this.changeSelection(null, b)
	}
};
GraphSelectionModel.prototype.changeSelection = function(a, b) {
	if (null != a && 0 < a.length && null != a[0] || null != b && 0 < b.length && null != b[0]) {
		var c = new SelectionChange(this, a, b);
		c.execute();
		var d = new UndoableEdit(this, !1);
		d.add(c);
		this.fireEvent(new EventObject(Event.UNDO, "edit", d))
	}
};
GraphSelectionModel.prototype.cellAdded = function(a) {
	null != a && !this.isSelected(a) && this.cells.push(a)
};
GraphSelectionModel.prototype.cellRemoved = function(a) {
	null != a && (a = Utils.indexOf(this.cells, a), 0 <= a && this.cells.splice(a, 1))
};

function SelectionChange(a, b, c) {
	this.selectionModel = a;
	this.added = null != b ? b.slice() : null;
	this.removed = null != c ? c.slice() : null
}
SelectionChange.prototype.execute = function() {
	var a = Log.enter("SelectionChange.execute");
	window.status = Resources.get(this.selectionModel.updatingSelectionResource) || this.selectionModel.updatingSelectionResource;
	if (null != this.removed) for (var b = 0; b < this.removed.length; b++) this.selectionModel.cellRemoved(this.removed[b]);
	if (null != this.added) for (b = 0; b < this.added.length; b++) this.selectionModel.cellAdded(this.added[b]);
	b = this.added;
	this.added = this.removed;
	this.removed = b;
	window.status = Resources.get(this.selectionModel.doneResource) || this.selectionModel.doneResource;
	Log.leave("SelectionChange.execute", a);
	this.selectionModel.fireEvent(new EventObject(Event.CHANGE, "added", this.added, "removed", this.removed))
};

function CellEditor(a) {
	this.graph = a
}
CellEditor.prototype.graph = null;
CellEditor.prototype.textarea = null;
CellEditor.prototype.editingCell = null;
CellEditor.prototype.trigger = null;
CellEditor.prototype.modified = !1;
CellEditor.prototype.autoSize = !0;
CellEditor.prototype.selectText = !0;
CellEditor.prototype.emptyLabelText = "";
CellEditor.prototype.textNode = "";
CellEditor.prototype.zIndex = 5;
CellEditor.prototype.init = function() {
	this.textarea = document.createElement("textarea");
	this.textarea.className = "CellEditor";
	this.textarea.style.position = "absolute";
	this.textarea.style.overflow = "visible";
	this.textarea.setAttribute("cols", "20");
	this.textarea.setAttribute("rows", "4");
	Client.IS_NS && (this.textarea.style.resize = "none");
	this.installListeners(this.textarea)
};
CellEditor.prototype.installListeners = function(a) {
	Event.addListener(a, "blur", Utils.bind(this, function(a) {
		this.focusLost(a)
	}));
	Event.addListener(a, "change", Utils.bind(this, function(a) {
		this.setModified(!0)
	}));
	Event.addListener(a, "keydown", Utils.bind(this, function(b) {
		Event.isConsumed(b) || (this.isStopEditingEvent(b) ? (this.graph.stopEditing(!1), Event.consume(b)) : 27 == b.keyCode ? (this.graph.stopEditing(!0), Event.consume(b)) : (this.clearOnChange && a.value == this.getEmptyLabelText() && (this.clearOnChange = !1, a.value = ""), this.setModified(!0)))
	}));
	this.changeHandler = Utils.bind(this, function(a) {
		null != this.editingCell && null == this.graph.getView().getState(this.editingCell) && this.stopEditing(!0)
	});
	this.graph.getModel().addListener(Event.CHANGE, this.changeHandler);
	Event.addListener(a, !Client.IS_IE || 9 <= document.documentMode ? "input" : "keypress", Utils.bind(this, function(a) {
		this.autoSize && !Event.isConsumed(a) && setTimeout(Utils.bind(this, function() {
			this.resize()
		}), 0)
	}))
};
CellEditor.prototype.isStopEditingEvent = function(a) {
	return 113 == a.keyCode || this.graph.isEnterStopsCellEditing() && 13 == a.keyCode && !Event.isControlDown(a) && !Event.isShiftDown(a)
};
CellEditor.prototype.isEventSource = function(a) {
	return Event.getSource(a) == this.textarea
};
CellEditor.prototype.resize = function() {
	if (null != this.textDiv) {
		var a = this.graph.getView().getState(this.editingCell);
		if (null == a) this.stopEditing(!0);
		else {
			var b = this.graph.isLabelClipped(a.cell),
				c = this.graph.isWrapping(a.cell),
				d = this.graph.getModel().isEdge(a.cell),
				e = this.graph.getView().scale,
				f = parseInt(a.style[Constants.STYLE_SPACING] || 0) * e,
				g = (parseInt(a.style[Constants.STYLE_SPACING_TOP] || 0) + Text.prototype.baseSpacingTop) * e + f,
				k = (parseInt(a.style[Constants.STYLE_SPACING_RIGHT] || 0) + Text.prototype.baseSpacingRight) * e + f,
				l = (parseInt(a.style[Constants.STYLE_SPACING_BOTTOM] || 0) + Text.prototype.baseSpacingBottom) * e + f,
				e = (parseInt(a.style[Constants.STYLE_SPACING_LEFT] || 0) + Text.prototype.baseSpacingLeft) * e + f,
				g = new Rectangle(a.x, a.y, a.width - e - k, a.height - g - l),
				g = null != a.shape ? a.shape.getLabelBounds(g) : g;
			d ? (this.bounds.x = a.absoluteOffset.x, this.bounds.y = a.absoluteOffset.y, this.bounds.width = 0, this.bounds.height = 0) : null != this.bounds && (this.bounds.x = g.x + a.absoluteOffset.x, this.bounds.y = g.y + a.absoluteOffset.y, this.bounds.width = g.width, this.bounds.height = g.height);
			d = this.textarea.value;
			if ("\n" == d.charAt(d.length - 1) || "" == d) d += "&nbsp;";
			d = Utils.htmlEntities(d, !1);
			c ? (this.textDiv.style.whiteSpace = "normal", this.textDiv.style.width = this.bounds.width + "px") : d = d.replace(/ /g, "&nbsp;");
			d = d.replace(/\n/g, "<br/>");
			this.textDiv.innerHTML = d;
			d = this.textDiv.offsetWidth + 30;
			g = this.textDiv.offsetHeight + 16;
			d = Math.max(d, 40);
			g = Math.max(g, 20);
			b ? (d = Math.min(this.bounds.width, d), g = Math.min(this.bounds.height, g)) : c && (d = Math.max(this.bounds.width, this.textDiv.scrollWidth));
			b = null != a.text ? a.text.margin : null;
			null == b && (b = Utils.getValue(a.style, Constants.STYLE_ALIGN, Constants.ALIGN_CENTER), a = Utils.getValue(a.style, Constants.STYLE_VERTICAL_ALIGN, Constants.ALIGN_MIDDLE), b = Utils.getAlignmentAsPoint(b, a));
			null != b && (this.textarea.style.left = Math.max(0, Math.round(this.bounds.x - b.x * this.bounds.width + b.x * d) - 3) + "px", this.textarea.style.top = Math.max(0, Math.round(this.bounds.y - b.y * this.bounds.height + b.y * g) + 4) + "px");
			this.textarea.style.width = d + (this.textarea.offsetWidth - this.textarea.clientWidth + 4) + "px";
			this.textarea.style.height = g + "px"
		}
	}
};
CellEditor.prototype.isModified = function() {
	return this.modified
};
CellEditor.prototype.setModified = function(a) {
	this.modified = a
};
CellEditor.prototype.focusLost = function() {
	this.stopEditing(!this.graph.isInvokesStopCellEditing())
};
CellEditor.prototype.startEditing = function(a, b) {
	null == this.textarea && this.init();
	this.stopEditing(!0);
	var c = this.graph.getView().getState(a);
	if (null != c) {
		this.editingCell = a;
		this.trigger = b;
		this.textNode = null;
		null != c.text && this.isHideLabel(c) && (this.textNode = c.text.node, this.textNode.style.visibility = "hidden");
		var d = this.graph.getView().scale,
			d = Utils.getValue(c.style, Constants.STYLE_FONTSIZE, Constants.DEFAULT_FONTSIZE) * d,
			e = Utils.getValue(c.style, Constants.STYLE_FONTFAMILY, Constants.DEFAULT_FONTFAMILY),
			f = Utils.getValue(c.style, Constants.STYLE_FONTCOLOR, "black"),
			g = Utils.getValue(c.style, Constants.STYLE_ALIGN, Constants.ALIGN_LEFT),
			k = (Utils.getValue(c.style, Constants.STYLE_FONTSTYLE, 0) & Constants.FONT_BOLD) == Constants.FONT_BOLD,
			l = (Utils.getValue(c.style, Constants.STYLE_FONTSTYLE, 0) & Constants.FONT_ITALIC) == Constants.FONT_ITALIC,
			m = (Utils.getValue(c.style, Constants.STYLE_FONTSTYLE, 0) & Constants.FONT_UNDERLINE) == Constants.FONT_UNDERLINE;
		this.textarea.style.lineHeight = Constants.ABSOLUTE_LINE_HEIGHT ? Math.round(d * Constants.LINE_HEIGHT) + "px" : Constants.LINE_HEIGHT;
		this.textarea.style.textDecoration = m ? "underline" : "";
		this.textarea.style.fontWeight = k ? "bold" : "normal";
		this.textarea.style.fontStyle = l ? "italic" : "";
		this.textarea.style.fontSize = Math.round(d) + "px";
		this.textarea.style.fontFamily = e;
		this.textarea.style.textAlign = g;
		this.textarea.style.overflow = "auto";
		this.textarea.style.outline = "none";
		this.textarea.style.color = f;
		this.bounds = d = this.getEditorBounds(c);
		this.textarea.style.left = d.x + "px";
		this.textarea.style.top = d.y + "px";
		this.textarea.style.width = d.width + "px";
		this.textarea.style.height = d.height + "px";
		this.textarea.style.zIndex = this.zIndex;
		c = this.getInitialValue(c, b);
		null == c || 0 == c.length ? (c = this.getEmptyLabelText(), this.clearOnChange = 0 < c.length) : this.clearOnChange = !1;
		this.setModified(!1);
		this.textarea.value = c;
		this.graph.container.appendChild(this.textarea);
		"none" != this.textarea.style.display && (this.autoSize && (this.textDiv = this.createTextDiv(), document.body.appendChild(this.textDiv), this.resize()), this.textarea.focus(), this.isSelectText() && 0 < this.textarea.value.length && (Client.IS_IOS ? document.execCommand("selectAll") : this.textarea.select()))
	}
};
CellEditor.prototype.isSelectText = function() {
	return this.selectText
};
CellEditor.prototype.createTextDiv = function() {
	var a = document.createElement("div"),
		b = a.style;
	b.position = "absolute";
	b.whiteSpace = "nowrap";
	b.visibility = "hidden";
	b.display = Client.IS_QUIRKS ? "inline" : "inline-block";
	b.zoom = "1";
	b.verticalAlign = "top";
	b.lineHeight = this.textarea.style.lineHeight;
	b.fontSize = this.textarea.style.fontSize;
	b.fontFamily = this.textarea.style.fontFamily;
	b.fontWeight = this.textarea.style.fontWeight;
	b.textAlign = this.textarea.style.textAlign;
	b.fontStyle = this.textarea.style.fontStyle;
	b.textDecoration = this.textarea.style.textDecoration;
	return a
};
CellEditor.prototype.stopEditing = function(a) {
	null != this.editingCell && (null != this.textNode && (this.textNode.style.visibility = "visible", this.textNode = null), !a && this.isModified() && this.graph.labelChanged(this.editingCell, this.getCurrentValue(), this.trigger), null != this.textDiv && (document.body.removeChild(this.textDiv), this.textDiv = null), this.bounds = this.trigger = this.editingCell = null, this.textarea.blur(), null != this.textarea.parentNode && this.textarea.parentNode.removeChild(this.textarea))
};
CellEditor.prototype.getInitialValue = function(a, b) {
	return this.graph.getEditingValue(a.cell, b)
};
CellEditor.prototype.getCurrentValue = function() {
	return this.textarea.value.replace(/\r/g, "")
};
CellEditor.prototype.isHideLabel = function(a) {
	return !0
};
CellEditor.prototype.getMinimumSize = function(a) {
	var b = this.graph.getView().scale;
	return new Rectangle(0, 0, null == a.text ? 30 : a.text.size * b + 20, "left" == this.textarea.style.textAlign ? 120 : 40)
};
CellEditor.prototype.getEditorBounds = function(a) {
	var b = this.graph.getModel().isEdge(a.cell),
		c = this.graph.getView().scale,
		d = this.getMinimumSize(a),
		e = d.width,
		d = d.height,
		f = parseInt(a.style[Constants.STYLE_SPACING] || 0) * c,
		g = (parseInt(a.style[Constants.STYLE_SPACING_TOP] || 0) + Text.prototype.baseSpacingTop) * c + f,
		k = (parseInt(a.style[Constants.STYLE_SPACING_RIGHT] || 0) + Text.prototype.baseSpacingRight) * c + f,
		l = (parseInt(a.style[Constants.STYLE_SPACING_BOTTOM] || 0) + Text.prototype.baseSpacingBottom) * c + f,
		c = (parseInt(a.style[Constants.STYLE_SPACING_LEFT] || 0) + Text.prototype.baseSpacingLeft) * c + f,
		k = new Rectangle(a.x, a.y, Math.max(e, a.width - c - k), Math.max(d, a.height - g - l)),
		k = null != a.shape ? a.shape.getLabelBounds(k) : k;
	b ? (k.x = a.absoluteOffset.x, k.y = a.absoluteOffset.y, null != a.text && null != a.text.boundingBox && (0 < a.text.boundingBox.x && (k.x = a.text.boundingBox.x), 0 < a.text.boundingBox.y && (k.y = a.text.boundingBox.y))) : null != a.text && null != a.text.boundingBox && (k.x = Math.min(k.x, a.text.boundingBox.x), k.y = Math.min(k.y, a.text.boundingBox.y));
	k.x += c;
	k.y += g;
	null != a.text && null != a.text.boundingBox && (b ? (k.width = Math.max(e, a.text.boundingBox.width), k.height = Math.max(d, a.text.boundingBox.height)) : (k.width = Math.max(k.width, a.text.boundingBox.width), k.height = Math.max(k.height, a.text.boundingBox.height)));
	this.graph.getModel().isVertex(a.cell) && (b = Utils.getValue(a.style, Constants.STYLE_LABEL_POSITION, Constants.ALIGN_CENTER), b == Constants.ALIGN_LEFT ? k.x -= a.width : b == Constants.ALIGN_RIGHT && (k.x += a.width), b = Utils.getValue(a.style, Constants.STYLE_VERTICAL_LABEL_POSITION, Constants.ALIGN_MIDDLE), b == Constants.ALIGN_TOP ? k.y -= a.height : b == Constants.ALIGN_BOTTOM && (k.y += a.height));
	return k
};
CellEditor.prototype.getEmptyLabelText = function(a) {
	return this.emptyLabelText
};
CellEditor.prototype.getEditingCell = function() {
	return this.editingCell
};
CellEditor.prototype.destroy = function() {
	null != this.textarea && (Event.release(this.textarea), null != this.textarea.parentNode && this.textarea.parentNode.removeChild(this.textarea), this.textarea = null, null != this.changeHandler && (this.graph.getModel().removeListener(this.changeHandler), this.changeHandler = null))
};

function CellRenderer() {}
CellRenderer.prototype.defaultEdgeShape = Connector;
CellRenderer.prototype.defaultVertexShape = RectangleShape;
CellRenderer.prototype.defaultTextShape = Text;
CellRenderer.prototype.legacyControlPosition = !0;
CellRenderer.prototype.defaultShapes = {};
CellRenderer.registerShape = function(a, b) {
	CellRenderer.prototype.defaultShapes[a] = b
};
CellRenderer.registerShape(Constants.SHAPE_RECTANGLE, RectangleShape);
CellRenderer.registerShape(Constants.SHAPE_ELLIPSE, Ellipse);
CellRenderer.registerShape(Constants.SHAPE_RHOMBUS, Rhombus);
CellRenderer.registerShape(Constants.SHAPE_CYLINDER, Cylinder);
CellRenderer.registerShape(Constants.SHAPE_CONNECTOR, Connector);
CellRenderer.registerShape(Constants.SHAPE_ACTOR, Actor);
CellRenderer.registerShape(Constants.SHAPE_TRIANGLE, Triangle);
CellRenderer.registerShape(Constants.SHAPE_HEXAGON, Hexagon);
CellRenderer.registerShape(Constants.SHAPE_CLOUD, Cloud);
CellRenderer.registerShape(Constants.SHAPE_LINE, Line);
CellRenderer.registerShape(Constants.SHAPE_ARROW, Arrow);
CellRenderer.registerShape(Constants.SHAPE_DOUBLE_ELLIPSE, DoubleEllipse);
CellRenderer.registerShape(Constants.SHAPE_SWIMLANE, Swimlane);
CellRenderer.registerShape(Constants.SHAPE_IMAGE, ImageShape);
CellRenderer.registerShape(Constants.SHAPE_LABEL, Label);
CellRenderer.prototype.initializeShape = function(a) {
	a.shape.dialect = a.view.graph.dialect;
	this.configureShape(a);
	a.shape.init(a.view.getDrawPane())
};
CellRenderer.prototype.createShape = function(a) {
	if (null != a.style) {
		var b = StencilRegistry.getStencil(a.style[Constants.STYLE_SHAPE]);
		null != b ? a.shape = new Shape(b) : (b = this.getShapeConstructor(a), a.shape = new b)
	}
};
CellRenderer.prototype.createIndicatorShape = function(a) {
	a.shape.indicatorShape = this.getShape(a.view.graph.getIndicatorShape(a))
};
CellRenderer.prototype.getShape = function(a) {
	return null != a ? CellRenderer.prototype.defaultShapes[a] : null
};
CellRenderer.prototype.getShapeConstructor = function(a) {
	var b = this.getShape(a.style[Constants.STYLE_SHAPE]);
	null == b && (b = a.view.graph.getModel().isEdge(a.cell) ? this.defaultEdgeShape : this.defaultVertexShape);
	return b
};
CellRenderer.prototype.configureShape = function(a) {
	a.shape.apply(a);
	a.shape.image = a.view.graph.getImage(a);
	a.shape.indicatorColor = a.view.graph.getIndicatorColor(a);
	a.shape.indicatorStrokeColor = a.style[Constants.STYLE_INDICATOR_STROKECOLOR];
	a.shape.indicatorGradientColor = a.view.graph.getIndicatorGradientColor(a);
	a.shape.indicatorDirection = a.style[Constants.STYLE_INDICATOR_DIRECTION];
	a.shape.indicatorImage = a.view.graph.getIndicatorImage(a);
	this.postConfigureShape(a)
};
CellRenderer.prototype.postConfigureShape = function(a) {
	null != a.shape && (this.resolveColor(a, "indicatorColor", Constants.STYLE_FILLCOLOR), this.resolveColor(a, "indicatorGradientColor", Constants.STYLE_GRADIENTCOLOR), this.resolveColor(a, "fill", Constants.STYLE_FILLCOLOR), this.resolveColor(a, "stroke", Constants.STYLE_STROKECOLOR), this.resolveColor(a, "gradient", Constants.STYLE_GRADIENTCOLOR))
};
CellRenderer.prototype.resolveColor = function(a, b, c) {
	var d = a.shape[b],
		e = a.view.graph,
		f = null;
	"inherit" == d ? f = e.model.getParent(a.cell) : "swimlane" == d ? (f = null != e.model.getTerminal(a.cell, !1) ? e.model.getTerminal(a.cell, !1) : a.cell, f = e.getSwimlane(f), c = e.swimlaneIndicatorColorAttribute) : "indicated" == d && (a.shape[b] = a.shape.indicatorColor);
	null != f && (d = e.getView().getState(f), a.shape[b] = null, null != d && (a.shape[b] = null != d.shape && "indicatorColor" != b ? d.shape[b] : d.style[c]))
};
CellRenderer.prototype.getLabelValue = function(a) {
	return a.view.graph.getLabel(a.cell)
};
CellRenderer.prototype.createLabel = function(a, b) {
	var c = a.view.graph;
	c.getModel().isEdge(a.cell);
	if (0 < a.style[Constants.STYLE_FONTSIZE] || null == a.style[Constants.STYLE_FONTSIZE]) {
		var d = c.isHtmlLabel(a.cell) || null != b && Utils.isNode(b);
		a.text = new this.defaultTextShape(b, new Rectangle, a.style[Constants.STYLE_ALIGN] || Constants.ALIGN_CENTER, c.getVerticalAlign(a), a.style[Constants.STYLE_FONTCOLOR], a.style[Constants.STYLE_FONTFAMILY], a.style[Constants.STYLE_FONTSIZE], a.style[Constants.STYLE_FONTSTYLE], a.style[Constants.STYLE_SPACING], a.style[Constants.STYLE_SPACING_TOP], a.style[Constants.STYLE_SPACING_RIGHT], a.style[Constants.STYLE_SPACING_BOTTOM], a.style[Constants.STYLE_SPACING_LEFT], a.style[Constants.STYLE_HORIZONTAL], a.style[Constants.STYLE_LABEL_BACKGROUNDCOLOR], a.style[Constants.STYLE_LABEL_BORDERCOLOR], c.isWrapping(a.cell) && c.isHtmlLabel(a.cell), c.isLabelClipped(a.cell), a.style[Constants.STYLE_OVERFLOW], a.style[Constants.STYLE_LABEL_PADDING]);
		a.text.opacity = Utils.getValue(a.style, Constants.STYLE_TEXT_OPACITY, 100);
		a.text.dialect = d ? Constants.DIALECT_STRICTHTML : a.view.graph.dialect;
		a.text.style = a.style;
		a.text.state = a;
		this.initializeLabel(a);
		var e = !1,
			f = function(b) {
				var d = a;
				if (Client.IS_TOUCH || e) d = Event.getClientX(b), b = Event.getClientY(b), b = Utils.convertPoint(c.container, d, b), d = c.view.getState(c.getCellAt(b.x, b.y));
				return d
			};
		Event.addGestureListeners(a.text.node, Utils.bind(this, function(b) {
			this.isLabelEvent(a, b) && (c.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(b, a)), e = c.dialect != Constants.DIALECT_SVG && "IMG" == Event.getSource(b).nodeName)
		}), Utils.bind(this, function(b) {
			this.isLabelEvent(a, b) && c.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(b, f(b)))
		}), Utils.bind(this, function(b) {
			this.isLabelEvent(a, b) && (c.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(b, f(b))), e = !1)
		}));
		c.nativeDblClickEnabled && Event.addListener(a.text.node, "dblclick", Utils.bind(this, function(b) {
			this.isLabelEvent(a, b) && (c.dblClick(b, a.cell), Event.consume(b))
		}))
	}
};
CellRenderer.prototype.initializeLabel = function(a) {
	Client.IS_SVG && Client.NO_FO && a.text.dialect != Constants.DIALECT_SVG ? a.text.init(a.view.graph.container) : a.text.init(a.view.getDrawPane())
};
CellRenderer.prototype.createCellOverlays = function(a) {
	var b = a.view.graph.getCellOverlays(a.cell),
		c = null;
	if (null != b) for (var c = new Dictionary, d = 0; d < b.length; d++) {
		var e = null != a.overlays ? a.overlays.remove(b[d]) : null;
		null == e && (e = new ImageShape(new Rectangle, b[d].image.src), e.dialect = a.view.graph.dialect, e.preserveImageAspect = !1, e.overlay = b[d], this.initializeOverlay(a, e), this.installCellOverlayListeners(a, b[d], e), null != b[d].cursor && (e.node.style.cursor = b[d].cursor));
		c.put(b[d], e)
	}
	null != a.overlays && a.overlays.visit(function(a, b) {
		b.destroy()
	});
	a.overlays = c
};
CellRenderer.prototype.initializeOverlay = function(a, b) {
	b.init(a.view.getOverlayPane())
};
CellRenderer.prototype.installCellOverlayListeners = function(a, b, c) {
	var d = a.view.graph;
	Event.addListener(c.node, "click", function(c) {
		d.isEditing() && d.stopEditing(!d.isInvokesStopCellEditing());
		b.fireEvent(new EventObject(Event.CLICK, "event", c, "cell", a.cell))
	});
	Event.addGestureListeners(c.node, function(a) {
		Event.consume(a)
	}, function(b) {
		d.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(b, a))
	});
	Client.IS_TOUCH && Event.addListener(c.node, "touchend", function(c) {
		b.fireEvent(new EventObject(Event.CLICK, "event", c, "cell", a.cell))
	})
};
CellRenderer.prototype.createControl = function(a) {
	var b = a.view.graph,
		c = b.getFoldingImage(a);
	if (b.foldingEnabled && null != c) {
		if (null == a.control) {
			var d = new Rectangle(0, 0, c.width, c.height);
			a.control = new ImageShape(d, c.src);
			a.control.preserveImageAspect = !1;
			a.control.dialect = b.dialect;
			this.initControl(a, a.control, !0, function(c) {
				if (b.isEnabled()) {
					var d = !b.isCellCollapsed(a.cell);
					b.foldCells(d, !1, [a.cell]);
					Event.consume(c)
				}
			})
		}
	} else null != a.control && (a.control.destroy(), a.control = null)
};
CellRenderer.prototype.initControl = function(a, b, c, d) {
	var e = a.view.graph;
	e.isHtmlLabel(a.cell) && Client.NO_FO && e.dialect == Constants.DIALECT_SVG ? (b.dialect = Constants.DIALECT_PREFERHTML, b.init(e.container), b.node.style.zIndex = 1) : b.init(a.view.getOverlayPane());
	b = b.innerNode || b.node;
	d && (e.isEnabled() && (b.style.cursor = "pointer"), Event.addListener(b, "click", d));
	c && Event.addGestureListeners(b, function(b) {
		e.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(b, a));
		Event.consume(b)
	}, function(b) {
		e.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(b, a))
	});
	return b
};
CellRenderer.prototype.isShapeEvent = function(a, b) {
	return !0
};
CellRenderer.prototype.isLabelEvent = function(a, b) {
	return !0
};
CellRenderer.prototype.installListeners = function(a) {
	var b = a.view.graph,
		c = function(c) {
			var e = a;
			if (b.dialect != Constants.DIALECT_SVG && "IMG" == Event.getSource(c).nodeName || Client.IS_TOUCH) e = Event.getClientX(c), c = Event.getClientY(c), c = Utils.convertPoint(b.container, e, c), e = b.view.getState(b.getCellAt(c.x, c.y));
			return e
		};
	Event.addGestureListeners(a.shape.node, Utils.bind(this, function(c) {
		this.isShapeEvent(a, c) && b.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(c, null != a.shape && Event.getSource(c) == a.shape.content ? null : a))
	}), Utils.bind(this, function(d) {
		this.isShapeEvent(a, d) && b.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(d, null != a.shape && Event.getSource(d) == a.shape.content ? null : c(d)))
	}), Utils.bind(this, function(d) {
		this.isShapeEvent(a, d) && b.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(d, null != a.shape && Event.getSource(d) == a.shape.content ? null : c(d)))
	}));
	b.nativeDblClickEnabled && Event.addListener(a.shape.node, "dblclick", Utils.bind(this, function(c) {
		this.isShapeEvent(a, c) && (b.dblClick(c, a.cell), Event.consume(c))
	}))
};
CellRenderer.prototype.redrawLabel = function(a, b) {
	var c = this.getLabelValue(a);
	if (null == a.text && null != c && (Utils.isNode(c) || 0 < c.length)) this.createLabel(a, c);
	else if (null != a.text && (null == c || 0 == c.length)) a.text.destroy(), a.text = null;
	if (null != a.text) {
		var d = a.view.graph,
			e = d.isWrapping(a.cell),
			d = d.isLabelClipped(a.cell),
			f = this.getLabelBounds(a),
			g = a.view.graph.isHtmlLabel(a.cell) || null != c && Utils.isNode(c) ? Constants.DIALECT_STRICTHTML : a.view.graph.dialect;
		if (b || a.text.value != c || a.text.isWrapping != e || a.text.isClipping != d || a.text.scale != a.view.scale || a.text.dialect != g || !a.text.bounds.equals(f)) a.text.dialect = g, a.text.value = c, a.text.bounds = f, a.text.scale = this.getTextScale(a), a.text.isWrapping = e, a.text.isClipping = d, a.text.redraw()
	}
};
CellRenderer.prototype.getTextScale = function(a) {
	return a.view.scale
};
CellRenderer.prototype.getLabelBounds = function(a) {
	var b = a.view.graph,
		c = a.view.scale,
		d = b.getModel().isEdge(a.cell),
		e = new Rectangle(a.absoluteOffset.x, a.absoluteOffset.y);
	if (d) {
		var f = a.text.getSpacing();
		e.x += f.x * c;
		e.y += f.y * c;
		b = b.getCellGeometry(a.cell);
		null != b && (e.width = Math.max(0, b.width * c), e.height = Math.max(0, b.height * c))
	} else a.text.isPaintBoundsInverted() && (b = e.x, e.x = e.y, e.y = b), e.x += a.x, e.y += a.y, e.width = Math.max(1, a.width), e.height = Math.max(1, a.height);
	a.text.isPaintBoundsInverted() && (b = (a.width - a.height) / 2, e.x += b, e.y -= b, b = e.width, e.width = e.height, e.height = b);
	null != a.shape && (e = a.shape.getLabelBounds(e));
	b = Utils.getValue(a.style, Constants.STYLE_LABEL_WIDTH, null);
	null != b && (e.width = parseFloat(b) * c);
	d || this.rotateLabelBounds(a, e);
	return e
};
CellRenderer.prototype.rotateLabelBounds = function(a, b) {
	b.x -= a.text.margin.x * b.width;
	b.y -= a.text.margin.y * b.height;
	if ("fill" != a.style[Constants.STYLE_OVERFLOW] && "width" != a.style[Constants.STYLE_OVERFLOW]) {
		var c = a.view.scale,
			d = a.text.getSpacing();
		b.x += d.x * c;
		b.y += d.y * c;
		var d = Utils.getValue(a.style, Constants.STYLE_LABEL_POSITION, Constants.ALIGN_CENTER),
			e = Utils.getValue(a.style, Constants.STYLE_VERTICAL_LABEL_POSITION, Constants.ALIGN_MIDDLE),
			f = Utils.getValue(a.style, Constants.STYLE_LABEL_WIDTH, null);
		b.width = Math.max(0, b.width - (d == Constants.ALIGN_CENTER && null == f ? a.text.spacingLeft * c + a.text.spacingRight * c : 0));
		b.height = Math.max(0, b.height - (e == Constants.ALIGN_MIDDLE ? a.text.spacingTop * c + a.text.spacingBottom * c : 0))
	}
	e = a.text.getTextRotation();
	if (0 != e && (null != a && a.view.graph.model.isVertex(a.cell)) && (c = a.getCenterX(), d = a.getCenterY(), b.x != c || b.y != d)) e *= Math.PI / 180, pt = Utils.getRotatedPoint(new Point(b.x, b.y), Math.cos(e), Math.sin(e), new Point(c, d)), b.x = pt.x, b.y = pt.y
};
CellRenderer.prototype.redrawCellOverlays = function(a, b) {
	this.createCellOverlays(a);
	if (null != a.overlays) {
		var c = Utils.mod(Utils.getValue(a.style, Constants.STYLE_ROTATION, 0), 90),
			d = Utils.toRadians(c),
			e = Math.cos(d),
			f = Math.sin(d);
		a.overlays.visit(function(d, k) {
			var l = k.overlay.getBounds(a);
			if (!a.view.graph.getModel().isEdge(a.cell) && null != a.shape && 0 != c) {
				var m = l.getCenterX(),
					n = l.getCenterY(),
					n = Utils.getRotatedPoint(new Point(m, n), e, f, new Point(a.getCenterX(), a.getCenterY())),
					m = n.x,
					n = n.y;
				l.x = Math.round(m - l.width / 2);
				l.y = Math.round(n - l.height / 2)
			}
			if (b || null == k.bounds || k.scale != a.view.scale || !k.bounds.equals(l)) k.bounds = l, k.scale = a.view.scale, k.redraw()
		})
	}
};
CellRenderer.prototype.redrawControl = function(a, b) {
	var c = a.view.graph.getFoldingImage(a);
	if (null != a.control && null != c) {
		var c = this.getControlBounds(a, c.width, c.height),
			d = this.legacyControlPosition ? Utils.getValue(a.style, Constants.STYLE_ROTATION, 0) : a.shape.getTextRotation(),
			e = a.view.scale;
		if (b || a.control.scale != e || !a.control.bounds.equals(c) || a.control.rotation != d) a.control.rotation = d, a.control.bounds = c, a.control.scale = e, a.control.redraw()
	}
};
CellRenderer.prototype.getControlBounds = function(a, b, c) {
	if (null != a.control) {
		var d = a.view.scale,
			e = a.getCenterX(),
			f = a.getCenterY();
		if (!a.view.graph.getModel().isEdge(a.cell) && (e = a.x + b * d, f = a.y + c * d, null != a.shape)) {
			var g = a.shape.getShapeRotation();
			if (this.legacyControlPosition) g = Utils.getValue(a.style, Constants.STYLE_ROTATION, 0);
			else if (a.shape.isPaintBoundsInverted()) var k = (a.width - a.height) / 2,
				e = e + k,
				f = f - k;
			0 != g && (k = Utils.toRadians(g), g = Math.cos(k), k = Math.sin(k), f = Utils.getRotatedPoint(new Point(e, f), g, k, new Point(a.getCenterX(), a.getCenterY())), e = f.x, f = f.y)
		}
		return a.view.graph.getModel().isEdge(a.cell), new Rectangle(Math.round(e - b / 2 * d), Math.round(f - c / 2 * d), Math.round(b * d), Math.round(c * d))
	}
	return null
};
CellRenderer.prototype.insertStateAfter = function(a, b, c) {
	for (var d = this.getShapesForState(a), e = 0; e < d.length; e++) if (null != d[e]) {
		var f = d[e].node.parentNode != a.view.getDrawPane(),
			g = f ? c : b;
		if (null != g && g.nextSibling != d[e].node) null == g.nextSibling ? g.parentNode.appendChild(d[e].node) : g.parentNode.insertBefore(d[e].node, g.nextSibling);
		else if (null == g) if (d[e].node.parentNode == a.view.graph.container) {
			for (g = a.view.canvas; null != g.parentNode && g.parentNode != a.view.graph.container;) g = g.parentNode;
			null != g.nextSibling && g.nextSibling != d[e].node && d[e].node.parentNode.insertBefore(d[e].node, g.nextSibling)
		} else null != d[e].node.parentNode.firstChild && d[e].node.parentNode.firstChild != d[e].node && d[e].node.parentNode.insertBefore(d[e].node, d[e].node.parentNode.firstChild);
		f ? c = d[e].node : b = d[e].node
	}
	return [b, c]
};
CellRenderer.prototype.getShapesForState = function(a) {
	return [a.shape, a.text]
};
CellRenderer.prototype.redraw = function(a, b, c) {
	b = this.redrawShape(a, b, c);
	if (null != a.shape && (null == c || c)) this.redrawLabel(a, b), this.redrawCellOverlays(a, b), this.redrawControl(a, b)
};
CellRenderer.prototype.redrawShape = function(a, b, c) {
	var d = !1;
	if (null != a.shape && (null == a.shape.node && (this.createIndicatorShape(a), this.initializeShape(a), this.createCellOverlays(a), this.installListeners(a)), this.createControl(a), Utils.equalEntries(a.shape.style, a.style) || (this.configureShape(a), b = !0), b || null == a.shape.bounds || a.shape.scale != a.view.scale || null == a.absolutePoints && !a.shape.bounds.equals(a) || null != a.absolutePoints && !Utils.equalPoints(a.shape.points, a.absolutePoints))) null != a.absolutePoints ? (a.shape.points = a.absolutePoints.slice(), a.shape.bounds = null) : (a.shape.points = null, a.shape.bounds = new Rectangle(a.x, a.y, a.width, a.height)), a.shape.scale = a.view.scale, null == c || c ? a.shape.redraw() : a.shape.updateBoundingBox(), d = !0;
	return d
};
CellRenderer.prototype.destroy = function(a) {
	null != a.shape && (null != a.text && (a.text.destroy(), a.text = null), null != a.overlays && (a.overlays.visit(function(a, c) {
		c.destroy()
	}), a.overlays = null), null != a.control && (a.control.destroy(), a.control = null), a.shape.destroy(), a.shape = null)
};
var EdgeStyle = {
	EntityRelation: function(a, b, c, d, e) {
		var f = a.view,
			g = f.graph;
		d = Utils.getValue(a.style, Constants.STYLE_SEGMENT, Constants.ENTITY_SEGMENT) * f.scale;
		var k = a.absolutePoints,
			l = k[0],
			m = k[k.length - 1],
			k = !1;
		if (null != l) b = new CellState, b.x = l.x, b.y = l.y;
		else if (null != b) {
			var n = Utils.getPortConstraints(b, a, !0, Constants.DIRECTION_MASK_NONE);
			n != Constants.DIRECTION_MASK_NONE ? k = n == Constants.DIRECTION_MASK_WEST : (l = g.getCellGeometry(b.cell), l.relative ? k = 0.5 >= l.x : null != c && (k = c.x + c.width < b.x))
		} else return;
		l = !0;
		null != m ? (c = new CellState, c.x = m.x, c.y = m.y) : null != c && (n = Utils.getPortConstraints(c, a, !1, Constants.DIRECTION_MASK_NONE), n != Constants.DIRECTION_MASK_NONE ? l = n == Constants.DIRECTION_MASK_WEST : (a = g.getCellGeometry(c.cell), a.relative ? l = 0.5 >= a.x : null != b && (l = b.x + b.width < c.x)));
		null != b && null != c && (a = k ? b.x : b.x + b.width, b = f.getRoutingCenterY(b), g = l ? c.x : c.x + c.width, c = f.getRoutingCenterY(c), f = new Point(a + (k ? -d : d), b), m = new Point(g + (l ? -d : d), c), k == l ? (d = k ? Math.min(a, g) - d : Math.max(a, g) + d, e.push(new Point(d, b)), e.push(new Point(d, c))) : (f.x < m.x == k ? (d = b + (c - b) / 2, e.push(f), e.push(new Point(f.x, d)), e.push(new Point(m.x, d))) : e.push(f), e.push(m)))
	},
	Loop: function(a, b, c, d, e) {
		c = a.absolutePoints;
		var f = c[c.length - 1];
		if (null != c[0] && null != f) {
			if (null != d && 0 < d.length) for (b = 0; b < d.length; b++) c = d[b], c = a.view.transformControlPoint(a, c), e.push(new Point(c.x, c.y))
		} else if (null != b) {
			var f = a.view,
				g = f.graph;
			c = null != d && 0 < d.length ? d[0] : null;
			null != c && (c = f.transformControlPoint(a, c), Utils.contains(b, c.x, c.y) && (c = null));
			var k = d = 0,
				l = 0,
				m = 0,
				g = Utils.getValue(a.style, Constants.STYLE_SEGMENT, g.gridSize) * f.scale;
			a = Utils.getValue(a.style, Constants.STYLE_DIRECTION, Constants.DIRECTION_WEST);
			a == Constants.DIRECTION_NORTH || a == Constants.DIRECTION_SOUTH ? (d = f.getRoutingCenterX(b), k = g) : (l = f.getRoutingCenterY(b), m = g);
			null == c || c.x < b.x || c.x > b.x + b.width ? null != c ? (d = c.x, m = Math.max(Math.abs(l - c.y), m)) : a == Constants.DIRECTION_NORTH ? l = b.y - 2 * k : a == Constants.DIRECTION_SOUTH ? l = b.y + b.height + 2 * k : d = a == Constants.DIRECTION_EAST ? b.x - 2 * m : b.x + b.width + 2 * m : null != c && (d = f.getRoutingCenterX(b), k = Math.max(Math.abs(d - c.x), m), l = c.y, m = 0);
			e.push(new Point(d - k, l - m));
			e.push(new Point(d + k, l + m))
		}
	},
	ElbowConnector: function(a, b, c, d, e) {
		var f = null != d && 0 < d.length ? d[0] : null,
			g = !1,
			k = !1;
		if (null != b && null != c) if (null != f) var l = Math.min(b.x, c.x),
			m = Math.max(b.x + b.width, c.x + c.width),
			k = Math.min(b.y, c.y),
			n = Math.max(b.y + b.height, c.y + c.height),
			f = a.view.transformControlPoint(a, f),
			g = f.y < k || f.y > n,
			k = f.x < l || f.x > m;
		else l = Math.max(b.x, c.x), m = Math.min(b.x + b.width, c.x + c.width), g = l == m, g || (k = Math.max(b.y, c.y), n = Math.min(b.y + b.height, c.y + c.height), k = k == n);
		!k && (g || a.style[Constants.STYLE_ELBOW] == Constants.ELBOW_VERTICAL) ? EdgeStyle.TopToBottom(a, b, c, d, e) : EdgeStyle.SideToSide(a, b, c, d, e)
	},
	SideToSide: function(a, b, c, d, e) {
		var f = a.view;
		d = null != d && 0 < d.length ? d[0] : null;
		var g = a.absolutePoints,
			k = g[0],
			g = g[g.length - 1];
		null != d && (d = f.transformControlPoint(a, d));
		null != k && (b = new CellState, b.x = k.x, b.y = k.y);
		null != g && (c = new CellState, c.x = g.x, c.y = g.y);
		null != b && null != c && (a = Math.max(b.x, c.x), k = Math.min(b.x + b.width, c.x + c.width), a = null != d ? d.x : k + (a - k) / 2, k = f.getRoutingCenterY(b), f = f.getRoutingCenterY(c), null != d && (d.y >= b.y && d.y <= b.y + b.height && (k = d.y), d.y >= c.y && d.y <= c.y + c.height && (f = d.y)), !Utils.contains(c, a, k) && !Utils.contains(b, a, k) && e.push(new Point(a, k)), !Utils.contains(c, a, f) && !Utils.contains(b, a, f) && e.push(new Point(a, f)), 1 == e.length && (null != d ? !Utils.contains(c, a, d.y) && !Utils.contains(b, a, d.y) && e.push(new Point(a, d.y)) : (f = Math.max(b.y, c.y), b = Math.min(b.y + b.height, c.y + c.height), e.push(new Point(a, f + (b - f) / 2)))))
	},
	TopToBottom: function(a, b, c, d, e) {
		var f = a.view;
		d = null != d && 0 < d.length ? d[0] : null;
		var g = a.absolutePoints,
			k = g[0],
			g = g[g.length - 1];
		null != d && (d = f.transformControlPoint(a, d));
		null != k && (b = new CellState, b.x = k.x, b.y = k.y);
		null != g && (c = new CellState, c.x = g.x, c.y = g.y);
		null != b && null != c && (k = Math.max(b.y, c.y), g = Math.min(b.y + b.height, c.y + c.height), a = f.getRoutingCenterX(b), null != d && (d.x >= b.x && d.x <= b.x + b.width) && (a = d.x), k = null != d ? d.y : g + (k - g) / 2, !Utils.contains(c, a, k) && !Utils.contains(b, a, k) && e.push(new Point(a, k)), a = null != d && d.x >= c.x && d.x <= c.x + c.width ? d.x : f.getRoutingCenterX(c), !Utils.contains(c, a, k) && !Utils.contains(b, a, k) && e.push(new Point(a, k)), 1 == e.length && (null != d && 1 == e.length ? !Utils.contains(c, d.x, k) && !Utils.contains(b, d.x, k) && e.push(new Point(d.x, k)) : (f = Math.max(b.x, c.x), b = Math.min(b.x + b.width, c.x + c.width), e.push(new Point(f + (b - f) / 2, k)))))
	},
	SegmentConnector: function(a, b, c, d, e) {
		var f = a.absolutePoints,
			g = !0,
			k = null,
			l = f[0];
		null == l && null != b ? l = new Point(a.view.getRoutingCenterX(b), a.view.getRoutingCenterY(b)) : null != l && (l = l.clone());
		var m = f.length - 1;
		if (null != d && 0 < d.length) {
			for (var k = a.view.transformControlPoint(a, d[0]), n = b, p = f[0], q = !1, r = !1, q = k, s = d.length, t = 0; 2 > t; t++) {
				var u = null != p && p.x == q.x,
					x = null != p && p.y == q.y,
					y = null != n && q.y >= n.y && q.y <= n.y + n.height,
					n = null != n && q.x >= n.x && q.x <= n.x + n.width,
					q = x || null == p && y,
					r = u || null == p && n;
				if (null != p && !x && !u && (y || n)) {
					g = y ? !1 : !0;
					break
				}
				if (r || q) {
					g = q;
					1 == t && (g = 0 == d.length % 2 ? q : r);
					break
				}
				n = c;
				p = f[m];
				q = a.view.transformControlPoint(a, d[s - 1])
			}
			g && (null != f[0] && f[0].y != k.y || null == f[0] && null != b && (k.y < b.y || k.y > b.y + b.height)) ? e.push(new Point(l.x, k.y)) : !g && (null != f[0] && f[0].x != k.x || null == f[0] && null != b && (k.x < b.x || k.x > b.x + b.width)) && e.push(new Point(k.x, l.y));
			g ? l.y = k.y : l.x = k.x;
			for (t = 0; t < d.length; t++) g = !g, k = a.view.transformControlPoint(a, d[t]), g ? l.y = k.y : l.x = k.x, e.push(l.clone())
		} else k = l, g = !0;
		l = f[m];
		null == l && null != c && (l = new Point(a.view.getRoutingCenterX(c), a.view.getRoutingCenterY(c)));
		g && (null != f[m] && f[m].y != k.y || null == f[m] && null != c && (k.y < c.y || k.y > c.y + c.height)) ? e.push(new Point(l.x, k.y)) : !g && (null != f[m] && f[m].x != k.x || null == f[m] && null != c && (k.x < c.x || k.x > c.x + c.width)) && e.push(new Point(k.x, l.y));
		if (null == f[0] && null != b) for (; 1 < e.length && Utils.contains(b, e[1].x, e[1].y);) e = e.splice(1, 1);
		if (null == f[m] && null != c) for (; 1 < e.length && Utils.contains(c, e[e.length - 1].x, e[e.length - 1].y);) e = e.splice(e.length - 1, 1)
	},
	orthBuffer: 10,
	orthPointsFallback: !0,
	dirVectors: [
		[-1, 0],
		[0, -1],
		[1, 0],
		[0, 1],
		[-1, 0],
		[0, -1],
		[1, 0]
	],
	wayPoints1: [
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0],
		[0, 0]
	],
	routePatterns: [
		[
			[513, 2308, 2081, 2562],
			[513, 1090, 514, 2184, 2114, 2561],
			[513, 1090, 514, 2564, 2184, 2562],
			[513, 2308, 2561, 1090, 514, 2568, 2308]
		],
		[
			[514, 1057, 513, 2308, 2081, 2562],
			[514, 2184, 2114, 2561],
			[514, 2184, 2562, 1057, 513, 2564, 2184],
			[514, 1057, 513, 2568, 2308, 2561]
		],
		[
			[1090, 514, 1057, 513, 2308, 2081, 2562],
			[2114, 2561],
			[1090, 2562, 1057, 513, 2564, 2184],
			[1090, 514, 1057, 513, 2308, 2561, 2568]
		],
		[
			[2081, 2562],
			[1057, 513, 1090, 514, 2184, 2114, 2561],
			[1057, 513, 1090, 514, 2184, 2562, 2564],
			[1057, 2561, 1090, 514, 2568, 2308]
		]
	],
	inlineRoutePatterns: [
		[null, [2114, 2568], null, null],
		[null, [514, 2081, 2114, 2568], null, null],
		[null, [2114, 2561], null, null],
		[
			[2081, 2562],
			[1057, 2114, 2568],
			[2184, 2562], null]
	],
	vertexSeperations: [],
	limits: [
		[0, 0, 0, 0, 0, 0, 0, 0, 0],
		[0, 0, 0, 0, 0, 0, 0, 0, 0]
	],
	LEFT_MASK: 32,
	TOP_MASK: 64,
	RIGHT_MASK: 128,
	BOTTOM_MASK: 256,
	LEFT: 1,
	TOP: 2,
	RIGHT: 4,
	BOTTOM: 8,
	SIDE_MASK: 480,
	CENTER_MASK: 512,
	SOURCE_MASK: 1024,
	TARGET_MASK: 2048,
	VERTEX_MASK: 3072,
	OrthConnector: function(a, b, c, d, e) {
		var f = a.view.graph,
			g = null == b ? !1 : f.getModel().isEdge(b.cell),
			f = null == c ? !1 : f.getModel().isEdge(c.cell);
		if (EdgeStyle.orthPointsFallback && null != d && 0 < d.length || g || f) EdgeStyle.SegmentConnector(a, b, c, d, e);
		else {
			d = a.absolutePoints;
			var k = d[0],
				l = d[d.length - 1];
			d = null != b ? b.x : k.x;
			var g = null != b ? b.y : k.y,
				m = null != b ? b.width : 1,
				n = null != b ? b.height : 1,
				p = null != c ? c.x : l.x,
				q = null != c ? c.y : l.y,
				r = null != c ? c.width : 1,
				s = null != c ? c.height : 1,
				f = a.view.scale * EdgeStyle.orthBuffer,
				t = [Constants.DIRECTION_MASK_ALL, Constants.DIRECTION_MASK_ALL],
				u = 0;
			null != b && (t[0] = Utils.getPortConstraints(b, a, !0, Constants.DIRECTION_MASK_ALL), u = Utils.getValue(b.style, Constants.STYLE_ROTATION, 0), 0 != u && (u = Utils.getBoundingBox(new Rectangle(d, g, m, n), u), d = u.x, g = u.y, m = u.width, n = u.height));
			null != c && (t[1] = Utils.getPortConstraints(c, a, !1, Constants.DIRECTION_MASK_ALL), u = Utils.getValue(c.style, Constants.STYLE_ROTATION, 0), 0 != u && (u = Utils.getBoundingBox(new Rectangle(p, q, r, s), u), p = u.x, q = u.y, r = u.width, s = u.height));
			a = [0, 0];
			d = [
				[d, g, m, n],
				[p, q, r, s]
			];
			for (m = 0; 2 > m; m++) EdgeStyle.limits[m][1] = d[m][0] - f, EdgeStyle.limits[m][2] = d[m][1] - f, EdgeStyle.limits[m][4] = d[m][0] + d[m][2] + f, EdgeStyle.limits[m][8] = d[m][1] + d[m][3] + f;
			m = d[0][0] + d[0][2] / 2 - (d[1][0] + d[1][2] / 2);
			n = d[0][1] + d[0][3] / 2 - (d[1][1] + d[1][3] / 2);
			g = 0;
			0 > m ? g = 0 > n ? 2 : 1 : 0 >= n && (g = 3, 0 == m && (g = 2));
			n = null;
			null != b && (n = k);
			b = [
				[0.5, 0.5],
				[0.5, 0.5]
			];
			for (m = 0; 2 > m; m++) null != n && (b[m][0] = (n.x - d[m][0]) / d[m][2], 0.01 > b[m][0] ? a[m] = Constants.DIRECTION_MASK_WEST : 0.99 < b[m][0] && (a[m] = Constants.DIRECTION_MASK_EAST), b[m][1] = (n.y - d[m][1]) / d[m][3], 0.01 > b[m][1] ? a[m] = Constants.DIRECTION_MASK_NORTH : 0.99 < b[m][1] && (a[m] = Constants.DIRECTION_MASK_SOUTH)), n = null, null != c && (n = l);
			m = d[0][1] - (d[1][1] + d[1][3]);
			n = d[0][0] - (d[1][0] + d[1][2]);
			p = d[1][1] - (d[0][1] + d[0][3]);
			q = d[1][0] - (d[0][0] + d[0][2]);
			EdgeStyle.vertexSeperations[1] = Math.max(n - 2 * f, 0);
			EdgeStyle.vertexSeperations[2] = Math.max(m - 2 * f, 0);
			EdgeStyle.vertexSeperations[4] = Math.max(p - 2 * f, 0);
			EdgeStyle.vertexSeperations[3] = Math.max(q - 2 * f, 0);
			c = [];
			k = [];
			l = [];
			k[0] = n >= q ? Constants.DIRECTION_MASK_WEST : Constants.DIRECTION_MASK_EAST;
			l[0] = m >= p ? Constants.DIRECTION_MASK_NORTH : Constants.DIRECTION_MASK_SOUTH;
			k[1] = Utils.reversePortConstraints(k[0]);
			l[1] = Utils.reversePortConstraints(l[0]);
			n = n >= q ? n : q;
			p = m >= p ? m : p;
			q = [
				[0, 0],
				[0, 0]
			];
			r = !1;
			for (m = 0; 2 > m; m++) 0 == a[m] && (0 == (k[m] & t[m]) && (k[m] = Utils.reversePortConstraints(k[m])), 0 == (l[m] & t[m]) && (l[m] = Utils.reversePortConstraints(l[m])), q[m][0] = l[m], q[m][1] = k[m]);
			p > 2 * f && n > 2 * f && (0 < (k[0] & t[0]) && 0 < (l[1] & t[1]) ? (q[0][0] = k[0], q[0][1] = l[0], q[1][0] = l[1], q[1][1] = k[1], r = !0) : 0 < (l[0] & t[0]) && 0 < (k[1] & t[1]) && (q[0][0] = l[0], q[0][1] = k[0], q[1][0] = k[1], q[1][1] = l[1], r = !0));
			p > 2 * f && !r && (q[0][0] = l[0], q[0][1] = k[0], q[1][0] = l[1], q[1][1] = k[1], r = !0);
			n > 2 * f && !r && (q[0][0] = k[0], q[0][1] = l[0], q[1][0] = k[1], q[1][1] = l[1]);
			for (m = 0; 2 > m; m++) if (0 == a[m] && (0 == (q[m][0] & t[m]) && (q[m][0] = q[m][1]), c[m] = q[m][0] & t[m], c[m] |= (q[m][1] & t[m]) << 8, c[m] |= (q[1 - m][m] & t[m]) << 16, c[m] |= (q[1 - m][1 - m] & t[m]) << 24, 0 == (c[m] & 15) && (c[m] <<= 8), 0 == (c[m] & 3840) && (c[m] = c[m] & 15 | c[m] >> 8), 0 == (c[m] & 983040) && (c[m] = c[m] & 65535 | (c[m] & 251658240) >> 8), a[m] = c[m] & 15, t[m] == Constants.DIRECTION_MASK_WEST || t[m] == Constants.DIRECTION_MASK_NORTH || t[m] == Constants.DIRECTION_MASK_EAST || t[m] == Constants.DIRECTION_MASK_SOUTH)) a[m] = t[m];
			t = a[0] == Constants.DIRECTION_MASK_EAST ? 3 : a[0];
			m = a[1] == Constants.DIRECTION_MASK_EAST ? 3 : a[1];
			t -= g;
			m -= g;
			1 > t && (t += 4);
			1 > m && (m += 4);
			t = EdgeStyle.routePatterns[t - 1][m - 1];
			EdgeStyle.wayPoints1[0][0] = d[0][0];
			EdgeStyle.wayPoints1[0][1] = d[0][1];
			switch (a[0]) {
			case Constants.DIRECTION_MASK_WEST:
				EdgeStyle.wayPoints1[0][0] -= f;
				EdgeStyle.wayPoints1[0][1] += b[0][1] * d[0][3];
				break;
			case Constants.DIRECTION_MASK_SOUTH:
				EdgeStyle.wayPoints1[0][0] += b[0][0] * d[0][2];
				EdgeStyle.wayPoints1[0][1] += d[0][3] + f;
				break;
			case Constants.DIRECTION_MASK_EAST:
				EdgeStyle.wayPoints1[0][0] += d[0][2] + f;
				EdgeStyle.wayPoints1[0][1] += b[0][1] * d[0][3];
				break;
			case Constants.DIRECTION_MASK_NORTH:
				EdgeStyle.wayPoints1[0][0] += b[0][0] * d[0][2], EdgeStyle.wayPoints1[0][1] -= f
			}
			f = 0;
			k = c = 0 < (a[0] & (Constants.DIRECTION_MASK_EAST | Constants.DIRECTION_MASK_WEST)) ? 0 : 1;
			for (m = l = 0; m < t.length; m++) l = t[m] & 15, s = l == Constants.DIRECTION_MASK_EAST ? 3 : l, s += g, 4 < s && (s -= 4), n = EdgeStyle.dirVectors[s - 1], l = 0 < s % 2 ? 0 : 1, l != c && (f++, EdgeStyle.wayPoints1[f][0] = EdgeStyle.wayPoints1[f - 1][0], EdgeStyle.wayPoints1[f][1] = EdgeStyle.wayPoints1[f - 1][1]), u = 0 < (t[m] & EdgeStyle.TARGET_MASK), r = 0 < (t[m] & EdgeStyle.SOURCE_MASK), p = (t[m] & EdgeStyle.SIDE_MASK) >> 5, p <<= g, 15 < p && (p >>= 4), q = 0 < (t[m] & EdgeStyle.CENTER_MASK), (r || u) && 9 > p ? (s = 0, r = r ? 0 : 1, s = q && 0 == l ? d[r][0] + b[r][0] * d[r][2] : q ? d[r][1] + b[r][1] * d[r][3] : EdgeStyle.limits[r][p], 0 == l ? (p = (s - EdgeStyle.wayPoints1[f][0]) * n[0], 0 < p && (EdgeStyle.wayPoints1[f][0] += n[0] * p)) : (p = (s - EdgeStyle.wayPoints1[f][1]) * n[1], 0 < p && (EdgeStyle.wayPoints1[f][1] += n[1] * p))) : q && (EdgeStyle.wayPoints1[f][0] += n[0] * Math.abs(EdgeStyle.vertexSeperations[s] / 2), EdgeStyle.wayPoints1[f][1] += n[1] * Math.abs(EdgeStyle.vertexSeperations[s] / 2)), 0 < f && EdgeStyle.wayPoints1[f][l] == EdgeStyle.wayPoints1[f - 1][l] ? f-- : c = l;
			for (m = 0; m <= f && !(m == f && ((0 < (a[1] & (Constants.DIRECTION_MASK_EAST | Constants.DIRECTION_MASK_WEST)) ? 0 : 1) == k ? 0 : 1) != (f + 1) % 2); m++) e.push(new Point(EdgeStyle.wayPoints1[m][0], EdgeStyle.wayPoints1[m][1]))
		}
	},
	getRoutePattern: function(a, b, c, d) {
		var e = a[0] == Constants.DIRECTION_MASK_EAST ? 3 : a[0];
		a = a[1] == Constants.DIRECTION_MASK_EAST ? 3 : a[1];
		e -= b;
		a -= b;
		1 > e && (e += 4);
		1 > a && (a += 4);
		b = routePatterns[e - 1][a - 1];
		if (0 == c || 0 == d) null != inlineRoutePatterns[e - 1][a - 1] && (b = inlineRoutePatterns[e - 1][a - 1]);
		return b
	}
},
	StyleRegistry = {
		values: [],
		putValue: function(a, b) {
			StyleRegistry.values[a] = b
		},
		getValue: function(a) {
			return StyleRegistry.values[a]
		},
		getName: function(a) {
			for (var b in StyleRegistry.values) if (StyleRegistry.values[b] == a) return b;
			return null
		}
	};
StyleRegistry.putValue(Constants.EDGESTYLE_ELBOW, EdgeStyle.ElbowConnector);
StyleRegistry.putValue(Constants.EDGESTYLE_ENTITY_RELATION, EdgeStyle.EntityRelation);
StyleRegistry.putValue(Constants.EDGESTYLE_LOOP, EdgeStyle.Loop);
StyleRegistry.putValue(Constants.EDGESTYLE_SIDETOSIDE, EdgeStyle.SideToSide);
StyleRegistry.putValue(Constants.EDGESTYLE_TOPTOBOTTOM, EdgeStyle.TopToBottom);
StyleRegistry.putValue(Constants.EDGESTYLE_ORTHOGONAL, EdgeStyle.OrthConnector);
StyleRegistry.putValue(Constants.EDGESTYLE_SEGMENT, EdgeStyle.SegmentConnector);
StyleRegistry.putValue(Constants.PERIMETER_ELLIPSE, Perimeter.EllipsePerimeter);
StyleRegistry.putValue(Constants.PERIMETER_RECTANGLE, Perimeter.RectanglePerimeter);
StyleRegistry.putValue(Constants.PERIMETER_RHOMBUS, Perimeter.RhombusPerimeter);
StyleRegistry.putValue(Constants.PERIMETER_TRIANGLE, Perimeter.TrianglePerimeter);
StyleRegistry.putValue(Constants.PERIMETER_HEXAGON, Perimeter.HexagonPerimeter);

function GraphView(a) {
	this.graph = a;
	this.translate = new Point;
	this.graphBounds = new Rectangle;
	this.states = new Dictionary
}
GraphView.prototype = new EventSource;
GraphView.prototype.constructor = GraphView;
GraphView.prototype.EMPTY_POINT = new Point;
GraphView.prototype.doneResource = "none" != Client.language ? "done" : "";
GraphView.prototype.updatingDocumentResource = "none" != Client.language ? "updatingDocument" : "";
GraphView.prototype.allowEval = !1;
GraphView.prototype.captureDocumentGesture = !0;
GraphView.prototype.optimizeVmlReflows = !0;
GraphView.prototype.rendering = !0;
GraphView.prototype.graph = null;
GraphView.prototype.currentRoot = null;
GraphView.prototype.graphBounds = null;
GraphView.prototype.scale = 1;
GraphView.prototype.translate = null;
GraphView.prototype.updateStyle = !1;
GraphView.prototype.lastNode = null;
GraphView.prototype.lastHtmlNode = null;
GraphView.prototype.lastForegroundNode = null;
GraphView.prototype.lastForegroundHtmlNode = null;
GraphView.prototype.getGraphBounds = function() {
	return this.graphBounds
};
GraphView.prototype.setGraphBounds = function(a) {
	this.graphBounds = a
};
GraphView.prototype.getBounds = function(a) {
	var b = null;
	if (null != a && 0 < a.length) for (var c = this.graph.getModel(), d = 0; d < a.length; d++) if (c.isVertex(a[d]) || c.isEdge(a[d])) {
		var e = this.getState(a[d]);
		null != e && (null == b ? b = new Rectangle(e.x, e.y, e.width, e.height) : b.add(e))
	}
	return b
};
GraphView.prototype.setCurrentRoot = function(a) {
	if (this.currentRoot != a) {
		var b = new CurrentRootChange(this, a);
		b.execute();
		var c = new UndoableEdit(this, !1);
		c.add(b);
		this.fireEvent(new EventObject(Event.UNDO, "edit", c));
		this.graph.sizeDidChange()
	}
	return a
};
GraphView.prototype.scaleAndTranslate = function(a, b, c) {
	var d = this.scale,
		e = new Point(this.translate.x, this.translate.y);
	if (this.scale != a || this.translate.x != b || this.translate.y != c) this.scale = a, this.translate.x = b, this.translate.y = c, this.isEventsEnabled() && (this.revalidate(), this.graph.sizeDidChange());
	this.fireEvent(new EventObject(Event.SCALE_AND_TRANSLATE, "scale", a, "previousScale", d, "translate", this.translate, "previousTranslate", e))
};
GraphView.prototype.getScale = function() {
	return this.scale
};
GraphView.prototype.setScale = function(a) {
	var b = this.scale;
	this.scale != a && (this.scale = a, this.isEventsEnabled() && (this.revalidate(), this.graph.sizeDidChange()));
	this.fireEvent(new EventObject(Event.SCALE, "scale", a, "previousScale", b))
};
GraphView.prototype.getTranslate = function() {
	return this.translate
};
GraphView.prototype.setTranslate = function(a, b) {
	var c = new Point(this.translate.x, this.translate.y);
	if (this.translate.x != a || this.translate.y != b) this.translate.x = a, this.translate.y = b, this.isEventsEnabled() && (this.revalidate(), this.graph.sizeDidChange());
	this.fireEvent(new EventObject(Event.TRANSLATE, "translate", this.translate, "previousTranslate", c))
};
GraphView.prototype.refresh = function() {
	null != this.currentRoot && this.clear();
	this.revalidate()
};
GraphView.prototype.revalidate = function() {
	this.invalidate();
	this.validate()
};
GraphView.prototype.clear = function(a, b, c) {
	var d = this.graph.getModel();
	a = a || d.getRoot();
	b = null != b ? b : !1;
	c = null != c ? c : !0;
	this.removeState(a);
	if (c && (b || a != this.currentRoot)) {
		c = d.getChildCount(a);
		for (var e = 0; e < c; e++) this.clear(d.getChildAt(a, e), b)
	} else this.invalidate(a)
};
GraphView.prototype.invalidate = function(a, b, c) {
	var d = this.graph.getModel();
	a = a || d.getRoot();
	b = null != b ? b : !0;
	c = null != c ? c : !0;
	var e = this.getState(a);
	null != e && (e.invalid = !0);
	if (!a.invalidating) {
		a.invalidating = !0;
		if (b) for (var f = d.getChildCount(a), e = 0; e < f; e++) {
			var g = d.getChildAt(a, e);
			this.invalidate(g, b, c)
		}
		if (c) {
			f = d.getEdgeCount(a);
			for (e = 0; e < f; e++) this.invalidate(d.getEdgeAt(a, e), b, c)
		}
		delete a.invalidating
	}
};
GraphView.prototype.validate = function(a) {
	var b = Log.enter("GraphView.validate");
	window.status = Resources.get(this.updatingDocumentResource) || this.updatingDocumentResource;
	this.resetValidationState();
	var c = null;
	if (this.optimizeVmlReflows && null != this.canvas && null == this.textDiv && (8 == document.documentMode || Client.IS_QUIRKS)) this.placeholder = document.createElement("div"), this.placeholder.style.position = "absolute", this.placeholder.style.width = this.canvas.clientWidth + "px", this.placeholder.style.height = this.canvas.clientHeight + "px", this.canvas.parentNode.appendChild(this.placeholder), c = this.drawPane.style.display, this.canvas.style.display = "none", this.textDiv = document.createElement("div"), this.textDiv.style.position = "absolute", this.textDiv.style.whiteSpace = "nowrap", this.textDiv.style.visibility = "hidden", this.textDiv.style.display = Client.IS_QUIRKS ? "inline" : "inline-block", this.textDiv.style.zoom = "1", document.body.appendChild(this.textDiv);
	a = this.getBoundingBox(this.validateCellState(this.validateCell(a || (null != this.currentRoot ? this.currentRoot : this.graph.getModel().getRoot()))));
	this.setGraphBounds(null != a ? a : this.getEmptyBounds());
	this.validateBackground();
	null != c && (this.canvas.style.display = c, this.placeholder.parentNode.removeChild(this.placeholder), this.textDiv.parentNode.removeChild(this.textDiv), this.textDiv = null);
	this.resetValidationState();
	window.status = Resources.get(this.doneResource) || this.doneResource;
	Log.leave("GraphView.validate", b)
};
GraphView.prototype.getEmptyBounds = function() {
	return new Rectangle(this.translate.x * this.scale, this.translate.y * this.scale)
};
GraphView.prototype.getBoundingBox = function(a, b) {
	b = null != b ? b : !0;
	var c = null;
	if (null != a && (null != a.shape && null != a.shape.boundingBox && (c = a.shape.boundingBox.clone()), null != a.text && null != a.text.boundingBox && (null != c ? c.add(a.text.boundingBox) : c = a.text.boundingBox.clone()), b)) for (var d = this.graph.getModel(), e = d.getChildCount(a.cell), f = 0; f < e; f++) {
		var g = this.getBoundingBox(this.getState(d.getChildAt(a.cell, f)));
		null != g && (null == c ? c = g : c.add(g))
	}
	return c
};
GraphView.prototype.createBackgroundPageShape = function(a) {
	return new RectangleShape(a, "white", "black")
};
GraphView.prototype.validateBackground = function() {
	this.validateBackgroundImage();
	this.validateBackgroundPage()
};
GraphView.prototype.validateBackgroundImage = function() {
	var a = this.graph.getBackgroundImage();
	if (null != a) {
		if (null == this.backgroundImage || this.backgroundImage.image != a.src) {
			null != this.backgroundImage && this.backgroundImage.destroy();
			var b = new Rectangle(0, 0, 1, 1);
			this.backgroundImage = new ImageShape(b, a.src);
			this.backgroundImage.dialect = this.graph.dialect;
			this.backgroundImage.init(this.backgroundPane);
			this.backgroundImage.redraw();
			8 == document.documentMode && Event.addGestureListeners(this.backgroundImage.node, Utils.bind(this, function(a) {
				this.graph.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(a))
			}), Utils.bind(this, function(a) {
				this.graph.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(a))
			}), Utils.bind(this, function(a) {
				this.graph.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(a))
			}))
		}
		this.redrawBackgroundImage(this.backgroundImage, a)
	} else null != this.backgroundImage && (this.backgroundImage.destroy(), this.backgroundImage = null)
};
GraphView.prototype.validateBackgroundPage = function() {
	if (this.graph.pageVisible) {
		var a = this.getBackgroundPageBounds();
		null == this.backgroundPageShape ? (this.backgroundPageShape = this.createBackgroundPageShape(a), this.backgroundPageShape.scale = this.scale, this.backgroundPageShape.isShadow = !0, this.backgroundPageShape.dialect = this.graph.dialect, this.backgroundPageShape.init(this.backgroundPane), this.backgroundPageShape.redraw(), this.graph.nativeDblClickEnabled && Event.addListener(this.backgroundPageShape.node, "dblclick", Utils.bind(this, function(a) {
			this.graph.dblClick(a)
		})), Event.addGestureListeners(this.backgroundPageShape.node, Utils.bind(this, function(a) {
			this.graph.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(a))
		}), Utils.bind(this, function(a) {
			null != this.graph.tooltipHandler && this.graph.tooltipHandler.isHideOnHover() && this.graph.tooltipHandler.hide();
			this.graph.isMouseDown && !Event.isConsumed(a) && this.graph.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(a))
		}), Utils.bind(this, function(a) {
			this.graph.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(a))
		}))) : (this.backgroundPageShape.scale = this.scale, this.backgroundPageShape.bounds = a, this.backgroundPageShape.redraw())
	} else null != this.backgroundPageShape && (this.backgroundPageShape.destroy(), this.backgroundPageShape = null)
};
GraphView.prototype.getBackgroundPageBounds = function() {
	var a = this.graph.pageFormat,
		b = this.scale * this.graph.pageScale;
	return new Rectangle(this.scale * this.translate.x, this.scale * this.translate.y, a.width * b, a.height * b)
};
GraphView.prototype.redrawBackgroundImage = function(a, b) {
	a.scale = this.scale;
	a.bounds.x = this.scale * this.translate.x;
	a.bounds.y = this.scale * this.translate.y;
	a.bounds.width = this.scale * b.width;
	a.bounds.height = this.scale * b.height;
	a.redraw()
};
GraphView.prototype.validateCell = function(a, b) {
	if (null != a) if (b = (null != b ? b : !0) && this.graph.isCellVisible(a), null != this.getState(a, b) && !b) this.removeState(a);
	else for (var c = this.graph.getModel(), d = c.getChildCount(a), e = 0; e < d; e++) this.validateCell(c.getChildAt(a, e), b && (!this.isCellCollapsed(a) || a == this.currentRoot));
	return a
};
GraphView.prototype.validateCellState = function(a, b) {
	b = null != b ? b : !0;
	var c = null;
	if (null != a && (c = this.getState(a), null != c)) {
		var d = this.graph.getModel();
		c.invalid && (c.invalid = !1, a != this.currentRoot && this.validateCellState(d.getParent(a), !1), c.setVisibleTerminalState(this.validateCellState(this.getVisibleTerminal(a, !0), !1), !0), c.setVisibleTerminalState(this.validateCellState(this.getVisibleTerminal(a, !1), !1), !1), this.updateCellState(c), a != this.currentRoot && this.graph.cellRenderer.redraw(c, !1, this.isRendering()));
		if (b) {
			null != c.shape && this.stateValidated(c);
			for (var e = d.getChildCount(a), f = 0; f < e; f++) this.validateCellState(d.getChildAt(a, f))
		}
	}
	return c
};
GraphView.prototype.updateCellState = function(a) {
	a.absoluteOffset.x = 0;
	a.absoluteOffset.y = 0;
	a.origin.x = 0;
	a.origin.y = 0;
	a.length = 0;
	if (a.cell != this.currentRoot) {
		var b = this.graph.getModel(),
			c = this.getState(b.getParent(a.cell));
		null != c && c.cell != this.currentRoot && (a.origin.x += c.origin.x, a.origin.y += c.origin.y);
		var d = this.graph.getChildOffsetForCell(a.cell);
		null != d && (a.origin.x += d.x, a.origin.y += d.y);
		var e = this.graph.getCellGeometry(a.cell);
		null != e && (b.isEdge(a.cell) || (d = e.offset || this.EMPTY_POINT, e.relative && null != c ? b.isEdge(c.cell) ? (c = this.getPoint(c, e), null != c && (a.origin.x += c.x / this.scale - this.translate.x, a.origin.y += c.y / this.scale - this.translate.y)) : (a.origin.x += e.x * c.width / this.scale + d.x, a.origin.y += e.y * c.height / this.scale + d.y) : (a.absoluteOffset.x = this.scale * d.x, a.absoluteOffset.y = this.scale * d.y, a.origin.x += e.x, a.origin.y += e.y)), a.x = this.scale * (this.translate.x + a.origin.x), a.y = this.scale * (this.translate.y + a.origin.y), a.width = this.scale * e.width, a.height = this.scale * e.height, b.isVertex(a.cell) && this.updateVertexState(a, e), b.isEdge(a.cell) && this.updateEdgeState(a, e))
	}
};
GraphView.prototype.isCellCollapsed = function(a) {
	return this.graph.isCellCollapsed(a)
};
GraphView.prototype.updateVertexState = function(a, b) {
	var c = this.graph.getModel(),
		d = this.getState(c.getParent(a.cell));
	if (b.relative && null != d) {
		var e = Utils.toRadians(d.style[Constants.STYLE_ROTATION] || "0");
		if (0 != e) {
			var c = Math.cos(e),
				e = Math.sin(e),
				f = new Point(a.getCenterX(), a.getCenterY()),
				d = new Point(d.getCenterX(), d.getCenterY()),
				c = Utils.getRotatedPoint(f, c, e, d);
			a.x = c.x - a.width / 2;
			a.y = c.y - a.height / 2
		}
	}
	this.updateVertexLabelOffset(a)
};
GraphView.prototype.updateEdgeState = function(a, b) {
	var c = a.getVisibleTerminalState(!0),
		d = a.getVisibleTerminalState(!1);
	null != this.graph.model.getTerminal(a.cell, !0) && null == c || null == c && null == b.getTerminalPoint(!0) || null != this.graph.model.getTerminal(a.cell, !1) && null == d || null == d && null == b.getTerminalPoint(!1) ? this.clear(a.cell, !0) : (this.updateFixedTerminalPoints(a, c, d), this.updatePoints(a, b.points, c, d), this.updateFloatingTerminalPoints(a, c, d), c = a.absolutePoints, a.cell != this.currentRoot && (null == c || 2 > c.length || null == c[0] || null == c[c.length - 1]) ? this.clear(a.cell, !0) : (this.updateEdgeBounds(a), this.updateEdgeLabelOffset(a)))
};
GraphView.prototype.updateVertexLabelOffset = function(a) {
	var b = Utils.getValue(a.style, Constants.STYLE_LABEL_POSITION, Constants.ALIGN_CENTER);
	if (b == Constants.ALIGN_LEFT) b = Utils.getValue(a.style, Constants.STYLE_LABEL_WIDTH, null), b = null != b ? b * this.scale : a.width, a.absoluteOffset.x -= b;
	else if (b == Constants.ALIGN_RIGHT) a.absoluteOffset.x += a.width;
	else if (b == Constants.ALIGN_CENTER && (b = Utils.getValue(a.style, Constants.STYLE_LABEL_WIDTH, null), null != b)) {
		var c = Utils.getValue(a.style, Constants.STYLE_ALIGN, Constants.ALIGN_CENTER),
			d = 0;
		c == Constants.ALIGN_CENTER ? d = 0.5 : c == Constants.ALIGN_RIGHT && (d = 1);
		0 != d && (a.absoluteOffset.x -= (b * this.scale - a.width) * d)
	}
	b = Utils.getValue(a.style, Constants.STYLE_VERTICAL_LABEL_POSITION, Constants.ALIGN_MIDDLE);
	b == Constants.ALIGN_TOP ? a.absoluteOffset.y -= a.height : b == Constants.ALIGN_BOTTOM && (a.absoluteOffset.y += a.height)
};
GraphView.prototype.resetValidationState = function() {
	this.lastForegroundHtmlNode = this.lastForegroundNode = this.lastHtmlNode = this.lastNode = null
};
GraphView.prototype.stateValidated = function(a) {
	var b = this.graph.getModel().isEdge(a.cell) && this.graph.keepEdgesInForeground || this.graph.getModel().isVertex(a.cell) && this.graph.keepEdgesInBackground;
	a = this.graph.cellRenderer.insertStateAfter(a, b ? this.lastForegroundNode || this.lastNode : this.lastNode, b ? this.lastForegroundHtmlNode || this.lastHtmlNode : this.lastHtmlNode);
	b ? (this.lastForegroundHtmlNode = a[1], this.lastForegroundNode = a[0]) : (this.lastHtmlNode = a[1], this.lastNode = a[0])
};
GraphView.prototype.updateFixedTerminalPoints = function(a, b, c) {
	this.updateFixedTerminalPoint(a, b, !0, this.graph.getConnectionConstraint(a, b, !0));
	this.updateFixedTerminalPoint(a, c, !1, this.graph.getConnectionConstraint(a, c, !1))
};
GraphView.prototype.updateFixedTerminalPoint = function(a, b, c, d) {
	var e = null;
	null != d && (e = this.graph.getConnectionPoint(b, d));
	if (null == e && null == b) {
		b = this.scale;
		d = this.translate;
		var f = a.origin,
			e = this.graph.getCellGeometry(a.cell).getTerminalPoint(c);
		null != e && (e = new Point(b * (d.x + e.x + f.x), b * (d.y + e.y + f.y)))
	}
	a.setAbsoluteTerminalPoint(e, c)
};
GraphView.prototype.updatePoints = function(a, b, c, d) {
	if (null != a) {
		var e = [];
		e.push(a.absolutePoints[0]);
		var f = this.getEdgeStyle(a, b, c, d);
		if (null != f) c = this.getTerminalPort(a, c, !0), d = this.getTerminalPort(a, d, !1), f(a, c, d, b, e);
		else if (null != b) for (f = 0; f < b.length; f++) null != b[f] && (d = Utils.clone(b[f]), e.push(this.transformControlPoint(a, d)));
		b = a.absolutePoints;
		e.push(b[b.length - 1]);
		a.absolutePoints = e
	}
};
GraphView.prototype.transformControlPoint = function(a, b) {
	var c = a.origin;
	return new Point(this.scale * (b.x + this.translate.x + c.x), this.scale * (b.y + this.translate.y + c.y))
};
GraphView.prototype.getEdgeStyle = function(a, b, c, d) {
	a = null != c && c == d ? Utils.getValue(a.style, Constants.STYLE_LOOP, this.graph.defaultLoopStyle) : !Utils.getValue(a.style, Constants.STYLE_NOEDGESTYLE, !1) ? a.style[Constants.STYLE_EDGE] : null;
	"string" == typeof a && (b = StyleRegistry.getValue(a), null == b && this.isAllowEval() && (b = Utils.eval(a)), a = b);
	return "function" == typeof a ? a : null
};
GraphView.prototype.updateFloatingTerminalPoints = function(a, b, c) {
	var d = a.absolutePoints,
		e = d[0];
	null == d[d.length - 1] && null != c && this.updateFloatingTerminalPoint(a, c, b, !1);
	null == e && null != b && this.updateFloatingTerminalPoint(a, b, c, !0)
};
GraphView.prototype.updateFloatingTerminalPoint = function(a, b, c, d) {
	b = this.getTerminalPort(a, b, d);
	var e = this.getNextPoint(a, c, d),
		f = this.graph.isOrthogonal(a);
	c = Utils.toRadians(Number(b.style[Constants.STYLE_ROTATION] || "0"));
	var g = new Point(b.getCenterX(), b.getCenterY());
	if (0 != c) var k = Math.cos(-c),
		l = Math.sin(-c),
		e = Utils.getRotatedPoint(e, k, l, g);
	k = parseFloat(a.style[Constants.STYLE_PERIMETER_SPACING] || 0);
	k += parseFloat(a.style[d ? Constants.STYLE_SOURCE_PERIMETER_SPACING : Constants.STYLE_TARGET_PERIMETER_SPACING] || 0);
	b = this.getPerimeterPoint(b, e, 0 == c && f, k);
	0 != c && (k = Math.cos(c), l = Math.sin(c), b = Utils.getRotatedPoint(b, k, l, g));
	a.setAbsoluteTerminalPoint(b, d)
};
GraphView.prototype.getTerminalPort = function(a, b, c) {
	a = Utils.getValue(a.style, c ? Constants.STYLE_SOURCE_PORT : Constants.STYLE_TARGET_PORT);
	null != a && (a = this.getState(this.graph.getModel().getCell(a)), null != a && (b = a));
	return b
};
GraphView.prototype.getPerimeterPoint = function(a, b, c, d) {
	var e = null;
	if (null != a) {
		var f = this.getPerimeterFunction(a);
		if (null != f && null != b && (d = this.getPerimeterBounds(a, d), 0 < d.width || 0 < d.height)) e = f(d, a, b, c);
		null == e && (e = this.getPoint(a))
	}
	return e
};
GraphView.prototype.getRoutingCenterX = function(a) {
	var b = null != a.style ? parseFloat(a.style[Constants.STYLE_ROUTING_CENTER_X]) || 0 : 0;
	return a.getCenterX() + b * a.width
};
GraphView.prototype.getRoutingCenterY = function(a) {
	var b = null != a.style ? parseFloat(a.style[Constants.STYLE_ROUTING_CENTER_Y]) || 0 : 0;
	return a.getCenterY() + b * a.height
};
GraphView.prototype.getPerimeterBounds = function(a, b) {
	b = null != b ? b : 0;
	null != a && (b += parseFloat(a.style[Constants.STYLE_PERIMETER_SPACING] || 0));
	return a.getPerimeterBounds(b * this.scale)
};
GraphView.prototype.getPerimeterFunction = function(a) {
	a = a.style[Constants.STYLE_PERIMETER];
	if ("string" == typeof a) {
		var b = StyleRegistry.getValue(a);
		null == b && this.isAllowEval() && (b = Utils.eval(a));
		a = b
	}
	return "function" == typeof a ? a : null
};
GraphView.prototype.getNextPoint = function(a, b, c) {
	a = a.absolutePoints;
	var d = null;
	if (null != a && (c || 2 < a.length || null == b)) d = a.length, d = a[c ? Math.min(1, d - 1) : Math.max(0, d - 2)];
	null == d && null != b && (d = new Point(b.getCenterX(), b.getCenterY()));
	return d
};
GraphView.prototype.getVisibleTerminal = function(a, b) {
	for (var c = this.graph.getModel(), d = c.getTerminal(a, b), e = d; null != d && d != this.currentRoot;) {
		if (!this.graph.isCellVisible(e) || this.isCellCollapsed(d)) e = d;
		d = c.getParent(d)
	}
	c.getParent(e) == c.getRoot() && (e = null);
	return e
};
GraphView.prototype.updateEdgeBounds = function(a) {
	var b = a.absolutePoints,
		c = b[0],
		d = b[b.length - 1];
	if (c.x != d.x || c.y != d.y) {
		var e = d.x - c.x,
			f = d.y - c.y;
		a.terminalDistance = Math.sqrt(e * e + f * f)
	} else a.terminalDistance = 0;
	var d = 0,
		g = [],
		f = c;
	if (null != f) {
		for (var c = f.x, k = f.y, l = c, m = k, n = 1; n < b.length; n++) {
			var p = b[n];
			null != p && (e = f.x - p.x, f = f.y - p.y, e = Math.sqrt(e * e + f * f), g.push(e), d += e, f = p, c = Math.min(f.x, c), k = Math.min(f.y, k), l = Math.max(f.x, l), m = Math.max(f.y, m))
		}
		a.length = d;
		a.segments = g;
		a.x = c;
		a.y = k;
		a.width = Math.max(1, l - c);
		a.height = Math.max(1, m - k)
	}
};
GraphView.prototype.getPoint = function(a, b) {
	var c = a.getCenterX(),
		d = a.getCenterY();
	if (null != a.segments && (null == b || b.relative)) {
		for (var e = a.absolutePoints.length, f = ((null != b ? b.x / 2 : 0) + 0.5) * a.length, g = a.segments[0], k = 0, l = 1; f > k + g && l < e - 1;) k += g, g = a.segments[l++];
		e = 0 == g ? 0 : (f - k) / g;
		f = a.absolutePoints[l - 1];
		l = a.absolutePoints[l];
		if (null != f && null != l) {
			k = c = d = 0;
			if (null != b) {
				var d = b.y,
					m = b.offset;
				null != m && (c = m.x, k = m.y)
			}
			m = l.x - f.x;
			l = l.y - f.y;
			c = f.x + m * e + ((0 == g ? 0 : l / g) * d + c) * this.scale;
			d = f.y + l * e - ((0 == g ? 0 : m / g) * d - k) * this.scale
		}
	} else null != b && (m = b.offset, null != m && (c += m.x, d += m.y));
	return new Point(c, d)
};
GraphView.prototype.getRelativePoint = function(a, b, c) {
	var d = this.graph.getModel().getGeometry(a.cell);
	if (null != d) {
		var e = a.absolutePoints.length;
		if (d.relative && 1 < e) {
			for (var d = a.length, f = a.segments, g = a.absolutePoints[0], k = a.absolutePoints[1], l = Utils.ptSegDistSq(g.x, g.y, k.x, k.y, b, c), m = 0, n = 0, p = 0, q = 2; q < e; q++) n += f[q - 2], k = a.absolutePoints[q], g = Utils.ptSegDistSq(g.x, g.y, k.x, k.y, b, c), g <= l && (l = g, m = q - 1, p = n), g = k;
			e = f[m];
			g = a.absolutePoints[m];
			k = a.absolutePoints[m + 1];
			l = k.x;
			f = k.y;
			a = g.x - l;
			m = g.y - f;
			l = b - l;
			f = c - f;
			l = a - l;
			f = m - f;
			f = l * a + f * m;
			a = Math.sqrt(0 >= f ? 0 : f * f / (a * a + m * m));
			a > e && (a = e);
			e = Math.sqrt(Utils.ptSegDistSq(g.x, g.y, k.x, k.y, b, c)); - 1 == Utils.relativeCcw(g.x, g.y, k.x, k.y, b, c) && (e = -e);
			return new Point(-2 * ((d / 2 - p - a) / d), e / this.scale)
		}
	}
	return new Point
};
GraphView.prototype.updateEdgeLabelOffset = function(a) {
	var b = a.absolutePoints;
	a.absoluteOffset.x = a.getCenterX();
	a.absoluteOffset.y = a.getCenterY();
	if (null != b && 0 < b.length && null != a.segments) {
		var c = this.graph.getCellGeometry(a.cell);
		if (c.relative) {
			var d = this.getPoint(a, c);
			null != d && (a.absoluteOffset = d)
		} else {
			var d = b[0],
				e = b[b.length - 1];
			if (null != d && null != e) {
				var b = e.x - d.x,
					f = e.y - d.y,
					g = e = 0,
					c = c.offset;
				null != c && (e = c.x, g = c.y);
				c = d.y + f / 2 + g * this.scale;
				a.absoluteOffset.x = d.x + b / 2 + e * this.scale;
				a.absoluteOffset.y = c
			}
		}
	}
};
GraphView.prototype.getState = function(a, b) {
	b = b || !1;
	var c = null;
	if (null != a && (c = this.states.get(a), b && (null == c || this.updateStyle) && this.graph.isCellVisible(a))) null == c ? (c = this.createState(a), this.states.put(a, c)) : c.style = this.graph.getCellStyle(a);
	return c
};
GraphView.prototype.isRendering = function() {
	return this.rendering
};
GraphView.prototype.setRendering = function(a) {
	this.rendering = a
};
GraphView.prototype.isAllowEval = function() {
	return this.allowEval
};
GraphView.prototype.setAllowEval = function(a) {
	this.allowEval = a
};
GraphView.prototype.getStates = function() {
	return this.states
};
GraphView.prototype.setStates = function(a) {
	this.states = a
};
GraphView.prototype.getCellStates = function(a) {
	if (null == a) return this.states;
	for (var b = [], c = 0; c < a.length; c++) {
		var d = this.getState(a[c]);
		null != d && b.push(d)
	}
	return b
};
GraphView.prototype.removeState = function(a) {
	var b = null;
	null != a && (b = this.states.remove(a), null != b && (this.graph.cellRenderer.destroy(b), b.destroy()));
	return b
};
GraphView.prototype.createState = function(a) {
	a = new CellState(this, a, this.graph.getCellStyle(a));
	var b = this.graph.getModel();
	null != a.view.graph.container && (a.cell != a.view.currentRoot && (b.isVertex(a.cell) || b.isEdge(a.cell))) && this.graph.cellRenderer.createShape(a);
	return a
};
GraphView.prototype.getCanvas = function() {
	return this.canvas
};
GraphView.prototype.getBackgroundPane = function() {
	return this.backgroundPane
};
GraphView.prototype.getDrawPane = function() {
	return this.drawPane
};
GraphView.prototype.getOverlayPane = function() {
	return this.overlayPane
};
GraphView.prototype.getDecoratorPane = function() {
	return this.decoratorPane
};
GraphView.prototype.isContainerEvent = function(a) {
	a = Event.getSource(a);
	return a == this.graph.container || a.parentNode == this.backgroundPane || null != a.parentNode && a.parentNode.parentNode == this.backgroundPane || a == this.canvas.parentNode || a == this.canvas || a == this.backgroundPane || a == this.drawPane || a == this.overlayPane || a == this.decoratorPane
};
GraphView.prototype.isScrollEvent = function(a) {
	var b = Utils.getOffset(this.graph.container);
	a = new Point(a.clientX - b.x, a.clientY - b.y);
	var b = this.graph.container.offsetWidth,
		c = this.graph.container.clientWidth;
	if (b > c && a.x > c + 2 && a.x <= b) return !0;
	b = this.graph.container.offsetHeight;
	c = this.graph.container.clientHeight;
	return b > c && a.y > c + 2 && a.y <= b ? !0 : !1
};
GraphView.prototype.init = function() {
	this.installListeners();
	var a = this.graph;
	a.dialect == Constants.DIALECT_SVG ? this.createSvg() : a.dialect == Constants.DIALECT_VML ? this.createVml() : this.createHtml()
};
GraphView.prototype.installListeners = function() {
	var a = this.graph,
		b = a.container;
	if (null != b) {
		Client.IS_TOUCH && (Event.addListener(b, "gesturestart", Utils.bind(this, function(b) {
			a.fireGestureEvent(b);
			Event.consume(b)
		})), Event.addListener(b, "gesturechange", Utils.bind(this, function(b) {
			a.fireGestureEvent(b);
			Event.consume(b)
		})), Event.addListener(b, "gestureend", Utils.bind(this, function(b) {
			a.fireGestureEvent(b);
			Event.consume(b)
		})));
		Event.addGestureListeners(b, Utils.bind(this, function(b) {
			this.isContainerEvent(b) && (!Client.IS_IE && !Client.IS_GC && !Client.IS_OP && !Client.IS_SF || !this.isScrollEvent(b)) && a.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(b))
		}), Utils.bind(this, function(b) {
			this.isContainerEvent(b) && a.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(b))
		}), Utils.bind(this, function(b) {
			this.isContainerEvent(b) && a.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(b))
		}));
		Event.addListener(b, "dblclick", Utils.bind(this, function(b) {
			this.isContainerEvent(b) && a.dblClick(b)
		}));
		var c = function(c) {
				var e = null;
				Client.IS_TOUCH && (e = Event.getClientX(c), c = Event.getClientY(c), c = Utils.convertPoint(b, e, c), e = a.view.getState(a.getCellAt(c.x, c.y)));
				return e
			};
		a.addMouseListener({
			mouseDown: function(b, c) {
				a.popupMenuHandler.hideMenu()
			},
			mouseMove: function() {},
			mouseUp: function() {}
		});
		this.moveHandler = Utils.bind(this, function(b) {
			null != a.tooltipHandler && a.tooltipHandler.isHideOnHover() && a.tooltipHandler.hide();
			this.captureDocumentGesture && (a.isMouseDown && null != a.container && !this.isContainerEvent(b) && "none" != a.container.style.display && "hidden" != a.container.style.visibility && !Event.isConsumed(b)) && a.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(b, c(b)))
		});
		this.endHandler = Utils.bind(this, function(b) {
			this.captureDocumentGesture && (a.isMouseDown && null != a.container && !this.isContainerEvent(b) && "none" != a.container.style.display && "hidden" != a.container.style.visibility) && a.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(b))
		});
		Event.addGestureListeners(document, null, this.moveHandler, this.endHandler)
	}
};
GraphView.prototype.createHtml = function() {
	var a = this.graph.container;
	null != a && (this.canvas = this.createHtmlPane("100%", "100%"), this.backgroundPane = this.createHtmlPane("1px", "1px"), this.drawPane = this.createHtmlPane("1px", "1px"), this.overlayPane = this.createHtmlPane("1px", "1px"), this.decoratorPane = this.createHtmlPane("1px", "1px"), this.canvas.appendChild(this.backgroundPane), this.canvas.appendChild(this.drawPane), this.canvas.appendChild(this.overlayPane), this.canvas.appendChild(this.decoratorPane), a.appendChild(this.canvas), Client.IS_QUIRKS && (a = Utils.bind(this, function(a) {
		a = this.getGraphBounds();
		this.updateHtmlCanvasSize(a.x + a.width + this.graph.border, a.y + a.height + this.graph.border)
	}), Event.addListener(window, "resize", a)))
};
GraphView.prototype.updateHtmlCanvasSize = function(a, b) {
	if (null != this.graph.container) {
		var c = this.graph.container.offsetHeight;
		this.canvas.style.width = this.graph.container.offsetWidth < a ? a + "px" : "100%";
		this.canvas.style.height = c < b ? b + "px" : "100%"
	}
};
GraphView.prototype.createHtmlPane = function(a, b) {
	var c = document.createElement("DIV");
	null != a && null != b ? (c.style.position = "absolute", c.style.left = "0px", c.style.top = "0px", c.style.width = a, c.style.height = b) : c.style.position = "relative";
	return c
};
GraphView.prototype.createVml = function() {
	var a = this.graph.container;
	if (null != a) {
		var b = a.offsetWidth,
			c = a.offsetHeight;
		this.canvas = this.createVmlPane(b, c);
		this.backgroundPane = this.createVmlPane(b, c);
		this.drawPane = this.createVmlPane(b, c);
		this.overlayPane = this.createVmlPane(b, c);
		this.decoratorPane = this.createVmlPane(b, c);
		this.canvas.appendChild(this.backgroundPane);
		this.canvas.appendChild(this.drawPane);
		this.canvas.appendChild(this.overlayPane);
		this.canvas.appendChild(this.decoratorPane);
		a.appendChild(this.canvas)
	}
};
GraphView.prototype.createVmlPane = function(a, b) {
	var c = document.createElement(Client.VML_PREFIX + ":group");
	c.style.position = "absolute";
	c.style.left = "0px";
	c.style.top = "0px";
	c.style.width = a + "px";
	c.style.height = b + "px";
	c.setAttribute("coordsize", a + "," + b);
	c.setAttribute("coordorigin", "0,0");
	return c
};
GraphView.prototype.createSvg = function() {
	var a = this.graph.container;
	this.canvas = document.createElementNS(Constants.NS_SVG, "g");
	this.backgroundPane = document.createElementNS(Constants.NS_SVG, "g");
	this.canvas.appendChild(this.backgroundPane);
	this.drawPane = document.createElementNS(Constants.NS_SVG, "g");
	this.canvas.appendChild(this.drawPane);
	this.overlayPane = document.createElementNS(Constants.NS_SVG, "g");
	this.canvas.appendChild(this.overlayPane);
	this.decoratorPane = document.createElementNS(Constants.NS_SVG, "g");
	this.canvas.appendChild(this.decoratorPane);
	var b = document.createElementNS(Constants.NS_SVG, "svg");
	b.style.width = "100%";
	b.style.height = "100%";
	b.style.display = "block";
	b.appendChild(this.canvas);
	null != a && (a.appendChild(b), this.updateContainerStyle(a))
};
GraphView.prototype.updateContainerStyle = function(a) {
	"static" == Utils.getCurrentStyle(a).position && (a.style.position = "relative");
	Client.IS_POINTER && (a.style.msTouchAction = "none")
};
GraphView.prototype.destroy = function() {
	var a = null != this.canvas ? this.canvas.ownerSVGElement : null;
	null == a && (a = this.canvas);
	null != a && null != a.parentNode && (this.clear(this.currentRoot, !0), Event.removeGestureListeners(document, null, this.moveHandler, this.endHandler), Event.release(this.graph.container), a.parentNode.removeChild(a), this.decoratorPane = this.overlayPane = this.drawPane = this.backgroundPane = this.canvas = this.endHandler = this.moveHandler = null)
};

function CurrentRootChange(a, b) {
	this.view = a;
	this.previous = this.root = b;
	this.isUp = null == b;
	if (!this.isUp) for (var c = this.view.currentRoot, d = this.view.graph.getModel(); null != c;) {
		if (c == b) {
			this.isUp = !0;
			break
		}
		c = d.getParent(c)
	}
}
CurrentRootChange.prototype.execute = function() {
	var a = this.view.currentRoot;
	this.view.currentRoot = this.previous;
	this.previous = a;
	a = this.view.graph.getTranslateForRoot(this.view.currentRoot);
	null != a && (this.view.translate = new Point(-a.x, -a.y));
	this.view.fireEvent(new EventObject(this.isUp ? Event.UP : Event.DOWN, "root", this.view.currentRoot, "previous", this.previous));
	this.isUp ? (this.view.clear(this.view.currentRoot, !0), this.view.validate()) : this.view.refresh();
	this.isUp = !this.isUp
};

function Graph(a, b, c, d) {
	this.mouseListeners = null;
	this.renderHint = c;
	this.dialect = Client.IS_SVG ? Constants.DIALECT_SVG : c == Constants.RENDERING_HINT_EXACT && Client.IS_VML ? Constants.DIALECT_VML : c == Constants.RENDERING_HINT_FASTEST ? Constants.DIALECT_STRICTHTML : c == Constants.RENDERING_HINT_FASTER ? Constants.DIALECT_PREFERHTML : Constants.DIALECT_MIXEDHTML;
	this.model = null != b ? b : new GraphModel;
	this.multiplicities = [];
	this.imageBundles = [];
	this.cellRenderer = this.createCellRenderer();
	this.setSelectionModel(this.createSelectionModel());
	this.setStylesheet(null != d ? d : this.createStylesheet());
	this.view = this.createGraphView();
	this.graphModelChangeListener = Utils.bind(this, function(a, b) {
		this.graphModelChanged(b.getProperty("edit").changes)
	});
	this.model.addListener(Event.CHANGE, this.graphModelChangeListener);
	this.createHandlers();
	null != a && this.init(a);
	this.view.revalidate()
}
LoadResources && Resources.add(Client.basePath + "/resources/graph");
Graph.prototype = new EventSource;
Graph.prototype.constructor = Graph;
Graph.prototype.EMPTY_ARRAY = [];
Graph.prototype.mouseListeners = null;
Graph.prototype.isMouseDown = !1;
Graph.prototype.model = null;
Graph.prototype.view = null;
Graph.prototype.stylesheet = null;
Graph.prototype.selectionModel = null;
Graph.prototype.cellEditor = null;
Graph.prototype.cellRenderer = null;
Graph.prototype.multiplicities = null;
Graph.prototype.renderHint = null;
Graph.prototype.dialect = null;
Graph.prototype.gridSize = 10;
Graph.prototype.gridEnabled = !0;
Graph.prototype.portsEnabled = !0;
Graph.prototype.nativeDblClickEnabled = !Client.IS_QUIRKS && (null == document.documentMode || 10 > document.documentMode);
Graph.prototype.doubleTapEnabled = !0;
Graph.prototype.doubleTapTimeout = 500;
Graph.prototype.doubleTapTolerance = 25;
Graph.prototype.lastTouchY = 0;
Graph.prototype.lastTouchY = 0;
Graph.prototype.lastTouchTime = 0;
Graph.prototype.tapAndHoldEnabled = !0;
Graph.prototype.tapAndHoldDelay = 500;
Graph.prototype.tapAndHoldInProgress = !1;
Graph.prototype.tapAndHoldValid = !1;
Graph.prototype.initialTouchX = 0;
Graph.prototype.initialTouchY = 0;
Graph.prototype.tolerance = 4;
Graph.prototype.defaultOverlap = 0.5;
Graph.prototype.defaultParent = null;
Graph.prototype.alternateEdgeStyle = null;
Graph.prototype.backgroundImage = null;
Graph.prototype.pageVisible = !1;
Graph.prototype.pageBreaksVisible = !1;
Graph.prototype.pageBreakColor = "gray";
Graph.prototype.pageBreakDashed = !0;
Graph.prototype.minPageBreakDist = 20;
Graph.prototype.preferPageSize = !1;
Graph.prototype.pageFormat = Constants.PAGE_FORMAT_A4_PORTRAIT;
Graph.prototype.pageScale = 1.5;
Graph.prototype.enabled = !0;
Graph.prototype.escapeEnabled = !0;
Graph.prototype.invokesStopCellEditing = !0;
Graph.prototype.enterStopsCellEditing = !1;
Graph.prototype.useScrollbarsForPanning = !0;
Graph.prototype.exportEnabled = !0;
Graph.prototype.importEnabled = !0;
Graph.prototype.cellsLocked = !1;
Graph.prototype.cellsCloneable = !0;
Graph.prototype.foldingEnabled = !0;
Graph.prototype.cellsEditable = !0;
Graph.prototype.cellsDeletable = !0;
Graph.prototype.cellsMovable = !0;
Graph.prototype.edgeLabelsMovable = !0;
Graph.prototype.vertexLabelsMovable = !1;
Graph.prototype.dropEnabled = !1;
Graph.prototype.splitEnabled = !0;
Graph.prototype.cellsResizable = !0;
Graph.prototype.cellsBendable = !0;
Graph.prototype.cellsSelectable = !0;
Graph.prototype.cellsDisconnectable = !0;
Graph.prototype.autoSizeCells = !1;
Graph.prototype.autoSizeCellsOnAdd = !1;
Graph.prototype.autoScroll = !0;
Graph.prototype.timerAutoScroll = !1;
Graph.prototype.allowAutoPanning = !1;
Graph.prototype.ignoreScrollbars = !1;
Graph.prototype.autoExtend = !0;
Graph.prototype.maximumGraphBounds = null;
Graph.prototype.minimumGraphSize = null;
Graph.prototype.minimumContainerSize = null;
Graph.prototype.maximumContainerSize = null;
Graph.prototype.resizeContainer = !1;
Graph.prototype.border = 0;
Graph.prototype.keepEdgesInForeground = !1;
Graph.prototype.keepEdgesInBackground = !1;
Graph.prototype.allowNegativeCoordinates = !0;
Graph.prototype.constrainChildren = !0;
Graph.prototype.constrainChildrenOnResize = !1;
Graph.prototype.extendParents = !0;
Graph.prototype.extendParentsOnAdd = !0;
Graph.prototype.extendParentsOnMove = !1;
Graph.prototype.recursiveResize = !1;
Graph.prototype.collapseToPreferredSize = !0;
Graph.prototype.zoomFactor = 1.2;
Graph.prototype.keepSelectionVisibleOnZoom = !1;
Graph.prototype.centerZoom = !0;
Graph.prototype.resetViewOnRootChange = !0;
Graph.prototype.resetEdgesOnResize = !1;
Graph.prototype.resetEdgesOnMove = !1;
Graph.prototype.resetEdgesOnConnect = !0;
Graph.prototype.allowLoops = !1;
Graph.prototype.defaultLoopStyle = EdgeStyle.Loop;
Graph.prototype.multigraph = !0;
Graph.prototype.connectableEdges = !1;
Graph.prototype.allowDanglingEdges = !0;
Graph.prototype.cloneInvalidEdges = !1;
Graph.prototype.disconnectOnMove = !0;
Graph.prototype.labelsVisible = !0;
Graph.prototype.htmlLabels = !1;
Graph.prototype.swimlaneSelectionEnabled = !0;
Graph.prototype.swimlaneNesting = !0;
Graph.prototype.swimlaneIndicatorColorAttribute = Constants.STYLE_FILLCOLOR;
Graph.prototype.imageBundles = null;
Graph.prototype.minFitScale = 0.1;
Graph.prototype.maxFitScale = 8;
Graph.prototype.panDx = 0;
Graph.prototype.panDy = 0;
Graph.prototype.collapsedImage = new Image(Client.imageBasePath + "/collapsed.gif", 9, 9);
Graph.prototype.expandedImage = new Image(Client.imageBasePath + "/expanded.gif", 9, 9);
Graph.prototype.warningImage = new Image(Client.imageBasePath + "/warning" + (Client.IS_MAC ? ".png" : ".gif"), 16, 16);
Graph.prototype.alreadyConnectedResource = "none" != Client.language ? "alreadyConnected" : "";
Graph.prototype.containsValidationErrorsResource = "none" != Client.language ? "containsValidationErrors" : "";
Graph.prototype.collapseExpandResource = "none" != Client.language ? "collapse-expand" : "";
Graph.prototype.init = function(a) {
	this.container = a;
	this.cellEditor = this.createCellEditor();
	this.view.init();
	this.sizeDidChange();
	Event.addListener(a, "mouseleave", Utils.bind(this, function() {
		null != this.tooltipHandler && this.tooltipHandler.hide()
	}));
	Client.IS_IE && (Event.addListener(window, "unload", Utils.bind(this, function() {
		this.destroy()
	})), Event.addListener(a, "selectstart", Utils.bind(this, function(a) {
		return this.isEditing() || !this.isMouseDown && !Event.isShiftDown(a)
	})));
	8 == document.documentMode && a.insertAdjacentHTML("beforeend", '<v:group style="DISPLAY: none;"></v:group>')
};
Graph.prototype.createHandlers = function(a) {
	this.tooltipHandler = new TooltipHandler(this);
	this.tooltipHandler.setEnabled(!1);
	this.selectionCellsHandler = new SelectionCellsHandler(this);
	this.connectionHandler = new ConnectionHandler(this);
	this.connectionHandler.setEnabled(!1);
	this.graphHandler = new GraphHandler(this);
	this.panningHandler = new PanningHandler(this);
	this.panningHandler.panningEnabled = !1;
	this.popupMenuHandler = new PopupMenuHandler(this)
};
Graph.prototype.createSelectionModel = function() {
	return new GraphSelectionModel(this)
};
Graph.prototype.createStylesheet = function() {
	return new Stylesheet
};
Graph.prototype.createGraphView = function() {
	return new GraphView(this)
};
Graph.prototype.createCellRenderer = function() {
	return new CellRenderer
};
Graph.prototype.createCellEditor = function() {
	return new CellEditor(this)
};
Graph.prototype.getModel = function() {
	return this.model
};
Graph.prototype.getView = function() {
	return this.view
};
Graph.prototype.getStylesheet = function() {
	return this.stylesheet
};
Graph.prototype.setStylesheet = function(a) {
	this.stylesheet = a
};
Graph.prototype.getSelectionModel = function() {
	return this.selectionModel
};
Graph.prototype.setSelectionModel = function(a) {
	this.selectionModel = a
};
Graph.prototype.getSelectionCellsForChanges = function(a) {
	for (var b = [], c = 0; c < a.length; c++) {
		var d = a[c];
		if (d.constructor != RootChange) {
			var e = null;
			d instanceof ChildChange && null == d.previous ? e = d.child : null != d.cell && d.cell instanceof Cell && (e = d.cell);
			null != e && 0 > Utils.indexOf(b, e) && b.push(e)
		}
	}
	return this.getModel().getTopmostCells(b)
};
Graph.prototype.graphModelChanged = function(a) {
	for (var b = 0; b < a.length; b++) this.processChange(a[b]);
	this.removeSelectionCells(this.getRemovedCellsForChanges(a));
	this.view.validate();
	this.sizeDidChange()
};
Graph.prototype.getRemovedCellsForChanges = function(a) {
	for (var b = [], c = 0; c < a.length; c++) {
		var d = a[c];
		if (d instanceof RootChange) break;
		else d instanceof ChildChange ? null != d.previous && null == d.parent && (b = b.concat(this.model.getDescendants(d.child))) : d instanceof VisibleChange && (b = b.concat(this.model.getDescendants(d.cell)))
	}
	return b
};
Graph.prototype.processChange = function(a) {
	if (a instanceof RootChange) this.clearSelection(), this.removeStateForCell(a.previous), this.resetViewOnRootChange && (this.view.scale = 1, this.view.translate.x = 0, this.view.translate.y = 0), this.fireEvent(new EventObject(Event.ROOT));
	else if (a instanceof ChildChange) {
		var b = this.model.getParent(a.child);
		this.view.invalidate(a.child, !0, !0);
		if (null == b || this.isCellCollapsed(b)) this.view.invalidate(a.child, !0, !0), this.removeStateForCell(a.child), this.view.currentRoot == a.child && this.home();
		b != a.previous && (null != b && this.view.invalidate(b, !1, !1), null != a.previous && this.view.invalidate(a.previous, !1, !1))
	} else a instanceof TerminalChange || a instanceof GeometryChange ? (a instanceof TerminalChange || null == a.previous && null != a.geometry || null != a.previous && !a.previous.equals(a.geometry)) && this.view.invalidate(a.cell) : a instanceof ValueChange ? this.view.invalidate(a.cell, !1, !1) : a instanceof StyleChange ? (this.view.invalidate(a.cell, !0, !0), this.view.removeState(a.cell)) : null != a.cell && a.cell instanceof Cell && this.removeStateForCell(a.cell)
};
Graph.prototype.removeStateForCell = function(a) {
	for (var b = this.model.getChildCount(a), c = 0; c < b; c++) this.removeStateForCell(this.model.getChildAt(a, c));
	this.view.invalidate(a, !1, !0);
	this.view.removeState(a)
};
Graph.prototype.addCellOverlay = function(a, b) {
	null == a.overlays && (a.overlays = []);
	a.overlays.push(b);
	var c = this.view.getState(a);
	null != c && this.cellRenderer.redraw(c);
	this.fireEvent(new EventObject(Event.ADD_OVERLAY, "cell", a, "overlay", b));
	return b
};
Graph.prototype.getCellOverlays = function(a) {
	return a.overlays
};
Graph.prototype.removeCellOverlay = function(a, b) {
	if (null == b) this.removeCellOverlays(a);
	else {
		var c = Utils.indexOf(a.overlays, b);
		0 <= c ? (a.overlays.splice(c, 1), 0 == a.overlays.length && (a.overlays = null), c = this.view.getState(a), null != c && this.cellRenderer.redraw(c), this.fireEvent(new EventObject(Event.REMOVE_OVERLAY, "cell", a, "overlay", b))) : b = null
	}
	return b
};
Graph.prototype.removeCellOverlays = function(a) {
	var b = a.overlays;
	if (null != b) {
		a.overlays = null;
		var c = this.view.getState(a);
		null != c && this.cellRenderer.redraw(c);
		for (c = 0; c < b.length; c++) this.fireEvent(new EventObject(Event.REMOVE_OVERLAY, "cell", a, "overlay", b[c]))
	}
	return b
};
Graph.prototype.clearCellOverlays = function(a) {
	a = null != a ? a : this.model.getRoot();
	this.removeCellOverlays(a);
	for (var b = this.model.getChildCount(a), c = 0; c < b; c++) {
		var d = this.model.getChildAt(a, c);
		this.clearCellOverlays(d)
	}
};
Graph.prototype.setCellWarning = function(a, b, c, d) {
	if (null != b && 0 < b.length) return c = null != c ? c : this.warningImage, b = new CellOverlay(c, "<font color=red>" + b + "</font>"), d && b.addListener(Event.CLICK, Utils.bind(this, function(b, c) {
		this.isEnabled() && this.setSelectionCell(a)
	})), this.addCellOverlay(a, b);
	this.removeCellOverlays(a);
	return null
};
Graph.prototype.startEditing = function(a) {
	this.startEditingAtCell(null, a)
};
Graph.prototype.startEditingAtCell = function(a, b) {
	if (null == b || !Event.isMultiTouchEvent(b)) null == a && (a = this.getSelectionCell(), null != a && !this.isCellEditable(a) && (a = null)), null != a && (this.fireEvent(new EventObject(Event.START_EDITING, "cell", a, "event", b)), this.cellEditor.startEditing(a, b))
};
Graph.prototype.getEditingValue = function(a, b) {
	return this.convertValueToString(a)
};
Graph.prototype.stopEditing = function(a) {
	this.cellEditor.stopEditing(a)
};
Graph.prototype.labelChanged = function(a, b, c) {
	this.model.beginUpdate();
	try {
		var d = a.value;
		this.cellLabelChanged(a, b, this.isAutoSizeCell(a));
		this.fireEvent(new EventObject(Event.LABEL_CHANGED, "cell", a, "value", b, "old", d, "event", c))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cellLabelChanged = function(a, b, c) {
	this.model.beginUpdate();
	try {
		this.model.setValue(a, b), c && this.cellSizeUpdated(a, !1)
	} finally {
		this.model.endUpdate()
	}
};
Graph.prototype.escape = function(a) {
	this.fireEvent(new EventObject(Event.ESCAPE, "event", a))
};
Graph.prototype.click = function(a) {
	var b = a.getEvent(),
		c = a.getCell(),
		d = new EventObject(Event.CLICK, "event", b, "cell", c);
	a.isConsumed() && d.consume();
	this.fireEvent(d);
	this.isEnabled() && (!Event.isConsumed(b) && !d.isConsumed()) && (null != c ? this.selectCellForEvent(c, b) : (c = null, this.isSwimlaneSelectionEnabled() && (c = this.getSwimlaneAt(a.getGraphX(), a.getGraphY())), null != c ? this.selectCellForEvent(c, b) : this.isToggleEvent(b) || this.clearSelection()))
};
Graph.prototype.dblClick = function(a, b) {
	var c = new EventObject(Event.DOUBLE_CLICK, "event", a, "cell", b);
	this.fireEvent(c);
	this.isEnabled() && (!Event.isConsumed(a) && !c.isConsumed() && null != b && this.isCellEditable(b) && !this.isEditing(b)) && (this.startEditingAtCell(b, a), Event.consume(a))
};
Graph.prototype.tapAndHold = function(a) {
	var b = a.getEvent(),
		c = new EventObject(Event.TAP_AND_HOLD, "event", b, "cell", a.getCell());
	this.fireEvent(c);
	c.isConsumed() && (this.panningHandler.panningTrigger = !1);
	this.isEnabled() && (!Event.isConsumed(b) && !c.isConsumed() && this.connectionHandler.isEnabled()) && (b = this.view.getState(this.connectionHandler.marker.getCell(a)), null != b && (this.connectionHandler.marker.currentColor = this.connectionHandler.marker.validColor, this.connectionHandler.marker.markedState = b, this.connectionHandler.marker.mark(), this.connectionHandler.first = new Point(a.getGraphX(), a.getGraphY()), this.connectionHandler.edgeState = this.connectionHandler.createEdgeState(a), this.connectionHandler.previous = b, this.connectionHandler.fireEvent(new EventObject(Event.START, "state", this.connectionHandler.previous))))
};
Graph.prototype.scrollPointToVisible = function(a, b, c, d) {
	if (!this.timerAutoScroll && (this.ignoreScrollbars || Utils.hasScrollbars(this.container))) {
		var e = this.container;
		d = null != d ? d : 20;
		if (a >= e.scrollLeft && b >= e.scrollTop && a <= e.scrollLeft + e.clientWidth && b <= e.scrollTop + e.clientHeight) {
			var f = e.scrollLeft + e.clientWidth - a;
			if (f < d) {
				if (a = e.scrollLeft, e.scrollLeft += d - f, c && a == e.scrollLeft) {
					if (this.dialect == Constants.DIALECT_SVG) {
						a = this.view.getDrawPane().ownerSVGElement;
						var g = this.container.scrollWidth + d - f
					} else g = Math.max(e.clientWidth, e.scrollWidth) + d - f, a = this.view.getCanvas();
					a.style.width = g + "px";
					e.scrollLeft += d - f
				}
			} else f = a - e.scrollLeft, f < d && (e.scrollLeft -= d - f);
			f = e.scrollTop + e.clientHeight - b;
			f < d ? (a = e.scrollTop, e.scrollTop += d - f, a == e.scrollTop && c && (this.dialect == Constants.DIALECT_SVG ? (a = this.view.getDrawPane().ownerSVGElement, b = this.container.scrollHeight + d - f) : (b = Math.max(e.clientHeight, e.scrollHeight) + d - f, a = this.view.getCanvas()), a.style.height = b + "px", e.scrollTop += d - f)) : (f = b - e.scrollTop, f < d && (e.scrollTop -= d - f))
		}
	} else this.allowAutoPanning && !this.panningHandler.isActive() && (null == this.panningManager && (this.panningManager = this.createPanningManager()), this.panningManager.panTo(a + this.panDx, b + this.panDy))
};
Graph.prototype.createPanningManager = function() {
	return new PanningManager(this)
};
Graph.prototype.getBorderSizes = function() {
	function a(a) {
		var b = 0,
			b = "thin" == a ? 2 : "medium" == a ? 4 : "thick" == a ? 6 : parseInt(a);
		isNaN(b) && (b = 0);
		return b
	}
	var b = Utils.getCurrentStyle(this.container),
		c = new Rectangle;
	c.x = a(b.borderLeftWidth) + parseInt(b.paddingLeft || 0);
	c.y = a(b.borderTopWidth) + parseInt(b.paddingTop || 0);
	c.width = a(b.borderRightWidth) + parseInt(b.paddingRight || 0);
	c.height = a(b.borderBottomWidth) + parseInt(b.paddingBottom || 0);
	return c
};
Graph.prototype.getPreferredPageSize = function(a, b, c) {
	a = this.view.scale;
	var d = this.view.translate,
		e = this.pageFormat,
		f = a * this.pageScale,
		e = new Rectangle(0, 0, e.width * f, e.height * f);
	b = this.pageBreaksVisible ? Math.ceil(b / e.width) : 1;
	c = this.pageBreaksVisible ? Math.ceil(c / e.height) : 1;
	return new Rectangle(0, 0, b * e.width + 2 + d.x / a, c * e.height + 2 + d.y / a)
};
Graph.prototype.sizeDidChange = function() {
	var a = this.getGraphBounds();
	if (null != this.container) {
		var b = this.getBorder(),
			c = Math.max(0, a.x + a.width + 1 + b),
			b = Math.max(0, a.y + a.height + 1 + b);
		null != this.minimumContainerSize && (c = Math.max(c, this.minimumContainerSize.width), b = Math.max(b, this.minimumContainerSize.height));
		this.resizeContainer && this.doResizeContainer(c, b);
		if (this.preferPageSize || !Client.IS_IE && this.pageVisible) {
			var d = this.getPreferredPageSize(a, c, b);
			null != d && (c = d.width, b = d.height)
		}
		null != this.minimumGraphSize && (c = Math.max(c, this.minimumGraphSize.width * this.view.scale), b = Math.max(b, this.minimumGraphSize.height * this.view.scale));
		c = Math.ceil(c - 1);
		b = Math.ceil(b - 1);
		this.dialect == Constants.DIALECT_SVG ? (d = this.view.getDrawPane().ownerSVGElement, d.style.minWidth = Math.max(1, c) + "px", d.style.minHeight = Math.max(1, b) + "px", d.style.width = "100%", d.style.height = "100%") : Client.IS_QUIRKS ? this.view.updateHtmlCanvasSize(Math.max(1, c), Math.max(1, b)) : (this.view.canvas.style.minWidth = Math.max(1, c) + "px", this.view.canvas.style.minHeight = Math.max(1, b) + "px");
		this.updatePageBreaks(this.pageBreaksVisible, c - 1, b - 1)
	}
	this.fireEvent(new EventObject(Event.SIZE, "bounds", a))
};
Graph.prototype.doResizeContainer = function(a, b) {
	if (Client.IS_IE) if (Client.IS_QUIRKS) {
		var c = this.getBorderSizes();
		a += Math.max(2, c.x + c.width + 1);
		b += Math.max(2, c.y + c.height + 1)
	} else 9 <= document.documentMode ? (a += 3, b += 5) : (a += 1, b += 1);
	else b += 1;
	null != this.maximumContainerSize && (a = Math.min(this.maximumContainerSize.width, a), b = Math.min(this.maximumContainerSize.height, b));
	this.container.style.width = Math.ceil(a) + "px";
	this.container.style.height = Math.ceil(b) + "px"
};
Graph.prototype.updatePageBreaks = function(a, b, c) {
	var d = this.view.scale,
		e = this.view.translate,
		f = this.pageFormat,
		g = d * this.pageScale,
		d = new Rectangle(d * e.x, d * e.y, f.width * g, f.height * g);
	a = a && Math.min(d.width, d.height) > this.minPageBreakDist;
	d.x = Utils.mod(d.x, d.width);
	d.y = Utils.mod(d.y, d.height);
	e = a ? Math.ceil((b - d.x) / d.width) : 0;
	a = a ? Math.ceil((c - d.y) / d.height) : 0;
	null == this.horizontalPageBreaks && 0 < e && (this.horizontalPageBreaks = []);
	if (null != this.horizontalPageBreaks) {
		for (f = 0; f <= e; f++) g = [new Point(d.x + f * d.width, 1), new Point(d.x + f * d.width, c)], null != this.horizontalPageBreaks[f] ? (this.horizontalPageBreaks[f].points = g, this.horizontalPageBreaks[f].redraw()) : (g = new Polyline(g, this.pageBreakColor), g.dialect = this.dialect, g.pointerEvents = !1, g.isDashed = this.pageBreakDashed, g.init(this.view.backgroundPane), g.redraw(), this.horizontalPageBreaks[f] = g);
		for (f = e; f < this.horizontalPageBreaks.length; f++) this.horizontalPageBreaks[f].destroy();
		this.horizontalPageBreaks.splice(e, this.horizontalPageBreaks.length - e)
	}
	null == this.verticalPageBreaks && 0 < a && (this.verticalPageBreaks = []);
	if (null != this.verticalPageBreaks) {
		for (f = 0; f <= a; f++) g = [new Point(1, d.y + f * d.height), new Point(b, d.y + f * d.height)], null != this.verticalPageBreaks[f] ? (this.verticalPageBreaks[f].points = g, this.verticalPageBreaks[f].redraw()) : (g = new Polyline(g, this.pageBreakColor), g.dialect = this.dialect, g.pointerEvents = !1, g.isDashed = this.pageBreakDashed, g.init(this.view.backgroundPane), g.redraw(), this.verticalPageBreaks[f] = g);
		for (f = a; f < this.verticalPageBreaks.length; f++) this.verticalPageBreaks[f].destroy();
		this.verticalPageBreaks.splice(a, this.verticalPageBreaks.length - a)
	}
};
Graph.prototype.getCellStyle = function(a) {
	var b = this.model.getStyle(a),
		c = null,
		c = this.model.isEdge(a) ? this.stylesheet.getDefaultEdgeStyle() : this.stylesheet.getDefaultVertexStyle();
	null != b && (c = this.postProcessCellStyle(this.stylesheet.getCellStyle(b, c)));
	null == c && (c = Graph.prototype.EMPTY_ARRAY);
	return c
};
Graph.prototype.postProcessCellStyle = function(a) {
	if (null != a) {
		var b = a[Constants.STYLE_IMAGE],
			c = this.getImageFromBundles(b);
		null != c ? a[Constants.STYLE_IMAGE] = c : c = b;
		null != c && "data:image/" == c.substring(0, 11) && ("data:image/svg+xml,<" == c.substring(0, 20) ? c = c.substring(0, 19) + encodeURIComponent(c.substring(19)) : "data:image/svg+xml,%3C" != c.substring(0, 22) && (b = c.indexOf(","), 0 < b && (c = c.substring(0, b) + ";base64," + c.substring(b + 1))), a[Constants.STYLE_IMAGE] = c)
	}
	return a
};
Graph.prototype.setCellStyle = function(a, b) {
	b = b || this.getSelectionCells();
	if (null != b) {
		this.model.beginUpdate();
		try {
			for (var c = 0; c < b.length; c++) this.model.setStyle(b[c], a)
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.toggleCellStyle = function(a, b, c) {
	c = c || this.getSelectionCell();
	this.toggleCellStyles(a, b, [c])
};
Graph.prototype.toggleCellStyles = function(a, b, c) {
	b = null != b ? b : !1;
	c = c || this.getSelectionCells();
	if (null != c && 0 < c.length) {
		var d = this.view.getState(c[0]),
			d = null != d ? d.style : this.getCellStyle(c[0]);
		null != d && (b = Utils.getValue(d, a, b) ? 0 : 1, this.setCellStyles(a, b, c))
	}
};
Graph.prototype.setCellStyles = function(a, b, c) {
	c = c || this.getSelectionCells();
	Utils.setCellStyles(this.model, c, a, b)
};
Graph.prototype.toggleCellStyleFlags = function(a, b, c) {
	this.setCellStyleFlags(a, b, null, c)
};
Graph.prototype.setCellStyleFlags = function(a, b, c, d) {
	d = d || this.getSelectionCells();
	if (null != d && 0 < d.length) {
		if (null == c) {
			var e = this.view.getState(d[0]),
				e = null != e ? e.style : this.getCellStyle(d[0]);
			null != e && (c = (parseInt(e[a] || 0) & b) != b)
		}
		Utils.setCellStyleFlags(this.model, d, a, b, c)
	}
};
Graph.prototype.alignCells = function(a, b, c) {
	null == b && (b = this.getSelectionCells());
	if (null != b && 1 < b.length) {
		if (null == c) for (var d = 0; d < b.length; d++) {
			var e = this.view.getState(b[d]);
			if (null != e && !this.model.isEdge(b[d])) if (null == c) if (a == Constants.ALIGN_CENTER) {
				c = e.x + e.width / 2;
				break
			} else if (a == Constants.ALIGN_RIGHT) c = e.x + e.width;
			else if (a == Constants.ALIGN_TOP) c = e.y;
			else if (a == Constants.ALIGN_MIDDLE) {
				c = e.y + e.height / 2;
				break
			} else c = a == Constants.ALIGN_BOTTOM ? e.y + e.height : e.x;
			else c = a == Constants.ALIGN_RIGHT ? Math.max(c, e.x + e.width) : a == Constants.ALIGN_TOP ? Math.min(c, e.y) : a == Constants.ALIGN_BOTTOM ? Math.max(c, e.y + e.height) : Math.min(c, e.x)
		}
		if (null != c) {
			var f = this.view.scale;
			this.model.beginUpdate();
			try {
				for (d = 0; d < b.length; d++) if (e = this.view.getState(b[d]), null != e) {
					var g = this.getCellGeometry(b[d]);
					null != g && !this.model.isEdge(b[d]) && (g = g.clone(), a == Constants.ALIGN_CENTER ? g.x += (c - e.x - e.width / 2) / f : a == Constants.ALIGN_RIGHT ? g.x += (c - e.x - e.width) / f : a == Constants.ALIGN_TOP ? g.y += (c - e.y) / f : a == Constants.ALIGN_MIDDLE ? g.y += (c - e.y - e.height / 2) / f : a == Constants.ALIGN_BOTTOM ? g.y += (c - e.y - e.height) / f : g.x += (c - e.x) / f, this.resizeCell(b[d], g))
				}
				this.fireEvent(new EventObject(Event.ALIGN_CELLS, "align", a, "cells", b))
			} finally {
				this.model.endUpdate()
			}
		}
	}
	return b
};
Graph.prototype.flipEdge = function(a) {
	if (null != a && null != this.alternateEdgeStyle) {
		this.model.beginUpdate();
		try {
			var b = this.model.getStyle(a);
			null == b || 0 == b.length ? this.model.setStyle(a, this.alternateEdgeStyle) : this.model.setStyle(a, null);
			this.resetEdge(a);
			this.fireEvent(new EventObject(Event.FLIP_EDGE, "edge", a))
		} finally {
			this.model.endUpdate()
		}
	}
	return a
};
Graph.prototype.addImageBundle = function(a) {
	this.imageBundles.push(a)
};
Graph.prototype.removeImageBundle = function(a) {
	for (var b = [], c = 0; c < this.imageBundles.length; c++) this.imageBundles[c] != a && b.push(this.imageBundles[c]);
	this.imageBundles = b
};
Graph.prototype.getImageFromBundles = function(a) {
	if (null != a) for (var b = 0; b < this.imageBundles.length; b++) {
		var c = this.imageBundles[b].getImage(a);
		if (null != c) return c
	}
	return null
};
Graph.prototype.orderCells = function(a, b) {
	null == b && (b = Utils.sortCells(this.getSelectionCells(), !0));
	this.model.beginUpdate();
	try {
		this.cellsOrdered(b, a), this.fireEvent(new EventObject(Event.ORDER_CELLS, "back", a, "cells", b))
	} finally {
		this.model.endUpdate()
	}
	return b
};
Graph.prototype.cellsOrdered = function(a, b) {
	if (null != a) {
		this.model.beginUpdate();
		try {
			for (var c = 0; c < a.length; c++) {
				var d = this.model.getParent(a[c]);
				b ? this.model.add(d, a[c], c) : this.model.add(d, a[c], this.model.getChildCount(d) - 1)
			}
			this.fireEvent(new EventObject(Event.CELLS_ORDERED, "back", b, "cells", a))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.groupCells = function(a, b, c) {
	null == c && (c = Utils.sortCells(this.getSelectionCells(), !0));
	c = this.getCellsForGroup(c);
	null == a && (a = this.createGroupCell(c));
	var d = this.getBoundsForGroup(a, c, b);
	if (0 < c.length && null != d) {
		var e = this.model.getParent(a);
		null == e && (e = this.model.getParent(c[0]));
		this.model.beginUpdate();
		try {
			null == this.getCellGeometry(a) && this.model.setGeometry(a, new Geometry);
			var f = this.model.getChildCount(e);
			this.cellsAdded([a], e, f, null, null, !1);
			f = this.model.getChildCount(a);
			this.cellsAdded(c, a, f, null, null, !1, !1);
			this.cellsMoved(c, -d.x, -d.y, !1, !0);
			this.cellsResized([a], [d], !1);
			this.fireEvent(new EventObject(Event.GROUP_CELLS, "group", a, "border", b, "cells", c))
		} finally {
			this.model.endUpdate()
		}
	}
	return a
};
Graph.prototype.getCellsForGroup = function(a) {
	var b = [];
	if (null != a && 0 < a.length) {
		var c = this.model.getParent(a[0]);
		b.push(a[0]);
		for (var d = 1; d < a.length; d++) this.model.getParent(a[d]) == c && b.push(a[d])
	}
	return b
};
Graph.prototype.getBoundsForGroup = function(a, b, c) {
	b = this.getBoundingBoxFromGeometry(b);
	null != b && (this.isSwimlane(a) && (a = this.getStartSize(a), b.x -= a.width, b.y -= a.height, b.width += a.width, b.height += a.height), b.x -= c, b.y -= c, b.width += 2 * c, b.height += 2 * c);
	return b
};
Graph.prototype.createGroupCell = function(a) {
	a = new Cell("");
	a.setVertex(!0);
	a.setConnectable(!1);
	return a
};
Graph.prototype.ungroupCells = function(a) {
	var b = [];
	if (null == a) {
		a = this.getSelectionCells();
		for (var c = [], d = 0; d < a.length; d++) 0 < this.model.getChildCount(a[d]) && c.push(a[d]);
		a = c
	}
	if (null != a && 0 < a.length) {
		this.model.beginUpdate();
		try {
			for (d = 0; d < a.length; d++) {
				var e = this.model.getChildren(a[d]);
				if (null != e && 0 < e.length) {
					var e = e.slice(),
						f = this.model.getParent(a[d]),
						g = this.model.getChildCount(f);
					this.cellsAdded(e, f, g, null, null, !0);
					b = b.concat(e)
				}
			}
			this.cellsRemoved(this.addAllEdges(a));
			this.fireEvent(new EventObject(Event.UNGROUP_CELLS, "cells", a))
		} finally {
			this.model.endUpdate()
		}
	}
	return b
};
Graph.prototype.removeCellsFromParent = function(a) {
	null == a && (a = this.getSelectionCells());
	this.model.beginUpdate();
	try {
		var b = this.getDefaultParent(),
			c = this.model.getChildCount(b);
		this.cellsAdded(a, b, c, null, null, !0);
		this.fireEvent(new EventObject(Event.REMOVE_CELLS_FROM_PARENT, "cells", a))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.updateGroupBounds = function(a, b, c) {
	null == a && (a = this.getSelectionCells());
	b = null != b ? b : 0;
	c = null != c ? c : !1;
	this.model.beginUpdate();
	try {
		for (var d = 0; d < a.length; d++) {
			var e = this.getCellGeometry(a[d]);
			if (null != e) {
				var f = this.getChildCells(a[d]);
				if (null != f && 0 < f.length) {
					var g = this.getBoundingBoxFromGeometry(f);
					if (0 < g.width && 0 < g.height) {
						var k = this.isSwimlane(a[d]) ? this.getStartSize(a[d]) : new Rectangle,
							e = e.clone();
						c && (e.x += g.x - k.width - b, e.y += g.y - k.height - b);
						e.width = g.width + k.width + 2 * b;
						e.height = g.height + k.height + 2 * b;
						this.model.setGeometry(a[d], e);
						this.moveCells(f, -g.x + k.width + b, -g.y + k.height + b)
					}
				}
			}
		}
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cloneCells = function(a, b) {
	b = null != b ? b : !0;
	var c = null;
	if (null != a) {
		for (var d = {}, c = [], e = 0; e < a.length; e++) {
			var f = CellPath.create(a[e]);
			d[f] = a[e];
			c.push(a[e])
		}
		if (0 < c.length) for (var f = this.view.scale, g = this.view.translate, c = this.model.cloneCells(a, !0), e = 0; e < a.length; e++) if (!b && this.model.isEdge(c[e]) && null != this.getEdgeValidationError(c[e], this.model.getTerminal(c[e], !0), this.model.getTerminal(c[e], !1))) c[e] = null;
		else {
			var k = this.model.getGeometry(c[e]);
			if (null != k) {
				var l = this.view.getState(a[e]),
					m = this.view.getState(this.model.getParent(a[e]));
				if (null != l && null != m) {
					var n = m.origin.x,
						m = m.origin.y;
					if (this.model.isEdge(c[e])) {
						for (var l = l.absolutePoints, p = this.model.getTerminal(a[e], !0), q = CellPath.create(p); null != p && null == d[q];) p = this.model.getParent(p), q = CellPath.create(p);
						null == p && k.setTerminalPoint(new Point(l[0].x / f - g.x, l[0].y / f - g.y), !0);
						p = this.model.getTerminal(a[e], !1);
						for (q = CellPath.create(p); null != p && null == d[q];) p = this.model.getParent(p), q = CellPath.create(p);
						null == p && (p = l.length - 1, k.setTerminalPoint(new Point(l[p].x / f - g.x, l[p].y / f - g.y), !1));
						k = k.points;
						if (null != k) for (l = 0; l < k.length; l++) k[l].x += n, k[l].y += m
					} else k.x += n, k.y += m
				}
			}
		} else c = []
	}
	return c
};
Graph.prototype.insertVertex = function(a, b, c, d, e, f, g, k, l) {
	b = this.createVertex(a, b, c, d, e, f, g, k, l);
	return this.addCell(b, a)
};
Graph.prototype.createVertex = function(a, b, c, d, e, f, g, k, l) {
	a = new Geometry(d, e, f, g);
	a.relative = null != l ? l : !1;
	c = new Cell(c, a, k);
	c.setId(b);
	c.setVertex(!0);
	c.setConnectable(!0);
	return c
};
Graph.prototype.insertEdge = function(a, b, c, d, e, f) {
	b = this.createEdge(a, b, c, d, e, f);
	return this.addEdge(b, a, d, e)
};
Graph.prototype.createEdge = function(a, b, c, d, e, f) {
	a = new Cell(c, new Geometry, f);
	a.setId(b);
	a.setEdge(!0);
	a.geometry.relative = !0;
	return a
};
Graph.prototype.addEdge = function(a, b, c, d, e) {
	return this.addCell(a, b, e, c, d)
};
Graph.prototype.addCell = function(a, b, c, d, e) {
	return this.addCells([a], b, c, d, e)[0]
};
Graph.prototype.addCells = function(a, b, c, d, e) {
	null == b && (b = this.getDefaultParent());
	null == c && (c = this.model.getChildCount(b));
	this.model.beginUpdate();
	try {
		this.cellsAdded(a, b, c, d, e, !1, !0), this.fireEvent(new EventObject(Event.ADD_CELLS, "cells", a, "parent", b, "index", c, "source", d, "target", e))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cellsAdded = function(a, b, c, d, e, f, g) {
	if (null != a && null != b && null != c) {
		this.model.beginUpdate();
		try {
			for (var k = f ? this.view.getState(b) : null, l = null != k ? k.origin : null, m = new Point(0, 0), k = 0; k < a.length; k++) if (null == a[k]) c--;
			else {
				var n = this.model.getParent(a[k]);
				if (null != l && a[k] != b && b != n) {
					var p = this.view.getState(n),
						q = null != p ? p.origin : m,
						r = this.model.getGeometry(a[k]);
					if (null != r) {
						var s = q.x - l.x,
							t = q.y - l.y,
							r = r.clone();
						r.translate(s, t);
						!r.relative && (this.model.isVertex(a[k]) && !this.isAllowNegativeCoordinates()) && (r.x = Math.max(0, r.x), r.y = Math.max(0, r.y));
						this.model.setGeometry(a[k], r)
					}
				}
				b == n && c + k > this.model.getChildCount(b) && c--;
				this.model.add(b, a[k], c + k);
				this.autoSizeCellsOnAdd && this.autoSizeCell(a[k], !0);
				this.isExtendParentsOnAdd() && this.isExtendParent(a[k]) && this.extendParent(a[k]);
				(null == g || g) && this.constrainChild(a[k]);
				null != d && this.cellConnected(a[k], d, !0);
				null != e && this.cellConnected(a[k], e, !1)
			}
			this.fireEvent(new EventObject(Event.CELLS_ADDED, "cells", a, "parent", b, "index", c, "source", d, "target", e, "absolute", f))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.autoSizeCell = function(a, b) {
	if (null != b ? b : 1) for (var c = this.model.getChildCount(a), d = 0; d < c; d++) this.autoSizeCell(this.model.getChildAt(a, d));
	this.getModel().isVertex(a) && this.isAutoSizeCell(a) && this.updateCellSize(a)
};
Graph.prototype.removeCells = function(a, b) {
	b = null != b ? b : !0;
	null == a && (a = this.getDeletableCells(this.getSelectionCells()));
	b && (a = this.getDeletableCells(this.addAllEdges(a)));
	this.model.beginUpdate();
	try {
		this.cellsRemoved(a), this.fireEvent(new EventObject(Event.REMOVE_CELLS, "cells", a, "includeEdges", b))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cellsRemoved = function(a) {
	if (null != a && 0 < a.length) {
		var b = this.view.scale,
			c = this.view.translate;
		this.model.beginUpdate();
		try {
			for (var d = {}, e = 0; e < a.length; e++) {
				var f = CellPath.create(a[e]);
				d[f] = a[e]
			}
			for (e = 0; e < a.length; e++) {
				for (var g = this.getConnections(a[e]), k = 0; k < g.length; k++) if (f = CellPath.create(g[k]), null == d[f]) {
					var l = this.model.getGeometry(g[k]);
					if (null != l) {
						var m = this.view.getState(g[k]);
						if (null != m) {
							var l = l.clone(),
								n = m.getVisibleTerminal(!0) == a[e],
								p = m.absolutePoints,
								q = n ? 0 : p.length - 1;
							l.setTerminalPoint(new Point(p[q].x / b - c.x, p[q].y / b - c.y), n);
							this.model.setTerminal(g[k], null, n);
							this.model.setGeometry(g[k], l)
						}
					}
				}
				this.model.remove(a[e])
			}
			this.fireEvent(new EventObject(Event.CELLS_REMOVED, "cells", a))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.splitEdge = function(a, b, c, d, e) {
	d = d || 0;
	e = e || 0;
	null == c && (c = this.cloneCells([a])[0]);
	var f = this.model.getParent(a),
		g = this.model.getTerminal(a, !0);
	this.model.beginUpdate();
	try {
		this.cellsMoved(b, d, e, !1, !1), this.cellsAdded(b, f, this.model.getChildCount(f), null, null, !0), this.cellsAdded([c], f, this.model.getChildCount(f), g, b[0], !1), this.cellConnected(a, b[0], !0), this.fireEvent(new EventObject(Event.SPLIT_EDGE, "edge", a, "cells", b, "newEdge", c, "dx", d, "dy", e))
	} finally {
		this.model.endUpdate()
	}
	return c
};
Graph.prototype.toggleCells = function(a, b, c) {
	null == b && (b = this.getSelectionCells());
	c && (b = this.addAllEdges(b));
	this.model.beginUpdate();
	try {
		this.cellsToggled(b, a), this.fireEvent(new EventObject(Event.TOGGLE_CELLS, "show", a, "cells", b, "includeEdges", c))
	} finally {
		this.model.endUpdate()
	}
	return b
};
Graph.prototype.cellsToggled = function(a, b) {
	if (null != a && 0 < a.length) {
		this.model.beginUpdate();
		try {
			for (var c = 0; c < a.length; c++) this.model.setVisible(a[c], b)
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.foldCells = function(a, b, c, d) {
	b = null != b ? b : !1;
	null == c && (c = this.getFoldableCells(this.getSelectionCells(), a));
	this.stopEditing(!1);
	this.model.beginUpdate();
	try {
		this.cellsFolded(c, a, b, d), this.fireEvent(new EventObject(Event.FOLD_CELLS, "collapse", a, "recurse", b, "cells", c))
	} finally {
		this.model.endUpdate()
	}
	return c
};
Graph.prototype.cellsFolded = function(a, b, c, d) {
	if (null != a && 0 < a.length) {
		this.model.beginUpdate();
		try {
			for (var e = 0; e < a.length; e++) if ((!d || this.isCellFoldable(a[e], b)) && b != this.isCellCollapsed(a[e])) if (this.model.setCollapsed(a[e], b), this.swapBounds(a[e], b), this.isExtendParent(a[e]) && this.extendParent(a[e]), c) {
				var f = this.model.getChildren(a[e]);
				this.foldCells(f, b, c)
			}
			this.fireEvent(new EventObject(Event.CELLS_FOLDED, "cells", a, "collapse", b, "recurse", c))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.swapBounds = function(a, b) {
	if (null != a) {
		var c = this.model.getGeometry(a);
		null != c && (c = c.clone(), this.updateAlternateBounds(a, c, b), c.swap(), this.model.setGeometry(a, c))
	}
};
Graph.prototype.updateAlternateBounds = function(a, b, c) {
	if (null != a && null != b) {
		c = this.view.getState(a);
		c = null != c ? c.style : this.getCellStyle(a);
		if (null == b.alternateBounds) {
			var d = b;
			this.collapseToPreferredSize && (a = this.getPreferredSizeForCell(a), null != a && (d = a, a = Utils.getValue(c, Constants.STYLE_STARTSIZE), 0 < a && (d.height = Math.max(d.height, a))));
			b.alternateBounds = new Rectangle(0, 0, d.width, d.height)
		}
		if (null != b.alternateBounds) {
			b.alternateBounds.x = b.x;
			b.alternateBounds.y = b.y;
			var e = Utils.toRadians(c[Constants.STYLE_ROTATION] || 0);
			0 != e && (a = b.alternateBounds.getCenterX() - b.getCenterX(), c = b.alternateBounds.getCenterY() - b.getCenterY(), d = Math.cos(e), e = Math.sin(e), b.alternateBounds.x += d * a - e * c - a, b.alternateBounds.y += e * a + d * c - c)
		}
	}
};
Graph.prototype.addAllEdges = function(a) {
	var b = a.slice();
	return b = b.concat(this.getAllEdges(a))
};
Graph.prototype.getAllEdges = function(a) {
	var b = [];
	if (null != a) for (var c = 0; c < a.length; c++) {
		for (var d = this.model.getEdgeCount(a[c]), e = 0; e < d; e++) b.push(this.model.getEdgeAt(a[c], e));
		d = this.model.getChildren(a[c]);
		b = b.concat(this.getAllEdges(d))
	}
	return b
};
Graph.prototype.updateCellSize = function(a, b) {
	b = null != b ? b : !1;
	this.model.beginUpdate();
	try {
		this.cellSizeUpdated(a, b), this.fireEvent(new EventObject(Event.UPDATE_CELL_SIZE, "cell", a, "ignoreChildren", b))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cellSizeUpdated = function(a, b) {
	if (null != a) {
		this.model.beginUpdate();
		try {
			var c = this.getPreferredSizeForCell(a),
				d = this.model.getGeometry(a);
			if (null != c && null != d) {
				var e = this.isCellCollapsed(a),
					d = d.clone();
				if (this.isSwimlane(a)) {
					var f = this.view.getState(a),
						g = null != f ? f.style : this.getCellStyle(a),
						k = this.model.getStyle(a);
					null == k && (k = "");
					Utils.getValue(g, Constants.STYLE_HORIZONTAL, !0) ? (k = Utils.setStyle(k, Constants.STYLE_STARTSIZE, c.height + 8), e && (d.height = c.height + 8), d.width = c.width) : (k = Utils.setStyle(k, Constants.STYLE_STARTSIZE, c.width + 8), e && (d.width = c.width + 8), d.height = c.height);
					this.model.setStyle(a, k)
				} else d.width = c.width, d.height = c.height;
				if (!b && !e) {
					var l = this.view.getBounds(this.model.getChildren(a));
					if (null != l) {
						var m = this.view.translate,
							n = this.view.scale,
							p = (l.y + l.height) / n - d.y - m.y;
						d.width = Math.max(d.width, (l.x + l.width) / n - d.x - m.x);
						d.height = Math.max(d.height, p)
					}
				}
				this.cellsResized([a], [d], !1)
			}
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.getPreferredSizeForCell = function(a) {
	var b = null;
	if (null != a) {
		var c = this.view.getState(a),
			d = null != c ? c.style : this.getCellStyle(a);
		if (null != d && !this.model.isEdge(a)) {
			var e = d[Constants.STYLE_FONTSIZE] || Constants.DEFAULT_FONTSIZE,
				f = 0,
				b = 0;
			if ((null != this.getImage(c) || null != d[Constants.STYLE_IMAGE]) && d[Constants.STYLE_SHAPE] == Constants.SHAPE_LABEL) d[Constants.STYLE_VERTICAL_ALIGN] == Constants.ALIGN_MIDDLE && (f += parseFloat(d[Constants.STYLE_IMAGE_WIDTH]) || Label.prototype.imageSize), d[Constants.STYLE_ALIGN] != Constants.ALIGN_CENTER && (b += parseFloat(d[Constants.STYLE_IMAGE_HEIGHT]) || Label.prototype.imageSize);
			f += 2 * (d[Constants.STYLE_SPACING] || 0);
			f += d[Constants.STYLE_SPACING_LEFT] || 0;
			f += d[Constants.STYLE_SPACING_RIGHT] || 0;
			b += 2 * (d[Constants.STYLE_SPACING] || 0);
			b += d[Constants.STYLE_SPACING_TOP] || 0;
			b += d[Constants.STYLE_SPACING_BOTTOM] || 0;
			c = this.getFoldingImage(c);
			null != c && (f += c.width + 8);
			a = this.getLabel(a);
			null != a && 0 < a.length ? (a = a.replace(/\n/g, "<br>"), a = Utils.getSizeForString(a, e, d[Constants.STYLE_FONTFAMILY]), f = a.width + f, b = a.height + b, Utils.getValue(d, Constants.STYLE_HORIZONTAL, !0) || (d = b, b = f, f = d), this.gridEnabled && (f = this.snap(f + this.gridSize / 2), b = this.snap(b + this.gridSize / 2)), b = new Rectangle(0, 0, f, b)) : (d = 4 * this.gridSize, b = new Rectangle(0, 0, d, d))
		}
	}
	return b
};
Graph.prototype.resizeCell = function(a, b, c) {
	return this.resizeCells([a], [b], c)[0]
};
Graph.prototype.resizeCells = function(a, b, c) {
	c = null != c ? c : this.isRecursiveResize();
	this.model.beginUpdate();
	try {
		this.cellsResized(a, b, c), this.fireEvent(new EventObject(Event.RESIZE_CELLS, "cells", a, "bounds", b))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cellsResized = function(a, b, c) {
	c = null != c ? c : !1;
	if (null != a && null != b && a.length == b.length) {
		this.model.beginUpdate();
		try {
			for (var d = 0; d < a.length; d++) this.cellResized(a[d], b[d], !1, c), this.isExtendParent(a[d]) ? this.extendParent(a[d]) : this.isConstrainChildrenOnResize() && this.constrainChild(a[d]);
			this.resetEdgesOnResize && this.resetEdges(a);
			this.fireEvent(new EventObject(Event.CELLS_RESIZED, "cells", a, "bounds", b))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.cellResized = function(a, b, c, d) {
	var e = this.model.getGeometry(a);
	if (null != e && (e.x != b.x || e.y != b.y || e.width != b.width || e.height != b.height)) {
		e = e.clone();
		!c && e.relative ? (c = e.offset, null != c && (c.x += b.x - e.x, c.y += b.y - e.y)) : (e.x = b.x, e.y = b.y);
		e.width = b.width;
		e.height = b.height;
		!e.relative && (this.model.isVertex(a) && !this.isAllowNegativeCoordinates()) && (e.x = Math.max(0, e.x), e.y = Math.max(0, e.y));
		this.model.beginUpdate();
		try {
			d && this.resizeChildCells(a, e), this.model.setGeometry(a, e), this.isConstrainChildrenOnResize() && this.constrainChildCells(a)
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.resizeChildCells = function(a, b) {
	for (var c = this.model.getGeometry(a), d = b.width / c.width, c = b.height / c.height, e = this.model.getChildCount(a), f = 0; f < e; f++) this.scaleCell(this.model.getChildAt(a, f), d, c, !0)
};
Graph.prototype.constrainChildCells = function(a) {
	for (var b = this.model.getChildCount(a), c = 0; c < b; c++) this.constrainChild(this.model.getChildAt(a, c))
};
Graph.prototype.scaleCell = function(a, b, c, d) {
	var e = this.model.getGeometry(a);
	if (null != e) {
		var e = e.clone(),
			f = e.points;
		if (null != f) {
			for (d = 0; d < f.length; d++) f[d].x *= b, f[d].y *= c;
			this.model.setGeometry(a, e)
		} else this.model.isVertex(a) && (e.relative || (e.x *= b, e.y *= c, e.width *= b, e.height *= c), this.cellResized(a, e, !0, d))
	}
};
Graph.prototype.extendParent = function(a) {
	if (null != a) {
		var b = this.model.getParent(a),
			c = this.model.getGeometry(b);
		if (null != b && (null != c && !this.isCellCollapsed(b)) && (a = this.model.getGeometry(a), null != a && (c.width < a.x + a.width || c.height < a.y + a.height))) c = c.clone(), c.width = Math.max(c.width, a.x + a.width), c.height = Math.max(c.height, a.y + a.height), this.cellsResized([b], [c], !1)
	}
};
Graph.prototype.importCells = function(a, b, c, d, e) {
	return this.moveCells(a, b, c, !0, d, e)
};
Graph.prototype.moveCells = function(a, b, c, d, e, f) {
	b = null != b ? b : 0;
	c = null != c ? c : 0;
	d = null != d ? d : !1;
	if (null != a && (0 != b || 0 != c || d || null != e)) {
		this.model.beginUpdate();
		try {
			d && (a = this.cloneCells(a, this.isCloneInvalidEdges()), null == e && (e = this.getDefaultParent()));
			var g = this.isAllowNegativeCoordinates();
			null != e && this.setAllowNegativeCoordinates(!0);
			this.cellsMoved(a, b, c, !d && this.isDisconnectOnMove() && this.isAllowDanglingEdges(), null == e, this.isExtendParentsOnMove() && null == e);
			this.setAllowNegativeCoordinates(g);
			if (null != e) {
				var k = this.model.getChildCount(e);
				this.cellsAdded(a, e, k, null, null, !0)
			}
			this.fireEvent(new EventObject(Event.MOVE_CELLS, "cells", a, "dx", b, "dy", c, "clone", d, "target", e, "event", f))
		} finally {
			this.model.endUpdate()
		}
	}
	return a
};
Graph.prototype.cellsMoved = function(a, b, c, d, e, f) {
	if (null != a && (0 != b || 0 != c)) {
		f = null != f ? f : !1;
		this.model.beginUpdate();
		try {
			d && this.disconnectGraph(a);
			for (var g = 0; g < a.length; g++) this.translateCell(a[g], b, c), f && this.isExtendParent(a[g]) ? this.extendParent(a[g]) : e && this.constrainChild(a[g]);
			this.resetEdgesOnMove && this.resetEdges(a);
			this.fireEvent(new EventObject(Event.CELLS_MOVED, "cells", a, "dx", b, "dy", c, "disconnect", d))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.translateCell = function(a, b, c) {
	var d = this.model.getGeometry(a);
	null != d && (d = d.clone(), d.translate(b, c), !d.relative && (this.model.isVertex(a) && !this.isAllowNegativeCoordinates()) && (d.x = Math.max(0, d.x), d.y = Math.max(0, d.y)), d.relative && !this.model.isEdge(a) && (null == d.offset ? d.offset = new Point(b, c) : (d.offset.x += b, d.offset.y += c)), this.model.setGeometry(a, d))
};
Graph.prototype.getCellContainmentArea = function(a) {
	if (null != a && !this.model.isEdge(a)) {
		var b = this.model.getParent(a);
		if (b == this.getDefaultParent() || b == this.getCurrentRoot()) return this.getMaximumGraphBounds();
		if (null != b && b != this.getDefaultParent()) {
			var c = this.model.getGeometry(b);
			if (null != c) {
				var d = a = 0,
					e = c.width,
					c = c.height;
				this.isSwimlane(b) && (b = this.getStartSize(b), a = b.width, e -= b.width, d = b.height, c -= b.height);
				return new Rectangle(a, d, e, c)
			}
		}
	}
	return null
};
Graph.prototype.getMaximumGraphBounds = function() {
	return this.maximumGraphBounds
};
Graph.prototype.constrainChild = function(a) {
	if (null != a) {
		var b = this.model.getGeometry(a),
			c = this.isConstrainChild(a) ? this.getCellContainmentArea(a) : this.getMaximumGraphBounds();
		if (null != b && null != c && !b.relative && (b.x < c.x || b.y < c.y || c.width < b.x + b.width || c.height < b.y + b.height)) {
			var d = this.getOverlap(a),
				b = b.clone();
			0 < c.width && (b.x = Math.min(b.x, c.x + c.width - (1 - d) * b.width));
			0 < c.height && (b.y = Math.min(b.y, c.y + c.height - (1 - d) * b.height));
			b.x = Math.max(b.x, c.x - b.width * d);
			b.y = Math.max(b.y, c.y - b.height * d);
			b.width = Math.min(b.width, c.width);
			b.height = Math.min(b.height, c.height);
			this.model.setGeometry(a, b)
		}
	}
};
Graph.prototype.resetEdges = function(a) {
	if (null != a) {
		for (var b = {}, c = 0; c < a.length; c++) {
			var d = CellPath.create(a[c]);
			b[d] = a[c]
		}
		this.model.beginUpdate();
		try {
			for (c = 0; c < a.length; c++) {
				var e = this.model.getEdges(a[c]);
				if (null != e) for (d = 0; d < e.length; d++) {
					var f = this.view.getState(e[d]),
						g = null != f ? f.getVisibleTerminal(!0) : this.view.getVisibleTerminal(e[d], !0),
						k = null != f ? f.getVisibleTerminal(!1) : this.view.getVisibleTerminal(e[d], !1),
						l = CellPath.create(g),
						m = CellPath.create(k);
					(null == b[l] || null == b[m]) && this.resetEdge(e[d])
				}
				this.resetEdges(this.model.getChildren(a[c]))
			}
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.resetEdge = function(a) {
	var b = this.model.getGeometry(a);
	null != b && (null != b.points && 0 < b.points.length) && (b = b.clone(), b.points = [], this.model.setGeometry(a, b));
	return a
};
Graph.prototype.getOutlineConstraint = function(a, b, c) {
	if (null != b.shape) {
		c = this.view.getPerimeterBounds(b);
		var d = b.style[Constants.STYLE_DIRECTION];
		if (d == Constants.DIRECTION_NORTH || d == Constants.DIRECTION_SOUTH) {
			c.x += c.width / 2 - c.height / 2;
			c.y += c.height / 2 - c.width / 2;
			var e = c.width;
			c.width = c.height;
			c.height = e
		}
		var f = Utils.toRadians(b.shape.getShapeRotation());
		if (0 != f) {
			var e = Math.cos(-f),
				f = Math.sin(-f),
				g = new Point(c.getCenterX(), c.getCenterY());
			a = Utils.getRotatedPoint(a, e, f, g)
		}
		var g = f = 1,
			k = 0,
			l = 0;
		if (this.getModel().isVertex(b.cell)) {
			var m = b.style[Constants.STYLE_FLIPH],
				n = b.style[Constants.STYLE_FLIPV];
			null != b.shape && null != b.shape.stencil && (m = 1 == Utils.getValue(b.style, "stencilFlipH", 0) || m, n = 1 == Utils.getValue(b.style, "stencilFlipV", 0) || n);
			if (d == Constants.DIRECTION_NORTH || d == Constants.DIRECTION_SOUTH) e = m, m = n, n = e;
			m && (f = -1, k = -c.width);
			n && (g = -1, l = -c.height)
		}
		a = new Point((a.x - c.x) * f - k + c.x, (a.y - c.y) * g - l + c.y);
		b = Math.round(1E3 * (a.x - c.x) / c.width) / 1E3;
		a = Math.round(1E3 * (a.y - c.y) / c.height) / 1E3;
		return new ConnectionConstraint(new Point(b, a), !1)
	}
	return null
};
Graph.prototype.getAllConnectionConstraints = function(a, b) {
	return null != a && null != a.shape && null != a.shape.stencil ? a.shape.stencil.constraints : null
};
Graph.prototype.getConnectionConstraint = function(a, b, c) {
	b = null;
	var d = a.style[c ? Constants.STYLE_EXIT_X : Constants.STYLE_ENTRY_X];
	if (null != d) {
		var e = a.style[c ? Constants.STYLE_EXIT_Y : Constants.STYLE_ENTRY_Y];
		null != e && (b = new Point(parseFloat(d), parseFloat(e)))
	}
	d = !1;
	null != b && (d = Utils.getValue(a.style, c ? Constants.STYLE_EXIT_PERIMETER : Constants.STYLE_ENTRY_PERIMETER, !0));
	return new ConnectionConstraint(b, d)
};
Graph.prototype.setConnectionConstraint = function(a, b, c, d) {
	if (null != d) {
		this.model.beginUpdate();
		try {
			null == d || null == d.point ? (this.setCellStyles(c ? Constants.STYLE_EXIT_X : Constants.STYLE_ENTRY_X, null, [a]), this.setCellStyles(c ? Constants.STYLE_EXIT_Y : Constants.STYLE_ENTRY_Y, null, [a]), this.setCellStyles(c ? Constants.STYLE_EXIT_PERIMETER : Constants.STYLE_ENTRY_PERIMETER, null, [a])) : null != d.point && (this.setCellStyles(c ? Constants.STYLE_EXIT_X : Constants.STYLE_ENTRY_X, d.point.x, [a]), this.setCellStyles(c ? Constants.STYLE_EXIT_Y : Constants.STYLE_ENTRY_Y, d.point.y, [a]), d.perimeter ? this.setCellStyles(c ? Constants.STYLE_EXIT_PERIMETER : Constants.STYLE_ENTRY_PERIMETER, null, [a]) : this.setCellStyles(c ? Constants.STYLE_EXIT_PERIMETER : Constants.STYLE_ENTRY_PERIMETER, "0", [a]))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.getConnectionPoint = function(a, b) {
	var c = null;
	if (null != a) {
		var d = this.view.getPerimeterBounds(a),
			e = new Point(d.getCenterX(), d.getCenterY()),
			f = a.style[Constants.STYLE_DIRECTION],
			g = 0;
		if (null != f && (f == Constants.DIRECTION_NORTH ? g += 270 : f == Constants.DIRECTION_WEST ? g += 180 : f == Constants.DIRECTION_SOUTH && (g += 90), f == Constants.DIRECTION_NORTH || f == Constants.DIRECTION_SOUTH)) {
			d.x += d.width / 2 - d.height / 2;
			d.y += d.height / 2 - d.width / 2;
			var k = d.width;
			d.width = d.height;
			d.height = k
		}
		if (null != b.point) {
			var l = c = 1,
				m = 0,
				n = 0;
			if (this.getModel().isVertex(a.cell)) {
				var p = a.style[Constants.STYLE_FLIPH],
					q = a.style[Constants.STYLE_FLIPV];
				null != a.shape && null != a.shape.stencil && (p = 1 == Utils.getValue(a.style, "stencilFlipH", 0) || p, q = 1 == Utils.getValue(a.style, "stencilFlipV", 0) || q);
				if ("north" == f || "south" == f) k = p, p = q, q = k;
				p && (c = -1, m = -d.width);
				q && (l = -1, n = -d.height)
			}
			c = new Point(d.x + b.point.x * d.width * c - m, d.y + b.point.y * d.height * l - n)
		}
		f = a.style[Constants.STYLE_ROTATION] || 0;
		b.perimeter ? (0 != g && null != c && (k = d = 0, 90 == g ? k = 1 : 180 == g ? d = -1 : 270 == f && (k = -1), c = Utils.getRotatedPoint(c, d, k, e)), null != c && b.perimeter && (c = this.view.getPerimeterPoint(a, c, !1))) : f += g;
		0 != f && null != c && (g = Utils.toRadians(f), d = Math.cos(g), k = Math.sin(g), c = Utils.getRotatedPoint(c, d, k, e))
	}
	return c
};
Graph.prototype.connectCell = function(a, b, c, d) {
	this.model.beginUpdate();
	try {
		var e = this.model.getTerminal(a, c);
		this.cellConnected(a, b, c, d);
		this.fireEvent(new EventObject(Event.CONNECT_CELL, "edge", a, "terminal", b, "source", c, "previous", e))
	} finally {
		this.model.endUpdate()
	}
	return a
};
Graph.prototype.cellConnected = function(a, b, c, d) {
	if (null != a) {
		this.model.beginUpdate();
		try {
			var e = this.model.getTerminal(a, c);
			this.setConnectionConstraint(a, b, c, d);
			this.isPortsEnabled() && (d = null, this.isPort(b) && (d = b.getId(), b = this.getTerminalForPort(b, c)), this.setCellStyles(c ? Constants.STYLE_SOURCE_PORT : Constants.STYLE_TARGET_PORT, d, [a]));
			this.model.setTerminal(a, b, c);
			this.resetEdgesOnConnect && this.resetEdge(a);
			this.fireEvent(new EventObject(Event.CELL_CONNECTED, "edge", a, "terminal", b, "source", c, "previous", e))
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.disconnectGraph = function(a) {
	if (null != a) {
		this.model.beginUpdate();
		try {
			for (var b = this.view.scale, c = this.view.translate, d = {}, e = 0; e < a.length; e++) {
				var f = CellPath.create(a[e]);
				d[f] = a[e]
			}
			for (e = 0; e < a.length; e++) if (this.model.isEdge(a[e])) {
				var g = this.model.getGeometry(a[e]);
				if (null != g) {
					var k = this.view.getState(a[e]),
						l = this.view.getState(this.model.getParent(a[e]));
					if (null != k && null != l) {
						var g = g.clone(),
							m = -l.origin.x,
							n = -l.origin.y,
							p = k.absolutePoints,
							q = this.model.getTerminal(a[e], !0);
						if (null != q && this.isCellDisconnectable(a[e], q, !0)) {
							for (var r = CellPath.create(q); null != q && null == d[r];) q = this.model.getParent(q), r = CellPath.create(q);
							null == q && (g.setTerminalPoint(new Point(p[0].x / b - c.x + m, p[0].y / b - c.y + n), !0), this.model.setTerminal(a[e], null, !0))
						}
						var s = this.model.getTerminal(a[e], !1);
						if (null != s && this.isCellDisconnectable(a[e], s, !1)) {
							for (var t = CellPath.create(s); null != s && null == d[t];) s = this.model.getParent(s), t = CellPath.create(s);
							if (null == s) {
								var u = p.length - 1;
								g.setTerminalPoint(new Point(p[u].x / b - c.x + m, p[u].y / b - c.y + n), !1);
								this.model.setTerminal(a[e], null, !1)
							}
						}
						this.model.setGeometry(a[e], g)
					}
				}
			}
		} finally {
			this.model.endUpdate()
		}
	}
};
Graph.prototype.getCurrentRoot = function() {
	return this.view.currentRoot
};
Graph.prototype.getTranslateForRoot = function(a) {
	return null
};
Graph.prototype.isPort = function(a) {
	return !1
};
Graph.prototype.getTerminalForPort = function(a, b) {
	return this.model.getParent(a)
};
Graph.prototype.getChildOffsetForCell = function(a) {
	return null
};
Graph.prototype.enterGroup = function(a) {
	a = a || this.getSelectionCell();
	null != a && this.isValidRoot(a) && (this.view.setCurrentRoot(a), this.clearSelection())
};
Graph.prototype.exitGroup = function() {
	var a = this.model.getRoot(),
		b = this.getCurrentRoot();
	if (null != b) {
		for (var c = this.model.getParent(b); c != a && !this.isValidRoot(c) && this.model.getParent(c) != a;) c = this.model.getParent(c);
		c == a || this.model.getParent(c) == a ? this.view.setCurrentRoot(null) : this.view.setCurrentRoot(c);
		null != this.view.getState(b) && this.setSelectionCell(b)
	}
};
Graph.prototype.home = function() {
	var a = this.getCurrentRoot();
	null != a && (this.view.setCurrentRoot(null), null != this.view.getState(a) && this.setSelectionCell(a))
};
Graph.prototype.isValidRoot = function(a) {
	return null != a
};
Graph.prototype.getGraphBounds = function() {
	return this.view.getGraphBounds()
};
Graph.prototype.getCellBounds = function(a, b, c) {
	var d = [a];
	b && (d = d.concat(this.model.getEdges(a)));
	d = this.view.getBounds(d);
	if (c) {
		c = this.model.getChildCount(a);
		for (var e = 0; e < c; e++) {
			var f = this.getCellBounds(this.model.getChildAt(a, e), b, !0);
			null != d ? d.add(f) : d = f
		}
	}
	return d
};
Graph.prototype.getBoundingBoxFromGeometry = function(a, b) {
	b = null != b ? b : !1;
	var c = null;
	if (null != a) for (var d = 0; d < a.length; d++) if (b || this.model.isVertex(a[d])) {
		var e = this.getCellGeometry(a[d]);
		if (null != e) {
			var f = e.points,
				g = null;
			if (this.model.isEdge(a[d])) {
				if (null != f && 0 < f.length) {
					for (var k = new Rectangle(f[0].x, f[0].y, 0, 0), g = function(a) {
							null != a && k.add(new Rectangle(a.x, a.y, 0, 0))
						}, l = 1; l < f.length; l++) g(f[l]);
					g(e.getTerminalPoint(!0));
					g(e.getTerminalPoint(!1));
					g = k
				}
			} else g = e;
			null != g && (null == c ? c = new Rectangle(g.x, g.y, g.width, g.height) : c.add(g))
		}
	}
	return c
};
Graph.prototype.refresh = function(a) {
	this.view.clear(a, null == a);
	this.view.validate();
	this.sizeDidChange();
	this.fireEvent(new EventObject(Event.REFRESH))
};
Graph.prototype.snap = function(a) {
	this.gridEnabled && (a = Math.round(a / this.gridSize) * this.gridSize);
	return a
};
Graph.prototype.panGraph = function(a, b) {
	if (this.useScrollbarsForPanning && Utils.hasScrollbars(this.container)) this.container.scrollLeft = -a, this.container.scrollTop = -b;
	else {
		var c = this.view.getCanvas();
		if (this.dialect == Constants.DIALECT_SVG) if (0 == a && 0 == b) {
			if (Client.IS_IE ? c.setAttribute("transform", "translate(" + a + "," + b + ")") : c.removeAttribute("transform"), null != this.shiftPreview1) {
				for (var d = this.shiftPreview1.firstChild; null != d;) {
					var e = d.nextSibling;
					this.container.appendChild(d);
					d = e
				}
				null != this.shiftPreview1.parentNode && this.shiftPreview1.parentNode.removeChild(this.shiftPreview1);
				this.shiftPreview1 = null;
				this.container.appendChild(c.parentNode);
				for (d = this.shiftPreview2.firstChild; null != d;) e = d.nextSibling, this.container.appendChild(d), d = e;
				null != this.shiftPreview2.parentNode && this.shiftPreview2.parentNode.removeChild(this.shiftPreview2);
				this.shiftPreview2 = null
			}
		} else {
			c.setAttribute("transform", "translate(" + a + "," + b + ")");
			if (null == this.shiftPreview1) {
				this.shiftPreview1 = document.createElement("div");
				this.shiftPreview1.style.position = "absolute";
				this.shiftPreview1.style.overflow = "visible";
				this.shiftPreview2 = document.createElement("div");
				this.shiftPreview2.style.position = "absolute";
				this.shiftPreview2.style.overflow = "visible";
				for (var f = this.shiftPreview1, d = this.container.firstChild; null != d;) e = d.nextSibling, d != c.parentNode ? f.appendChild(d) : f = this.shiftPreview2, d = e;
				null != this.shiftPreview1.firstChild && this.container.insertBefore(this.shiftPreview1, c.parentNode);
				null != this.shiftPreview2.firstChild && this.container.appendChild(this.shiftPreview2)
			}
			this.shiftPreview1.style.left = a + "px";
			this.shiftPreview1.style.top = b + "px";
			this.shiftPreview2.style.left = a + "px";
			this.shiftPreview2.style.top = b + "px"
		} else c.style.left = a + "px", c.style.top = b + "px";
		this.panDx = a;
		this.panDy = b;
		this.fireEvent(new EventObject(Event.PAN))
	}
};
Graph.prototype.zoomIn = function() {
	this.zoom(this.zoomFactor)
};
Graph.prototype.zoomOut = function() {
	this.zoom(1 / this.zoomFactor)
};
Graph.prototype.zoomActual = function() {
	1 == this.view.scale ? this.view.setTranslate(0, 0) : (this.view.translate.x = 0, this.view.translate.y = 0, this.view.setScale(1))
};
Graph.prototype.zoomTo = function(a, b) {
	this.zoom(a / this.view.scale, b)
};
Graph.prototype.center = function() {
	var a = Utils.hasScrollbars(this.container),
		b = this.container.clientWidth,
		c = this.container.clientHeight,
		d = this.getGraphBounds(),
		e = this.view.translate;
	d.x -= e.x;
	d.y -= e.y;
	var e = b - d.width,
		f = c - d.height;
	if (a) {
		var a = this.container.scrollWidth,
			g = this.container.scrollHeight;
		a > b && (e = 0);
		g > c && (f = 0);
		this.view.setTranslate(e / 2 - d.x, f / 2 - d.y);
		this.container.scrollLeft = (a - b) / 2;
		this.container.scrollTop = (g - c) / 2
	} else this.view.setTranslate(e / 2 - d.x, f / 2 - d.y)
};
Graph.prototype.zoom = function(a, b) {
	b = null != b ? b : this.centerZoom;
	var c = Math.round(100 * this.view.scale * a) / 100,
		d = this.view.getState(this.getSelectionCell());
	a = c / this.view.scale;
	if (this.keepSelectionVisibleOnZoom && null != d) d = new Rectangle(d.x * a, d.y * a, d.width * a, d.height * a), this.view.scale = c, this.scrollRectToVisible(d) || (this.view.revalidate(), this.view.setScale(c));
	else if (d = Utils.hasScrollbars(this.container), b && !d) {
		var d = this.container.offsetWidth,
			e = this.container.offsetHeight;
		if (1 < a) var f = (a - 1) / (2 * c),
			d = d * -f,
			e = e * -f;
		else f = (1 / a - 1) / (2 * this.view.scale), d *= f, e *= f;
		this.view.scaleAndTranslate(c, this.view.translate.x + d, this.view.translate.y + e)
	} else {
		var f = this.view.translate.x,
			g = this.view.translate.y,
			k = this.container.scrollLeft,
			l = this.container.scrollTop;
		this.view.setScale(c);
		d && (e = d = 0, b && (d = this.container.offsetWidth * (a - 1) / 2, e = this.container.offsetHeight * (a - 1) / 2), this.container.scrollLeft = (this.view.translate.x - f) * this.view.scale + Math.round(k * a + d), this.container.scrollTop = (this.view.translate.y - g) * this.view.scale + Math.round(l * a + e))
	}
};
Graph.prototype.zoomToRect = function(a) {
	var b = this.container.clientWidth / a.width / (this.container.clientHeight / a.height);
	a.x = Math.max(0, a.x);
	a.y = Math.max(0, a.y);
	var c = Math.min(this.container.scrollWidth, a.x + a.width),
		d = Math.min(this.container.scrollHeight, a.y + a.height);
	a.width = c - a.x;
	a.height = d - a.y;
	1 > b ? (b = a.height / b, c = (b - a.height) / 2, a.height = b, b = Math.min(a.y, c), a.y -= b, d = Math.min(this.container.scrollHeight, a.y + a.height), a.height = d - a.y) : (b *= a.width, c = (b - a.width) / 2, a.width = b, b = Math.min(a.x, c), a.x -= b, c = Math.min(this.container.scrollWidth, a.x + a.width), a.width = c - a.x);
	b = this.container.clientWidth / a.width;
	c = this.view.scale * b;
	Utils.hasScrollbars(this.container) ? (this.view.setScale(c), this.container.scrollLeft = Math.round(a.x * b), this.container.scrollTop = Math.round(a.y * b)) : this.view.scaleAndTranslate(c, this.view.translate.x - a.x / this.view.scale, this.view.translate.y - a.y / this.view.scale)
};
Graph.prototype.fit = function(a, b) {
	if (null != this.container) {
		a = null != a ? a : 0;
		b = null != b ? b : !1;
		var c = this.container.clientWidth,
			d = this.container.clientHeight,
			e = this.view.getGraphBounds();
		b && (null != e.x && null != e.y) && (e.width += e.x, e.height += e.y, e.x = 0, e.y = 0);
		var f = this.view.scale,
			g = e.width / f,
			k = e.height / f;
		null != this.backgroundImage && (g = Math.max(g, this.backgroundImage.width - e.x / f), k = Math.max(k, this.backgroundImage.height - e.y / f));
		var l = b ? a : 2 * a,
			c = Math.floor(100 * Math.min(c / (g + l), d / (k + l))) / 100;
		null != this.minFitScale && (c = Math.max(c, this.minFitScale));
		null != this.maxFitScale && (c = Math.min(c, this.maxFitScale));
		b ? this.view.scale != c && this.view.setScale(c) : Utils.hasScrollbars(this.container) ? (this.view.setScale(c), e = this.getGraphBounds(), null != e.x && (this.container.scrollLeft = e.x), null != e.y && (this.container.scrollTop = e.y)) : this.view.scaleAndTranslate(c, null != e.x ? Math.floor(this.view.translate.x - e.x / f + a + 1) : a, null != e.y ? Math.floor(this.view.translate.y - e.y / f + a + 1) : a)
	}
	return this.view.scale
};
Graph.prototype.scrollCellToVisible = function(a, b) {
	var c = -this.view.translate.x,
		d = -this.view.translate.y,
		e = this.view.getState(a);
	null != e && (c = new Rectangle(c + e.x, d + e.y, e.width, e.height), b && null != this.container && (d = this.container.clientWidth, e = this.container.clientHeight, c.x = c.getCenterX() - d / 2, c.width = d, c.y = c.getCenterY() - e / 2, c.height = e), this.scrollRectToVisible(c) && this.view.setTranslate(this.view.translate.x, this.view.translate.y))
};
Graph.prototype.scrollRectToVisible = function(a) {
	var b = !1;
	if (null != a) {
		var c = this.container.offsetWidth,
			d = this.container.offsetHeight,
			e = Math.min(c, a.width),
			f = Math.min(d, a.height);
		if (Utils.hasScrollbars(this.container)) {
			c = this.container;
			a.x += this.view.translate.x;
			a.y += this.view.translate.y;
			var g = c.scrollLeft - a.x,
				d = Math.max(g - c.scrollLeft, 0);
			0 < g ? c.scrollLeft -= g + 2 : (g = a.x + e - c.scrollLeft - c.clientWidth, 0 < g && (c.scrollLeft += g + 2));
			e = c.scrollTop - a.y;
			g = Math.max(0, e - c.scrollTop);
			0 < e ? c.scrollTop -= e + 2 : (e = a.y + f - c.scrollTop - c.clientHeight, 0 < e && (c.scrollTop += e + 2));
			!this.useScrollbarsForPanning && (0 != d || 0 != g) && this.view.setTranslate(d, g)
		} else {
			var g = -this.view.translate.x,
				k = -this.view.translate.y,
				l = this.view.scale;
			a.x + e > g + c && (this.view.translate.x -= (a.x + e - c - g) / l, b = !0);
			a.y + f > k + d && (this.view.translate.y -= (a.y + f - d - k) / l, b = !0);
			a.x < g && (this.view.translate.x += (g - a.x) / l, b = !0);
			a.y < k && (this.view.translate.y += (k - a.y) / l, b = !0);
			b && (this.view.refresh(), null != this.selectionCellsHandler && this.selectionCellsHandler.refresh())
		}
	}
	return b
};
Graph.prototype.getCellGeometry = function(a) {
	return this.model.getGeometry(a)
};
Graph.prototype.isCellVisible = function(a) {
	return this.model.isVisible(a)
};
Graph.prototype.isCellCollapsed = function(a) {
	return this.model.isCollapsed(a)
};
Graph.prototype.isCellConnectable = function(a) {
	return this.model.isConnectable(a)
};
Graph.prototype.isOrthogonal = function(a) {
	var b = a.style[Constants.STYLE_ORTHOGONAL];
	if (null != b) return b;
	a = this.view.getEdgeStyle(a);
	return a == EdgeStyle.SegmentConnector || a == EdgeStyle.ElbowConnector || a == EdgeStyle.SideToSide || a == EdgeStyle.TopToBottom || a == EdgeStyle.EntityRelation || a == EdgeStyle.OrthConnector
};
Graph.prototype.isLoop = function(a) {
	var b = a.getVisibleTerminalState(!0);
	a = a.getVisibleTerminalState(!1);
	return null != b && b == a
};
Graph.prototype.isCloneEvent = function(a) {
	return Event.isControlDown(a)
};
Graph.prototype.isToggleEvent = function(a) {
	return Client.IS_MAC ? Event.isMetaDown(a) : Event.isControlDown(a)
};
Graph.prototype.isGridEnabledEvent = function(a) {
	return null != a && !Event.isAltDown(a)
};
Graph.prototype.isConstrainedEvent = function(a) {
	return Event.isShiftDown(a)
};
Graph.prototype.validationAlert = function(a) {
	Utils.alert(a)
};
Graph.prototype.isEdgeValid = function(a, b, c) {
	return null == this.getEdgeValidationError(a, b, c)
};
Graph.prototype.getEdgeValidationError = function(a, b, c) {
	if (null != a && !this.isAllowDanglingEdges() && (null == b || null == c)) return "";
	if (null != a && null == this.model.getTerminal(a, !0) && null == this.model.getTerminal(a, !1)) return null;
	if (!this.allowLoops && b == c && null != b || !this.isValidConnection(b, c)) return "";
	if (null != b && null != c) {
		var d = "";
		if (!this.multigraph) {
			var e = this.model.getEdgesBetween(b, c, !0);
			if (1 < e.length || 1 == e.length && e[0] != a) d += (Resources.get(this.alreadyConnectedResource) || this.alreadyConnectedResource) + "\n"
		}
		var e = this.model.getDirectedEdgeCount(b, !0, a),
			f = this.model.getDirectedEdgeCount(c, !1, a);
		if (null != this.multiplicities) for (var g = 0; g < this.multiplicities.length; g++) {
			var k = this.multiplicities[g].check(this, a, b, c, e, f);
			null != k && (d += k)
		}
		k = this.validateEdge(a, b, c);
		null != k && (d += k);
		return 0 < d.length ? d : null
	}
	return this.allowDanglingEdges ? null : ""
};
Graph.prototype.validateEdge = function(a, b, c) {
	return null
};
Graph.prototype.validateGraph = function(a, b) {
	a = null != a ? a : this.model.getRoot();
	b = null != b ? b : {};
	for (var c = !0, d = this.model.getChildCount(a), e = 0; e < d; e++) {
		var f = this.model.getChildAt(a, e),
			g = b;
		this.isValidRoot(f) && (g = {});
		g = this.validateGraph(f, g);
		null != g ? this.setCellWarning(f, g.replace(/\n/g, "<br>")) : this.setCellWarning(f, null);
		c = c && null == g
	}
	d = "";
	this.isCellCollapsed(a) && !c && (d += (Resources.get(this.containsValidationErrorsResource) || this.containsValidationErrorsResource) + "\n");
	d = this.model.isEdge(a) ? d + (this.getEdgeValidationError(a, this.model.getTerminal(a, !0), this.model.getTerminal(a, !1)) || "") : d + (this.getCellValidationError(a) || "");
	e = this.validateCell(a, b);
	null != e && (d += e);
	null == this.model.getParent(a) && this.view.validate();
	return 0 < d.length || !c ? d : null
};
Graph.prototype.getCellValidationError = function(a) {
	var b = this.model.getDirectedEdgeCount(a, !0),
		c = this.model.getDirectedEdgeCount(a, !1);
	a = this.model.getValue(a);
	var d = "";
	if (null != this.multiplicities) for (var e = 0; e < this.multiplicities.length; e++) {
		var f = this.multiplicities[e];
		if (f.source && Utils.isNode(a, f.type, f.attr, f.value) && (0 == f.max && 0 < b || 1 == f.min && 0 == b || 1 == f.max && 1 < b)) d += f.countError + "\n";
		else if (!f.source && Utils.isNode(a, f.type, f.attr, f.value) && (0 == f.max && 0 < c || 1 == f.min && 0 == c || 1 == f.max && 1 < c)) d += f.countError + "\n"
	}
	return 0 < d.length ? d : null
};
Graph.prototype.validateCell = function(a, b) {
	return null
};
Graph.prototype.getBackgroundImage = function() {
	return this.backgroundImage
};
Graph.prototype.setBackgroundImage = function(a) {
	this.backgroundImage = a
};
Graph.prototype.getFoldingImage = function(a) {
	if (null != a && this.foldingEnabled && !this.getModel().isEdge(a.cell)) {
		var b = this.isCellCollapsed(a.cell);
		if (this.isCellFoldable(a.cell, !b)) return b ? this.collapsedImage : this.expandedImage
	}
	return null
};
Graph.prototype.convertValueToString = function(a) {
	a = this.model.getValue(a);
	if (null != a) {
		if (Utils.isNode(a)) return a.nodeName;
		if ("function" == typeof a.toString) return a.toString()
	}
	return ""
};
Graph.prototype.getLabel = function(a) {
	var b = "";
	if (this.labelsVisible && null != a) {
		var c = this.view.getState(a),
			c = null != c ? c.style : this.getCellStyle(a);
		Utils.getValue(c, Constants.STYLE_NOLABEL, !1) || (b = this.convertValueToString(a))
	}
	return b
};
Graph.prototype.isHtmlLabel = function(a) {
	return this.isHtmlLabels()
};
Graph.prototype.isHtmlLabels = function() {
	return this.htmlLabels
};
Graph.prototype.setHtmlLabels = function(a) {
	this.htmlLabels = a
};
Graph.prototype.isWrapping = function(a) {
	var b = this.view.getState(a);
	a = null != b ? b.style : this.getCellStyle(a);
	return null != a ? "wrap" == a[Constants.STYLE_WHITE_SPACE] : !1
};
Graph.prototype.isLabelClipped = function(a) {
	var b = this.view.getState(a);
	a = null != b ? b.style : this.getCellStyle(a);
	return null != a ? "hidden" == a[Constants.STYLE_OVERFLOW] : !1
};
Graph.prototype.getTooltip = function(a, b, c, d) {
	var e = null;
	if (null != a) {
		if (null != a.control && (b == a.control.node || b.parentNode == a.control.node)) e = this.collapseExpandResource, e = Resources.get(e) || e;
		null == e && null != a.overlays && a.overlays.visit(function(a, c) {
			if (null == e && (b == c.node || b.parentNode == c.node)) e = c.overlay.toString()
		});
		null == e && (c = this.selectionCellsHandler.getHandler(a.cell), null != c && "function" == typeof c.getTooltipForNode && (e = c.getTooltipForNode(b)));
		null == e && (e = this.getTooltipForCell(a.cell))
	}
	return e
};
Graph.prototype.getTooltipForCell = function(a) {
	var b = null;
	return b = null != a && null != a.getTooltip ? a.getTooltip() : this.convertValueToString(a)
};
Graph.prototype.getCursorForMouseEvent = function(a) {
	return this.getCursorForCell(a.getCell())
};
Graph.prototype.getCursorForCell = function(a) {
	return null
};
Graph.prototype.getStartSize = function(a) {
	var b = new Rectangle,
		c = this.view.getState(a);
	a = null != c ? c.style : this.getCellStyle(a);
	null != a && (c = parseInt(Utils.getValue(a, Constants.STYLE_STARTSIZE, Constants.DEFAULT_STARTSIZE)), Utils.getValue(a, Constants.STYLE_HORIZONTAL, !0) ? b.height = c : b.width = c);
	return b
};
Graph.prototype.getImage = function(a) {
	return null != a && null != a.style ? a.style[Constants.STYLE_IMAGE] : null
};
Graph.prototype.getVerticalAlign = function(a) {
	return null != a && null != a.style ? a.style[Constants.STYLE_VERTICAL_ALIGN] || Constants.ALIGN_MIDDLE : null
};
Graph.prototype.getIndicatorColor = function(a) {
	return null != a && null != a.style ? a.style[Constants.STYLE_INDICATOR_COLOR] : null
};
Graph.prototype.getIndicatorGradientColor = function(a) {
	return null != a && null != a.style ? a.style[Constants.STYLE_INDICATOR_GRADIENTCOLOR] : null
};
Graph.prototype.getIndicatorShape = function(a) {
	return null != a && null != a.style ? a.style[Constants.STYLE_INDICATOR_SHAPE] : null
};
Graph.prototype.getIndicatorImage = function(a) {
	return null != a && null != a.style ? a.style[Constants.STYLE_INDICATOR_IMAGE] : null
};
Graph.prototype.getBorder = function() {
	return this.border
};
Graph.prototype.setBorder = function(a) {
	this.border = a
};
Graph.prototype.isSwimlane = function(a) {
	if (null != a && this.model.getParent(a) != this.model.getRoot()) {
		var b = this.view.getState(a),
			b = null != b ? b.style : this.getCellStyle(a);
		if (null != b && !this.model.isEdge(a)) return b[Constants.STYLE_SHAPE] == Constants.SHAPE_SWIMLANE
	}
	return !1
};
Graph.prototype.isResizeContainer = function() {
	return this.resizeContainer
};
Graph.prototype.setResizeContainer = function(a) {
	this.resizeContainer = a
};
Graph.prototype.isEnabled = function() {
	return this.enabled
};
Graph.prototype.setEnabled = function(a) {
	this.enabled = a
};
Graph.prototype.isEscapeEnabled = function() {
	return this.escapeEnabled
};
Graph.prototype.setEscapeEnabled = function(a) {
	this.escapeEnabled = a
};
Graph.prototype.isInvokesStopCellEditing = function() {
	return this.invokesStopCellEditing
};
Graph.prototype.setInvokesStopCellEditing = function(a) {
	this.invokesStopCellEditing = a
};
Graph.prototype.isEnterStopsCellEditing = function() {
	return this.enterStopsCellEditing
};
Graph.prototype.setEnterStopsCellEditing = function(a) {
	this.enterStopsCellEditing = a
};
Graph.prototype.isCellLocked = function(a) {
	var b = this.model.getGeometry(a);
	return this.isCellsLocked() || null != b && this.model.isVertex(a) && b.relative
};
Graph.prototype.isCellsLocked = function() {
	return this.cellsLocked
};
Graph.prototype.setCellsLocked = function(a) {
	this.cellsLocked = a
};
Graph.prototype.getCloneableCells = function(a) {
	return this.model.filterCells(a, Utils.bind(this, function(a) {
		return this.isCellCloneable(a)
	}))
};
Graph.prototype.isCellCloneable = function(a) {
	var b = this.view.getState(a);
	a = null != b ? b.style : this.getCellStyle(a);
	return this.isCellsCloneable() && 0 != a[Constants.STYLE_CLONEABLE]
};
Graph.prototype.isCellsCloneable = function() {
	return this.cellsCloneable
};
Graph.prototype.setCellsCloneable = function(a) {
	this.cellsCloneable = a
};
Graph.prototype.getExportableCells = function(a) {
	return this.model.filterCells(a, Utils.bind(this, function(a) {
		return this.canExportCell(a)
	}))
};
Graph.prototype.canExportCell = function(a) {
	return this.exportEnabled
};
Graph.prototype.getImportableCells = function(a) {
	return this.model.filterCells(a, Utils.bind(this, function(a) {
		return this.canImportCell(a)
	}))
};
Graph.prototype.canImportCell = function(a) {
	return this.importEnabled
};
Graph.prototype.isCellSelectable = function(a) {
	return this.isCellsSelectable()
};
Graph.prototype.isCellsSelectable = function() {
	return this.cellsSelectable
};
Graph.prototype.setCellsSelectable = function(a) {
	this.cellsSelectable = a
};
Graph.prototype.getDeletableCells = function(a) {
	return this.model.filterCells(a, Utils.bind(this, function(a) {
		return this.isCellDeletable(a)
	}))
};
Graph.prototype.isCellDeletable = function(a) {
	var b = this.view.getState(a);
	a = null != b ? b.style : this.getCellStyle(a);
	return this.isCellsDeletable() && 0 != a[Constants.STYLE_DELETABLE]
};
Graph.prototype.isCellsDeletable = function() {
	return this.cellsDeletable
};
Graph.prototype.setCellsDeletable = function(a) {
	this.cellsDeletable = a
};
Graph.prototype.isLabelMovable = function(a) {
	return !this.isCellLocked(a) && (this.model.isEdge(a) && this.edgeLabelsMovable || this.model.isVertex(a) && this.vertexLabelsMovable)
};
Graph.prototype.isCellRotatable = function(a) {
	var b = this.view.getState(a);
	return 0 != (null != b ? b.style : this.getCellStyle(a))[Constants.STYLE_ROTATABLE]
};
Graph.prototype.getMovableCells = function(a) {
	return this.model.filterCells(a, Utils.bind(this, function(a) {
		return this.isCellMovable(a)
	}))
};
Graph.prototype.isCellMovable = function(a) {
	var b = this.view.getState(a),
		b = null != b ? b.style : this.getCellStyle(a);
	return this.isCellsMovable() && !this.isCellLocked(a) && 0 != b[Constants.STYLE_MOVABLE]
};
Graph.prototype.isCellsMovable = function() {
	return this.cellsMovable
};
Graph.prototype.setCellsMovable = function(a) {
	this.cellsMovable = a
};
Graph.prototype.isGridEnabled = function() {
	return this.gridEnabled
};
Graph.prototype.setGridEnabled = function(a) {
	this.gridEnabled = a
};
Graph.prototype.isPortsEnabled = function() {
	return this.portsEnabled
};
Graph.prototype.setPortsEnabled = function(a) {
	this.portsEnabled = a
};
Graph.prototype.getGridSize = function() {
	return this.gridSize
};
Graph.prototype.setGridSize = function(a) {
	this.gridSize = a
};
Graph.prototype.getTolerance = function() {
	return this.tolerance
};
Graph.prototype.setTolerance = function(a) {
	this.tolerance = a
};
Graph.prototype.isVertexLabelsMovable = function() {
	return this.vertexLabelsMovable
};
Graph.prototype.setVertexLabelsMovable = function(a) {
	this.vertexLabelsMovable = a
};
Graph.prototype.isEdgeLabelsMovable = function() {
	return this.edgeLabelsMovable
};
Graph.prototype.setEdgeLabelsMovable = function(a) {
	this.edgeLabelsMovable = a
};
Graph.prototype.isSwimlaneNesting = function() {
	return this.swimlaneNesting
};
Graph.prototype.setSwimlaneNesting = function(a) {
	this.swimlaneNesting = a
};
Graph.prototype.isSwimlaneSelectionEnabled = function() {
	return this.swimlaneSelectionEnabled
};
Graph.prototype.setSwimlaneSelectionEnabled = function(a) {
	this.swimlaneSelectionEnabled = a
};
Graph.prototype.isMultigraph = function() {
	return this.multigraph
};
Graph.prototype.setMultigraph = function(a) {
	this.multigraph = a
};
Graph.prototype.isAllowLoops = function() {
	return this.allowLoops
};
Graph.prototype.setAllowDanglingEdges = function(a) {
	this.allowDanglingEdges = a
};
Graph.prototype.isAllowDanglingEdges = function() {
	return this.allowDanglingEdges
};
Graph.prototype.setConnectableEdges = function(a) {
	this.connectableEdges = a
};
Graph.prototype.isConnectableEdges = function() {
	return this.connectableEdges
};
Graph.prototype.setCloneInvalidEdges = function(a) {
	this.cloneInvalidEdges = a
};
Graph.prototype.isCloneInvalidEdges = function() {
	return this.cloneInvalidEdges
};
Graph.prototype.setAllowLoops = function(a) {
	this.allowLoops = a
};
Graph.prototype.isDisconnectOnMove = function() {
	return this.disconnectOnMove
};
Graph.prototype.setDisconnectOnMove = function(a) {
	this.disconnectOnMove = a
};
Graph.prototype.isDropEnabled = function() {
	return this.dropEnabled
};
Graph.prototype.setDropEnabled = function(a) {
	this.dropEnabled = a
};
Graph.prototype.isSplitEnabled = function() {
	return this.splitEnabled
};
Graph.prototype.setSplitEnabled = function(a) {
	this.splitEnabled = a
};
Graph.prototype.isCellResizable = function(a) {
	var b = this.view.getState(a),
		b = null != b ? b.style : this.getCellStyle(a);
	return this.isCellsResizable() && !this.isCellLocked(a) && 0 != b[Constants.STYLE_RESIZABLE]
};
Graph.prototype.isCellsResizable = function() {
	return this.cellsResizable
};
Graph.prototype.setCellsResizable = function(a) {
	this.cellsResizable = a
};
Graph.prototype.isTerminalPointMovable = function(a, b) {
	return !0
};
Graph.prototype.isCellBendable = function(a) {
	var b = this.view.getState(a),
		b = null != b ? b.style : this.getCellStyle(a);
	return this.isCellsBendable() && !this.isCellLocked(a) && 0 != b[Constants.STYLE_BENDABLE]
};
Graph.prototype.isCellsBendable = function() {
	return this.cellsBendable
};
Graph.prototype.setCellsBendable = function(a) {
	this.cellsBendable = a
};
Graph.prototype.isCellEditable = function(a) {
	var b = this.view.getState(a),
		b = null != b ? b.style : this.getCellStyle(a);
	return this.isCellsEditable() && !this.isCellLocked(a) && 0 != b[Constants.STYLE_EDITABLE]
};
Graph.prototype.isCellsEditable = function() {
	return this.cellsEditable
};
Graph.prototype.setCellsEditable = function(a) {
	this.cellsEditable = a
};
Graph.prototype.isCellDisconnectable = function(a, b, c) {
	return this.isCellsDisconnectable() && !this.isCellLocked(a)
};
Graph.prototype.isCellsDisconnectable = function() {
	return this.cellsDisconnectable
};
Graph.prototype.setCellsDisconnectable = function(a) {
	this.cellsDisconnectable = a
};
Graph.prototype.isValidSource = function(a) {
	return null == a && this.allowDanglingEdges || null != a && (!this.model.isEdge(a) || this.connectableEdges) && this.isCellConnectable(a)
};
Graph.prototype.isValidTarget = function(a) {
	return this.isValidSource(a)
};
Graph.prototype.isValidConnection = function(a, b) {
	return this.isValidSource(a) && this.isValidTarget(b)
};
Graph.prototype.setConnectable = function(a) {
	this.connectionHandler.setEnabled(a)
};
Graph.prototype.isConnectable = function(a) {
	return this.connectionHandler.isEnabled()
};
Graph.prototype.setTooltips = function(a) {
	this.tooltipHandler.setEnabled(a)
};
Graph.prototype.setPanning = function(a) {
	this.panningHandler.panningEnabled = a
};
Graph.prototype.isEditing = function(a) {
	if (null != this.cellEditor) {
		var b = this.cellEditor.getEditingCell();
		return null == a ? null != b : a == b
	}
	return !1
};
Graph.prototype.isAutoSizeCell = function(a) {
	var b = this.view.getState(a);
	a = null != b ? b.style : this.getCellStyle(a);
	return this.isAutoSizeCells() || 1 == a[Constants.STYLE_AUTOSIZE]
};
Graph.prototype.isAutoSizeCells = function() {
	return this.autoSizeCells
};
Graph.prototype.setAutoSizeCells = function(a) {
	this.autoSizeCells = a
};
Graph.prototype.isExtendParent = function(a) {
	return !this.getModel().isEdge(a) && this.isExtendParents()
};
Graph.prototype.isExtendParents = function() {
	return this.extendParents
};
Graph.prototype.setExtendParents = function(a) {
	this.extendParents = a
};
Graph.prototype.isExtendParentsOnAdd = function() {
	return this.extendParentsOnAdd
};
Graph.prototype.setExtendParentsOnAdd = function(a) {
	this.extendParentsOnAdd = a
};
Graph.prototype.isExtendParentsOnMove = function() {
	return this.extendParentsOnMove
};
Graph.prototype.setExtendParentsOnMove = function(a) {
	this.extendParentsOnMove = a
};
Graph.prototype.isRecursiveResize = function(a) {
	return this.recursiveResize
};
Graph.prototype.setRecursiveResize = function(a) {
	this.recursiveResize = a
};
Graph.prototype.isConstrainChild = function(a) {
	return this.isConstrainChildren() && !this.getModel().isEdge(this.getModel().getParent(a))
};
Graph.prototype.isConstrainChildren = function() {
	return this.constrainChildren
};
Graph.prototype.setConstrainChildrenOnResize = function(a) {
	this.constrainChildrenOnResize = a
};
Graph.prototype.isConstrainChildrenOnResize = function() {
	return this.constrainChildrenOnResize
};
Graph.prototype.setConstrainChildren = function(a) {
	this.constrainChildren = a
};
Graph.prototype.isAllowNegativeCoordinates = function() {
	return this.allowNegativeCoordinates
};
Graph.prototype.setAllowNegativeCoordinates = function(a) {
	this.allowNegativeCoordinates = a
};
Graph.prototype.getOverlap = function(a) {
	return this.isAllowOverlapParent(a) ? this.defaultOverlap : 0
};
Graph.prototype.isAllowOverlapParent = function(a) {
	return !1
};
Graph.prototype.getFoldableCells = function(a, b) {
	return this.model.filterCells(a, Utils.bind(this, function(a) {
		return this.isCellFoldable(a, b)
	}))
};
Graph.prototype.isCellFoldable = function(a, b) {
	var c = this.view.getState(a),
		c = null != c ? c.style : this.getCellStyle(a);
	return 0 < this.model.getChildCount(a) && 0 != c[Constants.STYLE_FOLDABLE]
};
Graph.prototype.isValidDropTarget = function(a, b, c) {
	return null != a && (this.isSplitEnabled() && this.isSplitTarget(a, b, c) || !this.model.isEdge(a) && (this.isSwimlane(a) || 0 < this.model.getChildCount(a) && !this.isCellCollapsed(a)))
};
Graph.prototype.isSplitTarget = function(a, b, c) {
	return this.model.isEdge(a) && null != b && 1 == b.length && this.isCellConnectable(b[0]) && null == this.getEdgeValidationError(a, this.model.getTerminal(a, !0), b[0]) ? (c = this.model.getTerminal(a, !0), a = this.model.getTerminal(a, !1), !this.model.isAncestor(b[0], c) && !this.model.isAncestor(b[0], a)) : !1
};
Graph.prototype.getDropTarget = function(a, b, c) {
	if (!this.isSwimlaneNesting()) for (var d = 0; d < a.length; d++) if (this.isSwimlane(a[d])) return null;
	d = Utils.convertPoint(this.container, Event.getClientX(b), Event.getClientY(b));
	d.x -= this.panDx;
	d.y -= this.panDy;
	d = this.getSwimlaneAt(d.x, d.y);
	if (null == c) c = d;
	else if (null != d) {
		for (var e = this.model.getParent(d); null != e && this.isSwimlane(e) && e != c;) e = this.model.getParent(e);
		e == c && (c = d)
	}
	for (; null != c && !this.isValidDropTarget(c, a, b) && !this.model.isLayer(c);) c = this.model.getParent(c);
	for (b = c; null != b && 0 > Utils.indexOf(a, b);) b = this.model.getParent(b);
	return !this.model.isLayer(c) && null == b ? c : null
};
Graph.prototype.getDefaultParent = function() {
	var a = this.getCurrentRoot();
	null == a && (a = this.defaultParent, null == a && (a = this.model.getRoot(), a = this.model.getChildAt(a, 0)));
	return a
};
Graph.prototype.setDefaultParent = function(a) {
	this.defaultParent = a
};
Graph.prototype.getSwimlane = function(a) {
	for (; null != a && !this.isSwimlane(a);) a = this.model.getParent(a);
	return a
};
Graph.prototype.getSwimlaneAt = function(a, b, c) {
	c = c || this.getDefaultParent();
	if (null != c) for (var d = this.model.getChildCount(c), e = 0; e < d; e++) {
		var f = this.model.getChildAt(c, e),
			g = this.getSwimlaneAt(a, b, f);
		if (null != g) return g;
		if (this.isSwimlane(f) && (g = this.view.getState(f), this.intersects(g, a, b))) return f
	}
	return null
};
Graph.prototype.getCellAt = function(a, b, c, d, e) {
	d = null != d ? d : !0;
	e = null != e ? e : !0;
	null == c && (c = this.getCurrentRoot(), null == c && (c = this.getModel().getRoot()));
	if (null != c) for (var f = this.model.getChildCount(c) - 1; 0 <= f; f--) {
		var g = this.model.getChildAt(c, f),
			k = this.getCellAt(a, b, g, d, e);
		if (null != k) return k;
		if (this.isCellVisible(g) && (e && this.model.isEdge(g) || d && this.model.isVertex(g))) if (k = this.view.getState(g), this.intersects(k, a, b)) return g
	}
	return null
};
Graph.prototype.intersects = function(a, b, c) {
	if (null != a) {
		var d = a.absolutePoints;
		if (null != d) {
			a = this.tolerance * this.tolerance;
			for (var e = d[0], f = 1; f < d.length; f++) {
				var g = d[f];
				if (Utils.ptSegDistSq(e.x, e.y, g.x, g.y, b, c) <= a) return !0;
				e = g
			}
		} else if (e = Utils.toRadians(Utils.getValue(a.style, Constants.STYLE_ROTATION) || 0), 0 != e && (d = Math.cos(-e), e = Math.sin(-e), f = new Point(a.getCenterX(), a.getCenterY()), e = Utils.getRotatedPoint(new Point(b, c), d, e, f), b = e.x, c = e.y), Utils.contains(a, b, c)) return !0
	}
	return !1
};
Graph.prototype.hitsSwimlaneContent = function(a, b, c) {
	var d = this.getView().getState(a);
	a = this.getStartSize(a);
	if (null != d) {
		var e = this.getView().getScale();
		b -= d.x;
		c -= d.y;
		if (0 < a.width && 0 < b && b > a.width * e || 0 < a.height && 0 < c && c > a.height * e) return !0
	}
	return !1
};
Graph.prototype.getChildVertices = function(a) {
	return this.getChildCells(a, !0, !1)
};
Graph.prototype.getChildEdges = function(a) {
	return this.getChildCells(a, !1, !0)
};
Graph.prototype.getChildCells = function(a, b, c) {
	a = null != a ? a : this.getDefaultParent();
	a = this.model.getChildCells(a, null != b ? b : !1, null != c ? c : !1);
	b = [];
	for (c = 0; c < a.length; c++) this.isCellVisible(a[c]) && b.push(a[c]);
	return b
};
Graph.prototype.getConnections = function(a, b) {
	return this.getEdges(a, b, !0, !0, !1)
};
Graph.prototype.getIncomingEdges = function(a, b) {
	return this.getEdges(a, b, !0, !1, !1)
};
Graph.prototype.getOutgoingEdges = function(a, b) {
	return this.getEdges(a, b, !1, !0, !1)
};
Graph.prototype.getEdges = function(a, b, c, d, e, f) {
	c = null != c ? c : !0;
	d = null != d ? d : !0;
	e = null != e ? e : !0;
	f = null != f ? f : !1;
	for (var g = [], k = this.isCellCollapsed(a), l = this.model.getChildCount(a), m = 0; m < l; m++) {
		var n = this.model.getChildAt(a, m);
		if (k || !this.isCellVisible(n)) g = g.concat(this.model.getEdges(n, c, d))
	}
	g = g.concat(this.model.getEdges(a, c, d));
	k = [];
	for (m = 0; m < g.length; m++) n = this.view.getState(g[m]), l = null != n ? n.getVisibleTerminal(!0) : this.view.getVisibleTerminal(g[m], !0), n = null != n ? n.getVisibleTerminal(!1) : this.view.getVisibleTerminal(g[m], !1), (e && l == n || l != n && (c && n == a && (null == b || this.isValidAncestor(l, b, f)) || d && l == a && (null == b || this.isValidAncestor(n, b, f)))) && k.push(g[m]);
	return k
};
Graph.prototype.isValidAncestor = function(a, b, c) {
	return c ? this.model.isAncestor(b, a) : this.model.getParent(a) == b
};
Graph.prototype.getOpposites = function(a, b, c, d) {
	c = null != c ? c : !0;
	d = null != d ? d : !0;
	var e = [],
		f = {};
	if (null != a) for (var g = 0; g < a.length; g++) {
		var k = this.view.getState(a[g]),
			l = null != k ? k.getVisibleTerminal(!0) : this.view.getVisibleTerminal(a[g], !0),
			k = null != k ? k.getVisibleTerminal(!1) : this.view.getVisibleTerminal(a[g], !1);
		if (l == b && null != k && k != b && d) {
			var m = CellPath.create(k);
			null == f[m] && (f[m] = k, e.push(k))
		} else k == b && (null != l && l != b && c) && (m = CellPath.create(l), null == f[m] && (f[m] = l, e.push(l)))
	}
	return e
};
Graph.prototype.getEdgesBetween = function(a, b, c) {
	c = null != c ? c : !1;
	for (var d = this.getEdges(a), e = [], f = 0; f < d.length; f++) {
		var g = this.view.getState(d[f]),
			k = null != g ? g.getVisibleTerminal(!0) : this.view.getVisibleTerminal(d[f], !0),
			g = null != g ? g.getVisibleTerminal(!1) : this.view.getVisibleTerminal(d[f], !1);
		(k == a && g == b || !c && k == b && g == a) && e.push(d[f])
	}
	return e
};
Graph.prototype.getPointForEvent = function(a, b) {
	var c = Utils.convertPoint(this.container, Event.getClientX(a), Event.getClientY(a)),
		d = this.view.scale,
		e = this.view.translate,
		f = !1 != b ? this.gridSize / 2 : 0;
	c.x = this.snap(c.x / d - e.x - f);
	c.y = this.snap(c.y / d - e.y - f);
	return c
};
Graph.prototype.getCells = function(a, b, c, d, e, f) {
	f = null != f ? f : [];
	if (0 < c || 0 < d) {
		var g = a + c,
			k = b + d;
		null == e && (e = this.getCurrentRoot(), null == e && (e = this.getModel().getRoot()));
		if (null != e) for (var l = this.model.getChildCount(e), m = 0; m < l; m++) {
			var n = this.model.getChildAt(e, m),
				p = this.view.getState(n);
			if (this.isCellVisible(n) && null != p) {
				var q = p,
					p = Utils.getValue(p.style, Constants.STYLE_ROTATION) || 0;
				0 != p && (q = Utils.getBoundingBox(q, p));
				q.x >= a && q.y + q.height <= k && q.y >= b && q.x + q.width <= g ? f.push(n) : this.getCells(a, b, c, d, n, f)
			}
		}
	}
	return f
};
Graph.prototype.getCellsBeyond = function(a, b, c, d, e) {
	var f = [];
	if (d || e) if (null == c && (c = this.getDefaultParent()), null != c) for (var g = this.model.getChildCount(c), k = 0; k < g; k++) {
		var l = this.model.getChildAt(c, k),
			m = this.view.getState(l);
		this.isCellVisible(l) && null != m && (!d || m.x >= a) && (!e || m.y >= b) && f.push(l)
	}
	return f
};
Graph.prototype.findTreeRoots = function(a, b, c) {
	b = null != b ? b : !1;
	c = null != c ? c : !1;
	var d = [];
	if (null != a) {
		for (var e = this.getModel(), f = e.getChildCount(a), g = null, k = 0, l = 0; l < f; l++) {
			var m = e.getChildAt(a, l);
			if (this.model.isVertex(m) && this.isCellVisible(m)) {
				for (var n = this.getConnections(m, b ? a : null), p = 0, q = 0, r = 0; r < n.length; r++) this.view.getVisibleTerminal(n[r], !0) == m ? p++ : q++;
				(c && 0 == p && 0 < q || !c && 0 == q && 0 < p) && d.push(m);
				n = c ? q - p : p - q;
				n > k && (k = n, g = m)
			}
		}
		0 == d.length && null != g && d.push(g)
	}
	return d
};
Graph.prototype.traverse = function(a, b, c, d, e) {
	if (null != c && null != a) {
		b = null != b ? b : !0;
		e = e || [];
		var f = CellPath.create(a);
		if (null == e[f] && (e[f] = a, d = c(a, d), null == d || d)) if (d = this.model.getEdgeCount(a), 0 < d) for (f = 0; f < d; f++) {
			var g = this.model.getEdgeAt(a, f),
				k = this.model.getTerminal(g, !0) == a;
			if (!b || k) k = this.model.getTerminal(g, !k), this.traverse(k, b, c, g, e)
		}
	}
};
Graph.prototype.isCellSelected = function(a) {
	return this.getSelectionModel().isSelected(a)
};
Graph.prototype.isSelectionEmpty = function() {
	return this.getSelectionModel().isEmpty()
};
Graph.prototype.clearSelection = function() {
	return this.getSelectionModel().clear()
};
Graph.prototype.getSelectionCount = function() {
	return this.getSelectionModel().cells.length
};
Graph.prototype.getSelectionCell = function() {
	return this.getSelectionModel().cells[0]
};
Graph.prototype.getSelectionCells = function() {
	return this.getSelectionModel().cells.slice()
};
Graph.prototype.setSelectionCell = function(a) {
	this.getSelectionModel().setCell(a)
};
Graph.prototype.setSelectionCells = function(a) {
	this.getSelectionModel().setCells(a)
};
Graph.prototype.addSelectionCell = function(a) {
	this.getSelectionModel().addCell(a)
};
Graph.prototype.addSelectionCells = function(a) {
	this.getSelectionModel().addCells(a)
};
Graph.prototype.removeSelectionCell = function(a) {
	this.getSelectionModel().removeCell(a)
};
Graph.prototype.removeSelectionCells = function(a) {
	this.getSelectionModel().removeCells(a)
};
Graph.prototype.selectRegion = function(a, b) {
	var c = this.getCells(a.x, a.y, a.width, a.height);
	this.selectCellsForEvent(c, b);
	return c
};
Graph.prototype.selectNextCell = function() {
	this.selectCell(!0)
};
Graph.prototype.selectPreviousCell = function() {
	this.selectCell()
};
Graph.prototype.selectParentCell = function() {
	this.selectCell(!1, !0)
};
Graph.prototype.selectChildCell = function() {
	this.selectCell(!1, !1, !0)
};
Graph.prototype.selectCell = function(a, b, c) {
	var d = this.selectionModel,
		e = 0 < d.cells.length ? d.cells[0] : null;
	1 < d.cells.length && d.clear();
	var d = null != e ? this.model.getParent(e) : this.getDefaultParent(),
		f = this.model.getChildCount(d);
	null == e && 0 < f ? (a = this.model.getChildAt(d, 0), this.setSelectionCell(a)) : (null == e || b) && null != this.view.getState(d) && null != this.model.getGeometry(d) ? this.getCurrentRoot() != d && this.setSelectionCell(d) : null != e && c ? 0 < this.model.getChildCount(e) && (a = this.model.getChildAt(e, 0), this.setSelectionCell(a)) : 0 < f && (b = d.getIndex(e), a ? (b++, a = this.model.getChildAt(d, b % f)) : (b--, a = this.model.getChildAt(d, 0 > b ? f - 1 : b)), this.setSelectionCell(a))
};
Graph.prototype.selectAll = function(a) {
	a = a || this.getDefaultParent();
	a = this.model.getChildren(a);
	null != a && this.setSelectionCells(a)
};
Graph.prototype.selectVertices = function(a) {
	this.selectCells(!0, !1, a)
};
Graph.prototype.selectEdges = function(a) {
	this.selectCells(!1, !0, a)
};
Graph.prototype.selectCells = function(a, b, c) {
	c = c || this.getDefaultParent();
	var d = Utils.bind(this, function(c) {
		return null != this.view.getState(c) && 0 == this.model.getChildCount(c) && (this.model.isVertex(c) && a || this.model.isEdge(c) && b)
	});
	c = this.model.filterDescendants(d, c);
	this.setSelectionCells(c)
};
Graph.prototype.selectCellForEvent = function(a, b) {
	var c = this.isCellSelected(a);
	this.isToggleEvent(b) ? c ? this.removeSelectionCell(a) : this.addSelectionCell(a) : (!c || 1 != this.getSelectionCount()) && this.setSelectionCell(a)
};
Graph.prototype.selectCellsForEvent = function(a, b) {
	this.isToggleEvent(b) ? this.addSelectionCells(a) : this.setSelectionCells(a)
};
Graph.prototype.createHandler = function(a) {
	var b = null;
	null != a && (this.model.isEdge(a.cell) ? (b = this.view.getEdgeStyle(a), b = this.isLoop(a) || b == EdgeStyle.ElbowConnector || b == EdgeStyle.SideToSide || b == EdgeStyle.TopToBottom ? new ElbowEdgeHandler(a) : b == EdgeStyle.SegmentConnector || b == EdgeStyle.OrthConnector ? new EdgeSegmentHandler(a) : new EdgeHandler(a)) : b = new VertexHandler(a));
	return b
};
Graph.prototype.addMouseListener = function(a) {
	null == this.mouseListeners && (this.mouseListeners = []);
	this.mouseListeners.push(a)
};
Graph.prototype.removeMouseListener = function(a) {
	if (null != this.mouseListeners) for (var b = 0; b < this.mouseListeners.length; b++) if (this.mouseListeners[b] == a) {
		this.mouseListeners.splice(b, 1);
		break
	}
};
Graph.prototype.updateMouseEvent = function(a) {
	if (null == a.graphX || null == a.graphY) {
		var b = Utils.convertPoint(this.container, a.getX(), a.getY());
		a.graphX = b.x - this.panDx;
		a.graphY = b.y - this.panDy
	}
	return a
};
Graph.prototype.getStateForTouchEvent = function(a) {
	var b = Event.getClientX(a);
	a = Event.getClientY(a);
	b = Utils.convertPoint(this.container, b, a);
	return this.view.getState(this.getCellAt(b.x, b.y))
};
Graph.prototype.isEventIgnored = function(a, b, c) {
	var d = Event.isMouseEvent(b.getEvent()),
		e = this.isEditing();
	b.getEvent() == this.lastEvent ? e = !0 : this.lastEvent = b.getEvent();
	null != this.eventSource && a != Event.MOUSE_MOVE ? (Event.removeGestureListeners(this.eventSource, null, this.mouseMoveRedirect, this.mouseUpRedirect), this.eventSource = this.mouseUpRedirect = this.mouseMoveRedirect = null) : null != this.eventSource && b.getSource() != this.eventSource ? e = !0 : Client.IS_TOUCH && (a == Event.MOUSE_DOWN && !d) && (this.eventSource = b.getSource(), this.mouseMoveRedirect = Utils.bind(this, function(a) {
		this.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(a, this.getStateForTouchEvent(a)))
	}), this.mouseUpRedirect = Utils.bind(this, function(a) {
		this.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(a, this.getStateForTouchEvent(a)))
	}), Event.addGestureListeners(this.eventSource, null, this.mouseMoveRedirect, this.mouseUpRedirect));
	this.isSyntheticEventIgnored(a, b, c) && (e = !0);
	if (!Event.isPopupTrigger(this.lastEvent) && a != Event.MOUSE_MOVE && 2 == this.lastEvent.detail) return !0;
	if (a == Event.MOUSE_UP && this.isMouseDown) this.isMouseDown = !1;
	else if (a == Event.MOUSE_DOWN && !this.isMouseDown) this.isMouseDown = !0, this.isMouseTrigger = d;
	else if (!e && ((!Client.IS_FF || a != Event.MOUSE_MOVE) && this.isMouseDown && this.isMouseTrigger != d || a == Event.MOUSE_DOWN && this.isMouseDown || a == Event.MOUSE_UP && !this.isMouseDown)) e = !0;
	!e && a == Event.MOUSE_DOWN && (this.lastMouseX = b.getX(), this.lastMouseY = b.getY());
	return e
};
Graph.prototype.isSyntheticEventIgnored = function(a, b, c) {
	c = !1;
	b = Event.isMouseEvent(b.getEvent());
	this.ignoreMouseEvents && b && a != Event.MOUSE_MOVE ? (this.ignoreMouseEvents = a != Event.MOUSE_UP, c = !0) : Client.IS_FF && (!b && a == Event.MOUSE_UP) && (this.ignoreMouseEvents = !0);
	return c
};
Graph.prototype.isEventSourceIgnored = function(a, b) {
	var c = b.getSource(),
		d = null != c.nodeName ? c.nodeName.toLowerCase() : "",
		e = !Event.isMouseEvent(b.getEvent()) || Event.isLeftMouseButton(b.getEvent());
	return a == Event.MOUSE_DOWN && e && ("select" == d || "option" == d || "input" == d && "checkbox" != c.type && "radio" != c.type && "button" != c.type && "submit" != c.type && "file" != c.type)
};
Graph.prototype.fireMouseEvent = function(a, b, c) {
	if (this.isEventSourceIgnored(a, b)) null != this.tooltipHandler && this.tooltipHandler.hide();
	else {
		null == c && (c = this);
		b = this.updateMouseEvent(b);
		a == Event.MOUSE_DOWN && (this.isEditing() && !this.cellEditor.isEventSource(b.getEvent())) && this.stopEditing(!this.isInvokesStopCellEditing());
		if (!this.nativeDblClickEnabled && !Event.isPopupTrigger(b.getEvent()) || this.doubleTapEnabled && Client.IS_TOUCH && Event.isTouchEvent(b.getEvent())) {
			var d = (new Date).getTime();
			if (!Client.IS_QUIRKS && a == Event.MOUSE_DOWN || Client.IS_QUIRKS && a == Event.MOUSE_UP && !this.fireDoubleClick) if (null != this.lastTouchEvent && this.lastTouchEvent != b.getEvent() && d - this.lastTouchTime < this.doubleTapTimeout && Math.abs(this.lastTouchX - b.getX()) < this.doubleTapTolerance && Math.abs(this.lastTouchY - b.getY()) < this.doubleTapTolerance && 2 > this.doubleClickCounter) {
				if (this.doubleClickCounter++, d = !1, a == Event.MOUSE_UP ? b.getCell() == this.lastTouchCell && null != this.lastTouchCell && (this.lastTouchTime = 0, d = this.lastTouchCell, this.lastTouchCell = null, this.dblClick(b.getEvent(), d), d = !0) : (this.fireDoubleClick = !0, this.lastTouchTime = 0), !Client.IS_QUIRKS || d) {
					Event.consume(b.getEvent());
					return
				}
			} else {
				if (null == this.lastTouchEvent || this.lastTouchEvent != b.getEvent()) this.lastTouchCell = b.getCell(), this.lastTouchX = b.getX(), this.lastTouchY = b.getY(), this.lastTouchTime = d, this.lastTouchEvent = b.getEvent(), this.doubleClickCounter = 0
			} else if ((this.isMouseDown || a == Event.MOUSE_UP) && this.fireDoubleClick) {
				this.fireDoubleClick = !1;
				d = this.lastTouchCell;
				this.lastTouchCell = null;
				this.isMouseDown = !1;
				(null != d || Event.isTouchEvent(b.getEvent()) && (Client.IS_GC || Client.IS_SF)) && Math.abs(this.lastTouchX - b.getX()) < this.doubleTapTolerance && Math.abs(this.lastTouchY - b.getY()) < this.doubleTapTolerance ? this.dblClick(b.getEvent(), d) : Event.consume(b.getEvent());
				return
			}
		}
		if (!this.isEventIgnored(a, b, c)) {
			this.fireEvent(new EventObject(Event.FIRE_MOUSE_EVENT, "eventName", a, "event", b));
			if (Client.IS_OP || Client.IS_SF || Client.IS_GC || Client.IS_IE && Client.IS_SVG || b.getEvent().target != this.container) {
				a == Event.MOUSE_MOVE && (this.isMouseDown && this.autoScroll && !Event.isMultiTouchEvent(b.getEvent)) && this.scrollPointToVisible(b.getGraphX(), b.getGraphY(), this.autoExtend);
				if (null != this.mouseListeners) {
					d = [c, b];
					b.getEvent().preventDefault || (b.getEvent().returnValue = !0);
					for (var e = 0; e < this.mouseListeners.length; e++) {
						var f = this.mouseListeners[e];
						a == Event.MOUSE_DOWN ? f.mouseDown.apply(f, d) : a == Event.MOUSE_MOVE ? f.mouseMove.apply(f, d) : a == Event.MOUSE_UP && f.mouseUp.apply(f, d)
					}
				}
				a == Event.MOUSE_UP && this.click(b)
			}
			Event.isTouchEvent(b.getEvent()) && a == Event.MOUSE_DOWN && this.tapAndHoldEnabled && !this.tapAndHoldInProgress ? (this.tapAndHoldInProgress = !0, this.initialTouchX = b.getGraphX(), this.initialTouchY = b.getGraphY(), this.tapAndHoldThread && window.clearTimeout(this.tapAndHoldThread), this.tapAndHoldThread = window.setTimeout(Utils.bind(this, function() {
				this.tapAndHoldValid && this.tapAndHold(b);
				this.tapAndHoldValid = this.tapAndHoldInProgress = !1
			}), this.tapAndHoldDelay), this.tapAndHoldValid = !0) : a == Event.MOUSE_UP ? this.tapAndHoldValid = this.tapAndHoldInProgress = !1 : this.tapAndHoldValid && (this.tapAndHoldValid = Math.abs(this.initialTouchX - b.getGraphX()) < this.tolerance && Math.abs(this.initialTouchY - b.getGraphY()) < this.tolerance);
			this.consumeMouseEvent(a, b, c)
		}
	}
};
Graph.prototype.consumeMouseEvent = function(a, b, c) {
	a == Event.MOUSE_DOWN && Event.isTouchEvent(b.getEvent()) && b.consume(!1)
};
Graph.prototype.fireGestureEvent = function(a, b) {
	this.lastTouchTime = 0;
	this.fireEvent(new EventObject(Event.GESTURE, "event", a, "cell", b))
};
Graph.prototype.destroy = function() {
	this.destroyed || (this.destroyed = !0, null != this.tooltipHandler && this.tooltipHandler.destroy(), null != this.selectionCellsHandler && this.selectionCellsHandler.destroy(), null != this.panningHandler && this.panningHandler.destroy(), null != this.popupMenuHandler && this.popupMenuHandler.destroy(), null != this.connectionHandler && this.connectionHandler.destroy(), null != this.graphHandler && this.graphHandler.destroy(), null != this.cellEditor && this.cellEditor.destroy(), null != this.view && this.view.destroy(), null != this.model && null != this.graphModelChangeListener && (this.model.removeListener(this.graphModelChangeListener), this.graphModelChangeListener = null), this.container = null)
};

function CellOverlay(a, b, c, d, e, f) {
	this.image = a;
	this.tooltip = b;
	this.align = null != c ? c : this.align;
	this.verticalAlign = null != d ? d : this.verticalAlign;
	this.offset = null != e ? e : new Point;
	this.cursor = null != f ? f : "help"
}
CellOverlay.prototype = new EventSource;
CellOverlay.prototype.constructor = CellOverlay;
CellOverlay.prototype.image = null;
CellOverlay.prototype.tooltip = null;
CellOverlay.prototype.align = Constants.ALIGN_RIGHT;
CellOverlay.prototype.verticalAlign = Constants.ALIGN_BOTTOM;
CellOverlay.prototype.offset = null;
CellOverlay.prototype.cursor = null;
CellOverlay.prototype.defaultOverlap = 0.5;
CellOverlay.prototype.getBounds = function(a) {
	var b = a.view.graph.getModel().isEdge(a.cell),
		c = a.view.scale,
		d = null,
		e = this.image.width,
		f = this.image.height;
	b ? (b = a.absolutePoints, 1 == b.length % 2 ? d = b[Math.floor(b.length / 2)] : (d = b.length / 2, a = b[d - 1], b = b[d], d = new Point(a.x + (b.x - a.x) / 2, a.y + (b.y - a.y) / 2))) : (d = new Point, d.x = this.align == Constants.ALIGN_LEFT ? a.x : this.align == Constants.ALIGN_CENTER ? a.x + a.width / 2 : a.x + a.width, d.y = this.verticalAlign == Constants.ALIGN_TOP ? a.y : this.verticalAlign == Constants.ALIGN_MIDDLE ? a.y + a.height / 2 : a.y + a.height);
	return new Rectangle(Math.round(d.x - (e * this.defaultOverlap - this.offset.x) * c), Math.round(d.y - (f * this.defaultOverlap - this.offset.y) * c), e * c, f * c)
};
CellOverlay.prototype.toString = function() {
	return this.tooltip
};

function Outline(a, b) {
	this.source = a;
	null != b && this.init(b)
}
Outline.prototype.source = null;
Outline.prototype.outline = null;
Outline.prototype.graphRenderHint = Constants.RENDERING_HINT_FASTER;
Outline.prototype.enabled = !0;
Outline.prototype.showViewport = !0;
Outline.prototype.border = 10;
Outline.prototype.sizerSize = 8;
Outline.prototype.labelsVisible = !1;
Outline.prototype.updateOnPan = !1;
Outline.prototype.sizerImage = null;
Outline.prototype.suspended = !1;
Outline.prototype.forceVmlHandles = 8 == document.documentMode;
Outline.prototype.createGraph = function(a) {
	a = new Graph(a, this.source.getModel(), this.graphRenderHint, this.source.getStylesheet());
	a.foldingEnabled = !1;
	a.autoScroll = !1;
	return a
};
Outline.prototype.init = function(a) {
	this.outline = this.createGraph(a);
	var b = this.outline.graphModelChanged;
	this.outline.graphModelChanged = Utils.bind(this, function(a) {
		!this.suspended && null != this.outline && b.apply(this.outline, arguments)
	});
	Client.IS_SVG && (a = this.outline.getView().getCanvas().parentNode, a.setAttribute("shape-rendering", "optimizeSpeed"), a.setAttribute("image-rendering", "optimizeSpeed"));
	this.outline.labelsVisible = this.labelsVisible;
	this.outline.setEnabled(!1);
	this.updateHandler = Utils.bind(this, function(a, b) {
		!this.suspended && !this.active && this.update()
	});
	this.source.getModel().addListener(Event.CHANGE, this.updateHandler);
	this.outline.addMouseListener(this);
	a = this.source.getView();
	a.addListener(Event.SCALE, this.updateHandler);
	a.addListener(Event.TRANSLATE, this.updateHandler);
	a.addListener(Event.SCALE_AND_TRANSLATE, this.updateHandler);
	a.addListener(Event.DOWN, this.updateHandler);
	a.addListener(Event.UP, this.updateHandler);
	Event.addListener(this.source.container, "scroll", this.updateHandler);
	this.panHandler = Utils.bind(this, function(a) {
		this.updateOnPan && this.updateHandler.apply(this, arguments)
	});
	this.source.addListener(Event.PAN, this.panHandler);
	this.refreshHandler = Utils.bind(this, function(a) {
		this.outline.setStylesheet(this.source.getStylesheet());
		this.outline.refresh()
	});
	this.source.addListener(Event.REFRESH, this.refreshHandler);
	this.bounds = new Rectangle(0, 0, 0, 0);
	this.selectionBorder = new RectangleShape(this.bounds, null, Constants.OUTLINE_COLOR, Constants.OUTLINE_STROKEWIDTH);
	this.selectionBorder.dialect = this.outline.dialect;
	this.forceVmlHandles && (this.selectionBorder.isHtmlAllowed = function() {
		return !1
	});
	this.selectionBorder.init(this.outline.getView().getOverlayPane());
	a = Utils.bind(this, function(a) {
		var b = Event.getSource(a),
			e = Utils.bind(this, function(a) {
				this.outline.fireMouseEvent(Event.MOUSE_MOVE, new MouseEvent(a))
			}),
			f = Utils.bind(this, function(a) {
				Event.removeGestureListeners(b, null, e, f);
				this.outline.fireMouseEvent(Event.MOUSE_UP, new MouseEvent(a))
			});
		Event.addGestureListeners(b, null, e, f);
		this.outline.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(a))
	});
	Event.addGestureListeners(this.selectionBorder.node, a);
	this.sizer = this.createSizer();
	this.forceVmlHandles && (this.sizer.isHtmlAllowed = function() {
		return !1
	});
	this.sizer.init(this.outline.getView().getOverlayPane());
	this.enabled && (this.sizer.node.style.cursor = "pointer");
	Event.addGestureListeners(this.sizer.node, a);
	this.selectionBorder.node.style.display = this.showViewport ? "" : "none";
	this.sizer.node.style.display = this.selectionBorder.node.style.display;
	this.selectionBorder.node.style.cursor = "move";
	this.update(!1)
};
Outline.prototype.isEnabled = function() {
	return this.enabled
};
Outline.prototype.setEnabled = function(a) {
	this.enabled = a
};
Outline.prototype.setZoomEnabled = function(a) {
	this.sizer.node.style.visibility = a ? "visible" : "hidden"
};
Outline.prototype.refresh = function() {
	this.update(!0)
};
Outline.prototype.createSizer = function() {
	var a = null != this.sizerImage ? new ImageShape(new Rectangle(0, 0, this.sizerImage.width, this.sizerImage.height), this.sizerImage.src) : new RectangleShape(new Rectangle(0, 0, this.sizerSize, this.sizerSize), Constants.OUTLINE_HANDLE_FILLCOLOR, Constants.OUTLINE_HANDLE_STROKECOLOR);
	a.dialect = this.outline.dialect;
	return a
};
Outline.prototype.getSourceContainerSize = function() {
	return new Rectangle(0, 0, this.source.container.scrollWidth, this.source.container.scrollHeight)
};
Outline.prototype.getOutlineOffset = function(a) {
	return null
};
Outline.prototype.getSourceGraphBounds = function() {
	return this.source.getGraphBounds()
};
Outline.prototype.update = function(a) {
	if (null != this.source && null != this.outline) {
		var b = this.source.view.scale,
			c = this.getSourceGraphBounds(),
			c = new Rectangle(c.x / b + this.source.panDx, c.y / b + this.source.panDy, c.width / b, c.height / b),
			d = new Rectangle(0, 0, this.source.container.clientWidth / b, this.source.container.clientHeight / b),
			e = c.clone();
		e.add(d);
		var f = this.getSourceContainerSize(),
			d = Math.max(f.width / b, e.width),
			b = Math.max(f.height / b, e.height),
			e = Math.max(0, this.outline.container.clientWidth - this.border),
			f = Math.max(0, this.outline.container.clientHeight - this.border),
			d = Math.min(e / d, f / b),
			e = Math.floor(100 * d) / 100;
		if (0 < e) {
			this.outline.getView().scale != e && (this.outline.getView().scale = e, a = !0);
			d = this.outline.getView();
			d.currentRoot != this.source.getView().currentRoot && d.setCurrentRoot(this.source.getView().currentRoot);
			var b = this.source.view.translate,
				f = b.x + this.source.panDx,
				g = b.y + this.source.panDy,
				e = this.getOutlineOffset(e);
			null != e && (f += e.x, g += e.y);
			0 > c.x && (f -= c.x);
			0 > c.y && (g -= c.y);
			if (d.translate.x != f || d.translate.y != g) d.translate.x = f, d.translate.y = g, a = !0;
			var c = d.translate,
				e = this.source.getView().scale,
				f = e / d.scale,
				g = 1 / d.scale,
				k = this.source.container;
			this.bounds = new Rectangle((c.x - b.x - this.source.panDx) / g, (c.y - b.y - this.source.panDy) / g, k.clientWidth / f, k.clientHeight / f);
			this.bounds.x += this.source.container.scrollLeft * d.scale / e;
			this.bounds.y += this.source.container.scrollTop * d.scale / e;
			c = this.selectionBorder.bounds;
			if (c.x != this.bounds.x || c.y != this.bounds.y || c.width != this.bounds.width || c.height != this.bounds.height) this.selectionBorder.bounds = this.bounds, this.selectionBorder.redraw();
			c = this.sizer.bounds;
			d = new Rectangle(this.bounds.x + this.bounds.width - c.width / 2, this.bounds.y + this.bounds.height - c.height / 2, c.width, c.height);
			if (c.x != d.x || c.y != d.y || c.width != d.width || c.height != d.height) this.sizer.bounds = d, "hidden" != this.sizer.node.style.visibility && this.sizer.redraw();
			a && this.outline.view.revalidate()
		}
	}
};
Outline.prototype.mouseDown = function(a, b) {
	if (this.enabled && this.showViewport) {
		var c = !Event.isMouseEvent(b.getEvent()) ? this.source.tolerance : 0,
			c = this.source.allowHandleBoundsCheck && (Client.IS_IE || 0 < c) ? new Rectangle(b.getGraphX() - c, b.getGraphY() - c, 2 * c, 2 * c) : null;
		this.zoom = b.isSource(this.sizer) || null != c && Utils.intersects(shape.bounds, c);
		this.startX = b.getX();
		this.startY = b.getY();
		this.active = !0;
		this.source.useScrollbarsForPanning && Utils.hasScrollbars(this.source.container) ? (this.dx0 = this.source.container.scrollLeft, this.dy0 = this.source.container.scrollTop) : this.dy0 = this.dx0 = 0
	}
	b.consume()
};
Outline.prototype.mouseMove = function(a, b) {
	if (this.active) {
		this.selectionBorder.node.style.display = this.showViewport ? "" : "none";
		this.sizer.node.style.display = this.selectionBorder.node.style.display;
		var c = this.getTranslateForEvent(b),
			d = c.x,
			e = c.y,
			c = null;
		if (this.zoom) c = this.source.container, e = d / (c.clientWidth / c.clientHeight), c = new Rectangle(this.bounds.x, this.bounds.y, Math.max(1, this.bounds.width + d), Math.max(1, this.bounds.height + e)), this.selectionBorder.bounds = c, this.selectionBorder.redraw();
		else {
			var f = this.outline.getView().scale,
				c = new Rectangle(this.bounds.x + d, this.bounds.y + e, this.bounds.width, this.bounds.height);
			this.selectionBorder.bounds = c;
			this.selectionBorder.redraw();
			d = d / f * this.source.getView().scale;
			e = e / f * this.source.getView().scale;
			this.source.panGraph(-d - this.dx0, -e - this.dy0)
		}
		d = this.sizer.bounds;
		this.sizer.bounds = new Rectangle(c.x + c.width - d.width / 2, c.y + c.height - d.height / 2, d.width, d.height);
		"hidden" != this.sizer.node.style.visibility && this.sizer.redraw();
		b.consume()
	}
};
Outline.prototype.getTranslateForEvent = function(a) {
	return new Point(a.getX() - this.startX, a.getY() - this.startY)
};
Outline.prototype.mouseUp = function(a, b) {
	if (this.active) {
		var c = this.getTranslateForEvent(b),
			d = c.x,
			c = c.y;
		if (0 < Math.abs(d) || 0 < Math.abs(c)) {
			if (this.zoom) {
				var c = this.selectionBorder.bounds.width,
					e = this.source.getView().scale;
				this.source.zoomTo(e - d * e / c, !1)
			} else if (!this.source.useScrollbarsForPanning || !Utils.hasScrollbars(this.source.container)) this.source.panGraph(0, 0), d /= this.outline.getView().scale, c /= this.outline.getView().scale, e = this.source.getView().translate, this.source.getView().setTranslate(e.x - d, e.y - c);
			this.update();
			b.consume()
		}
		this.index = null;
		this.active = !1
	}
};
Outline.prototype.destroy = function() {
	null != this.source && (this.source.removeListener(this.panHandler), this.source.removeListener(this.refreshHandler), this.source.getModel().removeListener(this.updateHandler), this.source.getView().removeListener(this.updateHandler), Event.addListener(this.source.container, "scroll", this.updateHandler), this.source = null);
	null != this.outline && (this.outline.removeMouseListener(this), this.outline.destroy(), this.outline = null);
	null != this.selectionBorder && (this.selectionBorder.destroy(), this.selectionBorder = null);
	null != this.sizer && (this.sizer.destroy(), this.sizer = null)
};

function Multiplicity(a, b, c, d, e, f, g, k, l, m) {
	this.source = a;
	this.type = b;
	this.attr = c;
	this.value = d;
	this.min = null != e ? e : 0;
	this.max = null != f ? f : "n";
	this.validNeighbors = g;
	this.countError = Resources.get(k) || k;
	this.typeError = Resources.get(l) || l;
	this.validNeighborsAllowed = null != m ? m : !0
}
Multiplicity.prototype.type = null;
Multiplicity.prototype.attr = null;
Multiplicity.prototype.value = null;
Multiplicity.prototype.source = null;
Multiplicity.prototype.min = null;
Multiplicity.prototype.max = null;
Multiplicity.prototype.validNeighbors = null;
Multiplicity.prototype.validNeighborsAllowed = !0;
Multiplicity.prototype.countError = null;
Multiplicity.prototype.typeError = null;
Multiplicity.prototype.check = function(a, b, c, d, e, f) {
	var g = "";
	if (this.source && this.checkTerminal(a, c, b) || !this.source && this.checkTerminal(a, d, b)) {
		if (null != this.countError && (this.source && (0 == this.max || e >= this.max) || !this.source && (0 == this.max || f >= this.max))) g += this.countError + "\n";
		null != this.validNeighbors && (null != this.typeError && 0 < this.validNeighbors.length) && (this.checkNeighbors(a, b, c, d) || (g += this.typeError + "\n"))
	}
	return 0 < g.length ? g : null
};
Multiplicity.prototype.checkNeighbors = function(a, b, c, d) {
	b = a.model.getValue(c);
	d = a.model.getValue(d);
	c = !this.validNeighborsAllowed;
	for (var e = this.validNeighbors, f = 0; f < e.length; f++) if (this.source && this.checkType(a, d, e[f])) {
		c = this.validNeighborsAllowed;
		break
	} else if (!this.source && this.checkType(a, b, e[f])) {
		c = this.validNeighborsAllowed;
		break
	}
	return c
};
Multiplicity.prototype.checkTerminal = function(a, b, c) {
	b = a.model.getValue(b);
	return this.checkType(a, b, this.type, this.attr, this.value)
};
Multiplicity.prototype.checkType = function(a, b, c, d, e) {
	return null != b ? isNaN(b.nodeType) ? b == c : Utils.isNode(b, c, d, e) : !1
};

function LayoutManager(a) {
	this.undoHandler = Utils.bind(this, function(a, c) {
		this.isEnabled() && this.beforeUndo(c.getProperty("edit"))
	});
	this.moveHandler = Utils.bind(this, function(a, c) {
		this.isEnabled() && this.cellsMoved(c.getProperty("cells"), c.getProperty("event"))
	});
	this.setGraph(a)
}
LayoutManager.prototype = new EventSource;
LayoutManager.prototype.constructor = LayoutManager;
LayoutManager.prototype.graph = null;
LayoutManager.prototype.bubbling = !0;
LayoutManager.prototype.enabled = !0;
LayoutManager.prototype.updateHandler = null;
LayoutManager.prototype.moveHandler = null;
LayoutManager.prototype.isEnabled = function() {
	return this.enabled
};
LayoutManager.prototype.setEnabled = function(a) {
	this.enabled = a
};
LayoutManager.prototype.isBubbling = function() {
	return this.bubbling
};
LayoutManager.prototype.setBubbling = function(a) {
	this.bubbling = a
};
LayoutManager.prototype.getGraph = function() {
	return this.graph
};
LayoutManager.prototype.setGraph = function(a) {
	if (null != this.graph) {
		var b = this.graph.getModel();
		b.removeListener(this.undoHandler);
		this.graph.removeListener(this.moveHandler)
	}
	this.graph = a;
	null != this.graph && (b = this.graph.getModel(), b.addListener(Event.BEFORE_UNDO, this.undoHandler), this.graph.addListener(Event.MOVE_CELLS, this.moveHandler))
};
LayoutManager.prototype.getLayout = function(a) {
	return null
};
LayoutManager.prototype.beforeUndo = function(a) {
	a = this.getCellsForChanges(a.changes);
	var b = this.getGraph().getModel();
	if (this.isBubbling()) for (var c = b.getParents(a); 0 < c.length;) a = a.concat(c), c = b.getParents(c);
	this.layoutCells(Utils.sortCells(a, !1))
};
LayoutManager.prototype.cellsMoved = function(a, b) {
	if (null != a && null != b) for (var c = Utils.convertPoint(this.getGraph().container, Event.getClientX(b), Event.getClientY(b)), d = this.getGraph().getModel(), e = 0; e < a.length; e++) {
		var f = this.getLayout(d.getParent(a[e]));
		null != f && f.moveCell(a[e], c.x, c.y)
	}
};
LayoutManager.prototype.getCellsForChanges = function(a) {
	for (var b = [], c = {}, d = 0; d < a.length; d++) {
		var e = a[d];
		if (e instanceof RootChange) return [];
		for (var e = this.getCellsForChange(e), f = 0; f < e.length; f++) if (null != e[f]) {
			var g = CellPath.create(e[f]);
			null == c[g] && (c[g] = e[f], b.push(e[f]))
		}
	}
	return b
};
LayoutManager.prototype.getCellsForChange = function(a) {
	var b = this.getGraph().getModel();
	return a instanceof ChildChange ? [a.child, a.previous, b.getParent(a.child)] : a instanceof TerminalChange || a instanceof GeometryChange ? [a.cell, b.getParent(a.cell)] : a instanceof VisibleChange || a instanceof StyleChange ? [a.cell] : []
};
LayoutManager.prototype.layoutCells = function(a) {
	if (0 < a.length) {
		var b = this.getGraph().getModel();
		b.beginUpdate();
		try {
			for (var c = null, d = 0; d < a.length; d++) a[d] != b.getRoot() && a[d] != c && (c = a[d], this.executeLayout(this.getLayout(c), c));
			this.fireEvent(new EventObject(Event.LAYOUT_CELLS, "cells", a))
		} finally {
			b.endUpdate()
		}
	}
};
LayoutManager.prototype.executeLayout = function(a, b) {
	null != a && null != b && a.execute(b)
};
LayoutManager.prototype.destroy = function() {
	this.setGraph(null)
};

function SpaceManager(a, b, c, d) {
	this.resizeHandler = Utils.bind(this, function(a, b) {
		this.isEnabled() && this.cellsResized(b.getProperty("cells"))
	});
	this.foldHandler = Utils.bind(this, function(a, b) {
		this.isEnabled() && this.cellsResized(b.getProperty("cells"))
	});
	this.shiftRightwards = null != b ? b : !0;
	this.shiftDownwards = null != c ? c : !0;
	this.extendParents = null != d ? d : !0;
	this.setGraph(a)
}
SpaceManager.prototype = new EventSource;
SpaceManager.prototype.constructor = SpaceManager;
SpaceManager.prototype.graph = null;
SpaceManager.prototype.enabled = !0;
SpaceManager.prototype.shiftRightwards = !0;
SpaceManager.prototype.shiftDownwards = !0;
SpaceManager.prototype.extendParents = !0;
SpaceManager.prototype.resizeHandler = null;
SpaceManager.prototype.foldHandler = null;
SpaceManager.prototype.isCellIgnored = function(a) {
	return !this.getGraph().getModel().isVertex(a)
};
SpaceManager.prototype.isCellShiftable = function(a) {
	return this.getGraph().getModel().isVertex(a) && this.getGraph().isCellMovable(a)
};
SpaceManager.prototype.isEnabled = function() {
	return this.enabled
};
SpaceManager.prototype.setEnabled = function(a) {
	this.enabled = a
};
SpaceManager.prototype.isShiftRightwards = function() {
	return this.shiftRightwards
};
SpaceManager.prototype.setShiftRightwards = function(a) {
	this.shiftRightwards = a
};
SpaceManager.prototype.isShiftDownwards = function() {
	return this.shiftDownwards
};
SpaceManager.prototype.setShiftDownwards = function(a) {
	this.shiftDownwards = a
};
SpaceManager.prototype.isExtendParents = function() {
	return this.extendParents
};
SpaceManager.prototype.setExtendParents = function(a) {
	this.extendParents = a
};
SpaceManager.prototype.getGraph = function() {
	return this.graph
};
SpaceManager.prototype.setGraph = function(a) {
	null != this.graph && (this.graph.removeListener(this.resizeHandler), this.graph.removeListener(this.foldHandler));
	this.graph = a;
	null != this.graph && (this.graph.addListener(Event.RESIZE_CELLS, this.resizeHandler), this.graph.addListener(Event.FOLD_CELLS, this.foldHandler))
};
SpaceManager.prototype.cellsResized = function(a) {
	if (null != a) {
		var b = this.graph.getModel();
		b.beginUpdate();
		try {
			for (var c = 0; c < a.length; c++) if (!this.isCellIgnored(a[c])) {
				this.cellResized(a[c]);
				break
			}
		} finally {
			b.endUpdate()
		}
	}
};
SpaceManager.prototype.cellResized = function(a) {
	var b = this.getGraph(),
		c = b.getView(),
		d = b.getModel(),
		e = c.getState(a),
		f = c.getState(d.getParent(a));
	if (null != e && null != f) {
		var g = this.getCellsToShift(e),
			k = d.getGeometry(a);
		if (null != g && null != k) {
			var l = c.translate,
				m = c.scale,
				c = e.x - f.origin.x - l.x * m,
				f = e.y - f.origin.y - l.y * m,
				l = e.x + e.width,
				n = e.y + e.height,
				p = e.width - k.width * m + c - k.x * m,
				q = e.height - k.height * m + f - k.y * m,
				r = 1 - k.width * m / e.width,
				e = 1 - k.height * m / e.height;
			d.beginUpdate();
			try {
				for (k = 0; k < g.length; k++) g[k] != a && this.isCellShiftable(g[k]) && this.shiftCell(g[k], p, q, c, f, l, n, r, e, this.isExtendParents() && b.isExtendParent(g[k]))
			} finally {
				d.endUpdate()
			}
		}
	}
};
SpaceManager.prototype.shiftCell = function(a, b, c, d, e, f, g, k, l, m) {
	d = this.getGraph();
	var n = d.getView().getState(a);
	if (null != n) {
		var p = d.getModel(),
			q = p.getGeometry(a);
		if (null != q) {
			p.beginUpdate();
			try {
				if (this.isShiftRightwards()) if (n.x >= f) q = q.clone(), q.translate(-b, 0);
				else {
					var r = Math.max(0, n.x - x0),
						q = q.clone();
					q.translate(-k * r, 0)
				}
				if (this.isShiftDownwards()) if (n.y >= g) q = q.clone(), q.translate(0, -c);
				else {
					var s = Math.max(0, n.y - e),
						q = q.clone();
					q.translate(0, -l * s)
				}
				q != p.getGeometry(a) && (p.setGeometry(a, q), m && d.extendParent(a))
			} finally {
				p.endUpdate()
			}
		}
	}
};
SpaceManager.prototype.getCellsToShift = function(a) {
	var b = this.getGraph(),
		c = b.getModel().getParent(a.cell),
		d = this.isShiftDownwards(),
		e = this.isShiftRightwards();
	return b.getCellsBeyond(a.x + (d ? 0 : a.width), a.y + (d && e ? 0 : a.height), c, e, d)
};
SpaceManager.prototype.destroy = function() {
	this.setGraph(null)
};

function SwimlaneManager(a, b, c, d) {
	this.horizontal = null != b ? b : !0;
	this.addEnabled = null != c ? c : !0;
	this.resizeEnabled = null != d ? d : !0;
	this.addHandler = Utils.bind(this, function(a, b) {
		this.isEnabled() && this.isAddEnabled() && this.cellsAdded(b.getProperty("cells"))
	});
	this.resizeHandler = Utils.bind(this, function(a, b) {
		this.isEnabled() && this.isResizeEnabled() && this.cellsResized(b.getProperty("cells"))
	});
	this.setGraph(a)
}
SwimlaneManager.prototype = new EventSource;
SwimlaneManager.prototype.constructor = SwimlaneManager;
SwimlaneManager.prototype.graph = null;
SwimlaneManager.prototype.enabled = !0;
SwimlaneManager.prototype.horizontal = !0;
SwimlaneManager.prototype.addEnabled = !0;
SwimlaneManager.prototype.resizeEnabled = !0;
SwimlaneManager.prototype.addHandler = null;
SwimlaneManager.prototype.resizeHandler = null;
SwimlaneManager.prototype.isEnabled = function() {
	return this.enabled
};
SwimlaneManager.prototype.setEnabled = function(a) {
	this.enabled = a
};
SwimlaneManager.prototype.isHorizontal = function() {
	return this.horizontal
};
SwimlaneManager.prototype.setHorizontal = function(a) {
	this.horizontal = a
};
SwimlaneManager.prototype.isAddEnabled = function() {
	return this.addEnabled
};
SwimlaneManager.prototype.setAddEnabled = function(a) {
	this.addEnabled = a
};
SwimlaneManager.prototype.isResizeEnabled = function() {
	return this.resizeEnabled
};
SwimlaneManager.prototype.setResizeEnabled = function(a) {
	this.resizeEnabled = a
};
SwimlaneManager.prototype.getGraph = function() {
	return this.graph
};
SwimlaneManager.prototype.setGraph = function(a) {
	null != this.graph && (this.graph.removeListener(this.addHandler), this.graph.removeListener(this.resizeHandler));
	this.graph = a;
	null != this.graph && (this.graph.addListener(Event.ADD_CELLS, this.addHandler), this.graph.addListener(Event.CELLS_RESIZED, this.resizeHandler))
};
SwimlaneManager.prototype.isSwimlaneIgnored = function(a) {
	return !this.getGraph().isSwimlane(a)
};
SwimlaneManager.prototype.isCellHorizontal = function(a) {
	return this.graph.isSwimlane(a) ? (a = this.graph.getCellStyle(a), 1 == Utils.getValue(a, Constants.STYLE_HORIZONTAL, 1)) : !this.isHorizontal()
};
SwimlaneManager.prototype.cellsAdded = function(a) {
	if (null != a) {
		var b = this.getGraph().getModel();
		b.beginUpdate();
		try {
			for (var c = 0; c < a.length; c++) this.isSwimlaneIgnored(a[c]) || this.swimlaneAdded(a[c])
		} finally {
			b.endUpdate()
		}
	}
};
SwimlaneManager.prototype.swimlaneAdded = function(a) {
	for (var b = this.getGraph().getModel(), c = b.getParent(a), d = b.getChildCount(c), e = null, f = 0; f < d; f++) {
		var g = b.getChildAt(c, f);
		if (g != a && !this.isSwimlaneIgnored(g) && (e = b.getGeometry(g), null != e)) break
	}
	null != e && (b = null != c ? this.isCellHorizontal(c) : this.horizontal, this.resizeSwimlane(a, e.width, e.height, b))
};
SwimlaneManager.prototype.cellsResized = function(a) {
	if (null != a) {
		var b = this.getGraph().getModel();
		b.beginUpdate();
		try {
			for (var c = 0; c < a.length; c++) if (!this.isSwimlaneIgnored(a[c])) {
				var d = b.getGeometry(a[c]);
				if (null != d) {
					for (var e = new Rectangle(0, 0, d.width, d.height), f = a[c], g = f; null != g;) {
						var f = g,
							g = b.getParent(g),
							k = this.graph.isSwimlane(g) ? this.graph.getStartSize(g) : new Rectangle;
						e.width += k.width;
						e.height += k.height
					}
					var l = null != g ? this.isCellHorizontal(g) : this.horizontal;
					this.resizeSwimlane(f, e.width, e.height, l)
				}
			}
		} finally {
			b.endUpdate()
		}
	}
};
SwimlaneManager.prototype.resizeSwimlane = function(a, b, c, d) {
	var e = this.getGraph().getModel();
	e.beginUpdate();
	try {
		var f = this.isCellHorizontal(a);
		if (!this.isSwimlaneIgnored(a)) {
			var g = e.getGeometry(a);
			if (null != g && (d && g.height != c || !d && g.width != b)) g = g.clone(), d ? g.height = c : g.width = b, e.setGeometry(a, g)
		}
		var k = this.graph.isSwimlane(a) ? this.graph.getStartSize(a) : new Rectangle;
		b -= k.width;
		c -= k.height;
		var l = e.getChildCount(a);
		for (d = 0; d < l; d++) {
			var m = e.getChildAt(a, d);
			this.resizeSwimlane(m, b, c, f)
		}
	} finally {
		e.endUpdate()
	}
};
SwimlaneManager.prototype.destroy = function() {
	this.setGraph(null)
};

function TemporaryCellStates(a, b, c, d) {
	b = null != b ? b : 1;
	this.view = a;
	this.oldValidateCellState = a.validateCellState;
	this.oldBounds = a.getGraphBounds();
	this.oldStates = a.getStates();
	this.oldScale = a.getScale();
	var e = this;
	a.validateCellState = function(b, c) {
		return null == b || null == d || d(b) ? e.oldValidateCellState.apply(a, arguments) : null
	};
	a.setStates(new Dictionary);
	a.setScale(b);
	if (null != c) {
		a.resetValidationState();
		b = null;
		for (var f = 0; f < c.length; f++) {
			var g = a.getBoundingBox(a.validateCellState(a.validateCell(c[f])));
			null == b ? b = g : b.add(g)
		}
		a.setGraphBounds(b || new Rectangle)
	}
}
TemporaryCellStates.prototype.view = null;
TemporaryCellStates.prototype.oldStates = null;
TemporaryCellStates.prototype.oldBounds = null;
TemporaryCellStates.prototype.oldScale = null;
TemporaryCellStates.prototype.destroy = function() {
	this.view.setScale(this.oldScale);
	this.view.setStates(this.oldStates);
	this.view.setGraphBounds(this.oldBounds);
	this.view.validateCellState = this.oldValidateCellState
};

function CellStatePreview(a) {
	this.deltas = new Dictionary;
	this.graph = a
}
CellStatePreview.prototype.graph = null;
CellStatePreview.prototype.deltas = null;
CellStatePreview.prototype.count = 0;
CellStatePreview.prototype.isEmpty = function() {
	return 0 == this.count
};
CellStatePreview.prototype.moveState = function(a, b, c, d, e) {
	d = null != d ? d : !0;
	e = null != e ? e : !0;
	var f = this.deltas.get(a.cell);
	null == f ? (f = {
		point: new Point(b, c),
		state: a
	}, this.deltas.put(a.cell, f), this.count++) : d ? (f.point.x += b, f.point.y += c) : (f.point.x = b, f.point.y = c);
	e && this.addEdges(a);
	return f.point
};
CellStatePreview.prototype.show = function(a) {
	this.deltas.visit(Utils.bind(this, function(a, c) {
		this.translateState(c.state, c.point.x, c.point.y)
	}));
	this.deltas.visit(Utils.bind(this, function(b, c) {
		this.revalidateState(c.state, c.point.x, c.point.y, a)
	}))
};
CellStatePreview.prototype.translateState = function(a, b, c) {
	if (null != a) {
		var d = this.graph.getModel();
		if (d.isVertex(a.cell)) {
			a.view.updateCellState(a);
			var e = d.getGeometry(a.cell);
			if ((0 != b || 0 != c) && null != e && (!e.relative || null != this.deltas.get(a.cell))) a.x += b, a.y += c
		}
		for (var e = d.getChildCount(a.cell), f = 0; f < e; f++) this.translateState(a.view.getState(d.getChildAt(a.cell, f)), b, c)
	}
};
CellStatePreview.prototype.revalidateState = function(a, b, c, d) {
	if (null != a) {
		var e = this.graph.getModel();
		e.isEdge(a.cell) && a.view.updateCellState(a);
		var f = this.graph.getCellGeometry(a.cell),
			g = a.view.getState(e.getParent(a.cell));
		if ((0 != b || 0 != c) && null != f && f.relative && e.isVertex(a.cell) && (null == g || e.isVertex(g.cell) || null != this.deltas.get(a.cell))) a.x += b, a.y += c;
		this.graph.cellRenderer.redraw(a);
		null != d && d(a);
		f = e.getChildCount(a.cell);
		for (g = 0; g < f; g++) this.revalidateState(this.graph.view.getState(e.getChildAt(a.cell, g)), b, c, d)
	}
};
CellStatePreview.prototype.addEdges = function(a) {
	for (var b = this.graph.getModel(), c = b.getEdgeCount(a.cell), d = 0; d < c; d++) {
		var e = a.view.getState(b.getEdgeAt(a.cell, d));
		null != e && this.moveState(e, 0, 0)
	}
};

function ConnectionConstraint(a, b) {
	this.point = a;
	this.perimeter = null != b ? b : !0
}
ConnectionConstraint.prototype.point = null;
ConnectionConstraint.prototype.perimeter = null;

function GraphHandler(a) {
	this.graph = a;
	this.graph.addMouseListener(this);
	this.panHandler = Utils.bind(this, function() {
		this.updatePreviewShape();
		this.updateHint()
	});
	this.graph.addListener(Event.PAN, this.panHandler);
	this.escapeHandler = Utils.bind(this, function(a, c) {
		this.reset()
	});
	this.graph.addListener(Event.ESCAPE, this.escapeHandler)
}
GraphHandler.prototype.graph = null;
GraphHandler.prototype.maxCells = Client.IS_IE ? 20 : 50;
GraphHandler.prototype.enabled = !0;
GraphHandler.prototype.highlightEnabled = !0;
GraphHandler.prototype.cloneEnabled = !0;
GraphHandler.prototype.moveEnabled = !0;
GraphHandler.prototype.guidesEnabled = !1;
GraphHandler.prototype.guide = null;
GraphHandler.prototype.currentDx = null;
GraphHandler.prototype.currentDy = null;
GraphHandler.prototype.updateCursor = !0;
GraphHandler.prototype.selectEnabled = !0;
GraphHandler.prototype.removeCellsFromParent = !0;
GraphHandler.prototype.connectOnDrop = !1;
GraphHandler.prototype.scrollOnMove = !0;
GraphHandler.prototype.minimumSize = 6;
GraphHandler.prototype.previewColor = "black";
GraphHandler.prototype.htmlPreview = !1;
GraphHandler.prototype.shape = null;
GraphHandler.prototype.scaleGrid = !1;
GraphHandler.prototype.rotationEnabled = !0;
GraphHandler.prototype.isEnabled = function() {
	return this.enabled
};
GraphHandler.prototype.setEnabled = function(a) {
	this.enabled = a
};
GraphHandler.prototype.isCloneEnabled = function() {
	return this.cloneEnabled
};
GraphHandler.prototype.setCloneEnabled = function(a) {
	this.cloneEnabled = a
};
GraphHandler.prototype.isMoveEnabled = function() {
	return this.moveEnabled
};
GraphHandler.prototype.setMoveEnabled = function(a) {
	this.moveEnabled = a
};
GraphHandler.prototype.isSelectEnabled = function() {
	return this.selectEnabled
};
GraphHandler.prototype.setSelectEnabled = function(a) {
	this.selectEnabled = a
};
GraphHandler.prototype.isRemoveCellsFromParent = function() {
	return this.removeCellsFromParent
};
GraphHandler.prototype.setRemoveCellsFromParent = function(a) {
	this.removeCellsFromParent = a
};
GraphHandler.prototype.getInitialCellForEvent = function(a) {
	return a.getCell()
};
GraphHandler.prototype.isDelayedSelection = function(a) {
	return this.graph.isCellSelected(a)
};
GraphHandler.prototype.mouseDown = function(a, b) {
	if (!b.isConsumed() && this.isEnabled() && this.graph.isEnabled() && null != b.getState() && !Event.isMultiTouchEvent(b.getEvent())) {
		var c = this.getInitialCellForEvent(b);
		this.delayedSelection = this.isDelayedSelection(c);
		this.cell = null;
		this.isSelectEnabled() && !this.delayedSelection && this.graph.selectCellForEvent(c, b.getEvent());
		if (this.isMoveEnabled()) {
			var d = this.graph.model,
				e = d.getGeometry(c);
			this.graph.isCellMovable(c) && (!d.isEdge(c) || 1 < this.graph.getSelectionCount() || null != e.points && 0 < e.points.length || null == d.getTerminal(c, !0) || null == d.getTerminal(c, !1) || this.graph.allowDanglingEdges || this.graph.isCloneEvent(b.getEvent()) && this.graph.isCellsCloneable()) && this.start(c, b.getX(), b.getY());
			this.cellWasClicked = !0;
			b.consume()
		}
	}
};
GraphHandler.prototype.getGuideStates = function() {
	var a = this.graph.getDefaultParent(),
		b = this.graph.getModel(),
		c = Utils.bind(this, function(a) {
			return null != this.graph.view.getState(a) && b.isVertex(a) && null != b.getGeometry(a) && !b.getGeometry(a).relative
		});
	return this.graph.view.getCellStates(b.filterDescendants(c, a))
};
GraphHandler.prototype.getCells = function(a) {
	return !this.delayedSelection && this.graph.isCellMovable(a) ? [a] : this.graph.getMovableCells(this.graph.getSelectionCells())
};
GraphHandler.prototype.getPreviewBounds = function(a) {
	a = this.getBoundingBox(a);
	null != a && (a.grow(-1, -1), a.width < this.minimumSize && (a.x -= (this.minimumSize - a.width) / 2, a.width = this.minimumSize), a.height < this.minimumSize && (a.y -= (this.minimumSize - a.height) / 2, a.height = this.minimumSize));
	return a
};
GraphHandler.prototype.getBoundingBox = function(a) {
	var b = null;
	if (null != a && 0 < a.length) for (var c = this.graph.getModel(), d = 0; d < a.length; d++) if (c.isVertex(a[d]) || c.isEdge(a[d])) {
		var e = this.graph.view.getState(a[d]);
		if (null != e) {
			var f = e;
			c.isVertex(a[d]) && (null != e.shape && null != e.shape.boundingBox) && (f = e.shape.boundingBox);
			null == b ? b = new Rectangle(f.x, f.y, f.width, f.height) : b.add(f)
		}
	}
	return b
};
GraphHandler.prototype.createPreviewShape = function(a) {
	a = new RectangleShape(a, null, this.previewColor);
	a.isDashed = !0;
	this.htmlPreview ? (a.dialect = Constants.DIALECT_STRICTHTML, a.init(this.graph.container)) : (a.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG, a.init(this.graph.getView().getOverlayPane()), a.pointerEvents = !1, Client.IS_IOS && (a.getSvgScreenOffset = function() {
		return 0
	}));
	return a
};
GraphHandler.prototype.start = function(a, b, c) {
	this.cell = a;
	this.first = Utils.convertPoint(this.graph.container, b, c);
	this.cells = this.getCells(this.cell);
	this.bounds = this.graph.getView().getBounds(this.cells);
	this.pBounds = this.getPreviewBounds(this.cells);
	this.guidesEnabled && (this.guide = new Guide(this.graph, this.getGuideStates()))
};
GraphHandler.prototype.useGuidesForEvent = function(a) {
	return null != this.guide ? this.guide.isEnabledForEvent(a.getEvent()) : !0
};
GraphHandler.prototype.snap = function(a) {
	var b = this.scaleGrid ? this.graph.view.scale : 1;
	a.x = this.graph.snap(a.x / b) * b;
	a.y = this.graph.snap(a.y / b) * b;
	return a
};
GraphHandler.prototype.getDelta = function(a) {
	a = Utils.convertPoint(this.graph.container, a.getX(), a.getY());
	var b = this.graph.view.scale;
	return new Point(this.roundLength((a.x - this.first.x) / b) * b, this.roundLength((a.y - this.first.y) / b) * b)
};
GraphHandler.prototype.updateHint = function(a) {};
GraphHandler.prototype.removeHint = function() {};
GraphHandler.prototype.roundLength = function(a) {
	return Math.round(a)
};
GraphHandler.prototype.mouseMove = function(a, b) {
	var c = this.graph;
	if (!b.isConsumed() && c.isMouseDown && null != this.cell && null != this.first && null != this.bounds) if (Event.isMultiTouchEvent(b.getEvent())) this.reset();
	else {
		var d = this.getDelta(b),
			e = d.x,
			d = d.y,
			f = c.tolerance;
		if (null != this.shape || Math.abs(e) > f || Math.abs(d) > f) {
			null == this.highlight && (this.highlight = new CellHighlight(this.graph, Constants.DROP_TARGET_COLOR, 3));
			null == this.shape && (this.shape = this.createPreviewShape(this.bounds));
			var g = c.isGridEnabledEvent(b.getEvent()),
				f = !0;
			if (null != this.guide && this.useGuidesForEvent(b)) d = this.guide.move(this.bounds, new Point(e, d), g), f = !1, e = d.x, d = d.y;
			else if (g) var k = c.getView().translate,
				l = c.getView().scale,
				g = this.bounds.x - (c.snap(this.bounds.x / l - k.x) + k.x) * l,
				k = this.bounds.y - (c.snap(this.bounds.y / l - k.y) + k.y) * l,
				d = this.snap(new Point(e, d)),
				e = d.x - g,
				d = d.y - k;
			null != this.guide && f && this.guide.hide();
			c.isConstrainedEvent(b.getEvent()) && (Math.abs(e) > Math.abs(d) ? d = 0 : e = 0);
			this.currentDx = e;
			this.currentDy = d;
			this.updatePreviewShape();
			f = null;
			d = b.getCell();
			c.isDropEnabled() && this.highlightEnabled && (f = c.getDropTarget(this.cells, b.getEvent(), d));
			g = c.isCloneEvent(b.getEvent()) && c.isCellsCloneable() && this.isCloneEnabled();
			e = c.getView().getState(f);
			k = !1;
			null != e && (c.model.getParent(this.cell) != f || g) ? (this.target != f && (this.target = f, this.setHighlightColor(Constants.DROP_TARGET_COLOR)), k = !0) : (this.target = null, this.connectOnDrop && (null != d && 1 == this.cells.length && c.getModel().isVertex(d) && c.isCellConnectable(d)) && (e = c.getView().getState(d), null != e && (c = null == c.getEdgeValidationError(null, this.cell, d) ? Constants.VALID_COLOR : Constants.INVALID_CONNECT_TARGET_COLOR, this.setHighlightColor(c), k = !0)));
			null != e && k ? this.highlight.highlight(e) : this.highlight.hide()
		}
		this.updateHint(b);
		b.consume();
		Event.consume(b.getEvent())
	} else if ((this.isMoveEnabled() || this.isCloneEnabled()) && this.updateCursor && !b.isConsumed() && null != b.getState() && !c.isMouseDown) e = c.getCursorForMouseEvent(b), null == e && (c.isEnabled() && c.isCellMovable(b.getCell())) && (e = c.getModel().isEdge(b.getCell()) ? Constants.CURSOR_MOVABLE_EDGE : Constants.CURSOR_MOVABLE_VERTEX), b.getState().setCursor(e)
};
GraphHandler.prototype.updatePreviewShape = function() {
	null != this.shape && (this.shape.bounds = new Rectangle(Math.round(this.pBounds.x + this.currentDx - this.graph.panDx), Math.round(this.pBounds.y + this.currentDy - this.graph.panDy), this.pBounds.width, this.pBounds.height), this.shape.redraw())
};
GraphHandler.prototype.setHighlightColor = function(a) {
	null != this.highlight && this.highlight.setHighlightColor(a)
};
GraphHandler.prototype.mouseUp = function(a, b) {
	if (!b.isConsumed()) {
		var c = this.graph;
		if (null != this.cell && null != this.first && null != this.shape && null != this.currentDx && null != this.currentDy) {
			var d = b.getCell();
			if (this.connectOnDrop && null == this.target && null != d && c.getModel().isVertex(d) && c.isCellConnectable(d) && c.isEdgeValid(null, this.cell, d)) c.connectionHandler.connect(this.cell, d, b.getEvent());
			else {
				var d = c.isCloneEvent(b.getEvent()) && c.isCellsCloneable() && this.isCloneEnabled(),
					e = c.getView().scale,
					f = this.roundLength(this.currentDx / e),
					e = this.roundLength(this.currentDy / e),
					g = this.target;
				c.isSplitEnabled() && c.isSplitTarget(g, this.cells, b.getEvent()) ? c.splitEdge(g, this.cells, null, f, e) : this.moveCells(this.cells, f, e, d, this.target, b.getEvent())
			}
		} else this.isSelectEnabled() && (this.delayedSelection && null != this.cell) && this.selectDelayed(b)
	}
	this.cellWasClicked && b.consume();
	this.reset()
};
GraphHandler.prototype.selectDelayed = function(a) {
	(!this.graph.isCellSelected(this.cell) || !this.graph.popupMenuHandler.isPopupTrigger(a)) && this.graph.selectCellForEvent(this.cell, a.getEvent())
};
GraphHandler.prototype.reset = function() {
	this.destroyShapes();
	this.removeHint();
	this.delayedSelection = this.cellWasClicked = !1;
	this.target = this.cell = this.first = this.guides = this.currentDy = this.currentDx = null
};
GraphHandler.prototype.shouldRemoveCellsFromParent = function(a, b, c) {
	if (this.graph.getModel().isVertex(a)) {
		a = this.graph.getView().getState(a);
		c = Utils.convertPoint(this.graph.container, Event.getClientX(c), Event.getClientY(c));
		var d = Utils.toRadians(Utils.getValue(a.style, Constants.STYLE_ROTATION) || 0);
		if (0 != d) {
			b = Math.cos(-d);
			var d = Math.sin(-d),
				e = new Point(a.getCenterX(), a.getCenterY());
			c = Utils.getRotatedPoint(c, b, d, e)
		}
		return null != a && !Utils.contains(a, c.x, c.y)
	}
	return !1
};
GraphHandler.prototype.moveCells = function(a, b, c, d, e, f) {
	d && (a = this.graph.getCloneableCells(a));
	null == e && (this.isRemoveCellsFromParent() && this.shouldRemoveCellsFromParent(this.graph.getModel().getParent(this.cell), a, f)) && (e = this.graph.getDefaultParent());
	a = this.graph.moveCells(a, b - this.graph.panDx / this.graph.view.scale, c - this.graph.panDy / this.graph.view.scale, d, e, f);
	this.isSelectEnabled() && this.scrollOnMove && this.graph.scrollCellToVisible(a[0]);
	d && this.graph.setSelectionCells(a)
};
GraphHandler.prototype.destroyShapes = function() {
	null != this.shape && (this.shape.destroy(), this.shape = null);
	null != this.guide && (this.guide.destroy(), this.guide = null);
	null != this.highlight && (this.highlight.destroy(), this.highlight = null)
};
GraphHandler.prototype.destroy = function() {
	this.graph.removeMouseListener(this);
	this.graph.removeListener(this.panHandler);
	null != this.escapeHandler && (this.graph.removeListener(this.escapeHandler), this.escapeHandler = null);
	this.destroyShapes();
	this.removeHint()
};

function PanningHandler(a) {
	null != a && (this.graph = a, this.graph.addMouseListener(this), this.forcePanningHandler = Utils.bind(this, function(a, c) {
		var d = c.getProperty("eventName"),
			e = c.getProperty("event");
		d == Event.MOUSE_DOWN && this.isForcePanningEvent(e) && (this.start(e), this.active = !0, this.fireEvent(new EventObject(Event.PAN_START, "event", e)), e.consume())
	}), this.graph.addListener(Event.FIRE_MOUSE_EVENT, this.forcePanningHandler), this.gestureHandler = Utils.bind(this, function(a, c) {
		if (this.isPinchEnabled()) {
			var d = c.getProperty("event");
			!Event.isConsumed(d) && "gesturestart" == d.type ? (this.initialScale = this.graph.view.scale, !this.active && null != this.mouseDownEvent && (this.start(this.mouseDownEvent), this.mouseDownEvent = null)) : "gestureend" == d.type && null == this.initialScale && (this.initialScale = null);
			if (null != this.initialScale) {
				var e = Math.round(100 * this.initialScale * d.scale) / 100;
				null != this.minScale && (e = Math.max(this.minScale, e));
				null != this.maxScale && (e = Math.min(this.maxScale, e));
				this.graph.view.scale != e && (this.graph.zoomTo(e), Event.consume(d))
			}
		}
	}), this.graph.addListener(Event.GESTURE, this.gestureHandler))
}
PanningHandler.prototype = new EventSource;
PanningHandler.prototype.constructor = PanningHandler;
PanningHandler.prototype.graph = null;
PanningHandler.prototype.useLeftButtonForPanning = !1;
PanningHandler.prototype.usePopupTrigger = !0;
PanningHandler.prototype.ignoreCell = !1;
PanningHandler.prototype.previewEnabled = !0;
PanningHandler.prototype.useGrid = !1;
PanningHandler.prototype.panningEnabled = !0;
PanningHandler.prototype.pinchEnabled = !0;
PanningHandler.prototype.maxScale = 8;
PanningHandler.prototype.minScale = 0.01;
PanningHandler.prototype.dx = null;
PanningHandler.prototype.dy = null;
PanningHandler.prototype.startX = 0;
PanningHandler.prototype.startY = 0;
PanningHandler.prototype.isActive = function() {
	return this.active || null != this.initialScale
};
PanningHandler.prototype.isPanningEnabled = function() {
	return this.panningEnabled
};
PanningHandler.prototype.setPanningEnabled = function(a) {
	this.panningEnabled = a
};
PanningHandler.prototype.isPinchEnabled = function() {
	return this.pinchEnabled
};
PanningHandler.prototype.setPinchEnabled = function(a) {
	this.pinchEnabled = a
};
PanningHandler.prototype.isPanningTrigger = function(a) {
	var b = a.getEvent();
	return this.useLeftButtonForPanning && null == a.getState() && Event.isLeftMouseButton(b) || Event.isControlDown(b) && Event.isShiftDown(b) || this.usePopupTrigger && Event.isPopupTrigger(b)
};
PanningHandler.prototype.isForcePanningEvent = function(a) {
	return this.ignoreCell || Event.isMultiTouchEvent(a.getEvent())
};
PanningHandler.prototype.mouseDown = function(a, b) {
	this.mouseDownEvent = b;
	!b.isConsumed() && (this.isPanningEnabled() && !this.active && this.isPanningTrigger(b)) && (this.start(b), this.consumePanningTrigger(b))
};
PanningHandler.prototype.start = function(a) {
	this.dx0 = -this.graph.container.scrollLeft;
	this.dy0 = -this.graph.container.scrollTop;
	this.startX = a.getX();
	this.startY = a.getY();
	this.dy = this.dx = null;
	this.panningTrigger = !0
};
PanningHandler.prototype.consumePanningTrigger = function(a) {
	a.consume()
};
PanningHandler.prototype.mouseMove = function(a, b) {
	this.dx = b.getX() - this.startX;
	this.dy = b.getY() - this.startY;
	if (this.active) this.previewEnabled && (this.useGrid && (this.dx = this.graph.snap(this.dx), this.dy = this.graph.snap(this.dy)), this.graph.panGraph(this.dx + this.dx0, this.dy + this.dy0)), this.fireEvent(new EventObject(Event.PAN, "event", b));
	else if (this.panningTrigger) {
		var c = this.active;
		this.active = Math.abs(this.dx) > this.graph.tolerance || Math.abs(this.dy) > this.graph.tolerance;
		!c && this.active && this.fireEvent(new EventObject(Event.PAN_START, "event", b))
	}(this.active || this.panningTrigger) && b.consume()
};
PanningHandler.prototype.mouseUp = function(a, b) {
	if (this.active && null != this.dx && null != this.dy) {
		if (!this.graph.useScrollbarsForPanning || !Utils.hasScrollbars(this.graph.container)) {
			var c = this.graph.getView().scale,
				d = this.graph.getView().translate;
			this.graph.panGraph(0, 0);
			this.panGraph(d.x + this.dx / c, d.y + this.dy / c)
		}
		this.fireEvent(new EventObject(Event.PAN_END, "event", b));
		b.consume()
	}
	this.panningTrigger = !1;
	this.mouseDownEvent = null;
	this.active = !1;
	this.dy = this.dx = null
};
PanningHandler.prototype.panGraph = function(a, b) {
	this.graph.getView().setTranslate(a, b)
};
PanningHandler.prototype.destroy = function() {
	this.graph.removeMouseListener(this);
	this.graph.removeListener(this.forcePanningHandler);
	this.graph.removeListener(this.gestureHandler)
};

function PopupMenuHandler(a, b) {
	null != a && (this.graph = a, this.factoryMethod = b, this.graph.addMouseListener(this), this.gestureHandler = Utils.bind(this, function(a, b) {
		this.inTolerance = !1
	}), this.graph.addListener(Event.GESTURE, this.gestureHandler), this.init())
}
PopupMenuHandler.prototype = new PopupMenu;
PopupMenuHandler.prototype.constructor = PopupMenuHandler;
PopupMenuHandler.prototype.graph = null;
PopupMenuHandler.prototype.selectOnPopup = !0;
PopupMenuHandler.prototype.clearSelectionOnBackground = !0;
PopupMenuHandler.prototype.triggerX = null;
PopupMenuHandler.prototype.triggerY = null;
PopupMenuHandler.prototype.screenX = null;
PopupMenuHandler.prototype.screenY = null;
PopupMenuHandler.prototype.init = function() {
	PopupMenu.prototype.init.apply(this);
	Event.addGestureListeners(this.div, Utils.bind(this, function(a) {
		this.graph.tooltipHandler.hide()
	}))
};
PopupMenuHandler.prototype.isSelectOnPopup = function(a) {
	return this.selectOnPopup
};
PopupMenuHandler.prototype.mouseDown = function(a, b) {
	this.isEnabled() && !Event.isMultiTouchEvent(b.getEvent()) && (this.hideMenu(), this.triggerX = b.getGraphX(), this.triggerY = b.getGraphY(), this.screenX = b.getEvent().screenX, this.screenY = b.getEvent().screenY, this.popupTrigger = this.isPopupTrigger(b), this.inTolerance = !0)
};
PopupMenuHandler.prototype.mouseMove = function(a, b) {
	if (this.inTolerance && (null != this.screenX && null != this.screenY) && (Math.abs(b.getEvent().screenX - this.screenX) > this.graph.tolerance || Math.abs(b.getEvent().screenY - this.screenY) > this.graph.tolerance)) this.inTolerance = !1
};
PopupMenuHandler.prototype.mouseUp = function(a, b) {
	if (this.popupTrigger && this.inTolerance && null != this.triggerX && null != this.triggerY) {
		var c = this.getCellForPopupEvent(b);
		this.graph.isEnabled() && this.isSelectOnPopup(b) && null != c && !this.graph.isCellSelected(c) ? this.graph.setSelectionCell(c) : this.clearSelectionOnBackground && null == c && this.graph.clearSelection();
		this.graph.tooltipHandler.hide();
		var d = Utils.getScrollOrigin();
		this.popup(b.getX() + d.x + 1, b.getY() + d.y + 1, c, b.getEvent());
		b.consume()
	}
	this.inTolerance = this.popupTrigger = !1
};
PopupMenuHandler.prototype.getCellForPopupEvent = function(a) {
	return a.getCell()
};
PopupMenuHandler.prototype.destroy = function() {
	this.graph.removeMouseListener(this);
	this.graph.removeListener(this.gestureHandler);
	PopupMenu.prototype.destroy.apply(this)
};

function CellMarker(a, b, c, d) {
	EventSource.call(this);
	null != a && (this.graph = a, this.validColor = null != b ? b : Constants.DEFAULT_VALID_COLOR, this.invalidColor = null != b ? c : Constants.DEFAULT_INVALID_COLOR, this.hotspot = null != d ? d : Constants.DEFAULT_HOTSPOT, this.highlight = new CellHighlight(a))
}
Utils.extend(CellMarker, EventSource);
CellMarker.prototype.graph = null;
CellMarker.prototype.enabled = !0;
CellMarker.prototype.hotspot = Constants.DEFAULT_HOTSPOT;
CellMarker.prototype.hotspotEnabled = !1;
CellMarker.prototype.validColor = null;
CellMarker.prototype.invalidColor = null;
CellMarker.prototype.currentColor = null;
CellMarker.prototype.validState = null;
CellMarker.prototype.markedState = null;
CellMarker.prototype.setEnabled = function(a) {
	this.enabled = a
};
CellMarker.prototype.isEnabled = function() {
	return this.enabled
};
CellMarker.prototype.setHotspot = function(a) {
	this.hotspot = a
};
CellMarker.prototype.getHotspot = function() {
	return this.hotspot
};
CellMarker.prototype.setHotspotEnabled = function(a) {
	this.hotspotEnabled = a
};
CellMarker.prototype.isHotspotEnabled = function() {
	return this.hotspotEnabled
};
CellMarker.prototype.hasValidState = function() {
	return null != this.validState
};
CellMarker.prototype.getValidState = function() {
	return this.validState
};
CellMarker.prototype.getMarkedState = function() {
	return this.markedState
};
CellMarker.prototype.reset = function() {
	this.validState = null;
	null != this.markedState && (this.markedState = null, this.unmark())
};
CellMarker.prototype.process = function(a) {
	var b = null;
	this.isEnabled() && (b = this.getState(a), this.setCurrentState(b, a));
	return b
};
CellMarker.prototype.setCurrentState = function(a, b, c) {
	var d = null != a ? this.isValidState(a) : !1;
	c = null != c ? c : this.getMarkerColor(b.getEvent(), a, d);
	this.validState = d ? a : null;
	if (a != this.markedState || c != this.currentColor) this.currentColor = c, null != a && null != this.currentColor ? (this.markedState = a, this.mark()) : null != this.markedState && (this.markedState = null, this.unmark())
};
CellMarker.prototype.markCell = function(a, b) {
	var c = this.graph.getView().getState(a);
	null != c && (this.currentColor = null != b ? b : this.validColor, this.markedState = c, this.mark())
};
CellMarker.prototype.mark = function() {
	this.highlight.setHighlightColor(this.currentColor);
	this.highlight.highlight(this.markedState);
	this.fireEvent(new EventObject(Event.MARK, "state", this.markedState))
};
CellMarker.prototype.unmark = function() {
	this.mark()
};
CellMarker.prototype.isValidState = function(a) {
	return !0
};
CellMarker.prototype.getMarkerColor = function(a, b, c) {
	return c ? this.validColor : this.invalidColor
};
CellMarker.prototype.getState = function(a) {
	var b = this.graph.getView();
	cell = this.getCell(a);
	b = this.getStateToMark(b.getState(cell));
	return null != b && this.intersects(b, a) ? b : null
};
CellMarker.prototype.getCell = function(a) {
	return a.getCell()
};
CellMarker.prototype.getStateToMark = function(a) {
	return a
};
CellMarker.prototype.intersects = function(a, b) {
	return this.hotspotEnabled ? Utils.intersectsHotspot(a, b.getGraphX(), b.getGraphY(), this.hotspot, Constants.MIN_HOTSPOT_SIZE, Constants.MAX_HOTSPOT_SIZE) : !0
};
CellMarker.prototype.destroy = function() {
	this.graph.getView().removeListener(this.resetHandler);
	this.graph.getModel().removeListener(this.resetHandler);
	this.highlight.destroy()
};

function SelectionCellsHandler(a) {
	EventSource.call(this);
	this.graph = a;
	this.handlers = new Dictionary;
	this.graph.addMouseListener(this);
	this.refreshHandler = Utils.bind(this, function(a, c) {
		this.isEnabled() && this.refresh()
	});
	this.graph.getSelectionModel().addListener(Event.CHANGE, this.refreshHandler);
	this.graph.getModel().addListener(Event.CHANGE, this.refreshHandler);
	this.graph.getView().addListener(Event.SCALE, this.refreshHandler);
	this.graph.getView().addListener(Event.TRANSLATE, this.refreshHandler);
	this.graph.getView().addListener(Event.SCALE_AND_TRANSLATE, this.refreshHandler);
	this.graph.getView().addListener(Event.DOWN, this.refreshHandler);
	this.graph.getView().addListener(Event.UP, this.refreshHandler)
}
Utils.extend(SelectionCellsHandler, EventSource);
SelectionCellsHandler.prototype.graph = null;
SelectionCellsHandler.prototype.enabled = !0;
SelectionCellsHandler.prototype.refreshHandler = null;
SelectionCellsHandler.prototype.maxHandlers = 100;
SelectionCellsHandler.prototype.handlers = null;
SelectionCellsHandler.prototype.isEnabled = function() {
	return this.enabled
};
SelectionCellsHandler.prototype.setEnabled = function(a) {
	this.enabled = a
};
SelectionCellsHandler.prototype.getHandler = function(a) {
	return this.handlers.get(a)
};
SelectionCellsHandler.prototype.reset = function() {
	this.handlers.visit(function(a, b) {
		b.reset.apply(b)
	})
};
SelectionCellsHandler.prototype.refresh = function() {
	var a = this.handlers;
	this.handlers = new Dictionary;
	for (var b = this.graph.getSelectionCells(), c = 0; c < b.length; c++) {
		var d = this.graph.view.getState(b[c]);
		if (null != d) {
			var e = a.remove(b[c]);
			null != e && (e.state != d ? (e.destroy(), e = null) : (null != e.refresh && e.refresh(), e.redraw()));
			null == e && (e = this.graph.createHandler(d), this.fireEvent(new EventObject(Event.ADD, "state", d)));
			null != e && this.handlers.put(b[c], e)
		}
	}
	a.visit(Utils.bind(this, function(a, b) {
		this.fireEvent(new EventObject(Event.REMOVE, "state", b.state));
		b.destroy()
	}))
};
SelectionCellsHandler.prototype.mouseDown = function(a, b) {
	if (this.graph.isEnabled() && this.isEnabled()) {
		var c = [a, b];
		this.handlers.visit(function(a, b) {
			b.mouseDown.apply(b, c)
		})
	}
};
SelectionCellsHandler.prototype.mouseMove = function(a, b) {
	if (this.graph.isEnabled() && this.isEnabled()) {
		var c = [a, b];
		this.handlers.visit(function(a, b) {
			b.mouseMove.apply(b, c)
		})
	}
};
SelectionCellsHandler.prototype.mouseUp = function(a, b) {
	if (this.graph.isEnabled() && this.isEnabled()) {
		var c = [a, b];
		this.handlers.visit(function(a, b) {
			b.mouseUp.apply(b, c)
		})
	}
};
SelectionCellsHandler.prototype.destroy = function() {
	this.graph.removeMouseListener(this);
	null != this.refreshHandler && (this.graph.getSelectionModel().removeListener(this.refreshHandler), this.graph.getModel().removeListener(this.refreshHandler), this.graph.getView().removeListener(this.refreshHandler), this.refreshHandler = null)
};

function ConnectionHandler(a, b) {
	EventSource.call(this);
	null != a && (this.graph = a, this.factoryMethod = b, this.init(), this.escapeHandler = Utils.bind(this, function(a, b) {
		this.reset()
	}), this.graph.addListener(Event.ESCAPE, this.escapeHandler))
}
Utils.extend(ConnectionHandler, EventSource);
ConnectionHandler.prototype.graph = null;
ConnectionHandler.prototype.factoryMethod = !0;
ConnectionHandler.prototype.moveIconFront = !1;
ConnectionHandler.prototype.moveIconBack = !1;
ConnectionHandler.prototype.connectImage = null;
ConnectionHandler.prototype.targetConnectImage = !1;
ConnectionHandler.prototype.enabled = !0;
ConnectionHandler.prototype.select = !0;
ConnectionHandler.prototype.createTarget = !1;
ConnectionHandler.prototype.marker = null;
ConnectionHandler.prototype.constraintHandler = null;
ConnectionHandler.prototype.error = null;
ConnectionHandler.prototype.waypointsEnabled = !1;
ConnectionHandler.prototype.ignoreMouseDown = !1;
ConnectionHandler.prototype.first = null;
ConnectionHandler.prototype.connectIconOffset = new Point(0, Constants.TOOLTIP_VERTICAL_OFFSET);
ConnectionHandler.prototype.edgeState = null;
ConnectionHandler.prototype.changeHandler = null;
ConnectionHandler.prototype.drillHandler = null;
ConnectionHandler.prototype.mouseDownCounter = 0;
ConnectionHandler.prototype.movePreviewAway = Client.IS_VML;
ConnectionHandler.prototype.outlineConnect = !1;
ConnectionHandler.prototype.isEnabled = function() {
	return this.enabled
};
ConnectionHandler.prototype.setEnabled = function(a) {
	this.enabled = a
};
ConnectionHandler.prototype.isCreateTarget = function() {
	return this.createTarget
};
ConnectionHandler.prototype.setCreateTarget = function(a) {
	this.createTarget = a
};
ConnectionHandler.prototype.createShape = function() {
	var a = new Polyline([], Constants.INVALID_COLOR);
	a.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG;
	a.pointerEvents = !1;
	a.isDashed = !0;
	a.init(this.graph.getView().getOverlayPane());
	Event.redirectMouseEvents(a.node, this.graph, null);
	return a
};
ConnectionHandler.prototype.init = function() {
	this.graph.addMouseListener(this);
	this.marker = this.createMarker();
	this.constraintHandler = new ConstraintHandler(this.graph);
	this.changeHandler = Utils.bind(this, function(a) {
		null != this.iconState && (this.iconState = this.graph.getView().getState(this.iconState.cell));
		null != this.iconState ? (this.redrawIcons(this.icons, this.iconState), this.constraintHandler.reset()) : this.reset()
	});
	this.graph.getModel().addListener(Event.CHANGE, this.changeHandler);
	this.graph.getView().addListener(Event.SCALE, this.changeHandler);
	this.graph.getView().addListener(Event.TRANSLATE, this.changeHandler);
	this.graph.getView().addListener(Event.SCALE_AND_TRANSLATE, this.changeHandler);
	this.drillHandler = Utils.bind(this, function(a) {
		this.reset()
	});
	this.graph.addListener(Event.START_EDITING, this.drillHandler);
	this.graph.getView().addListener(Event.DOWN, this.drillHandler);
	this.graph.getView().addListener(Event.UP, this.drillHandler)
};
ConnectionHandler.prototype.isConnectableCell = function(a) {
	return !0
};
ConnectionHandler.prototype.createMarker = function() {
	var a = new CellMarker(this.graph);
	a.hotspotEnabled = !0;
	a.getCell = Utils.bind(this, function(b, c) {
		c = CellMarker.prototype.getCell.apply(a, arguments);
		var d = this.graph.view.scale,
			d = new Point(this.graph.snap(b.getGraphX() / d) * d, this.graph.snap(b.getGraphY() / d) * d);
		this.error = null;
		null == c && (c = this.getCellAt(d.x, d.y));
		if (this.graph.isSwimlane(c) && this.graph.hitsSwimlaneContent(c, d.x, d.y) || !this.isConnectableCell(c)) c = null;
		null != c ? this.isConnecting() ? null != this.previous && (this.error = this.validateConnection(this.previous.cell, c), null != this.error && 0 == this.error.length && (c = null, this.isCreateTarget() && (this.error = null))) : this.isValidSource(c, b) || (c = null) : this.isConnecting() && (!this.isCreateTarget() && !this.graph.allowDanglingEdges) && (this.error = "");
		return c
	});
	a.isValidState = Utils.bind(this, function(b) {
		return this.isConnecting() ? null == this.error : CellMarker.prototype.isValidState.apply(a, arguments)
	});
	a.getMarkerColor = Utils.bind(this, function(b, c, d) {
		return null == this.connectImage || this.isConnecting() ? CellMarker.prototype.getMarkerColor.apply(a, arguments) : null
	});
	a.intersects = Utils.bind(this, function(b, c) {
		return null != this.connectImage || this.isConnecting() ? !0 : CellMarker.prototype.intersects.apply(a, arguments)
	});
	return a
};
ConnectionHandler.prototype.start = function(a, b, c, d) {
	this.previous = a;
	this.first = new Point(b, c);
	this.edgeState = null != d ? d : this.createEdgeState(null);
	this.marker.currentColor = this.marker.validColor;
	this.marker.markedState = a;
	this.marker.mark();
	this.fireEvent(new EventObject(Event.START, "state", this.previous))
};
ConnectionHandler.prototype.getCellAt = function(a, b) {
	return !this.outlineConnect ? this.graph.getCellAt(a, b) : null
};
ConnectionHandler.prototype.isConnecting = function() {
	return null != this.first && null != this.shape
};
ConnectionHandler.prototype.isValidSource = function(a, b) {
	return this.graph.isValidSource(a)
};
ConnectionHandler.prototype.isValidTarget = function(a) {
	return !0
};
ConnectionHandler.prototype.validateConnection = function(a, b) {
	return !this.isValidTarget(b) ? "" : this.graph.getEdgeValidationError(null, a, b)
};
ConnectionHandler.prototype.getConnectImage = function(a) {
	return this.connectImage
};
ConnectionHandler.prototype.isMoveIconToFrontForState = function(a) {
	return null != a.text && a.text.node.parentNode == this.graph.container ? !0 : this.moveIconFront
};
ConnectionHandler.prototype.createIcons = function(a) {
	var b = this.getConnectImage(a);
	if (null != b && null != a) {
		this.iconState = a;
		var c = [],
			d = new Rectangle(0, 0, b.width, b.height),
			e = new ImageShape(d, b.src, null, null, 0);
		e.preserveImageAspect = !1;
		this.isMoveIconToFrontForState(a) ? (e.dialect = Constants.DIALECT_STRICTHTML, e.init(this.graph.container)) : (e.dialect = this.graph.dialect == Constants.DIALECT_SVG ? Constants.DIALECT_SVG : Constants.DIALECT_VML, e.init(this.graph.getView().getOverlayPane()), this.moveIconBack && null != e.node.previousSibling && e.node.parentNode.insertBefore(e.node, e.node.parentNode.firstChild));
		e.node.style.cursor = Constants.CURSOR_CONNECT;
		var f = Utils.bind(this, function() {
			return null != this.currentState ? this.currentState : a
		}),
			b = Utils.bind(this, function(a) {
				Event.isConsumed(a) || (this.icon = e, this.graph.fireMouseEvent(Event.MOUSE_DOWN, new MouseEvent(a, f())))
			});
		Event.redirectMouseEvents(e.node, this.graph, f, b);
		c.push(e);
		this.redrawIcons(c, this.iconState);
		return c
	}
	return null
};
ConnectionHandler.prototype.redrawIcons = function(a, b) {
	if (null != a && null != a[0] && null != b) {
		var c = this.getIconPosition(a[0], b);
		a[0].bounds.x = c.x;
		a[0].bounds.y = c.y;
		a[0].redraw()
	}
};
ConnectionHandler.prototype.getIconPosition = function(a, b) {
	var c = this.graph.getView().scale,
		d = b.getCenterX(),
		e = b.getCenterY();
	if (this.graph.isSwimlane(b.cell)) {
		var f = this.graph.getStartSize(b.cell),
			d = 0 != f.width ? b.x + f.width * c / 2 : d,
			e = 0 != f.height ? b.y + f.height * c / 2 : e,
			f = Utils.toRadians(Utils.getValue(b.style, Constants.STYLE_ROTATION) || 0);
		if (0 != f) var c = Math.cos(f),
			f = Math.sin(f),
			g = new Point(b.getCenterX(), b.getCenterY()),
			e = Utils.getRotatedPoint(new Point(d, e), c, f, g),
			d = e.x,
			e = e.y
	}
	return new Point(d - a.bounds.width / 2, e - a.bounds.height / 2)
};
ConnectionHandler.prototype.destroyIcons = function() {
	if (null != this.icons) {
		for (var a = 0; a < this.icons.length; a++) this.icons[a].destroy();
		this.iconState = this.selectedIcon = this.icon = this.icons = null
	}
};
ConnectionHandler.prototype.isStartEvent = function(a) {
	return null != this.constraintHandler.currentFocus && null != this.constraintHandler.currentConstraint || null != this.previous && null == this.error && (null == this.icons || null != this.icons && null != this.icon)
};
ConnectionHandler.prototype.mouseDown = function(a, b) {
	this.mouseDownCounter++;
	if (this.isEnabled() && this.graph.isEnabled() && !b.isConsumed() && !this.isConnecting() && this.isStartEvent(b)) {
		null != this.constraintHandler.currentConstraint && null != this.constraintHandler.currentFocus && null != this.constraintHandler.currentPoint ? (this.sourceConstraint = this.constraintHandler.currentConstraint, this.previous = this.constraintHandler.currentFocus, this.first = this.constraintHandler.currentPoint.clone()) : this.first = new Point(b.getGraphX(), b.getGraphY());
		this.edgeState = this.createEdgeState(b);
		this.mouseDownCounter = 1;
		this.waypointsEnabled && null == this.shape && (this.waypoints = null, this.shape = this.createShape());
		if (null == this.previous && null != this.edgeState) {
			var c = this.graph.getPointForEvent(b.getEvent());
			this.edgeState.cell.geometry.setTerminalPoint(c, !0)
		}
		this.fireEvent(new EventObject(Event.START, "state", this.previous));
		b.consume()
	}
	this.selectedIcon = this.icon;
	this.icon = null
};
ConnectionHandler.prototype.isImmediateConnectSource = function(a) {
	return !this.graph.isCellMovable(a.cell)
};
ConnectionHandler.prototype.createEdgeState = function(a) {
	return null
};
ConnectionHandler.prototype.isOutlineConnectEvent = function(a) {
	return this.outlineConnect && (a.isSource(this.marker.highlight.shape) || Event.isAltDown(a.getEvent()))
};
ConnectionHandler.prototype.updateCurrentState = function(a, b) {
	this.constraintHandler.update(a, null == this.first);
	if (null != this.constraintHandler.currentFocus && null != this.constraintHandler.currentConstraint) this.marker.reset(), this.currentState = this.constraintHandler.currentFocus;
	else if (this.marker.process(a), this.currentState = this.marker.getValidState(), null != this.currentState && this.isOutlineConnectEvent(a)) {
		var c = this.graph.getOutlineConstraint(b, this.currentState, a);
		this.constraintHandler.currentConstraint = c;
		this.constraintHandler.currentFocus = this.currentState;
		this.constraintHandler.currentPoint = b
	}
	this.outlineConnect && (null != this.marker.highlight && null != this.marker.highlight.shape) && (null != this.constraintHandler.currentConstraint && null != this.constraintHandler.currentFocus ? (this.marker.highlight.shape.stroke = Constants.OUTLINE_HIGHLIGHT_COLOR, this.marker.highlight.shape.strokewidth = Constants.OUTLINE_HIGHLIGHT_STROKEWIDTH / this.graph.view.scale / this.graph.view.scale, this.marker.highlight.repaint()) : this.marker.hasValidState() && (this.marker.highlight.shape.stroke = Constants.DEFAULT_VALID_COLOR, this.marker.highlight.shape.strokewidth = Constants.HIGHLIGHT_STROKEWIDTH / this.graph.view.scale / this.graph.view.scale, this.marker.highlight.repaint()))
};
ConnectionHandler.prototype.convertWaypoint = function(a) {
	var b = this.graph.getView().getScale(),
		c = this.graph.getView().getTranslate();
	a.x = a.x / b - c.x;
	a.y = a.y / b - c.y
};
ConnectionHandler.prototype.mouseMove = function(a, b) {
	if (!b.isConsumed() && (this.ignoreMouseDown || null != this.first || !this.graph.isMouseDown)) {
		!this.isEnabled() && null != this.currentState && (this.destroyIcons(), this.currentState = null);
		var c = this.graph.getView(),
			d = c.scale,
			e = c.translate,
			c = new Point(b.getGraphX(), b.getGraphY());
		this.graph.isGridEnabledEvent(b.getEvent()) && (c = new Point((this.graph.snap(c.x / d - e.x) + e.x) * d, (this.graph.snap(c.y / d - e.y) + e.y) * d));
		this.currentPoint = c;
		(null != this.first || this.isEnabled() && this.graph.isEnabled()) && this.updateCurrentState(b, c);
		if (null != this.first) {
			var f = null,
				d = c;
			null != this.constraintHandler.currentConstraint && null != this.constraintHandler.currentFocus && null != this.constraintHandler.currentPoint ? (f = this.constraintHandler.currentConstraint, d = this.constraintHandler.currentPoint.clone()) : null != this.previous && Event.isShiftDown(b.getEvent()) && (Math.abs(this.previous.getCenterX() - c.x) < Math.abs(this.previous.getCenterY() - c.y) ? c.x = this.previous.getCenterX() : c.y = this.previous.getCenterY());
			e = this.first;
			if (null != this.selectedIcon) {
				var g = this.selectedIcon.bounds.width,
					k = this.selectedIcon.bounds.height;
				null != this.currentState && this.targetConnectImage ? (g = this.getIconPosition(this.selectedIcon, this.currentState), this.selectedIcon.bounds.x = g.x, this.selectedIcon.bounds.y = g.y) : (g = new Rectangle(b.getGraphX() + this.connectIconOffset.x, b.getGraphY() + this.connectIconOffset.y, g, k), this.selectedIcon.bounds = g);
				this.selectedIcon.redraw()
			}
			if (null != this.edgeState) {
				this.edgeState.absolutePoints = [null, null != this.currentState ? null : d];
				this.graph.view.updateFixedTerminalPoint(this.edgeState, this.previous, !0, this.sourceConstraint);
				null != this.currentState && (null == f && (f = this.graph.getConnectionConstraint(this.edgeState, this.previous, !1)), this.edgeState.setAbsoluteTerminalPoint(null, !1), this.graph.view.updateFixedTerminalPoint(this.edgeState, this.currentState, !1, f));
				e = null;
				if (null != this.waypoints) {
					e = [];
					for (d = 0; d < this.waypoints.length; d++) f = this.waypoints[d].clone(), this.convertWaypoint(f), e[d] = f
				}
				this.graph.view.updatePoints(this.edgeState, e, this.previous, this.currentState);
				this.graph.view.updateFloatingTerminalPoints(this.edgeState, this.previous, this.currentState);
				d = this.edgeState.absolutePoints[this.edgeState.absolutePoints.length - 1];
				e = this.edgeState.absolutePoints[0]
			} else null != this.currentState && null == this.constraintHandler.currentConstraint && (g = this.getTargetPerimeterPoint(this.currentState, b), null != g && (d = g)), null == this.sourceConstraint && null != this.previous && (g = this.getSourcePerimeterPoint(this.previous, null != this.waypoints && 0 < this.waypoints.length ? this.waypoints[0] : d, b), null != g && (e = g));
			if (null == this.currentState && this.movePreviewAway) {
				g = e;
				null != this.edgeState && 2 < this.edgeState.absolutePoints.length && (f = this.edgeState.absolutePoints[this.edgeState.absolutePoints.length - 2], null != f && (g = f));
				f = d.x - g.x;
				g = d.y - g.y;
				k = Math.sqrt(f * f + g * g);
				if (0 == k) return;
				d.x -= 4 * f / k;
				d.y -= 4 * g / k
			}
			if (null == this.shape && (f = Math.abs(c.x - this.first.x), g = Math.abs(c.y - this.first.y), f > this.graph.tolerance || g > this.graph.tolerance)) this.shape = this.createShape(), this.updateCurrentState(b, c);
			null != this.shape && (null != this.edgeState ? this.shape.points = this.edgeState.absolutePoints : (c = [e], null != this.waypoints && (c = c.concat(this.waypoints)), c.push(d), this.shape.points = c), this.drawPreview());
			Event.consume(b.getEvent());
			b.consume()
		} else!this.isEnabled() || !this.graph.isEnabled() ? this.constraintHandler.reset() : this.previous != this.currentState && null == this.edgeState ? (this.destroyIcons(), null != this.currentState && (null == this.error && null == this.constraintHandler.currentConstraint) && (this.icons = this.createIcons(this.currentState), null == this.icons && (this.currentState.setCursor(Constants.CURSOR_CONNECT), b.consume())), this.previous = this.currentState) : this.previous == this.currentState && (null != this.currentState && null == this.icons && !this.graph.isMouseDown) && b.consume();
		if (!this.graph.isMouseDown && null != this.currentState && null != this.icons) {
			c = !1;
			e = b.getSource();
			for (d = 0; d < this.icons.length && !c; d++) c = e == this.icons[d].node || e.parentNode == this.icons[d].node;
			c || this.updateIcons(this.currentState, this.icons, b)
		}
	} else this.constraintHandler.reset()
};
ConnectionHandler.prototype.getTargetPerimeterPoint = function(a, b) {
	var c = null,
		d = a.view,
		e = d.getPerimeterFunction(a);
	if (null != e) {
		var f = null != this.waypoints && 0 < this.waypoints.length ? this.waypoints[this.waypoints.length - 1] : new Point(this.previous.getCenterX(), this.previous.getCenterY()),
			d = e(d.getPerimeterBounds(a), this.edgeState, f, !1);
		null != d && (c = d)
	} else c = new Point(a.getCenterX(), a.getCenterY());
	return c
};
ConnectionHandler.prototype.getSourcePerimeterPoint = function(a, b, c) {
	c = null;
	var d = a.view,
		e = d.getPerimeterFunction(a),
		f = new Point(a.getCenterX(), a.getCenterY());
	if (null != e) {
		var g = Utils.getValue(a.style, Constants.STYLE_ROTATION, 0),
			k = -g * (Math.PI / 180);
		0 != g && (b = Utils.getRotatedPoint(new Point(b.x, b.y), Math.cos(k), Math.sin(k), f));
		a = e(d.getPerimeterBounds(a), a, b, !1);
		null != a && (0 != g && (a = Utils.getRotatedPoint(new Point(a.x, a.y), Math.cos(-k), Math.sin(-k), f)), c = a)
	} else c = f;
	return c
};
ConnectionHandler.prototype.updateIcons = function(a, b, c) {};
ConnectionHandler.prototype.isStopEvent = function(a) {
	return null != a.getState()
};
ConnectionHandler.prototype.addWaypointForEvent = function(a) {
	var b = Utils.convertPoint(this.graph.container, a.getX(), a.getY()),
		c = Math.abs(b.x - this.first.x),
		b = Math.abs(b.y - this.first.y);
	if (null != this.waypoints || 1 < this.mouseDownCounter && (c > this.graph.tolerance || b > this.graph.tolerance)) null == this.waypoints && (this.waypoints = []), c = this.graph.view.scale, b = new Point(this.graph.snap(a.getGraphX() / c) * c, this.graph.snap(a.getGraphY() / c) * c), this.waypoints.push(b)
};
ConnectionHandler.prototype.mouseUp = function(a, b) {
	if (!b.isConsumed() && this.isConnecting()) {
		if (this.waypointsEnabled && !this.isStopEvent(b)) {
			this.addWaypointForEvent(b);
			b.consume();
			return
		}
		if (null == this.error) {
			var c = null != this.previous ? this.previous.cell : null,
				d = null;
			null != this.constraintHandler.currentConstraint && null != this.constraintHandler.currentFocus && (d = this.constraintHandler.currentFocus.cell);
			null == d && this.marker.hasValidState() && (d = this.marker.validState.cell);
			this.connect(c, d, b.getEvent(), b.getCell())
		} else null != this.previous && (null != this.marker.validState && this.previous.cell == this.marker.validState.cell) && this.graph.selectCellForEvent(this.marker.source, evt), 0 < this.error.length && this.graph.validationAlert(this.error);
		this.destroyIcons();
		b.consume()
	}
	null != this.first && this.reset()
};
ConnectionHandler.prototype.reset = function() {
	null != this.shape && (this.shape.destroy(), this.shape = null);
	this.destroyIcons();
	this.marker.reset();
	this.constraintHandler.reset();
	this.sourceConstraint = this.error = this.previous = this.edgeState = null;
	this.mouseDownCounter = 0;
	this.first = null;
	this.fireEvent(new EventObject(Event.RESET))
};
ConnectionHandler.prototype.drawPreview = function() {
	var a = null == this.error;
	this.shape.strokewidth = this.getEdgeWidth(a);
	a = this.getEdgeColor(a);
	this.shape.stroke = a;
	this.shape.redraw()
};
ConnectionHandler.prototype.getEdgeColor = function(a) {
	return a ? Constants.VALID_COLOR : Constants.INVALID_COLOR
};
ConnectionHandler.prototype.getEdgeWidth = function(a) {
	return a ? 3 : 1
};
ConnectionHandler.prototype.connect = function(a, b, c, d) {
	if (null != b || this.isCreateTarget() || this.graph.allowDanglingEdges) {
		var e = this.graph.getModel(),
			f = !1,
			g = null;
		e.beginUpdate();
		try {
			if (null != a && (null == b && this.isCreateTarget()) && (b = this.createTargetVertex(c, a), null != b)) {
				d = this.graph.getDropTarget([b], c, d);
				f = !0;
				if (null == d || !this.graph.getModel().isEdge(d)) {
					var k = this.graph.getView().getState(d);
					if (null != k) {
						var l = e.getGeometry(b);
						l.x -= k.origin.x;
						l.y -= k.origin.y
					}
				} else d = this.graph.getDefaultParent();
				this.graph.addCell(b, d)
			}
			var m = this.graph.getDefaultParent();
			null != a && (null != b && e.getParent(a) == e.getParent(b) && e.getParent(e.getParent(a)) != e.getRoot()) && (m = e.getParent(a), null != a.geometry && a.geometry.relative && (null != b.geometry && b.geometry.relative) && (m = e.getParent(m)));
			l = k = null;
			null != this.edgeState && (k = this.edgeState.cell.value, l = this.edgeState.cell.style);
			g = this.insertEdge(m, null, k, a, b, l);
			if (null != g) {
				this.graph.setConnectionConstraint(g, a, !0, this.sourceConstraint);
				this.graph.setConnectionConstraint(g, b, !1, this.constraintHandler.currentConstraint);
				null != this.edgeState && e.setGeometry(g, this.edgeState.cell.geometry);
				var n = e.getGeometry(g);
				null == n && (n = new Geometry, n.relative = !0, e.setGeometry(g, n));
				if (null != this.waypoints && 0 < this.waypoints.length) {
					var p = this.graph.view.scale,
						q = this.graph.view.translate;
					n.points = [];
					for (a = 0; a < this.waypoints.length; a++) {
						var r = this.waypoints[a];
						n.points.push(new Point(r.x / p - q.x, r.y / p - q.y))
					}
				}
				if (null == b) {
					var s = this.graph.view.translate,
						p = this.graph.view.scale,
						r = new Point(this.currentPoint.x / p - s.x, this.currentPoint.y / p - s.y);
					r.x -= this.graph.panDx / this.graph.view.scale;
					r.y -= this.graph.panDy / this.graph.view.scale;
					n.setTerminalPoint(r, !1)
				}
				this.fireEvent(new EventObject(Event.CONNECT, "cell", g, "terminal", b, "event", c, "target", d))
			}
		} catch (t) {
			Log.show(), Log.debug(t.message)
		} finally {
			e.endUpdate()
		}
		this.select && this.selectCells(g, f ? b : null)
	}
};
ConnectionHandler.prototype.selectCells = function(a, b) {
	this.graph.setSelectionCell(a)
};
ConnectionHandler.prototype.insertEdge = function(a, b, c, d, e, f) {
	if (null == this.factoryMethod) return this.graph.insertEdge(a, b, c, d, e, f);
	b = this.createEdge(c, d, e, f);
	return b = this.graph.addEdge(b, a, d, e)
};
ConnectionHandler.prototype.createTargetVertex = function(a, b) {
	for (var c = this.graph.getCellGeometry(b); null != c && c.relative;) b = this.graph.getModel().getParent(b), c = this.graph.getCellGeometry(b);
	var d = this.graph.cloneCells([b])[0],
		c = this.graph.getModel().getGeometry(d);
	if (null != c) {
		var e = this.graph.view.translate,
			f = this.graph.view.scale,
			g = new Point(this.currentPoint.x / f - e.x, this.currentPoint.y / f - e.y);
		c.x = g.x - c.width / 2 - this.graph.panDx / f;
		c.y = g.y - c.height / 2 - this.graph.panDy / f;
		g = this.getAlignmentTolerance();
		if (0 < g) {
			var k = this.graph.view.getState(b);
			if (null != k) {
				var l = k.x / f - e.x,
					e = k.y / f - e.y;
				Math.abs(l - c.x) <= g && (c.x = l);
				Math.abs(e - c.y) <= g && (c.y = e)
			}
		}
	}
	return d
};
ConnectionHandler.prototype.getAlignmentTolerance = function(a) {
	return this.graph.isGridEnabled() ? this.graph.gridSize / 2 : this.graph.tolerance
};
ConnectionHandler.prototype.createEdge = function(a, b, c, d) {
	var e = null;
	null != this.factoryMethod && (e = this.factoryMethod(b, c, d));
	null == e && (e = new Cell(a || ""), e.setEdge(!0), e.setStyle(d), a = new Geometry, a.relative = !0, e.setGeometry(a));
	return e
};
ConnectionHandler.prototype.destroy = function() {
	this.graph.removeMouseListener(this);
	null != this.shape && (this.shape.destroy(), this.shape = null);
	null != this.marker && (this.marker.destroy(), this.marker = null);
	null != this.constraintHandler && (this.constraintHandler.destroy(), this.constraintHandler = null);
	null != this.changeHandler && (this.graph.getModel().removeListener(this.changeHandler), this.graph.getView().removeListener(this.changeHandler), this.changeHandler = null);
	null != this.drillHandler && (this.graph.removeListener(this.drillHandler), this.graph.getView().removeListener(this.drillHandler), this.drillHandler = null);
	null != this.escapeHandler && (this.graph.removeListener(this.escapeHandler), this.escapeHandler = null)
};

function ConstraintHandler(a) {
	this.graph = a
}
ConstraintHandler.prototype.pointImage = new Image(Client.imageBasePath + "/point.gif", 5, 5);
ConstraintHandler.prototype.graph = null;
ConstraintHandler.prototype.enabled = !0;
ConstraintHandler.prototype.highlightColor = Constants.DEFAULT_VALID_COLOR;
ConstraintHandler.prototype.isEnabled = function() {
	return this.enabled
};
ConstraintHandler.prototype.setEnabled = function(a) {
	this.enabled = a
};
ConstraintHandler.prototype.reset = function() {
	if (null != this.focusIcons) {
		for (var a = 0; a < this.focusIcons.length; a++) this.focusIcons[a].destroy();
		this.focusIcons = null
	}
	null != this.focusHighlight && (this.focusHighlight.destroy(), this.focusHighlight = null);
	this.focusPoints = this.currentFocus = this.currentPoint = this.currentFocusArea = this.currentConstraint = null
};
ConstraintHandler.prototype.getTolerance = function() {
	return this.graph.getTolerance()
};
ConstraintHandler.prototype.getImageForConstraint = function(a, b, c) {
	return this.pointImage
};
ConstraintHandler.prototype.isEventIgnored = function(a, b) {
	return !1
};
ConstraintHandler.prototype.isStateIgnored = function(a, b) {
	return !1
};
ConstraintHandler.prototype.destroyIcons = function() {
	if (null != this.focusIcons) {
		for (var a = 0; a < this.focusIcons.length; a++) this.focusIcons[a].destroy();
		this.focusPoints = this.focusIcons = null
	}
};
ConstraintHandler.prototype.destroyFocusHighlight = function() {
	null != this.focusHighlight && (this.focusHighlight.destroy(), this.focusHighlight = null)
};
ConstraintHandler.prototype.update = function(a, b) {
	if (this.isEnabled() && !this.isEventIgnored(a)) {
		var c = this.getTolerance(),
			d = new Rectangle(a.getGraphX() - c, a.getGraphY() - c, 2 * c, 2 * c),
			e = null != a.getCell() ? this.graph.isCellConnectable(a.getCell()) : !1;
		if (null == this.currentFocusArea || !Utils.intersects(this.currentFocusArea, d) || null != a.getState() && null != this.currentFocus && e) if (this.currentFocusArea = null, a.getState() != this.currentFocus) if (this.currentFocus = null, this.constraints = null != a.getState() && e && !this.isStateIgnored(a.getState(), b) ? this.graph.getAllConnectionConstraints(a.getState(), b) : null, null != this.constraints) {
			this.currentFocus = a.getState();
			this.currentFocusArea = new Rectangle(a.getState().x, a.getState().y, a.getState().width, a.getState().height);
			if (null != this.focusIcons) {
				for (e = 0; e < this.focusIcons.length; e++) this.focusIcons[e].destroy();
				this.focusPoints = this.focusIcons = null
			}
			this.focusIcons = [];
			this.focusPoints = [];
			for (e = 0; e < this.constraints.length; e++) {
				var f = this.graph.getConnectionPoint(a.getState(), this.constraints[e]),
					g = this.getImageForConstraint(a.getState(), this.constraints[e], f),
					k = g.src,
					g = new Rectangle(f.x - g.width / 2, f.y - g.height / 2, g.width, g.height),
					g = new ImageShape(g, k);
				g.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_MIXEDHTML : Constants.DIALECT_SVG;
				g.preserveImageAspect = !1;
				g.init(this.graph.getView().getDecoratorPane());
				(Client.IS_QUIRKS || 8 == document.documentMode) && Event.addListener(g.node, "dragstart", function(a) {
					Event.consume(a);
					return !1
				});
				null != g.node.previousSibling && g.node.parentNode.insertBefore(g.node, g.node.parentNode.firstChild);
				k = Utils.bind(this, function() {
					return null != this.currentFocus ? this.currentFocus : a.getState()
				});
				g.redraw();
				Event.redirectMouseEvents(g.node, this.graph, k);
				this.currentFocusArea.add(g.bounds);
				this.focusIcons.push(g);
				this.focusPoints.push(f)
			}
			this.currentFocusArea.grow(c)
		} else this.destroyIcons(), this.destroyFocusHighlight();
		this.currentPoint = this.currentConstraint = null;
		if (null != this.focusIcons && null != this.constraints && (null == a.getState() || this.currentFocus == a.getState())) for (e = 0; e < this.focusIcons.length; e++) if (Utils.intersects(this.focusIcons[e].bounds, d)) {
			this.currentConstraint = this.constraints[e];
			this.currentPoint = this.focusPoints[e];
			c = this.focusIcons[e].bounds.clone();
			c.grow(Client.IS_IE ? 3 : 2);
			Client.IS_IE && (c.width -= 1, c.height -= 1);
			null == this.focusHighlight ? (c = new RectangleShape(c, null, this.highlightColor, 3), c.pointerEvents = !1, c.dialect = this.graph.dialect == Constants.DIALECT_SVG ? Constants.DIALECT_SVG : Constants.DIALECT_VML, c.init(this.graph.getView().getOverlayPane()), this.focusHighlight = c, k = Utils.bind(this, function() {
				return null != this.currentFocus ? this.currentFocus : a.getState()
			}), Event.redirectMouseEvents(c.node, this.graph, k)) : (this.focusHighlight.bounds = c, this.focusHighlight.redraw());
			break
		}
		null == this.currentConstraint && this.destroyFocusHighlight()
	} else this.currentPoint = this.currentFocus = this.currentConstraint = null
};
ConstraintHandler.prototype.destroy = function() {
	this.reset()
};

function Rubberband(a) {
	null != a && (this.graph = a, this.graph.addMouseListener(this), this.forceRubberbandHandler = Utils.bind(this, function(a, c) {
		var d = c.getProperty("eventName"),
			e = c.getProperty("event");
		if (d == Event.MOUSE_DOWN && this.isForceRubberbandEvent(e)) {
			var d = Utils.getOffset(this.graph.container),
				f = Utils.getScrollOrigin(this.graph.container);
			f.x -= d.x;
			f.y -= d.y;
			this.start(e.getX() + f.x, e.getY() + f.y);
			e.consume(!1)
		}
	}), this.graph.addListener(Event.FIRE_MOUSE_EVENT, this.forceRubberbandHandler), this.panHandler = Utils.bind(this, function() {
		this.repaint()
	}), this.graph.addListener(Event.PAN, this.panHandler), this.gestureHandler = Utils.bind(this, function(a, c) {
		null != this.first && this.reset()
	}), this.graph.addListener(Event.GESTURE, this.gestureHandler), Client.IS_IE && Event.addListener(window, "unload", Utils.bind(this, function() {
		this.destroy()
	})))
}
Rubberband.prototype.defaultOpacity = 20;
Rubberband.prototype.enabled = !0;
Rubberband.prototype.div = null;
Rubberband.prototype.sharedDiv = null;
Rubberband.prototype.currentX = 0;
Rubberband.prototype.currentY = 0;
Rubberband.prototype.isEnabled = function() {
	return this.enabled
};
Rubberband.prototype.setEnabled = function(a) {
	this.enabled = a
};
Rubberband.prototype.isForceRubberbandEvent = function(a) {
	return Event.isAltDown(a.getEvent())
};
Rubberband.prototype.mouseDown = function(a, b) {
	if (!b.isConsumed() && this.isEnabled() && this.graph.isEnabled() && null == b.getState() && !Event.isMultiTouchEvent(b.getEvent())) {
		var c = Utils.getOffset(this.graph.container),
			d = Utils.getScrollOrigin(this.graph.container);
		d.x -= c.x;
		d.y -= c.y;
		this.start(b.getX() + d.x, b.getY() + d.y);
		b.consume(!1)
	}
};
Rubberband.prototype.start = function(a, b) {
	function c(a) {
		a = new MouseEvent(a);
		var b = Utils.convertPoint(d, a.getX(), a.getY());
		a.graphX = b.x;
		a.graphY = b.y;
		return a
	}
	this.first = new Point(a, b);
	var d = this.graph.container;
	this.dragHandler = Utils.bind(this, function(a) {
		this.mouseMove(this.graph, c(a))
	});
	this.dropHandler = Utils.bind(this, function(a) {
		this.mouseUp(this.graph, c(a))
	});
	Client.IS_FF && Event.addGestureListeners(document, null, this.dragHandler, this.dropHandler)
};
Rubberband.prototype.mouseMove = function(a, b) {
	if (!b.isConsumed() && null != this.first) {
		var c = Utils.getScrollOrigin(this.graph.container),
			d = Utils.getOffset(this.graph.container);
		c.x -= d.x;
		c.y -= d.y;
		var d = b.getX() + c.x,
			c = b.getY() + c.y,
			e = this.first.x - d,
			f = this.first.y - c,
			g = this.graph.tolerance;
		if (null != this.div || Math.abs(e) > g || Math.abs(f) > g) null == this.div && (this.div = this.createShape()), Utils.clearSelection(), this.update(d, c), b.consume()
	}
};
Rubberband.prototype.createShape = function() {
	null == this.sharedDiv && (this.sharedDiv = document.createElement("div"), this.sharedDiv.className = "Rubberband", Utils.setOpacity(this.sharedDiv, this.defaultOpacity));
	this.graph.container.appendChild(this.sharedDiv);
	return this.sharedDiv
};
Rubberband.prototype.mouseUp = function(a, b) {
	var c = null != this.div && "none" != this.div.style.display;
	this.reset();
	c && (c = new Rectangle(this.x, this.y, this.width, this.height), this.graph.selectRegion(c, b.getEvent()), b.consume())
};
Rubberband.prototype.reset = function() {
	null != this.div && this.div.parentNode.removeChild(this.div);
	Event.removeGestureListeners(document, null, this.dragHandler, this.dropHandler);
	this.dropHandler = this.dragHandler = null;
	this.currentY = this.currentX = 0;
	this.div = this.first = null
};
Rubberband.prototype.update = function(a, b) {
	this.currentX = a;
	this.currentY = b;
	this.repaint()
};
Rubberband.prototype.repaint = function() {
	if (null != this.div) {
		var a = this.currentX - this.graph.panDx,
			b = this.currentY - this.graph.panDy;
		this.x = Math.min(this.first.x, a);
		this.y = Math.min(this.first.y, b);
		this.width = Math.max(this.first.x, a) - this.x;
		this.height = Math.max(this.first.y, b) - this.y;
		a = Client.IS_VML ? this.graph.panDy : 0;
		this.div.style.left = this.x + (Client.IS_VML ? this.graph.panDx : 0) + "px";
		this.div.style.top = this.y + a + "px";
		this.div.style.width = Math.max(1, this.width) + "px";
		this.div.style.height = Math.max(1, this.height) + "px"
	}
};
Rubberband.prototype.destroy = function() {
	this.destroyed || (this.destroyed = !0, this.graph.removeMouseListener(this), this.graph.removeListener(this.forceRubberbandHandler), this.graph.removeListener(this.panHandler), this.reset(), null != this.sharedDiv && (this.sharedDiv = null))
};

function VertexHandler(a) {
	null != a && (this.state = a, this.init(), this.escapeHandler = Utils.bind(this, function(a, c) {
		this.livePreview && (this.state.view.graph.cellRenderer.redraw(this.state, !0), this.state.view.invalidate(this.state.cell), this.state.invalid = !1, this.state.view.validate());
		this.reset()
	}), this.state.view.graph.addListener(Event.ESCAPE, this.escapeHandler))
}
VertexHandler.prototype.graph = null;
VertexHandler.prototype.state = null;
VertexHandler.prototype.singleSizer = !1;
VertexHandler.prototype.index = null;
VertexHandler.prototype.allowHandleBoundsCheck = !0;
VertexHandler.prototype.handleImage = null;
VertexHandler.prototype.tolerance = 0;
VertexHandler.prototype.rotationEnabled = !1;
VertexHandler.prototype.rotationRaster = !0;
VertexHandler.prototype.rotationCursor = "crosshair";
VertexHandler.prototype.livePreview = !1;
VertexHandler.prototype.manageSizers = !1;
VertexHandler.prototype.constrainGroupByChildren = !1;
VertexHandler.prototype.rotationHandleVSpacing = -16;
VertexHandler.prototype.horizontalOffset = 0;
VertexHandler.prototype.verticalOffset = 0;
VertexHandler.prototype.init = function() {
	this.graph = this.state.view.graph;
	this.selectionBounds = this.getSelectionBounds(this.state);
	this.bounds = new Rectangle(this.selectionBounds.x, this.selectionBounds.y, this.selectionBounds.width, this.selectionBounds.height);
	this.selectionBorder = this.createSelectionShape(this.bounds);
	this.selectionBorder.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG;
	this.selectionBorder.pointerEvents = !1;
	this.selectionBorder.init(this.graph.getView().getOverlayPane());
	Event.redirectMouseEvents(this.selectionBorder.node, this.graph, this.state);
	this.graph.isCellMovable(this.state.cell) && (this.selectionBorder.node.style.cursor = Constants.CURSOR_MOVABLE_VERTEX);
	if (0 >= GraphHandler.prototype.maxCells || this.graph.getSelectionCount() < GraphHandler.prototype.maxCells) {
		var a = this.graph.isCellResizable(this.state.cell);
		this.sizers = [];
		if (a || this.graph.isLabelMovable(this.state.cell) && 2 <= this.state.width && 2 <= this.state.height) {
			var b = 0;
			a && (this.singleSizer || (this.sizers.push(this.createSizer("nw-resize", b++)), this.sizers.push(this.createSizer("n-resize", b++)), this.sizers.push(this.createSizer("ne-resize", b++)), this.sizers.push(this.createSizer("w-resize", b++)), this.sizers.push(this.createSizer("e-resize", b++)), this.sizers.push(this.createSizer("sw-resize", b++)), this.sizers.push(this.createSizer("s-resize", b++))), this.sizers.push(this.createSizer("se-resize", b++)));
			a = this.graph.model.getGeometry(this.state.cell);
			null != a && (!a.relative && !this.graph.isSwimlane(this.state.cell) && this.graph.isLabelMovable(this.state.cell)) && (this.labelShape = this.createSizer(Constants.CURSOR_LABEL_HANDLE, Event.LABEL_HANDLE, Constants.LABEL_HANDLE_SIZE, Constants.LABEL_HANDLE_FILLCOLOR), this.sizers.push(this.labelShape))
		} else this.graph.isCellMovable(this.state.cell) && (!this.graph.isCellResizable(this.state.cell) && 2 > this.state.width && 2 > this.state.height) && (this.labelShape = this.createSizer(Constants.CURSOR_MOVABLE_VERTEX, null, null, Constants.LABEL_HANDLE_FILLCOLOR), this.sizers.push(this.labelShape))
	}
	if (this.graph.isEnabled() && this.rotationEnabled && this.graph.isCellRotatable(this.state.cell) && (0 >= GraphHandler.prototype.maxCells || this.graph.getSelectionCount() < GraphHandler.prototype.maxCells) && 2 < this.state.width && 2 < this.state.height) this.rotationShape = this.createSizer(this.rotationCursor, Event.ROTATION_HANDLE, Constants.HANDLE_SIZE + 3, Constants.HANDLE_FILLCOLOR), this.sizers.push(this.rotationShape);
	this.redraw();
	this.constrainGroupByChildren && this.updateMinBounds()
};
VertexHandler.prototype.isConstrainedEvent = function(a) {
	return Event.isShiftDown(a.getEvent()) || "fixed" == this.state.style[Constants.STYLE_ASPECT]
};
VertexHandler.prototype.updateMinBounds = function() {
	var a = this.graph.getChildCells(this.state.cell);
	if (0 < a.length && (this.minBounds = this.graph.view.getBounds(a), null != this.minBounds)) {
		var a = this.state.view.scale,
			b = this.state.view.translate;
		this.minBounds.x -= this.state.x;
		this.minBounds.y -= this.state.y;
		this.minBounds.x /= a;
		this.minBounds.y /= a;
		this.minBounds.width /= a;
		this.minBounds.height /= a;
		this.x0 = this.state.x / a - b.x;
		this.y0 = this.state.y / a - b.y
	}
};
VertexHandler.prototype.getSelectionBounds = function(a) {
	return new Rectangle(Math.round(a.x), Math.round(a.y), Math.round(a.width), Math.round(a.height))
};
VertexHandler.prototype.createSelectionShape = function(a) {
	a = new RectangleShape(a, null, this.getSelectionColor());
	a.strokewidth = this.getSelectionStrokeWidth();
	a.isDashed = this.isSelectionDashed();
	return a
};
VertexHandler.prototype.getSelectionColor = function() {
	return Constants.VERTEX_SELECTION_COLOR
};
VertexHandler.prototype.getSelectionStrokeWidth = function() {
	return Constants.VERTEX_SELECTION_STROKEWIDTH
};
VertexHandler.prototype.isSelectionDashed = function() {
	return Constants.VERTEX_SELECTION_DASHED
};
VertexHandler.prototype.createSizer = function(a, b, c, d) {
	c = c || Constants.HANDLE_SIZE;
	c = new Rectangle(0, 0, c, c);
	d = this.createSizerShape(c, b, d);
	d.isHtmlAllowed() && null != this.state.text && this.state.text.node.parentNode == this.graph.container ? (d.bounds.height -= 1, d.bounds.width -= 1, d.dialect = Constants.DIALECT_STRICTHTML, d.init(this.graph.container)) : (d.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_MIXEDHTML : Constants.DIALECT_SVG, d.init(this.graph.getView().getOverlayPane()));
	Event.redirectMouseEvents(d.node, this.graph, this.state);
	this.graph.isEnabled() && (d.node.style.cursor = a);
	this.isSizerVisible(b) || (d.node.style.visibility = "hidden");
	return d
};
VertexHandler.prototype.isSizerVisible = function(a) {
	return !0
};
VertexHandler.prototype.createSizerShape = function(a, b, c) {
	return null != this.handleImage ? (a = new Rectangle(a.x, a.y, this.handleImage.width, this.handleImage.height), a = new ImageShape(a, this.handleImage.src), a.preserveImageAspect = !1, a) : b == Event.ROTATION_HANDLE ? new Ellipse(a, c || Constants.HANDLE_FILLCOLOR, Constants.HANDLE_STROKECOLOR) : new RectangleShape(a, c || Constants.HANDLE_FILLCOLOR, Constants.HANDLE_STROKECOLOR)
};
VertexHandler.prototype.moveSizerTo = function(a, b, c) {
	null != a && (a.bounds.x = Math.round(b - a.bounds.width / 2), a.bounds.y = Math.round(c - a.bounds.height / 2), null != a.node && "none" != a.node.style.display && a.redraw())
};
VertexHandler.prototype.getHandleForEvent = function(a) {
	function b(b) {
		if (null != b && (a.isSource(b) || null != d && Utils.intersects(b.bounds, d) && "none" != b.node.style.display && "hidden" != b.node.style.visibility)) {
			var c = a.getGraphX() - b.bounds.getCenterX();
			b = a.getGraphY() - b.bounds.getCenterY();
			c = c * c + b * b;
			if (null == e || c <= e) return e = c, !0
		}
		return !1
	}
	var c = !Event.isMouseEvent(a.getEvent()) ? this.tolerance : 1,
		d = this.allowHandleBoundsCheck && (Client.IS_IE || 0 < c) ? new Rectangle(a.getGraphX() - c, a.getGraphY() - c, 2 * c, 2 * c) : null,
		e = null;
	if (b(this.rotationShape)) return Event.ROTATION_HANDLE;
	if (b(this.labelShape)) return Event.LABEL_HANDLE;
	if (null != this.sizers) for (c = 0; c < this.sizers.length; c++) if (b(this.sizers[c])) return c;
	return null
};
VertexHandler.prototype.mouseDown = function(a, b) {
	var c = !Event.isMouseEvent(b.getEvent()) ? this.tolerance : 0;
	if (!b.isConsumed() && this.graph.isEnabled() && (0 < c || b.getState() == this.state)) c = this.getHandleForEvent(b), null != c && (this.start(b.getGraphX(), b.getGraphY(), c), b.consume())
};
VertexHandler.prototype.isLivePreviewBorder = function() {
	return null != this.state.shape && null == this.state.shape.fill && null == this.state.shape.stroke
};
VertexHandler.prototype.start = function(a, b, c) {
	this.inTolerance = !0;
	this.childOffsetY = this.childOffsetX = 0;
	this.index = c;
	this.startX = a;
	this.startY = b;
	a = this.state.view.graph.model.getParent(this.state.cell);
	this.state.view.graph.model.isVertex(a) && this.state.view.currentRoot != a && (this.parentState = this.state.view.graph.view.getState(a));
	this.selectionBorder.node.style.display = c == Event.ROTATION_HANDLE ? "inline" : "none";
	if (!this.livePreview || this.isLivePreviewBorder()) this.preview = this.createSelectionShape(this.bounds), !(Client.IS_SVG && 0 != Number(this.state.style[Constants.STYLE_ROTATION] || "0")) && null != this.state.text && this.state.text.node.parentNode == this.graph.container ? (this.preview.dialect = Constants.DIALECT_STRICTHTML, this.preview.init(this.graph.container)) : (this.preview.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG, this.preview.init(this.graph.view.getOverlayPane()));
	if (this.livePreview) {
		this.hideSizers();
		c == Event.ROTATION_HANDLE ? this.rotationShape.node.style.display = "" : null != this.sizers[c] && (this.sizers[c].node.style.display = "");
		c = this.graph.getEdges(this.state.cell);
		this.edgeHandlers = [];
		for (a = 0; a < c.length; a++) b = this.graph.selectionCellsHandler.getHandler(c[a]), null != b && this.edgeHandlers.push(b)
	}
};
VertexHandler.prototype.hideSizers = function() {
	if (null != this.sizers) for (var a = 0; a < this.sizers.length; a++) this.sizers[a].node.style.display = "none"
};
VertexHandler.prototype.checkTolerance = function(a) {
	if (this.inTolerance && (null != this.startX && null != this.startY) && (Event.isMouseEvent(a.getEvent()) || Math.abs(a.getGraphX() - this.startX) > this.graph.tolerance || Math.abs(a.getGraphY() - this.startY) > this.graph.tolerance)) this.inTolerance = !1
};
VertexHandler.prototype.updateHint = function(a) {};
VertexHandler.prototype.removeHint = function() {};
VertexHandler.prototype.roundAngle = function(a) {
	return Math.round(a)
};
VertexHandler.prototype.roundLength = function(a) {
	return Math.round(a)
};
VertexHandler.prototype.mouseMove = function(a, b) {
	if (!b.isConsumed() && null != this.index) {
		this.checkTolerance(b);
		if (!this.inTolerance) {
			var c = new Point(b.getGraphX(), b.getGraphY()),
				d = this.graph.isGridEnabledEvent(b.getEvent()),
				e = this.graph.view.scale,
				f = this.graph.view.translate;
			if (this.index == Event.LABEL_HANDLE) d && (c.x = (this.graph.snap(c.x / e - f.x) + f.x) * e, c.y = (this.graph.snap(c.y / e - f.y) + f.y) * e), this.moveSizerTo(this.sizers[this.sizers.length - 1], c.x, c.y);
			else if (this.index == Event.ROTATION_HANDLE) {
				var g = this.state.x + this.state.width / 2 - c.x,
					k = this.state.y + this.state.height / 2 - c.y;
				this.currentAlpha = 0 != g ? 180 * Math.atan(k / g) / Math.PI + 90 : 0 > k ? 180 : 0;
				0 < g && (this.currentAlpha -= 180);
				this.rotationRaster && this.graph.isGridEnabledEvent(b.getEvent()) ? (g = c.x - this.state.getCenterX(), k = c.y - this.state.getCenterY(), e = Math.abs(Math.sqrt(g * g + k * k) - this.state.height / 2 - 20), e = Math.max(1, 5 * Math.min(3, Math.max(0, Math.round(80 / Math.abs(e))))), this.currentAlpha = Math.round(this.currentAlpha / e) * e) : this.currentAlpha = this.roundAngle(this.currentAlpha);
				this.selectionBorder.rotation = this.currentAlpha;
				this.selectionBorder.redraw();
				this.livePreview && this.redrawHandles()
			} else {
				var l = Utils.toRadians(this.state.style[Constants.STYLE_ROTATION] || "0"),
					m = Math.cos(-l),
					n = Math.sin(-l),
					p = new Point(this.state.getCenterX(), this.state.getCenterY()),
					g = c.x - this.startX,
					k = c.y - this.startY,
					c = n * g + m * k,
					g = m * g - n * k,
					k = c,
					m = this.graph.getCellGeometry(this.state.cell);
				this.unscaledBounds = this.union(m, g / e, k / e, this.index, d, 1, new Point(0, 0), this.isConstrainedEvent(b));
				this.bounds = new Rectangle((null != this.parentState ? this.parentState.x : f.x * e) + this.unscaledBounds.x * e, (null != this.parentState ? this.parentState.y : f.y * e) + this.unscaledBounds.y * e, this.unscaledBounds.width * e, this.unscaledBounds.height * e);
				m = Math.cos(l);
				n = Math.sin(l);
				k = new Point(this.bounds.getCenterX(), this.bounds.getCenterY());
				g = k.x - p.x;
				k = k.y - p.y;
				p = m * g - n * k - g;
				g = n * g + m * k - k;
				d = this.bounds.x - this.state.x;
				l = this.bounds.y - this.state.y;
				k = m * d - n * l;
				m = n * d + m * l;
				this.bounds.x += p;
				this.bounds.y += g;
				this.unscaledBounds.x = this.roundLength(this.unscaledBounds.x + p / e);
				this.unscaledBounds.y = this.roundLength(this.unscaledBounds.y + g / e);
				this.unscaledBounds.width = this.roundLength(this.unscaledBounds.width);
				this.unscaledBounds.height = this.roundLength(this.unscaledBounds.height);
				!this.graph.isCellCollapsed(this.state.cell) && (0 != p || 0 != g) ? (this.childOffsetX = this.state.x - this.bounds.x + k, this.childOffsetY = this.state.y - this.bounds.y + m) : this.childOffsetY = this.childOffsetX = 0;
				this.livePreview && (n = new Rectangle(this.state.x, this.state.y, this.state.width, this.state.height), g = this.state.origin, this.state.x = this.bounds.x, this.state.y = this.bounds.y, this.state.origin = new Point(this.state.x / e - f.x, this.state.y / e - f.y), this.state.width = this.bounds.width, this.state.height = this.bounds.height, e = this.state.absoluteOffset, e = new Point(e.x, e.y), this.state.absoluteOffset.x = 0, this.state.absoluteOffset.y = 0, m = this.graph.getCellGeometry(this.state.cell), null != m && (f = m.offset || this.EMPTY_POINT, null != f && !m.relative && (this.state.absoluteOffset.x = this.state.view.scale * f.x, this.state.absoluteOffset.y = this.state.view.scale * f.y), this.state.view.updateVertexLabelOffset(this.state)), this.state.view.graph.cellRenderer.redraw(this.state, !0), this.state.view.invalidate(this.state.cell), this.state.invalid = !1, this.state.view.validate(), this.redrawHandles(), this.state.x = n.x, this.state.y = n.y, this.state.width = n.width, this.state.height = n.height, this.state.origin = g, this.state.absoluteOffset = e);
				null != this.preview && this.drawPreview()
			}
			this.updateHint(b)
		}
		b.consume()
	} else!this.graph.isMouseDown && null != this.getHandleForEvent(b) && b.consume(!1)
};
VertexHandler.prototype.mouseUp = function(a, b) {
	if (null != this.index && null != this.state) {
		var c = new Point(b.getGraphX(), b.getGraphY());
		this.graph.getModel().beginUpdate();
		try {
			if (this.index == Event.ROTATION_HANDLE) {
				if (null != this.currentAlpha) {
					var d = this.currentAlpha - (this.state.style[Constants.STYLE_ROTATION] || 0);
					0 != d && this.rotateCell(this.state.cell, d)
				}
			} else {
				var e = this.graph.isGridEnabledEvent(b.getEvent()),
					f = Utils.toRadians(this.state.style[Constants.STYLE_ROTATION] || "0"),
					g = Math.cos(-f),
					k = Math.sin(-f),
					l = c.x - this.startX,
					m = c.y - this.startY,
					c = k * l + g * m,
					l = g * l - k * m,
					m = c,
					n = this.graph.view.scale,
					p = this.isRecursiveResize(this.state, b);
				this.resizeCell(this.state.cell, this.roundLength(l / n), this.roundLength(m / n), this.index, e, this.isConstrainedEvent(b), p)
			}
		} finally {
			this.graph.getModel().endUpdate()
		}
		b.consume();
		this.reset()
	}
};
VertexHandler.prototype.isRecursiveResize = function(a, b) {
	return this.graph.isRecursiveResize(this.state)
};
VertexHandler.prototype.rotateCell = function(a, b) {
	var c = this.graph.getModel();
	if (c.isVertex(a)) {
		var d = a == this.state ? this.state : this.graph.view.getState(a);
		null != d && this.graph.setCellStyles(Constants.STYLE_ROTATION, (d.style[Constants.STYLE_ROTATION] || 0) + b, [a]);
		if (this.state.cell != a && (d = this.graph.getCellGeometry(a), null != d && !d.relative && 0 != b)) {
			var e = this.graph.getModel().getParent(a),
				f = this.graph.getCellGeometry(e);
			if (!d.relative && null != f) {
				var g = Utils.toRadians(b),
					e = Math.cos(g),
					g = Math.sin(g),
					k = new Point(d.getCenterX(), d.getCenterY()),
					f = new Point(f.width / 2, f.height / 2),
					e = Utils.getRotatedPoint(k, e, g, f),
					d = d.clone();
				d.x = e.x - d.width / 2;
				d.y = e.y - d.height / 2;
				c.setGeometry(a, d)
			}
		}
		d = c.getChildCount(a);
		for (e = 0; e < d; e++) this.rotateCell(c.getChildAt(a, e), b)
	}
};
VertexHandler.prototype.reset = function() {
	null != this.sizers && (null != this.index && null != this.sizers[this.index] && "none" == this.sizers[this.index].node.style.display) && (this.sizers[this.index].node.style.display = "");
	this.index = this.inTolerance = this.currentAlpha = null;
	null != this.preview && (this.preview.destroy(), this.preview = null);
	null != this.selectionBorder && (this.selectionBorder.node.style.display = "inline", this.selectionBounds = this.getSelectionBounds(this.state), this.bounds = new Rectangle(this.selectionBounds.x, this.selectionBounds.y, this.selectionBounds.width, this.selectionBounds.height), this.drawPreview());
	if (this.livePreview && null != this.sizers) for (var a = 0; a < this.sizers.length; a++) null != this.sizers[a] && (this.sizers[a].node.style.display = "");
	this.removeHint();
	this.redrawHandles();
	this.unscaledBounds = this.edgeHandlers = null
};
VertexHandler.prototype.resizeCell = function(a, b, c, d, e, f, g) {
	e = this.graph.model.getGeometry(a);
	null != e && (d == Event.LABEL_HANDLE ? (c = this.graph.view.scale, b = (this.labelShape.bounds.getCenterX() - this.startX) / c, c = (this.labelShape.bounds.getCenterY() - this.startY) / c, e = e.clone(), null == e.offset ? e.offset = new Point(b, c) : (e.offset.x += b, e.offset.y += c), this.graph.model.setGeometry(a, e)) : null != this.unscaledBounds && (c = this.graph.view.scale, (0 != this.childOffsetX || 0 != this.childOffsetY) && this.moveChildren(a, this.childOffsetX / c, this.childOffsetY / c), this.graph.resizeCell(a, this.unscaledBounds, g)))
};
VertexHandler.prototype.moveChildren = function(a, b, c) {
	for (var d = this.graph.getModel(), e = d.getChildCount(a), f = 0; f < e; f++) {
		var g = d.getChildAt(a, f);
		if (d.isVertex(g)) {
			var k = this.graph.getCellGeometry(g);
			null != k && !k.relative && (k = k.clone(), k.x += b, k.y += c, d.setGeometry(g, k))
		}
	}
};
VertexHandler.prototype.union = function(a, b, c, d, e, f, g, k) {
	if (this.singleSizer) return d = a.x + a.width + b, g = a.y + a.height + c, e && (d = this.graph.snap(d / f) * f, g = this.graph.snap(g / f) * f), f = new Rectangle(a.x, a.y, 0, 0), f.add(new Rectangle(d, g, 0, 0)), f;
	var l = a.x - g.x * f,
		m = l + a.width,
		n = a.y - g.y * f;
	a = n + a.height;
	4 < d ? (a += c, e && (a = this.graph.snap(a / f) * f)) : 3 > d && (n += c, e && (n = this.graph.snap(n / f) * f));
	if (0 == d || 3 == d || 5 == d) l += b, e && (l = this.graph.snap(l / f) * f);
	else if (2 == d || 4 == d || 7 == d) m += b, e && (m = this.graph.snap(m / f) * f);
	e = m - l;
	c = a - n;
	k && (k = this.graph.getCellGeometry(this.state.cell), null != k && (k = k.width / k.height, 1 == d || 2 == d || 7 == d || 6 == d ? e = c * k : c = e / k, 0 == d && (l = m - e, n = a - c)));
	0 > e && (l += e, e = Math.abs(e));
	0 > c && (n += c, c = Math.abs(c));
	d = new Rectangle(l + g.x * f, n + g.y * f, e, c);
	null != this.minBounds && (d.width = Math.max(d.width, this.minBounds.x * f + this.minBounds.width * f + Math.max(0, this.x0 * f - d.x)), d.height = Math.max(d.height, this.minBounds.y * f + this.minBounds.height * f + Math.max(0, this.y0 * f - d.y)));
	return d
};
VertexHandler.prototype.redraw = function() {
	this.selectionBounds = this.getSelectionBounds(this.state);
	this.bounds = new Rectangle(this.selectionBounds.x, this.selectionBounds.y, this.selectionBounds.width, this.selectionBounds.height);
	this.redrawHandles();
	this.drawPreview()
};
VertexHandler.prototype.redrawHandles = function() {
	this.verticalOffset = this.horizontalOffset = 0;
	var a = this.bounds;
	if (null != this.sizers) {
		if (null == this.index && this.manageSizers && 1 < this.sizers.length) {
			var b = this.tolerance;
			a.width < 2 * this.sizers[0].bounds.width - 2 + 2 * b ? (this.sizers[1].node.style.display = "none", this.sizers[6].node.style.display = "none") : (this.sizers[1].node.style.display = "", this.sizers[6].node.style.display = "");
			a.height < 2 * this.sizers[0].bounds.height - 2 + 2 * b ? (this.sizers[3].node.style.display = "none", this.sizers[4].node.style.display = "none") : (this.sizers[3].node.style.display = "", this.sizers[4].node.style.display = "");
			if (a.width < 2 * this.sizers[0].bounds.width - 2 + 3 * b || a.height < 2 * this.sizers[0].bounds.height - 2 + 3 * b) a = new Rectangle(a.x, a.y, a.width, a.height), b /= 2, this.horizontalOffset = this.sizers[0].bounds.width + b, this.verticalOffset = this.sizers[0].bounds.height + b, a.x -= this.horizontalOffset / 2, a.width += this.horizontalOffset, a.y -= this.verticalOffset / 2, a.height += this.verticalOffset
		}
		var b = a.x + a.width,
			c = a.y + a.height;
		if (this.singleSizer) this.moveSizerTo(this.sizers[0], b, c);
		else {
			var d = a.x + a.width / 2,
				e = a.y + a.height / 2;
			if (1 < this.sizers.length) {
				var f = "nw-resize n-resize ne-resize e-resize se-resize s-resize sw-resize w-resize".split(" "),
					g = Utils.toRadians(this.state.style[Constants.STYLE_ROTATION] || "0"),
					k = Math.cos(g),
					l = Math.sin(g),
					g = Math.round(4 * g / Math.PI),
					m = new Point(a.getCenterX(), a.getCenterY()),
					n = Utils.getRotatedPoint(new Point(a.x, a.y), k, l, m);
				this.moveSizerTo(this.sizers[0], n.x, n.y);
				this.sizers[0].node.style.cursor = f[Utils.mod(0 + g, f.length)];
				n.x = d;
				n.y = a.y;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[1], n.x, n.y);
				this.sizers[1].node.style.cursor = f[Utils.mod(1 + g, f.length)];
				n.x = b;
				n.y = a.y;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[2], n.x, n.y);
				this.sizers[2].node.style.cursor = f[Utils.mod(2 + g, f.length)];
				n.x = a.x;
				n.y = e;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[3], n.x, n.y);
				this.sizers[3].node.style.cursor = f[Utils.mod(7 + g, f.length)];
				n.x = b;
				n.y = e;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[4], n.x, n.y);
				this.sizers[4].node.style.cursor = f[Utils.mod(3 + g, f.length)];
				n.x = a.x;
				n.y = c;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[5], n.x, n.y);
				this.sizers[5].node.style.cursor = f[Utils.mod(6 + g, f.length)];
				n.x = d;
				n.y = c;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[6], n.x, n.y);
				this.sizers[6].node.style.cursor = f[Utils.mod(5 + g, f.length)];
				n.x = b;
				n.y = c;
				n = Utils.getRotatedPoint(n, k, l, m);
				this.moveSizerTo(this.sizers[7], n.x, n.y);
				this.sizers[7].node.style.cursor = f[Utils.mod(4 + g, f.length)];
				this.moveSizerTo(this.sizers[8], d + this.state.absoluteOffset.x, e + this.state.absoluteOffset.y)
			} else 2 <= this.state.width && 2 <= this.state.height ? this.moveSizerTo(this.sizers[0], d + this.state.absoluteOffset.x, e + this.state.absoluteOffset.y) : this.moveSizerTo(this.sizers[0], a.x, a.y)
		}
	}
	null != this.rotationShape && (g = Utils.toRadians(null != this.currentAlpha ? this.currentAlpha : this.state.style[Constants.STYLE_ROTATION] || "0"), k = Math.cos(g), l = Math.sin(g), m = new Point(this.state.getCenterX(), this.state.getCenterY()), n = Utils.getRotatedPoint(new Point(a.x + a.width / 2, a.y + this.rotationHandleVSpacing), k, l, m), null != this.rotationShape.node && this.moveSizerTo(this.rotationShape, n.x, n.y));
	null != this.selectionBorder && (this.selectionBorder.rotation = Number(this.state.style[Constants.STYLE_ROTATION] || "0"));
	if (null != this.edgeHandlers) for (a = 0; a < this.edgeHandlers.length; a++) this.edgeHandlers[a].redraw()
};
VertexHandler.prototype.drawPreview = function() {
	null != this.preview && (this.preview.bounds = this.bounds, this.preview.node.parentNode == this.graph.container && (this.preview.bounds.width = Math.max(0, this.preview.bounds.width - 1), this.preview.bounds.height = Math.max(0, this.preview.bounds.height - 1)), this.preview.rotation = Number(this.state.style[Constants.STYLE_ROTATION] || "0"), this.preview.redraw());
	this.selectionBorder.bounds = this.bounds;
	this.selectionBorder.redraw()
};
VertexHandler.prototype.destroy = function() {
	null != this.escapeHandler && (this.state.view.graph.removeListener(this.escapeHandler), this.escapeHandler = null);
	null != this.preview && (this.preview.destroy(), this.preview = null);
	this.selectionBorder.destroy();
	this.labelShape = this.selectionBorder = null;
	this.removeHint();
	if (null != this.sizers) {
		for (var a = 0; a < this.sizers.length; a++) this.sizers[a].destroy(), this.sizers[a] = null;
		this.sizers = null
	}
};

function EdgeHandler(a) {
	null != a && (this.state = a, this.init(), this.escapeHandler = Utils.bind(this, function(a, c) {
		this.reset()
	}), this.state.view.graph.addListener(Event.ESCAPE, this.escapeHandler))
}
EdgeHandler.prototype.graph = null;
EdgeHandler.prototype.state = null;
EdgeHandler.prototype.marker = null;
EdgeHandler.prototype.constraintHandler = null;
EdgeHandler.prototype.error = null;
EdgeHandler.prototype.shape = null;
EdgeHandler.prototype.bends = null;
EdgeHandler.prototype.labelShape = null;
EdgeHandler.prototype.cloneEnabled = !0;
EdgeHandler.prototype.addEnabled = !1;
EdgeHandler.prototype.removeEnabled = !1;
EdgeHandler.prototype.preferHtml = !1;
EdgeHandler.prototype.allowHandleBoundsCheck = !0;
EdgeHandler.prototype.snapToTerminals = !1;
EdgeHandler.prototype.handleImage = null;
EdgeHandler.prototype.tolerance = 0;
EdgeHandler.prototype.outlineConnect = !1;
VertexHandler.prototype.manageLabelHandle = !1;
EdgeHandler.prototype.init = function() {
	this.graph = this.state.view.graph;
	this.marker = this.createMarker();
	this.constraintHandler = new ConstraintHandler(this.graph);
	this.points = [];
	this.abspoints = this.getSelectionPoints(this.state);
	this.shape = this.createSelectionShape(this.abspoints);
	this.shape.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_MIXEDHTML : Constants.DIALECT_SVG;
	this.shape.init(this.graph.getView().getOverlayPane());
	this.shape.pointerEvents = !1;
	this.shape.node.style.cursor = Constants.CURSOR_MOVABLE_EDGE;
	Event.redirectMouseEvents(this.shape.node, this.graph, this.state);
	this.preferHtml = null != this.state.text && this.state.text.node.parentNode == this.graph.container;
	if (!this.preferHtml) {
		var a = this.state.getVisibleTerminalState(!0);
		null != a && (this.preferHtml = null != a.text && a.text.node.parentNode == this.graph.container);
		this.preferHtml || (a = this.state.getVisibleTerminalState(!1), null != a && (this.preferHtml = null != a.text && a.text.node.parentNode == this.graph.container))
	}
	if (this.graph.getSelectionCount() < GraphHandler.prototype.maxCells || 0 >= GraphHandler.prototype.maxCells) this.bends = this.createBends();
	this.label = new Point(this.state.absoluteOffset.x, this.state.absoluteOffset.y);
	this.labelShape = this.createLabelHandleShape();
	this.initBend(this.labelShape);
	this.labelShape.node.style.cursor = Constants.CURSOR_LABEL_HANDLE;
	Event.redirectMouseEvents(this.labelShape.node, this.graph, this.state);
	this.redraw()
};
EdgeHandler.prototype.isAddPointEvent = function(a) {
	return Event.isShiftDown(a)
};
EdgeHandler.prototype.isRemovePointEvent = function(a) {
	return Event.isShiftDown(a)
};
EdgeHandler.prototype.getSelectionPoints = function(a) {
	return a.absolutePoints
};
EdgeHandler.prototype.createSelectionShape = function(a) {
	a = new this.state.shape.constructor;
	a.outline = !0;
	a.apply(this.state);
	a.isDashed = this.isSelectionDashed();
	a.stroke = this.getSelectionColor();
	a.isShadow = !1;
	return a
};
EdgeHandler.prototype.getSelectionColor = function() {
	return Constants.EDGE_SELECTION_COLOR
};
EdgeHandler.prototype.getSelectionStrokeWidth = function() {
	return Constants.EDGE_SELECTION_STROKEWIDTH
};
EdgeHandler.prototype.isSelectionDashed = function() {
	return Constants.EDGE_SELECTION_DASHED
};
EdgeHandler.prototype.isConnectableCell = function(a) {
	return !0
};
EdgeHandler.prototype.getCellAt = function(a, b) {
	return !this.outlineConnect ? this.graph.getCellAt(a, b) : null
};
EdgeHandler.prototype.createMarker = function() {
	var a = new CellMarker(this.graph),
		b = this;
	a.getCell = function(a) {
		var d = CellMarker.prototype.getCell.apply(this, arguments),
			e = b.getPointForEvent(a);
		if (d == b.state.cell || null == d) d = b.getCellAt(e.x, e.y), b.state.cell == d && (d = null);
		var f = b.graph.getModel();
		if (this.graph.isSwimlane(d) && this.graph.hitsSwimlaneContent(d, e.x, e.y) || !b.isConnectableCell(d) || d == b.state.cell || null != d && !b.graph.connectableEdges && f.isEdge(d) || f.isAncestor(b.state.cell, d)) d = null;
		return d
	};
	a.isValidState = function(a) {
		var d = b.graph.getModel(),
			d = b.graph.view.getTerminalPort(a, b.graph.view.getState(d.getTerminal(b.state.cell, !b.isSource)), !b.isSource),
			d = null != d ? d.cell : null;
		b.error = b.validateConnection(b.isSource ? a.cell : d, b.isSource ? d : a.cell);
		return null == b.error
	};
	return a
};
EdgeHandler.prototype.validateConnection = function(a, b) {
	return this.graph.getEdgeValidationError(this.state.cell, a, b)
};
EdgeHandler.prototype.createBends = function() {
	for (var a = this.state.cell, b = [], c = 0; c < this.abspoints.length; c++) if (this.isHandleVisible(c)) {
		var d = c == this.abspoints.length - 1;
		if ((d = 0 == c || d) || this.graph.isCellBendable(a)) {
			var e = this.createHandleShape(c);
			this.initBend(e);
			this.isHandleEnabled(c) && (e.node.style.cursor = Constants.CURSOR_BEND_HANDLE, Event.redirectMouseEvents(e.node, this.graph, this.state), (Client.IS_QUIRKS || 8 == document.documentMode) && Event.addListener(e.node, "dragstart", function(a) {
				Event.consume(a);
				return !1
			}));
			b.push(e);
			d || (this.points.push(new Point(0, 0)), e.node.style.visibility = "hidden")
		}
	}
	return b
};
EdgeHandler.prototype.isHandleEnabled = function(a) {
	return !0
};
EdgeHandler.prototype.isHandleVisible = function(a) {
	var b = this.state.getVisibleTerminalState(!0),
		c = this.state.getVisibleTerminalState(!1),
		d = this.graph.getCellGeometry(this.state.cell);
	return (null != d ? this.graph.view.getEdgeStyle(this.state, d.points, b, c) : null) != EdgeStyle.EntityRelation || 0 == a || a == this.abspoints.length - 1
};
EdgeHandler.prototype.createHandleShape = function(a) {
	if (null != this.handleImage) return a = new ImageShape(new Rectangle(0, 0, this.handleImage.width, this.handleImage.height), this.handleImage.src), a.preserveImageAspect = !1, a;
	a = Constants.HANDLE_SIZE;
	this.preferHtml && (a -= 1);
	return new RectangleShape(new Rectangle(0, 0, a, a), Constants.HANDLE_FILLCOLOR, Constants.HANDLE_STROKECOLOR)
};
EdgeHandler.prototype.createLabelHandleShape = function() {
	if (null != this.labelHandleImage) {
		var a = new ImageShape(new Rectangle(0, 0, this.labelHandleImage.width, this.handleImage.height), this.labelHandleImage.src);
		a.preserveImageAspect = !1;
		return a
	}
	a = Constants.LABEL_HANDLE_SIZE;
	return new RectangleShape(new Rectangle(0, 0, a, a), Constants.LABEL_HANDLE_FILLCOLOR, Constants.HANDLE_STROKECOLOR)
};
EdgeHandler.prototype.initBend = function(a) {
	this.preferHtml ? (a.dialect = Constants.DIALECT_STRICTHTML, a.init(this.graph.container)) : (a.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_MIXEDHTML : Constants.DIALECT_SVG, a.init(this.graph.getView().getOverlayPane()))
};
EdgeHandler.prototype.getHandleForEvent = function(a) {
	function b(b) {
		if (null != b && "none" != b.node.style.display && "hidden" != b.node.style.visibility && (a.isSource(b) || null != d && Utils.intersects(b.bounds, d))) {
			var c = a.getGraphX() - b.bounds.getCenterX();
			b = a.getGraphY() - b.bounds.getCenterY();
			c = c * c + b * b;
			if (null == e || c <= e) return e = c, !0
		}
		return !1
	}
	var c = !Event.isMouseEvent(a.getEvent()) ? this.tolerance : 1,
		d = this.allowHandleBoundsCheck && (Client.IS_IE || 0 < c) ? new Rectangle(a.getGraphX() - c, a.getGraphY() - c, 2 * c, 2 * c) : null,
		e = null;
	if (a.isSource(this.state.text) || b(this.labelShape)) return Event.LABEL_HANDLE;
	if (null != this.bends) for (c = 0; c < this.bends.length; c++) if (b(this.bends[c])) return c;
	return null
};
EdgeHandler.prototype.mouseDown = function(a, b) {
	var c = null,
		c = this.getHandleForEvent(b);
	if (null != this.bends && null != this.bends[c]) {
		var d = this.bends[c].bounds;
		this.snapPoint = new Point(d.getCenterX(), d.getCenterY())
	}
	this.addEnabled && null == c && this.isAddPointEvent(b.getEvent()) ? this.addPoint(this.state, b.getEvent()) : null != c && (!b.isConsumed() && this.graph.isEnabled()) && (this.removeEnabled && this.isRemovePointEvent(b.getEvent()) ? this.removePoint(this.state, c) : (c != Event.LABEL_HANDLE || this.graph.isLabelMovable(b.getCell())) && this.start(b.getX(), b.getY(), c), b.consume())
};
EdgeHandler.prototype.start = function(a, b, c) {
	this.startX = a;
	this.startY = b;
	this.isSource = null == this.bends ? !1 : 0 == c;
	this.isTarget = null == this.bends ? !1 : c == this.bends.length - 1;
	this.isLabel = c == Event.LABEL_HANDLE;
	if (this.isSource || this.isTarget) {
		if (a = this.state.cell, b = this.graph.model.getTerminal(a, this.isSource), null == b && this.graph.isTerminalPointMovable(a, this.isSource) || null != b && this.graph.isCellDisconnectable(a, b, this.isSource)) this.index = c
	} else this.index = c
};
EdgeHandler.prototype.clonePreviewState = function(a, b) {
	return this.state.clone()
};
EdgeHandler.prototype.getSnapToTerminalTolerance = function() {
	return this.graph.gridSize * this.graph.view.scale / 2
};
EdgeHandler.prototype.updateHint = function(a, b) {};
EdgeHandler.prototype.removeHint = function() {};
EdgeHandler.prototype.roundLength = function(a) {
	return Math.round(a)
};
EdgeHandler.prototype.isSnapToTerminalsEvent = function(a) {
	return this.snapToTerminals && !Event.isAltDown(a.getEvent())
};
EdgeHandler.prototype.getPointForEvent = function(a) {
	var b = this.graph.getView(),
		c = b.scale,
		d = new Point(this.roundLength(a.getGraphX() / c) * c, this.roundLength(a.getGraphY() / c) * c),
		e = this.getSnapToTerminalTolerance(),
		f = !1,
		g = !1;
	if (0 < e && this.isSnapToTerminalsEvent(a)) {
		var k = function(a) {
				if (null != a) {
					var b = a.x;
					Math.abs(d.x - b) < e && (d.x = b, f = !0);
					a = a.y;
					Math.abs(d.y - a) < e && (d.y = a, g = !0)
				}
			},
			l = function(a) {
				null != a && k.call(this, new Point(b.getRoutingCenterX(a), b.getRoutingCenterY(a)))
			};
		l.call(this, this.state.getVisibleTerminalState(!0));
		l.call(this, this.state.getVisibleTerminalState(!1));
		if (null != this.state.absolutePoints) for (l = 0; l < this.state.absolutePoints.length; l++) k.call(this, this.state.absolutePoints[l])
	}
	this.graph.isGridEnabledEvent(a.getEvent()) && (a = b.translate, f || (d.x = (this.graph.snap(d.x / c - a.x) + a.x) * c), g || (d.y = (this.graph.snap(d.y / c - a.y) + a.y) * c));
	return d
};
EdgeHandler.prototype.getPreviewTerminalState = function(a) {
	this.constraintHandler.update(a, this.isSource);
	if (null != this.constraintHandler.currentFocus && null != this.constraintHandler.currentConstraint) return this.marker.reset(), this.constraintHandler.currentFocus;
	this.marker.process(a);
	return this.marker.getValidState()
};
EdgeHandler.prototype.getPreviewPoints = function(a) {
	var b = this.graph.getCellGeometry(this.state.cell),
		b = null != b.points ? b.points.slice() : null;
	a = new Point(a.x, a.y);
	!this.isSource && !this.isTarget ? (this.convertPoint(a, !1), null == b ? b = [a] : b[this.index - 1] = a) : this.graph.resetEdgesOnConnect && (b = null);
	return b
};
EdgeHandler.prototype.isOutlineConnectEvent = function(a) {
	return this.outlineConnect && (a.isSource(this.marker.highlight.shape) || Event.isAltDown(a.getEvent()))
};
EdgeHandler.prototype.updatePreviewState = function(a, b, c, d) {
	var e = this.isSource ? c : this.state.getVisibleTerminalState(!0),
		f = this.isTarget ? c : this.state.getVisibleTerminalState(!1),
		g = this.graph.getConnectionConstraint(a, e, !0),
		k = this.graph.getConnectionConstraint(a, f, !1),
		l = this.constraintHandler.currentConstraint;
	null == l && (null != c && this.isOutlineConnectEvent(d) ? (l = this.graph.getOutlineConstraint(b, c, d), this.constraintHandler.currentConstraint = l, this.constraintHandler.currentFocus = c, this.constraintHandler.currentPoint = b) : l = new ConnectionConstraint);
	this.outlineConnect && (null != this.marker.highlight && null != this.marker.highlight.shape) && (null != this.constraintHandler.currentConstraint && null != this.constraintHandler.currentFocus ? (this.marker.highlight.shape.stroke = Constants.OUTLINE_HIGHLIGHT_COLOR, this.marker.highlight.shape.strokewidth = Constants.OUTLINE_HIGHLIGHT_STROKEWIDTH / this.state.view.scale / this.state.view.scale, this.marker.highlight.repaint()) : this.marker.hasValidState() && (this.marker.highlight.shape.stroke = Constants.DEFAULT_VALID_COLOR, this.marker.highlight.shape.strokewidth = Constants.HIGHLIGHT_STROKEWIDTH / this.state.view.scale / this.state.view.scale, this.marker.highlight.repaint()));
	this.isSource ? g = l : this.isTarget && (k = l);
	(!this.isSource || null != e) && a.view.updateFixedTerminalPoint(a, e, !0, g);
	(!this.isTarget || null != f) && a.view.updateFixedTerminalPoint(a, f, !1, k);
	if ((this.isSource || this.isTarget) && null == c) a.setAbsoluteTerminalPoint(b, this.isSource), null == this.marker.getMarkedState() && (this.error = this.graph.allowDanglingEdges ? null : "");
	a.view.updatePoints(a, this.points, e, f);
	a.view.updateFloatingTerminalPoints(a, e, f)
};
EdgeHandler.prototype.mouseMove = function(a, b) {
	if (null != this.index && null != this.marker) {
		var c = this.getPointForEvent(b);
		Event.isShiftDown(b.getEvent()) && null != this.snapPoint && (Math.abs(this.snapPoint.x - c.x) < Math.abs(this.snapPoint.y - c.y) ? c.x = this.snapPoint.x : c.y = this.snapPoint.y);
		if (this.isLabel) this.label.x = c.x, this.label.y = c.y;
		else {
			this.points = this.getPreviewPoints(c);
			var d = this.isSource || this.isTarget ? this.getPreviewTerminalState(b) : null,
				e = this.clonePreviewState(c, null != d ? d.cell : null);
			this.updatePreviewState(e, c, d, b);
			this.setPreviewColor(null == this.error ? this.marker.validColor : this.marker.invalidColor);
			this.abspoints = e.absolutePoints;
			this.active = !0
		}
		this.drawPreview();
		this.updateHint(b, c);
		Event.consume(b.getEvent());
		b.consume()
	} else Client.IS_IE && null != this.getHandleForEvent(b) && b.consume(!1)
};
EdgeHandler.prototype.mouseUp = function(a, b) {
	if (null != this.index && null != this.marker) {
		var c = this.state.cell;
		if (b.getX() != this.startX || b.getY() != this.startY) if (null != this.error) 0 < this.error.length && this.graph.validationAlert(this.error);
		else if (this.isLabel) this.moveLabel(this.state, this.label.x, this.label.y);
		else if (this.isSource || this.isTarget) {
			var d = null;
			null != this.constraintHandler.currentConstraint && null != this.constraintHandler.currentFocus && (d = this.constraintHandler.currentFocus.cell);
			null == d && this.marker.hasValidState() && (d = this.marker.validState.cell);
			if (null != d) c = this.connect(c, d, this.isSource, this.graph.isCloneEvent(b.getEvent()) && this.cloneEnabled && this.graph.isCellsCloneable(), b);
			else if (this.graph.isAllowDanglingEdges()) {
				d = this.abspoints[this.isSource ? 0 : this.abspoints.length - 1];
				d.x = this.roundLength(d.x / this.graph.view.scale - this.graph.view.translate.x);
				d.y = this.roundLength(d.y / this.graph.view.scale - this.graph.view.translate.y);
				var e = this.graph.getView().getState(this.graph.getModel().getParent(c));
				null != e && (d.x -= e.origin.x, d.y -= e.origin.y);
				d.x -= this.graph.panDx / this.graph.view.scale;
				d.y -= this.graph.panDy / this.graph.view.scale;
				this.changeTerminalPoint(c, d, this.isSource)
			}
		} else this.active ? this.changePoints(c, this.points) : (this.graph.getView().invalidate(this.state.cell), this.graph.getView().revalidate(this.state.cell));
		null != this.marker && (this.reset(), c != this.state.cell && this.graph.setSelectionCell(c));
		b.consume()
	}
};
EdgeHandler.prototype.reset = function() {
	this.snapPoint = this.points = this.label = this.index = this.error = null;
	this.isTarget = this.isSource = this.isLabel = this.active = !1;
	this.marker.reset();
	this.constraintHandler.reset();
	this.setPreviewColor(Constants.EDGE_SELECTION_COLOR);
	this.removeHint();
	this.redraw()
};
EdgeHandler.prototype.setPreviewColor = function(a) {
	null != this.shape && (this.shape.stroke = a)
};
EdgeHandler.prototype.convertPoint = function(a, b) {
	var c = this.graph.getView().getScale(),
		d = this.graph.getView().getTranslate();
	b && (a.x = this.graph.snap(a.x), a.y = this.graph.snap(a.y));
	a.x = Math.round(a.x / c - d.x);
	a.y = Math.round(a.y / c - d.y);
	c = this.graph.getView().getState(this.graph.getModel().getParent(this.state.cell));
	null != c && (a.x -= c.origin.x, a.y -= c.origin.y);
	return a
};
EdgeHandler.prototype.moveLabel = function(a, b, c) {
	var d = this.graph.getModel(),
		e = d.getGeometry(a.cell);
	if (null != e) {
		var f = this.graph.getView().scale,
			e = e.clone();
		if (e.relative) {
			var g = this.graph.getView().getRelativePoint(a, b, c);
			e.x = g.x;
			e.y = g.y;
			e.offset = new Point(0, 0);
			g = this.graph.view.getPoint(a, e);
			e.offset = new Point((b - g.x) / f, (c - g.y) / f)
		} else {
			var k = a.absolutePoints,
				g = k[0],
				k = k[k.length - 1];
			null != g && null != k && (e.offset = new Point((b - (g.x + (k.x - g.x) / 2)) / f, (c - (g.y + (k.y - g.y) / 2)) / f), e.x = 0, e.y = 0)
		}
		d.setGeometry(a.cell, e)
	}
};
EdgeHandler.prototype.connect = function(a, b, c, d, e) {
	e = this.graph.getModel();
	var f = e.getParent(a);
	e.beginUpdate();
	try {
		if (d) {
			var g = a.clone();
			e.add(f, g, e.getChildCount(f));
			var k = e.getTerminal(a, !c);
			this.graph.connectCell(g, k, !c);
			a = g
		}
		var l = this.constraintHandler.currentConstraint;
		null == l && (l = new ConnectionConstraint);
		this.graph.connectCell(a, b, c, l)
	} finally {
		e.endUpdate()
	}
	return a
};
EdgeHandler.prototype.changeTerminalPoint = function(a, b, c) {
	var d = this.graph.getModel(),
		e = d.getGeometry(a);
	if (null != e) {
		d.beginUpdate();
		try {
			e = e.clone(), e.setTerminalPoint(b, c), d.setGeometry(a, e), this.graph.connectCell(a, null, c, new ConnectionConstraint)
		} finally {
			d.endUpdate()
		}
	}
};
EdgeHandler.prototype.changePoints = function(a, b) {
	var c = this.graph.getModel(),
		d = c.getGeometry(a);
	null != d && (d = d.clone(), d.points = b, c.setGeometry(a, d))
};
EdgeHandler.prototype.addPoint = function(a, b) {
	var c = Utils.convertPoint(this.graph.container, Event.getClientX(b), Event.getClientY(b)),
		d = this.graph.isGridEnabledEvent(b);
	this.convertPoint(c, d);
	this.addPointAt(a, c.x, c.y);
	Event.consume(b)
};
EdgeHandler.prototype.addPointAt = function(a, b, c) {
	var d = this.graph.getCellGeometry(a.cell);
	b = new Point(b, c);
	if (null != d) {
		d = d.clone();
		c = this.graph.view.translate;
		var e = this.graph.view.scale;
		c = Utils.findNearestSegment(a, (b.x + c.x) * e, (b.y + c.y) * e);
		null == d.points ? d.points = [b] : d.points.splice(c, 0, b);
		this.graph.getModel().setGeometry(a.cell, d);
		this.refresh();
		this.redraw()
	}
};
EdgeHandler.prototype.removePoint = function(a, b) {
	if (0 < b && b < this.abspoints.length - 1) {
		var c = this.graph.getCellGeometry(this.state.cell);
		null != c && null != c.points && (c = c.clone(), c.points.splice(b - 1, 1), this.graph.getModel().setGeometry(a.cell, c), this.refresh(), this.redraw())
	}
};
EdgeHandler.prototype.getHandleFillColor = function(a) {
	a = 0 == a;
	var b = this.state.cell,
		c = this.graph.getModel().getTerminal(b, a),
		d = Constants.HANDLE_FILLCOLOR;
	null != c && !this.graph.isCellDisconnectable(b, c, a) || null == c && !this.graph.isTerminalPointMovable(b, a) ? d = Constants.LOCKED_HANDLE_FILLCOLOR : null != c && this.graph.isCellDisconnectable(b, c, a) && (d = Constants.CONNECT_HANDLE_FILLCOLOR);
	return d
};
EdgeHandler.prototype.redraw = function() {
	this.abspoints = this.state.absolutePoints.slice();
	this.redrawHandles();
	var a = this.graph.getModel().getGeometry(this.state.cell).points;
	if (null != this.bends && 0 < this.bends.length && null != a) {
		null == this.points && (this.points = []);
		for (var b = 1; b < this.bends.length - 1; b++) null != this.bends[b] && null != this.abspoints[b] && (this.points[b - 1] = a[b - 1])
	}
	this.drawPreview()
};
EdgeHandler.prototype.redrawHandles = function() {
	var a = this.state.cell,
		b = this.labelShape.bounds;
	this.label = new Point(this.state.absoluteOffset.x, this.state.absoluteOffset.y);
	this.labelShape.bounds = new Rectangle(Math.round(this.label.x - b.width / 2), Math.round(this.label.y - b.height / 2), b.width, b.height);
	b = this.graph.getLabel(a);
	this.labelShape.visible = null != b && 0 < b.length && this.graph.isLabelMovable(a);
	if (null != this.bends && 0 < this.bends.length) {
		var c = this.abspoints.length - 1,
			a = this.abspoints[0],
			d = a.x,
			e = a.y,
			b = this.bends[0].bounds;
		this.bends[0].bounds = new Rectangle(Math.round(d - b.width / 2), Math.round(e - b.height / 2), b.width, b.height);
		this.bends[0].fill = this.getHandleFillColor(0);
		this.bends[0].redraw();
		var c = this.abspoints[c],
			d = c.x,
			e = c.y,
			f = this.bends.length - 1,
			b = this.bends[f].bounds;
		this.bends[f].bounds = new Rectangle(Math.round(d - b.width / 2), Math.round(e - b.height / 2), b.width, b.height);
		this.bends[f].fill = this.getHandleFillColor(f);
		this.bends[f].redraw();
		this.redrawInnerBends(a, c);
		this.labelShape.redraw()
	}
};
EdgeHandler.prototype.redrawInnerBends = function(a, b) {
	for (var c = 1; c < this.bends.length - 1; c++) if (null != this.bends[c]) if (null != this.abspoints[c]) {
		var d = this.abspoints[c].x,
			e = this.abspoints[c].y,
			f = this.bends[c].bounds;
		this.bends[c].node.style.visibility = "visible";
		this.bends[c].bounds = new Rectangle(Math.round(d - f.width / 2), Math.round(e - f.height / 2), f.width, f.height);
		this.manageLabelHandle ? this.checkLabelHandle(this.bends[c].bounds) : null == this.handleImage && (this.labelShape.visible && Utils.intersects(this.bends[c].bounds, this.labelShape.bounds)) && (w = Constants.HANDLE_SIZE + 3, h = Constants.HANDLE_SIZE + 3, this.bends[c].bounds = new Rectangle(Math.round(d - w / 2), Math.round(e - h / 2), w, h));
		this.bends[c].redraw()
	} else this.bends[c].destroy(), this.bends[c] = null
};
EdgeHandler.prototype.checkLabelHandle = function(a) {
	if (null != this.labelShape) {
		var b = this.labelShape.bounds;
		Utils.intersects(a, b) && (a.getCenterY() < b.getCenterY() ? b.y = a.y + a.height : b.y = a.y - b.height)
	}
};
EdgeHandler.prototype.drawPreview = function() {
	if (this.isLabel) {
		var a = this.labelShape.bounds,
			a = new Rectangle(Math.round(this.label.x - a.width / 2), Math.round(this.label.y - a.height / 2), a.width, a.height);
		this.labelShape.bounds = a;
		this.labelShape.redraw()
	} else this.shape.points = this.abspoints, this.shape.scale = this.state.view.scale, this.shape.strokewidth = this.getSelectionStrokeWidth() / this.shape.scale / this.shape.scale, this.shape.arrowStrokewidth = this.getSelectionStrokeWidth(), this.shape.redraw()
};
EdgeHandler.prototype.refresh = function() {
	this.abspoints = this.getSelectionPoints(this.state);
	this.shape.points = this.abspoints;
	this.points = [];
	null != this.bends && (this.destroyBends(), this.bends = this.createBends());
	null != this.labelShape && (null != this.labelShape.node && null != this.labelShape.node.parentNode) && this.labelShape.node.parentNode.appendChild(this.labelShape.node)
};
EdgeHandler.prototype.destroyBends = function() {
	if (null != this.bends) {
		for (var a = 0; a < this.bends.length; a++) null != this.bends[a] && this.bends[a].destroy();
		this.bends = null
	}
};
EdgeHandler.prototype.destroy = function() {
	null != this.escapeHandler && (this.state.view.graph.removeListener(this.escapeHandler), this.escapeHandler = null);
	null != this.marker && (this.marker.destroy(), this.marker = null);
	null != this.shape && (this.shape.destroy(), this.shape = null);
	null != this.labelShape && (this.labelShape.destroy(), this.labelShape = null);
	null != this.constraintHandler && (this.constraintHandler.destroy(), this.constraintHandler = null);
	this.destroyBends();
	this.removeHint()
};

function ElbowEdgeHandler(a) {
	EdgeHandler.call(this, a)
}
Utils.extend(ElbowEdgeHandler, EdgeHandler);
ElbowEdgeHandler.prototype = new EdgeHandler;
ElbowEdgeHandler.prototype.constructor = ElbowEdgeHandler;
ElbowEdgeHandler.prototype.flipEnabled = !0;
ElbowEdgeHandler.prototype.doubleClickOrientationResource = "none" != Client.language ? "doubleClickOrientation" : "";
ElbowEdgeHandler.prototype.createBends = function() {
	var a = [],
		b = this.createHandleShape(0);
	this.initBend(b);
	b.node.style.cursor = Constants.CURSOR_BEND_HANDLE;
	Event.redirectMouseEvents(b.node, this.graph, this.state);
	a.push(b);
	Client.IS_TOUCH && b.node.setAttribute("pointer-events", "none");
	a.push(this.createVirtualBend());
	this.points.push(new Point(0, 0));
	b = this.createHandleShape(2);
	this.initBend(b);
	b.node.style.cursor = Constants.CURSOR_BEND_HANDLE;
	Event.redirectMouseEvents(b.node, this.graph, this.state);
	a.push(b);
	Client.IS_TOUCH && b.node.setAttribute("pointer-events", "none");
	return a
};
ElbowEdgeHandler.prototype.createVirtualBend = function() {
	var a = this.createHandleShape();
	this.initBend(a);
	var b = this.getCursorForBend();
	a.node.style.cursor = b;
	b = Utils.bind(this, function(a) {
		!Event.isConsumed(a) && this.flipEnabled && (this.graph.flipEdge(this.state.cell, a), Event.consume(a))
	});
	Event.redirectMouseEvents(a.node, this.graph, this.state, null, null, null, b);
	this.graph.isCellBendable(this.state.cell) || (a.node.style.display = "none");
	return a
};
ElbowEdgeHandler.prototype.getCursorForBend = function() {
	return this.state.style[Constants.STYLE_EDGE] == EdgeStyle.TopToBottom || this.state.style[Constants.STYLE_EDGE] == Constants.EDGESTYLE_TOPTOBOTTOM || (this.state.style[Constants.STYLE_EDGE] == EdgeStyle.ElbowConnector || this.state.style[Constants.STYLE_EDGE] == Constants.EDGESTYLE_ELBOW) && this.state.style[Constants.STYLE_ELBOW] == Constants.ELBOW_VERTICAL ? "row-resize" : "col-resize"
};
ElbowEdgeHandler.prototype.getTooltipForNode = function(a) {
	var b = null;
	if (null != this.bends && null != this.bends[1] && (a == this.bends[1].node || a.parentNode == this.bends[1].node)) b = this.doubleClickOrientationResource, b = Resources.get(b) || b;
	return b
};
ElbowEdgeHandler.prototype.convertPoint = function(a, b) {
	var c = this.graph.getView().getScale(),
		d = this.graph.getView().getTranslate(),
		e = this.state.origin;
	b && (a.x = this.graph.snap(a.x), a.y = this.graph.snap(a.y));
	a.x = Math.round(a.x / c - d.x - e.x);
	a.y = Math.round(a.y / c - d.y - e.y)
};
ElbowEdgeHandler.prototype.redrawInnerBends = function(a, b) {
	var c = this.graph.getModel().getGeometry(this.state.cell),
		d = this.state.absolutePoints,
		e = null;
	1 < d.length ? (a = d[1], b = d[d.length - 2]) : null != c.points && 0 < c.points.length && (e = d[0]);
	e = null == e ? new Point(a.x + (b.x - a.x) / 2, a.y + (b.y - a.y) / 2) : new Point(this.graph.getView().scale * (e.x + this.graph.getView().translate.x + this.state.origin.x), this.graph.getView().scale * (e.y + this.graph.getView().translate.y + this.state.origin.y));
	d = this.bends[1].bounds;
	c = d.width;
	d = d.height;
	c = new Rectangle(Math.round(e.x - c / 2), Math.round(e.y - d / 2), c, d);
	this.manageLabelHandle ? this.checkLabelHandle(c) : null == this.handleImage && (this.labelShape.visible && Utils.intersects(c, this.labelShape.bounds)) && (c = Constants.HANDLE_SIZE + 3, d = Constants.HANDLE_SIZE + 3, c = new Rectangle(Math.round(e.x - c / 2), Math.round(e.y - d / 2), c, d));
	this.bends[1].bounds = c;
	this.bends[1].redraw();
	this.manageLabelHandle && this.checkLabelHandle(this.bends[1].bounds)
};

function EdgeSegmentHandler(a) {
	EdgeHandler.call(this, a)
}
Utils.extend(EdgeSegmentHandler, EdgeHandler);
EdgeSegmentHandler.prototype = new ElbowEdgeHandler;
EdgeSegmentHandler.prototype.constructor = EdgeSegmentHandler;
EdgeSegmentHandler.prototype.getPreviewPoints = function(a) {
	if (this.isSource || this.isTarget) return ElbowEdgeHandler.prototype.getPreviewPoints.apply(this, arguments);
	this.convertPoint(a, !1);
	var b = this.state.absolutePoints,
		c = b[0].clone();
	this.convertPoint(c, !1);
	for (var d = [], e = 1; e < b.length; e++) {
		var f = b[e].clone();
		this.convertPoint(f, !1);
		e == this.index && (c.x == f.x ? (c.x = a.x, f.x = a.x) : (c.y = a.y, f.y = a.y));
		e < b.length - 1 && d.push(f);
		c = f
	}
	if (1 == d.length) {
		if (c = this.state.view, e = this.state.getVisibleTerminalState(!0), f = this.state.getVisibleTerminalState(!1), null != f & null != e) {
			var g = this.state.origin.x,
				k = this.state.origin.y;
			Utils.contains(f, d[0].x + g, d[0].y + k) ? b[1].y == b[2].y ? d[0].y = c.getRoutingCenterY(e) - k : d[0].x = c.getRoutingCenterX(e) - g : Utils.contains(e, d[0].x + g, d[0].y + k) && (b[1].y == b[0].y ? d[0].y = c.getRoutingCenterY(f) - k : d[0].x = c.getRoutingCenterX(f) - g)
		}
	} else 0 == d.length && (d = [a]);
	return d
};
EdgeSegmentHandler.prototype.createBends = function() {
	var a = [],
		b = this.createHandleShape(0);
	this.initBend(b);
	b.node.style.cursor = Constants.CURSOR_BEND_HANDLE;
	Event.redirectMouseEvents(b.node, this.graph, this.state);
	a.push(b);
	Client.IS_TOUCH && b.node.setAttribute("pointer-events", "none");
	var c = this.state.absolutePoints;
	if (this.graph.isCellBendable(this.state.cell)) {
		null == this.points && (this.points = []);
		for (var d = 0; d < c.length - 1; d++) b = this.createVirtualBend(), a.push(b), b.node.style.cursor = 0 == c[d].x - c[d + 1].x ? "col-resize" : "row-resize", this.points.push(new Point(0, 0)), Client.IS_TOUCH && b.node.setAttribute("pointer-events", "none")
	}
	b = this.createHandleShape(c.length);
	this.initBend(b);
	b.node.style.cursor = Constants.CURSOR_BEND_HANDLE;
	Event.redirectMouseEvents(b.node, this.graph, this.state);
	a.push(b);
	Client.IS_TOUCH && b.node.setAttribute("pointer-events", "none");
	return a
};
EdgeSegmentHandler.prototype.redraw = function() {
	this.refresh();
	EdgeHandler.prototype.redraw.apply(this, arguments)
};
EdgeSegmentHandler.prototype.redrawInnerBends = function(a, b) {
	if (this.graph.isCellBendable(this.state.cell)) {
		var c = this.state.absolutePoints;
		if (null != c && 1 < c.length) for (var d = 0; d < this.state.absolutePoints.length - 1; d++) if (null != this.bends[d + 1]) {
			a = c[d];
			b = c[d + 1];
			var e = new Point(a.x + (b.x - a.x) / 2, a.y + (b.y - a.y) / 2),
				f = this.bends[d + 1].bounds;
			this.bends[d + 1].bounds = new Rectangle(Math.round(e.x - f.width / 2), Math.round(e.y - f.height / 2), f.width, f.height);
			this.bends[d + 1].redraw();
			this.manageLabelHandle && this.checkLabelHandle(this.bends[d + 1].bounds)
		}
	}
};
EdgeSegmentHandler.prototype.changePoints = function(a, b) {
	b = [];
	var c = this.abspoints;
	if (1 < c.length) for (var d = c[0], e = c[1], f = 2; f < c.length; f++) {
		var g = c[f];
		if ((Math.round(d.x) != Math.round(e.x) || Math.round(e.x) != Math.round(g.x)) && (Math.round(d.y) != Math.round(e.y) || Math.round(e.y) != Math.round(g.y))) d = e, e = e.clone(), this.convertPoint(e, !1), b.push(e);
		e = g
	}
	ElbowEdgeHandler.prototype.changePoints.apply(this, arguments)
};

function KeyHandler(a, b) {
	null != a && (this.graph = a, this.target = b || document.documentElement, this.normalKeys = [], this.shiftKeys = [], this.controlKeys = [], this.controlShiftKeys = [], this.keydownHandler = Utils.bind(this, function(a) {
		this.keyDown(a)
	}), Event.addListener(this.target, "keydown", this.keydownHandler), Client.IS_IE && Event.addListener(window, "unload", Utils.bind(this, function() {
		this.destroy()
	})))
}
KeyHandler.prototype.graph = null;
KeyHandler.prototype.target = null;
KeyHandler.prototype.normalKeys = null;
KeyHandler.prototype.shiftKeys = null;
KeyHandler.prototype.controlKeys = null;
KeyHandler.prototype.controlShiftKeys = null;
KeyHandler.prototype.enabled = !0;
KeyHandler.prototype.isEnabled = function() {
	return this.enabled
};
KeyHandler.prototype.setEnabled = function(a) {
	this.enabled = a
};
KeyHandler.prototype.bindKey = function(a, b) {
	this.normalKeys[a] = b
};
KeyHandler.prototype.bindShiftKey = function(a, b) {
	this.shiftKeys[a] = b
};
KeyHandler.prototype.bindControlKey = function(a, b) {
	this.controlKeys[a] = b
};
KeyHandler.prototype.bindControlShiftKey = function(a, b) {
	this.controlShiftKeys[a] = b
};
KeyHandler.prototype.isControlDown = function(a) {
	return Event.isControlDown(a)
};
KeyHandler.prototype.getFunction = function(a) {
	return null != a ? this.isControlDown(a) ? Event.isShiftDown(a) ? this.controlShiftKeys[a.keyCode] : this.controlKeys[a.keyCode] : Event.isShiftDown(a) ? this.shiftKeys[a.keyCode] : this.normalKeys[a.keyCode] : null
};
KeyHandler.prototype.isGraphEvent = function(a) {
	var b = Event.getSource(a);
	return b == this.target || b.parentNode == this.target || null != this.graph.cellEditor && this.graph.cellEditor.isEventSource(a) ? !0 : Utils.isAncestorNode(this.graph.container, b)
};
KeyHandler.prototype.keyDown = function(a) {
	if (this.graph.isEnabled() && !Event.isConsumed(a) && this.isGraphEvent(a) && this.isEnabled()) if (27 == a.keyCode) this.escape(a);
	else if (!this.graph.isEditing()) {
		var b = this.getFunction(a);
		null != b && (b(a), Event.consume(a))
	}
};
KeyHandler.prototype.escape = function(a) {
	this.graph.isEscapeEnabled() && this.graph.escape(a)
};
KeyHandler.prototype.destroy = function() {
	null != this.target && null != this.keydownHandler && (Event.removeListener(this.target, "keydown", this.keydownHandler), this.keydownHandler = null);
	this.target = null
};

function TooltipHandler(a, b) {
	null != a && (this.graph = a, this.delay = b || 500, this.graph.addMouseListener(this))
}
TooltipHandler.prototype.zIndex = 10005;
TooltipHandler.prototype.graph = null;
TooltipHandler.prototype.delay = null;
TooltipHandler.prototype.ignoreTouchEvents = !0;
TooltipHandler.prototype.hideOnHover = !1;
TooltipHandler.prototype.destroyed = !1;
TooltipHandler.prototype.enabled = !0;
TooltipHandler.prototype.isEnabled = function() {
	return this.enabled
};
TooltipHandler.prototype.setEnabled = function(a) {
	this.enabled = a
};
TooltipHandler.prototype.isHideOnHover = function() {
	return this.hideOnHover
};
TooltipHandler.prototype.setHideOnHover = function(a) {
	this.hideOnHover = a
};
TooltipHandler.prototype.init = function() {
	null != document.body && (this.div = document.createElement("div"), this.div.className = "Tooltip", this.div.style.visibility = "hidden", document.body.appendChild(this.div), Event.addGestureListeners(this.div, Utils.bind(this, function(a) {
		this.hideTooltip()
	})))
};
TooltipHandler.prototype.mouseDown = function(a, b) {
	this.reset(b, !1);
	this.hideTooltip()
};
TooltipHandler.prototype.mouseMove = function(a, b) {
	if (b.getX() != this.lastX || b.getY() != this.lastY) this.reset(b, !0), (this.isHideOnHover() || b.getState() != this.state || b.getSource() != this.node && (!this.stateSource || null != b.getState() && this.stateSource == (b.isSource(b.getState().shape) || !b.isSource(b.getState().text)))) && this.hideTooltip();
	this.lastX = b.getX();
	this.lastY = b.getY()
};
TooltipHandler.prototype.mouseUp = function(a, b) {
	this.reset(b, !0);
	this.hideTooltip()
};
TooltipHandler.prototype.resetTimer = function() {
	null != this.thread && (window.clearTimeout(this.thread), this.thread = null)
};
TooltipHandler.prototype.reset = function(a, b) {
	if (!this.ignoreTouchEvents || Event.isMouseEvent(a.getEvent())) if (this.resetTimer(), b && this.isEnabled() && null != a.getState() && (null == this.div || "hidden" == this.div.style.visibility)) {
		var c = a.getState(),
			d = a.getSource(),
			e = a.getX(),
			f = a.getY(),
			g = a.isSource(c.shape) || a.isSource(c.text);
		this.thread = window.setTimeout(Utils.bind(this, function() {
			if (!this.graph.isEditing() && !this.graph.popupMenuHandler.isMenuShowing() && !this.graph.isMouseDown) {
				var a = this.graph.getTooltip(c, d, e, f);
				this.show(a, e, f);
				this.state = c;
				this.node = d;
				this.stateSource = g
			}
		}), this.delay)
	}
};
TooltipHandler.prototype.hide = function() {
	this.resetTimer();
	this.hideTooltip()
};
TooltipHandler.prototype.hideTooltip = function() {
	null != this.div && (this.div.style.visibility = "hidden")
};
TooltipHandler.prototype.show = function(a, b, c) {
	if (!this.destroyed && null != a && 0 < a.length) {
		null == this.div && this.init();
		var d = Utils.getScrollOrigin();
		this.div.style.zIndex = this.zIndex;
		this.div.style.left = b + d.x + "px";
		this.div.style.top = c + Constants.TOOLTIP_VERTICAL_OFFSET + d.y + "px";
		Utils.isNode(a) ? (this.div.innerHTML = "", this.div.appendChild(a)) : this.div.innerHTML = a.replace(/\n/g, "<br>");
		this.div.style.visibility = "";
		Utils.fit(this.div)
	}
};
TooltipHandler.prototype.destroy = function() {
	this.destroyed || (this.graph.removeMouseListener(this), Event.release(this.div), null != this.div && null != this.div.parentNode && this.div.parentNode.removeChild(this.div), this.destroyed = !0, this.div = null)
};

function CellTracker(a, b, c) {
	CellMarker.call(this, a, b);
	this.graph.addMouseListener(this);
	null != c && (this.getCell = c);
	Client.IS_IE && Event.addListener(window, "unload", Utils.bind(this, function() {
		this.destroy()
	}))
}
Utils.extend(CellTracker, CellMarker);
CellTracker.prototype.mouseDown = function(a, b) {};
CellTracker.prototype.mouseMove = function(a, b) {
	this.isEnabled() && this.process(b)
};
CellTracker.prototype.mouseUp = function(a, b) {
	this.reset()
};
CellTracker.prototype.destroy = function() {
	this.destroyed || (this.destroyed = !0, this.graph.removeMouseListener(this), CellMarker.prototype.destroy.apply(this))
};

function CellHighlight(a, b, c, d) {
	null != a && (this.graph = a, this.highlightColor = null != b ? b : Constants.DEFAULT_VALID_COLOR, this.strokeWidth = null != c ? c : Constants.HIGHLIGHT_STROKEWIDTH, this.dashed = null != d ? d : !1, this.repaintHandler = Utils.bind(this, function() {
		if (null != this.state) {
			var a = this.graph.view.getState(this.state.cell);
			null == a ? this.hide() : (this.state = a, this.repaint())
		}
	}), this.graph.getView().addListener(Event.SCALE, this.repaintHandler), this.graph.getView().addListener(Event.TRANSLATE, this.repaintHandler), this.graph.getView().addListener(Event.SCALE_AND_TRANSLATE, this.repaintHandler), this.graph.getModel().addListener(Event.CHANGE, this.repaintHandler), this.resetHandler = Utils.bind(this, function() {
		this.hide()
	}), this.graph.getView().addListener(Event.DOWN, this.resetHandler), this.graph.getView().addListener(Event.UP, this.resetHandler))
}
CellHighlight.prototype.keepOnTop = !1;
CellHighlight.prototype.graph = !0;
CellHighlight.prototype.state = null;
CellHighlight.prototype.spacing = 2;
CellHighlight.prototype.resetHandler = null;
CellHighlight.prototype.setHighlightColor = function(a) {
	this.highlightColor = a;
	null != this.shape && (this.shape.stroke = a)
};
CellHighlight.prototype.drawHighlight = function() {
	this.shape = this.createShape();
	this.repaint();
	!this.keepOnTop && this.shape.node.parentNode.firstChild != this.shape.node && this.shape.node.parentNode.insertBefore(this.shape.node, this.shape.node.parentNode.firstChild)
};
CellHighlight.prototype.createShape = function() {
	var a = StencilRegistry.getStencil(this.state.style[Constants.STYLE_SHAPE]),
		b = null,
		b = null != a ? new Shape(a) : new this.state.shape.constructor;
	b.scale = this.state.view.scale;
	b.outline = !0;
	b.points = this.state.absolutePoints;
	b.apply(this.state);
	b.strokewidth = this.strokeWidth / this.state.view.scale / this.state.view.scale;
	b.arrowStrokewidth = this.strokeWidth;
	b.stroke = this.highlightColor;
	b.isDashed = this.dashed;
	b.isShadow = !1;
	b.dialect = this.graph.dialect != Constants.DIALECT_SVG ? Constants.DIALECT_VML : Constants.DIALECT_SVG;
	b.init(this.graph.getView().getOverlayPane());
	Event.redirectMouseEvents(b.node, this.graph, this.state);
	this.graph.dialect != Constants.DIALECT_SVG ? b.pointerEvents = !1 : b.svgPointerEvents = "stroke";
	return b
};
CellHighlight.prototype.repaint = function() {
	null != this.state && null != this.shape && (this.graph.model.isEdge(this.state.cell) ? this.shape.points = this.state.absolutePoints : (this.shape.bounds = new Rectangle(this.state.x - this.spacing, this.state.y - this.spacing, this.state.width + 2 * this.spacing, this.state.height + 2 * this.spacing), this.shape.rotation = Number(this.state.style[Constants.STYLE_ROTATION] || "0")), null != this.state.shape && this.shape.setCursor(this.state.shape.getCursor()), this.shape.redraw())
};
CellHighlight.prototype.hide = function() {
	this.highlight(null)
};
CellHighlight.prototype.highlight = function(a) {
	this.state != a && (null != this.shape && (this.shape.destroy(), this.shape = null), this.state = a, null != this.state && this.drawHighlight())
};
CellHighlight.prototype.destroy = function() {
	this.graph.getView().removeListener(this.resetHandler);
	this.graph.getView().removeListener(this.repaintHandler);
	this.graph.getModel().removeListener(this.repaintHandler);
	null != this.shape && (this.shape.destroy(), this.shape = null)
};

function DefaultKeyHandler(a) {
	if (null != a) {
		this.editor = a;
		this.handler = new KeyHandler(a.graph);
		var b = this.handler.escape;
		this.handler.escape = function(c) {
			b.apply(this, arguments);
			a.hideProperties();
			a.fireEvent(new EventObject(Event.ESCAPE, "event", c))
		}
	}
}
DefaultKeyHandler.prototype.editor = null;
DefaultKeyHandler.prototype.handler = null;
DefaultKeyHandler.prototype.bindAction = function(a, b, c) {
	var d = Utils.bind(this, function() {
		this.editor.execute(b)
	});
	c ? this.handler.bindControlKey(a, d) : this.handler.bindKey(a, d)
};
DefaultKeyHandler.prototype.destroy = function() {
	this.handler.destroy();
	this.handler = null
};

function DefaultPopupMenu(a) {
	this.config = a
}
DefaultPopupMenu.prototype.imageBasePath = null;
DefaultPopupMenu.prototype.config = null;
DefaultPopupMenu.prototype.createMenu = function(a, b, c, d) {
	if (null != this.config) {
		var e = this.createConditions(a, c, d);
		this.addItems(a, b, c, d, e, this.config.firstChild, null)
	}
};
DefaultPopupMenu.prototype.addItems = function(a, b, c, d, e, f, g) {
	for (var k = !1; null != f;) {
		if ("add" == f.nodeName) {
			var l = f.getAttribute("if");
			if (null == l || e[l]) {
				var l = f.getAttribute("as"),
					l = Resources.get(l) || l,
					m = Utils.eval(Utils.getTextContent(f)),
					n = f.getAttribute("action"),
					p = f.getAttribute("icon"),
					q = f.getAttribute("iconCls");
				k && (b.addSeparator(g), k = !1);
				null != p && this.imageBasePath && (p = this.imageBasePath + p);
				l = this.addAction(b, a, l, p, m, n, c, g, q);
				this.addItems(a, b, c, d, e, f.firstChild, l)
			}
		} else "separator" == f.nodeName && (k = !0);
		f = f.nextSibling
	}
};
DefaultPopupMenu.prototype.addAction = function(a, b, c, d, e, f, g, k, l) {
	return a.addItem(c, d, function(a) {
		"function" == typeof e && e.call(b, b, g, a);
		null != f && b.execute(f, g, a)
	}, k, l)
};
DefaultPopupMenu.prototype.createConditions = function(a, b, c) {
	var d = a.graph.getModel(),
		e = d.getChildCount(b),
		f = [];
	f.nocell = null == b;
	f.ncells = 1 < a.graph.getSelectionCount();
	f.notRoot = d.getRoot() != d.getParent(a.graph.getDefaultParent());
	f.cell = null != b;
	d = null != b && 1 == a.graph.getSelectionCount();
	f.nonEmpty = d && 0 < e;
	f.expandable = d && a.graph.isCellFoldable(b, !1);
	f.collapsable = d && a.graph.isCellFoldable(b, !0);
	f.validRoot = d && a.graph.isValidRoot(b);
	f.emptyValidRoot = f.validRoot && 0 == e;
	f.swimlane = d && a.graph.isSwimlane(b);
	e = this.config.getElementsByTagName("condition");
	for (d = 0; d < e.length; d++) {
		var g = Utils.eval(Utils.getTextContent(e[d])),
			k = e[d].getAttribute("name");
		null != k && "function" == typeof g && (f[k] = g(a, b, c))
	}
	return f
};

function DefaultToolbar(a, b) {
	this.editor = b;
	null != a && null != b && this.init(a)
}
DefaultToolbar.prototype.editor = null;
DefaultToolbar.prototype.toolbar = null;
DefaultToolbar.prototype.resetHandler = null;
DefaultToolbar.prototype.spacing = 4;
DefaultToolbar.prototype.connectOnDrop = !1;
DefaultToolbar.prototype.init = function(a) {
	null != a && (this.toolbar = new Toolbar(a), this.toolbar.addListener(Event.SELECT, Utils.bind(this, function(a, c) {
		var d = c.getProperty("function");
		this.editor.insertFunction = null != d ? Utils.bind(this, function() {
			d.apply(this, arguments);
			this.toolbar.resetMode()
		}) : null
	})), this.resetHandler = Utils.bind(this, function() {
		null != this.toolbar && this.toolbar.resetMode(!0)
	}), this.editor.graph.addListener(Event.DOUBLE_CLICK, this.resetHandler), this.editor.addListener(Event.ESCAPE, this.resetHandler))
};
DefaultToolbar.prototype.addItem = function(a, b, c, d) {
	var e = Utils.bind(this, function() {
		null != c && 0 < c.length && this.editor.execute(c)
	});
	return this.toolbar.addItem(a, b, e, d)
};
DefaultToolbar.prototype.addSeparator = function(a) {
	a = a || Client.imageBasePath + "/separator.gif";
	this.toolbar.addSeparator(a)
};
DefaultToolbar.prototype.addCombo = function() {
	return this.toolbar.addCombo()
};
DefaultToolbar.prototype.addActionCombo = function(a) {
	return this.toolbar.addActionCombo(a)
};
DefaultToolbar.prototype.addActionOption = function(a, b, c) {
	var d = Utils.bind(this, function() {
		this.editor.execute(c)
	});
	this.addOption(a, b, d)
};
DefaultToolbar.prototype.addOption = function(a, b, c) {
	return this.toolbar.addOption(a, b, c)
};
DefaultToolbar.prototype.addMode = function(a, b, c, d, e) {
	var f = Utils.bind(this, function() {
		this.editor.setMode(c);
		null != e && e(this.editor)
	});
	return this.toolbar.addSwitchMode(a, b, f, d)
};
DefaultToolbar.prototype.addPrototype = function(a, b, c, d, e, f) {
	var g = Utils.bind(this, function() {
		return "function" == typeof c ? c() : null != c ? this.editor.graph.cloneCells([c])[0] : null
	}),
		k = Utils.bind(this, function(a, b) {
			"function" == typeof e ? e(this.editor, g(), a, b) : this.drop(g(), a, b);
			this.toolbar.resetMode();
			Event.consume(a)
		});
	a = this.toolbar.addMode(a, b, k, d, null, f);
	this.installDropHandler(a, function(a, b, c) {
		k(b, c)
	});
	return a
};
DefaultToolbar.prototype.drop = function(a, b, c) {
	var d = this.editor.graph,
		e = d.getModel();
	if (null == c || e.isEdge(c) || !this.connectOnDrop || !d.isCellConnectable(c)) {
		for (; null != c && !d.isValidDropTarget(c, [a], b);) c = e.getParent(c);
		this.insert(a, b, c)
	} else this.connect(a, b, c)
};
DefaultToolbar.prototype.insert = function(a, b, c) {
	var d = this.editor.graph;
	if (d.canImportCell(a)) {
		var e = Event.getClientX(b),
			f = Event.getClientY(b),
			e = Utils.convertPoint(d.container, e, f);
		return d.isSplitEnabled() && d.isSplitTarget(c, [a], b) ? d.splitEdge(c, [a], null, e.x, e.y) : this.editor.addVertex(c, a, e.x, e.y)
	}
	return null
};
DefaultToolbar.prototype.connect = function(a, b, c) {
	b = this.editor.graph;
	var d = b.getModel();
	if (null != c && b.isCellConnectable(a) && b.isEdgeValid(null, c, a)) {
		var e = null;
		d.beginUpdate();
		try {
			var f = d.getGeometry(c),
				g = d.getGeometry(a).clone();
			g.x = f.x + (f.width - g.width) / 2;
			g.y = f.y + (f.height - g.height) / 2;
			var k = this.spacing * b.gridSize,
				l = 20 * d.getDirectedEdgeCount(c, !0);
			this.editor.horizontalFlow ? g.x += (g.width + f.width) / 2 + k + l : g.y += (g.height + f.height) / 2 + k + l;
			a.setGeometry(g);
			var m = d.getParent(c);
			b.addCell(a, m);
			b.constrainChild(a);
			e = this.editor.createEdge(c, a);
			if (null == d.getGeometry(e)) {
				var n = new Geometry;
				n.relative = !0;
				d.setGeometry(e, n)
			}
			b.addEdge(e, m, c, a)
		} finally {
			d.endUpdate()
		}
		b.setSelectionCells([a, e]);
		b.scrollCellToVisible(a)
	}
};
DefaultToolbar.prototype.installDropHandler = function(a, b) {
	var c = document.createElement("img");
	c.setAttribute("src", a.getAttribute("src"));
	var d = Utils.bind(this, function(e) {
		c.style.width = 2 * a.offsetWidth + "px";
		c.style.height = 2 * a.offsetHeight + "px";
		Utils.makeDraggable(a, this.editor.graph, b, c);
		Event.removeListener(c, "load", d)
	});
	Client.IS_IE ? d() : Event.addListener(c, "load", d)
};
DefaultToolbar.prototype.destroy = function() {
	null != this.resetHandler && (this.editor.graph.removeListener("dblclick", this.resetHandler), this.editor.removeListener("escape", this.resetHandler), this.resetHandler = null);
	null != this.toolbar && (this.toolbar.destroy(), this.toolbar = null)
};

function Editor(a) {
	this.actions = [];
	this.addActions();
	if (null != document.body) {
		this.cycleAttributeValues = [];
		this.popupHandler = new DefaultPopupMenu;
		this.undoManager = new UndoManager;
		this.graph = this.createGraph();
		this.toolbar = this.createToolbar();
		this.keyHandler = new DefaultKeyHandler(this);
		this.configure(a);
		this.graph.swimlaneIndicatorColorAttribute = this.cycleAttributeName;
		!Client.IS_LOCAL && null != this.urlInit && (this.session = this.createSession());
		if (null != this.onInit) this.onInit();
		Client.IS_IE && Event.addListener(window, "unload", Utils.bind(this, function() {
			this.destroy()
		}))
	}
}
LoadResources && Resources.add(Client.basePath + "/resources/grapheditor");
Editor.prototype = new EventSource;
Editor.prototype.constructor = Editor;
Editor.prototype.askZoomResource = "none" != Client.language ? "askZoom" : "";
Editor.prototype.lastSavedResource = "none" != Client.language ? "lastSaved" : "";
Editor.prototype.currentFileResource = "none" != Client.language ? "currentFile" : "";
Editor.prototype.propertiesResource = "none" != Client.language ? "properties" : "";
Editor.prototype.tasksResource = "none" != Client.language ? "tasks" : "";
Editor.prototype.helpResource = "none" != Client.language ? "help" : "";
Editor.prototype.outlineResource = "none" != Client.language ? "outline" : "";
Editor.prototype.outline = null;
Editor.prototype.graph = null;
Editor.prototype.graphRenderHint = null;
Editor.prototype.toolbar = null;
Editor.prototype.session = null;
Editor.prototype.status = null;
Editor.prototype.popupHandler = null;
Editor.prototype.undoManager = null;
Editor.prototype.keyHandler = null;
Editor.prototype.actions = null;
Editor.prototype.dblClickAction = "edit";
Editor.prototype.swimlaneRequired = !1;
Editor.prototype.disableContextMenu = !0;
Editor.prototype.insertFunction = null;
Editor.prototype.forcedInserting = !1;
Editor.prototype.templates = null;
Editor.prototype.defaultEdge = null;
Editor.prototype.defaultEdgeStyle = null;
Editor.prototype.defaultGroup = null;
Editor.prototype.groupBorderSize = null;
Editor.prototype.filename = null;
Editor.prototype.linefeed = "&#xa;";
Editor.prototype.postParameterName = "xml";
Editor.prototype.escapePostData = !0;
Editor.prototype.urlPost = null;
Editor.prototype.urlImage = null;
Editor.prototype.urlInit = null;
Editor.prototype.urlNotify = null;
Editor.prototype.urlPoll = null;
Editor.prototype.horizontalFlow = !1;
Editor.prototype.layoutDiagram = !1;
Editor.prototype.swimlaneSpacing = 0;
Editor.prototype.maintainSwimlanes = !1;
Editor.prototype.layoutSwimlanes = !1;
Editor.prototype.cycleAttributeValues = null;
Editor.prototype.cycleAttributeIndex = 0;
Editor.prototype.cycleAttributeName = "fillColor";
Editor.prototype.tasks = null;
Editor.prototype.tasksWindowImage = null;
Editor.prototype.tasksTop = 20;
Editor.prototype.help = null;
Editor.prototype.helpWindowImage = null;
Editor.prototype.urlHelp = null;
Editor.prototype.helpWidth = 300;
Editor.prototype.helpHeight = 260;
Editor.prototype.propertiesWidth = 240;
Editor.prototype.propertiesHeight = null;
Editor.prototype.movePropertiesDialog = !1;
Editor.prototype.validating = !1;
Editor.prototype.modified = !1;
Editor.prototype.isModified = function() {
	return this.modified
};
Editor.prototype.setModified = function(a) {
	this.modified = a
};
Editor.prototype.addActions = function() {
	this.addAction("save", function(a) {
		a.save()
	});
	this.addAction("show", function(a) {
		Utils.show(a.graph, null, 10, 10)
	});
	this.addAction("exportImage", function(a) {
		var b = a.getUrlImage();
		if (null == b || Client.IS_LOCAL) a.execute("show");
		else {
			var c = Utils.getViewXml(a.graph, 1),
				c = Utils.getXml(c, "\n");
			Utils.submit(b, a.postParameterName + "=" + encodeURIComponent(c), document, "_blank")
		}
	});
	this.addAction("refresh", function(a) {
		a.graph.refresh()
	});
	this.addAction("cut", function(a) {
		a.graph.isEnabled() && Clipboard.cut(a.graph)
	});
	this.addAction("copy", function(a) {
		a.graph.isEnabled() && Clipboard.copy(a.graph)
	});
	this.addAction("paste", function(a) {
		a.graph.isEnabled() && Clipboard.paste(a.graph)
	});
	this.addAction("delete", function(a) {
		a.graph.isEnabled() && a.graph.removeCells()
	});
	this.addAction("group", function(a) {
		a.graph.isEnabled() && a.graph.setSelectionCell(a.groupCells())
	});
	this.addAction("ungroup", function(a) {
		a.graph.isEnabled() && a.graph.setSelectionCells(a.graph.ungroupCells())
	});
	this.addAction("removeFromParent", function(a) {
		a.graph.isEnabled() && a.graph.removeCellsFromParent()
	});
	this.addAction("undo", function(a) {
		a.graph.isEnabled() && a.undo()
	});
	this.addAction("redo", function(a) {
		a.graph.isEnabled() && a.redo()
	});
	this.addAction("zoomIn", function(a) {
		a.graph.zoomIn()
	});
	this.addAction("zoomOut", function(a) {
		a.graph.zoomOut()
	});
	this.addAction("actualSize", function(a) {
		a.graph.zoomActual()
	});
	this.addAction("fit", function(a) {
		a.graph.fit()
	});
	this.addAction("showProperties", function(a, b) {
		a.showProperties(b)
	});
	this.addAction("selectAll", function(a) {
		a.graph.isEnabled() && a.graph.selectAll()
	});
	this.addAction("selectNone", function(a) {
		a.graph.isEnabled() && a.graph.clearSelection()
	});
	this.addAction("selectVertices", function(a) {
		a.graph.isEnabled() && a.graph.selectVertices()
	});
	this.addAction("selectEdges", function(a) {
		a.graph.isEnabled() && a.graph.selectEdges()
	});
	this.addAction("edit", function(a, b) {
		a.graph.isEnabled() && a.graph.isCellEditable(b) && a.graph.startEditingAtCell(b)
	});
	this.addAction("toBack", function(a, b) {
		a.graph.isEnabled() && a.graph.orderCells(!0)
	});
	this.addAction("toFront", function(a, b) {
		a.graph.isEnabled() && a.graph.orderCells(!1)
	});
	this.addAction("enterGroup", function(a, b) {
		a.graph.enterGroup(b)
	});
	this.addAction("exitGroup", function(a) {
		a.graph.exitGroup()
	});
	this.addAction("home", function(a) {
		a.graph.home()
	});
	this.addAction("selectPrevious", function(a) {
		a.graph.isEnabled() && a.graph.selectPreviousCell()
	});
	this.addAction("selectNext", function(a) {
		a.graph.isEnabled() && a.graph.selectNextCell()
	});
	this.addAction("selectParent", function(a) {
		a.graph.isEnabled() && a.graph.selectParentCell()
	});
	this.addAction("selectChild", function(a) {
		a.graph.isEnabled() && a.graph.selectChildCell()
	});
	this.addAction("collapse", function(a) {
		a.graph.isEnabled() && a.graph.foldCells(!0)
	});
	this.addAction("collapseAll", function(a) {
		if (a.graph.isEnabled()) {
			var b = a.graph.getChildVertices();
			a.graph.foldCells(!0, !1, b)
		}
	});
	this.addAction("expand", function(a) {
		a.graph.isEnabled() && a.graph.foldCells(!1)
	});
	this.addAction("expandAll", function(a) {
		if (a.graph.isEnabled()) {
			var b = a.graph.getChildVertices();
			a.graph.foldCells(!1, !1, b)
		}
	});
	this.addAction("bold", function(a) {
		a.graph.isEnabled() && a.graph.toggleCellStyleFlags(Constants.STYLE_FONTSTYLE, Constants.FONT_BOLD)
	});
	this.addAction("italic", function(a) {
		a.graph.isEnabled() && a.graph.toggleCellStyleFlags(Constants.STYLE_FONTSTYLE, Constants.FONT_ITALIC)
	});
	this.addAction("underline", function(a) {
		a.graph.isEnabled() && a.graph.toggleCellStyleFlags(Constants.STYLE_FONTSTYLE, Constants.FONT_UNDERLINE)
	});
	this.addAction("shadow", function(a) {
		a.graph.isEnabled() && a.graph.toggleCellStyleFlags(Constants.STYLE_FONTSTYLE, Constants.FONT_SHADOW)
	});
	this.addAction("alignCellsLeft", function(a) {
		a.graph.isEnabled() && a.graph.alignCells(Constants.ALIGN_LEFT)
	});
	this.addAction("alignCellsCenter", function(a) {
		a.graph.isEnabled() && a.graph.alignCells(Constants.ALIGN_CENTER)
	});
	this.addAction("alignCellsRight", function(a) {
		a.graph.isEnabled() && a.graph.alignCells(Constants.ALIGN_RIGHT)
	});
	this.addAction("alignCellsTop", function(a) {
		a.graph.isEnabled() && a.graph.alignCells(Constants.ALIGN_TOP)
	});
	this.addAction("alignCellsMiddle", function(a) {
		a.graph.isEnabled() && a.graph.alignCells(Constants.ALIGN_MIDDLE)
	});
	this.addAction("alignCellsBottom", function(a) {
		a.graph.isEnabled() && a.graph.alignCells(Constants.ALIGN_BOTTOM)
	});
	this.addAction("alignFontLeft", function(a) {
		a.graph.setCellStyles(Constants.STYLE_ALIGN, Constants.ALIGN_LEFT)
	});
	this.addAction("alignFontCenter", function(a) {
		a.graph.isEnabled() && a.graph.setCellStyles(Constants.STYLE_ALIGN, Constants.ALIGN_CENTER)
	});
	this.addAction("alignFontRight", function(a) {
		a.graph.isEnabled() && a.graph.setCellStyles(Constants.STYLE_ALIGN, Constants.ALIGN_RIGHT)
	});
	this.addAction("alignFontTop", function(a) {
		a.graph.isEnabled() && a.graph.setCellStyles(Constants.STYLE_VERTICAL_ALIGN, Constants.ALIGN_TOP)
	});
	this.addAction("alignFontMiddle", function(a) {
		a.graph.isEnabled() && a.graph.setCellStyles(Constants.STYLE_VERTICAL_ALIGN, Constants.ALIGN_MIDDLE)
	});
	this.addAction("alignFontBottom", function(a) {
		a.graph.isEnabled() && a.graph.setCellStyles(Constants.STYLE_VERTICAL_ALIGN, Constants.ALIGN_BOTTOM)
	});
	this.addAction("zoom", function(a) {
		var b = 100 * a.graph.getView().scale,
			b = parseFloat(Utils.prompt(Resources.get(a.askZoomResource) || a.askZoomResource, b)) / 100;
		isNaN(b) || a.graph.getView().setScale(b)
	});
	this.addAction("toggleTasks", function(a) {
		null != a.tasks ? a.tasks.setVisible(!a.tasks.isVisible()) : a.showTasks()
	});
	this.addAction("toggleHelp", function(a) {
		null != a.help ? a.help.setVisible(!a.help.isVisible()) : a.showHelp()
	});
	this.addAction("toggleOutline", function(a) {
		null == a.outline ? a.showOutline() : a.outline.setVisible(!a.outline.isVisible())
	});
	this.addAction("toggleConsole", function(a) {
		Log.setVisible(!Log.isVisible())
	})
};
Editor.prototype.createSession = function() {
	var a = Utils.bind(this, function(a) {
		this.fireEvent(new EventObject(Event.SESSION, "session", a))
	});
	return this.connect(this.urlInit, this.urlPoll, this.urlNotify, a)
};
Editor.prototype.configure = function(a) {
	null != a && ((new Codec(a.ownerDocument)).decode(a, this), this.resetHistory())
};
Editor.prototype.resetFirstTime = function() {
	document.cookie = "graph=seen; expires=Fri, 27 Jul 2001 02:47:11 UTC; path=/"
};
Editor.prototype.resetHistory = function() {
	this.lastSnapshot = (new Date).getTime();
	this.undoManager.clear();
	this.ignoredChanges = 0;
	this.setModified(!1)
};
Editor.prototype.addAction = function(a, b) {
	this.actions[a] = b
};
Editor.prototype.execute = function(a, b, c) {
	var d = this.actions[a];
	if (null != d) try {
		var e = arguments;
		e[0] = this;
		d.apply(this, e)
	} catch (f) {
		throw Utils.error("Cannot execute " + a + ": " + f.message, 280, !0), f;
	} else Utils.error("Cannot find action " + a, 280, !0)
};
Editor.prototype.addTemplate = function(a, b) {
	this.templates[a] = b
};
Editor.prototype.getTemplate = function(a) {
	return this.templates[a]
};
Editor.prototype.createGraph = function() {
	var a = new Graph(null, null, this.graphRenderHint);
	a.setTooltips(!0);
	a.setPanning(!0);
	this.installDblClickHandler(a);
	this.installUndoHandler(a);
	this.installDrillHandler(a);
	this.installChangeHandler(a);
	this.installInsertHandler(a);
	a.popupMenuHandler.factoryMethod = Utils.bind(this, function(a, c, d) {
		return this.createPopupMenu(a, c, d)
	});
	a.connectionHandler.factoryMethod = Utils.bind(this, function(a, c) {
		return this.createEdge(a, c)
	});
	this.createSwimlaneManager(a);
	this.createLayoutManager(a);
	return a
};
Editor.prototype.createSwimlaneManager = function(a) {
	a = new SwimlaneManager(a, !1);
	a.isHorizontal = Utils.bind(this, function() {
		return this.horizontalFlow
	});
	a.isEnabled = Utils.bind(this, function() {
		return this.maintainSwimlanes
	});
	return a
};
Editor.prototype.createLayoutManager = function(a) {
	var b = new LayoutManager(a),
		c = this;
	b.getLayout = function(b) {
		var e = null,
			f = c.graph.getModel();
		if (null != f.getParent(b)) if (c.layoutSwimlanes && a.isSwimlane(b)) null == c.swimlaneLayout && (c.swimlaneLayout = c.createSwimlaneLayout()), e = c.swimlaneLayout;
		else if (c.layoutDiagram && (a.isValidRoot(b) || null == f.getParent(f.getParent(b)))) null == c.diagramLayout && (c.diagramLayout = c.createDiagramLayout()), e = c.diagramLayout;
		return e
	};
	return b
};
Editor.prototype.setGraphContainer = function(a) {
	null == this.graph.container && (this.graph.init(a), this.rubberband = new Rubberband(this.graph), this.disableContextMenu && Event.disableContextMenu(a), Client.IS_QUIRKS && new DivResizer(a))
};
Editor.prototype.installDblClickHandler = function(a) {
	a.addListener(Event.DOUBLE_CLICK, Utils.bind(this, function(b, c) {
		var d = c.getProperty("cell");
		null != d && (a.isEnabled() && null != this.dblClickAction) && (this.execute(this.dblClickAction, d), c.consume())
	}))
};
Editor.prototype.installUndoHandler = function(a) {
	var b = Utils.bind(this, function(a, b) {
		var e = b.getProperty("edit");
		this.undoManager.undoableEditHappened(e)
	});
	a.getModel().addListener(Event.UNDO, b);
	a.getView().addListener(Event.UNDO, b);
	b = function(b, d) {
		var e = d.getProperty("edit").changes;
		a.setSelectionCells(a.getSelectionCellsForChanges(e))
	};
	this.undoManager.addListener(Event.UNDO, b);
	this.undoManager.addListener(Event.REDO, b)
};
Editor.prototype.installDrillHandler = function(a) {
	var b = Utils.bind(this, function(a) {
		this.fireEvent(new EventObject(Event.ROOT))
	});
	a.getView().addListener(Event.DOWN, b);
	a.getView().addListener(Event.UP, b)
};
Editor.prototype.installChangeHandler = function(a) {
	var b = Utils.bind(this, function(b, d) {
		this.setModified(!0);
		!0 == this.validating && a.validateGraph();
		for (var e = d.getProperty("edit").changes, f = 0; f < e.length; f++) {
			var g = e[f];
			if (g instanceof RootChange || g instanceof ValueChange && g.cell == this.graph.model.root || g instanceof CellAttributeChange && g.cell == this.graph.model.root) {
				this.fireEvent(new EventObject(Event.ROOT));
				break
			}
		}
	});
	a.getModel().addListener(Event.CHANGE, b)
};
Editor.prototype.installInsertHandler = function(a) {
	var b = this;
	a.addMouseListener({
		mouseDown: function(a, d) {
			if (null != b.insertFunction && !d.isPopupTrigger() && (b.forcedInserting || null == d.getState())) b.graph.clearSelection(), b.insertFunction(d.getEvent(), d.getCell()), this.isActive = !0, d.consume()
		},
		mouseMove: function(a, b) {
			this.isActive && b.consume()
		},
		mouseUp: function(a, b) {
			this.isActive && (this.isActive = !1, b.consume())
		}
	})
};
Editor.prototype.createDiagramLayout = function() {
	var a = this.graph.gridSize,
		b = new StackLayout(this.graph, !this.horizontalFlow, this.swimlaneSpacing, 2 * a, 2 * a);
	b.isVertexIgnored = function(a) {
		return !b.graph.isSwimlane(a)
	};
	return b
};
Editor.prototype.createSwimlaneLayout = function() {
	return new CompactTreeLayout(this.graph, this.horizontalFlow)
};
Editor.prototype.createToolbar = function() {
	return new DefaultToolbar(null, this)
};
Editor.prototype.setToolbarContainer = function(a) {
	this.toolbar.init(a);
	Client.IS_QUIRKS && new DivResizer(a)
};
Editor.prototype.setStatusContainer = function(a) {
	null == this.status && (this.status = a, this.addListener(Event.SAVE, Utils.bind(this, function() {
		var a = (new Date).toLocaleString();
		this.setStatus((Resources.get(this.lastSavedResource) || this.lastSavedResource) + ": " + a)
	})), this.addListener(Event.OPEN, Utils.bind(this, function() {
		this.setStatus((Resources.get(this.currentFileResource) || this.currentFileResource) + ": " + this.filename)
	})), Client.IS_QUIRKS && new DivResizer(a))
};
Editor.prototype.setStatus = function(a) {
	null != this.status && null != a && (this.status.innerHTML = a)
};
Editor.prototype.setTitleContainer = function(a) {
	this.addListener(Event.ROOT, Utils.bind(this, function(b) {
		a.innerHTML = this.getTitle()
	}));
	Client.IS_QUIRKS && new DivResizer(a)
};
Editor.prototype.treeLayout = function(a, b) {
	null != a && (new CompactTreeLayout(this.graph, b)).execute(a)
};
Editor.prototype.getTitle = function() {
	for (var a = "", b = this.graph, c = b.getCurrentRoot(); null != c && null != b.getModel().getParent(b.getModel().getParent(c));) b.isValidRoot(c) && (a = " > " + b.convertValueToString(c) + a), c = b.getModel().getParent(c);
	return this.getRootTitle() + a
};
Editor.prototype.getRootTitle = function() {
	var a = this.graph.getModel().getRoot();
	return this.graph.convertValueToString(a)
};
Editor.prototype.undo = function() {
	this.undoManager.undo()
};
Editor.prototype.redo = function() {
	this.undoManager.redo()
};
Editor.prototype.groupCells = function() {
	var a = null != this.groupBorderSize ? this.groupBorderSize : this.graph.gridSize;
	return this.graph.groupCells(this.createGroup(), a)
};
Editor.prototype.createGroup = function() {
	return this.graph.getModel().cloneCell(this.defaultGroup)
};
Editor.prototype.open = function(a) {
	if (null != a) {
		var b = Utils.load(a).getXml();
		this.readGraphModel(b.documentElement);
		this.filename = a;
		this.fireEvent(new EventObject(Event.OPEN, "filename", a))
	}
};
Editor.prototype.readGraphModel = function(a) {
	(new Codec(a.ownerDocument)).decode(a, this.graph.getModel());
	this.resetHistory()
};
Editor.prototype.save = function(a, b) {
	a = a || this.getUrlPost();
	if (null != a && 0 < a.length) {
		var c = this.writeGraphModel(b);
		this.postDiagram(a, c);
		this.setModified(!1)
	}
	this.fireEvent(new EventObject(Event.SAVE, "url", a))
};
Editor.prototype.postDiagram = function(a, b) {
	this.escapePostData && (b = encodeURIComponent(b));
	Utils.post(a, this.postParameterName + "=" + b, Utils.bind(this, function(c) {
		this.fireEvent(new EventObject(Event.POST, "request", c, "url", a, "data", b))
	}))
};
Editor.prototype.writeGraphModel = function(a) {
	a = null != a ? a : this.linefeed;
	var b = (new Codec).encode(this.graph.getModel());
	return Utils.getXml(b, a)
};
Editor.prototype.getUrlPost = function() {
	return this.urlPost
};
Editor.prototype.getUrlImage = function() {
	return this.urlImage
};
Editor.prototype.connect = function(a, b, c, d) {
	var e = null;
	Client.IS_LOCAL || (e = new Session(this.graph.getModel(), a, b, c), e.addListener(Event.RECEIVE, Utils.bind(this, function(a, b) {
		null != b.getProperty("node").getAttribute("namespace") && this.resetHistory()
	})), e.addListener(Event.DISCONNECT, d), e.addListener(Event.CONNECT, d), e.addListener(Event.NOTIFY, d), e.addListener(Event.GET, d), e.start());
	return e
};
Editor.prototype.swapStyles = function(a, b) {
	var c = this.graph.getStylesheet().styles[b];
	this.graph.getView().getStylesheet().putCellStyle(b, this.graph.getStylesheet().styles[a]);
	this.graph.getStylesheet().putCellStyle(a, c);
	this.graph.refresh()
};
Editor.prototype.showProperties = function(a) {
	a = a || this.graph.getSelectionCell();
	null == a && (a = this.graph.getCurrentRoot(), null == a && (a = this.graph.getModel().getRoot()));
	if (null != a) {
		this.graph.stopEditing(!0);
		var b = Utils.getOffset(this.graph.container),
			c = b.x + 10,
			b = b.y;
		if (null != this.properties && !this.movePropertiesDialog) c = this.properties.getX(), b = this.properties.getY();
		else {
			var d = this.graph.getCellBounds(a);
			null != d && (c += d.x + Math.min(200, d.width), b += d.y)
		}
		this.hideProperties();
		a = this.createProperties(a);
		null != a && (this.properties = new Window(Resources.get(this.propertiesResource) || this.propertiesResource, a, c, b, this.propertiesWidth, this.propertiesHeight, !1), this.properties.setVisible(!0))
	}
};
Editor.prototype.isPropertiesVisible = function() {
	return null != this.properties
};
Editor.prototype.createProperties = function(a) {
	var b = this.graph.getModel(),
		c = b.getValue(a);
	if (Utils.isNode(c)) {
		var d = new Form("properties");
		d.addText("ID", a.getId()).setAttribute("readonly", "true");
		var e = null,
			f = null,
			g = null,
			k = null,
			l = null;
		b.isVertex(a) && (e = b.getGeometry(a), null != e && (f = d.addText("top", e.y), g = d.addText("left", e.x), k = d.addText("width", e.width), l = d.addText("height", e.height)));
		for (var m = b.getStyle(a), n = d.addText("Style", m || ""), p = c.attributes, q = [], c = 0; c < p.length; c++) q[c] = d.addTextarea(p[c].nodeName, p[c].nodeValue, "label" == p[c].nodeName ? 4 : 2);
		c = Utils.bind(this, function() {
			this.hideProperties();
			b.beginUpdate();
			try {
				null != e && (e = e.clone(), e.x = parseFloat(g.value), e.y = parseFloat(f.value), e.width = parseFloat(k.value), e.height = parseFloat(l.value), b.setGeometry(a, e));
				0 < n.value.length ? b.setStyle(a, n.value) : b.setStyle(a, null);
				for (var c = 0; c < p.length; c++) {
					var d = new CellAttributeChange(a, p[c].nodeName, q[c].value);
					b.execute(d)
				}
				this.graph.isAutoSizeCell(a) && this.graph.updateCellSize(a)
			} finally {
				b.endUpdate()
			}
		});
		m = Utils.bind(this, function() {
			this.hideProperties()
		});
		d.addButtons(c, m);
		return d.table
	}
	return null
};
Editor.prototype.hideProperties = function() {
	null != this.properties && (this.properties.destroy(), this.properties = null)
};
Editor.prototype.showTasks = function() {
	if (null == this.tasks) {
		var a = document.createElement("div");
		a.style.padding = "4px";
		a.style.paddingLeft = "20px";
		var b = document.body.clientWidth,
			b = new Window(Resources.get(this.tasksResource) || this.tasksResource, a, b - 220, this.tasksTop, 200);
		b.setClosable(!0);
		b.destroyOnClose = !1;
		var c = Utils.bind(this, function(b) {
			Event.release(a);
			a.innerHTML = "";
			this.createTasks(a)
		});
		this.graph.getModel().addListener(Event.CHANGE, c);
		this.graph.getSelectionModel().addListener(Event.CHANGE, c);
		this.graph.addListener(Event.ROOT, c);
		null != this.tasksWindowImage && b.setImage(this.tasksWindowImage);
		this.tasks = b;
		this.createTasks(a)
	}
	this.tasks.setVisible(!0)
};
Editor.prototype.refreshTasks = function(a) {
	null != this.tasks && (a = this.tasks.content, Event.release(a), a.innerHTML = "", this.createTasks(a))
};
Editor.prototype.createTasks = function(a) {};
Editor.prototype.showHelp = function(a) {
	if (null == this.help) {
		var b = document.createElement("iframe");
		b.setAttribute("src", Resources.get("urlHelp") || this.urlHelp);
		b.setAttribute("height", "100%");
		b.setAttribute("width", "100%");
		b.setAttribute("frameBorder", "0");
		b.style.backgroundColor = "white";
		a = document.body.clientWidth;
		var c = document.body.clientHeight || document.documentElement.clientHeight,
			d = new Window(Resources.get(this.helpResource) || this.helpResource, b, (a - this.helpWidth) / 2, (c - this.helpHeight) / 3, this.helpWidth, this.helpHeight);
		d.setMaximizable(!0);
		d.setClosable(!0);
		d.destroyOnClose = !1;
		d.setResizable(!0);
		null != this.helpWindowImage && d.setImage(this.helpWindowImage);
		Client.IS_NS && (a = function(a) {
			b.setAttribute("height", d.div.offsetHeight - 26 + "px")
		}, d.addListener(Event.RESIZE_END, a), d.addListener(Event.MAXIMIZE, a), d.addListener(Event.NORMALIZE, a), d.addListener(Event.SHOW, a));
		this.help = d
	}
	this.help.setVisible(!0)
};
Editor.prototype.showOutline = function() {
	if (null == this.outline) {
		var a = document.createElement("div");
		a.style.overflow = "hidden";
		a.style.position = "relative";
		a.style.width = "100%";
		a.style.height = "100%";
		a.style.background = "white";
		a.style.cursor = "move";
		8 == document.documentMode && (a.style.filter = "progid:DXImageTransform.Microsoft.alpha(opacity=100)");
		var b = new Window(Resources.get(this.outlineResource) || this.outlineResource, a, 600, 480, 200, 200, !1),
			c = new Outline(this.graph, a);
		b.setClosable(!0);
		b.setResizable(!0);
		b.destroyOnClose = !1;
		b.addListener(Event.RESIZE_END, function() {
			c.update()
		});
		this.outline = b;
		this.outline.outline = c
	}
	this.outline.setVisible(!0);
	this.outline.outline.update(!0)
};
Editor.prototype.setMode = function(a) {
	"select" == a ? (this.graph.panningHandler.useLeftButtonForPanning = !1, this.graph.setConnectable(!1)) : "connect" == a ? (this.graph.panningHandler.useLeftButtonForPanning = !1, this.graph.setConnectable(!0)) : "pan" == a && (this.graph.panningHandler.useLeftButtonForPanning = !0, this.graph.setConnectable(!1))
};
Editor.prototype.createPopupMenu = function(a, b, c) {
	this.popupHandler.createMenu(this, a, b, c)
};
Editor.prototype.createEdge = function(a, b) {
	var c = null;
	if (null != this.defaultEdge) c = this.graph.getModel().cloneCell(this.defaultEdge);
	else {
		c = new Cell("");
		c.setEdge(!0);
		var d = new Geometry;
		d.relative = !0;
		c.setGeometry(d)
	}
	d = this.getEdgeStyle();
	null != d && c.setStyle(d);
	return c
};
Editor.prototype.getEdgeStyle = function() {
	return this.defaultEdgeStyle
};
Editor.prototype.consumeCycleAttribute = function(a) {
	return null != this.cycleAttributeValues && 0 < this.cycleAttributeValues.length && this.graph.isSwimlane(a) ? this.cycleAttributeValues[this.cycleAttributeIndex++ % this.cycleAttributeValues.length] : null
};
Editor.prototype.cycleAttribute = function(a) {
	if (null != this.cycleAttributeName) {
		var b = this.consumeCycleAttribute(a);
		null != b && a.setStyle(a.getStyle() + ";" + this.cycleAttributeName + "=" + b)
	}
};
Editor.prototype.addVertex = function(a, b, c, d) {
	for (var e = this.graph.getModel(); null != a && !this.graph.isValidDropTarget(a);) a = e.getParent(a);
	a = null != a ? a : this.graph.getSwimlaneAt(c, d);
	var f = this.graph.getView().scale,
		g = e.getGeometry(b),
		k = e.getGeometry(a);
	if (this.graph.isSwimlane(b) && !this.graph.swimlaneNesting) a = null;
	else {
		if (null == a && this.swimlaneRequired) return null;
		if (null != a && null != k) {
			var l = this.graph.getView().getState(a);
			if (null != l) {
				if (c -= l.origin.x * f, d -= l.origin.y * f, this.graph.isConstrainedMoving) {
					var k = g.width,
						m = g.height,
						n = l.x + l.width;
					c + k > n && (c -= c + k - n);
					n = l.y + l.height;
					d + m > n && (d -= d + m - n)
				}
			} else null != k && (c -= k.x * f, d -= k.y * f)
		}
	}
	g = g.clone();
	g.x = this.graph.snap(c / f - this.graph.getView().translate.x - this.graph.gridSize / 2);
	g.y = this.graph.snap(d / f - this.graph.getView().translate.y - this.graph.gridSize / 2);
	b.setGeometry(g);
	null == a && (a = this.graph.getDefaultParent());
	this.cycleAttribute(b);
	this.fireEvent(new EventObject(Event.BEFORE_ADD_VERTEX, "vertex", b, "parent", a));
	e.beginUpdate();
	try {
		b = this.graph.addCell(b, a), null != b && (this.graph.constrainChild(b), this.fireEvent(new EventObject(Event.ADD_VERTEX, "vertex", b)))
	} finally {
		e.endUpdate()
	}
	null != b && (this.graph.setSelectionCell(b), this.graph.scrollCellToVisible(b), this.fireEvent(new EventObject(Event.AFTER_ADD_VERTEX, "vertex", b)));
	return b
};
Editor.prototype.destroy = function() {
	this.destroyed || (this.destroyed = !0, null != this.tasks && this.tasks.destroy(), null != this.outline && this.outline.destroy(), null != this.properties && this.properties.destroy(), null != this.keyHandler && this.keyHandler.destroy(), null != this.rubberband && this.rubberband.destroy(), null != this.toolbar && this.toolbar.destroy(), null != this.graph && this.graph.destroy(), this.templates = this.status = null)
};
var CodecRegistry = {
	codecs: [],
	aliases: [],
	register: function(a) {
		if (null != a) {
			var b = a.getName();
			CodecRegistry.codecs[b] = a;
			var c = Utils.getFunctionName(a.template.constructor);
			c != b && CodecRegistry.addAlias(c, b)
		}
		return a
	},
	addAlias: function(a, b) {
		CodecRegistry.aliases[a] = b
	},
	getCodec: function(a) {
		var b = null;
		if (null != a) {
			var b = Utils.getFunctionName(a),
				c = CodecRegistry.aliases[b];
			null != c && (b = c);
			b = CodecRegistry.codecs[b];
			if (null == b) try {
				b = new ObjectCodec(new a), CodecRegistry.register(b)
			} catch (d) {}
		}
		return b
	}
};

function Codec(a) {
	this.document = a || Utils.createXmlDocument();
	this.objects = []
}
Codec.prototype.document = null;
Codec.prototype.objects = null;
Codec.prototype.encodeDefaults = !1;
Codec.prototype.putObject = function(a, b) {
	return this.objects[a] = b
};
Codec.prototype.getObject = function(a) {
	var b = null;
	null != a && (b = this.objects[a], null == b && (b = this.lookup(a), null == b && (a = this.getElementById(a), null != a && (b = this.decode(a)))));
	return b
};
Codec.prototype.lookup = function(a) {
	return null
};
Codec.prototype.getElementById = function(a, b) {
	return Utils.findNodeByAttribute(this.document.documentElement, null != b ? b : "id", a)
};
Codec.prototype.getId = function(a) {
	var b = null;
	null != a && (b = this.reference(a), null == b && a instanceof Cell && (b = a.getId(), null == b && (b = CellPath.create(a), 0 == b.length && (b = "root"))));
	return b
};
Codec.prototype.reference = function(a) {
	return null
};
Codec.prototype.encode = function(a) {
	var b = null;
	if (null != a && null != a.constructor) {
		var c = CodecRegistry.getCodec(a.constructor);
		null != c ? b = c.encode(this, a) : Utils.isNode(a) ? b = Utils.importNode(this.document, a, !0) : Log.warn("Codec.encode: No codec for " + Utils.getFunctionName(a.constructor))
	}
	return b
};
Codec.prototype.decode = function(a, b) {
	var c = null;
	if (null != a && a.nodeType == Constants.NODETYPE_ELEMENT) {
		c = null;
		try {
			c = window[a.nodeName]
		} catch (d) {}
		c = CodecRegistry.getCodec(c);
		null != c ? c = c.decode(this, a, b) : (c = a.cloneNode(!0), c.removeAttribute("as"))
	}
	return c
};
Codec.prototype.encodeCell = function(a, b, c) {
	b.appendChild(this.encode(a));
	if (null == c || c) {
		c = a.getChildCount();
		for (var d = 0; d < c; d++) this.encodeCell(a.getChildAt(d), b)
	}
};
Codec.prototype.isCellCodec = function(a) {
	return null != a && "function" == typeof a.isCellCodec ? a.isCellCodec() : !1
};
Codec.prototype.decodeCell = function(a, b) {
	b = null != b ? b : !0;
	var c = null;
	if (null != a && a.nodeType == Constants.NODETYPE_ELEMENT) {
		c = CodecRegistry.getCodec(a.nodeName);
		if (!this.isCellCodec(c)) for (var d = a.firstChild; null != d && !this.isCellCodec(c);) c = CodecRegistry.getCodec(d.nodeName), d = d.nextSibling;
		this.isCellCodec(c) || (c = CodecRegistry.getCodec(Cell));
		c = c.decode(this, a);
		b && this.insertIntoGraph(c)
	}
	return c
};
Codec.prototype.insertIntoGraph = function(a) {
	var b = a.parent,
		c = a.getTerminal(!0),
		d = a.getTerminal(!1);
	a.setTerminal(null, !1);
	a.setTerminal(null, !0);
	a.parent = null;
	null != b && b.insert(a);
	null != c && c.insertEdge(a, !0);
	null != d && d.insertEdge(a, !1)
};
Codec.prototype.setAttribute = function(a, b, c) {
	null != b && null != c && a.setAttribute(b, c)
};

function ObjectCodec(a, b, c, d) {
	this.template = a;
	this.exclude = null != b ? b : [];
	this.idrefs = null != c ? c : [];
	this.mapping = null != d ? d : [];
	this.reverse = {};
	for (var e in this.mapping) this.reverse[this.mapping[e]] = e
}
ObjectCodec.prototype.template = null;
ObjectCodec.prototype.exclude = null;
ObjectCodec.prototype.idrefs = null;
ObjectCodec.prototype.mapping = null;
ObjectCodec.prototype.reverse = null;
ObjectCodec.prototype.getName = function() {
	return Utils.getFunctionName(this.template.constructor)
};
ObjectCodec.prototype.cloneTemplate = function() {
	return new this.template.constructor
};
ObjectCodec.prototype.getFieldName = function(a) {
	if (null != a) {
		var b = this.reverse[a];
		null != b && (a = b)
	}
	return a
};
ObjectCodec.prototype.getAttributeName = function(a) {
	if (null != a) {
		var b = this.mapping[a];
		null != b && (a = b)
	}
	return a
};
ObjectCodec.prototype.isExcluded = function(a, b, c, d) {
	return b == ObjectIdentity.FIELD_NAME || 0 <= Utils.indexOf(this.exclude, b)
};
ObjectCodec.prototype.isReference = function(a, b, c, d) {
	return 0 <= Utils.indexOf(this.idrefs, b)
};
ObjectCodec.prototype.encode = function(a, b) {
	var c = a.document.createElement(this.getName());
	b = this.beforeEncode(a, b, c);
	this.encodeObject(a, b, c);
	return this.afterEncode(a, b, c)
};
ObjectCodec.prototype.encodeObject = function(a, b, c) {
	a.setAttribute(c, "id", a.getId(b));
	for (var d in b) {
		var e = d,
			f = b[e];
		null != f && !this.isExcluded(b, e, f, !0) && (Utils.isNumeric(e) && (e = null), this.encodeValue(a, b, e, f, c))
	}
};
ObjectCodec.prototype.encodeValue = function(a, b, c, d, e) {
	if (null != d) {
		if (this.isReference(b, c, d, !0)) {
			var f = a.getId(d);
			if (null == f) {
				Log.warn("ObjectCodec.encode: No ID for " + this.getName() + "." + c + "=" + d);
				return
			}
			d = f
		}
		f = this.template[c];
		if (null == c || a.encodeDefaults || f != d) c = this.getAttributeName(c), this.writeAttribute(a, b, c, d, e)
	}
};
ObjectCodec.prototype.writeAttribute = function(a, b, c, d, e) {
	"object" != typeof d ? this.writePrimitiveAttribute(a, b, c, d, e) : this.writeComplexAttribute(a, b, c, d, e)
};
ObjectCodec.prototype.writePrimitiveAttribute = function(a, b, c, d, e) {
	d = this.convertAttributeToXml(a, b, c, d, e);
	null == c ? (b = a.document.createElement("add"), "function" == typeof d ? b.appendChild(a.document.createTextNode(d)) : a.setAttribute(b, "value", d), e.appendChild(b)) : "function" != typeof d && a.setAttribute(e, c, d)
};
ObjectCodec.prototype.writeComplexAttribute = function(a, b, c, d, e) {
	a = a.encode(d);
	null != a ? (null != c && a.setAttribute("as", c), e.appendChild(a)) : Log.warn("ObjectCodec.encode: No node for " + this.getName() + "." + c + ": " + d)
};
ObjectCodec.prototype.convertAttributeToXml = function(a, b, c, d) {
	this.isBooleanAttribute(a, b, c, d) && (d = !0 == d ? "1" : "0");
	return d
};
ObjectCodec.prototype.isBooleanAttribute = function(a, b, c, d) {
	return "undefined" == typeof d.length && (!0 == d || !1 == d)
};
ObjectCodec.prototype.convertAttributeFroml = function(a, b, c) {
	var d = b.nodeValue;
	this.isNumericAttribute(a, b, c) && (d = parseFloat(d));
	return d
};
ObjectCodec.prototype.isNumericAttribute = function(a, b, c) {
	return Utils.isNumeric(b.nodeValue)
};
ObjectCodec.prototype.beforeEncode = function(a, b, c) {
	return b
};
ObjectCodec.prototype.afterEncode = function(a, b, c) {
	return c
};
ObjectCodec.prototype.decode = function(a, b, c) {
	var d = b.getAttribute("id"),
		e = a.objects[d];
	null == e && (e = c || this.cloneTemplate(), null != d && a.putObject(d, e));
	b = this.beforeDecode(a, b, e);
	this.decodeNode(a, b, e);
	return this.afterDecode(a, b, e)
};
ObjectCodec.prototype.decodeNode = function(a, b, c) {
	null != b && (this.decodeAttributes(a, b, c), this.decodeChildren(a, b, c))
};
ObjectCodec.prototype.decodeAttributes = function(a, b, c) {
	b = b.attributes;
	if (null != b) for (var d = 0; d < b.length; d++) this.decodeAttribute(a, b[d], c)
};
ObjectCodec.prototype.decodeAttribute = function(a, b, c) {
	var d = b.nodeName;
	if ("as" != d && "id" != d) {
		b = this.convertAttributeFroml(a, b, c);
		var e = this.getFieldName(d);
		if (this.isReference(c, e, b, !1)) {
			a = a.getObject(b);
			if (null == a) {
				Log.warn("ObjectCodec.decode: No object for " + this.getName() + "." + d + "=" + b);
				return
			}
			b = a
		}
		this.isExcluded(c, d, b, !1) || (c[d] = b)
	}
};
ObjectCodec.prototype.decodeChildren = function(a, b, c) {
	for (b = b.firstChild; null != b;) {
		var d = b.nextSibling;
		b.nodeType == Constants.NODETYPE_ELEMENT && !this.processInclude(a, b, c) && this.decodeChild(a, b, c);
		b = d
	}
};
ObjectCodec.prototype.decodeChild = function(a, b, c) {
	var d = this.getFieldName(b.getAttribute("as"));
	if (null == d || !this.isExcluded(c, d, b, !1)) {
		var e = this.getFieldTemplate(c, d, b),
			f = null;
		"add" == b.nodeName ? (f = b.getAttribute("value"), null == f && (f = Utils.eval(Utils.getTextContent(b)))) : f = a.decode(b, e);
		this.addObjectValue(c, d, f, e)
	}
};
ObjectCodec.prototype.getFieldTemplate = function(a, b, c) {
	a = a[b];
	a instanceof Array && 0 < a.length && (a = null);
	return a
};
ObjectCodec.prototype.addObjectValue = function(a, b, c, d) {
	null != c && c != d && (null != b && 0 < b.length ? a[b] = c : a.push(c))
};
ObjectCodec.prototype.processInclude = function(a, b, c) {
	if ("include" == b.nodeName) {
		b = b.getAttribute("name");
		if (null != b) try {
			var d = Utils.load(b).getDocumentElement();
			null != d && a.decode(d, c)
		} catch (e) {}
		return !0
	}
	return !1
};
ObjectCodec.prototype.beforeDecode = function(a, b, c) {
	return b
};
ObjectCodec.prototype.afterDecode = function(a, b, c) {
	return c
};
CodecRegistry.register(function() {
	var a = new ObjectCodec(new Cell, ["children", "edges", "overlays", "Transient"], ["parent", "source", "target"]);
	a.isCellCodec = function() {
		return !0
	};
	a.isExcluded = function(a, c, d, e) {
		return ObjectCodec.prototype.isExcluded.apply(this, arguments) || e && "value" == c && d.nodeType == Constants.NODETYPE_ELEMENT
	};
	a.afterEncode = function(a, c, d) {
		if (null != c.value && c.value.nodeType == Constants.NODETYPE_ELEMENT) {
			var e = d;
			d = Utils.importNode(a.document, c.value, !0);
			d.appendChild(e);
			a = e.getAttribute("id");
			d.setAttribute("id", a);
			e.removeAttribute("id")
		}
		return d
	};
	a.beforeDecode = function(a, c, d) {
		var e = c,
			f = this.getName();
		c.nodeName != f ? (e = c.getElementsByTagName(f)[0], null != e && e.parentNode == c ? (Utils.removeWhitespace(e, !0), Utils.removeWhitespace(e, !1), e.parentNode.removeChild(e)) : e = null, d.value = c.cloneNode(!0), c = d.value.getAttribute("id"), null != c && (d.setId(c), d.value.removeAttribute("id"))) : d.setId(c.getAttribute("id"));
		if (null != e) for (c = 0; c < this.idrefs.length; c++) {
			var f = this.idrefs[c],
				g = e.getAttribute(f);
			if (null != g) {
				e.removeAttribute(f);
				var k = a.objects[g] || a.lookup(g);
				null == k && (g = a.getElementById(g), null != g && (k = (CodecRegistry.codecs[g.nodeName] || this).decode(a, g)));
				d[f] = k
			}
		}
		return e
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new GraphModel);
	a.encodeObject = function(a, c, d) {
		var e = a.document.createElement("root");
		a.encodeCell(c.getRoot(), e);
		d.appendChild(e)
	};
	a.decodeChild = function(a, c, d) {
		"root" == c.nodeName ? this.decodeRoot(a, c, d) : ObjectCodec.prototype.decodeChild.apply(this, arguments)
	};
	a.decodeRoot = function(a, c, d) {
		var e = null;
		for (c = c.firstChild; null != c;) {
			var f = a.decodeCell(c);
			null != f && null == f.getParent() && (e = f);
			c = c.nextSibling
		}
		null != e && d.setRoot(e)
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new RootChange, ["model", "previous", "root"]);
	a.afterEncode = function(a, c, d) {
		a.encodeCell(c.root, d);
		return d
	};
	a.beforeDecode = function(a, c, d) {
		if (null != c.firstChild && c.firstChild.nodeType == Constants.NODETYPE_ELEMENT) {
			c = c.cloneNode(!0);
			var e = c.firstChild;
			d.root = a.decodeCell(e, !1);
			d = e.nextSibling;
			e.parentNode.removeChild(e);
			for (e = d; null != e;) d = e.nextSibling, a.decodeCell(e), e.parentNode.removeChild(e), e = d
		}
		return c
	};
	a.afterDecode = function(a, c, d) {
		d.previous = d.root;
		return d
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new ChildChange, ["model", "child", "previousIndex"], ["parent", "previous"]);
	a.isReference = function(a, c, d, e) {
		return "child" == c && (null != a.previous || !e) ? !0 : 0 <= Utils.indexOf(this.idrefs, c)
	};
	a.afterEncode = function(a, c, d) {
		this.isReference(c, "child", c.child, !0) ? d.setAttribute("child", a.getId(c.child)) : a.encodeCell(c.child, d);
		return d
	};
	a.beforeDecode = function(a, c, d) {
		if (null != c.firstChild && c.firstChild.nodeType == Constants.NODETYPE_ELEMENT) {
			c = c.cloneNode(!0);
			var e = c.firstChild;
			d.child = a.decodeCell(e, !1);
			d = e.nextSibling;
			e.parentNode.removeChild(e);
			for (e = d; null != e;) {
				d = e.nextSibling;
				if (e.nodeType == Constants.NODETYPE_ELEMENT) {
					var f = e.getAttribute("id");
					null == a.lookup(f) && a.decodeCell(e)
				}
				e.parentNode.removeChild(e);
				e = d
			}
		} else e = c.getAttribute("child"), d.child = a.getObject(e);
		return c
	};
	a.afterDecode = function(a, c, d) {
		d.child.parent = d.previous;
		d.previous = d.parent;
		d.previousIndex = d.index;
		return d
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new TerminalChange, ["model", "previous"], ["cell", "terminal"]);
	a.afterDecode = function(a, c, d) {
		d.previous = d.terminal;
		return d
	};
	return a
}());
var GenericChangeCodec = function(a, b) {
		var c = new ObjectCodec(a, ["model", "previous"], ["cell"]);
		c.afterDecode = function(a, c, f) {
			Utils.isNode(f.cell) && (f.cell = a.decodeCell(f.cell, !1));
			f.previous = f[b];
			return f
		};
		return c
	};
CodecRegistry.register(GenericChangeCodec(new ValueChange, "value"));
CodecRegistry.register(GenericChangeCodec(new StyleChange, "style"));
CodecRegistry.register(GenericChangeCodec(new GeometryChange, "geometry"));
CodecRegistry.register(GenericChangeCodec(new CollapseChange, "collapsed"));
CodecRegistry.register(GenericChangeCodec(new VisibleChange, "visible"));
CodecRegistry.register(GenericChangeCodec(new CellAttributeChange, "value"));
CodecRegistry.register(function() {
	return new ObjectCodec(new Graph, "graphListeners eventListeners view container cellRenderer editor selection".split(" "))
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new GraphView);
	a.encode = function(a, c) {
		return this.encodeCell(a, c, c.graph.getModel().getRoot())
	};
	a.encodeCell = function(a, c, d) {
		var e = c.graph.getModel(),
			f = c.getState(d),
			g = e.getParent(d);
		if (null == g || null != f) {
			var k = e.getChildCount(d),
				l = c.graph.getCellGeometry(d),
				m = null;
			g == e.getRoot() ? m = "layer" : null == g ? m = "graph" : e.isEdge(d) ? m = "edge" : 0 < k && null != l ? m = "group" : e.isVertex(d) && (m = "vertex");
			if (null != m) {
				var n = a.document.createElement(m);
				null != c.graph.getLabel(d) && (n.setAttribute("label", c.graph.getLabel(d)), c.graph.isHtmlLabel(d) && n.setAttribute("html", !0));
				if (null == g) {
					var p = c.getGraphBounds();
					null != p && (n.setAttribute("x", Math.round(p.x)), n.setAttribute("y", Math.round(p.y)), n.setAttribute("width", Math.round(p.width)), n.setAttribute("height", Math.round(p.height)));
					n.setAttribute("scale", c.scale)
				} else if (null != f && null != l) {
					for (p in f.style) g = f.style[p], "function" == typeof g && "object" == typeof g && (g = StyleRegistry.getName(g)), null != g && ("function" != typeof g && "object" != typeof g) && n.setAttribute(p, g);
					g = f.absolutePoints;
					if (null != g && 0 < g.length) {
						l = Math.round(g[0].x) + "," + Math.round(g[0].y);
						for (p = 1; p < g.length; p++) l += " " + Math.round(g[p].x) + "," + Math.round(g[p].y);
						n.setAttribute("points", l)
					} else n.setAttribute("x", Math.round(f.x)), n.setAttribute("y", Math.round(f.y)), n.setAttribute("width", Math.round(f.width)), n.setAttribute("height", Math.round(f.height));
					p = f.absoluteOffset;
					null != p && (0 != p.x && n.setAttribute("dx", Math.round(p.x)), 0 != p.y && n.setAttribute("dy", Math.round(p.y)))
				}
				for (p = 0; p < k; p++) f = this.encodeCell(a, c, e.getChildAt(d, p)), null != f && n.appendChild(f)
			}
		}
		return n
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new Stylesheet);
	a.encode = function(a, c) {
		var d = a.document.createElement(this.getName()),
			e;
		for (e in c.styles) {
			var f = c.styles[e],
				g = a.document.createElement("add");
			if (null != e) {
				g.setAttribute("as", e);
				for (var k in f) {
					var l = this.getStringValue(k, f[k]);
					if (null != l) {
						var m = a.document.createElement("add");
						m.setAttribute("value", l);
						m.setAttribute("as", k);
						g.appendChild(m)
					}
				}
				0 < g.childNodes.length && d.appendChild(g)
			}
		}
		return d
	};
	a.getStringValue = function(a, c) {
		var d = typeof c;
		"function" == d ? c = StyleRegistry.getName(style[j]) : "object" == d && (c = null);
		return c
	};
	a.decode = function(a, c, d) {
		d = d || new this.template.constructor;
		var e = c.getAttribute("id");
		null != e && (a.objects[e] = d);
		for (c = c.firstChild; null != c;) {
			if (!this.processInclude(a, c, d) && "add" == c.nodeName && (e = c.getAttribute("as"), null != e)) {
				var f = c.getAttribute("extend"),
					g = null != f ? Utils.clone(d.styles[f]) : null;
				null == g && (null != f && Log.warn("StylesheetCodec.decode: stylesheet " + f + " not found to extend"), g = {});
				for (f = c.firstChild; null != f;) {
					if (f.nodeType == Constants.NODETYPE_ELEMENT) {
						var k = f.getAttribute("as");
						if ("add" == f.nodeName) {
							var l = Utils.getTextContent(f),
								m = null;
							null != l && 0 < l.length ? m = Utils.eval(l) : (m = f.getAttribute("value"), Utils.isNumeric(m) && (m = parseFloat(m)));
							null != m && (g[k] = m)
						} else "remove" == f.nodeName && delete g[k]
					}
					f = f.nextSibling
				}
				d.putCellStyle(e, g)
			}
			c = c.nextSibling
		}
		return d
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new DefaultKeyHandler);
	a.encode = function(a, c) {
		return null
	};
	a.decode = function(a, c, d) {
		if (null != d) for (c = c.firstChild; null != c;) {
			if (!this.processInclude(a, c, d) && "add" == c.nodeName) {
				var e = c.getAttribute("as"),
					f = c.getAttribute("action"),
					g = c.getAttribute("control");
				d.bindAction(e, f, g)
			}
			c = c.nextSibling
		}
		return d
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new DefaultToolbar);
	a.encode = function(a, c) {
		return null
	};
	a.decode = function(a, c, d) {
		if (null != d) {
			var e = d.editor;
			for (c = c.firstChild; null != c;) {
				if (c.nodeType == Constants.NODETYPE_ELEMENT && !this.processInclude(a, c, d)) if ("separator" == c.nodeName) d.addSeparator();
				else if ("br" == c.nodeName) d.toolbar.addBreak();
				else if ("hr" == c.nodeName) d.toolbar.addLine();
				else if ("add" == c.nodeName) {
					var f = c.getAttribute("as"),
						f = Resources.get(f) || f,
						g = c.getAttribute("icon"),
						k = c.getAttribute("pressedIcon"),
						l = c.getAttribute("action"),
						m = c.getAttribute("mode"),
						n = c.getAttribute("template"),
						p = "0" != c.getAttribute("toggle"),
						q = Utils.getTextContent(c),
						r = null;
					if (null != l) r = d.addItem(f, g, l, k);
					else if (null != m) var s = Utils.eval(q),
						r = d.addMode(f, g, m, k, s);
					else if (null != n || null != q && 0 < q.length) r = e.templates[n], n = c.getAttribute("style"), null != r && null != n && (r = e.graph.cloneCells([r])[0], r.setStyle(n)), n = null, null != q && 0 < q.length && (n = Utils.eval(q)), r = d.addPrototype(f, g, r, k, n, p);
					else if (k = Utils.getChildNodes(c), 0 < k.length) if (null == g) {
						n = d.addActionCombo(f);
						for (f = 0; f < k.length; f++) p = k[f], "separator" == p.nodeName ? d.addOption(n, "---") : "add" == p.nodeName && (g = p.getAttribute("as"), p = p.getAttribute("action"), d.addActionOption(n, g, p))
					} else {
						var t = null,
							u = d.addPrototype(f, g, function() {
								var a = e.templates[t.value];
								if (null != a) {
									var a = a.clone(),
										b = t.options[t.selectedIndex].cellStyle;
									null != b && a.setStyle(b);
									return a
								}
								Log.warn("Template " + a + " not found");
								return null
							}, null, null, p),
							t = d.addCombo();
						Event.addListener(t, "change", function() {
							d.toolbar.selectMode(u, function(a) {
								a = Utils.convertPoint(e.graph.container, Event.getClientX(a), Event.getClientY(a));
								return e.addVertex(null, s(), a.x, a.y)
							});
							d.toolbar.noReset = !1
						});
						for (f = 0; f < k.length; f++) p = k[f], "separator" == p.nodeName ? d.addOption(t, "---") : "add" == p.nodeName && (g = p.getAttribute("as"), q = p.getAttribute("template"), d.addOption(t, g, q || n).cellStyle = p.getAttribute("style"))
					}
					null != r && (n = c.getAttribute("id"), null != n && 0 < n.length && r.setAttribute("id", n))
				}
				c = c.nextSibling
			}
		}
		return d
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new DefaultPopupMenu);
	a.encode = function(a, c) {
		return null
	};
	a.decode = function(a, c, d) {
		var e = c.getElementsByTagName("include")[0];
		null != e ? this.processInclude(a, e, d) : null != d && (d.config = c);
		return d
	};
	return a
}());
CodecRegistry.register(function() {
	var a = new ObjectCodec(new Editor, "modified lastSnapshot ignoredChanges undoManager graphContainer toolbarContainer".split(" "));
	a.afterDecode = function(a, c, d) {
		a = c.getAttribute("defaultEdge");
		null != a && (c.removeAttribute("defaultEdge"), d.defaultEdge = d.templates[a]);
		a = c.getAttribute("defaultGroup");
		null != a && (c.removeAttribute("defaultGroup"), d.defaultGroup = d.templates[a]);
		return d
	};
	a.decodeChild = function(a, c, d) {
		if ("Array" == c.nodeName) {
			if ("templates" == c.getAttribute("as")) {
				this.decodeTemplates(a, c, d);
				return
			}
		} else if ("ui" == c.nodeName) {
			this.decodeUi(a, c, d);
			return
		}
		ObjectCodec.prototype.decodeChild.apply(this, arguments)
	};
	a.decodeUi = function(a, c, d) {
		for (a = c.firstChild; null != a;) {
			if ("add" == a.nodeName) {
				c = a.getAttribute("as");
				var e = a.getAttribute("element"),
					f = a.getAttribute("style"),
					g = null;
				if (null != e) g = document.getElementById(e), null != g && null != f && (g.style.cssText += ";" + f);
				else {
					var e = parseInt(a.getAttribute("x")),
						k = parseInt(a.getAttribute("y")),
						l = a.getAttribute("width"),
						m = a.getAttribute("height"),
						g = document.createElement("div");
					g.style.cssText = f;
					(new Window(Resources.get(c) || c, g, e, k, l, m, !1, !0)).setVisible(!0)
				}
				"graph" == c ? d.setGraphContainer(g) : "toolbar" == c ? d.setToolbarContainer(g) : "title" == c ? d.setTitleContainer(g) : "status" == c ? d.setStatusContainer(g) : "map" == c && d.setMapContainer(g)
			} else "resource" == a.nodeName ? Resources.add(a.getAttribute("basename")) : "stylesheet" == a.nodeName && Client.link("stylesheet", a.getAttribute("name"));
			a = a.nextSibling
		}
	};
	a.decodeTemplates = function(a, c, d) {
		null == d.templates && (d.templates = []);
		c = Utils.getChildNodes(c);
		for (var e = 0; e < c.length; e++) {
			for (var f = c[e].getAttribute("as"), g = c[e].firstChild; null != g && 1 != g.nodeType;) g = g.nextSibling;
			null != g && (d.templates[f] = a.decodeCell(g))
		}
	};
	return a
}());