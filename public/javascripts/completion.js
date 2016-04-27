$("#movie").on("input", function() {
  $.ajax({
    url: 'http://www.omdbapi.com/',
    method: "get",
    dataType: "json",
    data: {
        "t": $("#movie").val(),
        "type": "movie",
    },
    success: function(response, status) {
      if (response.Response == "True") {
        var url = "http://www.imdb.com/title/" + response.imdbID;
        $("#movie_field .info").html("<a href='"+url+"'>"+response.Title+"</a>");
      } else if ($("#movie").val() != "") {
        $("#movie_field .info").html("No matching movie found!");
      } else {
        $("#movie_field .info").html("Required");
      }
    },
  });
});
