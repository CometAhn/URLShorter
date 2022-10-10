package shorter.DAO;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class Encrypt {

    // 랜덤 문자 생성
    public String randnum() {
        Random random = new Random();
        int length = 5;

        StringBuffer newWord = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int choice = random.nextInt(3);
            switch (choice) {
                case 0:
                    newWord.append((char) ((int) random.nextInt(25) + 97));
                    break;
                case 1:
                    newWord.append((char) ((int) random.nextInt(25) + 65));
                    break;
                case 2:
                    newWord.append((char) ((int) random.nextInt(10) + 48));
                    break;
                default:
                    break;
            }
        }

        return newWord.substring(0);
    }

}
