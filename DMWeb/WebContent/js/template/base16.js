/* Base16(Hexadecimal) conversion methods.
 * Copyright (c) 2006 by Ali Farhadi.
 * released under the terms of the Gnu Public License.
 * see the GPL for details.
 *
 * Email: ali[at]farhadi[dot]ir
 * Website: http://farhadi.ir/
 */

//Encodes data to Base16(hex) format
function base16Encode(data){
	var b16_digits = '0123456789abcdef';
	var b16_map = new Array();
	for (var i=0; i<256; i++) {
		b16_map[i] = b16_digits.charAt(i >> 4) + b16_digits.charAt(i & 15);
	}
	
	var result = new Array();
	for (var i=0; i<data.length; i++) {
		result[i] = b16_map[data.charCodeAt(i)];
	}
	
	return result.join('');
}

//Decodes Base16(hex) formated data
function base16Decode(data){
	var b16_digits = '0123456789abcdef';
	var b16_map = new Array();
	for (var i=0; i<256; i++) {
		b16_map[b16_digits.charAt(i >> 4) + b16_digits.charAt(i & 15)] = String.fromCharCode(i);
	}
	data = data.replace(/[^a-f0-9]/ig, '');// strip none base16 characters
	
	if (data.length % 2) data = '0'+data;
		
	var result = new Array();
	var j=0;
	for (var i=0; i<data.length; i+=2) {
		result[j++] = b16_map[data.substr(i,2)];
	}

	return result.join('');
}