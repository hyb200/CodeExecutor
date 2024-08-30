package com.abin.executor.docker;

import cn.hutool.core.io.FileUtil;
import com.abin.executor.domain.enums.LanguageEnums;
import com.abin.executor.uitls.CommonUtils;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.*;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.DockerClientImpl;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Slf4j
@Data
@Configuration
@ConfigurationProperties(prefix = "sandbox.config")
public class DockerSandbox {

    private String dockerHost = "tcp://192.168.25.128:2375";

    private String image = "sandbox:v1.0";

    private long memoryLimit = 1024 * 1024 * 256L;

    private long cpuCount = 1L;

    private long memorySwap = 0L;

    private long timeoutLimit = 1L;

    private TimeUnit timeUnit = TimeUnit.SECONDS;

    private final DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
            .withDockerHost(dockerHost)
            .withDockerTlsVerify(false)
            .build();

    private final DockerHttpClient httpClient = new ApacheDockerHttpClient.Builder()
            .dockerHost(dockerClientConfig.getDockerHost())
            .maxConnections(100)
            .connectionTimeout(Duration.ofSeconds(30))
            .responseTimeout(Duration.ofSeconds(45))
            .build();

    private final DockerClient dockerClient = DockerClientImpl.getInstance(dockerClientConfig, httpClient);

    public void execCmd(String containerId, String[] cmd) {
        StatsCmd statsCmd = dockerClient.statsCmd(containerId);
        statsCmd.exec(new ResultCallback<Statistics>() {
            @Override
            public void onStart(Closeable closeable) {

            }

            @Override
            public void onNext(Statistics object) {
                MemoryStatsConfig memoryStats = object.getMemoryStats();

            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onComplete() {

            }

            @Override
            public void close() throws IOException {

            }
        });

        ExecCreateCmdResponse createCmdResponse = dockerClient.execCreateCmd(containerId)
                .withCmd(cmd)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .exec();

        final boolean[] result = {true};
        final boolean[] timeout = {true};

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             ByteArrayOutputStream err = new ByteArrayOutputStream();
             ResultCallback.Adapter<Frame> frameAdapter = new ResultCallback.Adapter<Frame>() {
                 @Override
                 public void onStart(Closeable stream) {
                     super.onStart(stream);
                 }

                 @Override
                 public void onComplete() {
                     timeout[0] = false;
                     super.onComplete();
                 }

                 @SneakyThrows
                 @Override
                 public void onNext(Frame frame) {
                     StreamType streamType = frame.getStreamType();
                     if (streamType.equals(StreamType.STDERR)) {
                         result[0] = false;
                         err.write(frame.getPayload());
                     } else {
                         out.write(frame.getPayload());
                     }
                     super.onNext(frame);
                 }

                 @Override
                 public void close() throws IOException {
                     statsCmd.close();
                     super.close();
                 }
             }) {
            dockerClient.execStartCmd(createCmdResponse.getId()).exec(frameAdapter).awaitCompletion(timeoutLimit, timeUnit);
            System.out.println("===== errMsg =====");
            System.out.print(err);
            System.out.println("===== output =====");
            System.out.print(out);
            System.out.println("===== end =====");
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public String createContainer(String codePath) {
        String image = "sandbox:v1.0";
        HostConfig hostConfig = new HostConfig();
        hostConfig.withMemorySwap(memorySwap);
        hostConfig.withMemory(memoryLimit);
        hostConfig.withCpuCount(cpuCount);

        String containerId = dockerClient.createContainerCmd(image)
                .withHostConfig(hostConfig)
//                .withName("111") //  设置容器名
                .withNetworkDisabled(true)  //  关闭网络
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .withTty(true)
                .exec()
                .getId();

        //  启动容器
        dockerClient.startContainerCmd(containerId).exec();

        //  复制文件
        dockerClient.copyArchiveToContainerCmd(containerId)
                .withHostResource(codePath)
                .withRemotePath("/box")
                .exec();

        return containerId;
    }



    public static void main(String[] args) {
        DockerSandbox sandbox = new DockerSandbox();
        String cppCode = "#include <iostream>\n" +
                "\n" +
                "using namespace std;\n" +
                "\n" +
                "int main()\n" +
                "{\n" +
                "    cout << \"hello, docker-java!\" << endl;\n" +
                "    return 0;\n" +
                "}\n" +
                "\n";

        String javaCode = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "           byte[] bytes = new byte[268435457];" +
                "    }\n" +
                "}";
        String codePath = CommonUtils.saveCode("java", javaCode);
        System.out.println(FileUtil.getParent(codePath, 1));
        String containerId = sandbox.createContainer(codePath);
        sandbox.execCmd(containerId, LanguageEnums.JAVA.getCompileCmd());
        sandbox.execCmd(containerId, LanguageEnums.JAVA.getExecCmd());
    }
}
