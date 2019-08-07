package com.twinkle.scaffold.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

/**
 * TODO ADD DESC <br/>
 * Date:    2019年8月5日 下午10:38:37 <br/>
 *
 * @author yukang
 * @see
 * @since JDK 1.8
 */
public class RandomUtilsTest {

    @Test
    public void randomTest(){
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomUtils.nextInt(1000, 9999)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomUtils.nextLong(1000000000L, 9999999999L)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.random(5)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.random(5, "abcdefgh")+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.random(5, true, true)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.randomNumeric(5)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.randomAlphabetic(5)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.randomGraph(5)+" ");
        }
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.print(RandomStringUtils.randomPrint(5)+" ");
        }
    }
}
