(window.webpackJsonp=window.webpackJsonp||[]).push([[17],{"f+ep":function(n,l,o){"use strict";o.r(l);var t=o("8Y7J");class e{}var u=o("pMnS"),r=o("TSSN"),i=o("OLyX");class g{constructor(n,l){this.r=l,this.restcontroller=n,this.router=l}ngOnInit(){}onLoggedin(n,l){this.restcontroller.getLogin(n,l).username===n?(this.router.navigate(["/layout/dashboard"]),localStorage.setItem("isLoggedin","isLoggedin")):this.router.navigate(["/access-denied"])}}var a=o("iInd"),c=t.qb({encapsulation:0,styles:[["[_nghost-%COMP%]{display:block}.login-page[_ngcontent-%COMP%]{position:absolute;top:0;left:0;right:0;bottom:0;overflow:auto;background:#222;text-align:center;color:#fff;padding:3em}.login-page[_ngcontent-%COMP%]   .col-lg-4[_ngcontent-%COMP%]{padding:0}.login-page[_ngcontent-%COMP%]   .input-lg[_ngcontent-%COMP%]{height:46px;padding:10px 16px;font-size:18px;line-height:1.3333333;border-radius:0}.login-page[_ngcontent-%COMP%]   .input-underline[_ngcontent-%COMP%]{background:0 0;border:none;box-shadow:none;border-bottom:2px solid rgba(255,255,255,.5);color:#fff;border-radius:0}.login-page[_ngcontent-%COMP%]   .input-underline[_ngcontent-%COMP%]:focus{border-bottom:2px solid #fff;box-shadow:none}.login-page[_ngcontent-%COMP%]   .rounded-btn[_ngcontent-%COMP%]{border-radius:50px;color:rgba(255,255,255,.8);background:#222;border:2px solid rgba(255,255,255,.8);font-size:18px;line-height:40px;padding:0 25px}.login-page[_ngcontent-%COMP%]   .rounded-btn[_ngcontent-%COMP%]:active, .login-page[_ngcontent-%COMP%]   .rounded-btn[_ngcontent-%COMP%]:focus, .login-page[_ngcontent-%COMP%]   .rounded-btn[_ngcontent-%COMP%]:hover, .login-page[_ngcontent-%COMP%]   .rounded-btn[_ngcontent-%COMP%]:visited{color:#fff;border:2px solid #fff;outline:0}.login-page[_ngcontent-%COMP%]   h1[_ngcontent-%COMP%]{font-weight:300;margin-top:20px;margin-bottom:10px;font-size:36px}.login-page[_ngcontent-%COMP%]   h1[_ngcontent-%COMP%]   small[_ngcontent-%COMP%]{color:rgba(255,255,255,.7)}.login-page[_ngcontent-%COMP%]   .form-group[_ngcontent-%COMP%]{padding:8px 0}.login-page[_ngcontent-%COMP%]   .form-group[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]::-webkit-input-placeholder{color:rgba(255,255,255,.6)!important}.login-page[_ngcontent-%COMP%]   .form-group[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]:-moz-placeholder{color:rgba(255,255,255,.6)!important}.login-page[_ngcontent-%COMP%]   .form-group[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]::-moz-placeholder{color:rgba(255,255,255,.6)!important}.login-page[_ngcontent-%COMP%]   .form-group[_ngcontent-%COMP%]   input[_ngcontent-%COMP%]:-ms-input-placeholder{color:rgba(255,255,255,.6)!important}.login-page[_ngcontent-%COMP%]   .form-content[_ngcontent-%COMP%]{padding:40px 0}.login-page[_ngcontent-%COMP%]   .user-avatar[_ngcontent-%COMP%]{border-radius:50%;border:2px solid #fff}"]],data:{animation:[{type:7,name:"routerTransition",definitions:[],options:{}}]}});function p(n){return t.Ob(0,[(n()(),t.sb(0,0,null,null,19,"div",[["class","login-page"]],[[24,"@routerTransition",0]],null,null,null,null)),(n()(),t.sb(1,0,null,null,18,"div",[["class","row justify-content-md-center"]],null,null,null,null,null)),(n()(),t.sb(2,0,null,null,17,"div",[["class","col-md-4"]],null,null,null,null,null)),(n()(),t.sb(3,0,null,null,0,"img",[["class","user-avatar"],["src","assets/images/logo.png"],["width","150px"]],null,null,null,null,null)),(n()(),t.sb(4,0,null,null,1,"h1",[],null,null,null,null,null)),(n()(),t.Mb(-1,null,["Crop Monitoring Cockpit "])),(n()(),t.sb(6,0,null,null,1,"h2",[],null,null,null,null,null)),(n()(),t.Mb(-1,null,["Control Unit"])),(n()(),t.sb(8,0,null,null,11,"form",[["role","form"]],null,null,null,null,null)),(n()(),t.sb(9,0,null,null,6,"div",[["class","form-content"]],null,null,null,null,null)),(n()(),t.sb(10,0,null,null,2,"div",[["class","form-group"]],null,null,null,null,null)),(n()(),t.sb(11,0,[["u",1]],null,1,"input",[["class","form-control input-underline input-lg"],["id",""],["ng-model","name"],["type","text"]],[[8,"placeholder",0]],null,null,null,null)),t.Gb(131072,r.i,[r.j,t.h]),(n()(),t.sb(13,0,null,null,2,"div",[["class","form-group"]],null,null,null,null,null)),(n()(),t.sb(14,0,[["p",1]],null,1,"input",[["class","form-control input-underline input-lg"],["id",""],["type","password"]],[[8,"placeholder",0]],null,null,null,null)),t.Gb(131072,r.i,[r.j,t.h]),(n()(),t.sb(16,0,null,null,2,"a",[["class","btn rounded-btn"]],null,[[null,"click"]],(function(n,l,o){var e=!0;return"click"===l&&(e=!1!==n.component.onLoggedin(t.Eb(n,11).value,t.Eb(n,14).value)&&e),e}),null,null)),(n()(),t.Mb(17,null,["",""])),t.Gb(131072,r.i,[r.j,t.h]),(n()(),t.Mb(-1,null,[" \xa0 "]))],null,(function(n,l){n(l,0,0,void 0),n(l,11,0,t.wb(1,"",t.Nb(l,11,0,t.Eb(l,12).transform("Username")),"")),n(l,14,0,t.wb(1,"",t.Nb(l,14,0,t.Eb(l,15).transform("Password")),"")),n(l,17,0,t.Nb(l,17,0,t.Eb(l,18).transform("Log in")))}))}function s(n){return t.Ob(0,[(n()(),t.sb(0,0,null,null,1,"app-login",[],null,null,null,p,c)),t.rb(1,114688,null,0,g,[i.a,a.l],null,null)],(function(n,l){n(l,1,0)}),null)}var d=t.ob("app-login",g,s,{},{},[]),b=o("jUld"),C=o("SVse"),f=o("fezN");class M{}var m=o("uw8D"),O=o("LzIi");o.d(l,"LoginModuleNgFactory",(function(){return P}));var P=t.pb(e,[],(function(n){return t.Bb([t.Cb(512,t.j,t.ab,[[8,[u.a,d,b.a]],[3,t.j],t.w]),t.Cb(4608,C.n,C.m,[t.t,[2,C.B]]),t.Cb(1073742336,C.b,C.b,[]),t.Cb(1073742336,r.g,r.g,[]),t.Cb(1073742336,a.p,a.p,[[2,a.u],[2,a.l]]),t.Cb(1073742336,M,M,[]),t.Cb(1073742336,m.a,m.a,[]),t.Cb(1073742336,O.a,O.a,[]),t.Cb(1073742336,e,e,[]),t.Cb(1024,a.j,(function(){return[[{path:"",component:g},{path:"/access-denied",component:f.a}],[{path:"",component:f.a}]]}),[])])}))}}]);