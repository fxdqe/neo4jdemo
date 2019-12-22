$(function () {

  /**
   * Some examples of how to use features.
   *
   **/

  var SohoExamle = {
    Message: {
      add: function (message, type) {
        var chat_body = $('.layout .content .chat .chat-body');
        if (chat_body.length > 0) {

          type = type ? type : '';

          if (type != 'outgoing-message') {
            $.ajax
            ({

                url: "http://localhost:8080/question/answer/" + message,
                dataType: "json",
                type: "get",
                async: "true",
                success: function (data) {


                  message = data.result.toString();

                  let date = new Date();
                  hi = dateFormat("YYYY-mm-dd HH:MM", date),

                    $('.layout .content .chat .chat-body .messages').append(
                      `<div class="message-item ` + type + `">
                        <div class="message-avatar">
                            <figure class="avatar">
                                <img src="./dist/media/img/` + ('cortana.jpg') + `" class="rounded-circle">
                            </figure>
                            <div>
                                <h5>` + (type == 'outgoing-message' ? 'User sama' : 'Cortana') + `</h5>
                                <div class="time">` + hi + ` ` + ('') + `</div>
                            </div>  
<!-- 添加变量的神秘语法-->
                        </div>
                        <div class="message-content">
                            ` + message + ` 
                        </div>
                    </div>`);

                },
                error: function () {

                  message = "与服务器连接异常。";

                  let date = new Date();
                  hi = dateFormat("YYYY-mm-dd HH:MM", date),

                    $('.layout .content .chat .chat-body .messages').append(
                      `<div class="message-item ` + type + `">
                        <div class="message-avatar">
                            <figure class="avatar">
                                <img src="./dist/media/img/` + ('cortana.jpg') + `" class="rounded-circle">
                            </figure>
                            <div>
                                <h5>` + (type == 'outgoing-message' ? 'User sama' : 'Cortana') + `</h5>
                                <div class="time">` + hi + ` ` + ('') + `</div>
                            </div>
                        </div>
                        <div class="message-content">
                            ` + message + `
                        </div>
                    </div>`);
                },
              }
            );
          } else {

            let date = new Date();
            hi = dateFormat("YYYY-mm-dd HH:MM", date),

              $('.layout .content .chat .chat-body .messages').append(
                `<div class="message-item ` + type + `">
                        <div class="message-avatar">
                            <figure class="avatar">
                                <img src="./dist/media/img/` + ('User.jpg') + `" class="rounded-circle">
                            </figure>
                            <div>
                                <h5>` + ('User sama') + `</h5>
                                <div class="time">` + hi + ` ` + ('<i class="ti-check"></i>') + `</div>
                            </div>
                        </div>
                        <div class="message-content">
                            ` + message + `
                        </div>
                    </div>`);
          }


          setTimeout(function () {
            chat_body.scrollTop(chat_body.get(0).scrollHeight, -1).niceScroll({
              cursorcolor: 'rgba(66, 66, 66, 0.20)',
              cursorwidth: "4px",
              cursorborder: '0px'
            }).resize();
          }, 200);
        }
      }
    }
  };

  setTimeout(function () {

    $('#pageTour').modal('show');
  }, 1000);

  $(document).on('submit', '.layout .content .chat .chat-footer form', function (e) {
    e.preventDefault();

    var input = $(this).find('input[type=text]');
    var message = input.val();

    message = $.trim(message);

    if (message) {
      SohoExamle.Message.add(message, 'outgoing-message');
      input.val('');

      setTimeout(function () {
        SohoExamle.Message.add(message);
      }, 1000);
      var div = document.getElementById("chat-body");
      div.scrollTop = div.scrollHeight;
    } else {
      input.focus();
    }
  });

  $(document).on('click', '.layout .content .sidebar-group .sidebar .list-group-item', function () {
    if (jQuery.browser.mobile) {
      $(this).closest('.sidebar-group').removeClass('mobile-open');
    }
  });

});

$(document).ready(function () {


  message = "晚上好，我是您聪明能干的助手。";

  let date = new Date();
  hi = dateFormat("YYYY-mm-dd HH:MM", date);

  $('.layout .content .chat .chat-body .messages').append(
    `<div class="message-item ` + "" + `">
                        <div class="message-avatar">
                            <figure class="avatar">
                                <img src="./dist/media/img/` + ('cortana.jpg') + `" class="rounded-circle">
                            </figure>
                            <div>
                                <h5>` + ('Cortana') + `</h5>
                                <div class="time">` + hi + ` ` + ('') + `</div>
                            </div>
                        </div>
                        <div class="message-content">
                            ` + message + `
                        </div>
                    </div>`);
});

function dateFormat(fmt, date) {
  let ret;
  let opt = {
    "Y+": date.getFullYear().toString(),        // 年
    "m+": (date.getMonth() + 1).toString(),     // 月
    "d+": date.getDate().toString(),            // 日
    "H+": date.getHours().toString(),           // 时
    "M+": date.getMinutes().toString(),         // 分
    "S+": date.getSeconds().toString()          // 秒
    // 有其他格式化字符需求可以继续添加，必须转化成字符串=5
  };
  for (let k in opt) {
    ret = new RegExp("(" + k + ")").exec(fmt);
    if (ret) {
      fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
    }
  }
  return fmt;
}
