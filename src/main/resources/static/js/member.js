var main = {
    init: function () {
        var _this = this;

        $('#update-member').on('click', function () {
            _this.updateMember();
        });

        $('#update-password').on('click', function () {
            _this.updatePassword();
        })
    },
    updateMember: function() {
      var data = {
          nickname: $('#nickname').val()
      };

      var loginId = $('#loginId').val();

      $.ajax({
          type: 'PUT',
          url: '/api/members/' + loginId,
          dataType: 'json',
          contentType:'application/json; charset=utf-8',
          data: JSON.stringify(data)
      }).done(function () {
          alert('정상적으로 수정되었습니다.');
          window.location.href = '/members/' + loginId + '/myPage';
      }).fail(function (error) {
          alert(JSON.stringify(error));
      })
    },  // update member
    updatePassword: function () {
        var data = {
            prevPassword: $('#prevPassword').val(),
            afterPassword: $('#afterPassword').val()
        };

        var loginId = $('#loginId').val();

        $.ajax({
            type: 'PUT',
            url: '/api/members/' + loginId + '/password',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('정상적으로 수정되었습니다.');
            window.location.href = '/members/' + loginId + '/myPage';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        })
    }   // update password
}

main.init();