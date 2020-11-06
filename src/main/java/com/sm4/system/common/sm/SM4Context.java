package com.sm4.system.common.sm;
/**
 * ${DESCRIPTION}
 *
 * @create 2019-11-19
 */
public class SM4Context {
    public int mode;

    public long[] sk;

    public boolean isPadding;

    public SM4Context()
    {
        this.mode = 1;
        this.isPadding = true;
        this.sk = new long[32];
    }
}
