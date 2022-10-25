package com.baomidou.mybatisplus.generator;



import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig.Builder;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.engine.AbstractTemplateEngine;
import java.util.Scanner;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public final class FastAutoGenerator {
    private final Builder dataSourceConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder globalConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.PackageConfig.Builder packageConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder strategyConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder injectionConfigBuilder;
    private final com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder templateConfigBuilder;
    private AbstractTemplateEngine templateEngine;
    private final Scanner scanner;

    private FastAutoGenerator(Builder dataSourceConfigBuilder) {
        this.scanner = new Scanner(System.in);
        this.dataSourceConfigBuilder = dataSourceConfigBuilder;
        this.globalConfigBuilder = new com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder();
        this.packageConfigBuilder = new com.baomidou.mybatisplus.generator.config.PackageConfig.Builder();
        this.strategyConfigBuilder = new com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder();
        this.injectionConfigBuilder = new com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder();
        this.templateConfigBuilder = new com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder();
    }

    public static FastAutoGenerator create(@NotNull String url, String username, String password) {
        return new FastAutoGenerator(new Builder(url, username, password));
    }

    public static FastAutoGenerator create(@NotNull Builder dataSourceConfigBuilder) {
        return new FastAutoGenerator(dataSourceConfigBuilder);
    }

    public String scannerNext(String message) {
        System.out.println(message);
        String nextLine = this.scanner.nextLine();
        return StringUtils.isBlank(nextLine) ? this.scanner.next() : nextLine;
    }

    public FastAutoGenerator globalConfig(Consumer<com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder> consumer) {
        consumer.accept(this.globalConfigBuilder);
        return this;
    }

    public FastAutoGenerator globalConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.GlobalConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.globalConfigBuilder);
        return this;
    }

    public FastAutoGenerator packageConfig(Consumer<PackageConfig.Builder> consumer) {
        consumer.accept(this.packageConfigBuilder);
        return this;
    }

    public FastAutoGenerator packageConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.PackageConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.packageConfigBuilder);
        return this;
    }

    public FastAutoGenerator strategyConfig(Consumer<com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder> consumer) {
        consumer.accept(this.strategyConfigBuilder);
        return this;
    }

    public FastAutoGenerator strategyConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.StrategyConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.strategyConfigBuilder);
        return this;
    }

    public FastAutoGenerator injectionConfig(Consumer<com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder> consumer) {
        consumer.accept(this.injectionConfigBuilder);
        return this;
    }

    public FastAutoGenerator injectionConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.InjectionConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.injectionConfigBuilder);
        return this;
    }

    public FastAutoGenerator templateConfig(Consumer<com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder> consumer) {
        consumer.accept(this.templateConfigBuilder);
        return this;
    }

    public FastAutoGenerator templateConfig(BiConsumer<Function<String, String>, com.baomidou.mybatisplus.generator.config.TemplateConfig.Builder> biConsumer) {
        biConsumer.accept((message) -> {
            return this.scannerNext(message);
        }, this.templateConfigBuilder);
        return this;
    }

    public FastAutoGenerator templateEngine(AbstractTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
        return this;
    }

    public void execute() {
        (new AutoGenerator(this.dataSourceConfigBuilder.build())).global(this.globalConfigBuilder.build()).packageInfo(this.packageConfigBuilder.build()).strategy(this.strategyConfigBuilder.build()).injection(this.injectionConfigBuilder.build()).template(this.templateConfigBuilder.build()).execute(this.templateEngine);
    }
}

