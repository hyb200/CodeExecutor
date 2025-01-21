package com.abin.executor.docker;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.abin.executor.domain.CmdResult;
import com.abin.executor.domain.ExecuteResp;
import com.abin.executor.domain.enums.ExecStatusEnums;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.command.StatsCmd;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Frame;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.Statistics;
import com.github.dockerjava.api.model.StreamType;
import com.github.dockerjava.api.model.Volume;

@Slf4j
@Component
public class DockerContainer {

    private static final String image = "compiler:2.0";

    private static final String REMOTE_PATH = "/workspace";

    @Value("${sandbox.config.memory-limit:268435456}")
    private long memoryLimit;

    @Value("${sandbox.config.cpu-count:1}")
    private long cpuCount;

    @Value("${sandbox.config.memory-swap:0}")
    private long memorySwap;

    @Resource
    public DockerClient dockerClient;

    public CmdResult execCmd(String containerId, String[] cmd, long timeoutLimit, TimeUnit timeUnit) {
        StatsCmd statsCmd = dockerClient.statsCmd(containerId);

        final long[] memory = new long[1];
        final long[] time = new long[2];

        CmdResult cmdResult = new CmdResult();
        ExecCreateCmdResponse createCmdResponse = dockerClient.execCreateCmd(containerId)
                .withCmd(cmd)
                .withAttachStdin(true)
                .withAttachStdout(true)
                .withAttachStderr(true)
                .exec();

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
            ByteArrayOutputStream err = new ByteArrayOutputStream();
            ResultCallback<Statistics> statisticsResultCallback = new ResultCallback.Adapter<>() {
                @Override
                public void onNext(Statistics statistics) {
                    memory[0] = Math.max(memory[0], statistics.getMemoryStats().getMaxUsage());
                }
            };
            ResultCallback.Adapter<Frame> frameAdapter = new ResultCallback.Adapter<>() {

                @Override
                public void onStart(Closeable stream) {
                    statsCmd.exec(statisticsResultCallback);
                    time[0] = System.currentTimeMillis();
                    super.onStart(stream);
                }

                @SneakyThrows
                @Override
                public void onNext(Frame frame) {
                    StreamType streamType = frame.getStreamType();
                    if (streamType.equals(StreamType.STDERR)) {
                        err.write(frame.getPayload());
                    } else {
                        out.write(frame.getPayload());
                    }
                    super.onNext(frame);
                }

                @Override
                public void close() throws IOException {
                    time[1] = System.currentTimeMillis();
                    statsCmd.close();
                    super.close();
                }
            }) {

            boolean finish  = dockerClient.execStartCmd(createCmdResponse.getId()).exec(frameAdapter).awaitCompletion(timeoutLimit, timeUnit);

            if (!finish) {
                cmdResult.setCode(ExecStatusEnums.TIME_LIMIT_EXCEEDED.getCode());
                return cmdResult;
            }

            cmdResult.setCode(ExecStatusEnums.SUCCESS.getCode()).setMessage(out.toString());
            Optional.ofNullable(err.toString()).ifPresent(message -> {
                cmdResult.setErrorMsg(message);
                cmdResult.setCode(ExecStatusEnums.COMMON_ERROR.getCode());
            });

            cmdResult.setTime(time[1] - time[0]);
            cmdResult.setMemory(memory[0]);
            return cmdResult;
        } catch (Exception e) {
            log.error("exec cmd fail", e);
        }
        return cmdResult;
    }

    public String create() {
        HostConfig hostConfig = new HostConfig();
        hostConfig.setBinds(new Bind("./code", new Volume(REMOTE_PATH)));
        hostConfig.withMemorySwap(memorySwap);
        hostConfig.withMemory(memoryLimit);
        hostConfig.withCpuCount(cpuCount);
        hostConfig.withReadonlyRootfs(true);

        String containerId = dockerClient.createContainerCmd(image).withHostConfig(hostConfig)
                .withNetworkDisabled(true)  //  关闭网络
                .withAttachStdin(true).withAttachStdout(true).withAttachStderr(true)
                .withTty(true).exec().getId();

        dockerClient.startContainerCmd(containerId).exec();
        return containerId;
    }

    public static void main(String[] args) {
//        DockerSandbox sandbox = new DockerSandbox();
        String cppCode = "#include <iostream>\n"
                + "\n"
                + "using namespace std;\n"
                + "\n"
                + "int main()\n"
                + "{\n"
                + "    int d[1024*1024*260];"
                + "    cout << \"hello, docker-java!\" << endl;\n"
                + "    return 0;\n"
                + "}\n"
                + "\n";

        String javaCode = "public class Main {\n"
                + "    public static void main(String[] args)  throws Exception{\n"
                + "int[] f = new int[1024 * 1024 * 256]; Thread.sleep(10000L);"
                + "           System.out.print(\"success\");"
                + "    }\n"
                + "}";
        String goCode = "package main\n"
                + "\n"
                + "import \"fmt\"\n"
                + "\n"
                + "func main() {\n"
                + "    var arr[1024*1024]int\n"
                + "    fmt.Println(arr)\n"
                + "}";
        String pyCode = "with open('a.txt', 'w') as f:\n" + "    f.write('这是写入到a.txt文件中的示例内容。')";
//                String codePath = CommonUtils.saveCode("cpp", cppCode);
//        String codePath = CommonUtils.saveCode(LanguageEnums.PYTHON3.getLanguage(), pyCode);
//        String containerId = sandbox.createContainer(codePath);
//        System.out.println(containerId);
//        System.out.println(sandbox.execCmd(containerId, LanguageEnums.GO.getCompileCmd(), 10000L, TimeUnit.MILLISECONDS));
//        System.out.println(sandbox.execCmd(containerId, LanguageEnums.PYTHON3.getExecCmd(), 1000L, TimeUnit.MILLISECONDS));
//        CommonUtils.deleteFile(codePath);
    }
}
