/* Overlay for divs */
(function ($) {
    'use strict';

    // Inner variables definition
    var Overlay,          // Constructor for the Overlay Object
        old,              // the old value stored inside jQuery
        observerOptions,  // Defaults options for the observer
        MutationObserver; // see https://developer.mozilla.org/en-US/docs/Web/API/MutationObserver

    // Definition for the mutation observer by use in the browser
    MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;
    // Definition for the options for the observer
    observerOptions = {subtree: false, attributes: true};

    /**
     * Computes the CSS for an element.
     * 
     * @param $elt (jQuery Object) the element whose css are to be computed
     * 
     * @returns (Object) an object representing the CSS properties of the element
     */
    function computeCss($elt) {
        return {
            position: 'absolute',
            height:   $elt.outerHeight(),
            width:    $elt.outerWidth()
        };
    }

    /**
     * Registers the observer for the element.
     *
     * @param $elt (jQuery Object)   the element on which the observer should be registered
     * @param obs  (Observer Object) the observer to register
     */
    function registerObserver($elt, obs) {
        var element = $elt.length > 0 && $elt[0];
        if (element) {
            obs.observe(element, observerOptions);
        }
    }

    /**
     * Checks Support for Event.
     *
     * @param eventName (String)        the name of the event
     * @param $elt      (jQuery Object) the element to test support against
     *
     * @return (Boolean) returns result of test - true or false
     */
    function isEventSupported(eventName, $elt) {
        var el = $elt.length > 0 && $elt[0],
            event = 'on' + eventName,
            supported = false;
        if (!el) {
            return false;
        }
        supported = el.hasOwnProperty(event);
        if (!supported) {
            el.setAttribute(event, 'return;');
            supported = (typeof el[event] === 'function');
        }
        return supported;
    }

    /**
     * Initialises the overlay.
     * 
     * @param overlay (Overlay Object) the overlay to initialize
     * 
     * @returns (Overlay Object) the initialised overlay
     */
    function initialise(overlay) {
        overlay.$overlayed.addClass('div-overlay');
        overlay.$element.before(overlay.$overlayed);
        overlay.$overlayed.hide();
        return overlay;
    }

    // OVERLAY CLASS DEFINITION
    // ========================
    Overlay = function (element, options) {
        this.$element = $(element);
        this.$options = $.extend(true, {}, $.fn.overlay.defaults, options);
        this.$overlayed = $('<div>');
        this.observer = undefined;
        initialise(this);
    };

    /** The contructor for the Overlay */
    Overlay.prototype.constructor = Overlay;

    /** Shows the overlay */
    Overlay.prototype.show = function () {
        var e = $.Event('show.overlay'),
            cssOptions = computeCss(this.$element),
            self = this;

        this.$element.trigger(e);
        if (e.isDefaultPrevented()) {
            return;
        }
        this.$overlayed.css(cssOptions)
            .fadeIn();
        this.$element.trigger('shown.overlay');

        /**
         * Function to resize the overlay based on the varied css of the relative element. 
         */
        function resizer() {
            cssOptions = computeCss(self.$element);
            self.$overlayed.css(cssOptions);
        }

        // Binds resize of the overlay to a style modification of the undelying element
        if (this.observer) {
            registerObserver(this.$element, this.observer);
        } else if (MutationObserver) {
            this.observer = new MutationObserver(resizer);
            registerObserver(this.$element, this.observer);
        } else if (isEventSupported('DOMAttrModified', this.$element)) {
            // Fallback for older event
            this.$element.on('DOMAttrModified', resizer);
        } else if (isEventSupported('propertychange', this.$element)) {
            // Fallback for older event on IE
            this.$element.on('propertychange', resizer);
        }
    };

    /** Hides the overlay */
    Overlay.prototype.hide = function () {
        var e = $.Event('hide.overlay');
        this.$element.trigger(e);
        if (e.isDefaultPrevented()) {
            return;
        }
        if (this.$options.loader) {
            this.$spinner.removeClass('active');
        }
        this.$overlayed.fadeOut();
        this.$element.trigger('hidden.overlay');

        // Unbinds resize
        if (this.observer) {
            this.observer.disconnect();
        } else if (isEventSupported('DOMAttrModified', this.$element)) {
            // Fallback for older event
            this.$element.off('DOMAttrModified');
        } else if (isEventSupported('propertychange', this.$element)) {
            // Fallback for older event on IE
            this.$element.off('propertychange');
        }
    };

    /** Overrides the definition of the toString method of Object.prototype */
    Overlay.prototype.toString = function () {
        return "[object Overlay]";
    };

    // OVERLAY PLUGIN DEFINITION
    // ========================

    // Old value for the object
    old = $.fn.overlay;

    /**
     * Plugin for jQuery.
     * 
     * @param option (String / Object / undefined) the option to pass to the plugin
     * 
     * @returns (jQuery Object) the jQuery object referring to the overlay
     */
    $.fn.overlay = function (option) {
        return this.each(function () {
            var self = $(this),
                data = self.data("overlay"),
                options = (typeof option === "object") && option;

            if (!data) {
                data = new Overlay(this, options);
                self.data("overlay", data);
            }
            if (typeof option === "string") {
                data[option]();
            }
        });
    };

    /** The default options of the plugin */
    $.fn.overlay.defaults = {
        // Still empty
    };

    // OVERLAY NO CONFLICT
    // ==================
    /** 
     * Reverts to the old definition of $().overlay.
     * 
     * @returns (Function) the plugin constructor function 
     */
    $.fn.overlay.noConflict = function () {
        $.fn.overlay = old;
        return this;
    };

    // OVERLAY DATA API
    // ================
    // Initialises the overlay for each element with 'data-overlay' set
    $("[data-overlay]").overlay();
}(window.jQuery));