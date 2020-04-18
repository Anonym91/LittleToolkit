package com.anonymous.cassandra;

import com.anonymous.config.ConfigStr;
import com.anonymous.config.Configurations;
import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DowngradingConsistencyRetryPolicy;
import com.datastax.driver.core.policies.LoggingRetryPolicy;
import com.datastax.driver.core.policies.RetryPolicy;
import com.datastax.driver.core.policies.RoundRobinPolicy;
import com.anonymous.swing.logAbtain.MySystemOut;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;


/**
 * @ClassName: CasConfig
 * @Author: DLF
 * @Version: 1.0v
 * @Date: 2020/4/10 0010
 * @Description: Cassandra Cluster Builder And Session Singleton
 */
@Slf4j
@Data
public class CasConfig {

    private static Cluster cluster; // Cas Cluster
    private static Session session; // Cas Session

    private CasConfig(){
    }

    public static CasConfig getInstance(){
        return CasConfigHolder.instance;
    }

    private static class CasConfigHolder{
        private static final CasConfig instance = new CasConfig();
    }

    /**
     * Initialize C* Cluster By Connecting
     * @param logArea JTextArea for MySystem.out.println method to print log in JTextArea panel
     * @param casHost Cassandra connect point
     */
    public void initCluster(JTextArea logArea, String casHost){
        try{
            SocketOptions socketOptions = new SocketOptions()
                    .setReadTimeoutMillis(Configurations.getInstance().getInteger(ConfigStr.CAS_SOCKET_CONNECT_TIMEOUT))
                    .setConnectTimeoutMillis(Configurations.getInstance().getInteger(ConfigStr.CAS_SOCKET_READ_TIMEOUT));
            PoolingOptions poolingOptions = new PoolingOptions()
                    .setMaxRequestsPerConnection(HostDistance.LOCAL,Configurations.getInstance().getInteger(ConfigStr.MAX_CONN_LOCAL))
                    .setMaxRequestsPerConnection(HostDistance.REMOTE,Configurations.getInstance().getInteger(ConfigStr.MAX_REQUEST))
                    .setCoreConnectionsPerHost(HostDistance.LOCAL,Configurations.getInstance().getInteger(ConfigStr.CORE_CONN_LOCAL))
                    .setCoreConnectionsPerHost(HostDistance.REMOTE,Configurations.getInstance().getInteger(ConfigStr.CORE_CONN_REMOTE))
                    .setMaxConnectionsPerHost(HostDistance.LOCAL, Configurations.getInstance().getInteger(ConfigStr.MAX_CONN_LOCAL))
                    .setMaxConnectionsPerHost(HostDistance.REMOTE, Configurations.getInstance().getInteger(ConfigStr.MAX_CONN_REMOTE))
                    .setMaxRequestsPerConnection(HostDistance.LOCAL, Configurations.getInstance().getInteger(ConfigStr.MAX_REQUEST))
                    .setMaxRequestsPerConnection(HostDistance.REMOTE, Configurations.getInstance().getInteger(ConfigStr.MAX_CONN_REMOTE))
                    .setHeartbeatIntervalSeconds(Configurations.getInstance().getInteger(ConfigStr.POOL_HEARTBEAT))
                    .setPoolTimeoutMillis(Configurations.getInstance().getInteger(ConfigStr.POOL_TIMEOUT));
            QueryOptions queryOptions = new QueryOptions().setConsistencyLevel(ConsistencyLevel.QUORUM);
            RetryPolicy retryPolicy = new LoggingRetryPolicy(DowngradingConsistencyRetryPolicy.INSTANCE);
            cluster = Cluster.builder()
                    .addContactPoint(casHost)
                    .withSocketOptions(socketOptions)
                    .withPoolingOptions(poolingOptions)
                    .withQueryOptions(queryOptions)
                    .withRetryPolicy(retryPolicy)
                    .withLoadBalancingPolicy(new RoundRobinPolicy())
                    .withPort(Configurations.getInstance().getInteger(ConfigStr.PORT))
                    .build();
            MySystemOut.System.out.println(logArea,"Cassandra Cluster Connected!");
            log.info("Cassandra Cluster Connected!");
        }catch (Exception e){
            MySystemOut.System.out.println(logArea,String.format("Connected to Cluster Error: %s",e.toString()));
            log.error(String.format("Connected to Cluster Error: %s",e.toString()));
        }
    }

    /**
     * Get C* Session
     * @param logArea For log to be printed on JTextArea Panel
     * @return C* Session
     */
    public Session getSession(JTextArea logArea){
        try{
            if(null != session){
                MySystemOut.System.out.println(logArea,"Session Already Existed! Get Session Successfully!");
                log.info("Session Already Existed! Get Session Successfully!");
                return session;
            }else {
                // Creat new session
                session = cluster.connect();
                MySystemOut.System.out.println(logArea,"Session Created! Get Session Successfully!");
                log.info("Session Created! Get Session Successfully!");
            }
        }catch (Exception e){
            MySystemOut.System.out.println(logArea,"Failed to get C* Session, Error: "+e.toString());
            log.error("Failed to get C* Session, Error: "+e.toString());
        }
        return session;
    }
}
