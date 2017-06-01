var common = {};

// AJAX同一请求方法
common.ajaxRequest = function (opt) {
    var o = $.extend({
        type: 'POST',
        url: '',
        data: null,
        async: true,
        success: null,
        error: null,
        message: '请求成功'
    }, opt || {});
    $.ajax({
        type: o.type,
        url: o.url,
        data: o.data,
        async: o.async,
        success: function (data) {
            if (data == null) {
                return;
            }
            if (data.status == 1) {
                if ($.isEmptyObject(data.data) == true) {
                    if (o.message != null) {
                    }
                }
                if (o.success != null && typeof(o.success) == "function") {
                    o.success(data.data);
                }
            } else if (data.status == -1) {
            } else if (data.status == -2) {
            } else if (data.status == -3) {
                window.location.href = '/logout';
            } else if (data.status == -4) {
                window.location.href = '/logout';
            } else if (data.status == -5) {
                window.location.href = '/logout';
            } else {
            }
        },
        error: o.error
    });
};

common.enterFocus = function (id) {
    if (event.keyCode == 13) {
        $(id).focus();
    }
};

$.fn.serializeJson = function () {
    var serializeObj = {};
    var array = [];
    if (this.is('form')) {
        array = this.serializeArray();
    } else {
        var _obj;
        this.find('[name]').each(function () {
            _obj = {};
            _obj.name = $(this).attr('name');
            _obj.value = $(this).val();
            array.push(_obj);
        });
    }
    $(array).each(function () {
        if (serializeObj[this.name]) {
            if ($.isArray(serializeObj[this.name])) {
                if (this.value != '') {
                    serializeObj[this.name].push(this.value);
                }
            } else {
                if (this.value != '') {
                    serializeObj[this.name] = [serializeObj[this.name], this.value];
                }
            }
        } else {
            if (this.value != '') {
                serializeObj[this.name] = this.value;
            }
        }
    });
    return serializeObj;
};