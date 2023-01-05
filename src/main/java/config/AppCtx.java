package config;


import spring.*;

@Cofiguration
@ComponentScan(basePackages = {"Spring"})
public class AppCtx {

    @Bean
    public MemberDao memberDao(){
        return new MemberDao();
    }
    @Bean
    public MemberRegisterService memberRegSvc(){
        return new MemberRegisterService();
    }
    @Bean
    public ChangePasswordService changePwdSvc(){
        return new ChangePasswordService();
    }
    @Bean
    public MemberPrinter memberPrinter(){
        return new MemberPrinter();
    }
    @Bean
    public MemberInfoPrinter infoPrinter(){
        MemberInfoPrinter infoPrinter = new MemberInfoPrinter();
        infoPrinter.setPrinter(memberPrinter2());
        return new MemberInfoPrinter();
    }
    @Bean
    @Qualifier("printer")
    public MemberPrinter memberPrinter1(){
        return new MemberPrinter();
    }
    @Bean
    @Qualifier("summaryPrinter")
    public MemberSummaryPrinter memberPrinter2(){
        return new MemberSummaryPrinter();
    }
    @Bean
    public VersionPrinter versionPrinter(){
        VersionPrinter versionPrinter = new VersionPrinter();
        versionPrinter.setMajorVersion(5);
        versionPrinter.setMinorVersion(0);
        return new VersionPrinter();
    }
}
