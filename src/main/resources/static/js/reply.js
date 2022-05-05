var main = {
    init : function() {
        var _this = this;
        $('#write-reply').on('click', function () {
            _this.save();
        });
    },

    save : function () {
        var id = $('#id').val();

        var data = {
            nickname: $('#nickname'),
            content: $('#content'),
            postId: $('#id')
        };

        $.ajax({
            type: 'POST',
            url: '/posts/'+id+'reply',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.href = '/posts/'+id;
        }).fail(function (error) {
            alert(JSON.stringify(error))
        });
    }
};

main.init();