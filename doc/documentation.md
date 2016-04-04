# How bad

By [Emil Laine](https://github.com/emlai) and [Tuure Piitulainen](https://github.com/tuutuu)

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

### Use cases
Use case: Create review

1. Logged-in user searches for a film title
2. Logged-in user finds a film they wish to review
3. Logged-in user gives a numerical rating to film
4. Logged-in user may give a reason for said rating

Use case: Rate review

1. Logged-in user selects a review under a film title
2. Logged-in user creates a response to said review
3. Response appears under review

Use case: Modify own review

1. Logged-in user accesses their own profile
2. Logged-in user searches from a list of their own reviews
3. Logged-in user chooses a review
4. Logged-in user modifies review
5. Modifications on review are saved

Use case: Delete own review

1. Logged-in user accesses their own profile
2. Logged-in user searches from a list of their own reviews
3. Logged-in user chooses a review
4. Logged-in user deletes review
5. Review is removed from the database

Use case: Browse reviews

1. Any user searches for a film title
2. Any user chooses a film
3. Reviews for said film are shown

Use case: Modify any review

1. Moderator searches for a review by user
2. Moderator chooses a review
3. Moderator modifies review
4. Modifications on review are saved

Use case: Delete any review

1. Moderator searches for a review by user
2. Moderator chooses a review
3. Moderator deletes review
4. Review is removed from public access

### Conceptual diagram
![conceptual diagram](https://cloud.githubusercontent.com/assets/7543552/14258557/9075f0da-faab-11e5-8fd2-5267bd3935ea.png)

### Database diagram
![database diagram](https://cloud.githubusercontent.com/assets/7543552/14261961/cdb09bfc-fabb-11e5-8a50-1eff5c2284f4.png)

All SQL code is located in [`conf/evolutions/default`](/conf/evolutions/default).

[1]: http://www.omdbapi.com
[2]: http://www.scala-lang.org
[3]: https://www.playframework.com
[4]: https://www.heroku.com
[5]: http://www.postgresql.org
