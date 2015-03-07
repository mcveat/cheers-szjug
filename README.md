Cheers
======

Cheers is an example Play! framework based  application developed for "O Play przy piwie" talk presented at [Szczecin Java User Group meetup on 16th March 2015](http://www.meetup.com/Szczecin-Java-Users-Group/events/220797721/).

Every commit lists the steps taken to reach particular state. Feel free to explore it.

Steps done so far
=================

1. **Initial project structure setup**
  * Install `activator` tool as described on [Play! framework installation webpage](https://www.playframework.com/documentation/2.3.x/Installing)
  * Create project structure by executing `activator new cheers play-scala` command. _To see a list of available project templates you may use `activator list-templates` command too._
  * Project structure is created in directory `cheers`. Execute `cd cheers` to get there.
  * After executing `activator` command you're taken to interactive console of your project. Try executing commands like `compile`, `test` or `run`. Last one launches your application. You may reach it under [http://localhost:9000/](http://localhost:9000/). Every time you make a change, it will be reflected after reloading the webpage. Try it!
