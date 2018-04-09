var gtmapCA = (function(windows,$,undefined){
    //数字证书
    var _certificate = null;
    //数字证书控件
    var _caOcx = null;

    var _certId = null;
    /**
     * 初始化CA控件
     * @param  caObj CA active控件对象
     * @returns {*}
     */
    var _initializeCertificate=function(caObj){
        _caOcx = caObj;
    };
    /**
     * 根据id获取证书
     * @param id 证书Id
     * @returns {*}
     */
    var _getCertificate=function(id){
        try{
            if(_certificate!=null&&_certificate!='')
                return;

            if((id==null||id=='')&&(_certId==null||_certId==''))
                id=_caOcx.SelectCertificateDialog();
            else if((id==null||id=='')&&(_certId!=null&&_certId!=''))
                id = _certId;
            _certificate = _caOcx.GetCertificate(id);
            _certId = id;
            _caOcx.PreferredCertificate =_certId;
        }catch(e){

        }
    }
    /**
     * 检查能否获取到证书
     * @returns {boolean}
     */
    var _checkValidCertificate=function(){
        _getCertificate(_certId);
        if(_certificate!=null){
            var issuer = _certificate.Issuer;
            if(issuer!=null&&issuer.indexOf('江苏省电子商务证书认证中心有限责任公司')!=-1)
                return true;
            else
                alert('当前数字证书颁发单位不正确！');
        }else
            alert('证书信息未获取到！');
        return false;
    };

    /**
     * 获取证书指纹
     * @returns {*}
     */
    var _getCertificateThumbprint=function(){
        _getCertificate(_certId);
        return _certificate.ThumbprintSHA1;
    };

    /**
     * 获取证书启用时间
     */
    var _getCertificateNotBeforeSystemTime=function(){
        _getCertificate(_certId);
        return _certificate.NotBeforeSystemTime;
    };
    /**
     * 获取证书失效时间
     * @returns {*}
     */
    var _getCertificateNotAfterSystemTime=function(){
        _getCertificate(_certId);
        return _certificate.NotAfterSystemTime;
    };
    /**
     * 获取证书内容
     * @returns {*}
     */
    var _getCertificateContent=function(){
        _getCertificate(_certId);
        return _certificate.Content;
    };

    /**
     * 进行数字签名
     * @param input
     * @param model
     * @returns {*}
     */
    var _signContent=function(input,model){
        if(input==null||input==''){
            alert('需数字签名的内容不能为空。');
            return null;
        }
        try{
            var result;
            if(model==1){
                result= _caOcx.PKCS1(input, "md5");
            } else{
                result = _caOcx.PKCS1(input, "sha1");

            }
            if(result==0){
                _certId = _caOcx.LastUsedCertificate;
                _caOcx.PreferredCertificate =_certId;
                return true;
            }else{
                return result;
            }

        }catch(e){
            throw e;
        }
    };
    /**
     * 获取当前证书的用户,使用友好用户名+Id（身份证号或组织机构代码）
     * @returns {*}
     */
    var _getCertificateFriendlyUser=function(){
        _getCertificate(_certId);
        var userName = '';
        var friendlyName = _certificate.FriendlyName;
        userName +=friendlyName;
        var subject =  _certificate.Subject;
        if(subject!=null&&subject!=''){
            var items = subject.split(",");
            for(var i=0;i<items.length;i++){
                if(items[i].indexOf('OID.2.5.4.1')>-1){
                    var userId = items[i].substr(items[i].indexOf('=')+1);
                    userName+=userId;
                    break;
                }
            }
        }
        return userName;
    };

    /**
     * 获取错误信息
     * @param code
     * @private
     */
    var _getErrorString=function(code){
        return _caOcx.GetErrorString(code);
    };

    /**
     * B64编码后的签名结果
     * @returns {*}
     * @private
     */
    var _getSxSignature=function(){
        return _caOcx.SXSignature;
    };

    /**
     * B64编码后的当前证书
     * @returns {*}
     * @private
     */
    var _getSxCertificate=function(){
        return _caOcx.SXCertificate;
    };

    /**
     * B64编码后的输入数据
     * @returns {*}
     * @private
     */
    var _getSxInput=function(){
        return _caOcx.SXInput;
    };


    return{
        initializeCertificate:_initializeCertificate,
        checkValidCertificate:_checkValidCertificate,
        getCertificateThumbprint:_getCertificateThumbprint,
        getCertificateNotBeforeSystemTime:_getCertificateNotBeforeSystemTime,
        getCertificateNotAfterSystemTime:_getCertificateNotAfterSystemTime,
        getCertificateContent:_getCertificateContent,
        signContent:_signContent,
        getCertificateFriendlyUser:_getCertificateFriendlyUser,
        getErrorString:_getErrorString,
        getSxSignature:_getSxSignature,
        getSxCertificate:_getSxCertificate,
        getSxInput:_getSxInput
    }
})(window,jQuery);