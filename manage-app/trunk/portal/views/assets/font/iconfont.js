;(function(window) {

  var svgSprite = '<svg>' +
    '' +
    '<symbol id="icon-sousuo-sousuo" viewBox="0 0 1024 1024">' +
    '' +
    '<path d="M668.16 283.9552c-106.5984-106.7008-279.552-106.7008-386.2528 0-106.7008 106.7008-106.7008 279.6544 0 386.2528 49.4592 49.4592 117.6576 79.9744 193.1264 79.9744 75.4688 0 143.6672-30.6176 193.1264-79.9744 49.4592-49.4592 79.9744-117.6576 79.9744-193.1264s-30.5152-143.6672-79.9744-193.1264zM222.4128 729.7024C157.7984 665.088 117.76 575.6928 117.76 477.0816 117.76 279.7568 277.7088 119.808 475.0336 119.808s357.2736 159.9488 357.2736 357.2736c0 98.7136-40.0384 188.0064-104.6528 252.6208-64.6144 64.6144-153.9072 104.6528-252.6208 104.6528s-187.904-40.0384-252.6208-104.6528zM889.7536 891.904c-8.8064 8.8064-20.992 14.336-34.5088 14.336-13.5168 0-25.7024-5.4272-34.5088-14.336l-68.9152-69.0176c-8.8064-8.8064-14.2336-20.992-14.2336-34.5088 0-26.9312 21.8112-48.7424 48.7424-48.7424 13.4144 0 25.7024 5.4272 34.5088 14.2336l69.0176 68.9152c8.9088 8.8064 14.336 20.992 14.336 34.5088-0.1024 13.6192-5.5296 25.8048-14.4384 34.6112z" fill="" ></path>' +
    '' +
    '</symbol>' +
    '' +
    '</svg>'
  var script = function() {
    var scripts = document.getElementsByTagName('script')
    return scripts[scripts.length - 1]
  }()
  var shouldInjectCss = script.getAttribute("data-injectcss")

  /**
   * document ready
   */
  var ready = function(fn) {
    if (document.addEventListener) {
      if (~["complete", "loaded", "interactive"].indexOf(document.readyState)) {
        setTimeout(fn, 0)
      } else {
        var loadFn = function() {
          document.removeEventListener("DOMContentLoaded", loadFn, false)
          fn()
        }
        document.addEventListener("DOMContentLoaded", loadFn, false)
      }
    } else if (document.attachEvent) {
      IEContentLoaded(window, fn)
    }

    function IEContentLoaded(w, fn) {
      var d = w.document,
        done = false,
        // only fire once
        init = function() {
          if (!done) {
            done = true
            fn()
          }
        }
        // polling for no errors
      var polling = function() {
        try {
          // throws errors until after ondocumentready
          d.documentElement.doScroll('left')
        } catch (e) {
          setTimeout(polling, 50)
          return
        }
        // no errors, fire

        init()
      };

      polling()
        // trying to always fire before onload
      d.onreadystatechange = function() {
        if (d.readyState == 'complete') {
          d.onreadystatechange = null
          init()
        }
      }
    }
  }

  /**
   * Insert el before target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var before = function(el, target) {
    target.parentNode.insertBefore(el, target)
  }

  /**
   * Prepend el to target
   *
   * @param {Element} el
   * @param {Element} target
   */

  var prepend = function(el, target) {
    if (target.firstChild) {
      before(el, target.firstChild)
    } else {
      target.appendChild(el)
    }
  }

  function appendSvg() {
    var div, svg

    div = document.createElement('div')
    div.innerHTML = svgSprite
    svgSprite = null
    svg = div.getElementsByTagName('svg')[0]
    if (svg) {
      svg.setAttribute('aria-hidden', 'true')
      svg.style.position = 'absolute'
      svg.style.width = 0
      svg.style.height = 0
      svg.style.overflow = 'hidden'
      prepend(svg, document.body)
    }
  }

  if (shouldInjectCss && !window.__iconfont__svg__cssinject__) {
    window.__iconfont__svg__cssinject__ = true
    try {
      document.write("<style>.svgfont {display: inline-block;width: 1em;height: 1em;fill: currentColor;vertical-align: -0.1em;font-size:16px;}</style>");
    } catch (e) {
      console && console.log(e)
    }
  }

  ready(appendSvg)


})(window)