package com.cinchapi.concourse;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.beust.jcommander.internal.Lists;
import com.cinchapi.concourse.lang.Criteria;
import com.cinchapi.concourse.test.ConcourseIntegrationTest;
import com.cinchapi.concourse.thrift.Operator;

/**
 * Tests to check the functionality of average feature.
 * 
 * @author Raghav Babu
 */
public class AverageTest extends ConcourseIntegrationTest {

    @Test
    public void testAverageKey() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 19, 2);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        int actual = 64;
        Number expected = client.calculate().average(key);
        Assert.assertEquals(expected.intValue(), actual/3);
    }

    @Test
    public void testAverageKeyCcl() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 19, 2);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        int actual = 34;
        Number expected = client.calculate().average(key, "name = bar");
        Assert.assertEquals(expected, actual/2);
    }

    @Test
    public void testAverageKeyCclTime() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 19, 2);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        int actual = 34;
        Timestamp timestamp = Timestamp.now();
        client.add(key, 100, 2);
        Number expected = client.calculate().average(key, "name = bar", timestamp);
        Assert.assertEquals(expected, actual/2);
    }

    @Test
    public void testAverageKeyCriteria() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 19, 2);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        int actual = 34;
        Number expected = client.calculate().average(key, Criteria.where()
                .key("age").operator(Operator.LESS_THAN).value(20).build());
        Assert.assertEquals(expected, actual/2);
    }

    @Test
    public void testAverageKeyCriteriaTime() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 19, 2);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        int actual = 34;
        Timestamp timestamp = Timestamp.now();
        client.add(key, 100, 2);
        Number expected = client.calculate()
                .average(key, Criteria.where().key("age")
                        .operator(Operator.LESS_THAN).value(20).build(),
                        timestamp);
        Assert.assertEquals(expected, actual/2);
    }

    @Test(expected = RuntimeException.class)
    public void testAverageKeyException() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add(key, "fifteen", 1);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        client.calculate().average(key);
    }

    @Test
    public void testAverageKeyRecord() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add(key, 19, 1);
        int actual = 49;
        Number expected = client.calculate().average(key, 1);
        Assert.assertEquals(expected, actual/2);
    }

    @Test(expected = RuntimeException.class)
    public void testAverageKeyRecordException() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add(key, "fifteen", 1);
        client.calculate().average(key, 1);
    }

    @Test
    public void testAverageKeyRecords() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 20, 2);
        int actual = 50;
        List<Long> list = Lists.newArrayList();
        list.add((long) 1);
        list.add((long) 2);
        Number expected = client.calculate().average(key, list);
        Assert.assertEquals(expected, actual/2);
    }

    @Test(expected = RuntimeException.class)
    public void testAverageKeyRecordsException() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, "fifty", 2);
        List<Long> list = Lists.newArrayList();
        list.add((long) 1);
        list.add((long) 2);
        client.calculate().average(key, list);
    }

    @Test
    public void testAverageKeyRecordsTime() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 20, 2);
        int actual = 50;
        Timestamp timestamp = Timestamp.now();
        client.add(key, 100, 2);
        Number expected = client.calculate().average(key,
                Lists.newArrayList(1L, 2L), timestamp);
        Assert.assertEquals(expected.intValue(), actual/2);
    }

    @Test(expected = RuntimeException.class)
    public void testAverageKeyRecordsTimeException() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, "fifty", 2);
        Timestamp timestamp = Timestamp.now();
        client.add(key, 100, 2);
        client.calculate().average(key, Lists.newArrayList(1L, 2L), timestamp);
    }

    @Test
    public void testAverageKeyRecordTime() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add(key, 19, 1);
        int actual = 49;
        Timestamp timestamp = Timestamp.now();
        client.add(key, 100);
        Number expected = client.calculate().average(key, 1, timestamp);
        Assert.assertEquals(expected, actual/2);
    }

    @Test
    public void testAverageKeyTime() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add("name", "bar", 2);
        client.add(key, 19, 2);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        int actual = 64;
        Timestamp timestamp = Timestamp.now();
        client.add(key, 100, 2);
        Number expected = client.calculate().average(key, timestamp);
        Assert.assertEquals(expected, actual/3);
    }

    @Test(expected = RuntimeException.class)
    public void testAverageKeyTimeException() {
        String key = "age";
        client.add("name", "foo", 1);
        client.add(key, 30, 1);
        client.add(key, "fifteen", 1);
        client.add("name", "bar", 2);
        client.add(key, 15, 2);
        client.calculate().average(key, Timestamp.now());
    }
}
