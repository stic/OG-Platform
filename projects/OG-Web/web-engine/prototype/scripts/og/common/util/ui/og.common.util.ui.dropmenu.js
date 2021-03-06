/*
 * Copyright 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * Please see distribution for license.
 */
$.register_module({
    name: 'og.common.util.ui.DropMenu',
    dependencies: [],
    obj: function () {
        var events = {
                focus: 'dropmenu:focus',
                focused:'dropmenu:focused',
                open: 'dropmenu:open',
                opened: 'dropmenu:opened',
                close: 'dropmenu:close',
                closed: 'dropmenu:closed'
            },
            /**
             * TODO AG: Must provide Getters/Setters for instance properties as these should really be private
             * and not accessible directly via the instance.
             */
            DropMenu = function (config) {
                var menu = this, tmpl = config.tmpl, data = config.data || {};
                menu.state = 'closed';
                menu.opened = false;
                menu.init_blurkill = false;
                menu.data = data;
                menu.events = events;
                menu.$dom = {};
                menu.$dom.cntr = config.$cntr.html($((Handlebars.compile(tmpl))(data)));
                menu.$dom.toggle = $('.og-menu-toggle', menu.$dom.cntr);
                menu.$dom.menu = $('.og-menu', menu.$dom.cntr);
                menu.addListener(events.open, menu.open.bind(menu))
                    .addListener(events.close, menu.close.bind(menu))
                    .addListener(events.focus, menu.focus.bind(menu));
                return menu;
            };
        $.extend(true, DropMenu.prototype, EventEmitter.prototype);
        DropMenu.prototype.constructor = DropMenu;
        DropMenu.prototype.focus = function () {
            return this.opened = true, this.state = 'focused', this.emitEvent(events.focused, [this]), this;
        };
        DropMenu.prototype.open = function () {
            var menu = this;
            if (this.$dom.menu) {
                setTimeout(function() {
                    if (!menu.init_blurkill) {
                        menu.init_blurkill = true;
                        menu.$dom.menu.blurkill(menu.close.bind(menu));
                    }
                });
                return this.$dom.menu.show(), this.state = 'open', this.opened = true,
                    this.$dom.toggle.addClass('og-active'), this.emitEvent(events.opened, [this]), this;
            }
        };
        DropMenu.prototype.close = function () {
            if (this.$dom.menu) {
                return this.$dom.menu.hide(), this.state = 'closed', this.opened = false, this.init_blurkill = false,
                    this.$dom.toggle.removeClass('og-active'), this.emitEvent(events.closed, [this]), this;
            }
        };
        DropMenu.prototype.menu_handler = function () {
            throw new Error ('Inheriting class needs to extend this method');
        };
        DropMenu.prototype.toggle_handler = function () {
            return this.opened ? this.close() : (this.open(), this.focus());
        };
        DropMenu.prototype.stop = function (event) {
            event.stopPropagation();
            event.preventDefault();
        };
        DropMenu.prototype.status = function () {
            return this.state;
        };
        DropMenu.prototype.is_opened = function () {
            return this.opened;
        };
        return DropMenu;
    }
});