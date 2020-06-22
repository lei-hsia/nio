var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

// 客户端访问服务端的: endpoint, prefix, controllerURL规则
function connect() {
    /* public void registerStompEndpoints(StompEndpointRegistry registry) {
            registry.addEndpoint("/lubanws").withSockJS();
        }*/
    var socket = new SockJS('/lubanws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);

        // 客户端接收服务端回调消息(服务端限制范围内的): 这还不是服务端向客户端发送的过程，只是回调而已
        // @SendTo("/topic/message")
        stompClient.subscribe('/topic/message', function (data) {
            console.log(data);
            // showGreeting(JSON.parse(greeting.body).content);
        });

        //
        stompClient.subscribe('/user/lei/p2p', function (data) {
            console.log(data);
            // showGreeting(JSON.parse(greeting.body).content);
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/someprefix/content", {}, JSON.stringify({'name': $("#name").val()}));
    // stompClient.send("/someprefix/content", "testContent");
}

function showGreeting(message) {
    $("#greetings").append("<tr><td>" + message + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});

