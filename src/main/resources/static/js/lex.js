(function (window) {

    var c = {}; //code
    var config = {
        server: 'http://localhost:8080/',
        apiGate: ''
    };

    c.lex_analyze = function (raw_code, func) {
        var code_json = JSON.stringify({code:raw_code});
        alert("1"+code_json);
        send_json("lex","analyze",code_json,func);
    };

    function send_json(moduleName, methodName, data, func) {

        if (typeof func == "undefined") {
            func = data;
            data = undefined;
        }

        $.ajax({
            url: config.server + config.apiGate + moduleName + "/" + methodName,
            timeout: 10,
            method: 'post',
            dataType: "jsonp",
            data: data,
            jsonp:"callback",
            success:func
        });


        alert("2"+data.toString());
    };

    window.$client = c;

})(window);