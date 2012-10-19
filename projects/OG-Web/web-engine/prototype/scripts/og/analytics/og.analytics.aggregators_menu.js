
$.register_module({
    name: 'og.analytics.AggregatorsMenu',
    dependencies: ['og.analytics.DropMenu'],
    obj: function () { 
        return function (config) {
            var menu = new og.analytics.DropMenu(config), $dom = menu.$dom, opts = menu.opts, data = menu.data,
                ag_opts = [], $query = $('.aggregation-selection', $dom.toggle), sel_val, sel_pos, $sel_parent,
                $sel_select, $sel_checkbox, default_sel_txt = 'select aggregation type...', del_s = '.og-icon-delete',
                options_s = '.OG-dropmenu-options', select_s = 'select', checkbox_s = '.og-option :checkbox',
                menu_click_s = 'input, div.og-icon-delete, a.OG-link-add', menu_change_s = select_s,
                process_ag_opts = function () {
                    if(ag_opts.length) {
                        var i = 0, arr = [], query;
                        ag_opts.sort(sort_opts).forEach(function (entry) { // revisit the need for sorting this..
                            if (i > 0) arr[i++] = $dom.toggle_infix.html() + " ";
                            arr[i++] = entry;
                        });
                        query = arr.reduce(function (a, v) {return a += v.val ? v.val : v;}, '');
                        $query.html(query);
                    } else $query.text(default_sel_txt);
                },
                sort_opts = function (a, b) {
                    if (a.pos < b.pos) return -1;
                    if (a.pos > b.pos) return 1;
                    return 0;
                },
                remove_entry = function (entry) {
                    if (ag_opts.length === 1) return ag_opts.length = 0;
                    ag_opts.splice(entry, 1);
                },
                del_handler = function (entry) {
                    if (menu.opts.length === 1) {
                        return $sel_checkbox[0].disabled = true, $query.text(default_sel_txt), 
                            $sel_select.val(default_sel_txt).focus(), remove_entry();
                    }
                    if($sel_select !== undefined) menu.del_handler($sel_parent);
                    for (var i = ~entry ? entry : sel_pos, len = ag_opts.length; i < len; ag_opts[i++].pos-=1);
                    if (~entry) {
                        remove_entry(entry);
                        process_ag_opts();
                    }
                },
                add_handler = function () {
                    if (data.length === opts.length) return;
                    menu.add_handler(); 
                },
                select_handler = function (entry) {
                    if (sel_val === default_sel_txt) {
                        remove_entry(entry);
                        $sel_checkbox[0].disabled = true;
                        if (ag_opts.length === 0) return $query.html(default_sel_txt);
                    } else if (~entry) ag_opts[entry].val = sel_val;
                    else {
                        ag_opts.splice(sel_pos, 0, {pos:sel_pos, val:sel_val, required_field:false});
                        $sel_checkbox[0].disabled = false;
                    }
                    process_ag_opts();
                },
                checkbox_handler = function (entry) {
                    if ($sel_checkbox[0].checked && ~entry) ag_opts[entry].required_field = true;
                    else if (!$sel_checkbox[0].checked && ~entry) ag_opts[entry].required_field = false;
                    $sel_checkbox.focus();
                },
                toggle_handler = function (event){ // Move to menu class
                    menu.toggle_handler();
                    menu.opts[menu.opts.length-1].find('select').first().focus();
                },
                menu_handler = function (event) {
                    var target = event.srcElement || event.target,
                        elem = $(target), entry;
                        $sel_parent = elem.parents(options_s);
                        $sel_select = $sel_parent.find(select_s);
                        $sel_checkbox = $sel_parent.find(checkbox_s);
                        sel_val = $sel_select.val();
                        sel_pos = $sel_parent.data('pos');
                        entry = ag_opts.pluck('pos').indexOf(sel_pos);
                    if (elem.is(menu.$dom.add)) return menu.stop(event), add_handler();
                    if (elem.is(del_s)) return menu.stop(event), del_handler(entry);
                    if (elem.is($sel_checkbox)) return checkbox_handler(entry); 
                    if (elem.is($sel_select)) return select_handler(entry);
                };
            $dom.toggle_prefix.append('<span>Aggregated by</span>');
            $dom.toggle_infix.append('<span>then</span>');
            if ($dom) {
                if ($dom.toggle) $dom.toggle.on('click', toggle_handler);
                if ($dom.menu) {
                    $dom.menu.on('click', menu_click_s, menu_handler).on('change', menu_change_s, menu_handler);
                }
            }
            menu.get_query = function () {
                return query;
            };
            return menu;
        };
    }
});