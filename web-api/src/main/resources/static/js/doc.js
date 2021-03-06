!function (A, e) {
    "use strict";
    "function" == typeof define && define.amd ? define([], e) : "object" == typeof module && module.exports ? module.exports = e() : (A.AnchorJS = e(), A.anchors = new A.AnchorJS)
}(this, function () {
    "use strict";
    return function (A) {
        function d(A) {
            A.icon = Object.prototype.hasOwnProperty.call(A, "icon") ? A.icon : "", A.visible = Object.prototype.hasOwnProperty.call(A, "visible") ? A.visible : "hover", A.placement = Object.prototype.hasOwnProperty.call(A, "placement") ? A.placement : "right", A.ariaLabel = Object.prototype.hasOwnProperty.call(A, "ariaLabel") ? A.ariaLabel : "Anchor", A.class = Object.prototype.hasOwnProperty.call(A, "class") ? A.class : "", A.base = Object.prototype.hasOwnProperty.call(A, "base") ? A.base : "", A.truncate = Object.prototype.hasOwnProperty.call(A, "truncate") ? Math.floor(A.truncate) : 64, A.titleText = Object.prototype.hasOwnProperty.call(A, "titleText") ? A.titleText : ""
        }

        function f(A) {
            var e;
            if ("string" == typeof A || A instanceof String) e = [].slice.call(document.querySelectorAll(A)); else {
                if (!(Array.isArray(A) || A instanceof NodeList)) throw new TypeError("The selector provided to AnchorJS was invalid.");
                e = [].slice.call(A)
            }
            return e
        }

        this.options = A || {}, this.elements = [], d(this.options), this.isTouchDevice = function () {
            return Boolean("ontouchstart" in window || window.TouchEvent || window.DocumentTouch && document instanceof DocumentTouch)
        }, this.add = function (A) {
            var e, t, o, n, i, s, a, r, c, l, h, u, p = [];
            if (d(this.options), "touch" === (h = this.options.visible) && (h = this.isTouchDevice() ? "always" : "hover"), 0 === (e = f(A = A || "h2, h3, h4, h5, h6")).length) return this;
            for (!function () {
                if (null !== document.head.querySelector("style.anchorjs")) return;
                var A, e = document.createElement("style");
                e.className = "anchorjs", e.appendChild(document.createTextNode("")), void 0 === (A = document.head.querySelector('[rel="stylesheet"],style')) ? document.head.appendChild(e) : document.head.insertBefore(e, A);
                e.sheet.insertRule(".anchorjs-link{opacity:0;text-decoration:none;-webkit-font-smoothing:antialiased;-moz-osx-font-smoothing:grayscale}", e.sheet.cssRules.length), e.sheet.insertRule(":hover>.anchorjs-link,.anchorjs-link:focus{opacity:1}", e.sheet.cssRules.length), e.sheet.insertRule("[data-anchorjs-icon]::after{content:attr(data-anchorjs-icon)}", e.sheet.cssRules.length), e.sheet.insertRule('@font-face{font-family:anchorjs-icons;src:url(data:n/a;base64,AAEAAAALAIAAAwAwT1MvMg8yG2cAAAE4AAAAYGNtYXDp3gC3AAABpAAAAExnYXNwAAAAEAAAA9wAAAAIZ2x5ZlQCcfwAAAH4AAABCGhlYWQHFvHyAAAAvAAAADZoaGVhBnACFwAAAPQAAAAkaG10eASAADEAAAGYAAAADGxvY2EACACEAAAB8AAAAAhtYXhwAAYAVwAAARgAAAAgbmFtZQGOH9cAAAMAAAAAunBvc3QAAwAAAAADvAAAACAAAQAAAAEAAHzE2p9fDzz1AAkEAAAAAADRecUWAAAAANQA6R8AAAAAAoACwAAAAAgAAgAAAAAAAAABAAADwP/AAAACgAAA/9MCrQABAAAAAAAAAAAAAAAAAAAAAwABAAAAAwBVAAIAAAAAAAIAAAAAAAAAAAAAAAAAAAAAAAMCQAGQAAUAAAKZAswAAACPApkCzAAAAesAMwEJAAAAAAAAAAAAAAAAAAAAARAAAAAAAAAAAAAAAAAAAAAAQAAg//0DwP/AAEADwABAAAAAAQAAAAAAAAAAAAAAIAAAAAAAAAIAAAACgAAxAAAAAwAAAAMAAAAcAAEAAwAAABwAAwABAAAAHAAEADAAAAAIAAgAAgAAACDpy//9//8AAAAg6cv//f///+EWNwADAAEAAAAAAAAAAAAAAAAACACEAAEAAAAAAAAAAAAAAAAxAAACAAQARAKAAsAAKwBUAAABIiYnJjQ3NzY2MzIWFxYUBwcGIicmNDc3NjQnJiYjIgYHBwYUFxYUBwYGIwciJicmNDc3NjIXFhQHBwYUFxYWMzI2Nzc2NCcmNDc2MhcWFAcHBgYjARQGDAUtLXoWOR8fORYtLTgKGwoKCjgaGg0gEhIgDXoaGgkJBQwHdR85Fi0tOAobCgoKOBoaDSASEiANehoaCQkKGwotLXoWOR8BMwUFLYEuehYXFxYugC44CQkKGwo4GkoaDQ0NDXoaShoKGwoFBe8XFi6ALjgJCQobCjgaShoNDQ0NehpKGgobCgoKLYEuehYXAAAADACWAAEAAAAAAAEACAAAAAEAAAAAAAIAAwAIAAEAAAAAAAMACAAAAAEAAAAAAAQACAAAAAEAAAAAAAUAAQALAAEAAAAAAAYACAAAAAMAAQQJAAEAEAAMAAMAAQQJAAIABgAcAAMAAQQJAAMAEAAMAAMAAQQJAAQAEAAMAAMAAQQJAAUAAgAiAAMAAQQJAAYAEAAMYW5jaG9yanM0MDBAAGEAbgBjAGgAbwByAGoAcwA0ADAAMABAAAAAAwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAABAAH//wAP) format("truetype")}', e.sheet.cssRules.length)
            }(), t = document.querySelectorAll("[id]"), o = [].map.call(t, function (A) {
                return A.id
            }), i = 0; i < e.length; i++) if (this.hasAnchorJSLink(e[i])) p.push(i); else {
                if (e[i].hasAttribute("id")) n = e[i].getAttribute("id"); else if (e[i].hasAttribute("data-anchor-id")) n = e[i].getAttribute("data-anchor-id"); else {
                    for (c = r = this.urlify(e[i].textContent), a = 0; void 0 !== s && (c = r + "-" + a), a += 1, -1 !== (s = o.indexOf(c));) ;
                    s = void 0, o.push(c), e[i].setAttribute("id", c), n = c
                }
                (l = document.createElement("a")).className = "anchorjs-link " + this.options.class, l.setAttribute("aria-label", this.options.ariaLabel), l.setAttribute("data-anchorjs-icon", this.options.icon), this.options.titleText && (l.title = this.options.titleText), u = document.querySelector("base") ? window.location.pathname + window.location.search : "", u = this.options.base || u, l.href = u + "#" + n, "always" === h && (l.style.opacity = "1"), "" === this.options.icon && (l.style.font = "1em/1 anchorjs-icons", "left" === this.options.placement && (l.style.lineHeight = "inherit")), "left" === this.options.placement ? (l.style.position = "absolute", l.style.marginLeft = "-1em", l.style.paddingRight = ".5em", e[i].insertBefore(l, e[i].firstChild)) : (l.style.paddingLeft = ".375em", e[i].appendChild(l))
            }
            for (i = 0; i < p.length; i++) e.splice(p[i] - i, 1);
            return this.elements = this.elements.concat(e), this
        }, this.remove = function (A) {
            for (var e, t, o = f(A), n = 0; n < o.length; n++) (t = o[n].querySelector(".anchorjs-link")) && (-1 !== (e = this.elements.indexOf(o[n])) && this.elements.splice(e, 1), o[n].removeChild(t));
            return this
        }, this.removeAll = function () {
            this.remove(this.elements)
        }, this.urlify = function (A) {
            var e = document.createElement("textarea");
            e.innerHTML = A, A = e.value;
            return this.options.truncate || d(this.options), A.trim().replace(/'/gi, "").replace(/[& +$,:;=?@"#{}|^~[`%!'<>\]./()*\\\n\t\b\v\u00A0]/g, "-").replace(/-{2,}/g, "-").substring(0, this.options.truncate).replace(/^-+|-+$/gm, "").toLowerCase()
        }, this.hasAnchorJSLink = function (A) {
            var e = A.firstChild && -1 < (" " + A.firstChild.className + " ").indexOf(" anchorjs-link "),
                t = A.lastChild && -1 < (" " + A.lastChild.className + " ").indexOf(" anchorjs-link ");
            return e || t || !1
        }
    }
});
/*!
* clipboard.js v2.0.6
* https://clipboardjs.com/
*
* Licensed MIT © Zeno Rocha
*/
!function (t, e) {
    "object" == typeof exports && "object" == typeof module ? module.exports = e() : "function" == typeof define && define.amd ? define([], e) : "object" == typeof exports ? exports.ClipboardJS = e() : t.ClipboardJS = e()
}(this, function () {
    return o = {}, r.m = n = [function (t, e) {
        t.exports = function (t) {
            var e;
            if ("SELECT" === t.nodeName) t.focus(), e = t.value; else if ("INPUT" === t.nodeName || "TEXTAREA" === t.nodeName) {
                var n = t.hasAttribute("readonly");
                n || t.setAttribute("readonly", ""), t.select(), t.setSelectionRange(0, t.value.length), n || t.removeAttribute("readonly"), e = t.value
            } else {
                t.hasAttribute("contenteditable") && t.focus();
                var o = window.getSelection(), r = document.createRange();
                r.selectNodeContents(t), o.removeAllRanges(), o.addRange(r), e = o.toString()
            }
            return e
        }
    }, function (t, e) {
        function n() {
        }

        n.prototype = {
            on: function (t, e, n) {
                var o = this.e || (this.e = {});
                return (o[t] || (o[t] = [])).push({fn: e, ctx: n}), this
            }, once: function (t, e, n) {
                var o = this;

                function r() {
                    o.off(t, r), e.apply(n, arguments)
                }

                return r._ = e, this.on(t, r, n)
            }, emit: function (t) {
                for (var e = [].slice.call(arguments, 1), n = ((this.e || (this.e = {}))[t] || []).slice(), o = 0, r = n.length; o < r; o++) n[o].fn.apply(n[o].ctx, e);
                return this
            }, off: function (t, e) {
                var n = this.e || (this.e = {}), o = n[t], r = [];
                if (o && e) for (var i = 0, a = o.length; i < a; i++) o[i].fn !== e && o[i].fn._ !== e && r.push(o[i]);
                return r.length ? n[t] = r : delete n[t], this
            }
        }, t.exports = n, t.exports.TinyEmitter = n
    }, function (t, e, n) {
        var d = n(3), h = n(4);
        t.exports = function (t, e, n) {
            if (!t && !e && !n) throw new Error("Missing required arguments");
            if (!d.string(e)) throw new TypeError("Second argument must be a String");
            if (!d.fn(n)) throw new TypeError("Third argument must be a Function");
            if (d.node(t)) return s = e, f = n, (u = t).addEventListener(s, f), {
                destroy: function () {
                    u.removeEventListener(s, f)
                }
            };
            if (d.nodeList(t)) return a = t, c = e, l = n, Array.prototype.forEach.call(a, function (t) {
                t.addEventListener(c, l)
            }), {
                destroy: function () {
                    Array.prototype.forEach.call(a, function (t) {
                        t.removeEventListener(c, l)
                    })
                }
            };
            if (d.string(t)) return o = t, r = e, i = n, h(document.body, o, r, i);
            throw new TypeError("First argument must be a String, HTMLElement, HTMLCollection, or NodeList");
            var o, r, i, a, c, l, u, s, f
        }
    }, function (t, n) {
        n.node = function (t) {
            return void 0 !== t && t instanceof HTMLElement && 1 === t.nodeType
        }, n.nodeList = function (t) {
            var e = Object.prototype.toString.call(t);
            return void 0 !== t && ("[object NodeList]" === e || "[object HTMLCollection]" === e) && "length" in t && (0 === t.length || n.node(t[0]))
        }, n.string = function (t) {
            return "string" == typeof t || t instanceof String
        }, n.fn = function (t) {
            return "[object Function]" === Object.prototype.toString.call(t)
        }
    }, function (t, e, n) {
        var a = n(5);

        function i(t, e, n, o, r) {
            var i = function (e, n, t, o) {
                return function (t) {
                    t.delegateTarget = a(t.target, n), t.delegateTarget && o.call(e, t)
                }
            }.apply(this, arguments);
            return t.addEventListener(n, i, r), {
                destroy: function () {
                    t.removeEventListener(n, i, r)
                }
            }
        }

        t.exports = function (t, e, n, o, r) {
            return "function" == typeof t.addEventListener ? i.apply(null, arguments) : "function" == typeof n ? i.bind(null, document).apply(null, arguments) : ("string" == typeof t && (t = document.querySelectorAll(t)), Array.prototype.map.call(t, function (t) {
                return i(t, e, n, o, r)
            }))
        }
    }, function (t, e) {
        if ("undefined" != typeof Element && !Element.prototype.matches) {
            var n = Element.prototype;
            n.matches = n.matchesSelector || n.mozMatchesSelector || n.msMatchesSelector || n.oMatchesSelector || n.webkitMatchesSelector
        }
        t.exports = function (t, e) {
            for (; t && 9 !== t.nodeType;) {
                if ("function" == typeof t.matches && t.matches(e)) return t;
                t = t.parentNode
            }
        }
    }, function (t, e, n) {
        "use strict";
        n.r(e);
        var o = n(0), r = n.n(o), i = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (t) {
            return typeof t
        } : function (t) {
            return t && "function" == typeof Symbol && t.constructor === Symbol && t !== Symbol.prototype ? "symbol" : typeof t
        };

        function a(t, e) {
            for (var n = 0; n < e.length; n++) {
                var o = e[n];
                o.enumerable = o.enumerable || !1, o.configurable = !0, "value" in o && (o.writable = !0), Object.defineProperty(t, o.key, o)
            }
        }

        function c(t) {
            !function (t, e) {
                if (!(t instanceof e)) throw new TypeError("Cannot call a class as a function")
            }(this, c), this.resolveOptions(t), this.initSelection()
        }

        var l = (function (t, e, n) {
                return e && a(t.prototype, e), n && a(t, n), t
            }(c, [{
                key: "resolveOptions", value: function (t) {
                    var e = 0 < arguments.length && void 0 !== t ? t : {};
                    this.action = e.action, this.container = e.container, this.emitter = e.emitter, this.target = e.target, this.text = e.text, this.trigger = e.trigger, this.selectedText = ""
                }
            }, {
                key: "initSelection", value: function () {
                    this.text ? this.selectFake() : this.target && this.selectTarget()
                }
            }, {
                key: "selectFake", value: function () {
                    var t = this, e = "rtl" == document.documentElement.getAttribute("dir");
                    this.removeFake(), this.fakeHandlerCallback = function () {
                        return t.removeFake()
                    }, this.fakeHandler = this.container.addEventListener("click", this.fakeHandlerCallback) || !0, this.fakeElem = document.createElement("textarea"), this.fakeElem.style.fontSize = "12pt", this.fakeElem.style.border = "0", this.fakeElem.style.padding = "0", this.fakeElem.style.margin = "0", this.fakeElem.style.position = "absolute", this.fakeElem.style[e ? "right" : "left"] = "-9999px";
                    var n = window.pageYOffset || document.documentElement.scrollTop;
                    this.fakeElem.style.top = n + "px", this.fakeElem.setAttribute("readonly", ""), this.fakeElem.value = this.text, this.container.appendChild(this.fakeElem), this.selectedText = r()(this.fakeElem), this.copyText()
                }
            }, {
                key: "removeFake", value: function () {
                    this.fakeHandler && (this.container.removeEventListener("click", this.fakeHandlerCallback), this.fakeHandler = null, this.fakeHandlerCallback = null), this.fakeElem && (this.container.removeChild(this.fakeElem), this.fakeElem = null)
                }
            }, {
                key: "selectTarget", value: function () {
                    this.selectedText = r()(this.target), this.copyText()
                }
            }, {
                key: "copyText", value: function () {
                    var e = void 0;
                    try {
                        e = document.execCommand(this.action)
                    } catch (t) {
                        e = !1
                    }
                    this.handleResult(e)
                }
            }, {
                key: "handleResult", value: function (t) {
                    this.emitter.emit(t ? "success" : "error", {
                        action: this.action,
                        text: this.selectedText,
                        trigger: this.trigger,
                        clearSelection: this.clearSelection.bind(this)
                    })
                }
            }, {
                key: "clearSelection", value: function () {
                    this.trigger && this.trigger.focus(), document.activeElement.blur(), window.getSelection().removeAllRanges()
                }
            }, {
                key: "destroy", value: function () {
                    this.removeFake()
                }
            }, {
                key: "action", set: function (t) {
                    var e = 0 < arguments.length && void 0 !== t ? t : "copy";
                    if (this._action = e, "copy" !== this._action && "cut" !== this._action) throw new Error('Invalid "action" value, use either "copy" or "cut"')
                }, get: function () {
                    return this._action
                }
            }, {
                key: "target", set: function (t) {
                    if (void 0 !== t) {
                        if (!t || "object" !== (void 0 === t ? "undefined" : i(t)) || 1 !== t.nodeType) throw new Error('Invalid "target" value, use a valid Element');
                        if ("copy" === this.action && t.hasAttribute("disabled")) throw new Error('Invalid "target" attribute. Please use "readonly" instead of "disabled" attribute');
                        if ("cut" === this.action && (t.hasAttribute("readonly") || t.hasAttribute("disabled"))) throw new Error('Invalid "target" attribute. You can\'t cut text from elements with "readonly" or "disabled" attributes');
                        this._target = t
                    }
                }, get: function () {
                    return this._target
                }
            }]), c), u = n(1), s = n.n(u), f = n(2), d = n.n(f),
            h = "function" == typeof Symbol && "symbol" == typeof Symbol.iterator ? function (t) {
                return typeof t
            } : function (t) {
                return t && "function" == typeof Symbol && t.constructor === Symbol && t !== Symbol.prototype ? "symbol" : typeof t
            }, p = function (t, e, n) {
                return e && y(t.prototype, e), n && y(t, n), t
            };

        function y(t, e) {
            for (var n = 0; n < e.length; n++) {
                var o = e[n];
                o.enumerable = o.enumerable || !1, o.configurable = !0, "value" in o && (o.writable = !0), Object.defineProperty(t, o.key, o)
            }
        }

        var m = (function (t, e) {
            if ("function" != typeof e && null !== e) throw new TypeError("Super expression must either be null or a function, not " + typeof e);
            t.prototype = Object.create(e && e.prototype, {
                constructor: {
                    value: t,
                    enumerable: !1,
                    writable: !0,
                    configurable: !0
                }
            }), e && (Object.setPrototypeOf ? Object.setPrototypeOf(t, e) : t.__proto__ = e)
        }(v, s.a), p(v, [{
            key: "resolveOptions", value: function (t) {
                var e = 0 < arguments.length && void 0 !== t ? t : {};
                this.action = "function" == typeof e.action ? e.action : this.defaultAction, this.target = "function" == typeof e.target ? e.target : this.defaultTarget, this.text = "function" == typeof e.text ? e.text : this.defaultText, this.container = "object" === h(e.container) ? e.container : document.body
            }
        }, {
            key: "listenClick", value: function (t) {
                var e = this;
                this.listener = d()(t, "click", function (t) {
                    return e.onClick(t)
                })
            }
        }, {
            key: "onClick", value: function (t) {
                var e = t.delegateTarget || t.currentTarget;
                this.clipboardAction && (this.clipboardAction = null), this.clipboardAction = new l({
                    action: this.action(e),
                    target: this.target(e),
                    text: this.text(e),
                    container: this.container,
                    trigger: e,
                    emitter: this
                })
            }
        }, {
            key: "defaultAction", value: function (t) {
                return b("action", t)
            }
        }, {
            key: "defaultTarget", value: function (t) {
                var e = b("target", t);
                if (e) return document.querySelector(e)
            }
        }, {
            key: "defaultText", value: function (t) {
                return b("text", t)
            }
        }, {
            key: "destroy", value: function () {
                this.listener.destroy(), this.clipboardAction && (this.clipboardAction.destroy(), this.clipboardAction = null)
            }
        }], [{
            key: "isSupported", value: function (t) {
                var e = 0 < arguments.length && void 0 !== t ? t : ["copy", "cut"], n = "string" == typeof e ? [e] : e,
                    o = !!document.queryCommandSupported;
                return n.forEach(function (t) {
                    o = o && !!document.queryCommandSupported(t)
                }), o
            }
        }]), v);

        function v(t, e) {
            !function (t, e) {
                if (!(t instanceof e)) throw new TypeError("Cannot call a class as a function")
            }(this, v);
            var n = function (t, e) {
                if (!t) throw new ReferenceError("this hasn't been initialised - super() hasn't been called");
                return !e || "object" != typeof e && "function" != typeof e ? t : e
            }(this, (v.__proto__ || Object.getPrototypeOf(v)).call(this));
            return n.resolveOptions(e), n.listenClick(t), n
        }

        function b(t, e) {
            var n = "data-clipboard-" + t;
            if (e.hasAttribute(n)) return e.getAttribute(n)
        }

        e.default = m
    }], r.c = o, r.d = function (t, e, n) {
        r.o(t, e) || Object.defineProperty(t, e, {enumerable: !0, get: n})
    }, r.r = function (t) {
        "undefined" != typeof Symbol && Symbol.toStringTag && Object.defineProperty(t, Symbol.toStringTag, {value: "Module"}), Object.defineProperty(t, "__esModule", {value: !0})
    }, r.t = function (e, t) {
        if (1 & t && (e = r(e)), 8 & t) return e;
        if (4 & t && "object" == typeof e && e && e.__esModule) return e;
        var n = Object.create(null);
        if (r.r(n), Object.defineProperty(n, "default", {
            enumerable: !0,
            value: e
        }), 2 & t && "string" != typeof e) for (var o in e) r.d(n, o, function (t) {
            return e[t]
        }.bind(null, o));
        return n
    }, r.n = function (t) {
        var e = t && t.__esModule ? function () {
            return t.default
        } : function () {
            return t
        };
        return r.d(e, "a", e), e
    }, r.o = function (t, e) {
        return Object.prototype.hasOwnProperty.call(t, e)
    }, r.p = "", r(r.s = 6).default;

    function r(t) {
        if (o[t]) return o[t].exports;
        var e = o[t] = {i: t, l: !1, exports: {}};
        return n[t].call(e.exports, e, e.exports, r), e.l = !0, e.exports
    }

    var n, o
});
;/*!
* JavaScript for Bootstrap's docs (https://getbootstrap.com/)
* Copyright 2011-2021 The Bootstrap Authors
* Copyright 2011-2021 Twitter, Inc.
* Licensed under the Creative Commons Attribution 3.0 Unported License.
* For details, see https://creativecommons.org/licenses/by/3.0/.
*/
(function () {
    'use strict'
    document.querySelectorAll('.tooltip-demo').forEach(function (tooltip) {
        new bootstrap.Tooltip(tooltip, {selector: '[data-bs-toggle="tooltip"]'})
    })
    document.querySelectorAll('[data-bs-toggle="popover"]').forEach(function (popover) {
        new bootstrap.Popover(popover)
    })
    var toastPlacement = document.getElementById('toastPlacement')
    if (toastPlacement) {
        document.getElementById('selectToastPlacement').addEventListener('change', function () {
            if (!toastPlacement.dataset.originalClass) {
                toastPlacement.dataset.originalClass = toastPlacement.className
            }
            toastPlacement.className = toastPlacement.dataset.originalClass + ' ' + this.value
        })
    }
    document.querySelectorAll('.bd-example .toast').forEach(function (toastNode) {
        var toast = new bootstrap.Toast(toastNode, {autohide: false})
        toast.show()
    })
    var toastTrigger = document.getElementById('liveToastBtn')
    var toastLiveExample = document.getElementById('liveToast')
    if (toastTrigger) {
        toastTrigger.addEventListener('click', function () {
            var toast = new bootstrap.Toast(toastLiveExample)
            toast.show()
        })
    }
    document.querySelectorAll('.tooltip-test').forEach(function (tooltip) {
        new bootstrap.Tooltip(tooltip)
    })
    document.querySelectorAll('.popover-test').forEach(function (popover) {
        new bootstrap.Popover(popover)
    })
    document.querySelectorAll('.bd-example-indeterminate [type="checkbox"]').forEach(function (checkbox) {
        checkbox.indeterminate = true
    })
    document.querySelectorAll('.bd-content [href="#"]').forEach(function (link) {
        link.addEventListener('click', function (e) {
            e.preventDefault()
        })
    })
    var exampleModal = document.getElementById('exampleModal')
    if (exampleModal) {
        exampleModal.addEventListener('show.bs.modal', function (event) {
            var button = event.relatedTarget
            var recipient = button.getAttribute('data-bs-whatever')
            var modalTitle = exampleModal.querySelector('.modal-title')
            var modalBodyInput = exampleModal.querySelector('.modal-body input')
            modalTitle.textContent = 'New message to ' + recipient
            modalBodyInput.value = recipient
        })
    }
    var btnToggleAnimatedProgress = document.getElementById('btnToggleAnimatedProgress')
    if (btnToggleAnimatedProgress) {
        btnToggleAnimatedProgress.addEventListener('click', function () {
            btnToggleAnimatedProgress.parentNode.querySelector('.progress-bar-striped').classList.toggle('progress-bar-animated')
        })
    }
    var btnHtml = '<div class="bd-clipboard"><button type="button" class="btn-clipboard" title="Copy to clipboard">Copy</button></div>'
    document.querySelectorAll('div.highlight').forEach(function (element) {
        element.insertAdjacentHTML('beforebegin', btnHtml)
    })
    document.querySelectorAll('.btn-clipboard').forEach(function (btn) {
        var tooltipBtn = new bootstrap.Tooltip(btn)
        btn.addEventListener('mouseleave', function () {
            tooltipBtn.hide()
        })
    })
    var clipboard = new ClipboardJS('.btn-clipboard', {
        target: function (trigger) {
            return trigger.parentNode.nextElementSibling
        }
    })
    clipboard.on('success', function (e) {
        var tooltipBtn = bootstrap.Tooltip.getInstance(e.trigger)
        e.trigger.setAttribute('data-bs-original-title', 'Copied!')
        tooltipBtn.show()
        e.trigger.setAttribute('data-bs-original-title', 'Copy to clipboard')
        e.clearSelection()
    })
    clipboard.on('error', function (e) {
        var modifierKey = /mac/i.test(navigator.userAgent) ? '\u2318' : 'Ctrl-'
        var fallbackMsg = 'Press ' + modifierKey + 'C to copy'
        var tooltipBtn = bootstrap.Tooltip.getInstance(e.trigger)
        e.trigger.setAttribute('data-bs-original-title', fallbackMsg)
        tooltipBtn.show()
        e.trigger.setAttribute('data-bs-original-title', 'Copy to clipboard')
    })
    anchors.options = {icon: '#'}
    anchors.add('.bd-content > h2, .bd-content > h3, .bd-content > h4, .bd-content > h5')
})();
(function () {
    'use strict'
    var inputElement = document.getElementById('search-input')
    if (!window.docsearch || !inputElement) {
        return
    }
    var siteDocsVersion = inputElement.getAttribute('data-bd-docs-version')
    document.addEventListener('keydown', function (event) {
        if (event.ctrlKey && event.key === '/') {
            event.preventDefault()
            inputElement.focus()
        }
    })
    window.docsearch({
        apiKey: '5990ad008512000bba2cf951ccf0332f',
        indexName: 'bootstrap',
        inputSelector: '#search-input',
        algoliaOptions: {facetFilters: ['version:' + siteDocsVersion]},
        transformData: function (hits) {
            return hits.map(function (hit) {
                var liveUrl = 'https://getbootstrap.com/'
                hit.url = window.location.origin.startsWith(liveUrl) ? hit.url : hit.url.replace(liveUrl, '/')
                if (hit.anchor === 'content') {
                    hit.url = hit.url.replace(/#content$/, '')
                    hit.anchor = null
                }
                return hit
            })
        },
        debug: false
    })
})()