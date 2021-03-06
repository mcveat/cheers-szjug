Cheers
======

Cheers is an example Play! framework based  application developed for "O Play przy piwie" talk presented at [Szczecin Java User Group meetup on 16th March 2015](http://www.meetup.com/Szczecin-Java-Users-Group/events/220797721/).

Every tagged commit lists the steps taken to reach particular state. Feel free to explore it.

You can see it running at [http://cheers-szjug.herokuapp.com/](http://cheers-szjug.herokuapp.com/).

Want it to? [![Deploy](https://www.herokucdn.com/deploy/button.png)](https://heroku.com/deploy)

Steps done so far
=================

1. Initial project structure setup
  * Install `activator` tool as described on [Play! framework installation webpage](https://www.playframework.com/documentation/2.3.x/Installing)
  * Create project structure by executing `activator new cheers play-scala` command. _To see a list of available project templates you may use `activator list-templates` command too._
  * Project structure is created in directory `cheers`. Execute `cd cheers` to get there.
  * After executing `activator` command you're taken to interactive console of your project. Try executing commands like `compile`, `test` or `run`. Last one launches your application. You may reach it under [http://localhost:9000/](http://localhost:9000/). Every time you make a change, it will be reflected after reloading the webpage. Try it!
2. Remove files unnecessary for this project _(optional step)_
  * Remove `activator` and `activator-launch-*.jar` as we're using globaly installed tool.
3. Update html template files
  * Let's remove `public/javascripts/hello.js` file. We're going to use coffescript. We have to remove line including it from `app/views/main.scala.html` file. Look for `<script src="@routes.Assets.at("javascripts/hello.js")" type="text/javascript"></script>`.
  * Update `index.scala.html` file:
    * Change the title to `Cheers`: `@main("Cheers")`
    * Replace welcome message with header
    * Update tests in `/test` directory
4. Get jQuery and Foundation framework from webjars
  * Add `"org.webjars" % "foundation" % "5.5.1"` and `"org.webjars" % "jquery" % "2.1.3"`
  * Include libraries in `main.scala.html`
    * `<link rel="stylesheet" href="@routes.Assets.at("lib/foundation/css/foundation.min.css")"/>`
    * `<script src="@routes.Assets.at("lib/jquery/jquery.min.js")"></script>`
  * In the play console execute `reload` command
5. Build form for sending Cheers _(check commit diff for details)_
6. Introduce endpoint exposing websocket
  * Put line `GET /socket controllers.Application.socket` in the `conf/routes` file
  * Consult changes in `app/controllers/Application.scala`
    * `Request` case class defines the structure of expected client request
    * `Cheer` case class defines the object sent to clients connected to websocket
    * Pair of format definitions allow us to transform between json and case class representations
    * `socket` method defines mechanics of the websocket: whenever json value is sent to the socket, it is read as `Request` instance, transformed to `Cheer` instance and then sent back to the socket in it's json representation
7. Wire web client with websocket
  * Put container for cheers in `app/views/index.html`: `<div id="cheers-list"></div>`
  * Consult `app/assets/js/app.coffee`:
    * `socket = new WebSocket('ws:/localhost:9000/socket')` opens socket on the endpoint defined in the previous step
    * `socket.onmessage` method is executed every time new message appears in the socket, building small html snippet out of it and prepending it to the container div element
    * `$('#send').click` is executed on `Send` button click, reading the form, building message and sending it throught the socket
  * Include script in `app/views/main.html` : `<script src="@routes.Assets.at("js/app.js")"></script>`
  * Consult `public/stylesheets/main.css` for changes in the cheer display
8. Introduce persistence of cheers
  * Import [Reactive Mongo](http://reactivemongo.org) [Play! plugin](https://github.com/ReactiveMongo/Play-ReactiveMongo) by adding `"org.reactivemongo" %% "play2-reactivemongo" % "0.10.5.0.akka23"` to `libraryDependencies` in `build.sbt`
  * Reload project executing `reload` in application console
  * Enable plugin by creating `conf/play.plugins` file and putting `1100:play.modules.reactivemongo.ReactiveMongoPlugin` there
  * Configure database location by putting `mongodb.uri = "mongodb://localhost:27017/cheers"` in `conf/application.conf`
  * Consult changes in `app/controllers/Application.scala`:
    * `import play.api.Play.current` brings current application context in scope, this is the plugin framework requirement
    * Code below:
      ```scala
      lazy val collection = ReactiveMongoPlugin.db.collection[JSONCollection]("cheers")
      collection.convertToCapped(100l, None)
      ```
      connects to collection, creating it if it doesn't exist, and ensures it is capped
    * instead of being sent back to the socket, incoming messages are persisted in the database
    * output of socket is now based on tailable cursor over cheers collection
9. Deploy on heroku _(optional step)_
  * propagate implicit request from controller into templates (check `app/controllers/Application.scala`, `app/views/index.scala.html` and `app/views/main.scala.html`)
  * use built-in helper to exposing socket endpoint as a javascipt object
    ```scala
    @helper.javascriptRouter("jsRoutes")(
      routes.javascript.Application.socket
    )
    ```
  * in `app/js/app.coffee` change socket enpoint to `jsRoutes.controllers.Application.socket().webSocketURL()`
  * create `Procfile` with content: `web: target/universal/stage/bin/cheers -Dhttp.port=$PORT -Dmongodb.uri=${MONGOLAB_URI} -Denv=prod`
  * create your application on heroku and add mongolab sandbox addon to it
  * execute `heroku git:remote -a <name of the app>` in the project root
  * execute `git push heroku master`
