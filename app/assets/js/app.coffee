$ ->
  socket = new WebSocket(jsRoutes.controllers.Application.socket().webSocketURL())
  socket.onmessage = (event) ->
    cheer = JSON.parse(event.data)
    $('#cheers-list').prepend "
      <div class=\"row\">
        <div class=\"small-2 columns\">
          <img src=\"http://www.gravatar.com/avatar/#{cheer.md5}\">
        </div>
        <div class=\"small-10 columns panel callout radius\">
          #{cheer.text}
        </div>
      </div>
    "
  $('#send').click ->
    email = $('#email').val()
    text = $('#text').val()
    if !email?.length || !text?.length then return
    cheer =
      email : email
      text : text
    socket.send JSON.stringify cheer

