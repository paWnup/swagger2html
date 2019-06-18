package com.puan.demo;

import io.github.swagger2markup.GroupBy;
import io.github.swagger2markup.Language;
import io.github.swagger2markup.Swagger2MarkupConfig;
import io.github.swagger2markup.Swagger2MarkupConverter;
import io.github.swagger2markup.builder.Swagger2MarkupConfigBuilder;
import io.github.swagger2markup.markup.builder.MarkupLanguage;
import org.apache.maven.shared.invoker.*;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Collections;

/**
 * @author puan
 * @date 2019-06-14 16:16
 **/
public class Demo {

    public static void main(String[] args) throws Exception {
        String url = "http://localhost:9091/v2/api-docs";
        generateAsciiDocsToFile(url);
        executeMavenCmd();
    }

    /**
     * 生成.adoc文件
     *
     * @throws Exception
     */
    static void generateAsciiDocsToFile(String url) throws Exception {
        //    输出Ascii到单文件
        Swagger2MarkupConfig config = new Swagger2MarkupConfigBuilder()
                .withMarkupLanguage(MarkupLanguage.ASCIIDOC)
                .withOutputLanguage(Language.ZH)
                .withPathsGroupedBy(GroupBy.TAGS)
                .withGeneratedExamples()
                .withoutInlineSchema()
                .build();

        Swagger2MarkupConverter.from(new URL(url))
                .withConfig(config)
                .build()
                .toFile(Paths.get("./adoc/all"));
    }

    /**
     * 执行maven命令生成html文档
     */
    static void executeMavenCmd() throws MavenInvocationException {
        InvocationRequest request = new DefaultInvocationRequest();
        request.setPomFile(new File("./pom.xml"));
        request.setGoals(Collections.singletonList("asciidoctor:process-asciidoc"));

        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File(System.getenv().get("MAVEN_HOME")));

        invoker.setLogger(new PrintStreamLogger(System.err, InvokerLogger.ERROR) {
        });
        invoker.setOutputHandler(s -> {
        });
        invoker.execute(request);
    }

}
