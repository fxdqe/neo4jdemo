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
          // var str = document.getElementById("text").value;
          $.ajax
          ({
            // url: "https://restapi.amap.com/v3/geocode/geo",
            url: "http://localhost:8081/author/getAuthor/" + message,
            dataType: "json",
            type: "get",
            async: "true",
            // data: {
            //     address: str,
            //     key: "7486e10d3ca83a934438176cf941df0c",
            // },

            success: function (data) {
              // alert(data.geocodes[0].formatted_address+"经纬度："+data.geocodes[0].location);

              message = type == 'outgoing-message' ? message : "yeah, i got you.";

              let date = new Date();
              hi = dateFormat("YYYY-mm-dd HH:MM", date),

                $('.layout .content .chat .chat-body .messages').append(
                  `<div class="message-item ` + type + `">
                        <div class="message-avatar">
                            <figure class="avatar">
                                <img src="./dist/media/img/` + (type == 'outgoing-message' ? 'women_avatar5.jpg' : 'man_avatar3.jpg') + `" class="rounded-circle">
                            </figure>
                            <div>
                                <h5>` + (type == 'outgoing-message' ? 'Mirabelle Tow' : 'Byrom Guittet') + `</h5>
                                <div class="time">` + hi + ` ` + (type == 'outgoing-message' ? '<i class="ti-check"></i>' : '') + `</div>
                            </div>  
<!-- 添加变量的神秘语法-->
                        </div>
                        <div class="message-content">
                            ` + message + ` 
                        </div>
                    </div>`);

            },
            error: function (data) {

              message = type == 'outgoing-message' ? message : "I did not understand what you said!";

              let date = new Date();
              hi = dateFormat("YYYY-mm-dd HH:MM", date),

                $('.layout .content .chat .chat-body .messages').append(
                  `<div class="message-item ` + type + `">
                        <div class="message-avatar">
                            <figure class="avatar">
                                <img src="./dist/media/img/` + (type == 'outgoing-message' ? 'women_avatar5.jpg' : 'man_avatar3.jpg') + `" class="rounded-circle">
                            </figure>
                            <div>
                                <h5>` + (type == 'outgoing-message' ? 'Mirabelle Tow' : 'Byrom Guittet') + `</h5>
                                <div class="time">` + hi + ` ` + (type == 'outgoing-message' ? '<i class="ti-check"></i>' : '') + `</div>
                            </div>
                        </div>
                        <div class="message-content">
                            ` + message + `
                        </div>
                    </div>`);
            },
          });


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
    // $('#disconnected').modal('show');
    // $('#call').modal('show');
    // $('#videoCall').modal('show');
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

// $(document).ready(function(){
//  // let date = new Date(),
//  //document.getElementById("time_d").innerHTML = dateFormat("YYYY-mm-dd HH:MM", date) ;
//
//   message = "早上好先生，我是您聪明能干的助手。";
//
//   let date = new Date();
//   hi = dateFormat("YYYY-mm-dd HH:MM", date);
//
//     $('.layout .content .chat .chat-body .messages').append(
//       `<div class="message-item ` + "" + `">
//                         <div class="message-avatar">
//                             <figure class="avatar">
//                                 <img src="./dist/media/img/` + ( 'man_avatar3.jpg') + `" class="rounded-circle">
//                             </figure>
//                             <div>
//                                 <h5>` + ( 'Byrom Guittet') + `</h5>
//                                 <div class="time">`+hi+` ` + ( '') + `</div>
//                             </div>
//                         </div>
//                         <div class="message-content">
//                             ` + message + `
//                         </div>
//                     </div>`);
//
// });

function dateFormat(fmt, date) {
  let ret;
  let opt = {
    "Y+": date.getFullYear().toString(),        // 年
    "m+": (date.getMonth() + 1).toString(),     // 月
    "d+": date.getDate().toString(),            // 日
    "H+": date.getHours().toString(),           // 时
    "M+": date.getMinutes().toString(),         // 分
    "S+": date.getSeconds().toString()          // 秒
    // 有其他格式化字符需求可以继续添加，必须转化成字符串
  };
  for (let k in opt) {
    ret = new RegExp("(" + k + ")").exec(fmt);
    if (ret) {
      fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
    }
  }
  return fmt;
}
