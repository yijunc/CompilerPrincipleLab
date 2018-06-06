(function (window) {

    var c = {}; //code
    var config = {
        server: 'http://47.100.228.168:8080/',
        apiGate: 'compilerlab/'
        // server: 'http://localhost:8080/',
        //         // apiGate: ''
    };

    c.lex_analyze = function (raw_code, func) {
        var code_json = JSON.stringify({code:raw_code});
        console.log("code_json"+code_json);
        fetch("lex","analyze",code_json,func);
    };

    function fetch(moduleName, methodName, data, func) {

        if (typeof func == "undefined") {
            func = data;
            data = undefined;
        }

        $.ajax({
            url: config.server + config.apiGate + moduleName + "/" + methodName,
            // timeout: 10000,
            type:"post",
            method: 'post',
            dataType: "json",
            data: data,
            contentType: "application/json; charset=utf-8",
            success:func
            });
    };

    c.grammar_analyze = function (raw_code, func) {
        var code_json = JSON.stringify({code:raw_code});
        console.log("code_json"+code_json);
        fetch("grammar","analyze",code_json,func);
    };

    window.$client = c;

})(window);