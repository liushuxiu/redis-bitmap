package com.uestc.redissortintegral.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import java.util.HashSet;
import java.util.Set;
@Data
@Configuration
@ConfigurationProperties(prefix = "spring.redis.cluster")
public class RedisClusterConfig {
    private String nodes;
    private Integer maxTotal;
    private Integer maxIdle;
    private Integer minIdle;

    @Bean//初始化方法构造一个jedisCluster对象
    public JedisCluster init() {
        try {
            Set<HostAndPort> set = new HashSet<HostAndPort>();
            String[] node = nodes.split(",");
            for (String hostAndPort : node) {
                String host = hostAndPort.split(":")[0];
                int port = Integer.parseInt(hostAndPort.split(":")[1]);
                set.add(new HostAndPort(host, port));
            }
            int a =2;
            //利用其它属性,编写config对象
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(maxTotal);
            config.setMaxIdle(maxIdle);
            config.setMinIdle(minIdle);
            return new JedisCluster(set, config);
        } catch (Exception e) {
            //说明构造过程出现了一些问题,一般是因为没有提供
            //redis相关配置
            return null;
        }
    }
}
