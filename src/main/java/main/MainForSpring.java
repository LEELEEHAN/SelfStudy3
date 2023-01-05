package main;

import config.AppCtx;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainForSpring {

    private static ApplicationContext ctx = null;

    public static void main(String[] args)throws IOException{
        ctx = new AnnotationConfigApplicationContext(AppCtx.class);

        BufferedReader reader=
                new BufferedReader(new InputStreamReader(System.in));
        while (true){
            System.out.println("명령어 입력");
            String command = reader.readLine();
            if(command.equals("exit")){
                System.out.println("종료");
                break;
            }
            if(command.startsWith("new ")){
                processNewCommand(command.split(" "));
                continue;
            } else if(command.startsWith("change")){
                processChangeCommand(command.split(" "));
                continue;
            } else if (command.equals("version")){
                processVersionCommand();
                continue;
            }else if(command.equals("list")){
                processListCommand();
                continue;
            } else if (command.equals("info ")){
                processInfoCommand(command.split(" "));
                continue;
            }
            printHelp();
        }
    }

    private static void processVersionCommand() {
        VersionPrinter versionPrinter =
                ctx.getBean("versionPrinter",VersionPrinter.class);
        versionPrinter.print();

    }

    private static void processInfoCommand(String[] arg) {
        if(arg.length!=2){
            printHelp();
            return;
        }
        MemberInfoPrinter infoPrinter =
                ctx.getBean("infoPrinter",MemberInfoPrinter.class);
        infoPrinter.printMemberInfo(arg[1]);
    }

    private static void processListCommand() {
        MemberListPrinter listPrinter=
                ctx.getBean("listPrinter",MemberListPrinter.class);
        listPrinter.printAll();
    }

    private static void processNewCommand(String[] arg){
        if(arg.length!=5){
            printHelp();
        }
        MemberRegisterService regSvc =
                ctx.getBean("memberRegSvc", MemberRegisterService.class);
        RegisterRequest req = new RegisterRequest();
        req.setEmail(arg[1]);

        if(!req.isPasswordEqualToConfirmPassword()){
            System.out.println("암호 일치 ㄴㄴ");
            return;
        }
        try{
            regSvc.regist(req);
            System.out.println("등록완료");
        } catch (AleadyExistingMemberException e){
            System.out.println("이멜존재함");
        }
    }

    private static void processChangeCommand(String[] arg){
        if(arg.length!=4){
            printHelp();
            return;
        }
        ChangePasswordService changePwdSvc=
                ctx.getBean("changePwdSvc", ChangePasswordService.class);
        try{
            changePwdSvc.changePassword(arg[1],arg[2],arg[3]);
            System.out.println("암호 변경");
        } catch (MemberNotFoundException e){
            System.out.println("존재하지않는 이메일");
        } catch (IdPasswordNotMatchingException e){
            System.out.println("이메일 암호 일치 엑스");
        }
    }

    private static void printHelp() {
    }

}
