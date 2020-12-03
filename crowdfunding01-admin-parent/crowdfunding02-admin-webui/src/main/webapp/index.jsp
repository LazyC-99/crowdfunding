<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/">
    <script type="text/javascript" src="jquery/jquery-3.5.1.js"></script>
    <script>
        $(function () {
            $("#btn").click(function () {
                $.ajax({
                    url:"com.lzc.crowd.test/ssm.html",
                    success:function (result) {
                        console.log(result)
                    }
                })

            })
        })
    </script>
</head>
<body>
    <a href="com.lzc.crowd.test/ssm.html">com.lzc.crowd.test</a>
    <button id="btn">Ajax</button>
</body>
</html>
