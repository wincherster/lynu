import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wangmeng247
 * @date 2018-02-05 11:09
 */

public class AllTest {
    public static void main(String[] args){
        Long timeStamp = 1535731199000L;  //获取当前时间戳
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss dd");
        String sd = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));   // 时间戳转换成时间
        System.out.println(sd);

        Long timeStamp2 = 1514768400000L;  //获取当前时间戳
        SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss dd");
        String sd2 = sdf2.format(new Date(Long.parseLong(String.valueOf(timeStamp2))));   // 时间戳转换成时间
        System.out.println(sd2);
    }
}
