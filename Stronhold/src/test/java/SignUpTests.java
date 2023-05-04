import controller.SignUpController;
import model.Trade;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import view.message.SignUpMessages;

public class SignUpTests {

    @Test
    public void questionIsOutOfRange(){
        SignUpController controller=new SignUpController();
        SignUpMessages message = controller.pickQuestion(4,"Hello","Hello");
        Assertions.assertEquals(SignUpMessages.OUT_OF_RANGE,message);
    }

    @Test
    public void repeatedMistake(){
        SignUpController controller=new SignUpController();
        SignUpMessages message = controller.checkValidationFormat("group21AP@gmail.com","moshan21","moshan#AP21","moshan#AP12");
        Assertions.assertEquals(SignUpMessages.BAD_REPEAT,message);
    }

    @Test
    public void passwordIsWeak(){
        SignUpController controller=new SignUpController();
        SignUpMessages message = controller.checkValidationFormat("group21AP@gmail.com","moshan21","moshanAP21","moshanAP21");
        Assertions.assertEquals(SignUpMessages.WEAK_PASSWORD,message);
    }

    @Test
    public void usernameFault(){
        SignUpController controller=new SignUpController();
        SignUpMessages message=controller.checkValidationFormat("ssea434w@gmail.com","mor#","moshan!AP21","moshan!AP21");
        Assertions.assertEquals(SignUpMessages.VALID_USERNAME,message);
    }

    @Test
    public void emailFault(){
        SignUpController controller=new SignUpController();
        SignUpMessages message=controller.checkValidationFormat("ssea434wgmail.com","SALAMman12Hastam","moshan!AP21","moshan!AP21");
        Assertions.assertEquals(SignUpMessages.VALID_EMAIL,message);
    }
}
