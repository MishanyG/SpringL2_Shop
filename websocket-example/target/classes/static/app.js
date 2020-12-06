var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if(connected) {
        $("#conversation").show();
    } else {
        $("#conversation").hide();
    }
    $("#greetings").html();
}

function connect() {
    var socket = new SockJS("/example");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log("Connected: " + frame);

        stompClient.subscribe('/topic', function (message) {
            showGreetings(message.body)
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);

    console.log("Disconnected")
}

function sendName() {
    stompClient.send("/topic", {}, JSON.stringify({'name': $("#name").val()}))
}

function showGreetings(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>")
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });

    $("#connect").click(function () { connect() });
    $("#disconnect").click(function () { disconnect() });
    $("#send").click(function () { sendName() });
})
