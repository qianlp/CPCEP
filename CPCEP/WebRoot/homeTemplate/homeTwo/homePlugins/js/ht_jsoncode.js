function wg_listToTree(l) {
	var q = l.list,
		n = l.inParams,
		a = l.outParams,
		s = l.sort;
	var t = [];
	var c = 1.1;
	var b = "wg huitoukest";
	var d = "20150824";
	var m = r(q);
	var k = {
		id: "id",
		pid: "pid",
		rootId: -1,
	};
	var j = {
		id: "id",
		pid: "pid",
		children: "children",
	};
	var e = {
		orderBy: ["pid", "id"],
		sort: "asc",
	};
	if (typeof n != "undefined" && n != null) {
		for (var i in n) {
			k[i] = n[i]
		}
	}
	if (typeof a != "undefined" && a != null) {
		for (var i in a) {
			j[i] = a[i]
		}
	}
	function f(u) {
		if (typeof u == "undefined") {
			return
		}
		for (var x = 0; x < u.length; x++) {
			if (typeof u[x][j.children] != "undefined") {
				u[x][j.children] = f(u[x][j.children])
			}
		}
		if (u.length < 2) {
			return u
		} else {
			for (var x = 0; x < u.length; x++) {
				for (var w = x + 1; w < u.length; w++) {
					var p = {};
					for (var v = 0; v < e.orderBy.length; v++) {
						var z = u[x][e.orderBy[v]];
						var y = u[w][e.orderBy[v]];
						if (e.sort == "asc") {
							if (y < z) {
								p = u[x];
								u[x] = u[w];
								u[w] = p;
								break
							} else {
								if (z == y) {
									continue
								} else {
									break
								}
							}
						} else {
							if (y > z) {
								p = u[x];
								u[x] = u[w];
								u[w] = p;
								break
							} else {
								if (z == y) {
									continue
								} else {
									break
								}
							}
						}
					}
				}
			}
			return u
		}
	}
	g();
	h();
	if (typeof s != "undefined" && s != null) {
		if (typeof s.orderBy != "undefined") {
			e.orderBy = s.orderBy
		}
		if (typeof s.sort != "undefined") {
			e.sort = s.sort
		}
		t = f(t)
	}
	function g() {
		for (var y = 0; y < m.length; y++) {
			var u = {};
			for (var x in m[y]) {
				var v = true;
				for (var z in j) {
					if (z == x) {
						u[j[z]] = m[y][x];
						v = false;
						break
					}
				}
				if (v) {
					u[x] = m[y][x]
				}
			}
			u[j.id] = m[y][k.id];
			u[j.pid] = m[y][k.pid];
			m[y] = u
		}
	}
	function h() {
		var u;
		while ((m.length) > 0) {
			u = m.length;
			for (var p = 0; p < m.length; p++) {
				o(t, m[p], p)
			}
			if (m.length >= u) {
				break
			}
		}
	}
	function o(v, u, x) {
		if (typeof v == "undefined") {
			v = [];
			return v
		}
		if (k.rootId == u[j.pid]) {
			v.push(u);
			m.splice(x, 1);
			return v
		} else {
			if (v.length > 0) {
				for (var y = 0; y < v.length; y++) {
					if (u.pid == v[y][j.id]) {
						if (typeof v[y][j.children] == "undefined") {
							v[y][j.children] = []
						}
						v[y][j.children].push(u);
						m.splice(x, 1);
						return v
					} else {
						if (typeof v[y][j.children] != "undefined") {
							var w = o(v[y][j.children], u, x);
							v[y][j.children] = w
						}
					}
				}
				return v
			} else {
				return []
			}
		}
	}
	function r(w) {
		var u;
		if (w instanceof Array) {
			u = [];
			var v = w.length;
			while (v-- > 0) {
				u[v] = r(w[v])
			}
			return u
		} else {
			if (w instanceof Object) {
				u = {};
				for (var p in w) {
					u[p] = r(w[p])
				}
				return u
			} else {
				return w
			}
		}
	}
	return t
};

var ht={
			decode:function(str){
				return eval("(" + str+ ")");
			},
			encode:function(obj){
				return JSON.stringify(obj);
			},
			arrayToTree:function(a,b,c,d,e){
				a=wg_listToTree({
				list:this.setIndex(a),
				inParams:{  
            				pid:d,  
           	 			rootId:e,  
            				id:c,  
            			},  
    				outParams:{  
            				children:b,  
           	 			pid:d,  
            				id:c  
        			},  
        			sort:{  
            				orderBy:["index"],  
           	 			sort:"asc",   
       			 } 
			})
				return a;
			},
			setIndex:function(o){
				$.each(o,function(i){
					this.index=i;
				})
				return o;
			}
		}

/** 
 * 时间对象的格式化; 
 */  
$.formatDate = function(date,format) {
   if(typeof date=="string"){
   	date= new Date(date.replace(/-/g,"/").replace("T"," "));
    } 
    var o = {  
        "M+" : date.getMonth() + 1, // month  
        "d+" : date.getDate(), // day  
        "h+" : date.getHours(), // hour  
        "m+" : date.getMinutes(), // minute  
        "s+" : date.getSeconds(), // second  
        "q+" : Math.floor((date.getMonth() + 3) / 3), // quarter  
        "S" : date.getMilliseconds()  
        // millisecond  
    }  
   
    if (/(y+)/.test(format)) {  
        format = format.replace(RegExp.$1, (date.getFullYear() + "").substr(4  
                        - RegExp.$1.length));  
    }  
   
    for (var k in o) {  
        if (new RegExp("(" + k + ")").test(format)) {  
            format = format.replace(RegExp.$1, RegExp.$1.length == 1  
                            ? o[k]  
                            : ("00" + o[k]).substr(("" + o[k]).length));  
        }  
    }  
    return format;  
}