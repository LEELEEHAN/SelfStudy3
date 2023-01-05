package spring;

@Component("infoPrinter")
public class MemberInfoPrinter {

    private MemberDao memberDao;
    private MemberPrinter printer;

    public void printMemberInfo(String email){
        Member member = memberDao.selectByEmail(email);
        if(member == null){
            System.out.println("no data");
            return;
        }
        printer.print(member);
        System.out.println();
    }
    @Autowired
    public void setMemberDao(MemberDao memberDao){
        //세터 메서드 작성
        this.memberDao = memberDao;
    }
    @Autowired
    @Qualifier("printer")
    public void setPrinter(MemberPrinter printer){
        //세터 메서드 작성
        this.printer =printer;
    }

}