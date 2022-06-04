package com.uestc.redissortintegral.service;
import org.springframework.stereotype.Service;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;
import javax.annotation.Resource;

@Service
public class ActiveService {
    @Resource
    private Jedis jedis;
    String[] keys = {DAY_01, DAY_02, DAY_03, DAY_04, DAY_05, DAY_07, DAY_06};
    public static final Long USER_A = 123456L;
    public static final Long USER_B = 2L;
    public static final Long USER_C = 3L;
    public static final String DAY_01 = "2021-05-21";
    public static final String DAY_02 = "2021-05-22";
    public static final String DAY_03 = "2021-05-23";
    public static final String DAY_04 = "2021-05-24";
    public static final String DAY_05 = "2021-05-25";
    public static final String DAY_06 = "2021-05-26";
    public static final String DAY_07 = "2021-05-27";

    /**
     * 统计活跃用户
     */
    public void test() {
        //  String today = DateUtil.today();
        jedis.setbit(DAY_01, USER_A, true);

        jedis.setbit(DAY_02, USER_A, true);
        jedis.setbit(DAY_02, USER_B, true);
        jedis.setbit(DAY_02, USER_C, true);

        jedis.setbit(DAY_03, USER_A, true);
        jedis.setbit(DAY_03, USER_C, true);

        jedis.setbit(DAY_04, USER_A, true);
        jedis.setbit(DAY_04, USER_C, true);

        jedis.setbit(DAY_05, USER_A, true);
        jedis.setbit(DAY_05, USER_C, true);

        jedis.setbit(DAY_06, USER_A, true);
        jedis.setbit(DAY_06, USER_C, true);

        jedis.setbit(DAY_07, USER_A, true);

        countTodayUser(DAY_02);
        countWeekUser();
    }

    /**
     * 统计当日登陆用户个数
     *
     * @param date
     */
    void countTodayUser(String date) {
        System.out.println(date + ":" + "登陆的用户个数 :" + jedis.bitcount(date));
    }

    /**
     * 统计一周内登陆用户的个数
     */
    void countWeekUser() {
        jedis.bitop(BitOP.AND, "count-week-user-and", keys);
        Long bitcount = jedis.bitcount("count-week-user-and");
        jedis.bitop(BitOP.OR, "count-week-user-or", keys);
        Long bitcount2 = jedis.bitcount("count-week-user-or");
        System.out.println("一周内每天都登陆的用户个数 :" + bitcount);
        System.out.println("一周内登陆过的用户个数 :" + bitcount2);
    }
}
