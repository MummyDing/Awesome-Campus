javascript: (function() {
	var objs = document.getElementsByTagName("img");
	var array = new Array();
	for (var j = 0; j < objs.length; j++) {
		array[j] = objs[j].attributes['src'].value;
	}
	for (var i = 0; i < objs.length; i++) {
		objs[i].pos = i;
		objs[i].onclick = function() {
			var pos = this.pos;
			window.imagelistener.openImage(array.join(","), pos);
		}
	}
})()