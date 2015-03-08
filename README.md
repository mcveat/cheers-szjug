Cheers
======

Cheers is an example Play! framework based  application developed for "O Play przy piwie" talk presented at [Szczecin Java User Group meetup on 16th March 2015](http://www.meetup.com/Szczecin-Java-Users-Group/events/220797721/).

Every commit lists the steps taken to reach particular state. Feel free to explore it.

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
5. **Build form for sending Cheers** _(check commit diff for details)_
