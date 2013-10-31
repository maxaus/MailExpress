(function($) {

    $.fn.splitter = function(args) {
        args = args || {};

        return this.each(function() {
            var zombie, // left-behind splitbar for outline resizes
                defaults = {
                    activeClass: 'active', // class name for active splitter
                    pxPerKey: 8,           // splitter px moved per keypress
                    tabIndex: 0,           // tab order indicator
                    accessKey: ''          // accessKey for splitbar
                },
                dependentDefaults = {
                    v : {
                        keyLeft: 39, keyRight: 37, cursor: "e-resize",
                        splitbarClass: "vsplitbar", outlineClass: "voutline",
                        type: 'v', eventPos: "pageX", origin: "left",
                        split: "width",  pxSplit: "offsetWidth",  side1: "Left", side2: "Right",
                        fixed: "height", pxFixed: "offsetHeight", side3: "Top",  side4: "Bottom"
                    },
                    h : {
                        keyTop: 40, keyBottom: 38,  cursor: "n-resize",
                        splitbarClass: "hsplitbar", outlineClass: "houtline",
                        type: 'h', eventPos: "pageY", origin: "top",
                        split: "height", pxSplit: "offsetHeight", side1: "Top",  side2: "Bottom",
                        fixed: "width",  pxFixed: "offsetWidth",  side3: "Left", side4: "Right"
                    }
                },
                // Determine settings based on incoming options, element classes, and defaults
                splitType = (args.splitHorizontal ? 'h' : args.splitVertical ? 'v' : args.type) || 'v',
                options = $.extend(defaults, dependentDefaults[splitType], args),
                // Create jQuery object closures for splitter and both panes
                container = $(this),
                firstPane, secondPane, splitbar, focuser,
                initPos;

            $(document).ready(init);

            function init() {
                initContainer();
                initPanes();
                initFocuser();
                initSplitbar();
                initialPosition();
            }

            function initContainer() {
                container.primaryDelta = 0;
                container.secondaryDelta = 0;

                if (container.css('position') === 'absolute') {
                    container.css('left', dimSum(container, 'left') - dimSum(container, 'padding-' + options.origin));
                }

                container.css('position', 'relative');

                if ($.support.boxModel) {
                    container.primaryDelta = dimSum(container,
                        "border" + options.side1 + "Width",
                        "border" + options.side2 + "Width",
                        "padding-" + options.side1,
                        "padding-" + options.side2
                    );

                    container.secondaryDelta = dimSum(container,
                        "border" + options.side3 + "Width",
                        "border" + options.side4 + "Width",
                        "padding-" + options.side3,
                        "padding-" + options.side4
                    );

                    container.offsetOrigin = dimSum(container, 'padding-' + options.origin);
                }

                container.resize(resizeContainer);
                
                if (!$.browser.msie || ($.browser.msie && parseInt($.browser.version, 10) >= 8)) {
                    // TODO Resize the container only if it's parent resizes
                    $(window).resize(function() {
                        container.trigger("resize");
                    });
                }
/*
                // Resize event handler; triggered immediately to set initial position
                $(document).load(function() {
                    container.trigger("resize");
                });
*/
            }

            function initPanes() {
                var panes = $(">*", container[0]);

                // left  or top
                firstPane = $(panes[0]);
                firstPane.paneName = options.side1;
                // right or bottom
                secondPane = $(panes[1]);
                secondPane.paneName = options.side2;

                $.each([firstPane,secondPane], function() {
                    // set the styles
                    this.css({
                        position: "absolute",           // positioned inside splitter container
                        "z-index": "1",                 // splitbar is positioned above
                        "-moz-outline-style": "none"    // don't show dotted outline
                    });

                    // determine the sizes
                    this.minSize = options["min" + this.paneName] || dimSum(this, "min-" + options.split);
                    this.maxSize = options["max" + this.paneName] || dimSum(this, "max-" + options.split) || 9999;
                    this.initSize = options["size" + this.paneName] === true ?
                        parseInt($.curCSS(this[0], options.split), 10) :
                        options["size" + this.paneName];

                    this.primaryDelta = this['outer' + capitalize(options.split)](true) - this[options.split]();
                    this.secondaryDelta = this['outer' + capitalize(options.fixed)](true) - this[options.fixed]();
                });
            }

            function initFocuser() {
                // Focuser element, provides keyboard support; title is shown by Opera accessKeys
                var focuser = $('<a href="javascript:void(0)"></a>');

                focuser.attr({
                        accessKey: options.accessKey,
                        tabIndex: options.tabIndex,
                        title: options.splitbarClass
                    })
                    .bind($.browser.opera ? "click" : "focus", function() {
                        this.focus();
                        splitbar.addClass(options.activeClass);
                    })
                    .bind("keydown", function(e) {
                        var key = e.which || e.keyCode;
                        var dir = key === options["key" + options.side1] ? 1 : key === options["key" + options.side2] ? -1 : 0;
                        if (dir) {
                            resplit(firstPane[0][options.pxSplit] + dir * options.pxPerKey, false);
                        }
                    })
                    .bind("blur", function() {
                        splitbar.removeClass(options.activeClass);
                    });
            }

            function initSplitbar() {
                splitbar = $('<div></div>');

                splitbar.insertAfter(firstPane)
                    .css("z-index", "2")
                    .append(focuser)
                    .attr({
                        "class": options.splitbarClass,
                        "unselectable": "on"
                    })
                    .css({
                        "position": "absolute",
                        "user-select": "none",
                        "-webkit-user-select": "none",
                        "-khtml-user-select": "none",
                        "-moz-user-select": "none"
                    })
                    .bind("mousedown", startSplitMouse);

                // Use our cursor unless the style specifies a non-default cursor
                if (/^(auto|default|)$/.test(splitbar.css("cursor"))) {
                    splitbar.css("cursor", options.cursor);
                }

                // Cache several dimensions for speed, rather than re-querying constantly
                splitbar.primaryDimension = splitbar[0][options.pxSplit];
            }

            function initialPosition() {
                initPos = firstPane.initSize;

                if (!isNaN(secondPane.initSize)) { // recalc initial secondPane size as an offset from the top or left side
                    initPos = container[0][options.pxSplit] - container.primaryDelta - secondPane.initSize - splitbar.primaryDimension;
                }
                if (isNaN(initPos)) { // King Solomon's algorithm
                    initPos = Math.round((container[0][options.pxSplit] - container.primaryDelta - splitbar.primaryDimension) / 2);
                }

                container.trigger("resize", [initPos]);
            }

            function startSplitMouse(evt) {
                if (options.outline) {
                    zombie = zombie || splitbar.clone(false).insertAfter(firstPane);
                }

                // Safari selects firstPane/secondPane text on a move
                firstPane.css("-webkit-user-select", "none");
                secondPane.css("-webkit-user-select", "none");

                splitbar.addClass(options.activeClass);
                firstPane._posSplit = firstPane[0][options.pxSplit] - evt[options.eventPos] +
                    dimSum(container, 'padding-' + options.side1) +
                    dimSum(firstPane, 'margin-' + options.side1, 'margin-' + options.side2);
                $(document)
                    .bind("mousemove", doSplitMouse)
                    .bind("mouseup", endSplitMouse);
            }

            function doSplitMouse(evt) {
                var newPos = firstPane._posSplit + evt[options.eventPos];

                if (options.outline) {
                    newPos = Math.max(0, Math.min(newPos, container.primaryDimension - splitbar.primaryDimension));
                    splitbar.css(options.origin, newPos);
                } else {
                    resplit(newPos);
                }
            }

            function endSplitMouse(evt) {
                var newPos;

                splitbar.removeClass(options.activeClass);
                newPos = firstPane._posSplit + evt[options.eventPos];
                if (options.outline) {
                    zombie.remove();
                    zombie = null;
                    resplit(newPos);
                }

                // let Safari select text again
                firstPane.css("-webkit-user-select", "text");
                secondPane.css("-webkit-user-select", "text");

                $(document)
                    .unbind("mousemove", doSplitMouse)
                    .unbind("mouseup", endSplitMouse);
            }

            function resizeContainer(e, size) {
                var parentHeight, containerDecorations;

                // Custom events bubble in jQuery 1.3; don't get into a Yo Dawg
                if (e.target !== this) {
                    return;
                }

                parentHeight = container.parent().height();
                containerDecorations = container.outerHeight(true) - container.height();
                var offset = container.position().top;
                
                if(!isNaN(offset) && offset > 0) {
                    offset -= dimSum(container.parent(), 'padding-top');
                }

                container.height(parentHeight - containerDecorations - offset);
                
                // Determine new width/height of splitter container
                container.primaryDimension = container[0][options.pxSplit] - container.primaryDelta;
                container.secondaryDimension = container[0][options.pxFixed] - container.secondaryDelta;

                // Bail if splitter isn't visible or content isn't there yet
                if (container.secondaryDimension <= 0 || container.primaryDimension <= 0) {
                    return;
                }

                // Re-divvy the adjustable dimension; maintain size of the preferred pane
                if (isNaN(size)) { 
                    if(!(options.sizeRight || options.sizeBottom)) { 
                        size = firstPane['outer' + capitalize(options.split)](true);
                    } else {
                        size = container.primaryDimension - secondPane['outer' + capitalize(options.split)](true) - splitbar.primaryDimension;
                    }
                }
                resplit(size);
            }

            function resplit(newPos) {
                var containerOffset = 0;
                
                // Constrain new splitbar position to fit pane size limits
                newPos = Math.max(firstPane.minSize,
                    container.primaryDimension - secondPane.maxSize,
                    Math.min(newPos,
                        firstPane.maxSize,
                        container.primaryDimension - splitbar.primaryDimension - secondPane.minSize));

                // bar size may change during dock
                splitbar.primaryDimension = splitbar[0][options.pxSplit];
                splitbar.css(options.origin, newPos + container.offsetOrigin)
                    .css(options.fixed, container.secondaryDimension);

                // Resize/position the two panes
                firstPane/*.css(options.origin, container.offsetOrigin)*/ // we don't need to set the offset
                    .css(options.split, newPos - firstPane.primaryDelta)
                    .css(options.fixed, container.secondaryDimension - firstPane.secondaryDelta);
                
                // if the pane is not at the same time a container, add an offset
                if (secondPane.css('position') === 'absolute') {
                    containerOffset = container.offsetOrigin;
                }

                secondPane.css(options.origin, newPos + containerOffset + splitbar.primaryDimension)
                    .css(options.split, container.primaryDimension - newPos - splitbar.primaryDimension - secondPane.primaryDelta)
                    .css(options.fixed, container.secondaryDimension - secondPane.secondaryDelta);
                
                // IE fires resize for us; all others pay cash
                if (!$.browser.msie || ($.browser.msie && parseInt($.browser.version, 10) >= 8)) {
                    firstPane.trigger("resize");
                    secondPane.trigger("resize");
                }
            }

            function dimSum(jq, dims) {
                // Opera returns -1 for missing min/max width, turn into 0
                var sum = 0, i;

                for (i = 1; i < arguments.length; i++) {
                    sum += Math.max(parseInt(jq.css(arguments[i]), 10) || 0, 0);
                }

                return sum;
            }

            function capitalize(word) {
                return word.charAt(0).toUpperCase() + word.slice(1);
            }
        });
    };

})(jQuery);