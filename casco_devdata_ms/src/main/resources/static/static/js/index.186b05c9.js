(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["index"],{"0bfb":function(t,e,a){"use strict";var i=a("cb7c");t.exports=function(){var t=i(this),e="";return t.global&&(e+="g"),t.ignoreCase&&(e+="i"),t.multiline&&(e+="m"),t.unicode&&(e+="u"),t.sticky&&(e+="y"),e}},"1af6":function(t,e,a){var i=a("63b6");i(i.S,"Array",{isArray:a("9003")})},"1eb0":function(t,e,a){},"20fd":function(t,e,a){"use strict";var i=a("d9f6"),r=a("aebd");t.exports=function(t,e,a){e in t?i.f(t,e,r(0,a)):t[e]=a}},"2f21":function(t,e,a){"use strict";var i=a("79e5");t.exports=function(t,e){return!!t&&i((function(){e?t.call(null,(function(){}),1):t.call(null)}))}},3702:function(t,e,a){var i=a("481b"),r=a("5168")("iterator"),n=Array.prototype;t.exports=function(t){return void 0!==t&&(i.Array===t||n[r]===t)}},"37f9":function(t,e,a){"use strict";a.r(e);var i=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"content"},[a("div",{staticClass:"left"},[a("div",{staticClass:"selectItem"},[a("el-form",{ref:"selectForm",attrs:{model:t.selectForm,"label-width":"80px"}},[a("el-form-item",{attrs:{label:"类型筛选"}},[a("el-cascader",{staticStyle:{width:"95%"},attrs:{options:t.devTypeList,props:{expandTrigger:"hover",value:"devTypeId",label:"devTypeName"},placeholder:"请选择设备类型",clearable:""},on:{change:t.getDev},model:{value:t.devType,callback:function(e){t.devType=e},expression:"devType"}})],1),t._v(" "),a("el-form-item",{attrs:{label:"设备筛选"}},[a("el-input",{staticStyle:{width:"95%"},on:{change:t.getDev},model:{value:t.devName,callback:function(e){t.devName=e},expression:"devName"}})],1)],1),t._v(" "),a("div",{staticClass:"devList"},[a("ul",t._l(t.devList,(function(e){return a("li",{key:e.devId,class:e.active?"active":"",on:{click:function(a){return t.chooseDev(e)}}},[t._v(t._s(e.devName))])})),0)])],1),t._v(" "),a("div",{staticClass:"attr"},[a("el-form",{ref:"selectForm",staticClass:"attr-form",attrs:{model:t.selectForm}},[a("el-form-item",{attrs:{label:"采集项:","label-width":"70px"}},[a("el-row",[a("el-checkbox",{on:{change:t.chooseVisibleAttr},model:{value:t.attrVisible,callback:function(e){t.attrVisible=e},expression:"attrVisible"}},[t._v("选中常用项")]),t._v(" "),a("el-button",{attrs:{size:"small"},on:{click:t.updateVisible}},[t._v("保存为常用项")])],1)],1)],1),t._v(" "),a("div",{staticClass:"attr-table"},[a("el-table",{ref:"attrList",staticStyle:{width:"100%",height:"100%",overflow:"scroll"},attrs:{data:t.attrList,"tooltip-effect":"dark"},on:{"select-all":t.selectAllAttr,"selection-change":t.selectAttr}},[a("el-table-column",{attrs:{type:"selection",width:"45"}}),t._v(" "),a("el-table-column",{attrs:{prop:"sort",label:"采集项ID",width:"60"}}),t._v(" "),a("el-table-column",{attrs:{prop:"RES_TYPE_NAME",label:"采集项"}}),t._v(" "),a("el-table-column",{attrs:{prop:"SRC_NAME",label:"来源","show-overflow-tooltip":""}}),t._v(" "),a("el-table-column",{attrs:{prop:"visible",label:"常用","show-overflow-tooltip":""},scopedSlots:t._u([{key:"default",fn:function(e){return[e.row.visible?a("span",[t._v("是")]):a("span",[t._v("否")])]}}])})],1)],1)],1)]),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:t.showTable,expression:"showTable"}],staticClass:"table-content"},[a("div",{directives:[{name:"show",rawName:"v-show",value:"VEHICLE"!==t.major,expression:"major!=='VEHICLE'"}],staticClass:"content-inner"},[a("el-table",{staticStyle:{width:"100%","margin-top":"20px"},attrs:{data:t.tableData}},t._l(t.headerData,(function(e,i){return a("el-table-column",{key:i,attrs:{label:e.RES_TYPE_NAME,prop:e.sort,align:"center",width:"150px"}},[e.SRC_NAME?[a("el-table-column",{attrs:{label:e.SRC_NAME,prop:e.sort,width:"150px"}})]:t._e()],2)})),1),t._v(" "),a("el-pagination",{staticStyle:{position:"absolute",bottom:"10px",right:"10px"},attrs:{background:"","current-page":t.pageInfo.currentPage,"page-size":t.pageInfo.pageSize,layout:"total, prev, pager, next, jumper",total:t.pageInfo.total},on:{"current-change":t.handleCurrentChange}})],1),t._v(" "),a("div",{directives:[{name:"show",rawName:"v-show",value:"VEHICLE"==t.major,expression:"major=='VEHICLE'"}],staticClass:"content-inner"},[a("el-table",{staticStyle:{width:"100%","margin-top":"20px"},attrs:{data:t.carTableData,column:t.carColumn}}),t._v(" "),a("el-pagination",{staticStyle:{position:"absolute",bottom:"10px",right:"10px"},attrs:{background:"","current-page":t.carPageInfo.currentPage,"page-size":t.carPageInfo.pageSize,layout:"total, prev, pager, next, jumper",total:t.carPageInfo.total},on:{"current-change":t.handleCarCurrentChange,"size-change":t.handleCarSizeChange}})],1)])])},r=[],n=a("85f2"),o=a.n(n);function s(t,e,a){return e in t?o()(t,e,{value:a,enumerable:!0,configurable:!0,writable:!0}):t[e]=a,t}a("456d");var c=a("a745"),l=a.n(c);function d(t,e){(null==e||e>t.length)&&(e=t.length);for(var a=0,i=new Array(e);a<e;a++)i[a]=t[a];return i}function u(t){if(l()(t))return d(t)}var f=a("774e"),v=a.n(f),h=a("c8bb"),p=a.n(h),b=a("67bb"),g=a.n(b);function y(t){if("undefined"!==typeof g.a&&p()(Object(t)))return v()(t)}function m(t,e){if(t){if("string"===typeof t)return d(t,e);var a=Object.prototype.toString.call(t).slice(8,-1);return"Object"===a&&t.constructor&&(a=t.constructor.name),"Map"===a||"Set"===a?v()(a):"Arguments"===a||/^(?:Ui|I)nt(?:8|16|32)(?:Clamped)?Array$/.test(a)?d(t,e):void 0}}function L(){throw new TypeError("Invalid attempt to spread non-iterable instance.\nIn order to be iterable, non-array objects must have a [Symbol.iterator]() method.")}function A(t){return u(t)||y(t)||m(t)||L()}a("55dd"),a("6b54"),a("ac6a");var I,S=(I={name:"index",data:function(){return{major:null,showTable:!1,typeId:null,topCheck:!0,checkList:[],selectForm:{name:"",region:""},pageInfo:{currentPage:1,pageSize:2,total:0},carPageInfo:{currentPage:1,pageSize:10,total:0},defaultHeader:[{RES_TYPE_NAME:"设备ID",SRC_NAME:"",sort:"devId"},{RES_TYPE_NAME:"设备名",SRC_NAME:"",sort:"devName"}],headerData:[{RES_TYPE_NAME:"设备ID",SRC_NAME:"",sort:"devId"},{RES_TYPE_NAME:"设备名",SRC_NAME:"",sort:"devName"}],tableData:[],carColumn:[{label:"采集项",property:"attr"},{label:"采集值",property:"val"}],carTableData:[{attr:"采集项1",val:"1"}],attrVisible:!1,devTypeList:[],devType:"",devName:"",devAllList:[],devList:[],tableDevList:[],mainTypeList:[],sonTypeList:[],attrList:[],visibleAttrList:[]}},watch:{},mounted:function(){this.getDevTypeList()}},s(I,"watch",{devName:function(t){this.devList=this.devAllList,this.devList=this.devAllList.filter((function(e){return-1!==e.devName.indexOf(t)}))}}),s(I,"methods",{getDevTypeList:function(){var t=this;this.$.ajax({url:"/devdata/listdevtype",type:"post",success:function(e){var a=e.data;a.forEach((function(t){t.devTypeId=t.devMainTypeId,t.devTypeName=t.devMainTypeName,t.children=t.sysDevTypeDefVoList}));var i=[{devTypeId:"SIG",devTypeName:"信号"},{devTypeId:"VEHICLE",devTypeName:"车辆"},{devTypeId:"BAS",devTypeName:"水泵"}];for(var r in a)for(var n in i)i[n].devTypeId===a[r].major&&(i[n].children=i[n].children?i[n].children:[],i[n].children.push(a[r]));t.devTypeList=[],t.devTypeList=i}})},getDev:function(t){if(this.major=t[0],this.typeId=t[2],t[2]){var e=this;this.$.ajax({url:"/devdata/listdev",type:"post",data:{typeid:t[2],start:0,limit:1e3},success:function(t){t.data&&t.data.length>0?(e.pageInfo.total=t.data.length,e.devAllList=t.data,e.devList=t.data,e.devId=t.data[0].devId,e.getAttr(t.data[0].devId,t.data[0].devTypeId)):(e.devId=null,e.devAllList=[],e.devList=[],e.attrList=[])}})}},getDevPages:function(){var t=this;this.$.ajax({url:"/devdata/listdev",type:"post",data:{start:t.pageInfo.currentPage,limit:t.pageInfo.pageSize,typeid:t.typeId},success:function(e){t.tableDevList=e.data,t.getDevValue(t.tableDevList)}})},chooseDev:function(t){var e=this,a=t.devId,i=this.devList;i.forEach((function(i){a===i.devId&&1==i.active?(i.active=!1,e.getDevValue(e.tableDevList)):a===i.devId?(e.getDevValue([t]),i.active=!0):i.active=!1})),this.devList=[],this.devList=i},getAttr:function(t,e){var a=this;this.$.ajax({url:"/devdata/listdevattr",type:"post",data:{devid:t},success:function(t){t.data.forEach((function(t,e){t.sort=e.toString()})),a.getVisibleAttr(t.data,e)}})},getVisibleAttr:function(t,e){var a=this;this.$.ajax({url:"/devdata/getvisible",type:"post",data:{typeid:e},success:function(e){e.data&&e.data.length>0&&(a.visibleAttrList=e.data,a.visibleAttrList.forEach((function(e){t.forEach((function(t){e.keyId==t.sort&&1==e.isVisible&&(t.id=e.id,t.visible=!0)}))}))),a.attrList=t,a.chooseAttrData=t,a.initAttrChoose(),a.initTableInfo(),a.headerData=[].concat(A(a.headerData),A(a.attrList)),a.showTable=!0}})},updateVisible:function(){var t=this,e=this.chooseAttrData,a=this;if(e.length>0){var i=[];this.attrList.forEach((function(a){var r=0;e.forEach((function(t){a.sort==t.sort&&(r=1)})),i.push({id:a.id,typeId:t.typeId,isVisible:r,keyId:a.sort})})),this.$.ajax({type:"post",url:"/devdata/updatevisible",contentType:"application/json",data:JSON.stringify(i),dataType:"json",success:function(t){"0000"==t.code?(a.attrVisible=!1,a.getAttr(a.devId,a.typeId),a.$message({type:"success",message:"保存成功"})):a.$message({type:"warning",message:t.msg})}})}else this.$message({type:"warning",message:"请选择采集项"})},initAttrChoose:function(){var t=this;this.$nextTick((function(){for(var e=0;e<t.attrList.length;e++)t.$refs.attrList.toggleRowSelection(t.attrList[e],!0)}))},showVisible:function(){var t=this;this.$nextTick((function(){for(var e=0;e<t.attrList.length;e++)t.$refs.attrList.toggleRowSelection(t.attrList[e],t.attrList[e].visible)}))},chooseVisibleAttr:function(t){var e=this;this.attrVisible=t;var a=[],i=[];t?(this.showVisible(),this.visibleAttrList.forEach((function(t){e.attrList.forEach((function(e){t.keyId==e.sort&&i.push(e)}))})),a=[].concat(A(this.defaultHeader),i)):(this.initAttrChoose(),a=[].concat(A(this.defaultHeader),A(this.attrList))),this.headerData=[],this.headerData=a},selectAllAttr:function(t){t.sort((function(t,e){return t.sort>e.sort?1:t.sort<e.sort?-1:0})),this.chooseAttrData=t;var e=[].concat(A(this.defaultHeader),A(t));this.headerData=[],this.headerData=e},selectAttr:function(t){t.sort((function(t,e){return t.sort>e.sort?1:t.sort<e.sort?-1:0})),this.chooseAttrData=t;var e=[].concat(A(this.defaultHeader),A(t));this.headerData=[],this.headerData=e},initTableInfo:function(){this.pageInfo.total<=2?(this.tableDevList=this.devList,this.getDevValue(this.tableDevList)):this.getDevPages()},getDevValue:function(t){var e=[];t.forEach((function(t){e.push({devId:t.devId})}));var a=this;this.$.ajax({type:"post",url:"/devdata/listdevscollects",contentType:"application/json",data:JSON.stringify(e),dataType:"json",success:function(t){a.valData=t.data,a.drawVal(a.valData)}})},drawVal:function(t){var e=[];Object.keys(t).forEach((function(t){e.push({devId:t})})),this.devAllList.forEach((function(t){e.forEach((function(e){e.devId===t.devId&&(e.devName=t.devName)}))})),e.forEach((function(e,a){t[e.devId].forEach((function(t,a){e[a]=t||"无数据"}))})),this.tableData=[],this.tableData=e},handleCurrentChange:function(t){var e=this.devList;e.forEach((function(t){t.active=!1})),this.devList=[],this.devList=e,this.pageInfo.currentPage=t,this.getDevPages()},handleCarCurrentChange:function(t){console.log(t)},handleCarSizeChange:function(t){console.log(t)}}),I),T=S,x=(a("cdfa"),a("2877")),E=Object(x["a"])(T,i,r,!1,null,null,null);e["default"]=E.exports},3846:function(t,e,a){a("9e1e")&&"g"!=/./g.flags&&a("86cc").f(RegExp.prototype,"flags",{configurable:!0,get:a("0bfb")})},"40c3":function(t,e,a){var i=a("6b4c"),r=a("5168")("toStringTag"),n="Arguments"==i(function(){return arguments}()),o=function(t,e){try{return t[e]}catch(a){}};t.exports=function(t){var e,a,s;return void 0===t?"Undefined":null===t?"Null":"string"==typeof(a=o(e=Object(t),r))?a:n?i(e):"Object"==(s=i(e))&&"function"==typeof e.callee?"Arguments":s}},"454f":function(t,e,a){a("46a7");var i=a("584a").Object;t.exports=function(t,e,a){return i.defineProperty(t,e,a)}},"456d":function(t,e,a){var i=a("4bf8"),r=a("0d58");a("5eda")("keys",(function(){return function(t){return r(i(t))}}))},"46a7":function(t,e,a){var i=a("63b6");i(i.S+i.F*!a("8e60"),"Object",{defineProperty:a("d9f6").f})},"4ee1":function(t,e,a){var i=a("5168")("iterator"),r=!1;try{var n=[7][i]();n["return"]=function(){r=!0},Array.from(n,(function(){throw 2}))}catch(o){}t.exports=function(t,e){if(!e&&!r)return!1;var a=!1;try{var n=[7],s=n[i]();s.next=function(){return{done:a=!0}},n[i]=function(){return s},t(n)}catch(o){}return a}},"549b":function(t,e,a){"use strict";var i=a("d864"),r=a("63b6"),n=a("241e"),o=a("b0dc"),s=a("3702"),c=a("b447"),l=a("20fd"),d=a("7cd6");r(r.S+r.F*!a("4ee1")((function(t){Array.from(t)})),"Array",{from:function(t){var e,a,r,u,f=n(t),v="function"==typeof this?this:Array,h=arguments.length,p=h>1?arguments[1]:void 0,b=void 0!==p,g=0,y=d(f);if(b&&(p=i(p,h>2?arguments[2]:void 0,2)),void 0==y||v==Array&&s(y))for(e=c(f.length),a=new v(e);e>g;g++)l(a,g,b?p(f[g],g):f[g]);else for(u=y.call(f),a=new v;!(r=u.next()).done;g++)l(a,g,b?o(u,p,[r.value,g],!0):r.value);return a.length=g,a}})},"54a1":function(t,e,a){a("6c1c"),a("1654"),t.exports=a("95d5")},"55dd":function(t,e,a){"use strict";var i=a("5ca1"),r=a("d8e8"),n=a("4bf8"),o=a("79e5"),s=[].sort,c=[1,2,3];i(i.P+i.F*(o((function(){c.sort(void 0)}))||!o((function(){c.sort(null)}))||!a("2f21")(s)),"Array",{sort:function(t){return void 0===t?s.call(n(this)):s.call(n(this),r(t))}})},"5eda":function(t,e,a){var i=a("5ca1"),r=a("8378"),n=a("79e5");t.exports=function(t,e){var a=(r.Object||{})[t]||Object[t],o={};o[t]=e(a),i(i.S+i.F*n((function(){a(1)})),"Object",o)}},"67bb":function(t,e,a){t.exports=a("f921")},"6b54":function(t,e,a){"use strict";a("3846");var i=a("cb7c"),r=a("0bfb"),n=a("9e1e"),o="toString",s=/./[o],c=function(t){a("2aba")(RegExp.prototype,o,t,!0)};a("79e5")((function(){return"/a/b"!=s.call({source:"a",flags:"b"})}))?c((function(){var t=i(this);return"/".concat(t.source,"/","flags"in t?t.flags:!n&&t instanceof RegExp?r.call(t):void 0)})):s.name!=o&&c((function(){return s.call(this)}))},"774e":function(t,e,a){t.exports=a("d2d5")},"7cd6":function(t,e,a){var i=a("40c3"),r=a("5168")("iterator"),n=a("481b");t.exports=a("584a").getIteratorMethod=function(t){if(void 0!=t)return t[r]||t["@@iterator"]||n[i(t)]}},"85f2":function(t,e,a){t.exports=a("454f")},"95d5":function(t,e,a){var i=a("40c3"),r=a("5168")("iterator"),n=a("481b");t.exports=a("584a").isIterable=function(t){var e=Object(t);return void 0!==e[r]||"@@iterator"in e||n.hasOwnProperty(i(e))}},a745:function(t,e,a){t.exports=a("f410")},ac6a:function(t,e,a){for(var i=a("cadf"),r=a("0d58"),n=a("2aba"),o=a("7726"),s=a("32e9"),c=a("84f2"),l=a("2b4c"),d=l("iterator"),u=l("toStringTag"),f=c.Array,v={CSSRuleList:!0,CSSStyleDeclaration:!1,CSSValueList:!1,ClientRectList:!1,DOMRectList:!1,DOMStringList:!1,DOMTokenList:!0,DataTransferItemList:!1,FileList:!1,HTMLAllCollection:!1,HTMLCollection:!1,HTMLFormElement:!1,HTMLSelectElement:!1,MediaList:!0,MimeTypeArray:!1,NamedNodeMap:!1,NodeList:!0,PaintRequestList:!1,Plugin:!1,PluginArray:!1,SVGLengthList:!1,SVGNumberList:!1,SVGPathSegList:!1,SVGPointList:!1,SVGStringList:!1,SVGTransformList:!1,SourceBufferList:!1,StyleSheetList:!0,TextTrackCueList:!1,TextTrackList:!1,TouchList:!1},h=r(v),p=0;p<h.length;p++){var b,g=h[p],y=v[g],m=o[g],L=m&&m.prototype;if(L&&(L[d]||s(L,d,f),L[u]||s(L,u,g),c[g]=f,y))for(b in i)L[b]||n(L,b,i[b],!0)}},b0dc:function(t,e,a){var i=a("e4ae");t.exports=function(t,e,a,r){try{return r?e(i(a)[0],a[1]):e(a)}catch(o){var n=t["return"];throw void 0!==n&&i(n.call(t)),o}}},c8bb:function(t,e,a){t.exports=a("54a1")},cdfa:function(t,e,a){"use strict";var i=a("1eb0"),r=a.n(i);r.a},d2d5:function(t,e,a){a("1654"),a("549b"),t.exports=a("584a").Array.from},f410:function(t,e,a){a("1af6"),t.exports=a("584a").Array.isArray}}]);