package com.anonymous.config;

/**
 * @ClassName: ConfigStr
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/10 0010
 * @Description: Configuration Constants
 */
public class ConfigStr {
    // Config file type
    public static final String DEPLOY = "deploy";

    public static final String PORT = "cas.port";
    public static final String  MAX_REQUEST = "cas.max-request.connection";
    public static final String  MAX_CONN_LOCAL = "cas.max.conn.local";
    public static final String  MAX_CONN_REMOTE = "cas.max.conn.remote";
    public static final String  CORE_CONN_LOCAL = "cas.core.conn.local";
    public static final String  CORE_CONN_REMOTE = "cas.core.conn.remote";
    public static final String  POOL_TIMEOUT = "cas.pool.timeout";
    public static final String  POOL_HEARTBEAT = "cas.pool.heartbeat";
    public static final String CAS_SOCKET_READ_TIMEOUT = "socket.read.timeout.millis";
    public static final String CAS_SOCKET_CONNECT_TIMEOUT = "socket.connect.timeout.millis";
}
