# How bad

## Introduction
The goal of this project is to create a movie rating website. Users can review
any movie by giving it a score and optionally some comments. The movie
information is obtained from a web API such as [OMDb][1]. Users can also rate
movie reviews made by other users. This allows distinguishing good-quality
reviews programmatically, so they can e.g. be shown on the home page of the
website.

The website will be implemented using the [Scala][2] programming language and
the [Play][3] web framework. The finished product will be hosted on [Heroku][4],
using [PostgreSQL][5] as its database management system.

## Overview
![use case diagram](https://cloud.githubusercontent.com/assets/7543552/13931049/3b9d5a1e-efaa-11e5-9efa-fe0dc524d2c2.png)

### Actors
__Anonymous users__ are the least privileged actors. They're viewers of the
website that are currently not logged in to the service. They can only do
passive things like browse reviews and movies.

__Logged-in users__ are actors that are currently logged in to the service. They
can do everything anonymous users can, as well as create and modify reviews.
However, they can only modify and delete reviews they created themselves.

__Moderators__ are the most privileged actors. They can view, modify, and delete
any content. This is useful when something inappropriate is posted to the
service that needs to be removed.


[1]: http://www.omdbapi.com
[2]: http://www.scala-lang.org
[3]: https://www.playframework.com
[4]: https://www.heroku.com
[5]: http://www.postgresql.org
