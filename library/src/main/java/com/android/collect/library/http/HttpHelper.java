package com.android.collect.library.http;

/**
 */
public class HttpHelper {

    public static final String strHttpUrl = "https://www.hkbchina.com/pcweb/";
    public static final String strExtInfo = "?_locale=zh_CN&BankId=9999&loginType=I&"; // URL扩展信息

    /**
     * 公共
     */
    public static final String GenTimeStamp = "GenTimeStamp.do";
    public static final String GenToken = "GenToken.do";
    /**
     * 非登录获取短信验证码
     */
    public static final String GenPhoneToken = "GenPhoneToken.do";

    public static final String GenTokenImg = "GenTokenImg.do"; //图形验证码
    /**
     * 登录获取短信验证码
     */
    public static final String GenPhoneTokenFLogin = "GenPhoneTokenFLogin.do";

    /**
     * 手势密码设置/修改：M修改，T设置
     */
    public static final String GesturePwdSet = "GesturePwdSet.do";// 手势密码设置/修改
    public static final String GesturePwdSetFL = "GesturePwdSetFL.do";// 手势密码设置/修改
    public static final String GesturePwdClose = "GesturePwdClose.do";// 手势密码关闭

    public static final String logout = "logout.do"; // 退出登录

    /**
     * 校验登录密码
     */
    public static final String CheckLoginPwd = "CheckLoginPwd.do";// 校验登录密码
    /**
     * 登录
     */
    public static final String login = "login.do";// 登录
    public static final String DeviceAuthent = "DeviceAuthent.do";// 设备绑定
    public static final String GestureQry = "GestureQry.do";// 手势密码查询
    public static final String UploadIdImg = "UploadIdImg.do";// 上传身份证
    public static final String ShowBindCard = "ShowBindCard.do";// 绑定卡查询

    public static final String BindCardChangeCheck = "BindCardChangeCheck.do";// 更换绑定卡检查
    public static final String BindCardChange = "BindCardChange.do";//  更换绑定卡
    public static final String AcctInfoQuery = "AcctInfoQuery.do";//  绑定卡信息更新

    public static final String MobilePhoneVersionQuery = "MobilePhoneVersionQuery.do";// 检查更新

    public static final String FeedBack = "FeedBack.do"; // 意见反馈
    public static final String QueryfeedBack = "QueryfeedBack.do"; // 意见反馈查询
    public static final String CurrentVersionQry = "CurrentVersionQry.do"; // 版本信息查询
    public static final String QuestionQry = "QuestionQry.do"; // 常见问题
    public static final String NewsList = "NewsList.do"; // 消息列表查询
    public static final String NewsDetail = "NewsDetail.do"; // 消息详情
    public static final String NewsRead = "NewsRead.do"; // 消息已读
    public static final String ActBalQry = "ActBalQry.do"; // 余额查询
    public static final String AdvertListQry = "AdvertListQry.do"; // 广告
    public static final String AdvertImageDownLoad4Mobile = "AdvertImageDownLoad4Mobile.do"; // 广告图片下载

    /**
     * 密码修改/重置
     */
    public static final String LoginPwdModify = "LoginPwdModify.do"; // 登录密码修改
    public static final String TrsPwdModify = "TrsPwdModify.do"; // 交易密码修改
    public static final String JudgeCifOrUsr = "JudgeCifOrUsr.do"; // 重置登录密码
    public static final String GetBackPwd = "GetBackPwd.do"; // 重置登录密码
    public static final String TrsPwdResetCheck = "TrsPwdResetCheck.do"; // 重置交易密码
    public static final String TrsPwdReset = "TrsPwdReset.do"; // 重置交易密码

    /**
     * 注册
     */
    public static final String JudgeMobilePhone = "JudgeMobilePhone.do";
    public static final String RegisterUser = "RegisterUser.do";

