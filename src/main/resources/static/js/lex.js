(function (window) {

    var c = {}; //code
    var config = {
        server: 'http://localhost:8080/',
        apiGate: ''
    };

    c.lex_analyze = function (raw_code, func) {
        var code_json = JSON.stringify({code:raw_code});
        console.log("code_json"+code_json);
        send_json("lex","analyze",code_json,func);
    };

    function send_json(moduleName, methodName, data, func) {

        if (typeof func == "undefined") {
            func = data;
            data = undefined;
        }

        $.ajax({
            url: config.server + config.apiGate + moduleName + "/" + methodName,
            timeout: 10000,
            type:"post",
            method: 'post',
            dataType: "json",
            data: data,
            contentType: "application/json; charset=utf-8",
            success:func
            });
    };

    window.$client = c;

})(window);