<#include "modules/common/og.common.header.ftl">
<title>OpenGamma</title>
${ogStyle.print('og_all.css', 'all',false)}
</head>
<body>
<div class="OG-Openfin">
    <form>
        <div class="og-back"></div>
        <div class="og-forward"></div>
        <input class="og-url" value="" type="text" />
    </form>
</div>
<div class="OG-layout-admin-container">
  <div class="ui-layout-center"></div>
  <div class="ui-layout-north"><#include "modules/common/og.common.masthead.ftl"></div>
</div>
<!--[if IE]>${ogScript.print('ie.js',false)}<![endif]-->
${ogScript.print('og_common.js', false)}
${ogScript.print('og_analytics.js',false)}
</body>
</html>