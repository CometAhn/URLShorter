package library;

import java.text.DecimalFormat;
import java.util.Calendar;

public class test {

	public static void main(String[] args) {

		DecimalFormat df = new DecimalFormat("#0");
        Calendar currentCalendar = Calendar.getInstance();
        String month  = df.format(currentCalendar.get(Calendar.MONTH) + 1);
        String day  = df.format(currentCalendar.get(Calendar.DATE));
        int start = Integer.parseInt(month)*30 + Integer.parseInt(day);
                
        System.out.println(Integer.parseInt(month)*30 + Integer.parseInt(day));
        System.out.println("오늘" +day);

        String month2 = "22 12:59:08";
        
        System.out.println(month2.substring(0, 2));
	}

}
