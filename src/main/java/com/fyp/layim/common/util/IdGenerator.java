package com.fyp.layim.common.util;

/**
 * @author fyp
 * @crate 2017/11/2 21:15
 * @project SpringBootLayIM
 */

public class IdGenerator {
    /**
     * SnowFlake算法 64位Long类型生成唯一ID 第一位0，表明正数 2-42，41位，表示毫秒时间戳差值，起始值自定义
     * 43-52，10位，机器编号，5位数据中心编号，5位进程编号 53-64，12位，毫秒内计数器 本机内存生成，性能高
     *
     * 主要就是三部分： 时间戳，进程id，序列号 时间戳41，id10位，序列号12位
     */

    private final static long BEGIN_TS = 1483200000000L;

    private long lastTs = 0L;

    private long processId = 1000L;
    private int processIdBits = 10;

    private long sequence = 0L;
    private int sequenceBits = 12;

    /**
    * 方法说明
    *@param
    *@param
    *@return
    */
    public IdGenerator(long processId) {
        if (processId > ((1 << processIdBits) - 1)) {
            throw new RuntimeException("进程ID超出范围，设置位数" + processIdBits + "，最大"
                    + ((1 << processIdBits) - 1));
        }
        this.processId = processId;
    }

    protected long timeGen() {
        return System.currentTimeMillis();
    }

    public synchronized long nextId() {
        long ts = timeGen();
        // 刚刚生成的时间戳比上次的时间戳还小，出错
        if (ts < lastTs) {
            throw new RuntimeException("时间戳顺序错误");
        }
        // 刚刚生成的时间戳跟上次的时间戳一样，则需要生成一个sequence序列号
        if (ts == lastTs) {
            // sequence循环自增
            sequence = (sequence + 1) & ((1 << sequenceBits) - 1);
            // 如果sequence=0则需要重新生成时间戳
            if (sequence == 0) {
                // 且必须保证时间戳序列往后
                ts = nextTs(lastTs);
            }
        } else {
            // 如果ts>lastTs，时间戳序列已经不同了，此时可以不必生成sequence了，直接取0
            sequence = 0L;
        }
        // 更新lastTs时间戳
        lastTs = ts;
        return ((ts - BEGIN_TS) << (processIdBits + sequenceBits)) | (processId << sequenceBits)
                | sequence;
    }

    protected long nextTs(long lastTs) {
        long ts = timeGen();
        while (ts <= lastTs) {
            ts = timeGen();
        }
        return ts;
    }

}
