/**
 * Copyright 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 * Please see distribution for license.
 */
$.register_module({
    name: 'og.common.util.ui.contextmenu',
    dependencies: [],
    obj: function () {
        return function (options, event, cell) {
            var $menu = $('<div />').addClass('OG-contextmenu OG-shadow'),
                css = {position: 'absolute', zIndex: options.zindex || 0};
            var items = options.items, html;
            if (options.defaults) items = items.concat([
                {}, // divider
                {name: 'Back', callback: function () {history.back()}},
                {name: 'Forward', callback: function () {history.forward()}},
                {name: 'Reload', callback: function () {location.reload()}}
            ]);
            html = items.reduce(function (acc, val, i) {
                return acc + (val.name ? '<div data-id='+ i +'><span></span>' + val.name + '</div>' : '<hr />');
            }, '');
            $menu.blurkill().html(html).css(css).appendTo('body')
                .position({my: 'left top', at: 'left top', of: event})
                .click(function (event) {items[$(event.target).attr('data-id')].callback(), $(this).remove();});
            return false
        };
    }
});