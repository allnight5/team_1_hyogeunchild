let main = {
    init: function () {
        let _this = this;
        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function () {
            _this.update();
        });

        $('#btn-delete').on('click', function () {
            _this.delete();
        });
        $('#save-comment').on('click',function(){
            _this.savecomment;
        });


    },
    save: function () {
        let data = {
            title: $('#title').val(),
            //author: $('#author').val(),
            content: $('#content').val()
        };

        const auth = getToken();

        $.ajax({
            type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function () {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    update: function () {
        let data = {
            title: $('#title').val(),
            author:$('#author').val(),
            content: $('#content').val()
        };
        const auth = getToken();

        let id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function () {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function () {
        let id = $('#id').val();
        let username = $('#author').val();

        const auth = getToken();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify({username: username}),
            beforeSend: function (xhr){
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function () {
            alert('글이 삭제되었습니다.');
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    },
    savecomment:function(){
        let data ={
            id :$('#id').val(),
            comment : $('#comment').val()
        };
        const auth = getToken();

        $.ajax({
            type: 'POST',
            url: '/api/v1/comment/save',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
            beforeSend: function (xhr){
                xhr.setRequestHeader("Authorization", auth);
            },
        }).done(function () {
            alert('댓글이 등록되었습니다.');
            window.location.reload();
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });

    }
};

main.init();



const href = location.href;
const queryString = href.substring(href.indexOf("?")+1)

if (queryString === 'error') {
    const errorDiv = document.getElementById('login-failed');
    errorDiv.style.display = 'block';
}

$(document).ready(function (){
    const auth = getToken();

    if(auth !==''){

        $('#logout').show();
        $('#save-post').show();
        $('#sign-text').hide();
        $('#login-text').hide();


    }else {
        $('#logout').hide();
        $('#save-post').show();
        $('#sign-text').show();
        $('#login-text').show();

    }
})
function  getToken() {
    let cName = 'Authorization' + '=';
    let cookieData = document.cookie;
    let cookie = cookieData.indexOf('Authorization');
    let auth = '';
    if(cookie !== -1){
        cookie += cName.length;
        let end = cookieData.indexOf(';', cookie);
        if(end === -1)end = cookieData.length;
        auth = cookieData.substring(cookie, end);
    }
    return auth;
}
/// 생각해볼 사항 ... 로그인 을 원래대로 되돌려보자 ~  뭔진 모르니까


function login() {

    let username = $('#login-id').val();
    let password = $('#login-password').val();

    if (username == '') {
        alert('ID를 입력해주세요');
        return;
    } else if(password== '') {
        alert('비밀번호를 입력해주세요');
        return;
    }

    $.ajax({
        type: "POST",
        url: `/users/login`,
        contentType: "application/json",
        data: JSON.stringify({username: username, password: password}),

        success: function (response, status, xhr) {
            if(response === 'success') {
                let host = window.location.host;
                let url = host + '/';

                document.cookie =
                    'Authorization' + '=' + xhr.getResponseHeader('Authorization') + ';path=/';
                window.location.href = 'http://' + url;
            } else {
                alert('로그인에 실패하셨습니다. 다시 로그인해 주세요.')
                window.location.reload();
            }
        }
    })
}



function logout() {
    // 토큰 값 ''으로 덮어쓰기
    document.cookie =
        'Authorization' + '=' + '' + ';path=/';
    window.location.reload();
}

function signup(){
    let username = $('#signup-id').val();
    let password = $('#signup-password').val();


    if (username == '') {
        alert('ID를 입력해주세요');
        return;
    } else if(password== '') {
        alert('비밀번호를 입력해주세요');
        return;
    }

    $.ajax({
        type: "POST",
        url: `/users/signup`,
        contentType: "application/json",
        data: JSON.stringify({username: username, password: password ,email: email}),

        success: function (response) {
            if(response === 'success') {
                let host = window.location.host;
                let url = host + '/users/login';
                window.location.href = 'http://' + url;
            } else {
                alert('조건만족실패')
                window.location.reload();
            }
        }
    })
}

function deleteUser(){
    let username = $('#signup-id').val();

    if(username == ''){
        alert('ID를입력 해주세요');
        return;
    }
    $.ajax({
        type: "DELETE",
        url : `/users/delete`,
        data : JSON.stringify({username:username}),
        success:function(reponse){
            if(reponse === 'success'){
                let host = winow.location.host;
                let url = host + '/users/login';
                window.location.href = 'http://' + url;
            }else{
                alert('아이디를 찾을수없습니다.')
            }
        }

    })

}