package generation;

import cn.hutool.core.io.FileUtil;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.TemplateType;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


/**
 * 代码生成器
 *
 * @author Yun
 */
@SpringBootTest
public class Generation {

    @Test
    public void generation() {
        DataSourceConfig.Builder dataSource = getDataSource("jdbc:mysql://101.35.147.40:10001/smart_bi_data?serverTimezone=UTC&useSSL=false&useUnicode=true&characterEncoding=utf8", "root", "SmartBi1024", "smart_bi_data");
        // outputDir 可以直接写项目地址 直接生成到项目里 比如D:\project\Smart-BI-Data
        generate(dataSource, "bi-messageCenter", "D:\\project\\Smart-BI-Data", "Yun", "mq_config");
    }

    /**
     * 生成代码
     *
     * @param dataSource  数据源
     * @param serviceName 服务名称
     * @param outputDir   输出路径
     * @param author      作者
     */
    private static void generate(DataSourceConfig.Builder dataSource, String serviceName, String outputDir, String author, String... tableName) {
        String rootPath = "com.yun." + serviceName.replace("-", "").toLowerCase() ;
        FastAutoGenerator autoGenerator = FastAutoGenerator.create(dataSource).
                globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() //禁止打开输出目录
                            .outputDir(outputDir); // 指定输出目录
                }).packageConfig(builder -> {
            builder.parent("") // 设置父包名
                    .controller(serviceName + "/" + serviceName + "-service/src/main/java/com/yun/" + serviceName.replace("-", "").toLowerCase() + "/controller")
                    .mapper(serviceName + "/" + serviceName + "-dao/src/main/java/com/yun/" + serviceName.replace("-", "").toLowerCase()  + "/dao")
                    .entity(serviceName + "/" + serviceName + "-dao/src/main/java/com/yun/" + serviceName.replace("-", "").toLowerCase()  + "/entity")
                    .service(serviceName + "/" + serviceName + "-dao/src/main/java/com/yun/" + serviceName.replace("-", "").toLowerCase()  + "/service")
                    .serviceImpl(serviceName + "/" + serviceName + "-dao/src/main/java/com/yun/" + serviceName.replace("-", "").toLowerCase()  + "/service/impl")
                    .packageEntity(rootPath + ".entity")
                    .packageController(rootPath + ".controller")
                    .packageMapper(rootPath + ".dao")
                    .packageServiceImpl(rootPath + ".service.impl")
                    .packageService(rootPath + ".service")
            ;// 设置dto包名
        }).strategyConfig(builder -> {
            // 设置需要生成的表名
            builder.addInclude(tableName)
                    .entityBuilder().formatFileName("%sEntity")
                    .build().controllerBuilder().formatFileName("%sController").build().mapperBuilder().formatMapperFileName("%sDao").serviceBuilder().formatServiceFileName("%sService").build()
            /*.addTablePrefix("t_", "c_")*/; // 设置过滤表前缀
        }).templateConfig(builder -> {
            builder.disable(TemplateType.ENTITY)
                    .entity("/templates/entity.java")
                    .mapper("/templates/mapper.java")
                    .service("/templates/service.java")
                    .serviceImpl("/templates/serviceImpl.java")
                    .controller("/templates/controller.java")
                    .build();
        });
        autoGenerator.execute();
        FileUtil.del(outputDir + "/mapper");
    }

    /**
     * 获取数据源配置
     *
     * @param url      驱动链接
     * @param userName 账户
     * @param password 密码
     * @param schema   库
     * @return 数据源
     */
    private static DataSourceConfig.Builder getDataSource(String url, String userName, String password, String schema) {
        return new DataSourceConfig.Builder(url, userName, password)
                .dbQuery(new MySqlQuery())
                .schema(schema)
                .typeConvert(new MySqlTypeConvert())
                .keyWordsHandler(new MySqlKeyWordsHandler());
    }
}
