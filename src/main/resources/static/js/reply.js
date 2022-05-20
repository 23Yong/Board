var main = {
    init : function() {
        var _this = this;
        $('#write-reply').on('click', function () {
            _this.save();
        });

        document.querySelectorAll('#update-reply').forEach(function (item) {
            item.addEventListener('click', function () {
                const form = this.closest('form');
                _this.update(form);
            });
        });

        document.querySelectorAll('.delBtn').forEach(function (item) {
            item.addEventListener('click', function () {
                const form = this.closest('form');
                _this.delete(form);
            })
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