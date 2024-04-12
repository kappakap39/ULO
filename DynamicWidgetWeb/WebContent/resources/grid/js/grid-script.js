// Loader settings
paceOptions = {
    ajax: {
        trackMethods: ['GET', 'POST']
    },
    document: true,
    eventLag: true
};

$(function () {

    // Prevent #
    $('a[href=#]').click(function (e) {
        e.preventDefault();
    });

    // Filter for clean Object
    function cleanObj(someobj) {
        for (var i in someobj) {
            if (someobj[i] === null || someobj[i] === undefined || someobj[i] === "") {
                delete someobj[i];
            }
        }
        return someobj;
    }

    // Prepare Element for use on Dialogs
    // Put elements to var
    var GridItemEl = $('.panel-template').clone(true).removeClass('hidden panel-template');
    $('.panel-template').remove();
    var GridItemDialog = $('.GridItem.modal-body').clone(true);
    $('.GridItem.modal-body').remove();
    var WidgetEditorDialog = $('.widgeteditor.modal-body').clone(true);
    $('.widgeteditor.modal-body').remove();
    var WidgetItemEditDialog = $('.widgetitemedit.modal-body').clone(true);
    $('.widgetitemedit.modal-body').remove();
    $('#modaldialogs').remove();


    var getGridItemEl = function () {
        return GridItemEl.clone(true);
    };

    var getWidgetEditorDialog = function () {
        return WidgetEditorDialog.clone(true);
    };

    var getGridItemDialog = function () {
        return GridItemDialog.clone(true);
    };

    var getWidgetItemEditDialog = function () {
        return WidgetItemEditDialog.clone(true);
    };


    // Sortable table rows
    //Helper function to keep table row from collapsing when being sorted
    var fixHelperModified = function (e, tr) {
        var $originals = tr.children();
        var $helper = tr.clone();
        $helper.children().each(function (index) {
            $(this).width($originals.eq(index).outerWidth());
        });
        return $helper;
    };

    //Re-number table rows
    var renumber_table = function (tableEl) {
        $(tableEl).find('tr').each(function () {
            count = $(this).parent().children().index($(this)) + 1;
            $(this).attr('data-seq', count);
            $(this).find('.sequence').html(count);
        });
    };

    // Load prototype to Grid Item
    var loadWidgetPrototype = function (el, node) {
        // el : Element of Grid item
        // node : Data from Grid item
        var panelbody = el.find('.panel-body');
        panelbody.addClass('loading'); // set loading
        $.ajax({
            url: 'wgproto.php', // URI
            data: 'id=' + node.id,
            contentType: "application/json; charset=utf-8",
            type: 'GET',
            cache: false,
            timeout: 10000,
            dataType: 'json', // IMPORTANT !!!
            error: function () {
                panelbody.removeClass('loading');
                return true;
            },
            success: function (rs) {
                var griditem = el.find('.widget-box');
                griditem.html(rs.html);
                panelbody.removeClass('loading');
            }
        });
    };

    // Bind Buttons Grid item
    // Edit Grid item
    $('.grid-stack').delegate('.editgriditem', 'click', function (e) {
        var el = $(this).closest('.grid-stack-item'); // Use  parents() -or- closest() ??
        var node = el.data('_gridstack_node');
        try {
            delete node._dirty;
            delete node._id;
            //delete node.el; // Not need to delete
            delete node.auto_position;
        } catch (ex) {
        }
        ;

        e.data = {node: node, el: el};
        GriditemEditor(e, true);
    });

    $('.grid-stack').delegate('.editwgs', 'click', function (e) {
        var el = $(this).closest('.grid-stack-item'); // Use  parents() -or- closest() ??
        var node = el.data('_gridstack_node');
        try {
            delete node._dirty;
            delete node._id;
            //delete node.el; // Don't Delete
            delete node.auto_position;
        } catch (ex) {
        }
        ;

        e.data = {
            node: node,
            el: el,
            byMenu: true,
            parent: {
                // Proxy functions for dialog
                setData: function (k, v) {
                    node[k] = v;
                    $('.grid-stack').data('gridstack').update_widget(el, node);
                    loadWidgetPrototype(e.data.el, node);
                },
                getData: function (k) {
                    return node[k];
                }
            }
        };
        WidgetEditor(e, true);
    });

    // Disable Dialog backgrop (Background)
    var disableBackDrop = function (d) {
        setTimeout(function (d) {
            md = d.getModal().find('.modal-backdrop').removeClass('in');
        }, 50, d);
    };

    // Functions for use
    var WidgetEditor = (function (e) {
        e.preventDefault();
        BootstrapDialog.show({
            nl2br: false,
            draggable: true,
            closable: false,
            title: 'Widget Editor',
            message: function () {
                var el = getWidgetEditorDialog();
                // bind functions
                //Make diagnosis table sortable
                el.find('.widget-list tbody').sortable({
                    helper: fixHelperModified,
                    stop: function (event, ui) {
                        renumber_table(el.find('.widget-list'));
                    }
                }).disableSelection();
                //Delete button in table rows
                el.find('.widget-list tbody').find('.btn-delete-widget').click(function (e) {
                    fnDeleteWg(e, el, $(this));
                });
                el.find('.widget-list tbody').find('.btn-delete-widget').click(function (e) {
                    WidgetItemEditor(e, true, $(this));
                });

                return el;
            },
            spinicon: 'fa fa-spinner fa-spin fa-fw',
            btnOKLabel: 'Save',
            btnOKClass: 'btn-primary',
            buttons: [
                {
                    label: 'Cancel',
                    icon: 'fa fa-times fa-fw',
                    action: function (d) {
                        d.close();
                    }
                },
                {
                    label: 'Save',
                    icon: 'fa fa-save fa-fw',
                    cssClass: 'btn-primary',
                    autospin: true,
                    action: function (d) {
                        var wgnodes = _.map(d.getModalBody().find('.widget-list > tbody > tr'), function (el) {
                            el = $(el);
                            return {
                                id: el.attr('data-id'),
                                seq: el.attr('data-seq'),
                                title: el.attr('data-title'),
                                type_id: el.attr('data-type-id'),
                                type_name: el.attr('data-type-name'),
                                yaxis_title: el.attr('data-yaxis-title'),
                                yaxis_min: el.attr('data-yaxis-min'),
                                yaxis_max: el.attr('data-yaxis-max'),
                                data_suffix: el.attr('data-data-suffix'),
                                tooltip_suffix: el.attr('data-tooltip-suffix'),
                                height: el.attr('data-height'),
                                css_class: el.attr('css-class')
                            };
                        }, this);
                        console.log('widgetList : ' + JSON.stringify(wgnodes, null, '    '));

                        e.data.parent.setData('widgetList', wgnodes);
                        d.close();
                    }
                }
            ],
            onshow: function (d) {
                if (typeof e.data.byMenu === 'undefined') {
                    disableBackDrop(d);
                }
                // Load WGs data
                var wgdatas = e.data.parent.getData('widgetList');
                var tbody = d.getModalBody().find('.widget-list tbody');
                $(wgdatas).each(function (i, node) {
                    var row = $('<tr>' +
                            '<td class="sequence">' + node.seq + '</td>' +
                            '<td>' + node.title + '</td>' +
                            '<td>' + node.type_name + '</td>' +
                            '<td><button type="button" class="btn btn-sm btn-default btn-edit-widget"><i class="fa fa-edit"></i></button> ' +
                            '<button type="button" class="btn btn-sm btn-danger btn-delete-widget"><i class="fa fa-times"></i></button>' +
                            '</td></tr>');
                    row.attr('data-id', node.id);
                    row.attr('data-seq', node.seq);
                    row.attr('data-title', node.title);
                    row.attr('data-type-id', node.type_id);
                    row.attr('data-type-name', node.type_name);
                    row.attr('data-yaxis-title', node.yaxis_title);
                    row.attr('data-yaxis-min', node.yaxis_min);
                    row.attr('data-yaxis-max', node.yaxis_max);
                    row.attr('data-data-suffix', node.data_suffix);
                    row.attr('data-tooltip-suffix', node.tooltip_suffix);
                    row.attr('data-height', node.height);
                    row.attr('css-class', node.css_class);
                    tbody.append(row);
                    // Bind Button
                    row.find('.btn-delete-widget').click(function (e) {
                        fnDeleteWg(e, d.getModalBody(), $(this));
                    });
                    row.find('.btn-edit-widget').click(function (e) {
                        e.data = {parent: d};
                        WidgetItemEditor(e, true, $(this));
                    });
                });


            },
            onshown: function (d) {

                // Bind Buttons

                // Add Widget
                d.getModalBody().find('.addwidget').click({parent: d}, function (e) {
                    WidgetItemEditor(e);
                });
            }
        });
    });

    var fnDeleteWg = function (e, el, row) {
        row = row.closest('tr');
        BootstrapDialog.confirm({
            title: 'Confirmation',
            message: 'Delete this item?',
            type: BootstrapDialog.TYPE_WARNING,
            draggable: true,
            btnOKLabel: 'Delete',
            btnOKClass: 'btn-danger',
            //btnOKIcon: 'fa fa-times', // Not worked
            callback: function (r) {
                if (r) {
                    row.remove();
                    renumber_table(el.find('.widget-list'));
                }
            }
        });
    };

    var GriditemEditor = function (e, isEdit) {
        e.preventDefault();
        var dlgprop = {
            title: 'Add Grid item',
            btnAdd: 'Add item',
            btnAddIcon: 'fa fa-plus fa-fw'
        };
        if (isEdit) {
            dlgprop = {
                title: 'Edit Grid item',
                btnAdd: 'Save item',
                btnAddIcon: 'fa fa-save fa-fw'
            };
        }
        BootstrapDialog.show({
            nl2br: false, // For disable auto <br/>
            draggable: true,
            spinicon: 'fa fa-spinner fa-spin fa-fw',
            title: dlgprop.title,
            message: function () {
                var el = getGridItemDialog();
                return el;
            },
            closable: false,
            buttons: [
                {
                    label: 'Cancel',
                    icon: 'fa fa-times fa-fw',
                    action: function (d) {
                        d.close();
                    }
                },
                {
                    label: dlgprop.btnAdd,
                    icon: dlgprop.btnAddIcon,
                    cssClass: 'btn-primary',
                    autospin: true,
                    action: function (d) {
                        // Get Element body
                        var dlgbody = d.getModalBody();
                        // Get form data
                        var node = {};
                        node.id = dlgbody.find('[name=id]').val();
                        node.title = dlgbody.find('[name=title]').val();
                        node.icon = dlgbody.find('[name=header_icon]').val();
                        node.x = dlgbody.find('[name=x]').val();
                        node.y = dlgbody.find('[name=y]').val();
                        node.width = dlgbody.find('[name=width]').val();
                        node.height = dlgbody.find('[name=height]').val();
                        node.max_width = dlgbody.find('[name=max_width]').val();
                        node.max_height = dlgbody.find('[name=max_height]').val();
                        node.min_width = dlgbody.find('[name=min_width]').val();
                        node.min_height = dlgbody.find('[name=min_height]').val();
                        node.widget_alignment = dlgbody.find('[name=widget_alignment]:selected').val();
                        node.no_resize = true;
                        node.no_move = true;
                        if (dlgbody.find('[name=no_resize]').is(":checked"))
                        {
                            node.no_resize = false;
                        }

                        if (dlgbody.find('[name=no_move]').is(":checked"))
                        {
                            node.no_move = false;
                        }

                        // Put Widget List
                        node.widgetList = d.getData('widgetList');

                        // Clean Array obj
                        node = cleanObj(node);

                        // Log Node
                        console.log('Node serialized : ' + JSON.stringify(node, null, '    '));
                        var newel;
                        if (!isEdit) {
                            // Add widget
                            newel = getGridItemEl();
                            $('.grid-stack').data('gridstack').add_widget(newel, node, true);
                            loadWidgetPrototype(newel, node);
                        } else {
                            node.el = e.data.el;
                            $('.grid-stack').data('gridstack').update_widget(e.data.el, node);
                            loadWidgetPrototype(e.data.el, node);
                        }


                        // Done, close dialog
                        d.close();
                    }
                }
            ],
            onshow: function (d) {
                if (isEdit) {
                    //alert(e.data.test); // Accessing data from e
                    var dlgbody = d.getModalBody();
                    dlgbody.find('[name=id]').val(e.data.node.id);
                    dlgbody.find('[name=title]').val(e.data.node.title);
                    dlgbody.find('[name=header_icon]').val(e.data.node.icon);
                    dlgbody.find('[name=x]').val(e.data.node.x);
                    dlgbody.find('[name=y]').val(e.data.node.y);
                    dlgbody.find('[name=width]').val(e.data.node.width);
                    dlgbody.find('[name=height]').val(e.data.node.height);
                    dlgbody.find('[name=max_width]').val(e.data.node.max_width);
                    dlgbody.find('[name=max_height]').val(e.data.node.max_height);
                    dlgbody.find('[name=min_width]').val(e.data.node.min_width);
                    dlgbody.find('[name=min_height]').val(e.data.node.min_height);
                    dlgbody.find('[name=widget_alignment]:selected').val(e.data.node.widget_alignment);
                    dlgbody.find('[name=no_resize]').prop('checked', !e.data.node.no_resize);
                    dlgbody.find('[name=no_move]').prop('checked', !e.data.node.no_move);
                    d.setData('widgetList', e.data.node.widgetList);
                }
            },
            onshown: function (d) {
                d.getModalBody().find('.editwidget').click({parent: d}, WidgetEditor);
                d.getModalBody().find('[name=title]').focus();
            }
        });
    };

    var WidgetItemEditor = function (e, isEdit, row) {
        e.preventDefault();
        var dlgprop = {
            title: 'Add widget',
            btnAdd: 'Add item',
            btnAddIcon: 'fa fa-plus fa-fw'
        };
        if (isEdit) {
            dlgprop = {
                title: 'Edit widget',
                btnAdd: 'Save item',
                btnAddIcon: 'fa fa-save fa-fw'
            };
        }
        BootstrapDialog.show({
            nl2br: false, // For disable auto <br/>
            draggable: true,
            spinicon: 'fa fa-spinner fa-spin fa-fw',
            title: dlgprop.title,
            message: function () {
                if (!isEdit) {
                    return getWidgetItemEditDialog();
                } else {
                    row = row.closest('tr');
                    msg = getWidgetItemEditDialog();
                    msg.find('[name=id]').val(row.attr('data-id'));
                    msg.find('[name=seq]').val(row.attr('data-seq'));
                    msg.find('[name=title]').val(row.attr('data-title'));
                    msg.find('[name=type_id]').val(row.attr('data-type-id')); // NOT SURE !!
                    msg.find('[name=yaxis_title]').val(row.attr('data-yaxis-title'));
                    msg.find('[name=yaxis_min]').val(row.attr('data-yaxis-min'));
                    msg.find('[name=yaxis_max]').val(row.attr('data-yaxis-max'));
                    msg.find('[name=data_suffix]').val(row.attr('data-data-suffix'));
                    msg.find('[name=tooltip_suffix]').val(row.attr('data-tooltip-suffix'));
                    msg.find('[name=height]').val(row.attr('data-height'));
                    msg.find('[name=css_class]').val(row.attr('data-css-class'));

                }
                return msg;
            },
            closable: false,
            buttons: [
                {
                    label: 'Cancel',
                    icon: 'fa fa-times fa-fw',
                    action: function (d) {
                        d.close();
                    }
                },
                {
                    label: dlgprop.btnAdd,
                    icon: dlgprop.btnAddIcon,
                    cssClass: 'btn-primary',
                    autospin: true,
                    action: function (d) {
                        // Set widget data to save
                        var wgfrm = d.getModalBody().find('#formaddwidgetitem');
                        var wgel = $('<tr>' +
                                '<td class="sequence">' + wgfrm.find('[name=seq]').val() + '</td>' +
                                '<td>' + wgfrm.find('[name=title]').val() + '</td>' +
                                '<td>' + wgfrm.find('[name=type_id] :selected').text() + '</td>' +
                                '<td><button type="button" class="btn btn-sm btn-default btn-edit-widget"><i class="fa fa-edit"></i></button> ' +
                                '<button type="button" class="btn btn-sm btn-danger btn-delete-widget"><i class="fa fa-times"></i></button>' +
                                '</td></tr>');

                        wgel.attr('data-id', wgfrm.find('[name=id]').val());
                        wgel.attr('data-seq', wgfrm.find('[name=seq]').val());
                        wgel.attr('data-title', wgfrm.find('[name=title]').val());
                        wgel.attr('data-type-id', wgfrm.find('[name=type_id] :selected').val());
                        wgel.attr('data-type-name', wgfrm.find('[name=type_id] :selected').text());
                        wgel.attr('data-yaxis-title', wgfrm.find('[name=yaxis_title]').val());
                        wgel.attr('data-yaxis-min', wgfrm.find('[name=yaxis_min]').val());
                        wgel.attr('data-yaxis-max', wgfrm.find('[name=yaxis_max]').val());
                        wgel.attr('data-data-suffix', wgfrm.find('[name=data_suffix]').val());
                        wgel.attr('data-tooltip-suffix', wgfrm.find('[name=tooltip_suffix]').val());
                        wgel.attr('data-height', wgfrm.find('[name=height]').val());
                        wgel.attr('data-css-class', wgfrm.find('[name=css_class]').val());

                        // Send data to parent
                        if (!isEdit) {
                            e.data.parent.getModalBody().find('.widget-list tbody:last').append(wgel);
                            renumber_table(e.data.parent.getModalBody().find('.widget-list'));
                        } else {
                            e.data.parent.getModalBody().find('.widget-list tbody tr[data-id="' +
                                    wgfrm.find('[name=id]').val() + '"]').replaceWith(wgel);
                        }

                        // Bind Button
                        wgel.find('.btn-delete-widget').click(function (e) {
                            fnDeleteWg(e, d.getModalBody(), $(this));
                        });
                        wgel.find('.btn-edit-widget').click(function (e) {
                            e.data = {parent: d};
                            WidgetItemEditor(e, true, $(this));
                        });
                        // Done, close dialog
                        d.close();
                    }
                }
            ],
            onshow: function (d) {
                disableBackDrop(d);
            }
        });
    };

    // OnClick Add grid item to open dialog
    $('.add-grid-item').click(function (e) {
        GriditemEditor(e);
    });

    // Save button action
    $('.save-grid-item').click(function (e) {
        e.preventDefault();
        $(this).html('<i class="fa fa-spinner fa-spin fa-fw"></i> Saving...').attr('disabled', 'disabled');
        /*setTimeout(function (el) { // FOR TEST
         el.html('<i class="fa fa-check fa-fw"></i> Saved');
         }, 1500, $(this));*/
        gridfunc.save_grid();
    });

    // Gridstack
    var options = {
    };
    $('.grid-stack').gridstack(options);
    var counter = 9;
    var gridfunc = new function () {
        // Load JSON Serialized data
        /*
         this.serialized_data = [
         {id: 1, title: 'Main Widget', x: 0, y: 0, width: 12, height: 3, icon: 'fa-bar-chart-o'},
         {id: 2, title: 'Has Widgets', x: 0, y: 3, width: 2, height: 2, widgetList: [
         {
         id: "1",
         seq: "1",
         title: "New widget",
         type_id: "1",
         type_name: "Solid Gauge",
         yaxis_title: "12312",
         yaxis_min: "312",
         yaxis_max: "41324",
         data_suffix: "123",
         tooltip_suffix: "124",
         height: "12"
         }
         ]},
         {id: 3, x: 5, y: 3, width: 2, min_width: 3, height: 2},
         {id: 4, title: 'Extra content', x: 2, y: 5, width: 3, height: 2, content: '<h2>Hello World</h2><p>skawweffgdfsdfks;dkf;lskdjfpoisdupfosidfsdfsdfs;djpsldf</p>'}
         ];*/
        this.serialized_data = {};
        this.grid = $('.grid-stack').data('gridstack');
        this.load_grid = function () {
            this.grid.remove_all();
            var items = GridStackUI.Utils.sort(this.serialized_data);
            _.each(items, function (node) {
                var newel = getGridItemEl();
                this.grid.add_widget(newel, node);
                loadWidgetPrototype(newel, node);
            }, this);
        }.bind(this);
        
        // Save function  send by AJAX
        this.save_grid = function () {
            this.serialized_data = _.map($('.grid-stack > .grid-stack-item:visible'), function (el) {
                el = $(el);
                var node = el.data('_gridstack_node');
                return {
                    id: node.id,
                    x: node.x,
                    y: node.y,
                    width: node.width,
                    height: node.height,
                    no_resize: node.no_resize,
                    no_move: node.no_move,
                    max_width: node.max_width,
                    max_height: node.max_height,
                    min_width: node.min_width,
                    min_height: node.min_width,
                    locked: node.locked,
                    title: node.title,
                    icon: node.icon,
                    widgetList: node.widgetList
                };
            }, this);
            console.log(JSON.stringify(this.serialized_data, null, '    '));
            $.ajax({
                url: 'data.php',
                data: JSON.stringify(this.serialized_data), // Serialized : send as Request Payload
                contentType: "application/json; charset=utf-8",
                type: 'POST',
                cache: false,
                timeout: 10000,
                dataType: 'json', // IMPORTANT !!!
                error: function (rs, textStatus, jqXHR) {
                    $('.save-grid-item')
                            .addClass('btn-danger')
                            .removeClass('btn-success')
                            .html('<i class="fa fa-times fa-fw"></i> Save error !')
                            .removeAttr('disabled');
                    console.log("Error : " + rs.responseJSON.msg);
                    return true;
                },
                success: function (rs) {
                    if (!rs.error === true) {
                        $('.save-grid-item')
                                .addClass('btn-success')
                                .removeClass('btn-danger')
                                .html('<i class="fa fa-check fa-fw"></i> Saved')
                                .removeAttr('disabled');
                    } else {
                        $('.save-grid-item')
                                .addClass('btn-danger')
                                .removeClass('btn-success')
                                .html('<i class="fa fa-times fa-fw"></i> Save error !')
                                .removeAttr('disabled');
                        console.log("Error : " + rs.msg);
                    }
                }
            });
        }.bind(this);
        this.clear_grid = function () {
            this.grid.remove_all();
        }.bind(this);
        this.add_grid = function (e) {
            e.preventDefault();
            var $el = getGridItemEl();
            this.grid.add_widget($($el),
                    counter++, 0, 0, Math.floor(1 + 3 * Math.random()), Math.floor(1 + 3 * Math.random()), true);
        }.bind(this);
        $('#save-grid').click(this.save_grid);
        $('#load-grid').click(this.load_grid);
        $('#clear-grid').click(this.clear_grid);
        $('#add-grid').click(this.add_grid);
        this.load_grid();
    };
    $(document).on("click", ".remove-me", function (e) {
        e.preventDefault();
        var grid = $('.grid-stack').data('gridstack');
        grid.remove_widget($(this).parents('.grid-stack-item'));
    });

    // And now Load DATA
    $.ajax({
        url: 'data.php',
        contentType: "application/json; charset=utf-8",
        type: 'GET',
        cache: false,
        timeout: 10000,
        dataType: 'json', // IMPORTANT !!!
        error: function () {
            return true;
        },
        success: function (msg) {
            gridfunc.serialized_data = msg;
            gridfunc.load_grid();
        }
    });

});