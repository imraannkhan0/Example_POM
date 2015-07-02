package org.example.test.common;

public enum RENDERINGDRIVER {
    Chrome(""),
    Firefox(""),
    IE(""),
    Safari(""),
    Opera(""),
    OperaMini("Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 820)"),
    OperaMobile("Opera/12.02 (Android 4.1; Linux; Opera Mobi/ADR-1111101157; U; en-US) Presto/2.9.201 Version/12.02"),
    Stock(""),
    MobileWebAndroid("MyntraRetailAndroid/1.3.5-QA3-Dev (Phone, 480dpi) Mozilla/5.0 (Linux; U; Android 4.3; en-us; SM-N900T Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30"),
    MobileWebIOS("MyntraRetailiPhone/1.0.0 (iPhone Simulator, 320, 568) Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4"),
    MobileWebWindows("MyntraRetailiWindowsPhone/1.0.0 (iPhone Simulator, 320, 568) Mozilla/5.0 (compatible; MSIE 10.0; Windows Phone 8.0; Trident/6.0; IEMobile/10.0; ARM; Touch; NOKIA; Lumia 820)"),
    MobileChrome("Mozilla/5.0 (Linux; Android 4.0.4; SGH-I777 Build/Task650 & Ktoonsez AOKP) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19 "),
    MobileChrome1("Mozilla/5.0 (BB10; Touch) AppleWebKit/537.10+ (KHTML, like Gecko) Version/10.0.9.2372 Mobile Safari/537.10+"),
    MobileSafari("Mozilla/5.0 (iPhone; CPU iPhone OS 8_0 like Mac OS X) AppleWebKit/600.1.3 (KHTML, like Gecko) Version/8.0 Mobile/12A4345d Safari/600.1.4"),
    MobileFirefox("Mozilla/5.0 (Linux; Android 4.4.4; en-us; Nexus 5 Build/JOP40D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2307.2 Mobile Safari/537.36"),
    IEMobile("IEMobile/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML; like Gecko) Version/4.0 Mobile/7A341 Safari/528.16"),
    AppAndroid(""),
    AppSelendroid(""),
    AppIOS(""),
    AppWindows(""),
    PhantomJs(""),
    MobileAppWebView("MyntraRetailAndroid/1.3.5-QA3-Dev (Phone, 480dpi)");
    public String userAgent;

    private RENDERINGDRIVER(String userAgent) {
        this.userAgent = userAgent;
    }
}