    /**
     * 绑卡开户
     */
    public static final String OpenAcctCardCheck = "OpenAcctCardCheck.do"; // 绑定卡认证
    public static final String SupportBankListQry = "SupportBankListQry.do"; // 查询银行列表
    public static final String SupportBankListQry2 = "SupportBankListQry2.do"; // 查询银行列表
    public static final String LimitOfBankQry = "LimitOfBankQry.do"; // 支持银行查询
    public static final String OpenAcctBankNameQry = "OpenAcctBankNameQry.do"; // 开户行查询
    public static final String OpenAcctIdCheck = "OpenAcctIdCheck.do"; // 上传身份照片
    public static final String OpenAcct = "OpenAcct.do"; // 开户

    public static final String MobilePhoneChange = "MobilePhoneChange.do";// 手机号变更

    /**
     * 个人信息
     */
    public static final String PerInfoMaintenancePre = "PerInfoMaintenancePre.do";// 个人信息查询
    public static final String PerInfoMaintenance = "PerInfoMaintenance.do";// 个人信息修改
    /**
     * 交易明细查询
     */
    public static final String ActTrsJnlQry = "ActTrsJnlQry.do";

    /**
     * 贷款
     */
    public static final String LoanInfoQry = "LoanInfoQry.do"; // 贷款信息查询
    public static final String LoanDrawPre = "LoanDrawPre.do"; // 提款
    public static final String ContractSign = "ContractSign.do"; // 提款
    public static final String ContractSignQry = "ContractSignQry.do"; // 协议参数查询
    public static final String LoanDraw = "LoanDraw.do"; // 提款
    public static final String RepaymnetPlanQry = "RepaymnetPlanQry.do"; // 还款计划查询
    public static final String LoanInfoDetailQry = "LoanInfoDetailQry.do"; // 贷款明细查询

    /**
     * 转入转出
     */
    public static final String AccountRechargePre = "AccountRechargePre.do"; // 转入
    public static final String AccountRecharge = "AccountRecharge.do"; // 转入
    public static final String AccountRechargeConfirm = "AccountRechargeConfirm.do"; // 转入
    public static final String AccountDepositPre = "AccountDepositPre.do"; // 转出
    public static final String AccountDeposit = "AccountDeposit.do"; // 转出
    public static final String AccountDepositConfirm = "AccountDepositConfirm.do"; // 转出

    public static final String DataDeal = "DataDeal.do";

    /**
     * 众邦宝
     */
    public static final String DepositZbPre = "DepositZbPre.do"; // 存入
    public static final String DepositZbConfirm = "DepositZbConfirm.do"; // 存入
    public static final String BankBaoContractSign = "BankBaoContractSign.do"; // 协议签约
    public static final String DepositZb = "DepositZb.do"; // 存入
    public static final String WithdrawalZbPre = "WithdrawalZbPre.do"; // 实时支取
    public static final String WithdrawalZbConfirm = "WithdrawalZbConfirm.do"; // 实时支取
    public static final String WithdrawalZb = "WithdrawalZb.do"; // 实时支取
    public static final String AccessRecordQry = "AccessRecordQry.do"; // 存取记录查询
    public static final String BaoIncreaseOrder = "BaoIncreaseOrder.do"; // 众邦宝增利支取
    public static final String BaoIncreaseOrderConfirm = "BaoIncreaseOrderConfirm.do"; // 众邦宝增利支取
    public static final String BaoDrawTrial = "BaoDrawTrial.do"; // 众邦宝支取利息试算
    public static final String MyZBankBao = "MyZBankBao.do"; // 我的众邦宝
    public static final String RateQry = "RateQry.do"; // 利率查询
    public static final String RealRateQry = "RealRateQry.do"; // 实时利率查询
    public static final String IncreaseRateQry = "IncreaseRateQry.do"; // 增利利率查询

    public static final String FriendsDetailQry = "FriendsDetailQry.do"; // 推荐的好友列表
    public static final String RecommendFriendsQrcode = "RecommendFriendsQrcode.do"; // 推荐好友的二维码
    public static final String OrganizationCodeQry = "OrganizationCodeQry.do"; // 推荐码查询
}
