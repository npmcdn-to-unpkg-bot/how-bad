$("#movie").on("input", function() {
  $.ajax({
    url: 'http://www.omdbapi.com/',
    method: "get",
    dataType: "json",
    data: {
        "t": $("#movie").val(),
    },
    success: function(response, status) {
      if (response.Response == "True") {
        $("#movie_field .info").html(response.Title);
      } else {
        $("#movie_field .info").html("");
      }
    },
  });
});
