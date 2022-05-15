var main = {
    init : function() {
        const _this = this;

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
        const data = {
            content: $('#content').val()
        };

        const id = $('#id').val();

        $.ajax({
            type: 'POST',
            url: '/api/posts/'+id+'/reply',
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function () {
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }, // save
    update : function (form) {
        const id = $('#id').val();
        const data = {
            id: form.querySelector('#replyId').value,
            content: form.querySelector('#reply-content').value
        }

        if (!data.content || data.content.trim() === "") {
            alert("공백 또는 입력하지 않은 부분이 있습니다.");
            return false;
        }
        const con_check = confirm("수정하시겠습니까?");

        if (con_check === true) {
            $.ajax({
                type: 'PUT',
                url: '/api/posts/' + id + '/reply',
                dataType: 'JSON',
                contentType: 'application/json; charset=utf-8',
                data: JSON.stringify(data)
            }).done(function () {
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }, // update
    delete : function (form) {
        const id = $('#id').val();

        const data = {
            id: form.querySelector('#btn-replyId').value
        };

        const con_check = confirm("삭제하시겠습니까?");

        if (con_check === true) {
            $.ajax({
                type: 'DELETE',
                url: '/api/posts/' + id + '/reply',
                dataType: 'json',
                contentType: 'application/json; charset=UTF-8',
                data: JSON.stringify(data)
            }).done(function () {
                window.location.reload();
            }).fail(function (error) {
                alert(JSON.stringify(error));
            });
        }
    }   // delete
};

main.init();