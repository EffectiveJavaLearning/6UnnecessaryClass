import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * 用于说明{@link ReuseImmutableObject}中的第二点
 *
 * @see #badIsBabyBoomer() 这是差评的判断方法，每次调用都会创建一个Calendar和两个Date，
 *      而返回的却是一样的两个日期。
 *
 * 这时候最好把生育高峰的起止时间提出来作为常量保存，并在类的static块中对其进行初始化。
 * 之后使用这种方式{@link #isBabyBoomer()}进行判断，在多次调用的情况下性能显著提升。
 *
 * @author LightDance
 */
class Person {

    private final Date birthday;

    Person(Date birthday) {
        this.birthday = birthday;
    }

    //生育高峰在1946到1964年之间

    /**
     * 差评的日期比较，创建了不必要的实例
     *
     * @return 比较结果
     */
    public boolean badIsBabyBoomer(){
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946 , Calendar.JANUARY , 1 , 0 , 0 , 0);
        Date boomStart = gmtCal.getTime();
        gmtCal.set(1964 , Calendar.JANUARY , 1 , 0 , 0 , 0);
        Date boomEnd = gmtCal.getTime();
        return birthday.compareTo(boomStart) >= 0 && birthday.compareTo(boomEnd) <= 0;
    }

    //下面是改进后的方式

    private static final Date BOOM_START;
    private static final Date BOOM_END;

    static {
        Calendar gmtCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        gmtCal.set(1946 , Calendar.JANUARY , 1 , 0 , 0 , 0);
        BOOM_START = gmtCal.getTime();
        gmtCal.set(1964 , Calendar.JANUARY , 1 , 0 , 0 , 0);
        BOOM_END = gmtCal.getTime();
    }

    public boolean isBabyBoomer(){
        return birthday.compareTo(BOOM_START) >= 0 && birthday.compareTo(BOOM_END) <= 0;
    }
}
